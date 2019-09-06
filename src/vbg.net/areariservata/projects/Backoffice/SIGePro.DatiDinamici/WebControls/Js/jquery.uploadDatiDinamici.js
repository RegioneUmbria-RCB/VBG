/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue:true */
/*jslint white: true */
/*global $, jQuery, alert,confirm, console */

window.console = window.console || {
    log: function () { }
};

function ControlloUploadDatiDinamici($, campoDatiDinamici, options) {
    'use strict';

    var formName = 'uploadForm_' + campoDatiDinamici.attr('id'),
        fileUploadName = 'fileUpload_' + campoDatiDinamici.attr('id'),
        verificaFirmaDigitale = campoDatiDinamici.data('verificaFirmaDigitale').toString() === '1',
        dimensioneMassima = campoDatiDinamici.data('dimensioneMassima').toString(),
        estensioniAmmesse = campoDatiDinamici.data('estensioniAmmesse').toString(),
        fileUpload,
        bootstrap_enabled = (typeof $().emulateTransitionEnd === 'function'),
        self = this;

	this.options = options;
	this.campoCodiceOggetto = campoDatiDinamici;
	this.campoCodiceOggetto.css('display', 'none');

	// Creo il form per l'invio dei dati
	campoDatiDinamici.wrap(function () {

		return $('<form />', {
			name: formName,
			method: 'POST',
			enctype: 'multipart/form-data',
			'class': options.classeForm,
			style: 'float:left;padding-right:5px'
		});
	
	});

	this.form = campoDatiDinamici.parent();

	// Aggiungo il contenitore dei controlli di upload
	this.uploadControls = $('<div />', {
        'class': options.classeContenitoreControlliUpload + " input-group"
    }).appendTo(this.form);

	this.uploadResultDiv = $('<div />', {
        'class': options.classeRisultatoUpload + " input-group has-success"
    }).appendTo(this.form);


    // Aggiungo il controllo "sfoglia"
	fileUpload = $('<input />', {
	    type: 'file',
	    name: fileUploadName,
        id: fileUploadName,
        "class": "d2Control form-control"
	});
	
    fileUpload.appendTo(this.uploadControls);
    fileUpload.on("change", function () {
        self.bottoneUpload.click();
        //$(this).val('');
    });

    function toHumanReadable(len) {
        var sizes = ["B", "KB", "MB", "GB", "TB"];
        var order = 0;

        while (len >= 1024 && order < sizes.length - 1) {
            order++;
            len = len / 1024;
        }

        // Adjust the format string to your preferences. For example "{0:0.#}{1}" would
        // show a single decimal place, and no space.
        return len.toFixed(2).toString() + " " + sizes[order];
    }

    // Se il campo ha una dimensione massima o una lista di estensioni ammesse allora mostro
    // il controllo di help
    if ((dimensioneMassima && dimensioneMassima !== "0") || estensioniAmmesse) {
        var helpCtrl = $("<small class='help-block'></small>");

        if (dimensioneMassima && dimensioneMassima !== "0") {
            $("<div><b>Dimensione massima:</b> " + toHumanReadable(parseInt(dimensioneMassima * 1024)) + " </div> ").appendTo(helpCtrl);
        }

        if (estensioniAmmesse) {
            $("<div><b>Estensioni ammesse:</b> " + estensioniAmmesse + " </div> ").appendTo(helpCtrl);
        }

        helpCtrl.appendTo(this.form);
    }

	// Aggiungo il bottone di submit
    // wrapper del bottone
    var buttonWrapper = $("<span class='input-group-btn'></span>");
    buttonWrapper.appendTo(this.uploadControls);

	this.bottoneUpload = $('<input />', {
        type: 'submit',
        value: 'Allega',
        'class': options.classeBottoneUpload + " btn btn-primary"
    }).appendTo(buttonWrapper);
    this.bottoneUpload.on("click", function (e) {
        var file = $(this).closest(".input-group").find("#" + fileUploadName);
        
        if (file.val() === '') {
            file.click();

            e.preventDefault();
        }
        
    });


	// Aggiungo il div contenente i messaggi per l'utente
	this.divMessaggi = $('<div />', {
        'class': options.classeContenitoreMessaggi
    }).appendTo(this.form);
		

	// Aggiungo il segnaposto per il nome file
    var segnapostoWrapper = $("<div class='form-control'></div>");
    segnapostoWrapper.appendTo(this.uploadResultDiv);

    this.segnapostoNomeFile = $('<span />', {
        'class': options.classeSegnapostoNomeFile 
    }).appendTo(segnapostoWrapper);
	
	// Aggiungo il segnaposto per la dimensione del file
    this.segnapostoDimensioneFile = $('<span />', {
        'class': options.classeSegnapostoDimensioneFile
    }).appendTo(segnapostoWrapper);


	// Aggiungo il bottone per rimuovere un allegato
    var eliminaWrapper = $("<span class='input-group-btn'></span>");

    eliminaWrapper.appendTo(this.uploadResultDiv);

    this.bottoneElimina = $('<input />', {
        type: 'button',
        value: 'Rimuovi',
        'class': options.classeBottoneElimina + " btn btn-primary"
    }).appendTo(eliminaWrapper);
    
    this.inizializza = function () {
		var that = this;

		this.mostraMessaggio('Inizializzazione in corso...');

		// Bottone elimina
		this.bottoneElimina.click(function (e) {
			e.preventDefault();
			that.eliminaFile();
		});

		// Collegamento agli eventi del form
		this.form.submit(function () {
			return that.submitForm();
		});

		if (this.campoCodiceOggetto.val() !== '') {
			this.caricaDatiFileEsistente();
		} else {
			this.mostraControlliUpload();
			this.nascondiDivMessaggi();
		}

		fileUpload.tooltip({
		    position: {
		        my: "left+5 center",
		        at: "right center",
		    },
		    tooltipClass: 'tooltipErrore tooltip-errore'
		});

		that.campoCodiceOggetto.bind('campoInizializzato', function () {

		    that.campoCodiceOggetto.unbind('errorSet');
		    that.campoCodiceOggetto.unbind('errorRemoved');

		    that.campoCodiceOggetto.bind('errorSet', function (event, msg) {
		        fileUpload.addClass('d2Errori');
		        fileUpload.attr('title', msg);
		        fileUpload.tooltip('enable');
		    });

		    fileUpload.bind('errorRemoved', function () {
		        //console.log('errorremoved su controllo ricerca');

		        fileUpload.attr('title', null);
		        fileUpload.tooltip('disable');
		        fileUpload.removeClass('d2Errori');
		    });
		});
	};
    
    
    
    
    this.submitForm = function () {

		var formObj = this,
            uploadUrl = this.options.uploadHandler + '?firma=' + verificaFirmaDigitale + '&max=' + dimensioneMassima + "&es=" + estensioniAmmesse;

		if (this.options.querystring !== '') {
			uploadUrl += "&" + this.options.querystring;
        }

		this.form.ajaxSubmit({
			url: uploadUrl,
			type: 'POST',
			iframe: true,
			dataType: 'json',
			success: function (parsedResult) {
                formObj.uploadCompletato(parsedResult);

                fileUpload.val('');
			},
			beforeSubmit: function () {
				formObj.uploadIniziato();
			}
		});

		return false;
	};
    
    
    
    this.uploadCompletato = function (parsedResult) {

        fileUpload.closest(".input-group").removeClass("has-error");

        if (parsedResult.Errori) {
            fileUpload.closest(".input-group").addClass("has-error");

			this.mostraErrore(parsedResult.Errori);
			this.nascondiDatiFile();
			return;
		}

		this.nascondiDivMessaggi();

		this.impostaDatiFile(parsedResult.codiceOggetto, parsedResult.fileName, parsedResult.length, parsedResult.mime);
	};
    
    
    
    this.uploadIniziato = function () {
		this.mostraMessaggio(this.options.messaggioCaricamentoInCorso);
	};
    

    
    this.impostaDatiFile = function (codiceOggetto, nomeFile, dimensione, mime) {

		this.setCodiceOggetto(codiceOggetto, nomeFile);
		this.setNomeFile(codiceOggetto, nomeFile);
		this.setDimensioneFile(dimensione);

		this.mostraDatiFile();
	};
    
    
    
    this.eliminaFile = function () {

        var deleteUrl,
            deleteArguments = {},
            qsArguments,
            i,
            arg;
        
		if (!confirm("Si desidera eliminare il file allegato?")) {
			return;
        }

		this.mostraMessaggio(this.options.messaggioEliminazioneInCorso);

        deleteUrl = this.options.deleteHandler;
        deleteArguments.codiceOggetto = this.getCodiceOggetto();

		if (this.options.querystring !== '') {
			qsArguments = this.options.querystring.split('&');

			for (i = 0; i < qsArguments.length; i++) {
				arg = qsArguments[i].split('=');

				deleteArguments[arg[0]] = arg[1];
			}
		}

		$.ajax({
			data: deleteArguments,
			url: deleteUrl,
			context: this,
			dataType: 'json',
			success: function (data, textStatus, jqXHR) {
				this.fileEliminato();
			},
			error: function (jqXHR, textStatus, errorThrown) {
				this.mostraErrore("Si è verificato un errore durante l'eliminazione del file. Dati tecnici: " + errorThrown);
				this.mostraDatiFile();
			}
		});
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
            },
            qsArguments,
            i,
            arg;

		if (this.options.querystring !== '') {
			qsArguments = this.options.querystring.split('&');

			for (i = 0; i < qsArguments.length; i++) {
				arg = qsArguments[i].split('=');

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
        this.mostraMessaggio('<div style=\'color:#a94442\'>' + text + '</div>');
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
				
				$.fn.uploadDatiDinamici.defaultOptions.querystring = window.location.search.substring(1);

				var options = $.extend($.fn.uploadDatiDinamici.defaultOptions, customOptions),
                    $this = $(this),
                    data = $this.data('__controlloUploadDatiDinamici');

				if (!data) {
					$this.data('__controlloUploadDatiDinamici', new ControlloUploadDatiDinamici($, $this, options));
                }
			});
		}
	};



	


	$.fn.uploadDatiDinamici = function (method) {

	    if (methods[method]) {
	        return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
	    } 

	    if (typeof method === 'object' || !method) {
	        return methods.init.apply(this, arguments);
	    } 

	    $.error('Method ' + method + ' does not exist on jQuery.uploadDatiDinamici');
	};


	$.fn.uploadDatiDinamici.defaultOptions = {
		// Classi dei controlli generati a runtime
		classeForm:							'ddUploadForm',
		classeContenitoreControlliUpload:	'ddUploadControls',
		classeRisultatoUpload:				'ddUploadResult',
		classeBottoneUpload:				'ddUploadButton',
		classeContenitoreMessaggi:			'ddContenitoreMessaggi',
		classeBottoneElimina:				'ddBottoneElimina',
		classeSegnapostoNomeFile:			'ddSegnapostoNomeFile',
		classeSegnapostoDimensioneFile:		'ddSegnapostoDonemsioneFile',

		// Messaggi
		messaggioCaricamentoInCorso:		'Caricamento in corso...',
		messaggioEliminazioneInCorso:		'Eliminazione del file in corso...',

		// Handlers per caricamento/lettura files
		uploadHandler:						'Helper/FileUploadHandlers/UploadHandler.ashx',
		readHandler:						'Helper/FileUploadHandlers/ReadHandler.ashx',
		deleteHandler:						'Helper/FileUploadHandlers/DeleteHandler.ashx',
		downloadHandler:					'Helper/FileUploadHandlers/DownloadHandler.ashx',

		// Token
		querystring:						''
	};


}(jQuery));