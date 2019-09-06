import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Configuration } from './configuration.model';
import { ConfigurationFileUrlService } from './configuration-file-url.service';
import { tap } from 'rxjs/operators';
import { buildMenuUrls } from './build-menu-urls';
import { BaseUrlservice } from '../url/base-url.service';
import { UrlParams } from '../url/url-params';
import { Observable } from 'rxjs';
import { AreaRiservataUrlChecker } from './area-riservata-url-checker';

@Injectable()
export class ConfigurationService {
    private configuration: Configuration = null;

    constructor(private http: HttpClient, private configurationFileUrlService: ConfigurationFileUrlService) { }

    init(): Observable<Configuration> {
        const url = this.configurationFileUrlService.getLocalizedFileUrl();

        return this.http.get<Configuration>(url).pipe(
            tap(data => {
                console.log('File di configurazione caricato dall\'url', url, 'dati: ', data);

                // tslint:disable-next-line: max-line-length
                data.menu = buildMenuUrls(data.menu, new BaseUrlservice(() => new UrlParams('', data.backend.alias, data.backend.software)));

                console.log('Menu di navigazione inizializzato: ', data.menu);

                // Inizializzazione delle voci dell'area riservata
                // tslint:disable-next-line: max-line-length
                const urlChecker = new AreaRiservataUrlChecker(data.backend.areaRiservata.baseUrl, data.backend.alias, data.backend.software);

                data.backend.areaRiservata = {
                    baseUrl: data.backend.areaRiservata.baseUrl,
                    urlLogin: urlChecker.check(data.backend.areaRiservata.urlLogin),
                    urlRegistrazione: urlChecker.check(data.backend.areaRiservata.urlRegistrazione),
                    urlNuovaDomanda: urlChecker.check(data.backend.areaRiservata.urlNuovaDomanda),
                    urlLeMiePratiche: urlChecker.check(data.backend.areaRiservata.urlLeMiePratiche),
                    urlArchivioPratiche: urlChecker.check(data.backend.areaRiservata.urlArchivioPratiche),
                    urlScadenzario: urlChecker.check(data.backend.areaRiservata.urlScadenzario),
                    urlDownloadModelloDomanda: urlChecker.check(data.backend.areaRiservata.urlDownloadModelloDomanda)
                };

                this.configuration = data;
            })
        );
    }

    getConfiguration(): Configuration {
        return this.configuration;
    }
}
