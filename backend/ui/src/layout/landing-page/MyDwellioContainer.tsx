import styled from "styled-components"
import { Box } from "../../components/atoms/Box"
import { Text } from "../../components/atoms/Text"

const MyDwellioBox = styled(Box)`
    @media screen and (max-width: 750px) {
    flex-direction: column;
    text-align: center;
    height: 200px;
  }
`

export const MwDwellioContainer: React.FC = () => {
    return (
      <MyDwellioBox contentDirection='row' contentAlignment='center' contentJustification='space-between' padding='30px' width='100%' height='auto' borderRadius='10px' backgroundColor='#FDC8B7' margin='auto'>
      <Box position='absolute' width='45px' height='20px' borderRadius='10px' backgroundColor='#FC7A50' top='10px' right='10px' contentJustification='center' contentAlignment='center'>
        <Text size='11px' weight='700' color='white'>New</Text>
      </Box>
      <Box contentAlignment='space-between'>
        <Text size='25px'>Introducing MyDwellio</Text>
        <Text weight='400'>Save your favourite properties and keep up to date with our latest news</Text>
      </Box>
     <Box width='150px' height='35px' borderRadius='40px' border='2px solid black' contentJustification='center' contentAlignment='center'>
      <Text>Sign up</Text>
     </Box>
    </MyDwellioBox>
    )
  }