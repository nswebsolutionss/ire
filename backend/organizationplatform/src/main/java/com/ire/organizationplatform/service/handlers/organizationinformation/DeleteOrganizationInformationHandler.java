package com.ire.organizationplatform.service.handlers.organizationinformation;

import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import com.ire.organizationplatform.service.response.ResponseHelper;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import com.ire.organizationplatform.service.request.IdRequest;
import com.ire.organizationplatform.service.response.IdResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteOrganizationInformationHandler implements ProcessingHandler<IdRequest> {

    private static final Logger LOGGER = LogManager.getLogger(DeleteOrganizationInformationHandler.class);
    private final OrganizationServiceInteraction serviceInteraction;

    public DeleteOrganizationInformationHandler(OrganizationServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final IdRequest request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.deleteOrganizationInformation(request.id()), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new IdResponse(request.id(), "Unable to delete Organization Information"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new IdResponse(res.result(), "Successfully deleted Organization Information"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
