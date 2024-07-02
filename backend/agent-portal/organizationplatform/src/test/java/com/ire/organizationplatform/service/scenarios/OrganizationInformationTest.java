package com.ire.organizationplatform.service.scenarios;

import com.ire.organizationplatform.service.fixtures.OrganizationFixture;
import com.ire.organizationplatform.service.fixtures.OrganizationInformationFixture;
import com.ire.organizationplatform.service.support.IntegrationDsl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.ire.organizationplatform.service.scenarios.sequences.Sequences.givenOrganizationCreated;
import static com.ire.organizationplatform.service.scenarios.sequences.Sequences.givenOrganizationInformationCreated;

@Tag("IntegrationTest")
public class OrganizationInformationTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDslIgnoringIds();
    private final OrganizationFixture organizationFixture = new OrganizationFixture();

    @BeforeEach
    public void setup() {
        givenOrganizationCreated(organizationFixture, dsl);
    }

    @Test
    public void shouldGetOrganizationInformation() {
        OrganizationInformationFixture fixture = organizationFixture.organizationInformationFixture();
        givenOrganizationInformationCreated(fixture, dsl);
    }

    @Test
    public void shouldNotGetOrganizationInformationIfIdDoesntExist() {
        OrganizationInformationFixture fixture = organizationFixture.organizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.getOrganizationInformationRequest("1209137237023"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to get Organization Information"));
    }

    @Test
    public void shouldDeleteOrganizationInformation() {
        OrganizationInformationFixture fixture = organizationFixture.organizationInformationFixture();
        givenOrganizationInformationCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.deleteOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully deleted Organization Information"));
    }

    @Test
    public void shouldNotDeleteOrganizationInformationIfDoesntExist() {
        OrganizationInformationFixture fixture = organizationFixture.organizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.invalidDeleteOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to delete Organization Information"));
    }

    @Test
    public void shouldUpdateOrganizationInformation() {
        OrganizationInformationFixture fixture = organizationFixture.organizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Organization Information"));

        dsl.webUser().when().httpUser().sends(fixture.updateOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully updated Organization Information"));

        dsl.webUser().when().httpUser().sends(fixture.getOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.updatedGetOrganizationInformationResponse());
    }

    @Test
    public void shouldNotUpdateOrganizationInformationIfDoesntExist() {
        OrganizationInformationFixture fixture = organizationFixture.organizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.updateOrganizationInformationRequest("1128907137237"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to update Organization Information"));
    }

}