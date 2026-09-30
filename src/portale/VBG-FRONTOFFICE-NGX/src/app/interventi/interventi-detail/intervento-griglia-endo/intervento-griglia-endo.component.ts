import { Component, Input, Output, EventEmitter } from "@angular/core";
import { EndoprocedimentoModel, FamigliaEndoprocedimentoModel } from "./endoprocedimento.model";


@Component({
    selector: "app-intervento-griglia-endo",
    templateUrl: "./intervento-griglia-endo.component.html",
    standalone: true,
    imports: [],
})
export class InterventoGrigliaEndoComponent {
    @Input() dataSource: FamigliaEndoprocedimentoModel[];
    @Input() titolo = "Endoprocedimenti";

    @Output() endoprocedimentoSelezionato = new EventEmitter<EndoprocedimentoModel>();


    onEndoprocedimentoSelezionato(e: EndoprocedimentoModel): void {
        this.endoprocedimentoSelezionato.emit(e);
    }
}
