import React from "react";
import { Box } from "../atoms/Box";
import { RxCross1 } from "react-icons/rx";
import styled from "styled-components";

export interface SearchBarProps {
    value: string;
    onValueChange: (val: string) => void;
}

const Input = styled.input`
  font-family: Roboto;
  color: grey;
  padding-left: 10px;
  box-sizing: border-box;
  width: 100%;
  border: 1px solid grey;
  border-radius: 7px;
  height: 100%;
  outline: none;
  &:focus {
    outline: 2px solid #B7ECFD;
  }
  @media screen and (max-width: 800px) {
    width: 100%;
  }
`
const IconContainer = styled(Box)<{hidden: boolean}>`
  ${(props) => props.hidden ? "visibility: hidden;" : ""}
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  &:hover {
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0.05);
  }
`
export const SearchBar: React.FC<SearchBarProps> = ({
    value,
    onValueChange
}) => {
    return (
        <Box width='100%' height='45px'>
            <Input 
              value={value} 
              placeholder='e.g. Spain, Athens ' 
              onChange={(e) => onValueChange(e.currentTarget.value)}
              />
              <IconContainer 
                hidden={value.length == 0} 
                onClick={() => onValueChange("")} 
                contentJustification='center' 
                contentAlignment='center' 
                position='absolute'
                right='0px'
                width='45px'
                height='45px'>
                < RxCross1 size='20' color='grey'/>
              </IconContainer>
        </Box> 
    )
}