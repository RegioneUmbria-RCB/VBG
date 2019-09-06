
import {mergeMap, map} from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';


import { TrasparenzaService } from '../trasparenza.service';
import { LocationModel } from '../../core/services/geolocalizzazione';

@Component({
  selector: 'app-trasparenza-detail',
  templateUrl: './trasparenza-detail.component.html',
  styleUrls: ['./trasparenza-detail.component.less']
})
export class TrasparenzaDetailComponent implements OnInit {

  public caricamentoInCorso = true;
  public dataSource: any;
  public markers: LocationModel[];
  public nascondiMappa = false;

  constructor(private route: ActivatedRoute, private service: TrasparenzaService, private location: Location) { }

  ngOnInit() {
    this.route.params.pipe(
      map(params => +params['id']),
      mergeMap(id => this.service.getById(id)),)
      .subscribe(istanza => {

        this.dataSource = istanza;

        /*
        if (this.dataSource.localizzazioni && this.dataSource.localizzazioni.length > 0) {

          Observable.forkJoin(
            this.dataSource.localizzazioni.map(i => this.geocoder.locate(i.indirizzo + ', ' + i.comune)))
            .subscribe(
            locs => {
              locs.forEach((l, i) => {
                this.dataSource.localizzazioni[i].geo = l;
              });

              this.markers = this.dataSource.localizzazioni
                .filter(l => l.geo != null)
                .map(l => l.geo);

              if (this.markers.length === 0) {
                this.nascondiMappa = true;
              }
            }
            );
        } else {
          this.nascondiMappa = true;
        }
        */
        this.caricamentoInCorso = false;
      });
  }


  tornaIndietro() {
    this.location.back();
  }
}
