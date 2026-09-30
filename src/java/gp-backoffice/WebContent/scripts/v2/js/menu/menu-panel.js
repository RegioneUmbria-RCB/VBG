System.register(['./left-panel', './right-panel'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var left_panel_1, right_panel_1;
    var MenuPanel;
    return {
        setters:[
            function (left_panel_1_1) {
                left_panel_1 = left_panel_1_1;
            },
            function (right_panel_1_1) {
                right_panel_1 = right_panel_1_1;
            }],
        execute: function() {
            class MenuPanel {
                constructor(_panel) {
                    this._panel = _panel;
                    var rightPanel = _panel.find('.right-panel');
                    this._leftPanel = new left_panel_1.LeftPanel(_panel.find('.left-panel'));
                    this._rightPanel = new right_panel_1.RightPanel(rightPanel);
                }
            }
            exports_1("MenuPanel", MenuPanel);
        }
    }
});
//# sourceMappingURL=menu-panel.js.map