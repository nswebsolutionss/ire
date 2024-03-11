#!/bin/bash

cd "$(dirname "$0")"
CURRENT_DIR="$(pwd)"

if [ ! -d $CURRENT_DIR/opt/pgsql ]; then
     source $CURRENT_DIR/installScripts/install-postgress.sh
fi

pkill postgres
./gradlew build
java -jar releases/orchestrator/build/libs/orchestrator.jar


