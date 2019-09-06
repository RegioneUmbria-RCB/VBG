
import {mergeMap, filter, map} from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { ProcedimentoDetailModel, ProcedimentiBaseService, ProcedimentiServiceFactory } from '../services';
import { Subscription } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { UrlServiziLocaliService, UrlServiziRegionaliService } from '../../core/services';

@Component({
  selector: 'app-procedimenti-detail-regionali',
  templateUrl: './procedimenti-detail-regionali.component.html',
  styleUrls: ['./procedimenti-detail-regionali.component.less']
})
export class ProcedimentiDetailRegionaliComponent implements OnInit, OnDestroy {

  caricamentoCompletato = false;
  procedimento$: Observable<ProcedimentoDetailModel>;

  private _subscription: Subscription;
  private _baseService: ProcedimentiBaseService;

  constructor(private serviceFactory: ProcedimentiServiceFactory, private router: Router, 
              private route: ActivatedRoute, private urlServizi: UrlServiziRegionaliService) {

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
                                  const service = this.serviceFactory.create(false);

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
