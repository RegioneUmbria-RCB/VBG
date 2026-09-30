/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import { Directive, ElementRef } from "@angular/core";
import { TrasparenzaService } from "../trasparenza.service";

@Directive({
    selector: "[appComuniAssociati]",
    standalone: true,
})
export class ComuniAssociatiDirective {
    constructor(el: ElementRef, trasparenzaService: TrasparenzaService) {
        trasparenzaService.getComuniAssociati().subscribe((comuni) => {
            console.log(
                "Ricreata lista di comuni associati oldVal=",
                el.nativeElement,
                el.nativeElement.value
            );
            const options = comuni.map((comune) => {
                const option = document.createElement("option");
                option.text = comune.comune;
                option.value = comune.codiceComune;

                return option;
            });

            el.nativeElement.add(document.createElement("option"));
            for (const op of options) {
                el.nativeElement.add(op);
            }
        });
    }
}
