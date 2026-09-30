System.register(['./event-names', './menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var EventNames, menu_root_1;
    var DizionarioRicercatestuale;
    return {
        setters:[
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class DizionarioRicercatestuale {
                constructor(panel) {
                    this._dizionarioVoci = Array();
                    this._menuRoot = new menu_root_1.MenuRoot(panel);
                    panel.find('.ricercabile').each((idx, el) => {
                        var jqEl = jQuery(el), titoloSezione = jqEl.find('h1').text(), voci = jqEl.find('.voce-dizionario');
                        if (titoloSezione.indexOf(' / ') > 0) {
                            titoloSezione = titoloSezione.slice(titoloSezione.indexOf(' / ') + 3);
                        }
                        voci.each((idx2, voce) => {
                            var a = jQuery(voce), sezioneAppartenenza = a.parent().parent().parent().find('>h2').text(), testo = sezioneAppartenenza + ' / ' + a.text(), link = a.attr('href');
                            this._dizionarioVoci.push({
                                testo: testo,
                                testoLowerCase: testo.toLocaleLowerCase(),
                                link: link,
                                software: titoloSezione
                            });
                        });
                    });
                    this._menuRoot.on(EventNames.RicercaTesto, (e, args) => {
                        this.ricercaTesto(args.text);
                    });
                }
                ricercaTesto(match) {
                    var results, dictResult = null, lowerText = match.toLocaleLowerCase(), rVal = null;
                    results = this._dizionarioVoci.filter((item) => {
                        return item.testoLowerCase.indexOf(lowerText) > -1;
                    });
                    if (results.length > 0) {
                        dictResult = {};
                        for (var item of results) {
                            if (dictResult[item.software] == null) {
                                dictResult[item.software] = new Array();
                            }
                            dictResult[item.software].push(item);
                        }
                        rVal = { items: [] };
                        for (var software in dictResult) {
                            rVal.items.push({
                                titolo: software,
                                voci: dictResult[software]
                            });
                        }
                        this._menuRoot.trigger(EventNames.TestoCercatoTrovato, rVal);
                        return;
                    }
                    this._menuRoot.trigger(EventNames.TestoCercatoNonTrovato, rVal);
                }
            }
            exports_1("DizionarioRicercatestuale", DizionarioRicercatestuale);
        }
    }
});
//# sourceMappingURL=dizionario-ricerca-testuale.js.map