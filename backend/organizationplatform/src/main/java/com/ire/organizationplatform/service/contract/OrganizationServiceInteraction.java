package com.ire.organizationplatform.service.contract;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.domain.PropertyDetails;

public interface OrganizationServiceInteraction {

    String createOrganizationInformation(final OrganizationInformation organizationInformation);

    String updateOrganizationInformation(final OrganizationInformation organizationInformation );

    String deleteOrganizationInformation(final String uuid);

    OrganizationInformation getOrganizationInformation(final String uuid);

    String createPropertyDetails(final PropertyDetails request);

    String updatePropertyDetails(final PropertyDetails propertyDetails);

    String deletePropertyDetails(final String uuid);

    PropertyDetails getPropertyDetails(final String uuid);
}
