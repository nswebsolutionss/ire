import { TypedUseSelectorHook, useSelector } from "react-redux";
import { createSelector } from "reselect";
import { store } from "../../redux/store";
import {UserState, userSelector } from "./userSlice";

export type RootState = ReturnType<typeof store.getState>

interface PartialRootState {
  user: UserState
}

export const createUserSelector = createSelector.withTypes<PartialRootState>();
export const useStateSelector: TypedUseSelectorHook<PartialRootState> = useSelector;

export const useUser = (): UserState => {
  return useStateSelector(userSelector.getUser)
}




