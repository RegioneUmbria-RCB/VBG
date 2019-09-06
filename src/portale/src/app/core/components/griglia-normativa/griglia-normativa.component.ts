import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-griglia-normativa',
  templateUrl: './griglia-normativa.component.html',
  styleUrls: ['./griglia-normativa.component.less']
})
export class GrigliaNormativaComponent implements OnInit {

  @Input() dataSource: any[];
  @Output() onDownload = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  download($event: string) {
    this.onDownload.emit($event);
  }
}
