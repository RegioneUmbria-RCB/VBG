import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-inner-page-layout',
  templateUrl: './inner-page-layout.component.html',
  styleUrls: ['./inner-page-layout.component.less']
})
export class InnerPageLayoutComponent implements OnInit {

  @Input() isLoading = false;
  constructor() { }

  ngOnInit() {
  }

}
