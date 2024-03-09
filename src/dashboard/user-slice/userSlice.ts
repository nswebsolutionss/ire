import { EntityState, PayloadAction, createEntityAdapter, createSlice } from '@reduxjs/toolkit';
import { store } from '../../redux/store';
import { PropertyImageState } from '../images-slice/imagesSlice';

export type RootState = ReturnType<typeof store.getState>

export interface InvoiceState {
    date: number;
    level: string;
    amount: string;
    status: string;
}

export interface ProfileDetails {
    companyName: string;
    emailAddress: string;
    telephoneNumber: string;
    websiteUrl: string;
    facebookUrl: string;
    instagramUrl: string;
    description: string;
    youtubeUrl: string;
    images: PropertyImageState[];
}

export interface OpeningTimeState {
    day: string;
    openingTime: string;
    closingTime: string;
    closed: boolean;
    twentyFourHour: boolean;
}

export interface ProfileState {
    details: ProfileDetails;
    openingTimes: OpeningTimeState[]
}

export interface MembershipState {
    level: string;
    billingPeriod: string;
    memberSinceMs: number;
    membershipExpiration: number;
    invoices: InvoiceState[]
}
export interface UserState {
    id: number;
    membership: MembershipState;
    profile: ProfileState;
}

const initialState: UserState = {
    id: 0,
    membership: {
        level: "",
        billingPeriod: "",
        memberSinceMs: 0,
        membershipExpiration: 0,
        invoices: []
    },
    profile: {
        details: {
            companyName: "",
            emailAddress: "",
            telephoneNumber: "",
            websiteUrl: "",
            facebookUrl: "",
            instagramUrl: "",
            description: "",
            youtubeUrl: "",
            images: []
        },
        openingTimes: []
    }
}

export const userSlice = createSlice({
    name: 'user',
    initialState: initialState,
    reducers: {
        upsertUser: (state, action: PayloadAction<UserState>) => {
            console.log(action.payload)
            state.id =  action.payload.id
            state.profile =  Object.assign({}, action.payload.profile)
            state.membership =  Object.assign({}, action.payload.membership)
        }
    },
    selectors: {
        getUser: (state) => {
            console.log(state)
            return state
        }
    }
})
export const userReducer = userSlice.reducer;

export const userSelector = userSlice.getSelectors<{ user: UserState }>(
    (state) => state.user
)




