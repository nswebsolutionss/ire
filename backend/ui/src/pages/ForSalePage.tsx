
import {SearchSection, PageContainer, NavBar, Box} from "@dwellio/components"

import styled from 'styled-components';

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

export const ForSalePage: React.FC = () => {


  return (
    <Box>
      <NavBar/>
      <SearchSection/>

      <PageContainer>

      
      </PageContainer>
      

    </Box>
  );
}

