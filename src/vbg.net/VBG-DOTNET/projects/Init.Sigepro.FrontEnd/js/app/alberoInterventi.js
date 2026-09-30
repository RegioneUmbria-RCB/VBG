    function InterventiServiceWrapper(settings) {
        this._url = settings.url;
        this._idComune = settings.idComune;
        this._software = settings.software;
        this._daAreaRiservata = settings.daAreaRiservata;
        this._utenteTester = settings.utenteTester;
        this._codiceComune = settings.codiceComune;
    }

    InterventiServiceWrapper.prototype = {

        caricaGerarchia: function (idNodo, successCallback) {
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: this.getUrl() + '/CaricaGerarchia',
                dataType: "json",
                data: JSON.stringify({
                    aliasComune: this.getIdComune(),
                    id: idNodo
                }),
                success: function (msg) {
                    successCallback(msg);
                }
            });

        },

        getNodiFiglio: function (idNodoPadre, idAteco, successCallback) {
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: this.getUrl() + '/GetNodiFiglio',
                dataType: "json",
                data: JSON.stringify({
                    aliasComune: this.getIdComune(),
                    software: this.getSoftware(),
                    idPadre: idNodoPadre,
                    idAteco: idAteco,
                    areaRiservata: this.getDaAreaRiservata(),
                    utenteTester: this.getUtenteTester(),
                    codiceComune: this.getCodiceComune()
                }),
                success: function (msg) {
                    successCallback(msg);
                }
            });

        },

        ricercaTestuale: function (term, modoRicerca, tipoRicerca, successCallback) {
            $.ajax({
                url: this.getUrl() + "/RicercaTestuale",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    aliasComune: this.getIdComune(),
                    software: this.getSoftware(),
                    matchParziale: term,
                    matchCount: 9999,
                    modoRicerca: modoRicerca,
                    tipoRicerca: tipoRicerca,
                    areaRiservata: this.getDaAreaRiservata(),
                    utenteTester: this.getUtenteTester()
                }),
                success: function (data) {
                    successCallback(data);
                }
            });
        },

        getDettagli: function (idNodo, successCallback) {
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: this.getUrl() + "/GetDettagli",
                dataType: "json",
                data: JSON.stringify({
                    aliasComune: this.getIdComune(),
                    id: idNodo
                }),
                success: function (msg) {
                    successCallback(msg);
                }
            });
        },

        // accesso alle proprieta
        getUrl: function () {
            /// <summary>Restituisce l'url del servizio di lettura dati</summary>
            return this._url;
        },
        getIdComune: function () {
            /// <summary>Restituisce l'id comune in uso</summary>
            return this._idComune;
        },
        getSoftware: function () {
            /// <summary>Restituisce il software in uso</summary>
            return this._software;
        },
        getUtenteTester: function () {
            return this._utenteTester;
        },
        getDaAreaRiservata: function () {
            return this._daAreaRiservata;
        },
        getCodiceComune: function () {
            return this._codiceComune;
        }
    };


    /*
    settings:
	
    - idComune
    - software
    - rootNode
    - divDescrizioneIntervento
    - divDescrizioneEndo
    - lnkRicerca
    - txtRicerca
    - urlInterventiService
    - urlDettagliIntervento
    - urlDettagliEndo
    - urlContenutiBoxRicerca
    - urlImgJsLoader
    - infoImageString
    - fogliaSelezionata	
    - mostraInformazioniIntervento
    */
    function AlberoInterventi(settings) {

        if (settings)
            this.initialize(settings);
    }

    AlberoInterventi.prototype = {
        instance: {},

        initialize: function (settings) {
            console.log(settings);
            this.settings = settings;

            if (!this.settings.utenteTester)
                this.settings.utenteTester = false;

            this.gerarchiaNodo = {};
            this.mostraInformazioniIntervento = this.settings.mostraInformazioniIntervento ?? true;

            this.preloadImage = new Image();
            this.preloadImage.src = this.settings.urlImgJsLoader;
            this.cookieName = this.settings.cookiePrefix + "_RamoSelezionato";

            this.settings.loadString = "<ul><li><img src='" + this.settings.urlImgJsLoader + "'>Caricamento in corso...</li></ul>";

            const interventiServiceSettings = {
                url: this.settings.urlInterventiService,
                idComune: this.settings.idComune,
                software: this.settings.software,
                daAreaRiservata: this.settings.areaRiservata,
                utenteTester: this.settings.utenteTester,
                codiceComune: this.settings.codiceComune
            };

            this._serviceWrapper = new InterventiServiceWrapper(interventiServiceSettings);

            var self = this;

            $(this.settings.divDescrizioneEndo).dialog({
                width: 600,
                height: 500,
                title: "Dettagli dell\'endoprocedimento",
                modal: true,
                autoOpen: false,
                open: function () {
                    $(this).find('#accordion').accordion({ header: "h3", heightStyle: 'content' });
                }
            });

            $(this.settings.divDescrizioneIntervento).dialog({
                height: 500,
                width: 600,
                title: "Dettagli dell\'intervento",
                modal: true,
                autoOpen: false,
                open: function () {
                    $(this).find('#accordion').accordion({ header: "h3", heightStyle: 'content' });
                    const tmp = $(this).find('.linkDettagliendo');

                    tmp.click(function (e) {
                        e.preventDefault();

                        const url = $(this).attr('href');

                        self.settings.divDescrizioneEndo.load(url, null, function () {
                            $(this).dialog('open');
                        });

                        return false;
                    });
                }
            });

            this.settings.rootNode.html(this.settings.loadString);
            this.caricaNodi(this.settings.rootNode, -1);

            $('.treeView').css('margin-top', $('#intestazioneAteco').height() + "px");

            // Form di ricerca
            if (this.settings.divRicerca && this.settings.lnkRicerca) {
                
                const useBootstrap = this.settings.useBootstrap;
                const corpoRicerca = useBootstrap ? this.settings.divRicerca.find('.modal-body') : this.settings.divRicerca;

                if (!useBootstrap) {
                    this.settings.divRicerca.dialog({
                        height: 400,
                        width: 550,
                        title: "Ricerca testuale",
                        modal: true,
                        autoOpen: false
                    });
                }

                corpoRicerca.load(this.settings.urlContenutiBoxRicerca, function () {
                                        

                    self.settings.lnkRicerca.click(function (e) {

                        e.preventDefault();
                        $('#modoRicerca3Div').hide();
                        self.settings.divRicerca.find('#txtRicerca').val('');

                        if (useBootstrap) {
                            self.settings.divRicerca.modal('show');
                        } else {
                            self.settings.divRicerca.dialog('open');
                        }

                        txtRicerca[0].focus();

                        self.settings.divRicerca.find('#modoRicerca1').attr('checked', 'true');
                        self.settings.divRicerca.find('#tipoRicerca1').attr('checked', 'true');
                    });

                    const txtRicerca = $(this).find('#txtRicerca');

                    

                    $('input:radio[name=modoRicerca], input:radio[name=tipoRicerca]').click(function () {
                        txtRicerca.autocomplete('search');
                    });


                    const ricercaAutocomplete = txtRicerca.autocomplete({
                        source: function (request, response) {
                            const modoRicerca = $('input:radio[name=modoRicerca]:checked').val();
                            const tipoRicerca = $('input:radio[name=tipoRicerca]:checked').val();

                            self.getServiceWrapper().ricercaTestuale(request.term, modoRicerca, tipoRicerca, function (data) {

                                response($.map(data.d, function (item) {
                                    return {
                                        label: item.Descrizione,
                                        id: item.Codice
                                    };
                                }));
                            });
                        },
                        select: function (event, ui) {
                            if (ui.item && ui.item.id) {

                                txtRicerca.val(ui.item.label);

                                if (useBootstrap) {
                                    self.settings.divRicerca.modal('hide');
                                } else {
                                    self.settings.divRicerca.dialog('close');
                                }

                                self.caricaGerarchiaInterventi(ui.item.id);
                            }
                            return false;
                        }
                    });

                    if (self.settings.autoCompleteCustomRenderer) {
                        ricercaAutocomplete.data('uiAutocomplete')._renderItem = self.settings.autoCompleteCustomRenderer;
                    }

                });
            }


            // Se in precedenza è stato selezionato un nodo lo riapro dall'ultima posizione precedente
            const idDaCookie = sessionStorage.getItem(self.cookieName);

            if (!this.idAtecoPresente() && idDaCookie)
                self.caricaGerarchiaInterventi(idDaCookie);
        },

        idAtecoPresente: function () {
            if (!this.settings.idAteco)
                return false;

            if (this.settings.idAteco === '-1')
                return false;

            return true;
        },

        caricaGerarchiaInterventi: function (id) {
            /// <summary>Dato l'id di un nodo dell'albero restituisce l'intera gerarchia dei relativi nodi padre</summary>
            /// <param name="id" type="Number">Id del nodo di cui si vuole risalire la gerarchia</param>
            const $self = this;

            this.getServiceWrapper().caricaGerarchia(id, function (msg) {
                $self.gerarchiaNodo = msg.d;
                $self.espandiSottonodiGerarchia();
            });
        },


        espandiSottonodiGerarchia: function () {
            /// <summary>Espande ricorsivamente i nodi impostati nella proprieta gearchiaNodo</summary>
            $('.selected').removeClass('selected');

            const lastVal = this.gerarchiaNodo[this.gerarchiaNodo.length - 1];
            this.gerarchiaNodo = this.gerarchiaNodo.slice(0, this.gerarchiaNodo.length - 1);

            if (this.gerarchiaNodo.length) {
                this.espandiNodo(lastVal);
            }
            else {
                $('*[idNodo=' + lastVal + ']').addClass('selected');
            }
        },

        espandiNodo: function (id) {
            /// <summary>Espande il nodo corrispondente al'id passato</summary>
            const spanNodo = $('*[idNodo=' + id + ']');

            let element = spanNodo.parent().children('ul');

            if (element.length > 0) {   // il sottonodo  ègià stato caricato
                if (this.gerarchiaNodo.length) {
                    element.show();
                    this.correggiTopPagina(spanNodo);
                } else {
                    element.toggle();

                    if (element.is(':visible')) {
                        spanNodo.children('i').first().attr('class', 'glyphicon glyphicon-folder-open');
                        this.correggiTopPagina(spanNodo);
                    }
                    else {
                        spanNodo.children('i').first().attr('class', 'glyphicon glyphicon-folder-close');
                    }
                }

                if (this.gerarchiaNodo.length)
                    this.espandiSottonodiGerarchia();
            }
            else {
                element = spanNodo.parent().append(this.settings.loadString).children('ul');
                spanNodo.children('i').first().attr('class', 'glyphicon glyphicon-folder-open');
                this.caricaNodi(element, id);
            }
        },

        isElementIntoView: function (elem) {
            const docViewTop = $(window).scrollTop();
            const docViewBottom = docViewTop + $(window).height();

            //if (!$(elem).offset()) {
            //    debugger;
            //}

            const elemTop = $(elem).offset().top;
            const elemBottom = elemTop + $(elem).height();

            return elemTop <= docViewBottom && elemTop >= docViewTop;
        },


        correggiTopPagina: function (el) {

            if (!this.isElementIntoView(el)) {
                $(el)[0].scrollIntoView(true);
            }

        },

        caricaNodi: function (el, id) {
            const self = this;

            const idAteco = this.settings.idAteco;

            this.getServiceWrapper().getNodiFiglio(id, idAteco, function (msg) {
                self.createSubtree(el, msg);

                if (self.gerarchiaNodo.length)
                    self.espandiSottonodiGerarchia();

                self.correggiTopPagina(el);
            });

        },

        creaNodoVuoto: function () {
            var li = $('<li />');
            var i = $('<i/>');
            var blank1 = $("<i class='glyphicon glyphicon-none'></i>");
            var blank2 = $("<i class='glyphicon glyphicon-none'></i>");

            blank1.appendTo(li);
            blank2.appendTo(li);
            i.appendTo(li);

            i.text('Nessuna attività trovata');

            return li;
        },

        creaNodoAlbero: function (dati) {

            const testoNodo = dati.Descrizione + (this.settings.mostraVociAttivabiliDaAreaRiservata && !dati.HaNodiFiglio && dati.PubblicaAreaRiservata ? " *" : "");

            const divTestuale = $('<span />', { 'class': 'descrizioneAteco' }).text(testoNodo);

            const img = $('<i />', {
                'class': dati.HaNodiFiglio ? 'glyphicon glyphicon-folder-close' : 'glyphicon glyphicon-list-alt'
            });

            if (!this.mostraInformazioniIntervento) {
                dati.HaNote = false;
            }

            const imagePlaceholder = dati.HaNote ? $("<a href='#'><i class='glyphicon glyphicon-question-sign'></i></a>") : $("<i class='glyphicon glyphicon-none'></i>");

            const span = $('<span />', {
                'class': dati.HaNodiFiglio ? "folder" : "file",
                'idNodo': dati.Codice
            });

            const li = $('<li />');

            img.appendTo(span);
            imagePlaceholder.appendTo(span);
            divTestuale.appendTo(span);
            span.appendTo(li);

            return li;
        },

        createSubtree: function (el, msg) {
            const self = this;
            let li;

            el.html('');

            if (msg.d.length === 0) {
                li = this.creaNodoVuoto();
                li.appendTo(el);
            }

            for (const element of msg.d) {
                li = this.creaNodoAlbero(element);
                li.appendTo(el);
            }

            el.find('.folder').click(function (e) {

                e.preventDefault();

                $('.selected').removeClass('selected');
                $(this).addClass('selected');

                const id = $(this).attr('idNodo');

                self.espandiNodo(id);
            });

            el.find('.file').click(function (e) {
                e.preventDefault();

                const id = $(this).attr('idNodo');

                sessionStorage.setItem(self.cookieName, id);

                if (self.settings.fogliaSelezionata)
                    self.settings.fogliaSelezionata(id);

                return false;
            });

            el.find('.folder > A, .file > A').click(function (e) {
                e.preventDefault();

                const elImg = $(this).find('img');
                const oldImg = elImg.attr('src');

                elImg.attr('src', self.settings.urlImgJsLoader);

                const id = $(this).parent().attr('idNodo');
                const url = self.settings.urlDettagliIntervento + "&Id=" + id;

                self.settings.divDescrizioneIntervento.load(url, null, function (responseText, textStatus, XMLHttpRequest) {

                    elImg.attr('src', oldImg);

                    $(this).dialog('open');

                    if (self.dialogDettaglioInterventiOpened)
                        self.dialogDettaglioInterventiOpened($(this));
                });

                return false;
            });

        },

        mostraDettagli: function (id) {
            /// <summary>Visualizza i dettagli dell'intervento con l'id passato</summary>
            const self = this;

            this.getServiceWrapper().getDettagli(id, function () {
                self.settings.divDescrizioneIntervento.html(msg.d.Descrizione);
                self.settings.divDescrizioneIntervento.dialog('option', 'title', msg.d.Titolo).dialog('open');
            });
        },

        getServiceWrapper: function () {
            /// <summary>Restituisce l'istanza del service wrapper in uso</summary>
            /// <returns type="InterventiServiceWrapper">service wrapper</returns>
            return this._serviceWrapper;
        }



    };

