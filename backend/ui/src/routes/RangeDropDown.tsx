import { SignIn, SignUp } from '@clerk/clerk-react';
import { NavBar } from '../public-dashboard/navigation/NavBar';
import React, { useRef, useState } from 'react';
import styled from 'styled-components';
import { Box } from '../components/atoms/Box';
import { PageContainer } from '../components/atoms/PageContainer';
import { SectionContainer } from '../components/atoms/SectionContainer';
import { IoArrowForwardCircleOutline } from "react-icons/io5";
import { SlArrowDown } from "react-icons/sl";

import { Text } from '../components/atoms/Text';
import { Size } from '../components/atoms/breakpoints';
import { MwDwellioContainer } from '../layout/landing-page/MyDwellioContainer';
import { SearchSection } from '../layout/landing-page/SearchContainer';
import { useNavigate } from 'react-router';
import { TextInputNew } from '../components/molecules/TextInputNew';
import SingleDropDown, { ContainerDropDown } from './SingleDropDown';
import { Label } from '../components/atoms/Label';

const SelectDropDownContainer = styled.div`
font-family: Roboto;
width: 20%;
height: 100%;
color: grey;
padding-right: 10px;
box-sizing: border-box;
border-right: 1px solid grey;
border-top: 1px solid grey;
border-bottom: 1px solid grey;
display: flex;
justify-content: center;
align-items: center;
border-top-right-radius: 5px;
border-bottom-right-radius: 5px;

height: 2.72em;
outline: none;
&:focus {
  outline: 2px solid #B7ECFD;
}
`

const SelectContainer = styled.div`
font-family: Roboto;
width: 80%;
color: black;
padding-left: 10px;
box-sizing: border-box;
border-left: 1px solid grey;
border-top: 1px solid grey;
border-bottom: 1px solid grey;

border-top-left-radius: 5px;
border-bottom-left-radius: 5px;

display: flex;
align-items: center;
height: 2.72em;
outline: none;
&:focus {
  outline: 2px solid #B7ECFD;
}
@media screen and (max-width: 800px) {
  width: 100%;
}
`

const DropDownBox = styled.div<{active: boolean}>`
padding: none;
margin: none;
display: flex;
${(props) => props.active ? 'transform: rotate(180deg);' : ''}
`

const DropDownSelection: React.FC<{
  active: boolean, 
  minItems: string[], 
  maxItems: string[], 
  setSelectedMin: (val: string) => void,
  setSelectedMax: (val: string) => void
  selectedMin: string,
  selectedMax: string;
  title: string;

}
  > = ({active, minItems, maxItems, setSelectedMin, setSelectedMax, selectedMin, selectedMax, title}) => {
      
  const DropDownContainer = styled.div<{active: boolean}>`
    display: flex;
    position: absolute;
    top: 50px;
    border: 1px solid grey;
    padding: 20px;
    background-color: white;
    border-radius: 5px;
    ${(props) => !props.active ? 'visibility: hidden;' : ''}
  `


  return (
    <DropDownContainer active={active}>
        <Box>
          <Label>
            Min {title}
          </Label>
        <SingleDropDown items={minItems} setSelectedValue={setSelectedMin} selectedValue={selectedMin}/>
        </Box>
        <Box width='10px'/>
        <Box>
          <Label>
            Max {title}
          </Label>
        <SingleDropDown items={maxItems} setSelectedValue={setSelectedMax} selectedValue={selectedMax}/>
        </Box>
    </DropDownContainer>
    
  )
}

const calcuatedMaxValues = (selectedMin: string, maximumItemList: string[]) => {
  if(selectedMin == "No min") {
    return maximumItemList;
  }
  const curentIndex = maximumItemList.indexOf(selectedMin)
  const newArray = maximumItemList.slice(curentIndex, maximumItemList.length - 1)
  const returnArray = ["No max"].concat(newArray);
  return returnArray;
}

const calcuatedMinValues = (selectedMax: string, minimumItemList: string[]) => {
  if(selectedMax == "No max") {
    return minimumItemList;
  }
  const curentIndex = minimumItemList.indexOf(selectedMax)
  const newArray = minimumItemList.slice(0, curentIndex + 1)
  return newArray;
}

export const RangeDropDown: React.FC<{
  title: string;
  minItems: string[], 
  maxItems: string[],
  calculateValue: (valMin: string, valMax: string) => string;
}> = ({
  title,
  minItems,
  maxItems, 
  calculateValue
}) => {
    const [isActive, setIsActive] = useState(false)
    const [selectedMin, setSelectedMin] = useState("No min")
    const [selectedMax, setSelectedMax] = useState("No max")

  return (
    <ContainerDropDown isActive={isActive} contentDirection='row' width='180px'>
      <SelectContainer onClick={() => setIsActive(!isActive)}>
        {calculateValue(selectedMin, selectedMax)}
      </SelectContainer>
      <SelectDropDownContainer onClick={() => setIsActive(!isActive)}>
        <DropDownBox active={isActive}>
          <SlArrowDown size='0.5em' color='grey'/>
        </DropDownBox>
      </SelectDropDownContainer>
      <DropDownSelection 
        title={title}
        active={isActive} 
        minItems={calcuatedMinValues(selectedMax, minItems)}
        maxItems={calcuatedMaxValues(selectedMin, maxItems)}
        setSelectedMin={setSelectedMin}
        setSelectedMax={setSelectedMax}
        selectedMin={selectedMin}
        selectedMax={selectedMax}
        />
    </ContainerDropDown>
  
    
  )
}

export default RangeDropDown;