import {
    HomeConfigurationModel,
    Configuration,
} from "./../core/services/configuration/configuration.model";
import { ConfigurationService } from "./../core/services/configuration/configuration.service";
import { Component, OnInit } from "@angular/core";
import { HomeInfoComponent } from "./home-info/home-info.component";
import { HomeFaqComponent } from "./home-faq/home-faq.component";
import { HomeNewsComponent } from "./home-news/home-news.component";
import { HomeInPrimoPianoComponent } from "./home-in-primo-piano/home-in-primo-piano.component";

import { CosaPuoiFareV2Component } from "./cosa-puoi-fare-v2/cosa-puoi-fare-v2.component";
import { PageLayoutComponent } from "../shared/page-layout/page-layout.component";

@Component({
    selector: "app-home-page",
    templateUrl: "./home-page.component.html",
    standalone: true,
    imports: [
    PageLayoutComponent,
    CosaPuoiFareV2Component,
    HomeInPrimoPianoComponent,
    HomeNewsComponent,
    HomeFaqComponent,
    HomeInfoComponent
],
})
export class HomePageComponent implements OnInit {
    public homeConfig: HomeConfigurationModel;
    public faqPresenti = true;
    public newsPresenti = true;
    public infoPresenti = true;

    constructor(private configurationService: ConfigurationService) { }

    ngOnInit(): void {
        const cfg = this.configurationService.getConfiguration();
        this.initializeHomeConfig(cfg);
    }

    initializeHomeConfig(cfg: Configuration): void {
        this.homeConfig = cfg.homeConfiguration;
    }
}
