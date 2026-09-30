System.register(['./event-names', './menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var EventNames, menu_root_1;
    var PannelloRicercaTestuale;
    return {
        setters:[
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class PannelloRicercaTestuale {
                constructor(_panel) {
                    this._panel = _panel;
                    this._pannelloDescrizione = this._panel.find('.menu-spiegazione-ricerca');
                    this._pannelloRisultati = this._panel.find('.menu-lista-risultati');
                    this._pannelloNoRisultati = this._panel.find('.menu-no-risultati');
                    this._menuRoot = new menu_root_1.MenuRoot(_panel);
                    if (this._searchResultTemplate == null) {
                        var template = this._panel.data('searchResultTemplate');
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
                inizializza() {
                    this._pannelloNoRisultati.hide();
                    this._pannelloRisultati.hide();
                    this._pannelloDescrizione.show();
                }
                onRisultatiNonTrovati() {
                    this._pannelloDescrizione.hide();
                    this._pannelloNoRisultati.show();
                    this._pannelloRisultati.hide();
                }
                onRisultatiTrovati(listaRisultati) {
                    let htmlVociTrovate = this._searchResultTemplate.render(listaRisultati);
                    this._pannelloDescrizione.hide();
                    this._pannelloRisultati.empty();
                    this._pannelloRisultati.append(htmlVociTrovate);
                    this._pannelloRisultati.show();
                    this._pannelloNoRisultati.hide();
                }
            }
            exports_1("PannelloRicercaTestuale", PannelloRicercaTestuale);
        }
    }
});
//# sourceMappingURL=pannello-ricerca-testuale.js.map