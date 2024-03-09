import { configureStore } from "@reduxjs/toolkit";
import { propertyReducer } from "../dashboard/property-slice/propertySlice";
import { navigationReducer } from "../dashboard/navigation-slice/navigationSlice";
import { imageReducer } from "../dashboard/images-slice/imagesSlice";
import { userReducer } from "../dashboard/user-slice/userSlice";

export const store = configureStore({
    reducer: {
        property: propertyReducer,
        navigation: navigationReducer,
        images: imageReducer,
        user: userReducer
    }
    
})