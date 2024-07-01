package com.ire.propertyportalgateway.service.messages;


import com.ire.propertyportalgateway.service.support.ContentType;

import java.util.Map;

public class SuccessResponseMessage implements MessageToReceive {

    private int statusCode = 200;
    private ContentType contentType = ContentType.JSON;
    private Object contentBody = null;
    private Map<String, String> headers = Map.of();

    public SuccessResponseMessage statusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public SuccessResponseMessage contentType(final ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public SuccessResponseMessage contentBody(final Object contentBody) {
        this.contentBody = contentBody;
        return this;
    }

    public SuccessResponseMessage headers(final Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public ResponseMessage toMessage() {
        return new ResponseMessage(
                statusCode,
                contentType,
                contentBody,
                headers
        );
    }
}
