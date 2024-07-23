package com.ire.propertyportalgateway.service.fixtures;

import com.generated.organizationplatform.protocol.response.Response;
import com.ire.propertyportalgateway.service.messages.MessageToSend;
import com.ire.propertyportalgateway.service.messages.RequestMessage;
import com.ire.propertyportalgateway.service.messages.SuccessResponseMessage;
import com.ire.propertyportalgateway.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

import java.util.Map;
import java.util.UUID;

public class AuthenticationFixture {
    private final String id = UUID.randomUUID().toString();

    public MessageToSend checkAuthenticatedEmptyToken() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "")
        );
    }

    public MessageToSend checkAuthenticatedMalformedToken() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "oihewd732fjoh23f23fh23f")
        );
    }

    public MessageToSend checkAuthenticatedNonSignedToken() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE3MTk5MDk4MDd9")
        );
    }

    public MessageToSend checkAuthenticatedTokenSignedWithWrongKey() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.FUt7MBjqfzDdoICeCGKlUEEVQfhZ47PmDaqxg7N9JR4")
        );
    }

    public MessageToSend checkAuthenticatedTokenManipulated() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.LRZEpNAsBLpOx8UHB5aiujtn564YzsXtC1WgVh9gkPA")
        );
    }

    public MessageToSend checkAuthenticatedTokenExpired() {
        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjEyMTAzOTQ4NTh9.skeLTM8caT2_vAE1YxD7h49VER1a8GhIbDG-KXtBWWI")
        );
    }

    public MessageToSend checkAuthenticatedValidToken() {

        return () -> new RequestMessage(
                HttpMethod.GET,
                "/api/authenticated",
                "localhost",
                8084,
                null,
                null,
                Map.of("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE5MTAzOTQ4NTh9.Om5ZbEcA_6hLS4PIenL3BTbxT8i7b-iK7icHwhheJXk")
        );
    }

    public SuccessResponseMessage unauthenticatedResponse(final String message) {
        Response contentBody = new Response("", message);
        return new SuccessResponseMessage().statusCode(401)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }

    public SuccessResponseMessage authenticateResponse() {
        return new SuccessResponseMessage().statusCode(200)
                .contentType(null)
                .contentBody(null);
    }
}
