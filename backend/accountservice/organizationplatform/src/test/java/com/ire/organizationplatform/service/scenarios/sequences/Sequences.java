package com.ire.organizationplatform.service.scenarios.sequences;

import com.ire.organizationplatform.service.fixtures.OrganizationInformationFixture;
import com.ire.organizationplatform.service.fixtures.PropertyDetailsFixture;
import com.ire.organizationplatform.service.support.IntegrationDsl;

public class Sequences {

    public static void givenOrganizationInformationCreated(OrganizationInformationFixture fixture, IntegrationDsl dsl) {
        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Organization Information"));

        dsl.webUser().when().httpUser().sends(fixture.getOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.getOrganizationInformationResponse());
    }

    public static void givenPropertyDetailsCreated(PropertyDetailsFixture fixture, IntegrationDsl dsl) {
        dsl.webUser().when().httpUser().sends(fixture.createPropertyDetails());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Property Details"));

        dsl.webUser().when().httpUser().sends(fixture.getPropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.getPropertyDetailsResponse());
    }

}
