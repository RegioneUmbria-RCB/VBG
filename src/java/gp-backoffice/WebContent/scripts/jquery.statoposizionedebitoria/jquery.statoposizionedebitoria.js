/**

 * 
 */

(function($) {
//////////PLUGIN BEGIN /////////
	
	
	function _getStatoPosizioneDebitoria($el, options){
		
		var el = $el;
		var id = el.data('id');
		var mostratesto = el.data('mostratesto');
		
		const defaultSettings = {
			urlStatoBreve: "../json/getStatoPosizioneDebitoria.htm",
			urlStatoDettaglio: "../json/getDettaglioStatoPosizioneDebitoria.htm"
		};
		
		const pluginSettings = {
			...defaultSettings,
			...options
		};
		
		var STATOENUM = {
				
				NON_DEFINITO: -1,
				INCORSO : 0,
				CONCLUSO_CON_ESITO_POSITIVO: 200,
				CONCLUSO_CON_ESITO_NEGATIVO: 500
		};
		
		reload();
		
		this.reload = reload;
		
		
		function getStatoIconClass(codiceStato){
			var statoiconclass = {};
			if(codiceStato == STATOENUM.INCORSO){
				 statoiconclass = {
						icon:"fa fa-clock-o",
						color: "#F3C716",
						classname: "warning"
				}
			}
			else if(codiceStato == STATOENUM.CONCLUSO_CON_ESITO_POSITIVO){
				statoiconclass = {
						icon:"fa fa-check",
						color: "#42A548",
						classname: "success"
				}
			}
			else if(codiceStato == STATOENUM.CONCLUSO_CON_ESITO_NEGATIVO){
				statoiconclass = {
						icon:"fa fa-times-circle-o",
						color: "#DF001E",
						classname: "critical"
				}
			}
			else{
				statoiconclass = {
						icon:"fa fa-times-circle-o",
						color: "gray",
						classname: "default"
				}
			}
			
			return statoiconclass;
		}
		
		function reload() {
			$.ajax({
				url : pluginSettings.urlStatoBreve, //"../json/getStatoPosizioneDebitoria.htm",
				data : {
					id : id
				},
				method : 'POST',
				type : 'POST', // For jQuery < 1.9
				cache : false,
				dataType : "json",
				success : function(data) {
					
					var statoiconclass = getStatoIconClass(data.stato.codice_stato);
					
					var iconastato = "<i class='dettaglio-posizioni-debitorie "+statoiconclass.icon+
										"' data-id-posizione-debitoria='"+data.stato.id_posizione_debitoria+
										"' data-id='"+data.stato.id+"'></i>";
					if(mostratesto){
						iconastato +="<span class='testo-stato-posizione-debitoria'>"+data.stato.stato+"</span>";
						$(el).addClass("contiene-testo");
						
					}else{
						$(el).addClass("non-contiene-testo")
					}
					$(el).attr("title","Stato del pagamento: "+data.stato.stato);
					$(el).addClass(statoiconclass.classname);
					$(el).addClass("dettaglio-posizione-debitoria");
					$(el).html(iconastato);
					$(el).click(dettaglioPosizioneDebitoria);
				},
				error : gestisciErrore
			});
		}
		
		function gestisciErrore(jqXHR, textStatus, errorThrown) {
			console.error([ jqXHR, textStatus, errorThrown ]);
			// TODO Mostrare a video
			alert("Si è verificato un errore durante l'escuzione.");

		}
		
		function dettaglioPosizioneDebitoria(){
			
			var id = $(this).data('id');
			var el = $(this); 
			$.ajax({
				url : pluginSettings.urlStatoDettaglio, //"../json/getDettaglioStatoPosizioneDebitoria.htm",
				data : {
					id : id
				},
				method : 'POST',
				type : 'POST', // For jQuery < 1.9
				cache : false,
				dataType : "json",
				success : function(data) {
					var statoiconclass = getStatoIconClass(data.stato.codice_stato);
					
					var content = "<div class='popup-form' style='background-color:"+ statoiconclass.color +"'>"+
									"<div><label>Id posizione</label><div class='read-only'>"+data.stato.id_posizione_debitoria+"</div></div>"+
									"<div><label>Data stato</label><div class='read-only'>"+data.stato.data_ultimo_stato+"</div></div>"+
									"<div><label>Stato</label><div class='read-only'>"+data.stato.stato+"<br/>"+
									data.stato.codice_stato_nodo +
									"</div></div>"+													
								"</div>";
					$(content).appendTo(el).dialog({title:'Dettaglio posizione debitoria', modal: true});
					
				},
				error : gestisciErrore
			});
		}
		
	}
	
////////// PLUGIN END /////////
	
	$.fn.statoPosizioneDebitoria = function (method) {
		
		function init(options) {
			return this.each(function (index, element) {
				
				var tipo = typeof options;

				
				const el = $(element);
				const instance = el.data('statoPosizioneDebitoria');
				
				if( tipo == "string" && options === 'reload') {
					if (!instance) {
						throw 'oggetto non inizializzato';
					}
					
					instance.reload();
					
					return;
				} 
				
				if (!instance) {
					const cls = new _getStatoPosizioneDebitoria(el, options);
					el.data('statoPosizioneDebitoria', cls);
				}
			
				
			});			
		}
		
		return init.apply(this, arguments);
	}

	
}(jQuery));
