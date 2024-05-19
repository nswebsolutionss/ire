package com.ire.organizationplatform.service.scenarios;

import com.ire.organizationplatform.service.fixtures.NackFixture;
import com.ire.organizationplatform.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@Tag("IntegrationTest")
public class NackTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDsl();

    @Test
    public void shouldSendErrorWhenSendingMalformedJson() {
        NackFixture fixture = new NackFixture();

        dsl.webUser().when().httpUser().sends(fixture.malformedJsonRequest());
        dsl.webUser().then().httpUser().receives(fixture.malformedJsonResponse("Failed to decode request"));
    }

    @Test
    public void shouldSendErrorWhenSendingMalformedPathParameter() {
        NackFixture fixture = new NackFixture();

        dsl.webUser().when().httpUser().sends(fixture.malformedPathParamRequest("ije932"));
        dsl.webUser().then().httpUser().receives(fixture.malformedJsonResponse("Unable to decode ID into UUID: ije932"));
    }


}
