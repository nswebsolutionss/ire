import React from "react"
import { Box } from "../box"
import { Label } from "../label"
import styled from "styled-components"
import { SlArrowDown } from "react-icons/sl"

export const ContainerDropDown = styled(Box)<{isActive: boolean}>`
  border-radius: 5px;
  background-color: white;
  &:hover {
    cursor: pointer;
  }
  ${(props) => props.isActive ? 'outline: 2px solid #B7ECFD;' : ''}

`

const DropDownIconContainer = styled(Box)<{active: boolean}>`
${(props) => props.active ? 'transform: rotate(180deg);' : ''}
`
export interface DropDownBoxProps {
    isActive: boolean,
    setIsActive: (val: boolean) => void,
    value: string,
    label: string,
    width: string
}

export const DropDown: React.FC<DropDownBoxProps> = ({
    isActive,
    setIsActive,
    value,
    label,
    width

}) => {
    return (
        <Box height='auto' contentJustification='center' contentDirection='column' marginR='15px ' width={width}>
        <Label>
          {label}
        </Label>
        <ContainerDropDown contentDirection='row' isActive={isActive} onClick={() => setIsActive(!isActive)} width={width}>
          <Box style={{borderTopLeftRadius: '5px', borderBottomLeftRadius: '5px'}}  borderL='1px solid grey' borderB='1px solid grey' width="100%" borderT='1px solid grey'  height='2.7em' paddingL='15px' paddingR='15px' contentAlignment='center' contentDirection='row'>
              <p style={{ overflow: 'hidden', textOverflow: 'ellipsis', height: '1.2em'}}>
                {value}
              </p>
          </Box>
          <Box style={{borderTopRightRadius: '5px', borderBottomRightRadius: '5px'}} height='100%' contentJustification='center' paddingL='10px' paddingR='10px' borderR='1px solid grey' borderB='1px solid grey' borderT='1px solid grey'>
            <DropDownIconContainer active={isActive}>
              <SlArrowDown size='0.5em' color='grey'/>
            </DropDownIconContainer>
          </Box>
        </ContainerDropDown>
    </Box>
    )
}