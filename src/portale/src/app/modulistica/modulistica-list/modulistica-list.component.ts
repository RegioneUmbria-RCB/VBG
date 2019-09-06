
import {zip as observableZip,  Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';


import { ModulisticaService } from '../modulistica.service';
import { SoftwareModulisticaModel } from '../software-modulistica.model';
import { CategoriaModulisticaItem } from '../categoria-modulistica.model';
import { UrlServiziLocaliService } from '../../core/services/url';

@Component({
    selector: 'app-modulistica-list',
    templateUrl: './modulistica-list.component.html'
})
export class ModulisticaListComponent implements OnInit {

    listaCompleta: CategoriaModulisticaItem[] = [];
    elementiTop: CategoriaModulisticaItem[] = [];

    dataSource: CategoriaModulisticaItem[] = [];

    caricamentoCompletato = false;

    constructor(private service: ModulisticaService, private downloadUrlService: UrlServiziLocaliService) { }

    ngOnInit() {

        observableZip(
            this.service.getList(),
            this.service.getTop(),

            (...results) => {
                return {
                    listaCompleta: results[0] as SoftwareModulisticaModel,
                    elementiTop: results[1] as SoftwareModulisticaModel
                };
            }).subscribe(results => {

                if (results.listaCompleta) {
                    this.listaCompleta = results.listaCompleta.categorie;
                    this.elementiTop = results.elementiTop.categorie;
                }

                this.mostraInPrimoPiano();

                this.caricamentoCompletato = true;
            });

    }

    onKeyUp(testo: string, $event: KeyboardEvent) {

        if (testo.length === 0) {
            this.mostraInPrimoPiano();

            return;
        }

        const tmp = testo.toLowerCase();

        const tmpList = this.listaCompleta.map(m => {
            const newItem = new CategoriaModulisticaItem();

            newItem.codice = m.codice;
            newItem.nome = m.nome;

            if (m.nome && m.nome.toLowerCase().indexOf(testo) >= 0) {
                newItem.modulistica = m.modulistica;
            } else {
                newItem.modulistica = m.modulistica.filter(item => item.titolo.toLowerCase().indexOf(tmp) >= 0 ||
                    item.descrizione.toLowerCase().indexOf(tmp) >= 0);
            }



            return newItem;
        });

        this.dataSource = tmpList.filter(x => x.modulistica.length > 0);
    }

    mostraInPrimoPiano() {
        this.dataSource = this.elementiTop;
    }

    urlDownload(codiceOggetto: string) {
        return this.downloadUrlService.url('download', [codiceOggetto]);
    }

    mostraListaCompleta() {
        this.dataSource = this.listaCompleta;
    }

}
