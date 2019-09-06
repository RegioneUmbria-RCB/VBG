import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER, Injector } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxPageScrollCoreModule } from 'ngx-page-scroll-core';
import { NgxPageScrollModule } from 'ngx-page-scroll';
import { AppComponent } from './app.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { HomePageComponent } from './home-page/home-page.component';
import { PageHeaderComponent, PageFooterComponent } from './shared';
import { CosaPuoiFareComponent } from './home-page/cosa-puoi-fare/cosa-puoi-fare.component';
import { HomeInPrimoPianoComponent } from './home-page/home-in-primo-piano/home-in-primo-piano.component';
import { HomeNewsComponent } from './home-page/home-news/home-news.component';
import { HomeFaqComponent } from './home-page/home-faq/home-faq.component';
import { HomeInfoComponent } from './home-page/home-info/home-info.component';
import { DatiComuneService } from './dati-comune';
import { CommonModule } from '@angular/common';
import { ConfigurationService, ConfigurationFileUrlService, RisorseService } from './core/services';
import { AliasSoftwareService } from './core/services/alias-software';
import { PageLayoutComponent } from './shared/page-layout/page-layout.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { UrlLocaliService, UrlServiziLocaliService, UrlServiziRegionaliService } from './core/services/url';
// Servizi verso il backend
import { OrariEContattiService } from './dati-comune';
import { InterventiLocaliService, InterventiRegionaliService } from './interventi';
import { ProcedimentiLocaliService, ProcedimentiRegionaliService, ProcedimentiServiceFactory } from './procedimenti/services';
import { NewsService } from './news';
import { FaqService } from './faq';
import { InfoService } from './info';
import { NormativaService } from './normativa';
import { ModulisticaService } from './modulistica';
import { TrasparenzaService } from './trasparenza/trasparenza.service';
import { GeocodingService } from './core/services/geolocalizzazione';

// pipes
import { StripHtmlPipe } from './core/pipes/strip-html.pipe';
import { RicercaInterventiComponent } from './interventi/ricerca-interventi/ricerca-interventi.component';
import { InfoListPageComponent } from './info/info-list-page/info-list-page.component';
import { InfoDetailPageComponent } from './info/info-detail-page/info-detail-page.component';
import { InnerPageLayoutComponent } from './shared/inner-page-layout/inner-page-layout.component';
import { ButtonGridComponent } from './core/components';
import { NormativaListComponent } from './normativa/normativa-list/normativa-list.component';
import { LoaderComponent } from './core/components/loader/loader.component';
import { ModulisticaListComponent } from './modulistica/modulistica-list/modulistica-list.component';
import { NewsListComponent } from './news/news-list/news-list.component';
import { InterventiSelezioneAreaComponent } from './interventi/interventi-selezione-area/interventi-selezione-area.component';
import { InterventiLocaliComponent } from './interventi/interventi-locali/interventi-locali.component';
import { InterventiRegionaliComponent } from './interventi/interventi-regionali/interventi-regionali.component';
import { InterventiLocaliDetailComponent } from './interventi/interventi-locali-detail/interventi-locali-detail.component';
import { InterventiTreeNavigatorComponent } from './interventi/interventi-tree-navigator/interventi-tree-navigator.component';
import { InterventiRegionaliDetailComponent } from './interventi/interventi-regionali-detail/interventi-regionali-detail.component';
import { InterventiDetailComponent } from './interventi/interventi-detail/interventi-detail.component';
import { InterventoGrigliaEndoComponent } from './interventi/interventi-detail/intervento-griglia-endo/intervento-griglia-endo.component';
// tslint:disable-next-line:max-line-length
import { InterventoListaInterventiLocaliComponent } from './interventi/interventi-detail/intervento-lista-interventi-locali/intervento-lista-interventi-locali.component';
// tslint:disable-next-line:max-line-length
import { InterventoFasiAttuativeComponent } from './interventi/interventi-detail/intervento-fasi-attuative/intervento-fasi-attuative.component';
import { ProcedimentiDetailBaseComponent } from './procedimenti/procedimenti-detail-base/procedimenti-detail-base.component';
import { ProcedimentiDetailLocaliComponent } from './procedimenti/procedimenti-detail-locali/procedimenti-detail-locali.component';
import { SezioneComponent } from './core/components/sezione/sezione.component';
import { GrigliaOneriComponent } from './core/components/griglia-oneri/griglia-oneri.component';
import { GrigliaModulisticaComponent } from './core/components/griglia-modulistica/griglia-modulistica.component';
import { GrigliaNormativaComponent } from './core/components/griglia-normativa/griglia-normativa.component';
import { NewsDetailComponent } from './news/news-detail/news-detail.component';
import { ShareSocialComponent } from './core/components/share-social/share-social.component';
import { FaqListComponent } from './faq/faq-list/faq-list.component';
import { TrasparenzaListComponent } from './trasparenza/trasparenza-list/trasparenza-list.component';
import { TrasparenzaDetailComponent } from './trasparenza/trasparenza-detail/trasparenza-detail.component';
import { LeafletMapComponent } from './core/components/leaflet-map/leaflet-map.component';
import { NuovaDomandaComponent } from './shared/nuova-domanda/nuova-domanda.component';
import { PratichePresentateComponent } from './shared/pratiche-presentate/pratiche-presentate.component';
import { ProcedimentiDetailRegionaliComponent } from './procedimenti/procedimenti-detail-regionali/procedimenti-detail-regionali.component';
import { SezioneInterventoComponent } from './interventi/interventi-detail/sezione-intervento/sezione-intervento.component';
import { CosaPuoiFareV2Component } from './home-page/cosa-puoi-fare-v2/cosa-puoi-fare-v2.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';



