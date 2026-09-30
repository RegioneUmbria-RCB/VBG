<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
<fieldset><legend><fmt:message key='label.info-file' /></legend>
	<fmt:message key='label.nome' />: <b>${allegato.nomefile}</b>&nbsp;<button id="scarica_file_id"><fmt:message key='button.download' /></button><br />
	<fmt:message key='label.dimensione' />: <b>${allegato.dimensioneFile } Bytes</b><br />
</fieldset>
<fieldset><legend><fmt:message key='label.info-firma' /></legend>
	<c:if test="${report.wsValidationReport.content != null}">
	<fmt:message key='label.contenuto' />: <b>${report.wsValidationReport.content.name}</b>&nbsp;<button id="scarica_contenuto_id"><fmt:message key='button.download' /></button><br />
	</c:if>
	<c:choose>
		<c:when test="${empty report.errors }">
			<fmt:message key='label.esito-verifica-firma' />: <b>OK</b><br />
			<fmt:message key='label.esito-verifica-revoca-certificati' />: <b>OK</b><br />
		</c:when>
		<c:otherwise>
			<ul>
			<c:forEach items="${report.errors }" var="err">
				<li><c:out value="${err.descrizione }" escapeXml="false"></c:out></li>
			</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
	<c:if test="${report.wsValidationReport != null}">
	<jsp:include page="../includes/signatureValidationResult.jsp" />
	</c:if>
</fieldset>
<script type="text/javascript">
$(document).ready(function(){
	$("#scarica_file_id").click(function(){
		download('${allegato.id.codice}', false);
	});
	$("#scarica_contenuto_id").click(function(){
		download('${allegato.id.codice}', true);
	});
	function download(idAllegato, clearContent) {
		location.href = '../nuovaistanzaallegati/ajaxDownload.htm?idAllegato='+idAllegato+'&clearContent='+clearContent;
	}
});
</script>
</body>
</html>
