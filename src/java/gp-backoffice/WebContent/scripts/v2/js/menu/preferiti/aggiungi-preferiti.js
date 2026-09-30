System.register(['./../event-names', './../menu-root'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var EventNames, menu_root_1;
    var AggiungiPreferiti;
    return {
        setters:[
            function (EventNames_1) {
                EventNames = EventNames_1;
            },
            function (menu_root_1_1) {
                menu_root_1 = menu_root_1_1;
            }],
        execute: function() {
            class AggiungiPreferiti {
                constructor(_panel) {
                    this._panel = _panel;
                    this._softwareId = '';
                    this._softwareId = this._panel.data('softwareId');
                    this._menuRoot = new menu_root_1.MenuRoot(_panel);
                    _panel.on('mouseover', (e) => {
                        this.onMouseOver();
                    });
                    _panel.on('mouseout', (e) => {
                        this.onMouseOut();
                    });
                    _panel.on('click', (e) => {
                        this.onClick();
                    });
                }
                onMouseOver() {
                    this._panel.removeClass('glyphicon-star-empty');
                    this._panel.addClass('glyphicon-star');
                }
                onMouseOut() {
                    this._panel.removeClass('glyphicon-star');
                    this._panel.addClass('glyphicon-star-empty');
                }
                onClick() {
                    var text = this.findContainer().find('a').text().trim(), menuName = this._panel.data('menuName');
                    this._menuRoot.trigger(EventNames.AggiungiAPreferiti, [this._softwareId, text, menuName]);
                }
                findContainer() {
                    var parent = this._panel.parent();
                    while (parent.length > 0 && parent[0].tagName.toUpperCase() != "LI") {
                        parent = parent.parent();
                    }
                    return parent;
                }
            }
            exports_1("AggiungiPreferiti", AggiungiPreferiti);
        }
    }
});
//aggiungi-preferiti 
//# sourceMappingURL=aggiungi-preferiti.js.map