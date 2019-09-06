
import {forkJoin as observableForkJoin,  Observable } from 'rxjs';

import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


import { ConfigurationService } from '../core/services/configuration';
import { ParametriRicercaTrasparenzaModel } from './parametri-ricerca-trasparenza.model';


@Injectable()
export class TrasparenzaService {

  constructor(private configurationService: ConfigurationService, private client: HttpClient) { }

  getList(filtri: ParametriRicercaTrasparenzaModel) {
    const config = this.configurationService.getConfiguration().backend;
    const parts = [
      'istanze',
      'alias',
      config.alias,
      'software',
      config.software,
      'istanze'
    ];
    const url = config.urlVisura + '/' + parts.join('/');

    return this.client.get<any>(url, { params: filtri.toHttpParams() });
  }

  getById(id: number): Observable<any> {
    const config = this.configurationService.getConfiguration().backend;
    const parts = [
      'istanze',
      'alias',
      config.alias,
      'software',
      config.software,
      'istanze',
      id.toString()
    ];
    const url = config.urlVisura + '/' + parts.join('/');

    const datiGenerali = this.client.get<any>(url + '/dati-generali');
    const localizzazioni = this.client.get<any>(url + '/localizzazioni');
    const autorizzazioni = this.client.get<any>(url + '/autorizzazioni');

    return observableForkJoin([datiGenerali, localizzazioni, autorizzazioni]).pipe(
      map(responses => {
        const cls = this.propToClass(responses[0]);

        cls.localizzazioni = responses[1];
        cls.autorizzazioni = responses[2];

        return cls;
      }));
  }

  private propToClass(propArray: any[]) {
    const obj = {
      localizzazioni: [],
      autorizzazioni: []
    };

    for (let i = 0; i < propArray.length; i += 1) {

      const item = propArray[i];

      obj[item.nome] = item.valore;
    }

    return obj;

  }

}
