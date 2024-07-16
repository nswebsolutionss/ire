package com.ire.propertyportalgateway.service.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AuthorizationCallbackHandler implements Handler<RoutingContext> {
    private final WebClient httpClient;
    private final Map<String, OAuthWorkflow> authWorkflowByStateId;
    private static final Logger LOGGER = LogManager.getLogger();

    public AuthorizationCallbackHandler(WebClient httpClient, Map<String, OAuthWorkflow> authWorkflowByStateId) {
        this.httpClient = httpClient;
        this.authWorkflowByStateId = authWorkflowByStateId;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String state = routingContext.request().getParam("state");
        OAuthWorkflow oAuthWorkflow = authWorkflowByStateId.get(state);
        oAuthWorkflow.onLoggedOn(routingContext, httpClient);


//        String code = routingContext.request().getParam("code");
//        String state = routingContext.request().getParam("state");
//        LOGGER.info(routingContext.request().streamId());
//        if (!state.equals("oiwjefoiewojnef8238ewf")) {
//            routingContext.response().setStatusCode(400).setStatusMessage("Bad Request").end();
//        }
//        else {
//            routingContext.vertx().executeBlocking(() -> httpClient.requestAbs(HttpMethod.POST,
//                            "https://0939966.propelauthtest.com/propelauth/oauth/token"
//                    )
//                    .putHeader("Content-Type", "application/x-www-urlenoded")
//                    .putHeader("Authorization", "Basic " + Base64Util.encode("531752b5a27b659e3592aeca6866d5a8:531752b5a27b659e3592aeca6866d5a8095dd66b6f7fb132ea389b293c1600bbf386cea338c231ebb5afb9be052845f3"))
//                    .sendForm(MultiMap.caseInsensitiveMultiMap()
//                            .add("client_id", "531752b5a27b659e3592aeca6866d5a8")
//                            .add("code", code)
//                            .add("redirect_uri", "http://localhost:8084/callback")
//                            .add("grant_type", "authorization_code")
//                    )
//                    .onComplete(
//                            c_res -> {
//                                HttpServerResponse httpServerResponse = routingContext.response()
//                                        .addCookie(Cookie.cookie("access_token", c_res.result().bodyAsJsonObject().getString("access_token"))
//                                                .setHttpOnly(true)
//                                                .setSecure(true)
//                                                .setSameSite(CookieSameSite.STRICT)
//                                        )
//                                        .addCookie(Cookie.cookie("refresh_token", c_res.result().bodyAsJsonObject().getString("refresh_token"))
//                                                .setHttpOnly(true)
//                                                .setSecure(true)
//                                                .setSameSite(CookieSameSite.STRICT)
//                                        )
//                                        .addCookie(Cookie.cookie("id_token", c_res.result().bodyAsJsonObject().getString("id_token"))
//                                                .setHttpOnly(true)
//                                                .setSecure(true)
//                                                .setSameSite(CookieSameSite.STRICT)
//                                        );
//                                httpServerResponse.headers().add("Location", "http://localhost:5174");
//                                httpServerResponse.setStatusCode(302);
//                                httpServerResponse.end();
//                            }
//                    ));
//        }

    }
}