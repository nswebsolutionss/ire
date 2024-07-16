import { useMemo, useState } from "react";
import { IoMdClose } from "react-icons/io";
import Select from "react-select";
import { useImageStateDispatch } from "../../client-dashboard/images-slice/useImageStateDispatch";
import { useAllImages } from "../../client-dashboard/images-slice/useImageStateSelectors";
import { useSelectedPropertyId } from "../../client-dashboard/property-slice/usePropertyStateSelectors";
// import { useGetPropertyQuery, useInsertPropertyMutation, useUpdatePropertyMutation } from "../../redux/api/apiSlice";
import { Button, HorizontalSpacer, InputBox, Label, VerticalSpacer } from "../atoms/FormFields";
import { NumberInput } from "../atoms/NumberInput";
import { TextInput } from "../atoms/TextInput";
import Editor from "../atoms/editor/Editor";
import { ImageUploader } from "./ImageUploader";

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
    const { deleteAllImages } = useImageStateDispatch();
    const [property, setProperty] = useState<{number_of_bathrooms: number, number_of_bedrooms: number, price: string, property_type: string, property_description: string}>();
    const selectedPropertyId = useSelectedPropertyId();
    const isLoading = false

    // const [insertProperty] = useInsertPropertyMutation()
    // const [updateProperty] = useUpdatePropertyMutation()
    const [skip, setSkip] = useState<boolean>(true)
    // const {data: property, isLoading} = useGetPropertyQuery(selectedPropertyId ?? 0)

    const images = useAllImages();
    //const [address, setAddress] = useState<Address>(selectedProperty?.address ?? defaultAddress)
    const [noOfbathrooms, setNoOfBathrooms] = useState(property?.number_of_bathrooms)
    const [noOfbedrooms, setNoOfBedrooms] = useState(property?.number_of_bedrooms)
    const [price, setPrice] = useState(property?.price)
    const [propertyType, setPropertyType] = useState(property?.property_type)
    const [description, setDescription] = useState(property?.property_description ?? "")
//    const [currency, setCurrency] = useState<UserCurrency>(selectedProperty?.userCurrency ?? "£")

    useMemo(() => {
        if(!isLoading) {
            setSkip(false)
            console.log(selectedPropertyId)
            console.log(property?.property_type)
            console.log("NUMBER OF BATHROOMS    " + property?.number_of_bathrooms)
            setNoOfBathrooms(property?.number_of_bathrooms)
            setNoOfBedrooms(property?.number_of_bedrooms)
            setPrice(property?.price)
            setPropertyType(property?.property_type)
            setDescription(property?.property_description ?? "")
        }
    }, [isLoading])
    const handleFormClose = () => {
        deleteAllImages();
        handleClose();
    }

    const handleSubmit = async () => {
        try {
            if(selectedPropertyId) {
                console.log("we got here")
                // await updateProperty({
                //     listing_id: selectedPropertyId ?? 0,
                //     property_type: propertyType ?? "",
                //     number_of_bedrooms: noOfbedrooms ?? 0,
                //     number_of_bathrooms: noOfbathrooms ?? 0,
                //     property_description: description ?? "",
                //     price: price ?? "",
                //     date_added: property?.date_added ?? 0,
                // })
            }
            else {
                // await insertProperty({
                //     listing_id:  Date.now(),
                //     property_type: propertyType ?? "",
                //     number_of_bedrooms: noOfbedrooms ?? 0,
                //     number_of_bathrooms: noOfbathrooms ?? 0,
                //     property_description: description ?? "",
                //     price: price ?? "",
                //     date_added: Date.now(),
                // })
            }
            
        } catch(err) {
            console.error(err)
        }
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
                    <InputBox name="postcode" style={{ width: "180px", height: "35px", borderTopRightRadius: "0px", borderBottomRightRadius: "0px", borderRight: "none" }} value={""} onChange={(e) => handleInputAddressStateChange(e)}></InputBox>
                    <Button style={{ margin: "0px", height: "35px", width: "130px", borderTopLeftRadius: "0px", borderBottomLeftRadius: "0px", fontSize: "14px" }}>Find Address</Button>
                </span>
                
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Flat / House Number</Label>
                        <TextInput name="houseNumber" value={""} onValueChange={handleInputAddressStateChange}></TextInput>
                    </div>
                </span>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Address Line 2</Label>
                        <TextInput name="line2" value={""} onValueChange={handleInputAddressStateChange}></TextInput>
                    </div>
                </span>
                <span style={{ alignSelf: "start", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Town</Label>
                        <TextInput name="town" value={""} onValueChange={handleInputAddressStateChange}></TextInput>
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
                        <Select name="country" styles={foo} isClearable options={countries} value={handleSelectValueChanging("")}></Select>
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
                    <Select menuPlacement="auto" components={{ IndicatorSeparator: () => null }} value={""} isSearchable={false} styles={foo3} ></Select>
                    <NumberInput value={price ?? ""} onValueChange={onPriceValueChange} />
                </span>

                <VerticalSpacer />

                <p style={{ fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "start" }}>Images & Videos</p>
                <ImageUploader cleanImages={null} />
                <Button onClick={() => handleSubmit()}>Submit</Button>
            </div>
        </div>
    )
}
