import styled, { css } from "styled-components";

export interface TextProps {
    size?: string;
    weight?: string;
    alignment?: "left" | "center" | "right"
    color?: string;
    alignSelf?: string;

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

    hoverable?: boolean;
}

export const Text = styled.p<TextProps>`
    font-size: ${(props) => props.size ? props.size : "inherit"};
    font-weight: ${(props) => props.weight ? props.weight : "500"};
    text-alignment: ${(props) => props.alignment ? props.alignment : "intial"};
    color: ${(props) => props.color ? props.color : "inherit"};
    align-self: ${(props) => props.alignSelf ? props.alignSelf : "initial"};

    font-family: Roboto;

    margin: ${(props) => props.margin ? props.margin : "0px"};
    marginT: ${(props) => props.marginT ? props.marginT : "0px"};
    marginB: ${(props) => props.marginB ? props.marginB : "0px"};
    marginL: ${(props) => props.marginL ? props.marginL : "0px"};
    marginR: ${(props) => props.marginR ? props.marginR : "0px"};

    padding: ${(props) => props.padding ? props.padding : "0px"};
    paddingT: ${(props) => props.paddingT ? props.paddingT : "0px"};
    paddingB: ${(props) => props.paddingB ? props.paddingB : "0px"};
    paddingL: ${(props) => props.paddingL ? props.paddingL : "0px"};
    paddingR: ${(props) => props.paddingR ? props.paddingR : "0px"};

    ${(props) => props.hoverable ? css`
            &:hover {
                text-decoration: underline;
                cursor: pointer;
                text-underline-offset: 5px;
            }
        ` : ''}



`