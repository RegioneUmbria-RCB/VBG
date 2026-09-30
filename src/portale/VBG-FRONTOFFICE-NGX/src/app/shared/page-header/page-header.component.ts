import { Component, OnInit } from "@angular/core";
import { DatiComuneService, DatiComuneModel } from "../../dati-comune";
import { ThemesPathBuilder } from "../../core/services/themes/themes-builder";
import { RisorseService } from "@core/services";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { RouterLink } from "@angular/router";
import { CollapseModule } from "ngx-bootstrap/collapse";
import { NgClass } from "@angular/common";

@Component({
    selector: "app-page-header",
    templateUrl: "./page-header.component.html",
    standalone: true,
    imports: [
    NgClass,
    CollapseModule,
    RouterLink,
    BsDropdownModule
],
})
export class PageHeaderComponent implements OnInit {
    pathLogoComune: string;
    datiComune: DatiComuneModel;
    loaded = false;
    isCollapsed = true;
    passaALinks = false;
    linkComune = false;
    nascondiTitoloPagina = false;
    nascondiAccessoAreaRiservata = false;

    constructor(
        private datiComuneService: DatiComuneService,
        private risorseService: RisorseService
    ) { }

    ngOnInit(): void {
        this.datiComuneService.get().subscribe((data) => {
            this.datiComune = data;
            this.pathLogoComune = new ThemesPathBuilder(
                data.themeLocation
            ).getLogoComune();
            this.loaded = true;
            if (this.datiComune.passaALinks) {
                this.passaALinks = true;
            }
            if (this.datiComune.linkComune) {
                this.linkComune = true;
            }
        });

        this.nascondiTitoloPagina =
            this.risorseService.getRisorsa(
                "header.nascondi-titolo-applicazione",
                "false"
            ) === "true";

        this.nascondiAccessoAreaRiservata = this.risorseService.getRisorsa(
            "header.nascondi-accesso-area-riservata",
            "false"
        ) === "true";

        console.log("this.nascondiTitoloPagina", this.nascondiTitoloPagina);
        console.log("this.nascondiAccessoAreaRiservata", this.nascondiAccessoAreaRiservata);
    }
}
