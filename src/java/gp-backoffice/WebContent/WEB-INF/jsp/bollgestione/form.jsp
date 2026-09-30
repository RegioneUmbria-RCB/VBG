<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${dettaglioBollettazione.id==null}">
	        <fmt:message key="dettaglioBollettazione.label.nuovo_dettaglioBollettazione.title" />
	    </c:if>
	    <c:if test="${dettaglioBollettazione.id!=null}">
	        <fmt:message key="dettaglioBollettazione.label.dettaglio_dettaglioBollettazione.title" />
	    </c:if>
	</title>
    <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>
    
</head>
<body>
    <span class="titoloPagina"> <c:if
            test="${dettaglioBollettazione.id==null}">
            <fmt:message
                key="dettaglioBollettazione.label.nuovo_dettaglioBollettazione.title" />
        </c:if> <c:if test="${dettaglioBollettazione.id!=null}">
            <fmt:message
                key="dettaglioBollettazione.label.dettaglio_dettaglioBollettazione.title" />
        </c:if>
    </span>
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="form" />
    </jsp:include>
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../bollgestione/view" />
	  </jsp:include>
    <div id="subcontent">
    <c:set var="campi_readonly" value="false" scope="page"/>
    <c:if test="${ dettaglioBollettazione.id != null }">
    	<c:set var="campi_readonly" scope="page" value="true" />
    </c:if>
    
        <spring-form:form commandName="dettaglioBollettazione"
            name="inviodati">
            <jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName"
                    value="dettaglioBollettazione" />
            </jsp:include>
				 <div id="elab_in_corso" style="padding-bottom:20px; border: 1px dotted maroon; display:none; max-height: 400px; overflow-y: scroll;" 
				 data-id="${dettaglioBollettazione.id}"></div>
            <table>
                <tr>
                    <td><fmt:message
                            key="dettaglioBollettazione.label.descrizione" /></td>
                    <td><spring-form:hidden id="codice" path="id" />
                    	<c:choose>
                    		<c:when test="${campi_readonly eq 'true'}">
                    			<b>${dettaglioBollettazione.descrizione}</b>
                    		</c:when>
                    		<c:otherwise>
                    			<spring-form:input id="descrizione_id"  path="descrizione" size="70" /> 
                            	<spring-form:errors path="descrizione" cssClass="error" />
                            </c:otherwise>
                    	</c:choose>
                      </td>
                </tr>
                <tr>
                    <td><fmt:message
                            key="dettaglioBollettazione.label.dalladata" />
                    </td>
                    <td>
                       <c:choose>
                    		<c:when test="${campi_readonly eq 'true'}">
                    			<b><fmt:formatDate value="${dettaglioBollettazione.dallaData}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </b>
                    		</c:when>
                    		<c:otherwise>
								<spring-form:input id="dallaData_id"
	                            path="dallaData" size="10"
	                            onblur="isValidDate(this,true);"  /> 
	                            <init:calendar
	                            imagePath="/images/cal.gif"
	                            idImage="calDallaData"
	                            idInput="dallaData_id"
	                            textKey="label.calendar" /> 
	                            <spring-form:errors path="dallaData" cssClass="error" />                            
                            </c:otherwise>
                    	</c:choose>
                    </td>
                    <td><fmt:message
                            key="dettaglioBollettazione.label.alladata" />
                    </td>
                    <td>
                       <c:choose>
                    		<c:when test="${campi_readonly eq 'true'}">
                    			<b><fmt:formatDate value="${dettaglioBollettazione.allaData}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </b>
                    		</c:when>
                    		<c:otherwise>
		                    	<spring-form:input id="allaData_id"
		                            path="allaData" size="10"
		                            onblur="isValidDate(this,true);" /> 
		                            <init:calendar
		                            imagePath="/images/cal.gif"
		                            idImage="calAllaData"
		                            idInput="allaData_id"
		                            textKey="label.calendar" /> 
		                            <spring-form:errors path="allaData" cssClass="error" />                        
                            </c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr>
                   <td>
                   	<init:editLabel key="dettaglioBollettazione.label.statoBollettazione" role="ROLE_EDITLABEL" />
                   <td>
             			<b>${dettaglioBollettazione.statoBollettazione}</b>
                   </td>
                </tr>
                <tr>
                	<td style="vertical-align: middle;">
                		<init:editLabel key="dettaglioBollettazione.label.dataScadenza" role="ROLE_EDITLABEL" />
                	</td>
					<td>
						<spring-form:input id="dataScadenza_id"
                            path="dataScadenza" size="10"
                            onblur="isValidDate(this,true);"  /> 
                            <init:calendar
                            imagePath="/images/cal.gif"
                            idImage="calDataScadenza"
                            idInput="dataScadenza_id"
                            textKey="label.calendar" />
                            <spring-form:errors path="dataScadenza" cssClass="error" />
                            
                             <ul class="funzioni-dettaglio">
					            <li class="cmd-aggiorna-scadenza">
									<i class="far fa-save"></i>
									<init:editLabel key="label.aggiornaDataScadenza" role="ROLE_EDITLABEL" />
								</li>
				            </ul>
                    </td>
                </tr>
            </table>
            <div class="vbg-form">
				<fieldset>
				<legend><fmt:message key="label.form_ricerca" /> </legend>
					<div class="form-group">
						<div class="input-icons">
							<i class="fa fa-search icon"></i>
							<input id="input_ricerca" type="text" placeholder="Cerca" />
							&nbsp;
							<label for="filtro_posizioni_id"><init:editLabel key="dettaglioBollettazione.label.filtro_posizioni" role="ROLE_EDITLABEL" /></label>
							<select name="filtro_posizioni" id="filtro_posizioni_id">
								<option value="">Tutte</option>
								<option value="true">Create</option>
								<option value="false">Non create</option>
							</select>
						</div>
						<div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella_invio" /></div>		
					</div>
				</fieldset>
			</div>

<div id="container-contenuti">



			
            <c:if test="${dettaglioBollettazione.bollettazioneChiusa eq false }">
            	 <div id="sel_des_tutto">
                	<label>
                		<fmt:message key="dettaglioBollettazione.label.validaTutto" />
                	</label>
                	<input id="valida_tutti" type="checkbox" name="validaTutti" data-id-bollettazione="${dettaglioBollettazione.id}" />
            	</div>
            </c:if>
           
           

 <style media="all">
 #sel_des_tutto{
 	text-align: right;
 	padding-right: 16px;
	padding-bottom: 8px;
 }
#subcontent>form>table {
	margin-bottom: 16px;
}

.funzioni-dettaglio {
	margin: 0;
	padding: 0;
	list-style-type: none;
	width: 100%;
}

.funzioni-dettaglio>li {
	display: inline-block;
	margin-right: 16px;
	cursor: pointer;
	color: #b61218;
}

.funzioni-dettaglio>li:hover {
	text-decoration: underline;
}

.blocco-anagrafica {
	border: 1px solid #666;
	margin-bottom: 16px;
	padding: 16px;
}

.blocco-anagrafica .jmesa {
	padding: 0;
}

.blocco-anagrafica .jmesa td {
	font-size: inherit !important;
}

.blocco-anagrafica h3 {
	margin: 0;
	margin-bottom: 16px;
}

.blocco-anagrafica .jmesa table {
	padding: 0;
	border-collapse: collapse;
	margin-bottom: 16px;
}

.blocco-anagrafica .cmd-aggiungi, .blocco-anagrafica .cmd-verifica-stato
{
	background-color: transparent;
	border: 0;
	padding: 0;
	margin: 0;
	cursor: pointer;
	color: #b61218;
}

.blocco-anagrafica .cmd-aggiungi:hover {
	text-decoration: underline;
}

.cmd-aggiorna-scadenza {
	background-color: transparent;
	border: 0;
	padding: 0;
	margin: 0;
	cursor: pointer;
	color: #b61218;
}

.cmd-aggiorna-scadenza:hover {
	text-decoration: underline;
}


#rettificaImportoSenzaIVA #rettificaIVA #rettificaImporto {
	text-align: right;
}

.riga-bollettazione.rettificato td {
	color: red;
	text-decoration: line-through;
}

.popup-form {
	padding-top: 16px;
}

.popup-form .read-only {
	width: 100%;
	padding: 4px;
	box-sizing: border-box;
	border: 1px solid #000;
	min-height: 24px;
	background-color: #fafafa;
}

