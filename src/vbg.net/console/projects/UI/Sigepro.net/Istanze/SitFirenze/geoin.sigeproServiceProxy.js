/// <reference path="~/js/jquery.min.js" />

function GeoInServiceProxy(ashxPathService,pageContext) {
	/// <summary>Proxy del web service di lettura dati per il plugin geoin</summary>

    this._path      = ashxPathService.path;
    this._idComune  = pageContext.idComune;
    this._software  = pageContext.software;
    this._token     = pageContext.token;

	this.getParametriVerticalizzazione = function (onSuccess) {

	    this.getParametriVerticalizzazioneOverrideSoftware(this._software, onSuccess);
	};

	this.rimuoviIdPunto = function (_idStradario, key) {
	    var self = this;
	    $.ajax({
	        async: false,
	        type: 'POST',
	        url: this._path + '/RimuoviIdPunto',
	        data: JSON.stringify({
	            token: self._token,
	            idComune: self._idComune,
	            idStradario: _idStradario
	        }),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json"
	    });
	};

	this.salvaIdPunto = function (_idStradario, key) {
	    var self = this;
	    $.ajax({
            async: false,
            type: 'POST',
            url: this._path + '/SalvaIdPunto',
	        data: JSON.stringify({
	            token: self._token,
	            idComune: self._idComune,
	            idStradario: _idStradario,
                idPunto: key
	        }),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json"
        });
	};

	this.getParametriVerticalizzazioneOverrideSoftware = function (overrideSoftware, onSuccess) {
	    /// <summary>Legge i parametri della verticalizzazione sitquaestioflorentia</summary>
	    var self = this;
	    $.ajax({
	        type: "POST",
	        url: this._path + '/GetParametriVerticalizzazione',
	        data: JSON.stringify({
	            token: self._token,
	            idComune: self._idComune,
	            software: overrideSoftware
	        }),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
	            if (data.d.errore) {
	                alert("Errore in getParametriVerticalizzazione: " + data.d.errore);
	                return;
	            }

	            onSuccess(data.d);
	        }
	    });
	};

	this.getPermessiEditing = function (modalita, onSuccess) {
	    var self = this;

	    $.ajax({
	        type: "POST",
	        url: this._path + '/GetPermessiEditing',
	        data: JSON.stringify({
	            token: self._token,
	            idComune: self._idComune,
	            software: self._software,
	            modalita: modalita
	        }),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
	            if (data.d.errore) {
	                alert("Errore in getParametriVerticalizzazione: " + data.d.errore);
	                return;
	            }

	            onSuccess(data.d);
	        }
	    });
	};

	this.getCodiciCiviciAttivita = function (stato, onSuccess) {
	    var self = this;

	    $.ajax({
	        type: "POST",
	        url: this._path + '/GetCodiciCiviciAttivita',
	        data: JSON.stringify({
	            token: self._token,
	            idComune: self._idComune,
	            software: self._software,
	            stato: stato
	        }),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
	            if (data.d.errore) {
	                alert("Errore in getCodiciCiviciAttivita: " + data.d.errore);
	                return;
	            }

	            console.log("chiamata a getCodiciCiviciAttivita riuscita, risultato: " + data.d);

	            onSuccess(data.d);
	        }
	    });
	};

	this.getDettagliIstanzaDaIdPunto = function (idPunto, onSuccess) {
	    var self = this;

	    $.ajax({
	        type: "POST",
	        url: this._path + '/GetDettagliIstanzaDaIdPunto',
	        data: JSON.stringify({
	            token: self._token,
	            idPunto: idPunto
	        }),
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
	            if (data.d.errore) {
	                alert("Errore in getDettagliIstanzaDaIdPunto: " + data.d.errore);
	                return;
	            }

	            console.log("chiamata a getDettagliIstanzaDaIdPunto riuscita, risultato: " + data.d);

	            onSuccess(data.d);
	        }
	    });
	};
}