/*
 * FUNZIONI JAVASCRIPT GENERICHE UTILIZZATE NELLE SCHEDE DINAMICHE
 *
 *
 */
	
	function settaValoreCheckbox(obj){
		var hiddenField = jQuery("#" + obj.id.replace('TMP_',''));
		var checkField = jQuery(obj);
		var valoreTrue = checkField.data('ValoreTrue');
		if(valoreTrue == undefined){
			valoreTrue = 1;
		}
		var valoreFalse = checkField.data('ValoreFalse');
		if(valoreFalse == undefined){
			valoreFalse = 0;
		}
		if(obj.checked){
			hiddenField.val(valoreTrue);
		}else{
			hiddenField.val(valoreFalse);
		}
		//le regole di attivazione vengono scatenate all'onchange sul campo hidden
		hiddenField.change();
	}
	
	function validaCampo(obj){
		dataChanged=true;
		infoWarningMsg();
		var objId = obj.id;
		var idIdx = objId.toUpperCase().lastIndexOf("_ID");
		if(idIdx > -1){
			objId = objId.substring(0, idIdx + 3);
		}
		var spanErrors = jQuery("#"+ objId +"_ERRORS");
		if(!spanErrors){
			spanErrors = jQuery("#_debugInfoId");
		}
		var jqxhr = jQuery.ajax({
			  url: "ajaxValidaCampo.htm?codiceModello=${schedaH.scheda.codice}",
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  data: "idField=" + obj.id + "&valore=" + obj.value,
			  success: function(data, textStatus, jqXHR) {
				  spanErrors.html(data).show();
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  spanErrors.html(textStatus+": "+errorThrown).show();
			  }	
		});	
	}

	function eliminaBlocco(numRiga, indiceBlocco){
		//TODO modificare
		/*
		if(changeData()){
			jQuery("#schede_form").attr("action","eliminaBlocco.htm?numeroRiga="+numRiga+"&indice=0&indiceMolteplicita="+indiceMolteplicita);
			jQuery("#schede_form").submit();
		}
		*/
		//div del blocco da eliminare
		var delBlockDiv = jQuery('DIV#BLOCK_' + numRiga + '_' + indiceBlocco);
		//div da cui rimuovere il blocco
		var blocskDiv = jQuery('DIV#BLOCK_' + numRiga);
		//indice a cui si trova il blocco da rimuovere
		var delIndex = blocskDiv.children().index(delBlockDiv);
		if(delIndex > -1){
			//elimino il blocco dal DOM
			delBlockDiv.remove();
			//TODO modifico gli indici negli attributi name e id nei blocchi successivi a quello eliminato. Per ora non sembra necessario.
			
		}
	}
	
	function aggiungiBlocco(numRiga){
		/*
		if(changeData()){
			jQuery("#schede_form").attr("action","aggiungiBlocco.htm?numeroRiga="+numRiga+"&indice=0&indiceMolteplicita="+indiceMolteplicita);
			jQuery("#schede_form").submit();
		}
		*/
		//recupero il div in cui aggiungere un blocco
		var divBlocchi = jQuery('DIV#BLOCK_' + numRiga);
		//conto le righe già presenti
		var numBlocchi = divBlocchi.children().length;
		//recupero il template per la nuova riga
		var template = jQuery("#template_blocco_" + numRiga);
		if(template && template.length > 0){
			//creo il dom per la nuova riga e lo aggiungo al div dei blocchi
			template.tmpl({indicePh: numBlocchi}).appendTo(divBlocchi);
		}
		var newBlockDiv = jQuery('DIV#BLOCK_' + numRiga + '_' + numBlocchi);
		var invoke = "attivaNuovaRiga_" + numRiga + "(" + numBlocchi + ");";
		eval(invoke);
		initUIControls(newBlockDiv);
	}

	function settaRicercaDyncampi(idInputField, codiceCampo, dataSrcUrl, options){
		if(options == undefined){
			options = {};
		}
		if("" + dataSrcUrl == "undefined"){
			dataSrcUrl = "../ajax/findRicercaDyncampi.htm";
		}
		var paramsStartIndex = dataSrcUrl.indexOf("?", 0);
		if(paramsStartIndex > -1){
			dataSrcUrl = dataSrcUrl.substring(0, paramsStartIndex);
		}
		jQuery( "#"+idInputField ).attr('placeholder','inizia a digitare o premi ↓ per attivare la ricerca');
		jQuery( "#"+idInputField ).autocomplete({
			source: dataSrcUrl + "?codiceCampo="+codiceCampo,
			minLength: 0,
			create: function( event, ui ) {
				if(jQuery("#"+idInputField+"_HIDDEN").val()==''){
					jQuery("#"+idInputField).val('');
				}
				if(jQuery("#"+idInputField).val()==''){
					jQuery("#"+idInputField+"_HIDDEN").val('');
				}
			},
			select: function( event, ui ) {
				if(ui.item){
					jQuery("#"+idInputField+"_HIDDEN").val(ui.item.id);
				}else{
					jQuery("#"+idInputField+"_HIDDEN").val('');
					jQuery("#"+idInputField).val('');
				}
			},
			change: function(event, ui) {
				
					if (ui.item) {
						jQuery("#" + idInputField + "_HIDDEN").val(ui.item.id);
					} else {
						if (options.forza_valori_esistenti) {
							jQuery("#" + idInputField + "_HIDDEN").val('');
							jQuery("#" + idInputField).val('');
						}
						else{
							jQuery("#" + idInputField + "_HIDDEN").val(jQuery("#" + idInputField).val());
						}
					}
					//validaCampo(jQuery("#"+idInputField)[0]);
			}
		});
	}
	
	function initializeNumericField(inputElementId, maxDecimals, minValue, maxValue){
		
		var autoNumericOptions = {
    			aSep: '',
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
		jQuery('#' + inputElementId).autoNumeric(autoNumericOptions);
	}
	
	function _scanInputFields(jq){
		//recupero dall'oggetto jquery solo i campi di tipo input, select o textarea fra quelli selezionati o nei nodi figli
		var fields = jq.filter("input").add(jq.filter("select")).add(jq.filter("textarea"));
		if(fields.length == 0){
			fields = jq.find("input").add(jq.find("select")).add(jq.find("textarea"));
		}
		return fields;
	}

	function readValuesFromElements(jQueryObj, idx, idxMulti, idxBlocco, stessoBlocco){
		var values = [];
		if(stessoBlocco){
			jQueryObj = filterElementsByMultiplicity(jQueryObj, idx, idxMulti, idxBlocco);
		}
		var fields = _scanInputFields(jQueryObj);
		fields.each(function(index,elem){
			var value;
			if(elem.tagName.toUpperCase() == "SELECT"){
				value = jQuery(elem).val();
			}
			else if(elem.tagName.toUpperCase() == "INPUT" || elem.tagName.toUpperCase() == "TEXTAREA"){
				if(elem.type.toUpperCase() == "CHECKBOX" || elem.type.toUpperCase() == "RADIO"){
					if(elem.checked){
						value = jQuery(elem).val();
					}
				}
				else{
					value = jQuery(elem).val();
				}
			}
			if(value == undefined || value == null){
				value = "";
			}
			values = values.concat(value);
		});
		return values;
	}
	
	function activateElements(activate, handleRegola, idx, idxMulti, idxBlocco, stessoBlocco){
		
		var activeDom = jQuery("[dynid='" + handleRegola + "']");
		var filterSelector = "[dynblock='" + idxBlocco + "']";
		//escludo i campi che non fanno parte del blocco e recupero solo quelli allo stesso indice molteplicità
		if(stessoBlocco){
			activeDom = filterElementsByMultiplicity(activeDom.filter(filterSelector), idx, idxMulti, idxBlocco);
		}
		//escludo i campi che fanno parte del blocco e non tengo conto della molteplicità
		else{
			activeDom = activeDom.not(filterSelector);
		}
		 displayElements(activeDom,activate);
	}
	
	function displayElements(jq, doDisplay, onDisplay){
		
		if(jq && jq.length > 0){
			//memorizzo lo stato di attivazione/disattivazione nell'attributo dell'elemento dom 'is-active' e non in tutti i campi di input figli
			jq.data('is-active',doDisplay);
			if(doDisplay){
				jq.show();
			}
			else{
				jq.hide();
			}
			var jqInput = jq.find('input').not(':file').not(':button').not('.facct_internal');
			jqInput = jqInput.add(jq.find('SELECT')).add(jq.find('TEXTAREA'));
			//jqInput = jQuery(':input:not(.facct_intenal)');
			jqInput.each(function(index,element){
				var jqElement = jQuery(element);
				//non escludo i campi hidden
				/*
				if(element.tagName.toUpperCase() == 'INPUT' && element.type.toUpperCase() == 'HIDDEN'){
					return;
				}
				if(jqElement.is(':checkbox')){
					//settaValoreCheckbox(jqElement[0],jqElement[0].id.replace('TMP_',''));
					jqElement = jQuery('#' + jqElement[0].id.replace('TMP_',''));
				}
				*/
				var nowHidden = isHidden(jqElement);
				//se precedentemente era attivo
				if(jqElement.data('disabled-datum') != true){
					//e l'ho disattivato
					if(nowHidden){
						//svuoto il valore del campo e lo memorizzo nella proprietà 'value-storage' del campo stesso (per poterlo ripristinare alla riattivazione)
						jqElement.data('value-storage',jqElement.val());

						jqElement.val('');
						
						jqElement.data('disabled-datum',true);
					}
				}
				//se precedentemente era disattivo
				else{
					//e l'ho attivato
					if(!nowHidden){
						//ripopolo il valore del campo con il valore memorizzato nella proprietà 'value-storage' del campo stesso
						jqElement.val(jqElement.data('value-storage'));
						jqElement.data('value-storage','');
						jqElement.data('disabled-datum',false);
					}
				}
				if(onDisplay){
					onDisplay.apply(this,[element,!nowHidden]);
				}
				jqElement.change();
			});
		}
	}
	
	function isHidden(jq){
		//TODO un campo è nascosto se attributo 'is-active' === false o se questo è vero per uno qualunque degli elementi a livello superiore nell'albero del DOM
		
		var hidden = jq.data('is-active') == false;
		if(!hidden){
			var parents = jq.parentsUntil('.modulo-cart');
			
			for (var ix = 0;ix < parents.length; ix++) {
				jq = $(parents[ix]);
				hidden = jq.data('is-active') == false;
				if(hidden){
					break;
				}
			}
		}
		return hidden;
	}
	
	function isDatumDisabled(jq){
		
	}
	
	function filterElementsByMultiplicity(jq, idx, idxMulti, idxBlocco){
		if("" + idxMulti != "undefined" && idxMulti > -1 && "" + idxBlocco != "undefined" && idxBlocco > -1){
			//chiamata proveniente da campoindicizzato
			jq = jq.filter(function(index,element){
			var idxBloccoAttr = getFieldBlockIndex(element);
			//elemento appartenente allo stesso blocco
			if(idxBloccoAttr == idxBlocco){
				//attivo solo gli elementi alla stessa molteplicità 
				var idxMultiAttr = getFieldMultiplicity(element);
				return idxMultiAttr == idxMulti;
			} 
			//elemento appartenente ad un altro blocco
			else{
				//attivo tutti gli elementi del blocco
				return true;
			}
			});
		}
		return jq;
	}
	
	function getFieldMultiplicity(field){
		return readParentHandleIndex(field,'dynidxm');
	}
	
	function getFieldIndex(field){
		return readParentHandleIndex(field,'dynidx');
	}
	
	function getFieldBlockIndex(field){
		return readParentHandleIndex(field,'dynblock');
	}

	function readParentHandleIndex(field,attribute){
		var retval = -1;
		if(field){
			if(field.selector == undefined){
				field = jQuery(field);
			}
			var parentNode = false;
			if(field.is("DIV.controllo") || field.is("SPAN.etichettaControllo")){
				parentNode = field;
			}
			if(!parentNode || parentNode.length == 0){
				if(field.is("TR.rigaModello")){
					parentNode = field;
				}
			}
			if(!parentNode || parentNode.length == 0){
				parentNode = field.parents("DIV.controllo").first();
			}
			if(!parentNode || parentNode.length == 0){
				parentNode = field.parents("SPAN.etichettaControllo").first();
			}
			if(!parentNode || parentNode.length == 0){
				parentNode = field.parents("TR.rigaModello").first();
			}
			if(parentNode && parentNode.length == 1){
				var tempVal = parentNode.attr(attribute);
				if(tempVal != undefined && tempVal.length > 0){
					tempVal = Number(tempVal);
					if(!isNaN(tempVal)){
						retval = tempVal;
					}
				}
			}
		}
		return retval;
	}
	
	function valueEquals(values, compareVal){
		
		var equals = false;
		if(!jQuery.isArray(values)){
			values = [values];
		}
		for(var i = 0; i < values.length; i++){
			var value = values[i];
			if(typeof compareVal == "number"){
				value = value.replace(',','.');
				var numValue = Number(value);
				if(!isNaN(numValue)){
					equals = numValue == compareVal;
				}
				else{
					equals = (values[i] == "" + compareVal) || (value == "" + compareVal);
				}
			}
			else if(isJsDate(compareVal)){
				var date = getJsDate(value);
				if(date){
					equals = date.compare(compareVal) == 0;
				}
				else{
					strDate = compareVal.day + "/" + compareVal.month + "/" + compareVal.year;
					equals = strDate == value;
				}
			}
			else{
				equals = value.toLowerCase() == compareVal.toLowerCase();
			}
			if(equals){
				break;
			}
		}
		return equals;
	}
	
	function valueCompare(isGreater, isEqual, values, compareVal){
		
		var isTrue = false;
		if(!jQuery.isArray(values)){
			values = [values];
		}
		for(var i = 0; i < values.length; i++){
			var value = values[i];
			if(typeof compareVal == "number"){
				value = value.replace(',','.');
				var numValue = Number(value);
				if(!isNaN(numValue)){
					isTrue = isGreater ? numValue > compareVal : numValue < compareVal;
					if(isEqual && !isTrue){
						isTrue = numValue == compareVal;
					}
				}
				else{
					//una stringa non può essere mionore di un numero
				}
			}
			else if(isJsDate(compareVal)){
				var date = getJsDate(value);
				if(date){
					isTrue = isGreater ? date.compare(compareVal) > 0 : date.compare(compareVal) < 0 ;
					if(isEqual && !isTrue){
						isTrue = date.compare(compareVal) == 0;
					}
				}
				else{
					//una stringa non può essere mionore di una data
				}
			}
			else{
				//una stringa è minore di un'altra stringa solo se entrambe possono essere convertite ad un numero ...
				var numValue = Number(value);
				if(!isNaN(numValue)){
					var numCompValue = Number(compareVal);
					if(!isNaN(numCompValue)){
						isTrue = isGreater ? numValue > numCompValue : numValue < numCompValue;
						if(isEqual && !isTrue){
							isTrue = numValue == numCompValue;
						}
					}
				}
			}
			if(isTrue){
				break;
			}
		}
		return isTrue;
	}
	
	function valueIsNull(values){
		
		if(!jQuery.isArray(values)){
			values = [values];
		}
		var isNull = values.length == 0;
		if(!isNull){
			var allNulls = true;
			for(var i = 0; i < values.length && allNulls; i++){
				allNulls = !isValue(values[i]);
			}
			isNull = allNulls; 
		}
		return isNull;
	}
	
	function valueIn(values, compareValues){
		
		var equals = false;
		if(!jQuery.isArray(values)){
			values = [values];
		}
		if(!jQuery.isArray(compareValues)){
			compareValues = compareValues.toString().split('|');
		}
		for(var i = 0; i < values.length && !equals; i++){
			var value = values[i];
			for(var j = 0; j < compareValues.length; j++){
				var compareVal = compareValues[j];
				if(typeof compareVal == "number"){
					value = value.replace(',','.');
					var numValue = Number(value);
					if(!isNaN(numValue)){
						equals = numValue == compareVal;
					}
					else{
						equals = (values[i] == "" + compareVal) || (value == "" + compareVal);
					}
				}
				else if(isJsDate(compareVal)){
					var date = getJsDate(value);
					if(date){
						equals = date.compare(compareVal) == 0;
					}
					else{
						strDate = compareVal.day + "/" + compareVal.month + "/" + compareVal.year;
						equals = strDate == value;
					}
				}
				else{
					compareVal = "" + compareVal;
					equals = value.toLowerCase() == compareVal.toLowerCase();
				}
				if(equals){
					break;
				}
			}
		}
		return equals;
	}
	
	function valueMatches(values, compareRegex){
		
		var matches = false;
		if(!jQuery.isArray(values)){
			values = [values];
		}
		for(var i = 0; i < values.length; i++){
			var value = values[i];
			matches = value.search(compareRegex) > -1;
			if(matches){
				break;
			}
		}
		return matches;
	}

	function isJsDate(dateValue){
		
		var isDate = false;
		if(dateValue && typeof dateValue == "object"){
			if( dateValue.year != undefined && typeof dateValue.year == "number"
				&& dateValue.month != undefined && typeof dateValue.month == "number"
				&& dateValue.day != undefined && typeof dateValue.day == "number"
				){
				isDate = true;
			}
		}
		return isDate;
	}
	
	function getJsDate(dateStr){
		var retDate = false;
		if(dateStr){
			aData=dateStr.split("/");
			day = aData[0];
			month = aData[1];
			year = aData[2];
			retDate = new JsDate(year,month,day);
		}
		return retDate;
	}
	
	function isValue(checkValue){
		var retVal = checkValue != undefined && checkValue != null;
		if(retVal){
			retVal = checkValue + '';
			retVal = retVal.length > 0;
		}
		return retVal;
	}
	
	/**
	 * Oggetto personalizzato per la rappresentazione delle date, 
	 * per evitare i comportamenti differenti dell'oggetto Date di javascript nei vari browser
	 */
	function JsDate(year,month,day){
		
		this.year = Number(year)
		if(isNaN(this.year))throw "Anno non valido: " + year;
		this.month = Number(month);
		if(isNaN(this.month) || this.month < 1 || this.month > 12)throw "Mese non valido: " + month;
		this.day = Number(day);
		if(isNaN(this.day) || this.day < 1 || this.day > 31)throw "Giorno non valido: " + day;

		
		/**
		 * restituisce 1 se la data passata è minore di questa data,
		 * -1 se è maggiore, 0 se sono uguali e undefined se l'oggetto passato non è una data.
		 */
		this.compare = function(jsDate){
			var comp = undefined;
			if(isJsDate(jsDate)){
				if(this.year > jsDate.year){
					comp = 1;
				}
				else if(this.year < jsDate.year){
					comp = -1;
				}
				else{
					if(this.month > jsDate.month){
						comp = 1;
					}
					else if(this.month < jsDate.month){
						comp = -1;
					}
					else{
						if(this.day > jsDate.day){
							comp = 1;
						}
						else if(this.day < jsDate.day){
							comp = -1;
						}
						else{
							comp = 0;
						}
					}
				}
			}
			return comp;
		};
	}
	
	function initUIControls(jqObject){
		if(jqObject + "" == "undefined"){
			jqObject = jQuery(document);
		}
		jqObject.find("input:submit").button();
		jqObject.find("input:file").button();
		jqObject.find("input:button").button();
		jqObject.find("button").button();
		jqObject.find(".button").button();
		jqObject.find(".help_image").tooltip();		
		jqObject.find(".error_image").tooltip();	
		jQuery.datepicker.regional['it'];
		jQuery.datepicker.setDefaults( {
			inline: true,
			showOn: 'both', 
			buttonImageOnly: true, 
			buttonImage: '../images/cal.gif',
			dateFormat: "dd/mm/yy",
			changeYear: true,
			yearRange: "1900:+00"}); 
	}
	
    function anteprimaModello(idModelloT, appContext){
    	if(!appContext){
    		appContext = "";
    	}
    	window.open(appContext + "/dyn2modellit/anteprimaModello.htm?codiceModello="+idModelloT,69,"+status=1,menubar=0,scrollbars=1,width=1024, height=768");
	}
	
