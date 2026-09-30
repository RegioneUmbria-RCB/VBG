<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="protocolloCommand" name="inviodati" id="invioDatiId">

			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="protocolloCommand" />
			</jsp:include>			
			<fieldset>
				<legend>
					<fmt:message key="label.dati_protocollo" />						
				</legend>
				
				<br class="break" />
				<table width="100%">
				
				<c:if test="${istanzePresenti eq true }">
					<tr>
						<td colspan="2">
						<div style="border: thin dotted; padding: 15px;" class="warning_header">
							<fmt:message key="label.sono_presenti_pratiche_con_riferimenti_protocollo" />&nbsp;
							<a href="javascript:visualizzaIstanze();"><img id="imgDettagliorichiedenteIdCodice" src="<%=request.getContextPath()%>/images/search.gif" title="<fmt:message key="label.visualizza" />" /></a>
						</div>	
							<div dojoType="dijit.Dialog" id="ListaIstanzePresentiDiv" title="<fmt:message key="label.visualizza" />" >
								<div class="generic_dialog" id="ListaIstanzePresentiDiv_content">
									
								</div>	
							</div>
							
						</td>
					</tr>
				</c:if>
				   
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
										
										<td><a
											href="../protocollazione/ajaxVisualizzaAllegato.htm?idBase=${current.IDBase}&codiceComune=${ azioniProtocollazioneCommand.comune.codicecomune}&pSoftware=${ azioniProtocollazioneCommand.protSoftware.codice}"
											title="<fmt:message key="label.visualizza" /> ${_nomefile}">${_nomefile}</a>
										</td>
											
										<td>${current.contentType}</td>
										
									
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
								</c:if>
								<!-- end -->
							</tbody>
						</table>
					</div>
				</fieldset>
			</fieldset>
			

			<input type="hidden" id="tipoMetodo_id" />



			
		</spring-form:form>
	</div>


			<%--  MOSTRA LA FUNZIONALITA' CREA ISTANZA --%>

