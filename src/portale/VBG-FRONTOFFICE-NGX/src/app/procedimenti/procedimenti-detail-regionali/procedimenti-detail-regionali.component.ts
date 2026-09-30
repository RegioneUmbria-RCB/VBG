import { mergeMap, map, finalize, tap } from "rxjs/operators";
import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import {
    ProcedimentoDetailModel,
    ProcedimentiServiceFactory,
} from "../services";
import { ActivatedRoute } from "@angular/router";
import { UrlServiziRegionaliService } from "../../core/services";
import { AsyncPipe } from "@angular/common";
import { ProcedimentiDetailBaseComponent } from "../procedimenti-detail-base/procedimenti-detail-base.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-procedimenti-detail-regionali",
    templateUrl: "./procedimenti-detail-regionali.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        ProcedimentiDetailBaseComponent,
        AsyncPipe,
    ],
})
export class ProcedimentiDetailRegionaliComponent implements OnInit {
    caricamentoCompletato = false;
    procedimento$: Observable<ProcedimentoDetailModel>;

    constructor(
        private serviceFactory: ProcedimentiServiceFactory,
        private route: ActivatedRoute,
        private urlServizi: UrlServiziRegionaliService
    ) { }

    ngOnInit(): void {
        this.procedimento$ = this.route.params.pipe(
            tap(() => (this.caricamentoCompletato = false)),
            map((params) => ({
                id: <string>params["id"],
                regionale: (<string>params["id"]).indexOf("R-") === 0,
            })),
            mergeMap((id) => {
                const service = this.serviceFactory.create(false);

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
