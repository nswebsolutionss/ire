import { useDispatch } from "react-redux";
import { useMemo } from "react";
import { PropertyImageState, imageSlice } from "./imagesSlice";

export interface ImageStateDispatch {
    upsertImage: (image: PropertyImageState) => void;
    upsertImages: (image: PropertyImageState[]) => void;
    deleteImageById: (imageId: number) => void;
    deleteAllImages: () => void;
}

export const useImageStateDispatch = (): ImageStateDispatch => {
    const dispatch = useDispatch();
    return useMemo(
    () => ({
        upsertImage: (image) => {
            dispatch(imageSlice.actions.upsertImage(image))
        },
        upsertImages: (images) => {
            dispatch(imageSlice.actions.upsertImages(images))
        },
        deleteImageById: (id) => {
            dispatch(imageSlice.actions.deleteImageById(id))
        },
        deleteAllImages: () => {
            dispatch(imageSlice.actions.deleteAll())
        }
    }), [dispatch])
}