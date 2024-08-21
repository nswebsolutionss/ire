package com.ire.propertyportalgateway.service.scenarios;

import com.ire.propertyportalgateway.service.fixtures.AuthenticationFixture;
import com.ire.propertyportalgateway.service.support.IntegrationDsl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


@Tag("IntegrationTest")
public class AuthenticationTest {

    @RegisterExtension
    private final IntegrationDsl dsl = IntegrationDsl.newDslIgnoringIds();

    @Test
    public void shouldSendUnauthenticatedIfAccessTokenIsEmpty() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedEmptyToken());
        dsl.webUser().then().httpUser().receives(fixture.unauthenticatedResponse("Unauthenticated"));

    }

    @Test
    public void shouldSendUnauthenticatedIfAccessTokenInvalid() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedMalformedToken());
        dsl.webUser().then().httpUser().receives(fixture.unauthenticatedResponse("Unauthenticated"));
    }

    @Test
    public void shouldSendUnauthenticatedIfAccessTokenNotSigned() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedNonSignedToken());
        dsl.webUser().then().httpUser().receives(fixture.unauthenticatedResponse("Unauthenticated"));
    }

    @Test
    public void shouldSendUnauthenticatedIfAccessTokenSignedWithWrongKey() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedTokenSignedWithWrongKey());
        dsl.webUser().then().httpUser().receives(fixture.unauthenticatedResponse("Unauthenticated"));
        dsl.alerts().and().expectAlertContaining("Signature verification failed");

    }

    @Test
    public void shouldSendUnauthenticatedIfAccessTokenHasBeenManipulated() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedTokenManipulated());
        dsl.webUser().then().httpUser().receives(fixture.unauthenticatedResponse("Unauthenticated"));
        dsl.alerts().and().expectAlertContaining("Signature verification failed");
    }

    @Test
    public void shouldSendUnauthenticatedIfAccessTokenHasExpiredAndNoRefreshTokenPresent() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedTokenExpired());
        dsl.webUser().then().httpUser().receives(fixture.unauthenticatedResponse("Unauthenticated"));
        dsl.alerts().and().expectAlertContaining("Invalid JWT token: token expired");

    }

    @Test
    public void shouldSend200IfAccessTokenIsValid() {
        AuthenticationFixture fixture = new AuthenticationFixture();

        dsl.webUser().when().httpUser().sends(fixture.checkAuthenticatedValidToken());
        dsl.webUser().then().httpUser().receives(fixture.authenticateResponse());
    }

}
