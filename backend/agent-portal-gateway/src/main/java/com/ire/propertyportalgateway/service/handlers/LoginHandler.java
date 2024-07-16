package com.ire.propertyportalgateway.service.handlers;

import com.ire.propertyportalgateway.service.AgentPropertyPortalGatewayMain;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.httpproxy.HttpProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class LoginHandler implements Handler<RoutingContext> {
    private final WebClient httpClient;
    private final HttpProxy proxy;
    private final Map<String, OAuthWorkflow> authWorkflowByStateId;
    private final AgentPropertyPortalGatewayMain.AuthorizationWorkflowFactory authorizationWorkflowFactory;
    private static final Logger LOGGER = LogManager.getLogger();

    public LoginHandler(
            WebClient httpClient,
            HttpProxy proxy,
            Map<String, OAuthWorkflow> authWorkflowByStateId,
            AgentPropertyPortalGatewayMain.AuthorizationWorkflowFactory authorizationWorkflowFactory
    ) {
        this.httpClient = httpClient;
        this.proxy = proxy;
        this.authWorkflowByStateId = authWorkflowByStateId;
        this.authorizationWorkflowFactory = authorizationWorkflowFactory;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        OAuthWorkflow oAuthWorkflow = authorizationWorkflowFactory.create(routingContext);
        authWorkflowByStateId.put(String.valueOf(oAuthWorkflow.id()), oAuthWorkflow);
        oAuthWorkflow.initiateWorkflow(routingContext);

//        routingContext.redirect("https://0939966.propelauthtest.com/propelauth/oauth/authorize?" +
//                        "redirect_uri=http://localhost:8084/callback" +
//                        "&client_id=531752b5a27b659e3592aeca6866d5a8" +
//                        "&response_type=code" +
//                        "&state=oiwjefoiewojnef8238ewf"
//        );
    }
}


// Authorize url
// https://0939966.propelauthtest.com/propelauth/oauth/authorize

//https://0939966.propelauthtest.com/propelauth/oauth/authorize?redirect_uri=http://localhost:5174&client_id=531752b5a27b659e3592aeca6866d5a8&response_type=code&state=oiwjefoiewojnef8238ewf