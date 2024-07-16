package com.ire.propertyportalgateway.service.scenarios;

import com.ire.propertyportalgateway.service.fixtures.OrganizationInformationFixture;
import com.ire.propertyportalgateway.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


@Tag("IntegrationTest")
public class AuthenticationTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDslIgnoringIds();

    @Test
    public void shouldNotCreateOrganizationInformationIfNoJWTPresentOnRequest() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest().withoutAuth());
        dsl.webUser().then().httpUser().receives(fixture.unauthorisedResponse("Missing JWT token"));
    }

    @Test
    public void shouldCreateOrganizationInformationIfJWTIsPresentAndValid() {
        OrganizationInformationFixture fixture = new OrganizationInformationFixture();

        dsl.webUser().when().httpUser().sends(fixture.createOrganizationInformationRequest());
        dsl.webUser().then().httpUser().receives(fixture.successResponse("Successfully created Organization Information"));
    }

}