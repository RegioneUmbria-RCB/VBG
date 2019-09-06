/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue:true */
/*global $, jQuery, alert, console */

function AutocompleteHelper(options) {
    'use strict';

    var textControl,
        valueControl,
        onGetCampiCollegati;

    function addAutocompleteToTextControl(ctrl) {

        ctrl.autocomplete({
            source: function (request, response) {

                options.valuesService.getCompletionList({
                    idCampo: options.idCampo,
                    partial: textControl.val(),
                    filtri: onGetCampiCollegati(),
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
            search: function (event, ui) { valueControl.val(''); },
            select: function (event, ui) {
                if (ui.item && ui.item.id.length > 0) {
                    valueControl.val(ui.item.id);
                    textControl.val(ui.item.label);
                    valueControl.change();
                } else {
                    valueControl.val('');
                    valueControl.change();
                }

                //return false;
            }
        });
    }

    function blurHandler(e) {
        if (valueControl.val() === '') {
            textControl.val('');
            valueControl.change();
        }

        if (textControl.val() === '') {
            valueControl.val('');
            valueControl.change();
        }
    }

    function keyDownHandler(e) {
        if (e.keyCode === 40) {
            // down pressed

            var $this = $(e.currentTarget);

            if ($this.val() === '') {
                $this.val('%');
                $this.autocomplete("search", "%%");
            }

            return false;
        }

        if (e.keyCode === 27) {
            textControl.val('');
            valueControl.val('');
            valueControl.change();

            return false;
        }
    }
    
    function focusHandler(e) {
        var $this = $(e.currentTarget);
        
        if ($this.val() === '') {
            $this.val('%');
            $this.autocomplete("search", "%%");
        }
    }
    
    
    
    textControl = options.textControl;
    valueControl = options.valueControl;
    onGetCampiCollegati = options.onGetCampiCollegati;

    addAutocompleteToTextControl(textControl);

    textControl.blur(blurHandler);
    textControl.keydown(keyDownHandler);
    textControl.on('focus', focusHandler);
}






function DatiDinamiciSearchService($, serviceUrl, querystring) {
    'use strict';

    this.initializeControl = function initializeControl(cmd) {

        this.createAjaxCall({
            url: 'initializeControl',
            data: JSON.stringify({
                idCampo: cmd.idCampo,
                valore: cmd.valore
            }),
            success: cmd.success
        });
    };


    this.getBaseUrl = function getBaseUrl() {
        return serviceUrl + "/";
    };


    this.getCompletionList = function getCompletionList(cmd) {
        
        console.log('getCompletionList', cmd);
        
        this.createAjaxCall({
            url: 'getCompletionList',
            data: JSON.stringify({
                idCampo: cmd.idCampo,
                partial: cmd.partial,
                filtri: cmd.filtri
            }),
            success: cmd.success
        });
    };


    this.createAjaxCall = function createAjaxCall(options) {
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

    self.options = options;
    self.campoCodice = campoDatiDinamici;
    self.searchService = searchService;
    self.idCampoDinamico = this.campoCodice.data('d2id');
    self.nomeCampo = this.campoCodice.data('nomeCampo');
    self.indice = this.campoCodice.data('d2indice');
    self.dipendeDa = this.campoCodice.data('dipendeDa');
    self.valoriCampiCollegati = {};

    self.autocompleteHelper = null;

    self.campoCodice.css('display', 'none');
    self.contenitore = $('<span />', {
        'class': self.options.cssClassContenitore,
        'id': 'container_' + self.campoCodice.attr('id')
    });

    self.campoCodice.wrap(function () {
        return self.contenitore;
    });

    self.campoDescrizione = $('<input />', {
        'class': self.options.cssClassCampoDescrizione,
        id: 'descrizione_' + self.campoCodice.attr('id'),
        type: 'text'
    }).appendTo(self.campoCodice.parent());

    self.campoDescrizione.attr('size', self.campoCodice.data('columns'));

    self.inizializza = function () {

        var i = 0,
            campiCollegati,
            nomeCampoEx,
            campo;

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
            },
            onGetCampiCollegati: function () {
                
                var result = [],
                    prop;
                
                if (self.dipendeDa) {
                    for (prop in self.valoriCampiCollegati) {
                        if (self.valoriCampiCollegati.hasOwnProperty(prop)) {
                            result.push({
                                nome: prop,
                                val: self.valoriCampiCollegati[prop]
                            });
                        }
                    }
                }
                
                return result;
            }
        });

        this.campoCodice.bind('errorSet', function () {
            self.campoDescrizione.addClass('d2Errori');
        });

        this.campoCodice.bind('errorRemoved', function () {
            self.campoDescrizione.removeClass('d2Errori');
        });

        // Collego il campo all'handler di modifica di eventuali campi collegati
        if (self.dipendeDa) {
            campiCollegati = self.dipendeDa.split(',');

            console.log('Campi collegati al campo di ricerca', campiCollegati);

            for (i = 0; i < campiCollegati.length; i += 1) {
                nomeCampoEx = campiCollegati[i];
                
                campo = $('*[data-nome-campo=' + nomeCampoEx + '][data-d2indice=' + self.indice + ']');

                campo.on('campoModificato', function (e, val) {

                    var nomeCampo = $(e.currentTarget).data('nomeCampo'),
                        indice = $(e.currentTarget).data('d2indice'),
                        valoreOld;
                    
                    console.log('campoModificato, nomeCampo=', nomeCampo, ' valore=', val, ' indice=', indice);

                    valoreOld = self.valoriCampiCollegati[nomeCampo];
                    
                    self.valoriCampiCollegati[nomeCampo] = val.valore;
                    
                    if (valoreOld !== val.valore) {
                        self.campoCodice.val('');
                        self.campoDescrizione.val('');
                    }
                    
                    console.log(self.nomeCampo, ' valoriCampiCollegati=', self.valoriCampiCollegati);
                });
            }
        }
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