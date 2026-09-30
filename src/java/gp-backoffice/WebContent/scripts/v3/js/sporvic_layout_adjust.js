/**
 * Funzioni js che effettuano sistemazioni al dom delle pagine nel caso di stile = sporvic3
 */
var restyling = false;

restyle = function () {
	if(restyling)return;
	restyling = true;
	var restyledClass = "gp-restyled";
	var restyledSelector = "." + restyledClass;
    jQuery('#functions li').not(restyledSelector).addClass('btn btn-primary ' + restyledClass);
    jQuery('input[type=checkbox]').not(restyledSelector).each(function (idx, item) {
		 	var el = jQuery(item);						 	
		 	el.css("width","14px");	
		 	el.css("height","12px"); // uguale al font-size
		 	el.addClass(restyledClass);
    });
    
    jQuery('.help_image').not(restyledSelector).addClass('glyphicon glyphicon-question-sign ' + restyledClass);
    //jQuery('.notifichePopupInfoInner').addClass('glyphicon glyphicon-info-sign');

    jQuery('input[type=text]').not(restyledSelector).each(function (idx, item) {
		 	var el = jQuery(item);
		 	var size = el.attr('size');
		 	//el.addClass(restyledClass);
		 	var id = el.attr('id');
		 	if (id && id.indexOf('data') >= 0){
		 		return;
		 	}
		 	if(size){
		 		var newSize = size*7 + 20;
		 		
		 		if (newSize < 120)
		 		{
		 			newSize = 120;
		 		}					 		
		 		
		 		el.css('max-width',newSize.toString() + "px");	
		 	}						 	
    });
    
    jQuery('textarea').not(restyledSelector).each(function (idx, item) {
 	   	var el = jQuery(item),
 	   		size = el.attr('cols');
 	   	//el.addClass(restyledClass);
 	   	if(size){
		 		var newSize = size*7 + 20;
		 		
		 		if (newSize < 120)
		 		{
		 			newSize = 120;
		 		}					 		
		 		
		 		el.css('max-width',newSize.toString() + "px");	
		 	}
    });
    
    jQuery('.searchbox').not(restyledSelector).each(function (idx, item) {
		 	var el = jQuery(item),
		 		parent = el.parent(),
		 		template = jQuery("<div class='input-group'></div>"),
		 		addonTemplate = jQuery("<span class='input-group-addon'><span class='glyphicon glyphicon-search'></span></span>");
		 	//el.addClass(restyledClass);
		 	var position = el.index();
		 	template.append(el);
		 	template.append(addonTemplate);
		 	var siblings = parent.children().detach();
		 	var offset = 0;
		 	for(var i = 0; i <= siblings.length; i++){
		 		if(i == position){
		 			parent.append(template);
		 			offset = 1;
		 		}
		 		else{
		 			parent.append(siblings[i - offset])
		 		}
		 	}
    });
    //jQuery('.searchbox').addClass(restyledClass);
    jQuery('.calendario').not(restyledSelector).each(function (idx, item) {
 	   
		 	var el = jQuery(item),
		 		parent = el.parent(),
		 		template = jQuery("<div class='input-group date-input'></div>"),
		 		addonTemplate = jQuery("<span class='input-group-addon'></span>"),
		 		input = parent.find('#'+el.data('calRefid'));
		 		
			el.find('>img').remove();
		 	el.addClass('glyphicon glyphicon-calendar');
		 	var position = input.index();
		 	template.append(input);
		 	template.append(addonTemplate);
		 	addonTemplate.append(el);
		 	var siblings = parent.children().detach();
		 	var offset = 0;
		 	for(var i = 0; i <= siblings.length; i++){
		 		if(i == position){
		 			parent.append(template);
		 			offset = 1;
		 		}
		 		else{
		 			parent.append(siblings[i - offset])
		 		}
		 	}
    });
    //jQuery('.calendario').addClass(restyledClass);
    jQuery('.error').not(restyledSelector).each(function (idx, item) {
 	   var el = jQuery(item),
 	   		parent = el.parent(),
 	   		formGroup = parent.find('>.form-control'),
 	   		template = jQuery('<div class="input-group has-error has-danger"></div>'),
 	   		inputGroup = parent.find('.input-group');
 	   
 	   el.addClass('help-block ' + restyledClass);
 	   template.append(el);
 	   parent.append(template);
 	   
 	   if (inputGroup.length > 0) {
 		   //inputGroup.append(el);
 		   inputGroup.insertBefore(el);
 	   }
 	   
    });
    //jQuery('.error').addClass(restyledClass);
    jQuery('input[type=text], textarea, select').not(restyledSelector).addClass('form-control ' + restyledClass);
    
    restyling = false;
};

	/*
	(function ($) {
	         $(restyle);     
	}(jQuery));
	*/

	jQuery(document).ready(function(){
		restyle();
		bootstrapRestyle();
		//observeDOM(document.body,restyle);
	});

	bootstrapRestyle = function() {
	    var isBootstrapEvent = false;
	    if (window.jQuery) {
	        var all = jQuery('*');
	        jQuery.each(['hide.bs.dropdown', 
	            'hide.bs.collapse', 
	            'hide.bs.modal', 
	            'hide.bs.tooltip',
	            'hide.bs.popover'], function(index, eventName) {
	            all.on(eventName, function( event ) {
	                isBootstrapEvent = true;
	            });
	        });
	    }
	    var originalHide = Element.hide;
	    Element.addMethods({
	        hide: function(element) {
	            if(isBootstrapEvent) {
	                isBootstrapEvent = false;
	                return element;
	            }
	            return originalHide(element);
	        }
	    });
	};
		
