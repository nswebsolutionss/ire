import styled, { css } from "styled-components";
import { SizeInterface } from "./breakpoints";

export interface ResponseProps {
    breakpoints?: [{
        size?: string,
        styles?: BoxProps;
    }]
}

export interface BoxProps {
    children?: React.ReactNode;
    backgroundColor?: string;
    color?: string;
    width?: string;
    minWidth?: string;
    height?: string;
    minHeight?: string;

    disabled?: boolean;
    borderRadius?: string;
    
    margin?: string;
    marginT?: string;
    marginB?: string;
    marginL?: string;
    marginR?: string;

    padding?: string;
    paddingT?: string;
    paddingB?: string;
    paddingL?: string;
    paddingR?: string;

    border?: string;
    borderT?: string;
    borderB?: string;
    borderL?: string;
    borderR?: string;

    contentDirection?: "row" | "column";
    contentAlignment?: "start" | "center" | "end" | "space-between";
    contentJustification?: "start" | "center" | "end" | "space-between" | "space-around" | "space-evenly";
    selfAlignment?: "start" | "center" | "end";
    textAlign?: "left" | "center";
    displayType?: "flex" | "fixed" | "none"

    position?: string;
    top?: string;
    left?: string;
    bottom?: string;
    right?: string;

    fontWeight?: string;

    hoverable?: boolean

}

export const Box = styled.div<BoxProps & ResponseProps>`
    position: ${(props) => props.position ?? "relative"};
    top: ${(props) => props.top ?? ""};
    bottom: ${(props) => props.bottom ?? ""};
    left: ${(props) => props.left ?? ""};
    right: ${(props) => props.right ?? ""};

    background-color: ${(props) => props.backgroundColor ? props.backgroundColor : "transparent"};
    width: ${(props) => props.width ? props.width : "initial"};
    min-width: ${(props) => props.minWidth ? props.minWidth : "initial"};
    height: ${(props) => props.height ? props.height : "initial"};
    min-height: ${(props) => props.minHeight ? props.minHeight : "initial"};
    
    border-radius: ${(props) => props.borderRadius ? props.borderRadius : "0px"};

    ${(props) => props.margin ? "margin: " + props.margin : ""};
    ${(props) => props.marginR ? "margin-right: " + props.marginR : ""};
    ${(props) => props.marginL ? "margin-left: " + props.marginL : ""};
    ${(props) => props.marginB ? "margin-bottom: " + props.marginB : ""};
    ${(props) => props.marginT ? "margin-top: " + props.marginT : ""};

    ${(props) => props.padding ? "padding: " + props.padding : ""};
    ${(props) => props.paddingR ? "padding-right: " + props.paddingR : ""};
    ${(props) => props.paddingL ? "padding-left: " + props.paddingL : ""};
    ${(props) => props.paddingB ? "padding-bottom: " + props.paddingB : ""};
    ${(props) => props.paddingT ? "padding-top: " + props.paddingT : ""};

    ${(props) => props.border ? "border: " + props.border : ""};
    ${(props) => props.borderR ? "border-right: " + props.borderR : ""};
    ${(props) => props.borderL ? "border-left: " + props.borderL : ""};
    ${(props) => props.borderB ? "border-bottom: " + props.borderB : ""};
    ${(props) => props.borderT ? "border-top: " + props.borderT : ""};

    ${(props) => props.color ? "color: " + props.color : ""};
    ${(props) => props.fontWeight ? "font-weight: " + props.fontWeight : ""};

    display: ${(props) => props.displayType ? props.displayType : "flex"};

    flex-direction: ${(props) => props.contentDirection ?? "column"};
    align-items: ${(props) => props.contentAlignment ?? "initial"};
    justify-content: ${(props) => props.contentJustification ?? "start"};
    text-align: ${(props) => props.textAlign ?? ""};
    align-self: ${(props) => props.selfAlignment ?? ""};

    box-sizing: border-box;

    ${(props) => props.hoverable ? css`
            &:hover {
                cursor: pointer;
                opacity: 0.7;
            }
        ` : 
        ''
    }

    ${(props) => props.breakpoints?.map((breakpoint) => {
        if(breakpoint.size && breakpoint.styles) {
            return css`
            @media(${breakpoint.size}) {
                position: ${breakpoint.styles.position ?? "relative"};
                top: ${breakpoint.styles.top ?? ""};
                bottom: ${breakpoint.styles.bottom ?? ""};
                left: ${breakpoint.styles.left ?? ""};
                right: ${breakpoint.styles.right ?? ""};

                background-color: ${breakpoint.styles.backgroundColor ? breakpoint.styles.backgroundColor : "transparent"};
                width: ${breakpoint.styles.width ? breakpoint.styles.width : "initial"};
                min-width: ${breakpoint.styles.minWidth ? breakpoint.styles.minWidth : "initial"};
                height: ${breakpoint.styles.height ? breakpoint.styles.height : "initial"};
                min-height: ${breakpoint.styles.minHeight ? breakpoint.styles.minHeight : "initial"};
                
                border-radius: ${breakpoint.styles.borderRadius ? breakpoint.styles.borderRadius : "0px"};

                ${breakpoint.styles.margin ? "margin: " + breakpoint.styles.margin : ""};
                ${breakpoint.styles.marginR ? "margin-right: " + breakpoint.styles.marginR : ""};
                ${breakpoint.styles.marginL ? "margin-left: " + breakpoint.styles.marginL : ""};
                ${breakpoint.styles.marginB ? "margin-bottom: " + breakpoint.styles.marginB : ""};
                ${breakpoint.styles.marginT ? "margin-top: " + breakpoint.styles.marginT : ""};

                ${breakpoint.styles.padding ? "padding: " + breakpoint.styles.padding : ""};
                ${breakpoint.styles.paddingR ? "padding-right: " + breakpoint.styles.paddingR : ""};
                ${breakpoint.styles.paddingL ? "padding-left: " + breakpoint.styles.paddingL : ""};
                ${breakpoint.styles.paddingB ? "padding-bottom: " + breakpoint.styles.paddingB : ""};
                ${breakpoint.styles.paddingT ? "padding-top: " + breakpoint.styles.paddingT : ""};

                ${breakpoint.styles.border ? "border: " + breakpoint.styles.border : ""};
                ${breakpoint.styles.borderR ? "border-right: " + breakpoint.styles.borderR : ""};
                ${breakpoint.styles.borderL ? "border-left: " + breakpoint.styles.borderL : ""};
                ${breakpoint.styles.borderB ? "border-bottom: " + breakpoint.styles.borderB : ""};
                ${breakpoint.styles.borderT ? "border-top: " + breakpoint.styles.borderT : ""};

                ${breakpoint.styles.color ? "color: " + breakpoint.styles.color : ""};
                ${breakpoint.styles.fontWeight ? "font-weight: " + breakpoint.styles.fontWeight : ""};

                display: flex;

                flex-direction: ${breakpoint.styles.contentDirection ?? "column"};
                align-items: ${breakpoint.styles.contentAlignment ?? "initial"};
                justify-content: ${breakpoint.styles.contentJustification ?? "start"};
                text-align: ${breakpoint.styles.textAlign ?? ""};
                align-self: ${breakpoint.styles.selfAlignment ?? ""};

                box-sizing: border-box;
            }
        `
        }

    })}





`