define(['jquery'], function ($) {

    function VerificaCf(configuration) {
        this._configuration = configuration;
        VerificaCf.globalInstance = this;

        // Invalida
        this._canSubmitForm = false;
        this._invalidateState();

        this._registraChangeHandler(this._configuration.txtNome);
        this._registraChangeHandler(this._configuration.txtCognome);
        this._registraChangeHandler(this._configuration.txtDataNascita);
        this._registraChangeHandler(this._configuration.txtSesso);
        this._registraChangeHandler(this._configuration.txtComuneNascita);
        this._registraChangeHandler(this._configuration.txtCodiceFiscale);

        //this._configuration.bottoneInvio.click(this.doCheck);
    }

    VerificaCf.globalInstance = {};

    VerificaCf.prototype = {
        doCheck: function (e) {
            if (VerificaCf.globalInstance._canSubmitForm)
                return true;

            var cognome = VerificaCf.globalInstance._configuration.txtCognome.val();
            var nome = VerificaCf.globalInstance._configuration.txtNome.val();
            var dataNascita = VerificaCf.globalInstance._configuration.txtDataNascita.val();
            var sesso = VerificaCf.globalInstance._configuration.txtSesso.val();
            var comuneNascita = VerificaCf.globalInstance._configuration.txtComuneNascita.val();
            var codiceFiscale = VerificaCf.globalInstance._configuration.txtCodiceFiscale.val();

            var esitoValidazione = {};

            $.ajax({
                url: VerificaCf.globalInstance._configuration.urlVerifica,
                type: "POST",
                async: false,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    cognome: cognome,
                    nome: nome,
                    dataNascita: dataNascita,
                    sesso: sesso,
                    comuneNascita: comuneNascita,
                    codiceFiscale: codiceFiscale
                }),
                success: function (data) {
                    esitoValidazione = data;
                }
            });

            if (!esitoValidazione.d)
                return false;

            if (esitoValidazione.d.Errore)
                return false;

            if (!esitoValidazione.d.EsitoValidazione) {
                var el = VerificaCf.globalInstance._configuration.divErrori;

                el.modal('show');

                el.find('.modal-ok-button').on('click', function (e) {
                    el.modal('hide');

                    e.preventDefault();

                    return false;
                });

                el.find('.modal-ko-button').on('click', function (e) {
                    el.modal('hide');

                    VerificaCf.globalInstance._setStateAsValid();
                    VerificaCf.globalInstance._configuration.bottoneInvio.click();

                    e.preventDefault();

                    return false;
                });

                el.find('.modal-body').html(esitoValidazione.d.MessaggioValidazione);
            }
            else {
                return true;
            }

            return false;
        },



        // metodi privati
        _registraChangeHandler: function (el) {
            el.change(this._invalidateState);
        },
        _invalidateState: function () {
            this._canSubmitForm = false;
        },
        _setStateAsValid: function () {
            this._canSubmitForm = true;
        }
    };

    return VerificaCf;
});