package com.ire.propertyportalgateway.service;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseHelper {

    private static final Logger LOGGER = LogManager.getLogger(ResponseHelper.class);

    public static void ok(RoutingContext routingContext, Object body) {
        try {
            Buffer buffer = new BufferImpl();
            String encodedBody = Json.encode(body);
            buffer.appendString(encodedBody);
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-type", "application/json");
            response.putHeader("Content-length", String.valueOf(buffer.length()));
            response.setStatusCode(200);
            response.write(buffer);
            response.end();
        } catch (Exception e) {
            LOGGER.error("Failed to send http response: ", e);
        }
    }


    //    public static void resourceExists(RoutingContext routingContext, String message)
//    {
//        Buffer buffer = new BufferImpl();
//        String encodedBody = Json.encode(new ErrorResponse().withMessage(message));
//        LOGGER.error(encodedBody);
//        buffer.appendString(encodedBody);
//        HttpServerResponse response = routingContext.response();
//        response.putHeader("Content_type", "application/json");
//        response.putHeader("Content-length", String.valueOf(buffer.length()));
//        response.setStatusCode(409);
//        response.write(buffer);
//        response.end();
//    }
//
    public static void badRequest(RoutingContext routingContext, Object body) {
        Buffer buffer = new BufferImpl();
        String encodedBody = Json.encode(body);
        LOGGER.error(encodedBody);
        buffer.appendString(encodedBody);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-type", "application/json");
        response.putHeader("Content-length", String.valueOf(buffer.length()));
        response.setStatusCode(400);
        response.write(buffer);
        response.end();
    }

    public static void unauthorised(RoutingContext routingContext, Object body) {
        Buffer buffer = new BufferImpl();
        String encodedBody = Json.encode(body);
        buffer.appendString(encodedBody);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-type", "application/json");
        response.putHeader("Content-length", String.valueOf(buffer.length()));
        response.setStatusCode(401);
        response.write(buffer);
        response.end();
    }

    public static void redirect(RoutingContext routingContext, Object body, String location) {
        Buffer buffer = new BufferImpl();
        String encodedBody = Json.encode(body);
        LOGGER.error(encodedBody);
        buffer.appendString(encodedBody);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-type", "application/json");
        response.putHeader("Content-length", String.valueOf(buffer.length()));
        response.putHeader("Location", location);
        response.setStatusCode(302);
        response.write(buffer);
        response.end();
    }

    public static void resourceNotFound(RoutingContext routingContext, Object body) {
        Buffer buffer = new BufferImpl();
        String encodedBody = Json.encode(body);
        LOGGER.error(encodedBody);
        buffer.appendString(encodedBody);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-type", "application/json");
        response.putHeader("Content-length", String.valueOf(buffer.length()));
        response.setStatusCode(404);
        response.write(buffer);
        response.end();
    }

    public static void conflict(RoutingContext routingContext, Object body) {
        Buffer buffer = new BufferImpl();
        String encodedBody = Json.encode(body);
        LOGGER.error(encodedBody);
        buffer.appendString(encodedBody);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-type", "application/json");
        response.putHeader("Content-length", String.valueOf(buffer.length()));
        response.setStatusCode(409);
        response.write(buffer);
        response.end();
    }
}
