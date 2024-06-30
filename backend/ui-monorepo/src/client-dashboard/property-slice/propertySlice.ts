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
}

export const propertyAdaptor = createEntityAdapter<PropertyState, number>({
    selectId: (propertyState) => propertyState.id
})

const initialState: State = {
    selectedPropertyId: null,
}

export const propertySlice = createSlice({
    name: 'dashboard',
    initialState: initialState,
    reducers: {
        setSelectedPropertyId: (state, action: PayloadAction<{ id: number | null }>) => {
            state.selectedPropertyId = action.payload.id
        }
    },
    selectors: {
        selectedPropertyId: (state) => {
            if (state.selectedPropertyId === null) {
                return null;
            }
            return state.selectedPropertyId
        }
    }
})
export const propertyReducer = propertySlice.reducer;



export const statesSelector = propertySlice.getSelectors<{ property: State }>(
    (state) => state.property
)




