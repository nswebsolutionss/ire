import Select from "react-select/dist/declarations/src/Select"
import { HorizontalSpacer, InputBox, Label, TimeInput } from "../atoms/FormFields"
import { TextInput } from "../atoms/TextInput"
import styled from "styled-components"
import { useState } from "react"



const OpeningTimeRow: React.FC<{ day: string, value: string, onValueChange: (foo: string) => void}> = ({ day, value, onValueChange }) => {
    
    const [disableInputs, setDisableInputs] = useState(false)

    return (
        <div style={{ display: "flex", flexDirection: "row", padding: "20px" }}>
            <Label style={{ width: "150px" }}>{day}</Label>
            <TimeInput type="time" disabled={disableInputs} value={value} onChange={(e) => onValueChange(e.target.value)}/>
            <HorizontalSpacer />
            <HorizontalSpacer />
            <TimeInput type="time" disabled={disableInputs} value={value} />
            <HorizontalSpacer />
            <div style={{ display: "flex", width: "80px", justifyContent: "center" }}>
                <input type="radio" name={day} />
            </div>
            <div style={{ display: "flex", width: "80px", justifyContent: "center" }}>
                <input type="radio" name={day} onClick={() => setDisableInputs(true)}/>
            </div>
        </div>
    )
}

export const OpeningTime: React.FC = () => {
    const [mondayOpen, setMondayOpen] = useState("");
    return (
        <div style={{ border: "1px solid rgba(0, 0, 0, 0.2)", width: "625px", height: "auto", display: "flex", flexDirection: "column", borderRadius: "5px" }}>
            <div style={{ display: "flex", flexDirection: "row", borderBottom: "1px solid rgba(0, 0, 0, 0.2)", padding: "20px" }}>
                <Label style={{ width: "150px" }}>Day</Label>
                <Label style={{ width: "100px", textAlign: "center"  }}>Time Open</Label>
                <HorizontalSpacer />
                <HorizontalSpacer />
                <Label style={{ width: "100px", textAlign: "center" }}>Time Close</Label>
                <HorizontalSpacer />
                <Label style={{ width: "80px", textAlign: "center" }}>24h</Label>
                <Label style={{ width: "80px", textAlign: "center" }}>Closed</Label>

            </div>
            <OpeningTimeRow day={"Monday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
            <OpeningTimeRow day={"Tuesday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
            <OpeningTimeRow day={"Wednesday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
            <OpeningTimeRow day={"Thursday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
            <OpeningTimeRow day={"Friday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
            <OpeningTimeRow day={"Saturday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
            <OpeningTimeRow day={"Sunday"} value={mondayOpen} onValueChange={(foo: string) => {setMondayOpen(foo)}}/>
        </div>
    )
}