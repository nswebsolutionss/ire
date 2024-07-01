package com.ire.organizationplatform.service.scenarios;

import com.ire.organizationplatform.service.fixtures.OrganizationFixture;
import com.ire.organizationplatform.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.ire.organizationplatform.service.scenarios.sequences.Sequences.givenOrganizationCreated;

@Tag("IntegrationTest")
public class OrganizationTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDsl();

    @Test
    public void shouldNotCreateOrganizationIfAlreadyExists() {
        OrganizationFixture fixture = new OrganizationFixture();

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Organization"));

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationRequest());
        dsl.webUser().then().httpUser().receives(fixture.conflictResponse("Organization already exists"));
    }

    @Test
    public void shouldGetOrganization() {
        OrganizationFixture fixture = new OrganizationFixture();
        givenOrganizationCreated(fixture, dsl);
    }

    @Test
    public void shouldNotGetOrganizationIfIdDoesntExist() {
        OrganizationFixture fixture = new OrganizationFixture();

        dsl.webUser().when().httpUser().sends(fixture.getOrganizationRequest());
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to get Organization"));
    }

    @Test
    public void shouldDeleteOrganization() {
        OrganizationFixture fixture = new OrganizationFixture();
        givenOrganizationCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.deleteOrganizationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully deleted Organization"));
    }

    @Test
    public void shouldNotDeleteOrganizationIfDoesntExist() {
        OrganizationFixture fixture = new OrganizationFixture();

        dsl.webUser().when().httpUser().sends(fixture.deleteOrganizationRequest());
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to delete Organization"));
    }

}