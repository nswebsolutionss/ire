package com.ire.organizationplatform.service.fixtures;

import com.ire.backend.database.OrganizationInformation;
import com.ire.organizationplatform.service.messages.OrganizationInformationRequestMessage;
import com.ire.organizationplatform.service.messages.SuccessResponseMessage;
import com.ire.organizationplatform.service.response.IdResponse;
import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;

import java.util.UUID;

public class NackFixture {
    private final String id = UUID.randomUUID().toString();
    private final long memberSince = System.currentTimeMillis();
    private final long lastUpdated = System.currentTimeMillis();

    public OrganizationInformationRequestMessage malformedJsonRequest()
    {
        OrganizationInformation organizationInformation = new OrganizationInformation(
                id,
                "companyName",
                "companyDescription",
                "telephoneNumber",
                "websiteUrl",
                "facebookUrl",
                "instagramUrl",
                "youtubeUrl",
                memberSince,
                lastUpdated

        );
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation")
                .payload(Json.encode("oikhjefoijhwef"));
    }


    public SuccessResponseMessage malformedJsonResponse(String message) {
        IdResponse contentBody = new IdResponse("", message);
        return new SuccessResponseMessage().statusCode(400)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }

    public OrganizationInformationRequestMessage malformedPathParamRequest(String id) {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/organizationInformation/" + id)
                .method(HttpMethod.GET);
    }
}
