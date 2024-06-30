export CURRENT_DIR=$(pwd)
export OPT_DIR=$CURRENT_DIR/opt

install()
{
    if ! [ -d "$OPT_DIR/pgsql" ]
    then ./install-postgres.sh
    fi

    if ! [ -d "$OPT_DIR/jdk" ]
        then ./install-jdk.sh
    fi

    if ! [ -d "$OPT_DIR/node" ]
            then ./install-node.sh
    fi

    if ! [ -d "$OPT_DIR/pnpm" ]
                then ./install-pnpm.sh
        fi

}

install


