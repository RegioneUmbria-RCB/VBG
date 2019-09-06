import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-sezione-intervento',
  templateUrl: './sezione-intervento.component.html',
  styleUrls: ['./sezione-intervento.component.less']
})
export class SezioneInterventoComponent implements OnInit {

  @Input() titolo:string = 'titolo sezione';

  constructor() { }

  ngOnInit() {
  }

}
