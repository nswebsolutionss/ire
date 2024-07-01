package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.domain.PropertyDetails;

import java.util.List;

public interface PropertyDetailsDao {
    PropertyDetails getPropertyDetails(String uuid);

    List<PropertyDetails> getAllPropertiesDetailsForOrganizationId(String uuid);

    List<PropertyDetails> getAllProperties();

    String updatePropertyDetails(PropertyDetails propertyDetails);

    String deletePropertyDetails(String uuid);

    String insertPropertyDetails(PropertyDetails propertyDetails);
}
