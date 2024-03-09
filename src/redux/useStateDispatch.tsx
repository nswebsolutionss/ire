import { Action, Dispatch } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";

type AnyStateDispatch = Dispatch<Action>

export const useStateDisptach = (): AnyStateDispatch => useDispatch<AnyStateDispatch>();