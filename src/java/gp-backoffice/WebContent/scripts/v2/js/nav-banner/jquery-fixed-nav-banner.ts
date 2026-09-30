class FixedNavBanner {

    constructor(nav:JQuery) {

        var banner =  jQuery( nav.data('navBanner')),
            bannerHeight = banner.height();

        nav.css('margin-top', bannerHeight);

        jQuery( window ).on('scroll', function() {
            var scroll = jQuery( this ).scrollTop(),
                amount = bannerHeight - scroll;

            if (amount < 0) {
                amount = 0;
            }

            nav.css('margin-top', amount);
        });
    }
}

(function ($) {

    function bootstrap() {
        let pluginName = 'fixedNavBanner';

        if ($ == undefined) {
            console.error('Questo plugin richiede jQuery');
        } else {
            $.fn[pluginName] = function (): JQuery {
                var jQueryItem = this as JQuery;

                return jQueryItem.each(function () {
                    if (!$.data(this, "plugin_" + pluginName)) {
                        var megaMenu = new FixedNavBanner($(this));

                        $.data(this, "plugin_" + pluginName, megaMenu);
                    }
                });
            };
        }
    }

    bootstrap();
}(jQuery));


