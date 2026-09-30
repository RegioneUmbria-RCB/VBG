<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${inventarioprocedimenti.testiestesi.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_testoestesi.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.testiestesi.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_testoestesi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${inventarioprocedimenti.testiestesi.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_testoestesi.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.testiestesi.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_testoestesi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.normativa" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="normativa_id" path="testiestesi.normativa" size="60"/>
						<spring-form:errors path="testiestesi.normativa" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.normative" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="normative_id" path="testiestesi.normative.normativa" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'normative_hidden')" size="57"/>
						<init:autocompleter methodAjax="findNormative.htm" idHidden="normative_hidden" idInput="normative_id" inputTitleKey="label.ricerca_normativa"></init:autocompleter>
						<spring-form:errors path="testiestesi.normative" cssClass="error"/> 
						<spring-form:hidden id="normative_hidden" path="testiestesi.normative.id.codice"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.indirizzoweb" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="indirizzoweb_id" path="testiestesi.indirizzoweb" size="60"/>
						<spring-form:errors path="testiestesi.indirizzoweb" cssClass="error"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<fmt:message key="label.documento" />
					</td>
					<td class="inline-ui-cell">
						<jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice" />
						<jsp:param name="codiceOggetto" value="${inventarioprocedimenti.testiestesi.oggetti.id.codice}" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
						<jsp:param name="nomefileId" value="oggetto_nomefile" />
						<jsp:param name="overrideExtensionsAllowed" value="pdf|rtf|doc|odt"/>
						</jsp:include> 
						<spring-form:hidden path="testiestesi.oggetti.id.codice" id="oggetto_id_codice" />
						<spring-form:hidden path="testiestesi.oggetti.nomefile" id="oggetto_nomefile" />
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.testiestesi.id.codice==null}">
				<li><a href="javascript:doSubmit('insertTestiestesi.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.testiestesi.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateTestiestesi.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteTestiestesi.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listtestiestesi.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>