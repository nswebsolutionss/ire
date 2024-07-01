package com.ire.organizationplatform.service.handlers.organizationinformation;

import com.generated.organizationplatform.protocol.request.Request;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteOrganizationInformationHandler implements ProcessingHandler<Request> {

    private static final Logger LOGGER = LogManager.getLogger(DeleteOrganizationInformationHandler.class);
    private final AccountServiceInteraction serviceInteraction;

    public DeleteOrganizationInformationHandler(AccountServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final Request request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.deleteOrganizationInformation(request.getId()), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new Response(request.getId(), "Unable to delete Organization Information"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new Response(res.result(), "Successfully deleted Organization Information"));
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
