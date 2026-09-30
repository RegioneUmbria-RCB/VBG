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
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
		<div class="titolo_sezione"><fmt:message key='label.' /></div>
		<div class="sezione">
			<table class="sezione_table">
				<tr>
					<td class="sezione_table_label"><fmt:message key='label.' /></td>
					<td></td>
				</tr>
			</table>
			<div class="titolo_sottosezione"><fmt:message key='label.' /></div>
			<table class="sezione_table">	
				<tr>
					<td class="sezione_table_label"><fmt:message key='label.' /></td>
					<td></td>
				</tr>
			</table>
			<table class="sezione_table">
				<tr>
					<td class="sezione_table_buttons">
						<input type="submit" value="<fmt:message key='button.' />"  />
					</td>
				</tr>
			</table>
		</div>
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>