<c:if test="${ FUNZIONALITA_DA_MOSTRARE eq 'ISTANZA' }">
	<a name="crea_istanza_acx">&nbsp;</a>
	<span class="titoloPagina">
	
		<fmt:message key="label.crea_istanza" />
	</span>
	<br  class="clear" />
		<spring-form:form commandName="azioniProtocollazioneCommand" name="istanzaDaPecForm" id="istanzaDaPecForm" action="${pageContext.request.contextPath}/azioniprotocollo/creaIstanza.htm">
	
			
		    
			<table width="100%" border="0">
				<tr>
					<td><fmt:message key="azioniprotocollo.label.invia_allegati" /></td>
					<td colspan="5">
						<c:set var="CHECKED" value=""></c:set>
						<c:if test="${ azioniProtocollazioneCommand.inviaAllegati eq true}">
							<c:set var="CHECKED" > checked="checked" </c:set>
						</c:if>
						<input id="inviaAllegati_id" name="inviaAllegati" type="checkbox" value="1" ${CHECKED} onclick="toggleScompattaAllegati(this)"/>
					</td>
				</tr>
				<tr id="scompattaAllegati_TRID">
					<td><fmt:message key="pecinbox.label.scompattaallegaticompressi" /></td>
					<td colspan="5">
						<c:set var="CHECKED" value=""></c:set>
						<c:if test="${ azioniProtocollazioneCommand.scompattaAllegati eq true}">
							<c:set var="CHECKED"> checked="checked" </c:set>
						</c:if>
						<input id="scompattaAllegati_id" name="scompattaAllegati" type="checkbox" value="1" ${CHECKED} onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_AZIONI_PROTOCOLLO_SCOMPATTAALLEGATICOMPRESSI%>',this);" />
						<init:help idHelp="helpScompattaAllegati" textKey="pecinbox.label.scompattaallegaticompressi.helpistanza" />
					</td>
				</tr>
				
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="label.dati_istanza"/>		
						</td>
					</tr>
					<c:if test="${empty error }">
						<c:if test="${not empty azioniProtocollazioneCommand.entity.id.codice }">
							<tr>
								<td style="vertical-align: top;"><fmt:message key="label.numeroistanza" /></td>
								<td colspan="5">
									
										<span id="label_numeroistanza" class="error">
											<fmt:message key="pecinbox.message.errorestc" >
												<fmt:param>${azioniProtocollazioneCommand.entity.numeroistanza }</fmt:param>
											</fmt:message>
										</span>
									
									<c:if test="${not istanzaPecCommand.stcError eq true}">
										<span id="label_numeroistanza"><input type="text" class="inputRed" id="_entity_numeroistanza_id" readonly="readonly" name="entity.numeroistanza" size="40" value="${azioniProtocollazioneCommand.entity.numeroistanza}"/></span>
									</c:if>
								</td>
							</tr>
						</c:if>
							<jsp:include page="../includes/comboComuni.jsp">
								<jsp:param name="mostraTutti" value="false" />
								<jsp:param name="emptyLabelTutti" value="false" />						
								<jsp:param name="readOnly" value="false" />
								<jsp:param name="commandPropertyPath" value="entity.comune" />
								<jsp:param name="colspan" value="5" />
								<jsp:param name="comune" value="${azioniProtocollazioneCommand.entity.comune.codicecomune}" />
								<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
							</jsp:include>							
						<!-- richiedente -->
						<tr>
							<td style="vertical-align: top;">
								<label class="required">*</label>
								<fmt:message key="label.richiedente" />
							</td>
							<td colspan="5">
								<jsp:include page="../includes/anagraficasearch.jsp" >
									<jsp:param name="idElemento" value="richiedenteIdCodice" />						
									<jsp:param name="pathAnagrafica" value="entity.richiedente" />
									<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=F" />
									<jsp:param name="tiposoggetto"  value="F"/>
								</jsp:include>
							</td>
						</tr>		
						<c:choose>
							<c:when test="${configurazione.flagAziendarappresentata eq true }">
								<tr>
									<td><fmt:message key="label.in_qualita_di" /></td>
									<td colspan="5">
									<script type="text/javascript">
											function setFieldTipisoggetto(inputField, listItem){
													var a = listItem.id;
													//array che contiene l'id dei campi separati da '#'
													//il primo valore è l'id della tabella tipisogegtto e il secondo se deve mostrare o meno la descrizione del soggetto
													var arrayValori=a.split('#');
													document.getElementById('tipisoggetto_id_id').value = inputField.value;
													document.getElementById('tipisoggetto_id_hidden').value = arrayValori[0];
													if(arrayValori[1]=='true'){
														$('TR_DESCRSOGGETTO').appear();
													}else{
														$('TR_DESCRSOGGETTO').fade();
													}
											}
										</script>
										
											<jsp:include page="../includes/autocompletergenerico.jsp" >
												<jsp:param name="idElemento" value="tipisoggetto_id" />
												<jsp:param name="propertyPath" value="entity.tipisoggetto" />
												<jsp:param name="pathPropertyDescription" value="entity.tipisoggetto.tiposoggetto" />
												<jsp:param name="pathPropertyCode" value="entity.tipisoggetto.id.codice" />
												<jsp:param name="autocompleterAjax" value="findTipisoggettoAndSpecificadescrizione.htm?flagQualita=true" />							
												<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
												<jsp:param name="afterUpdateElement" value="setFieldTipisoggetto" />
											</jsp:include>
										
										
									</td>
								</tr>
								<c:set var="displayDescrSoggetto" value="display: none;" />
							
								<c:if test="${azioniProtocollazioneCommand.entity.tipisoggetto.flgSpecificadescrizione eq true}">
									<c:set var="displayDescrSoggetto" value="" />
								</c:if>
							
								<tr id="TR_DESCRSOGGETTO" style="${displayDescrSoggetto}">
									<td>&nbsp;</td>
									<td colspan="5">
										<spring-form:input path="entity.descrsoggetto" size="70" />
										<spring-form:errors path="entity.descrsoggetto" cssClass="error"/>
										&nbsp;<fmt:message key="label.specificare_la_tipologia_di_soggetto" />
									</td>
								</tr>				
								<tr>
									<td style="vertical-align: top;"><fmt:message key="label.ragione_sociale" /></td>
									<td colspan="5">
										<jsp:include page="../includes/anagraficasearch.jsp" >
											<jsp:param name="idElemento" value="titolareLegaleIdCodice" />						
											<jsp:param name="pathAnagrafica" value="entity.titolarelegale" />
											<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=G" />
											<jsp:param name="tiposoggetto"  value="G"/>
										</jsp:include>
									</td>
								</tr>
								
							</c:when>
							<c:otherwise>
								<spring-form:hidden path="entity.tipisoggetto.id.codice" />
								<spring-form:hidden path="entity.descrsoggetto" />
								<spring-form:hidden path="entity.titolarelegale.id.codice" />
							</c:otherwise>
						</c:choose>					 
						<tr>
							<td style="vertical-align: text-top; "><fmt:message key="label.codice_pratica_telematica" /></td>
							<td colspan="5">
								<spring-form:input id="codice_pratica_telematica_id" path="entity.codicepraticatel" size="70" cssStyle="font-weight: bolder;"/>
								<spring-form:errors path="entity.codicepraticatel" cssClass="error" />
								
									<a href="javascript: void(0);" id="btn_calcola">
										<img src="../images/calcolatrice.gif" title="<fmt:message key="pecinbox.button.calcolacodicepratica"/>" />
									</a>
									<init:help idHelp="helpPraticaTelematica" textKey="label.codice_pratica_telematica.help" />									
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.domicilio_elettronico" /></td>
							<td colspan="5">
								<spring-form:input id="domicilioElettronico_id" path="entity.domicilioElettronico"	size="70" cssStyle="font-weight: bolder;"/>
								<spring-form:errors path="entity.domicilioElettronico" cssClass="error" />
							</td>
						</tr>
						<tr id="id_progetto_table">
							<td valign="top">
								
								<label class="required">*</label>
								
								<fmt:message key="label.alberoproc" />
							</td>
							<td colspan="5">
							<spring-form:input id="alberoproc_hidden" path="entity.alberoproc.id.codice" onchange="cercaProcedimento()" size="9" cssStyle="text-align: right; font-weight: bolder;" />
							<%-- ALBEROPROC DOJO TREE --%>
							
								<a href="javascript:cercaProcedimento();" style="vertical-align: bottom;" ><img id="alberoimg_id" border="0" src="${pageContext.request.contextPath }/images/search.gif" title="Cerca procedimento" /></a>
							
							<spring-form:input id="alberoproc_descrestesa_hidden" path="entity.alberoproc.vwAlberoproc.scDescrizione" size="100" readonly="true" cssStyle="font-weight: bolder;"/>
							<spring-form:errors path="entity.alberoproc" cssClass="error" />
							<div id="treeOne"></div>
		   	  				<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div>
								</td>						
							</tr>
							<tr>
								<td style="vertical-align: top;">
									<fmt:message key="label.lavori" />
								</td>
								<td colspan="5">
									<spring-form:textarea id="lavori_id" path="entity.lavori" cols="105" rows="4" cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
							<tr>
								<td  style="vertical-align: top;">
									<fmt:message key="label.lavoriestesa" />
								</td>
								<td colspan="5">
									<spring-form:textarea id="lavoriestesa_id" path="entity.lavoriestesa" 
									cols="105" rows="4" cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty error}">
						<tr>
							<td colspan="6">
								<span class="error">${error}</span>		
							</td>
						</tr>
						</c:if>
					
					<c:if test="${not empty error}">
						<tr>
							<td colspan="6">
								<span class="error">${error}</span>		
							</td>
						</tr>
					</c:if>
				</table>
				
			</spring-form:form>	
			
		<div id="functions">
			<ul>
				<li><a href="javascript:void();" id="btn_salva"><fmt:message key="label.salva" /></a></li>
				<li><a href="javascript:doHref('view.htm','');" ><fmt:message key="label.chiudi" /></a></li>
			</ul>
		</div>
			 		
	</div>

	<script type="text/javascript">
	
	  jQuery(document).ready(function(){
		  
		  
		  if($('inviaAllegati_id')){
			  if($('inviaAllegati_id').checked!=true){
				  if($('scompattaAllegati_TRID')){
					  jQuery('#scompattaAllegati_TRID').hide();						
				  }
			  }
		  }
		  
		  jQuery('#btn_salva').click(creaIstanza);
		  jQuery('#btn_calcola').click(calcolaCodicePraticaTelematica);
		  jQuery('#richiedenteIdCodice').css('fontWeight','bolder');
		  jQuery('#titolareLegaleIdCodice').css('fontWeight','bolder');
		  jQuery('#tipisoggetto_id_id').css('fontWeight','bolder');
		  //titolareLegaleIdCodice
		  
		  if($('alberoproc_descrestesa_hidden')){
			if('${fn:replace(istanzaPecCommand.intervento.vwAlberoproc.scDescrizione,'\'','%27')}' != ''){									
				$('alberoproc_descrestesa_hidden').innerText='${fn:replace(istanzaPecCommand.intervento.vwAlberoproc.scDescrizione,'\'','\\\'')}';
			}
		  }
		});
	  
	  function creaIstanza(){
		  disableFunctions();
		  jQuery.ajax({
				url: '${pageContext.request.contextPath}/azioniprotocollo/ajaxCreaIstanzaSTC.htm?', 
				dataType: 'html',
				type: 'post',
				data: jQuery('#istanzaDaPecForm').serialize(),
				cache: false,	
				success: function(data){
					enableFunctions();
					if (data.match("^OK")) {
						jQuery('#outputmessaggicreazione_div').html(data.replace("OK",""));
						avvisoIstanzaCreata();
					}else{
						jQuery('#outputmessaggicreazione_div').html(data.replace("KO",""));
						avvisoErroreIstanza();
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
					enableFunctions();
					var errorMessage = "Errore in inserimento pratica.";					
					if(errorThrown){
						errorMessage += errorThrown;
					}
					displayErrorMessage(errorMessage);
				}
			});
	  }
	 		
	  
	  function calcolaCodicePraticaTelematica(){
		  
			jQuery.ajax({
				url: '${pageContext.request.contextPath}/azioniprotocollo/ajaxCalcolaCodicePraticaTel.htm?', 
				dataType: 'json',
				data: jQuery('#istanzaDaPecForm').serialize(),
				cache: false,	
				success: calcolaCodicePraticaTelematicaCallback,
				error: function(jqXHR, textStatus, errorThrown){
					var errorMessage = "Errore nel calcolo automatico del codice pratica telematica.";
				
					if(errorThrown){
						errorMessage += errorThrown;
					}
					displayErrorMessage(errorMessage);
				}
			});
	  }
	  
	  function calcolaCodicePraticaTelematicaCallback(data, textStatus, jqXHR){
		  if(data.codicePraticaTel){
			  jQuery('#codice_pratica_telematica_id').val(data.codicePraticaTel);
		  }
		  else if(data.error){
			  displayErrorMessage(data.error);
		  }
	  }
	  
	  function displayErrorMessage(msg){
		  alert(msg);
	  }
	</script>	

</c:if>

			<%-- END CREAZIONE ISTANZA --%>

			<%-- CREAZIONE MOVIMENTO --%>
<c:if test="${ FUNZIONALITA_DA_MOSTRARE eq 'MOVIMENTO' }">
	
	<a name="crea_istanza_acx">&nbsp;</a>
	<span class="titoloPagina">
		<fmt:message key="label.crea_movimento" />
	</span>
	<br  class="clear" />
		<spring-form:form commandName="azioniProtocollazioneCommand" name="movimentoDaPecForm" id="movimentoDaPecForm" action="${pageContext.request.contextPath}/azioniprotocollo/creaMovimento.htm">
			<table width="100%" border="0">
			
				<tr>
					<td><fmt:message key="azioniprotocollo.label.invia_allegati" /></td>
					<td colspan="5">
						<c:set var="CHECKED" value=""></c:set>
						<c:if test="${ azioniProtocollazioneCommand.inviaAllegati eq true}">
							<c:set var="CHECKED" > checked="checked" </c:set>
						</c:if>
						<input id="inviaAllegati_id" name="inviaAllegati" type="checkbox" value="1" ${CHECKED} onclick="toggleScompattaAllegati(this)"/>
					</td>
				</tr>
				<tr id="scompattaAllegati_TRID">
					<td><fmt:message key="pecinbox.label.scompattaallegaticompressi" /></td>
					<td colspan="5">
						<c:set var="CHECKED" value=""></c:set>
						<c:if test="${ azioniProtocollazioneCommand.scompattaAllegati eq true}">
							<c:set var="CHECKED"> checked="checked" </c:set>
						</c:if>
						<input id="scompattaAllegati_id" name="scompattaAllegati" type="checkbox" value="1" ${CHECKED} onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_AZIONI_PROTOCOLLO_SCOMPATTAALLEGATICOMPRESSI%>',this);" />
						<init:help idHelp="helpScompattaAllegati" textKey="pecinbox.label.scompattaallegaticompressi.helpistanza" />
					</td>
				</tr>
						
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="pecinbox.label.dati_movimento"/>		
						</td>
					</tr>
						<tr>
							<td style="vertical-align: top;">								
								<label class="required">*</label>
								<fmt:message key="pecinbox.label.istanza" />
							</td>
							<td colspan="5">
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="istanza_id" />		
									<jsp:param name="propertyPath" value="movimento.istanza" />		
									<jsp:param name="pathPropertyDescription" value="movimento.istanza.numeroistanza" />
									<jsp:param name="pathPropertyCode" value="movimento.istanza.id.codice" />
									<jsp:param name="autocompleterAjax" value="findIstanzeExtended.htm" />
									<jsp:param name="titleKey" value="pecinbox.label.istanza.alt" />
									<jsp:param name="readOnly" value="false" />
									<jsp:param name="autocompleterInputSize" value="90" />
									<jsp:param name="autocompleterMinChars" value="1" />
									<jsp:param name="afterUpdateElement" value="afterUpdateIstanza" />
									<jsp:param name="ajaxCallBack" value="searchIstanzeParams" />
								</jsp:include>
								<input type="checkbox" name="searchProtocolloMovimenti" id="searchProtocolloMovimentiChk"/>
								<init:help idHelp="helpMovimento" textKey="label.cerca_protocollo_in_movimenti" />				
							</td>
						</tr>					
						<tr>
							<td>
								<label class="required">*</label>
								<fmt:message key="label.tipomovimento" />
							</td>
							<td colspan="5">
								<script type="text/javascript">
									function checkTipomovimento(inputField,listItem){
										var idElemento='tipoMovimentoInputId';
										var a = listItem.id;
										document.getElementById(idElemento+'_hidden').value = a;
										if($('id2_'+idElemento).style.display == 'inline'){
											document.getElementById(idElemento+'_id2').value = inputField.value;
											$(idElemento+'_id2'+'_choices').fade();
										}else{
											document.getElementById(idElemento+'_id1').value = inputField.value;
											$(idElemento+'_id1'+'_choices').fade();
										}
										checkEsito(a);					
									}
									
									function filterMovimentiIstanza(element, entry) { 
										if(document.getElementById("istanza_id_hidden")){
											return entry + "&codiceIstanza=" + document.getElementById("istanza_id_hidden").value;
										}else{
											return entry + "&codiceIstanza=null";
										}
									}
									
									function checkEsito(tipoMovimento){
											new Ajax.Request('../tipimovimento/ajaxEsitoTipomovimento.htm', {
											  method: 'post',
											  parameters: {tipoMovimento: tipoMovimento},
											  onSuccess: function(transport){
												  var response = transport.responseText;
												  result = response.split("#");
												  if(result[0]!='0'){
												   	  document.getElementById("div_esito_id").style.display='';
												  }else{
													  document.getElementById("div_esito_id").style.display='none';
												  }
											  },
											  onFailure: function(transport){ 
												var response = transport.responseText;
											    alert(response); 
											    }						    		 
										} );			
									}			
									
									function afterUpdateIstanza(inputField,listItem){
										var spanToShow = jQuery(listItem).find('#show_' + listItem.id);
										if(spanToShow.length > 0){
											inputField.value = spanToShow.text();
											jQuery("[name = 'movimento.istanza.id.codice']").val(listItem.id);
										}
									}
									
									function searchIstanzeParams(element, entry){
										var searchProtChk = jQuery("#searchProtocolloMovimentiChk");
										if(searchProtChk && searchProtChk.length){
											if(searchProtChk.prop('checked')){
												return entry + "&protInMovimenti=true";
											}
										}
										return entry + "&protInMovimenti=false";
									}
								</script>
								<jsp:include page="../includes/tipimovimentosearch.jsp" >
									<jsp:param name="idElemento" value="tipoMovimentoInputId" />
									<jsp:param name="pathTipomovimento" value="movimento.tipomovimento" />
									<jsp:param name="afterUpdateElement" value="checkTipomovimento" />
									<jsp:param name="readOnly" value="false" />	
									<jsp:param name="tipimovimentoAutocompleterAjax" value="findTipiMovimentoUsatiDalProtocollo.htm?codice=" />
									<jsp:param name="ajaxCallBack" value="filterMovimentiIstanza" />								
								</jsp:include>													
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.movimento" /></td>
							<td colspan="5">
								<spring-form:input id="movimento_id" path="movimento.movimento" size="70" readonly="false" cssStyle="font-weight: bolder;"/>
								<c:set var="helpMovimentoText"><fmt:message key="label.selasciatovuoto" />&nbsp;<fmt:message key="label.tipomovimento" /></c:set>
								<init:help idHelp="helpDescMovimento" text="${helpMovimentoText}" />				
							</td>
						</tr>	
						<c:set var="displayEsito">display:;</c:set>	
						<c:if test="${movimentiCommand.entity.tipomovimento.tipologiaesito eq 0}">
							<c:set var="displayEsito">display:none;</c:set>
						</c:if>
						<tr style="${displayEsito}" id="div_esito_id">
							<td><fmt:message key="label.esito_positivo" /></td>
							<td colspan="5">
								<spring-form:select id="esito_id" path="movimento.esito" disabled="false" cssStyle="font-weight: bolder;">
									<spring-form:option value="true"><fmt:message key="label.si" /></spring-form:option>
									<spring-form:option value="false"><fmt:message key="label.no" /></spring-form:option>					
								</spring-form:select>
								<spring-form:errors path="movimento.esito" cssClass="error"/>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="label.note" />
							</td>
							<td colspan="5">
								<spring-form:textarea readonly="false" id="lavori_id" path="movimento.note" cols="120" rows="8" cssStyle="font-weight: bolder;"/>
							</td>
						</tr>
				</table>
				
			</spring-form:form>	
			
		<div id="functions">
			<ul>				
				<li><a href="javascript:void();" id="btn_salva"><fmt:message key="label.salva" /></a></li>				
				<li><a href="javascript:doHref('view.htm','')" id="btn_indietro"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
			 		
	</div>

	<script type="text/javascript">
	
	  jQuery(document).ready(function(){
		  
		  if($('inviaAllegati_id')){
			  if($('inviaAllegati_id').checked!=true){
				  if($('scompattaAllegati_TRID')){
					  jQuery('#scompattaAllegati_TRID').hide();						
				  }
			  }
		  }
		  jQuery('#btn_salva').click(creaMovimento);
		  jQuery('#tipoMovimentoInputId_id1').add('#tipoMovimentoInputId_id2').add('#istanza_id_id').css('fontWeight','bolder');
		});
	  
	  function creaMovimento(){
		  disableFunctions();
		  jQuery.ajax({
				url: '${pageContext.request.contextPath}/azioniprotocollo/ajaxCreaMovimentoSTC.htm?', 
				dataType: 'html',
				type: 'post',
				data: jQuery('#movimentoDaPecForm').serialize(),
				cache: false,	
				success: function(data){
					enableFunctions();
					if (data.match("^OK")) {
						jQuery('#outputmessaggicreazione_div').html(data.replace("OK",""));
						avvisoIstanzaCreata();
					}else{
						jQuery('#outputmessaggicreazione_div').html(data.replace("KO",""));
						avvisoErroreIstanza();
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
					enableFunctions();
					var errorMessage = "Errore in inserimento pratica.";					
					if(errorThrown){
						errorMessage += errorThrown;
					}
					displayErrorMessage(errorMessage);
				}
			});
	  }
	  
	 
	</script>	
	
	
	
	

</c:if>			
			
			
<%--
			<div id="pannelloCreazioneMovimentoDiv" title="<fmt:message key="label.crea_movimento" />"  style="padding:10px;border:1px solid black;display:none;height: auto; min-width: 400px;">
				<div id="pannelloCreazioneMovimentoDiv_content">
					MOVIMENTO	
				</div>
				<br class="clear"/>
			</div>
 --%>			
			
			<%-- END CREAZIONE MOVIMENTO --%>

	<div id="functions">
		<ul>
		<c:if test="${not ((FUNZIONALITA_DA_MOSTRARE eq 'ISTANZA') or (FUNZIONALITA_DA_MOSTRARE eq 'MOVIMENTO') )}">
			<li><a href="javascript:pannelloSceltaSoftware('ISTANZA');"><fmt:message key="label.crea_istanza" /></a></li>
			<li><a href="javascript:pannelloSceltaSoftware('MOVIMENTO');;"><fmt:message key="label.crea_movimento" /></a></li>
			<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>
		</c:if>
			
		</ul>
	</div>
	

	
		<script type="text/javascript">

		function toggleScompattaAllegati(obj){		
			salvaPreferenza('<%=WebConstants.CONF_UTENTE_AZIONI_PROTOCOLLO_INVIAALLEGATI%>',obj);		
			jQuery('#scompattaAllegati_TRID').toggle();			
		}
		
		
		
		function ajaxHistorySet(url){
			new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
				  method: 'get',
				  parameters: {ReturnTo: url, limit: 12},
				  onSuccess: function(transport){},
				  onFailure: function(){}			  
			});
		}
		
		
		function salvaPreferenza(nomeparametro, objchk){
			var valore = "0";	
			if(objchk.checked==true){
				valore="1";	
			}
			saveUserPreference(nomeparametro, valore);
		}
		
		
			var isSoftwareTT = true;
			function setValuesAndGo(){
				if($('software_var_hidden').value!=''){
					dijit.byId('pannelloSceltaSoftwareDiv').hide();
					if(jQuery('#tipoMetodo_id').val()=='ISTANZA'){
						mostraCreaIstanza();						
					}else if(jQuery('#tipoMetodo_id').val()=='MOVIMENTO'){
						mostraCreaMovimento();
					}		
				}else{
					alert('<fmt:message key="label.software" /> <fmt:message key="alert.required" />');
					jQuery('#software_var_hidden').focus();
				}
			}
		
