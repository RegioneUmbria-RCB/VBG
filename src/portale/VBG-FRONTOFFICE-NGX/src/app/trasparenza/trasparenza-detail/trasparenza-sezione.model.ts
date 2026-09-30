import { VisuraAutorizzazioneModel } from '../visura-autorizzazione.model';
import { VisuraDatoGeneraleModel } from '../visura-dato-generale.model';
import { VisuraLocalizzazioneModel } from '../visura-localizzazione.model';

export interface TrasparenzaSezioneModel {
    titolo: string,
    corpo: (string | VisuraDatoGeneraleModel | Array<VisuraLocalizzazioneModel> | Array<VisuraAutorizzazioneModel>)
}
