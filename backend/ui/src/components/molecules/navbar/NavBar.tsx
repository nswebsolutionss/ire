import { Text } from "../../atoms"
import { Box } from "../../atoms"

import { VscTriangleUp } from "react-icons/vsc";
import { CiMenuBurger } from "react-icons/ci";
import { SlArrowDown } from 'react-icons/sl';
import { useNavigate } from "react-router";
import { RxCross1 } from "react-icons/rx";
import styled from "styled-components";
import React, {useState} from "react";
import { Container, Element} from "./NavBarStyledComponents";

interface NavBarProps {

}
const LanguageDropDownBox = styled.div<{active: boolean}>`
padding: none;
margin: none;
display: flex;
${(props) => props.active ? 'transform: rotate(180deg);' : ''}
`

const DropDownBox = styled(Box)<{active: boolean}>`
    ${(props) => !props.active ? 'visibility: hidden;' : ''}
    width: 100vw;
    height: 200px;
    position: absolute;
    ${(props) => !props.active ? 'transform: scaleY(0);' : 'scaleY(1);'}
    transition: all 0.3s ease-in-out;
    transform-origin: top center;
    top: 70px;
    background-color: #F4F4F4;
`

const DropDownArrow = styled(VscTriangleUp)<{active: boolean}>`
    ${(props) => !props.active ? 'visibility: hidden;' : ''}
    position: absolute;
    bottom: -7px;
`

const MenueIcon = styled(CiMenuBurger)`
    visibility: hidden;
    position: absolute;
    left: calc(30px - 10px);
    top: calc(30px - 10px);
    &:hover {
        cursor: pointer;
    }
    @media screen and (max-width: 850px) {
        visibility: visible;
    }
`

const ExitIcon = styled(RxCross1)<{active: boolean}>`
    ${(props) => !props.active ? 'visibility: hidden;' : ''}
    position: absolute;
    right: 15px;
    top: 15px;
    padding: 5px;
    border-radius: 7px;
    &:hover {
        cursor: pointer;
        background-color: rgba(0, 0, 0, 0.1);
    }
`

const SpacerBox = styled(Box)<{active: boolean}>`
    width: ${(props) => props.active ? "5px" : "50px"};
    height: ${(props) => props.active ? "50px" : "5px"};

`
const DropDownSelection: React.FC<{active: boolean, items: string[], setSelected: (val: string) => void, closeHandler: (val: boolean) => void}> = ({active, items, setSelected, closeHandler}) => {
      
    const DropDownSelectionContainer = styled.div<{active: boolean}>`
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    top: 25px;
    color: black;
    width: inherit;
    padding-bottom: 15px;
    padding-top: 7px;
    height: 200px;
    overflow: scroll;
    background-color: white;
    border-radius: 5px;
    border: 1px solid grey;
      ${(props) => !props.active ? 'visibility: hidden;' : ''}
    `
  
    const DropDownItemContainer = styled.div`
      width: 90%;
      display: flex;
      justify-content: center;
      border: 1px solid white;
      &:hover {
        cursor: pointer;
        background-color: rgba(0, 0, 0, 0.05);
      }
    `
  
    return (
      <DropDownSelectionContainer active={active} >
        {items.map(item => {
          return (
            <DropDownItemContainer onClick={() => {
                setSelected(item)
                closeHandler(false)
                }}>
              <div style={{width: '80%', textAlign: 'center', paddingTop: '7px', paddingBottom: '7px', marginTop: '1px', marginBottom: '1px', boxSizing: 'border-box'}}>
                {item}
              </div>
            </DropDownItemContainer>
            
          )
        })}
      </DropDownSelectionContainer>
    )
  }

