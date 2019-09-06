import { Component, OnInit } from '@angular/core';

import { UrlLocaliService } from '../../core/services/url';
import { InfoService, InfoItemModel } from '../../info';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
    selector: 'app-home-info',
    templateUrl: './home-info.component.html'
})
export class HomeInfoComponent implements OnInit {

    infoArray: Observable<InfoItemModel[][]>;

    infoUrl: string;

    constructor(private infoService: InfoService, urlService: UrlLocaliService) {
        this.infoUrl = urlService.url('/info');
    }

    ngOnInit() {
        this.infoArray = this.infoService.getList().pipe(
            map(info => {
                const infoArray = [];
                infoArray.push([]);
                infoArray.push([]);

                info.forEach((i, idx) => {
                    infoArray[idx % 2].push(i);
                });

                return infoArray;
            })
        );

    }

}
