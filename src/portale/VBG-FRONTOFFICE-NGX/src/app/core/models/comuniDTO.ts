export interface IComuniDTO {
    codiceComune: string;
    comune: string;
    siglaProvincia: string;
    cf: string;
    provincia: string;
}

export class ComuniDTO implements IComuniDTO {
    codiceComune: string;
    comune: string;
    siglaProvincia: string;
    cf: string;
    provincia: string;
}
