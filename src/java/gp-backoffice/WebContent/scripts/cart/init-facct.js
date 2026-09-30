/**
 * Funzioni e variabili JavaScript utilizzate nelle pagine della modulistica CART
 * si nel FO (CART-FACCT) che nel BO (generazione allegati per notifica CART ente terzo).
 */

var blockUIEnabled = true;
var tabs;
var idModuli = [];
var nomiModuli = [];
var riferimentiModuli = [];

var hiddenFieldsForDeletedRows = [];

var errorFieldsMap = new Array();

if("" + contextPath == "undefined"){
	var contextPath = "";
}


inizializzaFACCT = function(){
	$(document).ajaxStart(blockUIIfNeeded).ajaxStop(unblockUIIfNeeded);
	$("form").submit(function() {			
		  $("#contenutoDaBloccare").block(/* options here if needed */);
		  return true;
	});
	
	$('#ui-datepicker-div').hide();
	
	$('.init-comboautocompiler').on('comboautocompilersearch', 
		function(){
			blockUIEnabled = false;
			return true;
		}
	);
	$('.init-comboautocompiler').on('comboautocompilerclose', 
		function(){
			blockUIEnabled = true;
			return true;
		}
	);
	$('.init-allegatiendoautocompiler').on('allegatiendoautocompilersearch', 
			function(){
				blockUIEnabled = false;
				return true;
			}
	);
	$('.init-allegatiendoautocompiler').on('allegatiendoautocompilerclose', 
		function(){
			blockUIEnabled = true;
			return true;
		}
	);

	tabs = $( "#moduli_tabs" ).tabs();
	tabs.on("tabsbeforeactivate",null, selectModuloTabHandler);
	$('[name = "avanti"]').bind('click',goToNextQuadro);
	
	$("#presenta_domanda").click(function(){
		invioModulistica();
	});
	
	$('.help').tooltip();
	$('.help_image').tooltip();
	$('.error_image').tooltip();
	$('.warning_image').tooltip();
};


blockUIIfNeeded = function(){
	if(blockUIEnabled){
		$.blockUI();
	}
};

unblockUIIfNeeded = function(){
	if(blockUIEnabled){
		$.unblockUI();
	}
};

/*
* handler che gestisce gli eventi di click sui bottoni 'Aggiungi' delle tabelle
*/
addNewTableRow = function(event){
	var idTabella = event.data.idTabella;
	var idTabellaForCss = escapeStringForCssSelector(idTabella);
	var idTabellaForJs = idTabella;
	var indexPos = idTabellaForJs.indexOf("_i_");
	if(indexPos > -1){
		idTabellaForJs = idTabellaForJs.substring(0,indexPos);
	}
	idTabellaForJs = cleanStringForJsVariableName(idTabellaForJs);
	var tabella;
	if(idTabella){
		tabella = $("#" + idTabellaForCss);
	}
	if(tabella && tabella.length > 0){
		//numero di righe e numero max di righe sono memorizzati come dati collegati alla tabella stessa
		var numRighe = tabella.eq(0).data('numRighe');
		var maxRighe = tabella.eq(0).data('maxRighe');
		if(numRighe < maxRighe){
			var maxIndexF = $("#tabella_" + idTabellaForCss + "_maxindex");
			var maxProgF = $("#tabella_" + idTabellaForCss + "_maxprog");
			var maxIndex = Number(maxIndexF.val());
			var maxProg = Number(maxProgF.val());
			maxIndex++;
			maxProg++;
			//recupero il template per la creazione delle righe successive
			//var template = $("#template_" + idTabellaForCss).template();
			var idTabellaNoIndex = idTabellaForCss;
			var indexPos = idTabellaForCss.indexOf("_i_");
			var indexes = [];
			if(indexPos > -1){
				idTabellaNoIndex = idTabellaForCss.substring(0, indexPos);
				var subSuffix = idTabellaForCss.substring(indexPos+3);
				if(subSuffix.length > 0){
					indexes = subSuffix.split('_');
				}
			}
			indexes[indexes.length] = maxProg;
			var template = $("#template_" + idTabellaNoIndex);
			if(template && template.length > 0){
				//$.tmpl(template,{index: maxProg}).appendTo(tabella.find('tbody'));
				template.tmpl({index: indexes}).appendTo(tabella.children('table').children('tbody'));
			}
			//aggiungo il campo hidden che consente di ricavare lato server l'indice dell'id semantico a cui punta ciascuna riga delle tabelle
			//$("#" + idTabellaForCss).prepend("<input type='hidden' name='" + idTabella + "_row_" + maxProg + "_dataindex' id='" + idTabella + "_row_" + maxProg + "_dataindex' value='" + maxIndex + "'/>");
			//aggiorno il valore del campo hidden della tabella che serve per trasmettere al server il massimo progressivo riga da cercare
			maxProgF.val(maxProg);
			//aggiorno il valore del campo hidden della tabella che serve per memorizzare l'indice di id semantico a cui sono associati i valori dell'ultima riga della tabella.
			maxIndexF.val(maxIndex);
			//invoco lo script che attiva le funzionalità dinamiche nei campi della nuova riga
			var invoke = "attivaNuovaRiga_" + idTabellaForJs + "(indexes);";
			eval(invoke);
			numRighe++;
			tabella.eq(0).data('numRighe', numRighe);
			if(numRighe == maxRighe){
				$(this).button('disable');
			}
		}
		else{
			//non dovrebbe nemmeno verificarsi l'evento perchè quando numRighe == maxRighe il bottone viene disabilitato
			
		}
	}
};

