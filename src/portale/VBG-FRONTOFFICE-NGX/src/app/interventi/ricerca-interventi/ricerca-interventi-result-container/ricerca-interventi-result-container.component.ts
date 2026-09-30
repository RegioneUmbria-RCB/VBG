import { Component, OnInit, Input } from "@angular/core";
import { UrlLocaliService } from "@core/services";
import { RisultatoRicercaIntervento } from "../risultato-ricerca-intervento.model";
import { RicercaInterventiResultComponent } from "../ricerca-interventi-result/ricerca-interventi-result.component";


@Component({
    selector: "app-ricerca-interventi-result-container",
    templateUrl: "./ricerca-interventi-result-container.component.html",
    standalone: true,
    imports: [RicercaInterventiResultComponent],
})
export class RicercaInterventiResultContainerComponent implements OnInit {
    @Input()
    public risultatoRicerca: RisultatoRicercaIntervento;
    @Input()
    public mostraInterventiLocali = true;
    @Input()
    public mostraInterventiRegionali = true;
    @Input()
    public attivo = false;

    public get mostraTitoliSezioni(): boolean {
        return this.mostraInterventiLocali && this.mostraInterventiRegionali;
    }

    public urlRedirectRegionale: string;
    public urlRedirectLocale: string;

    constructor(private urlLocaliService: UrlLocaliService) { }

    ngOnInit(): void {
        this.urlRedirectLocale =
            this.urlLocaliService.url("/interventi-locali");
        this.urlRedirectRegionale = this.urlLocaliService.url(
            "/interventi-regionali"
        );
    }
}
