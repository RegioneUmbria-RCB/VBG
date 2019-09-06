/// <reference path="../../scripts/typings/jquery/jquery.d.ts" />
//import $ = require('jquery');

interface ITitoliServiceResponse {
    idTipoTitolo: number,
    messaggio: string,
    richiedeData: boolean,
    richiedeNumero: boolean,
    richiedeRilasciatoDa: boolean
}

class GestioneEndoPresenti {

    _chkPresente: JQuery;
    _ddlTipoTitolo: JQuery;
    _txtNumero: JQuery;
    _txtData: JQuery;
    _txtRilasciatoDa: JQuery;
    _txtNote: JQuery;
    _txtMessaggio: JQuery;
    _boxEstremiAtto: JQuery;

    constructor(private _webServiceUrl: string, private _token: string, private _jqRoot: JQuery) {
        this._chkPresente = this._jqRoot.find('.chk-presente input[type=checkbox]');
        this._ddlTipoTitolo = this._jqRoot.find('.ddl-tipo-titolo select');
        this._txtNumero = this._jqRoot.find('.txt-numero');
        this._txtData = this._jqRoot.find('.txt-data');
        this._txtRilasciatoDa = this._jqRoot.find('.txt-rilasciato-da');
        this._txtNote = this._jqRoot.find('.txt-note');
        this._txtMessaggio = this._jqRoot.find('.info-estremi-atto');
        this._boxEstremiAtto = this._jqRoot.find('.estremi-atto');

        this._chkPresente.on('change', (e) => {
            this.onCheckModificato();
        });

        this._ddlTipoTitolo.on('change', (e) => {
            this.onTipoTitoloModificato();
        });

        this._chkPresente.trigger('change');
        this._ddlTipoTitolo.trigger('change');
    }

    private onTipoTitoloModificato() {

        let tipoTitolo = this._ddlTipoTitolo.val();

        if (tipoTitolo == '' || tipoTitolo == '-1') {

            this._txtMessaggio.html('');

            this.nascondiCampo(this._txtNumero);
            this.nascondiCampo(this._txtData);
            this.nascondiCampo(this._txtRilasciatoDa);
            this.nascondiCampo(this._txtNote);

            return;
        }

        this.callTitoliService(tipoTitolo)
            .then((flagsTitolo) => {
                this._txtMessaggio.html(flagsTitolo.messaggio);

                this.toggle(this._txtNumero, flagsTitolo.richiedeNumero);
                this.toggle(this._txtData, flagsTitolo.richiedeData);
                this.toggle(this._txtRilasciatoDa, flagsTitolo.richiedeRilasciatoDa);

                this.mostraCampo(this._txtNote);
            });
    }

    private toggle(campo: JQuery, toggle: boolean) {
        if (toggle) {
            this.mostraCampo(campo);
        } else {
            this.nascondiCampo(campo);
        }
    }

    private mostraCampo(campo: JQuery) {
        campo.show();
    }

    private nascondiCampo(campo: JQuery) {
        campo.find('input').val('');
        campo.hide();
    }

    private onCheckModificato() {
        let checked = this._chkPresente.is(':checked');

        this._boxEstremiAtto.toggle(checked);
    }

    private callTitoliService(idTipoTitolo: number): JQueryPromise<ITitoliServiceResponse> {

        var parameters = {
            token: this._token,
            idTipoTitolo: idTipoTitolo
        };

        var deferred = jQuery.Deferred<ITitoliServiceResponse>();

        $.ajax({
            url: this._webServiceUrl,
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(parameters)
        }).then(
            (data) => {
                deferred.resolve(data.d as ITitoliServiceResponse);
            },
            (jqXHR: JQueryXHR, textStatus: string, errorThrown: any) => {
                console.log("Request failed: " + textStatus);
                deferred.reject(errorThrown);
            }
            );

        return deferred.promise();

    }
}

interface IGestioneEndoPresentiOptions {
    webServiceUrl: string,
    token: string
}

$.fn['gestioneEndoPresenti'] = function (options: IGestioneEndoPresentiOptions) {
    return this.each(function () {
        if (!$.data(this, "plugin_gestioneEndoPresenti")) {
            $.data(this, "plugin_gestioneEndoPresenti", new GestioneEndoPresenti(options.webServiceUrl, options.token, $(this)));
        }
    });
};