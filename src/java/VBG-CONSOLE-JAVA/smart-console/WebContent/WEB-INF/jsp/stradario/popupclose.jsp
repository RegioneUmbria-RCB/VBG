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
		<fmt:message key="form.stradario.title.view" />
	</title>
</head>
<body>
	<span class="titoloPagina">		
			<fmt:message key="form.stradario.title.view" />		
	</span>
	<div id="subcontent">
		<spring-form:form commandName="stradario" name="popupForm${stradario.popupCaller}">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="stradario" />
		    </jsp:include>
		    <spring-form:hidden id="descrizioneCompleta${stradario.popupCaller}" path="descrizioneCompleta" />
		    <spring-form:hidden id="codice${stradario.popupCaller}" path="id.codice" />
		    <spring-form:hidden id="cap${stradario.popupCaller}" path="cap" />
		    <spring-form:hidden id="locfraz${stradario.popupCaller}" path="locfraz" />
		    <spring-form:hidden id="stradariozone.zona${stradario.popupCaller}" path="stradariozone.zona" />
		<script type="text/javascript">
			opener.document.inviodati.${stradario.popupCaller}.value=$('descrizioneCompleta${stradario.popupCaller}').value;
			opener.document.inviodati.${stradario.popupCaller}_hidden.value=$('codice${stradario.popupCaller}').value;
			opener.document.inviodati.cap_id.value=$('cap${stradario.popupCaller}').value;
			opener.document.inviodati.frazione_id.value=$('locfraz${stradario.popupCaller}').value;
			opener.document.inviodati.circoscrizione_id.value=$('stradariozone.zona${stradario.popupCaller}').value;
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