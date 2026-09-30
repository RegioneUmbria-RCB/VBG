export interface SubprocedimentoModel extends EndoprocedimentoModel {
    idcomune: string;
}

export interface EndoprocedimentoModel {
    id: string;
    procedimentiCollegati: SubprocedimentoModel[];
    principale: boolean;
    nome: string;
    ordine: number;
    regionale: boolean;
}

export interface TipologiaEndoModel {
    id: number;
    nome: string;
    endoprocedimenti: EndoprocedimentoModel[];
    ordine: number;
}

export interface FamigliaEndoprocedimentoModel {
    id: number;
    famiglia: string;
    tipologie: TipologiaEndoModel[];
    ordine: number;
}
