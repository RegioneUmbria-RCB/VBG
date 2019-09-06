
import {of as observableOf, from as observableFrom,  Observable } from 'rxjs';

import {catchError, map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';





import { ServiceResponse } from '../../core/models';
import { InterventoTopModel } from './intervento-top.model';
import { InterventoCercato } from './intervento-cercato.model';
import { InterventiTreeItemModel } from './interventi-tree-item.model';
import { InterventoDetailModel } from './intervento-detail.model';
import { BaseUrlservice } from '../../core';

@Injectable()
export class InterventiBaseService {

  interventiTop: InterventoTopModel[] = null;

  constructor(private urlService: BaseUrlservice, private http: HttpClient) { }

  getTop(): Observable<InterventoTopModel[]> {

    const url = this.urlService.url('interventi', ['top']);

    if (this.interventiTop !== null) {
      return observableFrom([this.interventiTop]);
    }

    return this.http.get<ServiceResponse<InterventoTopModel[]>>(url).pipe(map(x => x.items));
  }

  cerca(testo: string): Observable<InterventoCercato[]> {
    const url = this.urlService.url('interventi', ['cerca', encodeURIComponent(testo).replace('.', '%2E')]);

    return this.http.get<ServiceResponse<InterventoCercato[]>>(url).pipe(
      map(x => x.items),
      catchError(res => observableOf([])),);

  }

  getSottonodi(idNodo: string): Observable<InterventiTreeItemModel[]> {

    const url = this.urlService.url('interventi', ['sottonodi', idNodo]);

    return this.http.get<ServiceResponse<InterventiTreeItemModel[]>>(url).pipe(
      map(x => x.items));
  }


  getGerarchia(idNodo: string) {
    const url = this.urlService.url('interventi', ['gerarchia', idNodo]);

    return this.http.get<ServiceResponse<string[]>>(url).pipe(
      map(x => x.items));
  }

  getTreeItemById(idNodo: string): Observable<InterventiTreeItemModel> {
    const url = this.urlService.url('interventi', [idNodo]);

    if (idNodo === '-1') {
      return observableOf(new InterventiTreeItemModel());
    }
    return this.http.get<ServiceResponse<any>>(url).pipe(
      map(x => {
        const item = new InterventiTreeItemModel();

        item.id = x.items.id;
        item.text = x.items.nome;
        item.hasChilds = true;
        item.percorso = x.items.percorso;

        return item;
      }));
  }

  getById(id: string): Observable<InterventoDetailModel> {
    const url = this.urlService.url('interventi', [id]);

    return this.http.get<ServiceResponse<InterventoDetailModel>>(url).pipe(
      map(x => x.items));
  }

  urlDownload(id: string) {

  }
}
