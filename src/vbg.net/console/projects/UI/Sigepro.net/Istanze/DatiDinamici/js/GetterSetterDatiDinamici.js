/*jslint browser: true*/
/*jslint plusplus: true */
/*jslint continue:true */
/*global $, jQuery, alert */
function SimpleGetSetValueExtender(el) {
    'use strict';
    
	el.GetValue = function () { return { valore: this.value, valoreDecodificato: this.value }; };
	el.SetValue = function (value) {

		var oldValue = this.value;

		this.value = value;

		return oldValue !== value;
	};
}


function IntGetSetValueExtender(el) {
    'use strict';
 
    $(el).keypress(function (e) {
        if (event.keyCode !== 44 && event.keyCode !== 8) {
            // Ensure that it is a number and stop the keypress
            if (event.keyCode < 48 || event.keyCode > 57) {
                event.preventDefault();
            }
        }
    });
    
    $(el).blur(function () {
        var valoreDefault = $(this).data('valoreDefault');
        
        if ($(this).val() === '' && valoreDefault !== '') {
            $(this).val(valoreDefault);
        }
    });
    
    el.GetValue = function () { return { valore: this.value, valoreDecodificato: this.value }; };
	el.SetValue = function (value) {

		var oldValue = this.value;

		this.value = value;

		return oldValue !== value;
	};
    
    $(el).blur();
}

function SearchGetSetValueExtender(el) {
    'use strict';
    
	var inputs = $(el).parent().find('input');
	
	if (inputs.length !== 2) {
		alert("SearchGetSetValueExtender: sono stati trovati " + inputs.length + " campi di input invece di 2");
    }

	el.onchange = null;

	el.campoCodice = inputs[0];
	el.campoDescrizione = inputs[1];

	el.campoDescrizione.onblur = function () {
		if (el.onchange) {
			el.onchange();
        }
	};



	el.GetValue = function () {
		var val = this.campoCodice.value,
            valDecodificato = this.campoDescrizione.value,
            valore = { valore: val, valoreDecodificato: valDecodificato };

		return valore;
	};
    
	el.SetValue = function (value) {

		var oldValue = this.campoCodice.value;

		this.campoCodice.value = value;

		return oldValue !== value;
	};
}

function DropDownGetSetValueExtender(el) {
    'use strict';
    
	el.GetValue = function () {
		var val = this.value,
            valDecodificato = val,
            i = 0;
        
		for (i = 0; i < this.options.length; i++) {
			if (this.options[i].selected) {
                valDecodificato = this.options[i].text;
            }
		}

		return { valore: val, valoreDecodificato: valDecodificato };
	};
	el.SetValue = function (value) {
		var oldValue = this.value;

		this.value = value;

		return oldValue !== value;
	};
}

function MultiSelectGetSetValueExtender(el) {
    'use strict';
    
	el.GetValue = function () {
		var val = "",
            valDec = "",
            isFirst = true,
            i = 0;

		for (i = 0; i < this.options.length; i++) {
			if (!this.options[i].selected) {
                continue;
            }

			if (!isFirst) {
				val += ";";
				valDec += ";";
			}

			val += this.options[i].value;
			valDec += this.options[i].text;

			isFirst = false;
		}

		//Debug.Write( val );

		return { valore: val, valoreDecodificato: valDec };
	};
	el.SetValue = function (value) {

		var oldVal = this.GetValue().valore,
            tmp = value.split(";"),
            i = 0,
            j = 0;

		for (i = 0; i < tmp.length; i++) {
			for (j = 0; j < this.options.length; j++) {
				if (this.options[j].value === tmp[i]) {
					this.options[j].selected = true;
					continue;
				}
			}
		}

		return value !== oldVal;
	};
}

function CheckBoxGetSetValueExtender(el) {
    'use strict';
    
	el.innerD2Control = $(el).find('input[type=checkbox]')[0];
	el.valoreTrue = $(el).data('valoreTrue').toString();
	el.valoreFalse = $(el).data('valoreFalse').toString();

	$(el).bind('errorSet', function () {
	    $(el.innerD2Control).addClass('d2Errori');
	});

	$(el).bind('errorRemoved', function () {
	    $(el.innerD2Control).removeClass('d2Errori');
	});

	el.GetValue = function () {
		var checked = this.innerD2Control.checked,
            valore = checked ? this.valoreTrue : this.valoreFalse;
        
		return { valore: valore, valoreDecodificato: valore };
	};
	el.SetValue = function (value) {
		var oldValue = this.innerD2Control.checked;

		this.innerD2Control.checked = value === this.valoreTrue;

		return oldValue !== this.innerD2Control.checked;
	};
}

function DateTextBoxGetSetValueExtender(el) {
    'use strict';
    
	el.GetValue = function () {
		if (this.value === '') {
			return {valore: "", valoreDecodificato: "" };
        }

		var data = this.value,
            parts = data.split('/');

		if (parts.length === 3) {
			return {
                valore: String(parts[2]) + String(parts[1]) + String(parts[0]),
                valoreDecodificato: this.value
            };
		}

		alert("Impossibile ottenere il valore del campo " + this.id);

		return { valore: "", valoreDecodificato: "" };
	};
	el.SetValue = function (v) {
		var oldVal = this.value,
            yyyy,
            mm,
            dd;

		if (!v) {
			this.value = "";
			return;
		}

		if (v.length !== 8) {
			alert("Il valore del campo " + this.id + " non è una data valida (" + v + ")");
			this.value = "";
			return;
		}

		yyyy = v.slice(0, 4);
		mm = v.slice(4, 6);
		dd = v.slice(6, 8);
        
		this.value = dd + "/" + mm + "/" + yyyy;

		return oldVal !== this.value;
	};
}

function UploadGetSetValueExtender(el) {
    'use strict';
    
	el.GetValue = function () {
		return {
			valore: this.value,
			valoreDecodificato: $(this).attr('valoreDecodificato')
		};
	};

	el.SetValue = function (value) {
		var oldVal = this.value;

		this.value = value;

		return oldVal !== value;
	};
}

function ButtonGetSetValueExtender(el) {
    'use strict';
    
	el.GetValue = function () {
		var val = new Date().getTime().toString();
		return {
			valore: val,
			valoreDecodificato: val
		};
	};

	el.SetValue = function (value) {
		return false;
	};
}