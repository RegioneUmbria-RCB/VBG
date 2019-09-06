import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-intervento-fasi-attuative',
  templateUrl: './intervento-fasi-attuative.component.html',
  styleUrls: ['./intervento-fasi-attuative.component.less']
})
export class InterventoFasiAttuativeComponent implements OnInit {

  @Input() dataSource: any[];

  constructor() { }

  ngOnInit() {
  }

}
