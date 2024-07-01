package com.ire.organizationplatform.service.handlers.organizationinformation;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.request.Request;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.ire.organizationplatform.service.contract.AccountServiceInteraction;
import com.ire.organizationplatform.service.handlers.ProcessingHandler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetOrganizationInformationHandler implements ProcessingHandler<Request> {

    private static final Logger LOGGER = LogManager.getLogger(GetOrganizationInformationHandler.class);
    private final AccountServiceInteraction serviceInteraction;

    public GetOrganizationInformationHandler(AccountServiceInteraction serviceInteraction) {
        this.serviceInteraction = serviceInteraction;
    }

    @Override
    public void handle(
            final RoutingContext routingContext,
            final Request request
    ) {
        routingContext.vertx().executeBlocking(() ->
                serviceInteraction.getOrganizationInformation(request.getId()), false
        ).onComplete(res -> {
                    if (res.succeeded()) {
                        if (res.result() == null) {
                            ResponseHelper.resourceNotFound(routingContext, new Response(request.getId(), "Unable to get Organization Information"));
                        }
                        else {
                            ResponseHelper.ok(routingContext, new OrganizationInformation(
                                    res.result().getId(),
                                    res.result().getCompanyName(),
                                    res.result().getCompanyDescription(),
                                    res.result().getTelephoneNumber(),
                                    res.result().getWebsiteUrl(),
                                    res.result().getFacebookUrl(),
                                    res.result().getInstagramUrl(),
                                    res.result().getYoutubeUrl(),
                                    res.result().getMemberSince(),
                                    res.result().getLastUpdated()

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
