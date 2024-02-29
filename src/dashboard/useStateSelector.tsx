import { TypedUseSelectorHook, useSelector } from "react-redux";
import { store } from "./property/propertySlice";

export type RootState = ReturnType<typeof store.getState>

export const useStateSelector: TypedUseSelectorHook<RootState> = useSelector;