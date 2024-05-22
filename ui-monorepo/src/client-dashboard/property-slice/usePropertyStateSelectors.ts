import { createSelector } from "reselect";
import { Address, PropertyState, State, statesSelector } from "./propertySlice"
import { TypedUseSelectorHook, useSelector } from "react-redux";
import { store } from "../../redux/store";
import { GridRowsProp } from "@mui/x-data-grid";

export type RootState = ReturnType<typeof store.getState>

interface PartialRootState {
  property: State
}

export const createPropertySelector = createSelector.withTypes<PartialRootState>();
export const useStateSelector: TypedUseSelectorHook<PartialRootState> = useSelector;

export const useSelectedPropertyId = (): number | null => {
  return useStateSelector(statesSelector.selectedPropertyId)
}



