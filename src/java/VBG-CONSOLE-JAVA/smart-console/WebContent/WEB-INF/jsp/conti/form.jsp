<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${conti.id.codice==null}">
			<fmt:message key="form.conti.title.create" />
		</c:if> 
		<c:if test="${conti.id.codice!=null}">
			<fmt:message key="form.conti.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${conti.id.codice==null}">
	<fmt:message key="form.conti.title.create" />
</c:if> 
<c:if test="${conti.id.codice!=null}">
	<fmt:message key="form.conti.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="conti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="conti" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.conti.codiceconto" /></td>
			<td><spring-form:input id="codiceconto_id" path="codiceconto" size="70" />
			<spring-form:errors path="codiceconto" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.conti.codicesottoconto" /></td>
			<td><spring-form:input id="codicesottoconto_id" path="codicesottoconto" size="70" />
			<spring-form:errors path="codicesottoconto" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.conti.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.conti.note" /></td>
			<td><spring-form:textarea id="note_id" path="note" cols="60" rows="4"/>
			<spring-form:errors path="note" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.conti.iva" /></td>
			<td><spring-form:input cssStyle="text-align: right;" id="iva_id" path="iva" size="5" maxlength="5" />
				<init:help idHelp="help1" textKey="form.conti.iva.help"/>
				<spring-form:errors path="iva" cssClass="error"/>			
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.conti.amministrazioni" />
			</td>
			<td>
				<spring-form:input id="amministrazioni_ID" path="amministrazioni.amministrazione" cssClass="searchbox" onchange="checkValue(this,'amministrazioni_HIDDEN')" size="50"  onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=false" idHidden="amministrazioni_HIDDEN" idInput="amministrazioni_ID" inputTitleKey="label.ricerca_amministrazione"/>
				<spring-form:errors path="amministrazioni.amministrazione" cssClass="error"/>  
				<spring-form:hidden id="amministrazioni_HIDDEN" path="amministrazioni.id.codice"  />
			</td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('codiceconto_id').focus();	
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${conti.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${conti.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
