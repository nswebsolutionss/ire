import { createAsyncThunk } from "@reduxjs/toolkit";
import { InsertAction, SelectAction } from "../usePropertyActionPublisher";

export interface NewPropertyState {
    listing_id: number;
    property_type: string;
    number_of_bedrooms: number;
    number_of_bathrooms: number;
    property_description: string;
    price: string;
    date_added: number;
}
export const usePostProperty = createAsyncThunk<void, InsertAction>(
    "type/postProperty",
    async (data: InsertAction) => {
        console.log(data)

        try {
            usePostData("http://localhost:3000/api/secure/properties/", data)
        }
        catch (error) {
            console.log("An error occured when posing to: " + "http://localhost:3000/api/secure/properties/" + "   : " + error)
        }

    }
)

export const useSelectById = createAsyncThunk<void, SelectAction>(
    "type/selectProperty",
    async (data: SelectAction) => {
        console.log(data)

        try {
            useSelectData(`http://localhost:3000/api/secure/properties/${data.state.id}`, data)
        }
        catch (error) {
            console.log(error)
        }

    }
)


export async function usePostData(url: string, data: InsertAction) {

    console.log(JSON.stringify(data.state))
    const response = await fetch(url, {
        method: "POST",
        mode: "cors",
        cache: "no-cache",
        credentials: "include",
        headers: {
            "Content-type": "application/json",
            Authorization: data.token
        },
        body: JSON.stringify(data.state)
    })
    return response.json();
}

export async function useSelectData(url: string, data: SelectAction) {

    const response = await fetch(url, {
        method: "GET",
        mode: "cors",
        cache: "no-cache",
        credentials: "include",
        headers: {
            "Content-type": "application/json",
            Authorization: data.token
        }
    })
    return response.json();
}

