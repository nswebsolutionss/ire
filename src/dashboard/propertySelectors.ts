import { useSelector } from "react-redux"
import { PropertyState, propertiesSelector } from "./property/propertySlice"

export const useAllProperties = (): PropertyState[] => {
  return useSelector(propertiesSelector.selectAll)
}
