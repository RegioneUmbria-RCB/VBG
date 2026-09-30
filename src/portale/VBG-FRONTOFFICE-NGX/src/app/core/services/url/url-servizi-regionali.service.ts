import { UrlParams } from "./url-params";
import { Injectable } from "@angular/core";
import { BaseUrlservice } from "./base-url.service";
import { ConfigurationService } from "../configuration/configuration.service";
import {
    ConfigurazioneServiziRegionaliModel,
} from "../configuration";

@Injectable()
export class UrlServiziRegionaliService extends BaseUrlservice {
    // _configurationService: ConfigurationService;
    private _config: ConfigurazioneServiziRegionaliModel;

    constructor(configurationService: ConfigurationService) {
        super(
            () =>
                new UrlParams(
                    configurationService.getConfiguration().serviziRegionali.baseUrl,
                    configurationService.getConfiguration().serviziRegionali.alias,
                    configurationService.getConfiguration().serviziRegionali.software
                )
        );

        this._config = configurationService.getConfiguration().serviziRegionali;
    }

    permetteDownloadModello(): boolean {
        return (
            this._config.urlDownloadModelloDomanda !== null &&
            this._config.urlDownloadModelloDomanda !== undefined &&
            this._config.urlDownloadModelloDomanda !== ""
        );
    }

    urlDownloadModelloDomanda(idIntervento: number): string {
        return this.urlDownloadModello(
            this._config.urlDownloadModelloDomanda,
            this._config.alias,
            this._config.software,
            idIntervento
        );
    }
}
