<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="documentidafirmare.label.title_icona" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="documentidafirmare.label.title_icona" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>

	<spring-form:form commandName="documentidafirmare" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="documentidafirmare" />
	    </jsp:include>
		    
	    <c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${documentidafirmare.istanze.id.codice}</c:param>
		</c:import>
		<br class="clear" />
			<table cellpadding="5">
				<tr><td><fmt:message key="documentidafirmare.label.movimento" /></td><td>${documentidafirmare.movimentiallegati.movimento.movimento} - [${documentidafirmare.movimentiallegati.movimento.tipomovimento.id.tipomovimento}]</td></tr>
				<tr><td><fmt:message key="documentidafirmare.label.operatore" /></td><td>${documentidafirmare.richiedente.responsabile}</td></tr>
				<tr><td><fmt:message key="documentidafirmare.label.data_richiesta" /></td><td>${documentidafirmare.dataRichiesta}</td></tr>
				<tr><td><fmt:message key="documentidafirmare.label.note_op" /></td><td>${documentidafirmare.annotazioniRichiedente}</td></tr>
				<tr><td><fmt:message key="documentidafirmare.label.descrizione_file" /></td><td>${documentidafirmare.movimentiallegati.descrizione}</td></tr>
				<tr>
					<td>
						<fmt:message key="label.documento_da_allegare" />
					</td>
					<td>
						<jsp:include page="../includes/oggetti.jsp" >
		       				<jsp:param name="idElemento" value="mall${documentidafirmare.id.codice}" />
		   					<jsp:param name="codiceOggetto" value="${documentidafirmare.oggetti.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
		   					<jsp:param name="nomefileId" value="oggetto_nomefile" />		   					
		   					<jsp:param name="showFirma" value="${showFirmaModel}" />
		   					<jsp:param name="pageFirmaCallbackFunction" value="aggiorna()" />					
		   				</jsp:include>	

					</td>
				</tr>
				<tr>
					<td><fmt:message key="documentidafirmare.label.stato_richiesta" /></td>
					<td>
						<spring-form:select id="flagDaFirmare_id" path="flagDaFirmare">
							<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
						    <spring-form:option value="FIRMA_COMPLETA"><fmt:message key="label.firma_completa"/></spring-form:option>						
							<spring-form:option value="FIRMA_NEGATA"><fmt:message key="label.firma_negata"/></spring-form:option>
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="documentidafirmare.label.note_resp" /></td>
					<td>						
						<spring-form:textarea id="note_id" path="annotazioniFirmatario" cols="67" rows="5"/>
						<spring-form:errors path="annotazioniFirmatario" cssClass="error"/>
					</td>
				</tr>
				
			</table>
		</spring-form:form>
		<div id="functions">
			<ul>
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
				
</body>