
/**
 *         upsertProperty: (state, action: PayloadAction<PropertyState>) => {
            propertyAdaptor.upsertOne(state.properties, action.payload)
        },
        setSelectedPropertyId: (state, action: PayloadAction<{ id: number | null }>) => {
            state.selectedPropertyId = action.payload.id
        },
        deletePropertyById: (state, action: PayloadAction<{ id: number | null }>) => {
            if (action.payload.id !== null) {
                propertyAdaptor.removeOne(state.properties, action.payload.id)
            }
        },
 */

import { useDispatch } from "react-redux";
import { Address, PropertyState, propertySlice } from "./propertySlice";
import { useMemo } from "react";

export interface PropertyStateDispatch {
    upsertProperty: (property: PropertyState) => void;
    setSelectedPropertyId: (propertyId: { id: number | null }) => void;
    deletePropertyById: (propertyId: {id: number | null}) => void;
}

export const usePropertyStateDispatch = (): PropertyStateDispatch => {
    const dispatch = useDispatch();
    return useMemo(
    () => ({
        upsertProperty: (property) => {
            dispatch(propertySlice.actions.upsertProperty(property))
        },
        setSelectedPropertyId: (id) => {
            dispatch(propertySlice.actions.setSelectedPropertyId(id))
        },
        deletePropertyById: (id) => {
            dispatch(propertySlice.actions.deletePropertyById(id))
        }
    }), [dispatch])
}