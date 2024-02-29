import { useSelector } from "react-redux"
import { NavElement, PropertyState, State, propertiesSelector, statesSelector } from "./propertySlice"
import { useStateSelector } from "./useStateSelector"
import { error } from "console"

export const useAllProperties = (): PropertyState[] => {
  return useStateSelector(propertiesSelector.selectAll)
}

const getSelectedPropertyNullable = (state: State): PropertyState | null => {
  const selectedPropertyId = statesSelector.selectedPropertyId(state);

  if(selectedPropertyId === null)
  {
    return null;
  }

  const property = propertiesSelector.selectById(state, selectedPropertyId)
  return property;
}

export const useSelectedPropertyId = (): number | null => {
  return useStateSelector(statesSelector.selectedPropertyId)
}

export const useSelectedNavElement = (): NavElement | null => {
  return useStateSelector(statesSelector.selectedNavElement)
}

export const useSelectedProperty = () => {
  return useStateSelector(getSelectedPropertyNullable);
}

