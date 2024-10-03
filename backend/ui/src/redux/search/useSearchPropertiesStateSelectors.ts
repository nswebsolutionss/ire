import { TypedUseSelectorHook, useSelector } from "react-redux";
import { store } from "../../redux/store";
import { createSelector } from "reselect";
import { searchPropertiesSelector } from "./searchPropertiesSlice";
import { Position, PropertyType } from "./SearchPropertyTypes";

export type RootState = ReturnType<typeof store.getState>

export const createSearchPropertiesSelector = createSelector.withTypes<RootState>();
export const useSearchPropertiesSelector: TypedUseSelectorHook<RootState> = useSelector;


export const useAddress = (): google.maps.places.PlaceResult | null => {
  return useSearchPropertiesSelector(searchPropertiesSelector.address)
}

export const useRadius = (): string => {
  return useSearchPropertiesSelector(searchPropertiesSelector.radius)
}

export const useMinBedrooms = (): string => {
  return useSearchPropertiesSelector(searchPropertiesSelector.minBedrooms)
}

export const useMaxBedrooms = (): string => {
  return useSearchPropertiesSelector(searchPropertiesSelector.maxBedrooms)
}

export const useMinPrice = (): string => {
  return useSearchPropertiesSelector(searchPropertiesSelector.minPrice)
}

export const useMaxPrice = (): string => {
  return useSearchPropertiesSelector(searchPropertiesSelector.maxPrice)
}

export const usePropertyTypes = (): PropertyType[] => {
  return useSearchPropertiesSelector(searchPropertiesSelector.propertyTypes)
}

export const usePosition = (): Position | null => {
  return useSearchPropertiesSelector(searchPropertiesSelector.position)
}

export const useFormattedBedrooms = () => {
  const min = useMinBedrooms()
  const max = useMaxBedrooms()

  if(min === "No min" && max === "No max") {
    return "Any beds"
  }
  else if(min === "Studio" && max == "No max") {
    return "Studio min"
  }
  else if(min !== "No min" && max === "No max") {
    return min + " bed min";
  }
  else if(min === "No min" && max !== "No max") {
    return max + " bed max"
  }
  else if(min === max) {
    return min
  }
  else {
    return min + " - " + max + " bed"
  }
}

export const useFormattedPrice = () => {
  const min = useMinPrice()
  const max = useMaxPrice()

  if(min === "No min" && max === "No max") {
    return "Any price"
  }
  else if(min !== "No min" && max === "No max") {
    return "£" + formatPrice(min) + ' min';
  }
  else if(min === "No min" && max !== "No max") {
    return "£" + formatPrice(max) + " max"
  }
  else if(min === max) {
    return "£" + formatPrice(min)
  }
  else {
    return "£" + formatPrice(min) + " - " + "£" +  formatPrice(max)
  }
  
}

const formatPrice = (val: string) => {
  const strippedCommas = val.replace(",", "")
  const strippedCurrencyAndCommas = strippedCommas.replace("£", "")
  const foo = parseInt(strippedCurrencyAndCommas)
    if (foo < 1e3) return foo.toString();
    if (foo >= 1e3 && foo < 1e6) return (foo / 1e3).toFixed(0).toString() + "k";
    if (foo >= 1e6 && foo < 1e9) return (foo / 1e6).toFixed(0).toString() + "m";
    
}

