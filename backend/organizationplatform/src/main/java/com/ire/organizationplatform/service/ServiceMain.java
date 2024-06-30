package com.ire.organizationplatform.service;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.generated.organizationplatform.protocol.request.Request;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import com.ire.organizationplatform.service.handlers.JsonDecodingHandler;
import com.ire.organizationplatform.service.handlers.UrlParamsDecodingHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.CreateOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.DeleteOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.GetOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.organizationinformation.UpdateOrganizationInformationHandler;
import com.ire.organizationplatform.service.handlers.propertydetails.CreatePropertyDetailsHandler;
import com.ire.organizationplatform.service.handlers.propertydetails.DeletePropertyDetailsHandler;
import com.ire.organizationplatform.service.handlers.propertydetails.GetPropertyDetailsHandler;
import com.ire.organizationplatform.service.handlers.propertydetails.UpdatePropertyDetailsHandler;
import com.ire.organizationplatform.service.interaction.OrganizationInformationInteractionImpl;
import com.ire.webapp.VertxWebApp;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.List;
import java.util.Set;

public class ServiceMain {

    public static VertxWebApp newVertxWebApp(final WebAppConfig config) {
        final VertxWebApp vertxWebApp = new VertxWebApp(config);
        final OrganizationServiceInteraction serviceInteraction = new OrganizationInformationInteractionImpl();

        routes(vertxWebApp, serviceInteraction);
        return vertxWebApp;
    }

    public static void main(String[] args) {
        VertxWebApp vertxWebApp = ServiceMain.newVertxWebApp(new WebAppConfig(8082, "0.0.0.0"));
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(32));
        vertx.deployVerticle(vertxWebApp);
    }

    private static void routes(VertxWebApp foo, OrganizationServiceInteraction serviceInteraction) {
        foo.withRoutes(
                router -> {
                    router.route().handler(CorsHandler.create().addOrigins(List.of("http://localhost:3000", "http://localhost:8082"))
                            .allowedMethods(Set.of(HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.GET, HttpMethod.POST))
                            .allowedHeader("Access-Control-Allow-Credentials")
                            .allowedHeader("Access-Control-Allow-Origin")
                            .allowedHeader("Access-Control-Allow-Method")
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
                    router.delete("/api/properties/:id").handler(
                            new UrlParamsDecodingHandler<>(Request.class, new DeletePropertyDetailsHandler(serviceInteraction))
                    );
                }
        );
    }

}
