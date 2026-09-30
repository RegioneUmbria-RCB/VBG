import { OnereModel } from "@core/components/griglia-oneri/onere.model";
import { ModuloModel } from "@core/components/griglia-modulistica/modulo.model";
import { NormativaModel } from "@core/components/griglia-normativa/normativa.model";

export interface ProcedimentoDetailModel {
    nome: string;
    amministrazione: string;
    schedaRegionale: string;
    tipologia: string;
    natura: string;
    descrizione: string;
    requisiti: string;
    adempimenti: string;
    oneri: OnereModel[];
    modulistica: ModuloModel[];
    normativa: NormativaModel[];
    dataAggiornamento: string;
}
