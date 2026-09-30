import {
    Component,
    Input,
    Output,
    EventEmitter,
    ViewChild,
    ViewChildren,
    QueryList,
} from "@angular/core";
import { Location } from "@angular/common";
import { ProcedimentoDetailModel } from "../services/procedimento-detail.model";
import { CdkScrollable } from "@angular/cdk/scrolling";
import { SezioneInterventoComponent } from "@interventi/interventi-detail/sezione-intervento/sezione-intervento.component";
import { GrigliaNormativaComponent } from "../../core/components/griglia-normativa/griglia-normativa.component";
import { GrigliaModulisticaComponent } from "../../core/components/griglia-modulistica/griglia-modulistica.component";
import { GrigliaOneriComponent } from "../../core/components/griglia-oneri/griglia-oneri.component";
import { SezioneInterventoComponent as SezioneInterventoComponent_1 } from "../../interventi/interventi-detail/sezione-intervento/sezione-intervento.component";
import { ScrollSpyDirective } from "../../core/directives/scroll-spy.directive";
import { MenuLateraleComponent } from "../../interventi/interventi-detail/menu-laterale/menu-laterale.component";
import { AffixDirective } from "../../core/directives/affix.directive";

@Component({
    selector: "app-procedimenti-detail-base",
    templateUrl: "./procedimenti-detail-base.component.html",
    standalone: true,
    imports: [
    CdkScrollable,
    AffixDirective,
    MenuLateraleComponent,
    ScrollSpyDirective,
    SezioneInterventoComponent_1,
    GrigliaOneriComponent,
    GrigliaModulisticaComponent,
    GrigliaNormativaComponent
],
})
export class ProcedimentiDetailBaseComponent {
    @Input() procedimento: ProcedimentoDetailModel;
    @Input() idAttivita: string;
    @Input() endoRegionale = false;

    @Output() tornaIndietro = new EventEmitter<string>();
    @Output() download = new EventEmitter<string>();

    @ViewChild(CdkScrollable) scrollContainer: CdkScrollable;

    public sezioneAttiva = "";
    public listaSezioni = new Array<string>();

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

    constructor(private location: Location) { }


    onDownload($event: string): void {
        this.download.emit($event);
    }

    tornaAdAttivita(): void {
        this.location.back();
    }

    onSectionChange(newSectionId: string): void {
        this.sezioneAttiva = newSectionId;
    }
}
