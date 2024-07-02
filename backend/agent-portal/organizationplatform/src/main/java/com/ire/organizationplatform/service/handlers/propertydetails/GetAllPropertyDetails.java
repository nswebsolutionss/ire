package com.ire.organizationplatform.service.handlers.propertydetails;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllPropertyDetails implements Handler<RoutingContext> {

    private static final Logger LOGGER = LogManager.getLogger(GetAllPropertyDetails.class);
    private final AccountServiceInteraction serviceInteraction;

    public GetAllPropertyDetails(AccountServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext
    ) {
        routingContext.vertx().executeBlocking(serviceInteraction::getAllProperties, false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new Response("", "Unable to get all Property Details"));
                        } else {
                            ResponseHelper.ok(routingContext, res.result());
                        }
                    } else {
                        LOGGER.error("Unable to write to database: ", res.cause());
                        ResponseHelper.badRequest(routingContext, "Exception thrown writing to database");
                    }
                }
        );
    }
}
