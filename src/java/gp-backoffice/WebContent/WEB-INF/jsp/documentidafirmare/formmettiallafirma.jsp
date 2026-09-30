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
	<c:set var="richiestaEffettuata" value="<%=StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name()%>" />
	<c:set var="firmaNegata" value="<%=StatiDocumentiDaFirmare.FIRMA_NEGATA.name()%>" />
	<c:set var="firmaCompleta" value="<%=StatiDocumentiDaFirmare.FIRMA_COMPLETA.name()%>" />
	<spring-form:form commandName="documentidafirmare" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="documentidafirmare" />
	    </jsp:include>
		<br class="clear" />
		<table cellpadding="5">
			<tr>
				<td ><fmt:message key="documentidafirmare.label.nome_file" /></td>
				<td >${documentidafirmare.oggetti.nomefile}</td>
			</tr>
			<tr>
				<td ><fmt:message key="documentidafirmare.label.descrizione_file" /></td>
				<td  >${documentidafirmare.movimentiallegati.descrizione}</td>
			</tr>
			<c:if test="${not empty documentidafirmare.richiedente.id.codice }">
				<tr>
					<td><fmt:message key="documentidafirmare.label.op_richiedente" /></td>
					<td>${documentidafirmare.richiedente.responsabile}</td>
				</tr>
				<tr>
					<td><fmt:message key="documentidafirmare.label.data_richiesta" /></td>
					<td><fmt:formatDate value="${documentidafirmare.dataRichiesta}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				</tr>
				<tr>		
					<td><fmt:message key="documentidafirmare.label.stato_richiesta" /></td>
					<td>
					<c:if test="${documentidafirmare.flagDaFirmare eq richiestaEffettuata}">
						<fmt:message key="documentidafirmare.label.richiesta_inoltrata" />
					</c:if>	
					<c:if test="${documentidafirmare.flagDaFirmare eq firmaNegata}">
						<fmt:message key="documentidafirmare.label.documento_non_firmato" /> <fmt:formatDate value="${documentidafirmare.dataFirma}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
					</c:if>
					<c:if test="${documentidafirmare.flagDaFirmare eq firmaCompleta}">
						<fmt:message key="documentidafirmare.label.documento_firmato" /> <fmt:formatDate value="${documentidafirmare.dataFirma}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
					</c:if>											
					</td>
				</tr>
			</c:if>
			<tr>
				<td><fmt:message key="documentidafirmare.label.responsabile_firmatario" /></td>
				<td>
		<c:choose>
			<c:when test="${not empty documentidafirmare.id.codice and not (documentidafirmare.flagDaFirmare eq richiestaEffettuata)}">
				${documentidafirmare.firmatario.responsabile}
			</c:when>
			<c:otherwise>
				<jsp:include page="../includes/responsabilesearch.jsp" >
					<jsp:param name="idElemento" value="resp_firmatario" />
					<jsp:param name="propertyPath" value="firmatario" />				
					<jsp:param name="pathPropertyDescription" value="firmatario.responsabile" />
					<jsp:param name="pathPropertyCode" value="firmatario.id.codice" />
					<jsp:param name="tipologiaResponsabilie" value="P" />	
					<jsp:param name="titleKey" value="label.ricerca_responsabile_no_tipologia" />
					<jsp:param name="id_help" value="help_responsabili" />
				</jsp:include>
				<init:help idHelp="help_resp" textKey="label.ricerca_responsabile_no_tipologia"/>			
			</c:otherwise>
		</c:choose>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="documentidafirmare.label.note_op" /></td>
				<td>
					<c:choose>
						<c:when test="${not empty documentidafirmare.id.codice and not (documentidafirmare.flagDaFirmare eq richiestaEffettuata)}">
							${documentidafirmare.annotazioniRichiedente}
						</c:when>
						<c:otherwise>
							<spring-form:textarea id="note_id" path="annotazioniRichiedente" cols="67" rows="5"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<c:if test="${not empty documentidafirmare.id.codice and not (documentidafirmare.flagDaFirmare eq richiestaEffettuata)}">
				<tr>
					<td><fmt:message key="documentidafirmare.label.note_resp" /></td>
					<td>						
						${documentidafirmare.annotazioniFirmatario}
					</td>
				</tr>
			</c:if>
		</table>
	<br/>
	</spring-form:form>
	
	<div id="functions">
		<ul>
			
				
				<c:choose>
					<c:when test="${fromDocAut eq true}"><%-- ho aperto il popup da lista documenti da firmare aperta dalla pagina documenti autorizzazione --%>
						<c:if test="${stessoFirmatarioRichiedente eq true }">
							<%-- in questo caso quando aggiorno il documento, updateMettiAllafirma non mi rimanda a list.jsp di documenti da firmare --%>
							<li><a href="javascript:doSubmit('updateMettiAllaFirma.htm?from=1','',document.inviodati)"><fmt:message key="button.save" /></a></li>				
							<%-- <li><a href="javascript:doSubmit('annullaMettiAllaFirma.htm?from=1','',document.inviodati)"><fmt:message key="documentidafirmare.button.annulla_richiesta" /></a></li> --%>
							<%-- Cliccando sul bottone ANNULLA RICHIESTA viene chiamato il metodo AJAX annullaRichiesta che chiude il popup e ricarica la pagina che lo ha aperto --%>
							<li><a href="javascript:annullaRichiesta()"><fmt:message key="documentidafirmare.button.annulla_richiesta" /></a></li>
						</c:if>
						<li><a id="close_button_id" href="javascript:self.close();"><fmt:message key="button.back" /></a></li>
					</c:when>
					<c:otherwise>						
						<c:if test="${empty documentidafirmare.id.codice}">
								<c:set var="msg_pdf" value="" scope="page"/>
							<c:if test="${convertibileInPdf eq true}">
								<c:set var="msg_pdf" scope="page" value="Il file verrà convertito in pdf per impostazione di sistema. Continuare?"/>
							</c:if>
							<li><a href="javascript:doSubmit('updateMettiAllaFirma.htm','${msg_pdf}',document.inviodati)"><fmt:message key="documentidafirmare.button.metti_alla_firma" /></a></li>
						</c:if>
						<c:if test="${not empty documentidafirmare.id.codice}">
							<c:if test="${stessoFirmatarioRichiedente eq true }">
								<li><a href="javascript:doSubmit('updateMettiAllaFirma.htm','',document.inviodati)"><fmt:message key="button.save" /></a></li>				
								<li><a href="javascript:doSubmit('annullaMettiAllaFirma.htm','',document.inviodati)"><fmt:message key="documentidafirmare.button.annulla_richiesta" /></a></li>
							</c:if>
						</c:if>
						<c:if test="${empty documentidafirmare.id.codice}">
							<li><a href="javascript:self.close();"><fmt:message key="button.back" /></a></li>
						</c:if>
						<c:if test="${not empty documentidafirmare.id.codice}">
							<li><a href="javascript:doSubmit('popupViewDocumentoDaFirmare.htm?codiceMovAllegato=${documentidafirmare.movimentiallegati.id.codice}','',document.inviodati)"><fmt:message key="button.back" /></a></li>
						</c:if>
					</c:otherwise>
				</c:choose>
		</ul>
	</div>
	
	<script type="text/javascript">
	
		jQuery('#close_button_id').click(function(e) {
		    window.opener.location.reload(true);
		    window.close();
		    e.preventDefault();
		});
		
		function annullaRichiesta(){
			var jhqrPr = jQuery.ajax({
				url: '${pageContext.request.contextPath}/documentidafirmare/ajaxAnnullaMettiAllaFirma.htm',
				context: document.body,
				cache: false,
				dataType: "html",
				success: function(data, textStatus, jqXHR){
					if (data == 'OK'){
						window.opener.location.reload(true);
					    window.close();
					    e.preventDefault();
					} 
				}
			});
		}
		
	</script>
	
</body>
</html>