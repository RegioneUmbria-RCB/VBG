<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${aree.id.codice==null}">
	<fmt:message key="form.aree.title.create" />
</c:if> <c:if test="${aree.id.codice!=null}">
	<fmt:message key="form.aree.title.view" />
</c:if></title>
</head>
<body>
<span class="titoloPagina"> <c:if test="${aree.id.codice==null}">
	<fmt:message key="form.aree.title.create" />
</c:if> <c:if test="${aree.id.codice!=null}">
	<fmt:message key="form.aree.title.view" />
</c:if> </span>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../aree/view" />
</jsp:include>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent"><spring-form:form commandName="aree" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="aree" />
	</jsp:include>
	<table>
		<tr id="elementIdBeforeCombo">
			<td colspan="2" style="height: 0px"></td>
		</tr>
		<jsp:include page="../includes/comboComuni.jsp">
			<jsp:param name="comune" value="${aree.comune.codicecomune}" />
			<jsp:param name="mostraTutti" value="false" />
			<jsp:param name="readOnly" value="false" />
			<jsp:param name="commandPropertyPath" value="comune" />
			<jsp:param name="colspan" value="1" />
			<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
		</jsp:include>
		
		<tr>
			<td><fmt:message key="form.aree.denominazione" /></td>
			<td><spring-form:input id="denominazione_id" path="denominazione" size="70" tabindex="0" /> <spring-form:errors path="denominazione" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.aree.proprieta" /></td>
			<td><spring-form:input id="proprieta_id" path="proprieta" size="70" /> <spring-form:errors path="proprieta" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.aree.localita" /></td>
			<td><spring-form:input id="localita_id" path="localita" size="70" /> <spring-form:errors path="localita" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.aree.note" /></td>
			<td><spring-form:input id="note_id" path="note" size="70" /> <spring-form:errors path="note" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.aree.tipoaree" /></td>
			<td><spring-form:input size="50" id="tipiaree_id" path="tipiaree.tipoarea" cssClass="searchbox" onchange="checkValue(this,'tipiaree_hidden')" onkeydown="javascript:return searchAll(this,event)" /> <init:autocompleter
				methodAjax="findTipiaree.htm" idHidden="tipiaree_hidden" idInput="tipiaree_id" inputTitleKey="label.ricerca_tipo_area"
			/> <spring-form:errors path="tipiaree" cssClass="error" /> <spring-form:hidden id="tipiaree_hidden" path="tipiaree.id.codice" /></td>
		</tr>
		<c:if test="${isDehorsConfigurato}">
		<tr>
			<td><fmt:message key="label.mq_disponibili_dehors"/></td>
			<td>
				<spring-form:input id="mq_disponibili_id" path="mqdisponibili" size="20" onblur="checkNumberValue(this);" />
				 <init:help idHelp="help_mq_disponibili_id" textKey="aree.label.mq_disponibili.help" /> 
				<spring-form:errors path="mqdisponibili" cssClass="error" />
			</td>
		</tr>
		</c:if>
	</table>
	<script type='text/javascript'>
	$('denominazione_id').focus();
</script>
</spring-form:form></div>
<div id="functions">
<ul>
	<c:if test="${aree.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${aree.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		
		<li><a href="javascript:historySet('${_urlback}','../areedettagli/list.htm?codiceArea=${aree.id.codice}','');"><fmt:message key="button.dettaglio" /></a></li>
		<%
		    if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_SU)) {
		%>
			<li><a href="javascript:doHref('../lotti/list.htm?codiceArea=${aree.id.codice}','',document.inviodati)"><fmt:message key="aree.button.lotti" /></a></li>
		<%
		    }
		%>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>