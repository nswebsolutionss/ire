#!/bin/bash

export CURRENT_DIR=$(pwd)
export OPT_HOME=$CURRENT_DIR/opt
export POSTGRES_HOME=$OPT_HOME/postgresql-15.6
export PGSQL_HOME=$OPT_HOME/pgsql
export PGSQL_DATA=$OPT_HOME/pgsql/pgsqlData/data
export PGSQL_LOG=$OPT_HOME/log/postgres
rm -r $CURRENT_DIR/opt
mkdir -p $OPT_HOME
mkdir -p $PGSQL_HOME
mkdir -p $PGSQL_LOG

echo "Downloading Postgres 15.6 "
curl -sS https://ftp.postgresql.org/pub/source/v15.6/postgresql-15.6.tar.gz --output opt/postgres-15.6.tar.gz
echo "Unzipping Postgres 15.6 "
tar -xf opt/postgres-15.6.tar.gz -C opt
rm opt/postgres-15.6.tar.gz

cd $POSTGRES_HOME
 ./configure --prefix $PGSQL_HOME
    make
    su
    make install

cd $CURRENT_DIR

