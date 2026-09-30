<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="documentidafirmare.label.titolo_pagina" />
	</title>
</head>
<body>
	<br class="clear" />
	<span class="titoloPagina">
		<fmt:message key="documentidafirmare.label.titolo_pagina" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
	<c:set var="richiestaEffettuata" value="<%=StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name()%>" />
	<c:set var="firmaNegata" value="<%=StatiDocumentiDaFirmare.FIRMA_NEGATA.name()%>" />
	<c:set var="firmaCompleta" value="<%=StatiDocumentiDaFirmare.FIRMA_COMPLETA.name()%>" />
	<spring-form:form commandName="documentidafirmare" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="documentidafirmare" />
	    </jsp:include>
		<br class="clear" />
		<div class="titoloSezione">
			<span><fmt:message key="documentidafirmare.label.lista_documenti_selezionati" /></span>
		</div>
		<table cellpadding="0" border="0" width="100%">
		<tr>
			<td colspan="2">
				<div class="jmesa">
					<table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="20%"><fmt:message key="documentidafirmare.label.descrizione_file" /></td>
								<td><fmt:message key="documentidafirmare.label.nome_file" /> </td>
								<td width="5%"><fmt:message key="documentidafirmare.label.responsabili_stato_firma" /> </td>
								<td width="5%"><fmt:message key="documentidafirmare.label.oggetto_presente" /> </td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>
						<c:forEach items="${listMovAllegati}" var="movall_var">
							<tr class="<%=(l%2)==0?"odd":"even"%>">
								<td>${movall_var.movimentiallegati.descrizione}</td>
								<td>${movall_var.movimentiallegati.oggetto.nomefile}</td>
								<td>
									<div class="jmesa">
								   		<table width="100%">
								    	<thead>
											<tr class="header">
												<td width="20%"><fmt:message key="label.firmatario" /></td>
												<td width="20%"><fmt:message key="label.messo_alla_firma_da" /></td>
												<td width="8%"><fmt:message key="label.data_messo_alla_firma" /></td>
												<td width="8%"><fmt:message key="label.stato" /></td>
												<td width="8%"><fmt:message key="label.data_firma" /></td>
								            </tr>
										</thead>
										<tbody>
										<%int j=1;%>
										<c:if test="${not empty movall_var.documentiDaFirmares}">
										<c:forEach items="${movall_var.documentiDaFirmares}" var="docDaFirmare">
											<tr class="<%=(j%2)==0?"odd":"even"%>">
												<td>${docDaFirmare.firmatario.responsabile}</td>
												<td>${docDaFirmare.richiedente.responsabile}</td>
												<td><fmt:formatDate value="${docDaFirmare.dataRichiesta}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </td>
												<td>${docDaFirmare.flagDaFirmare}</td>
												<td><fmt:formatDate value="${docDaFirmare.dataFirma}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </td>
											</tr>
										<%j++; %>
										</c:forEach>
										</c:if>
										<c:if test="${ empty movall_var.documentiDaFirmares}">
											<td colspan="4"><fmt:message key="documentidafirmare.non_messo_alla_firma" /> </td>
										</c:if>
										</tbody>
										</table>
									</div>
								</td>
								<td>
									<c:if test="${movall_var.oggettoPresente}"><img src="${pageContext.request.contextPath}/images/accept.png"></img></c:if>
									<c:if test="${!movall_var.oggettoPresente}"><img src="${pageContext.request.contextPath}/images/cross.gif"></img>
									(<fmt:message key="documentidafirmare.label.help.oggetto_presente" />)
									</c:if>
								</td>                   
				    		 </tr>
							<%l++; %>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr class="titoloSezione">
			<td colspan="3"><fmt:message key="documentidafirmare.label.sezione_firmatario" /></td>
		</tr>
		<tr>
		    <td><fmt:message key="documentidafirmare.label.responsabile_firmatario" />*</td>
		    <td>
			<jsp:include page="../includes/responsabilesearch.jsp" >
				<jsp:param name="idElemento" value="resp_firmatario" />
				<jsp:param name="propertyPath" value="firmatario" />				
				<jsp:param name="pathPropertyDescription" value="firmatario.responsabile" />
				<jsp:param name="pathPropertyCode" value="firmatario.id.codice" />
				<jsp:param name="tipologiaResponsabilie" value="P" />	
				<jsp:param name="id_help" value="label.ricerca_responsabile_no_tipologia" />
			</jsp:include>
			<init:help idHelp="help_resp" textKey="label.ricerca_responsabile_no_tipologia"/>	
			</td>
			<spring-form:errors path="firmatario"></spring-form:errors>	
		</tr>
		<tr>
			<td><fmt:message key="documentidafirmare.label.note_op" /></td>
			<td><spring-form:textarea id="note_id" path="annotazioniRichiedente" cols="67" rows="5"/></td>
		</tr>
	</table>
	<br/>
	</spring-form:form>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('updateMettiAllaFirmaMultipli.htm?codiciMovAllegato=${codiciMovAllegato}','',document.inviodati)"><fmt:message key="documentidafirmare.button.metti_alla_firma" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
</body>
</html>