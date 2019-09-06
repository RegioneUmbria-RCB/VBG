import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-griglia-modulistica',
  templateUrl: './griglia-modulistica.component.html',
  styleUrls: ['./griglia-modulistica.component.less']
})
export class GrigliaModulisticaComponent implements OnInit {

  @Input() dataSource: any[];
  @Output() onDownload = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  download($event: string) {
    console.log($event);
    this.onDownload.emit($event);
  }

}
