package com.ire.backend.database;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgBuilder;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceFactory {

    public static Connection ownerDataSource()
    {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String [] serverName = {"localhost"};
        dataSource.setServerNames(serverName);
        dataSource.setDatabaseName("ire");
        dataSource.setUser("superuser");
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(final Connection connection) {
        try{
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlClient vertxSqlClient() {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("ire")
                .setUser("superuser");

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        return PgBuilder
                .client()
                .with(poolOptions)
                .connectingTo(connectOptions)
                .build();
    }
}
