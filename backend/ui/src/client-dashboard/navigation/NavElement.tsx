import React from "react";
import { IconType } from "react-icons";
import styled from "styled-components";
import { useIsCollapsed, useSelectedNavElement } from "../navigation-slice/useNavigationStateSelectors";
import { NavElement } from "../navigation-slice/navigationSlice";
import { useNavigationStateDispatch } from "../navigation-slice/useNavigationStateDispatch";

interface ElementProps {
    onClick?: () => void;
    label: string;
    icon?: IconType;
    navElementEnum: NavElement;
}

interface ContainerProps {
    active: boolean;
}
export const Container = styled.div<ContainerProps>`
    box-sizing: border-box;
    width: 100%;
    height: 50px;
    text-align: center;
    margin-top: 20px;
    padding-left: 25px;
    display: flex;
    justify-content: center;
    color: ${p => p.active ? "#2044a8" : "white"};
    background-color: ${p => p.active ? "white" : "#2044a8"};
    align-items: center;
    transition: 0.5s ease all;

    &:hover {
        background-color: white;
        color:  #2044a8;
        cursor: pointer;
    }

`



export const Element: React.FC<ElementProps> = ({ label, icon, navElementEnum, onClick}) => {
    const {setSelectedNavElement} = useNavigationStateDispatch();
    const isSelected = useSelectedNavElement() === navElementEnum;


    return (
        useIsCollapsed() === false ?
            <Container active={isSelected} onClick={() => {
                setSelectedNavElement(navElementEnum)
                if(onClick) {
                    onClick();
                }
                }}>
                <div style={{ display: "flex", flexDirection: "row", justifyContent: "start", alignItems: "center", width: "100%" }}>
                    {
                        icon !== undefined ? React.createElement(icon, { size: 25 }) : ""
                    }
                    <div style={{ height: "auto", width: "20px" }}></div>
                    {label}
                </div>
            </Container >
            : 
            <Container data-tooltip-id={label} data-tooltip-content={label} active={isSelected} onClick={() => {
                setSelectedNavElement(navElementEnum)
                if(onClick) {
                    onClick();
                }
                }}>
                <div style={{ display: "flex", flexDirection: "row", justifyContent: "start", alignItems: "center", width: "100%" }}>
                    {
                        icon !== undefined ? React.createElement(icon, { size: 25 }) : <></>
                    }
                </div>
            </Container >

    )
}