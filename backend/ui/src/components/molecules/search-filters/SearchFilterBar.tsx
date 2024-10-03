
import {SingleDropDown} from '../drop-down/SingleDropDown'
import {RangeDropDown} from '../drop-down/RangeDropDown'
import { Label } from "../../atoms";
import { Text } from "../../atoms";
import { Box } from "../../atoms";
import { DropDown } from "../../atoms";
import React, {useState } from 'react';
import { PiHouseLineThin } from "react-icons/pi";
import styled from 'styled-components';
import { 
  useAddress, 
  useFormattedBedrooms, 
  useFormattedPrice, 
  useMaxBedrooms, 
  useMaxPrice, 
  useMinBedrooms, 
  useMinPrice, 
  usePropertyTypes, 
  useRadius 
} from 'src/redux/search/useSearchPropertiesStateSelectors';
import { useSearchPropertiesStateDispatch } from 'src/redux/search/useSearchPropertiesStateDispatch';
import { PropertyType } from 'src/redux/search/SearchPropertyTypes';
import {ControlPosition, MapControl} from '@vis.gl/react-google-maps';
import { AddressAutoCompleteInput } from 'src/components/organisms';

const Input = styled.input`
  padding-left: 10px;
  box-sizing: border-box;
  border: 1px solid grey;
  border-radius: 5px;
  width: 100%;
  height: 2.7em;
  outline: none;
  padding: none;
  margin: none;
  font-size: inherit;
  &:focus {
    outline: 2px solid #B7ECFD;
  }
`
const LocationSearchBar: React.FC = ({
}) => {

  return (
    
    <Box height='auto' width='300px' contentJustification='center' contentDirection='column' marginR='15px' >
      <Label>
        Enter a location
      </Label>
      <AddressAutoCompleteInput/>
    </Box>
  )
}

const RadiusDropDown: React.FC = () => {
  const radius = useRadius()
  const {setRadius} = useSearchPropertiesStateDispatch()
  const [incrementRadius, setIncrementRadius] = useState("This area only")
  const mapRadiusStringToNumber = (val: string) => {
    if(val === 'This area only') {
      return 0;
    }
    else if(val == '+ 0.25 miles') {
      return 250;
    }
    else if(val === '+ 0.5 miles' ) {
      return 500;
    }
    else if(val === '+ 1 mile') {
      return 1000;
    }
    else if(val === '+ 2 miles') {
      return 2000;
    }
    else if(val === '+ 5 miles') {
      return 5000;
    }
    else if(val === '+ 10 miles') {
      return 10000;
    }
    else if(val === '+ 15 miles') {
      return 15000
    }
    else if(val === '+ 20 miles') {
      return 20000
    }
    else if(val === '+ 25 miles') {
      return 25000
    }
    else if(val === '+ 30 miles') {
      return 30000
    }
    else {
      return 0;
    }  
  }

  const calculateRadius = (val: string) => {
    const selectedIncrementRadiusNumber = mapRadiusStringToNumber(val)
    const currentIncrementRadiusNumber = mapRadiusStringToNumber(incrementRadius)
    const currentRadiusNumber = Number.parseInt(radius)

    const calculatedValue = (currentRadiusNumber - currentIncrementRadiusNumber) + selectedIncrementRadiusNumber
    setRadius(calculatedValue.toString())
    setIncrementRadius(val)
     
  }

  return (
    <Box contentJustification='center'>
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
        setSelectedValue={calculateRadius}
        selectedValue={incrementRadius}
        label={'Radius'}
      />
  </Box>
  )
}

const BedroomDropDown: React.FC = () => {
  const minBedrooms = useMinBedrooms()
  const maxBedrooms = useMaxBedrooms()
  const formattedBedrooms = useFormattedBedrooms()
  const { setMinBedrooms, setMaxBedrooms, } = useSearchPropertiesStateDispatch()

  return (
    <Box height='100%' contentJustification='center'>
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
        value={formattedBedrooms}
        selectedMin={minBedrooms}
        selectedMax={maxBedrooms}
        setSelectedMin={setMinBedrooms}
        setSelectedMax={setMaxBedrooms}
        minLabel='Min Bedrooms'
        maxLabel='Max Bedrooms'
      />
    </Box>
  )
}

