#!/usr/bin/env python3

import os
import re
import subprocess


def create_tag():
    subprocess.check_call(["git", "fetch", "--all", "--tags"]) 
    # create new tag as per current release
    stream = os.popen('git describe --abbrev=0 --tags')
    current_tag = stream.read().strip()

    if not re.match("^v[0-9]+[.][0-9]", current_tag):
        raise Exception("Error: tag name does not follow expected pattern.")

    version = current_tag[1:].split('.')

    major_version = int(version[0])
    minor_version = int(version[1])
    increment, new_minor_version = divmod(minor_version + 1, 10)

    return f"v{major_version + increment}.{new_minor_version}"


def build_binary():
    # return built binary location
    print("building binary...")
    build_command = ['bazel', 'build', '//src/org/perses:perses_deploy.jar']

    pipe = None
    subprocess.check_call(
        build_command,
        stdout=pipe,
        stderr=pipe)
    # assume constant build path
    return "bazel-bin/src/org/perses/perses_deploy.jar"


def main():
    # ensure in root folder
    if not os.path.exists("WORKSPACE"):
        raise Exception('ERROR: This script should be run in the root folder of the project.')

    tag_name = create_tag()
    title = f"Perses {tag_name}"

    # get built binary path
    jar = build_binary()

    # release
    release_command = ['hub', 'release', 'create', '--browse', f"--attach={jar}", f'--message={title}', tag_name]

    pipe = None
    subprocess.check_call(
        release_command,
        stdout=pipe,
        stderr=pipe)


if __name__ == "__main__":
    main()
