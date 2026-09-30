export class StatoRicercaIntervento {
    private _ricercaInCorso = false;
    private _mostraRisultati = false;
    private _numeroRisultatiTrovati = 0;

    public get ricercaInCorso(): boolean {
        return this._ricercaInCorso;
    }
    public get mostraRisultati(): boolean {
        return this._mostraRisultati;
    }
    public get numeroRisultatiTrovati(): number {
        return this._numeroRisultatiTrovati;
    }

    public reset(): void {
        this._ricercaInCorso = false;
        this._mostraRisultati = false;
        this._numeroRisultatiTrovati = 0;
    }

    public ricercaIniziata(): void {
        this._ricercaInCorso = true;
        this._mostraRisultati = false;
        this._numeroRisultatiTrovati = 0;
    }

    public ricercaCompletata(numeroRisultati: number): void {
        this._ricercaInCorso = false;
        this._mostraRisultati = true;
        this._numeroRisultatiTrovati = numeroRisultati;
    }
}
