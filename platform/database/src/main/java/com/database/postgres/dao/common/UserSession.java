package com.database.postgres.dao.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Types.OTHER;

public class UserSession {


    public static boolean setCurrentUser(String userId, Connection conn) throws SQLException {
        CallableStatement stmt = conn.prepareCall("SELECT create_user(?)");
        stmt.setObject(1, userId, OTHER);
        boolean execute = stmt.execute();
        stmt.close();
        return execute;
    }

}
