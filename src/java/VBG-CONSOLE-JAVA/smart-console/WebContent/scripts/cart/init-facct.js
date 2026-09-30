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

const DIABLED_FIELD_PREFIX = "_DIS_";
const INDEXED_FIELDNAME_SEPARATOR = "_row_";
const DYNFIELD_NAME_PREFIX = "FLD_";

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
	$('.icon-firmadigitale').tooltip();
	
	//tutti i campi non indicizzati vengono tenuti allineati con campi omonimi in sola lettura
	var fields = jQuery('input:text').add('input:radio').add('input:checkbox').add('select').add('textarea').filter(function(index,element){
		var retVal = false;
		if(element && element.name){
			if(element.name.indexOf(INDEXED_FIELDNAME_SEPARATOR) == -1){
				retVal = !jQuery(element).hasClass("readonly-field");
			}
		}
		return retVal;
	});
	fields.change(refreshFieldsHandler);
};


blockUIIfNeeded = function(){
	if(blockUIEnabled && typeof jQuery.blockUI == 'function'){
		jQuery.blockUI();
	}
};

unblockUIIfNeeded = function(){
	if(blockUIEnabled && typeof jQuery.unblockUI == 'function'){
		jQuery.unblockUI();
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
			var indexPos = idTabellaForCss.indexOf("_i_");
			var indexes = [];
			if(indexPos > -1){
				var subSuffix = idTabellaForCss.substring(indexPos+3);
				if(subSuffix.length > 0){
					indexes = subSuffix.split('_');
				}
			}
			indexes[indexes.length] = maxProg;
			var template = $("#template_" + idTabellaForJs);
			if(template && template.length > 0){
				//$.tmpl(template,{index: maxProg}).appendTo(tabella.find('tbody'));
				var populatedRow = template.tmpl({index: indexes});
				populatedRow.find('.help').tooltip();
				populatedRow.find('.help_image').tooltip();
				populatedRow.find('.error_image').tooltip();
				populatedRow.find('.warning_image').tooltip();
				populatedRow.find('.icon-firmadigitale').tooltip();
				populatedRow.appendTo(tabella.children('table').children('tbody'));
			}
			//aggiungo il campo hidden che consente di ricavare lato server l'indice dell'id semantico a cui punta ciascuna riga delle tabelle
			//$("#" + idTabellaForCss).prepend("<input type='hidden' name='" + idTabella + INDEXED_FIELDNAME_SEPARATOR + maxProg + "_dataindex' id='" + idTabella + INDEXED_FIELDNAME_SEPARATOR + maxProg + "_dataindex' value='" + maxIndex + "'/>");
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
	var indexingSuffix = buildIndexingSuffix(newRowIndex);
	$("#" + idTabellaForCss + indexingSuffix + "_delbutton").button();
	var eventData = {
		idTabella: idTabella,
		rowIndex: newRowIndex
	};
	$("#" + idTabellaForCss + indexingSuffix + "_delbutton").bind('click',eventData,deleteTableRow);
}

/*
* Handler che gestisce la cancellazione delle righe delle tabelle
*/
deleteTableRow = function(event){
	var evtsrc = event.target;
	var parents = jQuery(evtsrc).parents("TR");
	if(parents.length > 0){
		var removing = parents.first();
		var preserveScripts = removing.find("SCRIPT");
		preserveScripts.detach();
		preserveScripts.appendTo("BODY");
		removing.remove();
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

//restituisce un oggetto jQuery che contiene il/i campo/i corrispondente all'id semantico passato
//il campo viene cercato prima con un nome non indicizzato, se non trovato, viene cercato nella versione indicizzata
//in quest'ultimo caso se forIndex è speciifcato verrà cercato con il nome che corrispondente all'indice, 
//se non specificato vengono cercati tutti i campi con suffisso indicizzato a qualunque indice
findAnyInputByIdSemanticoForActiveIndex = function(idSemantico,forIndexSuffix){
	//il campo viene cercato prima con un nome non indicizzato
	var formFields = $('[name ^= "[' + idSemantico + ']"]').add('[name ^= "' + DIABLED_FIELD_PREFIX + '[' + idSemantico + ']"]');
	if(formFields.length == 0){
		//se non trovo il campo lo cerco nella versione indicizzata
		//se non è specificato forIndexSuffix cerco tutti i campi indicizzati 
		forIndexSuffix = "" + forIndexSuffix;
		forIndexSuffix = $.trim(forIndexSuffix);
		if(forIndexSuffix == "undefined" || forIndexSuffix == ""){
			formFields = $('[name ^= "[' + idSemantico + '_row_"]').add('[name ^= "' + DIABLED_FIELD_PREFIX + idSemantico + '_row_"]');
		}
		//se specificato forIndexSuffix cerco il campo indicizzato allo stesso indice
		else{
			//forIndexSuffix viene passato con "_i_" anzichè "_row_" se la funzione è invocata per l'attivazione dinamica di una tabella
			forIndexSuffix.replace(new RegExp('i_', 'g'), 'row_');
			formFields = $('[name ^= "[' + idSemantico + forIndexSuffix + ']"]').add('[name = "' + DIABLED_FIELD_PREFIX + idSemantico + forIndexSuffix + '"]');
		}
	}
	return formFields;
};

//restituisce i valori dei campi passati in input come array di stringhe
/*
readValuesFromElements = function(jQueryObj){
	var values = [];
	var fields = _scanInputFields(jQueryObj);
	fields.each(function(index,elem){
		if(elem.tagName.toUpperCase() == "SELECT"){
			values = values.concat($(elem).val());
		}
		else if(elem.tagName.toUpperCase() == "INPUT"){
			if(elem.type.toUpperCase() == "CHECKBOX" || elem.type.toUpperCase() == "RADIO"){
				if(elem.checked){
					values = values.concat(jQuery(elem).val());
				}
			}
			else{
				values = values.concat(jQuery(elem).val());
			}
		}
	});
	return values;
};
*/

/**
 * imposta il valore 'value' nei campi di input contenuti nell'oggetto jQuery passato come primo argomento,
 * il valore passato può anche essere un'array di valori per impostare valori multipli in listbox o gruppi di checkbox,
 * i campi di input in cui impostare i valori passati possono anche essere figli degli elementi dom selezionati nell'oggetto jQuery
 */
setFieldValues = function(jq, values){
	var arrValues;
	if(!jQuery.isArray(values)){
		arrValues = [];
		arrValues[0] = values;
	}
	else{
		arrValues = values;
	}
	if(jq && jq.length > 0){
		var fields = _scanInputFields(jq);
		fields.val(arrValues);
	}
};

refreshFieldsHandler = function(evt){
	if (evt && evt.target) {
		var inputName = jQuery(evt.target).attr('name');
		var fieldIdAttr = "id";
		var containerSelector = "DIV.campo-cart-container";
		if(inputName.indexOf(DYNFIELD_NAME_PREFIX) != 0 ){
			if(inputName.indexOf(INDEXED_FIELDNAME_SEPARATOR) > -1){
				return true;
			}
			inputName = inputName.substring(0,inputName.lastIndexOf(']')+1);
		}
		else{
			containerSelector = "DIV.controllo";
			//fieldIdAttr = "dynmdid";
		}
		//cerco campi non indicizzati associati allo stesso id semantico
		if(inputName.length > 0){
			var evtgt = jQuery(evt.target);
			var targetDiv = evtgt.parents(containerSelector);
			var fieldId = targetDiv.attr(fieldIdAttr);
			if(fieldId == undefined){
				fieldId = "";
			}
			var readOnlyFields = jQuery('[name ^= ' + escapeStringForCssSelector(inputName) + ']');
			//li elaboro a gruppi come appaiono nella UI in modo da gestire i gruppi di checkbox e radiobuttons come un unico controllo
			var uiGroups = {};
			var inputTags = ['INPUT','SELECT','TEXTAREA']; 
			readOnlyFields.each(function(index,elem){
				var container = jQuery(elem).parents(containerSelector);
				/*
				 * escludo elementi che non sono campi di input (per evideziare in giallo i checkbox e i radiobuttons 
				 * obbligatori è stato assegnato l'attributo name anche al loro div contenitore).
				 * escludo gli eventuali INPUT che fanno parte dello stesso gruppo in cui si è scatenato l'evento
				 */
				if(container.length == 1 && container.attr(fieldIdAttr) != fieldId && jQuery.inArray(elem.tagName, inputTags) > -1){
					var groupId = container.attr(fieldIdAttr);
					var group = uiGroups[groupId] != undefined ? uiGroups[groupId] : jQuery();
					group = group.add(jQuery(elem));
					uiGroups[groupId] = group;
				}
			});
			for (var key in uiGroups) {
				var val = readValuesFromElements(targetDiv);
				setFieldValues(uiGroups[key], val);
			}
			/*
			var readOnlyFields = jQuery('[name ^= ' + escapeStringForCssSelector(inputName) + ']').filter(function(index,item){
				var container = jQuery(item).parents(containerSelector);
				return container.length == 1 && container.attr(fieldIdAttr) != fieldId;
			});
			//e vi imposto lo stesso valore del campo su cui si è scatenato l'evento
			readOnlyFields.each(function(index,elem){
				//if (jQuery(elem).hasClass('readonly-field')) {
					setFieldValues(jQuery(elem), jQuery(evt.target).val());
				//}
			});
			*/
		}
	} 
};

refreshDynFields = function(evt){
	
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
		//elimino i campi file che danno errore in IE8
		var fileUploads = form.find("input:file");
		var containers = fileUploads.parent();
		fileUploads.detach();
		//e li metto nel context insieme ai loro contenitori per poterli riposizionare nella callback
		var callbackContext = {
				goToModuloRef: toModuloRef,
				goToModuloId: toModuloId,
				goToModuloTitle: toModuloTitle,
				goToQuadroId: toQuadroId,
				fileFields: fileUploads,
				fileContainers: containers
		};	
		form.ajaxSubmit({
				  url: contextPath + "/cart/ajaxConfermaQuadro.htm?", 
				  type: "POST", 
				  data: extraParams, 
				  dataType: "json",
				  success: goToQuadroCallback,
				  error: ajaxErrorCallback,
				  complete: _restoreFileUploads,
				  //beforeSubmit: _handleFileFieldsOnSubmit,
				  context: callbackContext
		});
		/*
		fileUploads.each(function(index, item){
			$(item).appendTo(containers[index]);
		});
		*/
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
	var inputsToClear = data.filesToClear;
	if(inputsToClear != undefined){
		//svuoto gli elenchi dei files caricati da tutti i campi input:file specificati in inputsToClear
		var cssSelector;
		var indexingSuffix;
		var fileField;
		for (var int = 0; int < inputsToClear.length; int++) {
			fileField = inputsToClear[int];
			indexingSuffix = buildIndexingSuffix(fileField.indexes);
			cssSelector = "\\[" +  escapeStringForCssSelector(fileField.idSemantico) + indexingSuffix + "\\]" + fileField.idCampo;
			fileField = $('#fileupload-' + cssSelector);
			if(fileField && fileField.length > 0){
				fileField = fileField.data('blueimpFileupload');
				var fContainer = fileField.options.filesContainer;
				fContainer.empty();
				/*
				fileField.fileupload('option', 'done').call(fileField, $.Event('done'), {result: []});
				fileField.fileupload('option', 'sent').call(fileField, $.Event('sent'), {result: []});
				*/
			}
		}

	}
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
					_displayErrorInFormField(true,errors[i].idModulo,errors[i].idQuadro,errors[i].idSemantico,errors[i].idCampo, indexingSuffix, messages[y].message,!isBloccante);
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

_handleFileFieldsOnSubmit = function(params,jqForm,opts){
	for(var i = 0; i < params.length; ){
		if(params[i] && params[i].name && params[i].name == "files[]"){
			params.splice(i,1);
		}
		else{
			i++;
		}
	}
};

_restoreFileUploads = function(jqXHR, status){
	var containers = this.fileContainers;
	this.fileFields.each(function(index, item){
		$(item).appendTo(containers[index]);
	});	
};

/*
 * Funzione che visualizza un'errore di validazione accanto ad un campo della modulistica
 */
_displayErrorInFormField = function(errorFlag,idModulo,idQuadro,idSemantico,idCampo,indexingSuffix,errorMessage,isWarning){
	
	var flagStartsWith = false;
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
	if(!idCampo){
		idCampo = "";
		flagStartsWith = true;
	}
	var cssSelector = "\\[" + idSemantico + indexingSuffix + "\\]" + idCampo;
	//cssSelector = escapeStringForCssSelector(cssSelector);
	var field;
	if(flagStartsWith){
		field = $('[name ^= "' + cssSelector + '"]');
	}
	else{
		field = $("[name='" + cssSelector + "']");
	}
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
		var selector = escapeStringForCssSelector('ds-warning-' + cssSelector);
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
							_displayErrorInFormField(false,curModuloId,curQuadroId,idSem,undefined,indexSuffix,undefined,undefined);
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
	var selector = escapeStringForCssSelector(inputElementId);
	var multiFileOptions = {
			acceptFileTypes: fileTypeRestrictions,
			//maxUpload: maxUpload,
			url: contextPath + '/cart/ajaxUploadAllegatoDomanda.htm?disableMR=1',
			dataType: 'json',
			type: 'POST',
			formData: uploadParamsJson,
			uploadTemplateId: 'cart-template-upload',
			downloadTemplateId: 'cart-template-download',
			autoUpload: true,
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
initializeNumericFieldFacct = function(inputElementId, maxDecimals, minValue, maxValue){
	
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
			$("#" + selector + INDEXED_FIELDNAME_SEPARATOR + i + "_delbutton").button();
			var eventData = {
				idTabella: tableId,
				rowIndex: i
			};
			$("#" + selector + INDEXED_FIELDNAME_SEPARATOR + i + "_delbutton").bind('click', eventData, deleteTableRow);
		}
	}
	else{
		$("#" + selector + INDEXED_FIELDNAME_SEPARATOR + newRowIndex + "_delbutton").button();
		var eventData = {
			idTabella: tableId,
			rowIndex: newRowIndex
		};
		$("#" + selector + INDEXED_FIELDNAME_SEPARATOR + newRowIndex + "_delbutton").bind('click', eventData, deleteTableRow);
	}
};

/*
 * Funzione che modifica lo stile css di un controllo in base al fatto che sia o meno obbligatorio
 */
setMandatoryField = function(mandatoryFlag, fieldName){
	//fieldName passato è l'id del DIV che contiene il controllo da rendere obbligatorio
	if(fieldName){
		fieldName = escapeStringForCssSelector(fieldName);
		var field = $('#' + fieldName);
		//se non trovo il campo per id = valore cerco per nome inizia per valore
		if(field.length == 0){
			field = $("[name^='" + fieldName + "']");
		}
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
			var childInput = field.find('INPUT').add(field.find('SELECT')).add(field.find('TEXTAREA'));
			if(childInput && childInput.length > 0){
				if(mandatoryFlag){
					if(childInput.parent().hasClass('check-radio-wrap')){
						childInput.parent().addClass('mandatory');
					}
					childInput.addClass('mandatory');
				}
				else{
					if(childInput.parent().hasClass('check-radio-wrap')){
						childInput.parent().removeClass('mandatory');
					}
					childInput.removeClass('mandatory');
				}
			}
		}
	}
};


if (!String.prototype.iniziaCon) {
    String.prototype.iniziaCon = function(searchString, position){
      position = position || 0;
      return this.substr(position, searchString.length) === searchString;
  };
}

renameInputNameOnDisable = function(element,enableFlag){
	if (element) {
		var jqElement = jQuery(element);
		var currName = jqElement.attr('name');
		var isCheckbox = jqElement.attr('type') == undefined ? false : 'CHECKBOX' == jqElement.attr('type').toUpperCase();
		if(currName){
			if(enableFlag){
				if(currName.iniziaCon(DIABLED_FIELD_PREFIX)){
					currName = currName.substring(DIABLED_FIELD_PREFIX.length);
					//jqElement.attr('name',currName);
					element.setAttribute("name", currName);
					if(isCheckbox){
						jqElement.prop('checked',jqElement.data('was-checked'));
						jqElement.removeData('was-checked');
					}
				}
			}
			else{
				if(!currName.iniziaCon(DIABLED_FIELD_PREFIX)){
					currName = DIABLED_FIELD_PREFIX + currName;
					//jqElement.attr('name',currName);
					element.setAttribute("name", currName);
					if(isCheckbox){
						jqElement.data('was-checked',jqElement.prop('checked'));
						jqElement.prop('checked',true);
					}
				}
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

function setCheckValue(obj, elementName){
	if($(obj).attr('checked')=='checked'){
		$('[name ^= "'+elementName+'"]').val($(obj).val());
	}else{
		$('[name ^= "'+elementName+'"]').val('');
	}
}

