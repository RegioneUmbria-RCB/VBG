import { Injectable } from '@angular/core';
import { DatiComuneModel } from './dati-comune.model';
import { ConfigurationService } from '../core/services/configuration';
import { OrariEContattiService } from './orari-e-contatti.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class DatiComuneService {

    constructor(private configurationService: ConfigurationService, private orariEContattiService: OrariEContattiService) { }

    get(): Observable<DatiComuneModel> {

        const config = this.configurationService.getConfiguration();

        return this.orariEContattiService.get().pipe(
            map(orariEContatti => {
                return {
                    denominazioneRegione: config.globals.denominazioneRegione,
                    linkRegione: config.globals.linkRegione,
                    denominazioneComune: config.globals.denominazioneComune || orariEContatti.denominazioneComune,
                    themeLocation: config.globals.themeLocation,
                    // 'Comune di Gualdo Tadino',
                    // tslint:disable-next-line:max-line-length
                    denominazioneSportello: config.globals.denominazioneSportello || orariEContatti.denominazioneSportello, // 'Sportello Unico per le Attività Produttive',
                    canaliSocial: config.globals.canaliSocial,
                    footerLinks: config.globals.footerLinks,
                    linkComune: config.globals.linkComune,

                    menu: config.menu.map(item => {
                        return {
                            titolo: item.titolo,
                            link: item.link
                        };
                    }),

                    scrivaniaVirtuale:
                    {
                        titolo: 'Scrivania virtuale',
                        link: config.backend.areaRiservata.urlLogin
                    },
                    passaALinks: config.globals.passaALinks,
                    urlQuestionario: config.globals.urlQuestionario,
                    contatti: {
                        indirizzo: [
                            config.globals.denominazioneComune || orariEContatti.denominazioneComune,
                            orariEContatti.indirizzoSportello
                        ],

                        pec: orariEContatti.supporto.pec,
                        email: orariEContatti.supporto.email,
                        telefono: orariEContatti.supporto.telefono,
                        fax: orariEContatti.supporto.fax
                    }
                };
            })
        );
    }
}
