
import { tap, finalize } from 'rxjs/operators';
import { Component, OnInit, OnDestroy, Inject, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FaqService, ModuloFaqModel, FaqModel } from '../index';
import { DOCUMENT } from '@angular/common';
import { PageScrollConfig, PageScrollService, PageScrollInstance } from 'ngx-page-scroll-core';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-faq-list',
    templateUrl: './faq-list.component.html'
})
export class FaqListComponent implements OnInit, AfterViewInit {

    caricamentoCompletato = false;
    fullList$: Observable<ModuloFaqModel[]> = null;

    evidenziaId: string;

    @ViewChild('scrollContainer', { static: true })
    private scrollContainer: ElementRef;

    constructor(private service: FaqService, private route: ActivatedRoute,
        private pageScrollService: PageScrollService, @Inject(DOCUMENT) private document: any) { }

    ngOnInit() {

        this.evidenziaId = this.route.snapshot.params.id;

        this.fullList$ = this.service
            .getList().pipe(
                tap((categorieFaq) => {

                    if (!this.evidenziaId) {
                        return;
                    }

                    categorieFaq.forEach((modulo: ModuloFaqModel) => {

                        modulo.faq.forEach(faq => {
                            if (faq.id.toString() === this.evidenziaId) {
                                faq.visibile = true;
                                faq.attiva = true;
                            }
                        });
                    });
                }),
                finalize(() => setTimeout(() => {
                    this.caricamentoCompletato = true;
                    document.documentElement.scrollTop = 0;
                }))
            );
    }

    attiva(faq: any) {
        faq.visibile = !faq.visibile;
        faq.attiva = faq.visibile;
    }

    scrollTo(faqId: string) {
        this.pageScrollService.scroll({
            document: this.document,
            scrollTarget: '#faq-' + faqId,
        });
    }

    ngAfterViewInit(): void {
        if (this.evidenziaId) {
            setTimeout(() => this.scrollTo(this.evidenziaId), 500);
        }
    }

}
