define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var RicercaAlbi = /** @class */ (function () {
        function RicercaAlbi(_textElement, _hiddenElement, _idComune, _urlRicerca) {
            var _this = this;
            this._textElement = _textElement;
            this._hiddenElement = _hiddenElement;
            this._idComune = _idComune;
            this._urlRicerca = _urlRicerca;
            this._autocompleteCreated = false;
            this._regioni = ['Abruzzo',
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
            this._textElement.blur(function (e) {
                if (_this._hiddenElement.val() === '')
                    _this._textElement.val('');
            });
            this.initRicercaProvincia();
        }
        RicercaAlbi.prototype.initRicercaProvincia = function () {
            var _this = this;
            if (this._autocompleteCreated) {
                this._textElement.autocomplete('destroy');
            }
            this._textElement.autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: _this._urlRicerca, //'<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        data: JSON.stringify({
                            'aliasComune': _this._idComune,
                            'matchProvincia': _this._textElement.val()
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
                search: function (event, ui) {
                    _this._hiddenElement.val('');
                },
                select: function (event, ui) {
                    if (ui.item && ui.item.id.length > 0) {
                        _this._hiddenElement.val(ui.item.id);
                        _this._textElement.val(ui.item.value);
                    }
                    else {
                        _this._hiddenElement.val('');
                    }
                }
            });
            this._autocompleteCreated = true;
        };
        RicercaAlbi.prototype.initRicercaRegione = function () {
            var _this = this;
            if (this._autocompleteCreated) {
                this._textElement.autocomplete('destroy');
            }
            this._textElement.autocomplete({
                source: this._regioni,
                search: function (event, ui) {
                    _this._hiddenElement.val('');
                },
                select: function (event, ui) {
                    if (ui.item && ui.item.value.length > 0) {
                        _this._hiddenElement.val(ui.item.value);
                        _this._textElement.val(ui.item.value);
                    }
                    else {
                        _this._hiddenElement.val('');
                    }
                }
            });
            this._autocompleteCreated = true;
        };
        return RicercaAlbi;
    }());
    (function ($) {
        var pluginName = 'ricercaAlbo', methods = {
            init: function (opts) {
                var options = opts;
                if (!this.data("plugin_" + pluginName)) {
                    var ricercaAlbi = new RicercaAlbi(this, options.hiddenElement, options.idComune, options.urlRicerca);
                    this.data("plugin_" + pluginName, ricercaAlbi);
                }
            },
            ricercaRegione: function () {
                var el = this.data("plugin_" + pluginName);
                el.initRicercaRegione();
            },
            ricercaProvincia: function () {
                var el = this.data("plugin_" + pluginName);
                el.initRicercaProvincia();
            }
        };
        $.fn[pluginName] = function (methodOrOptions) {
            if (methods[methodOrOptions]) {
                return methods[methodOrOptions].apply(this, Array.prototype.slice.call(arguments, 1));
            }
            else if (typeof methodOrOptions === 'object' || !methodOrOptions) {
                // Default to "init"
                return methods.init.apply(this, arguments);
            }
            else {
                $.error('Method ' + methodOrOptions + ' does not exist on jQuery.tooltip');
            }
        };
        console.log('ricercaAlbo registrato');
    }(jQuery));
});
//# sourceMappingURL=ricerca-albo.js.map