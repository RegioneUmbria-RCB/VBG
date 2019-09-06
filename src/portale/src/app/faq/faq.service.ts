
import { refCount, publishReplay, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TopFaqModel } from './top-faq.model';
import { UrlServiziLocaliService } from '../core/services/url';
import { ServiceResponse } from '../core/models';
import { ModuloFaqModel } from './modulo-faq.model';

@Injectable()
export class FaqService {

    fullList$: Observable<ModuloFaqModel[]> = null;

    constructor(private urlServiziLocaliService: UrlServiziLocaliService, private http: HttpClient) { }

    getTop(): Observable<TopFaqModel[]> {
        const url = this.urlServiziLocaliService.url('faq', ['top']);

        return this.http.get<ServiceResponse<TopFaqModel[]>>(url).pipe(
            map(data => data.items)
        );
    }

    getList(): Observable<ModuloFaqModel[]> {
        const url = this.urlServiziLocaliService.url('faqpermodulo');

        if (this.fullList$ == null) {
            this.fullList$ = this.http.get<ServiceResponse<ModuloFaqModel[]>>(url).pipe(
                map(x => x.items),
                publishReplay(1),
                refCount());
        }

        return this.fullList$;
    }

}
