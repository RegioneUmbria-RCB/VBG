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

    //$(el).blur();
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


function d2Errore(TextBox) {
    alert('La data immessa non è valida');
    TextBox.value = "";
    TextBox.focus();
}

function d2IsValidDate(TextBox)	//Sistema le date dei text box 
{										// in input vuole la text box stessa
    //se MostraERR=true allora dà un alert in caso di data errata
    var dateStr = TextBox.value,
        datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{2,4})$/,
        aData = [],
        day = '',
        month = '',
        year = '';

    if (dateStr === '') {
        return true;
    }

    if (dateStr.match("/") === null) {
        if (dateStr.length === 8 || dateStr.length === 6) {
            dateStr = dateStr.substring(0, 2) + "/" + dateStr.substring(2, 4) + "/" + dateStr.substring(4, dateStr.length);
            TextBox.value = dateStr;
        } else {
            d2Errore(TextBox);
            return false;
        }
    } else {
        aData = dateStr.split("/");
        day = aData[0].padStart(2, '0');
        month = aData[1].padStart(2, '0');
        year = aData[2];

        if (year.length !== 4) {
            d2Errore(TextBox);
            return false;
        }

        TextBox.value = day + "/" + month + "/" + year;
    }

    var matchArray = dateStr.match(datePat);

    if (matchArray === null) {
        if (MostraERR) {
            d2Errore(TextBox);
        }
        return false;
    }
    month = matchArray[3];
    day = matchArray[1];
    year = matchArray[4];

    // Aggiusta l'anno
    switch (year.length) {
        case 2:
            {
                if (parseInt(year.substring(0, 2)) < 21)
                    year = "20" + year.substring(0, 2);
                else
                    year = "19" + year.substring(0, 2);
                break;
            }

    }
    dateStr = dateStr.substring(0, 6) + year;
    //Riscrive la data nel text box
    TextBox.value = dateStr;

    if (month < 1 || month > 12) {
        d2Errore(TextBox);
        return false;
    }

    if (day < 1 || day > 31) {
        d2Errore(TextBox);
        return false;
    }

    if ((month === 4 || month === 6 || month === 9 || month === 11) && day === 31) {
        d2Errore(TextBox);
        return false
    }

    if (month === 2) {
        var isleap = (year % 4 === 0 && (year % 100 !== 0 || year % 400 === 0));
        if (day > 29 || (day === 29 && !isleap)) {
            d2Errore(TextBox);
            return false;
        }
    }
    return true;
}







function DateTextBoxGetSetValueExtender(el) {
    'use strict';
    var icon = $("<span></span>", { "class": "glyphicon glyphicon-calendar d2-date-addon" });
    icon.insertAfter($(el));


    el.GetValue = function () {
        if (this.value === '') {
            return { valore: "", valoreDecodificato: "" };
        }

        d2IsValidDate(this);

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

function HtmlControlGetSetValueExtender(el) {
    el.GetValue = function () { return { valore: $(this).html(), valoreDecodificato: $(this).html() }; };
    el.SetValue = function (value) {

        var oldValue = $(this).html();

        $(this).html(value);

        return oldValue !== value;
    };
}