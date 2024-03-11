package com.database.postgres.dao.impl;

import com.database.postgres.dao.OrganizationInformationDao;
import com.database.postgres.datasource.DataSource;
import com.generated.platform.protocol.internal.OrganizationInformation;

import java.sql.*;
import java.util.UUID;

import static com.database.postgres.dao.common.SqlUtils.getIdFromResultSet;
import static java.sql.Types.OTHER;

public class OrganizationInformationImpl implements OrganizationInformationDao {
    private Connection conn;

    @Override
    public void getConnection() throws SQLException {
        javax.sql.DataSource dataSource = DataSource.ownerDataSource();
        this.conn = dataSource.getConnection();
    }

    @Override
    public UUID createOrganizationInformation(OrganizationInformation organizationInformation) throws SQLException {
        String sql = "INSERT INTO organization_information(" +
                "organization_id, " +
                "telephone_number, " +
                "website_url, " +
                "facebook_url, " +
                "instagram_url, " +
                "youtube_url, " +
                "company_description, " +
                "member_since, " +
                "last_updated, " +
                "company_name" +
                ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        UUID generatedKey = null;
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setObject(1, organizationInformation.getOrganizationId(), OTHER);
        ps.setString(2, organizationInformation.getTelephoneNumber());
        ps.setString(3, organizationInformation.getWebsiteUrl());
        ps.setString(4, organizationInformation.getFacebookUrl());
        ps.setString(5, organizationInformation.getInstagramUrl());
        ps.setString(6, organizationInformation.getYoutubeUrl());
        ps.setString(7, organizationInformation.getCompanyDescription());
        ps.setLong(8, organizationInformation.getMemberSince());
        ps.setLong(9, organizationInformation.getLastUpdated());
        ps.setString(10, organizationInformation.getCompanyName());
        ps.execute();
        return getIdFromResultSet(generatedKey, ps);
    }

    @Override
    public UUID updateOrganizationInformation(OrganizationInformation organizationInformation) throws SQLException {
        String sql = "UPDATE organization_information set " +
                "telephone_number = ?, " +
                "website_url = ?, " +
                "facebook_url = ?, " +
                "instagram_url = ?, " +
                "youtube_url = ?, " +
                "company_description = ?, " +
                "member_since = ?, " +
                "last_updated = ? ," +
                "company_name = ? " +
                "WHERE organization_id = ?";

        UUID generatedKey = null;
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, organizationInformation.getTelephoneNumber());
        ps.setString(2, organizationInformation.getWebsiteUrl());
        ps.setString(3, organizationInformation.getFacebookUrl());
        ps.setString(4, organizationInformation.getInstagramUrl());
        ps.setString(5, organizationInformation.getYoutubeUrl());
        ps.setString(6, organizationInformation.getCompanyDescription());
        ps.setLong(7, organizationInformation.getMemberSince());
        ps.setLong(8, organizationInformation.getLastUpdated());
        ps.setString(9, organizationInformation.getCompanyName());
        ps.setObject(10, organizationInformation.getOrganizationId(), OTHER);

        ps.execute();
        return getIdFromResultSet(generatedKey, ps);
    }

    @Override
    public UUID deleteOrganizationInformation(String organizationInformationId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM organization_information WHERE organization_id = ?", Statement.RETURN_GENERATED_KEYS);
        UUID generatedKey = null;
        ps.setObject(1, organizationInformationId, OTHER);

        ps.execute();
        return getIdFromResultSet(generatedKey, ps);
    }

    @Override
    public OrganizationInformation getOrganizationInformation(String organizationInformationId) throws SQLException {
        String sql = "SELECT organization_id, " +
                "    company_name, " +
                "    telephone_number, " +
                "    website_url, " +
                "    facebook_url, " +
                "    instagram_url, " +
                "    youtube_url, " +
                "    company_description, " +
                "    member_since, " +
                "    last_updated FROM organization_information WHERE organization_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, organizationInformationId, OTHER);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            return new OrganizationInformation()
                    .withOrganizationId(rs.getObject("organization_id", UUID.class).toString())
                    .withCompanyName(rs.getString("company_name"))
                    .withTelephoneNumber(rs.getString("telephone_number"))
                    .withWebsiteUrl(rs.getString("website_url"))
                    .withFacebookUrl(rs.getString("facebook_url"))
                    .withInstagramUrl(rs.getString("instagram_url"))
                    .withYoutubeUrl(rs.getString("youtube_url"))
                    .withCompanyDescription(rs.getString("company_description"))
                    .withMemberSince(rs.getLong("member_since"))
                    .withLastUpdated(rs.getLong("last_updated"));

        }
        rs.close();
        ps.close();
        return null;
    }

    @Override
    public void deleteAll() throws SQLException {
        throw new SQLException("Not supported");
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }
}
