import { refCount, publishReplay, map } from "rxjs/operators";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TopFaqModel } from "./top-faq.model";
import { UrlServiziLocaliService } from "@core/services/url";
import { ServiceResponse } from "../core/models";
import { ModuloFaqModel } from "./modulo-faq.model";
import { FaqConCategoriaModel } from './faq-con-categoria.model';
import { FaqModel } from './faq.model';
import { RisorseService } from '@core/services';

@Injectable()
export class FaqService {
    fullList$: Observable<ModuloFaqModel[]> = null;

    constructor(
        private urlServiziLocaliService: UrlServiziLocaliService,
        private http: HttpClient,
        private flags: RisorseService
    ) { }

    getTop(): Observable<TopFaqModel[]> {
        const url = this.urlServiziLocaliService.url("faq", ["top"]);

        return this.http
            .get<ServiceResponse<TopFaqModel[]>>(url)
            .pipe(map((data) => data.items));
    }

    getList(): Observable<ModuloFaqModel[]> {

        const usaFAQDiTuttiISoftware = this.flags.getRisorsa('FAQ.leggiDaTuttiISoftware', '1') == '1';

        if (usaFAQDiTuttiISoftware) {
            return this.getListRaggruppataPerModuloSoftware();
        } else {
            return this.getListRaggruppataPerCategoria();
        }
    }

    getListRaggruppataPerModuloSoftware(): Observable<ModuloFaqModel[]> {
        const url = this.urlServiziLocaliService.url("faqpermodulo");

        if (this.fullList$ == null) {
            this.fullList$ = this.http
                .get<ServiceResponse<ModuloFaqModel[]>>(url)
                .pipe(
                    map((x) => x.items),
                    publishReplay(1),
                    refCount()
                );
        }

        return this.fullList$;
    }

    getListRaggruppataPerCategoria(): Observable<ModuloFaqModel[]> {
        const url = this.urlServiziLocaliService.url("faq");

        if (this.fullList$ == null) {
            this.fullList$ = this.http
                .get<ServiceResponse<FaqConCategoriaModel[]>>(url)
                .pipe(
                    map((x) => {

                        const group = new Map<string, FaqModel[]>();

                        for (const faq of x.items) {
                            const categoria = faq.descrizioneCategoria ?? "Altre FAQ";
                            group.set(categoria, group.get(categoria) ?? []);
                            group.get(categoria).push({
                                id: faq.id,
                                attiva: true,
                                descrizione: faq.descrizione,
                                titolo: faq.titolo,
                                visibile: true
                            });
                        }

                        const listaFaq = new Array<ModuloFaqModel>();
                        let i = 0;

                        group.forEach((val, key) => {
                            listaFaq.push({
                                id: `MOD${i++}`,
                                descrizione: key,
                                faq: val
                            });
                        });

                        return listaFaq;
                    }),
                    publishReplay(1),
                    refCount()
                );
        }

        return this.fullList$;
    }
}
