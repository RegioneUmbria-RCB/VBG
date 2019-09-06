
import {mergeMap, filter, map} from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';




import { InterventiLocaliService, InterventoDetailModel } from '../services';
import { UrlLocaliService, UrlServiziLocaliService } from '../../core/services/url';

@Component({
  selector: 'app-interventi-locali-detail',
  templateUrl: './interventi-locali-detail.component.html',
  styleUrls: ['./interventi-locali-detail.component.less']
})
export class InterventiLocaliDetailComponent implements OnInit, OnDestroy {


  caricamentoCompletato = false;
  intervento$: Observable<InterventoDetailModel>;

  private _subscription: Subscription;

  constructor(private service: InterventiLocaliService, private urlLocaliService: UrlLocaliService,
    private route: ActivatedRoute, private router: Router,
    private location: Location, private urlServizi: UrlServiziLocaliService) { }

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
    const url = this.urlLocaliService.url('/interventi-locali');
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

    const url = this.urlLocaliService.url('/procedimenti', [id]);

    this.router.navigate([url]/*, {
          queryParams: {
            intervento: this.route.snapshot.params['id']
          }
        }*/);
  }

}
