/// <reference path="../../typings/index.d.ts" />
import {SelezionaSoftware} from './seleziona-software';
import {AggiungiPreferiti} from './preferiti/aggiungi-preferiti';
import {RimuoviPreferiti} from './preferiti/rimuovi-preferiti';

export class VoceMenu {

    _selezionaSoftware:SelezionaSoftware;
    _aggiungiPreferiti:AggiungiPreferiti = null;
    _rimuoviPreferiti:RimuoviPreferiti = null;

    constructor(private _panel:JQuery) {
        this._selezionaSoftware = new SelezionaSoftware(this._panel.find('.seleziona-software').first());

        let aggiungiPreferiti = this._panel.find('.aggiungi-preferiti');

        if (aggiungiPreferiti.length > 0) {
            this._aggiungiPreferiti = new AggiungiPreferiti(aggiungiPreferiti.first());
        }

        let rimuoviPreferiti = this._panel.find('.rimuovi-preferiti');

        if (rimuoviPreferiti.length > 0) {
            this._rimuoviPreferiti = new RimuoviPreferiti(rimuoviPreferiti.first());
        }
    }

}