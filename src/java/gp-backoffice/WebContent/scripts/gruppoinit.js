/**
 * Funzione per la modifica a runtime della lingua utilizzata
 * 
 * @param lang
 *            (it, en)
 */
function changeLang(lang) {
	changeUrlParam("lang", lang);
}

/**
 * Funzione per la modifica a runtime del tema visualizzato
 * 
 * @param theme
 *            (normal, smart)
 */
function changeTheme(theme) {
	changeUrlParam("theme", theme);
}

function changeFontSize(dim) {
	if (dim == 'normal') {
		document.body.style.fontSize = "62.5%";
	} else {
		document.body.style.fontSize = "100%";
	}
}

/**
 * Funzione per la visualizzazione a runtime della pagina corrente con layout di
 * stampa
 */
function printPage() {
	changeUrlParam("printable", "true");
}
/**
 * Funzione per aggiungere alla query string o rimpiazzare il valore di un
 * parametro
 * 
 * @param paramName
 *            il nome del parametro
 * @param paramValue
 *            il valore da assegnare
 */
function changeUrlParam(paramName, paramValue) {
	if (document.URL.match(new RegExp("\\?", "g"))) {
		if (document.URL.match(new RegExp(paramName + "=", "g"))) {
			var string = document.URL;
			var reg = new RegExp(paramName + "=.*", "g");
			string = string.replace(reg, paramName + "=" + paramValue);
			window.location.replace(string);
		} else {
			window.location.replace(document.URL + "&" + paramName + "="
					+ paramValue);
		}
	} else {
		window.location.replace(document.URL + "?" + paramName + "="
				+ paramValue);
	}
}

// Variabile di appoggio per la funzione disableFunctions()
var buttonSubmitted = false;

/**
 * Funzione per disabilitare (nasconde) il contenuto del div con id=functions
 * contentente i bottoni di azione in modo da evitare il doppio submit. Al posto
 * dei bottoni visualizza l'immagine spinner.gif (imposta a true la variabile
 * buttonSubmitted)
 */
function disableFunctions() {
	buttonSubmitted = true;
	if (dialogWorkingProgress) {
		dialogWorkingProgress.show();
	}
}

function enableFunctions() {
	buttonSubmitted = false;
	if (dialogWorkingProgress) {
		dialogWorkingProgress.hide();
	}
}

var dialogWorkingProgress = null;

jQuery(document).ready(function() {
	dialogWorkingProgress = new dijit.Dialog({
		title : "Operazione in corso...",
		style : "width: 250px;",
		content : "<img src='../images/spinner.gif'/>"
	});
});

/**
 * Funzione per eseguire la submit di un form
 * 
 * @param hrefFormAction
 *            URI di destinazione (es. insert.htm)
 * @param confirmMessage
 *            messaggio di conferma da visualizzare come confirm()
 * @param objForm
 *            l'oggetto che rappresenta il form (es. document.inviodati). Se
 *            lasciato vuoto esegue la submit di document.forms[0]
 * @param httpMethod
 *            (POST,GET) se lasciato vuoto indica POST
 * @param isToEncode
 *            (true, false) utilizzato solo se httpMethod = GET
 */
function doSubmit(hrefFormAction, confirmMessage, objForm, httpMethod,
		isToEncode) {
	var queryString;
	if (buttonSubmitted) {
		return;
	}

	if (httpMethod) {
		if (httpMethod == 'GET') {
			queryString = getQueryStringFromForm(objForm);
			if (isToEncode) {
				queryString = URLEncode(queryString);
			}
			hrefFormAction = hrefFormAction + queryString;
			doHref(hrefFormAction, confirmMessage);
			return;
		}
	}

	if (checkConfirmMessage(confirmMessage)) {
		if (objForm) {
			disableFunctions();
			objForm.action = hrefFormAction;
			objForm.submit();
			return;
		} else {
			disableFunctions();
			document.forms[0].action = hrefFormAction;
			document.forms[0].submit();
			return;
		}
	}
}
/**
 * Funzione per recuperare tutti gli oggetti di un form e costruire la query
 * string con i nomi e valori
 * 
 * @param objForm
 *            il form
 * @returns la query string
 */
function getQueryStringFromForm(objForm) {
	var tempForm;
	var qs = '';
	if (objForm) {
		tempForm = objForm;
	} else {
		tempForm = document.forms[0];
	}
	var cur_el;
	for (var i = 0; i < tempForm.elements.length; i++) {
		if (tempForm.elements[i]) {
			cur_el = tempForm.elements[i];
			if ((cur_el.disabled == false) && (cur_el.style.display != 'none')) {
				if (cur_el.name != '') {
					qs += '&' + cur_el.name + '=' + escape(cur_el.value);
				}
			}
		}
	}
	return qs;
}

/**
 * Funzione che esegue una location.href
 * 
 * @param hrefLink
 *            URI di destinazione
 * @param confirmMessage
 *            messaggio di conferma (opzionale)
 * @return
 */
function doHref(hrefLink, confirmMessage) {
	if (buttonSubmitted) {
		return;
	}

	if (checkConfirmMessage(confirmMessage)) {
		disableFunctions();
		document.location.href = hrefLink;
		return;
	}
}

/**
 * Funzione per l'apertura in popup del link invocato
 * 
 * @param hrefLink
 *            URI di destinazione
 * @param confirmMessage
 *            messaggio di conferma (opzionale)
 * @param attributes
 *            attributi della finestra
 * @return
 */
function popup(hrefLink, confirmMessage, attributes) {

	if (buttonSubmitted) {
		return;
	}

	if (checkConfirmMessage(confirmMessage)) {
		var rndnum = 100 * Math.random();
		rndnum = Math.ceil(rndnum);
		var w = window.open(hrefLink, rndnum, attributes);
	}
}

/**
 * Funzione per la presentazione di un messaggio di conferma.
 * 
 * @param message
 *            messaggio da visualizzare
 * @return (true, confirm(message))
 */
function checkConfirmMessage(message) {

	if (message) {
		if (message != '') {
			return confirm(message);
		}
		return true;
	}
	return true;
}

/**
 * Funzione per la visualizzazione di un messaggio in un campo con effetto
 * APPEAR
 * 
 * @param id
 *            id del campo
 * @param message
 *            messaggio da visualizzare
 * @return
 */
function showField(id, message) {
	$(id).value = message;
	$(id).appear();
}
/**
 * Funzione per azzerare i valori di due campi di input
 * 
 * @param id
 *            id del primo campo
 * @param id2
 *            id del secondo campo
 */
function clearField(id, id2) {
	$(id).value = "";
	$(id2).value = "";
}

/**
 * associa il comportamento a seconda che eventName sia blur o focus
 * 
 * @param field
 * @param eventName
 * @param message
 */
