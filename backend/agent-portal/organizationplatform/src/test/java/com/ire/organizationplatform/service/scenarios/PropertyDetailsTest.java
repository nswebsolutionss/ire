package com.ire.organizationplatform.service.scenarios;

import com.ire.organizationplatform.service.fixtures.OrganizationFixture;
import com.ire.organizationplatform.service.fixtures.PropertyDetailsFixture;
import com.ire.organizationplatform.service.support.IntegrationDsl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.ire.organizationplatform.service.scenarios.sequences.Sequences.givenOrganizationCreated;
import static com.ire.organizationplatform.service.scenarios.sequences.Sequences.givenPropertyDetailsCreated;

@Tag("IntegrationTest")
public class PropertyDetailsTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDslIgnoringIds();
    private final OrganizationFixture organizationFixture = new OrganizationFixture();

    @BeforeEach
    public void setup() {
        givenOrganizationCreated(organizationFixture, dsl);
    }

    @Test
    public void shouldGetPropertyDetails() {
        PropertyDetailsFixture fixture = organizationFixture.propertyDetailsFixture();
        givenPropertyDetailsCreated(fixture, dsl);
    }

    @Test
    public void shouldNotGetPropertyDetailsIfIdDoesntExist() {
        PropertyDetailsFixture fixture = organizationFixture.propertyDetailsFixture();

        dsl.webUser().when().httpUser().sends(fixture.getPropertyDetailsRequest("0"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to get Property Details"));
    }

    @Test
    public void shouldDeletePropertyDetails() {
        PropertyDetailsFixture fixture = organizationFixture.propertyDetailsFixture();
        givenPropertyDetailsCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.deletePropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully deleted Property Details"));
    }

    @Test
    public void shouldNotDeletePropertyDetailsIfDoesntExist() {
        PropertyDetailsFixture fixture = organizationFixture.propertyDetailsFixture();

        dsl.webUser().when().httpUser().sends(fixture.deletePropertyDetailsRequest("0"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to delete Property Details"));
    }

    @Test
    public void shouldUpdatePropertyDetails() {
        PropertyDetailsFixture fixture = organizationFixture.propertyDetailsFixture();

        givenPropertyDetailsCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.updatePropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully updated Property Details"));

        dsl.webUser().when().httpUser().sends(fixture.getPropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.updatedGetPropertyDetailsResponse());
    }

    @Test
    public void shouldNotUpdatePropertyDetailsIfDoesntExist() {
        PropertyDetailsFixture fixture = organizationFixture.propertyDetailsFixture();

        dsl.webUser().when().httpUser().sends(fixture.updatePropertyDetailsRequest("0"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to update Property Details"));
    }

}