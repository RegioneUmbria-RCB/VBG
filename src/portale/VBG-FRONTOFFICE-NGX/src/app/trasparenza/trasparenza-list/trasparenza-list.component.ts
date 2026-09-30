import { Component, OnInit } from "@angular/core";
import { ParametriRicercaTrasparenzaModel } from "../parametri-ricerca-trasparenza.model";
import { TrasparenzaService } from "../trasparenza.service";
import { StradarioReponse } from "../../core/models/StradarioResponse";
import { RisorseService } from "../../core/services/risorse/risorse.service";
import { TrasparenzaSearchResultModel } from "../trasparenza-search-result.model";
import { debounceTime, switchMap, tap } from "rxjs/operators";
import { Observable, of, Subject } from "rxjs";
import { CaricaDataSourcePagingModel } from "./carica-data-source-paging.model";
import { ComuniDTO } from "@core/models/comuniDTO";
import { StatoIstanzaDTO } from "@core/models/statoIstanzaDTO";
import { TabellaTrasparenzaComponent } from "./tabella-trasparenza/tabella-trasparenza.component";
import { LoaderComponent } from "../../core/components/loader/loader.component";
import { FormsModule } from "@angular/forms";
import { NgClass, AsyncPipe, SlicePipe } from "@angular/common";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-trasparenza-list",
    templateUrl: "./trasparenza-list.component.html",
    standalone: true,
    imports: [
    InnerPageLayoutComponent,
    NgClass,
    FormsModule,
    LoaderComponent,
    TabellaTrasparenzaComponent,
    AsyncPipe,
    SlicePipe
],
})
export class TrasparenzaListComponent implements OnInit {
    fieldIndirizzoRequired: string;
    fieldIndirizzoInvalid: boolean;
    fieldFoglioInvalid: boolean;
    fieldCampiObbligatori: boolean;

    public caricamentoInCorso = false;
    public risultatiVisibili = false;
    public errore = false;
    showRisultatoRicercaStradario = false;
    public comuniAssociati$: Observable<ComuniDTO[]>;
    public statiIstanza$: Observable<StatoIstanzaDTO[]>;
    public model = new ParametriRicercaTrasparenzaModel();
    public nascondiReferente = true;

    // stringhe
    public etichettaNumeroPratica="";

    // visualizzazione dei campi di ricerca
    public mostraAnnoProtocollo = true;

    // stringhe
    public etichettaReferente="";

    // stringhe
    public etichettaDallaData="";

    // stringhe
    public etichettaAllaData="";

    // stringhe
    public etichettaComune="";

    // stringhe
    public etichettaStato="";

    // stringhe
    public etichettaIndirizzo="";

    // stringhe
    public etichettaCivico="";

    // stringhe
    public etichettaNumeroProtocollo="";

    public etichettaAnnoProtocollo="";

    public messaggioCampiObbligatori="";


    public mostraDatiCatastali = true;

    public etichettaTipoCatasto="";
    public etichettaFoglio="";
    public etichettaParticella="";
    public etichettaSub="";


    // ok
    public validazioneIntervalloDateFallita: boolean;
    public recordsPerPagina = 10;
    public listaDataSource$ = new Observable<TrasparenzaSearchResultModel>();
    public risultatoRicercaStradario: StradarioReponse;

    private triggerRicercaIndirizzo = new Subject<string>();
    private triggerRicercaIstanze = new Subject<CaricaDataSourcePagingModel>();

    // eslint-disable-next-line max-len
    constructor(
        private trasparenzaService: TrasparenzaService,
        private risorseService: RisorseService
    ) {
        this.fieldIndirizzoRequired = this.risorseService.getRisorsa(
            "trasparenza.indirizzo.required",
            "false"
        );

        this.nascondiReferente = this.risorseService.getRisorsa(
            "trasparenza.referente.nascondi",
            "false"
        ) == "true";

        // inizializzazione delle etichette
        this.etichettaNumeroPratica = this.risorseService.getRisorsa(
            "trasparenza.etichette.numeropratica",
            "Numero pratica"
        );
        this.etichettaReferente = this.risorseService.getRisorsa(
            "trasparenza.etichette.referente",
            "Referente"
        );
        this.etichettaDallaData = this.risorseService.getRisorsa(
            "trasparenza.etichette.dalladata",
            "Dalla data"
        );
        this.etichettaAllaData = this.risorseService.getRisorsa(
            "trasparenza.etichette.alladata",
            "Alla data"
        );
        this.etichettaComune = this.risorseService.getRisorsa(
            "trasparenza.etichette.comune",
            "Comune"
        );
        this.etichettaStato = this.risorseService.getRisorsa(
            "trasparenza.etichette.stato",
            "Stato"
        );
        this.etichettaIndirizzo = this.risorseService.getRisorsa(
            "trasparenza.etichette.indirizzo",
            "Indirizzo"
        );
        this.etichettaCivico = this.risorseService.getRisorsa(
            "trasparenza.etichette.civico",
            "Civico"
        );
        this.etichettaNumeroProtocollo = this.risorseService.getRisorsa(
            "trasparenza.etichette.numeroprotocollo",
            "Numero protocollo"
        );
        this.messaggioCampiObbligatori = this.risorseService.getRisorsa(
            "trasparenza.risorsa.messaggiocampiobbligatori",
            "Per effettuare la ricerca è necessario compilare almeno uno dei campi obbligatori"
        );
        this.etichettaAnnoProtocollo = this.risorseService.getRisorsa(
            "trasparenza.etichette.annoprotocollo",
            "Anno protocollo"
        );

        // visualizzazione dei campi di ricerca
        this.mostraAnnoProtocollo =
            this.risorseService.getRisorsa(
                "trasparenza.ricerca.mostraAnnoProtocollo",
                "true"
            ) === "true";

        // dati catastali
        this.mostraDatiCatastali =
            this.risorseService.getRisorsa(
                "trasparenza.ricerca.mostraDatiCatastali",
                "true"
        ) === "true";

        this.etichettaTipoCatasto = this.risorseService.getRisorsa(
            "trasparenza.etichette.datiCatastali.tipoCatasto",
            "Tipo catasto"
        );

        this.etichettaFoglio = this.risorseService.getRisorsa(
            "trasparenza.etichette.datiCatastali.foglio",
            "Foglio"
        );


        this.etichettaParticella = this.risorseService.getRisorsa(
            "trasparenza.etichette.datiCatastali.particella",
            "Particella"
        );


        this.etichettaSub = this.risorseService.getRisorsa(
            "trasparenza.etichette.datiCatastali.sub",
            "Sub"
        );

        // Inizialization of model for search
        this.inizializzaModelRicerca();
    }

