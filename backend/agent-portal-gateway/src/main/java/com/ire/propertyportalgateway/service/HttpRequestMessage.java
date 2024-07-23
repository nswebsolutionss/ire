package com.ire.propertyportalgateway.service;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestMessage {
    private HttpMethod method;
    private final Map<String, String> headers = new HashMap<>();
    private final MultiMap formBody = MultiMap.caseInsensitiveMultiMap();
    private String url;
    private JsonObject jsonBody;

    public HttpRequestMessage withMethod(final HttpMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequestMessage withHeader(final String key, final String value) {
        headers.put(key, value);
        return this;
    }

    public HttpRequestMessage withFormBody(final MultiMap formBody) {
        this.formBody.addAll(formBody);
        return this;
    }

    public HttpRequestMessage withUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpRequestMessage withJsonBody(JsonObject jsonBody) {
        this.jsonBody = jsonBody;
        return this;
    }

    public HttpMethod method() {
        return method;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public MultiMap formBody() {
        return formBody;
    }

    public String url() {
        return url;
    }

    public JsonObject jsonBody() {
        return jsonBody;
    }

    @Override
    public String toString() {
        return "HttpRequestMessage{" +
                "method=" + method +
                ", headers=" + headers +
                ", formBody=" + formBody +
                ", url='" + url + '\'' +
                ", jsonBody='" + jsonBody + '\'' +
                '}';
    }
}
