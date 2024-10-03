import { useDispatch } from "react-redux";
import { useMemo } from "react";
import { searchPropertiesSlice } from "./searchPropertiesSlice";
import { Position, PropertyType } from "./SearchPropertyTypes";


export interface SearchPropertiesStateDispatch {
    setAddress: (address: google.maps.places.PlaceResult | null) => void;
    setRadius: (address: string) => void;
    setMinBedrooms: (minBedrooms: string) => void;
    setMaxBedrooms: (maxBedrooms: string) => void;
    setMinPrice: (minPrice: string) => void;
    setMaxPrice: (maxPrice: string) => void;
    setPropertyTypes: (propertyType: PropertyType) => void;
    setPosition: (position: Position) => void;



} 

export const useSearchPropertiesStateDispatch = (): SearchPropertiesStateDispatch => {
    const dispatch = useDispatch();
    return useMemo(
    () => ({
        setAddress: (address: google.maps.places.PlaceResult | null) => {
            dispatch(searchPropertiesSlice.actions.setAddress(address))
        },
        setRadius: (radius: string) => {
            dispatch(searchPropertiesSlice.actions.setRadius(radius))
        },
        setMinBedrooms: (minBedrooms: string) => {
            dispatch(searchPropertiesSlice.actions.setMinBedrooms(minBedrooms))
        },
        setMaxBedrooms: (maxBedrooms: string) => {
            dispatch(searchPropertiesSlice.actions.setMaxBedrooms(maxBedrooms))
        },
        setMinPrice: (minPrice: string) => {
            dispatch(searchPropertiesSlice.actions.setMinPrice(minPrice))
        },
        setMaxPrice: (maxPrice: string) => {
            dispatch(searchPropertiesSlice.actions.setMaxPrice(maxPrice))
        },
        setPropertyTypes(propertyType) {
            dispatch(searchPropertiesSlice.actions.setPropertyTypes(propertyType))
        },
        setPosition(position) {
            dispatch(searchPropertiesSlice.actions.setPosition(position))
        },
    }), [dispatch])
}