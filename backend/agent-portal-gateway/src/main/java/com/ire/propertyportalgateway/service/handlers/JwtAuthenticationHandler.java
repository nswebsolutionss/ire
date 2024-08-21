package com.ire.propertyportalgateway.service.handlers;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.propertyportalgateway.service.ResponseHelper;
import com.ire.propertyportalgateway.service.alerts.Alerts;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;

public class JwtAuthenticationHandler implements Handler<RoutingContext> {

    public static final String ACCESS_TOKEN = "access_token";

    private final JWTAuth authProvider;
    private final Alerts alerts;

    public JwtAuthenticationHandler(final JWTAuth authProvider, final Alerts alerts) {
        this.authProvider = authProvider;
        this.alerts = alerts;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        String token = tokenIsValid(routingContext);

        if (token.isEmpty()) {
            ResponseHelper.unauthorised(routingContext, new Response("", "Unauthenticated"));
        } else {
            authProvider.authenticate(new TokenCredentials(token))
                    .onFailure(
                            result ->
                            {
                                alerts.raiseAlert(result.getMessage());
                                ResponseHelper.unauthorised(routingContext, new Response("", "Unauthenticated"));

                            })
                    .onSuccess(res -> routingContext.next());
        }
    }

    private String tokenIsValid(final RoutingContext routingContext) {
        try {
            Cookie token = routingContext.request().getCookie(ACCESS_TOKEN);
            if (token == null) {
                return "";
            }
            return token.getValue();
        } catch (Exception e) {
            alerts.raiseAlert("Exception: ", e);
            return "";
        }
    }
}
