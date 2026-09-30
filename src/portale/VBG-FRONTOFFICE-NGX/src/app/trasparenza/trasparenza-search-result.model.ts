import { TrasparenzaListItemModel } from "./trasparenza-list-item.model";

export interface TrasparenzaSearchResultModel {
    lista: TrasparenzaListItemModel[];
    // eslint-disable-next-line @typescript-eslint/naming-convention
    numero_record: number;
}
