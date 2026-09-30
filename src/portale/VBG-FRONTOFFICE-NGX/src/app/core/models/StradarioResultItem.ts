/* eslint-disable @typescript-eslint/naming-convention */
export interface IStradarioResultItem {
    Codice: string;
    Descrizione: string;
    CodViario: string;
}

export class StradarioResultItem implements IStradarioResultItem {
    Codice: string;
    Descrizione: string;
    CodViario: string;
}
