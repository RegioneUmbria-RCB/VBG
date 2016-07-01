/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue: true */
/*global $, jQuery, alert, Sys, SimpleGetSetValueExtender,SearchGetSetValueExtender,DropDownGetSetValueExtender,MultiSelectGetSetValueExtender, CheckBoxGetSetValueExtender, DateTextBoxGetSetValueExtender,ButtonGetSetValueExtender,UploadGetSetValueExtender,IntGetSetValueExtender, confirm, D2PannelloErrori, D2FocusManager, AjaxCallManager*/
function DebugConsole()
{
    this.console = window.console || { log: function () { } };

    this.debug = function (msg) {
        this.console.debug(msg);
    };
}


function DatiDinamiciExtender(idPannelloErrori, idBottoneSalvataggio, registroBottoni) {
    'use strict';

    var log = window.console || { log: function () { } };

    this.console = new DebugConsole();
    this.registroControlli = {};
    this.registroBottoni = {};
    this.alertCollegatoABottoni = false;
    this.servicePath = 'Helper/DatiDinamiciScriptService.asmx';
    this.pannelloErrori = new D2PannelloErrori(idPannelloErrori);
    this.ajaxCallManager = new AjaxCallManager(this.servicePath);
    this.focusManager = new D2FocusManager();
    this.bottoneSalvataggio = $('#' + idBottoneSalvataggio);
    this.registroBottoni = registroBottoni;
    this.modificheInSospeso = false;

    this.collegaAlertABottoni = function () {
        this.modificheInSospeso = true;
    };

    this.rimuoviAlertBottoni = function () {
        this.modificheInSospeso = false;
    };

    this.aggiornaStatoModello = function (e) {

        var self = this,
            ignoraFlagModificaModello = e.ignoraFlagModificaModello || false;

        this.ajaxCallManager
            .createCall('GetStatoModello')
            .withSuccessCallback(function (res) {
                self.printDebugMessages(res.d.messaggiDebug);
                self.verificaModificheCompleted(res.d.modifiche, ignoraFlagModificaModello);
                self.mostraErrori(res.d.errori);
                self.visualizzaCampi(res.d.campiVisibili);
                self.nascondiCampi(res.d.campiNonVisibili);
                self.visualizzaGruppi(res.d.gruppiVisibili);
                self.nascondiGruppi(res.d.gruppiNonVisibili);
            })
            .execute();
    };

    this.init = function () {

        var self = this,
            numeroChiamate = 0;

        $('.d2DoubleTextBox').each(function (idx) { var it = new IntGetSetValueExtender(this); });
        $('.d2IntTextBox').each(function (idx) { var it = new IntGetSetValueExtender(this); });
        $('.d2TextBox').each(function (idx) { var it = new SimpleGetSetValueExtender(this); });
        $('.d2Search').each(function (idx) { $(this).searchDatiDinamici(); var it = new SearchGetSetValueExtender(this); });
        $('.d2ListBox').each(function (idx) { var it = new DropDownGetSetValueExtender(this); });
        $('.d2SigeproListBox').each(function (idx) { var it = new DropDownGetSetValueExtender(this); });
        $('.d2MultiListBox').each(function (idx) { var it = new MultiSelectGetSetValueExtender(this); });
        $('.d2CheckBox').each(function (idx) { var it = new CheckBoxGetSetValueExtender(this); });
        $('.d2DateTextBox').each(function (idx) { var it = new DateTextBoxGetSetValueExtender(this); });
        $('.d2Button').each(function (idx) { var it = new ButtonGetSetValueExtender(this); });
        $('.d2Upload').each(function (idx) { var it = new UploadGetSetValueExtender(this); $(this).uploadDatiDinamici(); });

        $('.d2Control').each(function (index) {
            var ctrl = $(this),
                idCampoDinamico = ctrl.data('d2id'),
                tipoCampo = ctrl.data('d2tipo'),
                indiceMolteplicita = ctrl.data('d2indice'),
                nomeEventoModifica = ctrl.data('eventoModifica');

            ctrl.tooltip({
                position: {
                    my: "left+5 center",
                    at: "right center",
                },
                tooltipClass: 'tooltipErrore'
            });

            ctrl.bind('errorSet', function (event, msg) {
                ctrl.addClass('d2Errori');
                ctrl.attr('title', msg);
                ctrl.tooltip('enable');
            });

            ctrl.bind('errorRemoved', function () {
                ctrl.attr('title', '');
                ctrl.tooltip('disable');
                ctrl.removeClass('d2Errori');
            });

            if (!self.registroControlli[idCampoDinamico]) {
                self.registroControlli[idCampoDinamico] = {};
            }

            self.registroControlli[idCampoDinamico][indiceMolteplicita] = this;

            if (nomeEventoModifica) {
                self.registraHandlerModifica(ctrl, nomeEventoModifica);
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

        this.bottoneSalvataggio.click(function (e) {

            $('.bottoni input').css('display', 'none');
            $('#salvataggioInCorso').fadeIn();

            if (this.ajaxCallManager && this.ajaxCallManager.getPendingCallsCount() > 0 && numeroChiamate < 20) {

                var cmdSalva = $(this);
                setTimeout(function () { cmdSalva.click(); }, 100);

                numeroChiamate++;

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



    this.registraHandlerModifica = function (jqCtrl, nomeEventoModifica) {

        var self = this;

        if (!jqCtrl) {
            return;
        }

        jqCtrl.on(nomeEventoModifica, function onCampoModificato(event, e) {

            var val,
                params,
                serviceMethod = "CampoModificato",
                ignoraFlagModificaModello = false;

            if (e) {
                ignoraFlagModificaModello = e.ignoraFlagModificaModello || false;
            }

            self.focusManager.saveFocus();

            val = this.GetValue();

            params = {
                idCampo: $(this).data('d2id'),
                indice: $(this).data('d2indice'),
                valore: val.valore,
                valoreDecodificato: val.valoreDecodificato
            };

            self.ajaxCallManager
                .createCall(serviceMethod)
                .withArguments(params)
                .withSuccessCallback(function (res) {
                    self.printDebugMessages(res.d.messaggiDebug);
                    self.verificaModificheCompleted(res.d.modifiche, ignoraFlagModificaModello);
                    self.mostraErrori(res.d.errori);
                    self.visualizzaCampi(res.d.campiVisibili);
                    self.nascondiCampi(res.d.campiNonVisibili);
                    self.visualizzaGruppi(res.d.gruppiVisibili);
                    self.nascondiGruppi(res.d.gruppiNonVisibili);
                })
                .execute();

            if (nomeEventoModifica === 'click' && this.tagName.toLowerCase() !== "span") {
                return false;
            }

            jqCtrl.trigger('campoModificato', [val]);
        });
    };

    this.printDebugMessages = function (messages) {

        if (!messages || !messages.length) {
            return;
        }

        for (var i = 0; i < messages.length; i++) {
            this.console.debug(messages[i]);
        }

    }

    this.verificaModificheCompleted = function (result, ignoraFlagModificaModello) {

        var i,
            rifCampo,
            el,
            params;

        if (!result || result.length === 0) {
            return;
        }

        if (result.length && !ignoraFlagModificaModello) {
            this.modificheInSospeso = true;
        }

        for (i = 0; i < result.length; i++) {
            rifCampo = result[i];

            el = this.registroControlli[rifCampo.id][rifCampo.indice];

            if (el) {
                el.SetValue(rifCampo.valore);

                if ($(el).data('notificaValoreDecodificato') === 'True') {

                    params = {
                        idCampo: rifCampo.id,
                        indice: rifCampo.indice,
                        valoreDecodifica: el.GetValue().valoreDecodificato
                    };

                    this.ajaxCallManager
                        .createCall('ValoreDecodificaModificato')
                        .withArguments(params)
                        .execute();
                }
            }
        }
    };


    //
    //  Gestione della visualizzazione degli errori
    //    

    this.evidenziaCampiConErrori = function (errori) {

        var i = 0,
            errore;

        $('.d2Errori').trigger('errorRemoved');

        if (!errori) {
            return;
        }

        for (i = 0; i < errori.length; i++) {
            errore = errori[i];

            if (errore.id < 0) {
                continue;
            }

            $(this.registroControlli[errore.id][errore.indice]).trigger('errorSet', errore.msg);
        }
    };

    this.mostraErrori = function (errori) {

        var i,
            messaggi = [];

        this.evidenziaCampiConErrori(errori);

        if (!errori) {
            return;
        }

        if (errori.length) {
            messaggi.push('Uno o pi&ugrave; campi contengono errori di compilazione, verificare i dati immessi nei campi evidenziati (posizionare il mouse sopra un campo per i dettagli dell\'errore)');
        }

        for (i = 0; i < errori.length; i++) {

            if (errori[i].id >= 0) {
                continue;
            }

            messaggi.push(errori[i].msg);
        }

        this.pannelloErrori
            .mostraErrori(messaggi);
    };





    //
    //  Gestione del salvataggio del modello
    //    

    this.sincronizzaValoriCampi = function () {

        var self = this,
            serviceMethod = "GetValoriCampi";

        this.ajaxCallManager
            .createCall(serviceMethod)
			.withArguments(null)
			.withSuccessCallback(function (res) {
			    self.printDebugMessages(res.d.messaggiDebug);
			    self.verificaModificheCompleted(res);

			})
			.execute();
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



    this.SalvaModello = function () {
        var self = this,
            serviceMethod = "SalvaModello";

        this.ajaxCallManager
            .createCall(serviceMethod)
            .withArguments(null)
            .withSuccessCallback(function (res) {
                self.salvaModelloCompleted(res.d);
            })
            .execute();
    };






    //
    //  Gestione della visualizzazione dei campi/gruppi
    //

    this.visualizzaCampi = function (campi) {

        var i,
            c;

        if (campi && campi.length) {
            for (i = 0; i < campi.length; i++) {
                c = campi[i];
                $('.c' + c.id + '_' + c.indice + ' > *:not(.descrizioneCampoDinamico)').show('fast');
            }
        }
    };

    this.nascondiCampi = function (campi) {

        var i,
            c;

        if (campi && campi.length) {
            for (i = 0; i < campi.length; i++) {
                c = campi[i];
                $('.c' + c.id + '_' + c.indice + ' > *').hide();
                $('.c' + c.id + '_' + c.indice + ' .d2Control').resetValues();
            }
        }
    };

    this.visualizzaGruppi = function (gruppi) {

        var i;

        if (gruppi && gruppi.length) {
            for (i = 0; i < gruppi.length; i++) {
                $("*[data-d2-group='" + gruppi[i] + "'").show('fast');
            }
        }
    };

    this.nascondiGruppi = function (gruppi) {

        var i;

        if (gruppi && gruppi.length) {
            for (i = 0; i < gruppi.length; i++) {
                $("*[data-d2-group='" + gruppi[i] + "'").hide();
            }
        }
    };




    this.init();
}

