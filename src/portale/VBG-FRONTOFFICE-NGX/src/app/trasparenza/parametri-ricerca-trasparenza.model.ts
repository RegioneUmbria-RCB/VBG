import { HttpParams } from "@angular/common/http";
import { HttpParamsWrapper } from "./http-params-wrapper";

export class ParametriRicercaTrasparenzaModel {
    numeroPratica: string;
    numeroProtocollo: string;
    annoProtocollo: string;
    dataDal: string;
    dataAl: string;
    civico: string;
    indirizzo: string;
    codiceStradario: string;
    stato: string;
    comune: string;
    referente: string;
    tipoCatasto: string;
    foglio: string;
    particella: string;
    sub: string;

    public intervalloDateValido(): boolean {
        const allaData = this.parseHttpDate(this.dataAl);
        const dallaData = this.parseHttpDate(this.dataDal);

        if (dallaData === null || allaData === null) {
            return true;
        }

        return parseInt(allaData, 10) >= parseInt(dallaData, 10);
    }

    public toHttpParams(pagina: number, recordsPerPagina: number): HttpParams {
        const allaData = this.parseHttpDate(this.dataAl);
        const dallaData = this.parseHttpDate(this.dataDal);

        console.log("dallaData=", dallaData, ", allaData=", allaData);

        return new HttpParamsWrapper()
            .addIfNotNull("dallaData", dallaData)
            .addIfNotNull("allaData", allaData)
            .addIfNotNull("numeroIstanza", this.numeroPratica)
            .addIfNotNull("numeroProtocollo", this.numeroProtocollo)
            .addIfNotNull("indirizzo", this.indirizzo)
            .addIfNotNull("codiceStradario", this.codiceStradario)
            .addIfNotNull("civico", this.civico)
            .addIfNotNull("comune", this.comune)
            .addIfNotNull("referente", this.referente)
            .addIfNotNull("stato", this.stato)
            .addIfNotNull("annoProtocollo", this.annoProtocollo)
            .addIfNotNull("firstResult", (pagina * recordsPerPagina).toString())
            .addIfNotNull("maxResults", recordsPerPagina.toString())            
            .addIfNotNull("tipoCatasto", this.tipoCatasto)
            .addIfNotNull("foglio", this.foglio)
            .addIfNotNull("particella", this.particella)
            .addIfNotNull("sub", this.sub)
            .getHttpParams();
    }

    private parseHttpDate(dataIn: string) {
        if (!dataIn || dataIn.length === 0) {
            return null;
        }

        if (!/^\d{4}-\d{2}-\d{2}$/.exec(dataIn)) {
            return null;
        }

        const arr = dataIn.split("-");
        return arr[0] + arr[1] + arr[2];
    }
}
