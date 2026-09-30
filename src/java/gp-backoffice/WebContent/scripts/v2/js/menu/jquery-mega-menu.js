System.register(["./menu-builder"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var menu_builder_1;
    return {
        setters:[
            function (menu_builder_1_1) {
                menu_builder_1 = menu_builder_1_1;
            }],
        execute: function() {
            (function ($) {
                function bootstrap() {
                    let pluginName = 'megaMenu';
                    if ($ == undefined) {
                        console.error('Questo plugin richiede jQuery');
                    }
                    else {
                        $.fn[pluginName] = function (options) {
                            var jQueryItem = this;
                            return jQueryItem.each(function () {
                                if (!$.data(this, "plugin_" + pluginName)) {
                                    var megaMenu = new menu_builder_1.MenuBuilder2($(this), options);
                                    megaMenu.build();
                                    $.data(this, "plugin_" + pluginName, megaMenu);
                                }
                            });
                        };
                    }
                }
                bootstrap();
            }(jQuery));
        }
    }
});
//# sourceMappingURL=jquery-mega-menu.js.map