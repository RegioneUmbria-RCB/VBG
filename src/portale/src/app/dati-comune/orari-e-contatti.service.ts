import { Injectable } from '@angular/core';
import { UrlServiziLocaliService } from '../core/services/url';
import { Observable, of } from 'rxjs';
import { OrariEContatti, OrariEContattiItems } from './orari-e-contatti.modal';
import { map, tap, shareReplay } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

const CACHE_SIZE = 1;

@Injectable()
export class OrariEContattiService {

    private _cachedOrariEContatti$: Observable<OrariEContatti>;

    constructor(private http: HttpClient, private urlServiziLocaliService: UrlServiziLocaliService) { }

    get(): Observable<OrariEContatti> {

        if (!this._cachedOrariEContatti$) {
            this._cachedOrariEContatti$ = this.getUncached().pipe(
              shareReplay(CACHE_SIZE)
            );
          }

          return this._cachedOrariEContatti$;
    }

    getUncached(): Observable<OrariEContatti> {
        const url = this.urlServiziLocaliService.url('orariecontatti');

        console.log('Orari e contatti riletti da api');

        return this.http.get<OrariEContattiItems>(url).pipe(
            map(x => x.items),
        );
    }
}
