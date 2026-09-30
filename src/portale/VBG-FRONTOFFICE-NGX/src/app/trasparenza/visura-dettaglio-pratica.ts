import { VisuraAutorizzazioneModel } from './visura-autorizzazione.model';
import { VisuraLocalizzazioneModel } from './visura-localizzazione.model';

export interface VisuraDettaglioPratica {
    localizzazioni: Array<VisuraLocalizzazioneModel>,
    autorizzazioni: Array<VisuraAutorizzazioneModel>,
    datiGenerali: { [key: string]: string }
}
