import { Component, Input, Output, EventEmitter } from "@angular/core";
import { InterventoLocaleModel } from "./intervento-locale.model";


@Component({
    selector: "app-intervento-lista-interventi-locali",
    templateUrl: "./intervento-lista-interventi-locali.component.html",
    standalone: true,
    imports: [],
})
export class InterventoListaInterventiLocaliComponent {
    @Input() dataSource: InterventoLocaleModel[];

    @Output() endoprocedimentoSelezionato = new EventEmitter<InterventoLocaleModel>();

    onEndoprocedimentoSelezionato(intervento: InterventoLocaleModel): void {
        this.endoprocedimentoSelezionato.emit(intervento);
    }
}
