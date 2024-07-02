package com.ire.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class VertxWebApp extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger();
    private final WebAppConfig config;
    private BiConsumer<Router, JWTAuth> authRouteConsumer;
    private Consumer<Router> routeConsumer;
    private final JWTAuth authProvider;

    public VertxWebApp(final WebAppConfig config) {
        this.config = config;
        this.authProvider = JWTAuth.create(
                vertx,
                new JWTAuthOptions().setJWTOptions(
                        new JWTOptions().setExpiresInSeconds(config.tokenExpirySeconds())
                )
        );
    }

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);

        if (authRouteConsumer == null && routeConsumer == null) {
            throw new RuntimeException("Expected at least one route consumer");
        } else if (authRouteConsumer != null) {

            authRouteConsumer.accept(router, authProvider);
        } else {
            routeConsumer.accept(router);
        }

        startHttpsServer(startPromise, router);
    }

    public void withRoutes(final BiConsumer<Router, JWTAuth> routes) {
        this.authRouteConsumer = routes;
    }

    public void withRoutesNoAuth(final Consumer<Router> routes) {
        this.routeConsumer = routes;
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
