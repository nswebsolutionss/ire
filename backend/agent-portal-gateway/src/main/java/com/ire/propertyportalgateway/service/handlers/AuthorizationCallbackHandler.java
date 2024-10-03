package com.ire.propertyportalgateway.service.handlers;

import com.ire.propertyportalgateway.service.alerts.Alerts;
import com.ire.propertyportalgateway.service.authentication.AuthWorkflow;
import io.vertx.core.Handler;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;

import static com.ire.propertyportalgateway.service.shared.Constants.AUTH_WORKFLOW_VERTX_KEY;

public class AuthorizationCallbackHandler implements Handler<RoutingContext> {

    private final Alerts alerts;

    public AuthorizationCallbackHandler(final Alerts alerts) {
        this.alerts = alerts;
    }

    @Override
    public void handle(final RoutingContext routingContext) {
        final String state = routingContext.request().getParam("state");
        final LocalMap<String, AuthWorkflow> authWorkflow = routingContext.vertx().sharedData().getLocalMap(AUTH_WORKFLOW_VERTX_KEY);
        try {
            if (authWorkflow.get(state) == null) {
                alerts.raiseAlert("OAuth Workflow broken - state does not match, be aware of man in the middle attack");
                routingContext.response().setStatusCode(400).setStatusMessage("Unauthorized").end();
            } else {
                authWorkflow.remove(state).onLoggedOn(state, routingContext);
            }
        } catch (final Exception exception) {
            alerts.raiseAlert("Caught exception on callback request: ", exception);
            routingContext.response().setStatusCode(400).setStatusMessage("Unauthorized").end();

        }


    }
}