/// <reference path="../../../scripts/typings/jqueryui/jqueryui.d.ts" />
import $ = require('jquery');

interface IRicercaOptions {
    hiddenElement: JQuery;
    idComune: string;
    urlRicerca: string;
}

class RicercaAlbi {

    _autocompleteCreated = false;
    _regioni = ['Abruzzo',
        'Basilicata',
        'Calabria',
        'Campania',
        'Emilia Romagna',
        'Friuli',
        'Lazio',
        'Liguria',
        'Lombardia',
        'Marche',
        'Molise',
        'Piemonte',
        'Puglia',
        'Sardegna',
        'Sicilia',
        'Toscana',
        'Trentino',
        'Umbria',
        'Valle d\'Aosta',
        'Veneto'];

    constructor(private _textElement:JQuery, private _hiddenElement:JQuery, private _idComune:string, private _urlRicerca:string) {

        this._textElement.blur((e) => {
            if (this._hiddenElement.val() === '')
                this._textElement.val('');
        });

        this.initRicercaProvincia();
    }

    public initRicercaProvincia() {

        if (this._autocompleteCreated) {
            this._textElement.autocomplete('destroy');
        }

        this._textElement.autocomplete({
            source: (request, response) => {
                $.ajax({
                    url: this._urlRicerca, //'<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({
                        'aliasComune': this._idComune,
                        'matchProvincia': this._textElement.val()
                    }),
                    success: function (data) {
                        response($.map(data.d, function (item) {
                            return {
                                label: item.Descrizione,
                                id: item.SiglaProvincia,
                                value: item.Provincia
                            };
                        }));
                    }
                });
            },
            search: (event, ui) => {
                this._hiddenElement.val('');
            },
            select: (event, ui) => {
                if (ui.item && ui.item.id.length > 0) {
                    this._hiddenElement.val(ui.item.id);
                    this._textElement.val(ui.item.value);
                }
                else {
                    this._hiddenElement.val('');
                }
            }
        });

        this._autocompleteCreated = true;
    }

    public initRicercaRegione() {

        if (this._autocompleteCreated) {
            this._textElement.autocomplete('destroy');
        }

        this._textElement.autocomplete({
            source: this._regioni,
            search: (event, ui) => {
                this._hiddenElement.val('');
            },
            select: (event, ui) => {
                if (ui.item && ui.item.value.length > 0) {
                    this._hiddenElement.val(ui.item.value);
                    this._textElement.val(ui.item.value);
                }
                else {
                    this._hiddenElement.val('');
                }
            }
        });

        this._autocompleteCreated = true;
    }
}


(function ($) {
    var pluginName = 'ricercaAlbo',
        methods = {
            init: function (opts) {
                var options = opts as IRicercaOptions;

                if (!this.data("plugin_" + pluginName)) {
                    var ricercaAlbi = new RicercaAlbi(this, options.hiddenElement, options.idComune, options.urlRicerca);

                    this.data("plugin_" + pluginName, ricercaAlbi);
                }
            },

            ricercaRegione: function () {
                var el = this.data("plugin_" + pluginName) as RicercaAlbi;

                el.initRicercaRegione();
            },

            ricercaProvincia: function () {
                var el = this.data("plugin_" + pluginName) as RicercaAlbi;

                el.initRicercaProvincia();
            }
        };


    $.fn[pluginName] = function (methodOrOptions) {
        if (methods[methodOrOptions]) {
            return methods[methodOrOptions].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof methodOrOptions === 'object' || !methodOrOptions) {
            // Default to "init"
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + methodOrOptions + ' does not exist on jQuery.tooltip');
        }
    };

    console.log('ricercaAlbo registrato');

} (jQuery));