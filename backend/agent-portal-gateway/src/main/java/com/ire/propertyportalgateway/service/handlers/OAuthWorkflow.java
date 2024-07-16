package com.ire.propertyportalgateway.service.handlers;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.propertyportalgateway.service.ResponseHelper;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Base64Util;

public class OAuthWorkflow {
    private static final Logger LOGGER = LogManager.getLogger();
    private final long id;

    public OAuthWorkflow(long id) {
        this.id = id;
    }

    public long id() {
        return this.id;
    }

    public void initiateWorkflow(RoutingContext routingContext) {
        routingContext.redirect("https://0939966.propelauthtest.com/propelauth/oauth/authorize?" +
                "redirect_uri=http://localhost:8084/callback" +
                "&client_id=531752b5a27b659e3592aeca6866d5a8" +
                "&response_type=code" +
                "&state=" + id
        ).onFailure(res -> {
            LogManager.getLogger().error("Failed to logon: " + res);
            routingContext.end();
        }).onSuccess(res -> {
            LOGGER.info("client initiated logon");
        });

    }

    public void onLoggedOn(RoutingContext routingContext, WebClient httpClient) {
        String code = routingContext.request().getParam("code");

        httpClient.requestAbs(HttpMethod.POST,
                        "https://0939966.propelauthtest.com/propelauth/oauth/token"
                )
                .putHeader("Content-Type", "application/x-www-urlenoded")
                .putHeader("Authorization", "Basic " + Base64Util.encode("531752b5a27b659e3592aeca6866d5a8:531752b5a27b659e3592aeca6866d5a8095dd66b6f7fb132ea389b293c1600bbf386cea338c231ebb5afb9be052845f3"))
                .sendForm(MultiMap.caseInsensitiveMultiMap()
                        .add("client_id", "531752b5a27b659e3592aeca6866d5a8")
                        .add("code", code)
                        .add("redirect_uri", "http://localhost:8084/callback")
                        .add("grant_type", "authorization_code")
                ).onSuccess(res -> {
                    LOGGER.info("client successfully logged in");
                    onToken(res, routingContext);
                })
                .onFailure(res -> {
                    LogManager.getLogger().error(res);
                    ResponseHelper.unauthorised(routingContext, new Response("", "Logon flow failed"));
                });
    }

    private void onToken(HttpResponse<Buffer> res, RoutingContext routingContext) {
        LOGGER.info("Issuing tokens");

        HttpServerResponse httpServerResponse = routingContext.response()
                .addCookie(Cookie.cookie("access_token", res.bodyAsJsonObject().getString("access_token"))
                        .setHttpOnly(true)
                        .setSecure(true)
                        .setSameSite(CookieSameSite.STRICT)
                )
                .addCookie(Cookie.cookie("refresh_token", res.bodyAsJsonObject().getString("refresh_token"))
                        .setHttpOnly(true)
                        .setSecure(true)
                        .setSameSite(CookieSameSite.STRICT)
                )
                .addCookie(Cookie.cookie("id_token", res.bodyAsJsonObject().getString("id_token"))
                        .setHttpOnly(true)
                        .setSecure(true)
                        .setSameSite(CookieSameSite.STRICT)
                );
        httpServerResponse.headers().add("Location", "http://localhost:5174");
        httpServerResponse.setStatusCode(302);
        httpServerResponse.end();
    }
}
