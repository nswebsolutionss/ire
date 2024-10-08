package com.ire.organizationplatform.service;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.generated.organizationplatform.protocol.request.Request;
import com.ire.backend.database.DataSourceFactory;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;
import com.ire.organizationplatform.service.handlers.JsonDecodingHandler;
import com.ire.organizationplatform.service.handlers.UrlParamsDecodingHandler;
import com.ire.organizationplatform.service.handlers.authentication.JwtAuthenticationHandler;
import com.ire.organizationplatform.service.handlers.organization.CreateOrganizationHandler;
import com.ire.organizationplatform.service.handlers.organization.DeleteOrganizationHandler;
import com.ire.organizationplatform.service.handlers.organization.GetOrganizationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.CreateOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.DeleteOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.GetOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.UpdateOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.propertydetails.*;
import com.ire.organizationplatform.service.interaction.AccountInformationInteractionImpl;
import com.ire.webapp.VertxWebApp;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

public class AccountServiceMain {

    public static VertxWebApp newVertxWebApp(final WebAppConfig config, DataSource dataSource) {
        final VertxWebApp vertxWebApp = new VertxWebApp(config);
        final AccountServiceInteraction serviceInteraction = new AccountInformationInteractionImpl(dataSource);
        routes(vertxWebApp, serviceInteraction);
        return vertxWebApp;
    }

    public static void main(String[] args) {

        VertxWebApp vertxWebApp = AccountServiceMain.newVertxWebApp(new WebAppConfig(8082, "0.0.0.0", WebAppConfig.PUBLIC_KEY, "RS256"), DataSourceFactory.ownerDataSource());
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(32));
        vertx.deployVerticle(vertxWebApp);
    }

    private static void routes(VertxWebApp vertxWebApp, AccountServiceInteraction serviceInteraction) {
        vertxWebApp.withRoutes(
                (router, jwtAuth) -> {
                    router.route().handler(CorsHandler.create().addOrigins(List.of("http://localhost:8082"))
                            .allowedMethods(Set.of(HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.GET, HttpMethod.POST))
                            .allowedHeader("Access-Control-Allow-Credentials")
                            .allowedHeader("Access-Control-Allow-Origin")
                            .allowedHeader("Access-Control-Allow-Method")
                    );
                    router.post("/organization").handler(
                            new JsonDecodingHandler<>(Organization.class, new CreateOrganizationHandler(serviceInteraction))
                    );
                    router.route("/api/*").handler(
                            new JwtAuthenticationHandler(jwtAuth)
                    );
                    router.get("/api/organization/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new GetOrganizationHandler(serviceInteraction))
                    );
                    router.delete("/api/organization/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new DeleteOrganizationHandler(serviceInteraction))
                    );
                    router.post("/api/organizationInformation").handler(
                            new JsonDecodingHandler<>(OrganizationInformation.class, new CreateOrganizationInformationHandler(serviceInteraction))
                    );
                    router.put("/api/organizationInformation").handler(
                            new JsonDecodingHandler<>(OrganizationInformation.class, new UpdateOrganizationInformationHandler(serviceInteraction))
                    );
                    router.get("/api/organizationInformation/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new GetOrganizationInformationHandler(serviceInteraction))
                    );
                    router.delete("/api/organizationInformation/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new DeleteOrganizationInformationHandler(serviceInteraction))
                    );
                    router.post("/api/properties").handler(
                            new JsonDecodingHandler<>(PropertyDetails.class, new CreatePropertyDetailsHandler(serviceInteraction))
                    );
                    router.put("/api/properties").handler(
                            new JsonDecodingHandler<>(PropertyDetails.class, new UpdatePropertyDetailsHandler(serviceInteraction))
                    );
                    router.get("/api/properties/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new GetPropertyDetailsHandler(serviceInteraction))
                    );
                    router.get("/api/properties/organization/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new GetAllPropertyDetailsForOrganizationHandler(serviceInteraction))
                    );
                    router.get("/api/properties").handler(
                            new GetAllPropertyDetails(serviceInteraction)
                    );
                    router.delete("/api/properties/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new DeletePropertyDetailsHandler(serviceInteraction))
                    );
                }
        );
    }

}
