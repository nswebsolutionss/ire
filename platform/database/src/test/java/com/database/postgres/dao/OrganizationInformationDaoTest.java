package com.database.postgres.dao;

import com.database.postgres.dao.impl.OrganizationInformationImpl;
import com.generated.platform.protocol.internal.OrganizationInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationInformationDaoTest {

    @Test
    public void shouldInsertOrganizationInformationData() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(organizationId.toString())
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        dao.createOrganizationInformation(data);
    }

    @Test
    public void shouldInsertAndReturnUUIDOfEntry() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(organizationId.toString())
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        UUID uuid = dao.createOrganizationInformation(data);
        assertEquals(organizationId, uuid);
    }

    @Test
    public void shouldInsertAndGetByIdOrganizationData() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(organizationId.toString())
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        dao.createOrganizationInformation(data);
        OrganizationInformation actual = dao.getOrganizationInformation(organizationId.toString());
        deepAssertOrganizationInformation(data, actual);
    }

    @Test
    public void shouldInsertAndDeleteByIdOrganizationData() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(organizationId.toString())
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        dao.createOrganizationInformation(data);
        OrganizationInformation actual = dao.getOrganizationInformation(organizationId.toString());
        deepAssertOrganizationInformation(data, actual);

        UUID uuid = dao.deleteOrganizationInformation(organizationId.toString());
        Assertions.assertEquals(organizationId, uuid);

        OrganizationInformation deleted = dao.getOrganizationInformation(organizationId.toString());
        Assertions.assertNull(deleted);
    }

    @Test
    public void shouldInsertAndUpdateByIdOrganizationData() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(organizationId.toString())
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        // Assert the insert was successful
        dao.createOrganizationInformation(data);
        OrganizationInformation actual = dao.getOrganizationInformation(organizationId.toString());
        deepAssertOrganizationInformation(data, actual);

        // Update original object
        data.withCompanyDescription("new company description");
        UUID updatedEntryId = dao.updateOrganizationInformation(data);
        Assertions.assertEquals(organizationId, updatedEntryId);

        OrganizationInformation updated = dao.getOrganizationInformation(organizationId.toString());
        deepAssertOrganizationInformation(data, updated);
    }

    @Test
    public void shouldReturnNullWhenUpdatingRowThatDoesntExist() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(organizationId.toString())
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        UUID uuid = dao.updateOrganizationInformation(data);
        Assertions.assertNull(uuid);
    }

    @Test
    public void shouldReturnNullWhenDeletingRowThatDoesntExist() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        UUID organizationId = UUID.randomUUID();
        dao.getConnection();

        UUID uuid = dao.deleteOrganizationInformation(organizationId.toString());
        Assertions.assertNull(uuid);
    }

    @Test
    public void shouldErrorWhenOrganizationIdIsNullOnInsert() {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        try {
            dao.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(null)
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        try {
            dao.createOrganizationInformation(data);
        } catch (SQLException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    public void shouldErrorWhenOrganizationIdIsNullOnUpdate() throws Exception {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        try {
            dao.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        OrganizationInformation data = new OrganizationInformation()
                .withOrganizationId(null)
                .withCompanyName("company name")
                .withCompanyDescription("company description")
                .withTelephoneNumber("07446511454")
                .withYoutubeUrl("youtube")
                .withFacebookUrl("facebook")
                .withInstagramUrl("instagram")
                .withWebsiteUrl("website")
                .withLastUpdated(System.currentTimeMillis())
                .withMemberSince(System.currentTimeMillis());

        try {
            dao.updateOrganizationInformation(data);

        } catch (SQLException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    public void shouldErrorWhenOrganizationIdIsNullOnGet() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        dao.getConnection();

        try {
            dao.getOrganizationInformation(null);

        } catch (SQLException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    public void shouldErrorWhenOrganizationIdIsNullOnDelete() throws SQLException {
        OrganizationInformationDao dao = new OrganizationInformationImpl();
        dao.getConnection();

        try {
            dao.deleteOrganizationInformation(null);

        } catch (SQLException e) {
            Assertions.assertNotNull(e);
        }
    }

    public static void deepAssertOrganizationInformation(final OrganizationInformation expected, final OrganizationInformation actual) {
        Assertions.assertNotNull(expected);
        Assertions.assertNotNull(actual);

        Assertions.assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
        Assertions.assertEquals(expected.getCompanyName(), actual.getCompanyName());
        Assertions.assertEquals(expected.getCompanyDescription(), actual.getCompanyDescription());
        Assertions.assertEquals(expected.getTelephoneNumber(), actual.getTelephoneNumber());
        Assertions.assertEquals(expected.getYoutubeUrl(), actual.getYoutubeUrl());
        Assertions.assertEquals(expected.getFacebookUrl(), actual.getFacebookUrl());
        Assertions.assertEquals(expected.getInstagramUrl(), actual.getInstagramUrl());
        Assertions.assertEquals(expected.getWebsiteUrl(), actual.getWebsiteUrl());
        Assertions.assertEquals(expected.getLastUpdated(), actual.getLastUpdated());
        Assertions.assertEquals(expected.getMemberSince(), actual.getMemberSince());
    }
}
