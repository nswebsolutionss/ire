#!/usr/bin/env bash

NODE_VERSION="20.11.1"


export CURRENT_DIR=$(pwd)
export OPT_HOME=$CURRENT_DIR/opt
export NODE_HOME=$OPT_HOME/node
mkdir -p $OPT_HOME
mkdir $NODE_HOME

echo "Downloading Node Version ${NODE_VERSION}"
curl -LJO https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-darwin-x64.tar.xz 
mv node-v${NODE_VERSION}-darwin-x64.tar.xz opt/node-v${NODE_VERSION}-darwin-x64.tar.xz
echo "Unzipping Postgres 15.6 "
tar -xf opt/node-v${NODE_VERSION}-darwin-x64.tar.xz  -C opt
mv opt/node-v${NODE_VERSION}-darwin-x64 opt/node
rm opt/node-v${NODE_VERSION}-darwin-x64.tar.xz

