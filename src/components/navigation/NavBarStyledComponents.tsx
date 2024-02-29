import {styled} from "styled-components"

export const Container = styled.div`
    width: 100%;
    height: 75px;
    display: flex;
    border-bottom: 1px solid grey;
    position: absolute;
    flex-directon: row;
    color: white;
    align-items: center;
    justify-content: center;

`

export const Element = styled.div`
    width: 100px;
    height: 100%;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #355AA3;
    &:hover {
        background-color: white;
        color: black;
        cursor: pointer;
    }

`