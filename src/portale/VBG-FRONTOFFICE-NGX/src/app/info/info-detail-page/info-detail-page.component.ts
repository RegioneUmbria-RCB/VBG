import { from as observableFrom, Subscription } from "rxjs";

import { mergeMap, map } from "rxjs/operators";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";

import { InfoService } from "../../info/info.service";
import { InfoItemModel } from "../../info/info-item.model";
import { ShareSocialComponent } from "../../core/components/share-social/share-social.component";
import { InnerPageLayoutComponent } from "../../shared/inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-info-detail-page",
    templateUrl: "./info-detail-page.component.html",
    standalone: true,
    imports: [InnerPageLayoutComponent, ShareSocialComponent],
})
export class InfoDetailPageComponent implements OnInit, OnDestroy {
    info: InfoItemModel = new InfoItemModel();
    loading = true;

    private sub: Subscription;

    constructor(
        private infoService: InfoService,
        private route: ActivatedRoute,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.sub = this.route.params
            .pipe(
                map((params) => +params["id"]),
                mergeMap((id) => observableFrom(this.infoService.getById(id)))
            )
            .subscribe((info) => {
                if (info === undefined) {
                    void this.router.navigate(["/404"]);
                }

                this.info = info;
                this.loading = false;
            });
    }

    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }
}
