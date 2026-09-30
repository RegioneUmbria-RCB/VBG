System.register(['./event-names', './menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var EventNames, menu_root_1;
    var SelezionaSoftware;
    return {
        setters:[
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class SelezionaSoftware {
                constructor(_panel) {
                    this._panel = _panel;
                    this._menuId = '';
                    this._panelId = _panel.data('panelId');
                    this._parent = this._panel.parent();
                    this._menuRoot = new menu_root_1.MenuRoot(_panel);
                    _panel.on("mouseover click", (e) => {
                        this._menuRoot.trigger(EventNames.ApriPannello, [{
                                panelId: this._panelId,
                                menuId: this._menuId,
                                element: this._parent
                            }]);
                    });
                }
            }
            exports_1("SelezionaSoftware", SelezionaSoftware);
        }
    }
});
//# sourceMappingURL=seleziona-software.js.map