<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.cambio_password" />		
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.cambio_password" />
	 </span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="passwordCommand">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="passwordCommand" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.password_old" /></td>
					<td>
						<spring-form:password id="password_id" path="password" size="33" maxlength="32"/>
						<spring-form:errors path="password" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password_new" /></td>
					<td>
						<spring-form:password id="newPassword_id" path="newPassword" size="33" maxlength="32"/>
						<spring-form:errors path="newPassword" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password_new_confirm" /></td>
					<td>
						<spring-form:password id="newPasswordConfirm_id" path="newPasswordConfirm" size="33" maxlength="32"/>
						<spring-form:errors path="newPasswordConfirm" cssClass="error" />
					</td>
				</tr>				
			</table>
		</spring-form:form>		
		<script type="text/javascript">
		</script>
	</div>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:historyBack('','')"><fmt:message	key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>