/*
 * Funzione che inizializza il bottone eliminia nelòle righe aggiunte alle tabelle
 */
initializeDeleteButton = function(idTabella, newRowIndex){
	var idTabellaForCss = escapeStringForCssSelector(idTabella);
	$("#" + idTabellaForCss + "_row_" + newRowIndex + "_delbutton").button();
	var eventData = {
		idTabella: idTabella,
		rowIndex: newRowIndex
	};
	$("#" + idTabellaForCss + "_row_" + newRowIndex + "_delbutton").bind('click',eventData,deleteTableRow);
}

/*
* Handler che gestisce la cancellazione delle righe delle tabelle
*/
deleteTableRow = function(event){
	var rowIndex = event.data.rowIndex;
	var idTabella = event.data.idTabella;
	var idTabellaForCss = escapeStringForCssSelector(idTabella);
	var idTabellaForJs = cleanStringForJsVariableName(idTabella);
	//showDialog("Cancellazione riga " + rowIndex + " dalla tabella " + idTabella + " non supportata.");
	var tabella;
	if(idTabella){
		tabella = $("#" + idTabellaForCss);
	}
	if(tabella && tabella.length > 0){
		var numRighe = tabella.eq(0).data('numRighe');
		var maxRighe = tabella.eq(0).data('maxRighe');
		$("#riga_" + rowIndex + "_" + idTabellaForCss).remove();
		var hiddenDeleted = $("#" + idTabellaForCss + "_row_" + rowIndex + "_dataindex");
		if(hiddenDeleted && hiddenDeleted.length > 0){
			hiddenFieldsForDeletedRows[hiddenFieldsForDeletedRows.length] = hiddenDeleted.eq(0);
		}
		numRighe--;
		tabella.eq(0).data('numRighe', numRighe);
		if(numRighe < maxRighe){
			$("#bottone_" + idTabellaForCss).button('enable');
		}
		else{
			$("#bottone_" + idTabellaForCss).button('disable');
		}
		//decremento il campo hidden maxIndex
		var maxIndexF = $("#tabella_" + idTabellaForCss + "_maxindex");
		var maxIndex = Number(maxIndexF.val());
		maxIndexF.val(--maxIndex);
		var maxProgF = $("#tabella_" + idTabellaForCss + "_maxprog");
		var maxProg = Number(maxProgF.val());
		//decremento il valore dei campi hidden dataindex per tutte le righe successive a quella cancellata
		/*
		var hiddenDataIndex;
		var dataIndex;
		for(var i = rowIndex+1; i <= maxProg; i++){
			hiddenDataIndex = $("#" + idTabellaForCss + "_row_" + i + "_dataindex");
			if(hiddenDataIndex && hiddenDataIndex.length > 0){
				dataIndex = Number(hiddenDataIndex.val());
				if(dataIndex > -1){
					hiddenDataIndex.val(dataIndex-1);
				}
			}
		}
		*/
	}
};


showDialog = function(message,title, width, height,callbackContextData){
	if(!title)title = "";
	var dialogDiv = $('#dialog');
	dialogDiv.attr('title',title);
	var dialogP = $('#dialog_content');
	//dialogP.empty();
	dialogP.html(message);
	/*
	* se l'argomento callbackContextData è valorizzato significa che la dialog 
	* contiene i messaggi di validazione del quadro e => devo mostrare i bottoni Procedi e Correggi
	*/
	var option = {
			minWidth: 300,
			minHeight: 250
	};
	if(callbackContextData){
		var arrayIndex = 0;
		var dialogButtons = [];
		if(callbackContextData.showNextQuadro || (!callbackContextData.showNextQuadro & !callbackContextData.isBloccato)){
			dialogButtons[arrayIndex] = {text: "Procedi", click: procediDialogAction};
			arrayIndex++;
		}
		dialogButtons[arrayIndex] = {text: "Correggi", click: correggiDialogOption};
		option.buttons = dialogButtons;
		option.callbackData = callbackContextData;
	}
	if(width){
		option.width = width;
	}
	if(height){
		option.height = height;
	}
	var dialogUI = dialogDiv.dialog(option);
};

//callback della dialog invocato nel contesto della dialog stessa al click su 'Procedi'
procediDialogAction = function(){
	var callbackData = $(this).dialog("option","callbackData");//$(this).dialog("option","callbackData");
	if(callbackData) {
		_procedi.call(callbackData);
	}
	$(this).dialog("close");
	$(this).dialog("destroy");
};

//callback della dialog invocato nel contesto della dialog stessa al click su 'Correggi'
correggiDialogOption = function(){
	$(this).dialog("close");
	$(this).dialog("destroy");
};

