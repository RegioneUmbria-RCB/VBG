<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>	
			<fmt:message key="label.loghi_frontoffice" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.loghi_frontoffice" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
		<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../configurazione/create" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="configurazione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="configurazione" />
		    </jsp:include>
			<table >
			<tr>
				<td width="20%">
					<fmt:message key="label.scritta_regione" />
				</td>
				<td>
					<spring-form:input id="scrittaregione_id" path="configurazione.scrittaregione" size="70" />
					<spring-form:errors path="configurazione.scrittaregione" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<fmt:message key="label.help.inserimento_loghi" />
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="label.logo_regione" /></td>
				<td>
					<jsp:include page="../includes/oggetti.jsp" >
		       			<jsp:param name="idElemento" value="oggettoIdCodice_regione" />
		   				<jsp:param name="codiceOggetto" value="${configurazione.configurazione.oggettoLogoregione.id.codice}" />
		   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice_regione" />
		   				<jsp:param name="nomefileId" value="codice_regione_nomefile" />
	    			</jsp:include>
	    			<spring-form:hidden path="configurazione.oggettoLogoregione.id.codice" id="oggetto_id_codice_regione"/>
	    			<spring-form:hidden path="configurazione.oggettoLogoregione.nomefile" id="codice_regione_nomefile"/>
	    			<spring-form:errors path="configurazione.oggettoLogoregione" cssClass="error"/>
				</td>
			</tr>
		    <tr>
				<td><fmt:message key="label.logo_comune" /></td>
				<td>
					<jsp:include page="../includes/oggetti.jsp" >
		       			<jsp:param name="idElemento" value="oggettoIdCodice_comune" />
		   				<jsp:param name="codiceOggetto" value="${configurazione.configurazione.oggettoLogocomune.id.codice}" />
		   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice_comune" />
		   				<jsp:param name="nomefileId" value="oggetto_id_nomefile" />
	    			</jsp:include>
	    			<spring-form:hidden path="configurazione.oggettoLogocomune.id.codice" id="oggetto_id_codice_comune"/>
	    			<spring-form:hidden path="configurazione.oggettoLogocomune.nomefile" id="oggetto_id_nomefile"/>
	    			<spring-form:errors path="configurazione.oggettoLogocomune" cssClass="error"/>
				</td>
			</tr>
			
			</table>
			<script type='text/javascript'>
				$('scrittaregione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${configurazione.displayMode == configurazione.displayConstants.NEW}">	
				<li><a href="javascript:doSubmit('insertOrUpdateLoghi.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${configurazione.displayMode == configurazione.displayConstants.VIEW}">	
				<li><a href="javascript:doSubmit('insertOrUpdateLoghi.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
				<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>