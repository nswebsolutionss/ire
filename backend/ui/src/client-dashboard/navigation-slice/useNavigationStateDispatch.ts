import { useDispatch } from "react-redux";
import { NavElement, navigationReducer, navigationSlice } from "./navigationSlice";
import { useMemo } from "react";


export interface NavigationStateDispatch {
    setSelectedNavElement: (element: NavElement | null) => void;
    setIsCollapsed: (isCollapsed: boolean) => void
} 

export const useNavigationStateDispatch = (): NavigationStateDispatch => {
    const dispatch = useDispatch();
    return useMemo(
    () => ({
        setSelectedNavElement: (element) => {
            dispatch(navigationSlice.actions.setSelectedNavElement(element))
        },
        setIsCollapsed: (isCollapsed) => {
            dispatch(navigationSlice.actions.setIsCollapsed(isCollapsed))
        }
    }), [dispatch])
}