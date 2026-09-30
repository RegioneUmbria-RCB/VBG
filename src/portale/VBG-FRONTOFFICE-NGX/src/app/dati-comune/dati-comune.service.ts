import { Injectable } from "@angular/core";
import { DatiComuneModel } from "./dati-comune.model";
import { ConfigurationService } from "../core/services/configuration";
import { OrariEContattiService } from "./orari-e-contatti.service";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { RisorseService } from "../core/services/risorse/risorse.service";

@Injectable()
export class DatiComuneService {
    constructor(
        private configurationService: ConfigurationService,
        private orariEContattiService: OrariEContattiService,
        private risorseService: RisorseService
    ) {}

    get(): Observable<DatiComuneModel> {
        const config = this.configurationService.getConfiguration();

        return this.orariEContattiService.get().pipe(
            map((orariEContatti) => ({
                denominazioneRegione: config.globals.denominazioneRegione,
                linkRegione: config.globals.linkRegione,
                denominazioneComune:
                    config.globals.denominazioneComune ||
                    orariEContatti.denominazioneComune,
                themeLocation: config.globals.themeLocation,
                // 'Comune di Gualdo Tadino',
                // eslint-disable-next-line max-len
                denominazioneSportello:
                    config.globals.denominazioneSportello ||
                    orariEContatti.denominazioneSportello, // 'Sportello Unico per le Attività Produttive',
                denominazioneSportelloRecapitieContatti:
                    orariEContatti.denominazioneSportello,
                canaliSocial: config.globals.canaliSocial,
                footerLinks: config.globals.footerLinks,
                linkComune: config.globals.linkComune,

                menu: config.menu.map((item) => ({
                    titolo: item.titolo,
                    link: item.link,
                })),

                scrivaniaVirtuale: {
                    titolo: this.risorseService.getRisorsa(
                        "scrivaniaVirtuale.titolo",
                        "Scrivania virtuale"
                    ),
                    link: config.backend.areaRiservata.urlLogin,
                },
                serviceDesk: {
                    titolo: this.risorseService.getRisorsa(
                        "serviceDesk.titolo",
                        null
                    ),
                    link: this.risorseService.getRisorsa(
                        "serviceDesk.link",
                        null
                    ),
                },
                passaALinks: config.globals.passaALinks,
                urlQuestionario: config.globals.urlQuestionario,
                contatti: {
                    indirizzo: [
                        // eslint-disable-next-line max-len
                        orariEContatti.denominazioneSportello ||
                            config.globals.denominazioneComune ||
                            orariEContatti.denominazioneComune,
                        orariEContatti.indirizzoSportello,
                    ],

                    pec: orariEContatti.supporto.pec,
                    email: orariEContatti.supporto.email,
                    telefono: orariEContatti.supporto.telefono,
                    fax: orariEContatti.supporto.fax,
                },
            }))
        );
    }
}
