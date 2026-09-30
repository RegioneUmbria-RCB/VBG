import { map } from "rxjs/operators";
import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlLocaliService } from "../../core/services/url";
import { InterventiRegionaliService } from "../services";
import { InterventiTreeNavigatorComponent } from "../interventi-tree-navigator/interventi-tree-navigator.component";
import { FogliaSelezionataEventModel } from '@interventi/interventi-tree-navigator/foglia-selezionata-event.model';
import { RicercaInterventiComponent } from "../ricerca-interventi/ricerca-interventi.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-interventi-regionali",
    templateUrl: "./interventi-regionali.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        RicercaInterventiComponent,
        InterventiTreeNavigatorComponent,
    ],
})
export class InterventiRegionaliComponent implements OnInit {
    @ViewChild(InterventiTreeNavigatorComponent, { static: true })
    treeNavigator: InterventiTreeNavigatorComponent;

    public caricamentoCompletato = true;

    constructor(
        private service: InterventiRegionaliService,
        private router: Router,
        private route: ActivatedRoute,
        private urlLocaliService: UrlLocaliService
    ) { }

    public ngOnInit(): void {
        this.treeNavigator.service = this.service;

        this.route.queryParams
            .pipe(map((params) => <string>params["open"]))
            .subscribe((open) => this.treeNavigator.ripristinaGerarchia(open));

        this.treeNavigator.ripristinaGerarchia("-1");
    }

    statoCaricamento(val: boolean): void {
        this.caricamentoCompletato = val;
    }

    onFogliaSelezionata($event: FogliaSelezionataEventModel): void {
        console.log($event);

        const url = this.urlLocaliService.url("/interventi-regionali", [
            $event.node.id,
        ]);

        void this.router.navigate([url], {
            queryParams: {
                returnTo: $event.lastNode.id,
            },
        });
    }
}