.popup-form .read-only ul {
	margin: 0;
	padding: 0;
	padding-left: 16px;
}

.popup-form label {
	padding-top: 5px;
	display: inline-block;
}

.popup-form table {
	width: 100%;
	/*margin-top: 16px;
				margin-bottom: 16px;*/
}

.popup-form table td {
	vertical-align: top;
	padding-bottom: 4px;
}

.popup-form textarea {
	width: 100%;
	height: 80px;
	padding: 4px;
	box-sizing: border-box;
}

.popup-form select {
	width: 100%;
	padding: 4px;
	box-sizing: border-box;
}

.popup-form input[type=text] {
	width: 100%;
	padding: 4px;
	box-sizing: border-box;
}

.popup-form input[type=text].numero {
	text-align: right;
}

.popup-form .touched.has-error {
	border: 1px solid #ba0000;
}

.blocco-anagrafica>h3 {
	display: inline-block;
}

.iva-riga:after {
    content: '%';
    margin-left: 2px;
    margin-right: 2px;
}
.pagination > a {
	text-decoration: none;
	padding: .5rem .75rem;
	margin-left: -1px;
	line-height: 1.25;
	color: #b61218;
	background-color: #fff;
	border: 1px solid #dee2e6;
}
.pagination{
	margin: 5px;
}
.pagination a.active {
  background-color: dodgerblue;
  color: white;
}

.links-pag-nav{
	margin: 12px;
}

