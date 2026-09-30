export interface ModuloModel {
    obbligatorio: boolean;
    descrizione: string;
    codiceOggetto: number;
    downloads: { codiceOggetto: number }[];
}
