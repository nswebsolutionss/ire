import { EntityState, PayloadAction, createEntityAdapter, createSlice } from '@reduxjs/toolkit';
import { store } from '../../redux/store';

export type RootState = ReturnType<typeof store.getState>

export type Image = Blob | MediaSource


export interface PropertyImageState {
    id: number;
    isCover: boolean;
    image: Image;
    index: number;
    title?: string;
}


export interface State {
    images: EntityState<PropertyImageState, number>
}

export const imageAdapter = createEntityAdapter<PropertyImageState, number>({
    selectId: (propertyImageState) => propertyImageState.id ?? 1,
    sortComparer: (a, b) => a.index ?? 1 < b.index ?? 1
})

export const imageSlice = createSlice({
    name: 'images',
    initialState: {
        images: imageAdapter.getInitialState()
    },
    reducers: {
        upsertImage: (state, action: PayloadAction<PropertyImageState>) => {
            imageAdapter.upsertOne(state.images, action.payload)
        },
        upsertImages: (state, action: PayloadAction<PropertyImageState[]>) => {
            imageAdapter.upsertMany(state.images, action.payload)
        },
        deleteImageById: (state, action: PayloadAction<number>) => {
            if (action.payload !== null) {
                imageAdapter.removeOne(state.images, action.payload)
            }
        },
        deleteAll: (state) => {
            imageAdapter.removeAll(state.images)
        }
    },
    selectors: {
        imageById: (state, id: number) => {
            return imageAdapterSelector.selectById(state, id)
        },
        allImages: (state) => {
            return imageAdapterSelector.selectAll(state)
        }
    }
})
export const imageReducer = imageSlice.reducer;

const imageAdapterSelector = imageAdapter.getSelectors<State>(
    (state) => state.images
)

export const statesSelector = imageSlice.getSelectors<{ images: State }>(
    (state) => state.images
)
