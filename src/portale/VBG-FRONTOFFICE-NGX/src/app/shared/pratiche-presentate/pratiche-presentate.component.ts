import { Component, OnInit } from "@angular/core";
import { ConfigurationService } from "../../core";
import { InnerPageLayoutComponent } from "../inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-pratiche-presentate",
    templateUrl: "./pratiche-presentate.component.html",
    standalone: true,
    imports: [InnerPageLayoutComponent],
})
export class PratichePresentateComponent implements OnInit {
    constructor(private arConfig: ConfigurationService) { }

    ngOnInit(): void {
        console.log(
            this.arConfig.getConfiguration().backend.areaRiservata
                .urlLeMiePratiche
        );
        document.location.replace(
            this.arConfig.getConfiguration().backend.areaRiservata
                .urlLeMiePratiche
        );
    }
}
