<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/taglibs.jsp" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.stc" /></title>
</head>
<body onload='document.f.j_username.focus();'>
<fieldset>
<center>
<h3><fmt:message key="label.stc.desc" /></h3>
<%
AuthenticationException ax = (AuthenticationException)session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
if(ax!=null){
    String ax_msg = ax.getLocalizedMessage();
    out.print("<p><font color='red'>- ");
    out.print(ax_msg);
    out.print(" -</font></p>");
}
%>
<form name='f' action='<%=request.getContextPath() %>/j_spring_security_check' method='post'>
<table>
    <tr><td><fmt:message key="label.user" /></td></tr>
    <tr><td><input type='text' name='j_username' /></td></tr>
    <tr><td><fmt:message key="label.password" /></td></tr>
    <tr><td><input type='password' name='j_password' /></td></tr>
    <tr><td><input name="submit" type="submit" value="Login" /></td></tr>
 </table>
</form>
</center>
</fieldset>
</body>
</html>