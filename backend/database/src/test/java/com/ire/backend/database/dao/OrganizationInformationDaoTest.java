package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.ire.backend.database.DataSourceFactory;
import com.ire.backend.database.OrganizationInformationDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

import static com.ire.backend.database.dao.OrganizationDaoSequence.prepopulateOrganization;

@Tag("DaoTest")
public class OrganizationInformationDaoTest {

    private String calculateUUID() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void shouldGetConnection() throws SQLException {
        DataSource connection = DataSourceFactory.ownerDataSource();
        DataSourceFactory.closeConnection(connection.getConnection());
    }

    @Test
    public void shouldInsertOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();
        prepopulateOrganization(uuid);

        OrganizationInformation organizationInformation = new OrganizationInformation(
                "",
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                uuid
        );
        OrganizationInformationDaoImpl organizationInformationDao = new OrganizationInformationDaoImpl(DataSourceFactory.ownerDataSource());

        String result = organizationInformationDao.insertOrganizationInformation(organizationInformation);

        Assertions.assertNotNull(result);
    }

    @Test
    public void shouldGetOrganizationInformation() {

        String uuid = calculateUUID();
        prepopulateOrganization(uuid);

        OrganizationInformation expectedOrgInfo = new OrganizationInformation(
                "",
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                uuid
        );
        OrganizationInformationDao organizationInformationDao = new OrganizationInformationDaoImpl(DataSourceFactory.ownerDataSource());

        String result = organizationInformationDao.insertOrganizationInformation(expectedOrgInfo);

        OrganizationInformation actualOrgInfo = organizationInformationDao.getOrganizationInformation(result);

        assertOrganizationInformation(actualOrgInfo, expectedOrgInfo);
    }

    @Test
    public void shouldDeleteOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();
        prepopulateOrganization(uuid);

        OrganizationInformation expectedOrgInfo = new OrganizationInformation(
                "",
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                uuid
        );
        OrganizationInformationDao organizationInformationDao = new OrganizationInformationDaoImpl(DataSourceFactory.ownerDataSource());

        String result = organizationInformationDao.insertOrganizationInformation(expectedOrgInfo);

        OrganizationInformation actualOrgInfo = organizationInformationDao.getOrganizationInformation(result);
        assertOrganizationInformation(actualOrgInfo, expectedOrgInfo);

        String deletedUuid = organizationInformationDao.deleteOrganizationInformation(result);

        Assertions.assertEquals(result, deletedUuid);

        OrganizationInformation deletedOrgInfo = organizationInformationDao.getOrganizationInformation(result);

        Assertions.assertNull(deletedOrgInfo);
    }

    @Test
    public void shouldUpdateOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();
        prepopulateOrganization(uuid);

        OrganizationInformation originalOrgInfo = new OrganizationInformation(
                "",
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                uuid
        );
        OrganizationInformationDao organizationInformationDao = new OrganizationInformationDaoImpl(DataSourceFactory.ownerDataSource());

        String result = organizationInformationDao.insertOrganizationInformation(originalOrgInfo);

        OrganizationInformation actualOrgInfo = organizationInformationDao.getOrganizationInformation(result);
        assertOrganizationInformation(actualOrgInfo, originalOrgInfo);

        OrganizationInformation updatedOrgInfoExpected = new OrganizationInformation(
                result,
                "a new company",
                "a new description",
                "a new telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                uuid
        );

        String updateUuid = organizationInformationDao.updateOrganizationInformation(updatedOrgInfoExpected);
        Assertions.assertEquals(result, updateUuid);

        OrganizationInformation updatedOrgInfoActual = organizationInformationDao.getOrganizationInformation(result);

        assertOrganizationInformation(updatedOrgInfoActual, updatedOrgInfoExpected);
    }

    private static void assertOrganizationInformation(OrganizationInformation actualOrgInfo, OrganizationInformation expectedOrgInfo) {
        Assertions.assertNotNull(actualOrgInfo);

        Assertions.assertEquals(expectedOrgInfo.getCompanyName(), actualOrgInfo.getCompanyName());
        Assertions.assertEquals(expectedOrgInfo.getCompanyDescription(), actualOrgInfo.getCompanyDescription());
        Assertions.assertEquals(expectedOrgInfo.getTelephoneNumber(), actualOrgInfo.getTelephoneNumber());
        Assertions.assertEquals(expectedOrgInfo.getWebsiteUrl(), actualOrgInfo.getWebsiteUrl());
        Assertions.assertEquals(expectedOrgInfo.getFacebookUrl(), actualOrgInfo.getFacebookUrl());
        Assertions.assertEquals(expectedOrgInfo.getInstagramUrl(), actualOrgInfo.getInstagramUrl());
        Assertions.assertEquals(expectedOrgInfo.getYoutubeUrl(), actualOrgInfo.getYoutubeUrl());
        Assertions.assertEquals(expectedOrgInfo.getMemberSince(), actualOrgInfo.getMemberSince());
        Assertions.assertEquals(expectedOrgInfo.getLastUpdated(), actualOrgInfo.getLastUpdated());
        Assertions.assertEquals(expectedOrgInfo.getOrganizationId(), actualOrgInfo.getOrganizationId());
    }
}
