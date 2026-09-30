interface ITitoliServiceResponse {
    idTipoTitolo: number,
    messaggio: string,
    richiedeData: boolean,
    richiedeNumero: boolean,
    richiedeRilasciatoDa: boolean
}

class GestioneEndoPresenti {

    _chkPresente: JQuery<Element>;
    _ddlTipoTitolo: JQuery<Element>;
    _txtNumero: JQuery<Element>;
    _txtData: JQuery<Element>;
    _txtRilasciatoDa: JQuery<Element>;
    _txtNote: JQuery<Element>;
    _txtMessaggio: JQuery<Element>;
    _boxEstremiAtto: JQuery<Element>;

    constructor(private readonly _webServiceUrl: string, private readonly _token: string, private readonly _jqRoot: JQuery) {
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

        this.callTitoliService(<number>tipoTitolo)
            .then((flagsTitolo) => {
                this._txtMessaggio.html(flagsTitolo.messaggio);

                this.toggle(this._txtNumero, flagsTitolo.richiedeNumero);
                this.toggle(this._txtData, flagsTitolo.richiedeData);
                this.toggle(this._txtRilasciatoDa, flagsTitolo.richiedeRilasciatoDa);

                this.mostraCampo(this._txtNote);
            });
    }

    private toggle(campo: JQuery<Element>, toggle: boolean) {
        if (toggle) {
            this.mostraCampo(campo);
        } else {
            this.nascondiCampo(campo);
        }
    }

    private mostraCampo(campo: JQuery<Element>) {
        campo.show();
    }

    private nascondiCampo(campo: JQuery<Element>) {
        campo.find('input').val('');
        campo.hide();
    }

    private onCheckModificato() {
        let checked = this._chkPresente.is(':checked');

        this._boxEstremiAtto.toggle(checked);
    }

    private callTitoliService(idTipoTitolo: number): JQueryPromise<ITitoliServiceResponse> {

        const parameters = {
            token: this._token,
            idTipoTitolo: idTipoTitolo
        };

        const deferred = jQuery.Deferred<ITitoliServiceResponse>();

        $.ajax({
            url: this._webServiceUrl,
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(parameters)
        }).then(
            (data: any) => {
                deferred.resolve(data.d as ITitoliServiceResponse);
            },
            (_: JQueryXHR, textStatus: string, errorThrown: any) => {
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

interface JQuery {
    gestioneEndoPresenti(options: IGestioneEndoPresentiOptions): JQuery;
}

$.fn.gestioneEndoPresenti = function (options: IGestioneEndoPresentiOptions) {
    return this.each(function () {
        if (!$.data(this, "plugin_gestioneEndoPresenti")) {
            $.data(this, "plugin_gestioneEndoPresenti", new GestioneEndoPresenti(options.webServiceUrl, options.token, $(this)));
        }
    });
};