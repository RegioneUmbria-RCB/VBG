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
			<fmt:message key="label.conferma_cancellazione" />		
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.conferma_cancellazione" />
	 </span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	
	<div id="subcontent">
			<form name="invio" action="controllaPasswordCancellazioni.htm">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="passwordCommand" />
			</jsp:include>			
			<input type="hidden" name="goTo" value="${param.goTo}"/>
			<input type="hidden" name="returnTo" value="${param.returnTo}"/>
			<input type="hidden" name="nomeParametro" value="${param.nomeParametro}"/>
				<table width="50%">
					<tr>
						<td>${messaggioUtente}</td>
					</tr>
					<tr>
						<td><label class="required">*</label> <fmt:message key="label.password" /></td>
						<td>
							<input type="password" id="password_id" name="password" size="20" maxlength="20"/>
						</td>
					</tr>				
				</table>
			</form>	
			
		<script type="text/javascript">
		</script>
	</div>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('controllaPasswordCancellazioni.htm','',document.inviodati)"><fmt:message key="button.ok" /></a></li>
		<li><a href="${param.returnTo}"><fmt:message key="button.annulla" /></a></li>
	</ul>	
	</div>
	
</body>
</html>