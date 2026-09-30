define(['jquery', 'json', 'jquery.ui'], function ($,JSON) {

    var RicercaStradarioServerInterface = function (serviceUrl, idComune, software, token) {
        this._serviceUrl = serviceUrl;
        this._idcomune = idComune;
        this._software = software;
        this._token = token;


        this.valida = function (validazioneCommand) {
            //console.log(validazioneCommand);

            var self = this;

            $.ajax({
                url: self._serviceUrl + "/ValidaCampo?idcomune=" + self._idcomune + "&software=" + self._software ,
                type: "POST",
                async: false,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    nomeCampo: validazioneCommand.campo,
                    codiceStradario: validazioneCommand.stradario,
                    civico: validazioneCommand.civico,
                    esponente: validazioneCommand.esponente,
                    circoscrizione: validazioneCommand.circoscrizione,
                    cap: validazioneCommand.cap
                }),
                success: function (data) {
                    if (data.d.error)
                        validazioneCommand.onFailure(data.d.errorDescription, data.campo);
                    else
                        validazioneCommand.onSuccess(data.d);
                }
            });
        }

        this.getListaCampi = function (getListaCampiCommand) {
            var self = this;

            $.ajax({
                url: self._serviceUrl + "/GetListaCampi?idcomune=" + self._idcomune + "&software=" + self._software,
                type: "POST",
                async: false,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    nomeCampo: getListaCampiCommand.campo,
                    codiceStradario: getListaCampiCommand.stradario,
                    civico: getListaCampiCommand.civico,
                    esponente: getListaCampiCommand.esponente,
                    circoscrizione: getListaCampiCommand.circoscrizione,
                    cap: getListaCampiCommand.cap
                }),
                success: function (data) {
                    if (data.d.error)
                        getListaCampiCommand.onFailure(data.d.errorDescription, data.campo);
                    else
                        getListaCampiCommand.onSuccess(data.d);
                }
            });
        }
    }



    var ErrorTooltipFactory = function () {

        this.appendTo = function (control) {

            control.tooltip = new function () {
                this._div = null;
                this._control = control;

                this.mostra = function (text, cssClass) {
                    this.assicuraEsistenzaTooltip();
                    this._div.html(text);

                    var ctrlOffset = this._control.position();
                    var newTop = ctrlOffset.top;
                    var newLeft = ctrlOffset.left + this._control.width();

                    this._div.css({ 'top': newTop + 'px', 'left': newLeft + 'px' });
                    if (cssClass)
                        this._div.addClass(cssClass);

                    this._div.fadeIn();
                }

                this.nascondi = function () {
                    this.assicuraEsistenzaTooltip();

                    this._div.fadeOut();
                }


                this.assicuraEsistenzaTooltip = function () {
                    if (this._div)
                        return;

                    var el = $('<div />', { 'class': 'formTooltip' });

                    el.appendTo(this._control.parent());

                    this._div = el;
                }

                this.nascondi();
            }
        }
    }

    var ListaValoriFactory = function (funzioneLetturaLista) {

        this._funzioneLetturaLista = funzioneLetturaLista;

        this.appendTo = function (control) {

            control.pannelloListaValori = new function () {

                this._div = $('<div />', { 'class': 'listaValoriSit' })
							.appendTo(control.parent());


                this.mostra = function (listaValori) {

                    var self = this;

                    this._div.html('');

                    var ul = $('<ul />', { 'class': 'ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all' })
							.css('width', '100%');

                    for (var i = 0; i < listaValori.length; i++) {

                        var valore = listaValori[i];

                        var li = $('<li />', { 'class': 'ui-menu-item' })
								.appendTo(ul);

                        $('<a />')
						.html(valore)
						.appendTo(li)
						.click(function () {
						    control.val($(this).html());
						    self._div.fadeOut('fast', function () { control.blur(); });
						})
						.mouseover(function () { $(this).addClass('ui-state-hover') })
						.mouseout(function () { $(this).removeClass('ui-state-hover') });

                    }

                    ul.appendTo(this._div);

                    var controlpos = control.position();

                    this._div
					.css({
					    'position': 'absolute',
					    'top': (controlpos.top + control.height() + 7) + 'px',
					    'left': (controlpos.left) + 'px',
					    'min-width': control.width() + 'px'
					})
					.fadeIn('fast');
                }

                this.nascondi = function () {
                    this._div.fadeOut('fast');
                }

                this.nascondi();
            }
        }
    }

    var MappaSit = function (urlBase) {
        this._urlBase = urlBase;

        this.mostra = function (codVia, civico) {
            var url = this._urlBase + "?VIA=" + codVia + "&CIVICO=" + civico;

            var w = window.open(url, "sit", "width: 100%, height: 100%");
        }
    }



    var RicercaStradario = function (config) {
        this._campoCodiceStradario = config.campoCodiceStradario;
        this._campoNomeVia = config.campoNomeVia;
        this._bottoneConferma = config.bottoneConferma;
        this._bottoneMappa = config.bottoneMappa;
        this._campoCivico = config.campoCivico;
        this._campoEsponente = config.campoEsponente;
        this._campoCircoscrizione = config.campoCircoscrizione;
        this._campoCap = config.campoCap;
        this._stradarioService = new RicercaStradarioServerInterface(config.stradarioServiceUrl, config.idComune, config.software, config.token);
        this._tooltipFactory = new ErrorTooltipFactory();
        this._urlImmagineRicerca = config.urlImmagineRicerca;
        this._listaValoriFactory = new ListaValoriFactory();
        this._mappa = new MappaSit(config.urlMappa);

        this.initialize = function () {
            var self = this;

            this._campoNomeVia.change(function () {
                self.impostaValoreCampo(self._campoCivico, '');
                self.impostaValoreCampo(self._campoEsponente, '');
                self.impostaValoreCampo(self._campoCap, '');
                self.impostaValoreCampo(self._campoCircoscrizione, '');
            });

            this._preparaCampo(self._campoCivico, 'Civico');
            this._preparaCampo(self._campoEsponente, 'Esponente');
            this._preparaCampo(self._campoCap, 'Cap');
            this._preparaCampo(self._campoCircoscrizione, 'Circoscrizione');

            this._bottoneMappa.click(function () {
                self._mappa.mostra(self._campoCodiceStradario.val(), self._campoCivico.val());
            });

            this.disabilitaBottoni();
        }

        this._preparaCampo = function (campo, nomeCampoValidazione) {
            var self = this;

            campo.blur(function () {
                self.valida(nomeCampoValidazione, campo);
            });

            campo.focus(function () { campo.tooltip.nascondi(); });

            $('<img />', { 'src': self._urlImmagineRicerca, 'class': 'listaDatiSit' })
			.appendTo(campo.parent())
			.click(function () { self.getLista(nomeCampoValidazione, campo) });

            self._listaValoriFactory.appendTo(campo);
            self._tooltipFactory.appendTo(campo);

            //campo.pannelloListaValori.mostra(['a', 'b', 'c']);
        }

        this.getLista = function (nomeCampo, campo) {
            this._stradarioService.getListaCampi({
                campo: nomeCampo,
                stradario: this._campoCodiceStradario.val(),
                civico: this._campoCivico.val(),
                esponente: this._campoEsponente.val(),
                circoscrizione: this._campoCircoscrizione.val(),
                cap: this._campoCap.val(),
                onSuccess: function (result) {

                    console.log(campo.pannelloListaValori);

                    campo.pannelloListaValori.mostra(result);
                },
                onFailure: function (error) {
                    alert(error);
                }
            });
        }

        this.abilitaBottoni = function () {

            this._bottoneConferma.removeClass('buttonDisabled');
            this._bottoneMappa.removeClass('buttonDisabled');
            this._bottoneConferma.removeAttr('disabled');
            this._bottoneMappa.removeAttr('disabled');

        }

        this.disabilitaBottoni = function () {


            this._bottoneConferma.attr('disabled', 'disabled');
            this._bottoneMappa.attr('disabled', 'disabled');
            this._bottoneConferma.addClass('buttonDisabled');
            this._bottoneMappa.addClass('buttonDisabled');
        }


        this.valida = function (campodaValidare, campo) {

            //		if (this._campoCodiceStradario.val() === '') {
            //			this.validazioneFallita('Specificare un indirizzo', this._campoCodiceStradario);
            //		}

            campo.removeClass('errore');

            if (campo.val() === '')
                return;

            var self = this;

            self.disabilitaBottoni();

            campo.addClass('elaborazione');

            this._stradarioService.valida({
                campo: campodaValidare,
                stradario: this._campoCodiceStradario.val(),
                civico: this._campoCivico.val(),
                esponente: this._campoEsponente.val(),
                circoscrizione: this._campoCircoscrizione.val(),
                cap: this._campoCap.val(),
                onSuccess: function (result) {
                    self.validazioneRiuscita(result);
                    campo.removeClass('elaborazione');
                },
                onFailure: function (error) {
                    self.validazioneFallita(error, campodaValidare);
                    campo.removeClass('elaborazione');
                }
            });
        };

        this.validazioneRiuscita = function (result) {
            //this._campoCodiceStradario.val(result.stradario);
            this.impostaValoreCampo(this._campoCivico, result.civico);
            this.impostaValoreCampo(this._campoEsponente, result.esponente);
            this.impostaValoreCampo(this._campoCircoscrizione, result.circoscrizione);
            this.impostaValoreCampo(this._campoCap, result.cap);

            this.abilitaBottoni();
        }

        this.validazioneFallita = function (error, campoDaValidare) {
            var self = this;

            if (campoDaValidare === 'Civico') {
                self.mostraErroreSuCampo(self._campoCivico, error);
            }

            if (campoDaValidare === 'Esponente') {
                self.mostraErroreSuCampo(self._campoEsponente, error);
            }

            this.disabilitaBottoni();
        }

        this.mostraErroreSuCampo = function (campo, error) {
            campo.addClass('errore');
            campo.tooltip.mostra(error, 'errore');
        }

        this.impostaValoreCampo = function (campo, valore) {
            campo.removeClass('errore');
            campo.val(valore);
        }



        this.initialize();
    }

    return RicercaStradario;
});