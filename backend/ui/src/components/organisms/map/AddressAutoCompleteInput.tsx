import React, {useRef, useEffect, useState, useMemo} from 'react';
import {useMapsLibrary} from '@vis.gl/react-google-maps';
import styled from 'styled-components';
import { Box } from '../../atoms';
import { useSearchPropertiesStateDispatch } from 'src/redux/search/useSearchPropertiesStateDispatch';
import { useAddress } from 'src/redux/search/useSearchPropertiesStateSelectors';

const Input = styled.input`
  padding-left: 10px;
  box-sizing: border-box;
  border: 1px solid grey;
  border-radius: 5px;
  width: 100%;
  height: 2.7em;
  outline: none;
  padding: none;
  margin: none;
  font-size: inherit;
  &:focus {
    outline: 2px solid #B7ECFD;
  }
`

export const AddressAutoCompleteInput = () => {
  const {setAddress} = useSearchPropertiesStateDispatch()
  const address = useAddress()
  const [placeAutocomplete, setPlaceAutocomplete] =
    useState<google.maps.places.Autocomplete | null>(null);
  const inputRef = useRef<HTMLInputElement>(null);
  const places = useMapsLibrary('places');

  useEffect(() => {
    if (!places || !inputRef.current) return;

    const options = {
      fields: ['geometry', 'name', 'formatted_address']
    };

 
    setPlaceAutocomplete(new places.Autocomplete(inputRef.current, options));


  }, [places]);

  useEffect(() => {
    if (!placeAutocomplete) return;
    placeAutocomplete.addListener('place_changed', () => {

      setAddress(placeAutocomplete.getPlace());
    });
  }, [setAddress, placeAutocomplete]);

  return (
    <Box>
      <Input ref={inputRef} />
    </Box>
  );
};
