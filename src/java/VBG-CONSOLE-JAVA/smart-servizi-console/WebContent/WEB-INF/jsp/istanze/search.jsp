<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.istanze-presentate' /></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.istanze-presentate' /></div>
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
	<fieldset><legend><fmt:message key='label.filtri-ricerca' /></legend>
	<spring-form:form commandName="filtriRicercaPratiche" action="list.htm">
	<%--
		<spring-form:checkbox path="cercaComeRichiedente" /><fmt:message key='label.cerca-come-richiedente' /><br />
		<spring-form:checkbox path="cercaComeAziendaRichiedente" /><fmt:message key='label.cerca-come-azienda' /><br />
		<spring-form:checkbox path="cercaComeIntermediario" /><fmt:message key='label.cerca-come-intermediario' /><br />
		<spring-form:checkbox path="cercaComeSoggettoCollegato" /><fmt:message key='label.cerca-come-sogg-collegato' /><br />
		<br />
	--%>
		<table>
		<%--
		<tr>
			<td><fmt:message key='label.numero-istanza' /></td><td></td><td colspan="3"><spring-form:input path="numeroPratica" size="35" /></td>
		</tr>
		 --%>
		<tr>
			<td><fmt:message key='label.data-istanza' /></td><td><fmt:message key='label.data-da' /></td><td><spring-form:input id="dataPresentazioneDa" path="dataPresentazioneDa" size="10" maxlength="10" readonly="true"/></td><td><fmt:message key='label.data-a' /></td><td><spring-form:input id="dataPresentazioneA" path="dataPresentazioneA" size="10" maxlength="10" readonly="true"/></td>
		</tr>
		<tr>
			<td><fmt:message key='label.numero-protocollo' /></td><td></td><td colspan="3"><spring-form:input path="numeroProtocollo" size="35" /></td>
		</tr>
		<tr>
			<td><fmt:message key='label.data-protocollo' /></td><td><fmt:message key='label.data-da' /></td><td><spring-form:input id="dataProtocolloDa" path="dataProtocolloDa" size="10" maxlength="10" readonly="true"/></td><td><fmt:message key='label.data-a' /></td><td><spring-form:input id="dataProtocolloA" path="dataProtocolloA" size="10" maxlength="10" readonly="true"/></td>
		</tr>
		</table>
		<br />
		<input type="button" value="<fmt:message key='button.cerca' />" onclick="cercaIstanze()"/>
	</spring-form:form>
	</fieldset>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#dataPresentazioneDa').datepicker();
			$('#dataPresentazioneA').datepicker();
			$('#dataProtocolloDa').datepicker();
			$('#dataProtocolloA').datepicker();
		});
		function cercaIstanze(){
			$.blockUI();
			document.forms[0].submit();
		}		
	</script>
</body>
</html>