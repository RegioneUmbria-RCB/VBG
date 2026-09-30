/**
 * Libreria di controlli per la compilazione automatica dei campi.
 * Basato su jQuery 1.8.3 e jQuery-ui 1.9.2
 */

$.initui = $.initui || {};
/**
 * Single Row Autocompiler
 * controllo generico per la compilazione automatica che prevede
 * l'apertura di una popup per l'immissione del criterio di ricerca.
 * I campi collegati vengono compilati solo se viene undividuato 
 * un solo record come risultato della ricerca, in caso contrario viene visualizzato un messaggio di errore
 */
(function( $ ) {
	$.widget("initui.singlerowautocompiler",{
		version: "1.0.0",
		fieldMatcherRegExp: /{field}/g,
		options: {
			fieldSelectorPattern: '[starid *= "\\[{field}\\]"]',
			/*
			stringa utilizzata per costruire i selettori jQuery che servono 
			per posizionare i valori restituiti nei campi di destinazione.
			{field} verrà di volta in volta sostituito con la stringa che 
			identifica il campo di destinazione così come definita nei mappings.
			Il valore di default prevede la selezione per id del tag html.
			*/
			height: "auto",//altezza del pannello di ricerca
			width: 300,// larghezza del pannello di ricerca
			maxHeight: false,//limite massimo di altezza a cui si può ingrandire il pannello di ricerca
			maxWidth: false,//limite massimo di larghezza a cui si può ingrandire il pannello di ricerca
			minHeight: 150,//limite minimo di altezza a cui si può rimpicciolire il pannello di ricerca
			minWidth: 200,//limite minimo di larghezza a cui si può rimpicciolire il pannello di ricerca
			resizable: true,//indica se il pannello di ricerca deve essere ridimensionabile
			filterLabel: "criterio di ricerca",//etichetta del campo di ricerca
			filterLabelHelp: "", // mostra l'help a fianco il filterLabel Se presente
			searchLabel: "Cerca",//etichetta del bottone che avvia la ricerca dei dati
			label: "Compilazione Automatica",//etichetta del bottone che apre il pannello della ricerca
			dialogTitle: "Ricerca",//titolo del pannello della ricerca
			dialogPosition: "behind",//posizione del pannello di ricerca rispetto al bottone che lo apre. Valori possibili: behind (default) = sotto al bottone, beside = a fianco del bottone
			
			url: ".", //URL invocato per il recupero dei dati. E' invocato via AJAX e deve restituire i dati in formato JSON
			idComune: "",
			mappings: [],
			/*
			 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
			 * l'id del campo del form in cui tale valore deve essere visualizzato
			 */ 
			ERRORS:{
				error: "Errore: ",
				noData: "Non è stato trovato nessun dato",
				multipleData: "E' stato trovato più di un record corrispondente ai criteri di ricerca",
				serverError: "Si è verificato un errore durante la ricerca dei dati richiesti"
			}
		},
		
		_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all",
		
		_dialogHTML: function(){
			return	"<div class='init-autocompiler_search_panel ui-widget ui-widget-content ui-corner-all' id='" + this.element_id + "_search_panel' title='" + this.options.dialogTitle + "'>"
			+ "<label class='init-autocompiler-filter-label' for='" + this.element_id + "_filer_0'>" + this.options.filterLabel + "</label>"+
			((this.options.filterLabelHelp=='')?"":"<span class='init-autocompiler-button init-autocompiler-button-help' id='"+ this.element_id + "-simplerowac-tooltip'><label>(?)</label></span>")+
			"<br /><input class='init-autocompiler-filter ui-corner-all' type='text' name='filter_0' id='" + this.element_id + "_filter_0'/><br/>"
			+ "<span class='init-autocompiler-error' id='" + this.element_id + "_error_message'></span><br/><span class='init-autocompiler-error' id='" + this.element_id + "_error_detail'></span></div>";
		},
		
		_create: function() {
			this.element.addClass(this._CSS_CLASSES);
			this.element_id = this.element.attr("starid");
			var fieldSelector;
			//applico lo stesso stile anche ai campi collegati per evidenziarli rispetto agli altri
			for ( var attrName in this.options.mappings) {
				var linkedFieldId = this.options.mappings[attrName];
				fieldSelector = escapeStringForCssSelector(fieldSelector);
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				sel.addClass(this._CSS_CLASSES);
			}
			this.wrapper = getWrapper(this.element);
			/*
			this.triggerButton = $( "<button>", {
				role: 'button',
                //text: this.options.triggerLabel,
                title: this.options.triggerLabel,
                class: "init-autocompiler-button"
            })
            */
			this.triggerButton = $( "<a>", {
				href: '#',
				title: this.options.label,
                "class": "init-autocompiler-button"
            }).append($("<span>", {
            	"class": "init-autocompiler-button init-autocompiler-button-find"
            })).appendTo( this.wrapper );
			this.triggerButton.bind("click",{control: this},this._openSearchPanel);/**/
			//creo la dialog utilizzata per le ricerche ma la lascio chiusa
			var styleAttr = "";
			//$(html).appendTo($(document.body)); 
			//definisco il bottone che scarena la ricerca e il su gestore di evento click
			/*
			var searchButton = $("#" + control.element_id + "_search_button").button();
			searchButton.bind("click",{control: control},control._searchData);
			*/
			var searchButton = {text: this.options.searchLabel, click: this._searchData};
			var atPos = this.options.dialogPosition == 'beside' ? "right top" : "left bottom";
			var dialogPos = {my: "left top", at: atPos, of: $(this.triggerButton)};
			this.searchDialog = $(this._dialogHTML()).dialog({
				autoOpen: false, 
				width: this.options.width,
				height: this.options.height,
				minWidth: this.options.minWidth,
				minHeight: this.options.minHeight,
				maxHeight: this.options.maxHeight,
				maxWidth: this.options.maxWidth,
				resizable: this.options.resizable,
				buttons: [searchButton], 
				draggable: false, 
				position: dialogPos
			});
			/*
			 * imposto nell'oggetto dialog un riferimento all'autocompiler che lo utilizza 
			 * perchè serve deurante l'esecuzione della funzione che gestisce il click sul bottone cerca
			 */ 
			this.searchDialog.data("autocompiler",this);
		},

		_destroy: function() {
			this.element.removeClass( this._CSS_CLASSES );
			for ( var attrName in this.options.mappings) {
				var linkedFieldId = this.options.mappings[attrName];
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				sel.removeClass(this._CSS_CLASSES);
			}
			this.triggerButton.remove();
			$(this.element).unwrap(); 
		},
		
		_openSearchPanel: function(event){
			var control = event.data.control;
			control.searchDialog.dialog("open");
			if(control.options.filterLabelHelp!=''){
				var ttip = $(escapeStringForCssSelector('#'+ control.element_id + '-simplerowac-tooltip'));			
				ttip.attr('title', control.options.filterLabelHelp);
				ttip.tooltip({content: control.options.filterLabelHelp});
			}
			$(escapeStringForCssSelector("#" + control.element_id + "_filter_0")).val("");
			control._hideError();
			//$("#" + control.element_id + "_search_panel").show();
			return false;
		},
		
		_searchData: function(event){
			var autocomp = $(this).data("autocompiler");//this.element_id + 
			autocomp._hideError();
			/*
			 * disattivazione Block UI per la chiamata ajax: nella pagina del FACCT della modulistica 
			 * il Block UI e' attivato solo quando la variabile globale blockUIEnabled == true
			 */
			blockUIEnabled = false;
			ajaxOpts = {
				success: autocomp._searchDataCallback,
				error: autocomp._searchDataErrorCallback,
				data: {
					filter_0: $(escapeStringForCssSelector("#" + autocomp.element_id + "_filter_0")).val(),
					idComune: autocomp.options.idComune
				},
				type: "POST",
				context: autocomp
			};
			$.ajax(autocomp.options.url,ajaxOpts);
		},
		
		_searchDataCallback: function(data, textStatus, jqXHR){
			//riattivazione Block UI
			blockUIEnabled = true;
			if(data){
				if(data.error){
					this._displayError(this.options.ERRORS.serverError, data.error);
				}
				else if(data.data && data.data.length > 0){
					if(data.data.length > 1){
						this._displayError(this.options.ERRORS.multipleData);
					}
					else{
						this._displayResults(data.data[0]);
					}
				}
				else{
					this._displayError(this.options.ERRORS.noData);
				}
			}
			else{
				this._displayError(this.options.ERRORS.noData);
			}
		},
		
		_searchDataErrorCallback: function(data, textStatus, jqXHR){
			//riattivazione Block UI
			blockUIEnabled = true;
			var errMsg = this.options.ERRORS.serverError;
			//TODO cercare di appendere al messaggio una descrizione dell'errore quando è possibile recuperarla
			this._displayError(errMsg);
		},
		
		_displayResults: function(jsonRecord){
			this._hideDialog();
			if(jsonRecord){
				var attrValue;
				var fieldSelector;
				for ( var attr in this.options.mappings) {
					fieldSelector = this.options.mappings[attr];
					fieldSelector = escapeStringForCssSelector(fieldSelector);
					fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,fieldSelector);
					attrValue = getNestedProperty(attr, jsonRecord);
					if(attrValue == undefined || attrValue == null){
						attrValue = "";
					}
					var destField = $(fieldSelector);
					if(destField.attr('name') && destField.attr('name').indexOf("_DIS_") == 0){
						destField.data('value-storage', attrValue);
					}
					else{
						//destField.val(attrValue);
						setFieldValues(destField,attrValue,true);
					}
				}
			}
		},
		
		_displayError: function(errorMsg, detailMsg){
			var span = $(escapeStringForCssSelector("#" + this.element_id + "_error_message"));
			span.text(errorMsg);
			span.show();
			if(detailMsg != undefined){
				span = $(escapeStringForCssSelector("#" + this.element_id + "_error_detail"));
				span.text(detailMsg);
				span.show();
			}
		},
		
		_hideError: function(){
			var span = $(escapeStringForCssSelector("#" + this.element_id + "_error_message"));
			span.text("");
			span.hide();			
		},
		
		_hideDialog: function(){
			this.searchDialog.dialog("close");
		}
		
	});
})(jQuery);
	
(function( $ ) {	
	/**
	 * Multi Row Autocompiler
	 * controllo generico per la compilazione automatica che prevede
	 * l'apertura di una popup per l'immissione del criterio di ricerca.
	 * I risultati della ricerca vengono visualizzati in una tabella della popup. 
	 * Alla selezione di uno dei record trovati il controllo compila automaticamente i campi collegati.
	 * ATTENZIONE!! l'implementazione del controllo non e' ancora finita. NON UTILIZZARE !!!!
	 */
	$.widget("initui.multirowautocompiler",jQuery.initui.singlerowautocompiler,{
		options: {
			labelSelectorPattern: '[for="{field}"]'
		},
		_dialogHTML: function(){
			var html =	"<div class='init-autocompiler_search_panel ui-widget ui-widget-content ui-corner-all' id='" + this.element_id + "_search_panel' title='" + this.options.dialogTitle + "'>"
			+ "<label class='init-autocompiler-filter-label' for='" + this.element_id + "_filer_0'>" + this.options.filterLabel + "</label><br/>"
			+ "<input class='init-autocompiler-filter ui-corner-all' type='text' name='filter_0' id='" + this.element_id + "_filter_0'/><br/>"
			+ "<span class='init-autocompiler-error' id='" + this.element_id + "_error_message'></span>"
			+ "<div id='" + this.element_id + "_results_panel' style='display:block;'>"
			+ "<table id='" + this.element_id + "_results_table' class='init-autocompiler-results-table'><tr>";
			var columnHeaders = [];
			for ( var attrName in this.options.mappings) {
				html += "<th class='ui-widget-header init-autocompiler-results-header'>";
				var linkedFieldId = this.options.mappings[attrName];
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				var header = sel.attr('title');
				if(!header){
					labelSelector = this.options.labelSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
					sel = $(labelSelector);
					header = sel.text();
					if(!header){
						header = sel.attr('title');
					}
				}
				html += header;
				html += "</th>";
			}
			html += "<th class='ui-widget-header init-autocompiler-results-header'>sel.</th></tr></table></div></div>";
			return html;
		},
		_recordTemplate: function(){
			var tpl = "";
			return tpl;
		}
		
	});
})(jQuery);

	/**
	 * Implementazione della ricerca 'singlerow' delle persone fisiche per codice fiscale
	 */
(function( $ ) {	
	$.widget("initui.cercapersonafisica",jQuery.initui.singlerowautocompiler,{
		options: {
			height: 200,//altezza del pannello di ricerca
			width: 300,// larghezza del pannello di ricerca
			resizable: false,//indica se il pannello di ricerca deve essere ridimensionabile
			filterLabel: "Inserisci il codice fiscale completo",//etichetta del campo di ricerca
			searchLabel: "Cerca",//etichetta del bottone che avvia la ricerca dei dati
			label: "cerca per codice fiscale",//etichetta del bottone che apre il pannello della ricerca
			dialogTitle: "Ricerca persone fisiche"//titolo del pannello della ricerca
		}		
	});
})(jQuery);

	/**
	 * Implementazione della ricerca 'singlerow' delle persone giuridiche per partita IVA
	 */
(function( $ ) {	
	$.widget("initui.cercapersonagiuridica",jQuery.initui.singlerowautocompiler,{
		options: {
			height: 200,//altezza del pannello di ricerca
			width: 300,// larghezza del pannello di ricerca
			resizable: false,//indica se il pannello di ricerca deve essere ridimensionabile
			filterLabel: "Inserire il codice fiscale impresa",//etichetta del campo di ricerca
			filterLabelHelp: "Il codice fiscale impresa è un numero di 11 cifre che spesso coincide con la partita iva, per le imprese individuali corrisponde al codice fiscale del titolare",//etichetta del campo di ricerca
			searchLabel: "Cerca",//etichetta del bottone che avvia la ricerca dei dati
			label: "cerca per codice fiscale impresa",//etichetta del bottone che apre il pannello della ricerca
			dialogTitle: "Ricerca persone giuridiche"//titolo del pannello della ricerca
		}		
	});
})(jQuery);

/**
 * Combo Autocompiler
 * controllo generico per la compilazione automatica che prevede il caricamento 
 * di una lista di risultati direttamente durante la digitazione del testo all'interno del campo del form.
 * La selezione del record dalla lista provoca la compilazione di eventualil altri campi collegati.
 */
