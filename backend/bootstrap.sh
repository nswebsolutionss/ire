export CURRENT_DIR=$(pwd)
export OPT_DIR=$CURRENT_DIR/opt

install()
{
    if ! [ -d "$OPT_DIR/pgsql" ]
    then ./install-postgres.sh
    fi

    if ! [ -d "$OPT_DIR/amazon-corretto-21.jdk" ]
        then ./install-jdk.sh
    fi

}

install


