package com.ire.organizationplatform.service.contract;

import com.ire.backend.database.OrganizationInformation;
import io.vertx.core.Future;

public interface OrganizationServiceInteraction {

    String createOrganizationInformation(final OrganizationInformation organizationInformation);

    String updateOrganizationInformation(final OrganizationInformation organizationInformation );

    String deleteOrganizationInformation(final String uuid);

    OrganizationInformation getOrganizationInformation(final String uuid);
}
