define(['jquery'], function ($) {

    function GestioneEndoPresenti() {
        var webServiceUrl,
            token,
            self = this;

        this.callTitoliService = function (idTipoTitolo, onSuccess) {
            $.ajax({
                url: webServiceUrl,
                dataType: 'json',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    token: token,
                    idTipoTitolo: idTipoTitolo
                }),
                success: onSuccess,
                fail: function (jqXHR, textStatus) {
                    console.log("Request failed: " + textStatus);
                }
            });
        };


        this.chkPresenteModificata = function chkPresenteModificata(jqCombo) {
            var checked = jqCombo.is(':checked');

            jqCombo
                .parent()	// span 
                .parent()	// td
                .find('.estremiAtto')
                    .css('display', checked ? 'block' : 'none');
        };

        this.tipoTitoloSelezionato = function (jqDropdownTipoTitolo) {

            var val = jqDropdownTipoTitolo.val(),
                campoMessaggi = jqDropdownTipoTitolo
                                .parent()
                                .parent()
                                .find('.infoEstremiAtto'),
                labelNumero = jqDropdownTipoTitolo
                                .parent()
                                .parent()
                                .find('.labelNumero'),
                labelData = jqDropdownTipoTitolo
                                .parent()
                                .parent()
                                .find('.labelData'),
                labelRilasciatoDa = jqDropdownTipoTitolo
                                .parent()
                                .parent()
                                .find('.labelRilasciatoDa');

            if (val === '' || val === '-1') {
                campoMessaggi.html('');
                self.nascondiCampo(labelNumero);
                self.nascondiCampo(labelData);
                self.nascondiCampo(labelRilasciatoDa);
                return;
            }


            self.callTitoliService(val, function (data) {

                campoMessaggi.html(data.d.messaggio);

                if (data.d.richiedeNumero)
                    self.mostraCampo(labelNumero);
                else
                    self.nascondiCampo(labelNumero);

                if (data.d.richiedeData)
                    self.mostraCampo(labelData);
                else
                    self.nascondiCampo(labelData);

                if (data.d.richiedeRilasciatoDa)
                    self.mostraCampo(labelRilasciatoDa);
                else
                    self.nascondiCampo(labelRilasciatoDa);
            });
        };

        this.nascondiCampo = function (ctrl) {
            ctrl.removeClass('estremoObbligatorio');
            ctrl.parent().find('.attoTextControl').val('');
            ctrl.parent().css('display', 'none');
        };

        this.mostraCampo = function (ctrl) {
            ctrl.addClass('estremoObbligatorio');
            ctrl.parent().css('display', 'block');
        };

        this.bootstrap = function (options) {
            webServiceUrl = options.serviceUrl;
            token = options.token;

            $(".comboPresente  input[type=checkbox]").click(function () {
                self.chkPresenteModificata($(this));
            });

            $('.dropDownTipiTitolo').change(function () {
                self.tipoTitoloSelezionato($(this));
            });

            $(".comboPresente  input[type=checkbox]").each(function (el) { self.chkPresenteModificata($(this)); });

            $('.dropDownTipiTitolo').each(function (el) { self.tipoTitoloSelezionato($(this)); });
        };

    }

    return new GestioneEndoPresenti();
});