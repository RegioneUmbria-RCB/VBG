/// <reference path="~/Scripts/jquery-1.5.min.js" />
/// <reference path="~/Scripts/json.js" />
/// <reference path="~/Scripts/jquery-ui-1.8.6.custom.min.js" />

define(['jquery', 'json', 'cookies', 'jquery.ui'], function ($, JSON, Cookies) {

    function InterventiServiceWrapper(settings) {
        this._url = settings.url;
        this._idComune = settings.idComune;
        this._software = settings.software;
        this._daAreaRiservata = settings.daAreaRiservata;
        this._utenteTester = settings.utenteTester;
        this._livelloAutenticazione = settings.livelloAutenticazione;
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
                    livelloAutenticazione: this.getLivelloAutenticazione()
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
        getLivelloAutenticazione: function () {
            return this._livelloAutenticazione;
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
    */
    function AlberoInterventi(settings) {

        if (settings)
            this.initialize(settings);
    }

    AlberoInterventi.prototype = {
        instance: {},

        initialize: function (settings) {

            this.settings = settings;

            if (!this.settings.utenteTester)
                this.settings.utenteTester = false;

            this.gerarchiaNodo = {};

            this.preloadImage = new Image();
            this.preloadImage.src = this.settings.urlImgJsLoader;
            this.cookieName = this.settings.cookiePrefix + "_RamoSelezionato";

            this.settings.loadString = "<ul><li><img src='" + this.settings.urlImgJsLoader + "'>Caricamento in corso...</li></ul>";

            var interventiServiceSettings = {
                url: this.settings.urlInterventiService,
                idComune: this.settings.idComune,
                software: this.settings.software,
                daAreaRiservata: this.settings.areaRiservata,
                utenteTester: this.settings.utenteTester,
                livelloAutenticazione: this.settings.livello
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
                    var tmp = $(this).find('.linkDettagliendo');

                    tmp.click(function (e) {
                        e.preventDefault();

                        var url = $(this).attr('href');

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

                this.settings.divRicerca.load(this.settings.urlContenutiBoxRicerca, function () {

                    $(this).dialog({
                        height: 360,
                        width: 550,
                        title: "Ricerca testuale",
                        modal: true,
                        autoOpen: false
                    });

                    self.settings.lnkRicerca.click(function (e) {

                        e.preventDefault();
                        $('#modoRicerca3Div').hide();
                        self.settings.divRicerca.find('#txtRicerca').val('');
                        self.settings.divRicerca.dialog('open');
                        self.settings.divRicerca.find('#modoRicerca1').attr('checked', 'true');
                        self.settings.divRicerca.find('#tipoRicerca1').attr('checked', 'true');
                    });

                    var txtRicerca = $(this).find('#txtRicerca');

                    $('input:radio[name=modoRicerca], input:radio[name=tipoRicerca]').click(function () {
                        txtRicerca.autocomplete('search');
                    });


                    var ricercaAutocomplete = txtRicerca.autocomplete({
                        source: function (request, response) {
                            var modoRicerca = $('input:radio[name=modoRicerca]:checked').val();
                            var tipoRicerca = $('input:radio[name=tipoRicerca]:checked').val();

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
                                self.caricaGerarchiaInterventi(ui.item.id);
                                self.settings.divRicerca.dialog('close');
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
            var idDaCookie = Cookies.get(self.cookieName);

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
            var idComune = this.settings.idComune;
            var $self = this;

            this.getServiceWrapper().caricaGerarchia(id, function (msg) {
                $self.gerarchiaNodo = msg.d;
                $self.espandiSottonodiGerarchia();
            });
        },


        espandiSottonodiGerarchia: function () {
            /// <summary>Espande ricorsivamente i nodi impostati nella proprieta gearchiaNodo</summary>
            $('.selected').removeClass('selected');

            var lastVal = this.gerarchiaNodo[this.gerarchiaNodo.length - 1];
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
            var spanNodo = $('*[idNodo=' + id + ']');

            var element = spanNodo.parent().children('ul');

            if (element.length > 0) {   // il sottonodo  ègià stato caricato
                if (this.gerarchiaNodo.length) {
                    element.show();
                    this.correggiTopPagina(spanNodo);
                } else {
                    element.toggle();

                    if (element.is(':visible')) {
                        spanNodo.children('img').attr('src', this.settings.folderOpenImage);
                        this.correggiTopPagina(spanNodo);
                    }
                    else {
                        spanNodo.children('img').attr('src', this.settings.folderClosedImage);
                    }
                }

                if (this.gerarchiaNodo.length)
                    this.espandiSottonodiGerarchia();
            }
            else {
                element = spanNodo.parent().append(this.settings.loadString).children('ul');
                spanNodo.children('img').attr('src', this.settings.folderOpenImage);
                this.caricaNodi(element, id);
            }
        },

        isElementIntoView: function (elem) {
            var docViewTop = $(window).scrollTop();
            var docViewBottom = docViewTop + $(window).height();

            var elemTop = $(elem).offset().top;
            var elemBottom = elemTop + $(elem).height();

            return ((elemTop <= docViewBottom) && (elemTop >= docViewTop));
        },


        correggiTopPagina: function (el) {

            if (!this.isElementIntoView(el)) {
                $(el)[0].scrollIntoView(true);
            }

        },

        caricaNodi: function (el, id) {
            var self = this;

            var idAteco = this.settings.idAteco;

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
            var blank1 = $(this.settings.blankInfoString);
            var blank2 = $(this.settings.blankInfoString);

            blank1.appendTo(li);
            blank2.appendTo(li);
            i.appendTo(li);

            i.text('Nessuna attività trovata');

            return li;
        },

        creaNodoAlbero: function (dati) {

            var testoNodo = dati.Descrizione + (this.settings.mostraVociAttivabiliDaAreaRiservata && !dati.HaNodiFiglio && dati.PubblicaAreaRiservata ? " *" : "");

            var divTestuale = $('<div />', { 'class': 'descrizioneAteco' }).text(testoNodo);

            var img = $('<img />', {
                'src': dati.HaNodiFiglio ? this.settings.folderClosedImage : this.settings.fileImage
            });

            var imagePlaceholder = dati.HaNote ? $(this.settings.infoImageString) : $('<span />').html(this.settings.blankInfoString);

            var span = $('<span />', {
                'class': dati.HaNodiFiglio ? "folder" : "file",
                'idNodo': dati.Codice
            });

            var li = $('<li />');

            imagePlaceholder.appendTo(span);
            img.appendTo(span);
            divTestuale.appendTo(span);
            span.appendTo(li);

            return li;
        },

        createSubtree: function (el, msg) {
            var self = this;

            el.html('');

            if (msg.d.length === 0) {
                var li = this.creaNodoVuoto();
                li.appendTo(el);
            }

            for (var i = 0; i < msg.d.length; i++) {
                var li = this.creaNodoAlbero(msg.d[i]);
                li.appendTo(el);
            }

            el.find('.folder').click(function (e) {

                e.preventDefault();

                $('.selected').removeClass('selected');
                $(this).addClass('selected');

                var id = $(this).attr('idNodo');

                self.espandiNodo(id);
            });

            el.find('.file').click(function (e) {
                e.preventDefault();

                var id = $(this).attr('idNodo');

                Cookies.set(self.cookieName, id);

                if (self.settings.fogliaSelezionata)
                    self.settings.fogliaSelezionata(id);

                return false;
            });

            el.find('.folder > A, .file > A').click(function (e) {
                e.preventDefault();

                var elImg = $(this).find('img');
                var oldImg = elImg.attr('src');

                elImg.attr('src', self.settings.urlImgJsLoader);

                var id = $(this).parent().attr('idNodo');
                var url = self.settings.urlDettagliIntervento + "&Id=" + id;

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
            var self = this;

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


    return new AlberoInterventi();
});