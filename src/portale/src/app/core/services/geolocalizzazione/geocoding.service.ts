
import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { LocationModel } from './location.model';
import {LatLngBounds} from 'leaflet';

@Injectable()
export class GeocodingService {

  private gmapsUrl = 'http://maps.googleapis.com/maps/api/geocode/json?address=';

  constructor(private client: HttpClient) { }

  locate(indirizzo: string) {
    return this.client.get<any>(this.gmapsUrl + encodeURIComponent(indirizzo)).pipe(
    map(result => {
      if (result.status !== 'OK') {
        return null;
      }

      const location = new LocationModel();
      location.address = result.results[0].formatted_address;
      location.latitude = result.results[0].geometry.location.lat;
      location.longitude = result.results[0].geometry.location.lng;

      const  viewPort = result.results[0].geometry.viewport;
      location.viewBounds = new LatLngBounds(
        {
          lat: viewPort.southwest.lat,
          lng: viewPort.southwest.lng},
        {
          lat: viewPort.northeast.lat,
          lng: viewPort.northeast.lng
        });

      return location;
    }));
  }
}
