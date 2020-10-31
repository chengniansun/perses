#!/usr/bin/env bash

set -o pipefail
set -o nounset
set -o errexit

if [[ "${HOME}" == "" ]] ; then
    echo "ERROR: HOME is empty."
    exit 1
fi
readonly REPO="https://github.com/perses-project/perses.git"
readonly ROOT="${HOME}/.perses"
readonly BUILD_ROOT="${ROOT}/repo"
mkdir -p "${BUILD_ROOT}"
cd "${BUILD_ROOT}"

readonly FOLDER_PERSES="${BUILD_ROOT}/perses"
if [[ ! -e "${FOLDER_PERSES}" ]] ; then
  git clone --depth 1 "${REPO}"
fi
cd "${FOLDER_PERSES}"
git pull --depth 1 "${REPO}"
bazel build //src/org/perses:perses_deploy.jar || true

readonly FOLDER_BIN="${ROOT}/bin"
mkdir -p "${FOLDER_BIN}"
cp "bazel-bin/src/org/perses/perses_deploy.jar" "${FOLDER_BIN}"

readonly FILE_JAR="${FOLDER_BIN}/perses_deploy.jar"
java -jar "${FILE_JAR}" "$@"

