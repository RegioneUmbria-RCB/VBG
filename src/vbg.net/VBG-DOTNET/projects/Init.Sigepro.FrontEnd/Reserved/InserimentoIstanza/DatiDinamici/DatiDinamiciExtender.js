// DatiDinamiciExtender.js

/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue: true */
/*jslint unparam: true */
/*global $, jQuery, alert, Sys, SimpleGetSetValueExtender,SearchGetSetValueExtender,DropDownGetSetValueExtender,MultiSelectGetSetValueExtender, CheckBoxGetSetValueExtender, DateTextBoxGetSetValueExtender,ButtonGetSetValueExtender,UploadGetSetValueExtender,IntGetSetValueExtender, confirm, D2PannelloErrori, D2FocusManager, AjaxCallManager*/

class AjaxCallManager2 {

    constructor(endpointUrl) {
        this.endpointUrl = endpointUrl;
        this.pendingRequests = [];
    }

    get haChiamatePendenti() { return this.pendingRequests.length > 0; }

    async post(path, data) {
        const url = `${this.endpointUrl}/${path}`;

        this.pendingRequests.push(url);

        console.log(`Inizio chiamata a ${url}`);

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify(data || {})
        });

        const lastUrl = this.pendingRequests.pop();

        console.log(`Chiamata a ${lastUrl} terminata, ${this.pendingRequests.length} chiamate pendenti`);

        if (!response.ok) {
            console.error(response);
            const msg = `Richiesta a ${url} fallita con l'errore ${response.statusText} text=${(await response.text()).value}`;
            console.error(msg);

            throw msg;
        }

        return response.json();
    }
}

