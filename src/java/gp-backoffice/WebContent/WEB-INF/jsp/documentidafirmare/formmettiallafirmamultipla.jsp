<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare"%>
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
	<c:set var="richiestaEffettuata" value="<%= StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name()%>" />
	<c:set var="firmaNegata" value="<%= StatiDocumentiDaFirmare.FIRMA_NEGATA.name()%>" />
	<c:set var="firmaCompleta" value="<%= StatiDocumentiDaFirmare.FIRMA_COMPLETA.name()%>" />
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
	<%-- SEZIONE CHE PERMETTE DI INSERIRE UNA NOTA IN CASO DI FIRMA NEGATA --%>
	<div dojoType="dijit.Dialog" id="rigettaFirmaDialogDiv" title="<fmt:message key="documentidafirmare.label.note_resp" />"  style="display: none;">
				<table>
				<tr>
					<td><fmt:message key="documentidafirmare.label.note_resp" /></td>
					<td>	
					     <textarea rows="4" cols="50" id="annotazione_firmatario" > </textarea> 		
					</td>
				</tr>
				</table>
				<div id="functions">
				    
					<ul>
						<li><a href="javascript:updateRigetta('annotazione_firmatario')"><fmt:message key="button.update" /></a></li>
						<li><a href="javascript:void 0" onclick="dijit.byId('rigettaFirmaDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
					</ul>
				</div>
				<br class="clear" />	
		</div>
	</spring-form:form>
		<br />
	
		<fieldset>
		
		<%-- SEZIONE DOCUMENTI MESSI CHE DEVONO ESSERE FIRMATI --%>
		<legend>	
			<a class="sezioneDatiMeno"
				id="id_link_docs" 
				href="javascript:showHidePanelBase('docs_lista', 'id_link_docs', '', '${pageContext.request.contextPath}/images/','ul', false);"	
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_da_firmare" />">
				<label for="id_link_docs">(${fn:length(documentidafirmares)}) <fmt:message key="label.documenti_da_firmare" /></label>
		</a>
		</legend>
		<ul id="docs_lista" style="display:;">
		<table>
		<c:forEach items="${documentidafirmares}" var="doc" varStatus="a" >
		            <tr>
		            <td>
					<li>
						<div>
							<span style="width: 200px;"><fmt:message key="documentidafirmare.label.nome_file" /></span>
							<span><b>${doc.oggetti.nomefile}</b></span>
							<span>
								<c:if test="${doc.oggetti != null}">
                                    <jsp:include page="../includes/visualizzaOggetto.jsp" >
                                           <jsp:param name="idElemento" value="qrxmlbase_${doc.oggetti.id.codice}" />
                                           <jsp:param name="fileId" value="${doc.oggetti.id.codice}" />
                                           <jsp:param value="true" name="readonly" />
                                    </jsp:include>
                                </c:if>   
							</span>
						</div>
						<div>
							<span style="width: 200px;"><fmt:message key="documentidafirmare.label.op_richiedente" /></span>
							<span><b>${doc.richiedente.responsabile}</b></span>
						</div>				
					</li>
					</td>
					<td>
					<script type='text/javascript'>
						function editDocs${a.index}(id){
							location.href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId="+id;
						}
						
						function trasformaInPDF${a.index}(movimentiAllegatiId,codiceOggetto){
							doHref('insertTrasformaInPdf.htm?codiceMovimentoallegato='+movimentiAllegatiId+'&codiceOggetto='+codiceOggetto+'&lista_doc_da_firmare=${lista_doc_da_firmare}','<fmt:message key="javascript.confirm.conferma_trasforma_il_documento_in_pdf" />');
						}
						
						function anteprimaPDF${a.index}(codiceOggetto){
							location.href="${pageContext.request.contextPath}/file/ajaxAnteprimaPdf.htm?fileId="+codiceOggetto
						}
						
						function segnaComeFirmato${a.index}(idDocDaFirmare){
							doHref('${pageContext.request.contextPath}/documentidafirmare/segnaDocumentoComeFirmato.htm?codiceDocumento='+idDocDaFirmare+'&lista_doc_da_firmare=${lista_doc_da_firmare}','<fmt:message key="javascript.confirm.segna_documento_firmato" />');
						}
						
					</script>	
					<div id="functions">
					<ul>
						<li><a href="javascript:editDocs${a.index}(${doc.oggetti.id.codice})"><fmt:message key="label.modifica" /></a></li>
						<c:if test = "${doc.estensioneFile=='rtf' || doc.estensioneFile=='odt'}">
							<li><a href="javascript:anteprimaPDF${a.index}(${doc.oggetti.id.codice})"><fmt:message key="label.anteprima_pdf" /></a></li>
							<li><a href="javascript:trasformaInPDF${a.index}(${doc.movimentiallegati.id.codice},${doc.oggetti.id.codice})"><fmt:message key="label.converti_in_pdf" /></a></li>
						</c:if>
						<c:if test="${isSegnaComeFirmatoAttivo && (doc.estensioneFile=='pdf'|| doc.estensioneFile=='p7m')}">
							<li><a href="javascript:segnaComeFirmato${a.index}(${doc.id.codice})"><fmt:message key="label.segna_come_firmato" /></a></li>
						</c:if>
					</ul>
					</div>
					</td>
					<td> 
						<c:if test="${!doc.isCheckOggettoUguale}"><fmt:message key="label.warning_incompatibilita_documenti_alla_firma" />: <b>${doc.movimentiallegati.oggetto.nomefile}</b></c:if></td>
				   </tr>
			</c:forEach>
			</table>
			</ul>
		</fieldset>
		
		
		
		<%-- SEZIONE CHE PERMETTE DI PASSARE I RECORD DI DOCUMENTIDAFIRMARE AL METODO CHE INVOCA L'APP DI FIRMA --%>
		<%--
		<form name="paginaFirmaFrm"  action="${pageContext.request.contextPath}/firmadigitale/startDocumentiDaFirmare.htm" method="post">
			<c:forEach items="${documentidafirmares}" var="o">
				<input type="hidden" name="codiceoggetto" value="${o.id.codice}"/>
			</c:forEach>
			<input type="hidden" name="func" value="setEsitoFirma"/>
		</form>
		--%>
		
	
		<form name="paginaFirmaFrm"  action="${pageContext.request.contextPath}/firmadigitale2/start.htm" method="get">
			<c:forEach items="${documentidafirmares}" var="o">
				<input type="hidden" name="codiceoggetto" value="${o.id.codice}"/>
			</c:forEach>
			<input type="hidden" name="mettiAllaFirma" value="true"/>
		</form>
		<form name="paginaFirmaRemotaFrm"  action="${pageContext.request.contextPath}/firmadigitale2/startFirmaRemota.htm" method="post">
			<c:forEach items="${documentidafirmares}" var="o">
				<input type="hidden" name="codiceoggetto" value="${o.id.codice}"/>
			</c:forEach>
			<input type="hidden" name="mettiAllaFirma" value="true"/>
		</form>
		
		
	
	<script type="text/javascript">
	
	/**
	function getListaCodiciOggetto(){
		return '${codiceoggetto}';
	}
		*/
	
	function setEsitoFirma(){
		jQuery('#flagDaFirmare_id').val('<%=StatiDocumentiDaFirmare.FIRMA_COMPLETA.name()%>'); 
		jQuery('#salva_id_button').show();
	}


	function cambiaValori(){
		if(jQuery('#flagDaFirmare_id').val() =='<%=StatiDocumentiDaFirmare.FIRMA_NEGATA.name()%>'){
			jQuery('#salva_id_button').show();
		}
	}
	
	/**
	Apre il dialog per inserire le annotazioni e rigettare la firma
	**/
	function rigetta(){
		dijit.byId('rigettaFirmaDialogDiv').show();
	}
	/**
	Invoca il metodo del controller che rigetta la firma e salva sul db i file come rigettati
	**/
	function updateRigetta(annotazioni_firmatario){
		var annotazioni=document.getElementById(annotazioni_firmatario).value
		doSubmit('${pageContext.request.contextPath}/documentidafirmare/updateRigettafirmamultipla.htm?annotazione_firmatario='+escape(annotazioni));
	}
	
	function firma(){		
		document.paginaFirmaFrm.submit();				
	}
	function firmaRemota(){		
		document.paginaFirmaRemotaFrm.submit();				
	}
	
	/*
	function salva(){
		if(jQuery('#flagDaFirmare_id').val() == ''){
			alert('<fmt:message key="documentidafirmare.label.stato_richiesta" /> <fmt:message key="alert.required" />');
			return;
		}
		doSubmit('${pageContext.request.contextPath}/documentidafirmare/updatesalvafirmamultipla.htm');
	}
	*/
	</script>
	<div id="functions">
		<ul>
			<%--  <li id="salva_id_button" style="display: none;"><a href="javascript:void 0" onclick="salva()"><fmt:message key="button.update" /></a></li> --%>
			<c:if test="${display eq '0'}">
				<li><a href="javascript:void 0" onclick="rigetta()"><fmt:message key="button.rigetta" /></a></li>
				<li><a title="<fmt:message key="label.firma_digitale" />" href="javascript:void 0" onclick="firma()"><fmt:message key="label.firma_digitale" /></a></li>
				<c:if test="${isFirmaRemotaAttiva}">
						<li><a href="javascript:firmaRemota();"><fmt:message key="label.firma_digitale_remota" /></a></li>
					</c:if>		
				<%-- <li><a href="javascript:void 0" onclick="salva()"><fmt:message key="button.firma" /></a></li> --%>
			</c:if>
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
</body>
</html>