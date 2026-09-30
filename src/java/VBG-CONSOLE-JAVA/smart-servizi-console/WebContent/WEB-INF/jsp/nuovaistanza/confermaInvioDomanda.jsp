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
	</c:if>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
		<div class="sezione">
		<table class="sezione_table">
			<tr>
				<td colspan="2"><fmt:message key='label.conferma-invio-domanda' /></td>
			</tr>
		</table>
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.indietro' />" onclick="indietro()" />
			<input type="button" value="<fmt:message key='button.invia' />" onclick="invio()" />
			</td>
		</tr>
		</table>
		</div>
	</spring-form:form>
	<script type="text/javascript">
	function invio(){
		$.blockUI();
		document.forms[0].submit();
		
	}
	function indietro(){
		$.blockUI();
		document.location.href='../nuovaistanza/indietro.htm?pager_azione=I';	
	}
	</script>
</body>
</html>