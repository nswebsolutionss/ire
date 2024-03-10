import { createSelector } from "reselect";
import { Address, PropertyState, State, statesSelector } from "./propertySlice"
import { TypedUseSelectorHook, useSelector } from "react-redux";
import { store } from "../../redux/store";
import { GridRowsProp } from "@mui/x-data-grid";

export type RootState = ReturnType<typeof store.getState>

interface PartialRootState {
  property: State
}

export const createPropertySelector = createSelector.withTypes<PartialRootState>();
export const useStateSelector: TypedUseSelectorHook<PartialRootState> = useSelector;

export const useAllProperties = (): PropertyState[] => {
  return useStateSelector(statesSelector.allProperties)
}

export const useAllPropertiesRowFormatted = () => {
  const allProperties = useStateSelector(statesSelector.allProperties);
  return allProperties.map(property => {
    const dateAddedMs = new Date(property.dateAdded);
    const year = dateAddedMs.getFullYear();
    const month = dateAddedMs.getUTCMonth();
    const day = dateAddedMs.getDay();

    const options = { maximumFractionDigits: 3 }
    const formattedPrice = Intl.NumberFormat("en-GB", options).format(Number(property.price));

    return {
      id: property.id,
      address: formatAddressForRow(property.address),
      price: property.userCurrency + " " + formattedPrice,
      dateAdded: `${day}/${month}/${year}`,
      propertyType: property.propertyType,
      country: property.address.country,
      beds: property.numberOfBedrooms,
      bathrooms: property.numberOfBathrooms,
      photos: property.images.length
    }

  })
}

export const useSelectedPropertyNullable = (): PropertyState | null => {
  console.log(useStateSelector(statesSelector.selectedPropertyById))
  return useStateSelector(statesSelector.selectedPropertyById)
}

export const useFormattedCurrency = (propertyId: number): string | null => {
  const property = useStateSelector((state) => statesSelector.propertyById(state, propertyId));
  const unformattedPrice = property.price;
  const options = { maximumFractionDigits: 3 }
  const formattedPrice = Intl.NumberFormat("en-GB", options).format(Number(unformattedPrice));

  return property.userCurrency + " " + formattedPrice;
}

export const useFormattedDate = (propertyId: number): string | null => {
  const property = useStateSelector((state) => statesSelector.propertyById(state, propertyId));
  const dateAddedMs = new Date(property.dateAdded);
  const year = dateAddedMs.getFullYear();
  const month = dateAddedMs.getUTCMonth();
  const day = dateAddedMs.getDay();
  return `${day}/${month}/${year}`
}

export const useFormattedDescription = (propertyId: number): string | null => {
  const property = useStateSelector((state) => statesSelector.propertyById(state, propertyId));
  const description = property.description;
  const regex = /(<([^>]+)>)/gi;
  return description.replace(regex, "");
}

export const useSelectedPropertyId = (): number | null => {
  return useStateSelector(statesSelector.selectedPropertyId)
}

const formatAddressForRow = (address: Address) => {
  return address.houseNumber + " " + address.line2 + ", " + address.town + ", " + address.postcode
}



