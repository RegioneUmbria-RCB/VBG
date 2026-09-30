<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatistradario.id.codice==null}">
			<fmt:message key="manifestazione.label.nuova_localizzazione.title" />
		</c:if> 
		<c:if test="${mercatistradario.id.codice!=null}">
			<fmt:message key="manifestazione.label.dettaglio_localizzazione.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${mercatistradario.id.codice==null}">
			<fmt:message key="manifestazione.label.nuova_localizzazione.title" />
		</c:if> 
		<c:if test="${mercatistradario.id.codice!=null}">
			<fmt:message key="manifestazione.label.dettaglio_localizzazione.title" />
		</c:if>
	</span>
	
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
			</div>
		<div class="parametro">
			<div><c:out value="${mercati.descrizione}" /></div>
		</div>
	</div>
	<br class="clear" />
	<spring-form:form commandName="mercatistradario" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
		      <jsp:param name="commandName" value="mercatistradario" />
		</jsp:include>
	<br class="clear"  />
			
	<table>
	    <tr>
			<td>
				<fmt:message key="label.indirizzo" />
			</td>
			<td>
				<spring-form:input id="stradario_id" path="stradario.descrizioneCompleta" cssClass="searchbox" onchange="checkValue(this,'descrizione_hidden')" onkeydown="javascript:return searchAll(this,event) " size="60"/>
				<init:autocompleter methodAjax="findStradario.htm?searchDisabilitati=false" idHidden="stradario_hidden" idInput="stradario_id" inputTitleKey="label.ricerca_stradario"></init:autocompleter>
				<spring-form:errors path="stradario" cssClass="error"/> 
				<spring-form:hidden id="stradario_hidden" path="stradario.id.codice"  />
			</td>			
		</tr>
	    <tr>
			<td>
				<fmt:message key="manifestazione.label.coefficiente_viario" />
			</td>
			<td>
				<spring-form:input id="coefficienteViario_id" path="coefficienteViario"  cssStyle="text-align:right;" size="10" onchange="checkNumberValue(this)" />
				<spring-form:errors path="coefficienteViario" cssClass="error"/>
			</td>
		</tr>
		<c:if test="${mercatistradario.id.codice!=null}">
		<tr>
			<td>
				<fmt:message key="label.cap" />
			</td>
			<td>
				<spring-form:input id="cap_id" path="stradario.cap" size="8" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.localita_frazione" />
			</td>
			<td>
				<spring-form:input id="locfraz_id" path="stradario.locfraz" size="64" readonly="true" />
				
			</td>
		</tr>
		</c:if>
	</table>
	
	</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${mercatistradario.id.codice==null}">
				<li><a href="javascript:doSubmit('insertMercatistradario.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercatistradario.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateMercatistradario.htm?codicemercato=${mercati.id.codice}&codicemercatistradario=${mercatistradario.id.codice}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteMercatistradario.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listmercatistradario.htm?codicemercato=${mercati.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>