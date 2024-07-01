package com.ire.organizationplatform.service.interaction;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.ire.backend.database.OrganizationDaoImpl;
import com.ire.backend.database.OrganizationInformationDaoImpl;
import com.ire.backend.database.PropertyDetailsDaoImpl;
import com.ire.backend.database.dao.OrganizationDao;
import com.ire.backend.database.dao.OrganizationInformationDao;
import com.ire.backend.database.dao.PropertyDetailsDao;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;

import java.util.List;

public class AccountInformationInteractionImpl implements AccountServiceInteraction {

    private final OrganizationDao organizationDao;
    private final OrganizationInformationDao organizationInformationDao;
    private final PropertyDetailsDao propertyDetailsDao;

    public AccountInformationInteractionImpl() {

        this.organizationDao = new OrganizationDaoImpl();
        this.organizationInformationDao = new OrganizationInformationDaoImpl();
        this.propertyDetailsDao = new PropertyDetailsDaoImpl();
    }

    /**
     * TODO: Log error in dao and return null if error has occurred
     */

    @Override
    public String createOrganization(final Organization organizationInformation) {
        if (organizationDao.getOrganization(organizationInformation.getId()) == null) {
            return organizationDao.insertOrganization(organizationInformation);
        } else {
            return "";
        }
    }

    @Override
    public String deleteOrganization(final String uuid) {

        return organizationDao.deleteOrganization(uuid);
    }

    @Override
    public Organization getOrganization(String uuid) {
        return organizationDao.getOrganization(uuid);
    }

    @Override
    public String createOrganizationInformation(final OrganizationInformation organizationInformation) {
        return organizationInformationDao.insertOrganizationInformation(organizationInformation);

    }

    @Override
    public String updateOrganizationInformation(final OrganizationInformation organizationInformation) {
        return organizationInformationDao.updateOrganizationInformation(organizationInformation);
    }

    @Override
    public String deleteOrganizationInformation(final String uuid) {

        return organizationInformationDao.deleteOrganizationInformation(uuid);
    }

    @Override
    public OrganizationInformation getOrganizationInformation(String uuid) {
        return organizationInformationDao.getOrganizationInformation(uuid);
    }


    @Override
    public String createPropertyDetails(final PropertyDetails organizationInformation) {
        if (propertyDetailsDao.getPropertyDetails(organizationInformation.getId()) == null) {
            return propertyDetailsDao.insertPropertyDetails(organizationInformation);
        } else {
            return "";
        }
    }

    @Override
    public String updatePropertyDetails(final PropertyDetails organizationInformation) {
        return propertyDetailsDao.updatePropertyDetails(organizationInformation);
    }

    @Override
    public String deletePropertyDetails(final String uuid) {

        return propertyDetailsDao.deletePropertyDetails(uuid);
    }

    @Override
    public PropertyDetails getPropertyDetails(String uuid) {
        return propertyDetailsDao.getPropertyDetails(uuid);
    }

    @Override
    public List<PropertyDetails> getAllPropertyDetailsForOrganizationId(String organizationId) {
        return propertyDetailsDao.getAllPropertiesDetailsForOrganizationId(organizationId);
    }

    @Override
    public List<PropertyDetails> getAllProperties() {
        return propertyDetailsDao.getAllProperties();
    }
}
