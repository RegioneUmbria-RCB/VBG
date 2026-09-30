import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import {
    Configuration,
    DEFAULT_HOME_CONFIGURATION,
    DEFAULT_CONFIGURAZIONE_AREA_RISERVATA,
    DEFAULT_CONFIGURAZIONE_BACKEND,
    DEFAULT_GLOBALS_CONFIGURATION,
} from "./configuration.model";
import { ConfigurationFileUrlService } from "./configuration-file-url.service";
import { tap } from "rxjs/operators";
import { buildMenuUrls } from "./build-menu-urls";
import { BaseUrlservice } from "../url/base-url.service";
import { UrlParams } from "../url/url-params";
import { Observable } from "rxjs";
import { AreaRiservataUrlChecker } from "./area-riservata-url-checker";
import { BackendUrlChecker } from "./backend-url-checker";

@Injectable()
export class ConfigurationService {
    private configuration: Configuration = null;

    constructor(
        private http: HttpClient,
        private configurationFileUrlService: ConfigurationFileUrlService
    ) {}

    init(): Observable<Configuration> {
        const url = this.configurationFileUrlService.getLocalizedFileUrl();

        return this.http.get<Configuration>(url).pipe(
            tap((data) => {
                // eslint-disable-next-line max-len
                data.menu = buildMenuUrls(
                    data.menu,
                    new BaseUrlservice(
                        () =>
                            new UrlParams(
                                "",
                                data.backend.alias,
                                data.backend.software
                            )
                    )
                );

                data.globals = {
                    ...DEFAULT_GLOBALS_CONFIGURATION,
                    ...data.globals,
                };
                // imposto le proprietà di configurazione per la home page che non
                // sono state lette dal file json
                data.homeConfiguration = {
                    ...DEFAULT_HOME_CONFIGURATION,
                    ...data.homeConfiguration,
                };

                console.log("Menu di navigazione inizializzato: ", data.menu);

                // Inizializzazione delle voci dell'area riservata
                // eslint-disable-next-line max-len
                const arUrlChecker = new AreaRiservataUrlChecker(
                    data.backend.areaRiservata.baseUrl,
                    data.backend.alias,
                    data.backend.software
                );
                const boUrlChecker = new BackendUrlChecker(
                    data.backend.baseUrl,
                    data.backend.alias,
                    data.backend.software
                );

                data.backend = boUrlChecker.verificaUrlBackend({
                    ...DEFAULT_CONFIGURAZIONE_BACKEND,
                    ...data.backend,
                });

                data.backend.areaRiservata = {
                    baseUrl: data.backend.areaRiservata.baseUrl,
                    ...arUrlChecker.verificaUrlAreaRiservata({
                        ...DEFAULT_CONFIGURAZIONE_AREA_RISERVATA,
                        ...data.backend.areaRiservata,
                    }),
                };

                this.configuration = data;
            })
        );
    }

    getConfiguration(): Configuration {
        return this.configuration;
    }
}
