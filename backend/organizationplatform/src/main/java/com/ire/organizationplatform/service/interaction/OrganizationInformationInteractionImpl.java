package com.ire.organizationplatform.service.interaction;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.ire.backend.database.OrganizationInformationDaoImpl;
import com.ire.backend.database.PropertyDetailsDaoImpl;
import com.ire.backend.database.dao.RestDao;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;

public class OrganizationInformationInteractionImpl implements OrganizationServiceInteraction {

    private final RestDao<OrganizationInformation> organizationInformationDao;
    private final RestDao<PropertyDetails> propertyDetailsDao;

    public OrganizationInformationInteractionImpl() {

        this.organizationInformationDao = new OrganizationInformationDaoImpl();
        this.propertyDetailsDao = new PropertyDetailsDaoImpl();
    }

    /**
     * TODO: Log error in dao and return null if error has occurred
     */

    @Override
    public String createOrganizationInformation(final OrganizationInformation organizationInformation) {
        if(organizationInformationDao.get(organizationInformation.getId()) == null) {
            return organizationInformationDao.insert(organizationInformation);
        }
        else {
            return "";
        }
    }

    @Override
    public String updateOrganizationInformation(final OrganizationInformation organizationInformation) {
        return organizationInformationDao.update(organizationInformation);
    }

    @Override
    public String deleteOrganizationInformation(final String uuid) {

        return organizationInformationDao.delete(uuid);
    }

    @Override
    public OrganizationInformation getOrganizationInformation(String uuid) {
        return organizationInformationDao.get(uuid);
    }


    @Override
    public String createPropertyDetails(final PropertyDetails organizationInformation) {
        if(propertyDetailsDao.get(organizationInformation.getId()) == null) {
            return propertyDetailsDao.insert(organizationInformation);
        }
        else {
            return "";
        }
    }

    @Override
    public String updatePropertyDetails(final PropertyDetails organizationInformation) {
        return propertyDetailsDao.update(organizationInformation);
    }

    @Override
    public String deletePropertyDetails(final String uuid) {

        return propertyDetailsDao.delete(uuid);
    }

    @Override
    public PropertyDetails getPropertyDetails(String uuid) {
        return propertyDetailsDao.get(uuid);
    }
}
