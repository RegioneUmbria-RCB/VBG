import { map, finalize, switchMap, tap } from "rxjs/operators";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Location, AsyncPipe } from "@angular/common";
import { Observable } from "rxjs";

import { NewsService } from "../news.service";
import { NewsDetailModel } from "../news-detail.model";
import { StripHtmlPipe } from "../../core/pipes/strip-html.pipe";
import { ShareSocialComponent } from "../../core/components/share-social/share-social.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-news-detail",
    templateUrl: "./news-detail.component.html",
    standalone: true,
    imports: [
    InnerPageLayoutComponent,
    ShareSocialComponent,
    AsyncPipe,
    StripHtmlPipe
],
})
export class NewsDetailComponent implements OnInit {
    caricamentoCompletato = false;
    news$: Observable<NewsDetailModel>;

    constructor(
        private service: NewsService,
        private currRoute: ActivatedRoute,
        private location: Location
    ) { }

    ngOnInit(): void {
        this.news$ = this.currRoute.params.pipe(
            tap(() => (this.caricamentoCompletato = false)),
            map((pars) => <string>pars["id"]),
            switchMap((id) =>
                this.service
                    .getById(id)
                    .pipe(finalize(() => (this.caricamentoCompletato = true)))
            )
        );
    }

    tornaIndietro(): void {
        this.location.back();
    }
}
