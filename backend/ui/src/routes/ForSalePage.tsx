import { SignIn, SignUp } from '@clerk/clerk-react';
import { NavBar } from '../public-dashboard/navigation/NavBar';
import React, { useRef, useState } from 'react';
import styled from 'styled-components';

import { Box } from '../components/atoms/Box';
import { PageContainer } from '../components/atoms/PageContainer';
import { SectionContainer } from '../components/atoms/SectionContainer';
import { IoArrowForwardCircleOutline } from "react-icons/io5";

import { Text } from '../components/atoms/Text';
import { Size } from '../components/atoms/breakpoints';
import { MwDwellioContainer } from '../layout/landing-page/MyDwellioContainer';
import { SearchSection } from '../layout/landing-page/SearchContainer';


const BackgroundImage = styled.img`
  height: 70vh;
  width: 100vw;
  margin-top: 50px;
  object-fit: cover;
  @media screen and (max-width: 800px) {
    height: 370px;
  }
`
const BackgroundImageOverlay = styled(Box)`
  position: absolute;
  background: linear-gradient(rgba(0, 0, 0, 0.3), transparent);
  height: 70vh;
  width: 100vw;
  margin-top: 50px;
  @media screen and (max-width: 800px) {
    background: linear-gradient(rgba(0, 0, 0, 0.3), transparent);
      height: 370px;
  }
`

const TextReactive = styled(Box)`
  font-size: 36px;
  @media screen and (max-width: 800px) {
    font-size: 28px;
    top: 120px;
    width: 80%;
    left: calc(50vw - 40vw);
  }

    @media screen and (max-width: 570px) {
    font-size: 24px;
    top: 110px;
    width: 80%;
    left: calc(50vw - 40vw);
  }
`

const Button = styled(Box)`
  &:hover{
    opacity: 0.7;
    cursor: pointer;
  }
`

function ForSalePage() {


  return (
    <Box>
      <NavBar/>
      <SearchSection/>

      <PageContainer>

      
      </PageContainer>
      

    </Box>
  );
}

export default ForSalePage;