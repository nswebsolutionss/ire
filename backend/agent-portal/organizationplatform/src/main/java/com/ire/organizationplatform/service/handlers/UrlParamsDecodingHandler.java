package com.ire.organizationplatform.service.handlers;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.ResponseHelper;
import com.organizationplatform.protocol.request.RequestInterface;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class UrlParamsDecodingHandler<T extends RequestInterface> implements Handler<RoutingContext> {

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
            }
            handler.handle(routingContext, request);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            LOGGER.error("Failed to decode url params: ", e);
            ResponseHelper.badRequest(routingContext, new Response("", "Failed to process request"));
        }
    }
}
