import { IndirizzoBrevePraticaModel } from "./indirizzo-breve-pratica.model";

export interface TrasparenzaListItemModel {
    id: string;
    intervento: string;
    ruolo: string;
    alias: string;
    data: string;
    sportello: string;
    descrizioneRichiedenteRuoloAzienda: string;
    richiedente: string;
    numero: string;
    numeroProtocollo: string;
    dataProtocollo: string;
    indirizzo: IndirizzoBrevePraticaModel;
}
