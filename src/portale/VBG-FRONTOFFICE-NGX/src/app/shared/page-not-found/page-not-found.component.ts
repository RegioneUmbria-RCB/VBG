import { Component } from "@angular/core";
import { environment } from "../../../environments/environment";


@Component({
    selector: "app-page-not-found",
    templateUrl: "./page-not-found.component.html",
    standalone: true,
    imports: [],
})
export class PageNotFoundComponent {
    isProduction: boolean = environment.production;
}
