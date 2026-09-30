<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="batchscadenzario.label.scadenzario.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="batchscadenzario.label.scadenzario.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<div id="subcontent">
	<input type="hidden" id="chiave_ricerca" value="batchscadenzario_search" />
	<input type="hidden" id="nome_form_ricerca" value="batchScadenzarioFilter" />
	<spring-form:form commandName="batchScadenzarioFilter" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
	        <jsp:param name="commandName" value="batchScadenzarioFilter" />
	    </jsp:include>
	    <jsp:include page="../includes/ricerche.jsp" />
	    <c:if test="${not empty msgUtente }">
	    <br />
			<div id="messaggio_utente_readonly" class="error_header" >
	    		${msgUtente}
	    	</div>
	    <br />	
		</c:if>		
	    <table>
			<tr>
				<td>
					<fmt:message key="batchscadenzario.label.periodo" />
				</td>
				<td>
					<fmt:message key="batchscadenzario.label.dalla_data" />
					<spring-form:input id="dallaData_id" path="dallaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData_id" textKey="label.calendar" /> 			  	
					<spring-form:errors path="dallaData" cssClass="error" delimiter="," />
					&nbsp;
					<fmt:message key="batchscadenzario.label.alla_data" />
					<spring-form:input id="allaData_id" path="allaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData_id" textKey="label.calendar" /> 
					<spring-form:errors path="allaData" cssClass="error" delimiter="," />
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="batchscadenzario.label.numero_istanza" />
				</td>
				<td>
					<spring-form:input id="numeroIstanza_id" path="numeroIstanza" size="10" />
					<spring-form:errors path="numeroIstanza" cssClass="error" delimiter="," />
				</td>
			</tr>	
			<c:if test="${sessionScope.software eq 'TT'}">		
				<tr>
						<td><fmt:message key="label.considera_le_scadenze_per_software" /></td>
						<td>
						
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="scadSoftware_id" />		
								<jsp:param name="propertyPath" value="scadSoftware" />				
								<jsp:param name="pathPropertyDescription" value="scadSoftware.descrizione" />
								<jsp:param name="pathPropertyCode" value="scadSoftware.codice" />
								<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=true" />							
								<jsp:param name="titleKey" value="label.ricerca_software" />
							</jsp:include>					
							<spring-form:errors path="scadSoftware" cssClass="error" />
							<init:help idHelp="help_scadSoftware" textKey="help.responsabili.scadSoftware"/>
						</td>
				</tr>
			</c:if>			
			<tr>
				<td>
					<fmt:message key="batchscadenzario.label.operatore_responsabile_istruttore" />
				</td>
				<td>
					<spring-form:input id="responsabile_id" path="responsabile.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabile_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67" />
					<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabile_hidden" idInput="responsabile_id" inputTitleKey="label.ricerca_responsabile" />
					<spring-form:errors path="responsabile.responsabile" cssClass="error" /> 
					<spring-form:hidden id="responsabile_hidden" path="responsabile.id.codice" />
				</td>
			</tr>
			<c:if test="${sessionScope.software eq 'TT'}">
				<tr>
						<td><fmt:message key="label.considera_le_scadenze_per_stato_pratica" /></td>
						<td>
							<spring-form:select path="scadComportamento" >
							    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
								<spring-form:option value="0"><fmt:message key="label.stato_pratiche_attive"/></spring-form:option>
								<spring-form:option value="1"><fmt:message key="label.stato_pratiche_chiuse"/></spring-form:option>
							</spring-form:select> 
							<spring-form:errors path="scadComportamento" cssClass="error" />
						</td>
			 	</tr>
			</c:if>
			
			<c:if test="${sessionScope.software ne 'TT'}">
			<input type="hidden" name="softwareSelezionati" value="${sessionScope.software}" />
			<tr>
				<td valign="top">
					<fmt:message key="batchscadenzario.label.tipologia_intervento" />
				</td>
				<td>
					<jsp:include page="../includes/searchAlberoProc.jsp">
						<jsp:param name="propertyPath" value="intervento.vwAlberoproc" />								
						<jsp:param name="pathPropertyDescription" value="intervento.vwAlberoproc.scDescrizione" />
						<jsp:param name="pathPropertyCode" value="intervento.id.codice" />
						<jsp:param name="isSelectLeafDisable" value="true" />
						<jsp:param name="isSelectNodoPadre" value="true" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="batchscadenzario.label.movimento_fatto" />
				</td>
				<td>
					<spring-form:input id="tipoMovimentoFatto_id" path="tipoMovimentoFatto.movimento" cssClass="searchbox" onchange="checkValue(this,'tipoMovimentoFatto_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67" />
					<init:autocompleter methodAjax="findTipiMovimento.htm?includiDisabilitate=true" idHidden="tipoMovimentoFatto_hidden" idInput="tipoMovimentoFatto_id" inputTitleKey="label.ricerca_tipo_movimento" />
					<spring-form:errors path="tipoMovimentoFatto.movimento" cssClass="error" /> 
					<spring-form:hidden id="tipoMovimentoFatto_hidden" path="tipoMovimentoFatto.id.tipomovimento" />
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="batchscadenzario.label.tipo_movimento_da_fare" />
				</td>
				<td>
					<spring-form:input id="tipoMovimentoDaFare_id" path="tipoMovimentoDaFare.movimento" cssClass="searchbox" onchange="checkValue(this,'tipoMovimentoDaFare_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67" />
					<init:autocompleter methodAjax="findTipiMovimento.htm?includiDisabilitate=true" idHidden="tipoMovimentoDaFare_hidden" idInput="tipoMovimentoDaFare_id" inputTitleKey="label.ricerca_tipo_movimento" />
					<spring-form:errors path="tipoMovimentoDaFare.movimento" cssClass="error" /> 
					<spring-form:hidden id="tipoMovimentoDaFare_hidden" path="tipoMovimentoDaFare.id.tipomovimento" />
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="batchscadenzario.label.stato_istanza" />
				</td>
				<td>
					<spring-form:select id="stati_id" path="statiIstanza" items="${stati}" itemLabel="stato" itemValue="id.codicestato" multiple="true" size="${fn:length(stati)}" />
				</td>
			</tr>
			</c:if>
		</table>
		<script type='text/javascript'>
			$('dallaData_id').focus();	
		</script>	
	</spring-form:form>
	</div>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('list.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>