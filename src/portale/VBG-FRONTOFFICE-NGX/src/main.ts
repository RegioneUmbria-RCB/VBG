import { enableProdMode, APP_INITIALIZER, importProvidersFrom } from "@angular/core";
import { environment } from "./environments/environment";
import { AppComponent } from "./app/app.component";
import { PaginationModule } from "ngx-bootstrap/pagination";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { CollapseModule } from "ngx-bootstrap/collapse";
import { provideAnimations } from "@angular/platform-browser/animations";
import { PageNotFoundComponent } from "./app/shared/page-not-found/page-not-found.component";
import { TrasparenzaDetailComponent } from "./app/trasparenza/trasparenza-detail/trasparenza-detail.component";
import { TrasparenzaListComponent } from "./app/trasparenza/trasparenza-list/trasparenza-list.component";
import { FaqListComponent } from "./app/faq/faq-list/faq-list.component";
import { NewsDetailComponent } from "./app/news/news-detail/news-detail.component";
import { NewsListComponent } from "./app/news/news-list/news-list.component";
import { ModulisticaListComponent } from "./app/modulistica/modulistica-list/modulistica-list.component";
import { ProcedimentiDetailRegionaliComponent } from "./app/procedimenti/procedimenti-detail-regionali/procedimenti-detail-regionali.component";
import { ProcedimentiDetailLocaliComponent } from "./app/procedimenti/procedimenti-detail-locali/procedimenti-detail-locali.component";
import { InterventiRegionaliDetailComponent } from "./app/interventi/interventi-regionali-detail/interventi-regionali-detail.component";
import { InterventiRegionaliComponent } from "./app/interventi/interventi-regionali/interventi-regionali.component";
import { InterventiLocaliDetailComponent } from "./app/interventi/interventi-locali-detail/interventi-locali-detail.component";
import { InterventiLocaliComponent } from "./app/interventi/interventi-locali/interventi-locali.component";
import { InterventiSelezioneAreaComponent } from "./app/interventi/interventi-selezione-area/interventi-selezione-area.component";
import { NormativaListComponent } from "./app/normativa/normativa-list/normativa-list.component";
import { InfoDetailPageComponent } from "./app/info/info-detail-page/info-detail-page.component";
import { InfoListPageComponent } from "./app/info/info-list-page/info-list-page.component";
import { PratichePresentateComponent } from "./app/shared/pratiche-presentate/pratiche-presentate.component";
import { NuovaDomandaComponent } from "./app/shared/nuova-domanda/nuova-domanda.component";
import { HomePageComponent } from "./app/home-page/home-page.component";
import { withInMemoryScrolling, provideRouter, Routes } from "@angular/router";
import { NgxPageScrollCoreModule } from "ngx-page-scroll-core";
import { ScrollingModule } from "@angular/cdk/scrolling";
import { NgxPageScrollModule } from "ngx-page-scroll";
import { withInterceptorsFromDi, provideHttpClient } from "@angular/common/http";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserModule, bootstrapApplication } from "@angular/platform-browser";
import { AppInitService } from "./app/app-init.service";
import { SlugifyPipe } from "./app/core/pipes";
import { TrasparenzaService } from "./app/trasparenza/trasparenza.service";
import { ModulisticaService } from "./app/modulistica";
import { InfoService } from "./app/info";
import { NormativaService } from "./app/normativa";
import { FaqService } from "./app/faq";
import { NewsService } from "./app/news";
import { ProcedimentiLocaliService, ProcedimentiRegionaliService, ProcedimentiServiceFactory } from "./app/procedimenti/services";
import { InterventiLocaliService, InterventiRegionaliService } from "./app/interventi";
import { UrlLocaliService, UrlServiziLocaliService, UrlServiziRegionaliService } from "./app/core/services/url";
import { DatiComuneService, OrariEContattiService } from "./app/dati-comune";
import { AliasSoftwareService } from "./app/core/services/alias-software";
import { ConfigurationService, ConfigurationFileUrlService, RisorseService } from "./app/core/services";

const appRoutes: Routes = [
    {
        path: "",
        children: [
            { path: "index/:alias/:software", component: HomePageComponent },
            {
                path: "nuova-domanda/:alias/:software",
                component: NuovaDomandaComponent,
            },
            {
                path: "pratiche-presentate/:alias/:software",
                component: PratichePresentateComponent,
            },

            { path: "home/:alias/:software", component: HomePageComponent },
            { path: "info/:alias/:software", component: InfoListPageComponent },
            {
                path: "info/:alias/:software/:id",
                component: InfoDetailPageComponent,
            },
            {
                path: "normativa/:alias/:software",
                component: NormativaListComponent,
            },
            {
                path: "interventi-selezione-area/:alias/:software",
                component: InterventiSelezioneAreaComponent,
            },
            {
                path: "interventi-locali/:alias/:software",
                component: InterventiLocaliComponent,
            },
            {
                path: "interventi-locali/:alias/:software/:id",
                component: InterventiLocaliDetailComponent,
            },
            {
                path: "interventi-regionali/:alias/:software",
                component: InterventiRegionaliComponent,
            },
            {
                path: "interventi-regionali/:alias/:software/:id",
                component: InterventiRegionaliDetailComponent,
            },

            {
                path: "procedimenti/:alias/:software/:id",
                component: ProcedimentiDetailLocaliComponent,
            },
            {
                path: "procedimenti-regionali/:alias/:software/:id",
                component: ProcedimentiDetailRegionaliComponent,
            },

            {
                path: "modulistica/:alias/:software",
                component: ModulisticaListComponent,
            },
            { path: "news/:alias/:software", component: NewsListComponent },
            {
                path: "news/:alias/:software/:id",
                component: NewsDetailComponent,
            },

            { path: "faq/:alias/:software", component: FaqListComponent },
            { path: "faq/:alias/:software/:id", component: FaqListComponent },

            {
                path: "archivio-pratiche/:alias/:software",
                component: TrasparenzaListComponent,
            },
            {
                path: "archivio-pratiche/:alias/:software/:id",
                component: TrasparenzaDetailComponent,
            },
        ],
    },
    { path: "404", component: PageNotFoundComponent },
    { path: "**", redirectTo: "/404" },
];

const initializeApp =
    (appInitService: AppInitService) => (): Promise<void> =>
        appInitService.init();

if (environment.production) {
    enableProdMode();
}

void bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(BrowserModule, FormsModule, CommonModule, NgxPageScrollModule, ReactiveFormsModule, ScrollingModule, NgxPageScrollCoreModule.forRoot({ duration: 500 }), CollapseModule.forRoot(), BsDropdownModule.forRoot(), PaginationModule.forRoot()),
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
        RisorseService,
        SlugifyPipe,
        AppInitService,
        {
            provide: APP_INITIALIZER,
            useFactory: initializeApp,
            deps: [AppInitService, AliasSoftwareService, ConfigurationService],
            multi: true,
        },
        provideHttpClient(withInterceptorsFromDi()),
        provideRouter(appRoutes, withInMemoryScrolling({ scrollPositionRestoration: "top",
            anchorScrolling: "enabled"
            // relativeLinkResolution: "legacy",
         })),
        provideAnimations(),
    ]
});
