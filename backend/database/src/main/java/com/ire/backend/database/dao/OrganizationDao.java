package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.Organization;

public interface OrganizationDao {
    Organization getOrganization(String uuid);

    String deleteOrganization(String uuid);

    String insertOrganization(Organization organization);
}
