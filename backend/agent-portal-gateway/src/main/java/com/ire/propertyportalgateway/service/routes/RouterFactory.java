package com.ire.propertyportalgateway.service.routes;

import com.ire.propertyportalgateway.service.HttpPublisher;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.httpproxy.HttpProxy;

@FunctionalInterface
public interface RouterFactory {
    void accept(Router router, HttpProxy httpProxy, HttpPublisher client, JWTAuth authProvider);
}