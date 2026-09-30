<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.net.URI"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.accettatore.intervento.title' /></title>
</head>
<body>
	<style>
	
.ui-autocomplete {
    z-index: 1000000; /* adjust this value */
}
	
	
	</style>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp" >
			<jsp:param name="settimeout" value="true"></jsp:param>
	</jsp:include>

	<script type="text/javascript">
	var voceAlberoDefault = '${voceAlberoDefault}';
	
	$(document).ready(function(){
		// generazione Dialog
		$( "#interventoSelezionato" ).dialog({ autoOpen: false, modal: true, width: "800px" });
		
		$( "#btnScegliIntervento" ).click(function() {
			confermaSceltaIntervento();
		});
		
		$( "#btnAnnullaIntervento" ).click(function() {
			$( "#interventoSelezionato" ).dialog( "close" );
		});
		
		// autocompete
		$( "#txtRicerca" ).autocomplete({
			source: function(request, response) {
		            $.ajax({
		                url: "${pageContext.request.contextPath}/ajax/ricercaInterventoPerDescrizione.htm",
		                dataType: "json",
		                cache: false,
		              	type: "POST",		              	
		                data: {
		                    testo: $("#txtRicerca").val(), 
		                    tipoRicerca: $( "input:radio[name=tipoRicerca]:checked" ).val()
		                     , modoRicerca: $( "input:radio[name=modoRicerca]:checked").val()		                   
		                },
		                success: function(data) {
		                	response($.map(data, function (item) {
                                return {
                                    label: item.descrizione,
                                    id: item.id,
                                    codice: item.codice
                                };
                            }));
		                }
		            	});
					}	,
			minLength: 2,
			select: function( event, ui ) {
				if(ui.item){
					if(ui.item.codice){
						selezionaIntervento(ui.item.codice);
					}
				};
			},
			change: function(event, ui) {
				if(ui.item){
					// 
				}
			}
			// ,appendTo: "#dialogRicercaId"
		});	
		
		

		// click ricerca testuale
		$( "#opener" ).click(function() {
		  $("#txtRicerca").val("");
		  // $( "#dialogRicercaId" ).dialog( "open" );
		  $( "#dialogRicercaId" ).show( );
		  $("#txtRicerca").focus();
		});
		

		
		});
		
	
	function selezionaIntervento(codice){
		$('#interventi_tree').jstree("close_all");
		if(codice!=''){
			
			var apri = new apriRamo("interventi_tree",codice);
			
			apri.apri();

		}		
		$( "#dialogRicercaId" ).hide();		
	}
	
	
	function selezionaInterventoSenzaChiudere(codice){
		if(codice!=''){				
			var apri = new apriRamo("interventi_tree",codice);
			apri.apri();

		}		
		
	}
	
	
	
	function apriRamo(nomeAlbero, scCodice){
		
		this.albero = $('#'+nomeAlbero);
		this.indice = 0;
		this.arScCodici = [];
		for (var i = 2; i <= scCodice.length; i+=2) {
			this.arScCodici.push(scCodice.substring(0, i));
		}
		
		console.log('sc-codice=', this.arScCodici);
		
		var self = this;
		
		this.apri = function(){	
			apriInternal(0);
		}	
		
		function apriInternal(indice) {
			self.indice = indice;
			
			apriNodoCorrente();
		} 
		
		function apriRicorsivo(){
			
			if (nodoCorrenteAperto()){
				console.log('nodo corrente aperto');
				console.log('apriRicorsivo->this.indice=', self.indice);
				console.log('apriRicorsivo->this.arScCodici.length=', self.arScCodici.length);				
				
				if(self.indice <= self.arScCodici.length) {
					focusNodocorrente();
					apriInternal(self.indice+1);
				}
			} else {
				console.log('nodo corrente NON aperto');
				if(self.indice < self.arScCodici.length) {
					setTimeout(function () {
						apriRicorsivo();
					}, 300);
				}
			}			
		}
		function focusNodocorrente(){
			
			var selectedramo = self.arScCodici[self.indice];

			console.log('selectedramo',selectedramo);
			
			$('html, body').animate({
		        scrollTop: ($('#'+selectedramo).offset().top - 10)
		    }, 500);
			$('.jstree-clicked').removeClass("jstree-clicked");
			$('#'+selectedramo+'>a').addClass("jstree-clicked");
		}
		
		function apriNodoCorrente(){
			var selectedramo = self.arScCodici[self.indice];
			
			self.albero.jstree("open_node","#"+selectedramo);
			
			apriRicorsivo();
		}	
		
		
		
		function nodoCorrenteAperto(){
			var selectedramo = self.arScCodici[self.indice];
			
			return self.albero.jstree("is_open","#"+selectedramo);				
			
		}
	}
	
	
	
		$(function () {
			$("#interventi_tree").jstree({
				"plugins" : [ "themes", "json_data", "ui" ],
				"json_data" : {
					"ajax" : {
						"url" : "${pageContext.request.contextPath}/ajax/getAlberoProcCart.htm",
						"data" : function (n) {
							return { "id" : n.attr ? n.attr("id") : "" }; 
						}
					}
				},
				"themes" : {
					"theme" : "classic",
					"dots" : true,
					"icons" : true
				},
				"ui" : {
					"select_limit" : 1
				}
			}).bind("select_node.jstree", function (event, data) { 
				
				var id = data.rslt.obj.attr("id");
				var descrizione = data.rslt.obj.attr("rel");
				$('#interventoSelezionatoId').val("");
				if(descrizione){
					if(descrizione!=''){
						if(descrizione.substring(0,4)==='DOC:'){
							apriDocumento(id);
							return false;
						}
					}
				}
				if(data.rslt.obj.attr("rel") == 'FOLDER'){
					selezionaInterventoSenzaChiudere(id);
				}else{
					if(data.rslt.obj.attr("non_selezionabile") == 'true'){
						interventoNonSelezionabile(id, descrizione);
					}else{
						mostraInfoIntervento(id, descrizione);
					}
				}
			});
		});
		
		function apriDocumento(identificativo){
			
			var docUrl ='${pageContext.request.contextPath}/docs/'+identificativo+'.rtf';
			window.open(docUrl,69);
			
			
		}
		
		function interventoNonSelezionabile(id, descrizione){
			alert("Nessun elemento trovato");
		}

		function mostraInfoIntervento(id, descrizione){
			$('#interventoSelezionatoId').val(id);
			$('#interventoSelezionatoLblId').text(descrizione);
			$( "#interventoSelezionato" ).dialog( "open" );
			
		}
		
		function confermaSceltaIntervento(){
			if($('#interventoSelezionatoId').val()!=''){
				var codiceComune = scegliComune();
				if(codiceComune){
					$.blockUI();
					location.href="../cart/verificaInterventi.htm?id="+$('#interventoSelezionatoId').val()+"&codicecatastalecomune="+codiceComune;
				}
			}			
		}
		
		function scegliComune(){
			<c:choose>	
			<c:when test="${fn:length(comunis)>1}">
				<c:choose>
					<c:when test="${fn:length(comunis) < 15 }">
						var codicecomune = $('input[name=codicecatastalecomune]:checked').val();
					</c:when>
					<c:otherwise>
						var codicecomune = $('input[name=codicecatastalecomune]').val();
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				var codicecomune = $('input[name=codicecatastalecomune]').val();
			</c:otherwise>
			</c:choose>
			if(codicecomune!=''){
				return codicecomune;
			}
			
		}
		$(document).ready(function(){
			
			<%if(!request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
			
			if(voceAlberoDefault!=''){
				$.blockUI();
				setTimeout(function(){					
					selezionaIntervento(voceAlberoDefault);
					$.unblockUI();
				}, 1500);
							
			}
			<%}%>
			
			$("input[name=codicecatastalecomune]:radio").change(function () {				
		        
		        $( ".chkComune" ).each(function() {
		        	  if($( this ).attr("checked")){
		        		  $('#lbl-'+this.id).addClass( "comune-selected" );
		        	  }else{
		        		  $('#lbl-'+this.id).removeClass( "comune-selected" );		        		  
		        	  }
		        });
		    });
			
		});
		
	</script>
	<div  style="width: 100%">
		<div style="width: 50%; float:left; ">
		
					<%if(request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
							<div class="titolo">Scelta dell'istanza da trasmettere</div>
							<div class="descrizione">Individuare la materia specifica</div>			
					<%}else{ %>
							<div class="titolo"><fmt:message key='label.accettatore.intervento.title' /></div>
							<div class="descrizione"><fmt:message key='label.accettatore.intervento.descrizione' /></div>
					<%} %>
			
			
			
		</div>		
	</div>
	<div style="clear: both;"></div>
	<%@ include file="../includes/alert.jsp" %>
	<div class="titolo_sezione"></div>
		
		<div id="interventoSelezionato" style="padding: 5px;">
			<input type="hidden" id="interventoSelezionatoId" value=""/>
						
			 
			
			
			<%if(request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
E' stato selezionato il procedimento	
					<%}else{ %>
E' stato selezionato l'intervento
					<%} %>
			
			
			<div class="nodo-selezionato">
			<label id="interventoSelezionatoLblId"></label>
			</div>
						
			<c:choose>
				<c:when test="${fn:length(comunis)>1 }">
				<c:choose>
					<c:when test="${fn:length(comunis) < 15 }">
					<div id="pannello_scelta_comune" class="nodo-selezionato" style="padding: 5px; margin: 5px;">
						<fmt:message key='label.accettatore.scelta_comune_nuova_domanda' />
					<br />
					<c:set var="_checked">checked="checked"</c:set>	
					<c:set var="_labelchecked"> class="comune-selected" </c:set>				
					<c:forEach var="comune" items="${comunis}">
						<input type="radio" name="codicecatastalecomune" class="chkComune" id="codicecatastalecomune-${comune.codicecomune }" value="${comune.codicecomune }" ${_checked}/>
						<label for="codicecatastalecomune-${comune.codicecomune }" id="lbl-codicecatastalecomune-${comune.codicecomune }" ${_labelchecked }  >${comune.comune}</label><br />
						<c:set var="_checked"> </c:set>		
						<c:set var="_labelchecked"> </c:set>	
					</c:forEach>
					</div>
					</c:when>
					<c:otherwise>
						<div id="pannello_scelta_comune" class="nodo-selezionato" style="padding: 5px; margin: 5px;">
							Selezionare il comune di pertinenza dove insiste la richiesta della pratica
							<br />
							<c:forEach var="comune" items="${comunis}">
								<div class="comuneperscelta" style="display: none;" data-codicecomune="${comune.codicecomune}">${comune.comune}</div>
							</c:forEach>
							<input type="text" name="codicecatastalecomuneselect" id="sceltacomuneautocompl" size="60"/>
							<input type="hidden" name="codicecatastalecomune" id="codicecatastalecomune_univoco" value=""/>	
							<script type="text/javascript">
							
								// var daticomuni = populateComuni();

								function populateComuni(){
									var res = [];
							        $( ".comuneperscelta" ).each(function() {
							        	res.push({
							                label: $(this).text(),
							                value: $(this).data('codicecomune')							                
							            });
							        });
							        return res;
								}
								
								$(function () {
								
									$( "#sceltacomuneautocompl" ).autocomplete({
									  appendTo: "#pannello_scelta_comune",
								      source: populateComuni(),
								      change: function( event, ui ) {
								    	  
								    	  console.log("change");
								    	  console.log(ui);
								    	  console.log(ui.item);
								    	  
								    	  if(ui.item == null){
								    		  $('#codicecatastalecomune_univoco').val('');
									    	  $('#sceltacomuneautocompl').val('');
								    	  }
								      },
								      select: function( event, ui ) {
								    	  console.log("select");
								    	  event.preventDefault();
								    	  console.log(ui.item.label);
								    	  console.log(ui.item.value);								    	  
								    	  $('#codicecatastalecomune_univoco').val(ui.item.value);
								    	  $('#sceltacomuneautocompl').val(ui.item.label);
								      },
								      search: function( event, ui ) {
								    	  console.log("search");
							    		  $('#codicecatastalecomune_univoco').val('');								    	  
								      }
								      ,
								    });
								
									$(/** autocomplete-selector **/)
								    .autocomplete("option", "appendTo", "#copy_dialog");
									
								});
							</script>
							
						</div>	
					</c:otherwise>
					</c:choose>					
				</c:when>
				<c:otherwise>
					<c:forEach var="comune" items="${comunis}">
						<input type="hidden" name="codicecatastalecomune" value="${comune.codicecomune }"/>	
					</c:forEach>				
				</c:otherwise>
			</c:choose>
			
			<br />
			<div style="text-align: center; ">
				procedere?<p>&nbsp;</p>
				<button id="btnScegliIntervento"><fmt:message key='label.ok' /></button>&nbsp;<button id="btnAnnullaIntervento"><fmt:message key='label.annulla' /></button>
			</div>

		</div>
		
		<button id="opener"><fmt:message key='label.ricerca-testuale' /></button>
		
		
			<div id="dialogRicercaId" style="display: none" >
				<div class="inputForm ricercaTestuale">
					<div>
						<div class="sezione-ricerca">
							<b><fmt:message key='label.testo-da-ricercare' /></b>
						</div>
						<div>							
							<input type="text" id="txtRicerca" size="60" />
						</div>
					</div>
					<%-- ATTUALMENTE E' UN CAMPO NASCOSTO. IN FASE 2 SARà POSSIBILE VISUALIZZARE QUELLO COMMENTATO SOTTO --%>
					<input type="hidden" name="modoRicerca" id="modoRicercaId" value="titoli" />		
					<div>
						<div class="sezione-ricerca">
							<b>Modalità di ricerca</b>
						</div>
						<div>
							<input type="radio" name="modoRicerca" id="modoRicercaId1" value="titoli" />
							<label for="modoRicercaId1">
							Cerca nei titoli</label>
						</div>
						<div>
							<input type="radio" name="modoRicerca" id="modoRicercaId2" value="titoliDescrizioni" checked="checked"  />
							<label for="modoRicercaId2">
							Cerca nei titoli e nelle descrizioni</label>
						</div>		
					</div>					
					<div>
						<div class="sezione-ricerca">
							<b><fmt:message key='label.tipo-ricerca' /></b>
						</div>
						<div>
							<input type="radio" name="tipoRicerca" id="tipoRicercaId1" value="tutteParole" checked="checked" />
							<label for="tipoRicercaId1">
							<fmt:message key='label.cerca-le-voci-che-contengono-tutte-parole' /></label>
						</div>
						<div>
							<input type="radio" name="tipoRicerca" id="tipoRicercaId2" value="interaFrase" />
							<label for="tipoRicercaId2">
							<fmt:message key='label.cerca-le-voci-che-contengono-intera-frase' /></label>
						</div>
						<div>
							<input type="radio" name="tipoRicerca" id="tipoRicercaId3" value="almenoUnaParola"/>
							<label for="tipoRicercaId3">
							<fmt:message key='label.cerca-le-voci-che-contengono-almeno-una-parola' /></label>
						</div>
					</div>
				</div>
		</div>	
		 
		<div class="titolo_sezione"><fmt:message key='label.lista-interventi' /></div>
		<div id="sez_interventi_tree" class="sezione">
			<div id="interventi_tree"></div>
		</div>



<div style="padding-top: 20px;">
	<%@ include file="../includes/chiudiPaginaIniziale.jsp" %>
</div>

</body>
</html>