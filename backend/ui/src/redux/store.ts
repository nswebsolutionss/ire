import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { propertyReducer } from "../client-dashboard/property-slice/propertySlice";
import { navigationReducer } from "../client-dashboard/navigation-slice/navigationSlice";
import { imageReducer } from "../client-dashboard/images-slice/imagesSlice";
import { userReducer } from "../client-dashboard/user-slice/userSlice";
import { searchPropertiesReducer } from "./search/searchPropertiesSlice";
import { persistStore, persistReducer } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";

const persistConfig = {
    key: "root",
    storage: storageSession
}

const rootReducer = combineReducers({
    property: propertyReducer,
        navigation: navigationReducer,
        images: imageReducer,
        user: userReducer,
        searchProperties: searchPropertiesReducer
})

const persistedReducer = persistReducer(persistConfig, rootReducer);


export const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
          serializableCheck: {
            ignoredActions: ["persist/PERSIST", "persist/REHYDRATE"],
          },
        })
})

export const persistor = persistStore(store)