package com.ire.organizationplatform.service.handlers.propertydetails;

import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.generated.organizationplatform.protocol.request.Request;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetPropertyDetailsHandler implements ProcessingHandler<Request> {

    private static final Logger LOGGER = LogManager.getLogger(GetPropertyDetailsHandler.class);
    private final OrganizationServiceInteraction serviceInteraction;

    public GetPropertyDetailsHandler(OrganizationServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final Request request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.getPropertyDetails(request.getId()), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new Response(request.getId(), "Unable to get Property Details"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new PropertyDetails(
                                    res.result().getId(),
                                    res.result().getAddress(),
                                    res.result().getDateAdded(),
                                    res.result().getLastUpdated(),
                                    res.result().getPropertyType(),
                                    res.result().getBeds(),
                                    res.result().getBathrooms(),
                                    res.result().getPrice(),
                                    res.result().getImages(),
                                    res.result().getOrganizationInformationId()

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