//verifica che il valore passato in input non sia vuoto
isValue = function(checkValue){
	var retVal = checkValue != undefined && checkValue != null;
	if(retVal){
		retVal = checkValue + '';
		retVal = retVal.length > 0;
	}
	return retVal;
};

//restituisce i valori dei campi passati in input come array di stringhe
readValuesFromElements = function(jQueryObj){
	var values = [];
	jQueryObj.each(function(index,elem){
		if(elem.tagName.toUpperCase() == "SELECT"){
			values = values.concat($(elem).val());
		}
		else if(elem.tagName.toUpperCase() == "INPUT"){
			if(elem.type.toUpperCase() == "CHECKBOX" || elem.type.toUpperCase() == "RADIO"){
				if(elem.checked){
					values = values.concat($(elem).val());
				}
			}
			else{
				values = values.concat($(elem).val());
			}
		}
	});
	return values;
};

/*
 * Handler che gestisce il click sul tab di un quadro
 */
selectQuadroTabHandler = function(event,ui){
	var idQuadro = ui.newPanel[0].id;
	if(idQuadro.indexOf("quadro_") == 0){
		idQuadro = idQuadro.substring(7);
	}
	//$('#quadro_attivo').val(idQuadro);
	var refModulo = $('#riferimento_modulo_attivo').val();
	var idModulo = $('#id_modulo_attivo').val();
	var titoloModulo = $('#titolo_modulo_attivo').val();
	goToQuadro(refModulo, idModulo, titoloModulo, idQuadro);
	//event.stopPropagation();
	event.preventDefault();
	return false;
	//showDialog("selezionato il tab del quadro " + idQuadro, "Test");
};

/*
 * Handler che gestisce il click sul tab di un modulo
 */
selectModuloTabHandler = function(event,ui){
	var panel = ui.newTab[0];
	var idModulo = panel.id;
	if(idModulo.indexOf("tab_") == 0){
		idModulo = idModulo.substring(4);
	}
	//$('#id_modulo_attivo').val(idModulo);
	var titoloModulo = $(panel).data('titolo_modulo');
	var riferimentoModulo = $(panel).data('riferimento_modulo');
	//$('#titolo_modulo_attivo').val(titoloModulo);
	var tabsQuadri = $(panel).data('tabs_quadri');
	if(tabsQuadri){
		var currentIndex = tabsQuadri.tabs( "option", "active" );
		var evalStr = INFO_QUADRI_MODULO_PREFIX + cleanStringForJsVariableName(idModulo) + "[" + currentIndex + "].idQuadro";
		var idQuadro = eval(evalStr);
		//$('#quadro_attivo').val(idQuadro);
		//tabsQuadri.trigger('tabsselect', tabsQuadri.tabs());
		goToQuadro(riferimentoModulo, idModulo, titoloModulo, idQuadro);
	}
	//event.stopPropagation();
	event.preventDefault();
	return false;
	//showDialog("selezionato il tab del modulo " + idModulo, "Test");
};

/*
 * Funzione invocata sempre al cambio di quadro.
 * trasmette i dati del form del quadro al server con una chiamata AJAX e gestisce l'asito della chiamata
 * nella funzione di callback 'goToQuadroCallback'
 */
goToQuadro = function(toModuloRef, toModuloId, toModuloTitle,toQuadroId){
	
	//TODO far partire un'animazione di attesa
	var riferimentoModulo = $('#riferimento_modulo_attivo').val();
	var idModulo = $('#id_modulo_attivo').val();
	var titoloModulo = $('#titolo_modulo_attivo').val();
	var idQuadro = $('#quadro_attivo').val();
	var formId = 'conferma_' + idModulo + '_' + idQuadro;
	var form = $('#' + escapeStringForCssSelector(formId));
	if(form && form.length > 0){
		var extraParams = {
				modulo_attivo: titoloModulo,
				quadro_attivo: idQuadro,
				riferimento_modulo_attivo: riferimentoModulo
		};
		if(!toModuloId || !toQuadroId){
			extraParams.presenta_domanda = "true";
		}
		var callbackContext = {
				goToModuloRef: toModuloRef,
				goToModuloId: toModuloId,
				goToModuloTitle: toModuloTitle,
				goToQuadroId: toQuadroId
		};	
		form.ajaxSubmit({
				  url: contextPath + "/cart/ajaxConfermaQuadro.htm?", 
				  type: "POST", 
				  data: extraParams, 
				  dataType: "json",
				  success: goToQuadroCallback,
				  error: ajaxErrorCallback,
				  context: callbackContext
		});
	}
	else{
		showDialog("Impossibile recuperare i dati inseriti per il quadro " + idQuadro + " del modulo " + idModulo, "Errore");
	}		
};

/*
 * Funzione di callback di 'goToQuadro' che gestisce l'esito della conferma del quadro.
 * Si occupa di visualizzare tutti i messaggi di errore nella dialog e accanto ai campi 
 * e aggiorna il conteggio degli errori a livello di quadro e di modulo
 */
