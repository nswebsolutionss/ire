import React from 'react';
import { useState } from 'react';
import styled from 'styled-components';
import { Box } from '../../components/atoms/Box';
import { Text } from '../../components/atoms/Text';
import { Label } from '../../components/atoms/Label';
import { SearchBar} from '../../components/molecules/SearchBar'
import { useNavigate } from 'react-router-dom';


const SearchContainer = styled.div`
  position: absolute;
  top: 50vh;
  width: 750px;
  height: auto;
  padding-top: 35px;
  padding-bottom: 35px;
  left: calc(50vw - 375px);
  background-color: white;
  border-radius: 15px;
  display: flex;
  justify-content: center;
  @media screen and (max-width: 800px) {
    top: 200px;
    height: auto;
    width: 90%;
    left: 0px;
    left: calc(50vw - 45vw);
    box-shadow: 0 4px 10px -6px grey;
  }
`

const SearchBarContainer = styled(Box)`
  @media screen and (max-width: 800px) {
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
  }
`

const SearchButtonReactive = styled(Box)`
  flex-direction: row;
  margin-left: 5px;
    @media screen and (max-width: 800px) {
    width: 100%;
    margin-left: 0px;
    justify-content: space-between;
  }
`
const Button = styled(Box)`
  transition: 0.2s ease;
  &:hover {
    cursor: pointer;
    transform: translate(0px, -1px);
    transition: 0.2s ease;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
    background-color: #689298;
  }
  &:active {
    opacity: 1;
    transform: translate(0px, 0px);
  }
  @media screen and (max-width: 800px) {
    margin-top: 15px;
    width: 49%;
    margin-left: 0px;
    margin-right: 0px;
  }
` 

export const SearchSection: React.FC = () => {
    const [value, setValue] = useState("");
    return (
      <SearchContainer>
        <Box contentAlignment='center' selfAlignment='center' height='100%' width='100%'>
        <Box contentJustification='center' contentAlignment='start' width='90%' height='100%'>
          <Label size={'1rem'} color='black'>Location</Label>
          <SearchBarContainer contentDirection='row' width='100%' contentJustification='space-between'>
            <SearchBar value={value} onValueChange={setValue} />
            <SearchButtonReactive>
                <Button marginL='5px' marginR='5px' width='100px' borderRadius='7px' height='45px' backgroundColor='#80ABB1' contentAlignment='center' contentJustification='center' color='white' fontWeight='500'>
                    <Text>Sale</Text>
                </Button>
                <Button width='100px' borderRadius='7px' height='45px' backgroundColor='#80ABB1' contentAlignment='center' contentJustification='center' color='white' fontWeight='500'>
                    <Text>Rent</Text>
                </Button>
            </SearchButtonReactive>
          </SearchBarContainer>
        </Box>
      </Box>
    </SearchContainer>
    )
  }