import {MenuBuilder2,IMenuOptions} from "./menu-builder";

export interface JQueryMegaMenu extends JQuery {
    megaMenu(options: IMenuOptions): JQuery;
}


(function ($) {

    function bootstrap() {
        let pluginName = 'megaMenu';

        if ($ == undefined) {
            console.error('Questo plugin richiede jQuery');
        } else {
            $.fn[pluginName] = function (options: IMenuOptions): JQuery {
                var jQueryItem = this as JQuery;

                return jQueryItem.each(function () {
                    if (!$.data(this, "plugin_" + pluginName)) {
                        var megaMenu = new MenuBuilder2($(this), options);

                        megaMenu.build();

                        $.data(this, "plugin_" + pluginName, megaMenu);
                    }
                });
            };
        }
    }

    bootstrap();
}(jQuery));
