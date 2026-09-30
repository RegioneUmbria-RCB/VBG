import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { NewsService, TopNewsModel } from "../../news";
import { UrlLocaliService } from "../../core/services/url";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { StripHtmlPipe } from "../../core/pipes/strip-html.pipe";
import { RouterLink } from "@angular/router";
import { AsyncPipe } from "@angular/common";

@Component({
    selector: "app-home-news",
    templateUrl: "./home-news.component.html",
    standalone: true,
    imports: [
    RouterLink,
    AsyncPipe,
    StripHtmlPipe
],
})
export class HomeNewsComponent implements OnInit {
    @Input() public newsPresenti = true;

    @Output() public newsPresentiChange = new EventEmitter<boolean>();

    public topNews$: Observable<TopNewsModel[]>;
    public urlDettaglioNews: string;

    constructor(
        private newsService: NewsService,
        urlLocaliService: UrlLocaliService
    ) {
        this.urlDettaglioNews = urlLocaliService.url("/news");
    }

    ngOnInit(): void {
        this.topNews$ = this.newsService
            .getTop()
            .pipe(tap((x) => this.toggle(x !== null && x.length > 0)));
    }

    toggle(mostra: boolean): void {
        this.newsPresenti = mostra;
        this.newsPresentiChange.emit(mostra);
    }
}
