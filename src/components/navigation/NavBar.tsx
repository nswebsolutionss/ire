import { useState } from "react"
import { Container, Element } from "./NavBarStyledComponents"
import { SignIn } from "../signin/SignIn";

interface NavBarProps {

}


export const NavBar: React.FC<NavBarProps> = () => {
    const [visible, setVisible] = useState(false);

    return (
        <div>
            <SignIn visible={visible} setVisible={setVisible}/>
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
    )
}