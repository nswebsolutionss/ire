

import LondonPic from 'assets/london.jpg'

import { ControlPosition, Map, Marker} from "@vis.gl/react-google-maps";
import React, { useState, KeyboardEvent, useCallback } from 'react';
import { MdOutlineFilterList } from "react-icons/md";
import { FaMapMarkerAlt } from "react-icons/fa";
import { AiOutlinePlus } from "react-icons/ai";
import { SlArrowDown } from 'react-icons/sl';
import { RxCross1 } from "react-icons/rx";
import { HiMenu } from "react-icons/hi";
import styled from 'styled-components';
import {AutoCompleteMapHandler, CircleOverlay} from '@dwellio/components'

import { Box, NavBar, SearchFilterBar, PropertyWidget, Text} from "@dwellio/components";
import { useAddress, usePosition, useRadius } from 'src/redux/search/useSearchPropertiesStateSelectors';

const Input = styled.input`
  padding-left: 10px;
  box-sizing: border-box;
  border: 1px solid grey;
  border-radius: 5px;
  outline: none;
  padding: 5px;
  margin: none;
  font-size: inherit;

`
const PreferenceItem = styled(Box)`
  &:hover {
    background-color: rgba(0, 0, 0, 0.05);
    cursor: pointer;
  }
`

const PreferenceBox = styled(Box)`
  &:hover {
    cursor: pointer;
  }
`

