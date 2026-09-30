<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.protocollo.schemas.messages.ArrayOfAllegatoResponseType"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page
	import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.dati_protocollo" />
</title>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="label.dati_protocollo" /> </span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../protocollazione/leggiProtocollo" />
	</jsp:include>
	<c:choose>
		<c:when test="${protocolloCommand.provenienza ne 'AZP' }">
			<c:import url="/ajax/dettaglioIstanza.htm">
				<c:param name="codIstanza">${protocolloCommand.entity.id.codice}</c:param>
			</c:import>
		</c:when>
	</c:choose>		
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="protocolloCommand" name="inviodati">

			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="protocolloCommand" />
			</jsp:include>
			<c:set var="_NUMDATAPROTMITT"><%=request
							.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NUMDATAPROTMITT)%></c:set>
			<c:set var="_NOALLEGATI"><%=request
							.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NOALLEGATI)%></c:set>
			<c:set var="_GESTISCI_FASCICOLAZIONE"><%=request
							.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE)%></c:set>
			<c:set var="_DESTINATARICC"><%=request
							.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_DESTINATARICC)%></c:set>
			<fieldset>
				<legend>
					<fmt:message key="label.dati_protocollo" />
				</legend>
				<br class="break" />
				<table width="100%">
				   
				    <c:set value="true" var="isNumeroProtocollo" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.numeroProtocollo}">
				      <c:set value="false" var="isNumeroProtocollo" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isNumeroProtocollo eq true}">
					<tr>
						<td width="10%"><fmt:message key="label.numero_protocollo" />
						</td>
						<td><b>${datiProtocolloLetto.numeroProtocollo}</b>
						</td>
					</tr>
					</c:if>
					<c:set value="true" var="isDataProtocollo" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli &&  empty datiProtocolloLetto.dataProtocollo}">
				      <c:set value="false" var="isDataProtocollo" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isDataProtocollo eq true}">
					<tr>
						<td><fmt:message key="label.data_protocollo" />
						</td>
						<td><b>${datiProtocolloLetto.dataProtocollo}</b>
						</td>
					</tr>
					</c:if>
					<c:set value="true" var="isOggetto" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli &&  empty datiProtocolloLetto.oggetto}">
				      <c:set value="false" var="isOggetto" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isOggetto eq true}">
					<tr>
						<td><fmt:message key="label.oggetto" />
						</td>
						<td><b>${datiProtocolloLetto.oggetto}</b>
						</td>
					</tr>
					</c:if>
					<c:set value="true" var="isMittenteInternoDescrizione" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.mittenteInternoDescrizione}">
				      <c:set value="false" var="isMittenteInternoDescrizione" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isMittenteInternoDescrizione eq true}">
					<tr> 
						<td><fmt:message key="label.mittente_interno" />
						</td>
						<td><b>${datiProtocolloLetto.mittenteInternoDescrizione}</b>
						</td>
					</tr>
					</c:if>
					
					<c:set value="true" var="isFlusso" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli &&  fn:length(flussiInRequest)==0}">
				      <c:set value="false" var="isFlusso" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isFlusso eq true}">
					<tr>
						<td><fmt:message key="label.protocollo_flusso" />
						</td>
						<td><c:forEach items="${flussiInRequest}" var="flusso">
								<c:if test="${flusso.codice eq datiProtocolloLetto.origine}">
									<b>${flusso.descrizione}</b>
								</c:if>
							</c:forEach>
						</td>
					</tr>
                    </c:if>

					<c:set value="true" var="isClassificaDescrizione" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.classificaDescrizione}">
				      <c:set value="false" var="isClassificaDescrizione" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isClassificaDescrizione eq true}">
					<tr>
						<td width="15%"><fmt:message key="label.classifica" />
						</td>
						<td><b>${datiProtocolloLetto.classificaDescrizione}</b>
						</td>
					</tr>
					</c:if>
					
					
					<c:set value="true" var="isTipoDocumentoDescrizione" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.tipoDocumentoDescrizione}">
				      <c:set value="false" var="isTipoDocumentoDescrizione" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isTipoDocumentoDescrizione eq true}">
					<tr>
						<td><fmt:message key="label.tipo_documento" />
						</td>
						<td><b>${datiProtocolloLetto.tipoDocumentoDescrizione}</b>
						</td>
					</tr>
					</c:if>
					<%-- 
					<c:set value="true" var="isinCaricoADescrizionee" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.inCaricoADescrizione}">
				      <c:set value="false" var="isinCaricoADescrizionee" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isinCaricoADescrizionee eq true}">
					<tr>
						<td><fmt:message key="label.in_carico_a" />
						</td>
						<td><b>${datiProtocolloLetto.inCaricoADescrizione}</b>
						</td>
					</tr>
					</c:if>
					--%>
					
					
					<c:set value="true" var="isNumeroPratica" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.numeroPratica}">
				      <c:set value="false" var="isNumeroPratica" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isNumeroPratica eq true}">
					<tr>
						<td><fmt:message key="label.numero_pratica" />
						</td>
						<td><b>${datiProtocolloLetto.numeroPratica}</b>
						</td>
					</tr>
					</c:if>
					<c:set value="true" var="isannoNumeroPratica" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.annoNumeroPratica}">
				      <c:set value="false" var="isannoNumeroPratica" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isannoNumeroPratica eq true}">
					<tr>
						<td><fmt:message key="label.anno_numero_pratica" />
						</td>
						<td><b>${datiProtocolloLetto.annoNumeroPratica}</b>
						</td>
					</tr>
					</c:if>
					
					<c:set value="true" var="isdataInserimento" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.dataInserimento}">
				      <c:set value="false" var="isdataInserimento" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isdataInserimento eq true}">
					<tr>
						<td><fmt:message key="label.data_inserimento" />
						</td>
						<td><b>${datiProtocolloLetto.dataInserimento}</b>
						</td>
					</tr>
					</c:if>
					
					<c:set value="true" var="isnumeroProtocolloMittente" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.numeroProtocolloMittente}">
				      <c:set value="false" var="isnumeroProtocolloMittente" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isnumeroProtocolloMittente eq true}">
					<tr>
						<td width="15%"><fmt:message
								key="label.numero_protocollo_mittente" />
						</td>
						<td><b>${datiProtocolloLetto.numeroProtocolloMittente}</b>
						</td>
					</tr>
					</c:if>
					
					
					<c:set value="true" var="isdataProtocolloMittente" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.dataProtocolloMittente}">
				      <c:set value="false" var="isdataProtocolloMittente" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isdataProtocolloMittente eq true}">
					<tr>
						<td><fmt:message key="label.data_protocollo_mittente" />
						</td>
						<td><b>${datiProtocolloLetto.dataProtocolloMittente}</b>
						</td>
					</tr>
					</c:if>
					
					<c:set value="true" var="isannullato" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.annullato}">
				      <c:set value="false" var="isannullato" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isannullato eq true}">
					<tr>
						<td><fmt:message key="label.annullato" />
						</td>
						<td><b>${datiProtocolloLetto.annullato}</b>
						</td>
					</tr>
					</c:if>
					<c:set value="true" var="ismotivoAnnullamento" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.motivoAnnullamento}">
				      <c:set value="false" var="ismotivoAnnullamento" scope="page"></c:set>
				    </c:if>
				    <c:if test="${ismotivoAnnullamento eq true}">
					<tr>
						<td><fmt:message key="label.motivo_annullamento" />
						</td>
						<td><b>${datiProtocolloLetto.motivoAnnullamento}</b>
						</td>
					</tr>
					</c:if>
					<c:set value="true" var="isdataAnnullamento" scope="page"></c:set>
				    <c:if test="${!isVisualizzaValoriNulli && empty datiProtocolloLetto.dataAnnullamento}">
				      <c:set value="false" var="isdataAnnullamento" scope="page"></c:set>
				    </c:if>
				    <c:if test="${isdataAnnullamento eq true}">
					<tr>
						<td><fmt:message key="label.data_annullamento" />
						</td>
						<td><b>${datiProtocolloLetto.dataAnnullamento}</b>
						</td>
					</tr>
					</c:if>
				</table>
             
				<br class="break" />
				<%--  LISTA MITTENTI --%>
				<fieldset>
					<legend>
							<fmt:message key="label.mittenti" />
					</legend>
					<c:choose>
					<%-- CASO DI PROTOCOLLO ARRIVO e INTERNO --%>
					<c:when test="${datiProtocolloLetto.origine eq 'A' || datiProtocolloLetto.origine eq 'I'}">
						<div class="jmesa">
							<div class="jmesa">
							<table border="0" width="100%" cellpadding="0" cellspacing="0"
								class="table">
								<thead>
									<tr class="header">
										<td width="5%"><fmt:message key="label.codice" />
										</td>
										<td><fmt:message key="label.descrizione" />
										</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${datiProtocolloLetto.mittentiDestinatari.mittDestOutType}"
										var="current" varStatus="a">
										<tr>
											<td>${current.idSoggetto}</td>
											<td>${current.cognomeNome}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						</div>
					</c:when>
					<%-- CASO DI PROTOCOLLO PARTENZA --%>
					<c:otherwise>
						<div class="jmesa">
							<table border="0" width="100%" cellpadding="0" cellspacing="0"
								class="table">
								<thead>
									<tr class="header">
										<td width="5%"><fmt:message key="label.codice" />
										</td>
										<td><fmt:message key="label.descrizione" />
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
									    <td>${datiProtocolloLetto.inCaricoA}</td>
										<td>${datiProtocolloLetto.inCaricoADescrizione}</td>
									</tr>
								</tbody>
							</table>
						</div>			
					</c:otherwise>
					</c:choose>
				</fieldset>
				<%-- LISTA DESTINATARI --%>
				<fieldset>
					<legend>
							<fmt:message key="label.destinatari" />
					</legend>
					<c:choose>
					<%-- CASO DI PROTOCOLLO ARRIVO o INTERNO --%>
					<c:when test="${datiProtocolloLetto.origine eq 'A' || datiProtocolloLetto.origine eq 'I'}">
						<div class="jmesa">
							<table border="0" width="100%" cellpadding="0" cellspacing="0"
								class="table">
								<thead>
									<tr class="header">
										<td width="5%"><fmt:message key="label.codice" />
										</td>
										<td><fmt:message key="label.descrizione" />
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${datiProtocolloLetto.inCaricoA}</td>
										<td>${datiProtocolloLetto.inCaricoADescrizione}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:when>
					<%-- CASO DI PROTOCOLLO PARTENZA --%>
					<c:otherwise>
						<div class="jmesa">
							<table border="0" width="100%" cellpadding="0" cellspacing="0"
								class="table">
								<thead>
									<tr class="header">
										<td width="5%"><fmt:message key="label.codice" />
										</td>
										<td><fmt:message key="label.descrizione" />
										</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${datiProtocolloLetto.mittentiDestinatari.mittDestOutType}"
										var="current" varStatus="a">
										<tr>
											<td>${current.idSoggetto}</td>
											<td>${current.cognomeNome}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:otherwise>
					</c:choose>
				</fieldset>
				
				
				<br class="break" />
				<fieldset>
					<legend>
						<fmt:message key="label.allegati" />
					</legend>
					<div class="jmesa">
						<table border="0" width="100%" cellpadding="1" cellspacing="1" class="table">
							<thead>
								<tr class="header">
									<td width="80%"><fmt:message key="label.file" /></td>
									<td><fmt:message key="label.content_type" /></td>
									<c:choose>
										<c:when test="${protocolloCommand.provenienza ne 'AZP' }">
											<td width="20%"><init:help idHelp="help_salva_doc_protocollo_id" textKey="help.salva_doc_da_protocollo" /><fmt:message key="label.salva" /></td>
										</c:when>
									</c:choose>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${datiProtocolloLetto.allegati.allegatoResponseType}"
									var="current" varStatus="a">
									<c:choose>
										<c:when test="${(a.index mod 2) eq 0 }">
											<c:set var="className">odd</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="className">even</c:set>
										</c:otherwise>
									</c:choose>
									<tr class="${className}">
										<c:set var="_nomefile">${current.serial}</c:set>
										<c:if test="${not empty current.commento }">
											<c:set var="_nomefile">${current.commento}</c:set>
										</c:if>
										<%--
										<c:choose>
											<c:when test="${isProtocolloSigepro eq true}">
												<%
												    String[] idallegato = ((ArrayOfAllegatoResponseType) pageContext
																					.getAttribute("current")).getIDBase()
																					.split("\\|");
																			String idallegatoVal = idallegato[3];
																			pageContext.setAttribute("id", idallegatoVal);
												%>
												<td><a target="_new"
													href="<%=BackofficeNETConstants.getURL_APP_ASPNET()%>/ProtocolloUpload/MostraOggetto.ashx?software=TT&token=<%=ORMHelper.getToken()%>&id=${id}"
													title="<fmt:message key="label.visualizza" /> ${_nomefile}">${_nomefile}</a>
												</td>
											</c:when>
											<c:otherwise>
												
											</c:otherwise>
										</c:choose>
										 --%>
										 <td><a
													href="../protocollazione/ajaxVisualizzaAllegato.htm?idBase=${current.IDBase}&codiceComune=${ protocolloCommand.comune.codicecomune}&pSoftware=${ protocolloCommand.protSoftware.codice}"
													title="<fmt:message key="label.visualizza" /> ${_nomefile}">${_nomefile}</a>
										</td>
										<td>${current.contentType}</td>
										<c:choose>
										<c:when test="${protocolloCommand.provenienza ne 'AZP' }">
										
										
										<!-- GESTIONE DELLA RIGA CHE PERMETTE IL SALVATAGGIO DEL SINGOLO FILE -->
										<!--  start -->
										<!-- FILE ANCORA DA SCARICARE  -->
										<c:if test="${empty mappaDocScaricati[a.index]}">
										<td id="salvaRiga_id${a.index}">
											<!-- FILE DA SALVARE IN DOCUMENTI ISTANZA -->
											<c:if test="${protocolloCommand.movimento.id.codice==null}">
											<a class="salvaRiga" href="javascript:salvaInDocumentiIstanza${a.index}('salvaRiga_id${a.index}','ajaxSalvaAllegatoInDocumentiIstanza.htm?idBase=${current.IDBase}&codiceIstanza=${protocolloCommand.entity.id.codice}&descrizioneFile=')"
													title="<fmt:message key="label.salva_allegato_in_documuenti_istanza"/>">
													<label><fmt:message key="label.visualizza.image" />
												</label> 
											</a>
											</c:if>
											<!-- FILE DA SALVARE IN ALLLEGATI MOVIMENTI -->
											<c:if test="${protocolloCommand.movimento.id.codice!=null}">
												<a class="salvaRiga" href="javascript:salvaInAllegatiMovimento${a.index}('salvaRiga_id${a.index}','ajaxSalvaAllegatoInMovimentiAllegati.htm?idBase=${current.IDBase}&codiceMovimento=${protocolloCommand.movimento.id.codice}&descrizioneFile=')"
													title="<fmt:message key="label.salva_allegato_in_documuenti_istanza"/>">
													<label><fmt:message key="label.visualizza.image" />
												</label> 
											</a>
											</c:if>
											
										</td>
										</c:if>
										<!-- FILE GIà SCARICATI -->
										<td id="rigaSalvata_id${a.index}" style="display: none">
										<c:if test="${ not empty mappaDocScaricati[a.index]}">
											<script type="text/javascript">
												document.getElementById('rigaSalvata_id${a.index}').style.display="";
											</script>
										</c:if>
											<img src="${pageContext.request.contextPath}/images/accept.gif"></img>
										</td>
										<script type="text/javascript">
											function salvaInDocumentiIstanza${a.index}(id,url)
											{    
												salvaAllegati${a.index}(id,url);
											}
											
											function salvaInAllegatiMovimento${a.index}(id,url)
											{
												salvaAllegati${a.index}(id,url);	
											}
											
											function salvaAllegati${a.index}(id,url)
											{
												var url_=url+escape("${_nomefile}");
												new Ajax.Request(url_, {
													method: 'post',	
													onSuccess: function(transport){
														dijit.showTooltip(transport.responseText, dojo.byId(id));
														setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},5000);
														document.getElementById(id).style.display="none";
														document.getElementById('rigaSalvata_id${a.index}').style.display="";
													},
													onFailure: function(transport){ 
														alert(transport);
													  	alert("Errore durante il salvataggio del dato");
													}						    		 
												});
											}
											
										</script>
									</tr>
									</c:when>
									<c:otherwise>
										<%-- PROVENIENZA AZIONI PROTOCOLLO --%>
									
									</c:otherwise>
									
									</c:choose>
									
									<!-- END -->
								<c:set var="countAllegati" value="${a.index}"></c:set>
								</c:forEach>
								
								<!-- GESTIONE DELLA RIGA CHE PERMETTE IL SALVATAGGIO DI TUTTI GLI ALLEGATI -->
								<!--  start -->
								<!-- Controlla se ci sono file da scaricare o se sono già stati tutti scaricati -->
								<c:if test="${datiProtocolloLetto.allegati!=null && (countAllegati+1)>fn:length(mappaDocScaricati)  }">
								
								
								<tr class="header">
									<td colspan="3"></td>
								</tr>
								<c:choose>
									<c:when test="${protocolloCommand.provenienza ne 'AZP' }">
										<!-- Funzionalità riferita ai documenti da salvare in documenti istanza -->
										<c:if test="${protocolloCommand.movimento.id.codice==null}">
										<tr class="odd">
											<b><td style="text-align: left;border: medium;">&nbsp;</td></b>
											<td>Salva tutti i file</td>
											<td>
												<a class="salvaRiga" href="javascript:doSubmit('salvaTuttiAllegatiDaProtocollo.htm?codiceIstanza=${protocolloCommand.entity.id.codice}','',document.inviodati)"
												   title="<fmt:message key="label.salva_allegato_in_documuenti_istanza"/>">
												   <label><fmt:message key="label.visualizza.image" /></label> 
												</a>
											</td>
										</tr>
										</c:if>
										<!-- Funzionalità riferita ai documenti da salvare in ALLEGATI MOVIMENTI -->
										<c:if test="${protocolloCommand.movimento.id.codice!=null}">
										<tr class="odd">
											<b><td>&nbsp;</td></b>
											<td>Salva tutti i file</td>
											<td>
												<a class="salvaRiga" href="javascript:doSubmit('salvaTuttiAllegatiDaProtocollo.htm?codiceIstanza=${protocolloCommand.entity.id.codice}&codiceMovimento=${protocolloCommand.movimento.id.codice}','',document.inviodati)"
												   title="<fmt:message key="label.salva_allegato_in_documuenti_istanza"/>">
												   <label><fmt:message key="label.visualizza.image" /></label> 
												</a>
											</td>
										</tr>
										</c:if>
									</c:when>
								</c:choose>
								
								</c:if>
								<!-- end -->
							</tbody>
						</table>
					</div>
				</fieldset>
			</fieldset>
		</spring-form:form>
	</div>

	<div id="functions">
		<ul>
			<c:choose>
				<c:when test="${protocolloCommand.provenienza eq 'AZP' }">
					<li><a href="javascript:historyBack('');"><fmt:message key="label.crea_istanza" /></a></li>
					<li><a href="javascript:historyBack('');"><fmt:message key="button.crea_movimento" /></a></li>
				</c:when>
			</c:choose>		
			<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
