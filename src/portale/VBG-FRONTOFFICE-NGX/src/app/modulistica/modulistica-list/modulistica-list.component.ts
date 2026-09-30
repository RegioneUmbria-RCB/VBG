import { zip } from "rxjs";
import { Component, OnInit } from "@angular/core";

import { ModulisticaService } from "../modulistica.service";
import { CategoriaModulisticaItem } from "../categoria-modulistica.model";
import { UrlServiziLocaliService } from "../../core/services/url";
import { finalize, tap } from "rxjs/operators";
import { SlugifyPipe } from "../../core/pipes/slugify.pipe";
import { StripHtmlPipe } from "../../core/pipes/strip-html.pipe";
import { ScrollSpyDirective } from "../../core/directives/scroll-spy.directive";
import { MenuLateraleComponent } from "../../interventi/interventi-detail/menu-laterale/menu-laterale.component";
import { AffixDirective } from "../../core/directives/affix.directive";
import { CdkScrollable } from "@angular/cdk/scrolling";
import { NgClass } from "@angular/common";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-modulistica-list",
    templateUrl: "./modulistica-list.component.html",
    standalone: true,
    imports: [
    InnerPageLayoutComponent,
    CdkScrollable,
    AffixDirective,
    MenuLateraleComponent,
    NgClass,
    ScrollSpyDirective,
    StripHtmlPipe,
    SlugifyPipe
],
})
export class ModulisticaListComponent implements OnInit {
    listaCompleta: CategoriaModulisticaItem[] = [];
    elementiTop: CategoriaModulisticaItem[] = [];
    listaCategorie = new Array<string>();
    sezioneAttiva = "";
    caricamentoCompletato = false;

    private _dataSource: CategoriaModulisticaItem[] = [];

    public set dataSource(value: CategoriaModulisticaItem[]) {
        this.listaCategorie = value.map(
            (x) => x.nome || "Modulistica generale"
        );
        this._dataSource = value;
    }

    public get dataSource(): CategoriaModulisticaItem[] {
        return this._dataSource;
    }

    constructor(
        private service: ModulisticaService,
        private downloadUrlService: UrlServiziLocaliService
    ) { }

    ngOnInit(): void {
        zip(this.service.getList(), this.service.getTop())
            .pipe(
                tap(([list, top]) => {
                    if (list) {
                        this.listaCompleta = list.categorie;
                        this.elementiTop = top.categorie;
                    }
                }),
                finalize(() => (this.caricamentoCompletato = true))
            )
            .subscribe(() => this.mostraInPrimoPiano());
    }

    onKeyUp($event: KeyboardEvent): void {
        const testo = ($event.target as HTMLInputElement).value;

        if (testo.length === 0) {
            this.mostraInPrimoPiano();

            return;
        }

        const tmp = testo.toLowerCase();

        const tmpList = this.listaCompleta.map((m) => {
            const newItem = new CategoriaModulisticaItem();

            newItem.codice = m.codice;
            newItem.nome = m.nome;

            if (m.nome && m.nome.toLowerCase().indexOf(testo) >= 0) {
                newItem.modulistica = m.modulistica;
            } else {
                newItem.modulistica = m.modulistica.filter(
                    (item) =>
                        item.titolo.toLowerCase().indexOf(tmp) >= 0 ||
                        item.descrizione.toLowerCase().indexOf(tmp) >= 0
                );
            }

            return newItem;
        });

        this.dataSource = tmpList.filter((x) => x.modulistica.length > 0);
    }

    mostraInPrimoPiano(): void {
        this.dataSource = this.elementiTop;
    }

    urlDownload(codiceOggetto: string): string {
        return this.downloadUrlService.url("download", [codiceOggetto]);
    }

    mostraListaCompleta(): void {
        this.dataSource = this.listaCompleta;
    }

    onSectionChange(nuovaSezione: string): void {
        this.sezioneAttiva = nuovaSezione;
    }
}
