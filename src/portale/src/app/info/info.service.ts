import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UrlServiziLocaliService } from '../core/services/url';
import { InfoItemModel, InfoItemModelWrapper } from './info-item.model';
import { Observable, of } from 'rxjs';
import { switchMap, map, shareReplay } from 'rxjs/operators';

@Injectable()
export class InfoService {

    infoSportello$: Observable<InfoItemModel[]> = null;

    constructor(private urlService: UrlServiziLocaliService, private http: HttpClient) { }

    getList(): Observable<InfoItemModel[]> {

        const url = this.urlService.url('infosportello');

        if (!this.infoSportello$) {
            this.infoSportello$ = this.http.get<InfoItemModelWrapper>(url).pipe(
                map(x => x.items),
                shareReplay(1)
            );
        }

        return this.infoSportello$;
    }


    getById(id: number): Observable<InfoItemModel> {
        return this.getList().pipe(
            switchMap(x => of(x.filter(i => i.id === id).shift()))
        );
    }

}