    inizializzaModelRicerca(): void {
        this.model.dataAl = "";
        this.model.dataDal = "";
        this.model.numeroPratica = "";
        this.model.numeroProtocollo = "";
        this.model.stato = "";
        this.model.indirizzo = "";
        this.model.civico = "";
        this.model.codiceStradario = "";
        this.model.comune = "";
        this.model.referente = "";
        this.model.annoProtocollo = "";
        this.model.tipoCatasto ="";
        this.model.foglio ="";
        this.model.particella ="";
        this.model.sub ="";
    }

    ngOnInit(): void {
        // Ricerca comuni associati
        /*
        this.trasparenzaService.getComuniAssociati()
            .subscribe(res => {
                this.comuniAssociati = res;
            });
        */
        // Ricerca dell'indirizzo
        this.triggerRicercaIndirizzo
            .pipe(
                debounceTime(300),
                tap((term) => {
                    console.log(term);
                    this.model.codiceStradario = "";
                }),
                switchMap((term) =>
                    this.trasparenzaService.getStradario(
                        this.model.comune,
                        term
                    )
                )
            )
            .subscribe((result) => {
                this.risultatoRicercaStradario = result;
            });

        this.comuniAssociati$ = this.trasparenzaService.getComuniAssociati();

        this.statiIstanza$ = this.trasparenzaService.getStatiIstanza();

        // Ricerca istanze
        this.listaDataSource$ = this.triggerRicercaIstanze.pipe(
            switchMap((cmd) => {
                if (cmd.pagina === 0 && cmd.elementiPerPagina === 0) {
                    return of<TrasparenzaSearchResultModel>(null);
                }

                return this.trasparenzaService.getList(
                    this.model,
                    cmd.pagina,
                    cmd.elementiPerPagina
                );
            }),
            tap((results) => {
                if (results == null || results.lista === null) {
                    this.risultatiVisibili = false;
                    this.caricamentoInCorso = false;
                } else {
                    this.caricamentoInCorso = false;
                    this.risultatiVisibili = true;
                }
            })
        );
    }

    ricercaStradario(): void {
        //        this.showRisultatoRicercaStradario = true;
        this.triggerRicercaIndirizzo.next(this.model.indirizzo);
    }

    chiudiRicerca(indirizzo: string, codiceStradario: string): void {
        this.model.indirizzo = indirizzo;
        this.model.codiceStradario = codiceStradario;
        this.risultatoRicercaStradario = null;
        this.showRisultatoRicercaStradario = false;
    }

    onNuovaRicercaClick(): void {
        this.risultatiVisibili = false;
        // this.model.comune = this.model.comune;
        // this.inizializzaModelRicerca();
        // this.dataSource = null;

        this.triggerRicercaIstanze.next(new CaricaDataSourcePagingModel(0, 0));
    }

    onRicercaStradarioBlur(): void {
        setTimeout(() => {
            this.risultatoRicercaStradario = null;
        }, 250);
    }

    onCercaClick(): void {
        this.fieldIndirizzoInvalid = false;
        this.fieldFoglioInvalid = false;
        this.validazioneIntervalloDateFallita = false;

        if (
            this.model.numeroPratica.length === 0 &&
            this.model.numeroProtocollo.length === 0 &&
            this.model.annoProtocollo.length === 0 &&
            this.model.indirizzo.length === 0 &&
            this.model.foglio.length === 0 &&
            (!this.mostraDatiCatastali || this.model.foglio.length === 0)
            ) {
            this.fieldCampiObbligatori = true;
            return;
        } else {
            this.fieldCampiObbligatori = false;
        }

        /*if (this.model.indirizzo.length === 0 && this.fieldIndirizzoRequired === 'true') {
            this.fieldIndirizzoInvalid = true;
            return;
        }*/

        if (!this.model.intervalloDateValido()) {
            this.validazioneIntervalloDateFallita = true;
            return;
        }

        this.caricamentoInCorso = true;
        this.triggerRicercaIstanze.next(
            new CaricaDataSourcePagingModel(0, this.recordsPerPagina)
        );
    }

    onRicaricaDataSource(event: CaricaDataSourcePagingModel): void {
        this.caricamentoInCorso = true;
        this.recordsPerPagina = event.elementiPerPagina;

        console.log("onRicaricaDataSource", event);
        this.triggerRicercaIstanze.next(event);
    }

    /* eslint-disable-next-line max-len, , , , max-len */
}

