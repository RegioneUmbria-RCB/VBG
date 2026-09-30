import { OnereModel } from "@core/components/griglia-oneri/onere.model";
import { NormativaModel } from "@core/components/griglia-normativa/normativa.model";
import { FaseAttuativaModel } from "@interventi/interventi-detail/intervento-fasi-attuative/fase-attuativa.model";
import { ModuloModel } from "@core/components/griglia-modulistica/modulo.model";
import { InterventoLocaleModel } from "@interventi/interventi-detail/intervento-lista-interventi-locali/intervento-locale.model";
import { FamigliaEndoprocedimentoModel } from "@interventi/interventi-detail/intervento-griglia-endo/endoprocedimento.model";

export interface InterventoDetailModel {
    id: string;
    nome: string;
    informazioni: string[];
    note: string[];
    schedaRegionale: string;
    oneri: OnereModel[];
    normativa: NormativaModel[];
    fasiAttuative: FaseAttuativaModel[];
    modulistica: ModuloModel[];
    percorso: Array<{
        id: string;
        descrizione: string;
    }>;
    interventiLocali: InterventoLocaleModel[];
    procedimentiNecessari: FamigliaEndoprocedimentoModel[];
    procedimentiRicorrenti: FamigliaEndoprocedimentoModel[];
    procedimentiEventuali: FamigliaEndoprocedimentoModel[];
    presentabileOnline: boolean;
}
