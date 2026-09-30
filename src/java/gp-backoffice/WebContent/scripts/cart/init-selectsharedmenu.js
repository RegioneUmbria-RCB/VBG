/**
 * Controllo derivato da jQuery UI selectmenu con la differenza che 
 * i dati visualizzati nell'elenco a tendina non sono presi dalle options della select 
 * su cui si costruisce il controllo, ma da un'array dim coppie chiave/valore 
 * memorizzato in una variabile globale a livello di pagina.
 * Tutte le istanze del controllo presenti nella stessa pagina condividono lo stesso
 * elenco di valori da visualizzare ma ciascun controllo visualizzerà solo i valori che non sono
 * ancora stati selezionati in nessuno degli altri controllo nella medesima pagina.
 */
(function( $ ) {	
	$.widget("initui.selectsharedmenu",jQuery.ui.selectmenu,{
		version: "1.0.0",
		options: {
			/* 
			 * Assegnare un riferimento all'array che contiene i valori da visualizzare nel menu a tendina.
			 * Tutti controlli che saranno creati con il riferimento allo stesso array condivideranno la stessa lista di elementi.
			 * Se questa option non è valorizzata il controllo si comporta esattamente come jquery.ui.selectmenu,
			 * ossia visualizza le option della select su cui viene costruito il controllo stesso.
			 */
			sharedItems: null,
			//nome della proprietà per accedere al valore (valore trasmesso) in ciascun elemento dell'array 
			valueProperty: "codiceOggetto",
			//nome della proprietà per accedere alla label (valore visualizzato) in ciascun elemento dell'array
			labelProperty: "nomeFile"
		},
		_refreshMenu: function() {
			this.menu.empty();

			if(this.options.sharedItems){
				this._loadSharedItems();
			}
			var item,
				options = this.element.find( "option" );

			if ( !options.length ) {
				return;
			}
			this._parseOptions( options );
			this._renderMenu( this.menu, this.items );

			this.menuInstance.refresh();
			this.menuItems = this.menu.find( "li" ).not( ".ui-selectmenu-optgroup" );

			item = this._getSelectedItem();

			// Update the menu to have the correct item focused
			this.menuInstance.focus( null, item );
			this._setAria( item.data( "ui-selectmenu-item" ) );

			// Set disabled state
			this._setOption( "disabled", this.element.prop( "disabled" ) );
		},
		_loadSharedItems: function(){
			var that = this;
			//svuoto la lista delle <option> nella select sottostante
			var prevSelectedIndex = this.element[0].selectedIndex;
			this.element.html("");
			var newOption = $('<option></option>').attr('value', '').text("");
			this.element.append(newOption);
			//carico le nuove <option> dall'array this.sharedItems
			$.each( this.options.sharedItems, function( index, item ) {
				newOption = $('<option></option>').attr('value', item[that.options.valueProperty]).text(item[that.options.labelProperty]);
				/*
				{
					value: item[that.options.valueProperty],
					text: item[that.options.labelProperty],
					disabled: item.selected ? "disabled" : ""
				}
				*/
				if(item.selected && item.selected != that.element[0].id){
					newOption.attr("disabled","disabled");
				}
				if(index + 1 == prevSelectedIndex){
					newOption.attr("selected",true);
				}
				that.element.append(newOption);
			});
		},
		_select: function( item, event ) {
			var oldIndex = this.element[ 0 ].selectedIndex;
			// Change native select element
			this.element[ 0 ].selectedIndex = item.index;
			this._setText( this.buttonText, item.label );
			this._setAria( item );
			var changed = item.index !== oldIndex;
			this._trigger( "select", event, { item: item } );

			if ( changed ) {
				//reimposto la proprietà selected a false nell'elemento condiviso precedentemente selezionato
				var oldSelectedSItem = this.options.sharedItems[oldIndex -1];
				if(oldSelectedSItem){
					oldSelectedSItem.selected = false;
				}
				//imposto la proprietà selected a true nell'elemento condiviso attualmente selezionato
				var selectedSItem = this.options.sharedItems[item.index -1];
				if(selectedSItem){
					selectedSItem.selected = this.element[0].id;
				}
				this._trigger( "change", event, { item: item } );
			}

			this.close( event );
		},
		open: function( event ) {
			if ( this.options.disabled ) {
				return;
			}

			/*
			 * il menu viene ridisegnato tutte le volte per tener conoto di elementi selezionati 
			 * in altri controlli che condividono le stesse <options>
			 */
			this._refreshMenu();

			this.isOpen = true;
			this._toggleAttr();
			this._resizeMenu();
			this._position();

			this._on( this.document, this._documentClick );

			this._trigger( "open", event );
		},


	});
})(jQuery);
