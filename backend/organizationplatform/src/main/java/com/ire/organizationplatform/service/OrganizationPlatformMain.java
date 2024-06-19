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
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class OrganizationPlatformMain extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void start(Promise<Void> startPromise) {

        Router router = Router.router(vertx);
        OrganizationServiceInteraction serviceInteraction = new OrganizationInformationInteractionImpl();
        routes(router, serviceInteraction);
        startHttpsServer(startPromise, router);
    }

    private void routes(Router router, OrganizationServiceInteraction serviceInteraction) {
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

    private void startHttpsServer(Promise<Void> startPromise, Router router) {
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router);
        httpServer.exceptionHandler(LOGGER::info);

        httpServer.listen(8082, "0.0.0.0", res -> {
            if (res.failed()) {

                LOGGER.error("Failed to start server on port 8082: ", res.cause());
                startPromise.fail(new RuntimeException("Failed to bind to port 8082: {}", res.cause()));
            } else {
                LOGGER.info("Server started listening on port 8082 and host 0.0.0.0");
                startPromise.complete();
            }
        });
    }

}
