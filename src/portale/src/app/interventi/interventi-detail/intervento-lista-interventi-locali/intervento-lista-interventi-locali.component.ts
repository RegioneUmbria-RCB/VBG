import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-intervento-lista-interventi-locali',
  templateUrl: './intervento-lista-interventi-locali.component.html',
  styleUrls: ['./intervento-lista-interventi-locali.component.less']
})
export class InterventoListaInterventiLocaliComponent implements OnInit {

  @Input() dataSource: any[];

  @Output() onEndoprocedimentoSelezionato = new EventEmitter<any>();


  constructor() { }

  ngOnInit() {
  }

  endoprocedimentoSelezionato($event: any) {
    this.onEndoprocedimentoSelezionato.emit($event);
  }
}
