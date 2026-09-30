import { zip } from "rxjs";
import { Component, OnInit } from "@angular/core";
import { NormativaService } from "../normativa.service";
import { NormativaItemModel } from "../normativa-item.model";
import { UrlServiziLocaliService } from "../../core/services/url";
import { finalize, tap } from "rxjs/operators";
import { NgClass } from "@angular/common";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-normativa-list",
    templateUrl: "./normativa-list.component.html",
    standalone: true,
    imports: [
    InnerPageLayoutComponent,
    NgClass
],
})
export class NormativaListComponent implements OnInit {
    listaCompleta: NormativaItemModel[] = [];
    normativaTop: NormativaItemModel[] = [];

    dataSource: NormativaItemModel[] = [];

    caricamentoCompletato = false;

    constructor(
        private service: NormativaService,
        private downloadUrlService: UrlServiziLocaliService
    ) { }

    ngOnInit(): void {
        zip(this.service.getList(), this.service.getTop())
            .pipe(
                tap(([list, top]) => {
                    this.listaCompleta = list;
                    this.normativaTop = top;
                }),
                finalize(() => (this.caricamentoCompletato = true))
            )
            .subscribe(() => {
                this.mostraInPrimoPiano();
            });
    }

    onKeyUp($event: KeyboardEvent): void {
        const testo = ($event.target as HTMLInputElement).value;

        if (testo.length === 0) {
            this.mostraInPrimoPiano();

            return;
        }

        const tmp = testo.toLowerCase();

        this.dataSource = this.listaCompleta.filter(
            (item) =>
                item.titolo.toLowerCase().indexOf(tmp) >= 0 ||
                item.descrizione.toLowerCase().indexOf(tmp) >= 0
        );
    }

    mostraInPrimoPiano(): void {
        this.dataSource = this.normativaTop;
    }

    urlDownload(codiceOggetto: string): string {
        return this.downloadUrlService.url("download", [codiceOggetto]);
    }

    mostraListaCompleta(): void {
        this.dataSource = this.listaCompleta;
    }
}
