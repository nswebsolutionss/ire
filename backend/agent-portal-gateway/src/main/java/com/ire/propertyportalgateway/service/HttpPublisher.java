package com.ire.propertyportalgateway.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;

public interface HttpPublisher {

    void publish(HttpRequestMessage request, Handler<AsyncResult<HttpResponse<Buffer>>> requestHandler);
}
