package com.ire.organizationplatform.service.messages;

import com.ire.organizationplatform.service.support.ContentType;

import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {
    public final int statusCode;
    public final ContentType contentType;
    public final Object contentBody;
    public final HashMap<String, String> headers = new HashMap<>();

    public ResponseMessage(
            final int statusCode,
            final ContentType contentType,
            final Object contentBody,
            final Map<String, String> headers
    ) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.contentBody = contentBody;
        this.headers.putAll(headers);
    }

    public ResponseMessage withHeaderEntry(final String key, final String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "statusCode=" + statusCode +
                ", contentType=" + contentType +
                ", contentBody=" + contentBody +
                ", headers=" + headers +
                '}';
    }
}
