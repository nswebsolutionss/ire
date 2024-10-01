import { ChangeEvent } from "react";
import styled from "styled-components";

export interface TextInputProps extends React.InputHTMLAttributes<HTMLInputElement>  {
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

    position?: string;
    top?: string;
    left?: string;
    bottom?: string;
    right?: string;

    fontWeight?: string;
    onValueChange?: (value: string) => void;

}

export const TextInputNew = styled.input<TextInputProps>`
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

    display: flex;

    flex-direction: ${(props) => props.contentDirection ?? "column"};
    align-items: ${(props) => props.contentAlignment ?? "initial"};
    justify-content: ${(props) => props.contentJustification ?? "start"};
    text-align: ${(props) => props.textAlign ?? ""};
    align-self: ${(props) => props.selfAlignment ?? ""};

    box-sizing: border-box;
`

export const TextInputFoo: React.FC<TextInputProps> = ({
    backgroundColor,
    color,
    width,
    minWidth,
    height,
    minHeight,
    disabled, 
    borderRadius,
    margin,
    marginT,
    marginB,
    marginL,
    marginR,
    padding,
    paddingT,
    paddingB,
    paddingL,
    paddingR,
    border,
    borderT,
    borderB,
    borderL,
    borderR,
    contentDirection,
    contentAlignment,
    contentJustification,
    selfAlignment,
    textAlign,
    position,
    top,
    left,
    bottom,
    right,
    fontWeight,
    onValueChange
}) => {
    return (
        <TextInputNew
            backgroundColor={backgroundColor}
            color={color}
            width={width}
            minWidth={minWidth}
            height={height}
            minHeight={minHeight}
            disabled={disabled} 
            borderRadius={borderRadius}
            margin={margin}
            marginT={marginT}
            marginB={marginB}
            marginL={marginL}
            marginR={marginR}
            padding={padding}
            paddingT={paddingT}
            paddingB={paddingB}
            paddingL={paddingL}
            paddingR={paddingR}
            border={border}
            borderT={borderT}
            borderB={borderB}
            borderL={borderL}
            borderR={borderR}
            contentDirection={contentDirection}
            contentAlignment={contentAlignment}
            contentJustification={contentJustification}
            selfAlignment={selfAlignment}
            textAlign={textAlign}
            position={position}
            top={top}
            left={left}
            bottom={bottom}
            right={right}
            fontWeight={fontWeight}
            onChange={onValueChange ? (e) => onValueChange(e.currentTarget.value) : () => {}}
        >
        </TextInputNew>
    )
}


