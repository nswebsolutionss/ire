import { ChangeEvent, useState } from "react";
import { usePropertyStateDispatch } from "../../dashboard/property-slice/usePropertyStateDispatch";
import { useSelectedPropertyId, useSelectedPropertyNullable } from "../../dashboard/property-slice/usePropertyStateSelectors";
import { Button, InputBox, Label, HorizontalSpacer, TextArea, VerticalSpacer } from "../atoms/FormFields";
import Select, { SingleValue } from "react-select"
import { Address, UserCurrency } from "../../dashboard/property-slice/propertySlice";
import { IoMdClose } from "react-icons/io";
import { NumberInput } from "../atoms/NumberInput";
import { TextInput } from "../atoms/TextInput";
import Editor from "../atoms/editor/Editor";
import { ImageUploader } from "./ImageUploader";
import { useAllImages } from "../../dashboard/images-slice/useImageStateSelectors";
import { useImageStateDispatch } from "../../dashboard/images-slice/useImageStateDispatch";

interface EditPropertyFormProps {
    handleClose: () => void
}

const countries = [
    { value: 'Germany', label: 'Germany' },
    { value: 'Spain', label: 'Spain' },
    { value: 'United Kingdom', label: 'United Kingdom' },
    { value: 'France', label: 'France' },
    { value: 'Poland', label: 'Poland' },
    { value: 'Italy', label: 'Italy' },
    { value: 'Ireland', label: 'Ireland' },
    { value: 'Austria', label: 'Austria' },
    { value: 'Croatia', label: 'Croatia' },
]

const bedrooms = [
    { value: "1", label: '1' },
    { value: "2", label: '2' },
    { value: "3", label: '3' },
    { value: "4", label: '4' },
    { value: "5", label: '5' },
    { value: "6", label: '6' },
    { value: "7", label: '7' },
    { value: "8", label: '8' },
    { value: "9", label: '9' },
    { value: "10", label: '10' },
]

const bathrooms = [
    { value: "1", label: '1' },
    { value: "2", label: '2' },
    { value: "3", label: '3' },
    { value: "4", label: '4' },
    { value: "5", label: '5' },
    { value: "6", label: '6' },
    { value: "7", label: '7' },
    { value: "8", label: '8' },
    { value: "9", label: '9' },
    { value: "10", label: '10' },

]

const currencies = [
    { value: "£", label: '£' },
    { value: "€", label: '€' },
    { value: "zł", label: 'zł' },
    { value: "$", label: '$' }

]

export const foo = {
    container: (base: any) => ({
        ...base,
        width: "310px"
    })
}
export const foo2 = {
    container: (base: any) => ({
        ...base,
        width: "140px"
    })
}

