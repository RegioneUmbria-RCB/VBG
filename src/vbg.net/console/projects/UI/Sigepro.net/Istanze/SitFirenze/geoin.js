/// <reference path="~/js/json.js" />
/// <reference path="~/js/jquery.min.js" />
/// <reference path="~/js/jquery-ui.min.js" />
/// <reference path="geoin.layer.js" />
/// <reference path="geoin.sigeproServiceProxy.js" />
/// <reference path="geoin.xmlResultsParser.js" />
/// <reference path="geoin.confirmDialog.js" />
/// <reference path="geoin.utils.js" />

function OperazioneInCoda(idPunto,callback){
    var obj = {};

    console.log('Aggiunta operazione in coda per il punto ' + idPunto);

    obj.esegui = function( punto ){
        console.log('tentativo di esecuzione di un\'operazione in coda per il punto ' + punto);

        if(idPunto === punto)
        {
            console.log('operazione in coda per il punto ' + punto + ' eseguita');

            callback();

            return true;
        }

        return false;
    };

    return obj;
}



//funzione per la gestione del file xml restituti dai vari metodi del componente GIS

if (!window.console) console = {};
console.log = console.log || function () { };
console.warn = console.warn || function () { };
console.error = console.error || function () { };
console.info = console.info || function () { };


var xmlDoc = null;
var _trg = null;
var _gis_plugin = null;
var _loaded = false;
var tipoIntervento;

var _geoInConfig = {
    serviceUrl: '',
    idComune: '',
	software: '',
	token: '',
	codice: '',
	modalita: ''
};



