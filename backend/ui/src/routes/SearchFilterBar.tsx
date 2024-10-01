import { NavBar } from '../public-dashboard/navigation/NavBar';
import styled from 'styled-components';
import { Box } from '../components/atoms/Box';
import { PageContainer } from '../components/atoms/PageContainer';
import {ContainerDropDown, SingleDropDown} from './SingleDropDown'
import {RangeDropDown} from './RangeDropDown'

import { CiFilter } from "react-icons/ci";
import { Text } from '../components/atoms/Text';
import React, { useState } from 'react';
import { Button } from '../components/atoms/FormFields';
import { Label } from '../components/atoms/Label';

import { PiHouseLineThin } from "react-icons/pi";
import { SlArrowDown } from 'react-icons/sl';

const Input = styled.input`
  padding-left: 10px;
  box-sizing: border-box;
  border: 1px solid grey;
  border-radius: 5px;
  height: 2.7em;
  outline: none;
  padding: none;
  margin: none;
  font-size: inherit;
  &:focus {
    outline: 2px solid #B7ECFD;
  }
  @media screen and (max-width: 800px) {
    width: 100%;
  }
`

const PropertyTypeDropDown = styled(Box)<{active: boolean}>`
${(props) => props.active ? 'transform: rotate(180deg);' : ''}
`

