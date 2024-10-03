import {useMap} from '@vis.gl/react-google-maps';
import React, {useEffect} from 'react';
import { useSearchPropertiesStateDispatch } from 'src/redux/search/useSearchPropertiesStateDispatch';

interface Props {
  place: google.maps.places.PlaceResult | null;
}

export function getDistanceFromLatLonInMeters(lat1: number, lon1: number, lat2: number, lon2: number) {
  var R = 6371; // Radius of the earth in km
  var dLat = deg2rad(lat2-lat1);  // deg2rad below
  var dLon = deg2rad(lon2-lon1); 
  var a = 
    Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
    Math.sin(dLon/2) * Math.sin(dLon/2)
    ; 
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
  var d = R * c; // Distance in km
  return d * 1000;
}

function deg2rad(deg: number) {
  return deg * (Math.PI/180)
}

export const AutoCompleteMapHandler = ({place}: Props) => {
  const map = useMap();
  const {setPosition, setRadius} = useSearchPropertiesStateDispatch()
  useEffect(() => {
    if (!map || !place) return;

    if (place.geometry?.viewport) {
      const centerLat = place.geometry.viewport.getCenter().lat();
      const centerLon = place.geometry.viewport.getCenter().lng();
      const northEastLat = place.geometry.viewport.getNorthEast().lat();
      const northEastlon = place.geometry.viewport.getNorthEast().lng();

      setPosition({
        lat: centerLat,
        lon: centerLon
      })

      const distance = getDistanceFromLatLonInMeters(centerLat, centerLon, northEastLat, northEastlon)
      if(distance > 50000) {
        setRadius("")
      }
      else {
        setRadius(distance.toString())

      }

      map.fitBounds(place.geometry?.viewport);
    }
  }, [map, place]);

  return null;
};

