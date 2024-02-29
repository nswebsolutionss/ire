import styled from "styled-components";
import { CgProfile } from "react-icons/cg";
import { useState } from "react";
import { Element } from "./NavElement";
import { LuLayoutDashboard } from "react-icons/lu";
import { BsHouse } from "react-icons/bs";
import { IoPeopleSharp } from "react-icons/io5";
import { IoMdArrowDropleft } from "react-icons/io";
import { IoMdArrowDropright } from "react-icons/io";
import { IoPersonOutline } from "react-icons/io5";
import { BsQuestionLg } from "react-icons/bs";
import { IoSettingsOutline } from "react-icons/io5";
import { BiLogOut } from "react-icons/bi";
import { NavElement } from "../../dashboard/propertySlice";








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
    const [selectedElementId, setSelectedElementId] = useState<number | null>(null);
    const [collapsed, setCollapsed] = useState(true);


    return (
        <div>
            <Container collapsed={collapsed}>

                {collapsed === false ?
                    <IconElement onClick={() => setCollapsed(true)}>
                        <IoMdArrowDropleft size={20}/>
                    </IconElement> :
                    <IconElement onClick={() => setCollapsed(false)}>
                        <IoMdArrowDropright size={20}/>
                    </IconElement>
                }

                <Element navElementEnum={NavElement.Dashboard}collapsed={collapsed} elementId={1} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Dashboard" icon={LuLayoutDashboard}></Element>
                <Element navElementEnum={NavElement.Properties}collapsed={collapsed} elementId={2} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Properties" icon={BsHouse}></Element>
                <Element navElementEnum={NavElement.Membership}collapsed={collapsed} elementId={3} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Membership" icon={IoPeopleSharp}></Element>
                <Element navElementEnum={NavElement.Profile}collapsed={collapsed} elementId={4} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Profile" icon={IoPersonOutline}></Element>
                <div style={{ width: "auto", height: "25px" }}></div>
                <div style={{ width: "auto", height: "25px" }}></div>
                <div style={{ width: "auto", height: "25px" }}></div>
                <div style={{ width: "auto", height: "25px" }}></div>
                <Element navElementEnum={NavElement.Settings}collapsed={collapsed} elementId={5} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Settings" icon={IoSettingsOutline}></Element>
                <Element navElementEnum={NavElement.Help}collapsed={collapsed} elementId={6} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Help" icon={BsQuestionLg}></Element>
                <Element navElementEnum={NavElement.SignOut}collapsed={collapsed} elementId={7} selectedElementId={selectedElementId} setSelectedElementId={setSelectedElementId} label="Sign Out" icon={BiLogOut}></Element>
            </Container>
        </div>
    )
}