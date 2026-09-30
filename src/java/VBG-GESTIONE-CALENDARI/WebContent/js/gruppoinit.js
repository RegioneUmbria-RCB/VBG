
/**
 * Sistema le date dei text box in input vuole la text box stessa se
 * MostraERR=true allora dà un alert in caso di data errata
 */
function isValidDate(TextBox,MostraERR)	{										
										
dateStr = TextBox.value;
var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{2,4})$/;

	if (dateStr == ""){
		return true;
	}
	if (dateStr.match("/") == null){
		if ((dateStr.length=8)|| (dateStr.length=6)){
			var dateStrTmp = dateStr.substring(0,2) + "/" + dateStr.substring(2,4) + "/" + dateStr.substring(4,dateStr.length);
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
			}else{
				erroreData(TextBox);
			}
	}else{
		aData=dateStr.split("/");
		day = aData[0];
		month = aData[1];
		year = aData[2];
		if (day.length==1){
			day="0" + day;
		}
		if (month.length==1){
			month="0" + month;
		}	
		var dateStrTmp = day + "/" + month + "/" + year;
		dateStr = dateStrTmp;
		TextBox.value = dateStr;
	}	
	var matchArray = dateStr.match(datePat);
	if (matchArray == null) {
		if (MostraERR){
			erroreData(TextBox);
		}
		return false;
	}
	month = matchArray[3]; 
	day = matchArray[1];
	year = matchArray[4];
	// Aggiusta l'anno
	switch (year.length){
		case 2: 
			if (year > "20"){
				year = "19" + year.substring(0,2) ;
			}else{
				year = "20" + year.substring(0,2) ;
			}
			break;
	}	
	dateStr = dateStr.substring(0,6) + year;
	// Riscrive la data nel text box
	TextBox.value = dateStr;
	
	if (month < 1 || month > 12) { 
		if (MostraERR){
			erroreData(TextBox);
		}
		return false;
	}

	if (day < 1 || day > 31) {
		if (MostraERR){
			erroreData(TextBox);
		}
		return false;
	}

	if ((month==4 || month==6 || month==9 || month==11) && day==31) {
		if (MostraERR){
			erroreData(TextBox);
		}
		return false;
	}

	if (month == 2) { 
	var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	if (day>29 || (day==29 && !isleap)) {
		if (MostraERR){
			erroreData(TextBox);
		}
		return false;
	   }
	}
	return true;
}

function erroreData(TextBox){
	alert("La data non è valida");
	TextBox.value="";
	TextBox.focus();
}

function onInvokeAction(id) {
	jQuery.jmesa.setExportToLimit(id, '');
	jQuery.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
}