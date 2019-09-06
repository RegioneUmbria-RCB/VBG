import { CanaleSocialModel } from '../core/services/configuration';
import { LinkModel } from '../core/services/configuration';
import { PassaALinksModel } from '../core/services/configuration';

export interface DatiComuneModel {
    denominazioneRegione: string;
    linkRegione: string;
    denominazioneComune: string;
    denominazioneSportello: string;
    themeLocation: string;
    linkComune: string;

    canaliSocial: CanaleSocialModel[];

    scrivaniaVirtuale: LinkModel;

    menu: LinkModel[];

    urlQuestionario: string;

    footerLinks: LinkModel[];

    passaALinks: PassaALinksModel;

    contatti: {
        indirizzo: string[];
        pec: string;
        email: string;
        telefono: string;
        fax: string;
    };
}
