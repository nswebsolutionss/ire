package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.generated.organizationplatform.protocol.domain.OrganizationInformation;

public interface OrganizationDao {
    Organization getOrganization(String uuid);

    String deleteOrganization(String uuid);

    String insertOrganization(Organization organization);
}
