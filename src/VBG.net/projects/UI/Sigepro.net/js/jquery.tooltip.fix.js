/**
*	This plugin will extend the JQuery ui tooltip function so that it fixes the 
*	IE select box issue.
*
*   @Author			William Gaines <sgscot87@gmail.com>
*   @Copyright		2013-2014
*   @Version		1
*	@Namespace		ui
*	@Plugin Name	tooltip
*
*/

(function ($) {

    // References to base methods
    var base = {
        open: $.ui.tooltip.prototype.open
    };

    // Extend the 'tooltip' widget
    $.extend(true, $.ui.tooltip.prototype, {

        // Setup the open function
        open: function (event) {

            // Setup some helpers
            var that = this

            // Get the target
            var test_target = event.target;

            // Find the select box and see if it is not a muiltiple select box and has a title
            if ($(test_target).prop('tagName').toLowerCase() == 'select' && !$(test_target).attr('multiple') && $(test_target).attr('title')) {

                // Get the title
                var title = $(test_target).attr('title');

                // Wrap the element with a div
                $(test_target).wrap('<span title="' + title + '"></span>');

                // Remove title
                $(test_target).removeAttr('title');

            }

            // Call the base open function
            base.open.apply(that, arguments);

        }

    });

})(jQuery);