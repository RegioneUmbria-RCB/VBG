import { Component, Input } from "@angular/core";
import { InterventoCercato } from "@interventi/services";
import { RouterLink } from "@angular/router";
import { SlicePipe } from "@angular/common";

@Component({
    selector: "app-ricerca-interventi-result",
    templateUrl: "./ricerca-interventi-result.component.html",
    standalone: true,
    imports: [
    RouterLink,
    SlicePipe
],
})
export class RicercaInterventiResultComponent {
    @Input()
    public titolo: string;
    @Input()
    public interventi: InterventoCercato[];
    @Input()
    public mostraTitoliSezioni = true;
    @Input()
    public urlRedirect: string;
    @Input()
    public limitaRisultati = 10;


    public onClick(): void {
        console.log("click");
    }
}
