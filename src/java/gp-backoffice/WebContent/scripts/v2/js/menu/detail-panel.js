/// <reference path="../../typings/index.d.ts" />
System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var DetailPanel;
    return {
        setters:[],
        execute: function() {
            class DetailPanel {
                constructor(_panel) {
                    this._panel = _panel;
                }
                hide() {
                    this._panel.hide();
                }
                show() {
                    this._panel.show();
                }
                scrollTop() {
                    this._panel.scrollTop(0);
                }
            }
            exports_1("DetailPanel", DetailPanel);
        }
    }
});
//# sourceMappingURL=detail-panel.js.map