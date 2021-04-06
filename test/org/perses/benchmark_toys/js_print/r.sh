#!/usr/bin/env bash

set -o pipefail
set -o nounset

readonly OUTPUT="output.txt.tmp"
readonly SRC="small.js"

rm "${OUTPUT}" || true

if ! command -v "node" ; then
  echo "No NodeJS on the path"
  exit 1
fi

node "${SRC}" &> "${OUTPUT}"

if [[ "${?}" != 0 ]] ; then
  exit 1
fi

if ! grep "Hello World!" "${OUTPUT}" ; then
  exit 1
fi

exit 0
