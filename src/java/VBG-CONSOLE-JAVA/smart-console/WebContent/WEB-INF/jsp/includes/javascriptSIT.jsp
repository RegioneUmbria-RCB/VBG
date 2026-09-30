<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<c:set var="VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO)%></c:set>
<c:set var="_PAGINA_PROVENIENZA">ISTANZESTRADARIO</c:set>
<c:if test="${not empty param.PAGINA_PROVENIENZA }">
	<c:set var="_PAGINA_PROVENIENZA">${param.PAGINA_PROVENIENZA}</c:set>
</c:if>
<c:set var="_CODICEISTANZA"></c:set>
<c:if test="${not empty param.CODICEISTANZA }">
	<c:set var="_CODICEISTANZA">${param.CODICEISTANZA }</c:set>
</c:if>



<script type="text/javascript">

		function listaValoriCampoSIT(obj, idField, qs, isClick){
			var timestamp = new Date().getTime();
			var call_msg = new Ajax.Request('<%=request.getContextPath()%>/istanzestradario/ajaxValidaSIT.htm?isClick='+isClick+'&provenienza=${_PAGINA_PROVENIENZA}&_objvalue='+obj.value+'&_ts='+ timestamp +'&idCampo='+idField+ qs, {
					  method: 'post',	
					  onSuccess: function(transport){ 
						var response = transport.responseText;		 	
							visualizzaLista(response, obj); 
							$('spinner-'+obj.id).style.display='none';
					  },
					  onFailure: function(transport){ 
			  			var responseTexts = transport.responseText;
			  			alert(responseTexts);

				  	 }						    		 
				});
		}
		
		function validaCampoSIT(obj, idField, qs, isClick){	
			if(!isClick){
				aggiornaCampiSit(obj, isClick);
			}else{
				listaValoriCampoSIT(obj, idField, qs, isClick);
			}
		}
		
		
		
		
		function visualizzaErrore(errore, obj)
		{		
			var divID = obj.name+"_valori";
			if(!document.getElementById(divID)){
				if(errore != null){						
					dialogResult.attr("title","Errore nella ricerca per il campo [" + obj.id + "]");
					dialogResult.attr("content", errore);
					dialogResult.show();
				} 
			}
		}
		var dialogResult = null;
		jQuery(document).ready(function(){								
			dialogResult = new dijit.Dialog({
	            style: "overflow:auto; width: 400px;"
	        });
		});
		function visualizzaLista(lista, obj)
		{		
			var divID = obj.name+"_valori";										
				if(!document.getElementById(divID)){
					dialogResult.attr("title","Risultati ricerca per il campo [" + obj.id + "]");
					dialogResult.attr("content", lista);
					dialogResult.show();										
				}
				parseAjaxResponse(lista, true, false);
		}

		function assegna(oggettoId, valore){
		          document.getElementById(oggettoId).value = valore;
		          aggiornaCampiSit(document.getElementById(oggettoId), false);
		          dialogResult.hide();
		}
		
		var aggiornaCampiSit = function(obj){
			var qs = parseQs(document.inviodati);
			var elementName = obj.id;
			var timestamp = new Date().getTime();
			$('spinner-'+obj.id).style.display='';
			new Ajax.Request('<%=request.getContextPath()%>/istanzestradario/jsonCompilaSIT.htm?provenienza=${_PAGINA_PROVENIENZA}&_ts='+timestamp+'&idCampo='+elementName+ qs, {
				  method: 'post',	
				  onSuccess: function(transport){ 										  
					var json = transport.responseText.evalJSON();		 	
					var datiSit = json.datiSit;
					var errori = json.errori;
					var isEccezioneRemota = json.isEccezioneRemota;										
					$('spinner-'+obj.id).style.display='none';										
					if(errori==''){
						
						settaValido(true);
						
						if($('codviario_id')){
							$('codviario_id').value=datiSit.codVia;
						}
						if($('civico_id')){
							$('civico_id').value=datiSit.civico;
						}
						if($('km_id')){
							$('km_id').value=datiSit.km;
						}
						if($('esponente_id')){
							$('esponente_id').value=datiSit.esponente;
						}
						if($('colore_id')){
							$('colore_id').value=datiSit.colore;
						}
						if($('scala_id')){
							$('scala_id').value=datiSit.scala;
						}
						if($('piano_id')){
							if(datiSit.piano){
								$('piano_id').value=datiSit.piano;
							}												
						}
						if($('interno_id')){
							$('interno_id').value=datiSit.interno;
						}
						if($('esponenteInterno_id')){
							$('esponenteInterno_id').value=datiSit.esponenteInterno;
						}
						if($('codcivico_id')){
							$('codcivico_id').value=datiSit.codCivico;
						}
						if($('fabbricato_id')){
							$('fabbricato_id').value=datiSit.fabbricato;
						}
						if($('cap_id')){
							$('cap_id').value=datiSit.cAP;
						}
						if($('circoscrizione_id')){
							$('circoscrizione_id').value=datiSit.circoscrizione;
						}
						if($('frazione_id')){
							$('frazione_id').value=datiSit.frazione;
						}
						if($('quartiere_id')){
							if(datiSit.quartiere){
								$('quartiere_id').value=datiSit.quartiere;
							}
						}
						if($('zona_id')){
							$('zona_id').value=datiSit.zona;
						}
						if($('daValidare')){
							var idxCatasto = $('daValidare').value;
							if($(extractNomeMappale('sezione_id',idxCatasto))){
								$(extractNomeMappale('sezione_id',idxCatasto)).value=datiSit.sezione;
							}
							if($(extractNomeMappale('tipocatasto_id',idxCatasto))){
								$(extractNomeMappale('tipocatasto_id',idxCatasto)).value=datiSit.tipoCatasto;
							}
							if($(extractNomeMappale('foglio_id',idxCatasto))){
								$(extractNomeMappale('foglio_id',idxCatasto)).value=datiSit.foglio;
							}
							if($(extractNomeMappale('particella_id',idxCatasto))){
								$(extractNomeMappale('particella_id',idxCatasto)).value=datiSit.particella;
							}
							if($(extractNomeMappale('sub_id',idxCatasto))){
								$(extractNomeMappale('sub_id',idxCatasto)).value=datiSit.sub;
							}
							if($(extractNomeMappale('unitaimmob_id',idxCatasto))){
								$(extractNomeMappale('unitaimmob_id',idxCatasto)).value=datiSit.uI;
							}																							
						}
		  			}else{

		  				settaValido(false);
		  				if(isEccezioneRemota){
		  					visualizzaErrore(errori, obj);
		  				}else{
		  					listaValoriCampoSIT(obj, elementName, qs, true);
		  				}
		  				$('spinner-'+obj.id).style.display='none';
		  			}
				  },
				  onFailure: function(transport){ 
		  			var responseTexts = transport.responseText;
		  			alert(responseTexts);
			  	 }						    		 
			});								
		};
									
		function extractNomeMappale(idElement, idx){
			
			<c:if test="${_PAGINA_PROVENIENZA eq 'ISTANZESTRADARIO'}">
				idElement +='_'+idx; 
			</c:if>
			return idElement;
		}
		
		
		function validaSIT(obj, isClick){
			<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">

				    $('spinner-'+obj.id).style.display='';
					var qs = parseQs(document.inviodati);
					var elementName = obj.id;										
					validaCampoSIT(obj, elementName, qs, isClick);
			</c:if>
		}
		
		
		
		function dettaglioSIT(obj, isClick)
		{
			<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
			    if(obj!=null)
				{
			  	 $('spinner-'+obj.id).style.display='';
			    }
				var qs = parseQs(document.inviodati);
				var elementName = obj.id;										
				dettaglioCampoSIT(obj, elementName, qs, isClick);
		</c:if>
		}
		
		
		
		var dialogResultDettaglio = null;
		jQuery(document).ready(function(){								
			dialogResultDettaglio = new dijit.Dialog({
	            style: "overflow:auto; width: 500px;"
	        });
		});
		
		
		
		function closeDialogResultDettaglio()
		{
			dialogResultDettaglio.hide();
		}
		
		function dettaglioCampoSIT(obj, idField, qs, isClick){
			var timestamp = new Date().getTime();
			var call_msg = new Ajax.Request('<%=request.getContextPath()%>/istanzestradario/ajaxDettaglioSIT.htm?isClick='+isClick+'&provenienza=${_PAGINA_PROVENIENZA}&_objvalue='+obj.value+'&_ts='+ timestamp +'&idCampo='+idField+ qs, {
					  method: 'post',	
					  onSuccess: function(transport){ 
						var response = transport.responseText;		 	
							visualizzaDettaglio(response, obj); 
							$('spinner-'+obj.id).style.display='none';
					  },
					  onFailure: function(transport){ 
			  			var responseTexts = transport.responseText;
			  			alert(responseTexts);

				  	 }						    		 
				});
		}
		
		function visualizzaDettaglio(dettaglio, obj)
		{		
			var divID = obj.name+"_valori";										
				if(!document.getElementById(divID)){
					dialogResultDettaglio.attr("title","Risultati visura per il campo [" + obj.id + "]");
					dialogResultDettaglio.attr("content", dettaglio);
					dialogResultDettaglio.show();										
				}
				parseAjaxResponse(dettaglio, true, false);
		}
		
		function parseQs(objForm){
			var qs = getQueryStringFromFormStripped(objForm);			
			return qs;
		}
		
		<%--
			È stato fatto l'override della funzione gruppoinit.js#getQueryStringFromForm perchè nel form dell'istanza con 
			troppi campi dava errore nella lunghezza della querystring passata come argomento
		--%>
		function getQueryStringFromFormStripped(objForm){
			var tempForm;
			var qs = '';
			if (objForm) {
				tempForm = objForm;
			} else {
				tempForm = document.forms[0];
			}
			var cur_el;
			for (var i=0;i<tempForm.elements.length;i++){	
				if (tempForm.elements[i]){
					 cur_el = tempForm.elements[i];
		    		 if ((cur_el.disabled==false)&&(cur_el.style.display!='none')){
		    			 if(cur_el.value!=''){
			    			 if(cur_el.name!=''){
			    				 qs+='&'+cur_el.name+'='+escape(cur_el.value);
			    			 }
		    			 }
		    		 }  	 
				}
			}
			return qs;
		}
		
		
		function showFilters(objForm){
			
			var tempForm;								
			if (objForm) {
				tempForm = objForm;
			} else {
				tempForm = document.forms[0];
			}
			var cur_el;
			var html="<ul>";
			for (var i=0;i<tempForm.elements.length;i++){	
				if (tempForm.elements[i]){
					 cur_el = tempForm.elements[i];
		    		 if ((cur_el.disabled==false)&&(cur_el.style.display!='none')){
		    			 if(cur_el.id!=''){							  
		    				 if(cur_el.id.endsWith("_N")==false){
			    				 var jqEl = jQuery.find("label[for=" + cur_el.id + "]");
			    				 if(jqEl.size()>0){
			    					 if(cur_el.value!=''){
			    				 		html+=	htmlForElToReset(cur_el.id, jqEl.first().innerHTML,cur_el.value);
			    					 }
			    				 }
		    			 	}
		    			 }
		    		 }  	 
				}
			}
			//if($('daValidare')){
				
				var idxCatasto = 0;// $('daValidare').value;
				html +=extractSnippetForElem('sezione_id_', idxCatasto);
				html +=extractSnippetForElem('tipocatasto_id_', idxCatasto);
				html +=extractSnippetForElem('foglio_id_', idxCatasto);
				html +=extractSnippetForElem('particella_id_', idxCatasto);
				html +=extractSnippetForElem('sub_id_', idxCatasto);
				html +=extractSnippetForElem('unitaimmob_id_', idxCatasto);
			// }
			html+="</ul>";								
			$("myFiltersValues").innerHTML = html;
			$("myFiltersValues").appear();
		}
		
		
		function htmlForElToReset(objId, etichetta, valore){
			return '<li>' + etichetta + ' = <b>'+ valore + '</b></li>';
		}
		
		function extractSnippetForElem(elName, idx){
			
			 var jqEl = jQuery.find("label[for=" + elName + "N]");
			 var elLabel = '';
			 if(jqEl.size()>0){
				 elLabel = jqEl.first().innerHTML;
			 }
			 if(elLabel!=''){
				 if($(elName + idx)){
					 var valore = $(elName + idx).value;
					 if(valore!=''){
						return  htmlForElToReset(elName + idx,elLabel, valore);
					 }
				 }
			 }
			 return '';
		}							
		
		function confermaScelta(){
			dialogResult.hide();
			dialogResult.hide();
		}
		
		function settaValido(valoreTrueOFalse){
		
			$('valido_id').value = valoreTrueOFalse;								
			if(valoreTrueOFalse == false){
				$('stradario_validato_id').style.display = '';
			}else{
				$('stradario_validato_id').style.display = 'none';
			}
		}	
		
		function filterComune(element, entry) {								
			return entry + "&codiceComune=${istanzestradarioCommand.entity.istanza.comune.codicecomune}";  
		}
		
		function filterCodiceComune(element, entry) {
			
			if(document.getElementById("entity_comune")){
				return entry + "&codiceComune=" + document.getElementById("entity_comune").value; // Filtra nel caso di inserimento istanza
			}
			else if(document.getElementById("istanzeFilter_comune")) // Filtra nel caso di utilizzo in cerca istanza
			{
				return entry + "&codiceComune=" + document.getElementById("istanzeFilter_comune").value;
			}
			else{
				return entry ;
			}
		}
		
		function ricercaStradarioAfterUpdate(inputField,listItem){

				if($('codviario_id')){
					$('codviario_id').value='';
				}
				resettaCodiceCivicoID();									
				var a = listItem.id;
				document.getElementById('stradario_id').value = inputField.value;
				document.getElementById('stradario_id_hidden').value = a;
				$('stradario_id_choices').fade();
				altreInformazioniStradario(a);
		}					
		
		function altreInformazioniStradario(codiceStradario){
			var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getStradario.htm?codiceStradario=' + codiceStradario, {
					  method: 'post',	
					  onSuccess: function(transport){ 
						var json = transport.responseText.evalJSON();											
						var stradario = json.stradario;
						aggiornaInformazioni(stradario.cap, stradario.locfraz, stradario.circoscrizione);											 
			  			},
					  onFailure: function(transport){ 
			  			var responseTexts = transport.responseText;
			  			alert(responseTexts);
				  	 }						    		 
				});								
		}
		
		function aggiornaInformazioni(cap, frazione, circoscrizione){
			var capElem = $('cap_id');
			var frazioneElem = $('frazione_id');
			var circoscrizioneElem = $('circoscrizione_id');
			
			capElem.value		= cap;
			frazioneElem.value 	= frazione;
			circoscrizioneElem.value 	= circoscrizione;
		}
		
		var resettaCodiceCivicoID = function() {
			if($('codcivico_id')){
				$('codcivico_id').value='';
			}	
		};
		
		var ajaxSitLink = function(contesto, divId, boFeature){
			var feat="";
			if(boFeature){
				feat = boFeature;
			}
			var qs = parseQs(document.inviodati);
			var jqxhr = jQuery.ajax({
				  url: "ajaxSITLink.htm?contesto="+contesto+"&boFeature="+feat+"&codiceistanza=${_CODICEISTANZA}"+qs,
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) { 
					  if($(divId)){
					   $(divId).innerHTML = data;
					  }
					} 
				})
			};
			
			function elaboraLinkSIT(linkSIT){

				var url = linkSIT;
				url = url.replace("$ISTANZA$", '${_CODICEISTANZA}');
				url = url.replace("$TOKEN$", '<%= ORMHelper.getToken()%>');
				url = url.replace("$SOFTWARE$", '<%= ORMHelper.getSoftware()%>');
				
				if(document.getElementById("codviario_id")){
					url = url.replace("$CODICEVIA$", document.getElementById("codviario_id").value);	
				}else{
					url = url.replace("$CODICEVIA$", "");
				}
				if(document.getElementById("civico_id").value){
					url = url.replace("$CIVICO$", document.getElementById("civico_id").value);	
				}else {
					url = url.replace("$CIVICO$", "");
				}
				if(document.getElementById("colore_id")){
					url = url.replace("$COLORE$",getValoreDellaSelect(document.getElementById("colore_id")));	
				}else{
					url = url.replace("$COLORE$","");
				}				

				if(document.getElementById("stradario_id")){
					url = url.replace("$VIA$",  document.getElementById("stradario_id").value);	
				}else{
					url = url.replace("$VIA$",  "");
				}				
				if(document.getElementById("codcivico_id")){
					url = url.replace("$CODCIVICO$",  document.getElementById("codcivico_id").value);	
				}else{
					url = url.replace("$CODCIVICO$",  "");
				}	
				
				if(document.getElementById("tipocatasto_id_0")){
					url = url.replace("$CATASTO$", document.getElementById("tipocatasto_id_0").value);
				}else{
					url = url.replace("$CATASTO$", "");
				}
				if(document.getElementById("sezione_id_0")){
					url = url.replace("$SEZIONE$", document.getElementById("sezione_id_0").value );
				}else{
					url = url.replace("$SEZIONE$", "");
				}
				if(document.getElementById("foglio_id_0")){
					url = url.replace("$FOGLIO$", document.getElementById("foglio_id_0").value);	
				}else{
					url = url.replace("$FOGLIO$", "");
				}
				if(document.getElementById("particella_id_0")){
					url = url.replace("$PARTICELLA$", document.getElementById("particella_id_0").value);	
				}else{
					url = url.replace("$PARTICELLA$", "");
				}
				if(document.getElementById("sub_id_0").value){
					url = url.replace("$SUB$", document.getElementById("sub_id_0").value);	
				}else{
					url = url.replace("$SUB$", document.getElementById("sub_id_0").value);
				}					
				
				
				var w = window.open(url);				
			}
			
			
		</script>