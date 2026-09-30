<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>
	<c:if test="${legge.id.codice==null}">
		<fmt:message key="leggi.label.nuova_legge.title" />
	</c:if> 
	<c:if test="${legge.id.codice!=null}">
		<fmt:message key="leggi.label.dettaglio_legge.title" />
	</c:if>
</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${legge.id.codice==null}">
	<fmt:message key="leggi.label.nuova_legge.title" />
</c:if> 
<c:if test="${legge.id.codice!=null}">
	<fmt:message key="leggi.label.dettaglio_legge.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="legge" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="legge" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="le" path="leDescrizione" size="100" tabindex="0" />
			<spring-form:errors path="leDescrizione" cssClass="error"/></td>
		</tr>
		
		<tr>
			<td>
				<fmt:message key="label.normativa" />
			</td>
			<td>
			<jsp:include page="../includes/autocompletergenerico.jsp">
				<jsp:param name="idElemento" value="attivita" />			
				<jsp:param name="propertyPath" value="normative" />			
				<jsp:param name="pathPropertyDescription" value="normative.normativa" />
				<jsp:param name="pathPropertyCode" value="normative.id.codice" />
				<jsp:param name="autocompleterAjax" value="findNormative.htm" />
				<jsp:param name="titleKey" value="label.ricerca_normative" />
				<jsp:param  name="autocompleterInputSize" value="40"/>
			</jsp:include>		
			</td>		
		</tr>
		
		<tr>
			<td>
				<fmt:message key="leggi.label.tipolegge" />
			</td>
			<td>
				<spring-form:input id="lt" path="leggitipi.ltDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
				<init:autocompleter methodAjax="findLeggitipi.htm" idHidden="h1" idInput="lt" inputTitleKey="label.ricerca_legge_tipo"/>
				<spring-form:errors path="leggitipi" cssClass="error"/> 
				<spring-form:hidden id="h1" path="leggitipi.id.codice"  />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="leggi.label.allegato" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${legge.oggetto.id.codice}" />
	   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
	   				<jsp:param name="nomefileId" value="oggetto_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice"/>
    			<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile"/>
    			<spring-form:errors path="oggetto" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="leggi.label.indirizzoweb" /></td>
			<td><spring-form:input id="leLink" path="leLink" size="70" tabindex="0" />
			<spring-form:errors path="leLink" cssClass="error"/></td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('le').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${legge.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${legge.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>