import styled from "styled-components";
import { CgProfile } from "react-icons/cg";
import { useState } from "react";
import { IconType } from "react-icons";
import React from "react";
import { Tooltip } from "react-tooltip";
import { useDispatch } from "react-redux";
import { NavElement, setSelectedNavElement } from "../../dashboard/propertySlice";

interface ElementProps {
    label: string;
    icon?: IconType;
    elementId: number;
    selectedElementId: number | null;
    setSelectedElementId: React.Dispatch<React.SetStateAction<null | number>>;
    collapsed: boolean;
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



export const Element: React.FC<ElementProps> = ({ label, icon, elementId, selectedElementId, setSelectedElementId, collapsed, navElementEnum}) => {
    const dispatch = useDispatch();
    const calculateSelected = (): void => {
        if (elementId === selectedElementId) {
            setSelectedElementId(null)
            return;
        }
        setSelectedElementId(elementId);
        dispatch(
            setSelectedNavElement(navElementEnum)
        )
    }
    return (
        collapsed === false ?
            <Container active={elementId === selectedElementId} onClick={() => calculateSelected()}>
                <div style={{ display: "flex", flexDirection: "row", justifyContent: "start", alignItems: "center", width: "100%" }}>
                    {
                        icon !== undefined ? React.createElement(icon, { size: 25 }) : ""
                    }
                    <div style={{ height: "auto", width: "20px" }}></div>
                    {label}
                </div>
            </Container >
            : 
            <Container data-tooltip-id={label} data-tooltip-content={label} active={elementId === selectedElementId} onClick={() => calculateSelected()}>
                <div style={{ display: "flex", flexDirection: "row", justifyContent: "start", alignItems: "center", width: "100%" }}>
                    {
                        icon !== undefined ? React.createElement(icon, { size: 25 }) : <></>
                    }
                </div>
            </Container >

    )
}