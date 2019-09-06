
import {zip as observableZip,  Observable } from 'rxjs';

import {map} from 'rxjs/operators';
import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';


import { RisorseService } from '../../core/services';
import { InterventiBaseService, InterventiTreeItemModel, InterventiTreeItemPathModel } from '../services';
import { FogliaSelezionataEventModel } from './foglia-selezionata-event.model';
import { Router } from '@angular/router';



@Component({
    selector: 'app-interventi-tree-navigator',
    templateUrl: './interventi-tree-navigator.component.html',
    styleUrls: ['./interventi-tree-navigator.component.less']
})
export class InterventiTreeNavigatorComponent implements OnInit {

    @Output() fogliaSelezionata = new EventEmitter<FogliaSelezionataEventModel>();
    @Output() caricamentoIniziato = new EventEmitter<void>();
    @Output() caricamentoCompletato = new EventEmitter<void>();

    _service: InterventiBaseService = null;
    titoloSezione: string;

    constructor(
        public router: Router, private risorseService: RisorseService
    ) { }

    dataSource: InterventiTreeItemModel[] = [];

    get service(): InterventiBaseService {
        return this._service;
    }

    @Input()
    set service(val: InterventiBaseService) {
        this._service = val;
    }

    lastNode = new Array<InterventiTreeItemPathModel>();

    ngOnInit() {
        this.titoloSezione = this.risorseService.getRisorsa('interventiTreeNavigatorComponent.titoloSezione', 'Lista attività');
    }


    elementoSelezionato(nodo: InterventiTreeItemModel) {

        if (!nodo.hasChilds) {
            this.fogliaSelezionata.emit(new FogliaSelezionataEventModel(nodo, this.lastNode.pop()));
        } else {
            this.lastNode.push(new InterventiTreeItemPathModel(nodo));
            this.caricaSottonodi(nodo.id);
        }
    }


    tornaIndietro(nodo: InterventiTreeItemPathModel) {

        this.caricamentoIniziato.emit();

        this.service
            .getGerarchia(nodo.id).pipe(
            map(list => list.length > 1 ? list[list.length - 2] : '-1'))
            .subscribe(idNodo => this.ripristinaGerarchia(idNodo));
    }

    ripristinaGerarchia(idNodo: string) {
        observableZip(
            this.service.getSottonodi(idNodo),
            this.service.getTreeItemById(idNodo),
            (...res) => {
                return {
                    sottonodi: res[0],
                    treeItem: res[1]
                };
            })
            .subscribe(res => {
                this.dataSource = res.sottonodi;
                this.lastNode = idNodo === '-1' ? new Array<InterventiTreeItemPathModel>() : res.treeItem.percorso;

                this.caricamentoCompletato.emit();
            }, err => console.error(err));
    }

    private caricaSottonodi(idNodo: string) {

        this.caricamentoIniziato.emit();

        return this.service.getSottonodi(idNodo).subscribe(data => {
            this.caricamentoCompletato.emit();

            this.dataSource = data;
        });
    }

}