function setDefault(field, eventName, message) {
	if (eventName) {
		if (eventName == 'blur') {
			if (field.value == '') {
				field.fade();
			}
		}
		if (eventName == 'focus') {
			if (field.value == message) {
				field.value = '';
			}
		}
	}
}

/**
 * funzione per determinare le proprietà del table facade (JMesa) controllo se è
 * presente "removeSortFromLimit" ed estraggo la proprietà con due modalità a
 * seconda se è presente oppure no
 * 
 * @param id
 * @return
 */
function getProperties(id) {

	key = new Array();
	var properties = new Array();
	var i;
	var selector = '#' + id + ' .header';
	var colProp = jQuery(selector).children();
	for (i = 0; i < colProp.length; i++) {
		key[i] = colProp[i].innerHTML;
		var str = new Array();
		if (key[i].indexOf('removeSortFromLimit') == -1) {

			str = key[i].split(',');
			properties[i] = URLEncode(str[2]);

		} else {

			var string = new Array();
			string = key[i].split(',');
			str = string[1].split(')');
			properties[i] = URLEncode(str[0]);
		}
	}
	return properties;
}

/**
 * determina a partire dall'id del table facade della tabella di JMesa determina
 * il nome delle colonne
 */
function getColumnArray(id) {
	key = new Array();
	var i;
	var selector = '#' + id + ' .header';
	var colName = jQuery(selector).children();
	for (i = 0; i < colName.length; i++) {
		if (colName[i].innerText == undefined) {
			key[i] = URLEncode(colName[i].textContent);
		} else {
			key[i] = URLEncode(colName[i].innerText);
		}
	}
	// BUG FIX CHROME: AGGIUNGE IL RITORNO A CAPO (\n) che codificato è (%0A)
	// Viene effettuato il replace di "%0A" con ""
	var keyResult = new Array();
	for (i = 0; i < key.length; i++) {
		keyResult[i] = key[i].replace("%0A", "");
	}
	return keyResult;
}

/**
 * funzione standardizzata per l'export delle tabelle di JMesa
 * 
 * @param id
 * @return
 */
function onInvokeAction(id) {
	jQuery.jmesa.setExportToLimit(id, '');
	jQuery.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
}

/**
 * funzione standardizzata per l'export delle tabelle di JMesa la request
 * contiene l'id della table facade, nome delle colonne e le properties
 */
function onInvokeExportAction(id) {
	key = new Array();
	var key = getColumnArray(id);
	var i;
	var keystring = '&table_id=' + id + '&colName=';

	for (i = 0; i < key.length - 1; i++) {
		keystring += key[i] + ',';
	}
	keystring += key[i];

	var props = getProperties(id);
	keystring += '&properties=';
	for (i = 0; i < props.length - 1; i++) {
		keystring += props[i] + ',';
	}
	keystring += props[i];

	var parameterString = jQuery.jmesa.createParameterStringForLimit(id);
	location.href = _jmesaUrl + parameterString + keystring + '&caption='
			+ URLEncode(_captionTab);
}

/**
 * funzione per resettare il campo hidden associato ad una ricerca ajax
 * 
 */
function checkValue(inputField, hiddenFieldId) {
	if (inputField.value == '')
		$(hiddenFieldId).value = '';
}

// ====================================================================
// URLEncode and URLDecode functions
//
// Copyright Albion Research Ltd. 2002
// http://www.albionresearch.com/
//
// You may copy these functions providing that
// (a) you leave this copyright notice intact, and
// (b) if you use these functions on a publicly accessible
// web site you include a credit somewhere on the web site
// with a link back to http://www.albionresearch.com/
//
// If you find or fix any bugs, please let us know at albionresearch.com
//
// SpecialThanks to Neelesh Thakur for being the first to
// report a bug in URLDecode() - now fixed 2003-02-19.
// And thanks to everyone else who has provided comments and suggestions.
// ====================================================================
function URLEncode(plaintext) {
	// alert("plaintext: "+plaintext);
	// The Javascript escape and unescape functions do not correspond
	// with what browsers actually do...
	var SAFECHARS = "0123456789" + // Numeric
	"ABCDEFGHIJKLMNOPQRSTUVWXYZ" + // Alphabetic
	"abcdefghijklmnopqrstuvwxyz" + "-_.!~*'()"; // RFC2396 Mark characters
	var HEX = "0123456789ABCDEF";

	var encoded = "";
	if (plaintext != null) {
		for (var i = 0; i < plaintext.length; i++) {
			var ch = plaintext.charAt(i);
			if (ch == " ") {
				encoded += "+"; // x-www-urlencoded, rather than %20
			} else if (SAFECHARS.indexOf(ch) != -1) {
				encoded += ch;
			} else {
				var charCode = ch.charCodeAt(0);
				if (charCode > 255) {
					/*
					 * alert( "Unicode Character '" + ch + "' cannot be encoded
					 * using standard URL encoding.\n" + "(URL encoding only
					 * supports 8-bit characters.)\n" + "A space (+) will be
					 * substituted." );
					 */
					encoded += "+";
				} else {
					encoded += "%";
					encoded += HEX.charAt((charCode >> 4) & 0xF);
					encoded += HEX.charAt(charCode & 0xF);
				}
			}
		} // for
	} else {
		return plaintext;
	}
	// alert("plaintext encoded: "+encoded);
	return encoded;
};

function URLDecode(encoded) {
	// Replace + with ' '
	// Replace %xx with equivalent character
	// Put [ERROR] in output if %xx is invalid.
	var HEXCHARS = "0123456789ABCDEFabcdef";
	var plaintext = "";
	var i = 0;
	while (i < encoded.length) {
		var ch = encoded.charAt(i);
		if (ch == "+") {
			plaintext += " ";
			i++;
		} else if (ch == "%") {
			if (i < (encoded.length - 2)
					&& HEXCHARS.indexOf(encoded.charAt(i + 1)) != -1
					&& HEXCHARS.indexOf(encoded.charAt(i + 2)) != -1) {
				plaintext += unescape(encoded.substr(i, 3));
				i += 3;
			} else {
				alert('Bad escape combination near ...' + encoded.substr(i));
				plaintext += "%[ERROR]";
				i++;
			}
		} else {
			plaintext += ch;
			i++;
		}
	} // while
	return plaintext;
};

// /////////////////////ENCODE DECODE UTF-8///////////////////////
/**
 * 
 * UTF-8 data encode / decode http://www.webtoolkit.info/
 * 
 */

var Utf8 = {
	encode : function(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
		}
		return utftext;
	},
	decode : function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while (i < utftext.length) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i + 1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i + 1);
				c3 = utftext.charCodeAt(i + 2);
				string += String.fromCharCode(((c & 15) << 12)
						| ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
};
// ////////////////////////////////////////////////////////////////

/**
 * Sistema le date dei text box in input vuole la text box stessa se
 * MostraERR=true allora dà un alert in caso di data errata
 */
