define(['jquery', 'cookies', 'jquery.ui'], function ($, Cookies) {
    /*
    settings:
	
    - idComune
    - rootNode
    - divRicerca
    - intestazioneAteco
    - divDescrizioneAteco
    - lnkRicerca
    - urlAtecoService
    - urlContenutiBoxRicerca
    - urlImgJsLoader
    - infoImageString
    - fogliaSelezionata
	
	
    */
    function AlberoAteco(settings) {
        if (settings)
            this.initialize(settings);
    }

    AlberoAteco.prototype = {

        initialize: function (settings) {

            var self = this;

            this.settings = settings;
            this.gerarchiaNodo = {};
            this.cookieName = this.settings.cookiePrefix + '_fogliaSelezionata';

            this.settings.loadString = "<ul><li><img src='" + this.settings.urlImgJsLoader + "'>Caricamento in corso...</li></ul>",



            $(this.settings.divDescrizioneAteco).dialog({
                height: 300,
                width: 400,
                title: "Dettagli",
                modal: true,
                autoOpen: false
            });

            if (this.settings.divRicerca && this.settings.lnkRicerca) {

                this.settings.divRicerca.load(this.settings.urlContenutiBoxRicerca, function () {

                    $(this).dialog({
                        height: 320,
                        width: 550,
                        title: "Ricerca testuale",
                        modal: true,
                        autoOpen: false
                    });

                    self.settings.lnkRicerca.click(function (e) {

                        e.preventDefault();

                        var divRicerca = self.settings.divRicerca;

                        divRicerca.find('#txtRicerca').val('');
                        divRicerca.dialog('open');
                        divRicerca.find('#modoRicerca1').attr('checked', 'true');
                        divRicerca.find('#tipoRicerca1').attr('checked', 'true');
                        divRicerca.find('input:radio[name=modoRicerca]').button('refresh');
                        divRicerca.find('input:radio[name=tipoRicerca]').button('refresh');
                    });

                    var txtRicerca = $(this).find('#txtRicerca');

                    $('input:radio[name=modoRicerca], input:radio[name=tipoRicerca]').click(function () {
                        txtRicerca.autocomplete('search');
                    });


                    txtRicerca.autocomplete({
                        source: function (request, response) {
                            $.ajax({
                                url: self.settings.urlAtecoService + "/RicercaAteco",
                                type: "POST",
                                contentType: "application/json; charset=utf-8",
                                dataType: "json",
                                data: "{'aliasComune':'" + self.settings.idComune + "'," +
									"'matchParziale':'" + request.term + "'," +
									"'matchCount':'9999'," +
									"'modoRicerca':'" + $('input:radio[name=modoRicerca]:checked').val() + "'," +
									"'tipoRicerca':'" + $('input:radio[name=tipoRicerca]:checked').val() + "'}",
                                success: function (data) {

                                    response($.map(data.d, function (item) {
                                        return { label: item.Titolo,
                                            id: item.Id,
                                            codiceBreve: item.Codicebreve
                                        };
                                    }));
                                }
                            });
                        },
                        select: function (event, ui) {
                            if (ui.item && ui.item.id > 0) {
                                self.caricaGerarchiaAteco(ui.item.id);
                                self.settings.divRicerca.dialog('close');
                            }
                            return false;
                        }
                    });

                });
            }

            this.settings.rootNode.html(this.settings.loadString);
            this.caricaNodi(this.settings.rootNode, -1);

            $('.treeView').css('margin-top', $('#intestazioneAteco').height() + "px");
        },

        caricaGerarchiaAteco: function (id) {
            var self = this;

            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: self.settings.urlAtecoService + '/CaricaGerarchia',
                dataType: "json",
                data: "{'aliasComune':'" + self.settings.idComune + "','id':" + id + "}",
                success: function (msg) { self.gerarchiaNodo = msg.d; self.espandiSottonodiGerarchia(); }
            });
        },

        espandiSottonodiGerarchia: function () {
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
            var spanNodo = $('*[idNodo=' + id + ']');

            var element = spanNodo.parent().children('ul');

            if (element.length > 0) {
                if (this.gerarchiaNodo.length) {
                    this.correggiTopPagina(element);
                    element.show();
                }
                else {
                    element.toggle();

                    if (element.is(':visible')) {
                        spanNodo.children('img').attr('src', this.settings.folderOpenImage);
                        this.correggiTopPagina(element);
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

        correggiTopPagina: function (el) {
            var container = this.settings.rootNode.parent();

            var top = el.position().top - container.position().top + container.scrollTop();
            var height = container.height();

            if (top > height) {
                var remainder = top % height;
                var quotient = (top - remainder) / height;

                var newtop = height * quotient;
                container.animate({ scrollTop: newtop }, 700);
            }
        },

        caricaNodi: function (el, id) {
            var self = this;

            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: self.settings.urlAtecoService + '/GetNodiFiglio',
                dataType: "json",
                data: "{'aliasComune':'" + self.settings.idComune + "','idPadre':" + id + "}",
                success: function (msg) {
                    self.createSubtree(el, msg);

                    if (self.gerarchiaNodo.length)
                        self.espandiSottonodiGerarchia();
                    self.correggiTopPagina(el);
                }
            });
        },


        createSubtree: function (el, msg) {
            var self = this;

            var html = "";

            for (var i = 0; i < msg.d.length; i++) {
                html += "<li>";

                html += "<span class='" + (msg.d[i].HasChilds ? "folder" : "file") + "' idNodo='" + msg.d[i].Id + "'>";

                if (msg.d[i].HasDescription) {
                    html += this.settings.infoImageString;
                } else {
                    html += "<span>" + this.settings.blankInfoString + "</span>";
                }

                if (msg.d[i].HasChilds) {
                    html += "<img src='" + this.settings.folderClosedImage + "'/>";
                } else {
                    html += "<img src='" + this.settings.fileImage + "'/>";
                }

                html += "<div class='descrizioneAteco'>";

                var codiceBreve = msg.d[i].Codicebreve;

                if (codiceBreve)
                    html += codiceBreve + " - ";

                html += msg.d[i].Titolo + "</div>";

                html += "</span></li>";
            }

            el.html(html);

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

                if (self.settings.fogliaSelezionata)
                    self.settings.fogliaSelezionata(id);

                return false;
            });

            el.find('.file > A,.folder > A').click(function (e) {
                e.preventDefault();
                var id = $(this).parent().attr('idNodo');
                self.mostraDettagli(id);

                return false;
            });

        },

        mostraDettagli: function (id) {
            var self = this;

            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: self.settings.urlAtecoService + "/GetDettagli",
                dataType: "json",
                data: "{'aliasComune':'" + self.settings.idComune + "','id':" + id + "}",
                success: function (msg) {
                    self.settings.divDescrizioneAteco.html(msg.d.Descrizione);
                    self.settings.divDescrizioneAteco.dialog('option', 'title', msg.d.Titolo).dialog('open');
                }
            });
        }

    };

    return new AlberoAteco();
});