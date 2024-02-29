import { LiaBedSolid } from "react-icons/lia"
import { PiShower } from "react-icons/pi"
import image from "../../../1732-midvale-ave-1.jpg";
import styled from "styled-components"
import { CiEdit } from "react-icons/ci";
import { IconElement } from "../DashboardNavBar";

export interface PropertyProps {
    propertyId: number;
    location: string;
    propertyType: string;
    numberOfBedrooms: number;
    numberOfBathrooms: number;
    description: string;
    price: string;
}

const Container = styled.div`
    width: 1000px;
    height: 300px;
    position: relative;
    background-color: white;
    box-shadow: 2px 3px 10px lightgrey;
    margin: 10px;
    box-sizing: border-box;
    display: flex;
    flex-direction;
    border-radius: 10px;
`

const InformationContainer = styled.div`
    padding-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    color: black;
    width: 50%;
    height: 100%;
`

const ImagesContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 50%;
    height: 100%;
`

export const Property: React.FC<PropertyProps> = ({ location, propertyType, numberOfBedrooms, numberOfBathrooms, description, price }) => {
    return (
        <Container>
            <IconElement style={{ position: "absolute", top: "20px", right: "20px", color: "grey" }}><CiEdit size={30} /></IconElement>
            <ImagesContainer>
                <div style={{ display: "flex", flexDirection: "column", height: "80%", width: "100%", borderBottom: "1px solid grey" }}>
                    <img src={image} style={{ height: "100%", width: "100%", objectFit: "cover", borderTopLeftRadius: "10px" }}></img>
                </div>
                <div style={{ paddingLeft: "25px", boxSizing: "border-box", width: "100%", height: "20%", backgroundColor: "#2044a8", borderBottomLeftRadius: "10px", display: "flex", justifyContent: "start", alignItems: "center" }}>
                    <p style={{ color: "white", fontSize: "30px", padding: "0px", margin: "0px", fontWeight: "300" }}>{price}</p>
                </div>
            </ImagesContainer>
            <InformationContainer>
                <p style={{ fontSize: "22px", fontWeight: "500", margin: "0px" }}>{location}</p>
                <div style={{ display: "flex", flexDirection: "row" }}>
                    <p style={{ fontSize: "18px", fontWeight: "300", margin: "0px" }}>{propertyType}</p>
                    <div style={{ display: "flex", flexDirection: "row", paddingLeft: "20px" }}>
                        <LiaBedSolid size={20} />
                        <p style={{ fontSize: "18px", fontWeight: "300", margin: "0px" }}>{numberOfBedrooms}</p>
                    </div>
                    <div style={{ display: "flex", flexDirection: "row", paddingLeft: "20px" }}>
                        <PiShower size={20} />
                        <p style={{ fontSize: "18px", fontWeight: "300", margin: "0px" }}>{numberOfBathrooms}</p>
                    </div>
                </div>
                <p style={{ fontSize: "14px", fontWeight: "200", margin: "0px", height: "160px", width: "95%", textAlign: "justify", textOverflow: "ellipsis", overflow: "hidden", whiteSpace: "no-wrap" }}>
                    {description}
                </p>
            </InformationContainer>
        </Container>
    )
} 