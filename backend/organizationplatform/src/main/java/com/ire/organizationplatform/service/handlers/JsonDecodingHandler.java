package com.ire.organizationplatform.service.handlers;

import com.ire.organizationplatform.service.response.IdResponse;
import com.ire.organizationplatform.service.response.ResponseHelper;
import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonDecodingHandler<T> implements Handler<RoutingContext> {

    private static final Logger LOGGER = LogManager.getLogger(JsonDecodingHandler.class);
    private final Class<T> requestObject;
    private final ProcessingHandler<T> handler;

    public JsonDecodingHandler(final Class<T> requestObject, ProcessingHandler<T> handler) {
        this.requestObject = requestObject;
        this.handler = handler;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        routingContext.request().body(req -> {
            try {
                final T request = Json.decodeValue(req.result().toString(), requestObject);
                handler.handle(routingContext, request);

            } catch (Exception e) {
                LOGGER.error("Failed to decode request: " + e);
                ResponseHelper.badRequest(routingContext, new IdResponse("", "Failed to decode request"));
            }
        });
    }
}
