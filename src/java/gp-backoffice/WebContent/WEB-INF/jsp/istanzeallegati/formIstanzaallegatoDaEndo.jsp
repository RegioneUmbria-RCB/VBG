<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.nuovo_istanzaallegato_da_endo" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${domain.id.codice==null}">
			<fmt:message key="label.nuovo_istanzaallegato_da_endo" />
		</c:if> 
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="istanzeallegati" name="innerFormDaendo">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="istanzeallegati" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeallegati.entity.istanza.id.codice}</c:param>
	</c:import>
	<br class="clear"/>
	<table width="100%">
		<tr>
			<td><fmt:message key="label.allegato" /></td>
			<td class="inline-ui-cell">
				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="istanzeallegato_da_endo_id" />
					<jsp:param name="propertyPath" value="allegati" />										
					<jsp:param name="pathPropertyDescription" value="allegati.allegato" />
					<jsp:param name="pathPropertyCode" value="allegati.id.codice" />
					<jsp:param name="autocompleterAjax" value="findAllegatiByProcedimento.htm?codiceInventario=${istanzeallegati.entity.inventarioprocedimenti.id.codice}" />							
					<jsp:param name="titleKey" value="label.ricerca_allegati" />
				</jsp:include>	
				<spring-form:errors path="allegati" cssClass="error"></spring-form:errors>		
			</td>
		</tr>
	</table>
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<c:if test="${domain.id.codice==null}">
			<li><a href="javascript:doSubmit('insertIstanzaallegatoDaEndo.htm','',document.innerFormDaendo)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${domain.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>