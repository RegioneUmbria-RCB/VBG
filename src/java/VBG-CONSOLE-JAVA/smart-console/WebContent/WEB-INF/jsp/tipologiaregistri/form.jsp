<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${tipologiaregistri.id.codice==null}">
			<fmt:message key="tipologiaregistri.label.nuovo_tipologiaregistri.title" />
		</c:if> 
		<c:if test="${tipologiaregistri.id.codice!=null}">
			<fmt:message key="tipologiaregistri.label.dettaglio_tipologiaregistri.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipologiaregistri.id.codice==null}">
	<fmt:message key="tipologiaregistri.label.nuovo_tipologiaregistri.title" />
</c:if> 
<c:if test="${tipologiaregistri.id.codice!=null}">
	<fmt:message key="tipologiaregistri.label.dettaglio_tipologiaregistri.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
 <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipologiaregistri/view" />	
 </jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipologiaregistri" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipologiaregistri" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="tipologiaregistri.label.trdescrizione" /></td>
			<td><spring-form:input id="trDescrizione_id" path="trDescrizione" size="70" />

			<spring-form:errors path="trDescrizione" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="tipologiaregistri.label.trFlagprotocollo" /></td>
			<td><spring-form:checkbox id="trFlagprotocollo_id" path="trFlagprotocollo"/>
			<init:help idHelp="help1" textKey="tipologiaregistri.help.trFlagprotocollo"/>
			<spring-form:errors path="trFlagprotocollo" cssClass="error"/></td>
		</tr>
        <c:if test="${tipologiaregistri.trFlagprotocollo==true}">
        <tr>
			<td><fmt:message key="tipologiaregistri.label.trProgressivo" /></td>
			<td><spring-form:input id="trProgressivo_id" path="trProgressivo" size="15" readonly="true" />
			<spring-form:errors path="trProgressivo" cssClass="error"/></td>
		</tr>
        </c:if>
        <c:if test="${tipologiaregistri.trFlagprotocollo==false  || tipologiaregistri.trFlagprotocollo==null}">
        <tr>
			<td><fmt:message key="tipologiaregistri.label.trProgressivo" /></td>
			<td><spring-form:input id="trProgressivo_id" path="trProgressivo" size="15"  />
			<spring-form:errors path="trProgressivo" cssClass="error"/></td>
		</tr>
        </c:if>
        <tr>
			<td><fmt:message key="tipologiaregistri.label.trFlagdataauto" /></td>
			<td><spring-form:checkbox id="trFlagdataauto_id" path="trFlagdataauto"/>
			<init:help idHelp="help2" textKey="tipologiaregistri.help.trFlagdataauto"/>
			<spring-form:errors path="trFlagdataauto" cssClass="error"/></td>
		</tr>
		<c:if test="${isDocEr eq true}">
		<tr>
			<td><fmt:message key="label.cod_docer" /></td>
			<td><spring-form:input id="codRegistroDocer_id" path="codRegistroDocer" size="50" />

			<spring-form:errors path="codRegistroDocer" cssClass="error"/></td>
		</tr>
		</c:if>
	</table>
	
	<script type='text/javascript'>
		$('trDescrizione_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipologiaregistri.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipologiaregistri.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	    <c:if test="${tipologiaregistri.trFlagprotocollo}">
	         <li><a href="javascript:historySet('${_urlback }','../tipologiaregistri/listProtocolloRegistri.htm?codiceRegistro=${tipologiaregistri.id.codice}','')"><fmt:message key="tipologiaregistri.button.parametri_protocollo" /></a></li>
	    </c:if>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
