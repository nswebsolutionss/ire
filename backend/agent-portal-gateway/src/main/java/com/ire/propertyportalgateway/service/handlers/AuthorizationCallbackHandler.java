package com.ire.propertyportalgateway.service.handlers;

import com.ire.propertyportalgateway.service.authentication.AuthWorkflow;
import io.vertx.core.Handler;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationCallbackHandler implements Handler<RoutingContext> {
    private static final Logger LOGGER = LogManager.getLogger();

    public AuthorizationCallbackHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String state = routingContext.request().getParam("state");
        LocalMap<String, AuthWorkflow> authWorkflow = routingContext.vertx().sharedData().getLocalMap("authWorkflow");
        if (authWorkflow.get(state) == null) {
            routingContext.reroute("/login");
        } else {
            authWorkflow.remove(state).onLoggedOn(state, routingContext);
        }

    }
}