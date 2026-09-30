import { Component, Input } from "@angular/core";
import { FaseAttuativaModel } from "./fase-attuativa.model";


@Component({
    selector: "app-intervento-fasi-attuative",
    templateUrl: "./intervento-fasi-attuative.component.html",
    standalone: true,
    imports: [],
})
export class InterventoFasiAttuativeComponent {
    @Input() dataSource: FaseAttuativaModel[];

}
