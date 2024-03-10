#!/bin/bash

cd "$(dirname "$0")"
CURRENT_DIR="$(pwd)"

if [ ! -d $CURRENT_DIR/opt/pgsql ]; then
     source $CURRENT_DIR/install-scripts/install-postgres.sh
fi