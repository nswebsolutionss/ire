package com.ire.propertyportalgateway.service.support;

import com.ire.propertyportalgateway.service.AgentPropertyPortalGatewayMain;
import com.ire.propertyportalgateway.service.VertxWebApp;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class IntegrationDsl implements BeforeEachCallback, AfterEachCallback {
    private final Vertx vertx = Vertx.vertx();
    private final DslWrapper<WebUserDsl> webUserDslWrapper;
    private final List<String> ignoredResolvers = new ArrayList<>();
    private final DslWrapper<StubAlerts> alertsDsl;
    private final StubAlerts alerts;

    public IntegrationDsl(final List<String> ignoredResolvers) {
        this.ignoredResolvers.addAll(ignoredResolvers);
        this.webUserDslWrapper = new DslWrapper<>(new WebUserDsl(ignoredResolvers));
        this.alerts = new StubAlerts();
        this.alertsDsl = new DslWrapper<>(alerts);
    }

    public static IntegrationDsl newDsl() {
        return newDsl(List.of());
    }

    public static IntegrationDsl newDslIgnoringIds() {
        return newDsl(List.of("id", "Location"));
    }

    public static IntegrationDsl newDsl(List<String> ignoredResolvers) {
        return new IntegrationDsl(ignoredResolvers);
    }

    public DslWrapper<WebUserDsl> webUser() {
        return this.webUserDslWrapper;
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws IOException, SQLException {
        VertxWebApp vertxWebApp = AgentPropertyPortalGatewayMain.newVertxWebApp(
                new WebAppConfig(8084, "0.0.0.0", WebAppConfig.TEST_KEY, "HS256"),
                (__) -> new HttpPublisherStub(), alerts
        );
        vertx.deployVerticle(vertxWebApp, new DeploymentOptions().setWorkerPoolSize(1));
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        final Consumer<Handler<AsyncResult<Void>>> foo = vertx::close;

        final CompletableFuture<Void> actionFuture = new CompletableFuture<>();
        foo.accept(result -> {
            if (result.failed()) {
                actionFuture.completeExceptionally(result.cause());
            } else {
                actionFuture.complete(null);
            }
        });
    }

    public DslWrapper<StubAlerts> alerts() {
        return this.alertsDsl;
    }

}
