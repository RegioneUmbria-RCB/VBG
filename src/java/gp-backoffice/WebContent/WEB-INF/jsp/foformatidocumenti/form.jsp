<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.foformatidocumenti.title" />
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.foformatidocumenti.title" />
	</span>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../foformatidocumenti/view" />
	</jsp:include>

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="foformatidocumenti" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="foformatidocumenti" />
		</jsp:include>
		<table>
			<tr>
				<td><fmt:message key="label.formato" /></td>
				<td>
					<spring-form:input id="formato_id" path="formato" size="40" /> 
					<spring-form:errors path="formato" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.dimensione_massima_pagina" /></td>
				<td>
					<spring-form:input id="dimensioneMaxPagina_id" path="dimensioneMaxPagina" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberInt(this);"/> Kb
					<spring-form:errors path="dimensioneMaxPagina" cssClass="error" /></td>
			    </td>
			</tr>
		</table>
		<script type='text/javascript'>
		$('formato_id').focus();
	</script>
	</spring-form:form></div>
	<div id="functions">
	<ul>
		<c:if test="${foformatidocumenti.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${foformatidocumenti.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>