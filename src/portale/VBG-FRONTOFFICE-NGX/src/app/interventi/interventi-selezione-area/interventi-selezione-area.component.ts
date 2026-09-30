import { Component, OnInit } from "@angular/core";
import { ButtonGridItem } from "../../core/components";
import { UrlLocaliService, RisorseService } from "../../core/services";
import { ButtonGridComponent } from "../../core/components/button-grid/button-grid.component";
import { RicercaInterventiComponent } from "../ricerca-interventi/ricerca-interventi.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-interventi-selezione-area",
    templateUrl: "./interventi-selezione-area.component.html",
    styleUrls: ["./interventi-selezione-area.component.less"],
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        RicercaInterventiComponent,
        ButtonGridComponent,
    ],
})
export class InterventiSelezioneAreaComponent implements OnInit {
    scelte: ButtonGridItem[] = [];
    titoloPagina: string;

    constructor(
        private urlService: UrlLocaliService,
        private risorseService: RisorseService
    ) { }

    ngOnInit(): void {
        this.titoloPagina = this.risorseService.getRisorsa(
            "interventi.lista.titoloPagina",
            "Procedimenti, modulistica ed adempimenti"
        );

        this.scelte = [
            {
                id: "interventi-regionali",
                titolo: "Procedimenti gestiti con modulistica unificata",
                sottotitolo:
                    "In questa sezione si possono trovare gli adempimenti relativi" +
                    " a procedimenti la cui modulistica è stata definita a livello nazionale e regionale ",
                link: this.urlService.url("/interventi-regionali"),
            },
            {
                id: "interventi-locali",
                titolo: "Altri procedimenti",
                sottotitolo:
                    "In questa sezione si possono trovare gli adempimenti relativi" +
                    ' a procedimenti "locali" che non sono presenti nella sezione "Procedimenti gestiti con modulistica unificata"',
                link: this.urlService.url("/interventi-locali"),
            },
        ];
    }
}
