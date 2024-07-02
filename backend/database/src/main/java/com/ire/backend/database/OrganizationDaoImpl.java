package com.ire.backend.database;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.ire.backend.database.dao.OrganizationDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ire.backend.database.DataSourceFactory.closeConnection;

public class OrganizationDaoImpl implements OrganizationDao {

    private static final Logger LOGGER = LogManager.getLogger();
    private final String[] idColumn = new String[]{"id"};

    @Override
    public String insertOrganization(Organization organization) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT into organization (id) VALUES (?);",
                    idColumn
            );

            ps.setString(1, organization.getId());
            ps.execute();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                String key = generatedKeys.getString(1);
                System.out.println(key);
                return key;
            } else {
                LOGGER.warn("Unable to INSERT organization: " + organization);
                return null;
            }
        } catch (final SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public Organization getOrganization(final String organizationId) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            String sql = "SELECT id FROM organization WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, organizationId);
            ResultSet rs = ps.executeQuery();
            Organization organization = null;
            if (rs.next()) {
                organization = new Organization(rs.getString("id"));
            }
            rs.close();
            ps.close();
            return organization;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public String deleteOrganization(final String uuid) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM organization WHERE id = ?", idColumn);
            String generatedKey = null;
            ps.setString(1, uuid);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getString(1);
            }
            ps.close();
            rs.close();
            return generatedKey;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }
}
