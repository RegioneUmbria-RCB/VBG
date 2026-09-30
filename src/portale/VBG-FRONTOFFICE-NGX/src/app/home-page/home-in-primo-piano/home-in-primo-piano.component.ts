import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { UrlLocaliService, RisorseService } from "../../core/services";
import { InterventiLocaliService, InterventoTopModel } from "../../interventi";
import {
    ProcedimentiLocaliService,
    ProcedimentoTopModel,
} from "../../procedimenti";
import { RouterLink } from "@angular/router";
import { AsyncPipe } from "@angular/common";
import { RicercaInterventiComponent } from "../../interventi/ricerca-interventi/ricerca-interventi.component";

@Component({
    selector: "app-home-in-primo-piano",
    templateUrl: "./home-in-primo-piano.component.html",
    standalone: true,
    imports: [
    RicercaInterventiComponent,
    RouterLink,
    AsyncPipe
],
})
export class HomeInPrimoPianoComponent implements OnInit {
    interventiTop$: Observable<InterventoTopModel[]>;
    procedimentiTop$: Observable<ProcedimentoTopModel[]>;
    urlDettagliIntervento: string;
    urlDettagliProcedimento: string;
    titoloAttivita: string;
    mostraInterventiRegionali = true;
    mostraInterventiLocali = true;

    mostraInterventiTop = false;
    mostraProcedimentiTop = false;

    constructor(
        private interventiService: InterventiLocaliService,
        private procedimentiService: ProcedimentiLocaliService,
        urlLocaliService: UrlLocaliService,
        private risorseService: RisorseService
    ) {
        this.urlDettagliIntervento = urlLocaliService.url("/interventi-locali");
        this.urlDettagliProcedimento = urlLocaliService.url("/procedimenti");
    }

    ngOnInit(): void {
        this.interventiTop$ = this.interventiService.getTop().pipe(
            tap((x) => {
                this.mostraInterventiTop = x && x.length > 0;
            })
        );
        this.procedimentiTop$ = this.procedimentiService.getTop().pipe(
            tap((x) => {
                this.mostraProcedimentiTop = x && x.length > 0;
            })
        );

        /*
    this.interventiTop$.subscribe(x => {
        this.mostraInterventiTop = x && x.length > 0;
    });

    this.procedimentiTop$.subscribe(x => {
        this.mostraProcedimentiTop = x && x.length > 0;
    });*/

        this.titoloAttivita = this.risorseService.getRisorsa(
            "home.inPrimoPiano.attivita",
            "Attività"
        );
        this.mostraInterventiRegionali =
            this.risorseService.getRisorsa(
                "home.inPrimoPiano.usaInterventiRegionali",
                "true"
            ) === "true";
        this.mostraInterventiLocali =
            this.risorseService.getRisorsa(
                "home.inPrimoPiano.usaInterventiLocali",
                "true"
            ) === "true";
    }
}
