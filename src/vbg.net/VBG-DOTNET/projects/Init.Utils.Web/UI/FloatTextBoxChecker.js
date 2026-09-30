/*jslint continue: true */
/*jslint plusplus: true */
/*global alert*/
function NumberValidator() {
    'use strict';
    
	this.defaultErrorMessage = "Valore numerico non valido";
    
    this.Initialize = function (control, errMsg) {
		if (control) {
			control.oldValue = control.value;
			control.errorMessage =  errMsg || this.defaultErrorMessage;
		}
	};
    
	this.ValidateFloat = function (control) {
        
		if (!control.oldValue) {
            control.oldValue = "";
        }
	
		var valore = control.value,
            patt = new RegExp("^(\\+|-)?(((\\d{1,3}\\.)*(\\d{3})(,\\d+)?)|(\\d+(,\\d+)?))$"),
            arr;
		
		if (valore.indexOf(',') === -1) {
			arr = valore.split('.');
		
			if (arr.length === 2 && arr[1].length !== 3) {
				valore = valore.replace('.', ',');
            }
		}
		
		if (valore !== "" && !valore.match(patt)) {
			alert(control.errorMessage);
			control.value = control.oldValue;
			control.focus();
			
			return false;
		}
		
		control.oldValue = control.value;
		control.value = valore;
		
		return true;
	};
    
	this.ValidateInt = function (control) {
        var i,
            val;
        
		if (!control.oldValue) {
            control.oldValue = "";
        }
	
		for (i = 0; i < control.value.length; i++) {
			val = control.value.charAt(i);

			if (i === 0 && val === '-') {
                continue;
            }

			if (isNaN(val)) {
				alert(control.errorMessage);
				control.value = control.oldValue;
				control.focus();
				return false;
			}
		}

		control.oldValue = control.value;

		return true;
	};
}


var g_numberValidator = new NumberValidator();