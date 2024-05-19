package com.ire.organizationplatform.service.handlers.organizationinformation;

import com.ire.backend.database.OrganizationInformation;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import com.ire.organizationplatform.service.response.ResponseHelper;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import com.ire.organizationplatform.service.request.IdRequest;
import com.ire.organizationplatform.service.response.IdResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetOrganizationInformationHandler implements ProcessingHandler<IdRequest> {

    private static final Logger LOGGER = LogManager.getLogger(GetOrganizationInformationHandler.class);
    private final OrganizationServiceInteraction serviceInteraction;

    public GetOrganizationInformationHandler(OrganizationServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final IdRequest request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.getOrganizationInformation(request.id()), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new IdResponse(request.id(), "Unable to get Organization Information"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new OrganizationInformation(
                                    res.result().id(),
                                    res.result().companyName(),
                                    res.result().companyDescription(),
                                    res.result().telephoneNumber(),
                                    res.result().websiteUrl(),
                                    res.result().facebookUrl(),
                                    res.result().instagramUrl(),
                                    res.result().youtubeUrl(),
                                    res.result().memberSince(),
                                    res.result().lastUpdated()

                            ));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
