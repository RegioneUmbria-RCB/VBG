<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.report.helper.TypeReport"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.archivio_istanze" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.archivio_istanze" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include> 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../report/createStatisticaPentahoPerModulo" />
	</jsp:include>
	<c:set var="VERTICALIZZAIONE_OBS_EXPORT_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAIONE_OBS_EXPORT)%></c:set>
	<div id="subcontent">	
        <spring-form:form commandName="reportistanze" name="inviodati" target="${targetValue}" id="search-theme-form">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="reportistanze" />
		    </jsp:include>

		    <input type="hidden" id="chiave_ricerca" value="istanze_search" />
			<input type="hidden" id="nome_form_ricerca" value="reportistanze" />
	    	<table width="100%" >
				
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_istanza"/></td>
				</tr>
				<tr id="elementIdBeforeCombo">
					<td width="15%"></td>
					<td colspan="3"></td>
				</tr>
				<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="mostraTutti" value="true" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="istanzeFilter.comune" />
					<jsp:param name="colspan" value="4" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>
				<tr>
					<td><fmt:message key="label.numeroistanza" /></td>
					<td colspan="3">
						<spring-form:input id="numeroistanza_id" path="istanzeFilter.numeroistanza" size="10"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.data_presentazione" /></td>
					<td colspan="3">
						<fmt:message key="label.dalla_data" />
						<spring-form:input id="dallaData_id" path="istanzeFilter.dallaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaData" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.alla_data" />
						<spring-form:input id="allaData_id" path="istanzeFilter.allaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaData" cssClass="error" delimiter="," />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.data_validita" /></td>
					<td colspan="3">
						<fmt:message key="label.dalla_data" />
						<spring-form:input id="dallaDataValidita_id" path="istanzeFilter.dallaDataValidita" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldallaDataValidita" idInput="dallaDataValidita_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaDataValidita" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.alla_data" />
						<spring-form:input id="allaDataValidita_id" path="istanzeFilter.allaDataValidita" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calallaDataValidita" idInput="allaDataValidita_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaDataValidita" cssClass="error" delimiter="," />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.data_protocollo" /></td>
					<td colspan="3">
						<fmt:message key="label.dalla_data" />
						<spring-form:input id="dallaDataProt_id" path="istanzeFilter.dallaDataProtocollo" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataProtInizio" idInput="dallaDataProt_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaDataProtocollo" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.alla_data" />
						<spring-form:input id="allaDataProt_id" path="istanzeFilter.allaDataProtocollo" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataProtFine" idInput="allaDataProt_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaDataProtocollo" cssClass="error" delimiter="," />
					</td>
				</tr>
				
				<tr>
					<td valign="top"><fmt:message key="label.alberoproc" /></td>
					<td colspan="3">
						<jsp:include page="../includes/searchAlberoProc.jsp">
							<jsp:param name="propertyPath" value="istanzeFilter.alberoproc" />								
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.alberoproc.vwAlberoproc.scDescrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.alberoproc.id.codice" />
							<jsp:param name="isSelectLeafDisable" value="true" />
							<jsp:param name="isSelectNodoPadre" value="true" />
						</jsp:include>
					</td>
						
				</tr>
				<tr>
				    <td>&nbsp;</td>
				    <td colspan="3"><spring-form:checkbox path="istanzeFilter.chkexportanagrafetrib"/>
				    	Se spuntato, verranno esclusi i procedimenti che non sono stati decodificati secondo gli interventi proposti dall'anagrafe tributaria
				    </td>
				</tr>
				<tr>
					<td><fmt:message key="label.lavori" /></td>
					<td colspan="3">
						<spring-form:textarea id="lavori_id"  path="istanzeFilter.lavori" cols="73" rows="2" />
					</td>
				</tr>
	            <tr>
					<td><fmt:message key="label.tipiprocedure.procedura" /></td>
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipiprocedure_id" />				
							<jsp:param name="propertyPath" value="istanzeFilter.procedura" />			
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.procedura.procedura" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.procedura.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=true" />
							<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
						</jsp:include>
					</td>
				</tr>
				
				<tr>
					<td><fmt:message key="label.zonizzazione" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="area_id" />				
							<jsp:param name="propertyPath" value="istanzeFilter.istanzearee" />		
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzearee.area.denominazione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzearee.id.codicearea" />
							<jsp:param name="autocompleterAjax" value="findAree.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_aree" />
						</jsp:include>
					</td>
				</tr>	
				
				<tr>
					<td><fmt:message key="label.registro" /></td>
					<td>						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="datiAutorizzazione" />		
							<jsp:param name="propertyPath" value="istanzeFilter.datiAutorizzazione.tipologiaregistro" />				
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.datiAutorizzazione.tipologiaregistro.trDescrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.datiAutorizzazione.tipologiaregistro.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipologiaRegistri.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipologia_registro" />
						</jsp:include>
					</td>
				</tr>
							
	            <tr>
					<td>
						<fmt:message key="label.stato_istanza" />
					</td>
					<td colspan="3">
						<spring-form:select id="statoistanza_id" path="istanzeFilter.chiusura.id.codicestato" onchange="savePreferenceStatoistanza('statoistanza_id')">
						    <spring-form:option value="stato_tutte"><fmt:message key="label.tutte"/></spring-form:option>
						    <spring-form:option value="stato_aperte"><fmt:message key="label.tutte_le_istanze_non_chiuse"/></spring-form:option>
						    <spring-form:option value="stato_chiuse"><fmt:message key="label.tutte_le_istanze_chiuse"/></spring-form:option>
							<spring-form:options items="${statiistanzaList}" itemValue="id.codicestato" itemLabel="stato"/>
						</spring-form:select>
					</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_soggetti_istanza"/></td>
				</tr>		
				<tr>
					<td><fmt:message key="label.richiedente_soggetti_collegati" /></td>
					<td colspan="3">
						<jsp:include page="../includes/anagraficasearch.jsp" >
							<jsp:param name="idElemento" value="richiedenteIdCodice" />						
							<jsp:param name="pathAnagrafica" value="istanzeFilter.richiedente" />
							<jsp:param value="true" name="anagrafeHideFunctions"/>
							<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?statoAnagrafe=ALL" />
						</jsp:include>&nbsp;<fmt:message key="label.ricerca_anagrafica_come_richiedente_azienda_professionista" />
					</td>
				</tr>
		
				
				<tr>
					<td><fmt:message key="label.tipologia_istanza" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipologia_istanze_id" />
							<jsp:param name="propertyPath" value="istanzeFilter.tipologiaistanza" />							
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.tipologiaistanza.tiDescrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.tipologiaistanza.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipologiaistanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipologiaistanze" />
						</jsp:include>
					</td>
				</tr>
				
							
				<tr>
					<td>
						<fmt:message key="label.operatore" />
					</td>
					<td colspan="3">
						<spring-form:input id="responsabile_id" path="istanzeFilter.responsabile.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabile_hidden');" onkeydown="return searchAll(this,event)"  size="67" />
						<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabile_hidden" idInput="responsabile_id" inputTitleKey="label.ricerca_responsabile" />						
						<spring-form:hidden id="responsabile_hidden" path="istanzeFilter.responsabile.id.codice" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.responsabile_procedimento" />
					</td>
					<td colspan="3">
						<spring-form:input id="responsabileProcedimento_id" path="istanzeFilter.responsabileProcedimento.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabileProcedimento_hidden');" onkeydown="return searchAll(this,event)"  size="67" />
						<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabileProcedimento_hidden" idInput="responsabileProcedimento_id" inputTitleKey="label.ricerca_responsabile" />						
						<spring-form:hidden id="responsabileProcedimento_hidden" path="istanzeFilter.responsabileProcedimento.id.codice" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.responsabile_istruttoria" />
					</td>
					<td colspan="3">
						<spring-form:input id="responsabileIstruttoria_id" path="istanzeFilter.responsabileIstruttoria.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabileIstruttoria_hidden');" onkeydown="return searchAll(this,event)"  size="67" />
						<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabileIstruttoria_hidden" idInput="responsabileIstruttoria_id" inputTitleKey="label.ricerca_responsabileIstruttoria" />						
						<spring-form:hidden id="responsabileIstruttoria_hidden" path="istanzeFilter.responsabileIstruttoria.id.codice" />
					</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.iter_istanza"/></td>
				</tr>	
				<tr>
						<td><fmt:message key="label.tipomovimento" /></td>
						<td colspan="3">
							<jsp:include page="../includes/tipimovimentosearch.jsp" >
								<jsp:param name="idElemento" value="tipoMovimentoInputId" />
								<jsp:param name="pathTipomovimento" value="istanzeFilter.tipoMovimento" />
								<jsp:param name="includiDisabilitate" value="true" />
							</jsp:include>					
						</td>
					</tr>
				
				<tr>
					<td><fmt:message key="label.data_movimento"/></td>
					<td colspan="3">
						<fmt:message key="label.dalla_data" />
						<spring-form:input id="dallaDataTipoMov_id" path="istanzeFilter.dallaDataTipoMov" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldallaDataTipoMov" idInput="dallaDataTipoMov_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaDataTipoMov" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.alla_data" />
						<spring-form:input id="allaDataTipoMov_id" path="istanzeFilter.allaDataTipoMov" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calallaDataTipoMov" idInput="allaDataTipoMov_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaDataTipoMov" cssClass="error" delimiter="," />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.endoprocedimenti" />
					</td>
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipiendo_id" />						
							<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti.tipoendo" />
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipo" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.tipoendo.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiendoSWeTT.htm" />
							<jsp:param name="titleKey" value="label.ricerca_tipiendo" />
						</jsp:include>						
					</td>
					</tr>
				
				<%
				//	String displayLocalizzazione = "display:none;";
				//	String styleLocalizzazione = "";
					//gestisce la visualizzazione della tabella altri dati
				//	if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ))) {
				//	    displayLocalizzazione = "";
				//	    styleLocalizzazione="sezioneDatiMeno";
				//	} else {
				//	    displayLocalizzazione = "display:none;";
				//	    styleLocalizzazione="sezioneDatiPiu";
				//	}
				%>
				<%-- 
				<tr class="titoloSezione">
					<td colspan="4">
						<a class="<%=styleLocalizzazione %>" 
							id="id_link_localizzazione" 
							href="javascript:showHidePanel('id_localizzazione_table', 'id_link_localizzazione', '<%= WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ %>', '${pageContext.request.contextPath}/images/');"	
							title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dati_localizzazione"/>">
							<label for="id_link_localizzazione"><fmt:message key="label.dati_localizzazione"/></label>
						</a>
					</td>
				</tr>
				--%>
				<tr class="titoloSezione">
					<td colspan="4">
						<fmt:message key="label.dati_localizzazione"/>
					</td>
				</tr>
				<%-- <tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">--%>
				<tr>	
					<td><fmt:message key="label.indirizzo" /></td>
					<td>						
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="stradario_id" />
							<jsp:param name="propertyPath" value="istanzeFilter.istanzestradario.stradario" />								
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzestradario.stradario.descrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzestradario.stradario.id.codice" />
							<jsp:param name="autocompleterAjax" value="findStradario.htm" />
							<jsp:param name="titleKey" value="label.ricerca_stradario" />
						</jsp:include>
					</td>	
					<td>
						<fmt:message key="label.civico" />
					</td>
					<td>
						<spring-form:input id="civico_id" path="istanzeFilter.istanzestradario.civico" size="5" />
					</td>												
				</tr>
				
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.sorteggi"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.data_sorteggi" /></td>
					<td colspan="3">
						<fmt:message key="label.dalla_data" />
						<spring-form:input id="dallaDataSorteggio_id" path="istanzeFilter.dallaDataSorteggio" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataSortInizio" idInput="dallaDataSorteggio_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaDataSorteggio" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.alla_data" />
						<spring-form:input id="allaDataSorteggio_id" path="istanzeFilter.allaDataSorteggio" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataSortFine" idInput="allaDataSorteggio_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaDataSorteggio" cssClass="error" delimiter="," />
					</td>
				</tr>
				
					
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.altre_informazioni"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.archivio_pratiche" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="archiviopratiche_id" />		
							<jsp:param name="propertyPath" value="istanzeFilter.tipiarchivioistanza" />				
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.tipiarchivioistanza.archivio" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.tipiarchivioistanza.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiarchivioistanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
						</jsp:include>
					</td>
				</tr>
			    <tr>
					<td>
						<fmt:message key="label.tipo_informazione" />
					</td>
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="settori" />					
							<jsp:param name="propertyPath" value="istanzeFilter.istanzeattivita.attivita.settori" />	
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzeattivita.attivita.settori.settore" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzeattivita.attivita.settori.id.codicesettore" />
							<jsp:param name="autocompleterAjax" value="findSettori.htm" />
							<jsp:param name="titleKey" value="label.ricerca_settori" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.dettaglio_informazione" />
					</td>
					<td colspan="3">
						<script type="text/javascript">
							function filtertiposettore(element, entry) {
								if(document.getElementById("settori_hidden")){	
									return entry + "&codicesettore=" + document.getElementById("settori_hidden").value;
								}else{
									return entry;
								}
							}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="attivita" />			
							<jsp:param name="propertyPath" value="istanzeFilter.istanzeattivita.attivita" />			
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzeattivita.attivita.istat" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzeattivita.attivita.id.codiceistat" />
							<jsp:param name="autocompleterAjax" value="findAttivita.htm" />
							<jsp:param name="titleKey" value="label.ricerca_attivita" />
							<jsp:param name="ajaxCallBack" value="filtertiposettore" />
						</jsp:include>						
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.mq_su_cui_si_svolge_lattivita" /></td>
					<td colspan="3">
						<fmt:message key="label.da" />&nbsp;
						<spring-form:input id="damq_id" path="istanzeFilter.daMq" size="6" maxlength="10" />
						&nbsp;
						<fmt:message key="label.a" />&nbsp;
						<spring-form:input id="amq_id" path="istanzeFilter.aMq" size="6" maxlength="10" />
					</td>
				</tr>
				
				<tr>
					
					<td><fmt:message key="label.bando" /></td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="bandi" />			
							<jsp:param name="propertyPath" value="istanzeFilter.bandi" />			
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.bandi.descrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.bandi.id.codice" />
							<jsp:param name="autocompleterAjax" value="findBandi.htm" />
							<jsp:param name="titleKey" value="label.ricerca_bandi" />
						</jsp:include>	
					</td>
				</tr>
				
				<tr><td><fmt:message key="label.graduatoria" /></td>
				    <script type="text/javascript">
						function filterbando(element, entry) {
							if(document.getElementById("bandi_hidden")){	
								return entry + "&codicebando=" + document.getElementById("bandi_hidden").value;
							}else{
								return entry;
							}
						}						
					</script>
				    <td>
				    	<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="graduatoriet" />			
							<jsp:param name="propertyPath" value="istanzeFilter.graduatoriet" />			
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.graduatoriet.descrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.graduatoriet.id.codice" />
							<jsp:param name="autocompleterAjax" value="findGraduatoriet.htm" />
							<jsp:param name="titleKey" value="label.ricerca_graduatorie" />
							<jsp:param name="ajaxCallBack" value="filterbando" />

						</jsp:include>		
				    </td>
				</tr>
				
			    <tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_stampa"/></td>
				</tr>
				<tr>
				   <td><fmt:message key="label.documento_tipo" /></td>
				   <td>
			   		<jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="letteretipo" />			
						<jsp:param name="propertyPath" value="letteretipo" />			
						<jsp:param name="pathPropertyDescription" value="letteretipo.descrizione" />
						<jsp:param name="pathPropertyCode" value="letteretipo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findLettereTipo.htm" />
						<jsp:param name="titleKey" value="label.ricerca_lettere_tipo" />
					</jsp:include>			
				   </td>
				</tr>
				
				<tr>
				    <td><fmt:message key="label.conteggio_mq_o_nr_istanze" /></td>
				    <td colspan="3"><spring-form:checkbox path="conteggioMqOrNrIstanze"/>
				    	Selezionare se si vuole fare il conteggio per superficie, altrimenti verra' effettuato il conteggio per nr. istanze.
				    </td>
				</tr>
											
	    	</table>
	    	<script type="text/javascript">			
				function salvaPreferenza(nomeparametro, objchk){
					var valore = "0";	
					if(objchk.checked==true){
						valore="1";	
					}
					saveUserPreference(nomeparametro, valore);
				}
			</script>	
			  	
	    </spring-form:form>
	</div>
	
	
	<div id="functions">
		<%-- 
		<c:if test="${VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_REQUEST eq VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_STANDARD_REQUEST}">
		<ul>
			<li><a href="javascript:printReport();"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
		</ul>
		</c:if>
		--%>
		
		
		<ul>
		    <li><a href="javascript:printReportEnterprise();"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:exportEnterprise();"><fmt:message key="button.export" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
		</ul>
		
	</div>
    
	<script type="text/javascript">
	$('numeroistanza_id').focus();
	
	
	var executed = false;
	jQuery("*").keypress(function(e) {
	  	  	var code = e.keyCode ? e.keyCode : e.which;
			if(code.toString() == 13) {
			if(!executed){
				executed=true;
					  printReport();	   
				  }
			}
	});
	
	function changeOrdinamento(id)
	{
		
		if(document.getElementById(id).value=='<%=TypeReport.STATISTICHE_ISTANZA_OPERATORE%>')
		{	
			$('label_id').value = 'Operatori';
    		$('field_id').value = 'responsabile.responsabile';
		}
		if(document.getElementById(id).value=='<%=TypeReport.STATISTICHE_ISTANZA_PROCEDURA%>')
		{	
			$('label_id').value = 'Tipo procedura';
    		$('field_id').value = 'alberoproc.tipoProcedura.procedura';
		}
		if(document.getElementById(id).value=='<%=TypeReport.STATISTICHE_ISTANZA_STATO%>')
		{
			$('label_id').value = 'Stato';
    		$('field_id').value = 'chiusura.stato';
		}
		if(document.getElementById(id).value=='<%=TypeReport.STATISTICHE_ISTANZA_PROCEDIMENTO%>')
		{
			$('label_id').value = 'Procedimento';
    		$('field_id').value = 'alberoproc.vwAlberoproc.scDescrizione';
		}
		
			
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
	
	
	
	function printReportEnterprise()
	{
		
		// Modalità dotNet
		//if (${VERTICALIZZAIONE_OBS_EXPORT_REQUEST})
		//{
		//	alert('report modalità donet');
		//	document.inviodati.action='printStatisticheModalitaDotNet.htm';
		//}else// Modalità CARTE & KETTLE
		//{
		//	alert('report modalità CARTE & KETTLE (Funziona come dotnet ora)');
		    
		
			document.inviodati.action='printStatisticheModalitaDotNet.htm';
		//}
		setTimeout("document.inviodati.submit()",10);
		
		
	}
	
	function exportEnterprise()
	{
		
		// Modalità dotNet
		if (${VERTICALIZZAIONE_OBS_EXPORT_REQUEST})
		{
			exportIstanze();
		}else// Modalità CARTE & KETTLE
		{
			goToExportPentahoPanel();
		}
	
	}
	
	
	function goToExportPentahoPanel(){
		var url  = URLDecode('${_urlback}');			
		ajaxHistorySet(url);			
		setTimeout("doSubmit('../report/createExportModalitaPentaho.htm?1=1','',document.inviodati)",10);
	}
	
	
	
	function exportIstanze(){	
		
		
		var secondDlg = new dijit.Dialog({
            title: "<fmt:message key="label.export_dati" />" ,
            style: "overflow:auto; width: 650px;height: 300px;"
        });
		disableFunctions(); 
		new Ajax.Request('<%=request.getContextPath()%>/report/ajaxExportModalitaDotNet.htm', {
			  method: 'post',
			  parameters: {},
			  onSuccess: function(transport){
				  enableFunctions();
				  var response = transport.responseText;		
				  result = parseAjaxResponse(response, true, false);
				  secondDlg.attr("content", result);
			      secondDlg.show();							  
			  },
			  onFailure: function(transport){ 
				  enableFunctions();
			  	  var response = transport.responseText;
				  secondDlg.attr("content", response);
				  secondDlg.show();	
			  }
		});
	}
	
	
	function esporta(){
		var codice = getSelectLabelAndValue(document.getElementById("tipoEsportazione_id"));
		var email = document.getElementById("responsabile_email_id").value;
		var checkInvioMail = false;
		
		if(document.getElementById("invio_email_id").checked)
		{
			if(email == '' || email == null )
			{
				alert('Attenzione, si è deciso di inviare l\'esportazione per e-mail, ma non ne è stata configurata una');
				checkInvioMail = false;
			}else
			{
			checkInvioMail=true;
			}
		}
		if(codice){
			if(codice[0]!=''){
				document.inviodati.action='ajaxCreateExportModalitaDotNet.htm?codice='+ escape(codice[0]) + '&descrizione=' + escape(codice[1])+'&email='+email+'&isInviaMail='+checkInvioMail;
			}
			let target = document.inviodati.target;
			document.inviodati.target= "_blank";
			setTimeout("document.inviodati.submit()",10);
			setTimeout("settaTarget('"+target+"')",20);
		}
	}
	
	function settaTarget(target){
		
		document.inviodati.target = target;
	}
	
	function getSelectLabelAndValue(selectObj){
		
		var pos=selectObj.selectedIndex;
		var valore='';
		var testo='';
		if (pos>-1) {
			valore=selectObj.options[pos].value;
			testo=selectObj.options[pos].label;
		} 
		var valori =	new Array(valore, testo);
		return valori;
	}

	
	
	
	function savePreferenceTypeOrder(obj){
		var a=document.getElementById(obj).value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE%>',a);
	}
	
	function savePreferenceFieldOrder(obj){
		var a=document.getElementById(obj).value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE%>',a);
	}
	
	function savePreferenceStatoistanza(obj){
		var a=document.getElementById(obj).value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA%>',a);
	}
	
	
	
	
	</script>
</body>
</html>