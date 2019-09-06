
import {mergeMap, filter, map} from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { Observable, Subscription } from 'rxjs';




import { InterventiRegionaliService, InterventoDetailModel } from '../services';
import { UrlLocaliService, UrlServiziRegionaliService } from '../../core/services/url';

@Component({
  selector: 'app-interventi-regionali-detail',
  templateUrl: './interventi-regionali-detail.component.html',
  styleUrls: ['./interventi-regionali-detail.component.less']
})
export class InterventiRegionaliDetailComponent implements OnInit, OnDestroy {



  caricamentoCompletato = false;
  intervento$: Observable<InterventoDetailModel>;

  private _subscription: Subscription;

  constructor(private service: InterventiRegionaliService, private urlLocaliService: UrlLocaliService,
              private route: ActivatedRoute, private router: Router,
              private location: Location, private urlServizi: UrlServiziRegionaliService) { }

  ngOnInit() {

    this.intervento$ = this.route.params.pipe(
      map(params => params['id']),
      filter(val => val),
      mergeMap(id => this.service.getById(id)),);

    this._subscription = this.intervento$.subscribe(() => this.caricamentoCompletato = true);
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  tornaIndietro() {
    const url = this.urlLocaliService.url('/interventi-regionali');
    const returnTo = this.route.snapshot.queryParams['returnTo'];

    if (returnTo) {
      this.router.navigate([url], {
        queryParams: {
          open: returnTo
        }
      });
    } else {
      this.location.back();
    }
  }

  download($event: string) {
    const url = this.urlServizi.url('download', [$event]);

    window.open(url);
  }

  onEndoprocedimentoSelezionato($event: any) {

    let id = $event.id;

    if ($event.regionale) {
      id = 'R-' + id;
    }

    const url = this.urlLocaliService.url('/procedimenti-regionali', [id]);

    this.router.navigate([url]/*, {
      queryParams: {
        intervento: this.route.snapshot.params['id']
      }
    }*/);
  }
}
