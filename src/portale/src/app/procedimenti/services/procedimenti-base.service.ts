
import {from as observableFrom,  Observable } from 'rxjs';

import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ProcedimentoTopModel } from './procedimento-top.model';
import { ProcedimentoDetailModel } from './procedimento-detail.model';
import { BaseUrlservice } from '../../core/services/url';
import { ServiceResponse } from '../../core/models';

@Injectable()
export class ProcedimentiBaseService {

  procedimentiTop: ProcedimentoTopModel[] = null;

  constructor(private urlService: BaseUrlservice, private http: HttpClient) { }

  getTop(): Observable<ProcedimentoTopModel[]> {

    const url = this.urlService.url('procedimenti', ['top']);

    if (this.procedimentiTop !== null) {
      return observableFrom([this.procedimentiTop]);
    }

    return this.http.get<ServiceResponse<ProcedimentoTopModel[]>>(url).pipe(map(x => x.items));
  }


  getById(id: string): Observable<ProcedimentoDetailModel> {
    const url = this.urlService.url('procedimenti', [id]);

    return this.http.get<ServiceResponse<ProcedimentoTopModel[]>>(url).pipe(map(x => x.items));
  }

}
