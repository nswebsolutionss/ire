import styled from "styled-components"

export const HorizontalSpacer = styled.div`
    width: 30px;
    height: 1px;
`

export const VerticalSpacer = styled.div<{size ?: string}>`
    width: 1px;
    height: ${p => p.size ? p.size : "30"}px;
`

export const InputBox = styled.input`
    box-sizing: border-box;
    width: 250px;
    padding: 5px;
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.3);
    font-size: 18px;
    font-family: inherit;
    color: rgba(0, 0, 0, 0.9);
`

export const Label = styled.label`
    font-sizing: 18px;
    font-family: inherit;
    color: rgba(0, 0, 0, 0.9);
    margin-bottom: 10px;
    margin-top: 10px;
`

export const TextArea = styled.textarea`
    width: 500px;
    min-height: 150px;
    padding: 5px;
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.3);
    font-size: 16px;
    font-family: inherit;
    color: rgba(0, 0, 0, 0.9);
`

export const Span = styled.span`
    align-self: start;
    display: flex;
    justify-content: center;
    align-items: center;
`

export const Button = styled.button`
    box-size: border-box;
    padding: 10px;
    margin-top: 25px;
    min-width: 100px;
    height: auto;
    border-radius: 5px;
    border: none;
    background-color: #2044a8;
    color: white;
    font-family: inherit;
    font-size: 18px;
    &:hover {
        opacity: 0.8;
        cursor: pointer;
    }

`

export const TimeInput = styled(InputBox)`
    width: auto;
`