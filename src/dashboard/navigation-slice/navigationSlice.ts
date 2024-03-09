import { PayloadAction, createSlice } from '@reduxjs/toolkit';
import { useDispatch, useSelector } from 'react-redux';
import { store } from '../../redux/store';
import { Address } from '../property-slice/propertySlice';


export type RootState = ReturnType<typeof store.getState>

export interface NavigationState {
    selectedNavElement: NavElement | null,
    isCollapsed: boolean;
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

const initialState: NavigationState = {
    selectedNavElement: null,
    isCollapsed: true
}

export const navigationSlice = createSlice({
    name: 'navigation',
    initialState: initialState,
    reducers: {
        setSelectedNavElement: (state, action: PayloadAction<NavElement | null>) => {
            state.selectedNavElement = action.payload
        },
        setIsCollapsed: (state, action: PayloadAction<boolean>) => {
            state.isCollapsed = action.payload
        }
    },
    selectors: {
        selectedNavElement: (state) => {
            if(state.selectedNavElement === null)
            {
                return null;
            }
            return state.selectedNavElement
        },
        isCollapsed: (state) => {
            return state.isCollapsed
        }
    }
})
export const navigationReducer = navigationSlice.reducer;

export const navigationSelector= navigationSlice.getSelectors<RootState>(
    (state) => state.navigation
)




