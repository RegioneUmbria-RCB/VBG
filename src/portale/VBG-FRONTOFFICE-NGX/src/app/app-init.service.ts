import { Injectable } from "@angular/core";
import { AliasSoftwareService, ConfigurationService } from "./core/services";

@Injectable()
export class AppInitService {
    constructor(
        private aliasSoftwareService: AliasSoftwareService,
        private configService: ConfigurationService
    ) { }

    init(): Promise<void> {
        console.log("window.location.origin: " + window.location.origin);
        console.log(`window.location: ${window.location.toString()}`);

        const href = window.location.href;
        const origin =
            window.location.origin +
            document.head.getElementsByTagName("base")[0].getAttribute("href");
        const urlParts = href
            .split("?")[0]
            .replace(origin, "")
            .split("/")
            .filter((x) => x.length > 0);
        // const path = urlParts[0];
        const alias = urlParts[1];
        const software = urlParts[2];

        this.aliasSoftwareService.setAliasSoftware(alias, software);

        return new Promise<void>((resolve) => {
            console.log("Inizializzazione della configurazione");

            this.configService.init().subscribe(() => resolve());
        });
    }
}
