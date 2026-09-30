import { Component, OnInit } from "@angular/core";
import { Router, NavigationEnd } from "@angular/router";
import { PageFooterComponent } from "../page-footer/page-footer.component";
import { PageHeaderComponent } from "../page-header/page-header.component";

@Component({
    selector: "app-page-layout",
    templateUrl: "./page-layout.component.html",
    standalone: true,
    imports: [PageHeaderComponent, PageFooterComponent],
})
export class PageLayoutComponent implements OnInit {
    constructor(private router: Router) { }

    ngOnInit(): void {
        this.router.events.subscribe((evt) => {
            if (!(evt instanceof NavigationEnd)) {
                return;
            }
            document.body.scrollTop = 0;
        });
    }
}
