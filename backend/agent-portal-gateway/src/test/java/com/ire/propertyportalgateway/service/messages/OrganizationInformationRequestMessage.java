package com.ire.propertyportalgateway.service.messages;

import com.ire.propertyportalgateway.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

import java.util.Map;

public class OrganizationInformationRequestMessage implements MessageToSend {

    private Object payload = null;
    private HttpMethod method = HttpMethod.POST;
    private String uri = "/api/organizationInformation";
    private String host = "localhost";
    private int port = 8084;
    private ContentType contentType = ContentType.JSON;
    private Map<String, String> cookieHeader = Map.of("Cookie", "Authorization=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MTk3NjY5MDMsImV4cCI6MTg0NTk5NzMwMywiYXVkIjoiIiwic3ViIjoiIn0");

    public OrganizationInformationRequestMessage() {
    }

    public OrganizationInformationRequestMessage payload(Object payload) {
        this.payload = payload;
        return this;
    }

    public OrganizationInformationRequestMessage method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public OrganizationInformationRequestMessage uri(String uri) {
        this.uri = uri;
        return this;
    }

    public OrganizationInformationRequestMessage host(String host) {
        this.host = host;
        return this;
    }

    public OrganizationInformationRequestMessage port(int port) {
        this.port = port;
        return this;
    }

    public OrganizationInformationRequestMessage contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public OrganizationInformationRequestMessage withoutAuth() {
        this.cookieHeader = Map.of();
        return this;
    }

    @Override
    public RequestMessage toMessage() {
        return new RequestMessage(
                method,
                uri,
                host,
                port,
                payload,
                contentType,
                cookieHeader
        );
    }

    @Override
    public String toString() {
        return "OrganizationInformationRequestMessage{" +
                "payload=" + payload +
                ", method=" + method +
                ", uri='" + uri + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", contentType=" + contentType +
                '}';
    }
}
