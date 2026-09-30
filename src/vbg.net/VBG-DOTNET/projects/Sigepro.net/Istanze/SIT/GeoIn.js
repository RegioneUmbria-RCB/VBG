/// <reference path="~/js/json.js" />
/// <reference path="~/js/jquery.min.js" />
/// <reference path="~/js/jquery-ui.min.js" />
//funzione per la gestione del file xml restituti dai vari metodi del componente GIS

var xmlDoc = null;
var _trg = null;
var _gis_plugin = null;
var _loaded = false;
var tipoIntervento;

var _geoInConfig = {
	param1: '',
	param2: '',
	param3: '',
	contesto: '',
	software: '',
	token: '',
	codice: '',
	modalita: '',
	baseUrl: '',
	returnTo: ''
};


function GeoInServiceProxy(ashxPath, token, idComune, software) {
	/// <summary>Proxy del web service di lettura dati per il plugin geoin</summary>

	this._path		= ashxPath;
	this._idComune  = idComune;
	this._software  = software;
	this._token		= token;

	this.getParametriVerticalizzazione = function (onSuccess) {

		this.getParametriVerticalizzazioneOverrideSoftware(this._software, onSuccess);
	}

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
	}

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
	}

	this.geoInListKey = function(stato, onSuccess){
		var self = this;

		$.ajax({
			type: "POST",
			url: this._path + '/GeoInListKey',
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
					alert("Errore in geoInListKey: " + data.d.errore);
					return;
				}

				onSuccess(data.d);
			}
		});
	}
}


