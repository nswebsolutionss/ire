package com.ire.organizationplatform.service.handlers.organizationinformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateOrganizationInformationHandler implements ProcessingHandler<OrganizationInformation> {

    private static final Logger LOGGER = LogManager.getLogger(UpdateOrganizationInformationHandler.class);
    private final AccountServiceInteraction serviceInteraction;
    private final ObjectMapper mapper = new ObjectMapper();

    public UpdateOrganizationInformationHandler(AccountServiceInteraction serviceInteraction) {
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
                            ResponseHelper.resourceNotFound(routingContext, new Response(request.getId(), "Unable to update Organization Information"));
                        } else {
                            ResponseHelper.ok(routingContext, new Response(res.result(), "Successfully updated Organization Information"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
