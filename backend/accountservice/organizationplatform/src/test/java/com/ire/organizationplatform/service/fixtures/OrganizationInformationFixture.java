package com.ire.organizationplatform.service.fixtures;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.messages.OrganizationInformationRequestMessage;
import com.ire.organizationplatform.service.messages.SuccessResponseMessage;
import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

import java.util.UUID;

import static com.ire.organizationplatform.service.support.RestApi.supplementaryData;

public class OrganizationInformationFixture {
    private String id = UUID.randomUUID().toString();
    private final long memberSince = System.currentTimeMillis();
    private final long lastUpdated = System.currentTimeMillis();
    private long lastUpdatedUpdate;

    public OrganizationInformationFixture(String id) {
        this.id = id;
    }

    public OrganizationInformationFixture() {
    }

    public OrganizationInformationRequestMessage createOrganizationInformationRequest() {
        OrganizationInformation organizationInformation = new OrganizationInformation(
                "",
                "companyName",
                "companyDescription",
                "telephoneNumber",
                "websiteUrl",
                "facebookUrl",
                "instagramUrl",
                "youtubeUrl",
                memberSince,
                lastUpdated,
                id

        );
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation")
                .payload(organizationInformation);
    }

    public OrganizationInformationRequestMessage getOrganizationInformationRequest() {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation/" + supplementaryData.getLastReceivedMessage().getString("id"))
                .method(HttpMethod.GET);
    }

    public OrganizationInformationRequestMessage getOrganizationInformationRequest(final String organizationInformationId) {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation/" + organizationInformationId)
                .method(HttpMethod.GET);
    }

    public SuccessResponseMessage getOrganizationInformationResponse() {
        OrganizationInformation organizationInformation = new OrganizationInformation(
                "",
                "companyName",
                "companyDescription",
                "telephoneNumber",
                "websiteUrl",
                "facebookUrl",
                "instagramUrl",
                "youtubeUrl",
                memberSince,
                lastUpdated,
                id

        );
        return new SuccessResponseMessage()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(organizationInformation);
    }

    public OrganizationInformationRequestMessage deleteOrganizationInformationRequest() {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation/" + supplementaryData.getLastReceivedMessage().getString("id"))
                .method(HttpMethod.DELETE);
    }

    public OrganizationInformationRequestMessage invalidDeleteOrganizationInformationRequest() {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation/12345567")
                .method(HttpMethod.DELETE);
    }

    public OrganizationInformationRequestMessage updateOrganizationInformationRequest() {
        lastUpdatedUpdate = System.currentTimeMillis();
        OrganizationInformation organizationInformation = new OrganizationInformation(
                supplementaryData.getLastReceivedMessage().getString("id"),
                "newCompanyName",
                "newCompanyDescription",
                "newTelephoneNumber",
                "newWebsiteUrl",
                "newFacebookUrl",
                "newInstagramUrl",
                "newYoutubeUrl",
                memberSince,
                lastUpdatedUpdate,
                id
        );
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation")
                .method(HttpMethod.PUT)
                .payload(organizationInformation);
    }

    public OrganizationInformationRequestMessage updateOrganizationInformationRequest(final String organizationInformationId) {
        lastUpdatedUpdate = System.currentTimeMillis();
        OrganizationInformation organizationInformation = new OrganizationInformation(
                organizationInformationId,
                "newCompanyName",
                "newCompanyDescription",
                "newTelephoneNumber",
                "newWebsiteUrl",
                "newFacebookUrl",
                "newInstagramUrl",
                "newYoutubeUrl",
                memberSince,
                lastUpdatedUpdate,
                id
        );
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation")
                .method(HttpMethod.PUT)
                .payload(organizationInformation);
    }

    public SuccessResponseMessage updatedGetOrganizationInformationResponse() {
        OrganizationInformation organizationInformation = new OrganizationInformation(
                "",
                "newCompanyName",
                "newCompanyDescription",
                "newTelephoneNumber",
                "newWebsiteUrl",
                "newFacebookUrl",
                "newInstagramUrl",
                "newYoutubeUrl",
                memberSince,
                lastUpdatedUpdate,
                id
        );
        return new SuccessResponseMessage()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(organizationInformation);
    }

    // Responses

    public SuccessResponseMessage successResponse(final String message) {
        Response contentBody = new Response("", message);
        return new SuccessResponseMessage().statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }

    public SuccessResponseMessage notFoundResponse(final String message) {
        Response contentBody = new Response("", message);
        return new SuccessResponseMessage().statusCode(404)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }

    public SuccessResponseMessage conflictResponse(final String message) {
        Response contentBody = new Response("", message);
        return new SuccessResponseMessage().statusCode(409)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }
}
