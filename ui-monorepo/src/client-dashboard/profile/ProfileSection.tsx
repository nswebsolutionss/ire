import { useState } from "react";
import { RxQuestionMarkCircled } from "react-icons/rx";
import { HorizontalSpacer, Label, Span, VerticalSpacer } from "../../components/atoms/FormFields";
import { TextInput } from "../../components/atoms/TextInput";
import Editor from "../../components/atoms/editor/Editor";
import { ImageUploader } from "../../components/molecules/ImageUploader";
import { OpeningTime } from "../../components/molecules/OpeningTime";
import { useUser } from "../user-slice/useUserStateSelectors";
import { S } from "vite/dist/node/types.d-AKzkD8vd";
import { useGetCompanyInformationQuery } from "../../redux/api/apiSlice";
import { useAuth } from "@clerk/clerk-react";

export interface ProfileDetails {
    company_desciption: string;
    comapany_name: string;
    email_address: string;
    telephone_number: string;
    website_url: string;
    facebook_url: string;
    instagram_url: string;
    youtube_url: string;
}

export const ProfileSection: React.FC = () => {
    const {orgId} = useAuth();
    const {data: profileDetailsApi, isLoading} = useGetCompanyInformationQuery(orgId ?? "")

    const user = useUser();

    const intialProfileDetails = {
        company_desciption: "",
        comapany_name: "",
        email_address: "",
        telephone_number: "",
        website_url: "",
        facebook_url: "",
        instagram_url: "",
        youtube_url: ""
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const name = e.target.name
        const value = e.target.value
        setProfileDetails((prev) => ({
            ...prev,
            [name]: value
        }))
    }

    const [profileDetails, setProfileDetails] = useState<ProfileDetails>(intialProfileDetails)

    return (
        <div style={{ width: "100%", padding: "25px", display: "flex", alignItems: "center", flexDirection: "column" }}>
            <div style={{ display: "flex", flexDirection: "column", width: "70%", alignItems: "center" }}>
                <p style={{ fontFamily: "Nunito Sans", fontSize: "35px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", margin: "0px" }}>Profile</p>
                <VerticalSpacer size={"5"} />
                <p style={{ paddingLeft: "1px", fontFamily: "Nunito Sans", fontSize: "18px", fontWeight: "400", color: "rgba(0, 0,0 ,0.3)", margin: "0px" }}>View and edit your company profile here</p>
                <VerticalSpacer />

                <div style={{ position: "relative", width: "100%", height: "auto", padding: "50px", borderRadius: "15px", boxShadow: "1px 2px 15px rgba(0, 0, 0, 0.2)" }}>
                    <p style={{ margin: "0px", fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "center" }}>Company Details</p>

                    <VerticalSpacer />
                    <Label style={{ alignSelf: "start", marginTop: "0px", marginBottom: "0px" }}>Description</Label>
                    <Editor value={user.profile.details.description} onValueChange={() => { }} />
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Company Name</Label>
                        <TextInput name="comapany_name" value={profileDetails.comapany_name} onValueChange={handleInputChange}></TextInput>
                    </div>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Email Address</Label>
                        <TextInput name="email_address" value={profileDetails.email_address} onValueChange={handleInputChange}></TextInput>
                    </div>
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Label style={{ alignSelf: "start" }}>Telephone Number</Label>
                        <TextInput name="telephone_number" value={profileDetails.telephone_number} onValueChange={handleInputChange}></TextInput>
                    </div>
                    <Span style={{ justifyContent: "start", display: "flex", flexWrap: "wrap" }}>
                        <div style={{ display: "flex", flexDirection: "column" }}>
                            <Label style={{ alignSelf: "start" }}>Website URL</Label>
                            <TextInput name="website_url" value={profileDetails.website_url} onValueChange={handleInputChange}></TextInput>
                        </div>
                        <HorizontalSpacer />
                        <div style={{ display: "flex", flexDirection: "column" }}>
                            <Label style={{ alignSelf: "start" }}>Facebook URL</Label>
                            <TextInput name="facebook_url" value={profileDetails.facebook_url} onValueChange={handleInputChange}></TextInput>
                        </div>
                        <HorizontalSpacer />
                        <div style={{ display: "flex", flexDirection: "column" }}>
                            <Label style={{ alignSelf: "start" }}>Instagram URL</Label>
                            <TextInput name="instagram_url" value={profileDetails.instagram_url} onValueChange={handleInputChange}></TextInput>
                        </div>
                    </Span>
                </div>
                <VerticalSpacer />
                <VerticalSpacer />

                <div style={{ position: "relative", width: "100%", height: "auto", padding: "50px", borderRadius: "15px", boxShadow: "1px 2px 15px rgba(0, 0, 0, 0.2)" }}>
                    <p style={{ margin: "0px", fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "center" }}>Images and Videos</p>

                    <VerticalSpacer />
                    <div style={{ display: "flex", flexDirection: "column" }}>
                        <Span>
                            <Label style={{ alignSelf: "start" }}>Youtube URL</Label>
                            <RxQuestionMarkCircled size={18} style={{ marginLeft: "10px" }} />
                        </Span>
                        <TextInput name="youtube_url" value={profileDetails.youtube_url} onValueChange={handleInputChange}></TextInput>

                    </div>
                    <VerticalSpacer />

                    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%" }}>
                        <ImageUploader cleanImages={user.profile.details.images} />

                    </div>

                </div>
                <VerticalSpacer />
                <VerticalSpacer />
                <div style={{ position: "relative", width: "100%", height: "auto", padding: "50px", borderRadius: "15px", boxShadow: "1px 2px 15px rgba(0, 0, 0, 0.2)" }}>
                    <p style={{ margin: "0px", fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "center" }}>Opening Times</p>

                    <VerticalSpacer />
                    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%" }}>
                        <OpeningTime />
                    </div>

                </div>

            </div>
        </div>
    )
}