goToQuadroCallback = function(data, code, jqXHR){
	//showDialog("Chiamata al server effettuata con successo.","Debug");
	var errors = data.validationErrors;
	var isError = errors && errors.length && errors.length > 0;
	var isBloccato = false;
	var showNextQuadro = this.goToModuloRef && this.goToModuloId && this.goToModuloTitle && this.goToQuadroId;
	this.showNextQuadro = showNextQuadro;
	//aggiorno lo stato dei campi file del quadro corrente prima di aprire il successivo
	var idQuadro = $('#quadro_attivo').val();
	//var idModulo = $('#id_modulo_attivo').val();
	//errorFieldsMap
	var modErrors, quadErrors;
	if(isError){
		//var errorCount, warningCount;
		var fullMessage = "Campi non validi:<br/> <ul class='error-list'>";
		var newErrors = [];
		var isBloccante;
		//identifico tutti i quadri interessati da errori di validazione
		for (i = 0; i < errors.length; i++) {
			isBloccante = errors[i].bloccante; 
			var messages = errors[i].errori;
			modErrors = newErrors[errors[i].idModulo] || [];
			quadErrors = modErrors[errors[i].idQuadro] || {errorCount: 0, warningCount: 0};
			if(messages){
				if(isBloccante){
					quadErrors.errorCount += messages.length;
				}
				else{
					quadErrors.warningCount += messages.length;
				}
			}
			modErrors[errors[i].idQuadro] = quadErrors;
			newErrors[errors[i].idModulo] = modErrors;
		}
		for (var idModulo in newErrors) {
			modErrors = newErrors[idModulo];
			for (idQuadro in modErrors) {
				//azzero la visualizzazione di errori e warning nei quadri che hanno subito nuova validazione
				_clearQuadroErrors(idModulo,idQuadro);
				quadErrors = modErrors[idQuadro];
			}
		}
		for (i = 0; i < errors.length; i++) {
			isBloccante = errors[i].bloccante; 
			if(!isBloccato && isBloccante){
				isBloccato = true;
			}
			var messages = errors[i].errori;
			if(messages){
				for (var y = 0; y < messages.length; y++) {
					var liClass = isBloccante ? "error-msg" : "warning-msg";
					fullMessage += "<li class='" + liClass + "'>" + messages[y].message + "</li>";
					var indexingSuffix = buildIndexingSuffix(messages[y].rowIndex);
					_displayErrorInFormField(true,errors[i].idModulo,errors[i].idQuadro,errors[i].idSemantico, indexingSuffix, messages[y].message,!isBloccante);
				}
			}
		}
		fullMessage += "</ul>";
		for (idModulo in newErrors) {
			modErrors = newErrors[idModulo];
			for (idQuadro in modErrors) {
				quadErrors = modErrors[idQuadro];
				if(quadErrors.errorCount > 0){
					//visulizzo il n. di errori nel quadro 
					_updateTabErrorsCount("quadro-"+ idQuadro + "-errors", quadErrors.errorCount);
					//e ne incremento il numero nel modulo
					_updateTabErrorsCount("modulo-"+ idModulo + "-errors", quadErrors.errorCount);
				}
				if(quadErrors.warningCount > 0){
					//visulizzo il n. di errori nel quadro 
					_updateTabErrorsCount("quadro-"+ idQuadro + "-warnings", quadErrors.warningCount);
					//e ne incremento il numero nel modulo
					_updateTabErrorsCount("modulo-"+ idModulo + "-warnings", quadErrors.warningCount);
				}
			}
		}
	}
	//se non ci sono nuovi errori
	else{
		//se ho validato solo un quadro
		if(showNextQuadro){
			//svuoto l'elenco errori del quadro corrente
			_clearQuadroErrors();
		}
		//se sto presentando la domanda svuoto l'elenco errori di tutti i quadri
		else{
			for (var idModulo in errorFieldsMap) {
				modErrors = errorFieldsMap[idModulo];
				for (idQuadro in modErrors) {
					_clearQuadroErrors(idModulo,idQuadro);
					quadErrors = modErrors[idQuadro];
				}
			}
		}
	}
	this.isBloccato = isBloccato;
	if(isError && (showNextQuadro || isBloccato)){
		showDialog(fullMessage,"Attenzione",500,false,this);
	}
	/*
	* in attesa implementare una migliore gestione dei messaggi di errore è stato reso possibile accedere 
	* ai quadri successivi anche se ci sono errori di validazione
	*/
	else {
		_procedi.call(this);
	}
};

/*
 * Funzione che visualizza un'errore di validazione accanto ad un campo della modulistica
 */
