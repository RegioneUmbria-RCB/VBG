<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.MercatiD"%>
<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
				<fmt:message key="label.nuova_concessione" />
			</c:if>
			<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
				<fmt:message key="label.dettaglio_concessione" />
			</c:if>
		</title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	</head>
	<body>
	<script type="text/javascript">
			var qstring = "codiceIstanza=${param.codiceIstanza}";
	</script>
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
		<jsp:include page="../includes/history.jsp">
	   		<jsp:param name="path" value="../autorizzazioni/createConcessione" />
	   	</jsp:include>	
	</c:if>   
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
		<jsp:include page="../includes/history.jsp">
	   		<jsp:param name="path" value="../autorizzazioni/viewConcessione" />
	   	</jsp:include>	
	</c:if>
	<span class="titoloPagina"> 
		<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
			<fmt:message key="label.nuova_concessione" />
		</c:if> 
		<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
			<fmt:message key="label.dettaglio_concessione" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>	
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.dati_istanza"/></legend>
			<c:import url="/ajax/dettaglioIstanza.htm">
				<c:param name="codIstanza">${param.codiceIstanza}</c:param>
			</c:import>
		</fieldset>
	</div>	
	<br class="clear" />	
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.DELETED}">
			<div id="subcontent">
				<spring-form:form commandName="concessioniCommand" name="inviodati">
					<jsp:include page="../includes/displayGlobalMessages.jsp">
						<jsp:param name="commandName" value="concessioniCommand" />
					</jsp:include>
				</spring-form:form>				
			</div>
	</c:if>
	<c:if test="${concessioniCommand.displayMode ne concessioniCommand.displayConstants.DELETED}">
		<div id="subcontent">
			<spring-form:form commandName="concessioniCommand" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
					<jsp:param name="commandName" value="concessioniCommand" />
				</jsp:include>
				<div class="vbg-form">
					<c:choose>
						<c:when test="${concessioniCommand.subentroPresente eq true}">				
						<fieldset>
							<legend><fmt:message key="label.concessione_dati_della_concessione" /></legend> 
							<%
							    String mercatiUso = "";
								String mercatiPosteggio = "display:none;";
							%>
							<jsp:include page="./registroconcessioni.jsp"/>						
						
							<%-- 
								QUESTO È IL CASO IN CUI VISUALIZZO UN'AUTORIZZAZIONE CHE È STATA SUBENTRATA E DEVO VISUALIZZARE 
								I DATI DEL SUBENTRO E NON QUELLI DELL'AUTORIZZAZIONE ATTIVA
							--%>
							<div class="form-group">
								<label><fmt:message key="label.concessione_numero_concessione" /></label>
								<spring-form:input id="entity_id" path="subentro.autoriznumero" size="20" disabled="true" />								
								<label><fmt:message key="label.concessione_data_validita_concessione" /></label>
								<spring-form:input id="autorizdata_id" path="subentro.autorizdata" size="10" disabled="true" />
							</div>
							<div class="form-group">
								<label><fmt:message key="label.concessione_data_rilascio_concessione" /></label>
								<spring-form:input id="dataRilascio_id" path="subentro.dataRilascio" size="10" disabled="true" />
								<c:if test="${concessioniCommand.subentro.tipologiaregistro.flagGestanzianita }">
									<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
									<spring-form:input id="dataAnzianita_id" path="subentro.dataAnzianita" size="10" disabled="true" />
								</c:if>	
							</div>
							
							<div class="form-group">
								<label><fmt:message key="label.concessione_titolare" /></label>
								<spring-form:input id="titolare_id" disabled="true" size="70" path="subentro.anagrafe.descrizioneRichiedente"/> 
								<spring-form:hidden id="titolare_hidden" path="subentro.anagrafe.id.codice" />								
							</div>
							
							<div class="form-group">
								<label><fmt:message key="mercatid.label.occupante" /></label>
								<spring-form:input id="occupante_id" size="70" path="subentro.occupante.descrizioneRichiedente" disabled="true" />
								<spring-form:hidden id="occupante_hidden" path="subentro.occupante.id.codice" />								
							</div>
							 							
							<div class="form-group">
								<label><fmt:message key="label.concessione_tipologia_concessione" /></label>
								<spring-form:input disabled="true" id="selectTipoConcessione" size="50" path="entity.concessionitipi.descrizione" />
								<spring-form:hidden id="hidden_tipoconcessione" path="entity.concessionitipi.tipoconcessione"/>
							</div>
							<div class="form-group" id="tipologiaStagionaleDiv">
								<label><fmt:message key="label.concessione_tipologia_stagionale_da" /></label>
								<spring-form:input id="stagionaleda_id" path="entity.stagionaledaTransient" size="6" maxlength="5" disabled="true"/>								
								<label><fmt:message key="label.concessione_tipologia_stagionale_a" /></label>
								<spring-form:input id="stagionalea_id" path="entity.stagionaleaTransient" size="6" maxlength="5"  disabled="true" />								
							</div>
							<div class="form-group">
								<label><fmt:message key="label.concessione_scadenza" /></label>
								<spring-form:input id="scadenza_id" path="subentro.datascadenza" size="10" disabled="true" /> 
							</div>
							<div class="form-group">
								<label><fmt:message key="label.concessione_causale_acquisizione" /></label>
								<spring-form:input id="selectCausaleAcquisizione" path="subentro.concessionicausaliByFkAutsubConccausAcq.descrizione" size="50" disabled="true"/>
								<c:if test="${concessioniCommand.subentro.concessionicausaliByFkAutsubConccausAcq.flagCausaliAffitto }">
									<label><fmt:message key="label.concessione_data_fine_affitto" /></label>						
									<spring-form:input path="subentro.dataFineAffitto" size="10" disabled="true"/>
								</c:if>
							</div>
							<div class="form-group">
								<label><fmt:message key="label.causale_cessazione" /></label>
								<spring-form:input path="subentro.concessionicausaliByFkAutsubConccausCess.descrizione" size="50" disabled="true"/>
								<label><fmt:message key="label.data_cessazione"/></label>
								<spring-form:input path="subentro.dataCessazione" size="10" disabled="true"/>								
							</div>
						</fieldset>
						<fieldset>
								<legend><fmt:message key="label.concessione_dati_della_manifestazione" /></legend>
								<div class="form-group" id="mercati">
									<label><fmt:message key="label.manifestazione" /></label>
									<spring-form:input disabled="true" id="mercati_id" path="concessioneInsert.mercati.descrizione" size="70" />
									<spring-form:hidden id="mercati_hidden" path="concessioneInsert.mercati.id.codice" />
								</div>
								<div class="form-group" id="mercatiUso" style="<%=mercatiUso%>">
									<label><fmt:message key="label.mercati_uso" /></label>
									<spring-form:input path="concessioneInsert.mercatiUso.descrizione" disabled="true" size="35"/>	
									<spring-form:hidden path="concessioneInsert.mercatiUso.id.codice" />
								</div>
								<div class="form-group" id="mercatiPosteggio">
									<label><fmt:message key="label.posteggio" /></label>
									<spring-form:input path="concessioneInsert.mercatiD.codiceposteggio" disabled="true"/>	
									<spring-form:hidden path="concessioneInsert.mercatiD.id.codice" />
								</div>
						</fieldset>
						<fieldset id="datiAutorizzazione">
							<c:if test="${concessioniCommand.inserisciAutorizzazione eq true}">								
								<spring-form:hidden path="inserisciAutorizzazione" />
								<legend><fmt:message key="label.concessione_dati_della_autorizzazione_inserisci_autorizzazione" /></legend>
							</c:if>	
								<div class="form-group" id="autorizzazionetitle_div" class="titoloSezione">
									<label><fmt:message key="label.concessione_dati_della_autorizzazione" /></label>
								</div>
								<div class="form-group" id="autorizzazione_div">
									<label><fmt:message key="label.autorizzazione_registro" /></label>
									<spring-form:input id="registro_id" path="subentroAutCollegata.tipologiaregistro.trDescrizioneCompleta" size="70" disabled="true"/>
								</div>
								<div class="form-group" id="autorizzazione_div2">
									<label><fmt:message key="label.autorizzazione_numero_autorizzazione" /></label>
									<spring-form:input id="concessioneNumeroAutorizzazione_id" disabled="true" path="subentroAutCollegata.autoriznumero" size="20" /> 
									<spring-form:errors	path="subentroAutCollegata.autoriznumero" cssClass="error" />									
									<label><fmt:message key="label.autorizzazione_data_validita_autorizzazione" /></label>
									<spring-form:input id="autorizdata2_id" readonly="true" path="subentroAutCollegata.autorizdata" size="10" disabled="true"/>
								</div>
								<div class="form-group" id="autorizzazione_div2_1">
									<label><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></label>
									<spring-form:input id="dataRilascio_id" readonly="true" path="subentroAutCollegata.dataRilascio" size="10" disabled="true"/>
									<c:if test="${concessioniCommand.subentroAutCollegata.tipologiaregistro.flagGestanzianita }">
										<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
										<spring-form:input id="dataAnzianita_id" path="subentroAutCollegata.dataAnzianita" size="10" disabled="true" />
									</c:if>
								</div>								
								<div class="form-group" id="autorizzazione_div4">
									<label><fmt:message key="label.concessione_autorizzata_da" /></label>
									<spring-form:input id="responsabile_id" size="50" path="subentroAutCollegata.autorizresponsabile" disabled="true"/>									
									<label><fmt:message key="label.concessione_autorizzata_il" /></label>
									<spring-form:input id="autorizdataregistr_id" path="subentroAutCollegata.autorizdataregistr" size="10" disabled="true"/> 
								</div>
							</fieldset>
						</c:when>
						<c:otherwise>
							<fieldset>
							<legend><fmt:message key="label.concessione_dati_della_concessione" /></legend> 
							<%
							    String mercatiUso = "";
								String mercatiPosteggio = "display:none;";
							%>
							<jsp:include page="./registroconcessioni.jsp"/>	
							
							<%-- QUESTO È IL CASO IN CUI VISUALIZZO UN'AUTORIZZAZIONE ATTIVA --%>
							<c:if test="${concessioniCommand.registroConcessioneProtocollo eq false}">
								<div class="form-group">
									<label><fmt:message key="label.concessione_numero_concessione" /></label>
									<spring-form:input id="entity_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" size="20" /> 
									<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />									
									<label><fmt:message key="label.concessione_data_validita_concessione" /></label>									
									<spring-form:input id="autorizdata_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" onblur="isValidDate(this,true);" />
								 	<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdata_id" idInput="autorizdata_id" textKey="label.calendar" /> 
								 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error"	/>									
								</div>
								<div class="form-group">
									<label><fmt:message key="label.concessione_data_rilascio_concessione" /></label>
									<spring-form:input id="dataRilascio_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio" size="10" onblur="isValidDate(this,true);" />
								 	<init:calendar imagePath="/images/cal.gif" idImage="cal_dataRilascio_id" idInput="dataRilascio_id" textKey="label.calendar" /> 
								 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio" cssClass="error"	/>
								 	<c:if test="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.flagGestanzianita }">
								 		<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
										<spring-form:input id="dataAnzianita_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita" size="10" onblur="isValidDate(this,true);"  />	
										<init:calendar imagePath="/images/cal.gif" idImage="cal_dataAnzianita_id" idInput="dataAnzianita_id" textKey="label.calendar" /> 
									 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita" cssClass="error"	/>
								 	</c:if>								 	
								</div>
							</c:if>
							<c:if test="${concessioniCommand.registroConcessioneProtocollo eq true}">
								<div class="form-group">
									<b><fmt:message key="label.concessione_estremi_da_protocollo" /></b>
								</div>
								<div class="form-group">
									<label><fmt:message key="label.concessione_numero_concessione" /></label>
									<input type="text" value="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero}" readonly/>																			
									<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />									
									<label><fmt:message key="label.concessione_data_validita_concessione" /></label>
									<c:if test="${not empty concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata }">
										<input type="text" value="<fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" readonly/>									
									</c:if>
									<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error" />
								</div>
								<div class="form-group">						
									<label><fmt:message key="label.concessione_data_rilascio_concessione" /></label>									
									<c:if test="${not empty concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio }">
										<input type="text" value="<fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" readonly />
									</c:if>									
									<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio" cssClass="error" />
									<c:if test="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.flagGestanzianita}">
										<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
										<spring-form:input id="dataAnzianita_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita" size="10" onblur="isValidDate(this,true);"  />	
										<init:calendar imagePath="/images/cal.gif" idImage="cal_dataAnzianita_id" idInput="dataAnzianita_id" textKey="label.calendar" /> 
									 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita" cssClass="error"	/>
									</c:if>							
								</div>
							</c:if>							
							<div class="form-group">
								<label><fmt:message key="label.concessione_titolare" /></label>													
								<spring-form:input id="titolare_id" size="70" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
								<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
								<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe" cssClass="error" /> 
								<spring-form:hidden id="titolare_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice" />								
							</div>
							<div class="form-group">
								<c:choose>							
								<c:when test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
											<label><fmt:message key="mercatid.label.occupante" /></label>							
											<spring-form:input id="occupante_id" size="70" 
												path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.descrizioneRichiedente" 
												cssClass="searchbox" onchange="checkValue(this,'occupante_hidden')" 
													onkeydown="javascript:return searchAll(this,event)"/> 
											<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="occupante_hidden" 
												idInput="occupante_id" inputTitleKey="label.ricerca"/>
											<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante" cssClass="error" /> 
											<spring-form:hidden id="occupante_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice" />
								</c:when>
								<c:otherwise>
									<label><fmt:message key="mercatid.label.occupante" /></label>		
									<spring-form:input id="occupante_id" size="70" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.descrizioneRichiedente" disabled="true" />
									<spring-form:hidden id="occupante_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice" />
									<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
										<jsp:include page="modifica_occupante.jsp" >
												<jsp:param name="idAutorizzazione" value="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.id.codice}" />
												<jsp:param name="paginaChiamante" value="${_urlback}" />
										</jsp:include>				
									</spring-security:authorize>
								</c:otherwise>
								</c:choose>								
							</div>							
							<div class="form-group">
								<label><fmt:message key="label.concessione_tipologia_concessione" /></label>
								<spring-form:select id="selectTipoConcessione" path="concessioneInsert.concessionitipi.tipoconcessione" onchange="showTipologia(this);">
									<spring-form:option value=""></spring-form:option>
									<spring-form:options items="${concessionitipis}" itemLabel="descrizione" itemValue="tipoconcessione"></spring-form:options>
								</spring-form:select>
								<spring-form:errors path="concessioneInsert.concessionitipi" cssClass="error" />
							</div>
							<div class="form-group" id="tipologiaStagionaleDiv">
								<label><fmt:message key="label.concessione_tipologia_stagionale_da" /></label>
								<spring-form:input id="stagionaleda_id" path="concessioneInsert.stagionaledaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" />
								<init:calendar imagePath="/images/cal.gif" idImage="cal_stagionaleda_id" idInput="stagionaleda_id" textKey="label.calendar" />
								<spring-form:errors	path="concessioneInsert.stagionaleda" cssClass="error"	/>
								<label><fmt:message key="label.concessione_tipologia_stagionale_a" /></label>								
								<spring-form:input id="stagionalea_id" path="concessioneInsert.stagionaleaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" />
								<init:calendar imagePath="/images/cal.gif" idImage="cal_stagionalea_id" idInput="stagionalea_id" textKey="label.calendar" />
								<spring-form:errors path="concessioneInsert.stagionalea" cssClass="error" />
							</div>
							<div class="form-group">
								<label><fmt:message key="label.concessione_scadenza" /></label>								
								<spring-form:input id="scadenza_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.datascadenza" size="10" onblur="isValidDate(this,true);" /> 
								<init:calendar imagePath="/images/cal.gif" idImage="cal_scadenza_id" idInput="scadenza_id" textKey="label.calendar"/> 
								<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.datascadenza" cssClass="error" />								
							</div>
							<div class="form-group">
								<label><fmt:message key="label.concessione_causale_acquisizione" /></label>								
								<spring-form:select id="selectCausaleAcquisizione" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice" onchange="viewDataAffitto()">
									<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
									<spring-form:options items="${concessionicausalisAcq}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
								</spring-form:select>
								<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice" cssClass="error" />
								<div id="dataAffitto">
									<label><fmt:message key="label.concessione_data_fine_affitto" /></label>
									<spring-form:input id="dataFineAffitto_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataFineAffitto" size="10" onblur="isValidDate(this,true);" /> 
									<init:calendar imagePath="/images/cal.gif" idImage="cal_dataFineAffitto_id" idInput="dataFineAffitto_id" textKey="label.calendar"/> 
									<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataFineAffitto" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label for="flag_attiva_id"><fmt:message key="label.concessione_attiva" /></label>
								<spring-form:checkbox path="concessioneInsert.autorizzazioniByFkAutconcAutatt.flagAttiva" id="flag_attiva_id" onclick="gestFlagAttiva();"/>
							</div>
							<div class="form-group" id="dati_cessazione_id">
								<label><fmt:message key="label.causale_cessazione" /></label>								
								<spring-form:select id="selectCausaleCessazione" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.id.codice">
									<spring-form:option value=""></spring-form:option>
									<spring-form:options items="${concessionicausalisCess}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
								</spring-form:select>
								<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.id.codice" cssClass="error" />								
								<label><fmt:message key="label.data_cessazione" /></label>
								<spring-form:input id="cessazione_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataCessazione" size="10" onblur="isValidDate(this,true);" /> 
								<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
								<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataCessazione" cssClass="error" />			
							</div>
							<c:if test="${concessioniCommand.displayMode ne concessioniCommand.displayConstants.NEW}">
								<div class="form-group">
									<label for="modificaBloccata_id"><fmt:message key="label.autorizzazione_completata" /></label>
									<spring-form:checkbox path="concessioneInsert.autorizzazioniByFkAutconcAutatt.modificaBloccata" id="modificaBloccata_id" onclick=""/>
								</div>
							</c:if>
						</fieldset>
						<fieldset>	
							<legend><fmt:message key="label.concessione_dati_della_manifestazione" /></legend>			
							<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
								<!-- L'albero del procedimento scelto ha un mercato configurato -->
								<c:if test="${concessioniCommand.isManifestazioneDaProcedimentoPresente}">
								    <input type="hidden" value="${concessioniCommand.isManifestazioneDaProcedimentoPresente}" name="concessioniCommand.isManifestazioneDaProcedimentoPresente"></input>
									<div class="form-group" id="mercati">
										<label><fmt:message key="label.manifestazione" /></label>
										<spring-form:input readonly="true" id="mercati_id" path="concessioneInsert.mercati.descrizione" size="70"	/> 
										<spring-form:hidden id="mercati_hidden" path="concessioneInsert.mercati.id.codice" />
										<a class="vbg-btn btn-dettaglio" title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />" href="javascript:composizioneMercato(document.getElementById('mercati_hidden').value,document.getElementById('mercatiUso_hidden').value);">
										</a>
									</div>									
									<div class="form-group" id="mercatiUso" style="<%=mercatiUso%>">
										<label><fmt:message key="label.mercati_uso" /></label>
										<spring-form:input path="concessioneInsert.mercatiUso.descrizione" readonly="true" size="35"/>	
										<spring-form:hidden path="concessioneInsert.mercatiUso.id.codice" id="mercatiUso_hidden"/>
									</div>																		
									<div class="form-group" id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
										<label><fmt:message key="label.posteggio" /></label>		
																	
										<spring-form:select id="selectPosteggio" path="concessioneInsert.mercatiD.id.codice" onchange="mostraModificaPosteggio(this);" />
										<a class="addColumn" href="javascript:nuovoPosteggio();" title="<fmt:message key="label.aggiungi_posteggio" />">
											<label><fmt:message key="label.add.record.image" /></label>
										</a>
										<a id="posteggioDettaglio_id" style="display: none;" class="dettaglioColumn" href="javascript:modificaPosteggio();"  title="<fmt:message key="label.edit.record" /> <fmt:message key="label.posteggio" />">
											<label><fmt:message key="label.edit.record.image" /></label>
										</a>
										
										<spring-form:errors path="concessioneInsert.mercatiD.id.codice" cssClass="error" />
									</div>
								</c:if>
								<!-- L'albero del procedimento non scelto ha un mercato configurato, c'è la possibilità di scegliere tra quelli config. -->
								<c:if test="${!concessioniCommand.isManifestazioneDaProcedimentoPresente}">
									<div class="form-group" id="mercati">
										<label><fmt:message key="label.manifestazione" /></label>
										<jsp:include page="../includes/autocompletergenerico.jsp" >
											<jsp:param name="idElemento" value="mercati" />		
											<jsp:param name="propertyPath" value="concessioneInsert.mercati" />				
											<jsp:param name="pathPropertyDescription" value="concessioneInsert.mercati.descrizione" />
											<jsp:param name="pathPropertyCode" value="concessioneInsert.mercati.id.codice" />
											<jsp:param name="autocompleterAjax" value="findMercati.htm" />	
											<jsp:param name="titleKey" value="label.manifestazione" />
										</jsp:include>
									</div>
									<div class="form-group" id="mercatiUso" style="<%=mercatiUso%>">
										<label><fmt:message key="label.mercati_uso" /></label>
										<script type="text/javascript">
											function filtermercato(element, entry) { 
												return entry + "&codiceMercato=" + document.getElementById("mercati_hidden").value;
											}
										
											function setHiddenFieldmercati(inputField,listItem){
												var a = listItem.id;
												document.getElementById('mercatiUso_id').value = inputField.value;
												document.getElementById('mercatiUso_hidden').value = a;
												assegnaPosteggio();
												}
										</script>
										<jsp:include page="../includes/autocompletergenerico.jsp" >
											<jsp:param name="idElemento" value="mercatiUso" />		
											<jsp:param name="propertyPath" value="concessioneInsert.mercatiUso" />				
											<jsp:param name="pathPropertyDescription" value="concessioneInsert.mercatiUso.descrizione" />
											<jsp:param name="pathPropertyCode" value="concessioneInsert.mercatiUso.id.codice" />
											<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
											<jsp:param name="ajaxCallBack" value="filtermercato"/>
											<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati"/>
											<jsp:param name="titleKey" value="label.giorno" />
										</jsp:include>				
									</div>									
									<div class="form-group" id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
										<label><fmt:message key="label.posteggio" /></label>										
										<spring-form:select id="selectPosteggio" path="concessioneInsert.mercatiD.id.codice" onchange="mostraModificaPosteggio(this);" />										
										<a class="addColumn" href="javascript:nuovoPosteggio();" title="<fmt:message key="label.aggiungi_posteggio" />">
											<label><fmt:message key="label.add.record.image" /></label>
										</a>
										<a id="posteggioDettaglio_id" style="display: none;" class="dettaglioColumn" href="javascript:modificaPosteggio();"  title="<fmt:message key="label.edit.record" /> <fmt:message key="label.posteggio" />">
											<label><fmt:message key="label.edit.record.image" /></label>
										</a>										
										<spring-form:errors path="concessioneInsert.mercatiD.id.codice" cssClass="error" />
									</div>								
								</c:if>
							</c:if>				
							<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">												 
								<div class="form-group">
									<td colspan="4">								
										<table class="vbg-table">										
										<thead>
											<th><fmt:message key="label.manifestazione" /></th>
											<th><fmt:message key="label.mercati_uso" /></th>
											<th><fmt:message key="label.posteggio" /></th>
											<th colspan="2"><fmt:message key="label.azioni" /></th>
										</thead>																	
										<c:forEach var="concessioni" items="${concessioniCommand.concessionis}">										
											<tr>	
												<td>${concessioni.mercati.descrizione}													
													<a class="dettaglio" title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />"
													href="javascript:composizioneMercato(${concessioni.mercati.id.codice}, ${concessioni.mercatiUso.id.codice});">
													<i class="fa fa-search" aria-hidden="true"></i>
													</a>													
												</td>											
												<td>${concessioni.mercatiUso.descrizione}</td>
												<td>${concessioni.mercatiD.codiceposteggio}																					
													<c:if test="${fn:length(mappaConcPosteggiLiberi[concessioni.id.codice])>0}">	
													<!-- TODO: popup -->
														<vbg-modal id="popup-selectPosteggio" >
															<div slot='body'>
																<h1>Assegna Posteggio Libero</h1>
															    <select id="selectPosteggio_${concessioni.id.codice}" name="selectPosteggio" onchange="(${concessioni.id.codice},'selectPosteggio_${concessioni.id.codice}');">	
																	<c:forEach  items="${mappaConcPosteggiLiberi[concessioni.id.codice]}" var="posteggio">
																		<c:if test="${concessioni.mercatiD.id.codice ne posteggio.codice}">
																			<option value="${posteggio.codice}" >${posteggio.descrizione}</option>
																		</c:if>
																	</c:forEach>				
																</select>
															</div>
															<div slot='footer'>
																<a class="btn btn-primary" href="javascript:assegnaAltroPosteggio(${concessioni.id.codice},${istanza.id.codice },${autorizzazioneAttuale.id.codice },'div_selectPosteggio_${concessioni.id.codice}')"><fmt:message key="button.ok" /></a>	
																<a class="btn btn-secondary" id="closeButtonModal"><fmt:message key="button.back" /></a>
															</div>		
														</vbg-modal>																								
													</c:if>	
												</td>	
												<td>
													<a id="posteggioDettaglio_id" class="dettaglioColumn" href="javascript:modificaPosteggio(${concessioni.mercatiD.id.codice}, ${istanza.id.codice }, ${autorizzazioneAttuale.id.codice });"  
														 title="<fmt:message key="label.edit.record" /> <fmt:message key="label.posteggio" />">
														<label><fmt:message key="label.edit.record.image" /></label>
													</a>
												</td>		
												<td>									
													<div class="form-button">
														<c:if test="${fn:length(mappaConcPosteggiLiberi[concessioni.id.codice])>0}">	
															<a class="btn btn-primary" href="javascript:apriModificaPosteggio('div_selectPosteggio_${concessioni.id.codice}')"><fmt:message key="button.assegna_posteggio_libero" /></a>	
														</c:if>
														<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../autorizzazioni/createScambioPosteggio.htm?codiceConcessione=${concessioni.id.codice}&codiceAutorizzazione=${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.id.codice}&codiceIstanza=${param.codiceIstanza}','')"><fmt:message key="button.scambia_posteggio" /></a>
													</div>									
												</td>							
											</tr>												
										</c:forEach>															
										</table>
									</td>
								</div>
							</c:if>
							<c:set var="readonlyAutColl">false</c:set>
							<c:set var="readonlyAutCollSearchClass">searchbox</c:set>	
							<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
								<c:set var="readonlyAutColl">true</c:set>
								<c:set var="readonlyAutCollSearchClass"></c:set>
							</c:if>
						</fieldset>
						<fieldset>
							<legend>
								<spring-form:checkbox path="inserisciAutorizzazione" id="inserisciAutorizzazione_id" onclick="visualizzaAutorizzazione();"/> 
									<label for="inserisciAutorizzazione_id"><fmt:message key="label.concessione_dati_della_autorizzazione_inserisci_autorizzazione" /></label>
							</legend>
							<fieldset id="ricercaAutorizzazione">
								<legend><fmt:message key="label.cerca_un_autorizzazione_esistente" /></legend>							
								<div class="form-group" id="autorizzazione_cerca_div">
									<label><fmt:message key="label.autorizzazione" /></label>
									<script type="text/javascript">
										function cercaAut(inputField,listItem){
											var a = listItem.id;
											document.getElementById('cerca_aut_id').value = inputField.value;
											document.getElementById('cerca_aut_id_hidden').value = a;
											/* $('cerca_aut_id_choices').fade(); */
											
											if(document.getElementById('cerca_aut_id_hidden').value!=''){
											}
										}
									</script>					
									<spring-form:input id="cerca_aut_id" path="autorizzazioneAssociata.autoriznumero" cssClass="searchbox" onchange="checkValue(this,'cerca_aut_id_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
									<init:autocompleter methodAjax="findAutorizzazioniIstanza.htm?codiceIstanza=${param.codiceIstanza}" afterUpdateElement="cercaAut"  idHidden="cerca_aut_id_hidden"  idInput="cerca_aut_id" inputTitleKey="label.cerca_un_autorizzazione_esistente_per_istanza"/>
									<spring-form:hidden id="cerca_aut_id_hidden" path="autorizzazioneAssociata.id.codice"  />	
								</div>
								<div class="form-button" >																	
									<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../autorizzazioni/createAutorizzazione.htm?codiceIstanza=${istanza.id.codice }','')"><fmt:message key="button.nuova_autorizzazione" /></a>										
								</div>
								
							</fieldset>
							<fieldset id="datiAutorizzazione">
								<legend><fmt:message key="label.concessione_dati_della_autorizzazione" /></legend>							
								<div class="form-group" id="autorizzazione_div">
									<label><fmt:message key="label.autorizzazione_registro" /></label>
									<script type="text/javascript">
										function updateRegistro(inputField,listItem){
											var a = listItem.id;
											document.getElementById('registro_id').value = inputField.value;
											document.getElementById('registro_hidden').value = a;
											$('registro_id_choices').fade();
											if(document.getElementById('registro_hidden').value!=''){
												doSubmit('updateRegistroConcessioneAutColl.htm?codice=${concessioneInsert.autorizzazioniByFkAutconcAutcoll.id.codice}&codiceIstanza=${param.codiceIstanza}&codiceRegistro=' + document.getElementById('registro_hidden').value+'#autorizzazione','',document.inviodati);
											}
										}
									</script>
									<spring-form:input readonly="${readonlyAutColl}" id="registro_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizioneCompleta" cssClass="${readonlyAutCollSearchClass}" onchange="checkValue(this,'registro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
									<c:if test="${readonlyAutColl eq 'false'}"> 
										<init:autocompleter methodAjax="findTipologiaRegistriPerManifestazioni.htm?codicecomune=${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutcoll.istanza.comune.codicecomune}" afterUpdateElement="updateRegistro"  idHidden="registro_hidden"  idInput="registro_id" inputTitleKey="label.ricerca_tipo_registro"/>
									</c:if>
									<spring-form:errors	path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizioneCompleta" cssClass="error"	/>
									<spring-form:hidden id="registro_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.id.codice"  />					
								</div>				
								<c:if test="${concessioniCommand.registroAutorizzazioneProtocollo eq false}">
									<div class="form-group" id="autorizzazione_div2">
										<label><fmt:message key="label.autorizzazione_numero_autorizzazione" /></label>										
										<spring-form:input id="concessioneNumeroAutorizzazione_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autoriznumero" readonly="${readonlyAutColl}"  size="20" />
										<spring-form:errors	path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autoriznumero" cssClass="error" />										
										<c:if test="${readonlyAutColl eq 'true'}">
											<c:if test="${ not empty concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutcoll.id.codice }">
												<div style="float: left;">
													<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?codice=${concessioniCommand.entity.autorizzazioniByFkAutconcAutcoll.id.codice }&codiceIstanza=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${concessioniCommand.entity.autorizzazioniByFkAutconcAutcoll.transientEstremiAut}">
														<label><fmt:message key="label.edit.record.image" /></label>
													</a>
												</div>
											</c:if>
										 </c:if>									
										<label><fmt:message key="label.autorizzazione_data_validita_autorizzazione" /></label>
										<spring-form:input id="autorizdata2_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizdata" readonly="${readonlyAutColl}" size="10" onblur="isValidDate(this,true);" />
									 	<c:if test="${readonlyAutColl eq 'false'}">
									 		<init:calendar imagePath="/images/cal.gif"	idImage="cal_autorizdata2_id" idInput="autorizdata2_id" textKey="label.calendar"/> 
									 	</c:if>
									 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizdata" cssClass="error" />
									</div>
									<div class="form-group" id="autorizzazione_div2_1">
										<label><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></label>
										<spring-form:input id="dataRilascio_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataRilascio" readonly="${readonlyAutColl}" size="10" onblur="isValidDate(this,true);" />
									 	<c:if test="${readonlyAutColl eq 'false'}">
									 		<init:calendar imagePath="/images/cal.gif"	idImage="cal_dataRilascio_id" idInput="dataRilascio_id" textKey="label.calendar"/> 
									 	</c:if>
									 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataRilascio" cssClass="error" />
									 	<c:if test="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.flagGestanzianita }">
										 	<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
										 	<spring-form:input id="dataAnzianita_id2" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataAnzianita" readonly="${readonlyAutColl}" size="10" onblur="isValidDate(this,true);" />
										 	<c:if test="${readonlyAutColl eq 'false'}">
										 		<init:calendar imagePath="/images/cal.gif"	idImage="cal_dataAnzianita_id2" idInput="dataAnzianita_id2" textKey="label.calendar"/> 
										 	</c:if>
										 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataAnzianita" cssClass="error" />
									 	</c:if>
									</div>
								</c:if>
								<c:if test="${concessioniCommand.registroAutorizzazioneProtocollo eq true}">		
									<div class="form-group" id="autorizzazione_div2">
										<label><fmt:message key="label.autorizzazione_estremi_da_protocollo" /></label>
									</div>
									<div class="form-group" id="autorizzazione_div3">
										<label><fmt:message key="label.autorizzazione_numero_autorizzazione" /></label>
										<spring-form:input id="concessioneNumeroAutorizzazione_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autoriznumero" size="10" disabled="true"/> 
										<spring-form:errors	path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autoriznumero" cssClass="error" />
										<label><fmt:message key="label.autorizzazione_data_validita_autorizzazione" /></label>
										<spring-form:input id="autorizdata2_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizdata" size="10" disabled="true"/>
									 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizdata" cssClass="error" />
									</div>								
									<div class="form-group" id="autorizzazione_div3_1">
										<label><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></label>
										<spring-form:input id="dataRilascio_id" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataRilascio" size="10" disabled="true"/>
									 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataRilascio" cssClass="error" />
									 	<c:if test="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.flagGestanzianita}">
										 	<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
										 	<spring-form:input id="dataAnzianita_id2" path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataAnzianita" readonly="${readonlyAutColl}" size="10" onblur="isValidDate(this,true);" />
										 	<c:if test="${readonlyAutColl eq 'false'}">
										 		<init:calendar imagePath="/images/cal.gif"	idImage="cal_dataAnzianita_id2" idInput="dataAnzianita_id2" textKey="label.calendar"/> 
										 	</c:if>
										 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.dataAnzianita" cssClass="error" />
									 	</c:if>
									</div>								
								</c:if>
								<div class="form-group" id="autorizzazione_div4">
									<label><fmt:message key="label.concessione_autorizzata_da" /></label>
									<spring-form:input id="responsabile_id" size="50" readonly="${readonlyAutColl}"  path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizresponsabile" />
									<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizresponsabile" cssClass="error" />
									<label><fmt:message key="label.concessione_autorizzata_il" /></label>
									<spring-form:input id="autorizdataregistr_id" readonly="${readonlyAutColl}"  path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizdataregistr" size="10" onblur="isValidDate(this,true);" />
									<c:if test="${readonlyAutColl eq 'false'}"> 
										<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdataregistr_id" idInput="autorizdataregistr_id" textKey="label.calendar" />
									</c:if> 
									<spring-form:errors	path="concessioneInsert.autorizzazioniByFkAutconcAutcoll.autorizdataregistr" cssClass="error"/>
								</div>
							</fieldset>
							</fieldset>
						</c:otherwise>
					</c:choose>
				</div>

		<vbg-modal id="vbg-modal-dettaglio-mercati-spuntisti"></vbg-modal>
		
		</spring-form:form>
		<jsp:include page="./funzioniJS.jsp" />
		</div>
		
		<script type="text/javascript">
			var isEditmode = true;	
			var posteggioSelezionato = '';
		</script>
		
		<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">	
			<script type="text/javascript">
				var isEditmode = false;
			</script>	
		</c:if>
	
		<c:if test="${concessioniCommand.inserisciAutorizzazione eq true}">		
			<script type="text/javascript">
				showHideDiv('ricercaAutorizzazione');
				showHideDiv('datiAutorizzazione');
				/* showHideDiv('autorizzazione_cerca_title_div');
				showHideDiv('autorizzazione_cerca_div');
				showDiv('autorizzazionetitle_div');
				showDiv('autorizzazione_div');
				showDiv('autorizzazione_div2');
				showDiv('autorizzazione_div3');
				showDiv('autorizzazione_div2_1');
				showDiv('autorizzazione_div3_1');
				showHideDiv('autorizzazione_div4'); */
			</script>
		</c:if>
	</c:if>	

	<div class="form-button">
		<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
			<a class="btn btn-primary" href="javascript:inserisci(false);"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
			<c:if test="${concessioniCommand.subentroPresente eq false}">	
				<a class="btn btn-primary" href="javascript:aggiorna();"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../mercatipresenzestorico/listDaAutorizzazione.htm?autId=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.id.codice}','')"><fmt:message key="button.presenze" /></a>
				<c:if test="${isTipoInformazioni}">
					<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../autorizzazioni/merceologie.htm?idautorizzazione=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.id.codice}','')"><fmt:message key="label.dettaglio_informazione" /></a>				
				</c:if>
				<c:if test="${isGestioneSpuntistiAttiva}"> 
					<a class="btn btn-primary" href="javascript:visualizzaDettaglioAutorizSpuntista();"><fmt:message key="label.spuntista_mercati" /></a>
				</c:if>
				<a class="btn btn-primary" href="javascript:doSubmit('validaDeleteConcessione.htm?codiceAutorizzazione=${concessioniCommand.entity.idAutorizzazione}&codiceIstanza=${param.codiceIstanza}&return_to_page='+encodeURIComponent('${_urlback}'),'<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
	<c:if test="${not empty concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris}">
		<br class="clear" />
		<div class="vbg-form">
			<fieldset>
			<legend><fmt:message key="label.passaggi_della_concessione" /></legend>				
				<table class="vbg-table">
					<thead>								
						<th><fmt:message key="label.concessione" /></th>
						<th><fmt:message key="label.autorizzazione" /></th>
						<th><fmt:message key="label.istanza" /></th>
						<th><fmt:message key="label.concessione_titolare" /></th>
						<th><fmt:message key="mercatid.label.occupante"/></th>					
						<th><fmt:message key="label.causale_acquisizione" /></th>
						<th><fmt:message key="label.causale_cessazione" /></th>
						<th><fmt:message key="label.data_cessazione" /></th>								
						<th><fmt:message key="label.mercati" /></th>
						<th><fmt:message key="label.mercati_uso" /></th>
						<th><fmt:message key="label.posteggio" /></th>								
					</thead>
					<tbody class="tbody" >
						<tr class="odd">
							<td>${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.transientEstremiAut}</td>
							<td>${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutcoll.transientEstremiAut}</td>
							<td>
								<a href ="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.istanza.id.codice }&software=${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.istanza.software.codice }');" >${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.istanza.numeroistanza}</a>
							</td>
							<td>${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente}</td>
							<td>
								<c:choose>
									<c:when test="${not empty concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.istanza.titolarelegale}">
										${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.istanza.titolarelegale.descrizioneRichiedente}
									</c:when>
									<c:otherwise>
										${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.istanza.richiedente.descrizioneRichiedente}
									</c:otherwise>									
								</c:choose>
							</td>					
							<td>${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.descrizione }</td>
							<td>${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.descrizione }</td>
							<td><fmt:formatDate value="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
							<td>${concessioniCommand.concessioneInsert.mercati.descrizione}</td>
							<td>${concessioniCommand.concessioneInsert.mercatiUso.descrizione}</td>
							<td>${concessioniCommand.concessioneInsert.mercatiD.codiceposteggio}</td>
						</tr>
						<%int i=1;%>
						<c:forEach var="subentro" items="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris}" varStatus="subentriStatus">
							<c:forEach var="subentro2" items="${subentro.autSubentrisConcs}" varStatus="subentriStatus2">					
								<tr class="<%=(i%2)==0?"odd":"even"%>"  >
									<td>${subentro.transientEstremiAut}</td>
									<td>
										${subentro2.autorizzazioniSubentriByFkAutconcAutcoll.transientEstremiAut}
									</td>
									<td>
										<a href ="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${subentro.istanze.id.codice }&software=${subentro.istanze.software.codice }');" >${subentro.istanze.numeroistanza}</a>
									</td>
									<td>${subentro.anagrafe.descrizioneRichiedente}</td>
									<td>
										<c:choose>
											<c:when test="${not empty subentro.istanze.titolarelegale}">
												${subentro.istanze.titolarelegale.descrizioneRichiedente}
											</c:when>
											<c:otherwise>
												${subentro.istanze.richiedente.descrizioneRichiedente}
											</c:otherwise>									
										</c:choose>
									</td>				
									<td>${subentro.concessionicausaliByFkAutsubConccausAcq.descrizione }</td>
									<td>${subentro.concessionicausaliByFkAutsubConccausCess.descrizione }</td>
									<td><fmt:formatDate value="${subentro.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
									<%if ( i==1 ) { // solo su primo subentro possibile modifica%>
									<jsp:include page="modifica_data_cessazione.jsp" >
											<jsp:param name="idAutorizzazioneSubentri" value="${subentro.id.codice}" />
											<jsp:param name="paginaChiamante" value="${_urlback}" />
									</jsp:include>
									<%} %>		
									</td>
									<td>${subentro2.mercati.descrizione}</td>
									<td>${subentro2.mercatiUso.descrizione}</td>
									<td>${subentro2.mercatiD.codiceposteggio}</td>
								</tr>
											<% i++; %>		
						 	</c:forEach>
						</c:forEach>			
					</tbody>
				</table>						
			</fieldset>
		</div>
	</c:if>

</body>
</html>