<c:choose>
<c:when test="${protocolloCommand.provenienza eq 'AZP' }">	
		<script type="text/javascript">

			var isSoftwareTT = true;
			function setValuesAndGo(){
				if($('software_var_hidden').value!=''){
					$('go_to_url_id').value=$('go_to_url_id').value+"&software=" + $('software_var_hidden').value;
					vaiAFunzione($('go_to_url_id').value,$('tipometodo_id').value,$('hidefunctions_id').value, $('confirmmessage_id').value);
				}else{
					alert('<fmt:message key="label.software" /> <fmt:message key="alert.required" />');
					jQuery('#software_var_hidden').focus();
				}
			}			
			
			function vaiAFunzione(goToUrl,tipoMetodo,isDisableFunctions, confirmMessage,idPec){
				var ancora = "";
				if(tipoMetodo=='historySet'){
					historySet('${_urlback}'+ancora, goToUrl, confirmMessage);
				}else if(tipoMetodo=='doHref'){
					doHref(url, confirmMessage);						
				}
				if(isDisableFunctions){
					disableFunctions();
				}
			}
			
			function pannelloSceltaSoftware(goToUrl,tipoMetodo,isDisableFunctions, confirmMessage, idPec){										
				mostraPannello();
			}
			
			function mostraPannello(){				
				dijit.byId('pannelloSceltaSoftwareDiv').show();
			}
			
		</script>
		<div dojoType="dijit.Dialog" id="pannelloSceltaSoftwareDiv" title="Scegli il modulo di destinazione"  style="height: auto;min-width: 400px;">
			<div style="height: 200px;">	

		<input type="hidden" name="go_to_url" id="go_to_url_id" />	
		<input type="hidden" name="tipometodo" id="tipometodo_id" />
		<input type="hidden" name="hidefunctions" id="hidefunctions_id" />
		<input type="hidden" name="confirmmessage" id="confirmmessage_id" />

		<select id="software_var_hidden" name="software_id">
			<option value=""><fmt:message key="label.select.default" /></option>
			<c:if test="${not empty softwareList}">
				<c:forEach items="${ softwareList }" var="soft">
					<option value="${soft.codice }">${soft.descrizione}</option>
				</c:forEach>
			</c:if>
		</select>
		
			<div id="functions">
				<ul>
					<li id="OkId"><a href="javascript:setValuesAndGo()"><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void 0" onclick="dijit.byId('pannelloSceltaSoftwareDiv').hide();"><fmt:message key="button.annulla" /></a></li>
				</ul>
			</div>
			<br class="clear" />	
			</div>
		</div>
	
	
	
</c:when>
</c:choose>	
	
	
	
</body>
</html>