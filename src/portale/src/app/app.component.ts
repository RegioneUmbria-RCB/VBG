import { ConfigurationService } from './core/services/configuration/configuration.service';
import { ThemesPathBuilder } from './core/services/themes/themes-builder';
import { Component, HostBinding, OnInit, AfterViewInit } from '@angular/core';

import { SafeResourceUrl, DomSanitizer, Title } from '@angular/platform-browser';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
    @HostBinding('class') public cssClass = '';

    cssUrl: SafeResourceUrl;

    constructor(private configService: ConfigurationService, public sanitizer: DomSanitizer, private titleService: Title) {
        this.cssUrl = this.sanitizer.bypassSecurityTrustResourceUrl('assets/style-blank.css');
    }

    ngOnInit(): void {
        // Esempio di come impostare programmaticamente
        // la classe css del contenitore root
        this.cssClass = 'portale-comunale gualdo';

        const config = this.configService.getConfiguration();
        const themesService = new ThemesPathBuilder(config.globals.themeLocation);

        this.cssUrl = this.sanitizer.bypassSecurityTrustResourceUrl(themesService.getCssPath());

        this.titleService.setTitle(config.globals.denominazioneComune + ' - ' + config.globals.denominazioneSportello);
    }

}

