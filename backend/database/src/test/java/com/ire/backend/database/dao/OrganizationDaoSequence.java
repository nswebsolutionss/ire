package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.ire.backend.database.DataSourceFactory;
import com.ire.backend.database.OrganizationDaoImpl;
import org.junit.jupiter.api.Assertions;

public class OrganizationDaoSequence {

    public static void prepopulateOrganization(final String uuid) {

        OrganizationDao organizationDao = new OrganizationDaoImpl(DataSourceFactory.ownerDataSource());
        Organization organization = new Organization(
                uuid
        );
        String result = organizationDao.insertOrganization(organization);
        Assertions.assertNotNull(result);
    }
}
