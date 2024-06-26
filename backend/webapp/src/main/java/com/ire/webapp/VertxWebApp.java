package com.ire.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
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
                new JWTAuthOptions()
                        .setJWTOptions(
                                new JWTOptions())
                        .addPubSecKey(
                                new PubSecKeyOptions()
                                        .setAlgorithm("RS256")
                                        .setBuffer(
                                                "-----BEGIN PUBLIC KEY-----\n" +
                                                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxPSbCQY5mBKFDIn1kggv\n" +
                                                        "Wb4ChjrctqD4nFnJOJk4mpuZ/u3h2ZgeKJJkJv8+5oFO6vsEwF7/TqKXp0XDp6IH\n" +
                                                        "byaOSWdkl535rCYR5AxDSjwnuSXsSp54pvB+fEEFDPFF81GHixepIbqXCB+BnCTg\n" +
                                                        "N65BqwNn/1Vgqv6+H3nweNlbTv8e/scEgbg6ZYcsnBBB9kYLp69FSwNWpvPmd60e\n" +
                                                        "3DWyIo3WCUmKlQgjHL4PHLKYwwKgOHG/aNl4hN4/wqTixCAHe6KdLnehLn71x+Z0\n" +
                                                        "SyXbWooftefpJP1wMbwlCpH3ikBzVIfHKLWT9QIOVoRgchPU3WAsZv/ePgl5i8Co\n" +
                                                        "qwIDAQAB\n" +
                                                        "-----END PUBLIC KEY-----"))
                        .addPubSecKey(new PubSecKeyOptions()
                                .setAlgorithm("RS256")
                                .setBuffer(
                                        "-----BEGIN PRIVATE KEY-----\n" +
                                                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDE9JsJBjmYEoUM\n" +
                                                "ifWSCC9ZvgKGOty2oPicWck4mTiam5n+7eHZmB4okmQm/z7mgU7q+wTAXv9Oopen\n" +
                                                "RcOnogdvJo5JZ2SXnfmsJhHkDENKPCe5JexKnnim8H58QQUM8UXzUYeLF6khupcI\n" +
                                                "H4GcJOA3rkGrA2f/VWCq/r4fefB42VtO/x7+xwSBuDplhyycEEH2Rgunr0VLA1am\n" +
                                                "8+Z3rR7cNbIijdYJSYqVCCMcvg8cspjDAqA4cb9o2XiE3j/CpOLEIAd7op0ud6Eu\n" +
                                                "fvXH5nRLJdtaih+15+kk/XAxvCUKkfeKQHNUh8cotZP1Ag5WhGByE9TdYCxm/94+\n" +
                                                "CXmLwKirAgMBAAECggEAeQ+M+BgOcK35gAKQoklLqZLEhHNL1SnOhnQd3h84DrhU\n" +
                                                "CMF5UEFTUEbjLqE3rYGP25mdiw0ZSuFf7B5SrAhJH4YIcZAO4a7ll23zE0SCW+/r\n" +
                                                "zr9DpX4Q1TP/2yowC4uGHpBfixxpBmVljkWnai20cCU5Ef/O/cAh4hkhDcHrEKwb\n" +
                                                "m9nymKQt06YnvpCMKoHDdqzfB3eByoAKuGxo/sbi5LDpWalCabcg7w+WKIEU1PHb\n" +
                                                "Qi+RiDf3TzbQ6TYhAEH2rKM9JHbp02TO/r3QOoqHMITW6FKYvfiVFN+voS5zzAO3\n" +
                                                "c5X4I+ICNzm+mnt8wElV1B6nO2hFg2PE9uVnlgB2GQKBgQD8xkjNhERaT7f78gBl\n" +
                                                "ch15DRDH0m1rz84PKRznoPrSEY/HlWddlGkn0sTnbVYKXVTvNytKSmznRZ7fSTJB\n" +
                                                "2IhQV7+I0jeb7pyLllF5PdSQqKTk6oCeL8h8eDPN7awZ731zff1AGgJ3DJXlRTh/\n" +
                                                "O6zj9nI8llvGzP30274I2/+cdwKBgQDHd/twbiHZZTDexYewP0ufQDtZP1Nk54fj\n" +
                                                "EpkEuoTdEPymRoq7xo+Lqj5ewhAtVKQuz6aH4BeEtSCHhxy8OFLDBdoGCEd/WBpD\n" +
                                                "f+82sfmGk+FxLyYkLxHCxsZdOb93zkUXPCoCrvNRaUFO1qq5Dk8eftGCdC3iETHE\n" +
                                                "6h5avxHGbQKBgQCLHQVMNhL4MQ9slU8qhZc627n0fxbBUuhw54uE3s+rdQbQLKVq\n" +
                                                "lxcYV6MOStojciIgVRh6FmPBFEvPTxVdr7G1pdU/k5IPO07kc6H7O9AUnPvDEFwg\n" +
                                                "suN/vRelqbwhufAs85XBBY99vWtxdpsVSt5nx2YvegCgdIj/jUAU2B7hGQKBgEgV\n" +
                                                "sCRdaJYr35FiSTsEZMvUZp5GKFka4xzIp8vxq/pIHUXp0FEz3MRYbdnIwBfhssPH\n" +
                                                "/yKzdUxcOLlBtry+jgo0nyn26/+1Uyh5n3VgtBBSePJyW5JQAFcnhqBCMlOVk5pl\n" +
                                                "/7igiQYux486PNBLv4QByK0gV0SPejDzeqzIyB+xAoGAe5if7DAAKhH0r2M8vTkm\n" +
                                                "JvbCFjwuvhjuI+A8AuS8zw634BHne2a1Fkvc8c3d9VDbqsHCtv2tVkxkKXPjVvtB\n" +
                                                "DtzuwUbp6ebF+jOfPK0LDuJoTdTdiNjIcXJ7iTTI3cXUnUNWWphYnFogzPFq9CyL\n" +
                                                "0fPinYmDJpkwMYHqQaLGQyg=\n" +
                                                "-----END PRIVATE KEY-----")
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
