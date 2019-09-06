import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';

import { InterventoDetailModel } from '../services';

@Component({
  selector: 'app-interventi-detail',
  templateUrl: './interventi-detail.component.html',
  styleUrls: ['./interventi-detail.component.less']
})
export class InterventiDetailComponent implements OnInit {

  @Input() intervento: InterventoDetailModel;
  @Output() onTornaIndietro = new EventEmitter<string>();
  @Output() onDownload = new EventEmitter<string>();
  @Output() onEndoprocedimentoSelezionato = new EventEmitter<any>();


  constructor(private location: Location) { }

  ngOnInit() {
  }

  tornaIndietro() {
    this.onTornaIndietro.emit();
  }

  download($event: string) {
    console.log($event);
    this.onDownload.emit($event);
  }

  endoprocedimentoSelezionato($event: any) {
    console.log($event);
    this.onEndoprocedimentoSelezionato.emit($event);
  }

}
