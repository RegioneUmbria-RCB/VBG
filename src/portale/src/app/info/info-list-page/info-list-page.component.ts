import { Component, OnInit } from '@angular/core';
import { InfoService } from '../../info/info.service';
import { ButtonGridItem, UrlLocaliService } from '../../core';
import { Observable } from 'rxjs';
import { map, tap, finalize } from 'rxjs/operators';
import { delay } from 'q';

@Component({
    selector: 'app-info-list-page',
    templateUrl: './info-list-page.component.html'
})
export class InfoListPageComponent implements OnInit {

    infoList: Observable<ButtonGridItem[]>;
    loading = true;

    constructor(private infoService: InfoService) { }

    ngOnInit() {
        this.loading = true;

        this.infoList = this.infoService.getList().pipe(
            map(info => info.map(i => {
                const item = new ButtonGridItem();

                item.id = i.id.toString();
                item.titolo = i.titolo;

                return item;
            })),
            // setTimeout risolve l'errore "ExpressionChangedAfterItHasBeenCheckedError"
            finalize(() => setTimeout(() =>  this.loading = false))
        );
    }
}
