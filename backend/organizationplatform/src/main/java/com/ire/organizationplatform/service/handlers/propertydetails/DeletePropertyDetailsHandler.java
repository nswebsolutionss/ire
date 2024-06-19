package com.ire.organizationplatform.service.handlers.propertydetails;

import com.generated.organizationplatform.protocol.request.Request;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeletePropertyDetailsHandler implements ProcessingHandler<Request> {

    private static final Logger LOGGER = LogManager.getLogger(DeletePropertyDetailsHandler.class);
    private final OrganizationServiceInteraction serviceInteraction;

    public DeletePropertyDetailsHandler(OrganizationServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final Request request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.deletePropertyDetails(request.getId()), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new Response(request.getId(), "Unable to delete Property Details"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new Response(res.result(), "Successfully deleted Property Details"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
