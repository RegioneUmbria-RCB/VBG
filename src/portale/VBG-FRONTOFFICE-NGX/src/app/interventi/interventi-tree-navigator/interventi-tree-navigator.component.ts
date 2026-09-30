import { Observable, zip } from "rxjs";
import { finalize, map, tap } from "rxjs/operators";
import { Component, EventEmitter, OnInit, Input, Output } from "@angular/core";
import { RisorseService } from "../../core/services";
import {
    InterventiBaseService,
    InterventiTreeItemModel,
    InterventiTreeItemPathModel,
} from "../services";
import { FogliaSelezionataEventModel } from "./foglia-selezionata-event.model";
import { ActivatedRoute, Params, Router } from "@angular/router";
import { NgClass, AsyncPipe } from "@angular/common";

@Component({
    selector: "app-interventi-tree-navigator",
    templateUrl: "./interventi-tree-navigator.component.html",
    standalone: true,
    imports: [
    NgClass,
    AsyncPipe
],
})
export class InterventiTreeNavigatorComponent implements OnInit {
    @Output() fogliaSelezionata =
        new EventEmitter<FogliaSelezionataEventModel>();
    @Output() caricamentoIniziato = new EventEmitter<void>();
    @Output() caricamentoCompletato = new EventEmitter<void>();

    public dataSource$: Observable<InterventiTreeItemModel[]>;
    public titoloSezione: string;
    public lastNode = new Array<InterventiTreeItemPathModel>();

    private _service: InterventiBaseService = null;

    constructor(
        public router: Router,
        private route: ActivatedRoute,
        private risorseService: RisorseService
    ) { }

    get service(): InterventiBaseService {
        return this._service;
    }

    @Input()
    set service(val: InterventiBaseService) {
        this._service = val;
    }

    ngOnInit(): void {
        this.titoloSezione = this.risorseService.getRisorsa(
            "interventiTreeNavigatorComponent.titoloSezione",
            "Lista attività"
        );
    }

    elementoSelezionato(nodo: InterventiTreeItemModel): void {
        if (!nodo.hasChilds) {
            this.fogliaSelezionata.emit(
                new FogliaSelezionataEventModel(nodo, this.lastNode.pop())
            );
        } else {
            this.navigaNodo(nodo.id);
        }
    }

    public navigaNodo(idNodo: string): void {
        const params: Params = { open: idNodo };

        void this.router.navigate([], {
            relativeTo: this.route,
            queryParams: params,
            queryParamsHandling: "merge",
        });
    }

    tornaIndietro(nodo: InterventiTreeItemPathModel): void {
        this.caricamentoIniziato.emit();

        this.service
            .getGerarchia(nodo.id)
            .pipe(
                map((list) => (list.length > 1 ? list[list.length - 2] : "-1"))
            )
            .subscribe((idNodo) => this.ripristinaGerarchia(idNodo));
    }

    ripristinaGerarchia(idNodo: string): void {
        console.log("ripristina gerarchia ", idNodo);

        this.dataSource$ = zip(
            this.service.getSottonodi(idNodo),
            this.service.getTreeItemById(idNodo)
        ).pipe(
            tap(
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                ([sottonodi, treeItem]) =>
                (this.lastNode =
                    idNodo === "-1"
                        ? new Array<InterventiTreeItemPathModel>()
                        : treeItem.percorso)
            ),
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            map(([sottonodi, treeItem]) => sottonodi),
            finalize(() => this.caricamentoCompletato.emit())
        );
    }
}
