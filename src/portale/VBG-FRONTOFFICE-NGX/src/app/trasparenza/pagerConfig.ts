export interface IPagerConfig {
    itemsPerPageRicercaDefault: number;
    maxSizePager: number;
    page: number;
    firstResultDefault: number;
    maxResultsDefault: number;
}

export class PagerConfig implements IPagerConfig {
    itemsPerPageRicercaDefault: number;
    maxSizePager: number;
    page: number;
    firstResultDefault: number;
    maxResultsDefault: number;
}
