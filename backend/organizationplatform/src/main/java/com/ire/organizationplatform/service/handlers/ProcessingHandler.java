package com.ire.organizationplatform.service.handlers;

import io.vertx.ext.web.RoutingContext;

public interface ProcessingHandler<T> {

    void handle(RoutingContext event, T request);

}
