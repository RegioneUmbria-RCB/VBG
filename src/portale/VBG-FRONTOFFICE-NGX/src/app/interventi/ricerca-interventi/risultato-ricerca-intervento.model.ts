import { InterventoCercato } from "@interventi/services";

export class RisultatoRicercaIntervento {
    private _risultatiTotali = 0;

    constructor(
        public locali: InterventoCercato[],
        public regionali: InterventoCercato[]
    ) {
        console.log("locali", locali);
        console.log("regionali", regionali);

        this._risultatiTotali = this.conta(locali) + this.conta(regionali);
    }

    public get almenoUnMatch(): boolean {
        return this._risultatiTotali > 0;
    }

    public get conteggioTotale(): number {
        return this._risultatiTotali;
    }

    private conta(arr: InterventoCercato[]): number {
        return arr == null ? 0 : arr.length;
    }
}
