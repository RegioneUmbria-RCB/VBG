<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.parametri" /></title>
</head>
<body>
	<div class="titolo"><fmt:message key="label.pannello-di-amministrazione" /></div>
	<div class="descrizione"></div>
		<div class="sezione">
		<spring-form:form action="reload.htm" method="post">
			<input type="submit" value="reload security" />
		</spring-form:form>
		<spring-form:form action="reset.htm" method="post">
			<input type="submit" value="reset cache" />
		</spring-form:form>
		</div>
		<c:if test="${attrs != null }">
			<fieldset><legend>RELOAD SECURITY</legend>
			<table>
			<c:forEach items="${attrs}" var="attr">
				<tr>
					<td>${attr.key }</td>
					<td>${attr.value }</td>
				</tr>
			</c:forEach>
			</table>
			</fieldset>
		</c:if>
		<c:if test="${caches != null }">
			<fieldset><legend>RESET CACHE</legend>
			<table>
			<c:forEach items="${caches}" var="cacheName">
				<tr>
					<td>${cacheName }</td>
				</tr>
			</c:forEach>
			</table>
			</fieldset>
		</c:if>
</body>
</html>