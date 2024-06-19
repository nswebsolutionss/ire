package com.ire.organizationplatform.service.fixtures;

import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.generated.organizationplatform.protocol.response.Response;
import com.ire.organizationplatform.service.messages.OrganizationInformationRequestMessage;
import com.ire.organizationplatform.service.messages.PropertyDetailsRequestMessage;
import com.ire.organizationplatform.service.messages.SuccessResponseMessage;
import com.ire.organizationplatform.service.support.ContentType;
import com.organizationplatform.protocol.domain.types.Address;
import com.organizationplatform.protocol.domain.types.Price;
import com.organizationplatform.protocol.domain.types.PropertyType;
import io.vertx.core.http.HttpMethod;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static com.ire.organizationplatform.service.support.RestApi.idsSeen;

public class PropertyDetailsFixture {
    private final String id = UUID.randomUUID().toString();
    private final long dateAdded = System.currentTimeMillis();
    private final long lastUpdated = System.currentTimeMillis();
    private long lastUpdatedUpdate;

    public PropertyDetailsRequestMessage createPropertyDetails()
    {
        PropertyDetails propertyDetails = new PropertyDetails(
                "1",
                new Address(
                        "12",
                        "Hazel Way",
                        "crawley",
                        "west sussex",
                        "rh104jr",
                        "united kingdom"
                ),
                dateAdded,
                lastUpdated,
                PropertyType.House,
                2,
                2,
                new Price(100.0, Currency.getInstance("GBP")),
                List.of(),
                id
        );
        return new PropertyDetailsRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties")
                .payload(propertyDetails);
    }

    public PropertyDetailsRequestMessage getPropertyDetailsRequest() {
        return new PropertyDetailsRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties/" + idsSeen.getLast())
                .method(HttpMethod.GET);
    }

    public PropertyDetailsRequestMessage getPropertyDetailsRequest(final String id) {
        return new PropertyDetailsRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties/" + id)
                .method(HttpMethod.GET);
    }

    public SuccessResponseMessage getPropertyDetailsResponse() {
        PropertyDetails propertyDetails = new PropertyDetails(
                "",
                new Address(
                        "12",
                        "Hazel Way",
                        "crawley",
                        "west sussex",
                        "rh104jr",
                        "united kingdom"
                ),
                dateAdded,
                lastUpdated,
                PropertyType.House,
                2,
                2,
                new Price(100.0, Currency.getInstance("GBP")),
                List.of(),
                id
        );
        return new SuccessResponseMessage()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(propertyDetails);
    }

    public OrganizationInformationRequestMessage deletePropertyDetailsRequest() {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties/" + idsSeen.getLast())
                .method(HttpMethod.DELETE);
    }

    public OrganizationInformationRequestMessage deletePropertyDetailsRequest(final String id) {
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties/" + id)
                .method(HttpMethod.DELETE);
    }

    public OrganizationInformationRequestMessage updatePropertyDetailsRequest() {
        lastUpdatedUpdate = System.currentTimeMillis();
        PropertyDetails propertyDetails = new PropertyDetails(
                idsSeen.getLast(),
                new Address(
                        "14",
                        "Chimp Way",
                        "London",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                dateAdded,
                lastUpdatedUpdate,
                PropertyType.Apartment,
                5,
                3,
                new Price(472.0, Currency.getInstance("GBP")),
                List.of(),
                id
        );
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties")
                .method(HttpMethod.PUT)
                .payload(propertyDetails);
    }

    public OrganizationInformationRequestMessage updatePropertyDetailsRequest(final String id) {
        lastUpdatedUpdate = System.currentTimeMillis();
        PropertyDetails propertyDetails = new PropertyDetails(
                id,
                new Address(
                        "14",
                        "Chimp Way",
                        "London",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                dateAdded,
                lastUpdatedUpdate,
                PropertyType.Apartment,
                5,
                3,
                new Price(472.0, Currency.getInstance("GBP")),
                List.of(),
                id
        );
        return new OrganizationInformationRequestMessage()
                .contentType(ContentType.JSON)
                .uri("/api/properties")
                .method(HttpMethod.PUT)
                .payload(propertyDetails);
    }

    public SuccessResponseMessage updatedGetPropertyDetailsResponse() {
        PropertyDetails propertyDetails = new PropertyDetails(
                idsSeen.getLast(),
                new Address(
                        "14",
                        "Chimp Way",
                        "London",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                dateAdded,
                lastUpdatedUpdate,
                PropertyType.Apartment,
                5,
                3,
                new Price(472.0, Currency.getInstance("GBP")),
                List.of(),
                id
        );
        return new SuccessResponseMessage()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentBody(propertyDetails);
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
        Response contentBody = new Response(idsSeen.getLast(), message);
        return new SuccessResponseMessage().statusCode(409)
                .contentType(ContentType.JSON)
                .contentBody(contentBody);
    }
}
