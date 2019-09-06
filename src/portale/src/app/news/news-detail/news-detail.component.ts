
import { mergeMap, map, finalize, switchMap, tap } from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Observable, Subscription } from 'rxjs';

import { NewsService } from '../news.service';
import { NewsDetailModel } from '../news-detail.model';

@Component({
    selector: 'app-news-detail',
    templateUrl: './news-detail.component.html'
})
export class NewsDetailComponent implements OnInit {


    caricamentoCompletato = false;
    news$: Observable<NewsDetailModel>;

    constructor(private service: NewsService, private currRoute: ActivatedRoute, private location: Location) { }

    ngOnInit() {

        this.news$ = this.currRoute.params.pipe(
            tap(() => this.caricamentoCompletato = false),
            map(pars => pars['id']),
            switchMap(id => this.service.getById(id).pipe(
                finalize(() => this.caricamentoCompletato = true)
            )),
        );
    }


    tornaIndietro() {
        this.location.back();
    }
}
