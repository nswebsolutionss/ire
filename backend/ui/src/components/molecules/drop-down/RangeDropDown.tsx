
import SingleDropDown from './SingleDropDown';
import { DropDown, Label } from "../../atoms";
import { Box } from "../../atoms";

import React, { useEffect, useRef, useState } from 'react';
import { SlArrowDown } from "react-icons/sl";
import styled from 'styled-components';


const DropDownSelection: React.FC<{
  active: boolean, 
  minItems: string[], 
  maxItems: string[], 
  setSelectedMin: (val: string) => void,
  setSelectedMax: (val: string) => void
  selectedMin: string,
  selectedMax: string,
  minLabel: string,
  maxLabel: string

}
  > = ({active, minItems, maxItems, setSelectedMin, setSelectedMax, selectedMin, selectedMax, minLabel, maxLabel}) => {
      
  const DropDownContainer = styled.div<{active: boolean}>`
    display: flex;
    position: absolute;
    top: calc(100% + 10px);
    border: 1px solid grey;
    padding: 20px;
    background-color: white;
    border-radius: 5px;
    ${(props) => !props.active ? 'visibility: hidden;' : ''}
  `


  return (
    <DropDownContainer active={active}>
        <Box>
        <SingleDropDown items={minItems} setSelectedValue={setSelectedMin} selectedValue={selectedMin} label={minLabel}/>
        </Box>
        <Box width='10px'/>
        <Box>
        <SingleDropDown items={maxItems} setSelectedValue={setSelectedMax} selectedValue={selectedMax} label={maxLabel}/>
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
  selectedMin: string,
  selectedMax: string,
  setSelectedMin: (val: string) => void,
  setSelectedMax: (val: string) => void,
  value: string;
  minLabel: string;
  maxLabel: string;

}> = ({
  title,
  minItems,
  maxItems, 
  value,
  selectedMin,
  selectedMax,
  setSelectedMin,
  setSelectedMax,
  minLabel,
  maxLabel
}) => {
    const [isActive, setIsActive] = useState(false)

  return (
    <Box>
      <DropDown
        isActive={isActive}
        setIsActive={setIsActive}
        label={title}
        value={value}
        width='180px'
      />
      <DropDownSelection 
        active={isActive} 
        minItems={calcuatedMinValues(selectedMax, minItems)}
        maxItems={calcuatedMaxValues(selectedMin, maxItems)}
        setSelectedMin={setSelectedMin}
        setSelectedMax={setSelectedMax}
        selectedMin={selectedMin}
        selectedMax={selectedMax}
        minLabel={minLabel}
        maxLabel={maxLabel}

        />
    </Box>
  
    
  )
}

export default RangeDropDown;