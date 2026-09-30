function __ControlloUploadDatiDinamici(campoDatiDinamici, options) {

	this.options = options;
	this.campoCodiceOggetto = campoDatiDinamici;
	this.campoCodiceOggetto.css('display', 'none');

	this.formName = 'uploadForm_' + campoDatiDinamici.attr('id');
	var fileUploadName = 'fileUpload_' + campoDatiDinamici.attr('id');
	var mainDivId = 'main_' + campoDatiDinamici.attr('id');

	// Creo il div che contiene tutti gli elementi del controllo
	campoDatiDinamici.wrap(function () {

		return jQuery('<div />', {
			id: mainDivId,
			style: 'float:left;padding:0px'
		});
	
	});

	this.mainContainer = campoDatiDinamici.parent();

	// Aggiungo il contenitore dei controlli di upload
	this.uploadControls = jQuery('<div />', {
								'class': options.classeContenitoreControlliUpload
							}).appendTo(this.mainContainer);

	this.uploadResultDiv = jQuery('<div />', {
								'class': options.classeRisultatoUpload
							}).appendTo(this.mainContainer);


	// Aggiungo il controllo "sfoglia"
	jQuery('<input />', {
		type: 'file',
		name: fileUploadName,
		id: fileUploadName
	}).appendTo(this.uploadControls);


	// Aggiungo il bottone di submit
	this.bottoneUpload = jQuery('<input />', {
								type: 'button',
								value: 'Allega',
								'class': options.classeBottoneUpload
							}).appendTo(this.uploadControls);


	// Aggiungo il div contenente i messaggi per l'utente
	this.divMessaggi = jQuery('<div />', {
			'class': options.classeContenitoreMessaggi
		}).appendTo(this.mainContainer);
		

	// Aggiungo il segnaposto per il nome file
	this.segnapostoNomeFile = jQuery('<span />',{
									'class': options.classeSegnapostoNomeFile
								}).appendTo(this.uploadResultDiv);
	
	// Aggiungo il segnaposto per la dimensione del file
	this.segnapostoDimensioneFile = jQuery('<span />',{
										'class': options.classeSegnapostoDimensioneFile
									}).appendTo(this.uploadResultDiv);


	// Aggiungo il bottone per rimuovere un allegato
	this.bottoneElimina = jQuery('<input />', {
								type: 'button',
								value: 'Rimuovi',
								'class': options.classeBottoneElimina
							}).appendTo(this.uploadResultDiv);

	this.inizializza();
}

