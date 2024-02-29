import { EntityId, EntityState, PayloadAction, configureStore, createEntityAdapter, createSlice } from '@reduxjs/toolkit';
import { useDispatch, useSelector } from 'react-redux';


export const useAppDispatch = useDispatch.withTypes<AppDispatch>()
export const useAppSelector = useSelector.withTypes<RootState>()

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export interface PropertyState {
    id: number;
    location: string;
    propertyType: string;
    numberOfBedrooms: number;
    numberOfBathrooms: number;
    description: string;
    price: string;
}

export enum NavElement {
    Properties,
    Dashboard,
    Membership,
    Profile,
    Settings,
    Help,
    SignOut
}

export interface State {
    selectedPropertyId: number | null;
    properties: EntityState<PropertyState, number>
    selectedNavElement: NavElement | null
}

export const propertyAdaptor = createEntityAdapter<PropertyState, number>({
    selectId: (propertyState) => propertyState.id
})

const initialState: State = {
    properties: propertyAdaptor.getInitialState(),
    selectedPropertyId: null,
    selectedNavElement: null
}
export const propertySlice = createSlice({
    name: 'dashboard',
    initialState: initialState,
    reducers: {
        upsertProperty: (state, action: PayloadAction<PropertyState>) => {
            propertyAdaptor.upsertOne(state.properties, action.payload)
        },
        setSelectedPropertyId: (state, action: PayloadAction<{id: number | null}>) => {
            state.selectedPropertyId = action.payload.id
        },
        setSelectedNavElement: (state, action: PayloadAction<NavElement | null>) => {
            state.selectedNavElement = action.payload
        }
    },
    selectors: {
        selectedPropertyId: (state) => {
            if(state.selectedPropertyId === null)
            {
                return null;
            }
            return state.selectedPropertyId
        },
        selectedNavElement: (state) => {
            if(state.selectedNavElement === null)
            {
                return null;
            }
            return state.selectedNavElement
        }
    }
})

export const store = configureStore({
    reducer: propertySlice.reducer
    
})

export const {setSelectedNavElement,  upsertProperty, setSelectedPropertyId} = propertySlice.actions

export const propertiesSelector = propertyAdaptor.getSelectors<RootState>(
    (state) => state.properties
)

export const statesSelector= propertySlice.getSelectors<State>(
    (state) => state
)




