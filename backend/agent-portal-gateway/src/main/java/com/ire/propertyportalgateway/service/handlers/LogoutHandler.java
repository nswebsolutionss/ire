package com.ire.propertyportalgateway.service.handlers;

import com.ire.propertyportalgateway.service.HttpOutboundMessagePublisher;
import com.ire.propertyportalgateway.service.HttpRequestMessage;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutHandler implements Handler<RoutingContext> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HttpOutboundMessagePublisher httpOutboundMessagePublisher;

    public LogoutHandler(
            final HttpOutboundMessagePublisher httpOutboundMessagePublisher
    ) {
        this.httpOutboundMessagePublisher = httpOutboundMessagePublisher;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        Cookie refreshToken = routingContext.request().getCookie("refresh_token");
        JsonObject refreshTokenEncoded = JsonObject.of("refresh_token", refreshToken.getValue());
        if (refreshToken != null) {
            LOGGER.info(refreshTokenEncoded);
            httpOutboundMessagePublisher.publish(
                    new HttpRequestMessage()
                            .withMethod(HttpMethod.POST)
                            .withUrl("https://0939966.propelauthtest.com/api/backend/v1/logout")
                            .withHeader("Content-Type", "application/json")
                            .withJsonBody(refreshTokenEncoded),
                    (res) -> {
                        routingContext.response().putHeader("Access-Control-Expose-Headers", "*");
                        routingContext.response().putHeader("Access-Control-Allow-Credentials", "true");
                        routingContext.response().putHeader("Access-Control-Allow-Headers", "*");
                        routingContext.response().setStatusCode(200);
                        routingContext.response().removeCookie("access_token");
                        routingContext.response().removeCookie("refresh_token");
                        routingContext.response().removeCookie("id_token");
                        routingContext.response().putHeader("Location", "http://localhost:5174");
                        routingContext.response().end();
                    }
            );
        }

    }
}