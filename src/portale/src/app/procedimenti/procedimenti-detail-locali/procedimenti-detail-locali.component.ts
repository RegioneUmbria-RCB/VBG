
import {mergeMap, filter, map} from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

import {ProcedimentoDetailModel, ProcedimentiBaseService, ProcedimentiServiceFactory} from '../services';
import { UrlServiziLocaliService } from '../../core';

@Component({
  selector: 'app-procedimenti-detail-locali',
  templateUrl: './procedimenti-detail-locali.component.html',
  styleUrls: ['./procedimenti-detail-locali.component.less']
})
export class ProcedimentiDetailLocaliComponent implements OnInit, OnDestroy {

  caricamentoCompletato = false;
  procedimento$: Observable<ProcedimentoDetailModel>;

  private _subscription: Subscription;
  private _baseService: ProcedimentiBaseService;

  constructor(private serviceFactory: ProcedimentiServiceFactory, private router: Router, 
              private route: ActivatedRoute, private urlServizi: UrlServiziLocaliService) {

  }

  ngOnInit() {
    this.procedimento$ = this.route.params.pipe(
                                map(params => params['id']),
                                filter(val => val),
                                map(id => {
                                  return {
                                    id: id,
                                    regionale: id.indexOf('R-') === 0
                                  };
                                }),
                                mergeMap(id => {

                                  console.log(id);
                                  const service = this.serviceFactory.create(!id.regionale);

                                  return service.getById(id.id);
                                }),);

    this._subscription = this.procedimento$.subscribe(() => this.caricamentoCompletato = true);
  }

  

  download($event: string) {
    const url = this.urlServizi.url('download', [$event]);

    window.open(url);
  }

  ngOnDestroy() {
    this._subscription.unsubscribe();
  }
}
