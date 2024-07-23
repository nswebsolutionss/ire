package com.ire.propertyportalgateway.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpOutboundMessagePublisher {
    private static final Logger LOGGER = LogManager.getLogger();
    private final WebClient httpClient;

    public HttpOutboundMessagePublisher(WebClient httpClient) {

        this.httpClient = httpClient;
    }

    public void publish(HttpRequestMessage request, Handler<AsyncResult<HttpResponse<Buffer>>> requestHandler) {
        HttpRequest<Buffer> requestToSend = httpClient.requestAbs(request.method(), request.url());
        request.headers().forEach(requestToSend::putHeader);
        if (!request.formBody().isEmpty()) {
            requestToSend.sendForm(request.formBody(), requestHandler);
        } else if (request.jsonBody() != null) {
            LOGGER.info(request);
            requestToSend.sendJsonObject(request.jsonBody(), requestHandler);

        } else {
            LOGGER.info("Sending: " + request);
            requestToSend.send(requestHandler);
        }
    }
}
