import { ThunkDispatch } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";

type AnyThunkDispatch = ThunkDispatch<never, {}, any>;

export const useThunkDispatch = (): AnyThunkDispatch => useDispatch<AnyThunkDispatch>();