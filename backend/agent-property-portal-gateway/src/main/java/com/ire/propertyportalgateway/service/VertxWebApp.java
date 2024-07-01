package com.ire.propertyportalgateway.service;

import com.ire.propertyportalgateway.service.handlers.JwtAuthenticationHandler;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.httpproxy.HttpProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;

public class VertxWebApp extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger();
    private final WebAppConfig config;
    private BiConsumer<Router, HttpProxy> routerConsumer;
    private final JWTAuth authProvider = JWTAuth.create(
            vertx,
            new JWTAuthOptions()
                    .setJWTOptions(
                            new JWTOptions().setExpiresInSeconds(86400)
                    )
    );

    public VertxWebApp(final WebAppConfig config) {
        this.config = config;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        Router proxyRouter = Router.router(vertx);

        if (routerConsumer == null) {
            throw new RuntimeException("Expected router consumer to not be null");
        }

        proxyRouter.route("/api/*").handler(
                new JwtAuthenticationHandler(authProvider)
        );
        startHttpsServer(startPromise, proxyRouter);

        HttpClient proxyClient = vertx.createHttpClient();
        HttpProxy httpProxy = HttpProxy.reverseProxy(proxyClient);
        httpProxy.origin(8082, "0.0.0.0");
        routerConsumer.accept(proxyRouter, httpProxy);

    }

    public void withRoutes(final BiConsumer<Router, HttpProxy> routes) {
        this.routerConsumer = routes;
    }

    private void startHttpsServer(Promise<Void> startPromise, Router router) {
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router);
        httpServer.exceptionHandler(LOGGER::info);

        //8082
        httpServer.listen(config.port(), config.host(), res -> {
            if (res.failed()) {

                LOGGER.error("Failed to start server on port " + config.port() + ": ", res.cause());
                startPromise.fail(new RuntimeException("Failed to bind to port " + config.port() + ": {}", res.cause()));
            } else {
                LOGGER.info("Server started listening on port " + config.port() + " and host " + config.host());
                startPromise.complete();
            }
        });
    }


}
