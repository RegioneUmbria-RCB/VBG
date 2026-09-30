<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.mercaticontabilitatributi.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.mercaticontabilitatributi.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercaticontabilitatributi/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mercaticontabilitatributi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercaticontabilitatributi" />
		    </jsp:include>
			<table border="0" width="100%">
				<c:if test="${checkMercatiContabilitaTributi > 1}"><fmt:message key="label.impossibile_aggiungere_nuovo_conto" /></c:if>
				<tr>
					<td>
						<fmt:message key="label.conto_accertamento" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="conti" />
							<jsp:param name="propertyPath" value="conti" />
							<jsp:param name="pathPropertyDescription" value="conti.descrizione" />
							<jsp:param name="pathPropertyCode" value="conti.id.codice" />
							<jsp:param name="autocompleterAjax" value="findConti.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_conto" />
						</jsp:include>
						<spring-form:errors path="conti.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td >
						<spring-form:input id="descrizione_mercatiContabilitaTributi_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
						<fmt:message key="help.label.copia_dscrizione_conto" />
					</td>
				</tr>
				<c:set scope="page" value="false" var="stopChangeData"></c:set>
				<c:if test="${mercaticontabilitatributi.id.codice != null && mercaticontabilitatributi.dataFineValidita !=null }">
					<c:set scope="page" value="true" var="stopChangeData"></c:set>
				</c:if>
				<tr>
					<td>
						<fmt:message key="label.data_inizio_validita" />
					</td>
					<td>
					
						<spring-form:input id="data_id" path="dataInizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${stopChangeData}"/>
						<c:if test="${!stopChangeData}">
							<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						</c:if>
						<spring-form:errors path="dataInizioValidita" cssClass="error"/>
						
					
						<fmt:message key="label.data_fine_validita" />
						<spring-form:input id="datafine_id" path="dataFineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${stopChangeData}"/>
						<c:if test="${!stopChangeData}">
							<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="datafine_id" textKey="label.calendar"/>
						</c:if>
						<spring-form:errors path="dataFineValidita" cssClass="error"/>
					</td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${mercaticontabilitatributi.id.codice == null && checkMercatiContabilitaTributi <=1}">
				<li><a href="javascript:doSubmit('insert.htm','<fmt:message key="javascript.confirm.conto_precedente_chiuso" />',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercaticontabilitatributi.id.codice != null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<%-- ><li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>  --%>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<br />
	
</body>
</html>