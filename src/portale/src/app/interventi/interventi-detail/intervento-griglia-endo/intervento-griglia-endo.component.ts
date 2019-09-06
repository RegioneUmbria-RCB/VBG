import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-intervento-griglia-endo',
  templateUrl: './intervento-griglia-endo.component.html',
  styleUrls: ['./intervento-griglia-endo.component.less']
})
export class InterventoGrigliaEndoComponent implements OnInit {

  @Input() dataSource: any[];
  @Input() titolo = 'Endoprocedimenti';

  @Output() onEndoprocedimentoSelezionato = new EventEmitter<any>();


  constructor() { }

  ngOnInit() {
  }

  endoprocedimentoSelezionato(e: any) {
    this.onEndoprocedimentoSelezionato.emit(e);
  }

}
