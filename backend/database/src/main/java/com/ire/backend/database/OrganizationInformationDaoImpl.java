package com.ire.backend.database;

import com.ire.backend.database.dao.RestDao;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static com.ire.backend.database.DataSourceFactory.closeConnection;
import static java.sql.Types.OTHER;

public class OrganizationInformationDaoImpl implements RestDao<OrganizationInformation> {

    private static final Logger LOGGER = LogManager.getLogger();
    private final String[] idColumn = new String[]{"id"};

    @Override
    public String insert(OrganizationInformation organizationInformation) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT into organization_information (" +
                    "id, " +
                    "company_name," +
                    "company_description," +
                    "telephone_number," +
                    "website_url," +
                    "facebook_url," +
                    "instagram_url," +
                    "youtube_url," +
                    "member_since," +
                    "last_updated) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", idColumn);

            ps.setObject(1, organizationInformation.id(), OTHER);
            ps.setString(2, organizationInformation.companyName());
            ps.setString(3, organizationInformation.companyDescription());
            ps.setString(4, organizationInformation.telephoneNumber());
            ps.setString(5, organizationInformation.websiteUrl());
            ps.setString(6, organizationInformation.facebookUrl());
            ps.setString(7, organizationInformation.instagramUrl());
            ps.setString(8, organizationInformation.youtubeUrl());
            ps.setLong(9, organizationInformation.memberSince());
            ps.setLong(10, organizationInformation.lastUpdated());
            ps.execute();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                String key = generatedKeys.getString(1);
                System.out.println(key);
                return key;
            } else {
                LOGGER.warn("Unable to INSERT organizationInformation: " + organizationInformation);
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
    public OrganizationInformation get(final String uuid) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            String sql = "SELECT id, " +
                    "    company_name, " +
                    "    company_description, " +
                    "    telephone_number, " +
                    "    website_url, " +
                    "    facebook_url, " +
                    "    instagram_url, " +
                    "    youtube_url, " +
                    "    member_since, " +
                    "    last_updated FROM organization_information WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, uuid, OTHER);
            ResultSet rs = ps.executeQuery();
            OrganizationInformation organizationInformation = null;
            if (rs.next()) {

                organizationInformation = new OrganizationInformation(
                        rs.getString("id"),
                        rs.getString("company_name"),
                        rs.getString("company_description"),
                        rs.getString("telephone_number"),
                        rs.getString("website_url"),
                        rs.getString("facebook_url"),
                        rs.getString("instagram_url"),
                        rs.getString("youtube_url"),
                        rs.getLong("member_since"),
                        rs.getLong("last_updated")
                );
            }
            rs.close();
            ps.close();
            return organizationInformation;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public String delete(final String uuid) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM organization_information WHERE id = ?", idColumn);
            String generatedKey = null;
            ps.setObject(1, uuid, OTHER);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getString(1);
            }
            ps.close();
            rs.close();
            return generatedKey;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public String update(final OrganizationInformation organizationInformation) {
        Connection connection = DataSourceFactory.ownerDataSource();
        try {
            String sql = "UPDATE organization_information set " +
                    "company_name = ?, " +
                    "company_description = ?, " +
                    "telephone_number = ?, " +
                    "website_url = ?, " +
                    "facebook_url = ?, " +
                    "instagram_url = ?, " +
                    "youtube_url = ?, " +
                    "member_since = ?, " +
                    "last_updated = ? " +
                    "WHERE id = ?";

            String generatedKey = null;
            PreparedStatement ps = connection.prepareStatement(sql, 1);

            ps.setString(1, organizationInformation.companyName());
            ps.setString(2, organizationInformation.companyDescription());
            ps.setString(3, organizationInformation.telephoneNumber());
            ps.setString(4, organizationInformation.websiteUrl());
            ps.setString(5, organizationInformation.facebookUrl());
            ps.setString(6, organizationInformation.instagramUrl());
            ps.setString(7, organizationInformation.youtubeUrl());
            ps.setLong(8, organizationInformation.memberSince());
            ps.setLong(9, organizationInformation.lastUpdated());
            ps.setObject(10, organizationInformation.id(), OTHER);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getString(1);
            }
            ps.close();
            rs.close();
            return generatedKey;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

}