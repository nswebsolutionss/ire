package com.ire.propertyportalgateway.service;

import com.ire.propertyportalgateway.service.alerts.Alerts;
import com.ire.propertyportalgateway.service.alerts.AlertsImpl;
import com.ire.propertyportalgateway.service.authentication.OAuthWorkflow;
import com.ire.propertyportalgateway.service.handlers.AuthorizationCallbackHandler;
import com.ire.propertyportalgateway.service.handlers.JwtAuthenticationHandler;
import com.ire.propertyportalgateway.service.handlers.LoginHandler;
import com.ire.propertyportalgateway.service.handlers.LogoutHandler;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.proxy.handler.ProxyHandler;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class AgentPropertyPortalGatewayMain {

    public static void main(String[] args) {
        VertxWebApp vertxWebApp = AgentPropertyPortalGatewayMain.newVertxWebApp(
                new WebAppConfig(8084, "0.0.0.0", WebAppConfig.PUBLIC_KEY, "RS256"),
                HttpOutboundMessagePublisher::new,
                new AlertsImpl()
        );
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(32));
        vertx.deployVerticle(vertxWebApp);

    }

    public static VertxWebApp newVertxWebApp(
            final WebAppConfig config,
            final Function<WebClient, HttpPublisher> httpPublisherFactory,
            final Alerts alerts
    ) {
        final VertxWebApp vertxWebApp = new VertxWebApp(config, httpPublisherFactory, alerts);
        vertxWebApp.withRoutes(
                (router, proxy, httpClientPublisher, authProvider) -> {
                    router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT));
                    router.route().handler(CorsHandler.create().addOrigins(List.of("http://localhost:5173", "https://0939966.propelauthtest.com", "http://localhost:8084"))
                            .allowedMethods(Set.of(HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.GET, HttpMethod.POST))
                            .allowedHeader("Access-Control-Request-Method")
                            .allowedHeader("Access-Control-Allow-Credentials")
                            .allowedHeader("Access-Control-Allow-Origin")
                            .allowedHeader("Access-Control-Allow-Method")
                            .allowedHeader("Access-Control-Allow-Headers")
                    );
                    router.get("/login").handler(new LoginHandler(
                            () ->
                                    new OAuthWorkflow(
                                            UUID.randomUUID().getMostSignificantBits(),
                                            config,
                                            httpClientPublisher,
                                            alerts
                                    )
                    ));
                    router.get("/logout").handler(new LogoutHandler(httpClientPublisher));
                    router.get("/callback").handler(new AuthorizationCallbackHandler(alerts));
                    router.route("/api/*").handler(
                            new JwtAuthenticationHandler(authProvider, alerts)
                    );
                    router.get("/api/authenticated").handler(ctx -> {
                        ctx.response()
                                .putHeader("Access-Control-Expose-Headers", "*")
                                .putHeader("Access-Control-Allow-Credentials", "true")
                                .setStatusCode(200)
                                .setStatusMessage("Authenticated")
                                .end();
                    });

                    router.post("/api/organizationInformation").handler(ProxyHandler.create(proxy));
                    router.put("/api/organizationInformation").handler(ProxyHandler.create(proxy));
                    router.get("/api/organizationInformation/:id").handler(ProxyHandler.create(proxy));
                    router.delete("/api/organizationInformation/:id").handler(ProxyHandler.create(proxy));

                    router.post("/api/properties").handler(ProxyHandler.create(proxy));
                    router.put("/api/properties").handler(ProxyHandler.create(proxy));
                    router.get("/api/properties/:id").handler(ProxyHandler.create(proxy));
                    router.delete("/api/properties/:id").handler(ProxyHandler.create(proxy));
                }
        );
        return vertxWebApp;
    }
}
