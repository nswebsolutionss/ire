package com.ire.propertyportalgateway.service.handlers;

import com.ire.propertyportalgateway.service.AgentPropertyPortalGatewayMain;
import com.ire.propertyportalgateway.service.authentication.AuthWorkflow;
import io.vertx.core.Handler;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginHandler implements Handler<RoutingContext> {
    private final AgentPropertyPortalGatewayMain.AuthorizationWorkflowFactory authorizationWorkflowFactory;
    private static final Logger LOGGER = LogManager.getLogger();

    public LoginHandler(
            final AgentPropertyPortalGatewayMain.AuthorizationWorkflowFactory authorizationWorkflowFactory
    ) {
        this.authorizationWorkflowFactory = authorizationWorkflowFactory;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        AuthWorkflow oAuthWorkflow = authorizationWorkflowFactory.create();

        LocalMap<String, AuthWorkflow> authWorkflow = routingContext.vertx().sharedData().getLocalMap("authWorkflow");
        authWorkflow.put(String.valueOf(oAuthWorkflow.id()), oAuthWorkflow);
        oAuthWorkflow.initiateWorkflow(routingContext);

    }
}