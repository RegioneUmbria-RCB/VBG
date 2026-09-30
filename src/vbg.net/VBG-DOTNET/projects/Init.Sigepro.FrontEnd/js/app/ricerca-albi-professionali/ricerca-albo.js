class RicercaAlbi {
    constructor(_textElement, _hiddenElement, _idComune, _urlRicerca) {
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
        this._textElement.on('blur', (e) => {
            if (this._hiddenElement.val() === '')
                this._textElement.val('');
        });
        this.initRicercaProvincia();
    }
    initRicercaProvincia() {
        if (this._autocompleteCreated) {
            this._textElement.autocomplete('destroy');
        }
        this._textElement.autocomplete({
            source: (request, response) => {
                $.ajax({
                    url: this._urlRicerca,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({
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
    initRicercaRegione() {
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
    const methods = {
        init: function (opts) {
            if (!this.data("plugin_ricercaAlbo")) {
                var ricercaAlbi = new RicercaAlbi(this, opts.hiddenElement, opts.idComune, opts.urlRicerca);
                this.data("plugin_ricercaAlbo", ricercaAlbi);
            }
        },
        ricercaRegione: function () {
            const el = this.data("plugin_ricercaAlbo");
            el.initRicercaRegione();
        },
        ricercaProvincia: function () {
            const el = this.data("plugin_ricercaAlbo");
            el.initRicercaProvincia();
        }
    };
    $.fn.ricercaAlbo = function (methodOrOptions) {
        if (typeof methodOrOptions === "string" && methods[methodOrOptions]) {
            return methods[methodOrOptions].apply(this, Array.prototype.slice.call(arguments, 1));
        }
        else if (typeof methodOrOptions === 'object' || !methodOrOptions) {
            return methods.init.apply(this, arguments);
        }
        else {
            $.error('Method ' + methodOrOptions + ' does not exist on jQuery');
        }
    };
    console.log('ricercaAlbo registrato');
}(jQuery));
//# sourceMappingURL=ricerca-albo.js.map