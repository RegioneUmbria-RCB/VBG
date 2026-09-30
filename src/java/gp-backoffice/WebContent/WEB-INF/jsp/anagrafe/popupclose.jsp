<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="anagrafe.label.dettaglio_anagrafe.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">		
			<fmt:message key="anagrafe.label.dettaglio_anagrafe.title" />		
	</span>
	<div id="subcontent">
		<spring-form:form commandName="anagrafe" name="popupForm${anagrafe.popupCaller}">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="anagrafe" />
		    </jsp:include>
		    <spring-form:hidden id="descrizioneRichiedente${anagrafe.popupCaller}" path="entity.descrizioneRichiedente" />
		    <spring-form:hidden id="codice${anagrafe.popupCaller}" path="entity.id.codice" />
		<script type="text/javascript">
			opener.document.inviodati.${anagrafe.popupCaller}.value=$('descrizioneRichiedente${anagrafe.popupCaller}').value;
			opener.document.inviodati.${anagrafe.popupCaller}_hidden.value=$('codice${anagrafe.popupCaller}').value;
		</script>	    
		</spring-form:form>
	</div>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:self.close();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>