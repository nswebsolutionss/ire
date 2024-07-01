package com.organizationplatform.protocol.domain.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Currency;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Price {
    @JsonProperty("value")
    private Double value;
    @JsonProperty("currency")
    private Currency currency;

    public Price() {

    }

    public Price(final Double value, final Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    @JsonProperty("value")
    public Double value() {
        return value;
    }

    @JsonProperty("currency")
    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;
        return Objects.equals(value, price.value) && Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(value);
        result = 31 * result + Objects.hashCode(currency);
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}
