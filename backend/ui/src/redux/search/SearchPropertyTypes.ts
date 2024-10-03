import { Tuple } from "@reduxjs/toolkit";

export enum PropertyType {
    DETACHED,
    SEMI_DETACHED,
    TERRACED,
    APARTMENT,
    BUNGALOW,
    VILLA
  }
  
export enum FooType {
    SALE,
    RENTAL
}

export interface Address {
    houseNumber: string,
    line2: string,
    town: string,
    country: string,
    postcode: string
}

export interface Position {
    lat: number,
    lon: number
}

export interface SearchPropertiesState {
    address: google.maps.places.PlaceResult | null,
    position: Position | null
    options: {
        radius: string;
        minBedrooms: string;
        maxBedrooms: string;
        minPrice: string,
        maxPrice: string,
        propertyTypes: PropertyType[],
    }
}