function GeoInPlugin( geoInServiceProxy, confirmationDialogElementService, divVisualizzazioneService, geoInCallbackService) {
	/// <summary>Plugin per accedere alle funzionalità esposte dal sistema geoin</summary>
	//this._geoInConfig   = configurazione;
    this._idDivVisualizzazione = divVisualizzazioneService.idDivVisualizzazione;
	this._serviceProxy  = geoInServiceProxy;
	this._loaded        = false;
	this._confirmDialog = new ConfirmDialog(confirmationDialogElementService.element);
	this._gis_plugin    = null;
	this._trg           = null;
	this._layerCorrente = null;
	this._idPunto       = '';
    this._parametriVerticalizzazione = null;
    this._xmlResultParser = new NullXmlResultParser();
    this._operazioniInCoda = [];
    this._geoInCallbackService = geoInCallbackService;
    this._layersVisualizzati = [];

	this.RENDERER_KEY_ISTANZA_CORRENTE = 'pointer_1_b';

	var self = this;

	this._serviceProxy.getParametriVerticalizzazione(function (data) {
	    self._parametriVerticalizzazione = data;

	    console.log(data);

	    self.inizializzaPlugin();

	    self._layerCorrente = new Layer(self._gis_plugin, data.panelKey, data.layerKey, self.RENDERER_KEY_ISTANZA_CORRENTE);

        self._geoInCallbackService.layerCorrenteInizializzato( self );
	});
	
}
GeoInPlugin.prototype = {

    inizializzaPlugin: function () {

        var self = this;

        this._loaded = true;
        this._gis_plugin = new tabula_map();
        //tabula_map.DEBUG = true;
        this._gis_plugin.init(this._idDivVisualizzazione ? this._idDivVisualizzazione : "Panel_centrale");
        this._gis_plugin.manage_layout();

        this.attachSignaler();

        //        this._serviceProxy.getPermessiEditing(this._geoInConfig.modalita, function (data) {
        //            self._gis_plugin.edit_visibility(data);
        //        });

        this._gis_plugin.edit_visibility(true);
    },

    ///
    /// Centra la mappa utilizzando un foglio/particella/catasto
    ///
    focusDaFoglioParticellaCatasto: function (foglio, particella, catasto) {

        this._xmlResultParser = new FoglioParticellaXmlResultsParser( catasto );
        this._gis_plugin.find_foglio_particella(foglio, particella, searcher.STRICT_MODE);
    },

    ///
    /// Centra la mappa utilizzando una via/civico
    ///
    focusDaViaCivico: function (via, civico, colore) {
        var arg = civico;

        if (colore === 'R')
            arg = civico + ' ' + colore;

        this._xmlResultParser = new ViaCivicoXmlResultsParser();
        this._gis_plugin.find_via_civico(via, arg, searcher.STRICT_MODE);
    },

    ///
    /// centra la mappa su un punto
    ///
    focusSuPuntoCorrente: function () {

        this._xmlResultParser = new IdPuntoXmlResultsParser( this._idPunto , this._layerCorrente._layerKey );
        this._gis_plugin.find_layer( this._layerCorrente._layerKey, this._idPunto, searcher.STRICT_MODE );
    },

    ///
    /// imposta il punto corrente (il punto su cui si sta lavorando)
    ///
    impostaPuntoCorrente: function (idPunto) {
        this._idPunto = idPunto;
        this._layerCorrente.mostra(idPunto);
    },

    mostraAziendeAttive : function(){

        var self = this;

        if( !this._layersVisualizzati['aziendeAttive'] )
            this._layersVisualizzati['aziendeAttive'] = new Layer(this._gis_plugin, this._parametriVerticalizzazione.panelKey + 'attive', this._parametriVerticalizzazione.layerKey, this._parametriVerticalizzazione.rendererKeyAttive);

        this._serviceProxy.getCodiciCiviciAttivita( 'attive' , function(data){
            if(data && data.length)
            {
                var ids = self.listaIdToString(data, ";");
                self._layersVisualizzati['aziendeAttive'].mostra( ids );
            }
        } );
    },

    nascondiAziendeAttive : function(){
        if( this._layersVisualizzati['aziendeAttive'] )
        {
            this._layersVisualizzati['aziendeAttive'].nascondi();
        }
    },

    mostraAziendeCessate : function(){
        var self = this;

        if( !this._layersVisualizzati['aziendeCessate'] )
            this._layersVisualizzati['aziendeCessate'] = new Layer(this._gis_plugin, this._parametriVerticalizzazione.panelKey + 'cessate', this._parametriVerticalizzazione.layerKey, this._parametriVerticalizzazione.rendererKeyCessate);

        this._serviceProxy.getCodiciCiviciAttivita( 'cessate' , function(data){
            if(data && data.length)
            {
                var ids = self.listaIdToString(data, ";");
                self._layersVisualizzati['aziendeCessate'].mostra( ids );
            }
        } );
    },

    nascondiAziendeCessate : function(){
        if( this._layersVisualizzati['aziendeCessate'] )
        {
            this._layersVisualizzati['aziendeCessate'].nascondi();
        }
    },


    /// 
    /// Collega gli handler degli eventi del plugin
    /// 
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
            PlugInError: function (code, err) { self.pluginError(code, err); },
        };

        this._gis_plugin.attach_event_observer(this._trg);
    },

    pageInit: function () { },
    elementEditing: function () { console.log("elementEditing") },
    endFind: function (find_type, xmlResult) {

        var self = this;

        console.log( xmlResult );

        console.log( "tipo di xmlParser " + this._xmlResultParser.nomeTipo );

        this._xmlResultParser.parse(find_type, xmlResult, function (results, blinkElement) {
            if (!results || !results.length) {
                alert('Nessun elemento trovato');
                return;
            }

            var item = results[0];

            console.log( "Focus sull'elemento " + item );

            self._gis_plugin.goto_element_from_idx(item);
            
            if(blinkElement)
                self._gis_plugin.blink_element_from_idx(item);
        });
    },
    endEditing: function (layer, key) 
    { 
        console.log('endEditing-> layer:' + layer + ', key:' + key + ', ' + this._operazioniInCoda.length + ' operazioni in coda'); 

        var elementiDaTenere = [];

        for( var i = 0; i < this._operazioniInCoda.length ; i++)
        {
            var item = this._operazioniInCoda[i];
            
            if( item.esegui(key) )
            {
                console.log('eseguita operazione su elemento ' + key );
                continue;
            }

            console.log('operazione non eseguita su elemento ' + key );

            elementiDaTenere.push( item );
        }

        this._operazioniInCoda = elementiDaTenere;        
    },
    elementClicked: function (layer, key) { 
        console.log('elementClicked-> layer:' + layer + ', key:' + key); 

        this._geoInCallbackService.puntoCliccato( key );
    },
    keyBuilded: function (key) { console.log('keyBuilded-> key:' + key); },
    pluginError: function (code, err) {
        alert("Plugin error, codice:" + code + " descrizione: " + err);
    },

    elementAdded: function (key) {
        console.log("elementAdded:" + key);

        if (!this._idPunto) {
            console.log("non esiste un punto corrente, il nuovo punto verrà aggiunto al livello:" + key);
           
            var self = this;

            this._confirmDialog.confermaAggiuntaPunto(function () {

                self._operazioniInCoda.push( new OperazioneInCoda( key , function(){ 
                    self.focusSuPuntoCorrente(); 
                    self._layerCorrente.mostra(key);
                } ) );

                self._geoInCallbackService.puntoAggiunto( key );
                self._layerCorrente.aggiungiPunto(key);
                self._idPunto = key;
            });

            return;
        }
    },

    elementMoved: function (layer, key) { 
        console.log('elementMoved-> layer:' + layer + ', key:' + key); 

        var self = this;

        this._confirmDialog.confermaSpostamentoPuntoCorrente(function () {

                self._operazioniInCoda.push( new OperazioneInCoda( key , function(){ 
                    self.focusSuPuntoCorrente(); 
                    self._layerCorrente.mostra(key);
                } ) );

            self._layerCorrente.spostaPunto(key);
        });
    },

    elementDeleted: function (layer, key) {
        console.log('elementDeleted-> layer:' + layer + ', key:' + key);

        var self = this;

        if (this._idPunto !== key) {
            console.log('L\'evento di eliminazione del punto è stato ignorato perchè il punto selezionato non è il punto corrente');
            return;
        }

        this._confirmDialog.confermaEliminazionePunto(function () {

            self._operazioniInCoda.push( new OperazioneInCoda( key , function(){ 
                self._layerCorrente.aggiorna();
            } ) );

            self._geoInCallbackService.puntoEliminato( key );
            self._layerCorrente.eliminaPunto(key);
            self._idPunto = null;
        });
    },
    /*
    mostraElementi: function (panel_key, layer_key, renderer_key, ids) {
        this._gis_plugin.overlay_elements(panel_key, layer_key, renderer_key, ids);
        this._gis_plugin.overlay_refresh(panel_key);
    },

    nascondiElementi: function (panel_key) {
        this._gis_plugin.overlay_delete(panel_key);
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
                nascondiElementi(panel_key);
                //self._gis_plugin.overlay_refresh(panel_key);

                return;
            }

            if (mostraAttive) {
                self._serviceProxy.getCodiciCiviciAttivita("attive", function (data) {
                    if (data.length > 0) {
                        var idsAttive = self.listaIdToString(data, ";");

                        console.log("invocazione di overlay_elements(panel_key:'" + panel_key + "', layer_key:'" + layer_key + "', renderer_key_attive:'" + renderer_key_attive + "', idsAttive:'" + idsAttive + "')");

                        self.mostraElementi(panel_key, layer_key, renderer_key_attive, idsAttive);
                    }
                    else {
                        alert("Non ci sono attivita' attive da visualizzare!");
                    }
                });
            }

            if (mostraCessate) {
                self._serviceProxy.getCodiciCiviciAttivita("cessate", function (data) {
                    if (data.length > 0) {
                        var idsCessate = self.listaIdToString(data, ";");

                        selfmostraElementi(panel_key, layer_key, renderer_key_cessate, idsCessate);
                    }
                    else {
                        alert("Non ci sono attivita' cessate da visualizzare!");
                    }
                });
            }
        });
    },
    */
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

