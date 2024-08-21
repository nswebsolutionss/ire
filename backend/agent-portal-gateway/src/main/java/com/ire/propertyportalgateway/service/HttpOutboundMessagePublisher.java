package com.ire.propertyportalgateway.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class HttpOutboundMessagePublisher implements HttpPublisher {
    private final WebClient httpClient;

    public HttpOutboundMessagePublisher(final WebClient httpClient) {

        this.httpClient = httpClient;
    }

    @Override
    public void publish(HttpRequestMessage request, Handler<AsyncResult<HttpResponse<Buffer>>> requestHandler) {
        HttpRequest<Buffer> requestToSend = httpClient.requestAbs(request.method(), request.url());
        request.headers().forEach(requestToSend::putHeader);

        if (!request.formBody().isEmpty()) {
            requestToSend.sendForm(request.formBody(), requestHandler);
        } else if (request.jsonBody() != null) {
            requestToSend.sendJsonObject(request.jsonBody(), requestHandler);
        } else {
            requestToSend.send(requestHandler);
        }
    }
}
