import { mergeMap, filter, map, finalize } from "rxjs/operators";
import { Component, OnInit } from "@angular/core";
import { Location, AsyncPipe } from "@angular/common";
import { ActivatedRoute, Router } from "@angular/router";
import { Observable } from "rxjs";

import { InterventiLocaliService, InterventoDetailModel } from "../services";
import {
    UrlLocaliService,
    UrlServiziLocaliService,
} from "../../core/services/url";
import { ConfigurationService } from "../../core/services/configuration/configuration.service";
import { Configuration } from "../../core/services/configuration/configuration.model";
import { EndoprocedimentoModel } from '@interventi/interventi-detail/intervento-griglia-endo/endoprocedimento.model';
import { InterventiDetailComponent } from "../interventi-detail/interventi-detail.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-interventi-locali-detail",
    templateUrl: "./interventi-locali-detail.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        InterventiDetailComponent,
        AsyncPipe,
    ],
})
export class InterventiLocaliDetailComponent implements OnInit {
    permetteDownloadModello = false;
    caricamentoCompletato = false;
    intervento$: Observable<InterventoDetailModel>;
    arConfig: Configuration;
    isUrlVaiAServizioPresente: boolean;

    // private _subscription: Subscription;

    constructor(
        private service: InterventiLocaliService,
        private urlLocaliService: UrlLocaliService,
        private route: ActivatedRoute,
        private router: Router,
        private location: Location,
        private urlServizi: UrlServiziLocaliService,
        private configService: ConfigurationService
    ) { }

    ngOnInit(): void {
        this.arConfig = this.configService.getConfiguration();

        if (
            this.arConfig.backend.areaRiservata.urlPresentaInterventoLocale !==
            "" &&
            this.arConfig.backend.areaRiservata.urlPresentaInterventoLocale
        ) {
            this.isUrlVaiAServizioPresente = true;
        }

        this.permetteDownloadModello =
            this.urlServizi.permetteDownloadModello();

        this.intervento$ = this.route.params.pipe(
            map((params) => <string>params["id"]),
            filter((val) => val !== null && val !== undefined && val != ''),
            mergeMap((id) =>
                this.service
                    .getById(id)
                    .pipe(finalize(() => (this.caricamentoCompletato = true)))
            )
        );
    }

    tornaIndietro(): void {
        const url = this.urlLocaliService.url("/interventi-locali");
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

        const url = this.urlLocaliService.url("/procedimenti", [id]);

        void this.router.navigate(
            [url] /* , {
          queryParams: {
            intervento: this.route.snapshot.params['id']
          }
        }*/
        );
    }

    onVaiAServizio($event: string): void {
        const url =
            this.arConfig.backend.areaRiservata.urlPresentaInterventoLocale.replace(
                "{codiceIntervento}",
                $event
            );

        window.open(url);
    }
}
