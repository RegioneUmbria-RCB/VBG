import { Component, EventEmitter, Input, Output } from "@angular/core";
import { UrlLocaliService } from "@core/services";
import { PagerConfig } from "app/trasparenza/pagerConfig";
import { TrasparenzaSearchResultModel } from "app/trasparenza/trasparenza-search-result.model";
import { PageChangedEvent } from "ngx-bootstrap/pagination";
import { CaricaDataSourcePagingModel } from "../carica-data-source-paging.model";
import { PaginationComponent } from "../../pagination/pagination/pagination.component";
import { LoaderComponent } from "../../../core/components/loader/loader.component";
import { RouterLink } from "@angular/router";
import { NgClass } from "@angular/common";

@Component({
    selector: "app-tabella-trasparenza",
    templateUrl: "./tabella-trasparenza.component.html",
    standalone: true,
    imports: [
    NgClass,
    RouterLink,
    LoaderComponent,
    PaginationComponent
],
})
export class TabellaTrasparenzaComponent {
    @Input()
    public dataSource: TrasparenzaSearchResultModel;
    @Input()
    public pagerConfig = new PagerConfig();
    @Input()
    public recordsPerPagina = 10;
    @Input()
    public currentPage = 0;
    @Input()
    public caricamentoInCorso = true;

    @Output()
    public nuovaRicerca = new EventEmitter();
    @Output()
    public ricaricaDataSource = new EventEmitter<CaricaDataSourcePagingModel>();

    public urlIstanze = "";
    public risultatiPerPagina = [10, 20, 50];

    constructor(private urlService: UrlLocaliService) {
        this.urlIstanze = this.urlService.url("/archivio-pratiche");
        /*
                this.pagerConfig.page = 1;
                this.pagerConfig.maxSizePager = 10;
                this.pagerConfig.firstResultDefault = 0;
                this.pagerConfig.itemsPerPageRicercaDefault = this.risultatiPerPagina[0];
                */
    }


    onPageChanged(event: PageChangedEvent): void {
        console.log("page changed", event);
        this.ricaricaDataSource.next(
            new CaricaDataSourcePagingModel(event.page - 1, event.itemsPerPage)
        );
    }

    impostaRisultatiPerPagina(elementiPerPagina: number): void {
        this.dataSource = null;
        this.ricaricaDataSource.next(
            new CaricaDataSourcePagingModel(0, elementiPerPagina)
        );
    }

    onNuovaRicercaClick(): void {
        this.dataSource = null;
        this.nuovaRicerca.next();
    }
}
