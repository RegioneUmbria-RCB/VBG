export interface ITrasparenzaResult {
    lista: ITrasparenzaResultModel[];
    // eslint-disable-next-line @typescript-eslint/naming-convention
    numero_record: number;
}

export class TrasparenzaResult implements ITrasparenzaResult {
    lista: ITrasparenzaResultModel[];
    // eslint-disable-next-line @typescript-eslint/naming-convention
    numero_record: number;
}

export interface ITrasparenzaResultModel {
    id: string;
    intervento: string;
    ruolo: string;
    alias: string;
    data: string;
    sportello: string;
    descrizioneRichiedenteRuoloAzienda: string;
    richiedente: string;
    numero: string;
}

export class TrasparenzaResultModel implements ITrasparenzaResultModel {
    id: string;
    intervento: string;
    ruolo: string;
    alias: string;
    data: string;
    sportello: string;
    descrizioneRichiedenteRuoloAzienda: string;
    richiedente: string;
    numero: string;
}
