System.register(['./seleziona-software', './preferiti/aggiungi-preferiti', './preferiti/rimuovi-preferiti'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var seleziona_software_1, aggiungi_preferiti_1, rimuovi_preferiti_1;
    var VoceMenu;
    return {
        setters:[
            function (seleziona_software_1_1) {
                seleziona_software_1 = seleziona_software_1_1;
            },
            function (aggiungi_preferiti_1_1) {
                aggiungi_preferiti_1 = aggiungi_preferiti_1_1;
            },
            function (rimuovi_preferiti_1_1) {
                rimuovi_preferiti_1 = rimuovi_preferiti_1_1;
            }],
        execute: function() {
            class VoceMenu {
                constructor(_panel) {
                    this._panel = _panel;
                    this._aggiungiPreferiti = null;
                    this._rimuoviPreferiti = null;
                    this._selezionaSoftware = new seleziona_software_1.SelezionaSoftware(this._panel.find('.seleziona-software').first());
                    let aggiungiPreferiti = this._panel.find('.aggiungi-preferiti');
                    if (aggiungiPreferiti.length > 0) {
                        this._aggiungiPreferiti = new aggiungi_preferiti_1.AggiungiPreferiti(aggiungiPreferiti.first());
                    }
                    let rimuoviPreferiti = this._panel.find('.rimuovi-preferiti');
                    if (rimuoviPreferiti.length > 0) {
                        this._rimuoviPreferiti = new rimuovi_preferiti_1.RimuoviPreferiti(rimuoviPreferiti.first());
                    }
                }
            }
            exports_1("VoceMenu", VoceMenu);
        }
    }
});
//# sourceMappingURL=voce-menu.js.map