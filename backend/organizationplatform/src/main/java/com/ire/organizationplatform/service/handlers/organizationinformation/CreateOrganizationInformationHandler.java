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

public class CreateOrganizationInformationHandler implements ProcessingHandler<OrganizationInformation> {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizationInformationHandler.class);
    private final OrganizationServiceInteraction serviceInteraction;
    private final ObjectMapper mapper = new ObjectMapper();

    public CreateOrganizationInformationHandler(OrganizationServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final OrganizationInformation request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.createOrganizationInformation(request), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if(res.result().isEmpty()) {
                            ResponseHelper.conflict(routingContext, new IdResponse(request.id(), "Organization Information already exists"));
                        }
                        else if (res.result() == null) {
                            ResponseHelper.badRequest(routingContext, new IdResponse(request.id(), "Unable to create Organization Information"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new IdResponse(res.result(), "Successfully created Organization Information"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
