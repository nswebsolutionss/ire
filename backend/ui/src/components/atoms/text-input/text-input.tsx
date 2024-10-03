
import styled from "styled-components";
import { InputBox } from "../form-fields";

export interface TextInputProps extends React.InputHTMLAttributes<HTMLInputElement>  {
    value: string;
    onValueChange: (value: React.ChangeEvent<HTMLInputElement>) => void;
    name: string;
    placeholder?: string;
    width?: string;
}

const TextInputStyled = styled(InputBox)`
    padding-left: 10px;
    width: 310px;
    height: 35px;
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.2);
    font-family: inherit;
`

export const TextInput: React.FC<TextInputProps> = ({onValueChange, value, name, placeholder}) => {
    return (
        <TextInputStyled placeholder={placeholder} name={name} inputMode="text" value={value} onChange={(e) => onValueChange(e)}></TextInputStyled>
    )
}