import { tap, finalize, map } from "rxjs/operators";
import {
    Component,
    OnInit,
    Inject,
    ViewChild,
    ElementRef,
    AfterViewInit,
} from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FaqService, ModuloFaqModel } from "../index";
import { DOCUMENT, NgClass, AsyncPipe } from "@angular/common";
import {
    PageScrollService,
} from "ngx-page-scroll-core";
import { Observable } from "rxjs";
import { FaqModel } from '../faq.model';
import { SlugifyPipe } from "../../core/pipes/slugify.pipe";
import { ScrollSpyDirective } from "../../core/directives/scroll-spy.directive";
import { MenuLateraleComponent } from "../../interventi/interventi-detail/menu-laterale/menu-laterale.component";
import { AffixDirective } from "../../core/directives/affix.directive";
import { CdkScrollable } from "@angular/cdk/scrolling";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-faq-list",
    templateUrl: "./faq-list.component.html",
    standalone: true,
    imports: [
    InnerPageLayoutComponent,
    CdkScrollable,
    AffixDirective,
    MenuLateraleComponent,
    ScrollSpyDirective,
    NgClass,
    AsyncPipe,
    SlugifyPipe
],
})
export class FaqListComponent implements OnInit, AfterViewInit {
    @ViewChild("scrollContainer", { static: true })
    public sezioneAttiva = "";
    public listaCategorie$: Observable<string[]> = null;
    public caricamentoCompletato = false;
    public fullList$: Observable<ModuloFaqModel[]> = null;

    public evidenziaId: string;

    private scrollContainer: ElementRef;

    constructor(
        private service: FaqService,
        private route: ActivatedRoute,
        private pageScrollService: PageScrollService,
        @Inject(DOCUMENT) private document: Document
    ) { }

    ngOnInit(): void {
        this.evidenziaId = <string>this.route.snapshot.params.id;

        this.fullList$ = this.service.getList().pipe(
            tap((categorieFaq) => {
                if (!this.evidenziaId) {
                    return;
                }

                categorieFaq.forEach((modulo: ModuloFaqModel) => {
                    modulo.faq.forEach((faq) => {
                        if (faq.id.toString() === this.evidenziaId) {
                            faq.visibile = true;
                            faq.attiva = true;
                        }
                    });
                });
            }),
            finalize(() =>
                setTimeout(() => {
                    this.caricamentoCompletato = true;
                })
            )
        );

        this.listaCategorie$ = this.fullList$.pipe(
            map((data) => data.map((x) => x.descrizione))
        );
    }

    attiva(faq: FaqModel): void {
        faq.visibile = !faq.visibile;
        faq.attiva = faq.visibile;
    }

    scrollTo(faqId: string): void {
        this.pageScrollService.scroll({
            document: this.document,
            scrollTarget: "#faq-" + faqId,
        });
    }

    ngAfterViewInit(): void {
        if (this.evidenziaId) {
            setTimeout(() => this.scrollTo(this.evidenziaId), 500);
        }
    }

    onSectionChange(nuovaSezione: string): void {
        this.sezioneAttiva = nuovaSezione;
    }
}
