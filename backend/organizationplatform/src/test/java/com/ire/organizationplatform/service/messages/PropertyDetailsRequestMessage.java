package com.ire.organizationplatform.service.messages;

import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

public class PropertyDetailsRequestMessage implements MessageToSend {

    private Object payload = null;
    private HttpMethod method = HttpMethod.POST;
    private String uri = "/api/properties";
    private String host = "localhost";
    private int port = 8082;
    private ContentType contentType = ContentType.JSON;

    public PropertyDetailsRequestMessage() {
    }

    public PropertyDetailsRequestMessage payload(Object payload) {
        this.payload = payload;
        return this;
    }

    public PropertyDetailsRequestMessage method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public PropertyDetailsRequestMessage uri(String uri) {
        this.uri = uri;
        return this;
    }

    public PropertyDetailsRequestMessage host(String host) {
        this.host = host;
        return this;
    }

    public PropertyDetailsRequestMessage port(int port) {
        this.port = port;
        return this;
    }

    public PropertyDetailsRequestMessage contentType(ContentType contentType) {
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
