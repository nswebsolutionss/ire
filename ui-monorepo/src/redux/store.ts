import { configureStore } from "@reduxjs/toolkit";
import { propertyReducer } from "../client-dashboard/property-slice/propertySlice";
import { navigationReducer } from "../client-dashboard/navigation-slice/navigationSlice";
import { imageReducer } from "../client-dashboard/images-slice/imagesSlice";
import { userReducer } from "../client-dashboard/user-slice/userSlice";

export const store = configureStore({
    reducer: {
        property: propertyReducer,
        navigation: navigationReducer,
        images: imageReducer,
        user: userReducer
    }
    
})