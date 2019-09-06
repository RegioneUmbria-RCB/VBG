
import { refCount, publishReplay, map, shareReplay } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { TopNewsModel } from './top-news.model';
import { NewsDetailModel } from './news-detail.model';
import { UrlServiziLocaliService } from '../core';
import { ServiceResponse } from '../core/models';

@Injectable()
export class NewsService {

    topNews$: Observable<TopNewsModel[]> = null;
    allNews$: Observable<TopNewsModel[]> = null;

    constructor(private urlServiziLocaliService: UrlServiziLocaliService, private http: HttpClient) { }

    getTop(): Observable<TopNewsModel[]> {

        const url = this.urlServiziLocaliService.url('news', ['top']);

        if (this.topNews$ == null) {
            this.topNews$ = this.http.get<ServiceResponse<TopNewsModel[]>>(url).pipe(
                map(x => x.items),
                shareReplay(1));
        }

        return this.topNews$;
    }

    getList(): Observable<TopNewsModel[]> {
        const url = this.urlServiziLocaliService.url('news');

        if (this.allNews$ == null) {
            this.allNews$ = this.http.get<ServiceResponse<TopNewsModel[]>>(url).pipe(
                map(x => x.items),
                shareReplay(1)
            );
        }

        return this.allNews$;
    }


    getById(id: string): Observable<NewsDetailModel> {
        const url = this.urlServiziLocaliService.url('news', [id]);

        return this.http.get<ServiceResponse<NewsDetailModel>>(url).pipe(
            map(item => item.items));
    }
}
