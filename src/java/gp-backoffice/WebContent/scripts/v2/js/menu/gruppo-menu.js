/// <reference path="../../typings/index.d.ts" />
System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var GruppoMenu;
    return {
        setters:[],
        execute: function() {
            class GruppoMenu {
                constructor(_panel) {
                    this._panel = _panel;
                    var funzione = this._panel.find('.espandi-menu');
                    funzione.on('click', (e) => {
                        let el = jQuery(e.currentTarget), targetId = this._panel.data('toggle'), toggle = this._panel.parent().find(targetId), hidden = toggle.hasClass('collapse'), icon = el.find('.glyphicon');
                        toggle.toggleClass('collapse', !hidden);
                        icon.removeClass('glyphicon-menu-down');
                        icon.removeClass('glyphicon-menu-up');
                        icon.addClass(hidden ? 'glyphicon-menu-up' : 'glyphicon-menu-down');
                        e.preventDefault();
                        return false;
                    });
                }
            }
            exports_1("GruppoMenu", GruppoMenu);
        }
    }
});
//# sourceMappingURL=gruppo-menu.js.map