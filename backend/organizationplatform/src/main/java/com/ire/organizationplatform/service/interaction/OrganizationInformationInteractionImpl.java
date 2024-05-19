package com.ire.organizationplatform.service.interaction;

import com.ire.backend.database.OrganizationInformation;
import com.ire.backend.database.OrganizationInformationDaoImpl;
import com.ire.backend.database.dao.RestDao;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import io.vertx.core.Future;

public class OrganizationInformationInteractionImpl implements OrganizationServiceInteraction {

    private  RestDao<OrganizationInformation> organizationInformationDao;

    public OrganizationInformationInteractionImpl() {

        this.organizationInformationDao = new OrganizationInformationDaoImpl();
    }

    /**
     * TODO: Log error in dao and return null if error has occurred
     */

    @Override
    public String createOrganizationInformation(final OrganizationInformation organizationInformation) {
        if(organizationInformationDao.get(organizationInformation.id()) == null) {
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
}
