<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${bandiallegati.id.codice==null}">
			<fmt:message key="form.bandiallegati.title.create" />
		</c:if> 
		<c:if test="${bandiallegati.id.codice!=null}">
			<fmt:message key="form.bandiallegati.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${bandiallegati.id.codice==null}">
	<fmt:message key="form.bandiallegati.title.create" />
</c:if> 
<c:if test="${bandiallegati.id.codice!=null}">
	<fmt:message key="form.bandiallegati.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<span class="parametri"><fmt:message key="form.bandiallegati.title.bando" />: <label><c:out value="${bando.descrizione}"/></label></span>
	<br />
    <spring-form:form commandName="bandiallegati" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="bandiallegati" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.bandiallegati.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.bandiallegati.dataInserimento" /></td>
			<td>
				<spring-form:input  tabindex="2" id="dataInserimento_id" path="dataInserimento" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   		<init:calendar idImage="calDataInserimento" idInput="dataInserimento_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   		<spring-form:errors	path="dataInserimento" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.bandiallegati.oggetto" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${bandiallegati.oggetto.id.codice}" />
	   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
	   				<jsp:param name="nomefileId" value="oggetto_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice"/>
    			<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile"/>
    			<spring-form:errors path="oggetto" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.bandiallegati.note" /></td>
			<td><spring-form:textarea id="note_id" path="note" cols="60" rows="4"/>
			<spring-form:errors path="note" cssClass="error"/></td>
		</tr>
		
	</table>
	
	<script type='text/javascript'>
		$('descrizione_id').focus();
		
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${bandiallegati.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${bandiallegati.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?codice=${bandiallegati.bandi.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
