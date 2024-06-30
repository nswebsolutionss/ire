import { useCallback } from "react";
import { useThunkDispatch } from "./useThunkDispatch"
import { NewPropertyState, usePostProperty } from "./thunks/PropertyActions";
import { PropertyState } from "../client-dashboard/property-slice/propertySlice";

export interface InsertAction {
    token: string
    state: NewPropertyState
}

export interface SelectAction {
    token: string
    state: {id: number}
}

export interface DeleteAction {

}

export enum PropertyAction {
    Insert = "Insert",
    Delete = "Delete"
}

export type ActionPublisher = (action: Action) => void;
export type Action = InsertAction

export const usePropertyActionPublisher = (): ActionPublisher => {
    const dispatch = useThunkDispatch();

    return useCallback(
        (action: InsertAction) => {
            void dispatch(
                usePostProperty(action)
            )
        },
        [dispatch]
    )
}

