import { Component, OnInit } from "@angular/core";
import { CosaPuoiFareCardModel } from "../cosa-puoi-fare/cosa-puoi-fare-card.model";
import { UrlLocaliService } from "../../core/services/url";
import { ConfigurationService } from "../../core/services/configuration";
import { ThemesPathBuilder } from "../../core/services/themes/themes-builder";
import { RisorseService } from "../../core/services/risorse/risorse.service";
import { RouterLink } from "@angular/router";


@Component({
    selector: "app-cosa-puoi-fare-v2",
    templateUrl: "./cosa-puoi-fare-v2.component.html",
    standalone: true,
    imports: [RouterLink],
})
export class CosaPuoiFareV2Component implements OnInit {
    cards: CosaPuoiFareCardModel[];
    immagineDefault: string;
    srcsetList: string;

    // eslint-disable-next-line max-len
    constructor(
        private urlLocaliService: UrlLocaliService,
        private configurationService: ConfigurationService,
        private risorseService: RisorseService
    ) { }

    ngOnInit(): void {
        const config = this.configurationService.getConfiguration();

        this.cards = new Array<CosaPuoiFareCardModel>();

        if (config.backend.areaRiservata.urlNuovaDomanda) {
            this.pushCard("nuovaDomanda", {
                titolo: "Inviare una pratica",
                sottotitolo: "Direttamente all'ufficio competente",
                link: this.urlLocaliService.url("/nuova-domanda"),
            });
        }

        if (config.backend.areaRiservata.urlLeMiePratiche) {
            this.pushCard("leMiePratiche", {
                titolo: "Seguire la pratica",
                sottotitolo: "Interrogare lo stato di avanzamento",
                link: this.urlLocaliService.url("/pratiche-presentate"),
            });
        }

        if (config.backend.areaRiservata.urlArchivioPratiche) {
            this.pushCard("archivioPratiche", {
                titolo: "Consultare l'archivio",
                sottotitolo: "Accedere alle pratiche libere",
                link: this.urlLocaliService.url("/archivio-pratiche"),
            });
        }

        const globals = this.configurationService.getConfiguration().globals;
        const builder = new ThemesPathBuilder(globals.themeLocation);

        this.immagineDefault = builder.getBackgroundImage().getDefaultImage();
        this.srcsetList = "";
        if (
            this.risorseService.getRisorsa("home.usaSrcSet", "true") === "true"
        ) {
            this.srcsetList = builder.getBackgroundImage().getSrcSet();
        }
    }

    private pushCard(configKey: string, defaultValues: CosaPuoiFareCardModel) {
        const visibile = this.risorseService.getRisorsa(
            configKey + ".visibile",
            "true"
        );

        if (visibile === "true") {
            defaultValues.titolo = this.risorseService.getRisorsa(
                configKey + ".titolo",
                defaultValues.titolo
            );
            defaultValues.sottotitolo = this.risorseService.getRisorsa(
                configKey + ".sottotitolo",
                defaultValues.sottotitolo
            );

            defaultValues.link = this.risorseService.getRisorsa(
                configKey + ".link",
                defaultValues.link
            );

            this.cards.push(defaultValues);
        }
    }
}
