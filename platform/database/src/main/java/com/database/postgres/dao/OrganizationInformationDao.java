package com.database.postgres.dao;

import com.generated.platform.protocol.internal.OrganizationInformation;

import java.sql.SQLException;
import java.util.UUID;

public interface OrganizationInformationDao {

    void getConnection() throws SQLException;

    UUID createOrganizationInformation(final OrganizationInformation organizationInformation) throws SQLException;

    UUID updateOrganizationInformation(final OrganizationInformation organizationInformation) throws SQLException;

    UUID deleteOrganizationInformation(final String organizationInformationId) throws SQLException;

    OrganizationInformation getOrganizationInformation(final String organizationInformationId) throws SQLException;

    void deleteAll() throws SQLException;

    void close() throws SQLException;
}