(function( $ ) {	
	$.widget("initui.comboautocompiler",jQuery.ui.autocomplete,{
		version: "1.0.0",
		defaultMinLength: 0,
		fieldMatcherRegExp: /{field}/g,
		options: {
			fieldSelectorPattern: '[starid ^= "\\[{field}\\]"]',
			/*
			stringa utilizzata per costruire i selettori jQuery che servono 
			per posizionare i valori restituiti nei campi di destinazione.
			{field} verrà di volta in volta sostituito con la stringa che 
			identifica il campo di destinazione così come definita nei mappings.
			Il valore di default prevede la selezione per id del tag html.
			*/
			url: ".", //URL invocato per il recupero dei dati. E' invocato via AJAX e deve restituire i dati in formato JSON
			idComune: '',
			mappings: []
			/*
			 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
			 * l'id del campo del form in cui tale valore deve essere visualizzato
			 */
		},
		_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all",
		_create: function(){
			this._super();
			this.element.addClass(this._CSS_CLASSES);
			this.element.addClass("init-comboautocompiler");
			this.element_id = this.element.attr("starid");
			//this.element.attr("placeholder",'inizia a digitare o premi ↓ per attivare la ricerca');
			//TODO riattivare il placeholder che è stato temporaneamente tolto per via di un bug di IE che scatena 
			//l'apertura della tendina al caricamento della pagina e al focus del controllo 
			var fieldSelector;
			var firstAttribute;
			//applico lo stesso stile anche ai campi collegati per evidenziarli rispetto agli altri
			for ( var attrName in this.options.mappings) {
				if(!firstAttribute){
					firstAttribute = attrName;
				}
				var linkedFieldId = this.options.mappings[attrName];
				if(linkedFieldId == this.element_id){
					/*
					 * attributo del record selezionato associato al campo di ricerca. 
					 * Viene memorizzato perchè questo attributo è utilizzatio 
					 * per recuperare il valore da visualizzare nel campo alla selezione del record
					 */
					this.element_attribute = attrName;
				}
				fieldSelector = escapeStringForCssSelector(fieldSelector);
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				sel.addClass(this._CSS_CLASSES);
			}
			/*
			 * se non c'è nessun'attributo del record associato al campo di ricerca 
			 * allora visualizzo nel campo di ricerca il primo degli attributi specificati nella option 'mappings'
			 */
			if(!this.element_attribute){
				this.element_attribute = firstAttribute;
			}
			//caricamento dati sempre via AJAX
			if(this.options.url){
				this.source = this._getSource();
			}
			else{
				//TODO gestire il caso in cui mancano i parametri necessari al funzionamento del controllo
			}
			this._on(this.menu.element, {
				menuselect: function( event, ui ) {
					// back compat for _renderItem using item.autocomplete, via #7810
					// TODO remove the fallback, see #8156
					var item = ui.item.data( "ui-autocomplete-item" ) || ui.item.data( "item.autocomplete" ),
						previous = this.previous;

					// only trigger when focus was lost (click on menu)
					if ( this.element[0] !== this.document[0].activeElement ) {
						this.element.focus();
						this.previous = previous;
						// #6109 - IE triggers two focus events and the second
						// is asynchronous, so we need to reset the previous
						// term synchronously and asynchronously :-(
						this._delay(function() {
							this.previous = previous;
							this.selectedItem = item;
						});
					}

					if ( false !== this._trigger( "select", event, { item: item } ) ) {
						this._value( item.value );
					}
					// reset the term after the select event
					// this allows custom select handling to work properly
					this.term = this._value();

					this.close( event );
					this.selectedItem = item;
					this._displayResults(item);
				}
			});
			/*
			* l'evento di selezione del record è già gestito internamente al controllo
			* ma può essere ridefinito passando la funzione che gestisce l'evento nella option 'select'
			*/
			this._initUI();
		},
		_getSource: function(){
			return function(request, response){
				//disattivazione Block UI (funzionante solo nella pagina della modulistica del FACCT)
				blockUIEnabled = false;
				var ajaxOpts = {
					data:{
						filter_0: request.term,
						idComune: this.options.idComune
					},
					context: this,
					type: 'POST',
					success: function(data){
						//riattivazione Block UI
						blockUIEnabled = true;
						var results = [];
						var item;
						for(var i = 0; i < data.data.length; i++){
							item = data.data[i];
							//val = eval("data.data." + this.element_attribute);
							var val = this._buildItemValue(item);
							var lab = this._buildItemLabel(item);
							results[i] = {
									value: val,
									label: lab,
									record: item
								};
						}
						response(results);
						/*
						response($.map(data.data, function(item){
							var val = eval("item." + this.element_attribute);
							//TODO prevedere option per specificare quali attributi si vuole visualizzare nella lista dei record trovati
							return {
								value: val,
								label: val,
								record: item
							};
						}));
						*/
					}
				};
				$.ajax(this.options.url,ajaxOpts);
			};	
		},
		_displayResults: function(item){
			if(item && item.record){
				var attrValue;
				var fieldSelector;
				for ( var attr in this.options.mappings) {
					fieldSelector = this.options.mappings[attr];
					fieldSelector = escapeStringForCssSelector(fieldSelector);
					fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,fieldSelector);
					attrValue = getNestedProperty(attr, item.record);
					if(attrValue == undefined || attrValue == null){
						attrValue = "";
					}
					var destField = $(fieldSelector);
					if(destField.attr('name') && destField.attr('name').indexOf("_DIS_") == 0){
						destField.data('value-storage', attrValue);
					}
					else{
						//destField.val(attrValue);
						setFieldValues(destField,attrValue,true);
					}
				}
			}
		},
		_buildItemValue: function(item){
			return this.element_attribute ? item[this.element_attribute] : item;
		},
		_buildItemLabel: function(item){
			return this.element_attribute ? item[this.element_attribute] : item;
		},
		_initUI: function(){
			
		}
	});
})(jQuery);

/**
 * Cached select Autocompiler
 * controllo generico per la compilazione automatica che prevede il caricamento 
 * di una lista di risultati direttamente durante la digitazione del testo all'interno del campo del form.
 * La selezione del record dalla lista provoca la compilazione di eventualil altri campi collegati.
 */
(function( $ ) {	
	$.widget("initui.selectautocompiler",{
		version: "1.0.0",
		fieldMatcherRegExp: /{field}/g,
		iconWidth: 30,
		options: {
			fieldSelectorPattern: '[starid ^= "\\[{field}\\]"]',
			/*
			stringa utilizzata per costruire i selettori jQuery che servono 
			per posizionare i valori restituiti nei campi di destinazione.
			{field} verrà di volta in volta sostituito con la stringa che 
			identifica il campo di destinazione così come definita nei mappings.
			Il valore di default prevede la selezione per id del tag html.
			*/
			url: ".", //URL invocato per il recupero dei dati. E' invocato via AJAX e deve restituire i dati in formato JSON
			idComune: '',
			mappings: [],
			/*
			 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
			 * l'id del campo del form in cui tale valore deve essere visualizzato
			 */
			useCache: true,
			emptyOption: true
		},
		_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all",
		_create: function(){
			this._super();
			//inizializzazione cache a livello di prototype
			this._initCache();
			this.element_id = this.element.attr("starid");
			this.replaceElement = this.element[0].tagName.toLowerCase() != "select"; 
			if(this.replaceElement){
				this.parentContainer = this.element.parent();
				var selectInput = $("<select></select>");
				selectInput.copyAllAttributes(this.element[0]);
				this.element.remove();
				this.element = selectInput;
				this.parentContainer.append(this.element);
				var jqSelect = this.element.selectmenu(this.options);
				jqSelect = jqSelect.data('uiSelectmenu');
				//ridefinisco gli ids del controllo selectmenu perchè jquery.ui li gestisce senza escape css e da errore
				var escapedId = escapeStringForCssSelector(jqSelect.ids.element);
				jqSelect.ids = {
						element: escapedId,
						button: escapedId + "-button",
						menu: escapedId + "-menu"
				};
			}
			var classes = this.element[0].classList;
			var escapedId = escapeStringForCssSelector(this.element_id);
			var targets = $('#' + escapedId + "-button").add('#' + escapedId + "-menu");
			targets.addClass(this._CSS_CLASSES);
			targets.addClass("init-selectautocompiler");
			for (var i = 0; i < classes.length; i++) {
				targets.addClass(classes[i]);
			}
			var fieldSelector;
			var firstAttribute;
			//applico lo stesso stile anche ai campi collegati per evidenziarli rispetto agli altri
			for ( var attrName in this.options.mappings) {
				if(!firstAttribute){
					firstAttribute = attrName;
				}
				var linkedFieldId = this.options.mappings[attrName];
				if(linkedFieldId == this.element_id){
					/*
					 * attributo del record selezionato associato al campo di ricerca. 
					 * Viene memorizzato perchè questo attributo è utilizzatio 
					 * per recuperare il valore da visualizzare nel campo alla selezione del record
					 */
					this.element_attribute = attrName;
				}
				fieldSelector = escapeStringForCssSelector(fieldSelector);
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				sel.addClass(this._CSS_CLASSES);
			}
			/*
			 * se non c'è nessun'attributo del record associato al campo di ricerca 
			 * allora visualizzo nel campo di ricerca il primo degli attributi specificati nella option 'mappings'
			 */
			if(!this.element_attribute){
				this.element_attribute = firstAttribute;
			}
			//caricamento dati via AJAX all'inizializzazione del controllo o da cache e caricamento js delle options della select
			if(this.options.url){
				this._loadOptionsfromCachedData();
			}
			else{
				console.eror("initui.selectautocompiler - ERRORE: url per il caricamento dei dati non definito.");
				//TODO gestire il caso in cui mancano i parametri necessari al funzionamento del controllo
			}
			//this.element.show();
			/*
			* l'evento di selezione del record è già gestito internamente al controllo
			* ma può essere ridefinito passando la funzione che gestisce l'evento nella option 'select'
			*/
			var that = this;
			this.element.on('selectmenuchange',function(event,ui) {
				that._displayResults(event.target.selectedIndex);
			});
		},
		_loadOptionsfromCachedData: function(){
			if (this.options.useCache === true && this._getCache() != undefined) {
				this._loadOptions(this._getCache());
			}
			else{
				this._getData();
			}
		},
		_cacheData: function(data){
			jQuery.initui.selectautocompiler.prototype.dataCache[this.options.url] = data;
		},
		_getCache: function(){
			return jQuery.initui.selectautocompiler.prototype.dataCache[this.options.url];
		},
		_initCache: function(){
			if(jQuery.initui.selectautocompiler.prototype.dataCache == undefined){
				jQuery.initui.selectautocompiler.prototype.dataCache = [];
			}
		},
		_getData: function(){
			var ajaxOpts = {
				data : {
					idComune : this.options.idComune
				},
				context : this,
				type : 'POST',
				success : function(data) {
					var results = [];
					var item;
					for (var i = 0; i < data.data.length; i++) {
						results[i] = data.data[i];
					}
					if (this.options.useCache === true) {
						this._cacheData(results);
					}
					this._loadOptions(results);
				}
			};
			$.ajax(this.options.url, ajaxOpts);
		},
		_loadOptions: function(results){
			this.data = results;
			//jqSelect = $(this.element);
			this.element.empty();
			if (this.options.emptyOption) {
				this.element.append("<option value=''></option>");
			}
			var widthCheck = $('<select id="' + this.element_id + '-wc"></select>');
			for (var index in results) {
				var record = results[index];
				var jqOption = $("<option value=\"" + this._buildItemValue(record) + "\">" + this._buildItemLabel(record) + "</option>");
				this.element.append(jqOption);
				jqOption.data('record',record);
				var jqOption2 = $("<option value=\"\">" + this._buildItemLabel(record) + "</option>");
				widthCheck.append(jqOption2);
			}
			//calcolo la lunghezza della select aggiungendola al body e rimuovendola subito dopo
			//posizionamento della select nel dom
			this.element.selectmenu( "refresh" );
			$('BODY').append(widthCheck);
			this.selectWidth = widthCheck[0].clientWidth;
			widthCheck.remove();
			//impostazione della larghezza
			if(this.selectWidth != undefined){
				this.element.selectmenu( "option", "width", this.selectWidth + this.iconWidth);
			}
		},
		_displayResults: function(itemIndex){
			if(this.options.emptyOption){
				itemIndex--;
			}
			if (itemIndex > -1 && itemIndex < this.data.length) {
				var item = this.data[itemIndex];
				if (item != undefined) {
					var attrValue;
					var fieldSelector;
					for ( var attr in this.options.mappings) {
						fieldSelector = this.options.mappings[attr];
						if (fieldSelector != undefined && attr != this.element_attribute) {
							fieldSelector = escapeStringForCssSelector(fieldSelector);
							fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,fieldSelector);
							attrValue = getNestedProperty(attr, item);
							if (attrValue == undefined || attrValue == null) {
								attrValue = "";
							}
							var destField = $(fieldSelector);
							if (destField.attr('name') && destField.attr('name').indexOf("_DIS_") == 0) {
								destField.data('value-storage', attrValue);
							} else {
								//destField.val(attrValue);
								setFieldValues(destField, attrValue, true);
							}
						}
					}
				}
			}
		},
		_buildItemValue: function(item){
			return this.element_attribute ? getNestedProperty(this.element_attribute,item) : item;
		},
		_buildItemLabel: function(item){
			return this.element_attribute ? getNestedProperty(this.element_attribute,item) : item;
		}
	});
})(jQuery);


/**
 * Endo Autocompiler
 * controllo per la compilazione automatica delle liste degli endoprocedimenti,
 * simile al comboautocompiler ma in più alla selezione dell'endoprocedimento 
 * viene visualizzato un link al di sopra del controllo che consente di richiamare 
 * la scheda descrittiva dell'endo selezionato.
 */
