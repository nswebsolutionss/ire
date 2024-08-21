package com.ire.propertyportalgateway.service.handlers;

import com.ire.propertyportalgateway.service.authentication.AuthWorkflow;
import com.ire.propertyportalgateway.service.authentication.AuthWorkflowFactory;
import io.vertx.core.Handler;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;

import static com.ire.propertyportalgateway.service.shared.Constants.AUTH_WORKFLOW_VERTX_KEY;

public class LoginHandler implements Handler<RoutingContext> {
    private final AuthWorkflowFactory authorizationWorkflowFactory;

    public LoginHandler(
            final AuthWorkflowFactory authorizationWorkflowFactory
    ) {
        this.authorizationWorkflowFactory = authorizationWorkflowFactory;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        AuthWorkflow oAuthWorkflow = authorizationWorkflowFactory.create();

        LocalMap<String, AuthWorkflow> authWorkflow = routingContext.vertx().sharedData().getLocalMap(AUTH_WORKFLOW_VERTX_KEY);
        authWorkflow.put(String.valueOf(oAuthWorkflow.id()), oAuthWorkflow);
        LogManager.getLogger().error("GOT HERE");
        oAuthWorkflow.initiateWorkflow(routingContext);
    }
}