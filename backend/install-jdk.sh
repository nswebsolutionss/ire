#!/bin/bash

export CURRENT_DIR=$(pwd)
export OPT_HOME=$CURRENT_DIR/opt

mkdir -p $OPT_HOME

if [ $(uname -p) == "arm" ];
then
  ARCH="aarch64"
else
  ARCH="x64"
fi

echo "Downloading Amazon Correto 21 for $ARCH"

curl https://corretto.aws/downloads/resources/21.0.3.9.1/amazon-corretto-21.0.3.9.1-macosx-"$ARCH".tar.gz --output opt/amazon-correto-21.tar.gz

tar -xf opt/amazon-correto-21.tar.gz -C opt
rm -r opt/amazon-correto-21.tar.gz

cd $OPT_HOME
ln -s amazon-corretto-21.jdk jdk
cd $CURRENT_DIR

