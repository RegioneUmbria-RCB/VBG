import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Location} from '@angular/common';
import {ProcedimentoDetailModel} from '../services/procedimento-detail.model';

@Component({
  selector: 'app-procedimenti-detail-base',
  templateUrl: './procedimenti-detail-base.component.html',
  styleUrls: ['./procedimenti-detail-base.component.less']
})
export class ProcedimentiDetailBaseComponent implements OnInit {

  @Input() procedimento: ProcedimentoDetailModel;
  @Input() idAttivita: string;
  @Input() endoRegionale = false;

  @Output() onTornaIndietro = new EventEmitter<string>();
  @Output() onDownload = new EventEmitter<string>();

  constructor(private location: Location) { }

  ngOnInit() {
  }

  download($event: string) {
    this.onDownload.emit($event);
  }

  tornaAdAttivita() {
    this.location.back();
  }
}
