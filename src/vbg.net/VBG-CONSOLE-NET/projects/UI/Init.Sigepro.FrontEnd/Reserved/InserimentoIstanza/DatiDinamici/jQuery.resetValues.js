
(function ($) {
    'use strict';
    
	var methods = {
		init: function (customOptions) {
			return this.each(function () {
				
                if (this.SetValue) {    // potrebbe essere richiamato su un campo statico
                    this.SetValue('');
                }
			});
		}
	};


	$.fn.resetValues = function (method) {

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' +  method + ' does not exist on jQuery.resetValues');
		}
	};
    

}(jQuery));