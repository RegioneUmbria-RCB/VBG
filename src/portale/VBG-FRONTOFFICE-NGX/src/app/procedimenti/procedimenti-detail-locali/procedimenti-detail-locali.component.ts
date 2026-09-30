import { mergeMap, map, finalize, tap } from "rxjs/operators";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Observable } from "rxjs";

import {
    ProcedimentoDetailModel,
    ProcedimentiServiceFactory,
} from "../services";
import { UrlServiziLocaliService } from "../../core";
import { AsyncPipe } from "@angular/common";
import { ProcedimentiDetailBaseComponent } from "../procedimenti-detail-base/procedimenti-detail-base.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-procedimenti-detail-locali",
    templateUrl: "./procedimenti-detail-locali.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        ProcedimentiDetailBaseComponent,
        AsyncPipe,
    ],
})
export class ProcedimentiDetailLocaliComponent implements OnInit {
    caricamentoCompletato = false;
    procedimento$: Observable<ProcedimentoDetailModel>;

    constructor(
        private serviceFactory: ProcedimentiServiceFactory,
        private route: ActivatedRoute,
        private urlServizi: UrlServiziLocaliService
    ) { }

    ngOnInit(): void {
        this.procedimento$ = this.route.params.pipe(
            tap(() => (this.caricamentoCompletato = false)),
            map((params) => ({
                id: <string>params["id"],
                regionale: (<string>params["id"]).indexOf("R-") === 0,
            })),
            mergeMap((id) => {
                const service = this.serviceFactory.create(!id.regionale);

                return service
                    .getById(id.id)
                    .pipe(finalize(() => (this.caricamentoCompletato = true)));
            })
        );
    }

    download($event: string): void {
        const url = this.urlServizi.url("download", [$event]);

        window.open(url);
    }
}
