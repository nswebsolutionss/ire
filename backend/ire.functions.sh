#!/usr/bin/env bash

function usages() {
  echo "IRE entry point script"
  echo "ire commit (runs all commit build tests)"
  echo "ire daoTest (runs all dao tests)"
  echo "ire g (runs gradle wrapper with provided args)"
}

function drs() {
  gradle build
  ./build/buildartifacts/dists/database-wrapper/bin/database-wrapper DEPLOY
}
commit()
{
  ./gradlew commit
}

daoTest()
{
  ./gradlew daoTest
}

package()
{
  ./gradlew installDist
}

gradle()
{
  ./gradlew $@
}