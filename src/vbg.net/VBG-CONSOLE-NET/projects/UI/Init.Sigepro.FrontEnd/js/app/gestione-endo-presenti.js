define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var GestioneEndoPresenti = /** @class */ (function () {
        function GestioneEndoPresenti(_webServiceUrl, _token, _jqRoot) {
            var _this = this;
            this._webServiceUrl = _webServiceUrl;
            this._token = _token;
            this._jqRoot = _jqRoot;
            this._chkPresente = this._jqRoot.find('.chk-presente input[type=checkbox]');
            this._ddlTipoTitolo = this._jqRoot.find('.ddl-tipo-titolo select');
            this._txtNumero = this._jqRoot.find('.txt-numero');
            this._txtData = this._jqRoot.find('.txt-data');
            this._txtRilasciatoDa = this._jqRoot.find('.txt-rilasciato-da');
            this._txtNote = this._jqRoot.find('.txt-note');
            this._txtMessaggio = this._jqRoot.find('.info-estremi-atto');
            this._boxEstremiAtto = this._jqRoot.find('.estremi-atto');
            this._chkPresente.on('change', function (e) {
                _this.onCheckModificato();
            });
            this._ddlTipoTitolo.on('change', function (e) {
                _this.onTipoTitoloModificato();
            });
            this._chkPresente.trigger('change');
            this._ddlTipoTitolo.trigger('change');
        }
        GestioneEndoPresenti.prototype.onTipoTitoloModificato = function () {
            var _this = this;
            var tipoTitolo = this._ddlTipoTitolo.val();
            if (tipoTitolo == '' || tipoTitolo == '-1') {
                this._txtMessaggio.html('');
                this.nascondiCampo(this._txtNumero);
                this.nascondiCampo(this._txtData);
                this.nascondiCampo(this._txtRilasciatoDa);
                this.nascondiCampo(this._txtNote);
                return;
            }
            this.callTitoliService(tipoTitolo)
                .then(function (flagsTitolo) {
                _this._txtMessaggio.html(flagsTitolo.messaggio);
                _this.toggle(_this._txtNumero, flagsTitolo.richiedeNumero);
                _this.toggle(_this._txtData, flagsTitolo.richiedeData);
                _this.toggle(_this._txtRilasciatoDa, flagsTitolo.richiedeRilasciatoDa);
                _this.mostraCampo(_this._txtNote);
            });
        };
        GestioneEndoPresenti.prototype.toggle = function (campo, toggle) {
            if (toggle) {
                this.mostraCampo(campo);
            }
            else {
                this.nascondiCampo(campo);
            }
        };
        GestioneEndoPresenti.prototype.mostraCampo = function (campo) {
            campo.show();
        };
        GestioneEndoPresenti.prototype.nascondiCampo = function (campo) {
            campo.find('input').val('');
            campo.hide();
        };
        GestioneEndoPresenti.prototype.onCheckModificato = function () {
            var checked = this._chkPresente.is(':checked');
            this._boxEstremiAtto.toggle(checked);
        };
        GestioneEndoPresenti.prototype.callTitoliService = function (idTipoTitolo) {
            var parameters = {
                token: this._token,
                idTipoTitolo: idTipoTitolo
            };
            var deferred = jQuery.Deferred();
            $.ajax({
                url: this._webServiceUrl,
                dataType: 'json',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(parameters)
            }).then(function (data) {
                deferred.resolve(data.d);
            }, function (jqXHR, textStatus, errorThrown) {
                console.log("Request failed: " + textStatus);
                deferred.reject(errorThrown);
            });
            return deferred.promise();
        };
        return GestioneEndoPresenti;
    }());
    $.fn['gestioneEndoPresenti'] = function (options) {
        return this.each(function () {
            if (!$.data(this, "plugin_gestioneEndoPresenti")) {
                $.data(this, "plugin_gestioneEndoPresenti", new GestioneEndoPresenti(options.webServiceUrl, options.token, $(this)));
            }
        });
    };
});
//# sourceMappingURL=gestione-endo-presenti.js.map