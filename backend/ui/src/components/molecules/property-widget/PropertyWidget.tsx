import { Text } from "../../atoms";
import { Box } from "../../atoms";

import { PiEnvelopeSimpleThin } from "react-icons/pi";
import { HiOutlineHeart } from "react-icons/hi";
import { IoBedOutline } from "react-icons/io5";
import { PiPhoneThin } from "react-icons/pi";
import { HiHeart } from "react-icons/hi";
import { TbBath } from "react-icons/tb";

import React, { useState } from 'react';

interface PropertyWidgetProps{
  price: string;
  location: string;
  propertyType: string;
  numberOfBeds: string;
  numberOfBathrooms: string;
  description: string;
  imageSource: any;
  
}

export const PropertyWidget: React.FC<PropertyWidgetProps> = ({
  price, 
  location, 
  propertyType, 
  numberOfBeds,
  numberOfBathrooms,
  description,
  imageSource
}) => {

  const [saved, setSaved] = useState(false)
  return (
          <Box backgroundColor='white' height='200px' borderRadius='10px' border='1px solid rgba(0, 0, 0, 0.2)' marginT='15px' marginB='15px' marginL='25px' marginR='25px' contentDirection='row'>
            <Box width='45%' height='100%' backgroundColor='white' style={{borderTopLeftRadius: '10px', borderBottomLeftRadius: '10px'}}>
              <img src={imageSource} style={{objectFit: 'cover', height: '100%', borderRadius: 'inherit'}}/>
            </Box>
            <Box contentDirection='column' padding='15px' >
              <Text size='1.3em'>{price}</Text>
              <Box height='5px'/>
              <Text size='1em'>{location}</Text>
              <Box height='7px'/>
              <Box contentDirection='row' contentAlignment='center'>
                <Text size='0.9em' weight='400'>{propertyType}</Text>
                <Box width='15px'/>

                <IoBedOutline/>
                <Box width='5px'/>
                <Text size='0.9em' weight='400'>{numberOfBeds}</Text>
                <Box width='20px'/>

                <TbBath/>
                <Box width='5px'/>
                <Text size='0.9em' weight='400'>{numberOfBathrooms}</Text>
              </Box>

              <Box height='7px'/>
              <Box  width='calc(100%)' style={{fontSize: '0.9em', textOverflow: 'ellipsis'}}>
                {description}
              </Box>

              <Box position='absolute' top='10px' right='10px' hoverable={true} contentDirection='row' contentAlignment='center' onClick={() => setSaved(!saved)}>
                {
                  saved 
                    ? 
                    <HiHeart size={30} color='#80ABB1'/>
                    :
                    <HiOutlineHeart size={30} color='#80ABB1'/>
                }
              </Box>

              <Box position='absolute' bottom='10px' left='10px' hoverable={true} contentDirection='row' contentAlignment='center'>
                <PiPhoneThin size={20}/>
                <Box width='5px'/>
                <Text weight='400' size='0.9em' style={{textDecoration: 'underline #80ABB1', textUnderlineOffset: '5px'}}>View phone</Text>
                
                <Box width='25px'/>


                <PiEnvelopeSimpleThin size={20}/>
                <Box width='5px'/>
                <Text weight='400'  size='0.9em' style={{textDecoration: 'underline #80ABB1', textUnderlineOffset: '5px'}}>Email</Text>

              </Box>
            </Box>
          </Box>
  );
}

