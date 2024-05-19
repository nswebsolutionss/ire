package com.ire.organizationplatform.service.request;

public class IdRequest implements Request{
    public String id;

    public IdRequest() {

    }

    public void setId(final String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }
}