function SearchFilterBar() {
  const [radius, setRadius] = useState("This area only")
  const [propertyTypeActive, setPropertyTypeActive] = useState(false)
  const [propertyTypeList, setPropertyTypeList] = useState<string[]>([])

  const calculateBeds = (min: string, max: string) => {
    if(min === "No min" && max === "No max") {
      return "Any beds"
    }
    else if(min === "Studio" && max == "No max") {
      return "Studio min"
    }
    else if(min !== "No min" && max === "No max") {
      return min + " bed min";
    }
    else if(min === "No min" && max !== "No max") {
      return max + " bed max"
    }
    else if(min === max) {
      return min
    }
    else {
      return min + " - " + max + " bed"
    }
  }

  const formatPrice = (val: string) => {
    const strippedCommas = val.replace(",", "")
    const strippedCurrencyAndCommas = strippedCommas.replace("£", "")
    const foo = parseInt(strippedCurrencyAndCommas)
      if (foo < 1e3) return foo.toString();
      if (foo >= 1e3 && foo < 1e6) return (foo / 1e3).toFixed(0).toString() + "k";
      if (foo >= 1e6 && foo < 1e9) return (foo / 1e6).toFixed(0).toString() + "m";
      
  }

  const calculatePrice = (min: string, max: string) => {
    if(min === "No min" && max === "No max") {
      return "Any price"
    }
    else if(min !== "No min" && max === "No max") {
      return "£" + formatPrice(min) + ' min';
    }
    else if(min === "No min" && max !== "No max") {
      return "£" + formatPrice(max) + " max"
    }
    else if(min === max) {
      return "£" + formatPrice(min)
    }
    else {
      return "£" + formatPrice(min) + " - " + "£" +  formatPrice(max)
    }
  }

  return (
    <Box 
    position='absolute' 
    top='70px' 
    width='100vw' 
    height='100px'
    borderB='1px solid rgba(0, 0, 0, 0.1)' 
    borderT='1px solid rgba(0, 0, 0, 0.1)'
    contentDirection='row'
    contentJustification='center'
    backgroundColor='white'
    style={{fontSize: '0.95em', position: 'fixed', zIndex: '1'}}
  >
    <Box height='auto' width='300px' contentJustification='center' contentDirection='column' marginR='15px' >
      <Label>
        Enter a location
      </Label>
     <Input placeholder='e.g. Spain, Athens'/>
    </Box>

    <Box height='auto' contentJustification='center' contentDirection='column' marginR='15px'>
      <Label>
        Radius
      </Label>
      <SingleDropDown items={[
      'This area only', 
      '+ 0.25 miles', 
      '+ 0.5 miles', 
      '+ 1 mile',
      '+ 2 miles',
      '+ 5 miles',
      '+ 10 miles',
      '+ 15 miles',
      '+ 20 miles',
      '+ 25 miles',
      '+ 30 miles'
      ]}
      setSelectedValue={setRadius}
      selectedValue={radius}
      />
    </Box>

    <Box height='auto' contentJustification='center' contentDirection='column' marginR='15px'>
      <Label>
        Bedrooms
      </Label>
     <RangeDropDown
     title='Bedrooms'
      minItems={[
        'No min',
        'Studio',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7+',

      ]}
      maxItems={[
        'No max',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7+',
      ]}
      calculateValue={calculateBeds}
     />
    </Box>


    <Box height='auto' contentJustification='center' contentDirection='column' marginR='15px'>
      <Label>
        Price
      </Label>
      <RangeDropDown
        title='Price'
        minItems={[
          'No min',
          "£10,000",
          "£20,000",
          "£30,000",
          "£40,000",
          "£50,000",
          "£60,000",
          "£70,000",
          "£80,000",
          "£90,000",
          "£100,000",
          "£110,000",
          "£120,000",
          "£130,000",
          "£140,000",
          "£150,000",
          "£160,000",
          "£170,000",
          "£180,000",
          "£190,000",
          "£200,000",
          "£210,000",
          "£220,000",
          "£230,000",
          "£240,000",
          "£250,000",
          "£300,000",
          "£350,000",
          "£400,000",
          "£450,000",
          "£500,000",
          "£550,000",
          "£600,000",
          "£650,000",
          "£700,000",
          "£750,000",
          "£800,000",
          "£850,000",
          "£900,000",
          "£950,000",
          "£1,000,000"
        ]}
        maxItems={[
          'No max',
          "£10,000",
          "£20,000",
          "£30,000",
          "£40,000",
          "£50,000",
          "£60,000",
          "£70,000",
          "£80,000",
          "£90,000",
          "£100,000",
          "£110,000",
          "£120,000",
          "£130,000",
          "£140,000",
          "£150,000",
          "£160,000",
          "£170,000",
          "£180,000",
          "£190,000",
          "£200,000",
          "£210,000",
          "£220,000",
          "£230,000",
          "£240,000",
          "£250,000",
          "£300,000",
          "£350,000",
          "£400,000",
          "£450,000",
          "£500,000",
          "£550,000",
          "£600,000",
          "£650,000",
          "£700,000",
          "£750,000",
          "£800,000",
          "£850,000",
          "£900,000",
          "£950,000",
          "£1,000,000"
        ]}
        calculateValue={calculatePrice}
     />
    </Box>


    <Box height='auto' contentJustification='center' contentDirection='column' marginR='15px'>
    
        <Label>
          Property Type 
        </Label>
        <ContainerDropDown contentDirection='row' isActive={propertyTypeActive} onClick={() => setPropertyTypeActive(!propertyTypeActive)}>
          <Box style={{borderTopLeftRadius: '5px', borderBottomLeftRadius: '5px'}}  borderL='1px solid grey' borderB='1px solid grey' borderT='1px solid grey' width='150px' height='2.7em' paddingL='15px' paddingR='15px' contentAlignment='center' contentDirection='row'>
              <p style={{ overflow: 'hidden', textOverflow: 'ellipsis', height: '1.2em'}}>
                {propertyTypeList.length == 0 ? 'Show all' : propertyTypeList.map((item) => item.concat(','))}
              </p>
          </Box>
          <Box style={{borderTopRightRadius: '5px', borderBottomRightRadius: '5px'}} height='100%' contentJustification='center' paddingL='10px' paddingR='10px' borderR='1px solid grey' borderB='1px solid grey' borderT='1px solid grey'>
            <PropertyTypeDropDown active={propertyTypeActive}>
              <SlArrowDown size='0.5em' color='grey'/>
            </PropertyTypeDropDown>
          </Box>
        </ContainerDropDown>
    </Box>

    <Box height='auto' contentJustification='center' contentDirection='column'>
      <Box height={'1.15em'}>
      </Box>
      <Box height='5px'/>
      <Box height='2.7em' border='2px solid black' borderRadius='5px' paddingL='15px' paddingR='15px' contentAlignment='center' contentDirection='row' hoverable={true}>
        <CiFilter color='black' size='20px'/>
        <Box width='5px'/>
        <Text>
          Filters
        </Text>
      </Box>
    </Box>
  
    <Box 
        displayType={propertyTypeActive ? 'flex' : 'none'}
        position='absolute' 
        top='99px' 
        width='100vw' 
        height='auto'
        borderB='1px solid rgba(0, 0, 0, 0.1)' 
        paddingT='25px'
        paddingB='25px'
        contentDirection='row'
        contentJustification='center'
        contentAlignment='center'
        backgroundColor='#f2f4f7'
        style={{fontSize: '0.95em'}}
      >
        <Box borderR='1px dashed grey' contentAlignment='center' hoverable={true} onClick={() => {
          const copiedArray = propertyTypeList.copyWithin(propertyTypeList.length, 0)
          if(propertyTypeList.includes('Detached')) {
            const updatedaArray = copiedArray.filter((item) => item !== 'Detached')
            setPropertyTypeList(updatedaArray)
          }
          else {
            setPropertyTypeList(propertyTypeList.concat('Detached'))}
          }
          }>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypeList.includes('Detached') ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypeList.includes('Detached') ? 'white' : 'inherit'}>
              Detached
            </Text>
          </Box>
          
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => {
          const copiedArray = propertyTypeList.copyWithin(propertyTypeList.length, 0)
          if(propertyTypeList.includes('Semi Detached')) {
            const updatedaArray = copiedArray.filter((item) => item !== 'Semi Detached')
            setPropertyTypeList(updatedaArray)
          }
          else {
            setPropertyTypeList(propertyTypeList.concat('Semi Detached'))}
          }}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypeList.includes('Semi Detached') ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypeList.includes('Semi Detached') ? 'white' : 'inherit'}>
              Semi Detached
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => {
          const copiedArray = propertyTypeList.copyWithin(propertyTypeList.length, 0)
          if(propertyTypeList.includes('Terraced')) {
            const updatedaArray = copiedArray.filter((item) => item !== 'Terraced')
            setPropertyTypeList(updatedaArray)
          }
          else {
            setPropertyTypeList(propertyTypeList.concat('Terraced'))}
          }}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypeList.includes('Terraced') ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypeList.includes('Terraced') ? 'white' : 'inherit'}>
              Terraced
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => {
          const copiedArray = propertyTypeList.copyWithin(propertyTypeList.length, 0)
          if(propertyTypeList.includes('Apartment')) {
            const updatedaArray = copiedArray.filter((item) => item !== 'Apartment')
            setPropertyTypeList(updatedaArray)
          }
          else {
            setPropertyTypeList(propertyTypeList.concat('Apartment'))}
          }}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypeList.includes('Apartment') ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypeList.includes('Apartment') ? 'white' : 'inherit'}>
              Apartment
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => {
          const copiedArray = propertyTypeList.copyWithin(propertyTypeList.length, 0)
          if(propertyTypeList.includes('Bungalow')) {
            const updatedaArray = copiedArray.filter((item) => item !== 'Bungalow')
            setPropertyTypeList(updatedaArray)
          }
          else {
            setPropertyTypeList(propertyTypeList.concat('Bungalow'))}
          }}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypeList.includes('Bungalow') ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypeList.includes('Bungalow') ? 'white' : 'inherit'}>
              Bungalow
            </Text>
          </Box>
        </Box>
        <Box contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => {
          const copiedArray = propertyTypeList.copyWithin(propertyTypeList.length, 0)
          if(propertyTypeList.includes('Villa')) {
            const updatedaArray = copiedArray.filter((item) => item !== 'Villa')
            setPropertyTypeList(updatedaArray)
          }
          else {
            setPropertyTypeList(propertyTypeList.concat('Villa'))}
          }}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypeList.includes('Villa') ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypeList.includes('Villa') ? 'white' : 'inherit'}>
              Villa
            </Text>
          </Box>
        </Box>

      </Box>
  </Box>
  );
}

export default SearchFilterBar;