_displayErrorInFormField = function(errorFlag,idModulo,idQuadro,idSemantico,indexingSuffix,errorMessage,isWarning){
	
	if(indexingSuffix + "" == "undefined"){
		indexingSuffix = "";
	}
	if(! errorFlag){
		errorMessage = '';
	}
	else{
		var moduloErrMap = errorFieldsMap[idModulo];
		if(!moduloErrMap){
			moduloErrMap = new Array();
			errorFieldsMap[idModulo] = moduloErrMap;
		}
		var quadroErrMap = moduloErrMap[idQuadro];
		if(!quadroErrMap){
			quadroErrMap = new Array();
			moduloErrMap[idQuadro] = quadroErrMap;
		}
		var campoErrMap = quadroErrMap[idSemantico];
		if(!campoErrMap){
			campoErrMap = new Array();
			quadroErrMap[idSemantico] = campoErrMap;
		}
		var errorList = campoErrMap[indexingSuffix];
		if(!errorList){
			errorList = new Array();
		}
		var err_warn_flag = isWarning ? false : true;
		errorList.push(err_warn_flag);
		campoErrMap[indexingSuffix] = errorList;
	}
	
	var cssSelector = idSemantico + indexingSuffix;
	//cssSelector = escapeStringForCssSelector(cssSelector);
	var field;
	/*
	if(rowIndex !== -1){
		var fields = $('[name ^= "' + cssSelector + '"]');
		if(fields.length > rowIndex){
			field = $(fields.get(rowIndex));
		}
	}
	else{
		*/
		field = $("[name='" + cssSelector + "']");
	//}
	if(field && field.length > 0){
		if(errorFlag){
			field.addClass('field-error');
		}
		else{
			field.removeClass('field-error');
		}
	}
	//var fieldContainer;
	if(field){
		field = field.parent();
		while(field && field.length > 0 && !field.hasClass('campo-cart-container')){
			field = field.parent();
		}
	}
	if(field && field.length > 0 && field.hasClass('campo-cart-container')){
		if(errorFlag){
			field.addClass('campo-cart-container-error');
		}
		else{
			field.removeClass('campo-cart-container-error');
		}
	}
	var fieldSelector;
	if(field && errorFlag){
		fieldSelector = isWarning ? '.warning_image' : '.error_image';
		var tooltipIcon = field.find(fieldSelector);
		if(tooltipIcon && tooltipIcon.length > 0){
			var ttipText = tooltipIcon.attr('title');
			if(ttipText == undefined){
				ttipText = "";
			}
			if(ttipText && ttipText.length > 0){
				ttipText += '\r\n';
			}
			ttipText += ' - ' + errorMessage;
			tooltipIcon.attr('title',ttipText);
			if(errorFlag){
				tooltipIcon.show();
			}
			else{
				tooltipIcon.hide();
			}
		}
	}
	else if(field){
		var tooltipIcon = field.find('.warning_image').add(field.find('.error_image'));
		tooltipIcon.attr('title','');
		tooltipIcon.hide();
	}
	//se è un warning per la firma digitale aggiorno il messaggio anche nel controllo file upload
	if(isWarning){
		var selector = escapeStringForCssSelector('ds-warning-' + cssSelector)
		var warningSpan = $('#' + selector);
		warningSpan.empty();
		warningSpan.html(errorMessage);
	}
};

/*
 * Funzione che svuota tutti gli errori di un quadro
 */
_clearQuadroErrors = function(curModuloId,curQuadroId){
	
	if(!curModuloId) curModuloId = $('#id_modulo_attivo').val();
	if(!curQuadroId) curQuadroId = $('#quadro_attivo').val();
	var moduloErrMap = errorFieldsMap[curModuloId];
	if($.isArray(moduloErrMap)){
		var errDeleteCount = 0;
		var warnDeleteCount = 0;
		var quadroErrMap = moduloErrMap[curQuadroId];
		if($.isArray(quadroErrMap)){
			for ( var idSem in quadroErrMap) {
				var campoErrors = quadroErrMap[idSem];
				if($.isArray(campoErrors)){
					for ( var indexSuffix in campoErrors) {
						var errors = campoErrors[indexSuffix];
						if(errors && errors.length){
							//elimino tutti gli errori di quel campo a quel row index
							_displayErrorInFormField(false,curModuloId,curQuadroId,idSem,indexSuffix,undefined,errors[i]);
							//conto in n. di errori e/o di warnings da decrementare nel tab del modulo
							for ( var i = 0; i < errors.length; i++) {
								if(errors[i]){
									errDeleteCount++;
								}
								else{
									warnDeleteCount++;
								}
							}
						}
					}
				}
			}
		}
		moduloErrMap[curQuadroId] = undefined;
		var tabId;
		if(errDeleteCount > 0){
			tabId = "modulo-" + curModuloId + "-errors";
			_updateTabErrorsCount(tabId,-errDeleteCount);
			tabId = "quadro-" + curQuadroId + "-errors";
			_updateTabErrorsCount(tabId,-errDeleteCount);
		}
		if(warnDeleteCount > 0){
			tabId = "modulo-" + curModuloId + "-warnings";
			_updateTabErrorsCount(tabId,-warnDeleteCount);
			tabId = "quadro-" + curQuadroId + "-warnings";
			_updateTabErrorsCount(tabId,-warnDeleteCount);
		}
	}
};

/*
 * Funzione che aggiorna il conteggio degli errori nel tab di un quadro o di un modulo
 */
