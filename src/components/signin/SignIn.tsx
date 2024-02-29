import { Dispatch, SetStateAction } from "react";
import { useNavigate } from "react-router"
import styled from "styled-components"


export interface SignInProps {
    visible: boolean;
    setVisible: Dispatch<SetStateAction<boolean>>;
}

const Container = styled.div<SignInProps>`
    box-sizing: border-box;
    text-align: center;
    display: flex;
    padding: 25px;
    flex-direction: column;
    align-items: center;
    height: calc(100% - 75px);
    width: 250px;
    position: absolute;
    background-color: white;
    border-left: 1px solid grey;
    top: 75px;
    right: 0px;
    visibility: ${p => p.visible ? "visible" : "hidden"};
    opacity: ${p => p.visible ? "1" : "0"};
    transition: 0.2s ease all;
    `

const Button = styled.div`
    display: flex;
    border-radius: 5px;
    width: 200px;
    height: 40px;
    margin-bottom: 5px;
    font-size: inherit;
    background-color: #355AA3;
    align-items: center;
    justify-content: center;
    color: white;

    &:hover {
        cursor: pointer;
        transform: scale(1.05);
        box-shadow: 3px 3px 8px grey;
        transition: 0.2s ease all;
    }
    &:active {
        cursor: pointer;
        transform: scale(1);
        box-shadow: 1px 1px 8px grey;
        transition: 0.2s ease all;
    }
    `
export const SignIn: React.FC<SignInProps> = ({ visible, setVisible }) => {
    const navigate = useNavigate();
    return (

        <Container visible={visible} setVisible={setVisible}>
            <p style={{ padding: "25px", fontSize: "20px" }}>
                Sign In Or Create Account
            </p>

            <input placeholder="Email Address" style={{ boxSizing: "border-box", border: "1px solid grey", paddingLeft: "10px", borderRadius: "5px", width: "200px", height: "35px", marginBottom: "20px", fontSize: "inherit" }}>
            </input>
            <Button onClick={(e) => {
                e.preventDefault();
                setVisible(false);
                localStorage.setItem("loggedIn", "true")
                navigate("/dashboard");

            }}>
                Continue
            </Button>
        </Container>


    )
}