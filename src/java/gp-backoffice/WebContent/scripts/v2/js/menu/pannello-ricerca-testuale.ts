/// <reference path="../../typings/index.d.ts" />
import * as EventNames from './event-names';
import {MenuRoot} from './menu-root';

export class PannelloRicercaTestuale {

    private _pannelloDescrizione:JQuery;
    private _pannelloRisultati:JQuery;
    private _pannelloNoRisultati:JQuery;
    private _searchResultTemplate: JsRender.Template;
    private _menuRoot:MenuRoot;

    constructor(private _panel:JQuery) {
        this._pannelloDescrizione = this._panel.find('.menu-spiegazione-ricerca');
        this._pannelloRisultati = this._panel.find('.menu-lista-risultati');
        this._pannelloNoRisultati = this._panel.find('.menu-no-risultati');

        this._menuRoot = new MenuRoot(_panel);

        if (this._searchResultTemplate == null) {
            var template = this._panel.data('searchResultTemplate') as JsRender.Template;

            if (template == null) {
                throw "Il pannello di ricerca testuale non ha definito un template";
            }

            this._searchResultTemplate = template;
        }

        this._menuRoot.on(EventNames.TestoCercatoNonTrovato, (e) => {
            this.onRisultatiNonTrovati();
        });

        this._menuRoot.on(EventNames.TestoCercatoTrovato, (e, risultati) => {
            this.onRisultatiTrovati(risultati);
        });

        this._menuRoot.on(EventNames.ResetRicercaTestuale, (e) => {
            this.inizializza();
        });

        this.inizializza();
    }

    private inizializza() {
        this._pannelloNoRisultati.hide();
        this._pannelloRisultati.hide();    
        this._pannelloDescrizione.show();    
    }

    private onRisultatiNonTrovati() {
        this._pannelloDescrizione.hide();
        this._pannelloNoRisultati.show();
        this._pannelloRisultati.hide();
    }

    private onRisultatiTrovati(listaRisultati:any):void {
        let htmlVociTrovate = this._searchResultTemplate.render(listaRisultati);

        this._pannelloDescrizione.hide();

        this._pannelloRisultati.empty();            
        this._pannelloRisultati.append(htmlVociTrovate);
        this._pannelloRisultati.show();

        this._pannelloNoRisultati.hide();
    }
}