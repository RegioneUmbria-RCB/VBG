/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue:true */
/*jslint unparam: true */
/*global $, jQuery, alert, console */

/*searchdatidinamici*/
function AutocompleteHelper(options) {
    'use strict';

    var textControl,
        valueControl,
        onGetCampiCollegati,
        cache = {};

    function addAutocompleteToTextControl(ctrl) {

        ctrl.autocomplete({
            source: function (request, response) {

                var searchedText = textControl.val(),
                    cacheKey,
                    valoriCollegati = onGetCampiCollegati();

                if (searchedText === '' && request.term && request.term !== '')
                {
                    searchedText = request.term; 
                }

                cacheKey = searchedText;

                if (!cacheKey || cacheKey === '%') {
                    cacheKey = "$empty_data$";
                }

                if (valoriCollegati) {

                    for (var i = 0; i < valoriCollegati.length; i++) {
                        cacheKey += "#" + valoriCollegati[i].nome + "#" + valoriCollegati[i].val;
                    }
                    
                }

                console.log(cacheKey);

                if (cacheKey in cache) {
                    response(cache[cacheKey]);
                    return;
                }

                options.valuesService.getCompletionList({
                    idCampo: options.idCampo,
                    partial: searchedText,
                    filtri: onGetCampiCollegati(),
                    success: function (data) {
                        var mappedData = $.map(data, function (item) {
                            return {
                                label: item.label,
                                id: item.value,
                                value: item.label
                            };
                        });

                        cache[cacheKey] = mappedData;

                        response(mappedData);
                    }
                });
            },
            minLength: options.minLength || 0,
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

    function performSearchIfEmpty(e) {
        var el = $(this),
            oldDelay = el.autocomplete("option", "delay");

        console.log(oldDelay);

        if (el.val() === '') {

            el.autocomplete("option", "delay", 0);
            el.autocomplete("search", "%");
            el.autocomplete("option", "delay", oldDelay);
        }
    }

    textControl = options.textControl;
    valueControl = options.valueControl;
    onGetCampiCollegati = options.onGetCampiCollegati;

    addAutocompleteToTextControl(textControl);

    textControl.blur(blurHandler);
    textControl.keydown(keyDownHandler);
    textControl.on('focus', performSearchIfEmpty);
    textControl.on('input', performSearchIfEmpty);
}






function DatiDinamiciSearchService($, serviceUrl, querystring) {
    'use strict';

    console.log("DatiDinamiciSearchService inizializzato");
    console.log("- serviceUrl:", serviceUrl);
    console.log("- querystring:", querystring);

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
        var fullUrl = this.getBaseUrl() + options.url + "?" + querystring;

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

    self.campoDescrizione.tooltip({
        position: {
            my: "left+5 center",
            at: "right center"
        },
        tooltipClass: 'tooltipErrore tooltip-errore'
    });

    self.campoDescrizione.attr('size', self.campoCodice.data('columns'));

    var iconaLente = $("<span></span>", { "class": "glyphicon glyphicon-search" });
    iconaLente.insertAfter(self.campoDescrizione);

    var widthDescrizione = self.campoDescrizione.width(),
        widthLente = 22,
        delta = widthDescrizione - widthLente + 20;

    self.campoDescrizione.width(delta);
    self.campoDescrizione.css("padding-right", widthLente + 3);

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

                var oldValue = self.campoCodice.val();

                self.campoCodice.val(data.value);
                self.campoDescrizione.val(data.label);
                self.campoDescrizione.removeClass('ui-autocomplete-loading');

                if (oldValue !== data.value ) {
                    self.campoCodice.trigger(self.campoCodice.data('eventoModifica'));
                }
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

        this.campoCodice.bind('campoInizializzato', function () {

            self.campoCodice.unbind('errorSet');
            self.campoCodice.unbind('errorRemoved');

            self.campoCodice.bind('errorSet', function (event, msg) {
                self.campoDescrizione.addClass('d2Errori');
                self.campoDescrizione.attr('title', msg);
                self.campoDescrizione.tooltip('enable');
            });

            self.campoDescrizione.bind('errorRemoved', function () {
                //console.log('errorremoved su controllo ricerca');

                self.campoDescrizione.attr('title', null);
                self.campoDescrizione.tooltip('disable');
                self.campoDescrizione.removeClass('d2Errori');
            });
        });


        // Collego il campo all'handler di modifica di eventuali campi collegati
        if (self.dipendeDa) {
            campiCollegati = self.dipendeDa.split(',');

            for (i = 0; i < campiCollegati.length; i += 1) {
                nomeCampoEx = campiCollegati[i];

                campo = $('*[data-nome-campo=' + nomeCampoEx + '][data-d2indice=' + self.indice + ']');

                if (campo.length) {
                    var valore = campo[0].GetValue();

                    self.valoriCampiCollegati[nomeCampoEx] = valore.valore;
                } else {
                    campo = $('*[data-nome-campo=' + nomeCampoEx + '][data-d2indice=0]');

                    if (campo.length) {
                        var valore = campo[0].GetValue();

                        self.valoriCampiCollegati[nomeCampoEx] = valore.valore;
                    }
                }
                

                campo.on('campoModificato', function (e, val) {

                    var nomeCampo = $(e.currentTarget).data('nomeCampo'),
                        //indice = $(e.currentTarget).data('d2indice'),
                        valoreOld;

                    valoreOld = self.valoriCampiCollegati[nomeCampo];

                    self.valoriCampiCollegati[nomeCampo] = val.valore;

                    if (valoreOld !== val.valore) {
                        self.campoCodice.val('');
                        self.campoDescrizione.val('');
                    }
                });
            }
        }
    };

    campoDatiDinamici.data('inizializzaSuModificaSw', 'true');
    campoDatiDinamici.data('d2Logic', self);
}





(function ($) {
    'use strict';

    var methods = {
        prepara: function (customOptions) {
            return this.each(function () {
                var options = {},
                    $this = $(this),
                    data = $this.data('__controlloSearchDatiDinamici'),
                    searchService;

                $.fn.searchDatiDinamici.defaultOptions.querystring = window.location.search.substring(1);

                if (!data) {

                    options = $.extend(options, $.fn.searchDatiDinamici.defaultOptions, customOptions)

                    searchService = new DatiDinamiciSearchService($, options.serviceUrl, options.querystring);

                    $this.data('__controlloSearchDatiDinamici', new ControlloSearchDatiDinamici($, $this, options, searchService));
                }
            });
        },
        inizializza: function () {
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
        }

        if (typeof method === 'object' || !method) {
            return methods.prepara.apply(this, arguments);
        }

        $.error('Method ' + method + ' does not exist on jQuery.searchDatiDinamici');
    };


    $.fn.searchDatiDinamici.defaultOptions = {
        cssClassContenitore: 'd2SearchContainer',
        cssClassCampoDescrizione: 'd2Control d2SearchDescription',
        serviceUrl: 'Helper/SearchHandlers/SearchHandler.asmx',
        querystring: ''
    };

}(jQuery));