<c:choose>			
	<c:when test="${not empty softwareList}">			
		<c:choose>			
			<c:when test="${fn:length(softwareList)>1}">
				function pannelloSceltaSoftware(tipoMetodo){
					nascondiDivIstanzaEMovimenti();
					jQuery('#tipoMetodo_id').val(tipoMetodo);
					mostraPannello();
				}
			</c:when>
			<c:otherwise>
			function pannelloSceltaSoftware(tipoMetodo){
				if(tipoMetodo=='ISTANZA'){
					mostraCreaIstanza();
				}else if(tipoMetodo=='MOVIMENTO'){
					mostraCreaMovimento();
				}				
			}
			</c:otherwise>
			</c:choose>			
	</c:when>
	<c:otherwise>
		function pannelloSceltaSoftware(tipoMetodo){
			alert('l\'operatore non ha abilitato alcun modulo software');
		}
	</c:otherwise>
</c:choose>

			function mostraPannello(){				
				dijit.byId('pannelloSceltaSoftwareDiv').show();
			}
			
			function mostraCreaIstanza(){			
				doHref('pannelloIstanza.htm?software=' + jQuery('#software_var_hidden').val()+'#crea_istanza_acx','');
			}
			function mostraCreaMovimento(){
				doHref('pannelloMovimento.htm?software=' + jQuery('#software_var_hidden').val()+'#crea_istanza_acx','');
			}
			
			function visualizzaIstanze(){
				
				disableFunctions();
				var jhqrPr = jQuery.ajax({
					  url: 'ajaxVisualizzaIstanze.htm',
					  context: document.body,
					  cache: false,					  
					  dataType: "html",
					  success: function(data) {
						  enableFunctions();
						  jQuery("#ListaIstanzePresentiDiv_content").html(data);
						  dijit.byId('ListaIstanzePresentiDiv').show();
						  
					  },
					  error: function(jqXHR, textStatus, errorThrown){
						  enableFunctions();
						  jQuery("#ListaIstanzePresentiDiv_content").html(jqXHR.responseText);
						  dijit.byId('ListaIstanzePresentiDiv').show();
					}
				});	
			}
			
			
			function nascondiDivIstanzaEMovimenti(){
				jQuery('#pannelloCreazioneIstanzaDiv').hide();			
				jQuery('#pannelloCreazioneMovimentoDiv').hide();	
			}
			
			
			function avvisoIstanzaCreata(){		  
			 	alert(jQuery('#outputmessaggicreazione_div').html()); 
			 	doHref('view.htm' , '');
		  	}
			function avvisoErroreIstanza(){
				alert(jQuery('#outputmessaggicreazione_div').html()); 
			}
		</script>
		
		<div id="outputmessaggicreazione_div" style="display:none">
			
		</div>
		
		<div dojoType="dijit.Dialog" id="pannelloSceltaSoftwareDiv" title="Scegli il modulo di destinazione"  style="height: auto;min-width: 400px;">
			<div style="height: 200px;">	

		<select id="software_var_hidden" name="software_id">
			<c:if test="${fn:length(softwareList)>1}">
				<option value=""><fmt:message key="label.select.default" /></option>
			</c:if>
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

	
	<div dojoType="dojo.data.ItemFileReadStore" 
				jsId="alberoprocStore" 
				url="${pageContext.request.contextPath}/json/getAlberoprocPec.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>"> 
	</div>
	<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" 
		rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" 
		childrenAttrs="children">
	</div>							
							
							<script type="text/javascript">
										var treeControl = null;
										var treeInitialized = false;
										
										function cercaProcedimento(){									
											var codProc = $('alberoproc_hidden').value;
											rimuoviValori2();
											if(codProc){
												cercaProcedimentoAjax(codProc);	
											}else{
												apriAlbero();
											}
											$('alberoproc_hidden').focus();
										}
										
										function cercaProcedimentoAjax(codiceAlberoproc){
											if(isNaN(codiceAlberoproc)){
												displayErrorMessage("Ricerca per codice. Inserire un valore numerico");
												return;
											}
											new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=true', {
												  method: 'post',
												  parameters: {id: codiceAlberoproc},
												  onSuccess: function(transport){ 
													var response = transport.responseText;
													//displayErrorMessage(response);
													var json = response.evalJSON();
													if(json.id){
														if(json.padre == 'true'){
															displayErrorMessage("Procedimento non selezionabile.");
														}else{
															assegnaValori2(json);
															$('treeOne').style.display="none";
														}						
													}else{
														displayErrorMessage("Procedimento non trovato o disattivato.");
												    }
												  },
												  onFailure: function(transport){ 
													var response = transport.responseText; 
												    displayErrorMessage("Errore nella ricerca del procedimento!");
												  }						    		 
											} );
										}
										
										jQuery($('alberoproc_hidden')).keypress(function(e) {
									  	  	var code = e.keyCode ? e.keyCode : e.which;
											if(code.toString() == 13) {
												cercaProcedimento(); 
											}
									    });
										
										function apriAlbero() {
									        if(!treeControl){
										        treeControl = new dijit.Tree({
										            model: alberoprocModel,
										            showRoot: true,							            
										            onClick: function(item, node){
										            	if(item.id !='0' ){	
											        		var itemId = alberoprocStore.getValue(item, "id");
											        		if(itemId>0){
																cercaProcedimentoAjax(itemId);
																$('alberoproc_hidden').focus();
											        		}
										            	}
										            },
										            getIconClass: function(item,opened){							            	
										            	treeInitialized = true;
										            	if(item.id!='0'){
											        		var dis = 'false';
											        		if(item){
											        			dis = alberoprocStore.getValue(item, "disabilitato");
											        		}
											        		if(dis == 'false'){
											        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf";
											        		}else{
											        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled";
											        		}
										            	}else{
										            		return "dijitFolderOpened"
										            	}	
										            }
										        },
										        "treeOne");
									        }else{
									        	document.getElementById('treeOne').style.display="";
									        }
									    }
										
										var forzaNumeroPratica = true; 
										
										var endoSplashDiv=null;
										
										jQuery(document).ready(function(){
											endoSplashDiv = new dijit.Dialog({
									            title: "<fmt:message key='label.endoprocedimenti' />" ,
									            style: "width: 500px"
									        });
										});
										
										function mostraDivEndo(codiceAlberoproc){
											
											new Ajax.Request('../pecinbox/ajaxListaEndo.htm', {
												  method: 'post',
												  parameters: {codiceAlberoProc: codiceAlberoproc},
												  onSuccess: function(transport){ 
													  endoSplashDiv.attr("content", transport.responseText);
													  endoSplashDiv.show();
												  },
												  onFailure: function(transport){ 
													var response = transport.responseText;
													console.error("Errore nella ricerca degli endo procedimenti:" + response);										    
												  }						    		 
											} );			
											
										}
										
										var progressivoIstanza='';
										
										function chiudiEndoDiv(){
											endoSplashDiv.hide();
										}
										
										function assegnaValori2(map){
											$('alberoproc_hidden').value = map.id;
											$('alberoproc_descrestesa_hidden').value = map.desc;
											// GESTIRE GLI ENDOPROCEDIMENTI ?
											if(map.endo_presenti == "true"){
												mostraDivEndo(map.id);
											}else{
												resetEndoInSession();
											}											
										}
										
										
										function resetEndoInSession(){
											jQuery.ajax({
												url: '${pageContext.request.contextPath}/pecinbox/ajaxResetEndo.htm', 
												dataType: 'text',													
												cache: false													
											});
										}
										
										function rimuoviValori2(){
											$('alberoproc_hidden').value = '';
											$('alberoproc_descrestesa_hidden').value = '';											
										}
										
										function endoInSession(chkobjid, codiceinventario) {			
											var _ts = new Date().getTime();
											var url = '${pageContext.request.contextPath}/pecinbox/ajaxEndoInSession.htm?_ts='+_ts;
													url+='&codiceinventario='+codiceinventario+'&selezionato='+$(chkobjid).checked;																			
											changeCheckboxValue(chkobjid,url);									
										}
									
									</script>
</body>
</html>