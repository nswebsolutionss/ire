package com.ire.backend.database.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Types.OTHER;

public class CurrentAgentSession {

    public static boolean setCurrentAgent(final String organizationInformationId, final Connection conn) throws SQLException {
        CallableStatement stmt = conn.prepareCall("SELECT upsert_current_organization(?)");
        stmt.setObject(1, organizationInformationId, OTHER);
        boolean execute = stmt.execute();
        stmt.close();
        return execute;
    }
}
