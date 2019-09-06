import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-griglia-oneri',
  templateUrl: './griglia-oneri.component.html',
  styleUrls: ['./griglia-oneri.component.less']
})
export class GrigliaOneriComponent implements OnInit {

  @Input() dataSource: any[];

  constructor() { }

  ngOnInit() {
  }

}
