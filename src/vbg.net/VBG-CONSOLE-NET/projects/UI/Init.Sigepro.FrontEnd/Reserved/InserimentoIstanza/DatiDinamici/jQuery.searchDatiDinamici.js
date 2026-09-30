/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue:true */
/*global $, jQuery, alert */

function AutocompleteHelper(options) {
    'use strict';
    
	options.textControl.autocomplete({
		source: function (request, response) {

			options.valuesService.getCompletionList({
				idCampo: options.idCampo,
				partial: options.textControl.val(),
				success: function (data) {
					response($.map(data, function (item) {
						return {
							label: item.label,
							id: item.value,
							value: item.label
						};
					}));
				}
			});
		},
		minLength: options.minLength || 1,
		search: function (event, ui) { options.valueControl.val(''); },
		select: function (event, ui) {
			if (ui.item && ui.item.id.length > 0) {
				options.valueControl.val(ui.item.id);
				options.textControl.val(ui.item.label);
			} else {
				options.valueControl.val('');
			}

			//return false;
		}
	});

	options.textControl.blur(function (e) {
		if (options.valueControl.val() === '') {
			options.textControl.val('');
        }

		if (options.textControl.val() === '') {
			options.valueControl.val('');
        }
	});

	options.textControl.keydown(function (e) {
		if (e.keyCode === 40) {
		   //alert( "down pressed" );

            if ($(this).val() === '') {
                $(this).val('%');
                $(this).autocomplete("search", "%%");
            }

            return false;
        }

		if (e.keyCode === 27) {
            options.textControl.val('');
            options.valueControl.val('');
            return false;
        }
	});
}

function DatiDinamiciSearchService($, serviceUrl, querystring) {
    'use strict';
    
	this.initializeControl = function (cmd) {
		
		this.createAjaxCall({
            url: 'initializeControl',
            data: JSON.stringify({
                idCampo: cmd.idCampo,
                valore: cmd.valore
            }),
            success: cmd.success
        });

		//cmd.success({ value: cmd.valore, label: 'descrizione di ' + cmd.valore });
	};


	this.getCompletionList = function (cmd) {
		this.createAjaxCall({
			url: 'getCompletionList',
			data: JSON.stringify({
				idCampo: cmd.idCampo,
				partial: cmd.partial
			}),
			success: cmd.success
		});
	};


	this.getBaseUrl = function () {
		return serviceUrl + "/";
	};


	this.createAjaxCall = function (options) {
		var that = this,
            fullUrl = this.getBaseUrl() + options.url + "?" + querystring;

		$.ajax({
			type: options.method || 'POST',
			dataType: "json",
			url: fullUrl,
			contentType: 'application/json; charset=UTF-8',
			success: function (data) {
				if (options.success) {
					options.success(data.d);
                }
			},
			failure: options.onFailure,
			data: options.data
		});
	};

}


// cssClassContenitore
// cssClassCampoDescrizione
// serviceUrl
// querystring

function ControlloSearchDatiDinamici($, campoDatiDinamici, options, searchService) {
    'use strict';
	var self = this;

	this.options = options;
	this.campoCodice = campoDatiDinamici;
	this.searchService = searchService;
	this.idCampoDinamico = this.campoCodice.data('d2id');
	this.autocompleteHelper = null;

	this.campoCodice.css('display', 'none');
	this.contenitore = $('<span />', {
		'class': self.options.cssClassContenitore,
		'id': 'container_' + self.campoCodice.attr('id')
	});

	this.campoCodice.wrap(function () {
		return self.contenitore;
	});

	this.campoDescrizione = $('<input />', {
		'class': self.options.cssClassCampoDescrizione,
		id: 'descrizione_' + self.campoCodice.attr('id'),
		type: 'text'
	}).appendTo(self.campoCodice.parent());

	this.campoDescrizione.attr('size', self.campoCodice.data('columns'));

	this.inizializza = function () {
		self.campoDescrizione.addClass('ui-autocomplete-loading');

		self.searchService.initializeControl({
			idCampo: self.idCampoDinamico,
			valore: self.campoCodice.val(),
			success: function (data) {
				self.campoCodice.val(data.value);
				self.campoDescrizione.val(data.label);
				self.campoDescrizione.removeClass('ui-autocomplete-loading');
				self.campoDescrizione.blur();
			}
		});

		self.autocompleteHelper = new AutocompleteHelper({
			idCampo: self.idCampoDinamico,
			textControl: self.campoDescrizione,
			valueControl: self.campoCodice,
			valuesService: self.searchService,
			minLength: 1,
			onselect: function () {
				self.campoDescrizione.blur();
			}
		});

        this.campoCodice.bind('errorSet', function () {
            self.campoDescrizione.addClass('d2Errori');
        });

        this.campoCodice.bind('errorRemoved', function () {
            self.campoDescrizione.removeClass('d2Errori');
        });
	};
}





(function ($) {
    'use strict';
    
	var methods = {
		prepara: function (customOptions) {
			return this.each(function () {
                var options = $.extend($.fn.searchDatiDinamici.defaultOptions, customOptions),
                    $this = $(this),
                    data = $this.data('__controlloSearchDatiDinamici'),
                    searchService;
                
				$.fn.searchDatiDinamici.defaultOptions.querystring = window.location.search.substring(1);

				if (!data) {
					searchService = new DatiDinamiciSearchService($, options.serviceUrl, options.querystring);

					$this.data('__controlloSearchDatiDinamici', new ControlloSearchDatiDinamici($, $this, options, searchService));
				}
			});
		},
		inizializza: function (customOptions) {
			var data = $(this).data('__controlloSearchDatiDinamici');

			if (!data) {
				throw 'Il controllo di ricerca dati dinamici non è stato inizializzato correttamente';
			}

			data.inizializza();
		}
	};


	$.fn.searchDatiDinamici = function (method) {

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.prepara.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.searchDatiDinamici');
		}


	};


	$.fn.searchDatiDinamici.defaultOptions = {
		cssClassContenitore: 'd2SearchContainer',
		cssClassCampoDescrizione: 'd2Control d2SearchDescription',
		serviceUrl: 'Helper/SearchHandlers/SearchHandler.asmx',
		querystring: ''
	};

}(jQuery));