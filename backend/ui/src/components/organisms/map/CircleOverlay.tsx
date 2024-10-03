/* eslint-disable complexity */
import {
    forwardRef,
    useContext,
    useEffect,
    useImperativeHandle,
    useRef
  } from 'react';
  
  import type {Ref} from 'react';
  import {GoogleMapsContext, latLngEquals} from '@vis.gl/react-google-maps';
import { getDistanceFromLatLonInMeters } from './AutoCompleteMapHandler';
  
  type CircleEventProps = {
    onRadiusChanged?: (r: ReturnType<google.maps.Circle['getRadius']>) => void;
  };
  
  export type CircleProps = google.maps.CircleOptions & CircleEventProps;
  
  export type CircleRef = Ref<google.maps.Circle | null>;
  
  function useCircle(props: CircleProps) {
    const {
      onRadiusChanged,
      radius,
      center,
      ...circleOptions
    } = props;
    // This is here to avoid triggering the useEffect below when the callbacks change (which happen if the user didn't memoize them)
    const callbacks = useRef<Record<string, (e: unknown) => void>>({});
    Object.assign(callbacks.current, {
      onRadiusChanged,
    });
  
    const circle = useRef(new google.maps.Circle()).current;
    // update circleOptions (note the dependencies aren't properly checked
    // here, we just assume that setOptions is smart enough to not waste a
    // lot of time updating values that didn't change)
    circle.setOptions(circleOptions);
  
    useEffect(() => {
      if (!center) return;
      if (!latLngEquals(center, circle.getCenter())) circle.setCenter(center);
    }, [center]);
  
    useEffect(() => {
      if (radius === undefined || radius === null) return;
      if (radius !== circle.getRadius()) circle.setRadius(radius);
    }, [radius]);
  
    const map = useContext(GoogleMapsContext)?.map;
  
    // create circle instance and add to the map once the map is available
    useEffect(() => {
      if (!map) {
        if (map === undefined)
          console.error('<Circle> has to be inside a Map component.');
  
        return;
      }
      circle.setMap(map);
  
      return () => {
        circle.setMap(null);
      };
    }, [map]);
  
    // attach and re-attach event-handlers when any of the properties change
    useEffect(() => {
      if (!circle || !map) return;
  
      // Add event listeners
      const gme = google.maps.event;

      gme.addListener(circle, 'radius_changed', () => {
        const newRadius = circle.getRadius();
        const circleBounds = circle.getBounds()
        if(circleBounds) {
            map.fitBounds(circleBounds);

        }
        callbacks.current.onRadiusChanged?.(newRadius);
      });
  
      return () => {
        gme.clearInstanceListeners(circle);
      };
    }, [circle]);
  
    return circle;
  }
  
  /**
   * Component to render a circle on a map
   */
  export const CircleOverlay = forwardRef((props: CircleProps, ref: CircleRef) => {
    const circle = useCircle(props);
  
    useImperativeHandle(ref, () => circle);
  
    return null;
  });
  