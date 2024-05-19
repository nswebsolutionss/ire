package com.ire.organizationplatform.service.response;

public class IdResponse {
    public final String id;
    public final String message;

    public IdResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SuccessIdResponse{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
