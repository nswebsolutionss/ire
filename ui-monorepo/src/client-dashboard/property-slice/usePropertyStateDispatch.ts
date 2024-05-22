
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

import { useMemo } from "react";
import { useDispatch } from "react-redux";
import { propertySlice } from "./propertySlice";

export interface PropertyStateDispatch {
    setSelectedPropertyId: (propertyId: { id: number | null }) => void;
}

export const usePropertyStateDispatch = (): PropertyStateDispatch => {
    const dispatch = useDispatch();
    return useMemo(
    () => ({
        setSelectedPropertyId: (id) => {
            dispatch(propertySlice.actions.setSelectedPropertyId(id))
        }
    }), [dispatch])
}