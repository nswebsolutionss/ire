package com.ire.propertyportalgateway.service.fixtures;

import com.ire.propertyportalgateway.service.messages.MessageToReceive;
import com.ire.propertyportalgateway.service.messages.MessageToSend;
import com.ire.propertyportalgateway.service.messages.RequestMessage;
import com.ire.propertyportalgateway.service.messages.SuccessResponseMessage;
import io.vertx.core.http.HttpMethod;

import java.util.Map;
import java.util.UUID;

import static com.ire.propertyportalgateway.service.support.RestApi.supplementaryData;

public class LogonFixture {

    public MessageToSend logonRequest() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/login",
                "localhost",
                8084,
                null,
                null,
                Map.of()
        );
    }

    public MessageToSend logonCallbackRequest() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/callback?state=982323f98h3f",
                "localhost",
                8084,
                null,
                null,
                Map.of()
        );
    }

    public MessageToSend logonCallbackRequestWithCorrectState() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/callback?state=" + supplementaryData.getLastReceivedHeader().get("Location").split("&state=")[1] + "&code=12489714hj3124907",
                "localhost",
                8084,
                null,
                null,
                Map.of()
        );
    }

    public SuccessResponseMessage authenticateResponse() {
        return new SuccessResponseMessage().statusCode(200)
                .contentType(null)
                .contentBody(null);
    }

    public MessageToReceive logonRedirectResponse() {
        return new SuccessResponseMessage().statusCode(200)
                .contentType(null)
                .headers(Map.of("Location", "https://0939966.propelauthtest.com/propelauth/oauth/authorize?redirect_uri=http://localhost:8084/callback&client_id=531752b5a27b659e3592aeca6866d5a8&response_type=code&state=1865365239572480830"));
    }

    public MessageToReceive logonRedirectUnauthenticatedResponse() {
        return new SuccessResponseMessage().statusCode(400)
                .contentType(null)
                .contentBody(null);
    }

    public MessageToReceive logonCallbackResponse() {
        return new SuccessResponseMessage().statusCode(200)
                .contentType(null)
                .headers(Map.of("Location", "http://localhost:5174/dashboard"));
    }
}
