import { AgGridReact } from "ag-grid-react"
import { Span, VerticalSpacer } from "../atoms/FormFields"
import { TextInput } from "../atoms/TextInput"
import { useCallback, useMemo, useRef, useState } from "react";
import { ColDef } from "ag-grid-community";
import { BsHouseAdd } from "react-icons/bs";
import { IoIosAddCircleOutline } from "react-icons/io";
import styled from "styled-components";

const AddListing = styled.div`
    display: flex;
    flex-direction: row;
    height: 35px;
    align-items: center;
    justify-content: center;
    font-family: inherit;
    border-radius: 7px;
    background-color: #2044a8;
    color: white;
    font-weight: 300;
    &:hover {
        cursor: pointer;
        opacity: 0.9;
    }
`

export const PropertyGrid: React.FC<{onAddProperty: React.Dispatch<React.SetStateAction<boolean | undefined>>, title: string, columnData: ColDef[], rowData: {}[] }> = ({ onAddProperty, columnData, rowData }) => {

    const gridRef = useRef<AgGridReact>(null);
    const [inputVal, setInputVal] = useState("");

    const onFilterTextBoxChanged = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setInputVal(e.target.value)
        gridRef.current!.api.setGridOption(
            'quickFilterText',
            (e.target.value));
    }, []);

    const defaultColDef = useMemo<ColDef>(() => {
        return {
            editable: false,
            enableRowGroup: true,
            enablePivot: true,
            enableValue: true,
            sortable: true,
            resizable: true,
            filter: false,
            flex: 1,
            minWidth: 100,
            autoHeight: true
        };
    }, []);

    return (
        <div className="ag-theme-quartz" style={{ height: "70%", width: "100%" }}>
            <span style={{ width: "100%", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                <TextInput placeholder="Search..." name="" value={inputVal} onValueChange={(e) => onFilterTextBoxChanged(e)} />
                <AddListing onClick={() => onAddProperty(true)}>
                    <IoIosAddCircleOutline style={{ color: "white", marginRight: "5px", marginLeft: "5px"}} size={25} />
                    <p style={{marginRight: "10px"}}>Add Listing</p>
                </AddListing>
            </span>
            <VerticalSpacer size={"5"} />
            <AgGridReact
                ref={gridRef}
                getRowId={(params) => params.data.id}
                rowData={rowData}
                columnDefs={columnData}
                defaultColDef={defaultColDef} />
        </div>
    )
}