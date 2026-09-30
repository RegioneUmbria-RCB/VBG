import { Component, OnInit } from "@angular/core";
import { CosaPuoiFareCardModel } from "./cosa-puoi-fare-card.model";
import { UrlLocaliService } from "../../core/services/url";
import { RouterLink } from "@angular/router";


@Component({
    selector: "app-cosa-puoi-fare",
    templateUrl: "./cosa-puoi-fare.component.html",
    standalone: true,
    imports: [RouterLink],
})
export class CosaPuoiFareComponent implements OnInit {
    cards: CosaPuoiFareCardModel[];

    constructor(private urlLocaliService: UrlLocaliService) { }

    ngOnInit(): void {
        this.cards = [
            {
                titolo: "Inviare una pratica",
                sottotitolo: "Direttamente all’ufficio competente",
                link: this.urlLocaliService.url("/nuova-domanda"),
            },
            {
                titolo: "Seguire la pratica",
                sottotitolo: "Interrogare lo stato di avanzamento",
                link: this.urlLocaliService.url("/pratiche-presentate"),
            },
            {
                titolo: "Consultare l’archivio",
                sottotitolo: "Accedere alle pratiche libere",
                link: this.urlLocaliService.url("/archivio-pratiche"),
            },
        ];
    }
}
