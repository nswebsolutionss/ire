package com.ire.organizationplatform.service.messages;

import com.ire.organizationplatform.service.support.ContentType;
import io.vertx.core.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestMessage {

    private final HttpMethod method;
    private final String uri;
    private final String host;
    private final int port;
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private final Object payload;
    private final ContentType contentType;
    //    private final String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjIwNzI4Mzk5OTJ9";
    private final String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE3MTk5MDk4MDd9.ujBHkL_7_h1uWymOakbSRSLvbCDIFZWftnZk4vAM9vWUms_fblTDvbRwlXCHglAH6MoSWd3FVp7luLapRsUriCUD2q9aavFQ5ScQBhM3-d_g6gXzNhwYD1elKksiBC0go2SPkeSY2RjifqNrkakFTno0XocK-EdblhNrF9NBgLmz4blCfOk8ZHj028Ul1MueNw7cPcGwGzwIKJi2Dr6qQ2bXvZ4YLEHIXddy6HT3eDxyUbJHLOec4Ddsp0G5LALa624I-P86yrUELkr3hqM4x3y2Z1k4d3vZzEEcNNqJHclFKupteL_kyLW6oe5vqaS0Qo1PIj8fV7fPZLTwhBCPMw";


    public RequestMessage(
            final HttpMethod method,
            final String uri,
            final String host,
            final int port,
            final Object payload,
            final ContentType contentType
    ) {
        this.method = method;
        this.uri = uri;
        this.host = host;
        this.port = port;
        this.payload = payload;
        this.contentType = contentType;
        this.headers.put("Cookie", "Authorization=" + jwt);

    }

    public String uri() {
        return this.uri;
    }

    public Map<String, String> headers() {
        return this.headers;
    }

    public HttpMethod method() {
        return this.method;
    }

    public String host() {
        return this.host;
    }

    public int port() {
        return this.port;
    }

    public Map<String, String> queryParams() {
        return this.queryParams;
    }

    public ContentType contentType() {
        return this.contentType;
    }

    public Object payload() {
        return this.payload;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "method=" + method +
                ", uri='" + uri + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", queryParams=" + queryParams +
                ", headers=" + headers +
                ", payload=" + payload +
                ", contentType=" + contentType +
                '}';
    }
}
