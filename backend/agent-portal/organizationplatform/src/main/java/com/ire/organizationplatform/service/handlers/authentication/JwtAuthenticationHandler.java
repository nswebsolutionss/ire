package com.ire.organizationplatform.service.handlers.authentication;

import com.ire.organizationplatform.service.ResponseHelper;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class JwtAuthenticationHandler implements Handler<RoutingContext> {

    private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationHandler.class);
    private final JWTAuth authProvider;

    public JwtAuthenticationHandler(JWTAuth authProvider) {

        this.authProvider = authProvider;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        Optional<String> token = tokenIsValid(routingContext);
        if (token.isEmpty()) {
            ResponseHelper.badRequest(routingContext, "Missing/Malformed JWT token");
        } else {
            Future<User> authenticator = authProvider.authenticate(new TokenCredentials(token.get()));
            authenticator
                    .onFailure(
                            result ->
                            {
                                LOGGER.error(result.getMessage());
                                ResponseHelper.badRequest(routingContext, result.getMessage());
                            }
                    )
                    .onSuccess(
                            event1 ->
                            {
                                routingContext.request().headers().add("userId", event1.subject());
                                routingContext.next();
                            }
                    );

        }
    }

    private static Optional<String> tokenIsValid(final RoutingContext routingContext) {
        try {
            Cookie token = routingContext.request().getCookie("Authorization");
            routingContext.request().cookies().forEach(cookie -> LOGGER.info(cookie.getValue() + " " + cookie.isSecure() + " " + cookie.getName()));
            return Optional.of(token.getValue());
        } catch (Exception e) {
            LOGGER.error(e);
            return Optional.empty();
        }

    }
}
