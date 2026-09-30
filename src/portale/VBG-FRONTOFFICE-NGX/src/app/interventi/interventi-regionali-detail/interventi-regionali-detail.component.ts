import {
    finalize,
    tap,
    switchMap,
} from "rxjs/operators";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Location, AsyncPipe } from "@angular/common";
import { Observable } from "rxjs";
import { InterventiRegionaliService, InterventoDetailModel } from "../services";
import {
    UrlLocaliService,
    UrlServiziRegionaliService,
} from "../../core/services/url";
import { ConfigurationService } from "../../core/services/configuration/configuration.service";
import { Configuration } from "../../core/services/configuration/configuration.model";
import { EndoprocedimentoModel } from '@interventi/interventi-detail/intervento-griglia-endo/endoprocedimento.model';
import { InterventiDetailComponent } from "../interventi-detail/interventi-detail.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-interventi-regionali-detail",
    templateUrl: "./interventi-regionali-detail.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        InterventiDetailComponent,
        AsyncPipe,
    ],
})
export class InterventiRegionaliDetailComponent implements OnInit {
    permetteDownloadModello = false;
    intervento$: Observable<InterventoDetailModel>;
    isLoading = true;
    arConfig: Configuration;
    isUrlVaiAServizioPresente: boolean;

    constructor(
        private service: InterventiRegionaliService,
        private urlLocaliService: UrlLocaliService,
        private route: ActivatedRoute,
        private router: Router,
        private location: Location,
        private urlServizi: UrlServiziRegionaliService,
        private configService: ConfigurationService
    ) { }

    public ngOnInit(): void {
        this.arConfig = this.configService.getConfiguration();

        if (
            this.arConfig.backend.areaRiservata.urlPresentaInterventoConsole !==
            "" &&
            this.arConfig.backend.areaRiservata.urlPresentaInterventoConsole
        ) {
            this.isUrlVaiAServizioPresente = true;
        }

        this.permetteDownloadModello =
            this.urlServizi.permetteDownloadModello();

        this.intervento$ = this.route.params.pipe(
            tap(() => (this.isLoading = true)),
            switchMap((params) =>
                this.service.getById(<string>params["id"]).pipe(
                    finalize(() => {
                        this.isLoading = false;
                    })
                )
            )
        );
    }

    tornaIndietro(): void {
        const url = this.urlLocaliService.url("/interventi-regionali");
        const returnTo = <string>this.route.snapshot.queryParams["returnTo"];

        if (returnTo) {
            void this.router.navigate([url], {
                queryParams: {
                    open: returnTo,
                },
            });
        } else {
            this.location.back();
        }
    }

    download($event: string): void {
        const url = this.urlServizi.url("download", [$event]);

        window.open(url);
    }

    onScaricaModello($event: string): void {
        const url = this.urlServizi.urlDownloadModelloDomanda(parseInt($event));
        window.open(url);
    }

    onEndoprocedimentoSelezionato($event: EndoprocedimentoModel): void {
        let id = $event.id;

        if ($event.regionale) {
            id = "R-" + id;
        }

        const url = this.urlLocaliService.url("/procedimenti-regionali", [id]);

        void this.router.navigate([url]);
    }

    onVaiAServizio($event: string): void {
        const url =
            this.arConfig.backend.areaRiservata.urlPresentaInterventoConsole.replace(
                "{codiceIntervento}",
                $event
            );

        window.open(url);
    }
}
