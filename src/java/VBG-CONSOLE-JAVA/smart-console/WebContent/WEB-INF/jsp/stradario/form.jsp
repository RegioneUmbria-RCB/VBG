<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${stradario.id.codice==null}">
	<fmt:message key="form.stradario.title.create" />
</c:if> <c:if test="${stradario.id.codice!=null}">
	<fmt:message key="form.stradario.title.view" />
</c:if></title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${stradario.id.codice==null}">
	<fmt:message key="form.stradario.title.create" />
</c:if> <c:if test="${stradario.id.codice!=null}">
	<fmt:message key="form.stradario.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent"><spring-form:form commandName="stradario"
	name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="stradario" />
	</jsp:include>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../stradario/view" />
</jsp:include>
	<table>
		<tr id="elementIdBeforeCombo">
			<td colspan="2"  style="height: 0px"></td>
		</tr>
		<c:if test="${stradario.id.codice==null}">
				<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="comune" value="${stradario.comune.codicecomune}" />
					<jsp:param name="mostraTutti" value="false" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="comune" />
					<jsp:param name="colspan" value="1" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>
		</c:if>
		<c:if test="${stradario.id.codice!=null}">
			<c:if test="${not empty stradario.comune.comune}">
				<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="comune" value="${stradario.comune.codicecomune}" />
					<jsp:param name="mostraTutti" value="false" />
					<jsp:param name="readOnly" value="${not canModifyComune}" />
					<jsp:param name="commandPropertyPath" value="comune" />
					<jsp:param name="colspan" value="1" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>				
			</c:if>				
		</c:if>			
		<tr>
			<td><fmt:message key="form.stradario.prefisso" /></td>
			<td><spring-form:input id="prefisso_id" path="prefisso"
				size="10" tabindex="0" /> <spring-form:errors path="prefisso"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.stradario.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione"
				size="70" tabindex="0" /> <spring-form:errors path="descrizione"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.stradario.cap" /></td>
			<td><spring-form:input id="cap_id" path="cap" size="5"
				tabindex="0" /> <spring-form:errors path="cap" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.stradario.locfraz" /></td>
			<td><spring-form:input id="locfraz_id" path="locfraz" size="50"
				tabindex="0" /> <spring-form:errors path="locfraz" cssClass="error" /></td>
		</tr>
		<c:if test="${stradario.id.codice!=null}">
		<tr>
			<td><fmt:message key="label.data_disabilitato" /></td>
			<td>
				<spring-form:input  id="datavalidita_id" path="datavalidita" size="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="calDatavalidita" idInput="datavalidita_id" textKey="label.calendar" /> 			  	
				<spring-form:errors path="datavalidita" cssClass="error" />
				<init:help idHelp="helpDatavalidita" textKey="help.data_validita_stradario"/>
			</td>
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="form.stradario.codviario" /></td>
			<td><spring-form:input id="codviario" path="codviario" size="5"
				tabindex="0" /> <spring-form:errors path="codviario"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.stradario.stradariozone" /></td>
			<td><spring-form:input size="50" id="stradariozone_id" path="stradariozone.zona" cssClass="searchbox" onchange="checkValue(this,'stradariozone_hidden')" onkeydown="javascript:return searchAll(this,event)" />
			<init:autocompleter methodAjax="findStradariozone.htm" idHidden="stradariozone_hidden" idInput="stradariozone_id" inputTitleKey="label.ricerca_stradario"/>
			<spring-form:errors path="stradariozone" cssClass="error" /> <spring-form:hidden id="stradariozone_hidden" path="stradariozone.id.codice" /></td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('prefisso_id').focus();
	</script>
</spring-form:form></div>
<div id="functions">
<c:if test="${stradario.popup eq true}">
	<c:set var="popup_prefix" value="popup"></c:set>
</c:if>
<ul>

	<c:if test="${stradario.id.codice==null}">
		<li><a
			href="javascript:doSubmit('${popup_prefix }insert.htm','',document.inviodati)"><fmt:message
			key="button.insert" /></a></li>
	</c:if>
	<c:if test="${stradario.id.codice!=null}">
		<c:if test="${not empty stradario.comune.comune}">
			<li><a
				href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message
				key="button.update" /></a></li>
			<li><a
				href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
				key="button.delete" /></a></li>
		</c:if>
	</c:if>
	
	<%--
	<c:if test="${stradario.popup ne true && stradario.id.codice!=null}">	
		<li><a href="javascript:historySet('${_urlback}','../areedettagli/list.htm?codiceStradario=${stradario.id.codice}','');"><fmt:message key="button.dettaglio" /></a></li>
	</c:if>
	 --%>
	 
	<c:if test="${stradario.popup eq false or empty stradario.popup}">
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</c:if>
	<c:if test="${stradario.popup eq true}">
		<li><a href="javascript:self.close()"><fmt:message key="button.back" /></a></li>
	</c:if>
	
	
</ul>
</div>
</body>
</html>