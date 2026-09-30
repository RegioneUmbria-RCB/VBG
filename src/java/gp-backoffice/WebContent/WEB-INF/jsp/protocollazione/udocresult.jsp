<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page
	import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.riversa_documenti_docer" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message
	key="label.riversa_documenti_docer" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<br class="clear" />
<div id="subcontent">
<spring-form:form commandName="protocolloCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="protocolloCommand" />
	</jsp:include>
</spring-form:form>
</div>
	Unità documentale creata con successo con codice <b>${param.cod}</b>
	<br class="clear"/>
<div id="functions">
   
	<ul>	     
			<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>