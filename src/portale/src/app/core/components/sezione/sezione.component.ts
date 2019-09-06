import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-sezione',
  templateUrl: './sezione.component.html',
  styleUrls: ['./sezione.component.less']
})
export class SezioneComponent implements OnInit {

  @Input() titolo: string;
  @Input() corpo: string;

  constructor() { }

  ngOnInit() {
  }

}