const appRoutes: Routes = [
    {
        path: '',
        children: [
            { path: 'index/:alias/:software', component: HomePageComponent },
            { path: 'nuova-domanda/:alias/:software', component: NuovaDomandaComponent },
            { path: 'pratiche-presentate/:alias/:software', component: PratichePresentateComponent },

            { path: 'home/:alias/:software', component: HomePageComponent },
            { path: 'info/:alias/:software', component: InfoListPageComponent },
            { path: 'info/:alias/:software/:id', component: InfoDetailPageComponent },
            { path: 'normativa/:alias/:software', component: NormativaListComponent },
            { path: 'interventi-selezione-area/:alias/:software', component: InterventiSelezioneAreaComponent },
            { path: 'interventi-locali/:alias/:software', component: InterventiLocaliComponent },
            { path: 'interventi-locali/:alias/:software/:id', component: InterventiLocaliDetailComponent },
            { path: 'interventi-regionali/:alias/:software', component: InterventiRegionaliComponent },
            { path: 'interventi-regionali/:alias/:software/:id', component: InterventiRegionaliDetailComponent },

            { path: 'procedimenti/:alias/:software/:id', component: ProcedimentiDetailLocaliComponent },
            { path: 'procedimenti-regionali/:alias/:software/:id', component: ProcedimentiDetailRegionaliComponent },

            { path: 'modulistica/:alias/:software', component: ModulisticaListComponent },
            { path: 'news/:alias/:software', component: NewsListComponent },
            { path: 'news/:alias/:software/:id', component: NewsDetailComponent },


            { path: 'faq/:alias/:software', component: FaqListComponent },
            { path: 'faq/:alias/:software/:id', component: FaqListComponent },

            { path: 'archivio-pratiche/:alias/:software', component: TrasparenzaListComponent },
            { path: 'archivio-pratiche/:alias/:software/:id', component: TrasparenzaDetailComponent }

        ]
    },
    { path: '404', component: PageNotFoundComponent },
    { path: '**', redirectTo: '/404' }
];

export class AppInitService {

