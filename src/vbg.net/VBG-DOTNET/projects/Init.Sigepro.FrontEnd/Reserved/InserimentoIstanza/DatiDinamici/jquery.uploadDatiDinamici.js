/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue:true */
/*global $, jQuery, alert,confirm */
function ControlloUploadDatiDinamici($, campoDatiDinamici, options) {
    'use strict';

    var formName = 'uploadForm_' + campoDatiDinamici.attr('id'),
        fileUploadName = 'fileUpload_' + campoDatiDinamici.attr('id');

    this.options = options;
    this.campoCodiceOggetto = campoDatiDinamici;
    this.campoCodiceOggetto.css('display', 'none');

    console.log('ControlloUploadDatiDinamici: this.options.querystring', this.options.querystring);

    // Creo il form per l'invio dei dati
    this.form = campoDatiDinamici.wrap(() => $('<div />', {
        name: formName,
        'class': options.classeForm,
        style: 'float:left;padding-left: 8px'
    })).parent();


    // Aggiungo il contenitore dei controlli di upload
    this.uploadControls = $('<div />', {
        'class': options.classeContenitoreControlliUpload
    }).appendTo(this.form);

    this.uploadResultDiv = $('<div />', {
        'class': options.classeRisultatoUpload
    }).appendTo(this.form);

    // Aggiungo il controllo "sfoglia"
    this.fileUpload = document.createElement('input');
    this.fileUpload.setAttribute('type', 'file');
    this.fileUpload.setAttribute('id', fileUploadName);
    this.fileUpload.classList.add('d2-upload-control');
    this.uploadControls[0].appendChild(this.fileUpload);

    // Aggiungo il div contenente i messaggi per l'utente
    this.divMessaggi = $('<div />', {
        'class': options.classeContenitoreMessaggi
    }).appendTo(this.form);


    // Aggiungo il segnaposto per il nome file
    this.segnapostoNomeFile = $('<span />', {
        'class': options.classeSegnapostoNomeFile
    }).appendTo(this.uploadResultDiv);

    // Aggiungo il segnaposto per la dimensione del file
    this.segnapostoDimensioneFile = $('<span />', {
        'class': options.classeSegnapostoDimensioneFile
    }).appendTo(this.uploadResultDiv);

    // Aggiungo il bottone per rimuovere un allegato
    this.bottoneElimina = $('<input />', {
        type: 'button',
        value: 'Rimuovi',
        'class': options.classeBottoneElimina
    }).appendTo(this.uploadResultDiv);

    this.inizializza = () => {

        this.mostraMessaggio('Inizializzazione in corso...');

        // Bottone elimina
        this.bottoneElimina.click((e) => {
            e.preventDefault();

            this.eliminaFile();

            return false;
        });

        this.fileUpload.addEventListener('change', event => {
            this.inviaFile(event);
        });

        if (this.campoCodiceOggetto.val() !== '') {
            this.caricaDatiFileEsistente();
        } else {
            this.mostraControlliUpload();
            this.nascondiDivMessaggi();
        }
    };


    this.inviaFile = async (event) => {

        const files = event.target.files;
        let uploadUrl = this.options.uploadHandler;

        if (this.options.querystring !== '') {
            uploadUrl += "?" + this.options.querystring;
        }
        console.log('Upload all\'url', uploadUrl);

        this.uploadIniziato();

        var data = new FormData()
        data.append('file', files[0]);

        const parsedResult = await fetch(uploadUrl, {
            method: 'POST',
            body: data
        });

        if (!parsedResult.ok) {
            console.error('Errore durante il caricamento del file')
        }

        const json = await parsedResult.json();
        //const parsedResult = await this.form.ajaxSubmit({
        //    url: uploadUrl,
        //    type: 'POST',
        //    iframe: true,
        //    dataType: 'json'
        //});

        console.log('Dati file caricato ', json);

        this.uploadCompletato(json);
    };

    this.uploadCompletato = function (parsedResult) {

        if (parsedResult.Errori) {
            this.mostraErrore(parsedResult.Errori);
            this.nascondiDatiFile();
            return;
        }

        this.nascondiDivMessaggi();

        this.impostaDatiFile(parsedResult.codiceOggetto, parsedResult.fileName, parsedResult.length, parsedResult.mime);

        if (nascondiModalCaricamento) {
            nascondiModalCaricamento();
        }
    };

    this.uploadIniziato = function () {
        if (mostraModalCaricamento) {
            mostraModalCaricamento();
        }

        this.mostraMessaggio(this.options.messaggioCaricamentoInCorso);
    };

    this.impostaDatiFile = function (codiceOggetto, nomeFile, dimensione, mime) {

        this.setCodiceOggetto(codiceOggetto, nomeFile);
        this.setNomeFile(codiceOggetto, nomeFile);
        this.setDimensioneFile(dimensione);

        this.mostraDatiFile();
    };

    this.eliminaFile = async () => {

        if (!confirm("Si desidera eliminare il file allegato?")) {
            return;
        }

        this.mostraMessaggio(this.options.messaggioEliminazioneInCorso);

        const deleteUrl = this.options.deleteHandler;
        const deleteArguments = {
            codiceOggetto: this.getCodiceOggetto()
        };
        /*
        if (this.options.querystring !== '') {
            const qsArguments = this.options.querystring.split('&');

            for (let i = 0; i < qsArguments.length; i++) {
                const arg = qsArguments[i].split('=');

                deleteArguments[arg[0]] = arg[1];
            }
        }
        */


        try {
            await $.ajax({
                data: deleteArguments,
                url: `${deleteUrl}?${this.options.querystring}`,
                context: this,
                dataType: 'json'
            });

            this.fileEliminato();
        } catch (error) {
            console.error(error);
            this.mostraErrore(`Si è verificato un errore durante l'eliminazione del file. Dati tecnici: ${error.statusText}`);
            this.mostraDatiFile();
        }
    };



    this.fileEliminato = function () {
        this.setCodiceOggetto('', '');
        this.nascondiDatiFile();
        this.nascondiDivMessaggi();
    };



    this.setNomeFile = function (codiceOggetto, nomeFile) {
        var url = this.options.downloadHandler + "?codiceOggetto=" + codiceOggetto + "&" + this.options.querystring,
            html = "<a href='" + url + "' target='_blank'>" + nomeFile + "</a>";

        this.segnapostoNomeFile.html(html);
    };



    this.setDimensioneFile = function (dimensioneFile) {
        this.segnapostoDimensioneFile.html(" (" + dimensioneFile + " bytes) ");
    };



    this.setCodiceOggetto = function (codiceOggetto, nomeFile) {
        this.campoCodiceOggetto.val(codiceOggetto);
        this.campoCodiceOggetto.attr('valoreDecodificato', nomeFile);
        this.campoCodiceOggetto.change();
    };


    this.getCodiceOggetto = function () {
        return this.campoCodiceOggetto.val();
    };



    this.caricaDatiFileEsistente = function () {

        var readUrl = this.options.readHandler,
            readArguments = {
                codiceOggetto: this.getCodiceOggetto()
            };

        if (this.options.querystring !== '') {
            const qsArguments = this.options.querystring.split('&');

            for (let i = 0; i < qsArguments.length; i++) {
                const arg = qsArguments[i].split('=');

                readArguments[arg[0]] = arg[1];
            }
        }

        $.ajax({
            data: readArguments,
            url: readUrl,
            context: this,
            dataType: 'json',
            success: function (data, textStatus, jqXHR) {
                this.impostaDatiFile(data.codiceOggetto, data.nomeFile, data.size, data.mime);
                this.nascondiDivMessaggi();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Errore');
                this.mostraErrore("Si è verificato un errore durante il caricamento del file. Dati tecnici: " + errorThrown);
            }

        });
    };



    this.mostraErrore = function (text) {
        this.mostraMessaggio('<div style=\'color:red\'>' + text + '</div>');
    };



    this.mostraMessaggio = function (text) {
        this.divMessaggi.html(text);
        this.visualizzaDivMessaggi();
        this.uploadControls.css('display', 'none');
        this.uploadResultDiv.css('display', 'none');
    };




    this.mostraControlliUpload = function () {
        this.uploadControls.css('display', '');
    };



    this.nascondiControlliUpload = function () {
        this.uploadControls.css('display', 'none');
    };



    this.mostraDatiFile = function () {
        this.nascondiControlliUpload();
        this.uploadResultDiv.css('display', '');
    };



    this.nascondiDatiFile = function () {
        this.mostraControlliUpload();
        this.uploadResultDiv.css('display', 'none');
    };



    this.nascondiDivMessaggi = function () {
        this.divMessaggi.css('display', 'none');
    };



    this.visualizzaDivMessaggi = function () {
        this.divMessaggi.css('display', '');
    };

    this.inizializza();
}