_updateTabErrorsCount = function(tabId, countIncrement){
	var tabSpan = $('#' + escapeStringForCssSelector(tabId));
	var isError = tabId.indexOf('warning') == -1;
	if(tabSpan.length > 0){
		var count = tabSpan.data('numErrors') || 0;
		count += countIncrement;
		tabSpan.data('numErrors',count);
		var tabLabel = tabSpan.children("LABEL");
		tabLabel.text('(' + count + ')');
		var ttipText = "";
		if(count == 0){
			tabSpan.attr('title',ttipText);
			tabSpan.hide();
		}
		else{
			ttipText =  "ATTENZIONE! Nel ";
			var index = tabId.indexOf('-');
			var tmp = "modulo";
			if(index > 0){
				tmp = tabId.substring(0,index);
			}
			ttipText += tmp + " sono presenti " + count ;
			if(!isError){
				tmp = " warnings";
			}
			else{
				tmp = " errori";
			}
			ttipText += tmp + " di validazione";
			tabSpan.attr('title',ttipText);
			tabSpan.show();
		}
	}
};

/*
 * Funzione invocata alla callback della conferma del quadro se non ci sono errori
 * o se ci sono errori e l'utente clicca su 'Procedi' nella dialog box 
 */
_procedi = function(){
	//se è stato specificato un quadro da visualizzare in seguito lo apro
	if(this.showNextQuadro){
		$('#riferimento_modulo_attivo').val(this.goToModuloRef);
		$('#id_modulo_attivo').val(this.goToModuloId);
		$('#titolo_modulo_attivo').val(this.goToModuloTitle);
		$('#quadro_attivo').val(this.goToQuadroId);
		//tabs.off("tabsselect",null, selectModuloTabHandler); evento select sostituito con beforeActivate a partire da JQuery UI 1.10
		tabs.off("tabsbeforeactivate",null, selectModuloTabHandler);
		//tabs.tabs("select","tab_" + this.goToModuloId); metodo select eliminato in jQuery UI 1.10
		//determino l'indice del modulo da attivare
		var modIndex = -1;
		var liid;
		var selectModulo = this.goToModuloId;
		var selectQuadro = this.goToQuadroId;
		$("#moduli_tabs >ul >li").each(function(index, item){
			liid = item.id;
			if(liid == "tab_" + selectModulo){
				modIndex = index;
				return false;
			}
		});
		tabs.tabs("option","active",modIndex);
		var tabsQuadriModulo = eval(TABS_QUADRI_MODULO_PREFIX + cleanStringForJsVariableName(this.goToModuloId));
		//tabsQuadriModulo.off("tabsselect",null, selectQuadroTabHandler); evento select sostituito con beforeActivate a partire da JQuery UI 1.10
		tabsQuadriModulo.off("tabsbeforeactivate",null, selectQuadroTabHandler);
		//tabsQuadriModulo.tabs("select","quadro_" + this.goToQuadroId); metodo select eliminato in jQuery UI 1.10
		//determino l'indice del quadro da attivare
		$("#modulo_" + escapeStringForCssSelector(this.goToModuloId) + " >ul >li").each(function(index, item){
			liid = item.id;
			if(liid == "tab_quadro_" + selectQuadro){
				modIndex = index;
				return false;
			}
		});
		tabsQuadriModulo.tabs("option","active",modIndex);
		//tabs.on("tabsselect",null, selectModuloTabHandler);evento select sostituito con beforeActivate a partire da JQuery UI 1.10
		tabs.on("tabsbeforeactivate",null, selectModuloTabHandler);
		//tabsQuadriModulo.on("tabsselect",null, selectQuadroTabHandler);evento select sostituito con beforeActivate a partire da JQuery UI 1.10
		tabsQuadriModulo.on("tabsbeforeactivate",null, selectQuadroTabHandler);
	}
	//se non è stato specificato il quadro di destinazione significa che si vuole sottomettere l'intera domanmda
	else if(this.isBloccato === false){
		presentaDomanda();
	}		
};

/*
 * Handler del bottone 'Avanti' presente in ogni quadro
 */
goToNextQuadro = function(){
	var idModuloCorrente = $('#id_modulo_attivo').val();
	var refModulo;
	var idModulo;
	var titoloModulo;
	var idQuadro;
	var attivo;
	var tabsQuadriModulo;
	var tabsQuadriModuloCorrente = eval(TABS_QUADRI_MODULO_PREFIX + cleanStringForJsVariableName(idModuloCorrente));
	var currentQuadroIndex = tabsQuadriModuloCorrente.tabs('option', 'active'); ;
	var numQuadri;
	//
	
	var currentModuloIndex = tabs.tabs('option', 'active');
	//var numModuli = tabs.tabs('length');
	var numModuli = $("#moduli_tabs >ul >li").size();
	//ciclo sui moduli a partire da quello corrente  
	while(currentModuloIndex < numModuli){
		//seleziono il primo quadro del modulo successivo
		refModulo = riferimentiModuli[currentModuloIndex];
		idModulo = idModuli[currentModuloIndex];
		titoloModulo = nomiModuli[currentModuloIndex];
	
		tabsQuadriModulo = eval(TABS_QUADRI_MODULO_PREFIX + cleanStringForJsVariableName(idModulo));
		//currentQuadroIndex = tabsQuadriModulo.tabs('option', 'selected'); 
		//numQuadri = tabsQuadriModulo.tabs('length');
		numQuadri = $("#modulo_" + escapeStringForCssSelector(idModulo) + " >ul >li").size();
		currentQuadroIndex++;
		//per ciascun modulo ciclo tutti i quadri a partire da quello corrente 
		while(currentQuadroIndex < numQuadri){
			var infoTabsQuadriModulo = eval(INFO_QUADRI_MODULO_PREFIX + cleanStringForJsVariableName(idModulo));
			attivo = infoTabsQuadriModulo[currentQuadroIndex].attivo;
			//appena trovo il primo quadro attivo esco dai cicli
			if(attivo === true){
				idQuadro = infoTabsQuadriModulo[currentQuadroIndex].idQuadro;
				break;
			}
			else{
				currentQuadroIndex++;
			}
		}
		if(idQuadro){
			break;
		}
		else{
			currentQuadroIndex = -1;
			currentModuloIndex++;
		}
	}
	/*
	* se c'è un quadro attivo successivo nello stesso modulo o in uno dei moduli successivi 
	* sottometto i dati del quadro corrente per passare al quadro attivo che ho trovato
	*/
	if(idQuadro){
		goToQuadro(refModulo, idModulo, titoloModulo, idQuadro);
	}
	//se non ci sono altri quadri attivi da compilare faccio partire la chiamata che trasmette la domanda completa al server
	else{
		invioModulistica();
	}
};

