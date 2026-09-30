System.register(['./gruppo-menu', './casella-ricerca-testuale', './preferiti/pannello-preferiti', './voce-menu', './menu-root', './event-names'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var gruppo_menu_1, casella_ricerca_testuale_1, pannello_preferiti_1, voce_menu_1, menu_root_1, EventNames;
    var LeftPanel;
    return {
        setters:[
            function (gruppo_menu_1_1) {
                gruppo_menu_1 = gruppo_menu_1_1;
            },
            function (casella_ricerca_testuale_1_1) {
                casella_ricerca_testuale_1 = casella_ricerca_testuale_1_1;
            },
            function (pannello_preferiti_1_1) {
                pannello_preferiti_1 = pannello_preferiti_1_1;
            },
            function (voce_menu_1_1) {
                voce_menu_1 = voce_menu_1_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            },
            function (EventNames_1) {
                EventNames = EventNames_1;
            }],
        execute: function() {
            class LeftPanel {
                constructor(_panel) {
                    this._panel = _panel;
                    this._funzioniMenu = new Array();
                    this._vociMenu = new Array();
                    this._lastSoftwarePanel = null;
                    this._menuRoot = new menu_root_1.MenuRoot(_panel);
                    this._ricercatestuale = new casella_ricerca_testuale_1.CasellaRicercaTestuale(this._menuRoot.getMenuId(), this._panel.find('.ricerca-testuale'));
                    this._pannelloPreferiti = new pannello_preferiti_1.PannelloPreferiti(this._panel.find('.pannello-preferiti'));
                    this._panel.find('.gruppo-menu').each((idx, el) => {
                        var jqEl = jQuery(el), fn = new gruppo_menu_1.GruppoMenu(jqEl);
                        this._funzioniMenu.push(fn);
                    });
                    this._panel.find('.voce-menu').each((idx, el) => {
                        var jqEl = jQuery(el), selezionaSoftware = new voce_menu_1.VoceMenu(jqEl);
                        this._vociMenu.push(selezionaSoftware);
                    });
                    this._menuRoot.on(EventNames.PannelloAperto, (e, args) => {
                        if (this._lastSoftwarePanel != null) {
                            this._lastSoftwarePanel.removeClass('menu-selected');
                        }
                        this._lastSoftwarePanel = args.element;
                        this._lastSoftwarePanel.addClass('menu-selected');
                    });
                    this._menuRoot.on(EventNames.ApriRicercaTestuale, (e) => {
                        if (this._lastSoftwarePanel != null) {
                            this._lastSoftwarePanel.removeClass('menu-selected');
                        }
                        this._lastSoftwarePanel = null;
                    });
                    this._panel.on('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                }
            }
            exports_1("LeftPanel", LeftPanel);
        }
    }
});
//# sourceMappingURL=left-panel.js.map