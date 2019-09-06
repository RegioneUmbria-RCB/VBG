import { Component, OnInit } from '@angular/core';

import { DatiComuneService, DatiComuneModel } from '../../dati-comune';
import { AliasSoftwareService } from '../../core/services/alias-software';
import { ThemesPathBuilder } from '../../core/services/themes/themes-builder';

@Component({
    selector: 'app-page-header',
    templateUrl: './page-header.component.html'
})
export class PageHeaderComponent implements OnInit {

    pathLogoComune: string;
    datiComune: DatiComuneModel;
    loaded = false;
    isCollapsed = true;
    passaALinks = false;
    linkComune = false;

    constructor(private datiComuneService: DatiComuneService, private aliasSoftwareService: AliasSoftwareService) {
    }

    ngOnInit() {
        this.datiComuneService.get()
            .subscribe(data => {
                this.datiComune = data;
                this.pathLogoComune = new ThemesPathBuilder(data.themeLocation).getLogoComune();
                this.loaded = true;
                if (this.datiComune.passaALinks) {
                    this.passaALinks = true;

                }
                if (this.datiComune.linkComune) {
                    this.linkComune = true;
                }
            });
    }
}

