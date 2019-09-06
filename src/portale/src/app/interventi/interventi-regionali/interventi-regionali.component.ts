
import {filter, map} from 'rxjs/operators';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable } from 'rxjs';


import { UrlLocaliService } from '../../core/services/url';
import { InterventiRegionaliService, InterventiTreeItemModel } from '../services';
import { InterventiTreeNavigatorComponent } from '../interventi-tree-navigator/interventi-tree-navigator.component';


@Component({
  selector: 'app-interventi-regionali',
  templateUrl: './interventi-regionali.component.html',
  styleUrls: ['./interventi-regionali.component.less']
})
export class InterventiRegionaliComponent implements OnInit {

  caricamentoCompletato = true;

  @ViewChild(InterventiTreeNavigatorComponent, { static: true }) treeNavigator: InterventiTreeNavigatorComponent;

  constructor(private service: InterventiRegionaliService, private router: Router,
    private route: ActivatedRoute, private urlLocaliService: UrlLocaliService) { }

  ngOnInit() {

    this.treeNavigator.service = this.service;

    this.route.queryParams.pipe(
      map(params => params['open']),
      filter(val => val),)
      .subscribe(open => this.treeNavigator.ripristinaGerarchia(open));

    this.treeNavigator.ripristinaGerarchia('-1');
  }

  statoCaricamento(val: boolean) {
    this.caricamentoCompletato = val;
  }


  onFogliaSelezionata($event: any) {
    console.log($event);

    const url = this.urlLocaliService.url('/interventi-regionali', [$event.node.id]);

    this.router.navigate([url], {
      queryParams: {
        returnTo: $event.lastNode.id
      }
    });

  }

}
