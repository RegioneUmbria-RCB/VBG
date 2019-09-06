import { Component, OnInit } from '@angular/core';
import { CosaPuoiFareCardModel } from '../cosa-puoi-fare/cosa-puoi-fare-card.model';
import { UrlLocaliService } from '../../core/services/url';
import { ConfigurationService } from '../../core/services/configuration';
import { ThemesPathBuilder } from '../../core/services/themes/themes-builder';

@Component({
    selector: 'app-cosa-puoi-fare-v2',
    templateUrl: './cosa-puoi-fare-v2.component.html',
    styleUrls: ['./cosa-puoi-fare-v2.component.less']
})
export class CosaPuoiFareV2Component implements OnInit {

    cards: CosaPuoiFareCardModel[];
    immagineDefault: string;
    srcsetList: string;


    constructor(private urlLocaliService: UrlLocaliService, private configurationService: ConfigurationService) { }

    ngOnInit() {
        this.cards = [{
            titolo: 'Inviare una pratica',
            sottotitolo: 'Direttamente all’ufficio competente',
            link: this.urlLocaliService.url('/nuova-domanda')
        },
        {
            titolo: 'Seguire la pratica',
            sottotitolo: 'Interrogare lo stato di avanzamento',
            link: this.urlLocaliService.url('/pratiche-presentate')
        },
        {
            titolo: 'Consultare l’archivio',
            sottotitolo: 'Accedere alle pratiche libere',
            link: this.urlLocaliService.url('/archivio-pratiche')
        }];

        const cfg = this.configurationService.getConfiguration();
        const builder = new ThemesPathBuilder(cfg.globals.themeLocation);
        
        this.immagineDefault = builder.getBackgroundImage().GetDefaultImage();
        this.srcsetList = builder.getBackgroundImage().getSrcSet();
    }

}
