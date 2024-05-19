package com.ire.organizationplatform.service.handlers;

import com.ire.organizationplatform.service.response.IdResponse;
import com.ire.organizationplatform.service.response.ResponseHelper;
import com.ire.organizationplatform.service.request.Request;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class UrlParamsDecodingHandler<T extends Request> implements Handler<RoutingContext> {

    private static final Logger LOGGER = LogManager.getLogger(UrlParamsDecodingHandler.class);

    private final Class<T> requestObject;
    private final ProcessingHandler<T> handler;

    public UrlParamsDecodingHandler(final Class<T> requestObject, ProcessingHandler<T> handler) {
        this.requestObject = requestObject;
        this.handler = handler;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        try {
            T request = requestObject.getDeclaredConstructor().newInstance();
            if (routingContext.pathParam("id") != null) {
                String id = routingContext.pathParam("id");
                request.setId(id);

                try {
                    UUID.fromString(id);
                } catch (Exception e) {
                    LOGGER.error("Unable to decode ID into UUID: " + e);
                    ResponseHelper.badRequest(routingContext, new IdResponse("", "Unable to decode ID into UUID: " + id));
                    return;
                }
            }
            handler.handle(routingContext, request);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            LOGGER.error("Failed to decode url params: " + e);
            ResponseHelper.badRequest(routingContext, new IdResponse("", "Failed to process request"));
        }
    }
}
