import { TypedUseSelectorHook, useSelector } from "react-redux";
import { store } from "./propertySlice";

export type RootState = ReturnType<typeof store.getState>

export const useStateSelector: TypedUseSelectorHook<RootState> = useSelector;