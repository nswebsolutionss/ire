package com.ire.propertyportalgateway.service.authentication;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.propertyportalgateway.service.HttpPublisher;
import com.ire.propertyportalgateway.service.HttpRequestMessage;
import com.ire.propertyportalgateway.service.ResponseHelper;
import com.ire.propertyportalgateway.service.UserInformation;
import com.ire.propertyportalgateway.service.alerts.Alerts;
import com.ire.webapp.WebAppConfig;
import io.vertx.core.AsyncResult;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.Shareable;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Base64Util;

public class OAuthWorkflow implements Shareable, AuthWorkflow {

    private static final Logger LOGGER = LogManager.getLogger();

    private final HttpPublisher httpOutboundMessagePublisher;
    private final Alerts alerts;
    private final WebAppConfig webAppConfig;
    private final long id;

    public OAuthWorkflow(final long id, WebAppConfig webAppConfig, HttpPublisher httpOutboundMessagePublisher, Alerts alerts) {
        this.id = id;
        this.webAppConfig = webAppConfig;
        this.httpOutboundMessagePublisher = httpOutboundMessagePublisher;
        this.alerts = alerts;
    }

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public void initiateWorkflow(final RoutingContext routingContext) {
        routingContext.response()
                .putHeader("Access-Control-Expose-Headers", "*")
                .putHeader("Location", formatOAuthRequestHeader(webAppConfig, id))
                .setStatusCode(200).end()
                .onFailure(res -> {
                    alerts.raiseAlert("Failed to logon: ", res);
                    routingContext.response().setStatusCode(500).setStatusMessage("Internal Server Error").end();
                })
                .onSuccess(res -> {
                });

    }


    @Override
    public void onLoggedOn(String state, final RoutingContext routingContext) {
        String code = routingContext.request().getParam("code");
        if (!state.equals(String.valueOf(id))) {
            alerts.raiseAlert("Received state does not much originator state: Received: " + code + " Actual: " + id);
            routingContext.response().setStatusMessage("Bad request").setStatusCode(400).end();
        } else {
            HttpRequestMessage authorizationEndpointResponse = createAuthorizationEndpointResponse(code, webAppConfig);
            httpOutboundMessagePublisher.publish(authorizationEndpointResponse, (res) -> onToken(res, routingContext));

        }

    }

    private void onToken(final AsyncResult<HttpResponse<Buffer>> res, final RoutingContext routingContext) {
        if (res.succeeded()) {
            if (res.result().statusCode() != 200) {
                alerts.raiseAlert("Unable to exchange code for token: {}", res.cause());
                routingContext.response().setStatusMessage(res.result().statusMessage()).setStatusCode(res.result().statusCode()).end();
            } else {
                UserInformation user = UserInformation.parseToken(res.result().bodyAsString(), alerts);
                if (user == null) {
                    routingContext.response().setStatusMessage("Unable to get user information from token").setStatusCode(500).end();
                } else {
                    httpOutboundMessagePublisher.publish(
                            new HttpRequestMessage()
                                    .withMethod(HttpMethod.POST)
                                    .withHeader("Content-Type", "application/json")
                                    .withUrl(webAppConfig.accountServiceUri() + "/organization")
                                    .withJsonBody(new JsonObject().put("id", user.getOrganizationId())),
                            (foo) -> {
                                HttpServerResponse httpServerResponse = addAuthCookies(res, routingContext);
                                httpServerResponse.headers().add("Location", "http://localhost:5173/dashboard");
                                httpServerResponse.setStatusCode(302);
                                httpServerResponse.end();
                            });
                }
            }
        } else {
            alerts.raiseAlert("Exception: ", res.cause());
            ResponseHelper.unauthorised(routingContext, new Response("", "Logon flow failed"));
        }

    }

    private static HttpServerResponse addAuthCookies(AsyncResult<HttpResponse<Buffer>> res, RoutingContext routingContext) {
        return routingContext.response()
                .addCookie(Cookie.cookie("access_token", res.result().bodyAsJsonObject().getString("access_token"))
                        .setHttpOnly(true)
                        .setSecure(true)
                        .setSameSite(CookieSameSite.STRICT)
                )
                .addCookie(Cookie.cookie("refresh_token", res.result().bodyAsJsonObject().getString("refresh_token"))
                        .setHttpOnly(true)
                        .setSecure(true)
                        .setSameSite(CookieSameSite.STRICT)
                )
                .addCookie(Cookie.cookie("id_token", res.result().bodyAsJsonObject().getString("id_token"))
                        .setHttpOnly(true)
                        .setSecure(true)
                        .setSameSite(CookieSameSite.STRICT)
                );
    }

    private static HttpRequestMessage createAuthorizationEndpointResponse(String code, WebAppConfig webAppConfig) {
        return new HttpRequestMessage()
                .withMethod(HttpMethod.POST)
                .withUrl(webAppConfig.getPropelAuthTokenEndpoint())
                .withHeader("Content-Type", "application/x-www-urlenoded")
                .withHeader("Authorization", "Basic " + Base64Util.encode(webAppConfig.getPropelAuthClientId() + ":" + webAppConfig.getPropelAuthClientSecret()))
                .withFormBody(
                        MultiMap.caseInsensitiveMultiMap()
                                .add("client_id", webAppConfig.getPropelAuthClientId())
                                .add("code", code)
                                .add("redirect_uri", webAppConfig.getPropelAuthRedirectUrl())
                                .add("grant_type", "authorization_code")
                );
    }

    private static String formatOAuthRequestHeader(final WebAppConfig webAppConfig, final Long id) {
        return webAppConfig.getPropelAuthAuthorizationEndpoint() + "?" +
                "redirect_uri=" + webAppConfig.getPropelAuthRedirectUrl() +
                "&client_id=" + webAppConfig.getPropelAuthClientId() +
                "&response_type=code" +
                "&state=" + id;
    }

    @Override
    public Shareable copy() {
        return new OAuthWorkflow(this.id, this.webAppConfig, httpOutboundMessagePublisher, alerts);
    }

}
