package com.ire.backend.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceFactory {

    public static DataSource ownerDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String[] serverName = {"localhost"};
        dataSource.setServerNames(serverName);
        dataSource.setDatabaseName("ire");
        dataSource.setUser("superuser");
        return dataSource;
    }

//    public static Connection ownerDataSource(final String organizationInformationId)
//    {
//        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        String [] serverName = {"localhost"};
//        dataSource.setServerNames(serverName);
//        dataSource.setDatabaseName("ire");
//        dataSource.setUser("superuser");
//        try {
//            Connection connection = dataSource.getConnection();
//            setCurrentAgent(organizationInformationId, connection);
//            return connection;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void closeConnection(final Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
