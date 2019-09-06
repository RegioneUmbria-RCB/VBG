import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ParametriRicercaTrasparenzaModel } from '../parametri-ricerca-trasparenza.model';
import { UrlLocaliService } from '../../core/services/url';
import { TrasparenzaService } from '../trasparenza.service';

@Component({
  selector: 'app-trasparenza-list',
  templateUrl: './trasparenza-list.component.html'
})
export class TrasparenzaListComponent implements OnInit {

  public mesi = [
    { nome: 'Gennaio', id: 1 },
    { nome: 'Febbraio', id: 2 },
    { nome: 'Marzo', id: 3 },
    { nome: 'Aprile', id: 4 },
    { nome: 'Maggio', id: 5 },
    { nome: 'Giugno', id: 6 },
    { nome: 'Luglio', id: 7 },
    { nome: 'Agosto', id: 8 },
    { nome: 'Settembre', id: 9 },
    { nome: 'Ottobre', id: 10 },
    { nome: 'Novembre', id: 11 },
    { nome: 'Dicembre', id: 12 }
  ];

  public anni = [];

  public caricamentoInCorso = false;
  public risultatiVisibili = false;
  public errore = false;

  public model = new ParametriRicercaTrasparenzaModel();
  public dataSource: any;


  constructor(private trasparenzaService: TrasparenzaService, private router: Router, private urlService: UrlLocaliService) {
    const currYear = new Date().getFullYear();
    const prevMonth = new Date();

    for (let i = currYear - 5; i < currYear + 1; i += 1) {
      this.anni.push(i);
    }

    prevMonth.setMonth(prevMonth.getMonth());

    this.model.numeroPratica = '';
    this.model.numeroProtocollo = '';
    this.model.dalAnno = prevMonth.getFullYear().toString();
    this.model.dalMese = new Date().getMonth().toString();
    this.model.alMese = (new Date().getMonth() + 1).toString();
    this.model.alAnno = currYear.toString();
    this.model.stato = '';
    this.model.indirizzo = '';
  }

  dataSelezionataChanged(e) {
    console.log('dal ', this.model.dalMese, this.model.dalAnno, ' Al ', this.model.alMese, this.model.alAnno);

    const dalAnno = parseInt(this.model.dalAnno, 10),
      alAnno = parseInt(this.model.alAnno, 10),
      dalMese = parseInt(this.model.dalMese, 10),
      alMese = parseInt(this.model.alMese, 10);

    if (dalAnno > alAnno) {
      this.model.alAnno = this.model.dalAnno;
    }

    if (dalMese > alMese && dalAnno >= alAnno) {
      this.model.alMese = this.model.dalMese;
    }

  }

  ngOnInit() {
  }

  onNuovaRicercaClick($event) {
    this.risultatiVisibili = false;
    this.dataSource = null;
  }

  onCercaClick($event) {
    this.caricamentoInCorso = true;
    this.errore = false;
    this.trasparenzaService.getList(this.model)
                            .subscribe(
                              (data)  => {
                                this.dataSource = data;
                                this.caricamentoInCorso = false;
                                this.risultatiVisibili = true;
                              },
                              (error) => {
                                this.errore = true;
                              });
  }

  onIstanzaSelezionata(item) {

    const url = this.urlService.url('/archivio-pratiche', [item.id]);
    this.router.navigate([url]);
  }
}