export const PropertySaleResults: React.FC = () => {
  const preferenceList: string[] = [
    "Highest Price",
    "Lowest Price",
    "Most Recent",
    "Oldest Listed"
  ]
  
  const address = useAddress()
  const [showMap, setShowMap] = useState(true)
  const [showKeywordInput, setShowKeywordInput] = useState(false)
  const [keywords, setKeywords] = useState<string[]>([])
  const [showSortPreferences, setShowSortPreferences] = useState(false)
  const [selectedPreference, setSelectedPreference] = useState(preferenceList[0])
  const position = usePosition()
  const radius = useRadius()

  return (
    <Box>
      <NavBar/>
      <SearchFilterBar/>
      
      <Box contentDirection='row' width='100vw' height='auto' marginT='170px' style={{zIndex: '0'}}>
        <Box backgroundColor='rgba(0, 0, 0, 0.02)' width={showMap ? '50%' : '80%'} height='auto' contentDirection='column'>
        
          <Box contentDirection='column' backgroundColor='white' position='fixed' width={showMap ? '50%' : '80%'} style={{zIndex: '2'}} >
            <Box height='50px'>
              <Box selfAlignment='start' contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor='white' padding='10px'>
                <Text weight='300' size='0.9em'>689 results</Text>
              </Box>
              <Box position='absolute' right='0px' height='100%' contentDirection='row'>
                <Box borderL='1px solid rgba(0, 0, 0, 0.05)' contentDirection='row' contentAlignment='center' contentJustification='start' height='100%' width='200px' paddingL='10px' backgroundColor='white' onClick={() => setShowSortPreferences(!showSortPreferences)}>
                  Sort:
                  <PreferenceBox paddingL='15px' paddingR='10px' contentDirection='row' contentAlignment='center'>
                    {selectedPreference}
                    <Box width='10px'/>
                    <SlArrowDown size={'0.5em'}/>
                    {
                      showSortPreferences
                      ?
                      <Box border='1px solid rgba(0, 0, 0, 0.05)' height='auto' padding='7.5px' width='100%' backgroundColor='white' position='absolute' top='35px' right='-1px' contentDirection='column' contentAlignment='start' style={{zIndex: '10'}}>
                        {
                          preferenceList.map((preference) => {
                            return (
                              <PreferenceItem borderRadius='5px' backgroundColor={selectedPreference === preference ? 'rgba(0, 0, 0, 0.05)' : 'white'} id={preference} width='100%' padding='10px' marginB='5px' marginT='5px' onClick={(e) => setSelectedPreference(preference)}>
                                {preference}
                              </PreferenceItem>
                            )
                          })
                        }
                      </Box>
                      :
                      <></>
                    }
                  </PreferenceBox>
                </Box>
                
                <Box hoverable={true} borderR='1px solid rgba(0, 0, 0, 0.05)' borderL='1px solid rgba(0, 0, 0, 0.05)' borderB='1px solid rgba(0, 0, 0, 0.02)' contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor={showMap ? 'rgba(0, 0, 0, 0.02)' : 'white'} padding='10px' onClick={() => setShowMap(true)}>
                  <FaMapMarkerAlt/>
                  <Box width='5px'/>
                  Map
                </Box>
                <Box hoverable={true} contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor={ !showMap ? 'rgba(0, 0, 0, 0.02)' : 'white'} padding='10px' onClick={() => setShowMap(false)} >
                    <HiMenu/>
                    <Box width='5px'/>
                    List
                </Box>
              </Box>
            </Box>

            <Box backgroundColor='rgba(0, 0, 0, 0.02)' height='60px' style={{zIndex: '2'}} borderT='1px solid rgba(0, 0, 0, 0.05)' >
              <Box padding='20px' contentDirection='row' contentAlignment='center' height='60px'>
                <MdOutlineFilterList size={25} color='black'/>
                <Box width='10px'/>
                <Text>Filter</Text>

                <Box width='40px'/>

                {
                  keywords.map((keyword) => 
                  <Box marginL='5px' marginR='5px' border='1px solid black' backgroundColor='white' borderRadius='5px' padding='5px' contentDirection='row' contentAlignment='center'>
                    {keyword}
                    <Box width='5px'/>
                    <Box hoverable={true} onClick={() => setKeywords(keywords.filter(a => a !== keyword))}>
                      <RxCross1 size={15}/>
                    </Box>
                  </Box>)
                }

                <Box hoverable={true} contentDirection='row' contentAlignment='center' onClick={() => setShowKeywordInput(true)}>
                  {
                    showKeywordInput
                    ?
                    <Box>
                      <Input onKeyDownCapture={(e: KeyboardEvent<HTMLInputElement>) => {
                          if(e.key === 'Enter') {
                            const value: string = e.currentTarget.value
                            if(value.trim().length > 0) {
                              setKeywords(keywords.concat(e.currentTarget.value))
                            }
                            setShowKeywordInput(false)
                          }
                          }
                        }/>
                    </Box>
                    :
                    keywords.length > 2 ? <></> :
                    <Box contentDirection='row' contentAlignment='center'>
                      <AiOutlinePlus size={25}/>
                      <Box width='5px'/>
                      <Text>Add keyword</Text>
                    </Box>
                    
                  }
                </Box>
              </Box>
            </Box>
          </Box>
        
          <Box paddingT='110px' style={{zIndex: '0'}}>
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          <PropertyWidget
            price='£425,000'
            location='Alicate, Spain'
            propertyType='Flat'
            numberOfBeds='2'
            numberOfBathrooms='1'
            description='This fabulous, very well presented, modern, top floor, 2 bedroom 2 bathroom apartment...'
            imageSource={LondonPic}
          />
          </Box>
          
          
          
        </Box>          
        <Box width={showMap ? '50%' : '0%' } height='calc(100vh - 170px)' style={{position: 'fixed', right: '0px'}}>
          <Map
            defaultZoom={12}
            defaultCenter={{lat: 51.54992, lng: -0.18472}}
            gestureHandling={'greedy'}
            disableDefaultUI={true}
          >
            {
              position !== null ?
            <CircleOverlay
              radius={Number.parseInt(radius)}
              center={{lat: position.lat, lng: position.lon}}
              onRadiusChanged={() => {}}
              strokeColor={'#0c4cb3'}
              strokeOpacity={1}
              strokeWeight={1}
              fillColor={'#3b82f6'}
              fillOpacity={0.2}
            />
            :
            <></>
            }
            
          </Map>
        <AutoCompleteMapHandler place={address} />
        </Box>
      </Box>
    </Box>
  );
}
