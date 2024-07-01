package com.ire.organizationplatform.service.scenarios;

import com.ire.organizationplatform.service.fixtures.OrganizationInformationFixture;
import com.ire.organizationplatform.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.ire.organizationplatform.service.scenarios.sequences.Sequences.givenOrganizationInformationCreated;

@Tag("IntegrationTest")
public class OrganizationInformationTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDsl();

    @Test
    public void shouldNotCreateOrganizationInformationIfAlreadyExists() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Organization Information"));

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.conflictResponse("Organization Information already exists"));
    }

    @Test
    public void shouldGetOrganizationInformation() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();
        givenOrganizationInformationCreated(fixture, dsl);
    }

    @Test
    public void shouldNotGetOrganizationInformationIfIdDoesntExist() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.getOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to get Organization Information"));
    }

    @Test
    public void shouldDeleteOrganizationInformation() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();
        givenOrganizationInformationCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.deleteOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully deleted Organization Information"));
    }

    @Test
    public void shouldNotDeleteOrganizationInformationIfDoesntExist() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.deleteOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to delete Organization Information"));
    }

    @Test
    public void shouldUpdateOrganizationInformation() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Organization Information"));

        dsl.webUser().when().httpUser().sends(fixture.updateOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully updated Organization Information"));

        dsl.webUser().when().httpUser().sends(fixture.getOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.updatedGetOrganizationInformationResponse());
    }

    @Test
    public void shouldNotUpdateOrganizationInformationIfDoesntExist() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.updateOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to update Organization Information"));
    }

}