function isValidDate(TextBox, MostraERR) {

	dateStr = TextBox.value;
	var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{2,4})$/;

	if (dateStr == "") {
		return true;
	}
	if (dateStr.match("/") == null) {
		if ((dateStr.length = 8) || (dateStr.length = 6)) {
			var dateStrTmp = dateStr.substring(0, 2) + "/"
					+ dateStr.substring(2, 4) + "/"
					+ dateStr.substring(4, dateStr.length);
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
		} else {
			erroreData(TextBox);
		}
	} else {
		aData = dateStr.split("/");
		day = aData[0];
		month = aData[1];
		year = aData[2];
		if (day.length == 1) {
			day = "0" + day;
		}
		if (month.length == 1) {
			month = "0" + month;
		}
		var dateStrTmp = day + "/" + month + "/" + year;
		dateStr = dateStrTmp;
		TextBox.value = dateStr;
	}
	var matchArray = dateStr.match(datePat);
	if (matchArray == null) {
		if (MostraERR) {
			erroreData(TextBox);
		}
		return false;
	}
	month = matchArray[3];
	day = matchArray[1];
	year = matchArray[4];
	// Aggiusta l'anno
	switch (year.length) {
	case 2:
		if (year > "30") {
			year = "19" + year.substring(0, 2);
		} else {
			year = "20" + year.substring(0, 2);
		}
		break;
	}
	dateStr = dateStr.substring(0, 6) + year;
	// Riscrive la data nel text box
	TextBox.value = dateStr;

	if (month < 1 || month > 12) {
		if (MostraERR) {
			erroreData(TextBox);
		}
		return false;
	}

	if (day < 1 || day > 31) {
		if (MostraERR) {
			erroreData(TextBox);
		}
		return false;
	}

	if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
		if (MostraERR) {
			erroreData(TextBox);
		}
		return false;
	}

	if (month == 2) {
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day == 29 && !isleap)) {
			if (MostraERR) {
				erroreData(TextBox);
			}
			return false;
		}
	}
	return true;
}

function erroreData(TextBox) {
	alert("La data non è valida");
	TextBox.value = "";
	TextBox.focus();
}

function checkTime(TextBox, MostraERR) {
	if (TextBox.value != '') {
		var result = false, m;
		var timeFormat = /^\s*([01]?\d|2[0-3]):?([0-5]\d)\s*$/;
		if (m = TextBox.value.match(timeFormat)) {
			result = (m[1].length == 2 ? "" : "0") + m[1] + ":" + m[2];
			TextBox.value = result;
			return result;
		}
		erroreOrario(TextBox)
		return result;
	}
}

function erroreOrario(TextBox) {
	alert("Ora non valida");
	TextBox.value = "";
	TextBox.focus();
}

/**
 * Sistema le date dei text box omettendo l'anno in input vuole la text box
 * stessa se MostraERR=true allora dà un alert in caso di data errata
 */

function isValidPeriod(TextBox, MostraERR) {
	dateStr = TextBox.value;

	var day;
	var month;

	if (dateStr == "")
		return true;

	if (dateStr.length >= 6) {
		return isValidDate(TextBox, MostraERR);
	} else {
		if (dateStr.match("/") == null) {
			if ((dateStr.length == 4)) {
				day = dateStr.substring(0, 2);
				month = dateStr.substring(2, 4);
				if (isNaN(day) || isNaN(month)) {
					vis_errore(TextBox, "Il periodo non è corretto!");
					return false;
				}
				var dateStrTmp = day + "/" + month;
				dateStr = dateStrTmp;
				TextBox.value = dateStr;
			} else {
				vis_errore(TextBox, "Il periodo non è corretto!");
				return false;
			}
		} else {
			aData = dateStr.split("/");
			day = aData[0];
			month = aData[1];

			if (day.length == 1)
				day = "0" + day;

			if (month.length == 1)
				month = "0" + month;

			var dateStrTmp = day + "/" + month;
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
		}

		// Riscrive la data nel text box
		TextBox.value = dateStr;

		if (month < 1 || month > 12) {
			if (MostraERR)
				vis_errore(TextBox, "Il periodo non è corretto!");

			return false;
		}

		if (day < 1 || day > 31) {
			if (MostraERR)
				vis_errore(TextBox, "Il periodo non è corretto!");
			return false;
		}

		if ((month == 4 || month == 6 || month == 9 || month == 11)
				&& day == 31) {
			if (MostraERR)
				vis_errore(TextBox, "Il periodo non è corretto!");
			return false;
		}

		if (month == 2) {
			if (day > 29) {
				if (MostraERR)
					vis_errore(TextBox, "Il periodo non è corretto!");

				return false;
			}
		}

		return true;
	}
}

/**
 * Restituisce come messaggio di errore il testo fornitogli in input
 * 
 */
function vis_errore(TextBox, Messaggio) {
	alert(Messaggio);
	TextBox.value = "";
	TextBox.focus();
}

/**
 * Sistema le ore dei text box in input vuole la text box stessa se
 * MostraERR=true allora dà un alert in caso di data errata
 */
function isValidOra(TextBox, MostraERR) {

	dateStr = TextBox.value;
	var datePat = /^(\d{1,2})(:)(\d{1,2})$/;

	if (dateStr == "") {
		return true;
	}

	if (dateStr.match(":") == null) {
		if (dateStr.length == 4) {
			var dateStrTmp = dateStr.substring(0, 2) + ":"
					+ dateStr.substring(2, 4);
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
		} else {
			erroreora(TextBox);
			return false;
		}
	} else {
		aData = dateStr.split(":");
		ore = aData[0];
		minuti = aData[1];

		if (ore.length == 1) {
			ore = "0" + ore;
		}
		if (minuti.length == 1) {
			minuti = "0" + minuti;
		}

		var dateStrTmp = ore + ":" + minuti;
		dateStr = dateStrTmp;
		TextBox.value = dateStr;
	}

	var matchArray = dateStr.match(datePat);

	if (matchArray == null) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}
	ore = (dateStr.substring(0, 2));
	minuti = (dateStr.substring(5, 3));
	if (ore.StringStartsWith("0")) {
		ore = ore.substring(2, 1);
	}
	if (minuti.StringStartsWith("0")) {
		minuti = minuti.substring(2, 1);
	}
	ore = parseInt(ore);
	minuti = parseInt(minuti);
	if (ore < 0 || ore > 23) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}
	if (minuti < 0 || minuti > 59) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}
	TextBox.value = dateStr;
	return true;
}

/**
 * Errore ora non valida
 * 
 */
function erroreora(TextBox) {
	alert("Formato ora non valido es hh:mm");
	TextBox.value = "";
	TextBox.focus();

}

