import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { UrlLocaliService } from "../../core/services/url";
import { InfoService, InfoItemModel } from "../../info";
import { Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { RouterLink } from "@angular/router";
import { AsyncPipe } from "@angular/common";

@Component({
    selector: "app-home-info",
    templateUrl: "./home-info.component.html",
    standalone: true,
    imports: [
    RouterLink,
    AsyncPipe
],
})
export class HomeInfoComponent implements OnInit {
    @Input() infoPresenti = true;
    @Output() infoPresentiChange = new EventEmitter<boolean>();

    infoArray: Observable<InfoItemModel[][]>;
    infoUrl: string;

    constructor(
        private infoService: InfoService,
        urlService: UrlLocaliService
    ) {
        this.infoUrl = urlService.url("/info");
    }

    ngOnInit(): void {
        this.infoArray = this.infoService.getList().pipe(
            tap((x) => this.toggle(x !== null && x.length > 0)),
            map((info) => {
                const infoArray = new Array<InfoItemModel[]>();
                infoArray.push([]);
                infoArray.push([]);

                info.forEach((i, idx) => {
                    infoArray[idx % 2].push(i);
                });

                return infoArray;
            })
        );
    }

    toggle(mostra: boolean): void {
        this.infoPresenti = mostra;
        this.infoPresentiChange.emit(mostra);
        console.log("mostra faq", mostra);
    }
}
