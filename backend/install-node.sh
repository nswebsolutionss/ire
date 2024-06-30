#!/bin/bash

CURRENT_DIR=$(pwd)
OPT_HOME=$CURRENT_DIR/opt
NODE_VERSION="20.11.1"
FINAL_NAME="node"

mkdir -p $OPT_HOME

if [ $(uname -p) == "arm" ];
then
  ARCH="arm64"
else
  ARCH="x64"
fi

echo "Downloading Node for $ARCH"

cd "${OPT_HOME}"

curl -LJO https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-darwin-"${ARCH}".tar.xz

echo "Extracting..."
tar xf node-v${NODE_VERSION}-darwin-"${ARCH}".tar.xz
rm node-v${NODE_VERSION}-darwin-"${ARCH}".tar.xz
mv node-v${NODE_VERSION}-darwin-"${ARCH}" ${FINAL_NAME}

cd "${CURRENT_DIR}"


