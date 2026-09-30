<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovStcAltridati.id.codice==null}">
			<fmt:message key="form.tipimovstcaltridati.title.create" />
		</c:if> 
		<c:if test="${tipimovStcAltridati.id.codice!=null}">
			<fmt:message key="form.tipimovstcaltridati.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipimovStcAltridati.id.codice==null}">
	<fmt:message key="form.tipimovstcaltridati.title.create" />
</c:if> 
<c:if test="${tipimovStcAltridati.id.codice!=null}">
	<fmt:message key="form.tipimovstcaltridati.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipimovStcAltridati" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipimovStcAltridati" />
    </jsp:include>
    <br></br>
    
    <span class="parametri">
        <fmt:message key="form.tipimovstcmapping.tipimovimento" /> : <label><c:out value="${tipimovStcMapping.tipimovimento.movimento }"/></label>
    </span>
	<table>
		<tr>
			<td><fmt:message key="form.tipimovstcaltridati.amministrazioni" /></td>
			<td><spring-form:select id="amministrazioni_id" path="amministrazioni.id.codice" items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
			<spring-form:errors path="amministrazioni" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcaltridati.nomeCampo" /></td>
			<td><spring-form:input id="nomeCampo_id" path="nomeCampo" size="50"/>
			<init:help idHelp="help_nomeCampo" textKey="form.tipimovstcaltridati.nomeCampo.help"/>
			<spring-form:errors path="nomeCampo" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcaltridati.valoreDefaultCampo" /></td>
			<td><spring-form:input id="valoreDefaultCampo_id" path="valoreDefaultCampo" size="50"/>
			<init:help idHelp="help_valoreDefaultCampo" textKey="form.tipimovstcaltridati.valoreDefaultCampo.help"/>
			<spring-form:errors path="valoreDefaultCampo" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcaltridati.etichetta" /></td>
			<td><spring-form:input id="etichetta_id" path="etichetta" size="50" />
			<init:help idHelp="help_etichetta" textKey="form.tipimovstcaltridati.etichetta.help"/>
			<spring-form:errors path="etichetta" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcaltridati.helpText" /></td>
			<td><spring-form:textarea id="helpText_id" path="helpText" rows="3" cols="70"/>
			<spring-form:errors path="helpText" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcaltridati.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="2" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.obbligatorio" /></td>
			<td><spring-form:checkbox id="obbligatorio_id" path="obbligatorio"/>
			<spring-form:errors path="obbligatorio" cssClass="error"/></td>
		</tr>
	</table>
	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipimovStcAltridati.id.codice==null}">
		<li><a href="javascript:doSubmit('insertAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipimovStcAltridati.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:void 0;" onclick="historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
