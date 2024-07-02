package com.ire.organizationplatform.service.support;

import com.ire.database.DatabaseWrapperConfig;
import com.ire.organizationplatform.service.AccountServiceMain;
import com.ire.webapp.VertxWebApp;
import com.ire.webapp.WebAppConfig;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.sql.DataSource;
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
    private EmbeddedPostgres embeddedPostgres;
    private DataSource dataSource;


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
    public void beforeEach(ExtensionContext extensionContext) throws IOException, SQLException {
        EmbeddedPostgres.Builder builder = EmbeddedPostgres.builder();
        embeddedPostgres = builder.start();
        dataSource = embeddedPostgres.getPostgresDatabase();

        dataSource.getConnection().prepareStatement(new String(new DatabaseWrapperConfig().setupScript.readAllBytes())).execute();

        VertxWebApp vertxWebApp = AccountServiceMain.newVertxWebApp(new WebAppConfig(8082, "0.0.0.0"), dataSource);
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
}
