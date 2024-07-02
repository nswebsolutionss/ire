package com.ire.organizationplatform.service.messages;

import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

public class OrganizationRequestMessage implements MessageToSend {

    private Object payload = null;
    private HttpMethod method = HttpMethod.POST;
    private String uri = "/api/organization";
    private String host = "localhost";
    private int port = 8082;
    private ContentType contentType = ContentType.JSON;

    public OrganizationRequestMessage() {
    }

    public OrganizationRequestMessage payload(Object payload) {
        this.payload = payload;
        return this;
    }

    public OrganizationRequestMessage method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public OrganizationRequestMessage uri(String uri) {
        this.uri = uri;
        return this;
    }

    public OrganizationRequestMessage host(String host) {
        this.host = host;
        return this;
    }

    public OrganizationRequestMessage port(int port) {
        this.port = port;
        return this;
    }

    public OrganizationRequestMessage contentType(ContentType contentType) {
        this.contentType = contentType;
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
                contentType
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