invioModulistica = function(){
	goToQuadro(false,false,false,false);
};

presentaDomanda = function(){
	$('#mainForm').submit();
};

/*
 * funzione che inizializza il controllo blueimpFileupload per lì'upload degli allegati
 *  e ne imposta le proprietà che definiscono il comportamento
 */
initializeMultiFile = function(inputElementId, fileTypeRestrictions, maxUploadNum, dbFilesJson, uploadParamsJson){
	var selector = escapeCssSelector(inputElementId);
	var multiFileOptions = {
			//accepts: fileTypeRestrictions,
			//maxUpload: maxUpload,
			url: contextPath + '/cart/ajaxUploadAllegatoDomanda.htm?disableMR=1',
			dataType: 'json',
			type: 'POST',
			formData: uploadParamsJson,
			uploadTemplateId: 'cart-template-upload',
			downloadTemplateId: 'cart-template-download',
			uploadTemplate: function(o){
				var uplTpl = $('#' + o.options.uploadTemplateId);
				var rendered = uplTpl.tmpl(o);
				return rendered;
			},
			downloadTemplate: function(o){
				var dwnlTpl = $('#' + o.options.downloadTemplateId);
				var rendered = dwnlTpl.tmpl(o);
				return rendered;
			},
			idSemantico: uploadParamsJson.idSemantico,
			inputName: inputElementId,
			sent: function (e, data) {
				var emptyValueHiddenField = $("#fileupload-" + selector).find('#empty-' + selector);
				if(emptyValueHiddenField.length > 0){
					$(this).data('blueimpFileupload').emptyValueField = emptyValueHiddenField.detach();
				}
			},
			destroyed: function (e, data) {
				var theControl = $(this).data('blueimpFileupload');
				var theRows = theControl.options.filesContainer.find('tr');
				if(theRows.length == 0 && theControl.emptyValueField){
					theControl.emptyValueField.appendTo("#fileupload-" + selector);
				}
			}
		};
	//$("#" + inputElementId).button();
	$("#fileupload-" + selector).fileupload();
	$("#fileupload-" + selector).fileupload('option',multiFileOptions);
	/*
	$("#fileupload-" + selector).bind('fileuploadsent', function (e, data) {
		var emptyValueHiddenField = $("#fileupload-" + selector).find('#empty-' + selector);
		if(emptyValueHiddenField.length > 0){
			$(this).data('blueimpFileupload').emptyValueField = emptyValueHiddenField.detach();
		}
	});
	$("#fileupload-" + selector).bind('fileuploaddestroy', function (e, data) {
		var theControl = $(this).data('blueimpFileupload');
		var theRows = theControl.options.filesContainer.find('tr');
		if(theRows.length = 0 && theControl.emptyValueField){
			theControl.emptyValueField.appendTo("#fileupload-" + selector);
		}
	});
	*/
	if(dbFilesJson){
		$("#fileupload-" + selector).fileupload('option', 'done').call($("#fileupload-" + selector), $.Event('done'), {result: dbFilesJson});
		$("#fileupload-" + selector).fileupload('option', 'sent').call($("#fileupload-" + selector), $.Event('sent'), {result: dbFilesJson});
	}
	$("#fileupload-" + selector).data('blueimpFileupload').enableDsValidation(uploadParamsJson.dsValidation);
};

/*
 * Funzione che inizializza i controlli numerici
 */
initializeNumericField = function(inputElementId, maxDecimals, minValue, maxValue){
	
	var autoNumericOptions = {
			aSep: '.',
			aDec: ','
			//,aPad: false disabilita il padding automatico con zeri delle cifre decimali
	};
	if(maxDecimals != undefined && maxDecimals > -1){
		autoNumericOptions.mDec = maxDecimals;
	}
	if(minValue != undefined){
		autoNumericOptions.vMin = minValue;
	}
	if(maxValue != undefined){
		autoNumericOptions.vMax = maxValue;
	}
	$('#' + escapeStringForCssSelector(inputElementId)).autoNumeric(autoNumericOptions);
};

