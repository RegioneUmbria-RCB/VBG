import { map, tap } from "rxjs/operators";
import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlLocaliService, RisorseService } from "../../core/services";
import { InterventiLocaliService } from "../services";
import { InterventiTreeNavigatorComponent } from "../interventi-tree-navigator/interventi-tree-navigator.component";
import { FogliaSelezionataEventModel } from '@interventi/interventi-tree-navigator/foglia-selezionata-event.model';
import { RicercaInterventiComponent } from "../ricerca-interventi/ricerca-interventi.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-interventi-locali",
    templateUrl: "./interventi-locali.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        RicercaInterventiComponent,
        InterventiTreeNavigatorComponent,
    ],
})
export class InterventiLocaliComponent implements OnInit {
    @ViewChild(InterventiTreeNavigatorComponent, { static: true })
    treeNavigator: InterventiTreeNavigatorComponent;

    public caricamentoCompletato = true;
    public titoloPagina: string;

    constructor(
        private service: InterventiLocaliService,
        private router: Router,
        private route: ActivatedRoute,
        private urlLocaliService: UrlLocaliService,
        private risorseService: RisorseService
    ) { }

    ngOnInit(): void {
        this.titoloPagina = this.risorseService.getRisorsa(
            "interventi.lista.titoloPagina",
            "Procedimenti, modulistica ed adempimenti"
        );

        this.treeNavigator.service = this.service;

        this.route.queryParams
            .pipe(
                map((params) => <string>params["open"]),
                tap((val) => console.log("queryparam open:", val))
            )
            .subscribe((open) =>
                this.treeNavigator.ripristinaGerarchia(open || "-1")
            );
    }

    statoCaricamento(val: boolean): void {
        this.caricamentoCompletato = val;
    }

    onFogliaSelezionata($event: FogliaSelezionataEventModel): void {
        console.log($event);

        const url = this.urlLocaliService.url("/interventi-locali", [
            $event.node.id,
        ]);

        void this.router.navigate([url], {
            queryParams: {
                returnTo: $event.lastNode.id,
            },
        });
    }
}