(function( $ ) {	
	$.widget("initui.endoautocompiler",jQuery.initui.comboautocompiler,{
		previousSelected: "",
		firstLoad: true,
		idEndo: -1,
		options: {
			fieldSelectorPattern: '[starid ^= "\\[{field}\\]"]',
			/*
			stringa utilizzata per costruire i selettori jQuery che servono 
			per posizionare i valori restituiti nei campi di destinazione.
			{field} verrà di volta in volta sostituito con la stringa che 
			identifica il campo di destinazione così come definita nei mappings.
			Il valore di default prevede la selezione per id del tag html.
			*/
			url: ".", //URL invocato per il recupero dei dati. E' invocato via AJAX e deve restituire i dati in formato JSON
			idComune: '',
			idComuneAlias: '',
			/*
			 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
			 * l'id del campo del form in cui tale valore deve essere visualizzato
			 */
			mappings: [],
			filterByExactMatch: false,
			schedaEndoUrl: "../dizionario/dialogSchedaSpiegazioneEndo1.htm?id=RPL_CODICEINVENTARIO&idcomuneinventario=RPL_IDCOMUNEINVENTARIO",
			idComuneAliasHttpParam: 'IdComune=',
			//si attiva la ricerca dei dati anche senza digitazione del testo da parte dell'utente
			minLength: 0
		},
		_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all init-endoautocompiler",
		_getSource: function(){
			return function(request, response){
				//disattivazione Block UI per pagina modulistica del FACCT 
				blockUIEnabled = false;
				var ajaxOpts = {
					data: this._buildQueryRequest(request),
					context: this,
					type: 'POST',
					success: function(data){
						//riattivazione Block UI
						blockUIEnabled = true;
						var results = [];
						var item;
						for(var i = 0; i < data.data.length; i++){
							item = data.data[i];
							//val = eval("data.data." + this.element_attribute);
							if(this._checkIfItemToDisplay(item)){
								var val = this._buildItemValue(item);
								var lab = this._buildItemLabel(item);
								results[results.length] = {
										value: val,
										label: lab,
										record: item
									};
							}
						}
						response(results);
						/*
						response($.map(data.data, function(item){
							var val = eval("item." + this.element_attribute);
							//TODO prevedere option per specificare quali attributi si vuole visualizzare nella lista dei record trovati
							return {
								value: val,
								label: val,
								record: item
							};
						}));
						*/
					}
				};
				$.ajax(this.options.url,ajaxOpts);
			};
		},
		
		_buildQueryRequest: function(request){
			var params = {
					filter_0: request.term,
					exact_match: this.options.filterByExactMatch,
					idComune: this.options.idComune					
			};
			return params;
		},
		
		_buildItemValue: function(item){
			val = "";
			if(item && item.codiceInventario){
				val += "E[";
				val += item.idcomune;
				val += "|";
				val += item.codiceInventario;
				val += "]";
			}
			return val;
		},
		_buildItemLabel: function(item){
			return this._buildItemValue(item);
		},
		
		_getSourceCallback: function(data){
			//riattivazione Block UI
			blockUIEnabled = true;
			var results = [];
			var item;
			for(var i = 0; i < data.data.length; i++){
				item = data.data[i];
				//val = eval("data.data." + this.element_attribute);
				if(this._checkIfItemToDisplay(item)){
					var val = this._buildItemValue(item);
					var lab = this._buildItemLabel(item);
					results[i] = {
							value: val,
							label: lab,
							record: item
						};
				}
			}
			this.response(results);
			/*
			response($.map(data.data, function(item){
				var val = eval("item." + this.element_attribute);
				//TODO prevedere option per specificare quali attributi si vuole visualizzare nella lista dei record trovati
				return {
					value: val,
					label: val,
					record: item
				};
			}));
			*/
		},
		
		/*
		 * le sottoclassi possono ridefinire questa funzione se si vuole escludere alcuni dati 
		 * dalla visualizzazione nell'elenco a tendina. se restituisce false l'item esaminato non sarà visibile nell'elenco.
		 */
		_checkIfItemToDisplay: function(item){
			return true;
		},

		_displayResults: function(item){
			if(item && item.record){
				//e' mantenuta la possibilità di configurare i mappings dei risultati su altri id semantici
				var attrValue;
				var fieldSelector;
				for ( var attr in this.options.mappings) {
					fieldSelector = this.options.mappings[attr];
					fieldSelector = escapeStringForCssSelector(fieldSelector);
					fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,fieldSelector);
					attrValue = getNestedProperty(attr, item.record);
					if(attrValue == undefined || attrValue == null){
						attrValue = "";
					}
					var destField = $(fieldSelector);
					if(destField.attr('name') && destField.attr('name').indexOf("_DIS_") == 0){
						destField.data('value-storage', attrValue);
					}
					else{
						//destField.val(attrValue);
						setFieldValues(destField,attrValue,true);
					}
				}
				this._updateUI(item.record);
			}
			else{
				this._cleanResults();
			}
			this.previousSelected = this._value();
		},
		
		_updateUI: function(record){
			this.infoEndoButton.show();
			//L'URL per il front office .NET deve trovarsi memorizzato nella variabile AR_MS_URL 
			//altrimenti gli indirizzi per i link alle schede di dettaglio degli endo saranno valutati come relativi
			var baseAreaRiservataMsUrl = /*AR_MS_URL ? AR_MS_URL : */""; 
			//L'URL relativo per la scheda endo è memorizzata nelle option dell'oggetto 
			// schedaEndoUrl: "../dizionario/dialogSchedaSpiegazioneEndo1.htm?id=CODICEINVENTARIO&idcomuneinventario=IDCOMUNEINVENTARIO",
			
			var linkUrl = baseAreaRiservataMsUrl + this.options.schedaEndoUrl.replace('RPL_CODICEINVENTARIO', record.codiceInventario).replace('RPL_IDCOMUNEINVENTARIO', record.idcomune)  + "&" + this.options.idComuneAliasHttpParam + this.options.idComuneAlias;
			this.infoEndoButton.attr('href',linkUrl);
			this.infoEndoButton.attr('title','scheda endoprocedimento: ' + (record.descrizione ? record.descrizione : ""));
			this.idEndo = record.codiceInventario;
		},
		
		_cleanResults: function(){
			this.infoEndoButton.hide();
			this.infoEndoButton.attr('title','scheda endoprocedimento');
			this.idEndo = -1;
		},
		
		/*
		//ogni endo visualizza solo il proprio codice che è l'unico dato restituito per ciascun record
		_buildItemValue: function(item){
			var val = "";
			if(item){
				val = item;
			}
			return val;
		},
		
		_buildItemLabel: function(item){
			return this._buildItemValue(item);
		},
		*/
		
		_initUI: function(){
			this.wrapper = getWrapper(this.element);
			var endoDiv = $("<div>",{"class": "init-autocompiler-toolbar"}).prependTo( this.wrapper );
			//posizionamento link e icone intorno al controllo
			//apertura scheda informativa dell'endo
			this.infoEndoButton = $( "<a>", {
				href: '#',
				title: "scheda endoprocedimento",
	            "class": "init-autocompiler-button"
	        }).append($("<span>", {
	        	"class": "init-autocompiler-button init-autocompiler-button-info"
	        })).appendTo( endoDiv );
			//creazione del div per il caricamento della dialog della scheda dell'endoprocedimento
			this.schedaEndoDiv = $('#schedaEndo');
			if(this.schedaEndoDiv.length == 0){
				this.schedaEndoDiv = $('<div>',{
					'class': "schedaEndo"
				}).appendTo($("BODY"));
			}
			this.schedaEndoDiv.dialog({
				width: 600,
				height: 500,
				title: "Dettagli dell\'endoprocedimento",
				modal: true,
				autoOpen: false,
				open: function () {
					$(this).find('#accordion').accordion({ header: "h3", autoHeight: false });
					$(this).find('tr:nth-child(2n+1)').addClass('rigaAlternata');
				}
			});
			this.infoEndoButton.bind("click",{control: this},this._visualizzaSchedaEndo);
			this.options.filterByExactMatch = true;
			this._loadFirstItem();
			//this.firstLoad = false;
			this.options.filterByExactMatch = false;
		},
		
		_visualizzaSchedaEndo: function(event) {

    		event.preventDefault();
    		var me = event.data.control;
    		var url = $(this).attr('href');
    		me.schedaEndoDiv.load(url, function () {
    			$(this).dialog('open'); 
    		});
	    },
		
		_loadFirstItem: function(){
			var that = this;
			this.source({term: this._value()},function(results){
				var curValue = that._value();
				var selectItem;
				if(results.length == 1){
					var item = results[0];
					if(curValue.length > 0 && curValue === item.value){
						that._value(item.value);
						selectItem = item;
					}
				}
				that.selectedItem = selectItem;
				that._displayResults(selectItem);
				//se il controllo è già prepopolato con un valore che corrisponde a un valore 
				//E[ALLEGATI.CODICEINVENTARIO]-[ALLEGATI.ALLEGATO] presente nel DB allora il campo viene impostato readonly 
				//e viene impedita la cancellazione della riga della tabella del modulo 
				/*
				if(that.firstLoad && selectItem){
					$(that.element).attr('readOnly','readOnly');
					$(that.element).addClass('init-comboautocompiler-disabled');
					$(that.element).addClass('init-endoautocompiler-disabled');
					//that.disable();
					if($(that.element).data('obbligatorio')){
						var trElement = findParentElementByTagName(that.element,"TR");
						if(trElement){
							var deleteButton = $(trElement).find('.cart-delete-row-button');
							if(deleteButton){
								deleteButton.button('disable');
							}
						}
					}
				}
				*/
				that.setEditable(!that._checkIfToProtect());
				that.firstLoad = false;
			});
		},
		
		setEditable: function(editable){
			if(editable){
				$(this.element).removeAttr('readonly');
			}
			else{
				$(this.element).attr('readonly',true);
			}
			//$(this.element).prop('disabled',!editable);
			if(editable){
				$(this.element).removeClass('init-comboautocompiler-disabled');
				$(this.element).removeClass('init-endoautocompiler-disabled');
			}
			else{
				$(this.element).addClass('init-comboautocompiler-disabled');
				$(this.element).addClass('init-endoautocompiler-disabled');
			}
		},
		
		_checkIfToProtect: function(){
			var toProtect = this.firstLoad && this.selectedItem ? true : false;
			return toProtect;
		},
		
		_change: function( event ) {
			if ( this.previous !== this._value() ) {
				if ( this.previousSelected !== this._value() ) {
					this._loadFirstItem();
				}
				this._trigger( "change", event, { item: this.selectedItem } );
			}
		}
		
	});
})(jQuery);

/**
 * Allegati Endo Autocompiler
 * controllo per la compilazione automatica delle liste degli allegati degli endoprocedimenti,
 * simile al comboautocompiler ma con una serie di funzionalità aggiuntive 
 * per la visualizzazione di dati relativi all'allegato (link a pagina info, download allegatio binario etc...)
 * o della scheda dinamica dell'endo
 */