function DatiDinamiciExtender(idPannelloErrori, bottoniSalvataggio, bottoniConConferma, d2SearchOptions) {
    //'use strict';

    console.log('DatiDinamiciExtender inizializzato:');
    console.log('- idPannelloErrori:', idPannelloErrori);
    console.log('- bottoniSalvataggio:', bottoniSalvataggio);
    console.log('- bottoniConConferma:', bottoniConConferma);
    console.log('- d2SearchOptions:', d2SearchOptions);


    this.registroControlli = {};
    this.registroBottoni = {};
    this.alertCollegatoABottoni = false;
    this.servicePath = 'Helper/DatiDinamiciScriptService.asmx';
    this.pannelloErrori = new D2PannelloErrori(idPannelloErrori);
    this.ajaxCallManager2 = new AjaxCallManager2(this.servicePath);
    this.focusManager = new D2FocusManager();
    this.bottoneSalvataggio = typeof (bottoniSalvataggio) === 'string' ? $(bottoniSalvataggio) : bottoniSalvataggio;
    this.registroBottoni = bottoniConConferma;
    this.modificheInSospeso = false;

    this.collegaAlertABottoni = function () {
        this.modificheInSospeso = true;
    };

    this.rimuoviAlertBottoni = function () {
        this.modificheInSospeso = false;
    };

    this.aggiornaStatoModello = async (e) => {

        var ignoraFlagModificaModello = e.ignoraFlagModificaModello || false;

        const res = await this.ajaxCallManager2.post('GetStatoModello')

        this.printDebugMessages(res.d.messaggiDebug);
        this.verificaModificheCompleted(res.d.modifiche, ignoraFlagModificaModello);
        this.visualizzaCampi(res.d.campiVisibili);
        this.nascondiCampi(res.d.campiNonVisibili);
        this.visualizzaGruppi(res.d.gruppiVisibili);
        this.nascondiGruppi(res.d.gruppiNonVisibili);
        this.mostraErrori(res.d.errori);
        this.ricaricaInterfaccia(res.d.necessitaReloadInterfaccia);
    };

    function d2AggiornaMessaggiErrore() {

        $('.gestione-errori').hide();

        $(".d2-touched.d2Errori:not(:hidden)").each(function () {
            var el = $(this),
                campoErrore = el.closest(".d2-control-cell").find(".gestione-errori");

            campoErrore.text(el.attr("title"));
            campoErrore.show();
        });
    }

    this.init = function () {

        const self = this;

        // Casi particolari: d2Search e d2Upload hanno bisogno di un oggetto jquery
        // d2search va inizializzato prima dei getter/setter
        document.querySelectorAll('.d2Search')
            .forEach(element => $(element).searchDatiDinamici(d2SearchOptions));

        const controlsMap = [
            { selector: '.d2DoubleTextBox', assign: DoubleGetSetValueExtender },
            { selector: '.d2IntTextBox', assign: IntGetSetValueExtender },
            { selector: '.d2TextBox', assign: SimpleGetSetValueExtender },
            { selector: '.d2Search', assign: SearchGetSetValueExtender },
            { selector: '.d2ListBox', assign: DropDownGetSetValueExtender },
            { selector: '.d2SigeproListBox', assign: DropDownGetSetValueExtender },
            { selector: '.d2MultiListBox', assign: MultiSelectGetSetValueExtender },
            { selector: '.d2CheckBox', assign: CheckBoxGetSetValueExtender },
            { selector: '.d2DateTextBox', assign: DateTextBoxGetSetValueExtender },
            { selector: '.d2Button', assign: ButtonGetSetValueExtender },
            { selector: '.d2Upload', assign: UploadGetSetValueExtender },
            { selector: '.d2-hidden', assign: SimpleGetSetValueExtender },
            { selector: '.d2-read-only-text', assign: HtmlControlGetSetValueExtender }
        ];

        controlsMap.forEach((item) => {
            document.querySelectorAll(item.selector)
                .forEach(element => item.assign(element));
        });

        // d2Upload va inizializzato dopo i getter/setter
        document.querySelectorAll('.d2Upload')
            .forEach(element => $(element).uploadDatiDinamici(d2SearchOptions));




        // $('.d2DoubleTextBox').each(function (idx) { var it = new IntGetSetValueExtender(this); });
        // $('.d2IntTextBox').each(function (idx) { var it = new IntGetSetValueExtender(this); });
        // $('.d2TextBox').each(function (idx) { var it = new SimpleGetSetValueExtender(this); });
        // $('.d2Search').each(function (idx) { $(this).searchDatiDinamici(d2SearchOptions); var it = new SearchGetSetValueExtender(this); });
        // $('.d2ListBox').each(function (idx) { var it = new DropDownGetSetValueExtender(this); });
        // $('.d2SigeproListBox').each(function (idx) { var it = new DropDownGetSetValueExtender(this); });
        // $('.d2MultiListBox').each(function (idx) { var it = new MultiSelectGetSetValueExtender(this); });
        // $('.d2CheckBox').each(function (idx) { var it = new CheckBoxGetSetValueExtender(this); });
        // $('.d2DateTextBox').each(function (idx) { var it = new DateTextBoxGetSetValueExtender(this); });
        // $('.d2Button').each(function (idx) { var it = new ButtonGetSetValueExtender(this); });
        // $('.d2Upload').each(function (idx) { var it = new UploadGetSetValueExtender(this); $(this).uploadDatiDinamici(); });
        // $('.d2-hidden').each(function (idx) { var it = new SimpleGetSetValueExtender(this); });
        // $('.d2-read-only-text').each(function (idx) { var it = new HtmlControlGetSetValueExtender(this); });

        document.querySelectorAll('.d2Control').forEach(element => {
            const ctrl = $(element);
            const idCampoDinamico = element.dataset.d2id;
            const indiceMolteplicita = element.dataset.d2indice;
            const nomeEventoModifica = element.dataset.eventoModifica;

            ctrl.on("focusout", () => {
                ctrl.addClass("d2-touched");
            });
            /*
            ctrl.tooltip({
                position: {
                    my: "left+5 center",
                    at: "right center"
                },
                tooltipClass: 'tooltipErrore tooltip-errore'
            });
            */
            ctrl.bind('errorSet', (event, msg) => {
                ctrl.closest('.form-group')
                    .addClass('d2-error')
                    .find('.error-feedback')
                    .text(msg);

                ctrl.addClass('d2Errori');
                // ctrl.tooltip('disable');
                ctrl.attr('title', msg);
                // ctrl.tooltip('enable');
            });

            ctrl.bind('errorRemoved', () => {
                ctrl.closest('.form-group')
                    .removeClass('d2-error')
                    .find('.error-feedback')
                    .text('');

                ctrl.attr('title', '');
                // ctrl.tooltip('disable');
                ctrl.removeClass('d2Errori');
            });

            if (!this.registroControlli[idCampoDinamico]) {
                this.registroControlli[idCampoDinamico] = {};
            }

            this.registroControlli[idCampoDinamico][indiceMolteplicita] = element;

            //console.log("registroControlli campo: " + idCampoDinamico + ", indice: " + indiceMolteplicita + " inizializzato");

            if (nomeEventoModifica) {
                this.registraHandlerModifica(ctrl, nomeEventoModifica);
            }
        });

        $('.d2Search').each(function (idx) {
            $(this).searchDatiDinamici('inizializza');
        });

        $('.d2CheckBox, .d2DoubleTextBox, .d2IntTextBox').each(function (index) {
            var ctrl = $(this),
                nomeEventoModifica = ctrl.data('eventoModifica');

            ctrl.trigger(nomeEventoModifica, [{ ignoraFlagModificaModello: true }]);
        });

        $('.d2Control').trigger('campoInizializzato');

        $("<div class='gestione-errori' style='display:none'></div>").appendTo('.d2-control-cell');

        this.bottoneSalvataggio.click((e) => {
            var campiConErrore = $(".d2Errori:not(:hidden)");

            $('.d2Control:not(:hidden)').addClass("d2-touched");

            if (campiConErrore.length > 0) {

                d2AggiornaMessaggiErrore();

                $(".d2Errori:not(:hidden)").first().focus();
                e.preventDefault();
                return;
            }

            $('.bottoni input, .bottoni-scheda input').css('display', 'none');
            $('#salvataggioInCorso').fadeIn();

            
            if (this.ajaxCallManager2.haChiamatePendenti) {

                var cmdSalva = $(this.bottoneSalvataggio);
                setTimeout(function () { cmdSalva.click(); }, 100);

                console.log('Salva non invocato perché ci sono chiamate pendenti');

                e.preventDefault();
                return false;
            }
            

        });

        this.aggiornaStatoModello({ ignoraFlagModificaModello: true });

        if (this.registroBottoni) {
            this.registroBottoni.each(function () {
                $(this).click(function (e) {

                    if (!self.modificheInSospeso) {
                        return true;
                    }

                    return confirm('Attenzione, i valori modificati non sono stati salvati.\nProseguire perdendo tutte le modifiche effettuate?');
                });
            });
        }

        this.modificheInSospeso = false;
    };



    this.registraHandlerModifica = (jqCtrl, nomeEventoModifica) => {

        if (!jqCtrl) {
            return;
        }

        jqCtrl.on(nomeEventoModifica, async (event, e) => {

            console.log('campodinamico', nomeEventoModifica, event);

            const campo = jqCtrl[0];
            const $campo = $(campo);

            let ignoraFlagModificaModello = e?.ignoraFlagModificaModello || false;

            this.focusManager.saveFocus();

            const val = campo.GetValue();

            const res = await this.ajaxCallManager2.post("CampoModificato", {
                idCampo: campo.dataset.d2id,
                indice: campo.dataset.d2indice,
                valore: val.valore,
                valoreDecodificato: val.valoreDecodificato
            });

            this.printDebugMessages(res.d.messaggiDebug);
            this.verificaModificheCompleted(res.d.modifiche, ignoraFlagModificaModello);

            await this.visualizzaCampi(res.d.campiVisibili);
            await this.nascondiCampi(res.d.campiNonVisibili);
            await this.visualizzaGruppi(res.d.gruppiVisibili);
            await this.nascondiGruppi(res.d.gruppiNonVisibili);
            await this.ricaricaInterfaccia(res.d.necessitaReloadInterfaccia);

            this.mostraErrori(res.d.errori);

            if ($campo.data("effettuaPostback") === 1) {
                __doPostBack(campo.id);
            }

            if (nomeEventoModifica === 'click' && campo.tagName.toLowerCase() !== "span") {
                return false;
            }

            jqCtrl.trigger('campoModificato', [val]);
        });
    };

    this.printDebugMessages = function (messages) {

        var i = 0;

        if (!messages || !messages.length) {
            return;
        }

        for (i = 0; i < messages.length; i++) {
            console.log("[D2DEBUG]" + messages[i]);
        }
    };

    this.verificaModificheCompleted = (result, ignoraFlagModificaModello) => {

        if (!result || result.length === 0) {
            return;
        }

        if (result.length && !ignoraFlagModificaModello) {
            this.modificheInSospeso = true;
        }

        for (let i = 0; i < result.length; i++) {
            const rifCampo = result[i];
            const el = this.registroControlli[rifCampo.id][rifCampo.indice];

            if (el) {

                const oldValue = el.GetValue();

                el.SetValue(rifCampo.valore);

                if (oldValue.valore !== rifCampo.valore) {

                    if (el.dataset.notificaValoreDecodificato === 'True') {
                        this.ajaxCallManager2.post('ValoreDecodificaModificato', {
                            idCampo: rifCampo.id,
                            indice: rifCampo.indice,
                            valoreDecodifica: el.GetValue().valoreDecodificato
                        });
                    }

                    if (el.dataset.inizializzaSuModificaSw === 'true') {
                        var logic = el.dataset.d2Logic;

                        if (logic.inizializza) {
                            logic.inizializza();
                        }
                    }
                }
            }
        }
    };


    //
    //  Gestione della visualizzazione degli errori
    //    
    this.evidenziaCampiConErrori = function (errori) {

        $('.d2Errori').trigger('errorRemoved');

        d2AggiornaMessaggiErrore();

        if (!errori) {
            return;
        }

        for (let i = 0; i < errori.length; i++) {
            const errore = errori[i];

            if (errore.id < 0) {
                continue;
            }

            $(this.registroControlli[errore.id][errore.indice]).trigger('errorSet', errore.msg);
        }

        d2AggiornaMessaggiErrore();
    };

    this.mostraErrori = (errori) => {

        this.evidenziaCampiConErrori(errori);

        if (!errori?.length) {
            return;
        }

        //messaggi.push('Uno o pi&ugrave; campi contengono errori di compilazione, verificare i dati immessi nei campi evidenziati (posizionare il mouse sopra un campo per i dettagli dell\'errore)');

        const messaggi = errori.filter(err => err.id < 0)
            .map(err => err.msg);

        this.pannelloErrori
            .mostraErrori(messaggi);
    };

    //
    //  Gestione del salvataggio del modello
    //    
    this.sincronizzaValoriCampi = async () => {

        const res = await this.ajaxCallManager2.post("GetValoriCampi");

        this.printDebugMessages(res.d.messaggiDebug);
        this.verificaModificheCompleted(res);
    };

    this.salvaModelloCompleted = function (result) {

        if (!result.modelloSalvato) {

            alert("Impossibile effettuare il salvataggio del modello corrente in quanto sono stati riscontrati errori.");
            this.printDebugMessages(result.messaggiDebug);
            this.mostraErrori(result.errori);
            this.visualizzaCampi(result.campiVisibili);
            this.nascondiCampi(result.campiNonVisibili);
            this.visualizzaGruppi(result.gruppiVisibili);
            this.nascondiGruppi(result.gruppiNonVisibili);
            this.verificaModificheCompleted(result.modifiche);

            return;
        }

        this.sincronizzaValoriCampi();

        alert("Salvataggio effettuato correttamente");

        this.rimuoviAlertBottoni();
    };



    this.SalvaModello = async () => {

        const res = await this.ajaxCallManager2.post("SalvaModello");

        this.salvaModelloCompleted(res.d);
    };






    //
    //  Gestione della visualizzazione dei campi/gruppi
    //

    this.visualizzaCampi = (campi) => {

        return new Promise((resolve, reject) => {

            if (campi && campi.length) {
                for (let i = 0; i < campi.length; i++) {
                    const campo = campi[i];
                    $('.c' + campo.id + '_' + campo.indice + ' > *:not(.descrizioneCampoDinamico)').show('fast');
                }
            }

            resolve('ok')
        });
    };

    this.nascondiCampi = (campi) => new Promise((resolve, reject) => {

        if (!campi || campi.length === 0) {
            resolve('ok');
            return;
        }

        for (let i = 0; i < campi.length; i++) {
            const c = campi[i];
            $(`.c${c.id}_${c.indice} > *`).hide();
            $(`.c${c.id}_${c.indice} .d2Control`).each((index, item) => {

                if (item.SetValue) {    // potrebbe essere richiamato su un campo statico
                    item.SetValue('');
                }
            });
        }

        resolve('ok');
    });

    this.ricaricaInterfaccia = function (ricarica) {

        return new Promise((resolve, reject) => {
            if (ricarica && __doPostBack) {
                __doPostBack();
            }

            resolve('ok');
        });
    }

    this.visualizzaGruppi = function (gruppi) {

        return new Promise((resolve, reject) => {

            if (gruppi && gruppi.length) {
                for (let i = 0; i < gruppi.length; i++) {
                    $("*[data-d2-group='" + gruppi[i] + "'").show('fast');
                }
            }

            resolve('ok');
        });
    };

    this.nascondiGruppi = function (gruppi) {

        return new Promise((resolve, reject) => {

            if (gruppi && gruppi.length) {
                for (let i = 0; i < gruppi.length; i++) {
                    $("*[data-d2-group='" + gruppi[i] + "'").hide();
                }
            }

            resolve('ok');

        });
    };




    this.init();
}


(function ($) {
    'use strict';

    var methods = {
        prepara: function (customOptions) {
            return this.each(function () {
                var options = $.extend($.fn.DatiDinamiciExtender.defaultOptions, customOptions),
                    $this = $(this),
                    data = $this.data('__DatiDinamiciExtender'),
                    datiDinamiciExtender;

                if (!data) {
                    datiDinamiciExtender = new DatiDinamiciExtender(
                        options.pannelloErrori,
                        options.bottoniSalvataggio,
                        options.bottoniConferma,
                        options.d2SearchOptions
                    );

                    $this.data('__DatiDinamiciExtender', datiDinamiciExtender);
                }
            });
        }
    };



    $.fn.DatiDinamiciExtender = function (method) {

        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        }

        if (typeof method === 'object' || !method) {
            return methods.prepara.apply(this, arguments);
        }

        $.error('Method ' + method + ' does not exist on jQuery.searchDatiDinamici');
    };


    $.fn.DatiDinamiciExtender.defaultOptions = {};


})(jQuery);

