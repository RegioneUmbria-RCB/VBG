<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="ruoli.label.dettaglio_responsabili.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="ruoli.label.dettaglio_responsabili.title" />
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
			<td><fmt:message key="ruoli.label.responsabiliruoli" /> <b>${ruolo.ruolo}</b></td>
			<td><spring-form:errors path="responsabiliruolis" cssClass="error"/></td>
		</tr>
		<c:forEach items="${responsabili}" var="resp_var" varStatus="a">
			<tr>
				<td>
				<c:if test="${!resp_var.responsabileRuoloTransient}">
					<input  type="checkbox" value="${resp_var.id.codice}" name="responsabilis" />${resp_var.responsabile}
				</c:if>
				<c:if test="${resp_var.responsabileRuoloTransient}">
					<input  type="checkbox" value="${resp_var.id.codice}" name="responsabilis" checked="checked"/>${resp_var.responsabile}
				</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('saveRuoliResponsabili.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('view.htm?codice=${ruolo.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
