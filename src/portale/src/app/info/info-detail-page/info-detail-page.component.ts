
import {from as observableFrom, Observable} from 'rxjs';

import {mergeMap, map} from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';


import {InfoService} from '../../info/info.service';
import {InfoItemModel} from '../../info/info-item.model';

@Component({
  selector: 'app-info-detail-page',
  templateUrl: './info-detail-page.component.html',
  styleUrls: ['./info-detail-page.component.less']
})
export class InfoDetailPageComponent implements OnInit, OnDestroy {

  info: InfoItemModel = new InfoItemModel();
  loading = true;

  private sub: any;

  constructor(private infoService: InfoService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.sub = this.route.params.pipe(
    map(params => +params['id']),
    mergeMap(id => observableFrom(this.infoService.getById(id))),)
    .subscribe( info => {

      if (info === undefined) {
        this.router.navigate(['/404']);
      }

      this.info = info;
      this.loading = false;
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}