.pagination a:hover:not(.active) {background-color: #ddd;}

.elaborazione{
	font-weight: bold;
	
}    
</style>


            <script type="text/javascript">
            
            
            const GLB_MOSTRA_RETTIFICA = ${mostraFunzioneRettifica};
            const GLB_MOSTRA_AGGIUNGI_RIGA = ${mostraFunzioneAggiungiRiga};
				(function($) {
					
					function inizializzaBlocchiAnagrafica() {
						$('.blocco-anagrafica').each(function(index, value) {
							inizializzaSingoloBloccoAnagrafica($(value));
						});
					}

					function inizializzaSingoloBloccoAnagrafica($blocco) {
						console.log($blocco);

						var cmdVerificaStato = $blocco.find(".cmd-verifica-stato");
						
						
						
						var cmdAggiungi = $blocco.find(".cmd-aggiungi");
						var cmdElencoRateAnagrafica = $blocco.find(".cmd-elencorate-anagrafica");
						
						var idAnagrafica = parseInt($blocco.data("id"));
						var idBollettazione = ${dettaglioBollettazione.id};
						
						if( cmdElencoRateAnagrafica ) {
							cmdElencoRateAnagrafica.on('click',
								function(e) {
								dettaglioRateizzazioneAnagrafica(idBollettazione, idAnagrafica);
									e.preventDefault();
							});
						}
						
						if(${dettaglioBollettazione.presenteLetteraAvvisatura}){
							var cmdAnteprimaAvvisatura = $blocco.find(".cmd-anteprima-avvisatura");
							if(cmdAnteprimaAvvisatura){
								if(cmdAnteprimaAvvisatura.data('id-posizione')){
									cmdAnteprimaAvvisatura.html( getSnippetOggettoAnteprima(cmdAnteprimaAvvisatura.data('id-posizione'), idBollettazione) );
								}
							}
						}
						cmdVerificaStato.on('click', function(e) {
							location.reload();
							e.preventDefault();
						});
						
						cmdAggiungi.on('click', function(e) {
							aggiungiRiga(idBollettazione, idAnagrafica);
							e.preventDefault();
						});


						var table = $blocco.find("table");
						
						inizializzaTabella(idBollettazione, idAnagrafica, table);
						console.log('Tabella inizializzata');
					}

					function inizializzaTabella(idBollettazione, idAnagrafica, $table) {

						var bollettazioneChiusa = ${dettaglioBollettazione.bollettazioneChiusa};
						var cfpivaPresente = $('#bloccoTabella' + idAnagrafica).data('cfpivapresente');
						
						var dettaglioposizionedebitoriapresente = $('#bloccoTabella' + idAnagrafica).data('dettaglioposizionedebitoriapresente');
						
						var preventValidazione = ( bollettazioneChiusa || !cfpivaPresente || dettaglioposizionedebitoriapresente);
						
						
						$table.find(".riga-bollettazione").each(function(index, value) {
							var $el = $(value);
							var idRiga = parseInt($el.data("idRiga"));
							var cmdDettaglio = $el.find(".cmd-dettaglio");
							var cmdRettifica = $el.find(".cmd-rettifica");
							var cmdCancella = $el.find(".cmd-cancella");
							var cmdValidato =$el.find(".cmd-validato");
							var cmdElencoRate = $el.find(".cmd-elencorate");
						
						
							cmdDettaglio.on('click',
								function(e) {
									dettaglioRiga(idBollettazione, idAnagrafica, idRiga);
									e.preventDefault();
							});
							
							cmdElencoRate.on('click',
								function(e) {
									dettaglioRateizzazioneRiga(idRiga);
									e.preventDefault();
							});
						

							cmdRettifica.on('click',
								function(e) {
									rettificaRiga(idBollettazione, idAnagrafica, idRiga);
									e.preventDefault();
							});

							cmdCancella.on('click',
								function(e) {
									cancellaRiga(idBollettazione, idAnagrafica, idRiga);
									e.preventDefault();
							});

							
							if( preventValidazione ) {
								cmdValidato.on('click',
										function(e) {
											
												e.preventDefault();
									});
							}
							
							cmdValidato.on('change',
								function(e){
								validaRiga(idBollettazione,idRiga,$(this));
								e.preventDefault();
							});
							
							cmdValidato.data('cmdRettifica',cmdRettifica);
							cmdValidato.data('cmdCancella',cmdCancella);
							
							cmdValidato.on('statoValidazioneModificato',function(){
								$(this).data('cmdRettifica').toggle(!$(this).prop('checked'));
								$(this).data('cmdCancella').toggle(!$(this).prop('checked'));
							});
							
							cmdValidato.trigger('statoValidazioneModificato');

							
						});
					}
					
					
					function callAsync(options) {
						
						disableFunctions();
						
						return $.ajax({	
							url : options.url,
							data : options.data,
							method : 'POST',
							type : 'POST', // For jQuery < 1.9
							context : document.body,
							cache : false,
							dataType : options.dataType || "html",
							success : options.success,
							error : options.error || gestisciErrore
						}).always(enableFunctions);
					}
					
					
					function dettaglioRiga(idBollettazione, idAnagrafica, idRiga) {

						callAsync({
							url : "ajaxDettaglioRiga.htm",
							data : {
								idBollettazione : idBollettazione,
								idAnagrafica : idAnagrafica,
								idRiga : idRiga
							},
							success : function(data) {

								var $dialog = $("#dialogDettaglioRiga");
								$dialog.empty();
								$dialog.append(data);
								$dialog.dialog("open");

								console.log("dettaglioRiga", idBollettazione, idAnagrafica, idRiga);
							}							
						});
					}
					
					function dettaglioRateizzazioneRiga(idRiga){
						callAsync({
							url : "ajaxDettaglioRateizzazione.htm",
							data : {
								idRiga : idRiga
							},
							success : function(data) {

								var $dialog = $("#dialogDettaglioRate");
								$dialog.empty();
								$dialog.append(data);
								$dialog.dialog("open");
							}							
						});
					}
					
					function dettaglioRateizzazioneAnagrafica(idBollettazione, idAnagrafica){
						callAsync({
							url : "ajaxDettaglioRateizzazioneAnagrafica.htm",
							data : {
								idBollettazione : idBollettazione,
								idAnagrafica : idAnagrafica
							},
							success : function(data) {

								var $dialog = $("#dialogDettaglioRate");
								$dialog.empty();
								$dialog.append(data);
								$dialog.dialog("open");
							}							
						});
					}
					
					function validaRiga(idBollettazione, idRiga, cmdValidato) {
						
						callAsync({
							url : "ajaxValida.htm",
							data : {
								idBollettazione : idBollettazione,
								idRiga : idRiga,
								validato: cmdValidato.prop('checked')
							},
							success : function(data) {				
								cmdValidato.trigger('statoValidazioneModificato');
								tutteRigheValidate(null);
								
								console.log('validaRiga: OK');
							}
						});
										
						console.log('validaRiga',idBollettazione, idRiga, ' valida: ', cmdValidato.prop('checked') );
					}
					
					function tutteLeRigheSonoValidate(table)
					{
					
						var retVal = true;
						
						$(".tabella-dettaglio")
							.find(".riga-bollettazione")
							.each(function(index, value) {
								var $el = $(value);
								var idRiga = parseInt($el.data("idRiga"));
								var cmdValidato = $el.find(".cmd-validato");
								
								if( cmdValidato.length > 0 && !cmdValidato.prop('checked')) {
									retVal = false;
									return false;
								}
							});
						
						return retVal;
					}

					function gestisciErrore(jqXHR, textStatus, errorThrown) {
						console.error([ jqXHR, textStatus, errorThrown ]);
						// TODO Mostrare a video
						alert("Si è verificato un errore durante l'esecuzione.");

					}

					function rettificaRiga(idBollettazione, idAnagrafica, idRiga) {
						console.log('Rettifica riga ', idRiga);

						// TODO: recuperare importo e descrizione
						var riga = $('#rigaBollettazione' + idRiga.toString()),
							descrizione = riga.find('.descrizione-riga').text().trim(),
							importoSenzaIVA = riga.find('.importo-senza-iva-riga').text().trim(),
							iva = riga.find('.iva-riga').text().trim(),
							importo = riga.find('.importo-riga').text().trim();
						
						popupRettifica.mostra(idBollettazione, idAnagrafica, idRiga, descrizione, importoSenzaIVA, iva, importo)
							.then(function onSuccess(e) {
								  
    							    console.log('success, ', e);
    							  
    								// TODO: passare i dati al server.
    								// al successo utilizzare ricaricaBlocco(idBollettazione, idAnagrafica);
    								// per ricaricare il blocco di righe 
    								// e visualizzare la riga rettificata con un carattere barrato
    						  	  
    								callAsync({
    									url : "ajaxRettificaRiga.htm",
    									data : {
    										idBollettazione : e.idBollettazione,
    										idAnagrafica : e.idAnagrafica,
    										idRiga : e.idRiga,
    										importoSenzaIVA : e.importoSenzaIVA,
    										iva : e.iva,
    										importo : e.importo
    									},
    									dataType : "json",
    									success : function(data) {
    										//ricaricaBlocco(idBollettazione, idAnagrafica);
    										//console.log(data);
    										var idBloccoAnagrafica =`blocco-anagrafica-\${idAnagrafica}`;
                                        	var idBloccoTabella = `bloccoTabella\${idAnagrafica}`;
                                         	ricaricaBloccoAnagrafica(idAnagrafica, idBloccoAnagrafica, idBloccoTabella); 
    									},
    						  	  	});
    								
    								popupRettifica.nascondi();
    							  },
        							  
    							  function onFail(){
    								  console.log('fail');
    							  }
        					);
					}

					function cancellaRiga(idBollettazione, idAnagrafica, idRiga) {
						callAsync({
	                        url : "ajaxCancellaRiga.htm",
                            data : {
                                idBollettazione : idBollettazione,
                                idAnagrafica : idAnagrafica,
                                idRiga : idRiga
                            },
                            success : function(data) {
                                //console.log('Cancellata riga ', idRiga);
                                //ricaricaBlocco(idBollettazione, idAnagrafica);
                                
                                var idBloccoAnagrafica =`blocco-anagrafica-\${idAnagrafica}`;
                            	var idBloccoTabella = `bloccoTabella\${idAnagrafica}`;
                             	ricaricaBloccoAnagrafica(idAnagrafica, idBloccoAnagrafica, idBloccoTabella); 
                            }
						});
					}

					function aggiungiRiga(idBollettazione, idAnagrafica) {
						console.log('Aggiunta di una nuova riga per idBollettazione=', idBollettazione, ', idAnagrafica=', idAnagrafica);

						popupAggiungi.mostra(idBollettazione, idAnagrafica)
							.then(
								function onSuccess(d) {
									console.log(d);
									
									popupAggiungi.nascondi();
									
									callAsync({
										url : "ajaxAggiungiRiga.htm",
                                        data : d,
                                        success : function(data) {
                                        	var idBloccoAnagrafica =`blocco-anagrafica-\${idAnagrafica}`;
                                        	var idBloccoTabella = `bloccoTabella\${idAnagrafica}`;
                                           // ricaricaBlocco(idBollettazione, idAnagrafica);
                                         	ricaricaBloccoAnagrafica(idAnagrafica, idBloccoAnagrafica, idBloccoTabella); 
                                        }
									});
									
								},
								function onFail() {
									
								}						
							
							);
						//ricaricaBlocco(idBollettazione, idAnagrafica);
					}

					function ricaricaBlocco(idBollettazione, idAnagrafica) {

						console.log('ricarica blocco con idBollettazione=', idBollettazione, ' e idAnagrafica=', idAnagrafica);

						callAsync({
							url : "ajaxRicaricaBlocco.htm",
                            data : {
                                idBollettazione : idBollettazione,
                                idAnagrafica : idAnagrafica
                            },
                            success : function(data) {

                                var $table = $(data);

                                $("#bloccoTabella" + idAnagrafica.toString()).empty();
                                $("#bloccoTabella" + idAnagrafica.toString()).append($table);

                                inizializzaTabella(idBollettazione, idAnagrafica, $table);
                            }
						});
					}
					
					var popupRettifica;
					
					var popupAggiungi;

					$(function (){
						 $("#dialogDettaglioRiga").dialog({
							 'autoOpen': false,
							 'width': '800',
							 'title': 'Dettaglio riga',
							 'modal': true
						 });
						 
						 $("#dialogDettaglioRate").dialog({
							 'autoOpen': false,
							 'width': '800',
							 'title': 'Dettaglio rate',
							 'modal': true
						 });
						 
						 popupRettifica = new PopupRettifica($("#formRettifica"));
						 
						 popupAggiungi = new PopupAggiungi($("#formAggiungi"));
						 
						 loadAnagraficaBoll();
						
						$('.numero')
							.on('keypress', function(e){
							  return e.metaKey || // cmd/ctrl
							    e.which <= 0 || // arrow keys
							    e.which == 8 || // delete key
							    /[0-9\.,]/.test(String.fromCharCode(e.which)); // numbers
							})
							.on('blur', function (e) {
								checkCurrencyValue(this);								
							});
						
						
					});
					
					function validaTutti(){
						
						var valida_tutti = $('#valida_tutti');
						//TODO verifica quando puoi fare il prevent click

						valida_tutti.on('click', function(e){

							var checkIt = valida_tutti.prop('checked');
							var conferma = false;
							
							if(checkIt) {
								conferma = window.confirm("Selezionare tutte le righe?");
							} else {
								conferma = window.confirm("Deselezionare tutte le righe?");
							}
							
							if( conferma )  {
								
								var idBollettazione = parseInt( valida_tutti.data("idBollettazione") );
								window.vbg.mostraModalCaricamento();
								callAsync({
									url : "ajaxValidaRighe.htm",
									data : {
										idBollettazione : idBollettazione,
										validato: checkIt
									},
									success : function(data) {	
										document.location.reload();
									},
									error : function(event, xhr, ajaxOptions, thrownError){
										valida_tutti.prop('checked',!checkIt);
										console.error("Si è verificato un errore ", event, xhr, ajaxOptions, thrownError);
										alert("Si è verificato un errore");
										document.location.reload();
									}
								});
										
							} else {
								e.preventDefault();
							}
						});	
					}
					const limit = 10;
						 async function loadAnagraficaBoll(){
							 disableFunctions();
							 const resp = await fetch("../bollgestione/ajaxGetDettaglioBollettazione.htm?idBollettazione="+${dettaglioBollettazione.id},{
					                method: "GET",
					                cache: "no-cache",
					                headers: {
					                    'Content-Type': 'application/json'
					                  }
								});
						
						const dettBoll = await resp.json();
						enableFunctions();
						const anagBollSec = document.getElementById("anag_boll_section");
			            mostraPaginaNav(limit,dettBoll.anagraficaBollettazioneList,"pagination1",null);
			            mostraPaginaAnagrafe(0,limit,dettBoll.anagraficaBollettazioneList);
			            tutteRigheValidate(dettBoll.anagraficaBollettazioneList);
		                
	
					}
					 
					 function iniziallizzaRigheCounter(pagination,section,dataSet,tableSection,newNumRighe=null){
						 var rowNum = 10;
						 if (newNumRighe !== null) {
							rowNum = dataSet.length;
						} else {
							rowNum = (rowNum > dataSet.length) ? dataSet.length:rowNum;
						}
						 
						 var html =`<div class="pagination-nav pagination" >
						 	<span style="padding:16px;">&nbsp;1-\${rowNum} di \${dataSet.length}&nbsp;</span>
						 	<span style="padding:5px;">Righe per pagina</span><select name="" style="display-inline-block" aria-controls="righe per pagina" class="num-righe">
	 							<option value="10">10</option>
	 							<option value="25">25</option>
	 							<option value="50">50</option>
	 							<option value="100">100</option>
	 						</select></div>`;
	
 
						section.lastChild.insertAdjacentHTML("afterbegin",html);
						const selectNumRighe = section.getElementsByTagName('select')[0];
						selectNumRighe.addEventListener('change',(e)=>{
							let numRighe = 0;
							if (newNumRighe !== null) {
								numRighe = newNumRighe;
							} else {
								numRighe = Number(e.target.value);
							}
 							
 							if (pagination == "pagination2") {
 								var text;
 								mostraTabellaRigaBoll(1, numRighe, dataSet, tableSection);
 								if (numRighe < dataSet.length) {
 									text = `&nbsp;1-\${numRighe} di \${dataSet.length}&nbsp;`;
								}else{
									text = `&nbsp;1-\${dataSet.length} di \${dataSet.length}&nbsp;`;
								}
 								
 								section.getElementsByClassName("pagination")[0].getElementsByTagName('span')[0].innerHTML="";
 								section.getElementsByClassName("pagination")[0].getElementsByTagName('span')[0].insertAdjacentHTML("beforeend",text);
 								mostraPaginaNav(numRighe,dataSet,"pagination2",section);
 								inizializzaBlocchiAnagrafica();
							}else{
								
							}
 							
 						});	 
					 }
					 
					 
					 function mostraPaginaNav(perPage,dataSet,id,bloccoAnagrafica){
						 	
						    var totalItems = dataSet.length;
						    perPage = perPage ? perPage : 1;
						    const pages = Math.ceil(totalItems/perPage);
						    //let navPagBack = document.createElement('a');
						    let navGroupBack = document.createElement('a');
						    navGroupBack.setAttribute('href' ,'javascript:void(0)');
						    navGroupBack.innerHTML =`<i class="fa fa-angle-left" aria-hidden="true"></i>`;
						    //navPagBack.setAttribute('href' ,'javascript:void(0)');
						    //navPagBack.innerHTML ="&laquo";
						    let navGroupCount = 1;
						    let group = 1;
						    navGroupBack.addEventListener('click',(e)=>{
						    	let listPags ;
						    	if (id == "pagination1") {
						    		listPags = document.getElementById('pagination1').getElementsByTagName('a');
							    
								}else{
									listPags = bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByTagName("a");
									
								}

								if (group > 1) {
							    	for (var i = 1; i < listPags.length-1; i++) {
						    			listPags[i].style.display="none";
										
									}
						    		group--;
						    		let start = group * 10-10;
						    		let end = start + 10;
						    		var arr = [].slice.call(listPags);
						    		let lnks = arr.slice(1,listPags.length-1 );
						    		if(end > lnks.length) {
										end = lnks.length;
									}
							    	for (var i = start; i < end ; i++){ 
											lnks[i].style.display= null;
									}
							    	
								}
						    	
						    	
						    	
						    });
						    
						  
					    	if (id == "pagination1") {
					    		document.getElementById(id).appendChild(navGroupBack);
					    		//document.getElementById(id).appendChild(navPagBack);
					    		
					    		
							} else {
								let html=`<div class="links-pag-nav pagination"></div>`;
								if(bloccoAnagrafica.getElementsByClassName("links-pag-nav").length > 0){
									bloccoAnagrafica.getElementsByClassName("links-pag-nav")[0].innerHTML ="";
								}else{
									bloccoAnagrafica.getElementsByClassName("pagination")[0].insertAdjacentHTML("beforeend",html);
								}
								bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByClassName("links-pag-nav")[0].appendChild(navGroupBack);
								//bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByClassName("links-pag-nav")[0].appendChild(navPagBack);
							}
					    	
					    	
						    let pagNavNum = 0;
						    
						    for(let i = 1; i <= pages; i++) {
						    	let a = document.createElement('a');
						    	a.setAttribute('href' ,'javascript:void(0)');
						    	a.innerHTML= i;
						    	if (pagNavNum > 9) {
						    		a.style.display="none";
									
								}
						    	if(id == "pagination1"){
						    		a.addEventListener('click',(e)=>{
						    		const listPag = document.getElementById("pagination1").getElementsByTagName("a");
					    			if(listPag.length > 0){
						    				for (let pagItem of listPag) {
												pagItem.classList.remove("active");
											}
						    			}
						    			a.classList.add("active");
							    		mostraPaginaAnagrafe(i,limit,dataSet);
							    		inizializzaBlocchiAnagrafica();
							    		
							    	});
						    		document.getElementById(id).appendChild(a);
						    	}else{
						    		
						    		a.setAttribute('class','pag-tabella');
						    		a.addEventListener('click',(e)=>{
						    			mostraTabellaRigaBoll(i,perPage,dataSet,bloccoAnagrafica.getElementsByClassName("jmesa")[0]);
						    			bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByTagName("span")[0].innerHTML='';
						    			let start = i * perPage - perPage+1;
									    let end = start + perPage -1;
									    if (end > totalItems) {
											end = totalItems;
										}
						    			bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByTagName("span")[0].insertAdjacentHTML("beforeend",`\${start}-\${end} di \${totalItems} `);
						    			inizializzaBlocchiAnagrafica();
						    			const listPag = bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByTagName("a");
						    			var arr = [].slice.call(listPag);
						    			let lnks = arr.slice(1,listPag.length-1);
						    			if(end > lnks.length) {
											end = lnks.length;
										}
						    			if(lnks.length > 0){
							    				for (let pagItem of lnks) {
													pagItem.classList.remove("active");
												}
							    			}
						    			lnks[i-1].classList.add("active");
						    			
						    		});
						    		bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByClassName("links-pag-nav")[0].appendChild(a);
						    		
						    	}
						    	
						    	 pagNavNum++;
						      
						    }
						   
						    //let navPagFoward = document.createElement('a');
						    //navPagFoward.setAttribute('href' ,'javascript:void(0)');
						    //navPagFoward.innerHTML ="&raquo;";
						    let navGroupForward = document.createElement('a');
						    navGroupForward.setAttribute('href' ,'javascript:void(0)');
						    navGroupForward.innerHTML =`<i class="fa fa-angle-right" aria-hidden="true"></i>`;
						    navGroupForward.addEventListener('click',(e)=>{
						    	
						    	let listPags;
						    	if (id == "pagination1") {
						    		
						    		listPags = document.getElementById('pagination1').getElementsByTagName('a');
							    	
						    		
									
								} else {
									listPags = bloccoAnagrafica.getElementsByClassName('pagination')[0].getElementsByTagName('a');

								}
						    	
						    	
						    	if (group < Math.ceil((listPags.length)/10)) {
						    		
						    		for (var i = 1; i < listPags.length-1; i++) {
						    			listPags[i].style.display="none";
										
									}
						    		group++;
						    		let start = group * 10-10;
						    		let end = start + 10;
					    			var arr = [].slice.call(listPags);
					    			let lnks = arr.slice(1,listPags.length-1 );
					    			if(end > lnks.length) {
										end = lnks.length;
									}
						    		for (var i = start; i < end ; i++){ 
						    			
										lnks[i].style.display= null;
									}

						    	}
						    	
						    });
						    
						    
						    if (id == "pagination1") {
						    	
						    	//document.getElementById(id).appendChild(navPagFoward);
						    	document.getElementById(id).appendChild(navGroupForward);
						    	
							} else {
								
								//bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByClassName("links-pag-nav")[0].appendChild(navPagFoward);
								bloccoAnagrafica.getElementsByClassName("pagination")[0].getElementsByClassName("links-pag-nav")[0].appendChild(navGroupForward);
								

							}
					    	
					    	
						   
						  }
					 function caricaRigheTabellaBySelect(dataSet){
						 
						 let listSelect = document.getElementsByClassName('num-righe');
						 for (let select of listSelect) {
							 
							 if(select.dataset.indice !== null){
								 select.addEventListener('change',(e)=>{
									 let numRighe = e.target.value;
									 let html = mostraTabellaRigaBoll(0,numRighe,dataSet,"");
						    		let table = document.getElementById(select.dataset.id).getElementsByClassName('tabella-dettaglio');
						    		table[0].innerHTML = html;
						    		e.target.parentNode.getElementsByClassName('pagination-nav')[0].innerHTML = mostraPaginaNav(numRighe,dataSet[select.dataset.indice].righeBollettazioneList,"pagination2","",e.target.dataset.id);
						    		caricaTabellaRigheBollettazione(dataSet,parseInt(numRighe));
						    		inizializzaBlocchiAnagrafica();
						    		let totale = dataSet[select.dataset.indice].righeBollettazioneList.length;
						    		if (numRighe > totale) {
										numRighe = totale;
									}
						    		
								 });
							 }
							
						}
						 
						 
					 }
					 
					 function caricaTabellaRigheBollettazione(dataSet,numRighe){
						 let numR;
						 if(numRighe == null){
							 numR = 10;
						 }else{
							 numR = numRighe;
						 }
						 const t = document.getElementsByClassName('jmesa');
						 for (let tt of t) {
							 let indice = tt.dataset.indice;
							 let a = tt.getElementsByClassName('pag-tabella');
							 for (let a2 of a) {
								 a2.addEventListener('click',(e)=>{
									 for (let a1 of a) {
											a1.classList.remove("active");
										}
						    			let html = mostraTabellaRigaBoll(a2.dataset.offset,numR,dataSet[indice].righeBollettazioneList,"");
						    			let totale = dataSet[indice].righeBollettazioneList.length;
						    			let table = document.getElementById(a2.dataset.id).getElementsByClassName('tabella-dettaglio');
						    			table[0].innerHTML = html;
						    			a2.classList.add("active");
						    			let start = a2.dataset.offset * numR - numR+1;
									    let end = start + numR-1;
									    if (end > totale) {
											end = totale;
										}
						    			
						    			
							    		
							    	});
							}
							
						}
						 
						 inizializzaBlocchiAnagrafica();
						
						 
					 }
					 function mostraTabellaRigaBoll(page = 1, perPage = 10, tabellaData, tableSection){
						 let index, offSet;
						 const formatter=Intl.NumberFormat('it-IT', {
 							  style: 'currency',
 							  currency: 'EUR',
 							});
						 if(page == 1 || page <=0)  {
						      index = 0;
						      offSet = perPage;
						    } else if(page > tabellaData.length) {
						      index = page - 1;
						      offSet = tabellaData.length;
						    } else {
						      index = page * perPage - perPage;
						      offSet = index + perPage;
						    }
						 const rigaList = tabellaData.slice(index, offSet);
					
						 let html = `<table class="table tabella-dettaglio">
				                        <colgroup>
				                        <col style="width:50%" />
				                        <col style="width:10%" />
				                        <col style="width:5%" />
				                        <col style="width:10%" />
				                        <col style="width:5%" />
				                        <col style="width:20%" />
				                        </colgroup>
				                        <thead>
				                        <tr class="header">
				                        <td><fmt:message key="dettaglioBollettazione.label.descrizione" /></td>						
				                        <td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.importoSenzaIVA" /> (&euro;)</td>
				                        <td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.iva" /> </td>
				                        <td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.importo" /> (&euro;)</td>
				                        <td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.validata" /></td>
				                        <td><fmt:message key="dettaglioBollettazione.label.funzioni" /></td>
				                        </tr>
				                        </thead>
				                        <tbody class="tbody"></tbody></table>`;
	                        tableSection.innerHTML ="";
	                        tableSection.insertAdjacentHTML("beforeend",html);
	                        var table = tableSection.getElementsByClassName("tabella-dettaglio")[0];
	    
	                        let loop = 0;
	                        html="";
	                        for (riga of rigaList ) {

	   							let importoSenzaIVA = riga.importoSenzaIVA;
	   							let importoTotale = riga.importoTotale;
	   							importoSenzaIVA = formatter.format(importoSenzaIVA);
	   							importoTotale = formatter.format(importoTotale);
	   							let clsRiga = (loop % 2 == 0) ? "odd" : "even";
	   							let rettificato = (riga.isRettificato) ? " rettificato" : "";
	                        	html += `<tr class="riga-bollettazione \${clsRiga} \${rettificato} " data-id-riga="\${riga.id}" id="rigaBollettazione\${riga.id}"  >
	                                                        <td class="descrizione-riga">
	                                                        \${riga.descrizione}
	                                                        </td>
	                                                        <td style="text-align: right" class="importo-senza-iva-riga">
	                                                            <span>\${importoSenzaIVA}</span>
	                                                        </td>
	                                                        <td style="text-align: right" class="iva-riga">\${riga.iva}</td>
	                                                        <td style="text-align: right" class="importo-riga">
	                                                            <span>\${importoTotale}</span>
	                                                        </td>`;
	                        loop++;
	                        if (riga.supportaValidazione ) {
		                        let valido = "";
		                        if ( riga.validato == true) {				
		                        valido = `checked="checked"`;
		                        }
		                        html += ` <td style="text-align: center">
		                        						<input type="checkbox" name="" class="cmd-validato" data-id-riga="\${riga.id}" \${valido}/>
		                        						</td>`;
	                        }else{
	                        	let valido = "";
	                        	if ( riga.validato == true) {				
			                        valido = ` <i class="fa fa-check"></i> `;
			                     }
	                        	 html += ` <td style="text-align: center">\${valido}</td>`;
	                        }

	                        html += `
	                        <td>
		                        <ul class="funzioni-dettaglio">
			                        <li class="azione cmd-dettaglio">
			                            <i class="fa fa-search"></i>
			                            <fmt:message key="label.dettaglio" />
			                        </li>`;
	                        
	                        if (riga.supportaRettifica  && riga.bollettazioneChiusa  == false && GLB_MOSTRA_RETTIFICA) {
	                        	html += `<li class="azione cmd-rettifica">
	                                                <i class="fa fa-edit"></i>
	                                                <fmt:message key="label.rettifica" />
	                                            </li>`;
	                        }
	                        if (riga.supportaCancellazione && riga.bollettazioneChiusa == false) {
	                        	html += `<li class="azione cmd-cancella" >
	                                                <i class="fa fa-trash-o"></i>
	                                                <fmt:message key="label.elimina" />
	                                            </li> `;
	                        }
	                        if (riga.daRateizzare == true) {
	                        	html += `<li class="azione cmd-elencorate" >
	                                                <i class="fa fa-file-invoice-dollar"></i>
	                                                <fmt:message key="bollgestione.rata.elencorate" />
	                                            </li> `;
	                        } 
	                        if (riga.posizioniDebitorieRaggruppate == false) {
	                        
		                        for (posizione of riga.idDettPosizioniDebitorie) {
		                        	html += `<vbg-dettaglio-posizione-debitoria
		                                                    id-posizione="\${posizione}"
		                                                    mostra-testo="false"
		                                                    fetch-ref='data-by-id' >
		                                                </vbg-dettaglio-posizione-debitoria> `;
		                        	html += `<span class="cmd-anteprima-avvisatura" data-id-posizione="\${posizione}" ></span > `;
		                        }
	                         	
	                        }
	                        html +=`</ul></td></tr>`;			                 
	                      }
	                      table.insertAdjacentHTML("beforeend",html);
	                      
						 
					 }
					 
					 
					 function mostraPaginaAnagrafe(page = 1, perPage = 2,dataSet,newLimit= null){
						 const formatter=Intl.NumberFormat('it-IT', {
  							  style: 'currency',
  							  currency: 'EUR',
  							});
						 
						 
						 var html = `
								<vbg-fetch-ref id='data-by-id' method='get' response-format='json' request-format='form'
				                    url='../dettposizionedebitoria/ajaxDettaglioPosizione.htm'>
				                </vbg-fetch-ref>`;
				         let container = document.getElementById("anag_boll_section");
				         container.innerHTML='';
				         container.insertAdjacentHTML("beforeend",html);
						 let index, offSet
						 
						    
						    if(page == 1 || page <=0)  {
						      index = 0;
						      offSet = perPage;
						    } else if(page > dataSet.length) {
						      index = page - 1;
						      offSet = dataSet.length;
						    } else {
						      index = page * perPage - perPage;
						      offSet = index + perPage;
						    }
						    const slicedItems = dataSet.slice(index, offSet);
						    let i=0;
						    for(anagBoll of slicedItems ){
								
				                
								html = `<div class="blocco-anagrafica"
						                    data-id="\${anagBoll.id}"
						                    id="blocco-anagrafica-\${anagBoll.id}"
						                    data-posizione-presente="\${anagBoll.dettaglioPosizioneDebitoriaPresente}">
						                    <h3>\${anagBoll.nominativo}</h3></div>`;
						      
						      container.insertAdjacentHTML("beforeend",html);
						      let bloccoAnagrafica = document.getElementById("blocco-anagrafica-"+anagBoll.id);
						      
						      <c:if test="${visualizzaBottoneDettaglio}">
						      html="";
						      html=`<a href="javascript:void(0)" onclick="showDettaglioBollettazioneTot(${dettaglioBollettazione.id},\${anagBoll.id})"><span class="icon-wrapper" style="color:#333333;"><i class="fas fa-list-ul fa-lg" style="padding-right: 6px;"></i><span class="icon-text">Dettaglio</span></span></a>`;
						      bloccoAnagrafica.insertAdjacentHTML("beforeend",html);
						      </c:if>
						      
						      html="";
							  if(anagBoll.checkPosizioneDebitoriaRaggruppata == true && anagBoll.dettaglioPosizioneDebitoriaPresente == true){
		                        	 for (posizione of anagBoll.posizioniDebitorie) {
				                        	html += `<vbg-dettaglio-posizione-debitoria 
				                        		id-posizione="\${posizione}"
				                        		fetch-ref='data-by-id'>
				                    		</vbg-dettaglio-posizione-debitoria>
				                    		<span class="cmd-anteprima-avvisatura" data-id-posizione="\${posizione}"></span>`;
				                    		
				                        }
		                        	 bloccoAnagrafica.insertAdjacentHTML("beforeend",html);
		                        	
		                        	 
						          }
							  if(anagBoll.cfpivaPresente == false){
					        	  html = `  <div class="warning">Il soggetto non ha
				                            codice fiscale o partita iva. Non sarà
				                            possibile inviare gli oneri al sistema di
				                            pagamento</div>`;
				                  bloccoAnagrafica.insertAdjacentHTML("beforeend",html);
					           }


					          html = `<div id="status_msg_\${anagBoll.id}"
					                        class="success_header alert alert-success"
					                        style="display: none;">Aggiornamento
					                        eseguito con successo</div>

					                    <div class="jmesa"
					                        id="bloccoTabella\${anagBoll.id}"
					                        data-cfpivapresente="\${anagBoll.cfpivaPresente}"
					                        data-dettaglioposizionedebitoriapresente="\${anagBoll.dettaglioPosizioneDebitoriaPresente}"
					                        data-indice="\${i}"></div>`;
					          bloccoAnagrafica.insertAdjacentHTML("beforeend",html);
					          let tabellaSection = document.getElementById("bloccoTabella"+anagBoll.id);
					          if (newLimit !== null) {
					        	  mostraTabellaRigaBoll(0,newLimit, anagBoll.righeBollettazioneList,tabellaSection);
							} else {
								 mostraTabellaRigaBoll(0,limit, anagBoll.righeBollettazioneList,tabellaSection);
							}
					         
					          html =`<ul class="funzioni-dettaglio">`;
								if (anagBoll.dettaglioPosizioneDebitoriaPresente == false && GLB_MOSTRA_AGGIUNGI_RIGA) {
									html+= ` <li class="cmd-aggiungi">
			                            <i class="fa fa-plus"></i>
			                            <fmt:message key="label.aggiungi" />
			                        </li>`;
								}
								if (anagBoll.dettaglioPosizioneDebitoriaPresente == true) {
									<%-- AL MOMENTO NON HA SENSO CHE LA FUNZIONE FA UNA LOCATION RELOAD --%>
								//	html += `  <li class="cmd-verifica-stato">
			                    //        <i class="fa fa-search"></i>
			                    //        <fmt:message key="label.verifica-stato-pagamenti" />
			                    //    </li>`;									
								}
								if (anagBoll.daRateizzare == true) {
									html += `&nbsp;<li class="cmd-elencorate-anagrafica">
										<i class="fa fa-file-invoice-dollar"></i>
										<fmt:message key="bollgestione.rata.elencorate" />
			                        </li>`;
								}
								html += `</ul>`;
								bloccoAnagrafica.insertAdjacentHTML("beforeend",html);
								if (newLimit !== null) {
									iniziallizzaRigheCounter("pagination2",bloccoAnagrafica,anagBoll.righeBollettazioneList,tabellaSection,newLimit);
									mostraPaginaNav(newLimit,anagBoll.righeBollettazioneList,"pagination2",bloccoAnagrafica);
								} else {
									iniziallizzaRigheCounter("pagination2",bloccoAnagrafica,anagBoll.righeBollettazioneList,tabellaSection);
									mostraPaginaNav(limit,anagBoll.righeBollettazioneList,"pagination2",bloccoAnagrafica);
								}
								
								
								i++;
								
								
						    }
				
						         
						    inizializzaBlocchiAnagrafica();
						 
					 }
				

					async function ricaricaBloccoAnagrafica(idAnagtafica, idBloccoAnagrafica, idBloccoTabella) {

					window.vbg.mostraModalCaricamento();
				    const resp = await fetch("../bollgestione/ajaxGetDettaglioBollettazione.htm?idBollettazione=" + ${ dettaglioBollettazione.id }, {
				        method: "GET",
				        cache: "no-cache",
				        headers: {
				            'Content-Type': 'application/json'
				        }
				    });

				    const dettaglioBollettazione = await resp.json();
				    
				    var table = document.getElementById(idBloccoTabella);
				    var bloccoAnagrafica = document.getElementById(idBloccoAnagrafica);
				    var numRighe = Number(bloccoAnagrafica.getElementsByClassName("num-righe")[0].value);
				    dettaglioBollettazione.anagraficaBollettazioneList.forEach(anagrafica => {
				        if (anagrafica.id == idAnagtafica) {

				            mostraTabellaRigaBoll(1, numRighe, anagrafica.righeBollettazioneList, table);
				            mostraPaginaNav(numRighe, anagrafica.righeBollettazioneList, "pagination2", bloccoAnagrafica);
				            inizializzaSingoloBloccoAnagrafica($(bloccoAnagrafica));
				           
				           return;
				        }
				    });
				    window.vbg.nascondiModalCaricamento();



				}
				async function tutteRigheValidate(anagraficaList) {
					  let ris = true;

					  if (anagraficaList !== null) {
					      anagraficaList.forEach(anagrafica => {					      
				    	  if(anagrafica.dettaglioPosizioneDebitoriaPresente){
				    		ris = true;
				    		return;
				    	  }
					      anagrafica.righeBollettazioneList.forEach(riga => {
					        if (!riga.validato) {
					          ris = false;
					        }
					      })
					    });
					  } else {
						  window.vbg.mostraModalCaricamento();
					    const resp = await fetch("../bollgestione/ajaxGetDettaglioBollettazione.htm?idBollettazione=" + ${ dettaglioBollettazione.id }, {
					      method: "GET",
					      cache: "no-cache",
					      headers: {
					        'Content-Type': 'application/json'
					      }
					    });

					    const dettaglioBollettazione = await resp.json();
					    dettaglioBollettazione.anagraficaBollettazioneList.forEach(anagrafica => {
					       if(anagrafica.dettaglioPosizioneDebitoriaPresente){
						   	ris = true;
						   	return;
					       }				    	
					      anagrafica.righeBollettazioneList.forEach(riga => {
					        if (!riga.validato) {
					          ris = false;
					        }
					      });
					    });
					  }
					  
					  // $("#btnInviaSistemaPagamenti").toggle(ris);
						var sezione_valida_tutti = $('sel_des_tutto');
						if(sezione_valida_tutti != null){
							if(ris){
								$('#valida_tutti').prop("checked",true);
							}else {
								$('#valida_tutti').prop("checked",false);
							}
						}
						window.vbg.nascondiModalCaricamento();


					}
					
					
					$(document).ready(function(){

						
						function _gestioneErrori(errJson){
				     		console.log(errJson.error);
				     		alert( errJson.error);
							}
						
						
						function searchFilter(obj, filtro){
							result = [];
							for (var i = 0; i < obj.length; i++) {
								
								  
								  if (filtro.isPosizioni() && filtro.isTesto()){
									  if(obj[i].nominativo.toUpperCase().indexOf(filtro.getFilterTesto()) > -1 && obj[i].dettaglioPosizioneDebitoriaPresente ==  filtro.getFilterPosizioni()){
										  result.push(obj[i]);
									  }
									continue;
								  }else if(filtro.isPosizioni()){ // solo posizioni
									  if(obj[i].dettaglioPosizioneDebitoriaPresente ==  filtro.getFilterPosizioni()){
										  result.push(obj[i]);
								     }
									continue;						
								  }else{ // solo testo
									  if( obj[i].nominativo.toUpperCase().indexOf(filtro.getFilterTesto()) > -1) {
										  result.push(obj[i]);
								    	  continue;
									  }
								  }


							      let righe = obj[i].righeBollettazioneList;
							      let found = false;      
							    	  for ( var key in righe) {
							    		  for ( var key2 in righe[key]) {
							    			  if (key2 === 'importoSenzaIVA' || key2 === 'importoTotale' || key2 === 'iva') {
							    				  
												let num = righe[key][key2];
												num = num.toString().replace('.',',');
												if (num.toString().toUpperCase().indexOf(filtro.getFilterTesto()) > -1) {
								    				  found = true;
												}
											  }else{
												 if (righe[key][key2].toString().toUpperCase().indexOf(filtro.getFilterTesto()) > -1) {
								    				  found = true;
												}
											}
										  }

									  	} 
							    	  if (found) {
										result.push(obj[i]);
									}

							    }
							
							console.log(result);
							return result;
						}
						
						class FiltroRicerche {
							
							 input = document.getElementById("input_ricerca");
							 posizioni  = document.getElementById("filtro_posizioni_id");
							 
							 getFilterTesto(){
								 return this.input.value.toUpperCase();								 
							 }
							 
							 getFilterPosizioni(){								 
								 return this.posizioni.value === 'true';								 
							 }
							 
							 isTesto(){
								let testo = this.getFilterTesto();
								return !(testo === '' || testo === null);
							 }
							 
							 isPosizioni(){
								return !(this.posizioni.value === '' || this.posizioni.value === null);
							 }
							 
							 isFiltroSelezionato(){
								 return (this.isTesto() || this.isPosizioni());
							 }
						}
						
						async function filtraTesto() {
							
							window.vbg.mostraModalCaricamento();
							 const resp = await fetch("../bollgestione/ajaxGetDettaglioBollettazione.htm?idBollettazione="+${dettaglioBollettazione.id},{
					                method: "GET",
					                cache: "no-cache",
					                headers: {
					                    'Content-Type': 'application/json'
					                  }
								});
							 
							 if( await resp.status == 200){
									const dettBoll = await resp.json();
									console.log(dettBoll);
									let filtro = new FiltroRicerche();
									
									let res = null;
									if (filtro.isFiltroSelezionato()) {
										res = searchFilter(dettBoll.anagraficaBollettazioneList, filtro);
									}else{
										res = dettBoll.anagraficaBollettazioneList;										
									}
									window.vbg.nascondiModalCaricamento();
									//enableFunctions();
									const anagBollSec = document.getElementById("anag_boll_section");
									document.getElementById("pagination1").innerHTML = '';
									if(res != null){
										anagBollSec.style.display = '';
							            mostraPaginaNav(limit,res,"pagination1",null);
							            mostraPaginaAnagrafe(0,limit,res,100);
							            tutteRigheValidate(res);
									}else{
										anagBollSec.style.display = 'none';
									}
									
				            		
				            	} else {
				            		_gestioneErrori(await response.json());
				            	}

						}
						
						document.getElementById('input_ricerca').addEventListener('keypress',(event)=>{
							
							if (event.key === "Enter") {
								filtraTesto();
						    }
							
						});
						
						document.getElementById('filtro_posizioni_id').addEventListener('change',(event)=>{
								filtraTesto();
						});
						
						
						var idBollettazione = ${dettaglioBollettazione.id};
						validaTutti(idBollettazione);
						
						$(".cmd-aggiorna-scadenza").on('click',
							function(e){
													
								callAsync({
									url : "ajaxAggiornaScadenza.htm",
									data : {
										idBollettazione : idBollettazione,
										dataScadenza: document.getElementById('dataScadenza_id').value
									},
									success : function(data) {				
										alert('Scadenza aggiornata');
									},
									error : function(event, xhr, ajaxOptions, thrownError){
										valida_tutti.prop('checked',!checkIt);
										console.error("Si è verificato un errore ", event, xhr, ajaxOptions, thrownError);
										alert("Si è verificato un errore");
									}
								});
							}
						);
												
					});
					
					
					
				})(jQuery);
				
				const getSnippetOggettoAnteprima =  (dettIdPosizioneDebitoria, idBollettazione) =>{
					let convertiPDF = false;
					let url = `../bollgestione/ajaxPreviewLettera.htm?dettIdPosizioneDebitoria=\${dettIdPosizioneDebitoria}&idBollettazione=\${idBollettazione}`;
					return `<a href="\${url}" target="blank" title="Cliccare per scaricare l'anteprima del documento"><i class="fas fa-eye fa-lg"></i></a>`;				
				}
				
				vbg.ready(function(){
					function aggiungiEventoReload(){
						let det = document.querySelectorAll('vbg-dettaglio-posizione-debitoria');
						det.forEach((vbgDettaglioPosizione)=>{
							vbgDettaglioPosizione.addEventListener('stato-modificato',()=>{
								window.vbg.mostraModalCaricamento();
								document.location.reload();
							});
							
							
							
						});
						
					}
					
					
					
					
					aggiungiEventoReload();
					
				});
				
			</script>

            <div id="dialogDettaglioRiga"></div>
            
            <div id="dialogDettaglioRate"></div>
            
            
            
            <vbg-modal id="pop-up-boll"></vbg-modal>
            
            
            

            <jsp:include page="./popupRettifica.jsp" />

            <jsp:include page="./popupAggiungi.jsp" />
            


			<div id="anag_boll_section">
				
			
			</div>
			<div id="pagination1" class="pagination"></div>
           
            <script type='text/javascript'>
							
				function openExport(){		
					var url  = URLDecode('${_urlback}');			
					ajaxHistorySet(url);			
					setTimeout("doHref('../bollgestione/createExportModalitaPentaho.htm?idBollettazione=${dettaglioBollettazione.id}','')",10);
				}
				
				function openComunicazioniMassive(){		
					var url  = URLDecode('${_urlback}');			
					ajaxHistorySet(url);			
					setTimeout("doHref('../comunicazionibollettazione/list.htm?idBollettazione=${dettaglioBollettazione.id}','')",10);
				}

				function ajaxHistorySet(url){
					
					var jhqr = jQuery.ajax({
						  url: '../history/ajaxSet.htm?ReturnTo='+url,
						  context: document.body,
						  cache: false,				
						  dataType: "html",
						  success: function(data) { 				   
							} 
						});	
				}
				
			
				<%-- fx post json nodo pagamenti --%>
				
				var GLB_NODO_PAGAMENTI_INVIATO = false;
				var GLB_TIMEOUT_RICORSIVO = null;
				
				const delay = ms => new Promise(async res => setTimeout(res, ms));			 
				
				async function inviaANodo(){
					
					if (confirm('<fmt:message key="javascript.confirm.bollettazione.invio-posizioni" />')){
						let data = new URLSearchParams(new FormData(document.inviodati));
						
						GLB_NODO_PAGAMENTI_INVIATO = true;
						
						nascondiFunzioniModificaDati();
						
						let response = fetch('inviaPosizioneDebitoria.htm', {
							method: "POST",
			                cache: "no-cache",
			                body: data       
							}).then((response) => response.json())
							  .then((data) => verificaRisultatoBollettazione(data));
							
						vbg.mostraModalCaricamento();
						await delay(5000);
						verificaElaborazioni();
						vbg.nascondiModalCaricamento();
					}
				}
				
			 	function verificaRisultatoBollettazione(myJson){
				 
					if(myJson.descrizione!=''){
						console.log(myJson.descrizione);
						contenutoModalElaborazioni(myJson.descrizione, false);						
						GLB_NODO_PAGAMENTI_INVIATO = false;
					}else{
						document.location.href='${pageContext.request.contextPath}/bollgestione/view.htm?codice=${dettaglioBollettazione.id}';
					}
				}
				
				async function verificaElaborazioni(){
					
					let el = document.getElementById('elab_in_corso');
					let idBollettazioneTestata = el.dataset.id;
					
					if(idBollettazioneTestata!=''){
											
						nascondiFunzioniModificaDati();
												
						await delay(1000);
						
						const data = new URLSearchParams();
							  data.append('idBollettazioneTestata', idBollettazioneTestata);
					       
						let url = "${pageContext.request.contextPath}/bollgestione/ajaxBollettazioneElaborazioneAttiva.htm";
					 	const response = await fetch(url, {
							method: "POST",
					              cache: "no-cache",
					              body: data       
						});		 	
					 	if (response.status !== 200) {
						      let errore = await response.text();
						      console.error(errore);
						      throw errore;
						 }
						 let elaborazioni = await response.json();
						 let trovata = elaborazioni.in_elaborazione;
					 	if( trovata ){		 		 								
					 		statoElaborazione();
					 	}else{			 		
					 		mostraFunzioniModificaDati();
					 	}
										 	
					}					 	
				}
				

				
				async function statoElaborazione(){
					
					let el = document.getElementById('elab_in_corso');
					let idBollettazioneTestata = el.dataset.id;
					
					if(idBollettazioneTestata!=''){
					
						let data = new URLSearchParams();
						 	data.append('idBollettazioneTestata', idBollettazioneTestata);
					       
						
						
						let url = "${pageContext.request.contextPath}/ajax/ajaxBollettazioneVerificaElaborazioneNodoPagamenti.htm";
					 	const response = await fetch(url, {
							method: "POST",
			                cache: "no-cache",
			                body: data       
						});
						
					 	if (response.status !== 200) {
						      let errore = await response.text();
						      console.error(errore);
						      throw errore;
						}
					 	
					 	
						let elaborazioni = await response.json();
						 
						 if(elaborazioni.elaborazione_in_corso){
							 
							 GLB_NODO_PAGAMENTI_INVIATO = true;
							 nascondiInviaPosizioniANodo();
							 contenutoModalElaborazioni(`<div class="elaborazione">&nbsp;Elaborazione dei bollettini in corso: elaborati \${elaborazioni.elaborati} su \${elaborazioni.totale}</div>`, true);
						 }
						 if(GLB_NODO_PAGAMENTI_INVIATO){
						    await delay(5000);
						 	statoElaborazione();
						 	
						 }
					}
				 	
				}				
				
				function nascondiInviaPosizioniANodo(){
					if(document.getElementById('btnInviaSistemaPagamenti')){
						document.getElementById('btnInviaSistemaPagamenti').style.display='none';
					}
				}
				function mostraInviaPosizioniANodo(){
					if(document.getElementById('btnInviaSistemaPagamenti')){
						document.getElementById('btnInviaSistemaPagamenti').style.display='';
					}	
				}
				
				function contenutoModalElaborazioni(contenuto, doSpin){
					
					let el = document.getElementById('elab_in_corso');
					
					
					let spinner = `<div style="padding: 20px"><i class="fa fa-circle-o-notch fa-spin fa-5x"></i></div>`;
					
					el.innerHTML = doSpin ? spinner + contenuto : contenuto;
					el.style.display='';
				}
				
				
				function nascondiFunzioniModificaDati(){
					
					document.getElementById('container-contenuti').style.display='none';
					
					var elList = document.querySelectorAll('.funzioni-modifica-dati');
					elList.forEach(el => el.style.display = 'none');
					
					 nascondiInviaPosizioniANodo();
				}
				function mostraFunzioniModificaDati(){
					
					document.getElementById('container-contenuti').style.display='';
					
					var elList = document.querySelectorAll('.funzioni-modifica-dati');
					elList.forEach(el => el.style.display = '');
				}
				
				function apriModalMessaggi(){
					
					if(GLB_NODO_PAGAMENTI_INVIATO){
						alert(`Attenzione! È in corso l'invio delle posizioni debitorie. Attendere il completamento dell'operazione.`);
					}
				}
				
				
			</script>
        </spring-form:form>
    </div>
    <div id="functions">
        <ul>
            <c:if test="${dettaglioBollettazione.id==null}">
                <li class="funzioni-modifica-dati"><a 
                    href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
                            key="button.insert" /></a></li>
            </c:if>
            <c:if
                test="${dettaglioBollettazione.id!=null && dettaglioBollettazione.bollettazioneChiusa eq false}">
                <li class="funzioni-modifica-dati"><a  class="funzioni-modifica-dati"
                    href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
                            key="button.delete" /></a></li>
                
                    <li class="funzioni-modifica-dati" id="btnInviaSistemaPagamenti"><a 
                        href="javascript:inviaANodo()"><fmt:message
                                key="button.inviaSistemaPagamenti" /> </a></li>
                
            </c:if>
            <c:if test="${dettaglioBollettazione.id!=null}">
                <li class="funzioni-modifica-dati"><a href="javascript:openExport()"><fmt:message key="button.esporta" /> </a></li>
            </c:if>
<c:if test="${mostraFunzioneComunicazioniMassive eq true}">
             <li class="funzioni-modifica-dati"><a href="javascript:openComunicazioniMassive()"><fmt:message
                        key="button.comunicazioni.massive" /></a></li>
</c:if>            
            <li><a href="javascript:doHref('list.htm','')"><fmt:message
                        key="button.back" /></a></li>
        </ul>
    </div>




    <script type="text/javascript"
        src="${pageContext.request.contextPath}/scripts/jquery.statoposizionedebitoria/jquery.statoposizionedebitoria.js"></script>
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/scripts/jquery.statoposizionedebitoria/jquery.statoposizionedebitoria.css">
        
			<script type="text/javascript">
			
			
						vbg.ready(async function(){
						
						
						verificaElaborazioni();
						
					

					});

			</script>
			
			<script type="text/javascript">
			
			  async function showDettaglioBollettazioneTot(idbollettazione, codiceanagrafe){
				  
				  
				    window.vbg.mostraModalCaricamento();
				    
				    try{
				    const resp = await fetch("../bollgestione/ajaxGetSummaryBollettazione.htm?idBollettazione="+idbollettazione + "&" + "codiceAnagrafe=" + codiceanagrafe, {
				        method: "GET",
				        cache: "no-cache",
				        headers: {
				            'Content-Type': 'application/json'
				        }
				    });

				    if(!resp.ok){
				        let errorMsg = "Errore nel recupero dei dati.";
				        try {
				            const err = await resp.json();
				            errorMsg = err.message || JSON.stringify(err);
				        } catch (_) {
				            errorMsg = await resp.text();
				        }
				        throw new Error(errorMsg);
				    }
				    
				    
				    const dettaglioBollettazione = await resp.json();
				    const modal = document.getElementById('pop-up-boll');
				    let myhtml = dettaglioBollettazione.html;
				    modal.innerHTML = myhtml;
				    modal.open();
				    }catch(e){
				    	console.error("Errore nel caricamento della bollettazione:", e);
				        alert("Errore nel recupero dei dati: " + e.message);
				    }finally {
				        window.vbg.nascondiModalCaricamento();
				    }

		    }
			
			  
			  
			  
			  async function scaricaPDF(idbollettazione, codiceanagrafe) {
				  let url = null;
				  window.vbg.mostraModalCaricamento();

				  try {
				    const response = await fetch("../bollgestione/ajaxGetSummaryBollettazionePdf.htm?idBollettazione="+idbollettazione + "&" + "codiceAnagrafe=" + codiceanagrafe);

				    if (!response.ok) {
				      const errorText = await response.text();
				      throw new Error(errorText || "Errore generico durante il download");
				    }

				    const blob = await response.blob();
				    url = window.URL.createObjectURL(blob);

				    const a = document.createElement('a');
				    a.href = url;

				    a.download = 'report.pdf';

				    document.body.appendChild(a);
				    a.click();
				    document.body.removeChild(a);
				  } catch (err) {
				    alert("Errore nel download: " + err.message);
				  } finally {
				    if (url) {
				      window.URL.revokeObjectURL(url);
				    }
				    window.vbg.nascondiModalCaricamento();
				  }
				} 
			</script>
</body>
</html>