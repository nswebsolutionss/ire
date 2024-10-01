import React, { useRef, useState } from 'react';
import styled from 'styled-components';
import { Box } from '../components/atoms/Box';
import { SlArrowDown } from "react-icons/sl";

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

export const ContainerDropDown = styled(Box)<{isActive: boolean}>`
  border-radius: 5px;
  background-color: white;
  &:hover {
    cursor: pointer;
  }
  ${(props) => props.isActive ? 'outline: 2px solid #B7ECFD;' : ''}

`

const DropDownSelection: React.FC<{active: boolean, items: string[], setSelected: (val: string) => void}> = ({active, items, setSelected}) => {
      
  const DropDownSelectionContainer = styled.div<{active: boolean}>`
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  top: 50px;
  width: inherit;
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
    <DropDownSelectionContainer active={active} >
      {items.map(item => {
        return (
          <DropDownItemContainer onClick={() => setSelected(item)}>
            <div style={{width: '80%', textAlign: 'center', paddingTop: '7px', paddingBottom: '7px', marginTop: '1px', marginBottom: '1px', boxSizing: 'border-box'}}>
              {item}
            </div>
          </DropDownItemContainer>
          
        )
      })}
    </DropDownSelectionContainer>
  )
}

export const SingleDropDown: React.FC<{items: string[], selectedValue: string, setSelectedValue: (val: string) => void}> = ({items, selectedValue, setSelectedValue}) => {
    const [isActive, setIsActive] = useState(false)

  return (
    <ContainerDropDown isActive={isActive} contentDirection='row' width='180px' onClick={() => setIsActive(!isActive)}>
      <SelectContainer>
        {selectedValue}
      </SelectContainer>
      <SelectDropDownContainer>
        <DropDownBox active={isActive}>
          <SlArrowDown size='0.5em' color='grey'/>
        </DropDownBox>
      </SelectDropDownContainer>
      <DropDownSelection 
        setSelected={setSelectedValue}
        active={isActive} 
        items={items}/>
    </ContainerDropDown>
  
    
  )
}

export default SingleDropDown;