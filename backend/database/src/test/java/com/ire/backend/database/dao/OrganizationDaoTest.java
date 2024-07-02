package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.ire.backend.database.DataSourceFactory;
import com.ire.backend.database.OrganizationDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

@Tag("DaoTest")
public class OrganizationDaoTest {

    private final OrganizationDaoImpl organizationInformationDao = new OrganizationDaoImpl();

    private String calculateUUID() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void shouldGetConnection() {
        Connection connection = DataSourceFactory.ownerDataSource();
        DataSourceFactory.closeConnection(connection);
    }

    @Test
    public void shouldInsertOrganizationAndReturnUUID() {

        String uuid = calculateUUID();

        Organization organization = new Organization(
                uuid
        );

        String result = organizationInformationDao.insertOrganization(organization);

        Assertions.assertEquals(uuid, result);
    }

    @Test
    public void shouldGetOrganizationInformation() {

        String uuid = calculateUUID();

        Organization expectedOrgInfo = new Organization(
                uuid
        );
        OrganizationDao organizationInformationDao = new OrganizationDaoImpl();

        String result = organizationInformationDao.insertOrganization(expectedOrgInfo);

        Organization actualOrgInfo = organizationInformationDao.getOrganization(result);

        Assertions.assertEquals(expectedOrgInfo.getId(), actualOrgInfo.getId());

    }

    @Test
    public void shouldDeleteOrganizationInformationAndReturnUUID() {

        String uuid = calculateUUID();

        Organization expectedOrgInfo = new Organization(
                uuid
        );
        OrganizationDao organizationInformationDao = new OrganizationDaoImpl();

        String result = organizationInformationDao.insertOrganization(expectedOrgInfo);

        Organization actualOrgInfo = organizationInformationDao.getOrganization(result);
        Assertions.assertEquals(expectedOrgInfo.getId(), actualOrgInfo.getId());

        String deletedUuid = organizationInformationDao.deleteOrganization(result);

        Assertions.assertEquals(result, deletedUuid);

        Organization deletedOrgInfo = organizationInformationDao.getOrganization(result);

        Assertions.assertNull(deletedOrgInfo);
    }
}
