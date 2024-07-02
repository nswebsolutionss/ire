package com.organizationplatform.protocol.domain.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Address {

    @JsonProperty("buildingIdentifier")
    private String buildingIdentifier;
    @JsonProperty("streetName")
    private String streetName;
    @JsonProperty("city")
    private String city;
    @JsonProperty("county")
    private String county;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("country")
    private String country;


    public Address() {

    }

    public Address(
            final String buildingIdentifier,
            final String streetName,
            final String city,
            final String county,
            final String postcode,
            final String country
    ) {
        this.buildingIdentifier = buildingIdentifier;
        this.streetName = streetName;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.country = country;
    }

    @JsonProperty("buildingIdentifier")
    public String buildingIdentifier() {
        return buildingIdentifier;
    }

    @JsonProperty("streetName")
    public String streetName() {
        return streetName;
    }

    @JsonProperty("city")
    public String city() {
        return city;
    }

    @JsonProperty("county")
    public String county() {
        return county;
    }

    @JsonProperty("postcode")
    public String postcode() {
        return postcode;
    }

    @JsonProperty("country")
    public String country() {
        return country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "buildingIdentifier='" + buildingIdentifier + '\'' +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;
        return Objects.equals(buildingIdentifier, address.buildingIdentifier) && Objects.equals(streetName, address.streetName) && Objects.equals(city, address.city) && Objects.equals(county, address.county) && Objects.equals(postcode, address.postcode) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(buildingIdentifier);
        result = 31 * result + Objects.hashCode(streetName);
        result = 31 * result + Objects.hashCode(city);
        result = 31 * result + Objects.hashCode(county);
        result = 31 * result + Objects.hashCode(postcode);
        result = 31 * result + Objects.hashCode(country);
        return result;
    }
}
