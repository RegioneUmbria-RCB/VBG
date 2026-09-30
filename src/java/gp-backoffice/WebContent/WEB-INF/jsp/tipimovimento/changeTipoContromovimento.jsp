<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="contromovimento.label.dettaglio_contromovimento.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="contromovimento.label.dettaglio_contromovimento.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
		 <spring-form:form commandName="contromovimento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="contromovimento" />
		    </jsp:include>
		<table>
			
			<tr>
				<td>
					<fmt:message key="tipimovimento.label.codice_tipocontromovimento" />
				</td>
				<td>
				<jsp:include page="../includes/tipimovimentosearch.jsp" >
					<jsp:param name="idElemento" value="tipoMovimentoInputId" />
					<jsp:param name="pathTipomovimento" value="tipocontromovimento" />
				</jsp:include>
				</td>
			</tr>
		</table>
	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('updateTipoContromovimento.htm','',document.inviodati)"><fmt:message key="button.save" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>