package com.ire.propertyportalgateway.service.support;

import com.ire.organizationplatform.service.AccountServiceMain;
import com.ire.propertyportalgateway.service.AgentPropertyPortalGatewayMain;
import com.ire.webapp.VertxWebApp;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class IntegrationDsl implements BeforeEachCallback, AfterEachCallback {
    private final Vertx vertx = Vertx.vertx();
    private final DslWrapper<WebUserDsl> webUserDslWrapper;
    private final List<String> ignoredResolvers = new ArrayList<>();

    public IntegrationDsl(final List<String> ignoredResolvers) {
        this.ignoredResolvers.addAll(ignoredResolvers);
        this.webUserDslWrapper = new DslWrapper<>(new WebUserDsl(ignoredResolvers));
    }

    public static IntegrationDsl newDsl() {
        return newDsl(List.of());
    }

    public static IntegrationDsl newDslIgnoringIds() {
        return newDsl(List.of("id"));
    }

    public static IntegrationDsl newDsl(List<String> ignoredResolvers) {
        return new IntegrationDsl(ignoredResolvers);
    }

    public DslWrapper<WebUserDsl> webUser() {
        return this.webUserDslWrapper;
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        VertxWebApp accountService = AccountServiceMain.newVertxWebApp(new WebAppConfig(8082, "0.0.0.0"), null);
        vertx.deployVerticle(accountService, new DeploymentOptions().setWorkerPoolSize(1));

        com.ire.propertyportalgateway.service.VertxWebApp propertyPortalGateway = AgentPropertyPortalGatewayMain.newVertxWebApp(new WebAppConfig(8084, "0.0.0.0"));
        vertx.deployVerticle(propertyPortalGateway, new DeploymentOptions().setWorkerPoolSize(1));
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
}