function GeoInPlugin(configurazione) {
	/// <summary>Plugin per accedere alle funzionalità esposte dal sistema geoin</summary>
	this._geoInConfig = configurazione;
	this._serviceProxy = new GeoInServiceProxy(this._geoInConfig.serviceUrl,
												this._geoInConfig.token,
												this._geoInConfig.idComune,
												this._geoInConfig.software);
	this._loaded = false;

	this._gis_plugin = null;
	this._trg = null;
	this._parametriVerticalizzazione = null;

	var self = this;

	this._serviceProxy.getParametriVerticalizzazione(function (data) {
		self._parametriVerticalizzazione = data;
		self.inizializzaPlugin();
	});
	
}
GeoInPlugin.prototype = {

	inizializzaPlugin: function () {

		var self = this;

		this._loaded = true;
		this._gis_plugin = new tabula_map();
		//tabula_map.DEBUG = true;
		this._gis_plugin.init("Panel_centrale");
		this._gis_plugin.manage_layout();

		this.attachSignaler();


		if (this._geoInConfig.contesto == "Stradario") {

			var arg = this._geoInConfig.param2;

			if (this._geoInConfig.param3 == 'R')
				arg = this._geoInConfig.param2 + ' ' + this._geoInConfig.param3;

			this._gis_plugin.find_via_civico(this._geoInConfig.param1, arg, searcher.STRICT_MODE);
		}

		if (this._geoInConfig.contesto == "Mappale") {
			this._gis_plugin.find_foglio_particella(this._geoInConfig.param1, this._geoInConfig.param2, searcher.STRICT_MODE);
		}
		
		this._serviceProxy.getPermessiEditing(this._geoInConfig.modalita, function (data) {
			self._gis_plugin.edit_visibility(data);
		});
	},

	attachSignaler: function () {
		if (this._trg != null)
			return;

		var self = this;

		this._trg = {
			PageInit: function () { self.pageInit(); },
			EndFind: function (find_type, result) { self.endFind(find_type, result); },
			ElementEditing: function () { self.elementEditing(); },
			EndEditing: function (layer, key) { self.endEditing(layer, key) },
			ElementDeleted: function (layer, key) { self.elementDeleted(layer, key); },
			ElementAdded: function (key) { self.elementAdded(key); },
			ElementMoved: function (layer, key) { self.elementMoved(layer, key); },
			ElementClicked: function (layer, key) { self.elementClicked(layer, key); },
			KeyBuilded: function (key) { self.keyBuilded(key); },
			PlugInError: function (code, err) { self.pluginError(code, err); }
		};

		this._gis_plugin.attach_event_observer(this._trg);
	},
	pageInit: function () { },
	elementEditing: function () { },
	endFind: function (find_type, result) {

		//alert(result);

		var self = this;

		this.loadXmlFile(result, function (xmlDoc) {

			var elements = xmlDoc.getElementsByTagName("item");

			if (!elements.length) {
				alert("L'elemento trovato non è valido");
				return;
			}

			var orders = "";
			for (i = 0; i < elements.length; i++) {
				switch (find_type) {
					case "topo":
						orders += elements[i].getAttributeNode("order").nodeValue + ";";
					case "via_civ":
						orders += elements[i].getAttributeNode("order").nodeValue + ";";
						break;
					case "fgl_part":
						if ((self._geoInConfig.param3 == 'F') && (elements[i].firstChild.nodeValue.match(/FABBRICATI/) != null))
							orders += elements[i].getAttributeNode("order").nodeValue + ";";

						if ((self._geoInConfig.param3 == 'T') && (elements[i].firstChild.nodeValue.match(/PARTICELLE/) != null))
							orders += elements[i].getAttributeNode("order").nodeValue + ";";
						break;
				}
			}

			orders = orders.substr(0, orders.length - 1);

			self._gis_plugin.goto_element_from_idx(orders);
			self._gis_plugin.blink_element_from_idx(orders);
		});
	},
	endEditing: function (layer, key) { },
	elementDeleted: function (layer, key) { },
	elementAdded: function (key) { },
	elementMoved: function (layer, key) { },
	elementClicked: function (layer, key) { },
	keyBuilded: function (key) { },
	pluginError: function (code, err) {
		alert("Plugin error, codice:" + code + " descrizione: " + err);
	},

	loadXmlFile: function (xmlFile, onParseCompleted) {
		if (window.DOMParser) {
			parser = new DOMParser();
			xmlDoc = parser.parseFromString(xmlFile, "text/xml");
		}
		else // Internet Explorer
		{
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(xmlFile);
		}

		onParseCompleted(xmlDoc);
	},

	toggleLayerVisibility: function (mostraLivello, softwareName, mostraAttive, mostraCessate) {
		var self = this;


		this._serviceProxy.getParametriVerticalizzazioneOverrideSoftware(softwareName, function (data) {

			var panel_key = data.panelKey;
			var layer_key = data.layerKey;
			var renderer_key_attive = data.rendererKeyAttive;
			var renderer_key_cessate = data.rendererKeyCessate;

			if (panel_key == "topo" || panel_key == "background") {
				self._gis_plugin.overlay_visibility(panel_key, mostraLivello);
				return;
			}

			if (!mostraLivello) {
				self._gis_plugin.overlay_delete(panel_key);
				//self._gis_plugin.overlay_refresh(panel_key);

				return;
			}

			if (mostraAttive) {
				self._serviceProxy.geoInListKey("attive", function (data) {
					if (data.length > 0) {
						var idsAttive = self.listaIdToString(data, ";");
						self._gis_plugin.overlay_elements(panel_key, layer_key, renderer_key_attive, idsAttive);
						self._gis_plugin.overlay_refresh(panel_key);
					}
					else {
						alert("Non ci sono attivita' attive da visualizzare!");
					}
				});
			}

			if (mostraCessate) {
				self._serviceProxy.geoInListKey("cessate", function (data) {
					if (data.length > 0) {
						var idsCessate = self.listaIdToString(data, ";");

						self._gis_plugin.overlay_elements(panel_key, layer_key, renderer_key_cessate, idsCessate);
						self._gis_plugin.overlay_refresh(panel_key);
					}
					else {
						alert("Non ci sono attivita' cessate da visualizzare!");
					}
				});
			}
		});
	},

	listaIdToString: function (array, separator) {
		var str = "";

		for (var i = 0; i < array.length; i++) {
			str += ";";
			str += array[i];
		}

		if (str.length > 0)
			str = str.slice(1);

		return str;
	}

}


function visibility_layer(id, idAttive, idCessate, softwareChkBox) {

	var mostraLivello = document.getElementById(id).checked;
	var mostraAttive  = document.getElementById(idAttive).checked;
	var mostraCessate = document.getElementById(idCessate).checked;

	if (mostraLivello && !mostraAttive && !mostraCessate) {
		alert("Selezionare se si intende visualizzare le attivita' attive e/o cessate!");

		document.getElementById(id).checked = false;

		return;
	}


	plugin.toggleLayerVisibility(mostraLivello, softwareChkBox, mostraAttive, mostraCessate);
}

function LoadXmlFile(xmlFile) {

    if (window.DOMParser) {
        parser = new DOMParser();
        xmlDoc = parser.parseFromString(xmlFile, "text/xml");
    }
    else // Internet Explorer
    {
        xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = "false";
        xmlDoc.loadXML(xmlFile);
    }
}
/////////////////////////////////////


