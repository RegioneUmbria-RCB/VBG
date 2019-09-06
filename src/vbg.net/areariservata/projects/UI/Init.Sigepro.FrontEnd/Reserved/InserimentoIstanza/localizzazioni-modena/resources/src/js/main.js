	/**
	 * Metodo principale contenente la logica del programma
	 */

	//inizializzo le variabili che verranno usate per gestire gli elementi DOM
	var html_selectedFoglioTitolo, html_selectedParticelleHtmlList, html_fogli_DropDownList,
	html_mappali_DropDownList, html_mappali_DropDownList_empty,
	html_selectionBlockDiv, navigation, layersProgetto,
	html_buttonReset, html_buttonConfirm , map	=	undefined;
	 
	
	/**
	 * Funzione di inizializzazione applicazione
	 */
	function run()
	{

		/**
		 * oggetto contenente:
		 * - coordinata mappa punto cliccato
		 * - numero foglio selezionato
		 * - array particelle selezionate
		 */
		navigation =  
		{
			intersectionPoint     : undefined,
			seLectedFoglio        : undefined,
			selectedParticelle    : []
		};

		/**
		 * CARICO GLI ELEMENTI FORM
		 */

		//blocco container di particelle selezionate
		html_selectionBlockDiv			=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.selection_block_div);

		//titolo HTML "selezionato il foglio numero 5"
		html_selectedFoglioTitolo		=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.selected_foglio_div);

		//lista HTML delle particelle selezionate
		html_selectedParticelleHtmlList	=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.selected_particelle_div);

		//elemento <select> con tutti i fogli selezionabili
		html_fogli_DropDownList			=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.fogli_input_select);

		//elemento <select> con tutti i mappali (relativi al foglio selezionato) selezionabili
		html_mappali_DropDownList		=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.mappali_input_select);

		//elemento <select> vuoto da mostrare in fase di aggiornamento del contenuto della <select> particelle
		html_mappali_DropDownList_empty	=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.mappali_input_select_alt_txt);

		//gestisco evento click su bottone 'CONFERMA SELEZIONE'
		html_buttonConfirm				=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.button_confirm_selection);

		//gestisco evento click su bottone 'ANNULLA SELEZIONE'
		html_buttonReset				=	wrappers.object.getObjectFromDOMbySelector(config_ol.html_page_elments.button_reset_selection);
		
		//inizializzo oggetto contenente tutti i layers usati nel programma
		layersProgetto = createMapLayers();


		//INIZIALIZZAZIONE MAPPA
		//@todo verificare se serve overlay per eventuali popup informativi
		//mapParams['overlays'] = overlays;

		var mapParams =
		{
			controls: ol.control.defaults().extend([
				new ol.control.FullScreen({
				source: 'fullscreen'
				})
			]),
			layers: new ol.layer.Group(
				{
					title: 'Livelli di Base',
					layers: [
							layersProgetto.tms,
							layersProgetto.layer_carto_base,
							layersProgetto.layer_perimetro_comunale
							]
				}),
			target: config_ol.html_page_elments.map_div,
			view: new ol.View
				({
					projection: config_ol.projection,
					center: config_ol.territory_center_point,
					zoom: config_ol.initial_zoom,
					minZoom: config_ol.min_zoom,
					maxZoom: config_ol.max_zoom,
					extent: config_ol.tms_extent
				})
		};

		map = new ol.Map(mapParams);

		//aggiungo i layer del catasto e il layer vettoriale
		map.addLayer(layersProgetto.layer_catasto_fogli);
		map.addLayer(layersProgetto.layer_catasto_particelle);
		map.addLayer(layersProgetto.layer_vett_foglio);
		map.addLayer(layersProgetto.layer_vett_particelle);

		//attivo gestione evento click su mappa
		map.on('click', onMapClick);

		
		//INIZIALIZZO TENDINE DI RICERCA FOGLI CATASTALI
		//IL METODO DI ATTERRAGGIO SI OCCUPA DI INIZIALIZZARE 
		//LA MAPPA CON EVENTUALI DATI DA PRE-CARICARE
		catastoUtils.loadAllFogli(initializeFormAndMap);


	}