ControlloUploadDatiDinamici.prototype = {



};


(function ($) {
    'use strict';

    var methods = {
        init: function (customOptions) {
            return this.each(function () {
                console.log(window.location.search);
                $.fn.uploadDatiDinamici.defaultOptions.querystring = window.location.search.substring(1);

                const options = {
                    ...$.fn.uploadDatiDinamici.defaultOptions,
                    ...customOptions
                };
                const $this = $(this);
                const data = $this.data('__controlloUploadDatiDinamici');

                if (!data) {
                    $this.data('__controlloUploadDatiDinamici', new ControlloUploadDatiDinamici($, $this, options));
                }
            });
        }
    };






    $.fn.uploadDatiDinamici = function (method) {

        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.uploadDatiDinamici');
        }
    };


    $.fn.uploadDatiDinamici.defaultOptions = {
        // Classi dei controlli generati a runtime
        classeForm: 'ddUploadForm',
        classeContenitoreControlliUpload: 'ddUploadControls',
        classeRisultatoUpload: 'ddUploadResult',
        classeBottoneUpload: 'ddUploadButton',
        classeContenitoreMessaggi: 'ddContenitoreMessaggi',
        classeBottoneElimina: 'ddBottoneElimina',
        classeSegnapostoNomeFile: 'ddSegnapostoNomeFile',
        classeSegnapostoDimensioneFile: 'ddSegnapostoDonemsioneFile',

        // Messaggi
        messaggioCaricamentoInCorso: 'Caricamento in corso...',
        messaggioEliminazioneInCorso: 'Eliminazione del file in corso...',

        // Handlers per caricamento/lettura files
        uploadHandler: 'Helper/FileUploadHandlers/UploadHandler.ashx',
        readHandler: 'Helper/FileUploadHandlers/ReadHandler.ashx',
        deleteHandler: 'Helper/FileUploadHandlers/DeleteHandler.ashx',
        downloadHandler: 'Helper/FileUploadHandlers/DownloadHandler.ashx',

        // Token
        querystring: ''
    };


}(jQuery));