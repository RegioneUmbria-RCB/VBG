System.register(['./aggiungi-preferiti', './../event-names', './../menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var aggiungi_preferiti_1, EventNames, menu_root_1;
    var RimuoviPreferiti;
    return {
        setters:[
            function (aggiungi_preferiti_1_1) {
                aggiungi_preferiti_1 = aggiungi_preferiti_1_1;
            },
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class RimuoviPreferiti extends aggiungi_preferiti_1.AggiungiPreferiti {
                constructor(panel) {
                    super(panel);
                    this._menuRoot = new menu_root_1.MenuRoot(panel);
                }
                onMouseOver() {
                    this._panel.removeClass('glyphicon-star');
                    this._panel.addClass('glyphicon-star-empty');
                }
                onMouseOut() {
                    this._panel.removeClass('glyphicon-star-empty');
                    this._panel.addClass('glyphicon-star');
                }
                onClick() {
                    var parent = this.findContainer();
                    parent.animate({ height: '0px', padding: '0px' }, 300, () => {
                        parent.remove();
                        this._menuRoot.trigger(EventNames.RimuoviDaPreferiti, [this._softwareId, parent]);
                    });
                }
                setSoftwareId(value) {
                    this._softwareId = value;
                    this._panel.data('softwareId', value);
                    this.findContainer().find('a').data('softwareId', value);
                }
                setText(value) {
                    this.findContainer().find('a').text(value);
                }
            }
            exports_1("RimuoviPreferiti", RimuoviPreferiti);
        }
    }
});
//# sourceMappingURL=rimuovi-preferiti.js.map