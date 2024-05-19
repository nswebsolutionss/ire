#!/usr/bin/env bash

function usages() {
  echo "IRE entry point script"
  echo "ire commit (runs all commit build tests)"
  echo "ire daoTest (runs all dao tests)"
  echo "ire g (runs gradle wrapper with provided args)"
}

function drs() {
  gradle build
  java -jar databasewrapper/build/libs/database-wrapper.jar DEPLOY
}
commit()
{
  ./gradlew commit
}

daoTest()
{
  ./gradlew daoTest
}

gradle()
{
  ./gradlew $@
}