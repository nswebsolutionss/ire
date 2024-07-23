package com.ire.propertyportalgateway.service.handlers;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.propertyportalgateway.service.HttpOutboundMessagePublisher;
import com.ire.propertyportalgateway.service.ResponseHelper;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JwtAuthenticationHandler implements Handler<RoutingContext> {

    private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationHandler.class);

    private final JWTAuth authProvider;
    private final HttpOutboundMessagePublisher httpOutboundMessagePublisher;

    public JwtAuthenticationHandler(JWTAuth authProvider, HttpOutboundMessagePublisher httpOutboundMessagePublisher) {
        this.authProvider = authProvider;
        this.httpOutboundMessagePublisher = httpOutboundMessagePublisher;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String token = tokenIsValid(routingContext);
        if (token.isEmpty()) {
            LOGGER.warn("Missing access token: " + formatHeaderLog(routingContext));
            ResponseHelper.unauthorised(routingContext, new Response("", "Unauthenticated"));
        } else {
            Future<User> authenticator = authProvider.authenticate(new TokenCredentials(token));
            authenticator
                    .onFailure(
                            result ->
                            {
                                LOGGER.warn(result.fillInStackTrace());
                                ResponseHelper.unauthorised(routingContext, new Response("", "Unauthenticated"));
                            }
                    )
                    .onSuccess(res -> {
                        LOGGER.info(res.expired());
                        routingContext.next();
                    });
        }
    }

    private String formatHeaderLog(final RoutingContext routingContext) {
        StringBuilder headers = new StringBuilder();
        headers.append("Request Headers: \n");
        routingContext.request().headers().forEach(header -> headers.append(header.getKey() + " : " + header.getValue() + "\n"));
        return headers.toString();
    }


    private static String tokenIsValid(final RoutingContext routingContext) {
        try {
            Cookie token = routingContext.request().getCookie("access_token");
            if (token == null) {
                return "";
            }
            return token.getValue();
        } catch (Exception e) {
            LOGGER.error(e);
            return "";
        }
    }
}
