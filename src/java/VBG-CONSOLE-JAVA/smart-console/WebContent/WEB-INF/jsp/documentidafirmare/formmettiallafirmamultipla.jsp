<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	<c:set var="richiestaEffettuata" value="<%= WebConstants.FIRMA_RICHIESTA%>" />
	<c:set var="firmaNegata" value="<%= WebConstants.FIRMA_NEGATA%>" />
	<c:set var="firmaCompleta" value="<%= WebConstants.FIRMA_COMPLETA%>" />
	<spring-form:form commandName="documentidafirmare" name="inviodati">
	
	
	<div style="width: 80%;min-height: 50px; border: thin dotted; padding: 5px;">
		<fmt:message key="label.help_firma_multipla_documenti" />
	</div>
	
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="documentidafirmare" />
	    </jsp:include>
		<br class="clear" />
		
		<c:forEach items="${documentidafirmares}" var="doc">
			<input type="hidden" name="id_doc_da_firmare"  value="${doc.id.codice}"/>		
		</c:forEach>
		
			
			<table style="width: 80%">
				<tr>
					<td><fmt:message key="documentidafirmare.label.stato_richiesta" /> *</td>
					<td>
						<spring-form:select id="flagDaFirmare_id" path="flagDaFirmare" onchange="cambiaValori()">
							<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
						    <spring-form:option value="<%=WebConstants.FIRMA_COMPLETA %>"><fmt:message key="label.firma_completa"/></spring-form:option>						
							<spring-form:option value="<%=WebConstants.FIRMA_NEGATA %>"><fmt:message key="label.firma_negata"/></spring-form:option>
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
				 
				<tr>
					<td>&nbsp;</td>
					<td>
						<div id="functions">
						<ul>
							<li><a title="<fmt:message key="label.firma_digitale" />" href="javascript:void 0" onclick="firma()"><fmt:message key="label.firma_digitale" /></a></li>		
						</ul>
						</td>
				</tr>
			</table>
			
			
							
	<br/>
	</spring-form:form>
		<br />
	
		<fieldset>
		<legend>	
			<a class="sezioneDatiPiu"
				id="id_link_docs" 
				href="javascript:showHidePanelBase('docs_lista', 'id_link_docs', '', '${pageContext.request.contextPath}/images/','ul', false);"	
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_da_firmare" />">
				<label for="id_link_endo">(${fn:length(documentidafirmares)}) <fmt:message key="label.documenti_da_firmare" /></label>
		</a>
		</legend>
				<ul id="docs_lista" style="display: none;">
		<c:forEach items="${documentidafirmares}" var="doc">
		
		
					<li>
						<div>
							<span style="width: 200px;"><fmt:message key="documentidafirmare.label.nome_file" /></td>
							<span><b>${doc.oggetti.nomefile}</b></span>
						</div>
						<div>
							<span style="width: 200px;"><fmt:message key="documentidafirmare.label.op_richiedente" /></td>
							<span><b>${doc.richiedente.responsabile}</b></span>
						</div>						
					</li>
				
			</c:forEach>
			</ul>
		</fieldset>
		
		<form name="paginaFirmaFrm" target="_new" action="${pageContext.request.contextPath}/firmadigitale/start.htm" method="post">
			<c:forEach items="${codicioggetto_file_da_firmare}" var="o">
				<input type="hidden" name="codiceoggetto" value="${o}"/>
			</c:forEach>
			<input type="hidden" name="func" value="setEsitoFirma"/>
		</form>
		
	
	<script type="text/javascript">
		
	function getListaCodiciOggetto(){
		return '${codiceoggetto}';
	}
		
	function setEsitoFirma(){
		jQuery('#flagDaFirmare_id').val('<%=WebConstants.FIRMA_COMPLETA%>'); 
		jQuery('#salva_id_button').show();
	}
	
	function cambiaValori(){
		if(jQuery('#flagDaFirmare_id').val() =='<%=WebConstants.FIRMA_NEGATA%>'){
			jQuery('#salva_id_button').show();
		}
	}
	
	function firma(){		
		document.paginaFirmaFrm.submit();				
	}
	
	
	function salva(){
		if(jQuery('#flagDaFirmare_id').val() == ''){
			alert('<fmt:message key="documentidafirmare.label.stato_richiesta" /> <fmt:message key="alert.required" />');
			return;
		}
		doSubmit('${pageContext.request.contextPath}/documentidafirmare/updatesalvafirmamultipla.htm');
	}
	
	</script>
	<div id="functions">
		<ul>
			<li id="salva_id_button" style="display: none;"><a href="javascript:void 0" onclick="salva()"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
</body>
</html>