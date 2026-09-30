System.register(['./event-names', './menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var EventNames, menu_root_1;
    var CasellaRicercaTestuale;
    return {
        setters:[
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class CasellaRicercaTestuale {
                constructor(_menuId, _panel) {
                    this._menuId = _menuId;
                    this._panel = _panel;
                    this._menuRoot = new menu_root_1.MenuRoot(this._panel);
                    this._textbox = this._panel.find('input[type=text]');
                    this._textbox.on('focus click', (e) => {
                        this._menuRoot.trigger(EventNames.ApriRicercaTestuale);
                        this._menuRoot.trigger(EventNames.BloccaCambiamentoPannello);
                        this._panel.addClass('selected');
                    });
                    this._textbox.on('blur', (e) => {
                        this._menuRoot.trigger(EventNames.SbloccaCambiamentoPannello);
                        this._panel.removeClass('selected');
                    });
                    this._textbox.on('input propertychange paste', (e) => {
                        var text = this._textbox.val();
                        if (text.length > 2) {
                            this._menuRoot.trigger(EventNames.RicercaTesto, {
                                text: text,
                                menuId: this._menuId
                            });
                        }
                        else {
                            this._menuRoot.trigger(EventNames.ResetRicercaTestuale);
                        }
                    });
                    this._panel.addClass('selected');
                }
            }
            exports_1("CasellaRicercaTestuale", CasellaRicercaTestuale);
        }
    }
});
//# sourceMappingURL=casella-ricerca-testuale.js.map