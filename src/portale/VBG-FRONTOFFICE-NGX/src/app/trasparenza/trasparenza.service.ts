import { Observable, of, zip } from "rxjs";
import { map, shareReplay, tap } from "rxjs/operators";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ConfigurationService } from "../core/services/configuration";
import { ParametriRicercaTrasparenzaModel } from "./parametri-ricerca-trasparenza.model";
import { StradarioReponse } from "../core/models/StradarioResponse";
import { ComuniDTO } from "../core/models/comuniDTO";
import { StatoIstanzaDTO } from "@core/models/statoIstanzaDTO";
import { TrasparenzaSearchResultModel } from "./trasparenza-search-result.model";
import { VisuraDatoGeneraleModel } from './visura-dato-generale.model';
import { VisuraLocalizzazioneModel } from './visura-localizzazione.model';
import { VisuraDettaglioPratica } from './visura-dettaglio-pratica';
import { VisuraAutorizzazioneModel } from './visura-autorizzazione.model';

@Injectable()
export class TrasparenzaService {
    // eslint-disable-next-line max-len
    constructor(
        private configurationService: ConfigurationService,
        private client: HttpClient
    ) { }

    getList(
        filtri: ParametriRicercaTrasparenzaModel,
        pagina: number,
        recordsPerPagina: number
    ): Observable<TrasparenzaSearchResultModel> {
        const config = this.configurationService.getConfiguration().backend;
        const parts = [
            "istanze",
            "alias",
            config.alias,
            "software",
            config.software,
            "istanze",
        ];
        const url = config.urlVisura + "/" + parts.join("/");

        return this.client.get<TrasparenzaSearchResultModel>(url, {
            params: filtri.toHttpParams(pagina, recordsPerPagina),
        });
    }

    getStradario(
        codiceComune: string,
        indirizzo: string
    ): Observable<StradarioReponse> {
        const config = this.configurationService.getConfiguration().backend;
        const data = {
            idComune: config.alias,
            codiceComune,
            comuneLocalizzazione: "",
            match: indirizzo,
        };

        if (!indirizzo || indirizzo.length < 2) {
            return of({
                d: {
                    // eslint-disable-next-line @typescript-eslint/naming-convention
                    Items: [],
                    // eslint-disable-next-line @typescript-eslint/naming-convention
                    ItemCount: 0,
                },
            });
        }

        return this.client.post<StradarioReponse>(
            config.areaRiservata.urlAutocompleteStradario,
            data
        );
    }

    getComuniAssociati(): Observable<ComuniDTO[]> {
        const config = this.configurationService.getConfiguration().backend;
        return this.client
            .get<ComuniDTO[]>(config.urlComuniAssociati)
            .pipe(shareReplay(1));
    }

    getStatiIstanza(): Observable<StatoIstanzaDTO[]> {
        const config = this.configurationService.getConfiguration().backend;
        console.log(config.urlStatiIstanza);
        return this.client
            .get<StatoIstanzaDTO[]>(config.urlStatiIstanza)
            .pipe(shareReplay(1));
    }



    getById(id: string): Observable<VisuraDettaglioPratica> {
        const config = this.configurationService.getConfiguration().backend;

        const url = `${config.urlVisura}/istanze/alias/${config.alias}/software/${config.software}/istanze/${id.toString()}`;

        const datiGenerali$ = this.client.get<VisuraDatoGeneraleModel[]>(url + "/dati-generali");
        const localizzazioni$ = this.client.get<VisuraLocalizzazioneModel[]>(url + "/localizzazioni");
        const autorizzazioni$ = this.client.get<VisuraAutorizzazioneModel[]>(url + "/autorizzazioni");

        return zip(datiGenerali$, localizzazioni$, autorizzazioni$).pipe(
            map(([datiGenerali, localizzazioni, autorizzazioni]) => {
                const datiPratica: VisuraDettaglioPratica = {
                    localizzazioni: localizzazioni,
                    autorizzazioni: autorizzazioni,
                    datiGenerali: {}
                };

                for (const el of datiGenerali) {
                    datiPratica.datiGenerali[el.nome] = el.valore;
                }

                return datiPratica;
            }),
            tap((data) => console.log(data))
        );

        /*
        const datiGenerali = this.client.get<any>(url + '/dati-generali');
        const localizzazioni = this.client.get<any>(url + '/localizzazioni');
        const autorizzazioni = this.client.get<any>(url + '/autorizzazioni');

        return observableForkJoin([datiGenerali, localizzazioni, autorizzazioni]).pipe(
            map(responses => {
                const cls = this.propToClass(responses[0]);

                cls.localizzazioni = responses[1];
                cls.autorizzazioni = responses[2];

                return cls;
            }));
        */
    }

    /*
    private propToClass(propArray: any[]) {
        const obj = {
            localizzazioni: [],
            autorizzazioni: [],
        };

        for (const item of propArray) {
            obj[item.nome] = item.valore;
        }

        return obj;
    }
    */
}
