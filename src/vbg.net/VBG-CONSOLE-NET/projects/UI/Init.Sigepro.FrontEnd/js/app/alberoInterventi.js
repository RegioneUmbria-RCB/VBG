/// <reference path="../lib/jquery.js" />
/// <reference path="../lib/cookies.js" />
/// <reference path="../lib/jquery.ui.js" />

define(['jquery', 'cookies', 'jquery.ui'], function ($, Cookies) {

    class InterventiServiceWrapper {

        constructor(settings) {
            this._url = settings.url;
            this._idComune = settings.idComune;
            this._software = settings.software;
            this._codiceComune = settings.codiceComune;
            this._daAreaRiservata = settings.daAreaRiservata;
            this._utenteTester = settings.utenteTester;
        }

        caricaGerarchia(idNodo, successCallback) {
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

        }

        getNodiFiglio(idNodoPadre, idAteco, successCallback) {
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
                    codiceComune: this.getCodiceComune() ?? ''
                }),
                success: function (msg) {
                    successCallback(msg);
                }
            });

        }

        getNodiPadre(idNodo, successCallback) {
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: this.getUrl() + '/GetNodiPadre',
                dataType: "json",
                data: JSON.stringify({
                    aliasComune: this.getIdComune(),
                    id: idNodo,
                    utenteTester: this.getUtenteTester()
                }),
                success: function (msg) {
                    successCallback(msg?.d);
                }
            });
        }

        ricercaTestuale(term, modoRicerca, tipoRicerca, successCallback) {
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
        }

        getDettagli(idNodo, successCallback) {
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
        }

        // accesso alle proprieta
        getUrl() {
            /// <summary>Restituisce l'url del servizio di lettura dati</summary>
            return this._url;
        }

        getIdComune() {
            /// <summary>Restituisce l'id comune in uso</summary>
            return this._idComune;
        }

        getSoftware() {
            /// <summary>Restituisce il software in uso</summary>
            return this._software;
        }

        getUtenteTester() {
            return this._utenteTester;
        }

        getDaAreaRiservata() {
            return this._daAreaRiservata;
        }

        getCodiceComune() { return this._codiceComune; }
    }

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
    - fogliaSelezionata	
    */

    class AlberoInterventiClass {

        initialize(settings) {
            this.settings = settings;

            if (!this.settings.utenteTester)
                this.settings.utenteTester = false;

            this.gerarchiaNodo = {};

            this.cookieName = this.settings.cookiePrefix + "_RamoSelezionato";

            const interventiServiceSettings = {
                url: this.settings.urlInterventiService,
                idComune: this.settings.idComune,
                software: this.settings.software,
                daAreaRiservata: this.settings.areaRiservata,
                utenteTester: this.settings.utenteTester,
                codiceComune: this.settings.codiceComune ?? ''
            };

            this._serviceWrapper = new InterventiServiceWrapper(interventiServiceSettings);

            $(this.settings.divDescrizioneEndo$).dialog({
                width: 600,
                height: 500,
                title: "Dettagli dell\'endoprocedimento",
                modal: true,
                autoOpen: false,
                open: () => {
                    this.settings.divDescrizioneEndo$.find('#accordion').accordion({ header: "h3", heightStyle: 'content' });
                }
            });

            this.settings.divDescrizioneIntervento$.dialog({
                height: 500,
                width: 600,
                title: "Dettagli dell'intervento",
                modal: true,
                autoOpen: false,
                open: () => {
                    this.settings.divDescrizioneIntervento$.find('#accordion').accordion({ header: "h3", heightStyle: 'content' });
                    const tmp = this.settings.divDescrizioneIntervento$.find('.linkDettagliendo');

                    tmp.click((e) => {
                        e.preventDefault();

                        const url = this.settings.divDescrizioneIntervento$.attr('href');

                        this.settings.divDescrizioneEndo$.load(url, null, ()  => {
                            this.settings.divDescrizioneIntervento$.dialog('open');
                        });

                        return false;
                    });
                }
            });

            this.settings.loadString = "<i class='glyphicon glyphicon-refresh spin' style='margin-right: 12px'></i>Caricamento in corso...";

            let rootNode = this.settings.rootNode$[0];

            if (this.settings.idInterventoRoot !== -1) {
                // è stato selezionato un filtro sull'albero. Devo caricare i nodi padre (ma senza espanderli)
                const spinner = document.createElement('li');
                spinner.innerHTML = this.settings.loadString;

                rootNode.append(spinner);

                this._serviceWrapper.getNodiPadre(this.settings.idInterventoRoot, (msg) => {

                    for (const nodo of msg) {
                        nodo.HaNodiFiglio = true;

                        const li = this.creaNodoAlbero(nodo, true);

                        spinner.remove();
                        rootNode.append(li);

                        const ul = document.createElement('ul');

                        li.append(ul);

                        rootNode = ul;
                    }

                    rootNode.append(spinner);

                    this.terminaInizializzazione(rootNode);
                });
            } else {
                this.terminaInizializzazione(rootNode);
            }
        }

        terminaInizializzazione(rootNode) {

            this.caricaNodi(rootNode, this.settings.idInterventoRoot ?? -1);

            $('.treeView').css('margin-top', $('#intestazioneAteco').height() + "px");

            // Form di ricerca
            if (this.settings.divRicerca$ && this.settings.lnkRicerca$) {

                let useBootstrap = true;
                let corpoRicerca = useBootstrap ? this.settings.divRicerca$.find('.modal-body') : this.settings.divRicerca$;

                if (!useBootstrap) {
                    this.settings.divRicerca$.dialog({
                        height: 400,
                        width: 550,
                        title: "Ricerca testuale",
                        modal: true,
                        autoOpen: false
                    });
                }

                corpoRicerca.load(this.settings.urlContenutiBoxRicerca, (item) => {

                    this.settings.lnkRicerca$.click((e) => {

                        e.preventDefault();
                        $('#modoRicerca3Div').hide();
                        this.settings.divRicerca$.find('#txtRicerca').val('');

                        if (useBootstrap) {
                            this.settings.divRicerca$.modal('show');
                        } else {
                            this.settings.divRicerca$.dialog('open');
                        }

                        txtRicerca[0].focus();

                        this.settings.divRicerca$.find('#modoRicerca1').attr('checked', 'true');
                        this.settings.divRicerca$.find('#tipoRicerca1').attr('checked', 'true');
                    });

                    var txtRicerca = corpoRicerca.find('#txtRicerca');

                    $('input:radio[name=modoRicerca], input:radio[name=tipoRicerca]').click(function () {
                        txtRicerca.autocomplete('search');
                    });


                    txtRicerca.autocomplete({
                        source: (request, response) => {
                            const modoRicerca$ = $('input:radio[name=modoRicerca]:checked').val();
                            const tipoRicerca$ = $('input:radio[name=tipoRicerca]:checked').val();

                            this._serviceWrapper.ricercaTestuale(request.term, modoRicerca$, tipoRicerca$, function (data) {

                                response($.map(data.d, (item) => {
                                    return {
                                        label: item.Descrizione,
                                        id: item.Codice
                                    };
                                }));
                            });
                        },
                        select: (event, ui) => {
                            if (ui.item && ui.item.id) {

                                txtRicerca.val(ui.item.label);

                                this.settings.divRicerca$.modal('hide');

                                this.caricaGerarchiaInterventi(ui.item.id, true);
                            }
                            return false;
                        }
                    });

                });
            }

            // Se in precedenza è stato selezionato un nodo lo riapro dall'ultima posizione precedente
            const idDaCookie = Cookies.get(this.cookieName);

            if (idDaCookie)
                this.caricaGerarchiaInterventi(idDaCookie, true);
        }

        caricaGerarchiaInterventi(id, espandiSottonodi) {
            /// <summary>Dato l'id di un nodo dell'albero restituisce l'intera gerarchia dei relativi nodi padre</summary>
            /// <param name="id" type="Number">Id del nodo di cui si vuole risalire la gerarchia</param>

            this._serviceWrapper.caricaGerarchia(id, (msg) => {
                this.gerarchiaNodo = msg.d;

                if (espandiSottonodi) {
                    this.espandiSottonodiGerarchia();
                }
            });
        }

        espandiSottonodiGerarchia() {
            /// <summary>Espande ricorsivamente i nodi impostati nella proprieta gearchiaNodo</summary>
            document.querySelectorAll('.selected').forEach((item) => item.classList.remove('selected'));

            var lastVal = this.gerarchiaNodo[this.gerarchiaNodo.length - 1];
            this.gerarchiaNodo = this.gerarchiaNodo.slice(0, this.gerarchiaNodo.length - 1);

            if (this.gerarchiaNodo.length) {
                this.espandiNodo(lastVal);
            }
            else {
                document.querySelector('*[idNodo=' + lastVal + ']')?.classList.add('selected');
            }
        }

        espandiNodo(id) {
            const spanNodo = document.querySelector(`*[idNodo='${id}']`);

            if (!spanNodo) {
                return;
            }

            const element = spanNodo.parentElement.querySelector('ul');

            if (element) {
                if (this.gerarchiaNodo.length) {
                    element.classList.remove('hidden');

                    this.correggiTopPagina(spanNodo);
                } else {

                    const i = spanNodo.querySelector('i');

                    if (element.classList.contains('hidden')) {
                        element.classList.remove('hidden');
                        i.classList.replace('glyphicon-folder-close', 'glyphicon-folder-open');
                    } else {
                        element.classList.add('hidden');
                        i.classList.replace('glyphicon-folder-open', 'glyphicon-folder-close');

                        this.correggiTopPagina(spanNodo);
                    }
                }

                if (this.gerarchiaNodo.length)
                    this.espandiSottonodiGerarchia();
            } else {

                const element = document.createElement('ul');
                element.innerHTML = `<li>${this.settings.loadString}</li>`;

                spanNodo.parentElement.append(element);

                spanNodo.querySelector('i').classList.add('glyphicon', 'glyphicon-folder-open');

                this.caricaNodi(element, id);
            }
        }

        isElementIntoView(elem) {
            var docViewTop = $(window).scrollTop();
            var docViewBottom = docViewTop + $(window).height();

            var elemTop = $(elem).offset().top;
            var elemBottom = elemTop + $(elem).height();

            return ((elemTop <= docViewBottom) && (elemTop >= docViewTop));
        }

        correggiTopPagina(el) {

            if (!this.isElementIntoView(el)) {
                $(el)[0].scrollIntoView(true);
            }

        }

        caricaNodi(el, idNodoPadre) {
            var idAteco = this.settings.idAteco;

            this._serviceWrapper.getNodiFiglio(idNodoPadre, idAteco, (msg) => {
                this.createSubtree(el, msg);

                if (this.gerarchiaNodo.length)
                    this.espandiSottonodiGerarchia();

                this.correggiTopPagina(el);
            });

        }

        creaNodoVuoto() {

            const li = document.createElement('li');

            li.innerHTML = `
<i class='glyphicon glyphicon-none'></i>
<i class='glyphicon glyphicon-none'></i>
<i>Nessuna attività trovata</i>
`;
            return li;
        }

        creaNodoAlbero(dati, forzaFolderOpen) {

            const testoNodo = dati.Descrizione + (this.settings.mostraVociAttivabiliDaAreaRiservata && !dati.HaNodiFiglio && dati.PubblicaAreaRiservata ? " *" : "");

            // Nome del nodo
            const divTestuale = document.createElement('span');
            divTestuale.classList.add('descrizioneAteco');
            divTestuale.innerText = testoNodo;

            // Icona file o cartella
            const iconaCartella = document.createElement('i');


            let classeCartella = 'glyphicon-list-alt';

            if (dati.HaNodiFiglio) {
                classeCartella = (forzaFolderOpen ? 'glyphicon-folder-open' : 'glyphicon-folder-close');
            }

            iconaCartella.classList.add('glyphicon', classeCartella);

            // Link note
            let linkNote = document.createElement('i');

            linkNote.classList.add('glyphicon');

            if (dati.HaNote) {
                const anchor = document.createElement('a');
                anchor.setAttribute('href', '#');

                anchor.append(linkNote);

                linkNote.classList.add('glyphicon-question-sign');

                linkNote = anchor;
            } else {
                linkNote.classList.add('glyphicon-none');
            }

            var span = document.createElement('span');

            span.classList.add(dati.HaNodiFiglio ? "folder" : "file");
            span.setAttribute('idNodo', dati.Codice);

            var li = document.createElement('li');

            span.append(iconaCartella);
            span.append(linkNote);
            span.append(divTestuale);

            li.append(span);

            return li;
        }

        createSubtree(containerElement, msg) {

            containerElement.innerHTML = '';

            if (msg.d.length === 0) {
                const li = this.creaNodoVuoto();
                li.appendTo(containerElement);
            }

            for (const element of msg.d) {
                const li = this.creaNodoAlbero(element);
                containerElement.append(li);
            }

            containerElement.querySelectorAll('.folder').forEach((folder) => {
                folder.addEventListener('click', (e) => {
                    e.preventDefault();

                    document.querySelectorAll('.selected').forEach((selected) => selected.classList.remove('selected'));
                    folder.classList.add('selected');

                    var id = folder.getAttribute('idNodo');

                    this.espandiNodo(id);
                });
            });

            containerElement.querySelectorAll('.file').forEach((file) => {
                file.addEventListener('click', (e) => {
                    e.preventDefault();

                    var id = file.getAttribute('idNodo');

                    Cookies.set(this.cookieName, id);

                    if (this.settings.fogliaSelezionata)
                        this.settings.fogliaSelezionata(id);

                    return false;
                });
            });

            containerElement.querySelectorAll('.folder > A, .file > A').forEach((item) => {

                item.addEventListener('click', (e) => {
                    e.preventDefault();
                    e.stopPropagation();

                    const iconaPuntoInterrogativo = item.querySelector('.glyphicon-question-sign');

                    iconaPuntoInterrogativo.classList.remove('glyphicon-question-sign');
                    iconaPuntoInterrogativo.classList.add('glyphicon-refresh', 'spin');

                    const fileOFolder = item.closest('.file') ?? item.closest('.folder');

                    const id = fileOFolder?.getAttribute('idNodo');

                    if (!id) {
                        alert('Id is null');
                        return false;
                    }

                    const url = this.settings.urlDettagliIntervento + "&Id=" + id;

                    this.settings.divDescrizioneIntervento$.load(url, null, (responseText, textStatus, XMLHttpRequest) => {

                        iconaPuntoInterrogativo.classList.add('glyphicon-question-sign');
                        iconaPuntoInterrogativo.classList.remove('glyphicon-refresh', 'spin');

                        this.settings.divDescrizioneIntervento$.dialog('open');

                        if (this.dialogDettaglioInterventiOpened)
                            this.dialogDettaglioInterventiOpened(this.settings.divDescrizioneIntervento);
                    });


                    return false;
                });
            });

        }

        mostraDettagli(id) {
            /// <summary>Visualizza i dettagli dell'intervento con l'id passato</summary>
            this._serviceWrapper.getDettagli(id, () => {

                if (msg.d.Descrizione) {
                    this.settings.divDescrizioneIntervento$.html(msg.d.Descrizione);
                    this.settings.divDescrizioneIntervento$.dialog('option', 'title', msg.d.Titolo).dialog('open');
                }
            });
        }
    }

    return new AlberoInterventiClass();
});