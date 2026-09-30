<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${lotti.id.codicelotto==null}">
	<fmt:message key="lotti.label.nuovo_lotti.title" />
</c:if> <c:if test="${lotti.id.codicelotto!=null}">
	<fmt:message key="lotti.label.dettaglio_lotti.title" />
</c:if></title>
</head>
<body>
<span class="titoloPagina"> <c:if test="${lotti.id.codicelotto==null}">
	<fmt:message key="lotti.label.nuovo_lotti.title" />
</c:if> <c:if test="${lotti.id.codicelotto!=null}">
	<fmt:message key="lotti.label.dettaglio_lotti.title" />
</c:if> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.area" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${lotti.aree.denominazione}" /></div>
		</div>
</div>
<br />
<spring-form:form commandName="lotti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="lotti" />
	</jsp:include>
	<table>
	<c:if test="${lotti.id.codicelotto!=null}">
		<tr>
			<td><fmt:message key="label.codice" /></td>
			<td>
				<spring-form:input path="id.codicelotto" size="5" readonly="true" />				
			</td>
		</tr>
	</c:if>
		<tr>
			<td><fmt:message key="lotti.label.assegnato" /></td>
			<td><spring-form:checkbox path="assegnato" value="1" /></td>

		</tr>
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td><spring-form:input id="note_id" path="note" size="60" /> 
			<spring-form:errors path="note" cssClass="error" /></td>
		</tr>
	</table>
	<script type='text/javascript'>
	$('note_id').focus();
</script>
</spring-form:form></div>
<div id="functions">
<ul>
	<c:if test="${lotti.id.codicelotto==null}">
		<li><a href="javascript:doSubmit('insert.htm?codiceArea=${param.codiceArea}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${lotti.id.codicelotto!=null}">
		<li><a href="javascript:doSubmit('update.htm?codiceArea=${lotti.id.codicearea}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm?codiceArea=${lotti.id.codicearea}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?codiceArea=${param.codiceArea}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>