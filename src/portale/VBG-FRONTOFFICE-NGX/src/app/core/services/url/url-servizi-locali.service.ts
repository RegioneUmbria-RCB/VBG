import { Injectable } from "@angular/core";
import { BaseUrlservice } from "./base-url.service";
import { ConfigurationService } from "../configuration/configuration.service";
import { UrlParams } from "./url-params";

@Injectable()
export class UrlServiziLocaliService extends BaseUrlservice {
    // _configurationService: ConfigurationService;

    constructor(private _configurationService: ConfigurationService) {
        super(
            () =>
                new UrlParams(
                    _configurationService.getConfiguration().backend.baseUrl,
                    _configurationService.getConfiguration().backend.alias,
                    _configurationService.getConfiguration().backend.software
                )
        );
    }

    urlDownloadModelloDomanda(idIntervento: number): string {
        return this.urlDownloadModello(
            this._configurationService.getConfiguration().backend.areaRiservata
                .urlDownloadModelloDomanda,
            this._configurationService.getConfiguration().backend.alias,
            this._configurationService.getConfiguration().backend.software,
            idIntervento
        );
    }

    permetteDownloadModello(): boolean {
        const urlDownload =
            this._configurationService.getConfiguration().backend.areaRiservata
                ?.urlDownloadModelloDomanda;

        return (
            urlDownload !== null &&
            urlDownload !== undefined &&
            urlDownload !== ""
        );
    }
}
