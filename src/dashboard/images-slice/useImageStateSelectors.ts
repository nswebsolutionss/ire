import { createSelector } from "reselect";
import { TypedUseSelectorHook, useSelector } from "react-redux";
import { store } from "../../redux/store";
import { PropertyImageState, State, statesSelector } from "./imagesSlice";

export type RootState = ReturnType<typeof store.getState>

interface PartialRootState {
  images: State
}

export const createPropertySelector = createSelector.withTypes<PartialRootState>();
export const useStateSelector: TypedUseSelectorHook<PartialRootState> = useSelector;

export const useAllImages = (): PropertyImageState[] => {
  return useStateSelector(statesSelector.allImages)
}




