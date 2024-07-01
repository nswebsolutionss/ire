package com.ire.organizationplatform.service.fixtures;

import com.generated.organizationplatform.protocol.domain.OrganizationInformation;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.messages.OrganizationInformationRequestMessage;
import com.ire.organizationplatform.service.messages.SuccessResponseMessage;
import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

import java.util.Random;
import java.util.UUID;

import static com.ire.organizationplatform.service.support.RestApi.idsSeen;

public class OrganizationInformationFixture {
    private final String id = UUID.randomUUID().toString();
    private final long memberSince = System.currentTimeMillis();
    private final long lastUpdated = System.currentTimeMillis();
    private long lastUpdatedUpdate;

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
                .uri("/api/organizationInformation/" + idsSeen.getLast())
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
                .uri("/api/organizationInformation/" + idsSeen.getLast())
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
                idsSeen.getLast(),
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
