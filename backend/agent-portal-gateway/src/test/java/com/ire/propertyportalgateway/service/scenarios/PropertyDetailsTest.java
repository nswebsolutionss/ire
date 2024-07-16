package com.ire.propertyportalgateway.service.scenarios;

import com.ire.propertyportalgateway.service.fixtures.PropertyDetailsFixture;
import com.ire.propertyportalgateway.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.ire.propertyportalgateway.service.scenarios.sequences.Sequences.givenPropertyDetailsCreated;


@Tag("IntegrationTest")
public class PropertyDetailsTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDslIgnoringIds();

    @Test
    public void shouldGetPropertyDetails() {
        PropertyDetailsFixture fixture = new PropertyDetailsFixture();
        givenPropertyDetailsCreated(fixture, dsl);
    }

    @Test
    public void shouldNotGetPropertyDetailsIfIdDoesntExist() {
        PropertyDetailsFixture fixture = new PropertyDetailsFixture();

        dsl.webUser().when().httpUser().sends(fixture.getPropertyDetailsRequest("0"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to get Property Details"));
    }

    @Test
    public void shouldDeleteOrganizationInformation() {
        PropertyDetailsFixture fixture = new PropertyDetailsFixture();
        givenPropertyDetailsCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.deletePropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully deleted Property Details"));
    }

    @Test
    public void shouldNotDeleteOrganizationInformationIfDoesntExist() {
        PropertyDetailsFixture fixture = new PropertyDetailsFixture();

        dsl.webUser().when().httpUser().sends(fixture.deletePropertyDetailsRequest("0"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to delete Property Details"));
    }

    @Test
    public void shouldUpdateOrganizationInformation() {
        PropertyDetailsFixture fixture = new PropertyDetailsFixture();

        givenPropertyDetailsCreated(fixture, dsl);

        dsl.webUser().when().httpUser().sends(fixture.updatePropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully updated Property Details"));

        dsl.webUser().when().httpUser().sends(fixture.getPropertyDetailsRequest());
        dsl.webUser().then().httpUser().receives(fixture.updatedGetPropertyDetailsResponse());
    }

    @Test
    public void shouldNotUpdateOrganizationInformationIfDoesntExist() {
        PropertyDetailsFixture fixture = new PropertyDetailsFixture();

        dsl.webUser().when().httpUser().sends(fixture.updatePropertyDetailsRequest("0"));
        dsl.webUser().then().httpUser().receives(fixture.notFoundResponse("Unable to update Property Details"));
    }

}