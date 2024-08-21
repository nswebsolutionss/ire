package com.ire.propertyportalgateway.service.scenarios;

import com.ire.propertyportalgateway.service.fixtures.LogonFixture;
import com.ire.propertyportalgateway.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


@Tag("IntegrationTest")
public class LogonTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDslIgnoringIds();

    @Test
    public void shouldSendRedirectHeaderOnLogon() {
        LogonFixture fixture = new LogonFixture();

        dsl.webUser().when().httpUser().sends(fixture.logonRequest());
        dsl.webUser().then().httpUser().receives(fixture.logonRedirectResponse());
        dsl.alerts().then().expectNoAlerts();

    }

    @Test
    public void shouldRerouteToLoginEndpointIfAuthWorkflowNotCreated() {
        LogonFixture fixture = new LogonFixture();

        dsl.webUser().when().httpUser().sends(fixture.logonCallbackRequest());
        dsl.webUser().then().httpUser().receives(fixture.logonRedirectResponse());
        dsl.alerts().then().expectAlertContaining("OAuth Workflow broken");

    }

    @Test
    public void shouldHandleCallback() {
        LogonFixture fixture = new LogonFixture();

        dsl.webUser().when().httpUser().sends(fixture.logonRequest());
        dsl.webUser().then().httpUser().receives(fixture.logonRedirectResponse());

        dsl.webUser().when().httpUser().sends(fixture.logonCallbackRequestWithCorrectState());
        dsl.webUser().then().httpUser().receives(fixture.logonCallbackResponse());
        dsl.alerts().then().expectNoAlerts();

    }
}
