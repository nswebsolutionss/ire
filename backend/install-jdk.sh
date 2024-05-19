#!/bin/bash

export CURRENT_DIR=$(pwd)
export OPT_HOME=$CURRENT_DIR/opt

mkdir -p $OPT_HOME

echo "Downloading Amazon Correto 21 "
curl -sS https://corretto.aws/downloads/resources/21.0.3.9.1/amazon-corretto-21.0.3.9.1-macosx-x64.tar.gz --output opt/packages/amazon-correto-21.tar.gz