__ControlloUploadDatiDinamici.prototype = {
	inizializza: function () {
		var that = this;

		this.mostraMessaggio('Inizializzazione in corso...');

		// Bottone elimina
		this.bottoneElimina.click(function (e) {
			e.preventDefault();
			that.eliminaFile();
		});
		
		this.bottoneUpload.click(function (e) {
			e.preventDefault();
			that.submitForm();
		});

		if (this.campoCodiceOggetto.val() != '') {
			this.caricaDatiFileEsistente(false);
		} else {
			this.mostraControlliUpload();
			this.nascondiDivMessaggi();
		}
	},
	createForm: function(){
		
		var me = this;
		// Creo il form per l'invio dei dati
		this.mainContainer.wrap(function () {

			return jQuery('<form />', {
				name: me.formName,
				method: 'POST',
				enctype: 'multipart/form-data',
				'class': me.options.classeForm,
				style: 'float:left;padding-right:5px'
			});
		
		});

		this.form = this.mainContainer.parent();
		
		// Collegamento agli eventi del form
		/*
		this.form.submit(function () {
			return that.submitForm();
		});
		*/
	},
	destroyForm: function(){
		
		this.mainContainer.unwrap();
	},
	submitForm: function () {

		var formObj = this;

		var uploadUrl = this.options.uploadHandler;
		
		this.createForm();

		if (this.options.querystring != '')
			uploadUrl += "?" + this.options.querystring;

		this.form.ajaxSubmit({
			url: uploadUrl,
			type: 'POST',
			iframe: true,
			dataType: 'json',
			success: function (parsedResult) {
				formObj.destroyForm();
				formObj.uploadCompletato(parsedResult);
			},
			error: function( jqXHR, textStatus, errorThrown){
				formObj.destroyForm();
				alert(textStatus);
			},
			
			beforeSubmit: function () {
				formObj.uploadIniziato();
			}
		});

		return false;
	},
	uploadCompletato: function (parsedResult) {

		if (parsedResult.Errori) {
			this.mostraErrore(parsedResult.Errori);
			this.nascondiDatiFile();
			return;
		}

		this.nascondiDivMessaggi();

		this.impostaDatiFile(parsedResult.codiceOggetto, parsedResult.fileName, parsedResult.length, parsedResult.mime, true);
	},
	uploadIniziato: function () {
		this.mostraMessaggio(this.options.messaggioCaricamentoInCorso);
	},
	impostaDatiFile: function (codiceOggetto, nomeFile, dimensione, mime, fireChangeEvent) {

		this.setCodiceOggetto(codiceOggetto, nomeFile, fireChangeEvent);
		this.setNomeFile(codiceOggetto, nomeFile);
		this.setDimensioneFile(dimensione);

		this.mostraDatiFile();
	},
	eliminaFile: function () {

		if (!confirm("Si desidera eliminare il file allegato?"))
			return;

		this.mostraMessaggio(this.options.messaggioEliminazioneInCorso);

		// var deleteUrl = this.options.deleteHandler;
		var deleteArguments = {
			codiceOggetto: this.getCodiceOggetto()
		};

		if (this.options.querystring != '') {
			var qsArguments = this.options.querystring.split('&');

			for (var i = 0; i < qsArguments.length; i++) {
				var arg = qsArguments[i].split('=');

				deleteArguments[arg[0]] = arg[1];
			}
		};
		this.fileEliminato();
	},
	fileEliminato: function () {
		this.setCodiceOggetto('','', true);
		this.nascondiDatiFile();
		this.nascondiDivMessaggi();
		jQuery('#fileUpload_' + this.campoCodiceOggetto.attr('id')).clearInputs();
	},
	setNomeFile: function (codiceOggetto, nomeFile) {
		var html = "<a href='" + this.options.downloadHandler + "?codiceOggetto=" + codiceOggetto + "&" + this.options.querystring + "' target='_blank'>" + nomeFile + "</a>";

		this.segnapostoNomeFile.html(html);
	},
	setDimensioneFile: function (dimensioneFile) {
		this.segnapostoDimensioneFile.html(" (" + dimensioneFile + " bytes) ");
	},
	setCodiceOggetto: function (codiceOggetto, nomeFile, fireChangeEvent) {
		this.campoCodiceOggetto.val(codiceOggetto);
		this.campoCodiceOggetto.attr('valoreDecodificato', nomeFile);
		if(fireChangeEvent){
			this.campoCodiceOggetto.change();
		}
	},
	getCodiceOggetto: function () {
		return this.campoCodiceOggetto.val();
	},
	caricaDatiFileEsistente: function (fireChangeEvent) {

		var readUrl = this.options.readHandler;
		var readArguments = {
			codiceOggetto: this.getCodiceOggetto()
		};

		if (this.options.querystring != '') {
			var qsArguments = this.options.querystring.split('&');

			for (var i = 0; i < qsArguments.length; i++) {
				var arg = qsArguments[i].split('=');

				readArguments[arg[0]] = arg[1];
			}
		};


		jQuery.ajax({
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
	},
	mostraErrore: function (text) {
		this.mostraMessaggio('<div style=\'color:red\'>' + text + '</div>');
	},
	mostraMessaggio: function (text) {
		this.divMessaggi.html(text);
		this.visualizzaDivMessaggi();
		this.uploadControls.css('display', 'none');
		this.uploadResultDiv.css('display', 'none');
	},
	mostraControlliUpload: function () {
		this.uploadControls.css('display', '');
	},
	nascondiControlliUpload: function () {
		this.uploadControls.css('display', 'none');
	},
	mostraDatiFile: function () {
		this.nascondiControlliUpload();
		this.uploadResultDiv.css('display', '');
	},
	nascondiDatiFile: function () {
		this.mostraControlliUpload();
		this.uploadResultDiv.css('display', 'none');
	},
	nascondiDivMessaggi: function () {
		this.divMessaggi.css('display', 'none');
	},
	visualizzaDivMessaggi: function () {
		this.divMessaggi.css('display', '');
	}
};


(function (jQuery) {

	var methods = {
		init: function( customOptions ){
			return this.each(function () {
				
				jQuery.fn.uploadDatiDinamici.defaultOptions.querystring = window.location.search.substring(1);

				var options = jQuery.extend(jQuery.fn.uploadDatiDinamici.defaultOptions, customOptions); 

				var $this = jQuery(this);
				var data = $this.data('__controlloUploadDatiDinamici');

				if( !data )
					$this.data( '__controlloUploadDatiDinamici' , new __ControlloUploadDatiDinamici( $this , options ) );
			});
		}
	};



	


	jQuery.fn.uploadDatiDinamici = function (method) {

		if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
			return methods.init.apply( this, arguments );
		} else {
			jQuery.error( 'Method ' +  method + ' does not exist on jQuery.uploadDatiDinamici' );
		}    


	};


	jQuery.fn.uploadDatiDinamici.defaultOptions = {
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
		uploadHandler:						'../uploaddatidinamici/ajaxUploadFile.htm',
		readHandler:						'../uploaddatidinamici/ajaxReadFile.htm',
		deleteHandler:						'../uploaddatidinamici/ajaxDeleteFile.htm',
		downloadHandler:					'../uploaddatidinamici/ajaxDownloadFile.htm',

		// Token
		querystring:						''
	};


})(jQuery); 