import React from "react";
import { InputBox } from "../form-fields";

export interface NumberInputProps {
    value: string;
    onValueChange: (value: string) => void;
}


export const NumberInput: React.FC<NumberInputProps> = ({onValueChange, value}) => {
    const formatPrice = (suppliedPrice: string): string => {
        const regex = /[^\d.]/g;
        return suppliedPrice.replace(regex, "");
    }

    return (
        <InputBox inputMode="numeric" style={{ paddingLeft: "10px", width: "180px", height: "38px", borderTopLeftRadius: "0px", borderBottomLeftRadius: "0px", borderLeft: "none" }} value={value} onChange={(e) => onValueChange(formatPrice(e.target.value))}></InputBox>
    )
}