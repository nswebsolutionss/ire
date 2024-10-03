import {styled} from "styled-components"

export const Container = styled.div<{isActive: boolean}>`
    width: 100vw;
    height: 100%;
    display: flex;
    flex-direction: row;
    color: white;
    align-items: center;
    justify-content: center;
    font-family: Roboto;
    font-weight: 500;
    left: calc(100vw - 80vw);

    @media screen and (max-width: 850px) {
        ${(props) => !props.isActive ? 'visibility: hidden;' : ''}
        box-sizing: border-box;
        padding-left: 50px;
        position: absolute;
        left: 0px;
        flex-direction: column;
        align-items: start;
        justify-content: start;
        padding-top: 100px;
        font-size: 18px;
        width: 225px;
        height: 100vh;
        background-color: white;
        box-shadow: 2px 0 10px rgba(0, 0, 0, 0.2);
    }
`

export const Element = styled.div<{isActive ?: boolean}>`
    width: auto;
    height: auto;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    color: black;
    font-family: Roboto;
    padding-bottom: 5px;
    padding-top: 5px;

    ${(props) => props.isActive ? 'border-bottom: 2px solid black;' : 'border-bottom: 2px solid transparent;'}
    ${(props) => props.isActive ? ' cursor: pointer;' : ''}


    &:hover {
    ${(props) => props.isActive == undefined ? "border-bottom: 2px solid black;" : ""}
        cursor: pointer;
    }

`