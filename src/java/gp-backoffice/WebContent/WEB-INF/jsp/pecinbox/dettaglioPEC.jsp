<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="pecinbox.dettagliopec.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="pecinbox.dettagliopec.title" />
	</span>
	<br  class="clear" />
	<spring-form:form commandName="pecCommand" name="istanzaDaPecForm" id="istanzaDaPecForm" action="${pageContext.request.contextPath}/pecinbox/creaIstanza.htm">
	
	<spring-form:hidden path="pec.id.id" />
	
		<br />
		<%-- TODO far funzionare e testare la visualizzazione dei messaggi di errore --%>
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="pecCommand" />
	    </jsp:include>
		<table width="100%" border=0">
			<tr class="titoloSezione">
				<td colspan="6">
					<fmt:message key="pecinbox.label.dati_pec"/>		
				</td>
			</tr>
			<jsp:include page="datiPEC.jsp" >
		        <jsp:param name="commandName" value="pecCommand" />
		    </jsp:include>
		</table>
	</spring-form:form>
	<script type="text/javascript">
	
		function backToPecInbox(){
			doHref("${pageContext.request.contextPath}/pecinbox/indietro.htm?idPec=${istanzaPecCommand.pec.id.id}", "");
		}
		
	</script>
	<div id="functions">
		<ul>
			<%-- qui si potrebbero aggiungere dei bottoni che richiemano direttamente le altre funzioni: protocolla, crea istanza e crea movimento --%>
			<%-- 
			<c:if test="${empty error and not istanzaPecCommand.readOnly}">
				<li><a href="javascript:void();" id="btn_salva"><fmt:message key="label.salva" /></a></li>
			</c:if>
			--%>
			<c:if test="${empty returnToExternal}">
				<li><a href="javascript:backToPecInbox();" id="btn_indietro"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${not empty returnToExternal}">
			<li><a href="javascript:location.href='${returnToExternal}'" id="btn_indietro"><fmt:message key="button.back" /></a></li>
			</c:if>
		</ul>
	</div>
</body>
</html>