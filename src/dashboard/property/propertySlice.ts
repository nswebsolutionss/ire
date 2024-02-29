import { configureStore, createEntityAdapter, createSlice } from '@reduxjs/toolkit';
import { useDispatch, useSelector } from 'react-redux';


export const useAppDispatch = useDispatch.withTypes<AppDispatch>()
export const useAppSelector = useSelector.withTypes<RootState>()

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

// States
export interface PropertyState {
    id: number;
    location: string;
    propertyType: string;
    numberOfBedrooms: number;
    numberOfBathrooms: number;
    description: string;
    price: string;
}

export const propertyAdaptor = createEntityAdapter({
    selectId: (property: PropertyState) => property.id,
    sortComparer: (a: PropertyState, b: PropertyState) => a.price.localeCompare(b.price)

})

export const propertySlice = createSlice({
    name: 'properties',
    initialState: propertyAdaptor.getInitialState(),
    reducers: {
        upsertProperty: propertyAdaptor.addOne
    }
})

export const store = configureStore({
    reducer: {
        properties: propertySlice.reducer
    },
})

export const { upsertProperty } = propertySlice.actions

export const propertiesSelector = propertyAdaptor.getSelectors<RootState>(
    (state) => state.properties
)

export const allPropertiesSelector = propertiesSelector.selectAll(store.getState())

