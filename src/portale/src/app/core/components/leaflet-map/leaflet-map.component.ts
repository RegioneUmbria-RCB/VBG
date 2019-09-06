import { Component, OnInit, Input } from '@angular/core';
import { GeocodingService, LocationModel } from '../../services/geolocalizzazione';
import * as L from 'leaflet';

@Component({
  selector: 'app-leaflet-map',
  templateUrl: './leaflet-map.component.html',
  styleUrls: ['./leaflet-map.component.less']
})
export class LeafletMapComponent implements OnInit {

  options: L.MapOptions;
  center: L.LatLng;
  fitBounds: L.LatLngBounds;
  _markers: L.Marker[] = null;


  @Input()
  public set markers(v: LocationModel[]) {

    if (!v || v.length === 0) {
      return;
    }

    if (this._markers == null) {
      const m = v[0];

      this.center = new L.LatLng(m.latitude, m.longitude);
      this.fitBounds = m.viewBounds;
      this._markers = [];
    }

    this._markers = v.map(m => {
      return L.marker([m.latitude, m.longitude], {
        icon: L.icon({
          iconAnchor: [ 13, 41 ],
          iconUrl: 'assets/marker-icon.png',
          shadowUrl: 'assets/marker-shadow.png'
        })
      });
    });
  }

  constructor(private geocoder: GeocodingService) { }

  ngOnInit() {
    this.options = {
      layers: [
        L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>, ' +
          'Tiles courtesy of <a href="http://hot.openstreetmap.org/" target="_blank">Humanitarian OpenStreetMap Team</a>'
        })
      ],
      zoom: 5
    };

  }

}
