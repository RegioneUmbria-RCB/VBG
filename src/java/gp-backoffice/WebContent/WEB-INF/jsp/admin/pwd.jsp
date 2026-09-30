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
			Autorizzazione funzioni amministrative	
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		Autorizzazione funzioni amministrative
	 </span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<form name="passwordCommand" method="post">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="passwordCommand" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.password" /></td>
					<td>
						<input type="password" id="password_id" name="password" size="33" maxlength="32"/>
					</td>
				</tr>				
			</table>
		</form>		
		<script type="text/javascript">
		</script>
	</div>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('authorize.htm','',document.passwordCommand)"><fmt:message key="button.ok" /></a></li>
		<li><a href="javascript:historyBack('','')"><fmt:message	key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>