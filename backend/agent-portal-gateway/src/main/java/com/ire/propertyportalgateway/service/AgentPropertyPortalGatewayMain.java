package com.ire.propertyportalgateway.service;

import com.ire.propertyportalgateway.service.handlers.AuthorizationCallbackHandler;
import com.ire.propertyportalgateway.service.handlers.LoginHandler;
import com.ire.propertyportalgateway.service.handlers.OAuthWorkflow;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.proxy.handler.ProxyHandler;

import java.util.*;

public class AgentPropertyPortalGatewayMain {

    public static VertxWebApp newVertxWebApp(final WebAppConfig config) {
        Map<String, OAuthWorkflow> authWorkflowByStateId = new HashMap<>();

        final VertxWebApp vertxWebApp = new VertxWebApp(config);
        routes(vertxWebApp, authWorkflowByStateId);
        return vertxWebApp;
    }

    public static void main(String[] args) {


        VertxWebApp vertxWebApp = AgentPropertyPortalGatewayMain.newVertxWebApp(new WebAppConfig(8084, "0.0.0.0"));
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(32));
        vertx.deployVerticle(vertxWebApp);

    }

    private static void routes(VertxWebApp foo, Map<String, OAuthWorkflow> authWorkflowByStateId) {

        foo.withRoutes(
                (router, proxy, httpClient) -> {
                    router.route().handler(CorsHandler.create().addOrigins(List.of("http://localhost:3000", "https://108.156.46.42:443"))
                            .allowedMethods(Set.of(HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.GET, HttpMethod.POST))
                            .allowedHeader("Access-Control-Allow-Credentials")
                            .allowedHeader("Access-Control-Allow-Origin")
                            .allowedHeader("Access-Control-Allow-Method")
                    );
                    router.get("/login").handler(new LoginHandler(httpClient, proxy, authWorkflowByStateId,
                            (RoutingContext routingContext) ->
                                    new OAuthWorkflow(
                                            UUID.randomUUID().getMostSignificantBits()
                                    )
                    ));
                    router.get("/callback").handler(new AuthorizationCallbackHandler(httpClient, authWorkflowByStateId));
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
        OAuthWorkflow create(RoutingContext routingContext);
    }

}
