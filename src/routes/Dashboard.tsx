import { IoMdAddCircleOutline } from "react-icons/io";
import { useDispatch } from "react-redux";
import { DashboardNavBar } from '../components/dashboard/DashboardNavBar';
import { Property } from "../components/dashboard/properties/Property";
import { useAllProperties, useSelectedNavElement, useSelectedPropertyId } from "../dashboard/propertySelectors";
import { NavElement, PropertyState, upsertProperty } from "../dashboard/propertySlice";
import { EditProperty } from "../components/dashboard/properties/EditProperty";


let propertyId = 1;

function Dashboard() {
  const dispatch = useDispatch();


  const addProperty = () => {
    dispatch(
      upsertProperty({
        id: propertyId++,
        location: "Alicate",
        propertyType: "Detached Villa",
        numberOfBedrooms: 3,
        numberOfBathrooms: 2,
        description: "Constance Le Prince Maurice is a design feat planned by designers, Jean-Marc Eynaud, and David Edwards. It offers the most sentimental water estates in Mauritius and best in class, rooms when it comes to comfort and Jean-Marc Eynaud, and David Edwards. It offers the most sentimental water estates in Mauritius and best in class, rooms when it comes to comfort and rooms when it comes to comfort and Jean-Marc Eynaud, and David Edwards. It offers the most sentimental water estates in Mauritius and best in class, rooms when it comes to comfort and ",
        price: "$500,000",
      })
    )
  }

  const PropertySection: React.FC = () => {
    return (
      <>
        <IoMdAddCircleOutline size={30} style={{ position: "absolute", top: "30px", right: "30px" }} onClick={() => { addProperty() }} />
        {useAllProperties().map(property => {
          return createProperty(property)
        })}
      </>
    )
  }

  return (
    <div style={{ width: "100vw", height: "100vh", display: "flex", fontFamily: "Inter", fontWeight: "500" }}>
      <DashboardNavBar />
      {useSelectedPropertyId() !== null ? <EditProperty /> : <></>}
      <div style={{ height: "100%", width: "100%", backgroundColor: "#f0f0f0", padding: "25px", boxSizing: "border-box" }}>
        <div style={{ height: "100%", width: "100%", backgroundColor: "white", padding: "25px", boxSizing: "border-box", display: "flex", justifyContent: "space-around", flexWrap: "wrap", overflowY: "auto" }}>
          {useSelectedNavElement() === NavElement.Properties ? <PropertySection/> : <></>}
        </div>
      </div>
    </div>
  );

  function createProperty(property: PropertyState) {
    return (
      <Property
        propertyId={property.id}
        location={property.location}
        propertyType={property.propertyType}
        numberOfBedrooms={property.numberOfBedrooms}
        numberOfBathrooms={property.numberOfBathrooms}
        description={property.description}
        price={property.price}
      />
    )
  }
}

export default Dashboard;