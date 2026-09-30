<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${alboPubblicazioniAllegati.id.codice==null}">
			<fmt:message key="albopubblicazioni.label.associa_nuovo_allegato" />
		</c:if> 
		<c:if test="${alboPubblicazioniAllegati.id.codice!=null}">
			<fmt:message key="albopubblicazioni.label.modifica_allegato_presente" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${alboPubblicazioniAllegati.id.codice==null}">
	<fmt:message key="albopubblicazioni.label.associa_nuovo_allegato" />
</c:if> 
<c:if test="${alboPubblicazioniAllegati.id.codice!=null}">
	<fmt:message key="albopubblicazioni.label.modifica_allegato_presente" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="alboPubblicazioniAllegati" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="alboPubblicazioniAllegati" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="albopubblicazioni.label.descrizione_allegato" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<!--<init:help idHelp="help1" textKey="form.alboPubblicazioniAllegati.descrizione.help"/>
			--><spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="albopubblicazioni.label.ordine_allegato" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="3" /><!--
			<init:help idHelp="help1" textKey="form.alboPubblicazioniAllegati.ordine.help"/>
			--><spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="albopubblicazioni.label.allegato" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${alboPubblicazioniAllegati.oggetti.id.codice}" />
	   				<jsp:param name="idComuneOggetto" value="${alboPubblicazioniAllegati.oggetti.id.idcomune}" />
	   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
	   				<jsp:param name="nomefileId" value="oggetto_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice"/>
    			<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile"/>
    			<spring-form:errors path="oggetti" cssClass="error"/>
			</td>
		</tr>
       
		
	</table>
	
	<script type='text/javascript'>
		$('descrizione_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${alboPubblicazioniAllegati.id.codice==null}">
		<li><a href="javascript:doSubmit('insertAllegato.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${alboPubblicazioniAllegati.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateAllegato.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doHref('deleteAlbopubblicazioneallegatoFromCodice.htm?codice=${alboPubblicazioniAllegati.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../albopubblicazioni/view.htm?codice=${alboPubblicazioniAllegati.alboPubblicazioni.id.codice }','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
