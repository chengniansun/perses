#!/usr/bin/env python3
# Simpler reimplementation of Android's sdkmanager
# Extra features of this implementation are pinning and mirroring

# These URLs are the Google repositories containing the list of available
# packages and their versions. The list has been generated by listing the URLs
# fetched while executing `tools/bin/sdkmanager --list`
BASE_REPOSITORY = "https://dl.google.com/android/repository/"
REPOSITORIES = [
    "sys-img/android/sys-img2-1.xml",
    "sys-img/android-wear/sys-img2-1.xml",
    "sys-img/android-wear-cn/sys-img2-1.xml",
    "sys-img/android-tv/sys-img2-1.xml",
    "sys-img/google_apis/sys-img2-1.xml",
    "sys-img/google_apis_playstore/sys-img2-1.xml",
    "addon2-1.xml",
    "glass/addon2-1.xml",
    "extras/intel/addon2-1.xml",
    "repository2-1.xml",
]

# Available hosts: linux, macosx and windows
HOST_OS = "linux"

# Mirroring options
MIRROR_BUCKET = "rust-lang-ci-mirrors"
MIRROR_BUCKET_REGION = "us-west-1"
MIRROR_BASE_DIR = "rustc/android/"

import argparse
import hashlib
import os
import subprocess
import sys
import tempfile
import urllib.request
import xml.etree.ElementTree as ET


class Package:
    def __init__(self, path, url, sha1, deps=None):
        if deps is None:
            deps = []
        self.path = path.strip()
        self.url = url.strip()
        self.sha1 = sha1.strip()
        self.deps = deps

    def download(self, base_url):
        _, file = tempfile.mkstemp()
        url = base_url + self.url
        subprocess.run(["curl", "-o", file, url], check=True)
        # Ensure there are no hash mismatches
        with open(file, "rb") as f:
            sha1 = hashlib.sha1(f.read()).hexdigest()
            if sha1 != self.sha1:
                raise RuntimeError(
                    "hash mismatch for package " + self.path + ": " +
                    sha1 + " vs " + self.sha1 + " (known good)"
                )
        return file

    def __repr__(self):
        return "<Package " + self.path + " at " + self.url + " (sha1=" + self.sha1 + ")"


def fetch_url(url):
    page = urllib.request.urlopen(url)
    return page.read()


def fetch_repository(base, repo_url):
    packages = {}
    root = ET.fromstring(fetch_url(base + repo_url))
    for package in root:
        if package.tag != "remotePackage":
            continue
        path = package.attrib["path"]

        for archive in package.find("archives"):
            host_os = archive.find("host-os")
            if host_os is not None and host_os.text != HOST_OS:
                continue
            complete = archive.find("complete")
            url = os.path.join(os.path.dirname(repo_url), complete.find("url").text)
            sha1 = complete.find("checksum").text

            deps = []
            dependencies = package.find("dependencies")
            if dependencies is not None:
                for dep in dependencies:
                    deps.append(dep.attrib["path"])

            packages[path] = Package(path, url, sha1, deps)
            break

    return packages


def fetch_repositories():
    packages = {}
    for repo in REPOSITORIES:
        packages.update(fetch_repository(BASE_REPOSITORY, repo))
    return packages


class Lockfile:
    def __init__(self, path):
        self.path = path
        self.packages = {}
        if os.path.exists(path):
            with open(path) as f:
                for line in f:
                    path, url, sha1 = line.split(" ")
                    self.packages[path] = Package(path, url, sha1)

    def add(self, packages, name, *, update=True):
        if name not in packages:
            raise NameError("package not found: " + name)
        if not update and name in self.packages:
            return
        self.packages[name] = packages[name]
        for dep in packages[name].deps:
            self.add(packages, dep, update=False)

    def save(self):
        packages = list(sorted(self.packages.values(), key=lambda p: p.path))
        with open(self.path, "w") as f:
            for package in packages:
                f.write(package.path + " " + package.url + " " + package.sha1 + "\n")


def cli_add_to_lockfile(args):
    lockfile = Lockfile(args.lockfile)
    packages = fetch_repositories()
    for package in args.packages:
        lockfile.add(packages, package)
    lockfile.save()


def cli_update_mirror(args):
    lockfile = Lockfile(args.lockfile)
    for package in lockfile.packages.values():
        path = package.download(BASE_REPOSITORY)
        subprocess.run([
            "aws", "s3", "mv", path,
            "s3://" + MIRROR_BUCKET + "/" + MIRROR_BASE_DIR + package.url,
            "--profile=" + args.awscli_profile,
        ], check=True)


def cli_install(args):
    lockfile = Lockfile(args.lockfile)
    for package in lockfile.packages.values():
        # Download the file from the mirror into a temp file
        url = "https://" + MIRROR_BUCKET + ".s3-" + MIRROR_BUCKET_REGION + \
              ".amazonaws.com/" + MIRROR_BASE_DIR
        downloaded = package.download(url)
        # Extract the file in a temporary directory
        extract_dir = tempfile.mkdtemp()
        subprocess.run([
            "unzip", "-q", downloaded, "-d", extract_dir,
        ], check=True)
        # Figure out the prefix used in the zip
        subdirs = [d for d in os.listdir(extract_dir) if not d.startswith(".")]
        if len(subdirs) != 1:
            raise RuntimeError("extracted directory contains more than one dir")
        # Move the extracted files in the proper directory
        dest = os.path.join(args.dest, package.path.replace(";", "/"))
        os.makedirs("/".join(dest.split("/")[:-1]), exist_ok=True)
        os.rename(os.path.join(extract_dir, subdirs[0]), dest)
        os.unlink(downloaded)


def cli():
    parser = argparse.ArgumentParser()
    subparsers = parser.add_subparsers()

    add_to_lockfile = subparsers.add_parser("add-to-lockfile")
    add_to_lockfile.add_argument("lockfile")
    add_to_lockfile.add_argument("packages", nargs="+")
    add_to_lockfile.set_defaults(func=cli_add_to_lockfile)

    update_mirror = subparsers.add_parser("update-mirror")
    update_mirror.add_argument("lockfile")
    update_mirror.add_argument("--awscli-profile", default="default")
    update_mirror.set_defaults(func=cli_update_mirror)

    install = subparsers.add_parser("install")
    install.add_argument("lockfile")
    install.add_argument("dest")
    install.set_defaults(func=cli_install)

    args = parser.parse_args()
    if not hasattr(args, "func"):
        print("error: a subcommand is required (see --help)")
        exit(1)
    args.func(args)


if __name__ == "__main__":
    cli()
