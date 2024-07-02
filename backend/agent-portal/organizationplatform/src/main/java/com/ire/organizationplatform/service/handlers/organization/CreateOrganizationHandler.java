package com.ire.organizationplatform.service.handlers.organization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.generated.organizationplatform.protocol.domain.Organization;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateOrganizationHandler implements ProcessingHandler<Organization> {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizationHandler.class);
    private final AccountServiceInteraction serviceInteraction;
    private final ObjectMapper mapper = new ObjectMapper();

    public CreateOrganizationHandler(AccountServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final Organization request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.createOrganization(request), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result().isEmpty()) {
                            ResponseHelper.conflict(routingContext, new Response(request.getId(), "Organization already exists"));
                        } else if (res.result() == null) {
                            ResponseHelper.badRequest(routingContext, new Response(request.getId(), "Unable to create Organization"));
                        } else {
                            ResponseHelper.ok(routingContext, new Response(res.result(), "Successfully created Organization"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
