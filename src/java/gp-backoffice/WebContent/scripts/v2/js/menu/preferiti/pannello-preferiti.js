System.register(['./../event-names', './rimuovi-preferiti', './../voce-menu', './../menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var EventNames, rimuovi_preferiti_1, voce_menu_1, menu_root_1;
    var PannelloPreferiti;
    return {
        setters:[
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (rimuovi_preferiti_1_1) {
                rimuovi_preferiti_1 = rimuovi_preferiti_1_1;
            },
            function (voce_menu_1_1) {
                voce_menu_1 = voce_menu_1_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class PannelloPreferiti {
                constructor(_panel) {
                    this._panel = _panel;
                    this._nomiMenu = new Array();
                    this._ul = this._panel.find('.scelta-software');
                    this._menuName = this._panel.data('menuName');
                    this._pannelloNoPreferiti = _panel.find('.no-preferiti');
                    this._originalHeight = this._pannelloNoPreferiti.css('height');
                    this._menuRoot = new menu_root_1.MenuRoot(_panel);
                    this._pannelloNoPreferiti.hide();
                    this._menuRoot.on(EventNames.RimuoviDaPreferiti, (e, softwareId, element) => {
                        this.updateStatus();
                    });
                    this._menuRoot.on(EventNames.AggiungiAPreferiti, (e, softwareId, softwareName, menuName) => {
                        if (this._menuName != menuName) {
                            return;
                        }
                        var esiste = this._nomiMenu.filter((x) => {
                            return x.localeCompare(softwareName) === 0;
                        }).length > 0;
                        if (esiste) {
                            return;
                        }
                        let template = '<li class="voce-menu">' +
                            '<i class="glyphicon glyphicon-star rimuovi-preferiti"></i>' +
                            '<a href="#" class="seleziona-software" data-panel-id="' + softwareId + '">template</a>' +
                            '</li>';
                        let compiled = jQuery(template);
                        this._ul.append(compiled);
                        let newEl = new rimuovi_preferiti_1.RimuoviPreferiti(compiled.find('i'));
                        newEl.setSoftwareId(softwareId);
                        newEl.setText(softwareName);
                        var voceMenu = new voce_menu_1.VoceMenu(compiled);
                        this.updateStatus();
                    });
                    // fix per il link expand-menu che viene aggiunto da bootstrap
                    this.fixExpandMenu();
                    this.updateStatus();
                }
                fixExpandMenu() {
                    var expand = this._panel.find('li>a.espandi-menu');
                    expand.each((idx, elem) => {
                        var jqEl = jQuery(elem), child = jqEl.children();
                        child.detach();
                        jqEl.parent().prepend(child);
                        jqEl.remove();
                    });
                }
                updateStatus() {
                    this.leggiNomiVoci();
                    this.visualizzaPannelloNoPreferiti();
                }
                leggiNomiVoci() {
                    this._nomiMenu = new Array();
                    this._panel.find('.voce-menu').each((idx, el) => {
                        this._nomiMenu.push(jQuery(el).find('a').text().trim());
                    });
                }
                visualizzaPannelloNoPreferiti() {
                    var esistonoPreferiti = this._panel.find('.voce-menu').length > 0;
                    this._pannelloNoPreferiti.toggle(!esistonoPreferiti);
                }
            }
            exports_1("PannelloPreferiti", PannelloPreferiti);
        }
    }
});
//# sourceMappingURL=pannello-preferiti.js.map