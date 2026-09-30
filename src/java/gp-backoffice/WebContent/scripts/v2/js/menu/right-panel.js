System.register(['./detail-panel', './dizionario-ricerca-testuale', './pannello-ricerca-testuale', './menu-root', './event-names'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var detail_panel_1, dizionario_ricerca_testuale_1, pannello_ricerca_testuale_1, menu_root_1, EventNames;
    var RightPanel;
    return {
        setters:[
            function (detail_panel_1_1) {
                detail_panel_1 = detail_panel_1_1;
            },
            function (dizionario_ricerca_testuale_1_1) {
                dizionario_ricerca_testuale_1 = dizionario_ricerca_testuale_1_1;
            },
            function (pannello_ricerca_testuale_1_1) {
                pannello_ricerca_testuale_1 = pannello_ricerca_testuale_1_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            },
            function (EventNames_1) {
                EventNames = EventNames_1;
            }],
        execute: function() {
            class RightPanel {
                constructor(_panel) {
                    this._panel = _panel;
                    this._detailPanels = {};
                    this._keysList = new Array();
                    this._cambiamentoPannelloBloccato = false;
                    this._menuRoot = new menu_root_1.MenuRoot(this._panel);
                    this._panel.find('.pannello-menu').each((idx, el) => {
                        var jqEl = jQuery(el), pnlId = jqEl.data('panelId');
                        this._keysList.push(pnlId);
                        this._detailPanels[pnlId] = new detail_panel_1.DetailPanel(jqEl);
                    });
                    this._ricercaTestuale = new dizionario_ricerca_testuale_1.DizionarioRicercatestuale(this._panel);
                    this._pannelloricercatestuale = new pannello_ricerca_testuale_1.PannelloRicercaTestuale(_panel.find('.MENU_RICERCA'));
                    this.showPanel("MENU_RICERCA");
                    this._menuRoot.on(EventNames.ApriPannello, (e, args) => {
                        if (this.showPanel(args.panelId)) {
                            this._menuRoot.trigger(EventNames.PannelloAperto, [args]);
                        }
                    });
                    this._menuRoot.on(EventNames.BloccaCambiamentoPannello, (e) => {
                        this._cambiamentoPannelloBloccato = true;
                    });
                    this._menuRoot.on(EventNames.SbloccaCambiamentoPannello, (e) => {
                        this._cambiamentoPannelloBloccato = false;
                    });
                    this._menuRoot.on(EventNames.ApriRicercaTestuale, (e) => {
                        this.showPanel("MENU_RICERCA");
                    });
                }
                hideAllPanels() {
                    for (var key of this._keysList) {
                        this._detailPanels[key].hide();
                    }
                }
                showPanel(panelId) {
                    if (this._cambiamentoPannelloBloccato) {
                        return false;
                    }
                    this.hideAllPanels();
                    this._detailPanels[panelId].scrollTop();
                    this._detailPanels[panelId].show();
                    return true;
                }
            }
            exports_1("RightPanel", RightPanel);
        }
    }
});
//# sourceMappingURL=right-panel.js.map