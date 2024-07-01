package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;

public interface OrganizationInformationDao {
    OrganizationInformation getOrganizationInformation(String uuid);

    String updateOrganizationInformation(OrganizationInformation organizationInformation);

    String deleteOrganizationInformation(String uuid);

    String insertOrganizationInformation(OrganizationInformation organizationInformation);
}