const PriceDropDown: React.FC = () => {
  const minPrice = useMinPrice()
  const maxPrice = useMaxPrice()
  const formattedPrice = useFormattedPrice()
  const { setMinPrice, setMaxPrice } = useSearchPropertiesStateDispatch()

  return (
    <Box height='100%' contentJustification='center'>
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
        value={formattedPrice}
        selectedMin={minPrice}
        selectedMax={maxPrice}
        setSelectedMin={setMinPrice}
        setSelectedMax={setMaxPrice}
        minLabel='Min Price'
        maxLabel='Max Price'
     />
    </Box>
  )
}

const PropertyDropDown: React.FC = () => {

  const propertyTypes = usePropertyTypes()
  const { setPropertyTypes } = useSearchPropertiesStateDispatch()
  const [propertyTypeActive, setPropertyTypeActive] = useState(false)

  const mapPropertyType = (propertyType: PropertyType): string => {
    if(propertyType == PropertyType.DETACHED) {
      return "Detached"
    }
    else if(propertyType == PropertyType.SEMI_DETACHED) {
      return "Semi Detached"
    }
    else if(propertyType == PropertyType.APARTMENT) {
      return "Apartment"
    }
    else if(propertyType == PropertyType.BUNGALOW) {
      return "Bungalow"
    }
    else if(propertyType == PropertyType.VILLA) {
      return "Villa"
    }
    else if(propertyType == PropertyType.TERRACED) {
      return "Terraced"
    }
    else {
      return ""
    }
  }
  return (
    <Box contentJustification='center' height='100%' position='static'>
      <DropDown
      isActive={propertyTypeActive}
      setIsActive={setPropertyTypeActive}
      value={propertyTypes.length == 0 ? 'Show all' : propertyTypes.map((item) => mapPropertyType(item)).toString()}
      label='Property Type'
      width='200px'
      />
    <Box 
        displayType={propertyTypeActive ? 'flex' : 'none'}
        position='absolute' 
        left='0px'
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
        <Box borderR='1px dashed grey' contentAlignment='center' hoverable={true} onClick={() => setPropertyTypes(PropertyType.DETACHED)}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypes.includes(PropertyType.DETACHED) ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypes.includes(PropertyType.DETACHED) ? 'white' : 'inherit'}>
              Detached
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => setPropertyTypes(PropertyType.SEMI_DETACHED)}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypes.includes(PropertyType.SEMI_DETACHED) ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypes.includes(PropertyType.SEMI_DETACHED) ? 'white' : 'inherit'}>
              Semi Detached
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => setPropertyTypes(PropertyType.TERRACED)}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypes.includes(PropertyType.TERRACED) ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypes.includes(PropertyType.TERRACED) ? 'white' : 'inherit'}>
              Terraced
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => setPropertyTypes(PropertyType.APARTMENT)}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypes.includes(PropertyType.APARTMENT) ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypes.includes(PropertyType.APARTMENT) ? 'white' : 'inherit'}>
              Apartment
            </Text>
          </Box>
        </Box>
        <Box borderR='1px dashed grey' contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => setPropertyTypes(PropertyType.BUNGALOW)}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypes.includes(PropertyType.BUNGALOW) ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypes.includes(PropertyType.BUNGALOW) ? 'white' : 'inherit'}>
              Bungalow
            </Text>
          </Box>
        </Box>
        <Box contentAlignment='center' paddingL='25px' paddingR='25px' hoverable={true} onClick={() => setPropertyTypes(PropertyType.VILLA)}>
          <PiHouseLineThin size={70}/>
          <Box backgroundColor={ propertyTypes.includes(PropertyType.VILLA) ? '#80ABB1' : 'none'} paddingL='25px' paddingR='25px' paddingT='2px' paddingB='2px' borderRadius='5px' marginR='5px'>
            <Text color={propertyTypes.includes(PropertyType.VILLA) ? 'white' : 'inherit'}>
              Villa
            </Text>
          </Box>
        </Box>
      </Box>
      </Box>
  )
}

export const SearchFilterBar: React.FC = ({
}) => {
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
    style={{fontSize: '0.95em', position: 'fixed', zIndex: '1'}}>

    <LocationSearchBar/>
    <RadiusDropDown/>
    <BedroomDropDown/>
    <PriceDropDown/>
    <PropertyDropDown/>
  
  </Box>
  );
}

