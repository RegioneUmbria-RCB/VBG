import { Component, OnInit } from "@angular/core";
import { InfoService } from "../../info/info.service";
import { ButtonGridItem } from "../../core";
import { Observable } from "rxjs";
import { map, finalize } from "rxjs/operators";
import { AsyncPipe } from "@angular/common";
import { ButtonGridComponent } from "../../core/components/button-grid/button-grid.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-info-list-page",
    templateUrl: "./info-list-page.component.html",
    standalone: true,
    imports: [
        InnerPageLayoutComponent,
        ButtonGridComponent,
        AsyncPipe,
    ],
})
export class InfoListPageComponent implements OnInit {
    infoList: Observable<ButtonGridItem[]>;
    loading = true;

    constructor(private infoService: InfoService) { }

    ngOnInit(): void {
        this.loading = true;

        this.infoList = this.infoService.getList().pipe(
            map((info) =>
                info.map((i) => {
                    const item = new ButtonGridItem();

                    item.id = i.id.toString();
                    item.titolo = i.titolo;

                    return item;
                })
            ),
            // setTimeout risolve l'errore "ExpressionChangedAfterItHasBeenCheckedError"
            finalize(() => setTimeout(() => (this.loading = false)))
        );
    }
}
