import { Component, Input } from "@angular/core";
import { LoaderComponent } from "../../core/components/loader/loader.component";

import { PageLayoutComponent } from "../page-layout/page-layout.component";

@Component({
    selector: "app-inner-page-layout",
    templateUrl: "./inner-page-layout.component.html",
    standalone: true,
    imports: [
    PageLayoutComponent,
    LoaderComponent
],
})
export class InnerPageLayoutComponent {
    @Input() isLoading = false;
}
