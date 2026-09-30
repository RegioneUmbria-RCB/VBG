import {
    Component,
    Input,
    Output,
    EventEmitter,
    ViewChild,
    QueryList,
    ViewChildren,
} from "@angular/core";
import { InterventoDetailModel } from "../services";
import { SezioneInterventoComponent } from "./sezione-intervento/sezione-intervento.component";
import { CdkScrollable } from "@angular/cdk/scrolling";
import { Router, RouterLink } from "@angular/router";
import { RisorseService } from "../../core/services/risorse/risorse.service";
import { EndoprocedimentoModel } from './intervento-griglia-endo/endoprocedimento.model';
import { InterventoGrigliaEndoComponent } from "./intervento-griglia-endo/intervento-griglia-endo.component";
import { InterventoListaInterventiLocaliComponent } from "./intervento-lista-interventi-locali/intervento-lista-interventi-locali.component";
import { GrigliaModulisticaComponent } from "../../core/components/griglia-modulistica/griglia-modulistica.component";
import { InterventoFasiAttuativeComponent } from "./intervento-fasi-attuative/intervento-fasi-attuative.component";
import { GrigliaNormativaComponent } from "../../core/components/griglia-normativa/griglia-normativa.component";
import { GrigliaOneriComponent } from "../../core/components/griglia-oneri/griglia-oneri.component";
import { ScrollSpyDirective } from "../../core/directives/scroll-spy.directive";
import { MenuLateraleComponent } from "./menu-laterale/menu-laterale.component";
import { AffixDirective } from "../../core/directives/affix.directive";


@Component({
    selector: "app-interventi-detail",
    templateUrl: "./interventi-detail.component.html",
    standalone: true,
    imports: [
    RouterLink,
    CdkScrollable,
    AffixDirective,
    MenuLateraleComponent,
    ScrollSpyDirective,
    SezioneInterventoComponent,
    GrigliaOneriComponent,
    GrigliaNormativaComponent,
    InterventoFasiAttuativeComponent,
    GrigliaModulisticaComponent,
    InterventoListaInterventiLocaliComponent,
    InterventoGrigliaEndoComponent
],
})
export class InterventiDetailComponent {
    @Input() permetteDownloadModello = false;
    @Input() intervento: InterventoDetailModel;
    @Input() isUrlVaiAServizioPresente = false;
    @Output()
    tornaIndietro = new EventEmitter<string>();
    @Output()
    download = new EventEmitter<string>();
    @Output()
    endoprocedimentoSelezionato = new EventEmitter<EndoprocedimentoModel>();
    @Output()
    scaricaModello = new EventEmitter<string>();
    @Output()
    vaiAServizio = new EventEmitter<string>();

    @ViewChild(CdkScrollable) scrollContainer: CdkScrollable;

    public returnTo = "";
    public sezioneAttiva = "";
    public labelBtnVaiAServizio = this.risorseService.getRisorsa(
        "presentaIntervento.titolo",
        "Vai al servizio"
    );

    @ViewChildren(SezioneInterventoComponent) set sezioneInterventoComponent(
        value: QueryList<SezioneInterventoComponent>
    ) {
        // Evita l'errore 'Expression has changed...'
        setTimeout(() => {
            this.listaSezioni = value
                .toArray()
                .filter((x) => x.visibileSe)
                .map((x) => x.titolo);
        }, 0);
    }

    public listaSezioni = new Array<string>();

    constructor(public router: Router, private risorseService: RisorseService) {
        console.log("router url:", router.url);

        const parts = router.url.split("/");
        this.returnTo = parts.slice(0, -1).join("/");
    }


    onTornaIndietro(): void {
        this.tornaIndietro.emit();
    }

    onScaricaModello($event: string): void {
        this.scaricaModello.emit($event);
    }

    onDownload($event: string): void {
        console.log($event);
        this.download.emit($event);
    }

    onEndoprocedimentoSelezionato(endo: EndoprocedimentoModel): void {
        console.log(endo);
        this.endoprocedimentoSelezionato.emit(endo);
    }

    onSectionChange(newSectionId: string): void {
        this.sezioneAttiva = newSectionId;
    }

    onVaiAServizio($event: string): void {
        this.vaiAServizio.emit($event);
    }
}