/*
 * Funzione che inizializza le funzionalità delle tabelle 
 */
initializeTable = function(tableId, maxRighe, numRighe, newRowIndex){
	var selector = escapeStringForCssSelector(tableId);
	var eventData = {idTabella: tableId};
	$('#bottone_' + selector).button();
	$('#bottone_' + selector).bind('click',eventData, addNewTableRow);
	var table = $('#' + selector);
	table.eq(0).data('maxRighe', maxRighe);
	table.eq(0).data('numRighe', numRighe);
	if(!jQuery.isNumeric(maxRighe) && maxRighe == 'unbounded'){
		maxRighe = 100000;
	}
	else{
		maxRighe = Number(maxRighe);
	}
	if(maxRighe == numRighe){
		$('#bottone_' + selector).button("disable");
	}
	if("" + newRowIndex == "undefined"){
		for(var i = 0; i < numRighe; i++){ 
			$("#" + selector + "_row_" + i + "_delbutton").button();
			var eventData = {
				idTabella: tableId,
				rowIndex: i
			};
			$("#" + selector + "_row_" + i + "_delbutton").bind('click', eventData, deleteTableRow);
		}
	}
	else{
		$("#" + selector + "_row_" + newRowIndex + "_delbutton").button();
		var eventData = {
			idTabella: tableId,
			rowIndex: newRowIndex
		};
		$("#" + selector + "_row_" + newRowIndex + "_delbutton").bind('click', eventData, deleteTableRow);
	}
}

/*
 * Funzione che modifica lo stile css di un controllo in base al fatto che sia o meno obbligatorio
 */
setMandatoryField = function(mandatoryFlag, cssSelector){
	if(cssSelector){
		var field = $('[name = "' + cssSelector + '"]');
		if(field && field.length > 0){
			if(mandatoryFlag){
				field.addClass('mandatory');
			}
			else{
				field.removeClass('mandatory');
			}
		}
		var fieldContainer;
		field = field.parent();
		while(field && field.length > 0 && !field.hasClass('campo-cart-container')){
			field = field.parent();
		}
		if(field && field.length > 0 && field.hasClass('campo-cart-container')){
			if(mandatoryFlag){
				field.addClass('campo-cart-container-obbligatorio');
			}
			else{
				field.removeClass('campo-cart-container-obbligatorio');
			}
		}
	}
};

/*
 * Handler del bottone 'Torna a selezione endoprocedimenti'
 */
returnToEndo = function(){
	$('#mainForm').attr('action',contextPath + '/cart/elencoEndo.htm');
	$('#mainForm').submit();
};
/*
 * funzione di utilità che restituisce il suffisso "_row_n_m_x_y" per i campi con valori indicizzati a partire dall'array di indici passato come argomento. 
 */
buildIndexingSuffix = function(indexes){
	var suffix = "";
	if(indexes && indexes.length){
		suffix = INDEXING_SUFFIX;
		for (var i = 0; i < indexes.length; i++) {
			suffix += indexes[i];
			if(i < indexes.length-1){
				suffix += "_";
			}
		}
	}
	return suffix;
};

/*
 * Funzione di callabck generica per la gestione di errori AJAX
 */
ajaxErrorCallback = function(jqXHR, errorType, exception){
	$.unblockUI();
	var errMsg = "";
	//if(errorType)errMsg += "tipo errore: " + errorType + " ";
	if(exception){
		if(exception.message){
			exception = exception.message;
		}
		errMsg += "messaggio: " + exception;
	}
	var status = jqXHR.status;
	if(status){
		errMsg += "<br/>Status: " + status;
	}
	showDialog("Si è verificato un errore nella chiamata al server: " + errMsg,"Errore");
};

adjustGridLayout = function(showingItem){
	if(showingItem){
		var td = $(showingItem).parents('td.column-cart');
		//se l'elemento che si sta rendendo visibile si trova in una tabella
		if(td.length > 0){
			//verifico se tutte le etichette cart visibili della cella sono vuote
			var labels = td.find('div.label-cart:visible');
			var hasLabel = false; 
			for(var i = 0; i < labels.length && !hasLabel; i++){
				var label = $(labels[i]).text();
				hasLabel = $.trim(label).length > 0;
			}
			//modifico la classe di tutti i div dei campi cart contenuti nella cella
			var cartFields = td.find('div.campo-cart');
			var classToSet, classToRemove;
			if(hasLabel){
				classToSet = 'campo-cart-right';
				classToRemove = 'campo-cart-left';
			}
			else{
				classToSet = 'campo-cart-left';
				classToRemove = 'campo-cart-right';
			}
			cartFields.addClass(classToSet);
			cartFields.removeClass(classToRemove);
		}
	}
};

/* FUNZIONI DI UTILITA' GENERALE' */
cleanStringForJsVariableName = function(toClean){
	toClean = "" + toClean;
	toClean = toClean.replace(/ /g,"_");
	toClean = toClean.replace(/\./g,"_");
	return toClean.replace(/-/g,"_");
};

escapeStringForCssSelector = function(toEscape){
	toEscape = "" + toEscape;
	toEscape = toEscape.replace(/(\.)/g,"\$1");
	return toEscape;
};

