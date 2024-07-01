#!/usr/bin/env bash

function usages() {
  echo "IRE entry point script"
  echo "ire commit (runs all commit build tests)"
  echo "ire daoTest (runs all dao tests)"
  echo "ire integrationTest (runs all integration tests)"
  echo "ire allTests (runs a drs followed by all tests)"
  echo "ire g (runs gradle wrapper with provided args)"
}

function drs() {
  gradle build
  gradle installDist
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

integrationTest()
{
  ./gradlew integrationTest
}

package()
{
  ./gradlew installDist
}

gradle()
{
  ./gradlew $@
}

allTests()
{
  drs && ./gradlew daoTest && ./gradlew integrationTest
}