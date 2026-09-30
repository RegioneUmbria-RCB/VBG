<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione" >&nbsp;</div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
		<div class="informativa" >
			<c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out>
		</div>
		<c:set var="accetta_informativa">
			<c:if test="${ETICHETTA_CHECKBOX == null}">
				<fmt:message key="label.accetta-informativa-privacy" />
			</c:if>
			<c:if test="${ETICHETTA_CHECKBOX != null}">
				${ETICHETTA_CHECKBOX}
			</c:if>
		</c:set>
		<spring-form:checkbox path="informativa.accettata" title="${accetta_informativa }" label="${accetta_informativa }"  cssErrorClass="validation_error_input" />
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>