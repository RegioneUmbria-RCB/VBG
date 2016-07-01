define(['jquery', 'json', 'jquery.ui'], function ($, JSON) {

    function AutocompleteStradario(settings) {
        this._indirizzoAutoComplete = $('#' + settings.idCampoIndirizzo);
        this._hiddenCodiceStradario = $('#' + settings.idCampoCodiceStradario);
        this._comuneLocalizzazione = $('#' + settings.idCampoComuneLocalizzazione);
        this._serviceUrl = settings.serviceUrl;
        this._idComune = settings.idComune;
        this._codiceComune = settings.codiceComune;
        this._hiddenCodViario = $('#' + settings.idCampoCodViario);
        this._errMsgOuput = $('#errMsgOuput');


        var self = this;

        function svuotaCampi() {
            self._hiddenCodiceStradario.val('');
            self._indirizzoAutoComplete.val('');
            self._errMsgOuput.html('');
        }

        function svuotaValoreSelezionato()
        {
            self._hiddenCodiceStradario.val('');
            self._errMsgOuput.html('');
        }

        this._indirizzoAutoComplete.autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: self._serviceUrl,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({ 'idComune': self._idComune, 'codiceComune': self._codiceComune, 'comuneLocalizzazione': self._comuneLocalizzazione.val() || '', 'match': self._indirizzoAutoComplete.val() }),
                    success: function (data) {

                        if (data.d.ItemCount > data.d.Items.length) {
                            self._errMsgOuput.html('<b>Sono stati restituiti i primi ' + data.d.Items.length + ' elementi su un totale di ' + data.d.ItemCount + "</b>");
                        }

                        response($.map(data.d.Items, function (item) {
                            return {
                                label: item.Descrizione,
                                id: item.Codice,
                                value: item.Descrizione,
                                codViario: item.CodViario
                            };
                        }));
                    }
                });
            },
            search: function (event, ui) { svuotaValoreSelezionato(); },
            select: function (event, ui) {

                self._errMsgOuput.html('');

                if (ui.item && ui.item.id >= 0) {
                    self._hiddenCodiceStradario.val(ui.item.id);
                    self._indirizzoAutoComplete.val(ui.item.value);

                    if (self._hiddenCodViario && self._hiddenCodViario.length > 0) {
                        self._hiddenCodViario.val(ui.item.codViario);
                    }
                }
                else {
                    self._hiddenCodiceStradario.val('');
                }
            }
        });

        return {
           svuotaCampi : svuotaCampi
        }
    }

    return AutocompleteStradario;
});