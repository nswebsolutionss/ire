package com.ire.propertyportalgateway.service;

import com.ire.propertyportalgateway.service.alerts.Alerts;
import com.ire.propertyportalgateway.service.routes.RouterFactory;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
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

import java.util.function.Function;

public class VertxWebApp extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger();
    private final WebAppConfig config;
    private RouterFactory routerConsumer;
    private final JWTAuth authProvider;
    private final Alerts alerts;
    private final Function<WebClient, HttpPublisher> httpPublisherFactory;

    public VertxWebApp(
            final WebAppConfig config,
            final Function<WebClient, HttpPublisher> httpPublisherFactory,
            final Alerts alerts
    ) {
        this.alerts = alerts;
        this.config = config;
        this.httpPublisherFactory = httpPublisherFactory;
        this.authProvider = generateAuthProvider(config, vertx);
    }

    @Override
    public void start(Promise<Void> startPromise) {
        HttpPublisher httpOutboundMessagePublisher = httpPublisherFactory.apply(
                WebClient.create(
                        vertx,
                        new WebClientOptions().setLogActivity(true)
                )
        );
        Router proxyRouter = Router.router(vertx);

        if (routerConsumer == null) {
            throw new RuntimeException("Expected router consumer to not be null");
        }

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(proxyRouter);
        httpServer.exceptionHandler((res) -> alerts.raiseAlert("Exception: ", res));

        httpServer.listen(config.port(), config.host(), res -> {
            if (res.failed()) {

                alerts.raiseAlert("Failed to start server on port " + config.port() + ": ", res.cause());
                startPromise.fail(new RuntimeException("Failed to bind to port " + config.port() + ": {}", res.cause()));
            } else {
                LOGGER.info("Server started listening on port " + config.port() + " and host " + config.host());
                startPromise.complete();
            }
        });

        HttpClient proxyClient = vertx.createHttpClient();
        HttpProxy httpProxy = HttpProxy.reverseProxy(proxyClient);
        httpProxy.origin(443, "http://localhost:8082");

        routerConsumer.accept(
                proxyRouter,
                httpProxy,
                httpOutboundMessagePublisher,
                authProvider
        );

    }

    public void withRoutes(final RouterFactory routes) {
        this.routerConsumer = routes;
    }

    private static JWTAuth generateAuthProvider(WebAppConfig config, Vertx vertx) {
        return JWTAuth.create(
                vertx,
                new JWTAuthOptions().addPubSecKey(
                        new PubSecKeyOptions().setBuffer(config.privateKey()).setAlgorithm(config.privateKeyAlg())
                )
        );
    }
}
