import { PayloadAction, createSlice } from '@reduxjs/toolkit';
import { store } from '../../redux/store';
import { Position, PropertyType, SearchPropertiesState } from './SearchPropertyTypes';


export type RootState = ReturnType<typeof store.getState>

const initialState: SearchPropertiesState = {
    address: null,
    position: null,
    options: {
        radius: "This area only",
        minBedrooms: "No min",
        maxBedrooms: "No max",
        minPrice: "No min",
        maxPrice: "No max",
        propertyTypes: []
    }
}

export const searchPropertiesSlice = createSlice({
    name: 'searchProperties',
    initialState: initialState,
    reducers: {
        setAddress: (state, action: PayloadAction<google.maps.places.PlaceResult | null>) => {
            state.address = action.payload
        },
        setRadius: (state, action: PayloadAction<string>) => {
            state.options.radius = action.payload
        },
        setMinBedrooms: (state, action: PayloadAction<string>) => {
            state.options.minBedrooms = action.payload
        },
        setMaxBedrooms: (state, action: PayloadAction<string>) => {
            state.options.maxBedrooms = action.payload
        },
        setMinPrice: (state, action: PayloadAction<string>) => {
            state.options.minPrice = action.payload
        },
        setMaxPrice: (state, action: PayloadAction<string>) => {
            state.options.maxPrice = action.payload
        },
        setPropertyTypes: (state, action: PayloadAction<PropertyType>) => {
            console.log(state.options.propertyTypes)
            if(state.options.propertyTypes.includes(action.payload)) {
                state.options.propertyTypes = state.options.propertyTypes.filter((item) => item !== action.payload)

            }
            else {
                state.options.propertyTypes = [...state.options.propertyTypes, action.payload]
            }
        },
        setPosition: (state, action: PayloadAction<Position>) => {
            state.position = action.payload
        },
    },
    selectors: {
        address: (state) => {
            return state.address
        },
        radius: (state) => {
            return state.options.radius
        },
        minBedrooms: (state) => {
            return state.options.minBedrooms
        },
        maxBedrooms: (state) => {
            return state.options.maxBedrooms
        },
        minPrice: (state) => {
            return state.options.minPrice
        },
        maxPrice: (state) => {
            return state.options.maxPrice
        },
        propertyTypes: (state) => {
            return state.options.propertyTypes
        },
        position: (state) => {
            return state.position
        }
    }
})
export const searchPropertiesReducer = searchPropertiesSlice.reducer;

export const searchPropertiesSelector= searchPropertiesSlice.getSelectors<RootState>(
    (state) => state.searchProperties
)




