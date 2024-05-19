export CURRENT_DIR=$(pwd)
export OPT_DIR=$CURRENT_DIR/opt

export POSTGRESQL="packages/postgres-15.2"

cd $CURRENT_DIR

install()
{
    pushd $OPT_DIR
    if [ -f "$POSTGRESQL.tar.gz" ]
    then tar -xf "$POSTGRESQL.tar.gz"
      else echo "$POSTGRESQL DOESNT EXIST"
        popd
        ./install-postgres.sh && install
    fi

    if [ -f "packages/amazon-correto-21.tar.gz" ]
        then tar -xf "packages/amazon-correto-21.tar.gz"
          else echo "packages/amazon-correto-21 DOESNT EXIST"
            popd
            ./install-jdk.sh && install
    fi

    popd

}

install