function isValidTime(TextBox, MostraERR) // Sistema le ore dei text box in
											// formato hh:mi:ss
{ // in input vuole la text box stessa
	// se MostraERR=true allora dà un alert in caso di data errata
	// DA UTILIZZARE SE SI DESIDERA VISUALIZZARE ANCHE I SECONDI
	dateStr = TextBox.value;
	var datePat = /^(\d{1,2})(:)(\d{1,2})(:)(\d{1,2})$/;

	if (dateStr == "") {
		return true;
	}

	if (dateStr.match(":") == null) {
		if (dateStr.length == 4) {
			var dateStrTmp = dateStr.substring(0, 2) + ":"
					+ dateStr.substring(2, 4) + ":" + "00";
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
		}

		if (dateStr.length == 6) {
			var dateStrTmp = dateStr.substring(0, 2) + ":"
					+ dateStr.substring(2, 4) + ":" + dateStr.substring(4, 6);
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
		} else {
			erroreora(TextBox);
			return false;
		}
	} else {
		aData = dateStr.split(":");
		secondi = "00";

		if (aData.length == 3) {
			secondi = aData[2];
		}

		ore = aData[0];
		minuti = aData[1];

		if (ore.length == 1) {
			ore = "0" + ore;
		}
		if (minuti.length == 1) {
			minuti = "0" + minuti;
		}

		if (secondi.length == 1) {
			secondi = "0" + secondi;
		}

		var dateStrTmp = ore + ":" + minuti + ":" + secondi;
		dateStr = dateStrTmp;
		TextBox.value = dateStr;
	}

	var matchArray = dateStr.match(datePat);

	if (matchArray == null) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}

	ore = dateStr.substring(0, 2);
	minuti = dateStr.substring(3, 2);
	secondi = dateStr.substring(5, 2);

	if (ore < 1 || ore.value > 24) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}

	if (minuti < 1 || minuti > 60) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}

	if (secondi < 1 || secondi > 60) {
		if (MostraERR) {
			erroreora(TextBox);
		}
		return false;
	}

	TextBox.value = dateStr;

	return true;
}

String.prototype.StringStartsWith = function(str) {
	return (this.match("^" + str) == str);
};

/**
 * funzione per controllare e convertire se possibile nel formato corretto la
 * valuta inserita. es. 10.2 -> 10,2
 * 
 * @param obj
 * @return
 */
function checkCurrencyValue(obj) {
	if (isNaN(obj.value.replace(",", "."))) {
		alert('Il formato della valuta inserita non è corretto');
		obj.value = "";
		obj.focus();
		return false;
	}
	if (obj.value.indexOf(".", 0) > 0) {
		obj.value = obj.value.replace(".", ",");
	}
	return true;
}

/**
 * funzione per controllare e convertire se possibile nel formato corretto il
 * numero inserito. es. 10.2 -> 10,2
 * 
 * @param obj
 * @return
 */
function checkNumberValue(obj) {
	if (isNaN(obj.value.replace(",", "."))) {
		alert('Il numero inserito non è corretto');
		obj.value = "";
		obj.focus();
		return false;
	}
	if (obj.value.indexOf(".", 0) > 0) {
		obj.value = obj.value.replace(".", ",");
	}
	return true;
}

var allCheckBoxSelected = false;
/**
 * la funzione seleziona / deseleziona tutti i checkbox di un dato form
 * 
 * @param objForm
 */
function selezionaDeselezionaTuttiCheckbox(objForm) {
	if (objForm) {
		allCheckBoxSelected = (allCheckBoxSelected == false) ? true : false;
		for (var i = 0; i < objForm.elements.length; i++) {
			var type = objForm.elements[i].type;
			if (type == "checkbox") {
				objForm.elements[i].checked = allCheckBoxSelected;
			}
		}
	}
}

/**
 * funzione che esegue un doHref al metodo set di HistoryController
 * 
 * @param returnToUrlEncoded
 *            url di ritorno (inserito nella history)
 * @param goToUrlNotEncoded
 *            url di destinazione
 * @param confirmMessage
 *            messaggio di conferma
 */
function historySet(returnToUrlEncoded, goToUrlNotEncoded, confirmMessage) {
	var goToUrl = URLEncode(goToUrlNotEncoded);
	doHref('../history/set.htm?ReturnTo=' + returnToUrlEncoded + '&GoTo='
			+ goToUrl, confirmMessage);
}

/**
 * funzione che esegue un doHref al metodo back di HistoryController
 * 
 * @param confirmMessage
 *            messaggio di conferma (opzionale)
 * @return
 */
function historyBack(confirmMessage) {
	doHref('../history/back.htm?GoTo=%2F', confirmMessage);
}

/**
 * funzione che esegue un doHref al metodo clear di HistoryController
 * 
 * @param goTo
 *            url di destinazione encoded
 */
function historyClear(goTo) {
	// var goToEncoded=URLEncode(goTo);
	doHref("../history/clear.htm?GoTo=" + goTo);
}

/**
 * <b>Summary:</b><br />
 * Encodes a Uniform Resource Identifier (URI) component by replacing each
 * instance of certain characters by one, two, three, or four escape sequences
 * representing the UTF-8 encoding of the character (will only be four escape
 * sequences for characters composed of two "surrogate" characters).<br />
 * <b>Syntax:</b><br />
 * var encoded = encodeURIComponent(str);<br />
 * <b>Parameter:</b><br />
 * str: A component of a URI.<br />
 * <b>Description:</b><br />
 * encodeURIComponent escapes all characters except the following: alphabetic,
 * decimal digits, - _ . ! ~ * ' ( ) To avoid unexpected requests to the server,
 * you should call encodeURIComponent on any user-entered parameters that will
 * be passed as part of a URI. For example, a user could type "Thyme
 * &time=again" for a variable comment. Not using encodeURIComponent on this
 * variable will give comment=Thyme%20&time=again. Note that the ampersand and
 * the equal sign mark a new key and value pair. So instead of having a POST
 * comment key equal to "Thyme &time=again", you have two POST keys, one equal
 * to "Thyme " and another (time) equal to again. For
 * application/x-www-form-urlencoded (POST), per
 * http://www.w3.org/TR/html401/interac...m-content-type, spaces are to be
 * replaced by '+', so one may wish to follow a encodeURIComponent replacement
 * with an additional replacement of "%20" with "+". If one wishes to be more
 * stringent in adhering to RFC 3986 (which reserves !, ', (, ), and *), even
 * though these characters have no formalized URI delimiting uses, the following
 * <b>fixedEncodeURIComponent</b> can be safely used.
 */
