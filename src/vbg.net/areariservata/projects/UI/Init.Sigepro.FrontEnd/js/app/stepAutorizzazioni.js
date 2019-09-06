
function SigeproAutorizzazioniService(path, idComune, software, token) {

    /// <summary>Proxy del web service di lettura dati per il plugin geoin</summary>

    if (AutocompleteHelper === undefined) {
        throw "Script AutocompleteHelper.js non importato";
    }

    this._path = path;
    this._idComune = idComune;
    this._software = software;
    this._token = token;

    this.getNumeroPresenze = function (idDomanda, idAutorizzazione, onSuccess) {
        var self = this;
        $.ajax({
            async: true,
            type: 'POST',
            url: this._path + '/GetNumeroPresenze?token=' + self._token + "&software=" + self._software + "&idcomune=" + self._idComune,
            data: JSON.stringify({
                idComune: self._idComune,
                idDomanda: idDomanda,
                idAutorizzazione: idAutorizzazione
            }),
            success: function (data, status, xhr) {
                onSuccess(data.d);
            },
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });
    };

    this.getElencoEnti = function (partial, onSuccess) {
        var self = this;
        $.ajax({
            async: true,
            type: 'POST',
            url: this._path + '/GetElencoEnti?token=' + self._token + "&software=" + self._software + "&idcomune=" + self._idComune,
            data: JSON.stringify({
                partialMatch: partial
            }),
            success: function (data, status, xhr) {
                onSuccess(data.d);
            },
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });
    };
}

function ListaEntiService(autorizzazioniService) {
    this.getValues = function (partial, onSuccess) {
        autorizzazioniService.getElencoEnti(partial, onSuccess);
    };
}

function GestioneAutorizzazioni(controlli, settings) {

    var sigeproAutorizzazioniService = new SigeproAutorizzazioniService(settings.autorizzazioniServiceUrl, settings.idComune, settings.software, settings.token);

    controlli.ddlAutorizzazioni.change(function () {
        autorizzazioneSelezionata();
    });

    controlli.chkEnteNonTrovato.click(function () {
        spuntaEnteNonTrovatoSelezionata();
    });

    autorizzazioneSelezionata(true);
    spuntaEnteNonTrovatoSelezionata();

    var autoCompleteEnti = new AutocompleteHelper(controlli.txtEnte.find('input[type=text]'), controlli.hidIdEnte, new ListaEntiService(sigeproAutorizzazioniService));


    function autorizzazioneSelezionata(primoCaricamento) {
        var value = controlli.ddlAutorizzazioni.val();

        controlli.fldstDettagliAutorizzazione.css('display', value === '-1' ? 'block' : 'none');
        controlli.divNumeroPresenzeConAutorizzazione.css('display', value === '-1' ? 'none' : 'block');

        if (value !== '' && value !== '-1' && !primoCaricamento) {
            sigeproAutorizzazioniService.getNumeroPresenze('<%=IdDomanda %>', value, function (data) {
                controlli.txtNumeroPresenzeConAutorizzazione.val(data);
            });
        }

        if (value === '')
            controlli.txtNumeroPresenzeConAutorizzazione.val('');
    }

    function spuntaEnteNonTrovatoSelezionata() {
        var value = controlli.chkEnteNonTrovato.is(':checked');

        var textBoxEnte = controlli.txtEnte.find('input[type=text]');

        if (value) {
            textBoxEnte.val('');
            controlli.hidIdEnte.val('');
            textBoxEnte.attr('disabled', 'disabled');
            //controlli.txtEnte.hide();
        }
        else {
            textBoxEnte.removeAttr('disabled');
            controlli.divNomeEstesoEnte.find('input[type=text]').val('');
            //controlli.txtEnte.show();
        }



        controlli.divNomeEstesoEnte.css('display', value ? 'block' : 'none');
    }
}
