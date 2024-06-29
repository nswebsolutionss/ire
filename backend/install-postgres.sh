#!/bin/bash

export CURRENT_DIR=$(pwd)
export OPT_HOME=$CURRENT_DIR/opt
export POSTGRES_HOME=$OPT_HOME/postgresql-15.2
export PGSQL_HOME=$OPT_HOME/pgsql
export PGSQL_DATA=$OPT_HOME/pgsqlData/data

mkdir -p $OPT_HOME
mkdir -p $PGSQL_HOME
mkdir -p $PGSQL_DATA

echo "Downloading Postgres 15.2 "
curl -sS https://ftp.postgresql.org/pub/source/v15.2/postgresql-15.2.tar.gz --output opt/postgres-15.2.tar.gz
echo "Unzipping Postgres 15.2 "
tar -xf opt/postgres-15.2.tar.gz -C opt
rm opt/postgres-15.2.tar.gz

cd $POSTGRES_HOME
./configure --prefix $PGSQL_HOME --without-readline
make > /dev/null
make install > /dev/null
cd $CURRENT_DIR