(function( $ ) {
$.widget("initui.allegatiendoautocompiler",jQuery.initui.endoautocompiler,{
	/*
	version: "1.0.0",
	fieldMatcherRegExp: /{field}/g,
	previousSelected: "",
	*/
	fkD2mtId: -1,
	idcomunerecord: -1,
	allegatoId: -1,
	alberoDocId: -1,
	aggiungiBloccoRegExp: /aggiungiBlocco\s*\(\s*["']?([\w\d]*)["']?\s*,\s*(\d*)\s*\)/g,
	eliminaBloccoRegExp: /eliminaBlocco\s*\(\s*["']?([\w\d]*)["']?\s*,\s*(\d*)\s*\)/g,
	settaValoreRegExp: /settaValore\s*\(\s*.*\s*,\s*.*\s*,\s*["']?([\w\d]*)["']?\s*,\s*["']?([\w\d]*)["']?\s*\)/g,
	dsValidationRequired: false,
	options: {
		fieldSelectorPattern: '[starid ^= "\\[{field}\\]"]',
		/*
		stringa utilizzata per costruire i selettori jQuery che servono 
		per posizionare i valori restituiti nei campi di destinazione.
		{field} verrà di volta in volta sostituito con la stringa che 
		identifica il campo di destinazione così come definita nei mappings.
		Il valore di default prevede la selezione per id del tag html.
		*/
		url: ".", //URL invocato per il recupero dei dati. E' invocato via AJAX e deve restituire i dati in formato JSON
		idComune: '',
		/*
		 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
		 * l'id del campo del form in cui tale valore deve essere visualizzato
		 */
		mappings: [],
		/*
		 * se valorizzato serve a specificare quale sia il campo che contiene il codice dell'endoprocedimento,
		 * se presente il codice endo non viene visualizzato nel valore di questo campo perchè già presente nell'altro
		 */
		idSemanticoEndo: "",
		idSemanticoAllegato: "",
		idAlberoproc: '',
		filterByExactMatch: false,
		schedaDinamicaWidth: 0,
		//schedaEndoUrl: "/Public/mostraDettagliEndo.aspx?IdComune=DEMOCART&fromAreaRiservata=True&print=False&Id=",
		schedaDinamicaUrl: "/nuovaistanzaschede/ajaxGetScheda.htm?codiceModello=",
		caricaDatiDinamiciUrl: "/cart/ajaxCaricaSchedaDinamica.htm?codiceModello=",
		salvaSchedaDinamicaUrl: "/nuovaistanzaschede/ajaxSalvaScheda.htm",
		allegaSchedaDinamicaUrl: "/cart/ajaxAllegaSchedaDinamica.htm",
		validaCampoUrl: "/nuovaistanzaschede/ajaxValidaCampo.htm?codiceModello=",
		aggiungiBloccoUrl: "/nuovaistanzaschede/ajaxAggiungiBlocco.htm",
		eliminaBloccoUrl: "/nuovaistanzaschede/ajaxEliminaBlocco.htm"
	},
	
	_buildQueryRequest: function(request){
		var params = {
				filter_0: request.term,
				endo: this.options.idEndo,
				exact_match: this.options.filterByExactMatch,
				idComune: this.options.idComune					
		};
		var linkedField = this._getLinkedField();
		if(linkedField){
			params.filter_1 = linkedField._value();
		}
		return params;
	},
	
	_getLinkedField: function(){
		var linkedField;
		if(this.options.idSemanticoEndo){
			var splittedName = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name'));
			var linkedFieldName = this.options.idSemanticoEndo;
			var indexIndex = 2; 
			for (var int = indexIndex; int < splittedName.length; int++) {
				if("-1" != splittedName[int]){
					var separator = int == indexIndex ? "_row_" : "_";
					linkedFieldName += separator + splittedName[int];
				}
			}
			linkedFieldName = "[" + escapeStringForCssSelector(linkedFieldName) + "]";
			var linkedInput = $("input[starid^='" + linkedFieldName + "']");
			linkedField = linkedInput.data("initui-endoautocompiler");
		}
		return linkedField;
	},
	
	_getLinkedFileField: function(){
		//TODO il campo file associato non viene più recuperato per via del fatto che nel nome ora è presente anche l'id dell'item (per distinguere campi diversi associati allo stesso id semantico)
		// recuperare il campo file con nome inizia per [ID_SEMANTICO_row_INDEX]
		var fileField;
		if(this.options.idSemanticoAllegato){
			var splittedName = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name'));
			var linkedFieldName = this.options.idSemanticoAllegato;
			var indexIndex = 2; 
			for (var int = indexIndex; int < splittedName.length; int++) {
				if("-1" != splittedName[int]){
					var separator = int == indexIndex ? "_row_" : "_";
					linkedFieldName += separator + splittedName[int];
				}
			}
			linkedFieldName = "fileupload-[" + escapeStringForCssSelector(linkedFieldName) + "]";
			var linkedInput = $("div[id^='" + linkedFieldName + "']");
			//fileField = linkedInput.data("initui-endoautocompiler");
			fileField = linkedInput.data('blueimpFileupload');
		}
		return fileField;
	},
	
	_getLinkedFileHiddenFields: function(){
		var fileField;
		if(this.options.idSemanticoAllegato){
			var splittedName = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name'));
			var linkedFieldName = this.options.idSemanticoAllegato;
			var indexIndex = 2; 
			for (var int = indexIndex; int < splittedName.length; int++) {
				if("-1" != splittedName[int]){
					var separator = int == indexIndex ? "_row_" : "_";
					linkedFieldName += separator + splittedName[int];
				}
			}
			linkedFieldName = "[" + escapeStringForCssSelector(linkedFieldName) + "]";
			//var linkedInput = $("input[starid='" + linkedFieldName + "']");
			//fileField = linkedInput.data("initui-endoautocompiler");
			fileField = $("input[starid^='" + linkedFieldName + "']");
		}
		return fileField;
	},
	
	/*
	 * i dati da caricare nell'elenco a tendina vengono filtrati per escludere gli allegati e le schede dinamiche
	 * che sono già stati caricati nelle altre righe della tabella ALLEGATI.
	 * Si tratta di codice specifico per l'utilizzo del controllo all'interno dei moduli del CART inefficace in altri contesti.
	 */ 
	_checkIfItemToDisplay: function(record){
		//verifico che il valore non sia già presente in altre righe della stessa tabella.
		var fieldInfo = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name'));
		/*
		 * TODO Verificare: se la stessa scheda o lo stesso allegato è associato a due endo diversi 
		 * deve apparire una volta sola comunque o essere ripetuto per ciascun endo?
		 * Se deve apparire una sola volta va modificato anche il metodo service che imposta 
		 * i valori di default al caricamento della domanda.
		 */ 
		var myIdEndo, myIdAllegato, myIdScheda;
		if(record.allegato && record.allegato.id){
			myIdEndo = record.allegato.inventarioprocedimentoId;
			myIdAllegato = record.allegato.id.codice;
			myIdScheda = -1;
		}
		else if(record.schedaDinamica && record.schedaDinamica.id){
			myIdEndo = record.schedaDinamica.id.codiceinventario;
			myIdAllegato = -1;
			myIdScheda = record.schedaDinamica.id.fkD2mtId;
		}
		var valueFound = false;
		var field;
		var jsObj;
		var rowCount = 0;
		var maxRowCount = 0;
		var tableElement = findParentElementByTagName(this.element, "TABLE");
		if(tableElement){
			var tabId = $(tableElement).attr('id');
			var maxDataIndexHidden = $("input[starid='" + tabId + "_maxprog']");
			if(maxDataIndexHidden && maxDataIndexHidden.length > 0){
				maxRowCount = Number(maxDataIndexHidden.val());
			}
		}
		if("" + INDEXING_SUFFIX == "undefined"){
			INDEXING_SUFFIX = "_row_";
		}
		while(rowCount < maxRowCount +1){
			if(rowCount != Number(fieldInfo[2])){
				field = $("input[starid='" + fieldInfo[0] + INDEXING_SUFFIX + rowCount + "']");
				if(field && field.length){
					jsObj = field.data("allegatiendoautocompiler");
					if(jsObj){
						if(jsObj.idEndo == myIdEndo && jsObj.allegatoId == myIdAllegato && jsObj.fkD2mtId == myIdScheda){
							valueFound = true;
							break;
						}
					}
				}
			}
			rowCount++;
		}
		return !valueFound;
	},

	_updateUI: function(record){
		this.allegatoId = -1;
		this.fkD2mtId = -1;
		var codePrefix = "";
		var validateDs = true;
		var mandatory = false;
		if(record.allegato){
			this._displayResultsAllegato(record.allegato);
			this.idEndo = record.allegato.inventarioprocedimento.id.codice;
			validateDs = record.allegato.foRichiedefirma;
			mandatory = record.allegato.richiesto;
			codePrefix = "E";
		}
		else if(record.schedaDinamica){
			this._displayResultsSchedaDinamica(record.schedaDinamica);
			this.idEndo = record.schedaDinamica.id.codiceinventario;
			validateDs = record.schedaDinamica.flagTipofirma > 0;
			mandatory = !(record.schedaDinamica.flagFacoltativa == true);
			codePrefix = "E";
		}
		else if(record.documentoAlbero){
			this._displayResultsDocumentoAlbero(record.documentoAlbero);
			this.idEndo = record.documentoAlbero.alberoproc.id.codice;
			validateDs = record.documentoAlbero.foRichiedefirma;
			mandatory = record.documentoAlbero.richiesto;
			codePrefix = "P";
		}
		var linkedField = this._getLinkedField();
		if(linkedField){
			var linkedEndo = linkedField.idEndo;
			if(linkedEndo && linkedEndo != this.idEndo){
				linkedField._value(codePrefix + "[" + this.options.idAlberoproc + "]");
				linkedField._loadFirstItem();
			}
		}
		var linkedFile = this._getLinkedFileField();
		if(linkedFile && typeof linkedFile.enableDsValidation === 'function'){
			linkedFile.enableDsValidation(validateDs);
			this.dsValidationRequired = validateDs;
		}
		if(linkedFile){
			var hiddenInputs = this._getLinkedFileHiddenFields();
			if(hiddenInputs && hiddenInputs.length > 0){
				if(setMandatoryField != undefined && typeof setMandatoryField == "function"){
					setMandatoryField(mandatory, hiddenInputs.first().attr('name'));
				}
			}
		}
	},
	
	_displayResultsAllegato: function(allegato){
		this.schedaDinamicaButton.hide();
		if(allegato && allegato.id && allegato.id.codice){
			this.allegatoId = allegato.id.codice;
			this.fkD2mtId = -1;
			this.alberoDocId = -1;
		}
		/*
		 * determinati attributi vengono utilizzati per attivare specifiche funzionalità interne al controllo
		 * come il collegamento alla scheda dell'endo, il link alla pagina dell'allegato, il link per il download dell'allegato 
		 * e il tooltip che riporta le note dell'allegato 
		 */
		if(!this.options.idSemanticoEndo && allegato && allegato.inventarioprocedimento.id.codice){
			this.infoEndoButton.show();
			//L'URL per il front office .NET deve trovarsi memorizzato nella variabile AR_MS_URL 
			//altrimenti glil indirizzi per i link alle schede di dettaglio degli endo saranno valutati come relativi
			var baseAreaRiservataMsUrl = /*AR_MS_URL ? AR_MS_URL : */""; 
			//L'URL relativo per la scheda endo è memorizzata nelle option dell'oggetto 
			// var linkUrl = baseAreaRiservataMsUrl + this.options.schedaEndoUrl + allegato.inventarioprocedimentoId;
			var linkUrl = baseAreaRiservataMsUrl + this.options.schedaEndoUrl.replace('RPL_CODICEINVENTARIO', allegato.inventarioprocedimento.id.codice).replace('RPL_IDCOMUNEINVENTARIO', allegato.inventarioprocedimento.id.idcomune) ;			
			this.infoEndoButton.attr('href',linkUrl);
			//imposto il nome dell'endo nel title del bottone info
			this.infoEndoButton.attr('title','scheda endoprocedimento: ' + (allegato.inventarioprocedimento.procedimento ? allegato.inventarioprocedimento.procedimento : ""));
		} 
		else{
			this.infoEndoButton.hide();
		}
		if(allegato && allegato.noteFrontend){
			this.infoAllegatoButton.show();
			//this.infoAllegatoButton.attr('title',allegato.noteFrontend);
			var ttip = this.infoAllegatoButton.find('.init-autocompiler-button-help');
			ttip.attr('title',allegato.noteFrontend);
			ttip.tooltip({content: allegato.noteFrontend});
		} 
		else{
			this.infoAllegatoButton.hide();
		}
		if(allegato && allegato.indirizzoweb){
			var urlAllegato = allegato.indirizzoweb.indexOf("http") == 0 ? allegato.indirizzoweb : 'http://' + allegato.indirizzoweb;
			this.urlAllegatoButton.show();
			this.urlAllegatoButton.attr('href',urlAllegato);
		}
		else{
			this.urlAllegatoButton.hide();
		}
		if(allegato && allegato.oggettiId){
			if(!allegato.foTipodownload){
				//predispongo il donload generico
				this.downloadAllegatoButton.show();
				this.downloadAllegatoButton.data('codiceoggetto',allegato.oggettiId);
				this.downloadAllegatoButton.data('idcomuneOggetto',allegato.oggetti.id.idcomune);
				this.downloadAllegatoPdfButton.hide();
				this.downloadAllegatoDocButton.hide();
				this.downloadAllegatoRtfButton.hide();
				this.downloadAllegatoOdtButton.hide();
			}
			else{
				var formati = allegato.foTipodownload.split(',');
				//predispongo i bottoni per il download del modulo nei formati richiesti
				this.downloadAllegatoButton.hide();
				this.downloadAllegatoPdfButton.hide();
				this.downloadAllegatoOdtButton.hide();
				this.downloadAllegatoDocButton.hide();
				this.downloadAllegatoRtfButton.hide();
				for(var i = 0; i < formati.length; i++){
					formati[i] = $.trim(formati[i]).toUpperCase();
					if(formati[i] == 'PDF'){
						this.downloadAllegatoPdfButton.show();
						this.downloadAllegatoPdfButton.data('codiceoggetto',allegato.oggettiId);
						this.downloadAllegatoPdfButton.data('idcomuneOggetto',allegato.oggetti.id.idcomune);
						this.downloadAllegatoPdfButton.data('formato',formati[i]);
					}
					else if(formati[i] == 'DOC'){
						this.downloadAllegatoDocButton.show();
						this.downloadAllegatoDocButton.data('codiceoggetto',allegato.oggettiId);
						this.downloadAllegatoDocButton.data('idcomuneOggetto',allegato.oggetti.id.idcomune);
						this.downloadAllegatoDocButton.data('formato',formati[i]);
					}
					else if(formati[i] == 'OPN' || formati[i] == 'ODT'){
						this.downloadAllegatoOdtButton.show();
						this.downloadAllegatoOdtButton.data('codiceoggetto',allegato.oggettiId);
						this.downloadAllegatoOdtButton.data('idcomuneOggetto',allegato.oggetti.id.idcomune);
						this.downloadAllegatoOdtButton.data('formato','ODT');
					}
					else if(formati[i] == 'RTF'){
						this.downloadAllegatoRtfButton.show();
						this.downloadAllegatoRtfButton.data('codiceoggetto',allegato.oggettiId);
						this.downloadAllegatoRtfButton.data('idcomuneOggetto',allegato.oggetti.id.idcomune);
						this.downloadAllegatoRtfButton.data('formato',formati[i]);
					}
				}
			}
			//this.downloadAllegatoButton.data('nomefile',allegato.allegato);
		}
		else{
			this.downloadAllegatoButton.hide();
			this.downloadAllegatoPdfButton.hide();
			this.downloadAllegatoDocButton.hide();
			this.downloadAllegatoOdtButton.hide();
			this.downloadAllegatoRtfButton.hide();
		}
		$(this.element).data('obbligatorio',false);
		if(allegato){
			$(this.element).data('obbligatorio',allegato.richiesto);
		}
		var filefield = this._getLinkedFileField();
		if(setMandatoryField != undefined && typeof setMandatoryField == "function" && allegato != undefined && filefield != undefined){
			setMandatoryField(allegato.richiesto, filefield.element.id);
		}
		//TODO adeguare la grafica del controllo fileupload alla condizione di obbligatorietà specificata per l'allegato nell'attributo 'richiesto'
		//TODO adeguare la presenza dell'icona firma digitale al flag 'foRichiedefirma'
	},
	
	_displayResultsDocumentoAlbero: function(doc){
		this.schedaDinamicaButton.hide();
		if(doc && doc.id && doc.id.codice){
			this.allegatoId = -1;
			this.fkD2mtId = -1;
			this.alberoDocId = doc.id.codice;
		}
		this.infoEndoButton.hide();
		if(doc){
		if(doc.noteFrontend){
			this.infoAllegatoButton.show();
			//this.infoAllegatoButton.attr('title',allegato.noteFrontend);
			var ttip = this.infoAllegatoButton.find('.init-autocompiler-button-help');
			ttip.attr('title',doc.noteFrontend);
			ttip.tooltip({content: doc.noteFrontend});
		} 
		else if(doc.note){
			this.infoAllegatoButton.show();
			//this.infoAllegatoButton.attr('title',allegato.noteFrontend);
			var ttip = this.infoAllegatoButton.find('.init-autocompiler-button-help');
			ttip.attr('title',doc.note);
			ttip.tooltip({content: doc.note});
		}
		else{
			this.infoAllegatoButton.hide();
		}
		}
		this.urlAllegatoButton.hide();
		if(doc && doc.oggettoId){
			if(!doc.foTipodownload){
				//predispongo il donload generico
				this.downloadAllegatoButton.show();
				this.downloadAllegatoButton.data('codiceoggetto',doc.oggettoId);
				this.downloadAllegatoButton.data('idcomuneOggetto',doc.oggetto.id.idcomune);
				this.downloadAllegatoPdfButton.hide();
				this.downloadAllegatoDocButton.hide();
				this.downloadAllegatoRtfButton.hide();
				this.downloadAllegatoOdtButton.hide();
			}
			else{
				var formati = doc.foTipodownload.split(',');
				//predispongo i bottoni per il download del modulo nei formati richiesti
				this.downloadAllegatoButton.hide();
				this.downloadAllegatoPdfButton.hide();
				this.downloadAllegatoOdtButton.hide();
				this.downloadAllegatoDocButton.hide();
				this.downloadAllegatoRtfButton.hide();
				for(var i = 0; i < formati.length; i++){
					formati[i] = $.trim(formati[i]).toUpperCase();
					if(formati[i] == 'PDF'){
						this.downloadAllegatoPdfButton.show();
						this.downloadAllegatoPdfButton.data('codiceoggetto',doc.oggettoId);
						this.downloadAllegatoButton.data('idcomuneOggetto',doc.oggetto.id.idcomune);
						this.downloadAllegatoPdfButton.data('formato',formati[i]);
					}
					else if(formati[i] == 'DOC'){
						this.downloadAllegatoDocButton.show();
						this.downloadAllegatoDocButton.data('codiceoggetto',doc.oggettoId);
						this.downloadAllegatoButton.data('idcomuneOggetto',doc.oggetto.id.idcomune);
						this.downloadAllegatoDocButton.data('formato',formati[i]);
					}
					else if(formati[i] == 'OPN' || formati[i] == 'ODT'){
						this.downloadAllegatoOdtButton.show();
						this.downloadAllegatoOdtButton.data('codiceoggetto',doc.oggettoId);
						this.downloadAllegatoButton.data('idcomuneOggetto',doc.oggetto.id.idcomune);
						this.downloadAllegatoOdtButton.data('formato','ODT');
					}
					else if(formati[i] == 'RTF'){
						this.downloadAllegatoRtfButton.show();
						this.downloadAllegatoRtfButton.data('codiceoggetto',doc.oggettoId);
						this.downloadAllegatoButton.data('idcomuneOggetto',doc.oggetto.id.idcomune);
						this.downloadAllegatoRtfButton.data('formato',formati[i]);
					}
				}
			}
			//this.downloadAllegatoButton.data('nomefile',allegato.allegato);
		}
		else{
			this.downloadAllegatoButton.hide();
			this.downloadAllegatoPdfButton.hide();
			this.downloadAllegatoDocButton.hide();
			this.downloadAllegatoOdtButton.hide();
			this.downloadAllegatoRtfButton.hide();
		}
		$(this.element).data('obbligatorio',false);
		if(doc){
			$(this.element).data('obbligatorio',doc.richiesto);
		}
	},
	
	_displayResultsSchedaDinamica: function(dyn){
		
		if(!this.options.idSemanticoEndo && dyn && dyn.dyn2Modellit && dyn.id && dyn.id.codiceinventario){
			this.infoEndoButton.show();
			//L'URL per il front office .NET deve trovarsi memorizzato nella variabile AR_MS_URL 
			//altrimenti glil indirizzi per i link alle schede di dettaglio degli endo saranno valutati come relativi
			var baseAreaRiservataMsUrl = /* AR_MS_URL ? AR_MS_URL : */""; 
			//L'URL relativo per la scheda endo è memorizzata nelle option dell'oggetto 
			// var linkUrl = baseAreaRiservataMsUrl + this.options.schedaEndoUrl + dyn.id.codiceinventario;
			var linkUrl = baseAreaRiservataMsUrl + this.options.schedaEndoUrl.replace('RPL_CODICEINVENTARIO', dyn.id.codiceinventario).replace('RPL_IDCOMUNEINVENTARIO', dyn.id.idcomune) ;
			this.infoEndoButton.attr('href',linkUrl);
			this.infoEndoButton.attr('title','scheda endoprocedimento: ' + (dyn.inventarioprocedimenti.procedimento ? dyn.inventarioprocedimenti.procedimento : ""));
		} 
		else{
			this.infoEndoButton.hide();
		}
		$(this.element).data('obbligatorio',false);
		if(dyn && dyn.id && dyn.id.fkD2mtId){
			this.schedaDinamicaButton.show();
			//predispongo il bottone per l'apertura della scheda dinamica
			//var linkUrl = this.options.appContext + this.options.schedaDinamicaUrl + dyn.id.fkD2mtId;
			this.schedaDinamicaButton.data('fkD2mtId',dyn.id.fkD2mtId);
			this.fkD2mtId = dyn.id.fkD2mtId;
			this.idcomunerecord = dyn.id.idcomune;
			this.allegatoId = -1;
			this.alberoDocId = -1;
			//this.schedaDinamicaButton.attr('href',linkUrl);
			$(this.element).data('obbligatorio',!dyn.flagFacoltativa);
		} 
		else{
			this.schedaDinamicaButton.hide();
		}
		this.downloadAllegatoButton.hide();
		this.downloadAllegatoPdfButton.hide();
		this.downloadAllegatoDocButton.hide();
		this.downloadAllegatoOdtButton.hide();
		this.downloadAllegatoRtfButton.hide();
		this.infoAllegatoButton.hide();
		this.urlAllegatoButton.hide();
	},
	
	_cleanResults: function(){
		this.infoEndoButton.hide();
		this.infoEndoButton.attr('title', 'scheda endoprocedimento');
		this.downloadAllegatoButton.hide();
		this.downloadAllegatoPdfButton.hide();
		this.downloadAllegatoDocButton.hide();
		this.downloadAllegatoOdtButton.hide();
		this.downloadAllegatoRtfButton.hide();
		this.infoAllegatoButton.hide();
		this.urlAllegatoButton.hide();
		this.schedaDinamicaButton.hide();
	},
	
	//ogni allegato visualizza E[ALLEGATI.CODICEINVENTARIO]-ALLEGATI.ALLEGATO
	_buildItemValue: function(item){
		var val = "";
		if(!this.options.idSemanticoEndo){
			if(item.allegato){
				val = "E[" + item.allegato.inventarioprocedimento.id.idcomune + "|" + item.allegato.inventarioprocedimento.id.codice;
			}
			else if(item.schedaDinamica){
				val = "E[" + item.schedaDinamica.id.idcomune+ "|" + item.schedaDinamica.id.codiceinventario;
			}
			else if(item.documentoAlbero){
				val = "P[" + item.documentoAlbero.alberoproc.id.codice;
			}
			val += "]-";
		}
		if(item.allegato){
			val += item.allegato.allegato;
		}
		else if(item.schedaDinamica){
			val += item.schedaDinamica.dyn2Modellit.descrizione;
		}
		else if(item.documentoAlbero){
			val += item.documentoAlbero.descrizione;
		}
		return val;
	},
	_buildItemLabel: function(item){
		return this._buildItemValue(item);
	},
	
	_initUI: function(){
		this.wrapper = getWrapper(this.element);
		/*
		this.triggerButton = $( "<button>", {
			role: 'button',
            //text: this.options.triggerLabel,
            title: this.options.triggerLabel,
            class: "init-autocompiler-button"
        })
        */
		var endoDiv = $("<div>",{"class": "init-autocompiler-toolbar"}).prependTo( this.wrapper );
		//posizionamento link e icone intorno al controllo
		//apertura scheda informativa dell'endo
		this.infoEndoButton = $( "<a>", {
			href: '#',
			title: "scheda endoprocedimento",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-info"
        })).appendTo( endoDiv );
		//visualizzazione tooltip info dell'allegato
		this.infoAllegatoButton = $( "<a>", {
			href: '#',
			//title: "note allegato",
			//target: '_blank',
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-help",
        	id: this.element_id + "-allegato-tooltip"//,
        	//title: "note allegato"
        }).append($("<label>",{text: "(?)"}))).appendTo( endoDiv );
		$(escapeStringForCssSelector('#' + this.element_id + '-allegato-tooltip')).tooltip();
		//apertura scheda informativa dell'allegato
		this.urlAllegatoButton = $( "<a>", {
			href: '#',
			title: "visualizza la sezione modulistica",
			target: '_blank',
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-link"
        })).appendTo( endoDiv );
		//download dell'allegato (generico)
		this.downloadAllegatoButton = $( "<a>", {
			href: '#',
			title: "scarica modulo",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-download"
        })).appendTo( endoDiv );	
		//download dell'allegato (pdf)
		this.downloadAllegatoPdfButton = $( "<a>", {
			href: '#',
			title: "scarica modulo in formato PDF",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-download-pdf"
        })).appendTo( endoDiv );	
		//download dell'allegato (doc)
		this.downloadAllegatoDocButton = $( "<a>", {
			href: '#',
			title: "scarica modulo in formato MS Word",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-download-doc"
        })).appendTo( endoDiv );
		//download dell'allegato (rtf)
		this.downloadAllegatoRtfButton = $( "<a>", {
			href: '#',
			title: "scarica modulo in formato RTF",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-download-rtf"
        })).appendTo( endoDiv );	
		//download dell'allegato (odt)
		this.downloadAllegatoOdtButton = $( "<a>", {
			href: '#',
			title: "scarica modulo in formato Open Office Document",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-download-odt"
        })).appendTo( endoDiv );
		//bottone per l'apertura della scheda dinamica
		this.schedaDinamicaButton = $( "<a>", {
			href: '#',
			title: "compila il modulo online",
            "class": "init-autocompiler-button"
        }).append($("<span>", {
        	"class": "init-autocompiler-button init-autocompiler-button-scheda-dinamica"
        })).appendTo( endoDiv );	
		//creazione del div per il caricamento della dialog della scheda dell'endoprocedimento
		this.schedaEndoDiv = $('#schedaEndo');
		if(this.schedaEndoDiv.length == 0){
			this.schedaEndoDiv = $('<div>',{
				'class': "schedaEndo"
			}).appendTo($("BODY"));
		}
		this.schedaEndoDiv.dialog({
			width: 600,
			height: 500,
			title: "Dettagli dell\'endoprocedimento",
			modal: true,
			autoOpen: false,
			open: function () {
				$(this).find('#accordion').accordion({ header: "h3", autoHeight: false });
				$(this).find('tr:nth-child(2n+1)').addClass('rigaAlternata');
			}
		});
		//creazione del form e del div per la visualizzazione della scheda dinamica dell'endoprocedimento
		this.schedaDinamicaDiv = $('#schedaDinamica');
		if(this.schedaDinamicaDiv.length == 0){
			this.schedaDinamicaDiv = $('<div>',{
				'class': "schedaDinamica",
				id: 'schedaDinamica'
			}).appendTo($("BODY"));
		}
		this.errorDiv = $('#errorDiv');
		if(this.errorDiv.length == 0){
			this.errorDiv = $('<div>',{
				'class': "init-autocompiler-error-big",
				id: 'errorDiv',
				tabindex: 20
			}).appendTo(this.schedaDinamicaDiv);
		}
		this.schedaDinamicaForm = $('#schedaDynForm');
		if(this.schedaDinamicaForm.length == 0){
			this.schedaDinamicaForm = $("<form>",{
				action: '/ajaxSalvaScheda.htm',
				name: 'schedaDynForm',
				'class': "schedaDynForm",
				id: 'schedaDynForm'
			}).append($("<input>",{
				type: 'hidden',
				name: 'codiceModello',
				id: 'codiceModello'
			})).append($("<input>",{
				type: 'hidden',
				name: 'idcomunemodello',
				id: 'idcomunemodello'
			})).appendTo(this.schedaDinamicaDiv);
		}
		this.schedaDinamicaInnerDiv = $('#schedaDinamicaInner');
		if(this.schedaDinamicaInnerDiv.length == 0){
			this.schedaDinamicaInnerDiv = $('<div>',{
				'class': "schedaDinamica",
				id: 'schedaDinamicaInner'
			}).appendTo(this.schedaDinamicaForm);
		}
		//var that = this;
		this.downloadAllegatoButton.bind("click",{control: this},this._downloadModuloAllegato);
		this.downloadAllegatoPdfButton.bind("click",{control: this},this._downloadModuloAllegato);
		this.downloadAllegatoDocButton.bind("click",{control: this},this._downloadModuloAllegato);
		this.downloadAllegatoRtfButton.bind("click",{control: this},this._downloadModuloAllegato);
		this.downloadAllegatoOdtButton.bind("click",{control: this},this._downloadModuloAllegato);
		this.infoEndoButton.bind("click",{control: this},this._visualizzaSchedaEndo);
		this.schedaDinamicaButton.bind("click",{control: this},this._apriSchedaDinamica);
		this.infoAllegatoButton.bind("click",function(event){});
		this.options.filterByExactMatch = true;
		this._loadFirstItem();
		this.options.filterByExactMatch = false;
		//$(this.element).attr('readOnly','readOnly');
		/*
		 * se esiste il campo codice endo collegato specificato da this.options.idSemanticoEndo
		 * allora imposto un listener sul campo collegato che fa si che al cambio di valore del campo collegato 
		 * il valore di questo controllo sia svuotato se non corrisponde allo stesso endoprocedimento.
		 */
		var linkedField = this._getLinkedField();
		if(linkedField){
			$(linkedField.element).on("endoautocompilerchange",{control: this},this._onChangeEndoprocedimento);
		}
	},
	
	setEditable: function(editable){
		if(editable){
			$(this.element).removeAttr('readonly');
		}
		else{
			$(this.element).attr('readonly',true);
		}
		if(editable){
			$(this.element).removeClass('init-comboautocompiler-disabled');
			$(this.element).removeClass('init-endoautocompiler-disabled');
		}
		else{
			$(this.element).addClass('init-comboautocompiler-disabled');
			$(this.element).addClass('init-endoautocompiler-disabled');
		}
		var trElement = findParentElementByTagName(this.element,"TR");
		var deleteButton = undefined;
		var mandatoryFields = undefined;
		if(trElement){
			deleteButton = $(trElement).find('.cart-delete-row-button');
			mandatoryFields = $(trElement).find('.campo-cart-container');//contenitori campi xml engine
			mandatoryFields = mandatoryFields.add($(trElement).find('DIV.controllo'));//contenitori campi dinamici
			//mandatoryFields = $(trElement).find('[starid]');//tutti i campi con l'attributo starid (sia dinamici che xml)
		}
		if(deleteButton.length > 0){
			if(!editable && $(this.element).data('obbligatorio')){
				deleteButton.hide();
			}
			else{
				deleteButton.show();
			}
		}
		if(mandatoryFields && $(this.element).data('obbligatorio')){
			mandatoryFields.each(function(index, element){
				/*
				if(!$(element).hasClass('campo-cart-container-obbligatorio')){
					$(element).addClass('campo-cart-container-obbligatorio');
				}*/
				setMandatoryField(true, element);
			});
			//mandatoryFields.toggleClass('campo-cart-container-obbligatorio');
		}
		var linkedField = this._getLinkedField();
		if(linkedField){
			linkedField.setEditable(editable);
		}
	},
	
	_checkIfToProtect: function(){
		var toProtect = this.firstLoad && this.selectedItem ? true : false;
		if(toProtect){
			//controllo che non ci sia già lo stesso valore nelle righe precedenti, se presente il campo deve rimanere editablie
			var fieldInfo = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name'));
			var index = Number(fieldInfo[2]);
			if(index > -1){
				var precControl;
				var precInput;
				for(var i = 0; i < index; i++){
					precInput = $('input[starid="' + fieldInfo[0] + '_row_' + i + '"]');
					if(precInput && precInput.length > 0){
						precControl = precInput.data('allegatiendoautocompiler');
						if(precControl){
							if(this.idEndo == precControl.idEndo && this._value() == precControl._value()){
								toProtect = false;
								break;
							}
						}
						else{
							if(this._value() == precInput.attr('value')){
								toProtect = false;
								break;
							}
						}
					}
				}
			}
		}
		return toProtect;
	},
	
	_downloadModuloAllegato: function(event){
		var me = event.data.control;
		var objId = $(this).data('codiceoggetto');
		var objIdComuneId = $(this).data('idcomuneOggetto');
		if(objId){
			var newhref = me.options.appContext + "/ajax/downloadOggettoInFormato.htm?idcomuneOggetto=" + objIdComuneId + "&idOggetto=" + objId + "";
			var format = $(this).data('formato');
			if(format){
				newhref += "&formato=" + format;
			}
			window.location.href = newhref;
		}
	},
	
	_apriSchedaDinamica: function(event){

		event.preventDefault();
		var me = event.data.control;
		var schedaUrl = me.options.appContext + me.options.caricaDatiDinamiciUrl + me.fkD2mtId+ '&idcomunemodello='+me.idcomunerecord;
		//me.fkD2mtId = $(this).data('fkD2mtId');
		me.schedaDinamicaDiv.dialog({
			width: 800,
			height: 600,
			title: "Dettagli dell\'endoprocedimento",
			modal: true,
			autoOpen: false,
			buttons:[
			    {
			    	text: 'Annulla',
			    	click: function(){$(this).dialog("close");}
			    },
			    {
			    	text: 'Salva modulo',
			    	click: function(){
			    		me.salvaSchedaDinamica();
			    	}
			    }
			]
			/*,
			open: function () {
				$(this).find('#accordion').accordion({ header: "h3", autoHeight: false });
				$(this).find('tr:nth-child(2n+1)').addClass('rigaAlternata');
			}*/
		});
		
		$('#codiceModello').val(me.fkD2mtId);
		$('#idcomunemodello').val(me.idcomunerecord);
		//disattivazione Block UI
		blockUIEnabled = false;
		var jqxhr = $.ajax({
			  //url: "../nuovaistanzaschede/ajaxValidaCampo.htm?codiceModello=" + me.fkD2mtId,
			  url: schedaUrl,
			  context: me,
			  cache: false,				
			  dataType: "json",
			  success: function(data, textStatus, jqXHR) {
				  if(data && data.scheda){
					  this.schedaDinamicaInnerDiv.html(data.scheda);
					  this.errorDiv.empty();
					  this.schedaDinamicaDiv.dialog('open');
					  var innerWidth = this.schedaDinamicaInnerDiv[0].scrollWidth;
					  var outerWidth = this.schedaDinamicaDiv.width();
					  var windowWidth = $(window).width();
					  if(innerWidth > outerWidth){
						  var padding = 34;
						  this.schedaDinamicaWidth = innerWidth;
						  var dialogWidth = innerWidth + padding > windowWidth ? windowWidth -18 : innerWidth + padding;
						  this.schedaDinamicaDiv.dialog('option','width',dialogWidth);
						  this.schedaDinamicaDiv.dialog('option','position',{my: 'center', at: 'center', of: window});
					  }
					  //this._setHandlersOnLoad();
				  }
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  this.errorDiv.html(textStatus+": "+errorThrown).show();
			  }
		});	
		/*
		me.schedaDinamicaInnerDiv.load(url, function () { 
			$('#schedaDinamica').dialog('open'); 
			me._setHandlersOnLoad();
		});
		*/
	},
	
	_loadSchedaDinamicaUI: function(data, textStatus, jqXHR) {
		var schedaUrl = this.options.appContext + this.options.schedaDinamicaUrl + this.fkD2mtId + '&idcomunemodello='+me.idcomunerecord;
		var jqxhr = $.ajax({
			  //url: "../nuovaistanzaschede/ajaxValidaCampo.htm?codiceModello=" + this.fkD2mtId,
			  url: schedaUrl,
			  context: this,
			  cache: false,				
			  dataType: "json",
			  success: function(data, textStatus, jqXHR) {
				  if(data && data.scheda){
					  this.schedaDinamicaInnerDiv.html(data.scheda);
					  this.errorDiv.empty();
					  this.schedaDinamicaDiv.dialog('open');
					  var innerWidth = this.schedaDinamicaInnerDiv[0].scrollWidth;
					  var outerWidth = this.schedaDinamicaDiv.width();
					  var windowWidth = $(window).width();
					  if(innerWidth > outerWidth){
						  var padding = 34;
						  this.schedaDinamicaWidth = innerWidth;
						  var dialogWidth = innerWidth + padding > windowWidth ? windowWidth -18 : innerWidth + padding;
						  this.schedaDinamicaDiv.dialog('option','width',dialogWidth);
						  this.schedaDinamicaDiv.dialog('option','position',{my: 'center', at: 'center', of: window});
					  }
					  //this._setHandlersOnLoad();
				  }
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  this.errorDiv.html(textStatus+": "+errorThrown).show();
			  },
			  complete: function(jqXHR, textStatus){
				  //riattivazione Block UI
				  blockUIEnabled = true;
			  }
		});	
	},
	
	dynDataChanged: false,

	validaCampoDinamico: function(event){
		event.preventDefault();
		//disattivazione Block UI
		blockUIEnabled = false;
		var me = event.data.control;
		me.dataChanged=true;
		//infoWarningMsg();
		var spanErrors = $("#"+this.id+"_ERRORS");
		if(!spanErrors){
			spanErrors = $("#_debugInfoId");
		}
		var jqxhr = $.ajax({
			  //url: "../nuovaistanzaschede/ajaxValidaCampo.htm?codiceModello=" + me.fkD2mtId,
			  url: me.options.appContext + me.options.validaCampoUrl + me.fkD2mtId,
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  data: "idField=" + this.id + "&valore=" + this.value,
			  success: function(data, textStatus, jqXHR) {
				  spanErrors.html(data).show();
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  spanErrors.html(textStatus+": "+errorThrown).show();
			  },
			  complete: function(jqXHR, textStatus){
				  //riattivazione Block UI
				  blockUIEnabled = true;
			  }
		});	
	},
	
	salvaSchedaDinamica: function(event){
		//generare il PDF dalla scheda compilata e allegarlo alla domanda 
		//visualizzandolo anche nel campo file adiacente al controllo
		var url = this.options.appContext + this.options.salvaSchedaDinamicaUrl;
		//disattivazione Block UI
		blockUIEnabled = false;
		$.ajax(url,{
			data: this.schedaDinamicaForm.serialize(),
			context: this,
			type: 'POST',
			//dataType: 'html',
			success: this._salvaSchedaDinamicaCallback,
			complete: function(jqXHR, textStatus){
				//riattivazione Block UI
				blockUIEnabled = true;
			}
		});
	},
	
	_salvaSchedaDinamicaCallback: function( data, textStatus, jqXHR){
		if(data){
			if(!data.error && !data.errors){
				this.schedaDinamicaDiv.dialog('close');
				this._allegaSchedaADomandaCart();
			}
			else{
				this.errorDiv.empty();
				this._displayScheda(data, textStatus, jqXHR);
				this._displayValidationErrors(data, textStatus, jqXHR);
			}
		}
	},
	
	_allegaSchedaADomandaCart: function(){
		
		var url = this.options.appContext + this.options.allegaSchedaDinamicaUrl;
		//attivazione Block UI
		blockUIEnabled = true;
		var fieldInfo = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name')); 
		var tableElement = findParentElementByTagName(this.element, "TABLE");
		var rowDataIndex = "-1";
		if(tableElement){
			//determino il vero indice della riga da cui parte la chiamata
			//che se sono state eliminate delle righe non corrisponde al rowIndex determinato dal nome del campo
			var brothers = $(tableElement).find('input[starid^=' + escapeStringForCssSelector('[' + fieldInfo[0]) + ']');
			rowDataIndex = "" + brothers.index(this.element);
		}
		var riferimentoModulo = $('#riferimento_modulo_attivo').val();
		var idModuloVal = $('#id_modulo_attivo').val();
		var titoloModulo = $('#titolo_modulo_attivo').val();
		var idQuadro = $('#quadro_attivo').val();
		var formId = '#conferma_' + idModuloVal + '_' + idQuadro;
		var form = $(escapeStringForCssSelector(formId))
		//per qualche ragione blockUI non viene invocato automaticamente perciò lo invoco io in modo esplicito
		if($.blockUI){
			$.blockUI();
		}
		var ajaxParams = {
				modulo_attivo: titoloModulo,
				quadro_attivo: idQuadro,
				riferimento_modulo_attivo: riferimentoModulo,
				codiceModello: this.fkD2mtId,
				idcomunemodello: this.idcomunerecord,
				idSemDescAllegato: fieldInfo[0],
				rowIndex: fieldInfo[2],
				dataIndex: rowDataIndex,
				docWidth: this.schedaDinamicaWidth,
				validateDs: this.dsValidationRequired
			};
		if(this.options.idSemanticoAllegato){
			ajaxParams.idSemFileAllegato = this.options.idSemanticoAllegato;
		}
		form.ajaxSubmit({
			url: url,
			data: ajaxParams,
			context: this,
			type: 'POST',
			dataType: 'json',
			success: this._allegaSchedaCallback,
			complete: function(jqXHR, textStatus){
				if($.unblockUI){
					$.unblockUI();
				}
			}			
		});
	},
	
	_allegaSchedaCallback: function(data, textStatus, jqXHR){
		
		//visualizzo il nome del nuovo allegato nell'elenco dei files già salvati
		if(data){
			if(data.files){
				var selector = this.options.idSemanticoAllegato;
				var fieldInfo = getIdSemanticoIdAndIndexFromInputName($(this.element).attr('name'));
				var indexedPart = "";
				if(fieldInfo[2] != "-1"){
					indexedPart = "_row_" + fieldInfo[2];
				}
				selector = "[" + selector + indexedPart + "]";
				selector = escapeStringForCssSelector(selector);
				var fileInput = $("[id^=fileupload-" + selector + "]").data('blueimpFileupload');
				if(fileInput){
					fileInput.options.filesContainer.empty();
					try{
						fileInput.options.done.call("[id^=fileupload-" + selector + "]", $.Event('done'), {result: data});
					}catch(error){
						//
					}
				}
			}
			
			var msg = data.error;
			var nomeFile = "";
			var title = "Errore";
			if(!msg){
				if(data.files){
					if(data.fileName + "" != "undefined"){
						nomeFile = data.fileName;
					}
					else{
						nomeFile = data.files[data.files.length -1].name;
					}
				}
				msg = "E' stato generato il file " + nomeFile + " ed e' stato allegato alla domanda.";
				title = "Operazione Completata";
			}
			if(showDialog != undefined && typeof showDialog == "function"){
				showDialog(msg, title);
			}
			else{
				alert(msg);
			}
		}
	},
	
	aggiungiBlocco: function(event){
		//disattivazione Block UI
		blockUIEnabled = false;
		var me = event.data.control;
		var numRiga = $(this).data('numRiga');
		var indiceMolteplicita = $(this).data('indiceMolteplicita');
		//numeroRiga="+numRiga+"&indice=0&indiceMolteplicita="+indiceMolteplicita);
		var url = me.options.appContext + me.options.aggiungiBloccoUrl + "?numeroRiga=" + numRiga + "&indice=0&indiceMolteplicita=" + indiceMolteplicita;
		$.ajax(url,{
			data: me.schedaDinamicaForm.serialize(),
			context: me,
			type: 'POST',
			dataType: 'json',
			success: me._displayScheda,
			complete: function(jqXHR, textStatus){
				//riattivazione Block UI
				blockUIEnabled = true;
			}			
		});
		//me.schedaDinamicaForm.attr("action", '');
	},
	
	eliminaBlocco: function(event){
		//disattivazione Block UI
		blockUIEnabled = false;
		var me = event.data.control;
		var numRiga = $(this).data('numRiga');
		var indiceMolteplicita = $(this).data('indiceMolteplicita');
		//numeroRiga="+numRiga+"&indice=0&indiceMolteplicita="+indiceMolteplicita);
		var url = me.options.appContext + me.options.eliminaBloccoUrl + "?numeroRiga=" + numRiga + "&indice=0&indiceMolteplicita=" + indiceMolteplicita;
		$.ajax(url,{
			data: me.schedaDinamicaForm.serialize(),
			context: me,
			type: 'POST',
			dataType: 'json',
			success: me._displayScheda,
			complete: function(jqXHR, textStatus){
				//riattivazione Block UI
				blockUIEnabled = true;
			}			
		});
		//me.schedaDinamicaForm.attr("action", '');
	},
	
	settaValore: function(event){
		var me = event.data.control;
		var valoreTrue = $(this).data('valoreTrue');
		var valoreFalse = $(this).data('valoreFalse');
		var elementId = $(this).attr('id');
		var idx = elementId.indexOf("TMP_");
		if(idx == 0){
			elementId = elementId.substring(4);
			if(this.checked){
				$("#" + elementId).val(valoreTrue);
			}
			else{
				$("#" + elementId).val(valoreFalse);
			}
			$("#" + elementId).change();
			//me.validaCampoDinamico($("#" + elementId).get(0));
		}
		
	},

	_displayScheda: function( data, textStatus, jqXHR ){
		if(data && data.scheda){
			this.schedaDinamicaInnerDiv.html(data.scheda);
			//this._setHandlersOnLoad();
		}
	},
	
	_displayValidationErrors: function( data, textStatus, jqXHR ){
		var focusMe = this.errorDiv;
		if(data.error){
			var errorSpan = $('<span>',{
				style: "font-weight: bold;",
				id: 'errorSpan',
				tabindex: 21,
				text: data.error
			}).appendTo(this.errorDiv);
			focusMe = errorSpan;
			$('<br>',{}).appendTo(this.errorDiv);
		}
		if(data.errors && $.isArray(data.errors)){
			var errorList = $('<ul>',{}).appendTo(this.errorDiv);
			for(var i = 0; i < data.errors.length; i++){
				$('<li>',{
					style: "margin-left: 12px;",
					text: data.errors[i]
				}).appendTo(errorList);
			}
		}
		focusMe.focus();
	},
	
	_setHandlersOnLoad: function(){
		
		$("span.controllo [starid ^= 'FLD_']").bind('change',{control: this}, this.validaCampoDinamico);
		
		var addLinks = $("a[href *= 'aggiungiBlocco']");
		for(var i = 0; i < addLinks.length; i++){
			var a = addLinks[i];
			if(this._saveParametriBlocco(a, this.aggiungiBloccoRegExp)){
				$(a).bind('click', {control: this}, this.aggiungiBlocco);
			}
		}
		var removeLinks = $("a[href *= 'eliminaBlocco']");
		for(var i = 0; i < removeLinks.length; i++){
			var a = removeLinks[i];
			if(this._saveParametriBlocco(a, this.eliminaBloccoRegExp)){
				$(a).bind('click', {control: this}, this.eliminaBlocco);
			}
		}
		//$("a[href *= 'eliminaBlocco']").bind('click',{control: this}, this.eliminaBlocco);
		var checkBoxes = $("span.controllo [type = 'checkbox']");
		for(var i = 0; i < checkBoxes.length; i++){
			var check = checkBoxes[i];
			if(this._saveParametriCheckbox(check, this.settaValoreRegExp)){
				$(check).bind('click', {control: this}, this.settaValore);
			}
		}
		$("#salva_scheda_id").button();
		$(".help_image").tooltip();
		$("div#functions > ul > li > a").button();
		$("div.divEliminazioneBlocco > a").button();		
	},
	
	_saveParametriBlocco: function(a, functionRegExp){
		var href = $(a).attr('href');
		//functionRegExp.compile();
		var match = new RegExp(functionRegExp).exec(href);
		if(match != null && match.length == 3){
			$(a).data('numRiga',match[1]);
			$(a).data('indiceMolteplicita',match[2]);
			$(a).attr('href','javascript:void(0)');
			return true;
		}
		return false;
	},
	
	_saveParametriCheckbox: function(input, functionRegExp){
		var onclick = $(input).attr('onclick');
		var match = new RegExp(functionRegExp).exec(onclick);
		if(match != null && match.length == 3){
			$(input).data('valoreTrue', match[1]);
			$(input).data('valoreFalse', match[2]);
			$(input).attr('onclick','javascript:void(0)');
			return true;
		}
		return false;
	},
	
	_onChangeEndoprocedimento: function(event){
		var me = event.data.control;
		var you = me._getLinkedField();
		if(you.idEndo != me.idEndo){
			me._value("");
			me.selectedItem = null;
			me._loadFirstItem();
		}
	}

});
})(jQuery);


	/**
	 * Form To Form Autocompiler
	 * controllo generico per la compilazione automatica che consente di copiare 
	 * in una serie di campi del form i dati visualizzati in altri campi della stessa pagina
	 * che possono trovarsi anche in altri form. 
	 */
