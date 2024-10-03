import niceplace from 'assets/niceplace.jpg'
import mountain from 'assets/mountain.jpg'
import london from 'assets/london.jpg'
import foo from 'assets/foo.jpg';

import { IoArrowForwardCircleOutline } from "react-icons/io5";
import styled from 'styled-components';

import { Box, SectionContainer, SearchSection, PageContainer, NavBar, Text} from "@dwellio/components";

// import { MwDwellioContainer } from '../layout/landing-page/MyDwellioContainer';


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

export const LandingPage: React.FC = () => {
  return (
    <Box>
      <NavBar/>

      <BackgroundImage src={foo}/>
      <BackgroundImageOverlay/>
      <TextReactive position='absolute' width='750px' height='auto' top='38vh' left='calc(50vw - 375px)'>
        <Text size='1em' color='white' weight='500'>Connecting buyers and sellers from</Text>
        <Text size='1em' color='#B7ECFD' weight='500' alignSelf='end'>from around the world</Text>
      </TextReactive>
      <SearchSection/>

      <PageContainer>
        <SectionContainer contentDirection='column' contentJustification='space-between' style={{flexWrap: 'wrap'}}>
          <Text weight='500' size='1.5em'>Feel confident making your next move</Text>
          <Box height='10px'/>
          <Text weight='300' size='1.1em'>We value each of our customers equally woeifh wefjio fjewipom ewfnkladhfi wehfonef f newofi afhu weofh ewf klnewf e </Text>

          <Box height='30px'/>
          <Box contentDirection='row' contentJustification='space-between'>
            <img  src={niceplace} style={{objectFit: "cover", height: "30%", width: "30%", minWidth: "250px", minHeight: "225px", maxWidth: "400px", maxHeight: "420px", borderRadius: "10px"}}/>
            <img  src={london} style={{objectFit: "cover", height: "30%", width: "30%", minWidth: "250px", minHeight: "225px", maxWidth: "400px", maxHeight: "420px", borderRadius: "10px"}}/>
            <img  src={mountain} style={{objectFit: "cover", height: "30%", width: "30%", minWidth: "250px", minHeight: "225px", maxWidth: "400px", maxHeight: "420px", borderRadius: "10px", opacity: "0.7"}}/>
          </Box>
        </SectionContainer>
        
        <SectionContainer>
          {/* <MwDwellioContainer/>  */}
        </SectionContainer>

        <SectionContainer contentDirection='row' contentJustification='space-between' height='500px' backgroundColor='black' borderRadius='10px' style={{flexWrap: 'wrap'}}>
          <img  src={foo} style={{opacity: '0.6', objectFit: "cover", height: "100%", width: "100%", borderRadius: "10px"}}/>
          <div style={{position: 'absolute', bottom: '90px', left: '50px', width: '42%'}}>
            <Text size='3em' color='white'>
              Browse our locations
            </Text>
            <Box height='20px'/>
            <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-between'}}>
              <Button width='120px' height='35px' paddingT='20px' paddingB='20px' borderRadius='40px' border='2px solid white' contentJustification='center' contentAlignment='center' color='white'>
                <Text>Spain</Text>
              </Button>
              <Button width='120px' height='35px' paddingT='20px' paddingB='20px' borderRadius='40px' border='2px solid white' contentJustification='center' contentAlignment='center' color='white'>
                <Text>Portugal</Text>
              </Button>
              <Button width='120px' height='35px' paddingT='20px' paddingB='20px' borderRadius='40px' border='2px solid white' contentJustification='center' contentAlignment='center' color='white'>
                <Text>France</Text>
              </Button>
              <Button width='120px' height='35px' paddingT='20px' paddingB='20px' borderRadius='40px' border='2px solid white' contentJustification='center' contentAlignment='center' color='white'>
                <Text>Poland</Text>
              </Button>
            </div>
            <Box height='30px'/>

            <Box  contentDirection='row' height='auto' contentAlignment='center' hoverable={true}>
              <Text size='1.3em' color='#B7ECFD' weight='600'>
                Show more locations
              </Text>
              <Box width='10px'/>
              <IoArrowForwardCircleOutline size={'1.8em'} color='#B7ECFD'/>
            </Box>
         
          </div>
        </SectionContainer>

      
      </PageContainer>
      
      <Box contentDirection='row' contentAlignment='center' contentJustification='space-between' paddingR='60px' paddingL='60px' width='100vw' height='400px' backgroundColor='#80ABB1' marginT='50px'></Box>

    </Box>
  );
}

