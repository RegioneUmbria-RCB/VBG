import { Observable, Subject, forkJoin } from "rxjs";
import { debounceTime, skipWhile, switchMap, map, tap } from "rxjs/operators";
import { Component, OnInit, ViewChild, ElementRef, Input } from "@angular/core";
import {
    InterventiLocaliService,
    InterventiRegionaliService,
    InterventoCercato,
} from "../services";
import { RisorseService } from "../../core/services";
import { RisultatoRicercaIntervento } from "./risultato-ricerca-intervento.model";
import { StatoRicercaIntervento } from "./stato-ricerca-intervento.model";
import { RicercaInterventiResultContainerComponent } from "./ricerca-interventi-result-container/ricerca-interventi-result-container.component";
import { AsyncPipe } from "@angular/common";
import { FormsModule } from "@angular/forms";

@Component({
    selector: "app-ricerca-interventi",
    templateUrl: "./ricerca-interventi.component.html",
    standalone: true,
    imports: [
    FormsModule,
    RicercaInterventiResultContainerComponent,
    AsyncPipe
],
})
export class RicercaInterventiComponent implements OnInit {
    @ViewChild("searchInput")
    public inputRef: ElementRef;

    @Input()
    public mostraInterventiLocali = true;
    @Input()
    public mostraInterventiRegionali = true;

    public segnapostoTesto: string;
    public text: string;
    public risultatoRicerca$: Observable<RisultatoRicercaIntervento>;
    public statoRicerca = new StatoRicercaIntervento();

    private dataSource: Subject<string>;

    public constructor(
        private interventiLocali: InterventiLocaliService,
        private interventiRegionali: InterventiRegionaliService,
        private risorseService: RisorseService
    ) { }

    chiudiRicerca(): void {
        this.clearControl();
    }

    clearControl(): void {
        console.log("clearcontrol");
        this.text = "";
    }

    ngOnInit(): void {
        // eslint-disable-next-line max-len
        this.segnapostoTesto = this.risorseService.getRisorsa(
            "ricercaInterventiComponent.segnapostoTesto",
            "Cerca attività e procedimenti"
        );
        this.dataSource = new Subject<string>();
        this.risultatoRicerca$ = this.dataSource.pipe(
            debounceTime(250),
            skipWhile((x) => x.length < 3),
            tap(() => {
                this.statoRicerca.ricercaIniziata();
            }),
            switchMap((term) =>
                forkJoin([
                    this.interventiLocali.cerca(term),
                    this.interventiRegionali.cerca(term),
                ])
            ),
            map(
                (joined) =>
                    new RisultatoRicercaIntervento(
                        this.mostraInterventiLocali
                            ? joined[0]
                            : new Array<InterventoCercato>(),
                        this.mostraInterventiRegionali
                            ? joined[1]
                            : new Array<InterventoCercato>()
                    )
            ),
            tap((res) => {
                this.statoRicerca.ricercaCompletata(res.conteggioTotale);
            })
        );
    }

    onBlur(): void {
        setTimeout(() => {
            this.statoRicerca.reset();
            this.clearControl();
        }, 300);
    }

    onFocus(): void {
        this.statoRicerca.reset();
    }

    onKeyUp($event: KeyboardEvent): void {
        console.log($event.key);
        if ($event.key === "Escape") {
            this.clearControl();
        } else {
            const testo = ($event.target as HTMLInputElement).value;
            this.dataSource.next(testo);
        }
    }
}