/////////////////////////////////////
//Funzioni per la gestione dei vari eventi
//function PageInit() {
//}
//function EndFind(find_type, result) {
//    LoadXmlFile(result);
//    var elements = xmlDoc.getElementsByTagName("item");
//    var orders = "";
//    for (i = 0; i < elements.length; i++) {
//        switch (find_type) {
//            case "topo":
//                orders += elements[i].getAttributeNode("order").nodeValue + ";";
//            case "via_civ":
//                orders += elements[i].getAttributeNode("order").nodeValue + ";";
//                break;
//            case "fgl_part":
//            	if ((_geoInConfig.param3 == 'F') && (elements[i].firstChild.nodeValue.match(/FABBRICATI/) != null))
//                    orders += elements[i].getAttributeNode("order").nodeValue + ";";

//            	if ((_geoInConfig.param3 == 'T') && (elements[i].firstChild.nodeValue.match(/PARTICELLE/) != null))
//                    orders += elements[i].getAttributeNode("order").nodeValue + ";";
//                break;
//        }
//    }

//    orders = orders.substr(0, orders.length - 1);
//    _gis_plugin.goto_element_from_idx(orders);
//    _gis_plugin.blink_element_from_idx(orders);
//}


function ElementEditing(layer, key) {
	microAjax("http://" + location.host + location.pathname.replace('GeoIn.aspx', 'Handlers/GeoInGestioneVert.ashx') + "?Token=" + _geoInConfig.token + "&software=" + _geoInConfig.software, function (res) {
        if (res.match("Error") == null)
            _gis_plugin.overlay_refresh(getElement(res, 0, 10));
        else {
            alert(res);
        }
    });

    microAjax("http://" + location.host + location.pathname.replace('GeoIn.aspx', 'Handlers/GeoInUpdateGeoReferenzazione.ashx') + "?Token=" + _geoInConfig.token + "&software=" + _geoInConfig.software + "&Codice=" + _geoInConfig.codice + "&Key=" + key + "&TipoIntervento=" + tipoIntervento, function (res) {
        alert(res);
    });
}

function ElementDeleted(key) {
    //alert("ElementDeleted\n"+key+"\nargomenti:"+arguments.length);
	microAjax("http://" + location.host + location.pathname.replace('GeoIn.aspx', 'Handlers/GeoInGestioneVert.ashx') + "?Token=" + _geoInConfig.token + "&software=" + _geoInConfig.software, function (res) {
        if (res.match("Error") == null)
            _gis_plugin.remove_element(getElement(res, 1, 10), key, "1");
        else
            alert(res);
    });

    tipoIntervento = "remove";
}
function ElementAdded(key) {
	microAjax("http://" + location.host + location.pathname.replace('GeoIn.aspx', 'Handlers/GeoInGestioneVert.ashx') + "?Token=" + _geoInConfig.token + "&software=" + _geoInConfig.software, function (res) {
        if (res.match("Error") == null)
            _gis_plugin.add_element(getElement(res, 1, 10), key);
        else
            alert(res);
    });

    tipoIntervento = "add";
}

function ElementMoved(key) {
    alert("ElementMoved\n" + key + "\nargomenti:" + arguments.length);
}
function ElementClicked(key) {
    alert("ElementClicked\n" + key + "\nargomenti:" + arguments.length);
}
function KeyBuilded(key) {
    alert("KeyBuilded\n" + key + "\nargomenti:" + arguments.length);
}

///////////////////////////////////





function getElement(res, index, length) {
    result = res.split(",");
    return result[index].substring(length);
}


//function clear_elements(id) {
//    if (!_loaded) {
//        alert("PlugIn geografico non caricato");
//        return;
//    }

//    if (document.getElementById(id).checked) {
//        //create object
//        _gis_plugin.clear_elements();
//    }
//    else {
//    	switch (_geoInConfig.param4) {
//    		case "Stradario":
//    			var tmpParam2 = _geoInConfig.param2;

//    			if (_geoInConfig.param3 == 'R')
//    				tmpParam2 = _geoInConfig.param2 + ' ' + _geoInConfig.param3;
//    			//Evidenzio la via
//    			//_gis_plugin.find_toponomastica(param1,searcher.STRICT_MODE);
//    			//Evidenzio il civico in corrispondnza della via
//    			_gis_plugin.find_via_civico(_geoInConfig.param1, tmpParam2, searcher.STRICT_MODE);
//    			break;
//            case "Mappale":
//            	_gis_plugin.find_foglio_particella(_geoInConfig.param1, _geoInConfig.param2, searcher.STRICT_MODE);
//                break;
//        }
//    }
//}




