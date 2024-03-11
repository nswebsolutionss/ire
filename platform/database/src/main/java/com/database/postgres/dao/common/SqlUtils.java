package com.database.postgres.dao.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlUtils {
    public static UUID getIdFromResultSet(UUID generatedKey, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
        {
            generatedKey = rs.getObject(1, UUID.class);
        }
        ps.close();
        return generatedKey;
    }
}
