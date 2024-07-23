package com.ire.propertyportalgateway.service;

import com.ire.webapp.WebAppConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.httpproxy.HttpProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertxWebApp extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger();
    private final WebAppConfig config;
    private RouteConsumer routerConsumer;
    private final JWTAuth authProvider;

    public VertxWebApp(final WebAppConfig config) {
        this.config = config;
        this.authProvider = JWTAuth.create(
                vertx,
                new JWTAuthOptions().addPubSecKey(
                        new PubSecKeyOptions().setBuffer(config.privateKey()).setAlgorithm(config.privateKeyAlg())
                )
        );
    }

    @Override
    public void start(Promise<Void> startPromise) {
        HttpOutboundMessagePublisher httpOutboundMessagePublisher = new HttpOutboundMessagePublisher(WebClient.create(vertx, new WebClientOptions().setLogActivity(true)));
        Router proxyRouter = Router.router(vertx);

        if (routerConsumer == null) {
            throw new RuntimeException("Expected router consumer to not be null");
        }
        startHttpsServer(startPromise, proxyRouter);

        HttpClient proxyClient = vertx.createHttpClient();
        HttpProxy httpProxy = HttpProxy.reverseProxy(proxyClient);
        httpProxy.origin(443, "http://localhost:8082");
        routerConsumer.accept(proxyRouter, httpProxy, httpOutboundMessagePublisher, authProvider);

    }

    public void withRoutes(final RouteConsumer routes) {
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

    @FunctionalInterface
    public interface RouteConsumer {
        void accept(Router router, HttpProxy httpProxy, HttpOutboundMessagePublisher client, JWTAuth authProvider);
    }
}
