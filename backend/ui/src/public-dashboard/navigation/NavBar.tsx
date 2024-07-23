import { useState } from "react"
import { Container, Element } from "./NavBarStyledComponents"
import { SignIn } from "@clerk/clerk-react";
import React from "react";
import { red } from "@mui/material/colors";
import { useNavigate } from "react-router-dom";

interface NavBarProps {

}


export const NavBar: React.FC<NavBarProps> = () => {
    const [visible, setVisible] = useState(false);
    const navigate = useNavigate();


    return (
        <>
            <div>
                <Container>
                    <Element>
                        Buy
                    </Element>
                    <Element>
                        Locations
                    </Element>
                    <Element>
                        Favourites
                    </Element>
                    <Element>
                        About Us
                    </Element>
                    <Element onClick={() => {navigate('/dashboard')}}>
                        Account
                    </Element>
                    <Element onClick={() => { 
                        fetch('http://localhost:8084/login', {
                            method: 'GET', 
                             mode: 'cors'                    
                            })
                        .then(response => {
                            const redirectUrl = response.headers.get("Location");
                            if(redirectUrl) {
                                window.location.replace(redirectUrl)

                            }
                        })
                
                     }}>
                        Sign In
                    </Element>
                    <Element onClick={() => { 
                        fetch('http://localhost:8084/logout', {
                            method: 'GET', 
                             mode: 'cors',
                             credentials: 'include'         
                            })
                        .then(response => {
                            // const payload = {};
                            // response.headers.forEach((value, name) => payload[name] = value);
                            // console.log(payload);
                            // const redirectUrl = response.headers.get("Location");
                            // console.log(response.headers.values.toString())
                            // if(redirectUrl) {
                            //     window.location.replace(redirectUrl)
                            // }
                        })
                
                     }}>
                        Sign Out
                    </Element>

                </Container>
            </div>
                
        </>
    )
}