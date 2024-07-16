import { ColDef } from "ag-grid-community";
import { CustomCellRendererProps } from "ag-grid-react";
import { useState } from "react";
import { CiEdit } from "react-icons/ci";
import styled from "styled-components";
import { VerticalSpacer } from "../../components/atoms/FormFields";
import { PropertyDetailsForm } from "../../components/molecules/PropertyDetailsForm";
import { PropertyGrid } from "../../components/molecules/PropertyGrid";
// import { useGetAllPropertiesQuery } from "../../redux/api/apiSlice";
import { usePropertyStateDispatch } from "../property-slice/usePropertyStateDispatch";
import { useSelectedPropertyId } from "../property-slice/usePropertyStateSelectors";

const StyledIcon = styled.div`
  position: absolute;
  top: 20px;
  right: 20px;
  color: #2044a8;
  &:hover {
    cursor: pointer;
    color: black;
  }
`

export const PropertySection: React.FC = () => {
  // const {data} = useGetAllPropertiesQuery()
  // const propertiesFormatted = data?.map((property) => {
  //   const dateAddedMs = new Date(property.date_added);
  //   const year = dateAddedMs.getFullYear();
  //   const month = dateAddedMs.getUTCMonth();
  //   const day = dateAddedMs.getDay();
  //   return {
  //     id: property.listing_id,
  //     address: {},
  //     price: property.price,
  //     dateAdded: `${day}/${month}/${year}`,
  //     propertyType: property.property_type,
  //     country: "Spain",
  //     beds: property.number_of_bedrooms,
  //     bathrooms: property.number_of_bathrooms,
  //     photos: 0
  //   }
  // })
  const selectedPropertyId = useSelectedPropertyId();
  const { setSelectedPropertyId } = usePropertyStateDispatch();
  const [addProperty, setAddProperty] = useState<boolean>()

  const columns: ColDef[] = [
    { headerName: 'Listing ID', valueGetter: "node.id" },
    { field: 'address' },
    { field: 'price' },
    { field: 'dateAdded' },
    { field: 'propertyType' },
    { field: 'country' },
    { field: 'beds' },
    { field: 'bathrooms' },
    { field: 'photos' },
    { field: "edit", cellRenderer: (props: CustomCellRendererProps) => { return <CiEdit size={20} onClick={() => {
      setSelectedPropertyId({ id: Number(props.node.id) })
      console.log(props.node.id)
    }}
       /> } }
  ];

  const handleClose = () => {
    setAddProperty(false)
    setSelectedPropertyId({ id: null })

  }

  return (
    <div style={{ height: "100%", width: "100%", position: "relative", flexDirection: "column", display: "flex", alignItems: "center", padding: "25px" }}>
      {addProperty || selectedPropertyId !== null ? <PropertyDetailsForm handleClose={handleClose} /> : <></>}
      <p style={{ fontFamily: "Nunito Sans", fontSize: "35px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", margin: "0px" }}>Property Listings</p>
      <VerticalSpacer size={"5"} />
      <p style={{ paddingLeft: "1px", fontFamily: "Nunito Sans", fontSize: "18px", fontWeight: "400", color: "rgba(0, 0,0 ,0.3)", margin: "0px" }}>Edit, track and view your active and ended listings here</p>
      <VerticalSpacer />
      <PropertyGrid
        title="Properties"
        rowData={[]}
        columnData={columns}
        onAddProperty={setAddProperty}
      />
    </div>
  )
}