package com.ire.organizationplatform.service.handlers.organizationinformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ire.backend.database.OrganizationInformation;
import com.ire.organizationplatform.service.response.ResponseHelper;
import com.ire.organizationplatform.service.contract.OrganizationServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import com.ire.organizationplatform.service.response.IdResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateOrganizationInformationHandler implements ProcessingHandler<OrganizationInformation> {

    private static final Logger LOGGER = LogManager.getLogger(UpdateOrganizationInformationHandler.class);
    private final OrganizationServiceInteraction serviceInteraction;
    private final ObjectMapper mapper = new ObjectMapper();

    public UpdateOrganizationInformationHandler(OrganizationServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final OrganizationInformation request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.updateOrganizationInformation(request), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new IdResponse(request.id(), "Unable to update Organization Information"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new IdResponse(res.result(), "Successfully updated Organization Information"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
