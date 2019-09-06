import { HomeConfigurationModel, Configuration } from './../core/services/configuration/configuration.model';
import { ConfigurationService } from './../core/services/configuration/configuration.service';
import { Component, OnInit } from '@angular/core';
import { ThemesPathBuilder } from '../core/services/themes/themes-builder';

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.less']
})
export class HomePageComponent implements OnInit {

    immagineDefault: string;
    srcsetList: string;
    homeConfig: HomeConfigurationModel;

    constructor(private configurationService: ConfigurationService) { }

    ngOnInit() {
        const cfg = this.configurationService.getConfiguration();
        this.initializeHomeConfig(cfg);
        const builder = new ThemesPathBuilder(cfg.globals.themeLocation);
        this.immagineDefault = builder.getBackgroundImage().GetDefaultImage();
        this.srcsetList = builder.getBackgroundImage().getSrcSet();
    }
    initializeHomeConfig(cfg: Configuration) {
        this.homeConfig = {} as HomeConfigurationModel;

        this.homeConfig.faqVisible = true;
        this.homeConfig.infoVisible = true;
        this.homeConfig.newsVisible = true;
        this.homeConfig.primoPianoVisible = true;

        if (cfg.homeConfiguration != null && cfg.homeConfiguration.faqVisible != null) {
          this.homeConfig.faqVisible = cfg.homeConfiguration.faqVisible;
        }
        if (cfg.homeConfiguration != null  && cfg.homeConfiguration.infoVisible != null ) {
          this.homeConfig.infoVisible = cfg.homeConfiguration.infoVisible;
        }
        if (cfg.homeConfiguration != null  && cfg.homeConfiguration.newsVisible != null ) {
          this.homeConfig.newsVisible  = cfg.homeConfiguration.newsVisible ;
        }
        if (cfg.homeConfiguration != null  && cfg.homeConfiguration.primoPianoVisible != null) {
          this.homeConfig.primoPianoVisible = cfg.homeConfiguration.primoPianoVisible;
        }
        console.log(this.homeConfig);
    }
}
