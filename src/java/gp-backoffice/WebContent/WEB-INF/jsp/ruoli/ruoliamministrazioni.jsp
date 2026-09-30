<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="ruoli.label.dettaglio_amministrazioni.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="ruoli.label.dettaglio_amministrazioni.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="ruolo" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="ruolo" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="ruoli.label.ruoliamministrazioni" /> <b>${ruolo.ruolo}</b></td>
			<td><spring-form:errors path="ruoliamministrazionis" cssClass="error"/></td>
		</tr>
		<c:forEach items="${amministrazioni}" var="amm_var" varStatus="a">
			<tr>
				<td>
				<c:if test="${!amm_var.amministrazioneRuoloTransient}">
					<input  type="checkbox" value="${amm_var.id.codice}" name="amministrazionis" />${amm_var.amministrazione}
				</c:if>
				<c:if test="${amm_var.amministrazioneRuoloTransient}">
					<input  type="checkbox" value="${amm_var.id.codice}" name="amministrazionis" checked="checked"/>${amm_var.amministrazione}
				</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('saveRuoliAmministrazioni.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('view.htm?codice=${ruolo.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
