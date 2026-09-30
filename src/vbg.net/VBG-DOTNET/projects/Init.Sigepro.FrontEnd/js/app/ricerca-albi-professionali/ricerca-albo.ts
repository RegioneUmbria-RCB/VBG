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

    constructor(private readonly _textElement: JQuery, private readonly _hiddenElement: JQuery, private readonly _idComune: string, private readonly _urlRicerca: string) {

        this._textElement.on('blur', (e) => {
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
            source: (request: any, response: any) => {
                $.ajax({
                    url: this._urlRicerca, //'<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
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
            search: (event: Event, ui: JQueryUI.AutocompleteUIParams) => {
                this._hiddenElement.val('');
            },
            select: (event: Event, ui: JQueryUI.AutocompleteUIParams) => {
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
            search: (event: Event, ui: JQueryUI.AutocompleteUIParams) => {
                this._hiddenElement.val('');
            },
            select: (event: Event, ui: JQueryUI.AutocompleteUIParams) => {
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

type VoidFunc = () => void;

interface MethodsList {
    init: (opts: IRicercaOptions) => void,
    ricercaRegione: VoidFunc,
    ricercaProvincia: VoidFunc,
    [key: string]: any
}


interface JQuery {
    ricercaAlbo(methodOptions: any): JQuery;
}

(function ($) {
    const methods: MethodsList = {
            init: function (opts: IRicercaOptions) {

                if (!this.data("plugin_ricercaAlbo")) {
                    var ricercaAlbi = new RicercaAlbi(this, opts.hiddenElement, opts.idComune, opts.urlRicerca);

                    this.data("plugin_ricercaAlbo", ricercaAlbi);
                }
            },

            ricercaRegione: function () {
                const el = this.data("plugin_ricercaAlbo") as RicercaAlbi;

                el.initRicercaRegione();
            },

            ricercaProvincia: function () {
                const el = this.data("plugin_ricercaAlbo") as RicercaAlbi;

                el.initRicercaProvincia();
            }
        };


    $.fn.ricercaAlbo = function (methodOrOptions: any) {
        if (typeof methodOrOptions === "string" && methods[methodOrOptions]) {
            return methods[methodOrOptions].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof methodOrOptions === 'object' || !methodOrOptions) {
            // Default to "init"
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + methodOrOptions + ' does not exist on jQuery');
        }
    };

    console.log('ricercaAlbo registrato');

}(jQuery));