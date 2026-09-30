<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.ricevuta' /></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.ricevuta' /></div>
	<div class="descrizione"></div>
	<c:if test="${not empty errors }">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
			<span class="ui-icon ui-icon-alert" style="float: left;margin-top: 2px;margin-right: .3em;"></span> 
			<strong><fmt:message key='label.errore-generico' /></strong>
			<ul>
			<c:forEach items="${errors }" var="error">
				<li><c:out value="${error.numeroErrore }"></c:out> - <c:out value="${error.descrizione }" escapeXml="false"></c:out></li>
			</c:forEach>
			</ul>
		</div>
	</c:if>
	<fieldset style="height: 500px; padding: 2px">
		<object data="${pageContext.request.contextPath}/istanze/ajaxDownloadPDFRicevuta.htm?idDomanda=${domanda.id.codice}&no_dialog=true" type="application/pdf" width="100%" height="100%" standby="Loading...">
		  	<p><fmt:message key='label.anteprima-non-disponibile' /></p>
		</object>	
	</fieldset>
	<br />
	<label>
	<fmt:message key='label.scarica-pdf-ricevuta' /> <a href="${pageContext.request.contextPath}/istanze/ajaxDownloadPDFRicevuta.htm?idDomanda=${domanda.id.codice}&no_dialog=true">(<fmt:message key='label.scarica' />)</a>
	</label>		
</body>
</html>