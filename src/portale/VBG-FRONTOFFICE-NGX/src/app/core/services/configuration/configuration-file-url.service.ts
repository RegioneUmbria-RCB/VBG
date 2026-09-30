import { Injectable } from "@angular/core";
import { AliasSoftwareService } from "../alias-software/alias-software.service";

import { environment } from "../../../../environments/environment";

@Injectable()
export class ConfigurationFileUrlService {
    private basePath = "./assets/configuration/";
    private isProduction = environment.production;

    constructor(private aliasSoftwareService: AliasSoftwareService) { }

    getDefaultFileUrl(): string {
        return this.basePath + "config.default.json";
    }

    getLocalizedFileUrl(): string {
        if (
            !this.aliasSoftwareService.getAlias() ||
            !this.aliasSoftwareService.getSoftware()
        ) {
            return this.getDefaultFileUrl();
        }

        const alias = this.aliasSoftwareService.getAlias();
        const software = this.aliasSoftwareService.getSoftware();
        const devConfig = !this.isProduction ? "dev." : "";

        return (
            this.basePath +
            `config.${alias}.${software}.${devConfig}json?_ts=${Date.now()}`
        );
    }
}
