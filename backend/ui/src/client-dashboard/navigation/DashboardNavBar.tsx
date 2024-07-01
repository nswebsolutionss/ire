import { BiLogOut } from "react-icons/bi";
import { BsHouse, BsQuestionLg } from "react-icons/bs";
import { IoMdArrowDropleft, IoMdArrowDropright } from "react-icons/io";
import { IoPeopleSharp, IoPersonOutline, IoSettingsOutline } from "react-icons/io5";
import { LuLayoutDashboard } from "react-icons/lu";
import styled from "styled-components";
import { useIsCollapsed } from "../navigation-slice/useNavigationStateSelectors";
import { NavElement } from "../navigation-slice/navigationSlice";
import { useNavigationStateDispatch } from "../navigation-slice/useNavigationStateDispatch";
import { Element } from "./NavElement";
import { SignedIn, UserButton } from "@clerk/clerk-react";


interface NavBarProps {

}

interface ContainerProps {
    collapsed: boolean;
}

export const Container = styled.div<ContainerProps>`
    box-sizing: border-box;
    width: ${p => p.collapsed ? "75px" : "210px"};
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    position: relative;
    color: white;
    background-color: #2044a8;
    transition: 0.5s ease all;

`

export const IconElement = styled.div`
    width: auto;
    height: auto;
    position: absolute;
    top: 25px;
    right: 25px;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: 0.5s ease all;
    &:hover {
        transition: 0.5s ease all;
        cursor: pointer;
        transform: scale(1.2);
    }
    &:active {
        transform: scale(1);
        transition: 0.1s ease all;
    }

`

export const DashboardNavBar: React.FC<NavBarProps> = () => {
    const { setIsCollapsed } = useNavigationStateDispatch();

    return (
        <div>
            <Container collapsed={useIsCollapsed()}>

                {useIsCollapsed() === false ?
                    <IconElement onClick={() => setIsCollapsed(true)}>
                        <IoMdArrowDropleft size={20} />
                    </IconElement> :
                    <IconElement onClick={() => setIsCollapsed(false)}>
                        <IoMdArrowDropright size={20} />
                    </IconElement>
                }
                <div style={{ display: "flex", flexDirection: "row", justifyContent: "center", alignItems: "center", width: "100%" }}>
                    <UserButton afterSignOutUrl='/' />
                </div>
                <Element navElementEnum={NavElement.Dashboard} label="Dashboard" icon={LuLayoutDashboard}></Element>
                <Element navElementEnum={NavElement.Properties} label="Properties" icon={BsHouse}></Element>
                <Element navElementEnum={NavElement.Membership} label="Membership" icon={IoPeopleSharp}></Element>
                <Element navElementEnum={NavElement.Profile} label="Profile" icon={IoPersonOutline}></Element>
                <div style={{ width: "auto", height: "25px" }}></div>
                <div style={{ width: "auto", height: "25px" }}></div>
                <div style={{ width: "auto", height: "25px" }}></div>
                <div style={{ width: "auto", height: "25px" }}></div>
                <Element navElementEnum={NavElement.Settings} label="Settings" icon={IoSettingsOutline}></Element>
                <Element navElementEnum={NavElement.Help} label="Help" icon={BsQuestionLg}></Element>
                <Element navElementEnum={NavElement.SignOut} label="Sign Out" icon={BiLogOut}></Element>
            </Container>
        </div>
    )
}