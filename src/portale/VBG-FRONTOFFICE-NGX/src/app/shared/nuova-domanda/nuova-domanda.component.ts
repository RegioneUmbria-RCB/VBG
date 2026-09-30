import { Component, OnInit } from "@angular/core";
import { ConfigurationService } from "../../core";
import { InnerPageLayoutComponent } from "../inner-page-layout/inner-page-layout.component";

@Component({
    selector: "app-nuova-domanda",
    templateUrl: "./nuova-domanda.component.html",
    standalone: true,
    imports: [InnerPageLayoutComponent],
})
export class NuovaDomandaComponent implements OnInit {
    constructor(private arConfig: ConfigurationService) { }

    ngOnInit(): void {
        console.log(
            this.arConfig.getConfiguration().backend.areaRiservata
                .urlNuovaDomanda
        );
        document.location.replace(
            this.arConfig.getConfiguration().backend.areaRiservata
                .urlNuovaDomanda
        );
    }
}
