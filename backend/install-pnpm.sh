#!/bin/bash

CURRENT_DIR=$(pwd)
OPT_HOME=$CURRENT_DIR/opt
PNPM_VERSION="9.4.0"
FINAL_NAME="pnpm"

mkdir -p $OPT_HOME

if [ $(uname -p) == "arm" ];
then
  ARCH="arm64"
else
  ARCH="x64"
fi


echo "Fetching version of pnpm... $(pwd)"

archive_url="https://github.com/pnpm/pnpm/releases/download/v${PNPM_VERSION}/pnpm-macos-${ARCH}"

cd $OPT_HOME

mkdir -p ${FINAL_NAME}
curl -fsSL "${archive_url}" >"${FINAL_NAME}/pnpm"
chmod +x "${FINAL_NAME}/pnpm"

cd $CURRENT_DIR