/*

function VisibilityTracker( plugin, checkAttive , checkCessate , softwareCheckboxes ) {

	this._checkAttive = checkAttive;
	this._checkCessate = checkCessate;
	this._softwareCheckboxes = softwareCheckboxes;
	this._plugin = plugin;

	
	this.inizializza = _inizializza;
	this.toggleVisibility = _toggleVisibility;

	this.inizializza();



	// inizializza l'oggetto VisibilityTracker
	function _inizializza() {

		var self = this;

		this._softwareCheckboxes.click(function () {
			var chk = $(this);

			self.effettuaToggle(chk);
		});

		this._checkAttive.click(function () {
			self._softwareCheckboxes.each(function () {
				self.toggleVisibility($(this));
			});
		});

		this._checkCessate.click(function () {
			self._softwareCheckboxes.each(function () {
				self.toggleVisibility($(this));
			});
		});
	};



	function _toggleVisibility(checkBox) {
		var mostraLivello	= checkBox.find("input[type='checkbox']").is(':checked');
		var software		= checkBox.attr('software');
		var mostraAttive = this._checkAttive.find("input[type='checkbox']").is(':checked');
		var mostraCessate = this._checkCessate.find("input[type='checkbox']").is(':checked');

		if (mostraLivello && !mostraAttive && !mostraCessate) {
			alert("Selezionare se si intende visualizzare le attivita' attive e/o cessate!");

			checkBox.find("input[type='checkbox']")[0].checked = false;

			return;
		}

		this._plugin.toggleLayerVisibility(mostraLivello, software, mostraAttive, mostraCessate);
	}
}

*/

/*
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
*/
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

/*
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
*/



/*
function getElement(res, index, length) {
    result = res.split(",");
    return result[index].substring(length);
}
*/

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




