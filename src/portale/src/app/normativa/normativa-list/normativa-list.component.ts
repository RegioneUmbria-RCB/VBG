
import {zip as observableZip,  Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';


import { NormativaService } from '../normativa.service';
import { NormativaItemModel } from '../normativa-item.model';
import { UrlServiziLocaliService } from '../../core/services/url';

@Component({
  selector: 'app-normativa-list',
  templateUrl: './normativa-list.component.html',
  styleUrls: ['./normativa-list.component.less']
})
export class NormativaListComponent implements OnInit {

  listaCompleta: NormativaItemModel[] = [];
  normativaTop: NormativaItemModel[] = [];

  dataSource: NormativaItemModel[] = [];

  caricamentoCompletato = false;

  constructor(private service: NormativaService, private downloadUrlService: UrlServiziLocaliService) { }

  ngOnInit() {

    observableZip(
      this.service.getList(),
      this.service.getTop(),

      (...results) => {
        return {
          listaCompleta: results[0] as NormativaItemModel[],
          normativaTop: results[1] as NormativaItemModel[]
        };
      }).subscribe(results => {
        this.listaCompleta = results.listaCompleta;
        this.normativaTop = results.normativaTop;

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

    this.dataSource = this.listaCompleta
      .filter(item => item.titolo.toLowerCase().indexOf(tmp) >= 0 ||
        item.descrizione.toLowerCase().indexOf(tmp) >= 0);
  }

  mostraInPrimoPiano() {
    this.dataSource = this.normativaTop;
  }

  urlDownload(codiceOggetto: string) {
    return this.downloadUrlService.url('download', [codiceOggetto]);
  }

  mostraListaCompleta() {
    this.dataSource = this.listaCompleta;
  }

}
