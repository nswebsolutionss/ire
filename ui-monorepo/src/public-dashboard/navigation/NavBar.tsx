import { useState } from "react"
import { Container, Element } from "./NavBarStyledComponents"
import { SignIn } from "@clerk/clerk-react";
import React from "react";

interface NavBarProps {

}


export const NavBar: React.FC<NavBarProps> = () => {
    const [visible, setVisible] = useState(false);



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
                    <Element onClick={() => { setVisible(!visible) }}>
                        Sign In
                    </Element>
                </Container>
            </div>
        
            <SignIn afterSignInUrl={"/dashboard"}/>
            
        </>
    )
}