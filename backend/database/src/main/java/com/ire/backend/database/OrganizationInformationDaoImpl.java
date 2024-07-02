package com.ire.backend.database;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.ire.backend.database.dao.OrganizationInformationDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ire.backend.database.DataSourceFactory.closeConnection;
import static java.sql.Types.OTHER;

public class OrganizationInformationDaoImpl implements OrganizationInformationDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private final String[] idColumn = new String[]{"id"};
    private final DataSource dataSource;

    public OrganizationInformationDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Received exception when attempting to get dataSource connection: " + e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public String insertOrganizationInformation(OrganizationInformation organizationInformation) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT into organization_information (" +
                    "company_name," +
                    "company_description," +
                    "telephone_number," +
                    "website_url," +
                    "facebook_url," +
                    "instagram_url," +
                    "youtube_url," +
                    "member_since," +
                    "last_updated," +
                    "organization_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", idColumn);

            ps.setString(1, organizationInformation.getCompanyName());
            ps.setString(2, organizationInformation.getCompanyDescription());
            ps.setString(3, organizationInformation.getTelephoneNumber());
            ps.setString(4, organizationInformation.getWebsiteUrl());
            ps.setString(5, organizationInformation.getFacebookUrl());
            ps.setString(6, organizationInformation.getInstagramUrl());
            ps.setString(7, organizationInformation.getYoutubeUrl());
            ps.setLong(8, organizationInformation.getMemberSince());
            ps.setLong(9, organizationInformation.getLastUpdated());
            ps.setObject(10, organizationInformation.getOrganizationId(), OTHER);
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
    public OrganizationInformation getOrganizationInformation(final String organizationInformationId) {
        Connection connection = getConnection();
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
                    "    organization_id, " +
                    "    last_updated FROM organization_information WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, Long.parseLong(organizationInformationId));
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
                        rs.getLong("last_updated"),
                        rs.getString("organization_id")
                );
            }
            rs.close();
            ps.close();
            return organizationInformation;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public String deleteOrganizationInformation(final String uuid) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM organization_information WHERE id = ?", idColumn);
            String generatedKey = null;
            ps.setLong(1, Long.parseLong(uuid));

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

    @Override
    public String updateOrganizationInformation(final OrganizationInformation organizationInformation) {
        Connection connection = getConnection();
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

            ps.setString(1, organizationInformation.getCompanyName());
            ps.setString(2, organizationInformation.getCompanyDescription());
            ps.setString(3, organizationInformation.getTelephoneNumber());
            ps.setString(4, organizationInformation.getWebsiteUrl());
            ps.setString(5, organizationInformation.getFacebookUrl());
            ps.setString(6, organizationInformation.getInstagramUrl());
            ps.setString(7, organizationInformation.getYoutubeUrl());
            ps.setLong(8, organizationInformation.getMemberSince());
            ps.setLong(9, organizationInformation.getLastUpdated());
            ps.setLong(10, Long.parseLong(organizationInformation.getId()));

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
