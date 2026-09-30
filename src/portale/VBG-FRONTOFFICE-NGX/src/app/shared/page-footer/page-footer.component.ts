import { Component, OnInit } from "@angular/core";
import { DatiComuneService, DatiComuneModel } from "../../dati-comune";


@Component({
    selector: "app-page-footer",
    templateUrl: "./page-footer.component.html",
    standalone: true,
    imports: [],
})
export class PageFooterComponent implements OnInit {
    datiComune: DatiComuneModel;
    loaded = false;

    constructor(private datiComuneService: DatiComuneService) { }

    ngOnInit(): void {
        this.datiComuneService.get().subscribe((x) => {
            this.datiComune = x;
            this.loaded = true;
        });
    }
}
