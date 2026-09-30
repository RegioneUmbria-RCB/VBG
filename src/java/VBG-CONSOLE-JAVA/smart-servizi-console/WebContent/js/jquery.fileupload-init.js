/*
 * jQuery File Upload jQuery UI Plugin 1.3.1
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Personalizzazione INIT
 */

/*jslint nomen: true, unparam: true */
/*global define, window */

(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        // Register as an anonymous AMD module:
        define(['jquery', './jquery.fileupload-jui'], factory);
    } else {
        // Browser globals:
        factory(window.jQuery);
    }
}(function ($) {
    'use strict';
    $.widget('blueimp.fileupload', $.blueimp.fileupload, {
        enableDsValidation: function (enableFlag) {
        	this.options.formData.dsValidation = enableFlag;
        	var dsErrorSpans = this.element.find("TABLE.file-list TD.warning SPAN");
        	var dsIcon = this.element.find("SPAN.icon-firmadigitale");
        	if(enableFlag){
        		dsErrorSpans.show();
        		dsIcon.show();
        	}
        	else{
        		dsErrorSpans.hide();
        		dsIcon.hide();
        	}
        },
    });
}));
