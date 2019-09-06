import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.less']
})
export class PageNotFoundComponent implements OnInit {

  isProduction: boolean = environment.production;

  constructor() { }

  ngOnInit() {
  }

}
