import { useDispatch } from "react-redux";
import { useMemo } from "react";
import { UserState, userSlice } from "./userSlice";

export interface UserStateDispatch {
    upsertUser: (user: UserState) => void;

}

export const useUserStateDispatch = (): UserStateDispatch => {
    const dispatch = useDispatch();
    return useMemo(
    () => ({
        upsertUser: (user) => {
            dispatch(userSlice.actions.upsertUser(user))
        }
    }), [dispatch])
}