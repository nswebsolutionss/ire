
import { Box, DropDown } from "../../atoms";

import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';

const DropDownSelection: React.FC<{active: boolean, items: string[], setSelected: (val: string) => void, width: string, setIsActive: (val: boolean) => void}> = ({active, items, setSelected, width, setIsActive}) => {
      
  const DropDownSelectionContainer = styled.div<{active: boolean, width: string}>`
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  top: calc(100% + 10px);
  width: ${(props) => props.width ? props.width : 'inherit'};
  padding-bottom: 15px;
  padding-top: 7px;
  height: 200px;
  overflow: scroll;
  background-color: white;
  border-radius: 5px;
  border: 1px solid grey;
    ${(props) => !props.active ? 'visibility: hidden;' : ''}
  `

  const DropDownItemContainer = styled.div`
    width: 90%;
    display: flex;
    justify-content: center;
    border-radius: 7px;
      border: 1px solid white;
    &:hover {
      outline: 2px solid #B7ECFD;
      cursor: pointer;
      border: 1px solid grey;
    }
  `

  return (
    <DropDownSelectionContainer width={width} active={active} >
      {items.map(item => {
        return (
          <DropDownItemContainer onClick={() => {
            setSelected(item)
            setIsActive(!active)
          }
          }>
            <div style={{width: '80%', textAlign: 'center', paddingTop: '7px', paddingBottom: '7px', marginTop: '1px', marginBottom: '1px', boxSizing: 'border-box'}}>
              {item}
            </div>
          </DropDownItemContainer>
          
        )
      })}
    </DropDownSelectionContainer>
  )
}

export const SingleDropDown: React.FC<{items: string[], selectedValue: string, setSelectedValue: (val: string) => void, label: string}> = ({items, selectedValue, setSelectedValue, label}) => {
    const [isActive, setIsActive] = useState(false)
  return (
    <Box>
      <DropDown
        isActive={isActive}
        setIsActive={setIsActive}
        label={label}
        value={selectedValue}
        width='180px'
      />
      <DropDownSelection 
        setSelected={setSelectedValue}
        setIsActive={setIsActive}
        active={isActive} 
        items={items}
        width='180px'/>
    </Box>
  
    
  )
}

export default SingleDropDown;