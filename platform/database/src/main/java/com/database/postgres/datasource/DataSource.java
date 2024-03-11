package com.database.postgres.datasource;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.SQLException;

public class DataSource {

    public static PGSimpleDataSource userDataSource(String userId) throws SQLException {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String [] serverName = {"localhost"};
        dataSource.setServerNames(serverName);
        dataSource.setDatabaseName("test");
        dataSource.setUser(String.valueOf(userId));
        dataSource.setPassword("P4ssword.");
        return dataSource;
    }

    public static PGSimpleDataSource ownerDataSource()
    {

        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String [] serverName = {"localhost"};
        dataSource.setServerNames(serverName);
        dataSource.setDatabaseName("test");
        dataSource.setUser("superuser");
        return dataSource;
    }

}
