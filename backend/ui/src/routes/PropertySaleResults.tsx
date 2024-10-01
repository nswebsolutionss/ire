import { NavBar } from '../public-dashboard/navigation/NavBar';
import { Box } from '../components/atoms/Box';
import SearchFilterBar from './SearchFilterBar'
import LondonPic from '../routes/london.jpg'
import { Text } from '../components/atoms/Text';
import MapsImage from '../routes/googlemaps.webp'
import PropertyWidget from './PropertyWidget'
import { MdOutlineFilterList } from "react-icons/md";
import { AiOutlinePlus } from "react-icons/ai";
import { FaMapMarkerAlt } from "react-icons/fa";
import { HiMenu } from "react-icons/hi";
import { SlArrowDown } from 'react-icons/sl';
import { useState } from 'react';
import { Map, Marker} from "@vis.gl/react-google-maps";

function PropertySaleResults() {
  const [showMap, setShowMap] = useState(true)
  const [markerLocation, setMarkerLocation] = useState({
    lat: 51.509865,
    lng: -0.118092,
  });
  return (
    <Box>
      <NavBar/>
      <SearchFilterBar/>
      
      <Box contentDirection='row' width='100vw' height='auto' marginT='170px' style={{zIndex: '0'}}>
        <Box backgroundColor='rgba(0, 0, 0, 0.05)' width={showMap ? '55%' : '80%'} height='auto' contentDirection='column'>
        
          <Box contentDirection='column' backgroundColor='white' position='fixed' width={showMap ? '55%' : '80%'} style={{zIndex: '2'}} >
            <Box height='50px'>
                <Box selfAlignment='start' contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor='white' padding='10px'>
                  <Text weight='300' size='0.9em'>689 results</Text>
                </Box>
              <Box position='absolute' right='0px' height='100%' contentDirection='row'>
                
                <Box borderL='1px solid rgba(0, 0, 0, 0.05)' contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor='white' padding='10px'>
                  Sort:
                  <Box width='15px'/>
                  Highest Price
                  <Box width='10px'/>
                  <SlArrowDown size={'0.5em'}/>
                </Box>


                <Box hoverable={true} borderR='1px solid rgba(0, 0, 0, 0.05)' contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor={showMap ? 'rgba(0, 0, 0, 0.05)' : 'white'} padding='10px' onClick={() => setShowMap(true)}>
                    <FaMapMarkerAlt/>
                    <Box width='5px'/>
                    Map
                </Box>

                <Box hoverable={true} contentDirection='row' contentAlignment='center' contentJustification='center' height='100%' width='auto' backgroundColor={ !showMap ? 'rgba(0, 0, 0, 0.05)' : 'white'} padding='10px' onClick={() => setShowMap(false)} >
                    <HiMenu/>
                    <Box width='5px'/>
                    List
                </Box>
              </Box>
            </Box>

            <Box backgroundColor='rgba(0, 0, 0, 0.05)' height='60px' style={{zIndex: '2'}} >
              <Box padding='20px' contentDirection='row' contentAlignment='center'>
                <MdOutlineFilterList size={25} color='black'/>
                <Box width='10px'/>
                <Text>Filter</Text>

                <Box width='40px'/>

                <AiOutlinePlus size={25}/>
                <Box width='10px'/>
                <Text>Add keyword</Text>
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
        <Box width={showMap ? '45%' : '0%' } height='calc(100vh - 170px)' style={{position: 'fixed', right: '0px'}}>
        <Map
          style={{outline: 'none'}}
          defaultZoom={13}
          defaultCenter={markerLocation}
          gestureHandling={"greedy"}
          disableDefaultUI
        >
        <Marker position={markerLocation} />
      </Map>
        </Box>
      </Box>
      

    </Box>
  );
}

export default PropertySaleResults;