/**
 * Questo file contiene delle funzioni di utilita per eseguire chiamate WFS/WMS a GEOSERVER
 */

var gisUtils =
{

	filter_type_cql			:	'cql',
	//parametri non utilizzati al momento
	/*
	selection_origin		:	
			{
				selby_map_click				:	"SEL_ORIGIN_MAP_CLICK",
				selby_map_area_selection	:	"SEL_ORIGIN_MAP_AREA_SELECTION",
				selby_form_selection		:	"SEL_ORIGIN_FORM"

			},
			*/
	/**
	 * 
	 * @param {object} options 
	 * @param {function} successMethod 
	 * @param {function} errorMethod 
	 * @param {boolean} xmlMode 
	 */
	getWFSFeatures:
		function(url_geoserver, options, successMethod, errorMethod, xmlMode)
		{
			if (xmlMode)
			{
				//=====================================================
				//VERSIONE CHE EFFETTUA LA RICHIESTA POST XML
				//IN QUESTA MODALITA, ANCHE NODEJS, SEMBRANO NON FUNZIONARE 
				//LE RICHIESTE LIKE CON SPAZI NEL VALORE
				//=====================================================
				console.log("ATTENZIONE FUNZIONE SPERIMENTALE!!!! AL MOMENTO FUNZIONA SOLO CON NODEJS");
				console.log("SENZA NODEJS LA RICHIESTA PARTE MA IL SISTEMA NON SEMBRA IN GRADO DI RECUPERARE IL JSON DI RISPOSTA");

				var featureRequest = new ol.format.WFS().writeGetFeature(options);

				fetch(url_geoserver + '/wfs', {
					method: 'POST',
					credentials: 'same-origin',
					redirect: 'follow',
					agent: null,
					body: new XMLSerializer().serializeToString(featureRequest)

					//,headers: {
					//	"Content-Type": "text/plain",
					//	'Authorization': 'Basic YWRtaW46ZnJhbWFnaWEz',
					//} 
					,
					//timeout: 5000
				}).then( function(response) {
					return response.json();
				}).then( function(json) {
					return successMethod(json);
				});
			}
			else
			{
				//=====================================================
				//VERSIONE CHE EFFETTUA LA RICHIESTA GET URLENCODED
				//IN QUESTA MODALITA RIESCO A PASSARE I PARAMETRI URLENCODED
				//IN MODO DA FARE UNA RICHIESTA LIKE CON SPAZI SENZA PROBLEMI
				//=====================================================

				var searchParams = Object.keys(options).map( function(key) {
					return key + '=' + options[key];
				}).join('&');

				var url = url_geoserver + '/wfs?' + searchParams;

				fetch(url, {
					method: 'GET',
					headers: {
						// 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
					}
				}).then( function(response) {
					return response.json();
				}).then( function(json) {
					return successMethod(json);
				}).catch( function(error) {
					return errorMethod(error);
				});
			}
		},

	/**
	 * Data una risposta JSON di Geoserver, ritorna il primo elemento della lista
	 * @param {object} jsonResponse 
	 */
	getFirstFeatureFromJsonResponse:
		function(jsonResponse)
		{
			var firstFeature = undefined;
			var features = new ol.format.GeoJSON().readFeatures(jsonResponse);
			if (features != undefined && features.length > 0 && features[0] != undefined) 
			{
				firstFeature = features[0];
			}
			
			return firstFeature;
		},

	/**
	 * 
	 * @param {Boolean} xmlMode 
	 * @param {String} geometryFieldName 
	 * @param {Number} pointX 
	 * @param {Number} pointY 
	 * @param {String} srs_code 
	 */
	prepareIntersectWithPointFilter:
		function(xmlMode, geometryFieldName, pointX, pointY, srs_code)
		{
			var filter = undefined;
			if (xmlMode) 
			{
				filter = new ol.format.filter.Intersects(geometryFieldName, new ol.geom.Point([pointX, pointY]), srs_code);
			}
			else 
			{
				filter =  "INTERSECTS("+geometryFieldName+" ,POINT("+pointX+" "+pointY+"))";
			}
			return filter;
		},

	/**
	 * 
	 * @param {boolean} xmlMode 
	 * @param {string} fieldName 
	 * @param {string | number} value
	 * @param {boolean} matchCase - case sensitive search?
	 */
	prepareEqualToFilter:
		function(xmlMode, fieldName, value, matchCase)
		{
			if(matchCase == undefined)
			{
				matchCase = false;
			}

			var filter = undefined;
			if (xmlMode) 
			{
				filter = new ol.format.filter.equalTo(fieldName, value, matchCase);
			}
			else 
			{
				if(matchCase)
				{
					filter = fieldName+" = '"+value+"'";
				}
				else
				{
					filter = fieldName+" ILIKE '"+value+"'";
				}
			}

			return filter;
		},
		
	/**
	 * 
	 * @param {boolean} xmlMode 
	 * @param {string} fieldName 
	 * @param {string} value 
	 * @param {boolean} matchCase - case sensitive search?
	 */
	prepareLikeFilter:
		function(xmlMode, fieldName, value, matchCase)
		{
			if(matchCase == undefined)
			{
				matchCase = false;
			}

			var filter = undefined;
			if (xmlMode) 
			{
				filter = new ol.format.filter.like(fieldName, value, "*", ".", "!", matchCase);
			}
			else 
			{
				if(matchCase)
				{
					filter = fieldName+" LIKE '"+value+"'";
				}
				else
				{
					filter = fieldName+" ILIKE '"+value+"'";
				}
			}

			return filter;
		},

	/**
	 * 
	 * @param {boolean} xmlMode 
	 * @param {string} fieldName 
	 * @param {array} values 
	 */
	prepareInFilter:
		function(xmlMode, fieldName, values)
		{
			var filter = undefined;

			if(xmlMode)
			{
				console.log("metoodo da implentare");
			}
			else
			{
				filter = fieldName+" IN ('"+values.join("', '")+"')";
			}

			return filter;
		},
		

	/**
	 * 
	 * funzione che ritorna un Json con attributi e rispettivi valori da usare nella richiesta WFS 
	 * 
	 * @param {array|string} layerNames 
	 * @param {string} workspaceName 
	 * @param {string} workspaceURI 
	 * @param {array|string} layerProperties 
	 * @param {string} srs_code 
	 * @param {boolean} xmlMode 
	 * @param {object|string} filter 
	 * @param {string} featureID 
	 * @param {string} sortBy 
	 * @param {boolean} sortAscending 
	 */
	prepareWFSRequestParams:
		function(layerName, workspaceName, workspaceURI, layerProperties, srs_code, xmlMode, filter, filterType, featureID, sortBy, sortAscending) {
			//=====================================			
			//WORKSPACE NAME
			//=====================================
			if (commonUtils.string.isEmptyOrNull(workspaceName)) {
				console.log("Attenzione, obbligatorio indicare il nome del workspace da interrogare");
				return undefined;
			}
			workspaceName = commonUtils.string.trim(workspaceName);

			//=====================================
			//WORKSPACE URI
			//=====================================
			if (commonUtils.string.isEmptyOrNull(workspaceURI)) {
				console.log("Attenzione, obbligatorio indicare la URI del workspace da interrogare");
				return undefined;
			}
			workspaceURI = commonUtils.string.trim(workspaceURI);

			//=====================================
			//SRS CODE
			//=====================================
			if (commonUtils.string.isEmptyOrNull(srs_code)) {
				console.log("Attenzione, obbligatorio indicare il sistema di coordinate da utilizzare");
				return undefined;
			}
			srs_code = commonUtils.string.trim(srs_code);

			//=====================================
			//definisco oggetto base per il return
			//=====================================

			var wfsParamsObj = undefined;
			if (xmlMode) {
				//qui eseguo la richiesta con WFS 1.1.0
				wfsParamsObj =
					{
						featureNS: workspaceURI,
						featurePrefix: workspaceName,
						srsName: srs_code,
						//valore fisso, chiedo sempre JSON al momento
						outputFormat: 'application/json'
					};
			}
			else {
				//qui eseguo la richiesta con WFS 2.0.0
				wfsParamsObj =
					{
						service: 'WFS',
						version: '2.0.0',
						request: 'GetFeature',
						typenames: workspaceName + ":" + layerName,
						outputFormat: encodeURIComponent('application/json'),
						srsName: srs_code
					};
			}

			//=====================================
			// filter
			//=====================================

			if (!commonUtils.common.isUndefinedVar(filter)) {
				//se non sto preparando oggetto per ol.format.WFS().writeGetFeature applico encodeuri
				//MA VISTO SEMBRA SI CREINO PROBLEMI CON ENCODEURI SE IL FILTRO CONTIENE SPAZI.
				//SOSTITUISCO GLI SPAZI COL SIMBOLO +
				if (!xmlMode) {
					filter = filter.replace(/ /g, "+");
				}

				if (!commonUtils.common.isUndefinedVar(filterType) && filterType == 'cql') {
					wfsParamsObj['cql_filter'] = filter;
				}
				else {
					wfsParamsObj['filter'] = filter;
				}

			}

			//============================================
			//NOMI ATTRIBUTI CHE VOGLIO FARMI RESTITUIRE
			//============================================

			var attributesRequested = undefined;
			if (commonUtils.common.isArray(layerProperties) && layerProperties.length > 0) {
				attributesRequested = layerProperties;
			}
			else if (commonUtils.common.isString(layerProperties) && !commonUtils.string.isEmptyOrNull(layerProperties)) {
				layerProperties = layerProperties.trim();
				attributesRequested = [layerProperties];
			}

			if (!commonUtils.common.isUndefinedVar(attributesRequested) && attributesRequested.length > 0) {
				if (xmlMode) 
				{
					//per oggetto ol.format.WFS().writeGetFeature devo passare un oggetto
					wfsParamsObj['propertyNames'] = attributesRequested;
				}
				else 
				{
					//per richiesta con parametri get devo passare una stringa di parametri concatenati con virgola
					wfsParamsObj['propertyName'] = attributesRequested.join(',');
				}
			}

			//=====================================
			//ATTRIBUTI DA TESTARE
			//=====================================

			//SORT BY (ATTRIBUTE)
			if (!commonUtils.string.isEmptyOrNull(sortBy)) {
				wfsParamsObj['sortBy'] = sortBy;

				//ASCENDING OR DESCENDING?
				if (commonUtils.common.isUndefinedVar(sortAscending)) {
					sortAscending = true;
				}
				if (sortAscending) {
					wfsParamsObj['sortBy'] = wfsParamsObj['sortBy'] + "+A";
				}
				else {
					wfsParamsObj['sortBy'] = wfsParamsObj['sortBy'] + "+D";
				}

			}

			//FEATURE ID
			if (!commonUtils.string.isEmptyOrNull(featureID)) {
				wfsParamsObj['featureID'] = commonUtils.string.trim(featureID);
			}


			return wfsParamsObj;
		},

	/**
	* Ritorna un oggetto Tile o Image a seconda dei parametri passati.
	* al momento gestisce solo due tipi di gruppi di layer 'base' e 'overlay'
	* vedere se utile parametrizzare questo parametro.
	* DOPO OCCORRE CAMBIARE ANCHE IL LAYER SWITCHER
	*
	* ELENCO ATTRIBUTI:
	* sourceLayers -	OBBLIGATORIO | DEFAULT null errore
	* lista separata da virgola di layers WMS
	*
	* workspace	-	OBBLIGATORIO SE LAYERS CONFIGURATI CON WORKSPACE IN GEOSERVER
	* eventuale workspace a cui appartengono i layer in geoserver
	*
	* serviceType	-	FACOLTATIVO | DEFAULT wms
	* a scelta tra:
	* wms		-> servizio wms (immagini generate in tempo reale)
	* wms-c	-> cached wms  (versione cached - se esiste piu performante)
	* (wms-c funziona solo se creata la mappa cached in geowebcache)
	*
	* title		-	FACOLTATIVO | DEFAULT (Base)Layer nr. arrayIndex+1
	* titolo che compare nel layer switcher
	*
	* visible		-	FACOLTATIVO | DEFAULT true
	* indica se il layer e' acceso o spento in fase di inizializzazione della mappa
	*
	* tiled		-	FACOLTATIVO | DEFAULT false
	* indica se il layer viene richiesto come singola immagine o tassellata.
	* singola immagine piu' performante ma su connessioni lente da effetto
	* "latenza con pagina bianca".
	* un altro svantaggio di richiedere una immagine tiled, e' che se la feature ha una label,
	* la label viene ristampata su ogni tassello che compone la feature (es su un poligono grande,
	* composto da 5 tasselli, il nome del poligono viene scritto 5 volte)
	*
	* opacity		-	FACOLTATIVO | DEFAULT 1
	* indica la percentuale di trasparenza (0 completamente trasparente, 1 non trasparente) DEFAULT 1
	*
	* format		-	FACOLTATIVO | DEFAULT 'image/png'
	* a scelta tra:
	* image/jpeg	(immagine piu leggera ma senza trasparenza)
	* image/png	(immagine piu pesante ma con trasparenza)
	*
	* filter		-	FACOLTATIVO
	* filtro cql da applicare al layer WMS
	*
	* ====================================================================
	* NB: SE AGGIUNGO CAMPI CUSTOM IN ARRAY PARAMETRI,
	* QUESTO CAMPO DEVE ESSERE ANCHE GESTITO NEL METODO prepareMapObject
	* ====================================================================
	*
	*
	* @param currentLayer
	* @param isBaseLayerGroup
	* @returns ol.layer.Layer
	*/
	getWMSLayerFromParams:
		function(params, isBaseLayerGroup, geoserver_url, extent, projection, isGeoserver) {

			var myWMSLayer = null;

			//se sourceLayers non definito impossibile creare il corrente layer
			if (typeof params.sourceLayers === 'undefined' || params.sourceLayers == null || params.sourceLayers.trim().length < 1) {
				console.log('Attenzione, campo sourceLayer non definito. impossibile creare questo layer');
			}
			else {
				//====================================================================================
				//controllo se parametri facoltativi inseriti, altrimenti imposto parametri di default
				//====================================================================================

				//parametro non esistente in openlayers. rimosso
				//controllo se impostato il title, altrimenti lo imposto a: (Base)Layer nr. index+1
				/*
			  if(typeof params.title === 'undefined' || params.title == null || params.title.trim().length < 1)
			  {
				params.title = 'LAYER SENZA TITOLO';
			  }
			  */
				//controllo se impostato flag visible, altrimenti lo imposto a: true
				if (typeof params.visible === 'undefined' || params.visible == null) {
					params.visible = true;
				}
				//controllo se impostato flag tiled, altrimenti lo imposto a: false
				if (typeof params.tiled === 'undefined' || params.tiled == null) {
					params.tiled = false;
				}
				//controllo se impostato livello opacity, altrimenti lo imposto a: 1
				if (typeof params.opacity === 'undefined' || params.opacity == null || params.opacity < 0) {
					params.opacity = 1;
				}
				//controllo se impostato formato output, altrimenti lo imposto a: image/png
				if (typeof params.format === 'undefined' || params.format == null || params.format.trim().length < 1) {
					params.format = 'image/png';
				}
				//controllo se impostato il nome dello stile per il layer, altrimenti metto vuoto
				if (typeof params.style === 'undefined' || params.style == null || params.style.trim().length < 1) {
					params.style = '';
				}


				//====================================================================================
				//preparazione della URL per richiamare il servizio WMS o GWC
				//====================================================================================

				var service_url = geoserver_url;
				//aggiungo eventuale workspace
				if (!(typeof params.workspace === 'undefined' || params.workspace == null || params.workspace.trim().length < 1)) {
					service_url += '/' + params.workspace;
				}
				//imposto il tipo di servizio (wms o wms-c)
				if (typeof params.serviceType === 'undefined' || params.serviceType == null || params.serviceType.trim() == 0 ||
					params.serviceType.trim().toLowerCase() == 'wms') {
					service_url += '/wms';
				}
				else if (params.serviceType.trim().toLowerCase() == 'wms-c') {
					service_url += '/gwc/service/wms';
				}

				//====================================================================================
				//creo un layer tiled o single image a seconda del parametro passato nella richiesta
				//====================================================================================

				var sourceParams =
				{
					LAYERS: params.sourceLayers,
					FORMAT: params.format,
					STYLES: params.style
				};

				if (!(typeof params.filter === 'undefined' || params.filter == null || params.filter.trim().length < 1))
					sourceParams['CQL_FILTER'] = params.filter;

				var sourceAttributes =
				{
					//PARAMETRO DI OTTIMIZZAZIONE, VEDI COME IMPOSTARLO QUI
					//https://developer.mozilla.org/en-US/docs/Web/HTML/CORS_enabled_image
					crossOrigin: null,
					params: sourceParams,

					//SPERIMENTALE - FORSE SI PUO TOGLIERE
					'projection': projection,
					url: service_url
				};

				if (isGeoserver) {
					sourceAttributes['serverType'] = 'geoserver';
				}


				var layerParams =
				{
					opacity: params.opacity,
					//title:		params.title,
					extent: extent,
					visible: params.visible
				};

				//questi sono tutti parametri che non esistono nativamente in openlayers
				/*
				if(isBaseLayerGroup)
				{
				  layerParams['type'] = 'base';
				}
				else
				{
				  layerParams['type'] = 'overlay';
		  
				  //PARAMETRI AGGIUNTO DA ME
				  if(!(typeof params.pageTitle === 'undefined' || params.pageTitle == null || params.pageTitle.trim().length < 1))
					layerParams['pageTitle'] = params.pageTitle.trim();
				  if(!(typeof params.layerId === 'undefined' || params.layerId == null || params.layerId.trim().length < 1))
					layerParams['layerId'] = params.layerId.trim();
				}
				*/

				//tentativo di applicare uno sfondo alla mappa
				//layerParams['bgcolor'] = '0x000000';

				if (params.tiled == true) {
					layerParams['source'] = new ol.source.TileWMS(sourceAttributes);
					myWMSLayer = new ol.layer.Tile(layerParams);
				}
				else {
					layerParams['source'] = new ol.source.ImageWMS(sourceAttributes);
					myWMSLayer = new ol.layer.Image(layerParams);
				}

			}

			return myWMSLayer;

		},


	/**
	 * Dato un array di parametri per creare dei Layers WMS, ritorna un layerGroup contenente i layers richiesti
	 *
	 * @param modenaBaseLayersParams
	 * @returns {ol.layer.Group}
	 */
	getWMSLayersGroup: 
		function(layersParams, isBaseLayerGroup, groupTitle, geoserver_url, extent, projection, isGeoserver) 
		{
			var layers = [];

			if (typeof layersParams === 'undefined' || layersParams == null || layersParams.length < 1) {
				log('Attenzione, in getWMSLayersGroup layersParams obbligatorio. impossibile generare il layergroup');
				return null;
			}

			//imposto titolo del gruppo di default se non specificato
			if (typeof groupTitle === 'undefined' || groupTitle == null || groupTitle.trim().length < 1) {
				if (isBaseLayerGroup)
					groupTitle = 'Cartografia di Sfondo';
				else
					groupTitle = 'Livelli di sovrapposizione';
			}

			for (var li = 0; li < layersParams.length; li++) {
				layers[li] = getWMSLayerFromParams(layersParams[li], isBaseLayerGroup, geoserver_url, extent, projection, isGeoserver);
			}

			if (layers.length > 0) {
				return new ol.layer.Group({
					title: groupTitle,
					layers: layers
				});
			}
			else 
			{
				return null;
			}

		},

		/**
		 * Ordina una lista di features in base al valore di un attributo
		 * Ritorna la lista di features ordinata
		 * @param {array} features 
		 * @param {string} attribute 
		 */
		sortFeaturesByAttribute:
			function(features, attributeKey)
			{
				return features.sort(
					function(firstFeature, nextFeature)
					{
						if(firstFeature != undefined && nextFeature == undefined)
						{
							return 1;
						}
						else if(firstFeature == undefined && nextFeature != undefined)
						{
							return -1;
						}
						else
						{
							var firstAttributeValue  =	firstFeature.getProperties()[attributeKey];
							var nextAttributeValue   =	nextFeature.getProperties()[attributeKey];
							
							return commonUtils.common.compareIntegersAndLetters(firstAttributeValue, nextAttributeValue);
						}
					}
				);

			}

		/**
		 * Sulla base dei parametri messi nel file g4c.properties del programma,
		 * ritorna l'url diretto o il proxy per fare la richiesta a geoserver
		 */

		/*
		getWFSRequestURL	:
				   function(layerName, layerProperties, filter, featureID, srs_code, sortBy, sortAscending)
				   {
					   var wfsURL = null;
					   if(commonUtils.string.isEmptyString(config_ol.wfs_proxy_enabled) || config_ol.wfs_proxy_enabled === 'false')
					   {
						   var paramsURL = wfsFunctions.getWFSParamsRequestWithFilter(layerName, layerProperties, filter, featureID, srs_code, false, sortBy, sortAscending);
						   wfsURL = url_geoserver+"/"+paramsURL;
					   }
					   else
					   {
						   var paramsURL = wfsFunctions.getWFSParamsRequestWithFilter(layerName, layerProperties, filter, featureID, srs_code, true, sortBy, sortAscending);
						   //@todo verificare se devo impostare una app_path
						   //wfsURL = "/"+app_path+"/"+wfs_proxy_servlet_url+"?geoParams="+paramsURL;
						   wfsURL = "/"+config_ol.wfs_proxy_servlet_url+"?geoParams="+paramsURL;
					   }
					   return wfsURL;
				   },
	   */
};