export const foo3 = {
    control: (base: any, state: any) => ({
        ...base,
        width: "75px",
        borderRadius: "0px",
        borderBottomLeftRadius: "5px",
        borderTopLeftRadius: "5px",
        border: state.isFocused && "none"
    })
}
export const PropertyDetailsForm: React.FC<EditPropertyFormProps> = ({ handleClose }) => {
    const { upsertProperty } = usePropertyStateDispatch();
    const { deleteAllImages } = useImageStateDispatch();

    const defaultAddress: Address = {
        houseNumber: "",
        line2: "",
        town: "",
        country: "",
        postcode: ""
    }


    const selectedProperty = useSelectedPropertyNullable()
    const images = useAllImages();
    const [address, setAddress] = useState<Address>(selectedProperty?.address ?? defaultAddress)
    const [noOfbathrooms, setNoOfBathrooms] = useState(selectedProperty?.numberOfBathrooms)
    const [noOfbedrooms, setNoOfBedrooms] = useState(selectedProperty?.numberOfBedrooms)
    const [price, setPrice] = useState(selectedProperty?.price)
    const [propertyType, setPropertyType] = useState(selectedProperty?.propertyType)
    const [description, setDescription] = useState(selectedProperty?.description ?? "")
    const [currency, setCurrency] = useState<UserCurrency>(selectedProperty?.userCurrency ?? "£")

    const handleFormClose = () => {
        deleteAllImages();
        handleClose();
    }

    const handleSubmit = () => {
        upsertProperty({
            id: selectedProperty?.id ?? Date.now(),
            address: address,
            propertyType: propertyType ?? "",
            numberOfBedrooms: noOfbedrooms ?? 0,
            numberOfBathrooms: noOfbathrooms ?? 0,
            description: description ?? "",
            price: price ?? "",
            userCurrency: currency,
            dateAdded: selectedProperty?.dateAdded ?? Date.now(),
            images: images
        })
        deleteAllImages();
        handleClose();


    }

    const handleDescriptionChange = (val: string): void => {
        setDescription(val)
    }

    const handlePropertyType = (e: React.ChangeEvent<HTMLInputElement>): void => {
        setPropertyType(e.target.value)
    }

    const handleSelectValueChanging = (value: string | number | null): { value: string, label: string } => {
        if (typeof value === "number") {
            return { value: value.toString() ?? "", label: value.toString() ?? "" }

        }
        return { value: value ?? "", label: value ?? "" }
    }

    const handleInputAddressStateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        console.log()
        const value = e.target.value;
        const name = e.target.name;
        setAddress((prevState) => ({
            ...prevState,
            [name]: value
        })
        )
    }

    const handleSelectAddressStateChange = (value: SingleValue<string>, name: string) => {
        setAddress((prevState) => ({
            ...prevState,
            [name]: value
        })
        )
    }

    const mapCurrency = (suppliedCurrency: string | undefined): UserCurrency => {
        if (!suppliedCurrency) {
            return '£'
        }
        switch (suppliedCurrency) {
            case "£":
                return '£'
            case "$":
                return '$'
            case "zł":
                return 'zł'
            case "€":
                return '€'
            default:
                return '£'
        }
    }

    const onPriceValueChange = (value: string) => {
        setPrice(value)
    }

    return (
        <div style={{
            borderRadius: "15px",
            width: "900px",
            height: "100%",
            backgroundColor: "white",
            position: "absolute",
            top: "0px",
            right: "calc(50% - 450px)",
            zIndex: "5",
            boxShadow: "2px 4px 15px grey",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            alignContent: "center",
            justifyItems: "center",
            overflowY: "scroll",
            overflowX: "hidden"
        }}>

            <div style={{ display: "flex", height: "auto", width: "90%", position: "absolute", top: "0px", paddingTop: "25px", paddingBottom: "70px", flexDirection: "column", alignItems: "center", justifyContent: "center" }}>
                <IoMdClose size={30} style={{ alignSelf: "end" }} onClick={() => handleFormClose()} />
                <p style={{ fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "start" }}>Property Details</p>

                <Label style={{ alignSelf: "start" }}>Postcode</Label>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <InputBox name="postcode" style={{ width: "180px", height: "35px", borderTopRightRadius: "0px", borderBottomRightRadius: "0px", borderRight: "none" }} value={address?.postcode} onChange={(e) => handleInputAddressStateChange(e)}></InputBox>
                    <Button style={{ margin: "0px", height: "35px", width: "130px", borderTopLeftRadius: "0px", borderBottomLeftRadius: "0px", fontSize: "14px" }}>Find Address</Button>
                </span>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Flat / House Number</Label>
                        <TextInput name="houseNumber" value={address.houseNumber} onValueChange={handleInputAddressStateChange}></TextInput>
                    </div>
                </span>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Address Line 2</Label>
                        <TextInput name="line2" value={address.line2} onValueChange={handleInputAddressStateChange}></TextInput>
                    </div>
                </span>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Town</Label>
                        <TextInput name="town" value={address.town} onValueChange={handleInputAddressStateChange}></TextInput>
                    </div>
                    <HorizontalSpacer />
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Property Type</Label>
                        <TextInput name="propertyType" value={propertyType ?? ""} onValueChange={handlePropertyType}></TextInput>
                    </div>
                </span>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Country</Label>
                        <Select name="country" styles={foo} isClearable options={countries} value={handleSelectValueChanging(address.country)} onChange={(e) => handleSelectAddressStateChange(e?.value ?? "", "country")}></Select>
                    </div>
                    <HorizontalSpacer />
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Bedrooms</Label>
                        <Select styles={foo2} options={bedrooms} value={handleSelectValueChanging(noOfbedrooms ?? "")} onChange={(val) => setNoOfBedrooms(Number(val?.value) ?? 0)}></Select>
                    </div>
                    <HorizontalSpacer />
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Bathrooms</Label>
                        <Select styles={foo2} options={bathrooms} value={handleSelectValueChanging(noOfbathrooms ?? "")} onChange={(val) => setNoOfBathrooms(Number(val?.value))}></Select>
                    </div>
                </span>

                <VerticalSpacer />

                <p style={{ fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "start" }}>Property Description</p>
                <Label style={{ alignSelf: "start" }}>Description</Label>
                <Editor value={description} onValueChange={handleDescriptionChange} />

                <p style={{ fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "start" }}>Sale Information</p>
                <Label style={{ alignSelf: "start" }}>Price</Label>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <Select menuPlacement="auto" components={{ IndicatorSeparator: () => null }} value={handleSelectValueChanging(currency ?? "£")} isSearchable={false} styles={foo3} options={currencies} onChange={(val) => setCurrency(mapCurrency(val?.value))}></Select>
                    <NumberInput value={price ?? ""} onValueChange={onPriceValueChange} />
                </span>

                <VerticalSpacer />

                <p style={{ fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "start" }}>Images & Videos</p>
                <ImageUploader cleanImages={selectedProperty?.images ?? null} />
                <Button onClick={() => handleSubmit()}>Submit</Button>
            </div>
        </div>
    )
}