export const NavBar: React.FC<NavBarProps> = () => {
    const [active, setActive] = useState(false);
    const [hiddenMenueActive, setHiddenMenuActive] = useState(false);
    const [language, setLanguage] = useState("EN")
    const [languageDropDownActive, setLanguageDropDownActive] = useState(false)
    const navigate = useNavigate();
    const countires = [
        'All countries',
        'Spain',
        'France',
        'Croatia',
        'Portugal',
        'Poland',
        'United Kingdom',
        'Ireland',
        'Austria',
        'Germany',
        'Hungary',
        'Czechia',
        'Slovakia',
        'Slovenia',
        'Montenegro',
        'Greece',
    ]


    return (
            <div style={{position: 'fixed', zIndex: '10', backgroundColor: 'white', width: '100vw', height: '70px', display: 'flex', justifyContent: 'center'}}>
            <DropDownBox contentAlignment='center' contentJustification="center" onMouseLeave={() => setActive(false)} active={active}>
                <Box width='40%' height="80%" contentJustification="space-between" contentDirection="column" style={{flexWrap: 'wrap'}}>
                   {countires
                   .sort()
                   .map((country) => {
                    return <Text margin='10px' hoverable={true}>
                        {country}
                    </Text>
                   })}
                </Box>
            </DropDownBox>
            <MenueIcon size={15} color={'black'} onClick={() => setHiddenMenuActive(true)}/>
                <Container isActive={hiddenMenueActive}>
                    <ExitIcon active={hiddenMenueActive} size={20} color={'grey'} onClick={() => setHiddenMenuActive(false)}/>
                    {/* <Element onMouseOver={() => setActive(false)}>
                        For Sale
                    </Element>
                    <SpacerBox active={hiddenMenueActive}/>
                    <Element onMouseOver={() => setActive(false)}>
                        For Rent
                    </Element> */}
                    <Element isActive={active} onMouseOver={() => setActive(true)} onClick={() => setActive(!active)}>
                        Countries
                        <DropDownArrow size={20} color={'#F4F4F4'} active={active}/>
                    </Element>
                    <SpacerBox active={hiddenMenueActive}/>
                    <Element onMouseOver={() => setActive(false)}>
                        About us
                    </Element>
                    <SpacerBox active={hiddenMenueActive}/>
                    <Element onMouseOver={() => setActive(false)}>
                        List with us
                    </Element>
                    <SpacerBox active={hiddenMenueActive}/>
                    <Element onMouseOver={() => setActive(false)}>
                            MyDwellio
                        </Element>
                        <SpacerBox active={hiddenMenueActive}/>
                        <Element onMouseOver={() => setActive(false)}>
                            Saved
                        </Element>
                        <SpacerBox active={hiddenMenueActive}/>
                        <Element 
                        style={{ 
                            borderRadius: '10px',
                             border: '2px solid #80ABB1', 
                             paddingLeft: '15px',
                             paddingRight: '15px',
                             paddingTop: '7px',
                             paddingBottom: '7px'
                             }}
                             onMouseOver={() => setActive(false)}>
                            Sign in
                        </Element>
                    {/* <div style={{ position: 'absolute', display: 'flex', right: '50px'}}>
                        <Element onMouseOver={() => setActive(false)}>
                            MyDwellio
                        </Element>
                        <SpacerBox active={hiddenMenueActive}/>
                        <Element onMouseOver={() => setActive(false)}>
                            Saved
                        </Element>
                        <SpacerBox active={hiddenMenueActive}/>
                        <Element 
                        style={{ 
                            borderRadius: '10px',
                             border: '2px solid #80ABB1', 
                             paddingLeft: '15px',
                             paddingRight: '15px',
                             paddingTop: '7px',
                             paddingBottom: '7px'
                             }}
                             onMouseOver={() => setActive(false)}>
                            Sign in
                        </Element>
                    </div> */}
                    <div style={{ position: 'absolute', display: 'flex', left: '50px'}}>
                        <Element style={{fontFamily:'Pacifico', fontSize: '1.5em', color: '#80ABB1', textDecoration: 'none'}} onMouseOver={() => setActive(false)} onClick={() => navigate('/')}>
                            Dwellio
                        </Element>
                    </div>
                    <div style={{ position: 'absolute', display: 'flex', right: '50px', width: '50px', justifyContent: 'center'}}>
                        <Box hoverable={true} contentDirection="row" contentAlignment="center" height="0.95em" onClick={() => setLanguageDropDownActive(!languageDropDownActive)}>
                            <Box color="black" marginR="7px">
                                {language}
                            </Box>
                            <LanguageDropDownBox active={languageDropDownActive}>
                                <SlArrowDown color={'black'} size={'0.5em'}/> 
                            </LanguageDropDownBox>
                        </Box>
                        <DropDownSelection
                                active={languageDropDownActive}
                                items={[
                                    "EN",
                                    "ES",
                                    "PL",
                                    "DE",
                                    "GR",
                                    "HU",
                                    "IT",
                                    "LV"
                                ]}
                                setSelected={setLanguage}
                                closeHandler={setLanguageDropDownActive}
                            />
                    </div>
                    
                </Container>
            </div>
    )
}