(function( $ ) {	
	$.widget("initui.formtoformautocompiler",{
		version: "1.0.0",
		fieldMatcherRegExp: /{field}/g,
		indexMatcherRegExp: /{index}/g,
		index: -1,
		options: {
			fieldSelectorPattern: '[starid ^= "\\[{field}\\]"]',
			fieldSelectorIndexedPattern: '[starid ^= "\\[{field}_row_{index}\\]"]',
			fieldSelectorPlainPattern: '[starid ^= "\\[{field}\\]"]',
			/*
			stringa utilizzata per costruire i selettori jQuery che servono 
			per posizionare i valori restituiti nei campi di destinazione.
			{field} verrà di volta in volta sostituito con la stringa che 
			identifica il campo di destinazione così come definita nei mappings.
			*/
			label: "Compilazione Automatica",//etichetta/tooltip del bottone che scatena la copia dei dati, da personalizzare per ciascun tipo di utilizzo
			url: ".", //URL da invocare per il recupero dei dati. Non utilizzato per questo controllo
			mappings: [],
			/*
			 * la proprietà mappings è un array associativo che associa il nome dell'attributo da cui recuperare il valore 
			 * (nel FACCT saranno utilizzati gli id semantici) all'id del campo del form in cui tale valore deve essere visualizzato 
			 */ 
			ERRORS:{
				error: "Errore: ",
				noData: "Non è stato trovato nessun dato da copiare"
			}
			/*
			 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
			 * l'id del campo del form in cui tale valore deve essere visualizzato
			 */ 
		},
		
		_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all",
		
		_create: function() {
			this.element.addClass(this._CSS_CLASSES);
			this.element_id = this.element.attr("starid");
			var suffixIndex = this.element_id.indexOf(INDEXED_FIELDNAME_SEPARATOR);
			if(suffixIndex > -1){
				var endIndex = this.element_id.indexOf(']');
				if (endIndex > 0) {
					var indexSubStr = this.element_id.substring(suffixIndex
							+ INDEXED_FIELDNAME_SEPARATOR.length, endIndex);
					var tmpIndex = parseInt(indexSubStr);
					if(!isNaN(tmpIndex)){
						this.index = tmpIndex;
					}
				}
			}
			var fieldSelector;
			//applico lo stesso stile anche ai campi collegati per evidenziarli rispetto agli altri
			for ( var attrName in this.options.mappings) {
				var linkedFieldId = this.options.mappings[attrName];
				var sel = this._getLinkedField(linkedFieldId);
				sel.addClass(this._CSS_CLASSES);
			}
			this.wrapper = getWrapper(this.element);
			//creo il bottone che scatena la copia dei dati e il suo gestore di evento click
			this.triggerButton = $( "<a>", {
				href: '#',
				title: this.options.label,
				//text: this.options.triggerLabel,
                "class": "init-autocompiler-button"
            })
            .append($("<span>", {
            	"class": "init-autocompiler-button init-autocompiler-button-copy"
            }))
            .appendTo( this.wrapper );
			//span nascosto per la visualizzazione die ventuali messaggi di errore
			$("<span>",{
				id: "#" + this.element_id + "_error_message",
				"class": 'init-autocompiler-error'
			}).appendTo( this.wrapper ).hide();
			this.triggerButton.bind("click",{control: this},this._copyData);/**/
		},

		_destroy: function() {
			this.element.removeClass( this._CSS_CLASSES );
			for ( var attrName in this.options.mappings) {
				var linkedFieldId = this.options.mappings[attrName];
				var sel = this._getLinkedField(linkedFieldId);
				sel.removeClass(this._CSS_CLASSES);
			}
			this.triggerButton.remove();
			$(this.element).unwrap(); 
		},
		
		_copyData: function(event){
			var autocomp = event.data.control;
			for ( var fromName in autocomp.options.mappings) {
				var toName = autocomp.options.mappings[fromName];
				var from = autocomp._getFromField(fromName);
				if(from.length > 0){
					var to = autocomp._getFromField(toName);// autocomp._getLinkedField(toName);
					if(to.length > 0){
						var value = from.val();
						if(!value)value = "";
						setFieldValues(to,value,true);
						//to.val(value);
					}
				}
			}
			return false;
		},
		
		_getLinkedField: function(idSemantico){
			var jqField = $();
			var fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp, escapeStringForCssSelector(idSemantico));
			if (this.index > -1) {
				fieldSelector = fieldSelector.replace(this.indexMatcherRegExp, "" + this.index);
				jqField = $(fieldSelector);
			}
			return jqField;
		},
		
		_getFromField: function(idSemantico){
			var fieldSelector = this.options.fieldSelectorPlainPattern.replace(this.fieldMatcherRegExp, escapeStringForCssSelector(idSemantico));
			var jqField = $(fieldSelector);
			if (jqField.length == 0 && this.index > -1) {
				fieldSelector = this.options.fieldSelectorIndexedPattern.replace(this.fieldMatcherRegExp,escapeStringForCssSelector(idSemantico));
				fieldSelector = fieldSelector.replace(this.indexMatcherRegExp, "" + this.index);
				jqField = jqField.add($(fieldSelector));
			}
			return jqField;
		},
		
	});
})(jQuery);


