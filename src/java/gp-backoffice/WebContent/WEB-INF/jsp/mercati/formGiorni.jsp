<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatiuso.id.codice==null}">
			<fmt:message key="manifestazione.label.nuovo_mercatiuso.title" />
		</c:if> 
		<c:if test="${mercatiuso.id.codice!=null}">
			<fmt:message key="manifestazione.label.dettaglio_mercatiuso.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		<c:if test="${mercatiuso.id.codice==null}">
			<fmt:message key="manifestazione.label.nuovo_mercatiuso.title" />
		</c:if> 
		<c:if test="${mercatiuso.id.codice!=null}">
			<fmt:message key="manifestazione.label.dettaglio_mercatiuso.title" />
		</c:if> 
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercati/viewGiorni" />
	</jsp:include>
	<div id="subcontent">
	    <jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercati.descrizione}" />											
		</jsp:include>
	<br class="clear" />
	<div>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="mercatiuso" />
	</jsp:include>
  	</div>
	<spring-form:form commandName="mercatiuso" name="inviodati">
	<table>
		<tr>
			<td><fmt:message key="manifestazione.label.descrizione_mercato_uso" />
			</td>
			<td><spring-form:input id="descrizione_id" path="descrizione"
				size="70" /> <init:help idHelp="help1"
				textKey="manifestazione.help.descrizione_mercatouso" /> <spring-form:errors
				path="descrizione" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.giorno_settimana" /></td>

			<td><spring-form:select path="giornisettimana.id"
				items="${listagiorni}" itemLabel="gsDescrizione" itemValue="id"></spring-form:select>
			<spring-form:errors path="giornisettimana" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.concessione_uso"/></td>
			<td><spring-form:input id="concessioniuso_id"
				path="concessioniuso.descrizione" cssClass="searchbox"
				onchange="checkValue(this,'concessioniuso_hidden')"
				onkeydown="javascript:return searchAll(this,event)" /> <init:autocompleter
				methodAjax="findconcessioniuso.htm" idHidden="concessioniuso_hidden"
				idInput="concessioniuso_id"
				inputTitleKey="label.ricerca_concessioniuso"></init:autocompleter> <spring-form:errors
				path="concessioniuso" cssClass="error" /> <spring-form:hidden
				id="concessioniuso_hidden" path="concessioniuso.id.codice" /></td>
		</tr>
		<tr>
			<td><fmt:message key="manifestazione.label.mercato_uso_peso_importanza" />
			</td>
			<td><spring-form:input id="peso_id" path="peso"  cssStyle="text-align:right;" size="6" /> 
			<init:help idHelp="help_peso" textKey="manifestazione.help.peso" />
				<spring-form:errors path="peso" cssClass="error" /></td>
		</tr>
	</table>

	<script type='text/javascript'>
			$('descrizione_id').focus();
	</script>
	</spring-form:form>
	</div>
	<div id="functions">
	<ul>
		<c:if test="${mercatiuso.id.codice==null}">
			<li><a
				href="javascript:doSubmit('insertGiorni.htm','',document.inviodati)"><fmt:message
				key="button.insert" /></a></li>
		</c:if>
		
		<c:if test="${mercatiuso.id.codice!=null}">
			<li><a
				href="javascript:doSubmit('updateGiorni.htm?codicemercato=${mercati.id.codice}&codicemercatouso=${mercatiuso.id.codice}','',document.inviodati)"><fmt:message
				key="button.update" /></a></li>
			<li><a
			href="javascript:historySet('${_urlback}','../mercatilivelloservizio/list.htm?codicemercato=${mercatiuso.mercati.id.codice}&codiceuso=${mercatiuso.id.codice}')"><fmt:message
			key="button.livelli_servizio" /></a></li>
			<li><a
			href="javascript:historySet('${_urlback}','..%2Fmercatiformulecalcolo/list.htm?codicemercato=${mercati.id.codice}&codiceuso=${mercatiuso.id.codice}','')"><fmt:message
			key="button.formule_posteggi" /></a></li>
			
			
			
			<li><a
				href="javascript:doSubmit('deleteGiorni.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
				key="button.delete" /></a></li>
		</c:if>
		<li><a
			href="javascript:doHref('listgiorni.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>