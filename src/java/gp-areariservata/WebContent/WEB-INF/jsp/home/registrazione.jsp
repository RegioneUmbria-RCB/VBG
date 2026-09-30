<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.registrazione-utente" /></title>
	</head>
	<body>	
		<p>
			<%pageContext.setAttribute("ESITOREG", request.getSession().getAttribute("ESITOREG")); %>
			<%pageContext.setAttribute("AUTH_TYPE", request.getSession().getAttribute("AUTH_TYPE")); %>
			<c:if test="${ESITOREG eq 'OK' }">
				<c:if test="${AUTH_TYPE eq 'AUTH_TYPE_REG' }">
					<fmt:message key="label.registrazione-ok" />
				</c:if>
				<c:if test="${AUTH_TYPE eq 'AUTH_TYPE_REG_SC' }">
					<fmt:message key="label.registrazione-sc-ok" />
				</c:if>	
			</c:if>
			<c:if test="${ESITOREG eq 'KO' }">
				<fmt:message key="label.registrazione-ko" />
			</c:if>
			<c:if test="${ESITOREG eq 'KO_USER_REGISTERED' }">
				<fmt:message key="label.registrazione-ko-utente-registrato" />
			</c:if>
		</p>
	</body>
</html>