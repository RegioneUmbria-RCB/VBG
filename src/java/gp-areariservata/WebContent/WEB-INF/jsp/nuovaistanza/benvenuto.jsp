<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<c:if test="${NASCONDI_LISTA_STEPS == null}">
	<ul class="list">
		<c:forEach items="${STEPS_HELPER.steps }" var="step">
		<li>
			<c:if test="${step.ordinePager < 10 }">&nbsp;</c:if>
			<c:out value="${step.ordinePager }"></c:out>.&nbsp;&nbsp;<c:out value="${step.titolo }"></c:out>
		</li>
		</c:forEach>
	</ul>
	</c:if>
	<div class="descrizione">
		<fmt:message key="label.click-su-avanti-per-procedere" />
	</div>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>