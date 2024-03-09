import { EntityState, PayloadAction, createEntityAdapter, createSlice } from '@reduxjs/toolkit';
import { store } from '../../redux/store';
import { PropertyImageState } from '../images-slice/imagesSlice';

export type RootState = ReturnType<typeof store.getState>

export type UserCurrency = "£" | "$" | "€" | "zł" | null

export interface Address {
    houseNumber: string,
    line2: string,
    town: string,
    country: string,
    postcode: string
}
export interface PropertyState {
    id: number;
    address: Address;
    propertyType: string;
    numberOfBedrooms: number;
    numberOfBathrooms: number;
    description: string;
    price: string;
    userCurrency: UserCurrency;
    dateAdded: number;
    images: PropertyImageState[];
}


export interface State {
    selectedPropertyId: number | null;
    properties: EntityState<PropertyState, number>
}

export const propertyAdaptor = createEntityAdapter<PropertyState, number>({
    selectId: (propertyState) => propertyState.id
})

const initialState: State = {
    properties: propertyAdaptor.getInitialState(),
    selectedPropertyId: null,
}

export const propertySlice = createSlice({
    name: 'dashboard',
    initialState: initialState,
    reducers: {
        upsertProperty: (state, action: PayloadAction<PropertyState>) => {
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
    },
    selectors: {
        selectedPropertyId: (state) => {
            if (state.selectedPropertyId === null) {
                return null;
            }
            return state.selectedPropertyId
        },
        selectedPropertyById: (state) => {
            const selectedPropertyId = state.selectedPropertyId
            if (selectedPropertyId === null) {
                return null;
            }
            return propertyAdapterSelector.selectById(state, selectedPropertyId)
        },
        propertyById: (state, id: number) => {
           
            return propertyAdapterSelector.selectById(state, id)
        },
        allProperties: (state) => {
            return propertyAdapterSelector.selectAll(state)
        }
    }
})
export const propertyReducer = propertySlice.reducer;

const propertyAdapterSelector = propertyAdaptor.getSelectors<State>(
    (state) => state.properties
)

export const statesSelector = propertySlice.getSelectors<{ property: State }>(
    (state) => state.property
)




