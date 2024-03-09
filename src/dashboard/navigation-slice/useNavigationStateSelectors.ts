import { TypedUseSelectorHook, useSelector } from "react-redux";
import { NavElement, navigationSelector } from "./navigationSlice"
import { store } from "../../redux/store";
import { createSelector } from "reselect";

export type RootState = ReturnType<typeof store.getState>

export const createNavigationSelector = createSelector.withTypes<RootState>();
export const useNavigationSelector: TypedUseSelectorHook<RootState> = useSelector;


export const useSelectedNavElement = (): NavElement | null => {
  return useNavigationSelector(navigationSelector.selectedNavElement)
}

export const useIsCollapsed = (): boolean => {
  return useNavigationSelector(navigationSelector.isCollapsed)
}