function fixedEncodeURIComponent(str) {
	return encodeURIComponent(str).replace(/!/g, '%21').replace(/'/g, '%27')
			.replace(/\(/g, '%28').replace(/\)/g, '%29').replace(/\*/g, '%2A');
}
/**
 * La funzione controlla se il numero inserito è un numero positivo (lo zero non
 * è considerato)
 * 
 * @param obj
 * @return
 */
function isPositiveNumber(obj) {

	if (obj.value < 0) {
		alert('Il valore non può essere negativo');
		obj.value = "";
		$('numeroPosteggi_id').focus();
	}
}
/**
 * Funzione per il decode degli HTTP error
 * 
 * @param code
 * @param message
 * @return
 */
function decodeHTTPStatus(code, message) {
	var s = code.toString().split("");
	switch (s[0]) {
	case "1":
		return "Errore. (codice HTTP: " + code.toString() + ")";
	case "2":
		return "Operazione eseguita correttamente.";
	case "3":
		return "Errore. (codice HTTP: " + code.toString() + ")";
	case "4":
		return "Errore. (codice HTTP: " + code.toString() + ")";
	case "5":
		return "Errore. (codice HTTP: " + code.toString() + ")";
	default:
		return "";
	}

}
var printResult = function(transport, message) {
	var result = decodeHTTPStatus(transport.status, message);
	alert(result);
};

/**
 * Mostra / nasconde un elemento della pagina l'identificativo del div
 * 
 * @return true se l'elemento torna ad essere visibile false se l'elemento viene
 *         nascosto
 */
function showHideElement(elem) {
	if (elem) {
		if (elem.style.display == 'block' || elem.style.display == 'inline'
				|| elem.style.display == '') {
			elem.style.display = 'none';
			return false;
		} else {
			elem.style.display = '';
			return true;
		}
	}
	return false;
}

/**
 * Mostra / nasconde un div della pagina
 * 
 * @param div_Id
 *            l'identificativo del div
 * @return
 */
function showHideDiv(div_Id) {
	var elem = document.getElementById(div_Id);
	if (elem) {
		if (elem.style.display == 'block' || elem.style.display == 'inline'
				|| elem.style.display == '') {
			elem.style.display = 'none';
		} else {
			elem.style.display = '';
		}
	}
}
/**
 * Mostra un div della pagina
 * 
 * @param div_Id
 *            l'identificativo del div
 * @return
 */
function showDiv(div_Id) {
	var elem = document.getElementById(div_Id);
	if (elem) {
		if (elem.style.display == 'none') {
			elem.style.display = '';
		}
	}
}
/**
 * Nasconde un div della pagina
 * 
 * @param div_Id
 *            l'identificativo del div
 * @return
 */
function hideDiv(div_Id) {
	var elem = document.getElementById(div_Id);
	if (elem) {
		elem.style.display = 'none';
	}
}

/**
 * Controlla che il valore inserito sia un numero e sia intero
 */

function checkNumberInt(obj) {
	var value = obj.value.replace(",", ".");
	if (isNaN(value)) {
		alert('Devi inserire un numero');
		obj.value = "";
		obj.focus();
		return false;
	}
	if (value.indexOf(".") > 0) {
		alert('Devi inserire un numero intero');
		obj.value = "";
		obj.focus();
		return false;
	}
	return true;
}
/**
 * da una select torna un array contenente: nella posizione [0] la chiave della
 * option selezionata e nella posizione [1] il testo
 * 
 * @param selectObj
 * @returns {Array}
 */
function getSelectTextAndValue(selectObj) {

	var pos = selectObj.selectedIndex;
	var valore = '';
	var testo = '';
	if (pos > -1) {
		valore = selectObj.options[pos].value;
		testo = selectObj.options[pos].text;
	}
	var valori = new Array(valore, testo);
	return valori;
}

function getValoreDellaSelect(objSelect) {
	if (objSelect == null || objSelect.options == null
			|| objSelect.selectedIndex == -1) {
		return "";
	}
	return objSelect.options[objSelect.selectedIndex].value;
}

function doStartAfterSessionExpired(urlback) {
	if (window.opener != null) {
		self.close();
	} else {
		location.href = urlback;
	}
}
/**
 * La funzione serve per visualizzare/nascondere le sezioni dei form che sono
 * gestite tramite configurazione/utente
 * 
 * @param panelId
 *            l'id degli elementi &lt;tr> da nascondere
 * @param elemImg
 *            l'id del link che effettua l'operazione
 * @param userprefParam
 *            il nome del parametro di preferenza da salvare
 * @param imagePath
 *            il path alla cartella images <br>
 *            es:. showHidePanel('id_normative_table', this,
 *            'CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE',
 *            '${pageContext.request.contextPath}/images/');
 */
function showHidePanel(panelId, elemImg, userprefParam, imagePath, tagElement) {
	showHidePanelBase(panelId, elemImg, userprefParam, imagePath, tagElement,
			true);
}

function setPanelVisible(panelId, elemImg, doShow, userprefParam,
		savePreferences) {
	var panel = jQuery('#' + panelId);
	var linkExpand = jQuery('#' + elemImg);
	if (doShow) {
		panel.show();
		linkExpand.addClass("sezioneDatiMeno");
		linkExpand.removeClass("sezioneDatiPiu");
	} else {
		panel.hide();
		linkExpand.addClass("sezioneDatiPiu");
		linkExpand.removeClass("sezioneDatiMeno");
	}
	if (savePreferences) {
		var valore = doShow ? 1 : 0;
		saveUserPreference(userprefParam, valore);
	}
}

/**
 * La funzione serve per visualizzare/nascondere le sezioni dei form che sono
 * gestite tramite configurazione/utente
 * 
 * @param panelId
 *            l'id degli elementi &lt;tr> da nascondere
 * @param elemImg
 *            l'id del link che effettua l'operazione
 * @param userprefParam
 *            il nome del parametro di preferenza da salvare
 * @param imagePath
 *            il path alla cartella images
 * @param savePreferences
 *            booleano se devono venir salvate le preferenze utente <br>
 *            es:. showHidePanelBase('id_normative_table', this,
 *            'CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE',
 *            '${pageContext.request.contextPath}/images/',true);
 */
function showHidePanelBase(panelId, elemImg, userprefParam, imagePath,
		tagElement, savePreferences) {
	var visibile = false;

	if (!tagElement) {
		tagElement = 'tr';
	}
	visibile = showHideElements(panelId, tagElement);
	if (visibile) {
		document.getElementById(elemImg).className = "sezioneDatiMeno";
	} else {
		document.getElementById(elemImg).className = "sezioneDatiPiu";
	}
	if (savePreferences) {
		var valore = (visibile == true) ? 1 : 0;
		saveUserPreference(userprefParam, valore);
	}
}

/**
 * La funzione serve per visualizzare le sezioni dei form che sono gestite
 * tramite configurazione/utente
 * 
 * @param panelId
 *            l'id degli elementi &lt;tr> da nascondere
 * @param elemImg
 *            l'id del link che effettua l'operazione
 * @param imagePath
 *            il path alla cartella images <br>
 *            es:. showPanel('id_normative_table', this,
 *            '${pageContext.request.contextPath}/images/');
 */

function showPanel(panelId, elemImg, userprefParam, imagePath, tagElement,
		savePreferences) {
	var visibile = false;

	if (!tagElement) {
		tagElement = 'tr';
	}
	visibile = showElements(panelId, tagElement);
	if (visibile) {
		document.getElementById(elemImg).className = "sezioneDatiMeno";
	} else {
		document.getElementById(elemImg).className = "sezioneDatiPiu";
	}
	if (savePreferences) {
		var valore = (visibile == true) ? 1 : 0;
		saveUserPreference(userprefParam, valore);
	}
}

function showElements(panelId, tagElement) {
	var visibile = false;
	var nodeList = document.getElementsByTagName(tagElement);
	for (var i = 0; i < nodeList.length; i++) {
		var currEl = nodeList[i];
		if (currEl.id == panelId) {
			visibile = showElement(currEl);
		}
	}
	return visibile;
}

/**
 * Mostra un elemento della pagina l'identificativo
 * 
 * @return true se l'elemento torna ad essere visibile
 */
function showElement(elem) {
	if (elem) {
		elem.style.display = '';
		return true;
	}
	return false;
}

/**
 * Salva una impostazione utente
 * 
 * @param nomeparametro
 *            il nome del parametro che deve venire salvato
 * @param valore
 *            il valore da salvare
 */
function saveUserPreference(nomeparametro, valore) {

	return jQuery.ajax({
		url : 'salvaPreferenza.htm?nomeparametro=' + nomeparametro + '&valore='
				+ valore,
		context : document.body,
		cache : false,
		dataType : "html",
		success : function(data) {
			console.debug = 'data';
		},
		fail : function(jqXHR, textStatus, errorThrown) {
			console.error = 'textStatus=' + textStatus + '\nerrorThrown='
					+ errorThrown;
		}
	});
}

function showHideElements(panelId, tagElement) {
	var visibile = false;
	var nodeList = document.getElementsByTagName(tagElement);
	for (var i = 0; i < nodeList.length; i++) {
		var currEl = nodeList[i];
		if (currEl.id == panelId) {
			visibile = showHideElement(currEl);
		}
	}
	return visibile;
}

function parseAjaxResponse(_source, validaScript, stripScripts) {

	var originalSource = _source;
	var source = _source;
	var scripts = new Array();

	// Strip out tags
	while (source.indexOf("<script") > -1 || source.indexOf("</script") > -1) {
		var s = source.indexOf("<script");
		var s_e = source.indexOf(">", s);
		var e = source.indexOf("</script", s);
		var e_e = source.indexOf(">", e);

		// Add to scripts array
		scripts.push(source.substring(s_e + 1, e));
		// Strip from source
		source = source.substring(0, s) + source.substring(e_e + 1);
	}

	// Loop through every script collected and eval it
	if (validaScript) {
		for (var i = 0; i < scripts.length; i++) {
			try {
				eval(scripts[i]);
			} catch (ex) {
				console.error(ex);
				// do what you want here when a script fails
			}
		}
	}

	// Return the cleaned source
	if (stripScripts) {
		return source;
	} else {
		return originalSource;
	}

}

function gestDialog(elementId) {
	if (document.getElementById(elementId).style.display == 'none') {
		$(elementId).appear();
	} else {
		$(elementId).fade();
	}
}

function changeCheckboxValue(id, url) {

	var lid = id;
	new Ajax.Request(url, {
		method : 'post',
		onSuccess : function(transport) {
			dijit.showTooltip(transport.responseText, dojo.byId(id));
			setTimeout(function() {
				dijit.hideTooltip(dojo.byId(id))
			}, 1000);
		},
		onFailure : function(transport) {
			alert("Errore durante il salvataggio del dato");
			console.error(transport);
			if ($(id).checked) {
				$(id).checked = false;
			} else {
				$(id).checked = true;
			}
		}
	});
}

function ajaxCall(id, url) {
	new Ajax.Request(url, {
		method : 'post',
		onSuccess : function(transport) {
			dijit.showTooltip(transport.responseText, dojo.byId(id));
			setTimeout(function() {
				dijit.hideTooltip(dojo.byId(id))
			}, 5000);
		},
		onFailure : function(transport) {
			alert("Errore durante il salvataggio del dato");
			if ($(id).checked) {
				$(id).checked = false;
			} else {
				$(id).checked = true;
			}
		}
	});
}

/**
 * Funzione che effettua l'escape dei caratteri speciali !"#$%&'()*+,./:;<=>?@[\]^`{|}~
 * per le stringhe utilizzate come selettori CSS, ad esempio nella costruzione
 * di oggetti jQuery
 * 
 * @param toEscape
 *            string ti escape
 * @returns escaped string
 */
function escapeStringForCssSelector(toEscape) {
	toEscape = "" + toEscape;
	toEscape = toEscape.replace(/([ #;?%&,.+*~\':"!^$[\]()=>|\/@])/g, '\\$1');
	return toEscape;
}

function setFieldCancellazioneMaster() {
	var timestamp = 'fld_' + jQuery.now() + '_set_cancellazione_master';
	var hiddenInputField = jQuery('<input />', {
		type : 'hidden',
		name : timestamp,
		id : timestamp + '_id',
		value : 'true'
	});
	jQuery("form").each(function() {
		hiddenInputField.appendTo(jQuery(this));
	});
	alert('Operazione avvenuta con successo procedere alla cancellazione.');
}

function applyStyle() {

	if (!(typeof restyle === 'undefined' || restyle === null)) {
		if (typeof restyle == 'function') {
			restyle();
		}
	}
}

function observeDOM(obj, callback) {
	var MutationObserver = window.MutationObserver
			|| window.WebKitMutationObserver, eventListenerSupported = window.addEventListener;

	if (MutationObserver) {
		// define a new observer
		var obs = new MutationObserver(function(mutations, observer) {
			if (mutations[0].addedNodes.length
					|| mutations[0].removedNodes.length)
				callback();
		});
		// have the observer observe foo for changes in children
		obs.observe(obj, {
			childList : true,
			subtree : true
		});
	} else if (eventListenerSupported) {
		obj.addEventListener('DOMNodeInserted', callback, false);
		obj.addEventListener('DOMNodeRemoved', callback, false);
	}
}

function verificaDocumentiFirmatiInComunicazioni(codicecomunicazione) {

	var jqxhr = jQuery.ajax({
		url : "../comunicazionit/ajaxReportDocDaFirmare.htm",
		context : document.body,
		cache : false,
		dataType : "html",
		data : "codice=" + codicecomunicazione,
		success : function(dataResult) {
			// $("dialogsDocDaFirmare_inner").innerHTML = dataResult;
			// dijit.byId("dialogsDocDaFirmare").show();
			// applyStyle();
			jQuery('<div />').html(dataResult).dialog();
			applyStyle();
		},
		error : function(dataError) {
			jQuery('<div />').html(dataError).dialog();
			// $("dialogsDocDaFirmare_inner").innerHTML = dataError;
			// dijit.byId("dialogsDocDaFirmare").show();
		}
	});

}

/**
 * 
 * 
 */
jQuery.fn.eliminaConConferma = function(options) {

	// ...
	var deleteId = -1, dialogPanel = jQuery(options.dialogSelector), terms = dialogPanel
			.find("#terms"), termsLabel = dialogPanel.find("[for=terms]");

	dialogPanel.dialog({
		title : "Condizioni cancellazione file",
		autoOpen : false,
		buttons : [ {
			text : 'Elimina',
			click : function(event, ui) {
				if (!terms.is(":checked")) {
					termsLabel.addClass("invalid");
				} else {
					options.callback(deleteId);
					jQuery(this).dialog("close");
				}
			}
		}, {
			text : 'Annulla',
			click : function(event, ui) {
				jQuery(this).dialog("close");
			}
		} ],
		width : 600
	});

	jQuery(this).click(function() {

		deleteId = jQuery(this).data(options.dataId);
		dialogPanel.dialog("open");
		dialogPanel.on("dialogbeforeclose", function() {
			terms.prop('checked', false);
			termsLabel.removeClass('invalid');
		});
	});

	return this;
};

/**
 * 
 * 
 */
jQuery.fn.conferma = function(options) {

	// ...
	var deleteId = -1, dialogPanel = jQuery(options.dialogSelector), terms = dialogPanel
			.find("#terms"), termsLabel = dialogPanel.find("[for=terms]");

	dialogPanel.dialog({
		title : options.text_title,
		autoOpen : false,
		buttons : [ {
			text : options.text_button,
			click : function(event, ui) {
				if (!terms.is(":checked")) {
					termsLabel.addClass("invalid");
				} else {
					options.callback(deleteId);
					jQuery(this).dialog("close");
				}
			}
		}, {
			text : 'Annulla',
			click : function(event, ui) {
				jQuery(this).dialog("close");
			}
		} ],
		width : 600
	});

	jQuery(this).click(function() {

		deleteId = jQuery(this).data(options.dataId);
		dialogPanel.dialog("open");
		dialogPanel.on("dialogbeforeclose", function() {
			terms.prop('checked', false);
			termsLabel.removeClass('invalid');
		});
	});

	return this;
};

jQuery.fn.applicaLayerConConferma = function(options) {

	// ...
	var oggettoId = -1, movimentoId = -1, dialogPanel = jQuery(options.dialogSelector), terms = dialogPanel
			.find("#terms"), termsLabel = dialogPanel.find("[for=terms]");

	dialogPanel.dialog({
		title : "Applica layer protocollo",
		autoOpen : false,
		buttons : [ {
			text : 'Applica',
			click : function(event, ui) {
				if (!terms.is(":checked")) {
					termsLabel.addClass("invalid");
				} else {
					options.callback(movimentoId, oggettoId);
					jQuery(this).dialog("close");
				}
			}
		}, {
			text : 'Annulla',
			click : function(event, ui) {
				jQuery(this).dialog("close");
			}
		} ],
		width : 600
	});

	jQuery(this).click(function() {

		movimentoId = jQuery(this).data(options.dataMovId);
		oggettoId = jQuery(this).data(options.dataOggId);
		dialogPanel.dialog("open");
		dialogPanel.on("dialogbeforeclose", function() {
			terms.prop('checked', false);
			termsLabel.removeClass('invalid');
		});
	});

	return this;
};

Date.prototype.formatTime = function() {

	var mm = this.getMonth() + 1; // getMonth() is zero-based
	var dd = this.getDate();
	var seconds = this.getSeconds();
	var minutes = this.getMinutes();
	var hour = this.getHours();

	var output = [ (dd > 9 ? '' : '0') + dd, (mm > 9 ? '' : '0') + mm,
			this.getFullYear(), ].join('/');
	output += " " + hour + ":" + minutes + ":" + seconds;
	return output;
};

/**
 * Controllo basato su jquery.ui.widget. Applica a fianco di un campo di testo
 * un bottone che consente di popolare il campo con un valore restituito da un
 * servizio JSON passato come opzione di configurazione nella funzione che crea
 * il controllo.
 */
(function($) {
	jQuery.widget("initui.autofill",
					{
						version : "1.0.0",
						fieldMatcherRegExp : /{field}/g,
						options : {
							label : "Compilazione Automatica",// tooltip del
																// bottone che
																// scatena la
																// copia dei
																// dati, da
																// personalizzare
																// per ciascun
																// tipo di
																// utilizzo
							triggerButtonClass : "copiaRecord",// classe CSS
																// per la
																// visualizzazione
																// del bottone
																// di default
																// l'icona copia
							serviceUrl : ".", // URL da invocare per il
												// recupero dei dati
							serviceParams : {},// parametri passati alla
												// chiamata al servizio, è anche
												// possibile passare una
												// function che restituisce
												// l'oggetto con i parametri a
												// runtime
							valuePath : "",// path dell'attributo da cui
											// recuperare il valore desiderato
											// nel caso in cui il servizio
											// restiutisca un oggetto json
											// complesso, se vuoto vengono
											// mostrati i dati restituiti così
											// come sono
							ERRORS : {
								error : "Errore: ",
								noData : "Informazione non presente"
							}
						},

						_CSS_CLASSES : "init-autocomplete ui-widget init-widget-content ui-corner-all",

						_create : function() {
							if (!jQuery.isFunction(this.element.val)) {
								throw new Error(
										"Il controllo autofill può essere applicato solo ai campi di un form.")
							}
							this.element.addClass(this._CSS_CLASSES);
							this.element_id = this.element.attr("id");
							this.wrapper = this._getWrapper(this.element);
							// creo il bottone che scatena la copia dei dati e
							// il suo gestore di evento click
							this.triggerButton = jQuery("<a>", {
								href : 'javascript:void(0);',
								title : this.options.label,
								// text: this.options.triggerLabel,
								"class" : "init-autocomplete-button"
							});
							this.triggerButtonLabel = jQuery(
									"<label>",
									{
										"class" : "btn-beside "
												+ this.options.triggerButtonClass
									}).appendTo(this.triggerButton);
							this.triggerButton.appendTo(this.wrapper);
							// span nascosto per la visualizzazione di eventuali
							// messaggi di errore
							$("<span>", {
								id : this.element_id + "_error_message",
								"class" : 'error_ajax_call'
							}).appendTo(this.wrapper).hide();
							this.triggerButton.bind("click", {
								control : this
							}, this._searchData);
						},

						_destroy : function() {
							this.element.removeClass(this._CSS_CLASSES);
							this.triggerButton.remove();
							jQuery(this.element).unwrap();
							this.triggerButton = undefined;
							this.wrapper = undefined;
						},

						_searchData : function(event) {
							event.data.control._hideError();
							event.data.control._startWait();
							ajaxOpts = {
								success : event.data.control._searchDataCallback,
								data : event.data.control._getServiceParams(),
								error : event.data.control._searchDataErrorCallback,
								complete : event.data.control._stopWait,
								type : "POST",
								context : event.data.control
							};
							jQuery.ajax(event.data.control.options.serviceUrl,
									ajaxOpts);
						},

						_searchDataCallback : function(data, textStatus, jqXHR) {
							// riattivazione Block UI
							blockUIEnabled = true;
							if (data) {
								if (data.error) {
									this._displayError(data.error);
								} else {
									this._displayResults(data);
								}
							} else {
								this._displayError(this.options.ERRORS.noData);
							}
						},

						_searchDataErrorCallback : function(data, textStatus,
								jqXHR) {
							// riattivazione Block UI
							blockUIEnabled = true;
							var errMsg = this.options.ERRORS.serverError;
							// TODO cercare di appendere al messaggio una
							// descrizione dell'errore quando è possibile
							// recuperarla
							this._displayError(errMsg);
						},

						// funzione che restituisce l'oggetto con i parametri da
						// passare alla chiamata del servizio ajax
						_getServiceParams : function() {
							var params = this.options.serviceParams;
							if (jQuery.isFunction(this.options.serviceParams)) {
								params = this.options.serviceParams.call();
							}
							return params;
						},

						// funzione che recupera il valore dai dati restituiti e
						// lo imposta nel campo
						_displayResults : function(data) {
							var value = getNestedProperty(
									this.options.valuePath, data);
							this.element.val(value);
						},

						_displayError : function(errorMsg) {
							var span = jQuery(escapeCssSelector("#"
									+ this.element_id + "_error_message"));
							span.text(errorMsg);
							span.show();
						},

						_hideError : function() {
							var span = jQuery(escapeCssSelector("#"
									+ this.element_id + "_error_message"));
							span.text("");
							span.hide();
						},

						_getWrapper : function(element) {
							var elementId = element.attr('id');
							var wrapper = jQuery(escapeCssSelector('#'
									+ elementId + '_wrapper'));
							if (wrapper.length == 0) {
								element
										.wrap("<div id='"
												+ elementId
												+ "_wrapper' class='init-autocomlete-wrapper'></div>");
							}
							wrapper = jQuery(escapeCssSelector('#' + elementId
									+ '_wrapper'));
							return wrapper;
						},

						_startWait : function() {
							this.triggerButtonLabel.removeClass(
									this.options.triggerButtonClass).addClass(
									'btn-attesa');
						},

						_stopWait : function() {
							this.triggerButtonLabel.removeClass('btn-attesa')
									.addClass(this.options.triggerButtonClass);
						}
					});
})(jQuery);

// funzione di utilità che restituisce il valore di una proprietà da un oggetto
// JSON. La proprietà può essere anche annidata a qualsiasi livello
getNestedProperty = function(propName, from) {
	var value = from ? from : "";
	if (propName && from) {
		var propPath = propName.split('.');
		value = from[propPath[0]];
		if (value != null && value != undefined && propPath.length > 1) {
			propPath = propPath.slice(1);
			value = getNestedProperty(propPath.join('.'), value);
		}
	}
	return value;
};

// funzione di utilità per l'escape dei selettori CSS
escapeCssSelector = function(toEscape) {
	toEscape = "" + toEscape;
	toEscape = toEscape.replace(/(\.)/g, "\\$1");
	return toEscape;
};

relativeURL = function(documentUrl , isEscape){
	// rimuove /backend/ dal pathname
	var rel = documentUrl.pathname;
	rel = rel.substring(1); // elimino / iniziale
	rel = rel.substring(rel.indexOf('/')+1);
	rel = '../'+rel; // aggiungo path relativo ../letteretipo/metod.htm ecc.....
	if(documentUrl.search){
		rel += documentUrl.search;
	}
	if(documentUrl.hash){
	rel += documentUrl.hash;
	}
	if(isEscape){
		rel = escape(rel);
	}
	return rel;
}

var impostaInnerHTMLConScript = function(elm, html) {
  elm.innerHTML = html;
  Array.from(elm.querySelectorAll("script")).forEach( oldScript => {
    const newScript = document.createElement("script");
    Array.from(oldScript.attributes)
      .forEach( attr => newScript.setAttribute(attr.name, attr.value) );
    newScript.appendChild(document.createTextNode(oldScript.innerHTML));
    oldScript.parentNode.replaceChild(newScript, oldScript);
  });
}


var aggiornaMetadatoOggettoBoolean = async function(obj){
	
	if(confirm(obj.getAttribute('data-messaggio-conferma'))){
		
		
		
		let metadato = obj.getAttribute('data-metadato');
		let codiceoggetto = obj.getAttribute('data-id');
		let valore = obj.getAttribute('data-valore');
		
		let dataClass = obj.getAttribute('data-class');
	
		let dataClassTrue = obj.getAttribute('data-class-valore-true');
		let dataClassFalse = obj.getAttribute('data-class-valore-false');
		
		let etichettaNonAttivato = obj.getAttribute('data-etichetta-dato-sensibile-non-attivo');
		let etichettaAttivato = obj.getAttribute('data-etichetta-dato-sensibile-attivo');
		let nuovaEtichettaTitle = etichettaAttivato;
		
		let valoreDB = 1;
		if(valore === 'true'){
			valoreDB = 0;
			valore = 'false';
			nuovaEtichettaTitle = etichettaNonAttivato;
		} 
		
		vbg.mostraModalCaricamento();
		let formData = new FormData();
		formData.append('codiceoggetto', codiceoggetto);
		formData.append('metadato', metadato);
		formData.append('valore', valoreDB);
		let url = '../file/ajaxModificaMetadato.htm';
		let response = await fetch(url, {
			 method: 'POST',
			 cache: "no-cache",
		     body: formData         
		});
		
		
		
		if (response.status !== 200) {
            const errore = await response.text();
            console.error(errore);
            throw errore;
        }
        vbg.nascondiModalCaricamento();
        obj.title = nuovaEtichettaTitle;
        obj.setAttribute('data-valore', valore);
        
		let elementiTrovati = document.querySelectorAll('.'+dataClass);
        
        if( elementiTrovati.length == 0 ){
			let icona = obj.querySelector('.'+dataClass);
			_cambiaClasse(icona,dataClassFalse,dataClassTrue);
			return;
		}
        
       	elementiTrovati.forEach( e =>  {
			_cambiaClasse(e,dataClassFalse,dataClassTrue);
		}); 
	
	}
}

function glbAjaxHistorySet(url){
		
		var jhqr = jQuery.ajax({
			  url: '../history/ajaxSet.htm?ReturnTo='+url,
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  success: function(data) { 				   
				} 
			});
		
	}

function _cambiaClasse(obj,classe1,classe2){
	
	if(obj.classList.contains(classe1)){
		obj.classList.remove(classe1);
		obj.classList.add(classe2);	
	}else{
		obj.classList.remove(classe2);
		obj.classList.add(classe1);	
	}
}
