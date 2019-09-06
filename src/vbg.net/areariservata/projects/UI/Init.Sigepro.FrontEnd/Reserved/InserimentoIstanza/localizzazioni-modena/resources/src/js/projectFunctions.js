	//================================================
	//	FUNZIONI DI INIZIALIZZAZIONE
	//================================================

	/**
	 * INIZIALIZZO I LAYERS DEL PROGETTO CORRENTE
	 */
	function createMapLayers()
	{
		var layersPrj = {
			layer_perimetro_comunale 	:	undefined,
			layer_carto_base			:	undefined,
			tms							:	undefined,
			layer_group					:	undefined,
			layer_catasto_fogli			:	undefined,
			layer_catasto_particelle	:	undefined,
			layer_vett_foglio			:	undefined,
			layer_vett_particelle		:	undefined
		};

		// INIZIALIZZAZIONE LAYERS DI BASE

		layersPrj.layer_perimetro_comunale = gisUtils.getWMSLayerFromParams(
			{
				sourceLayers: config_ol.gs_default_workspace_name+':'+config_ol.baselayers_name.layer_name_perimetrocom,
				workspace: config_ol.gs_default_workspace_name,
				//serviceType		:	'wms-c',
				serviceType: 'wms',
				visible: true,
				//provare a vedere se tiled true va bene con WMS-C. in teoria sono gia generate le label e non dovrebbero esserci problemi
				tiled: false,
				opacity: 1,
				format: 'image/png'
			},
			true, config_ol.url_geoserver, config_ol.territory_extent, config_ol.coord_system_code, true);
		
		
		layersPrj.layer_carto_base = gisUtils.getWMSLayerFromParams(
			{
				//sembra che un layer group senza workspace dia problemi se usato come cached WMS
				sourceLayers: config_ol.gs_default_workspace_name+':'+config_ol.baselayers_name.layer_name_viecivici,
				workspace: config_ol.gs_default_workspace_name,
				//serviceType		:	'wms-c',
				serviceType: 'wms',
				visible: true,
				//provare a vedere se tiled true va bene con WMS-C. in teoria sono gia generate le label e non dovrebbero esserci problemi
				tiled: false,
				opacity: 0.7,
				format: 'image/png'
			},
			true, config_ol.url_geoserver, config_ol.territory_extent, config_ol.coord_system_code, true);
		
		layersPrj.tms = new ol.layer.Tile(
			{
				preload: 1,
				source:
					new ol.source.TileImage(
						{
							crossOrigin: null,
							extent: config_ol.tms_extent,
							projection: config_ol.projection,
							tileGrid:
								new ol.tilegrid.TileGrid(
									{
										extent: config_ol.tms_extent,
										origin: config_ol.tms_origin,
										resolutions: config_ol.tms_resolutions
									}),
							tileUrlFunction:
								function(coordinate) {
									if (coordinate === null) return undefined;
		
									// TMS Style URL
									var z = coordinate[0];
									var x = coordinate[1];
									var y = coordinate[2];
									var url = config_ol.tms_base_url + config_ol.tms_last_year_geo + '/' + z + '/' + x + '/' + y + '.' + config_ol.tms_image_extension;
									return url;
								}
						})
			});

		
		//INIZIALIZZAZIONE LAYERS CATASTALI

		layersPrj.layer_catasto_fogli = gisUtils.getWMSLayerFromParams(
			{
				sourceLayers: config_ol.gs_default_workspace_name+':'+config_ol.layer_foglio.layer_name,
				workspace: config_ol.gs_default_workspace_name,
				style: config_ol.layer_foglio.style,
				//serviceType		:	'wms-c',
				serviceType: 'wms',
				visible: true,
				//se si mette a tiled poi ripete in ogni tassello la label
				tiled: false,
				opacity: 1,
				format: 'image/png'
			},
			true, config_ol.url_geoserver, config_ol.territory_extent, config_ol.coord_system_code, true);

		
		layersPrj.layer_catasto_particelle = gisUtils.getWMSLayerFromParams(
			{
				sourceLayers: config_ol.gs_default_workspace_name+':'+config_ol.layer_particella.layer_name,
				workspace: config_ol.gs_default_workspace_name,
				style: config_ol.layer_particella.style,
				//serviceType:	'wms-c',
				serviceType: 'wms',
				visible: false,
				//se si mette a tiled poi ripete in ogni tassello la label
				tiled: false,
				opacity: 1,
				format: 'image/png'
			},
			true, config_ol.url_geoserver, config_ol.territory_extent, config_ol.coord_system_code, true);

			
		//===============================================
		//	INIZIALIZZAZIONE LAYER VETTORIALE
		//	USATO PER DISEGNARE I POLIGONI CREATI
		//	E IL PUNTO O LA VIA DI POSIZIONAMENTO
		//===============================================

		//=============================================
		//DEFINISCO LO STILE DI PUNTI LINEE E POLIGONI
		//=============================================

		//utile ma non usato in questo progetto quindi commentato
		/*
		var point_style =
		new ol.style.Style({
			
			image: new ol.style.Circle({
			
			//versione con nodejs
			//image: new CircleStyle({
			radius: 7,
			stroke: new ol.style.Stroke({color: 'black', width: 2}),
			fill: new ol.style.Fill({color: 'red'})
			})
			});
		*/

		//utile ma non usato in questo progetto quindi commentato
		/*
		var line_style =
		new ol.style.Style({
			fill: 	new ol.style.Fill({ color: 'rgba(247, 105, 54, 0.6)' }),
			stroke: new ol.style.Stroke({ color: 'rgba(247, 105, 54, 0.7)', width: 3 })
		});
		*/

		
		//INIZIALIZZO I LAYERS VETTORIALI PER EVIDENZIARE IL FOGLIO E LE PARTICELLE SELEZIONATI

		layersPrj.layer_vett_foglio = 
			new ol.layer.Vector({
				//versione con nodejs
				//layersPrj.layer_vett_foglio = new VectorLayer({
				source: new ol.source.Vector(),
				//versione con nodejs
				//source: new VectorSource();
				title: 'Foglio Selezionato',
				style: new ol.style.Style({
					fill: 	new ol.style.Fill({ color: 'rgba(153, 0, 0, 0.2)' }),
					stroke: new ol.style.Stroke({ color: '#990000', width: 3 })
				})
		});

		layersPrj.layer_vett_particelle = 
			new ol.layer.Vector({
				//versione con nodejs
				//layersPrj.layer_vett_particelle = new VectorLayer({
				source: new ol.source.Vector(),
				//versione con nodejs
				//source: new VectorSource();
				title: 'Particelle Selezionate',
				style: new ol.style.Style({
					fill: 	new ol.style.Fill({ color: 'rgba(35, 209, 99, 0.2)' }),
					stroke: new ol.style.Stroke({ color: '#7BEF51', width: 3 })
				})
		});

		return layersPrj;

	}


	/**
	 * Questo metodo popola la drop down dei fogli catastali (la jsonResponse contiene tutti i numeri di foglio presenti)
	 * inoltre verifica se un eventuale array di PARTKEY con cui inizializzare la mappa contiene valori validi
	 * e in caso affermativo avvia la ricerca delle particelle corrispondenti per inizializzare l'applicazione
	 * @param {object} jsonResponse - oggetto JSON di risposta inviato da Geoserver
	 */
	function initializeFormAndMap(jsonResponse)
	{
		resetHTMLForm(true);
		
		//inizializzo la tendina dei fogli catastali
		populateFogliDropDownList(jsonResponse);

		//prendo come riferimento il foglio della prima feature
		if(arrayParticelleInput != undefined && arrayParticelleInput.length > 0)
		{
			navigation.seLectedFoglio 		= 	catastoUtils.getNumeroFoglioFromPartkey(arrayParticelleInput[0]);

            if (navigation.seLectedFoglio != undefined) {
                //imposto subito il foglio selezionato, in questo modo non viene cambiato
                //durante esecuzione metodo displaySelectedFoglioOnMap
                //se accadesse si azionerebbe il trigger su onChange e avverrebbe il reset di 
                //navigation.selectedParticelle
                wrappers.dropdown.selectItemByValue(html_fogli_DropDownList, navigation.seLectedFoglio);
                catastoUtils.loadFoglioByNumero(navigation.seLectedFoglio, displaySelectedFoglioOnMap);

                //attivo il trigger su change di tendina solo dopo inizializzazione, altrimenti 
                //viene per sbaglio eseguito il reset di selectedParticelle

                wrappers.event.addClickEventListener(html_buttonConfirm, onConfirmSelection);
                wrappers.event.addClickEventListener(html_buttonReset, onResetApplication);

                wrappers.event.addChangeEventListener(html_fogli_DropDownList, onChangeFogliDropDown);
                wrappers.event.addChangeEventListener(html_mappali_DropDownList, onChangeParticelleDropDown);

                navigation.selectedParticelle = commonUtils.array.remove_duplicates(arrayParticelleInput);

                //nel caso in cui array navigation.selectedParticelle contenga elementi
                //verifico che tutte le particelle siano dello stesso foglio, in caso contrario non le seleziono
                //in caso affermativo le seleziono sulla mappa e le inserisco nella lista a lato
                if (isAllParticelleOfSameFoglio()) {
                    //metodo che evidenzia le feature 'particella' richieste
                    var displaySelectedParticelleOnMap =
                        function (jsonResponse) {
                            var features = new ol.format.GeoJSON().readFeatures(jsonResponse);
                            if (features != undefined && features.length > 0) {
                                //rimuovo tutte le particelle precedentemente selezionate
                                layersProgetto.layer_vett_particelle.getSource().clear();
                                layersProgetto.layer_vett_particelle.getSource().addFeatures(features);

                                //aggiungo le particelle anche alla lista HTML
                                //creo una opzione per ogni particella
                                for (var i = 0; i < features.length; i++) {
                                    var currentFeatureProperties = features[i].getProperties();
                                    wrappers.html_list.addItem(html_selectedParticelleHtmlList, createPartKeyHtmlListElement(currentFeatureProperties));
                                }

                                commonUtils.html_list.sortItemsByAttribute(html_selectedParticelleHtmlList, "li", config_ol.html_page_elments.form_list_element_partnumber);

                                wrappers.object.show(html_selectionBlockDiv);
                                wrappers.object.show(html_buttonConfirm);

                                //in questo caso faccio sempre zoom su particelle selezionate, in quanto sto inizializzando la mappa
                                map.getView().fit(layersProgetto.layer_vett_particelle.getSource().getExtent());
                            }
                        };

                    //tutti le particelle appartengono allo stesso foglio, posso caricarle in mappa e in lista selezionati
                    catastoUtils.loadSelectedParticelle(navigation.selectedParticelle, displaySelectedParticelleOnMap);
                }
            }
        } else {
            wrappers.event.addClickEventListener(html_buttonConfirm, onConfirmSelection);
            wrappers.event.addClickEventListener(html_buttonReset, onResetApplication);

            wrappers.event.addChangeEventListener(html_fogli_DropDownList, onChangeFogliDropDown);
            wrappers.event.addChangeEventListener(html_mappali_DropDownList, onChangeParticelleDropDown);
        }
	}


	/**
	 * selezione valore corretto di dropdown fogli e richiesta lista valori particelle
	 * per foglio selezionato
	 */
	function setFogliDropDownSelectedValue()
	{
		
		wrappers.dropdown.hide(html_mappali_DropDownList);
		wrappers.dropdown.show(html_mappali_DropDownList_empty);	
				
		catastoUtils.loadAllParticelleByFoglio(	navigation.seLectedFoglio, populateParticelleDropDownList);

		//======================================================================================
		//tento di risalire al foglio richiesto per impostare il valore selezionato in tendina
		//======================================================================================
		var selectedFoglioDropDownValue = wrappers.dropdown.getSelectedValue(html_fogli_DropDownList);
		if(selectedFoglioDropDownValue != navigation.seLectedFoglio)
		{
			wrappers.dropdown.selectItemByValue(html_fogli_DropDownList, navigation.seLectedFoglio);
		}
	}


	//=============================================
	//	FUNZIONI DI CONTROLLO
	//=============================================

	/**
	 * Verifico se tutte le particelle selezionate appartengono al foglio selezionato
	 */
	function isAllParticelleOfSameFoglio()
	{
		var foundDifferentFoglio = false;
		for(var i = 0; i < navigation.selectedParticelle.length; i++)
		{
			var currentFoglio = catastoUtils.getNumeroFoglioFromPartkey(navigation.selectedParticelle[i]);
			if(currentFoglio != navigation.seLectedFoglio) 
			{
				foundDifferentFoglio = true;
				break;
			}
		}

		if(!foundDifferentFoglio)
		{
			return true;
		}
		return false;
	}

	/**
	 * verifico se una feature particella appartiene al foglio selezionato
	 * @param {object} feature 
	 */
	function isParticellaInFoglioCorretto(featureProperties)
	{
		//recupero numero del foglio
		if (featureProperties[config_ol.layer_particella.field_name_foglio] == navigation.seLectedFoglio) 
		{
			return true;
		}
		return false;
    }



	//=============================================
	//	FUNZIONI DI RESET
	//=============================================


	/**
	 * Metodo che fa reset di tendine e lista riepilogo
	 * permetto di ignorare reset di drop down lista fogli
	 * perche questo reset viene eseguito anche su evento change
	 * di lista fogli, e non voglio perdere la selezione appena 
	 * effettuata
	 */
	function resetHTMLForm(ignoreFogliDropDown)
	{
		//faccio reset di titolo HTML "selezionato foglio.."
		wrappers.object.empty(html_selectedFoglioTitolo);
		
		//faccio reset di lista HTML particelle selezionate (vuoto tendina)
		wrappers.object.empty(html_selectedParticelleHtmlList);
		
		//faccio reset di lista mappali (vuoto tendina)
		wrappers.object.empty(html_mappali_DropDownList);

		//nascondo la lista vera e carico lista sempre vuota
		wrappers.dropdown.hide(html_mappali_DropDownList);
		wrappers.dropdown.show(html_mappali_DropDownList_empty);

		wrappers.object.hide(html_selectionBlockDiv);


		if(!ignoreFogliDropDown)
		{
			wrappers.dropdown.selectFirstItem(html_fogli_DropDownList);
		}
	}

  
    /**
     * vuoto le variabili di navigazione
     */
	function resetNavigation()
	{
		navigation.intersectionPoint	=	undefined;
		navigation.seLectedFoglio		=	undefined;
		navigation.selectedParticelle	=	[];
    }
    
    /**
	 * vuota i layer vettoriali e non carica nessuna particella dal layer WMS
	 */
	function resetMap()
	{
		//VUOTO ARRAY VETTORIALI
		layersProgetto.layer_vett_foglio.getSource().clear();
		layersProgetto.layer_vett_particelle.getSource().clear();
		
		//richiamo le particelle con una condizione sempre falsa, cosi il layer diventa vuoto
		var parameters = layersProgetto.layer_catasto_particelle.getSource().getParams();
		parameters['CQL_FILTER'] = "1=2";
		layersProgetto.layer_catasto_particelle.getSource().updateParams(parameters);

		//FACCIO ZOOM ALLA ESTENSIONE MASSIMA DELLA MAPPA
		map.getView().fit(config_ol.tms_extent);
	}


	//======================================================
	//	FUNZIONI DI GESTIONE EVENTI
	//======================================================

	/**
	 * GESTIONE EVENTO CLICK SU MAPPA
	 * @param {event} evt 
	 */

	function onMapClick(evt) 
	{
		navigation.intersectionPoint = evt.coordinate;

		//se ancora non ho scelto il foglio, al click seleziono il foglio
		if (navigation.seLectedFoglio === undefined) 
		{
			catastoUtils.loadFoglioByIntersectionPoint(
				navigation.intersectionPoint[0],
				navigation.intersectionPoint[1],
				displaySelectedFoglioOnMap);
		}
		else 
		{
			//il foglio è selezionato. ora su click seleziono la particella
			catastoUtils.loadParticellaByIntersectionPoint(
				navigation.intersectionPoint[0],
				navigation.intersectionPoint[1],
				addRemoveParticellaFromSelectionList
			);
		}
    }

	/**
	 * funzione eseguita su evento change della tendina dei fogli catastali
	 * @param {event} event 
	 */
	function onChangeFogliDropDown(event)
	{
		var foglioSelezionato = event.target.value;

		//FACCIO RESET DI PARAMETRI DI SELEZIONE
		resetNavigation();

		//PULISCO LA FORM HTML
		resetHTMLForm(true);

		//elimino ogni feature caricata nella mappa
		resetMap();

		catastoUtils.loadFoglioByNumero(foglioSelezionato, displaySelectedFoglioOnMap);
		
    }

	/**
	 * funzione eseguita su evento change della tendina delle particelle catastali.
	 * effettua una ricerca di feature particelle per PARKEY selezionato.
	 * In caso di successo, richiama un metodo che evidenzia le particelle in mappa
	 * @param {event} event 
	 */
	function onChangeParticelleDropDown(event)
	{
		var partkeySelezionato = event.target.value;
		//verificare se la particella gia presente in array particelle selezionate
		if(navigation.selectedParticelle.includes(partkeySelezionato))
		{
			//FINISCO IN QUESTA CONDIZIONE ANCHE OGNI VOLTA CHE SELEZIONO LA PARTICELLA
			//DA CLICK SU MAPPA, IN QUANTO PER CHIAREZZA IMPOSTO IL VALORE DELLA PARTICELLA ANCHE NELLA TENDINA
			//E QUESTO OVVIAMENTE SCATENA EVENTO CHANGE.
			//NON OCCORRE QUINDI SCRIVERE UN LOG A CONSOLE PER QUESTA CONDIZIONE
		}
		else
		{
			//verifico se partkey fa parte di foglio selezionato (dovrebbe essere sempre cosi, giusto uno scrupolo per errori imprevisti)
			var numFoglioDaPartKey      =   catastoUtils.getNumeroFoglioFromPartkey(partkeySelezionato);

			var numFoglioSelezionato	=	wrappers.dropdown.getSelectedValue(html_fogli_DropDownList);
			
			if(numFoglioDaPartKey == numFoglioSelezionato)
			{
				var addParticellaToSelection = function(jsonResponse)
					{
						var firstFeature = gisUtils.getFirstFeatureFromJsonResponse(jsonResponse);
						var featureProperties = firstFeature.getProperties();
						if(firstFeature != undefined && isParticellaInFoglioCorretto(featureProperties))
						{
							commonAddParticellaFeatureToVectorLayer(firstFeature, true);
						}
					};

				//ok la particella e' selezionabile. procedo ad aggiungerla
				catastoUtils.loadSelectedParticelle([partkeySelezionato], addParticellaToSelection);
			}
			else
			{
				console.log("errore imprevisto. la paricella richiesta ("+partkeySelezionato+") non fa parte del foglio selezionato ("+numFoglioSelezionato+").");
			}
		}
	}


	/**
	 * Metodo che dopo aver eseguito alcune verifiche sulle particelle selezionate,
	 * se ok, richiama la funzine di callback
	 */
	function onConfirmSelection()
	{	
		if(isAllParticelleOfSameFoglio())
		{
			//elimino eventuali voci duplicate
			navigation.selectedParticelle	=	commonUtils.array.remove_duplicates(navigation.selectedParticelle);

			//riordino array particelle selezionate
			navigation.selectedParticelle = navigation.selectedParticelle.sort(
				function(firstPartkey, nextPartkey)
				{
					if(firstPartkey != undefined && nextPartkey == undefined)
					{
						return 1;
					}
					else if(firstPartkey == undefined && nextPartkey != undefined)
					{
						return -1;
					}
					else
					{
						var firstMappale  =	catastoUtils.getValoreMappaleFromPartkey(firstPartkey);
						var nextMappale   =	catastoUtils.getValoreMappaleFromPartkey(nextPartkey);
						
						return commonUtils.common.compareIntegersAndLetters(firstMappale, nextMappale);
					}
				}
			);

			callBack(navigation.selectedParticelle);
		}
		else
		{
			console.log('Attenzione, alcune delle particelle selezionate non appartengono al foglio selezionato');
		}	
	}


	/**
	 * Riporta applicazione a stato privo di selezione
	 */
	function onResetApplication()
	{
		//FACCIO RESET DI PARAMETRI DI SELEZIONE
		resetNavigation();

		//PULISCO LA FORM HTML
		resetHTMLForm(false);

		//elimino ogni feature caricata nella mappa
		resetMap();
	}


	//===================================================
	//	FUNZIONI DI MANIPOLAZIONE DEL DOM
	//===================================================
	
	/**
	 * Crea un elemento LI nella lista HTML delle particelle selezionate
	 * questo metodo utilizza plain javascript, non jquery
	 * @param {object} featureProperties 
	 */
	function createPartKeyHtmlListElement(featureProperties)
	{
		var listElement 	= document.createElement("li");

		//classe usata da bootstrap per dare lo stile a box a elemento list
		//versione semplice
		//listElement.setAttribute("class", "list-group-item list-group-item-action");
		//versione per gestire un ulteriore contenuto
		listElement.setAttribute("class", "list-group-item d-flex justify-content-between align-items-center");

		//creo il contenitore del link
		var removePartkeylinkElementContainer = document.createElement("span");
		removePartkeylinkElementContainer.setAttribute("class", "badge badge-danger badge-pill");
		
		//creo link per eliminare particella da selezione
		var removePartkeylinkElement		= document.createElement("a");
		removePartkeylinkElement.setAttribute("href", "#");
		//aggiunge stile 'X'
		removePartkeylinkElement.setAttribute("class", "ol-closer");
		//aggiunge la funzione javascript di rimozione
		removePartkeylinkElement.setAttribute("onclick", "eliminaPartKeyDaSelezione('"+featureProperties[config_ol.layer_particella.field_name_partkey]+"')");

		removePartkeylinkElementContainer.appendChild(removePartkeylinkElement);

		//creo il contenitore del testo item
		var particellaValueContainerElement = document.createElement("span");
		//stile usato da versione plain-js
		particellaValueContainerElement.setAttribute("class", "particella_value_container");

		//questo serve a creare un elemento testo da includere tra due tag di apertura e chiusura
		//<li>bla bla</li> questo crea il 'bla bla'
		var itemListText	= document.createTextNode(featureProperties[config_ol.layer_particella.field_name_mappale]);
		particellaValueContainerElement.appendChild(itemListText);


		listElement.appendChild(particellaValueContainerElement);
		listElement.appendChild(removePartkeylinkElementContainer);
		
		listElement.setAttribute(config_ol.html_page_elments.form_list_element_partkey, featureProperties[config_ol.layer_particella.field_name_partkey]);
		listElement.setAttribute(config_ol.html_page_elments.form_list_element_partnumber, featureProperties[config_ol.layer_particella.field_name_mappale]);
		
		return listElement;
	}

	/**
	 * Imposto il titolo 'Foglio 50'
	 * @param {string} titolo 
	 */
	function setTitoloFoglioSelezionato(titolo)
	{
		//imposto il titolo della lista
		wrappers.object.setInnerHtmlValue(html_selectedFoglioTitolo, titolo);
	}


	/**
     * aggiunge una option per ogni foglio al campo input select per il filtro fogli nel form di ricerca.
     * Aggiunge anche un eventlistener per evento change.
     * che effettua una richiesta WFS delle particelle associate al foglio selezionato
     *
     * @param {object} jsonResponse - risposta in formato JSON inviata da Geoserver. contiene le features foglio corrispondenti ai filtri ricerca impostati.
     */
    function populateFogliDropDownList(jsonResponse)
	{
		var features = new ol.format.GeoJSON().readFeatures(jsonResponse);
		if (features != undefined && features.length > 0) 
		{
			//riordino le features
			features = gisUtils.sortFeaturesByAttribute(features, config_ol.layer_foglio.field_name_foglio);
			
			//elimino eventuali option inserite in precedenza
			wrappers.dropdown.empty(html_fogli_DropDownList);
			
			//creo opzione vuota, la indico come selezione di default e come valore attualmente selezionato
			var emptyOpt = new Option(" - fogli - ", "", true, true);
			wrappers.dropdown.addItem(html_fogli_DropDownList, emptyOpt, false);
			
			for (var i = 0; i < features.length; i++)
			{
				var currentFeatureProps =	features[i].getProperties();
				var opt_text			= currentFeatureProps[config_ol.layer_foglio.field_name_foglio];
				var opt_value			= currentFeatureProps[config_ol.layer_foglio.field_name_foglio];
								
				var currOpt = new Option(opt_value, opt_text, false, false);
				
				wrappers.dropdown.addItem(html_fogli_DropDownList, currOpt, false);
			}

			//richiamo metodo refresh alla fine del popolamento
			//in questo modo se si utilizza bootstrap-select nel progetto
			//la tendina viene aggiornata in modo corretto
			//se si usa plain-js il metodo non fa nulla
			wrappers.dropdown.refreshAfterUpdate(html_fogli_DropDownList);

			//forzo il primo valore di scelta
			//non dovrebbe servire, gia impostato quando lo ho creato
			//wrappers.dropdown.selectFirstItem(fogliInputSelect);
							
			//se necessario la tendina si puo totalmente creare dinamicamente
			//per semplicita mi collego ad una tendina gia esistente
			//https://developer.mozilla.org/en-US/docs/Web/API/HTMLSelectElement/add
		}
	}


	/**
    * Vuota il campo input select delle particelle e lo popola nuovamente con i dati inviati da Geoserver
    * 
    * @param {object} jsonResponse - risposta in formato JSON inviata da Geoserver. contiene le features particella corrispondenti ai filtri ricerca impostati.
    */
	function populateParticelleDropDownList(jsonResponse)
	{
		var features = new ol.format.GeoJSON().readFeatures(jsonResponse);
		if (features != undefined && features.length > 0) 
		{
			//riordino le features
			features = gisUtils.sortFeaturesByAttribute(features, config_ol.layer_particella.field_name_mappale);

			//elimino eventuali option inserite in precedenza
			wrappers.dropdown.empty(html_mappali_DropDownList);
			
			//creo opzione vuota, la indico come selezione di default e come valore attualmente selezionato
			var emptyOpt = new Option(" - mappali - ", "", true, true);
			wrappers.dropdown.addItem(html_mappali_DropDownList, emptyOpt, false);
			
			//creo una opzione per ogni mappale
			for (var i = 0; i < features.length; i++)
			{
				var currentFeatureProps =	features[i].getProperties();
				var opt_text			= currentFeatureProps[config_ol.layer_particella.field_name_partkey];
				var opt_value			= currentFeatureProps[config_ol.layer_particella.field_name_mappale];
								
				var currOpt = new Option(opt_value, opt_text, false, false);
				
				wrappers.dropdown.addItem(html_mappali_DropDownList, currOpt, false);
			}

			//richiamo metodo refresh alla fine del popolamento
			//in questo modo se si utilizza bootstrap-select nel progetto
			//la tendina viene aggiornata in modo corretto
			//se si usa plain-js il metodo non fa nulla
			wrappers.dropdown.refreshAfterUpdate(html_mappali_DropDownList);

			//forzo il primo valore di scelta
			//non dovrebbe servire, gia impostato quando lo ho creato
			//wrappers.dropdown.selectFirstItem(html_mappali_DropDownList);

			//se necessario la tendina si puo totalmente creare dinamicamente
			//per semplicita mi collego ad una tendina gia esistente
			//https://developer.mozilla.org/en-US/docs/Web/API/HTMLSelectElement/add

			wrappers.dropdown.show(html_mappali_DropDownList);
			wrappers.dropdown.hide(html_mappali_DropDownList_empty);
		}
	}



	//===================================================
	//	FUNZIONI DI MANIPOLAZIONE DELLA MAPPA OPENLAYERS
	//===================================================

    /**
	 * metodo che evidenzia le feature foglio richieste e 'accende' tutte le particelle contenute in tali feature
	 * 
	 * @param {object} jsonResponse  - risposta in formato JSON inviata da Geoserver. contiene le features particella corrispondenti ai filtri ricerca impostati.
	 */
	function displaySelectedFoglioOnMap(jsonResponse)
	{
		//potrebbero arrivarmi piu feature, il foglio potrebbe comprendere un frazionamento (allegato, sviluppo)
		var features = new ol.format.GeoJSON().readFeatures(jsonResponse);
		if (features != undefined && features[0] != undefined)
		{
			//imposto la prima feature trovata come numero foglio
			navigation.seLectedFoglio = features[0].getProperties()[config_ol.layer_foglio.field_name_foglio];

			//imposto il titolo della lista
			setTitoloFoglioSelezionato("Foglio "+navigation.seLectedFoglio);
			wrappers.object.show(html_selectionBlockDiv);
			
			//aggiungo tutte le features al layer vector dei fogli
			for (var i = 0; i < features.length; i++) 
			{
				var currFeatureProperties = features[i].getProperties();
				if (currFeatureProperties[config_ol.layer_foglio.field_name_foglio] == navigation.seLectedFoglio) {
					//stesso foglio di foglio principale, o è il cliccato o è il suo allegato o sviluppo
					//posso aggiungerlo
					layersProgetto.layer_vett_foglio.getSource().addFeature(features[i]);
				}
			}

			//faccio zoom mappa sul foglio selezionato
			if(config_ol.layer_foglio.fit_selected_area_when_change)
			{
				map.getView().fit(layersProgetto.layer_vett_foglio.getSource().getExtent());
			}

			//accendo nel layer WMS tutte le particelle del foglio selezionato (inclusi i frazionamenti)
			var parameters = layersProgetto.layer_catasto_particelle.getSource().getParams();
			parameters['CQL_FILTER'] = config_ol.layer_particella.field_name_foglio+"='"+navigation.seLectedFoglio+"'";
			layersProgetto.layer_catasto_particelle.getSource().updateParams(parameters);

			//rendo visibile il layer  particelle
			layersProgetto.layer_catasto_particelle.setVisible(true);


			//imposto il valore selezionato nella lista fogli e avvio la richiesta delle relative particelle
			setFogliDropDownSelectedValue();
		}
	}


    /**
	 * funzione eseguita a seguito di esito positivo della richiesta WFS
	 * di ricerca particella tramite coordinata (ottenuta da click su particelle in mappa).
	 * il sistema verifica se la selezione era gia presente.
	 * in caso affermativo rimuove la feature dalla selezione.
	 * in caso negativo aggiunge la feature alla selezione
	 * @param {object} jsonResponse 
	 */
	function addRemoveParticellaFromSelectionList(jsonResponse) 
	{
		firstFeature = gisUtils.getFirstFeatureFromJsonResponse(jsonResponse);
		if(firstFeature != undefined)
		{
			//devo verificare se questa particella fa parte del foglio scelto
			var featureProperties = firstFeature.getProperties();

			if(isParticellaInFoglioCorretto(featureProperties))
			{
				//verifico se particella gia PRESENTE
				var currentPartkeyValue	= featureProperties[config_ol.layer_particella.field_name_partkey];
				var particellaIndex		= navigation.selectedParticelle.indexOf(currentPartkeyValue);
				
				//verifico se particella gia PRESENTE
				if (particellaIndex > -1) 
				{
					//particella gia presente, la rimuovo da mappa e lista
					eliminaPartKeyDaSelezione(currentPartkeyValue);
				}
				else 
				{
					//particella non ancora presente, la aggiungo in mappa e lista
					commonAddParticellaFeatureToVectorLayer(firstFeature, false);
				}
			}
		}
    }


    /**
	 * metodo comune che si occupa di aggiungere la particella in mappa e in lista
	 * @param {object} feature 
	 * @param {boolean} fromDropDownSelection - indica se il metodo viene chiamato a seguito di evento change su dropdown particelle o a seguito di click su mappa
	 */
	function commonAddParticellaFeatureToVectorLayer(feature, fromDropDownSelection)
	{
		var featureProperties = feature.getProperties();
		var currentPartkeyValue = featureProperties[config_ol.layer_particella.field_name_partkey];

		//aggiungo partkey ad array partkey selezionati
		navigation.selectedParticelle.push(currentPartkeyValue);

		if(wrappers.object.isVisible(html_buttonConfirm))
		{
			wrappers.object.show(html_buttonConfirm);
		}

		//aggiungo particella a lista HTML particelle selezionate e la ordino
		wrappers.html_list.addItem(html_selectedParticelleHtmlList, createPartKeyHtmlListElement(featureProperties));
		commonUtils.html_list.sortItemsByAttribute(html_selectedParticelleHtmlList, "li", config_ol.html_page_elments.form_list_element_partnumber);

		//aggiungo la feature alla mappa
		layersProgetto.layer_vett_particelle.getSource().addFeature(feature);
		if(config_ol.layer_particella.fit_selected_area_when_change)
		{
			map.getView().fit(layersProgetto.layer_vett_particelle.getSource().getExtent());
		}

		//se il metodo viene richiamato da change su dropdown non occorre impostarne il valore
		if(!fromDropDownSelection)
		{
			var selectedParticellaDropDownValue = wrappers.dropdown.getSelectedValue(html_mappali_DropDownList);
			if(selectedParticellaDropDownValue != currentPartkeyValue)
			{
				wrappers.dropdown.selectItemByValue(html_mappali_DropDownList, currentPartkeyValue);
			}
		}

    }
	
    
    /**
	 * metodo che dato un partkey, verifica se presente in array selezionati.
	 * in caso affermativo lo elimina da mappa, da array selezionati, e da lista HTML
	 */
	function eliminaPartKeyDaSelezione(partkey)
	{
		//verifico se particella effettivamente selezionata
		var particellaIndex		= navigation.selectedParticelle.indexOf(partkey);

		if (particellaIndex > -1) 
		{
			//PARTKEY GIA PRESENTE, LO ELIMINO DA ARRAY e tolgo feature dal layer source

			var removableFeature = layersProgetto.layer_vett_particelle.getSource().forEachFeature(
				function(currentFeature) {
					if (currentFeature.getProperties()[config_ol.layer_particella.field_name_partkey] == partkey) 
					{
						return currentFeature;
					}
				});

			if (removableFeature != undefined) 
			{
				layersProgetto.layer_vett_particelle.getSource().removeFeature(removableFeature);
				//rimuovo particella da array particelle selezionate
				navigation.selectedParticelle.splice(particellaIndex, 1);


				//aggiorno lo zoom mappa in modo da vedere tutta la selezione
				//se questa era ultima particella di lista selezionate, faccio zoom a tutto il foglio
				//altrimenti mappa va in errore per tentativo di fare zoom su un extent vuoto
				if(config_ol.layer_particella.fit_selected_area_when_change)
				{
					if(navigation.selectedParticelle.length > 0)
					{
						map.getView().fit(layersProgetto.layer_vett_particelle.getSource().getExtent());
					}
					else
					{
						map.getView().fit(layersProgetto.layer_vett_foglio.getSource().getExtent());
					}
				}
			}

			//elimino il partkey dalla lista HTML dei selezionati
			wrappers.object.removeChildrenByTagAndDataAttribute(html_selectedParticelleHtmlList, "li", config_ol.html_page_elments.form_list_element_partkey, partkey);
			
		}
    }