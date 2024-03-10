#!/bin/bash

cd "$(dirname "$0")"
CURRENT_DIR="$(pwd)"

if [ ! -d $CURRENT_DIR/opt/node ]; then
     source $CURRENT_DIR/install-scripts/install-node.sh
fi