    constructor(private aliasSoftwareService: AliasSoftwareService, private configService: ConfigurationService) { }

    init(): Promise<void> {

        console.log('window.location.origin: ' + window.location.origin);
        console.log('window.location: ' + window.location);

        const href = window.location.href;
        const origin = window.location.origin + document.head.getElementsByTagName('base')[0].getAttribute('href')
        const urlParts = href.split('?')[0].replace(origin, '').split('/').filter(x => x.length > 0);
        const path = urlParts[0];
        const alias = urlParts[1];
        const software = urlParts[2];

        this.aliasSoftwareService.setAliasSoftware(alias, software);

        return new Promise<void>((resolve, reject) => {

            console.log('Inizializzazione della configurazione');

            this.configService.init().subscribe(() => resolve());
        });
    }
}

export function initializeApp(appInitService: AppInitService) {
    return (): Promise<any> => {
        return appInitService.init();
    };
}

@NgModule({
    declarations: [
        AppComponent,
        HomePageComponent,
        PageHeaderComponent,
        PageFooterComponent,
        CosaPuoiFareComponent,
        HomeInPrimoPianoComponent,
        HomeNewsComponent,
        HomeFaqComponent,
        HomeInfoComponent,
        PageLayoutComponent,
        PageNotFoundComponent,
        StripHtmlPipe,
        RicercaInterventiComponent,
        InfoListPageComponent,
        InnerPageLayoutComponent,
        ButtonGridComponent,
        InfoDetailPageComponent,
        NormativaListComponent,
        LoaderComponent,
        ModulisticaListComponent,
        NewsListComponent,
        InterventiSelezioneAreaComponent,
        InterventiLocaliComponent,
        InterventiRegionaliComponent,
        InterventiLocaliDetailComponent,
        InterventiTreeNavigatorComponent,
        InterventiRegionaliDetailComponent,
        InterventiDetailComponent,
        InterventoGrigliaEndoComponent,
        InterventoListaInterventiLocaliComponent,
        InterventoFasiAttuativeComponent,
        ProcedimentiDetailBaseComponent,
        ProcedimentiDetailLocaliComponent,
        SezioneComponent,
        GrigliaOneriComponent,
        GrigliaModulisticaComponent,
        GrigliaNormativaComponent,
        NewsDetailComponent,
        ShareSocialComponent,
        FaqListComponent,
        TrasparenzaListComponent,
        TrasparenzaDetailComponent,
        LeafletMapComponent,
        NuovaDomandaComponent,
        PratichePresentateComponent,
        ProcedimentiDetailRegionaliComponent,
        SezioneInterventoComponent,
        CosaPuoiFareV2Component
    ],
    imports: [
        BrowserModule,
        FormsModule,
        CommonModule,
        HttpClientModule,
        NgxPageScrollModule,
        NgxPageScrollCoreModule.forRoot({ duration: 500 }),
        LeafletModule.forRoot(),
        RouterModule.forRoot(
            appRoutes,
            { enableTracing: false } // <-- debugging purposes only
        ),
        BrowserAnimationsModule,
        CollapseModule.forRoot(),
        BsDropdownModule.forRoot()
    ],
    providers: [
        ConfigurationService,
        AliasSoftwareService,
        DatiComuneService,
        ConfigurationFileUrlService,
        UrlLocaliService,
        UrlServiziLocaliService,
        UrlServiziRegionaliService,
        OrariEContattiService,
        InterventiLocaliService,
        InterventiRegionaliService,
        ProcedimentiLocaliService,
        ProcedimentiRegionaliService,
        ProcedimentiServiceFactory,
        NewsService,
        FaqService,
        NormativaService,
        InfoService,
        ModulisticaService,
        TrasparenzaService,
        GeocodingService,
        RisorseService,
        AppInitService,
        { provide: APP_INITIALIZER, useFactory: initializeApp, deps: [AppInitService, AliasSoftwareService], multi: true }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
