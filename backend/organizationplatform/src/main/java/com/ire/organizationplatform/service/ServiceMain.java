package com.ire.organizationplatform.service;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ServiceMain {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(32));
        vertx.deployVerticle(new OrganizationPlatformMain());
    }
}
