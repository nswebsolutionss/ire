package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.ire.backend.database.DataSourceFactory;
import com.ire.backend.database.OrganizationInformationDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

@Tag("DaoTest")
public class OrganizationInformationDaoTest {
    private static int counter = 0;

    private String calculateUUID() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void shouldGetConnection() {
        Connection connection = DataSourceFactory.ownerDataSource();
        DataSourceFactory.closeConnection(connection);
    }

    @Test
    public void shouldInsertOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();

        OrganizationInformation organizationInformation = new OrganizationInformation(
                uuid,
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
        OrganizationInformationDaoImpl organizationInformationDao = new OrganizationInformationDaoImpl();

        String result = organizationInformationDao.insert(organizationInformation);

        Assertions.assertEquals(uuid, result);
    }

    @Test
    public void shouldGetOrganizationInformation() {

        String uuid = calculateUUID();

        OrganizationInformation expectedOrgInfo = new OrganizationInformation(
                uuid,
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
        RestDao<OrganizationInformation> organizationInformationDao = new OrganizationInformationDaoImpl();

        String result = organizationInformationDao.insert(expectedOrgInfo);

        OrganizationInformation actualOrgInfo = organizationInformationDao.get(result);

        assertOrganizationInformation(actualOrgInfo, expectedOrgInfo);
    }

    @Test
    public void shouldDeleteOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();

        OrganizationInformation expectedOrgInfo = new OrganizationInformation(
                uuid,
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
        RestDao<OrganizationInformation> organizationInformationDao = new OrganizationInformationDaoImpl();

        String result = organizationInformationDao.insert(expectedOrgInfo);

        OrganizationInformation actualOrgInfo = organizationInformationDao.get(result);
        assertOrganizationInformation(actualOrgInfo, expectedOrgInfo);

        String deletedUuid = organizationInformationDao.delete(result);

        Assertions.assertEquals(result, deletedUuid);

        OrganizationInformation deletedOrgInfo = organizationInformationDao.get(result);

        Assertions.assertNull(deletedOrgInfo);
    }

    @Test
    public void shouldUpdateOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();

        OrganizationInformation originalOrgInfo = new OrganizationInformation(
                uuid,
                "a company",
                "a description",
                "a telephone number",
                "a website",
                "a facebook url",
                "a instagram url",
                "a youtube url",
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
        RestDao<OrganizationInformation> organizationInformationDao = new OrganizationInformationDaoImpl();

        String result = organizationInformationDao.insert(originalOrgInfo);

        OrganizationInformation actualOrgInfo = organizationInformationDao.get(result);
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
                System.currentTimeMillis()
        );

        String updateUuid = organizationInformationDao.update(updatedOrgInfoExpected);
        Assertions.assertEquals(result, updateUuid);

        OrganizationInformation updatedOrgInfoActual = organizationInformationDao.get(result);

        assertOrganizationInformation(updatedOrgInfoActual, updatedOrgInfoExpected);
    }

    private static void assertOrganizationInformation(OrganizationInformation actualOrgInfo, OrganizationInformation expectedOrgInfo) {
        Assertions.assertNotNull(actualOrgInfo);

        Assertions.assertEquals(expectedOrgInfo.getId(), actualOrgInfo.getId());
        Assertions.assertEquals(expectedOrgInfo.getCompanyName(), actualOrgInfo.getCompanyName());
        Assertions.assertEquals(expectedOrgInfo.getCompanyDescription(), actualOrgInfo.getCompanyDescription());
        Assertions.assertEquals(expectedOrgInfo.getTelephoneNumber(), actualOrgInfo.getTelephoneNumber());
        Assertions.assertEquals(expectedOrgInfo.getWebsiteUrl(), actualOrgInfo.getWebsiteUrl());
        Assertions.assertEquals(expectedOrgInfo.getFacebookUrl(), actualOrgInfo.getFacebookUrl());
        Assertions.assertEquals(expectedOrgInfo.getInstagramUrl(), actualOrgInfo.getInstagramUrl());
        Assertions.assertEquals(expectedOrgInfo.getYoutubeUrl(), actualOrgInfo.getYoutubeUrl());
        Assertions.assertEquals(expectedOrgInfo.getMemberSince(), actualOrgInfo.getMemberSince());
        Assertions.assertEquals(expectedOrgInfo.getLastUpdated(), actualOrgInfo.getLastUpdated());
    }
}
