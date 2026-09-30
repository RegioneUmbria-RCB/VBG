<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.riepilogo" /></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<c:if test="${not empty errors }">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
			<span class="ui-icon ui-icon-alert" style="float: left;margin-top: 2px;margin-right: .3em;"></span> 
			<strong><fmt:message key="label.errore-generico" /></strong>
			<ul>
			<c:forEach items="${errors }" var="error">
				<li><c:out value="${error.descrizione }" escapeXml="false"></c:out></li>
			</c:forEach>
			</ul>
		</div>
		<div>
			<input type="button" value="<fmt:message key='button.indietro' />" onclick="indietro()" />
		</div>
	</c:if>
	<c:if test="${empty errors }">
	<br /><br />
	<fieldset style="height: 500px; padding: 2px;">
		<object id="anteprima" data="${pageContext.request.contextPath}/nuovaistanzariepilogo/ajaxDownloadPDF.htm?idDomanda=${nuovaIstanzaCommand.id}&no_dialog=true" type="application/pdf" width="100%" height="100%" standby="Loading...">
		  	<p><fmt:message key='label.anteprima-non-disponibile' /></p>
		</object>	
	</fieldset>
	<form action="save.htm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pager_step" />
	<input type="hidden" name="pager_azione" value="A" />
	<div class="sezione">
	<c:if test="${empty RIEPILOGO_NON_FIRMATO }">
	<c:set var="checkFile" value="0"></c:set>
	<table class="sezione_table" border="0">
	<tr>
		<td><div class="titolo_sezione"><fmt:message key='label.firma-riepilogo' /></div></td>
		<td><div class="titolo_sezione"><fmt:message key='label.firma-riepilogo-alternativa' /></div></td>
	</tr>
	<tr>
	<td width="50%" valign="top">
		<input type="button" id="button-firma" value="<fmt:message key='button.firma-online' />" onclick="mettiAllaFirma()" />
		<div id="applet-container" style="display: none">
			<%@ include file="../includes/firmadigitale2.jsp"%>
		</div>
	</td>
	<td width="50%" valign="top">	
	<div>
	<fieldset id="alternativa">
	<ol>
		<li><fmt:message key='label.scarica-pdf-riepilogo' /> <a href="${pageContext.request.contextPath}/nuovaistanzariepilogo/ajaxDownloadPDF.htm?idDomanda=${nuovaIstanzaCommand.id}&no_dialog=true">(<fmt:message key='label.scarica' />)</a></li>
		<li><fmt:message key='label.firma-pdf-scaricato' /></li>
		<li><fmt:message key='label.allega-pdf-firmato' /><input type="file" name="file" id="file" size="40" /></li>
	</ol>
	</fieldset>
	</div>
	</td>
	</tr>
	</table>
	</c:if>
	<c:if test="${not empty RIEPILOGO_NON_FIRMATO }">
	<input type="file" name="file" id="file" size="40" style="display: none;"/>
	<c:set var="checkFile" value="0"></c:set>
	<ul>
		<li><fmt:message key='label.scarica-pdf-riepilogo' /> <a href="${pageContext.request.contextPath}/nuovaistanzariepilogo/ajaxDownloadPDF.htm?idDomanda=${nuovaIstanzaCommand.id}&no_dialog=true">(<fmt:message key='label.scarica' />)</a></li>
	</ul>
	</c:if>
	<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.indietro' />" onclick="indietro()" />
			<input type="button" value="<fmt:message key='button.salva' />" onclick="salva('${checkFile}')" />
			</td>
		</tr>
	</table>
	</div>	
	</form>
	</c:if>
	<script type="text/javascript">
	function salva(checkFile){
		if($('#file').val() == '' && checkFile == '1'){
			alert("<fmt:message key='alert.pdf-firmato-non-caricato' />");
		}else{
			$('#anteprima').hide();
			if(typeof hideAppletContainer == 'function')hideAppletContainer();
			$.blockUI();
			document.forms[0].submit();
		}
	}
	function indietro(){
		$('#anteprima').hide();
		if(typeof hideAppletContainer == 'function')hideAppletContainer();
		$.blockUI();
		document.location.href='../nuovaistanza/indietro.htm?pager_azione=I';
	}
	
	//MEDODO DA IMPLEMENTARE PER LA FIRMA
	function getFileDaFirmare(){
		return new Array("${nuovaIstanzaCommand.idRiepilogo}");
	}
	//MEDODO DA IMPLEMENTARE PER LA FIRMA
	function afterSetEsitoFirma(codiceOggetto, fileName, esito, messaggio){
		$("#firma_esito_msg").remove();
		if ('OK' == esito) {
			$("#applet-container").after("<b id='firma_esito_msg' style='color: green'> <fmt:message key='label.firma-ok' /> ("+fileName+")</b>");
		}else{
			$("#applet-container").after("<b id='firma_esito_msg' style='color: red'> <fmt:message key='label.firma-ko' /> "+messaggio+"</b>");
		}
	}
	//MEDODO DA IMPLEMENTARE PER LA FIRMA
	function firmaCompletata(){
		$('#file').val('');
		salva('0');
	}
	
	</script>
</body>
</html>