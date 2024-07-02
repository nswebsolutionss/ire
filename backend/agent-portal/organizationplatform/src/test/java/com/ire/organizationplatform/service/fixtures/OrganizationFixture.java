package com.ire.organizationplatform.service.fixtures;

import com.generated.organizationplatform.protocol.domain.Organization;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.messages.OrganizationRequestMessage;
import com.ire.organizationplatform.service.messages.SuccessResponseMessage;
import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

import java.util.UUID;

public class OrganizationFixture {
    private final String id = UUID.randomUUID().toString();

    public OrganizationInformationFixture organizationInformationFixture() {
        return new OrganizationInformationFixture(id);
    }

    public PropertyDetailsFixture propertyDetailsFixture() {
        return new PropertyDetailsFixture(id);
    }

    public OrganizationRequestMessage createOrganizationRequest() {
        Organization organization = new Organization(
                id
        );
        return new OrganizationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/organization")
                .payload(organization);
    }

    public OrganizationRequestMessage getOrganizationRequest() {
        return new OrganizationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organization/" + id)
                .method(HttpMethod.GET);
    }

    public SuccessResponseMessage getOrganizationResponse() {
        Organization organization = new Organization(
                id
        );
        return new SuccessResponseMessage()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(organization);
    }

    public OrganizationRequestMessage deleteOrganizationRequest() {
        return new OrganizationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organization/" + id)
                .method(HttpMethod.DELETE);
    }

    // Responses

    public SuccessResponseMessage successResponse(final String message) {
        Response contentBody = new Response(id, message);
        return new SuccessResponseMessage().statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }

    public SuccessResponseMessage notFoundResponse(final String message) {
        Response contentBody = new Response(id, message);
        return new SuccessResponseMessage().statusCode(404)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }

    public SuccessResponseMessage conflictResponse(final String message) {
        Response contentBody = new Response(id, message);
        return new SuccessResponseMessage().statusCode(409)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }
}
