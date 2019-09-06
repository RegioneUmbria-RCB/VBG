import { HttpParams } from '@angular/common/http';

export class ParametriRicercaTrasparenzaModel {
    numeroPratica: string;
    numeroProtocollo: string;
    dalMese: string;
    dalAnno: string;
    alMese: string;
    alAnno: string;
    indirizzo: string;
    stato: string;


    toHttpParams(): HttpParams {
        return new HttpParams()
            .set('dalAnno', this.dalAnno)
            .set('dallaData', this.dalAnno + this.dalMese.padStart(2, '0') + '01')
            .set('allaData', this.alAnno + this.alMese.padStart(2, '0') + '31')
            .set('dalMese', this.dalMese)
            .set('alAnno', this.alAnno)
            .set('alMese', this.alMese)
            .set('numeroIstanza', this.numeroPratica)
            .set('numeroProtocollo', this.numeroProtocollo)
            .set('indirizzo', this.indirizzo)
            .set('stato', this.stato);
    }
}