/**
 * Basic Autocompiler
 * controllo generico per la compilazione automatica che consente di compilare i campi di destinazione 
 * con i dati recuperati dagli attributi di oggetti java restituiti da un AjaxController
 */
(function( $ ) {	
$.widget("initui.basicautocompiler",{
	version: "1.0.0",
	fieldMatcherRegExp: /{field}/g,
	options: {
		/*
		stringa utilizzata per costruire i selettori jQuery che servono 
		per posizionare i valori restituiti nei campi di destinazione.
		{field} verrà di volta in volta sostituito con la stringa che 
		identifica il campo di destinazione così come definita nei mappings.
		Il valore di default prevede la selezione per id del tag html.
		*/
		fieldSelectorPattern: '[starid ^= "\\[{field}\\]"]',
		label: "Compilazione Automatica",//etichetta del bottone che scatena la copia dei dati, da personalizzare per ciascun tipo di utilizzo
		triggerButtonClass: "",//classe CSS per la visualizzazione dell'icona. Personalizzabile nelle sottoclassi
		url: ".", //URL da invocare per il recupero dei dati. Di default viene invocato
		idComune: '',
		mappings: [],
		/*
		 * la proprietà mappings è un array associativo che associa il nome dell'attributo da cui recuperare il valore 
		 * (nel FACCT saranno utilizzati gli id semantici) all'id del campo del form in cui tale valore deve essere visualizzato 
		 */ 
		ERRORS:{
			error: "Errore: ",
			noData: "Impossibile recuperare l'informazione desiderata"
		}
		/*
		 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
		 * l'id del campo del form in cui tale valore deve essere visualizzato
		 */ 
	},
	
	_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all",
	
	_create: function() {
		this.element.addClass(this._CSS_CLASSES);
		this.element_id = this.element.attr("starid");
		var fieldSelector;
		//applico lo stesso stile anche ai campi collegati per evidenziarli rispetto agli altri
		for ( var attrName in this.options.mappings) {
			var linkedFieldId = this.options.mappings[attrName];
			linkedFieldId = escapeStringForCssSelector(linkedFieldId);
			fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
			var sel = $(fieldSelector);
			sel.addClass(this._CSS_CLASSES);
		}
		this.wrapper = getWrapper(this.element);
		//creo il bottone che scatena la copia dei dati e il suo gestore di evento click
		this.triggerButton = $( "<a>", {
			href: '#',
			title: this.options.label,
			//text: this.options.triggerLabel,
            "class": "init-autocompiler-button"
        })
        .append($("<span>", {
        	"class": "init-autocompiler-button " + this.options.triggerButtonClass
        }))
        .appendTo( this.wrapper );
		//span nascosto per la visualizzazione die ventuali messaggi di errore
		$("<span>",{
			id: "#" + this.element_id + "_error_message",
			"class": 'init-autocompiler-error'
		}).appendTo( this.wrapper ).hide();
		this.triggerButton.bind("click",{control: this},this._searchData);/**/
	},

	_destroy: function() {
		this.element.removeClass( this._CSS_CLASSES );
		for ( var attrName in this.options.mappings) {
			var linkedFieldId = this.options.mappings[attrName];
			fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
			var sel = $(fieldSelector);
			sel.removeClass(this._CSS_CLASSES);
		}
		this.triggerButton.remove();
		$(this.element).unwrap(); 
	},
	
	_searchData: function(event){
		event.data.control._hideError();
		/*
		 * disattivazione Block UI per la chiamata ajax: nella pagina del FACCT della modulistica 
		 * il Block UI e' attivato solo quando la vartiabile globale blockUIEnabled == true
		 */
		blockUIEnabled = false;
		ajaxOpts = {
			success: event.data.control._searchDataCallback,
			data: {idComune: event.data.control.options.idComune},
			error: event.data.control._searchDataErrorCallback,
			type: "POST",
			context: event.data.control
		};
		$.ajax(event.data.control.options.url,ajaxOpts);
	},
	
	_searchDataCallback: function(data, textStatus, jqXHR){
		//riattivazione Block UI
		blockUIEnabled = true;
		if(data){
			if(data.error){
				this._displayError(data.error);
			}
			else if(data.data != undefined){
				this._displayResults(data);
			}
			else{
				this._displayError(this.options.ERRORS.noData);
			}
		}
		else{
			this._displayError(this.options.ERRORS.noData);
		}
	},
	
	_searchDataErrorCallback: function(data, textStatus, jqXHR){
		//riattivazione Block UI
		blockUIEnabled = true;
		var errMsg = this.options.ERRORS.serverError;
		//TODO cercare di appendere al messaggio una descrizione dell'errore quando è possibile recuperarla
		this._displayError(errMsg);
	},
	
	_displayResults: function(data){
		//funzione da ridefinire nelle sottoclassi
	},
			
	_displayError: function(errorMsg){
		var span = $(escapeStringForCssSelector("#" + this.element_id + "_error_message"));
		span.text(errorMsg);
		span.show();
	},
	
	_hideError: function(){
		var span = $(escapeStringForCssSelector("#" + this.element_id + "_error_message"));
		span.text("");
		span.hide();			
	}
	
});
})(jQuery);

