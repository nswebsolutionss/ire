package com.organizationplatform.protocol.domain.types;

public enum PropertyType {
    House("House"),
    Apartment("Apartment");

    private final String type;

    PropertyType(final String type) {
        this.type = type;
    }
    
    public String representation() {
        return type;
    }
}
