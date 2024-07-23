package com.ire.propertyportalgateway.service;

import com.ire.propertyportalgateway.service.authentication.AuthWorkflow;
import com.ire.propertyportalgateway.service.authentication.OAuthWorkflow;
import com.ire.propertyportalgateway.service.handlers.AuthorizationCallbackHandler;
import com.ire.propertyportalgateway.service.handlers.JwtAuthenticationHandler;
import com.ire.propertyportalgateway.service.handlers.LoginHandler;
import com.ire.propertyportalgateway.service.handlers.LogoutHandler;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.proxy.handler.ProxyHandler;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AgentPropertyPortalGatewayMain {

    public static VertxWebApp newVertxWebApp(final WebAppConfig config) {
        final VertxWebApp vertxWebApp = new VertxWebApp(config);
        routes(vertxWebApp, config);
        return vertxWebApp;
    }

    public static void main(String[] args) {


        VertxWebApp vertxWebApp = AgentPropertyPortalGatewayMain.newVertxWebApp(new WebAppConfig(8084, "0.0.0.0", WebAppConfig.PUBLIC_KEY, "RS256"));
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(32));
        vertx.deployVerticle(vertxWebApp);

    }

    private static void routes(final VertxWebApp foo, final WebAppConfig webAppConfig) {

        foo.withRoutes(
                (router, proxy, httpClientPublisher, authProvider) -> {
                    router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT));
                    router.route().handler(CorsHandler.create().addOrigins(List.of("http://localhost:5174", "https://0939966.propelauthtest.com", "http://localhost:8084"))
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
                                            webAppConfig,
                                            httpClientPublisher
                                    )
                    ));
                    router.get("/logout").handler(new LogoutHandler(httpClientPublisher));
                    router.get("/callback").handler(new AuthorizationCallbackHandler());
                    router.route("/api/*").handler(
                            new JwtAuthenticationHandler(authProvider, httpClientPublisher)
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
    }

    @FunctionalInterface
    public interface AuthorizationWorkflowFactory {
        AuthWorkflow create();
    }

}
