System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var MenuRoot;
    return {
        setters:[],
        execute: function() {
            class MenuRoot {
                constructor(element) {
                    this._root = this.findRoot(element);
                    this._menuId = this._root.data('menuId');
                    if (this._menuId === '' || this._menuId == undefined) {
                        console.error("Il menu root non ha un menu id impostato");
                    }
                }
                findRoot(element) {
                    var tmpEl = element, safetyCheck = 0;
                    while (tmpEl != null && !tmpEl.hasClass('menu-root')) {
                        if (safetyCheck > 100) {
                            throw 'safetyCheck!!!!!';
                        }
                        tmpEl = tmpEl.parent();
                        safetyCheck++;
                    }
                    if (tmpEl == null) {
                        throw "Impossibile trovare un menu root";
                    }
                    return tmpEl;
                }
                trigger(event, extraParameters) {
                    return this._root.trigger(event, extraParameters);
                }
                on(events, handler) {
                    return this._root.on(events, handler);
                }
                getMenuId() {
                    return this._menuId;
                }
            }
            exports_1("MenuRoot", MenuRoot);
        }
    }
});
//# sourceMappingURL=menu-root.js.map