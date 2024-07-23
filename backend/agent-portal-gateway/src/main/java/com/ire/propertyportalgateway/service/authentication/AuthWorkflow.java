package com.ire.propertyportalgateway.service.authentication;

import io.vertx.core.shareddata.Shareable;
import io.vertx.ext.web.RoutingContext;

public interface AuthWorkflow extends Shareable {
    long id();

    void initiateWorkflow(RoutingContext routingContext);

    void onLoggedOn(String state, RoutingContext routingContext);
}
