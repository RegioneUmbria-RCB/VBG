<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${attivita.displayMode==attivita.displayConstants.NEW}">
	<fmt:message key="attivita.label.nuovo_attivita.title" />
</c:if> <c:if test="${attivita.displayMode==attivita.displayConstants.VIEW}">
	<fmt:message key="attivita.label.dettaglio_attivita.title" />
</c:if></title>
</head>
<body>
<span class="titoloPagina"> <c:if
	test="${attivita.displayMode==attivita.displayConstants.NEW}">
	<fmt:message key="attivita.label.nuovo_attivita.title" />
</c:if> <c:if test="${attivita.displayMode==attivita.displayConstants.VIEW}">
	<fmt:message key="attivita.label.dettaglio_attivita.title" />
</c:if> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
   
   <!-- Controllo che il codicesectore non sia null o stringa vuota,se non lo è sto inserendo un attività a partire da un settore  
        ,quindi mostro il settore in uso-->

    <c:if test="${codicesectore !='' && codicesectore!=null}">
	
	
	<div class="parametriDiv">
	<div class="etichetta">
	<div><fmt:message key="settori.label.settore" />:</div>
	</div>
	<div class="parametro">

	<div><c:out value="${settori.settore}" /></div>
	</div>
	</div>
	<br class="clear"/>
</c:if> <spring-form:form commandName="attivita" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="attivita" />
	</jsp:include>
	<table>
	    
		<tr>
			<td><fmt:message key="label.codice" /></td>
			<c:if test="${attivita.displayMode==attivita.displayConstants.NEW}">
				<td class="inline-ui-cell"><spring-form:input id="codiceistat_id"
					path="entity.id.codiceistat" size="15" /> <spring-form:errors
					path="entity.id.codiceistat" cssClass="error" /></td>
			</c:if>
			<c:if test="${attivita.displayMode==attivita.displayConstants.VIEW}">
				<td class="inline-ui-cell"><spring-form:input id="codiceistat_id"
					path="entity.id.codiceistat" size="15" readonly="true" /> <init:help
					idHelp="help1" textKey="help.codice" /></td>
			</c:if>
		</tr>
       <!-- Controllo che il codicesectore non sia null o stringa vuota;se lo è, sto inserendo un nuova attività dalla funzionalità base, quindi
       devo visualizzare il campo di insermimento del settore che è un campo obligatorio-->
		<c:if test="${codicesectore  eq '' || codicesectore == null}">
			<tr>
				<td><fmt:message key="attivita.label.settori" /></td>

				<td><spring-form:input id="settori_id" path="entity.settori.settore"
					cssClass="searchbox" size="67"
					onchange="checkValue(this,'settori_hidden')"
					onkeydown="javascript:return searchAll(this,event)" /> <init:autocompleter
					methodAjax="findSettori.htm" idHidden="settori_hidden"
					idInput="settori_id" inputTitleKey="label.ricerca_settori"></init:autocompleter>
				<spring-form:errors path="entity.settori" cssClass="error" /> <spring-form:hidden
					id="settori_hidden" path="entity.settori.id.codicesettore" /></td>

			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="attivita.label.istat" /></td>
			<td><spring-form:input id="istat_id" path="entity.istat" size="70" />
			<spring-form:errors path="entity.istat" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td><spring-form:textarea id="note_id" path="entity.note" cols="70"
				rows="15" /></td>
			<td><spring-form:errors path="entity.note" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.disabilitato" /></td>
			<td><spring-form:select id="flagDisabilitato_id"
				path="entity.flagDisabilitato">
				<spring-form:option value="false" label="no" />
				<spring-form:option value="true" label="si" />
			</spring-form:select> <spring-form:errors path="entity.flagDisabilitato" cssClass="error" /></td>
		</tr>

	</table>
	<script type='text/javascript'>
	$('codiceistat_id').focus();
</script>
</spring-form:form></div>
<div id="functions">
<ul>
	<c:if test="${attivita.displayMode==attivita.displayConstants.NEW}">
		<li><a
			href="javascript:doSubmit('insert.htm?codicesectore=${codicesectore}','',document.inviodati)"><fmt:message
			key="button.insert" /></a></li>
	</c:if>
	<c:if test="${attivita.displayMode==attivita.displayConstants.VIEW}">
		<li><a
			href="javascript:doSubmit('update.htm?codicesectore=${codicesectore}','',document.inviodati)"><fmt:message
			key="button.update" /></a></li>
		<li><a
			href="javascript:doSubmit('delete.htm?codicesectore=${codicesectore}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
			key="button.delete" /></a></li>
	</c:if>
	
    <li><a
		href="javascript:doHref('list.htm?codicesectore=${codicesectore}','')"><fmt:message
		key="button.back" /></a></li>
   </ul>
</div>
</body>
</html>