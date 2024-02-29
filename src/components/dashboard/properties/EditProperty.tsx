import { IoMdClose } from "react-icons/io";
import { useSelectedProperty } from "../../../dashboard/propertySelectors";
import { PropertyState, setSelectedPropertyId, upsertProperty } from "../../../dashboard/propertySlice";
import { useStateDisptach } from "../../../dashboard/useStateDispatch";
import { useState } from "react";
import styled from "styled-components";

interface EditPropertyFormProps {

}

const InputBox = styled.input`
    width: 250px;
    padding: 5px;
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.3);
    font-size: 18px;
    font-family: Inter;
    color: rgba(0, 0, 0, 0.9);
`

const Label = styled.label`
    font-size: 18px;
    font-family: Inter;
    color: rgba(0, 0, 0, 0.9);
    margin-bottom: 5px;
    margin-top: 10px;
`

const TextArea = styled.textarea`
    width: 500px;
    height: 150px;
    padding: 5px;
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.3);
    font-size: 16px;
    font-family: Inter;
    color: rgba(0, 0, 0, 0.9);
`

export const EditProperty: React.FC = () => {
    const dispatch = useStateDisptach();

    const handleClose = () => {
        dispatch(
            setSelectedPropertyId({ id: null })
        )
    }



    return (
        <div style={{ width: "100vw", height: "100vh", backgroundColor: "rgba(0, 0, 0, 0.4)", position: "absolute", zIndex: "5" }}>
            <IoMdClose size={30} style={{ position: "absolute", top: "calc(50vh - 370px)", right: "500px", zIndex: "6" }} onClick={() => handleClose()} />
            <div style={{ borderRadius: "15px", width: "1000px", height: "800px", backgroundColor: "white", position: "absolute", top: "calc(50vh - 400px)", right: "calc(50vw - 500px)", zIndex: "5", boxShadow: "2px 4px 15px grey" }}>

            <EditPropertyForm/>
            </div>
        </div>

    )
}

export const EditPropertyForm: React.FC<EditPropertyFormProps> = () => {
    const dispatch = useStateDisptach();

    const selectedProperty = useSelectedProperty()
    const [address, setAddress] = useState(selectedProperty?.location)
    const [bathrooms, setBathrooms] = useState(selectedProperty?.numberOfBathrooms.toString())
    const [bedrooms, setBedrooms] = useState(selectedProperty?.numberOfBedrooms.toString())
    const [price, setPrice] = useState(selectedProperty?.price)
    const [propertyType, setPropertyType] = useState(selectedProperty?.propertyType)
    const [description, setDescription] = useState(selectedProperty?.description)

    const handleClose = () => {
        dispatch(
            setSelectedPropertyId({ id: null })
        )
    }

    const handleSubmit = () => {
        {
            dispatch(
                upsertProperty({
                    id: selectedProperty?.id ?? 1,
                    location: address ?? "",
                    propertyType: propertyType ?? "",
                    numberOfBedrooms: Number(bedrooms),
                    numberOfBathrooms: Number(bathrooms),
                    description: description ?? "",
                    price: price ?? "",
                })
            )
            handleClose();
        }

    }
    return (
        <div style={{ display: "flex", height: "100%", flexDirection: "column", alignItems: "center", justifyContent: "center" }}>
            <Label>Address</Label>
            <InputBox value={address} onChange={(e) => { setAddress(e.target.value) }}></InputBox>
            <Label>Bathrooms</Label>
            <InputBox value={bathrooms} onChange={(e) => { setBathrooms(e.target.value) }}></InputBox>
            <Label>Bedrooms</Label>
            <InputBox value={bedrooms} onChange={(e) => { setBedrooms(e.target.value) }}></InputBox>
            <Label>Price</Label>
            <InputBox value={price} onChange={(e) => { setPrice(e.target.value) }}></InputBox>
            <Label>Property Type</Label>
            <InputBox value={propertyType} onChange={(e) => { setPropertyType(e.target.value) }}></InputBox>
            <Label>Description</Label>
            <TextArea value={description} onChange={(e) => { setDescription(e.target.value) }}></TextArea>
            <button onClick={() => handleSubmit()}></button>
        </div>
    )
}