/**
 * Implementazione della ricerca del numero degli allegati
 */
(function( $ ) {	
$.widget("initui.contaallegati",jQuery.initui.basicautocompiler,{
	options: {
		label: "Calcola Numero Allegati",//etichetta del bottone che attiva il conteggio automatico degli allegati
		triggerButtonClass: "init-autocompiler-button-count"//classe CSS per la visualizzazione dell'icona. 
	},
	
	/*
	 * in questo tipo di controllo autocompiler viene restituito un unico valore scalare 
	 * che viene recuperato indipendentemente dal valore di configurazione di <result-attribute-name>
	 */
	_displayResults: function(data){
		for ( var attrName in this.options.mappings) {
			var destField = this.options.mappings[attrName];
			fieldSelector = escapeStringForCssSelector(fieldSelector);
			var fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,destField);
			//$(fieldSelector).val(data.data);
			setFieldValues($(fieldSelector),data.data,true);
			//si esce alla prima iterazione perchè questa ricerca prevede la restituzione di un solo valore
			break;
		}
	}
});
})(jQuery);

/**
 * Implementazione della ricerca dei dati anagrafici dell'utente di sessione
 */
(function( $ ) {	
$.widget("initui.cercautente",jQuery.initui.basicautocompiler,{
	options: {
		label: "Copia dati utente",//etichetta del bottone che attiva la copia dei dati dell'utente di sessione
		triggerButtonClass: "init-autocompiler-button-user"//classe CSS per la visualizzazione dell'icona. 
	},
	
	_displayResults: function(data){
		var jsonRecord;
		if(data.data.length > 0){
			jsonRecord = data.data[0];
		}
		if(jsonRecord){
			var attrValue;
			var fieldSelector;
			for ( var attr in this.options.mappings) {
				fieldSelector = this.options.mappings[attr];
				fieldSelector = escapeStringForCssSelector(fieldSelector);
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,fieldSelector);
				attrValue = getNestedProperty(attr, jsonRecord);
				if(attrValue == undefined || attrValue == null){
					attrValue = "";
				}
				var destField = $(fieldSelector);
				if(destField.attr('name') && destField.attr('name').indexOf("_DIS_") == 0){
					destField.data('value-storage', attrValue);
				}
				else{
					//destField.val(attrValue);
					setFieldValues(destField,attrValue,true);
				}
			}
		}
		else{
			this._displayError(this.options.ERRORS.noData);
		}
	}
});
})(jQuery);

