import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

import { FaqService, TopFaqModel } from "../../faq";
import { UrlLocaliService } from "../../core/services/url";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { RouterLink } from "@angular/router";
import { AsyncPipe } from "@angular/common";

@Component({
    selector: "app-home-faq",
    templateUrl: "./home-faq.component.html",
    standalone: true,
    imports: [
    RouterLink,
    AsyncPipe
],
})
export class HomeFaqComponent implements OnInit {
    @Input() faqPresenti = true;
    @Output() faqPresentiChange = new EventEmitter<boolean>();

    topFaq$: Observable<TopFaqModel[]>;
    urlDettaglioFaq: string;

    constructor(private faqService: FaqService, urlLocali: UrlLocaliService) {
        this.urlDettaglioFaq = urlLocali.url("/faq");
    }

    ngOnInit(): void {
        this.topFaq$ = this.faqService.getTop().pipe(
            tap((x) => {
                this.toggle(x !== null && x.length !== 0);
            })
        );
    }

    toggle(mostra: boolean): void {
        this.faqPresenti = mostra;
        this.faqPresentiChange.emit(mostra);
        console.log("mostra faq", mostra);
    }
}
