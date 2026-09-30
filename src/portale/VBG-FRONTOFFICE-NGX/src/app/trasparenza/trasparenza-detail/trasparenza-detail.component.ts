import { mergeMap, map } from "rxjs/operators";
import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import { ActivatedRoute, Params } from "@angular/router";
import { TrasparenzaService } from "../trasparenza.service";
import { VisuraDettaglioPratica } from '../visura-dettaglio-pratica';
import { TrasparenzaSezioneModel } from './trasparenza-sezione.model';
import { SlugifyPipe } from "../../core/pipes/slugify.pipe";
import { ScrollSpyDirective } from "../../core/directives/scroll-spy.directive";
import { MenuLateraleComponent } from "../../interventi/interventi-detail/menu-laterale/menu-laterale.component";
import { AffixDirective } from "../../core/directives/affix.directive";
import { CdkScrollable } from "@angular/cdk/scrolling";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-trasparenza-detail",
    templateUrl: "./trasparenza-detail.component.html",
    standalone: true,
    imports: [
    InnerPageLayoutComponent,
    CdkScrollable,
    AffixDirective,
    MenuLateraleComponent,
    ScrollSpyDirective,
    SlugifyPipe
],
})
export class TrasparenzaDetailComponent implements OnInit {
    public caricamentoInCorso = true;
    public dataSource: VisuraDettaglioPratica;
    public nascondiMappa = false;
    public sezioni = new Array<TrasparenzaSezioneModel>();
    public titoliSezioni = new Array<string>();
    public sezioneAttiva = "";

    constructor(
        private route: ActivatedRoute,
        private service: TrasparenzaService,
        private location: Location
    ) { }

    ngOnInit(): void {
        this.route.params
            .pipe(
                map((params: Params) => <string>params["id"]),
                mergeMap((id) => this.service.getById(id))
            )
            .subscribe((istanza) => {
                this.dataSource = istanza;

                this.sezioni = [
                    {
                        titolo: "Attività",
                        corpo: this.dataSource.datiGenerali['Intervento'],
                    },
                    {
                        titolo: "Oggetto",
                        corpo: this.dataSource.datiGenerali['Oggetto'],
                    },
                    {
                        titolo: "Stato",
                        corpo: this.dataSource.datiGenerali['Stato'],
                    },
                    {
                        titolo: "Responsabile del procedimento",
                        corpo: this.dataSource.datiGenerali["Responsabile del procedimento"],
                    },
                ];

                this.sezioni = this.sezioni.filter(
                    (x) => x.corpo !== undefined && (x.corpo || "")
                );
                this.titoliSezioni = this.sezioni.map((x) => x.titolo);

                if (this.dataSource.localizzazioni) {
                    this.titoliSezioni.push("Localizzazioni");
                }

                this.caricamentoInCorso = false;
            });
    }

    tornaIndietro(): void {
        this.location.back();
    }

    onSectionChange(nuovaSezione: string): void {
        this.sezioneAttiva = nuovaSezione;
    }
}
