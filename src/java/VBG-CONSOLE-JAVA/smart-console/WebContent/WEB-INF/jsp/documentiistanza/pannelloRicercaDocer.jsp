<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.NEW}">
			<fmt:message key="documentiistanza.label.nuovo_documentiistanza.title" />
		</c:if> 
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.VIEW}">
			<fmt:message key="documentiistanza.label.dettaglio_documentiistanza.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.NEW}">
			<fmt:message key="documentiistanza.label.nuovo_documentiistanza.title" />
		</c:if> 
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.VIEW}">
			<fmt:message key="documentiistanza.label.dettaglio_documentiistanza.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
			<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../documentiistanza/view" />
		</jsp:include>
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${param.codiceIstanza}</c:param>
		</c:import>
		<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="documentiistanza" name="inviodati" id="inviodatiFormID">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="documentiistanza" />
		    </jsp:include>
			
					<fmt:message key="label.ricerca_documenti_docer.help" />
		<table style="width: 600px;">
			<colgroup>
			    <col style="width: 150px">
			    <col style="">
			  </colgroup>
			<c:choose>
			<c:when test="${not empty tipiDocsDocer }">
				<tr>
					<td><fmt:message key="label.tipo_documento" /></td>
					<td>
					<select name="tipoDocumento" id="tipoDocumento_id" onchange="metadati('tipoDocumento_id')">
						<option value=""><fmt:message key="label.select.default" /></option>
						<c:forEach items="${tipiDocsDocer }" var="td">
							<option value="${td.chiave }">${td.valore }</option>
						</c:forEach>
					</select></td>
				</tr>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="tipoDocumento" id="tipoDocumento_id" value="" />
			</c:otherwise>
			</c:choose>
			<tr>
				<td><fmt:message key="label.filename" /></td>
				<td><input type="text" name="nomeFile" id="nomeFile_id" size="50"/></td>
			</tr>
			<tr>
				<td><fmt:message key="label.descrizione_file" /></td>
				<td><input type="text" name="descrizioneFile" id="descrizioneFile_id" size="50"/></td>
			</tr>
			<tr>
				<td><fmt:message key="label.dati_protocollo" /></td>
				<td><fmt:message key="label.numero" />: <input type="text" name="numeroProtocollo" id="numeroProtocollo_id" size="10"/>&nbsp;<fmt:message key="label.anno" />: <input type="text" name="annoProtocollo" id="annoProtocollo_id" size="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="label.registro" /></td>
				<td><fmt:message key="label.numero" />: <input type="text" name="registroId" id="registroId_id" size="10"/></td>
			</tr>
			<tr>
				<td><fmt:message key="label.keywords" /></td>
				<td><input type="text" name="keywords" id="keywords_id" size="50"/>* indicare più parole separate da "," (virgola)</td>
			</tr>
		</table>

		<div id="metadati_agg_id"></div>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:cercaDocumenti();"><fmt:message key="button.search" /></a></li>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<br class="clear" />
	
	
	
	<div id="queryOut_id"></div>
	
	
	<script type='text/javascript'>
	
	
		function metadati(selectId){
			
			
			var tipodoc = jQuery( "#"+selectId ).val();
			var htmlInCorso = "Ricerca in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
			jQuery('#metadati_agg_id').html(htmlInCorso);
			
			var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/ajaxMetadatiDocumenti.htm',
				  context: document.body,
				  cache: false,
				  data: "tipoDocumento="+tipodoc+"&codiceComune=${codiceComune}&pSoftware=${pSoftware}",
				  dataType: "html",
				  success: function(data) {
						
						jQuery('#metadati_agg_id').html(data);
					  
				  },
				  error: function(jqXHR, textStatus, errorThrown){
					  
					  jQuery('#metadati_agg_id').html(jqXHR.responseText);
				}
			});		
			
		}
	
	
		function cercaDocumenti(){
				
				var datiForm = jQuery( "#inviodatiFormID" ).serialize();
				var htmlInCorso = "Ricerca in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
				jQuery('#queryOut_id').html(htmlInCorso);
				var jhqrPr = jQuery.ajax({
					  url: '../ajaxdocer/ajaxCercaDocumenti.htm',
					  context: document.body,
					  cache: false,
					  data: datiForm,
					  dataType: "html",
					  success: function(data) {
							jQuery('#queryOut_id').html(data);
					  },
					  error: function(jqXHR, textStatus, errorThrown){
						  jQuery('#queryOut_id').html(jqXHR.responseText);
					}
				});		
		}
	
		function creaDocumento( docNum , nomefile){
			var codiceIstanza = ${param.codiceIstanza};
			disableFunctions();				
			var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/ajaxCreaDocumento.htm',
				  context: document.body,
				  cache: false,
				  data: 'docnum='+docNum+'&codiceIstanza='+codiceIstanza+'&nomefile='+nomefile,
				  dataType: "text",
				  success: function(data) {
					  enableFunctions();
					  if(data){
						  if(data=='OK'){
						  	jQuery('#azioniOut' + docNum ).html("<span class=\"success_header\">Operazione avvenuta correttamente</span>");
						  }else{
							jQuery('#azioniOut' + docNum ).html("<span class=\"error_header\">Operazione non avvenuta a causa di: "+data+"</span>");
						  }
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
					  enableFunctions();
					  jQuery('#azioniOut' + docNum ).html(jqXHR.responseText);
				}
			});		
		}
		function visualizza( docNum , nomefile ){
			
			window.location.href='${pageContext.request.contextPath}/ajaxdocer/ajaxDownload.htm?docnum='+docNum+'&nomefile='+nomefile;
			
		}
		
		
	</script>		
</body>
</html>