/**
 * nasconde la select pratica_tipologia
 * mette come valore di default comunicazione
 * scrive come label comunicazione
 */
(function( $ ) {	
$.widget("initui.std0praticatipologia",jQuery.initui.basicautocompiler,{
	options: {
	},

	_create: function() {
		this.element_id = this.element.attr("id");
		this.wrapper = getWrapper(this.element);
		var elIdEscaped = escapeStringForCssSelector( '#' + this.element_id );
		if($(elIdEscaped)){
			$(elIdEscaped+' option[value="comunicazione"]').attr("selected",true);
			$(elIdEscaped).hide();
			var labelDiv = this.wrapper.parent().prev();
			labelDiv.hide();
			//this.wrapper.append("<b>comunicazione</b>");
		}
	}
	
});
})(jQuery);

/**
 * nasconde la select pratica_tipologia
 * mette come valore di default comunicazione
 * scrive come label comunicazione
 */
(function( $ ) {	
$.widget("initui.std0praticaazione",jQuery.initui.basicautocompiler,{
	options: {
		
	},

	_create: function() {
		this.element_id = this.element.attr("id");
		this.wrapper = getWrapper(this.element);
		//l'opzione di configurazione dell'autocompiler viene impostatata nell'inizializzazione dei valori di default della domanda
		//solo nel caso in cui si tratti di modulistica dinamica. 
		//In questo caso ci si aspetta che il campo associato all'id semantico PRATICA_AZIONE sia di tipo testo 
		if(this.options.praticaAzione != undefined){
			var elIdEscaped = escapeStringForCssSelector( '#' + this.element_id );
			var el = $(elIdEscaped);
			if(el){
				//al caricamento l'id semantico contiene la descrizione da visualizzare e non il valore da trasmettere
				//la descrizione deve essere messa in una etichetta e il valore da trasmettee in un campo hidden
				var descAzione = el.val();
				el.remove();
				this.wrapper.append("<input type='hidden' id='" + this.element_id + "' name='" + el.attr('name') + "' value='" + this.options.praticaAzione + "'/>");
				this.wrapper.append("<b>" + descAzione + "</b>");
			}
		}
	}
	
});
})(jQuery);

/**
 * utilizzato per forzare l'aspetto dei campi a cui è applicato come obbligatori o non obbligatori.
 * N.B.: non ha alcun effetto sulle effettive validazioni che vengono svolte lato server.
 */
(function( $ ) {	
	$.widget("initui.displayAsMandatory",{
		element_id: '',
		options: {
			mandatory: "false"
		},

		_create: function() {
			var ctrl = $(this.element);
			this.element_id = this.element.attr('starid');
			if(setMandatoryField != undefined && typeof setMandatoryField == "function"){
				var boolMandatory = (this.options.mandatory != undefined && this.options.mandatory.toUpperCase() === "TRUE") ? true: false;
				setMandatoryField(boolMandatory, this.element_id);
			}
		}
		
	});
})(jQuery);

/**
 * Single Row Autocompiler
 * controllo generico per la compilazione automatica che prevede
 * l'apertura di una popup per l'immissione del criterio di ricerca.
 * I campi collegati vengono compilati solo se viene undividuato 
 * un solo record come risultato della ricerca, in caso contrario viene visualizzato un messaggio di errore
 */
(function( $ ) {
	$.widget("initui.ajaxdefaultvalue",{
		version: "1.0.0",
		fieldMatcherRegExp: /{field}/g,
		options: {
			/*
			stringa utilizzata per costruire i selettori jQuery che servono 
			per posizionare i valori restituiti nei campi di destinazione.
			{field} verrà di volta in volta sostituito con la stringa che 
			identifica il campo di destinazione così come definita nei mappings.
			Il valore di default prevede la selezione per id del tag html.
			*/
			fieldSelectorPattern: '[starid *= "\\[{field}\\]"]',
			
			url: ".", //URL invocato per il recupero dei dati. E' invocato via AJAX e deve restituire i dati in formato JSON
			idComune: "",
			mappings: [],
			disable: false,
			/*
			 * la proprietà mappings è un array associativo che associa al nome dell'attributo da cui recuperare il valore 
			 * l'id del campo del form in cui tale valore deve essere visualizzato
			 */ 
			ERRORS:{
				error: "Errore: ",
				serverError: "si è verificato un errore nella chiamata al server."
			}
		},
		
		_CSS_CLASSES: "init-autocompiler ui-widget init-widget-content ui-corner-all",
				
		_create: function() {
			this.element.addClass(this._CSS_CLASSES);
			this.element_id = this.element.attr("id");
			var fieldSelector;
			//applico lo stesso stile anche ai campi collegati per evidenziarli rispetto agli altri
			for ( var attrName in this.options.mappings) {
				var linkedFieldId = this.options.mappings[attrName];
				fieldSelector = escapeStringForCssSelector(fieldSelector);
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				sel.addClass(this._CSS_CLASSES);
				if(isTrue(this.options.disable)){
					sel.prop('readonly',true);
				}
			}
			this.wrapper = getWrapper(this.element);
			this._searchData();
		},

		_destroy: function() {
			this.element.removeClass( this._CSS_CLASSES );
			for ( var attrName in this.options.mappings) {
				var linkedFieldId = this.options.mappings[attrName];
				fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,linkedFieldId);
				var sel = $(fieldSelector);
				sel.removeClass(this._CSS_CLASSES);
			}
			this.triggerButton.remove();
			$(this.element).unwrap(); 
		},
		
		_searchData: function(event){
			/*
			 * disattivazione Block UI per la chiamata ajax: nella pagina del FACCT della modulistica 
			 * il Block UI e' attivato solo quando la variabile globale blockUIEnabled == true
			 */
			blockUIEnabled = false;
			ajaxOpts = {
				success: this._searchDataCallback,
				error: this._searchDataErrorCallback,
				data: {
					idComune: this.options.idComune
				},
				type: "POST",
				context: this
			};
			$.ajax(this.options.url,ajaxOpts);
		},
		
		_searchDataCallback: function(data, textStatus, jqXHR){
			//riattivazione Block UI
			blockUIEnabled = true;
			if(data){
				if(data.error){
					this._displayError(data.error);
				}
				else {
					this._displayResults(data);
				}
			}
			else{
				this._displayError(this.options.ERRORS.noData);
			}
		},
		
		_searchDataErrorCallback: function(data, textStatus, jqXHR){
			//riattivazione Block UI
			blockUIEnabled = true;
			var errMsg = this.options.ERRORS.serverError;
			//TODO cercare di appendere al messaggio una descrizione dell'errore quando è possibile recuperarla
			this._displayError(errMsg);
		},
		
		_displayResults: function(jsonRecord){
			if(jsonRecord){
				var attrValue;
				var fieldSelector;
				for ( var attr in this.options.mappings) {
					fieldSelector = this.options.mappings[attr];
					fieldSelector = escapeStringForCssSelector(fieldSelector);
					fieldSelector = this.options.fieldSelectorPattern.replace(this.fieldMatcherRegExp,fieldSelector);
					attrValue = getNestedProperty(attr, jsonRecord);
					if(attrValue == undefined || attrValue == null){
						attrValue = "";
					}
					var destField = $(fieldSelector);
					if(destField.attr('name') && destField.attr('name').indexOf("_DIS_") == 0){
						destField.data('value-storage', attrValue);
					}
					else{
						//destField.val(attrValue);
						setFieldValues(destField,attrValue,true);
					}
				}
			}
		},
		
		_displayError: function(errorMsg){
			var span = $(escapeStringForCssSelector("#" + this.element_id + "_error_message"));
			span.text(errorMsg);
			span.show();
		},
		
		_hideError: function(){
			var span = $(escapeStringForCssSelector("#" + this.element_id + "_error_message"));
			span.text("");
			span.hide();			
		}
		
	});
})(jQuery);


getWrapper = function(element){
	var elementId = element.attr('starid');
	var wrapper = $(escapeStringForCssSelector( '#' + elementId + '_wrapper'));
	if(wrapper.length == 0){
		element.wrap("<div id='" + elementId + "_wrapper' class='init-autocompiler-wrapper'></div>");
		wrapper = $(escapeStringForCssSelector( '#' + elementId + '_wrapper'));
	}
	return wrapper;
};

getNestedProperty = function(propName, from){
	var value;
	if(propName){
		var propPath = propName.split('.');
		value = from[propPath[0]];
		if(value != null && value != undefined && propPath.length > 1){
			propPath = propPath.slice(1);
			value = getNestedProperty(propPath.join('.'),value);
		}
	}
	return value;
};


var FIELD_NAME_PARSING_REGEX = /^\[(.+)\](.+)/;
//funzione di utilità per recuperare id semantico e indice di riga dal nome di un campo della modulistica CART
getIdSemanticoIdAndIndexFromInputName = function(inputName){
	var retVal = [inputName,"","-1"];
	var matches = FIELD_NAME_PARSING_REGEX.exec(inputName);
	if (matches) {
		var idSemPart = matches[1];
		retVal[1] = matches[2];
		if ("" + INDEXING_SUFFIX == "undefined") {
			INDEXING_SUFFIX = "_row_";
		}
		var sepIndex = idSemPart.indexOf(INDEXING_SUFFIX);
		if (sepIndex > -1) {
			retVal[0] = idSemPart.substring(0, sepIndex);
			var indexIndex = 2;
			retVal[indexIndex] = idSemPart.substring(sepIndex + INDEXING_SUFFIX.length);
			var toSplit = retVal[indexIndex];
			sepIndex = toSplit.indexOf("_");
			while (sepIndex > -1) {
				retVal[indexIndex] = toSplit.substring(0, sepIndex);
				indexIndex++;
				toSplit = toSplit.substring(sepIndex + 1);
				retVal[indexIndex] = toSplit;
				sepIndex = toSplit.indexOf("_");
			}
		}
		else{
			retVal[0] = idSemPart;
		}
	}
	return retVal;
};

//funzione di utilità per identificare l'elemento html parent rispetto a 'element' cha ha tag di tipo 'tagName'
findParentElementByTagName = function(element, tagName){
	
	var retParent;
	if(element && tagName){
		var field = $(element).parent();
		element = field.length > 0 ? field.get(0) : null;
		while(field && field.length > 0 && element && element.tagName != tagName.toUpperCase()){
			field = field.parent();
			element = field.length > 0 ? field.get(0) : null;
		}
		if(element && element.tagName == tagName.toUpperCase()){
			retParent = element;
		}
	}
	return retParent;
}


/* FUNZIONI PER LA COMPATIBILITA' DELLE SCHEDE DINAMICHE DEL FRONT-OFFICE JAVA ALL'INTERNO DEI MODULI CART */
var dataChanged = false;

function validaCampo(obj){
	dataChanged=true;
	/*
	//infoWarningMsg();
	var spanErrors = $("#"+obj.id+"_ERRORS");
	if(!spanErrors){
		spanErrors = $("#_debugInfoId");
	}
	var jqxhr = $.ajax({
		  url: "../nuovaistanzaschede/ajaxValidaCampo.htm?codiceModello=${schedaH.scheda.codice}",
		  context: document.body,
		  cache: false,				
		  dataType: "html",
		  data: "idField=" + obj.id + "&valore=" + obj.value,
		  success: function(data, textStatus, jqXHR) {
			  spanErrors.html(data).show();
		  },
		  error: function(jqXHR, textStatus, errorThrown){
			  spanErrors.html(textStatus+": "+errorThrown).show();
		  }	
	});	
	*/
}

function changeData(){
	if(dataChanged){
		if( confirm("<fmt:message key='alert.salvare-i-dati' />")){
			dataChanged = false;
			return true;
		}else{
			return false;
		}
	}
	return true;
}

function eliminaBlocco(numRiga, indiceMolteplicita){
	/*
	if(changeData()){
		$("#schedaDynForm").attr("action","../nuovaistanzaschede/eliminaBlocco.htm?numeroRiga="+numRiga+"&indice=0&indiceMolteplicita="+indiceMolteplicita);
		$("#schedaDynForm").submit();
	}
	*/
	$(this).data('numRiga', numRiga);
	$(this).data('indiceMolteplicita', indiceMolteplicita);
}

function aggiungiBlocco(numRiga,indiceMolteplicita){
	/*
	if(changeData()){
		$("#schedaDynForm").attr("action","../nuovaistanzaschede/aggiungiBlocco.htm?numeroRiga="+numRiga+"&indice=0&indiceMolteplicita="+indiceMolteplicita);
		$("#schedaDynForm").submit();
	}
	*/
	$(this).data('numRiga', numRiga);
	$(this).data('indiceMolteplicita', indiceMolteplicita);
}

$.fn.copyAllAttributes = function(sourceElement) {

    // 'that' contains a pointer to the destination element
    var that = this;

    // Place holder for all attributes
    var allAttributes = ($(sourceElement) && $(sourceElement).length > 0) ?
        $(sourceElement).prop("attributes") : null;

    // Iterate through attributes and add    
    if (allAttributes && $(that) && $(that).length == 1) {
        $.each(allAttributes, function() {
            // Ensure that class names are not copied but rather added
            if (this.name == "class") {
                $(that).addClass(this.value);
            } else {
                that.attr(this.name, this.value);
            }

        });
    }

    return that;
}; 

function isTrue(value){
	if(value != undefined){
		if(value === true || value.toString().toLowerCase() === 'true' || value.toString().toLowerCase() === '1'){
			return true;
		}
		else{
			return false;
		}
	}
	else{
		return false;
	}
}

/*
function infoWarningMsg(){
	$("#change_data_status_msg").text("<< <fmt:message key='label.salvare-i-dati-della-scheda' />");
	$("#change_data_status_msg").show();
	
}
*/
