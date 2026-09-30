<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipibando.id.codice==null}">
			<fmt:message key="form.tipibando.title.create" />
		</c:if> 
		<c:if test="${tipibando.id.codice!=null}">
			<fmt:message key="form.tipibando.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
	<c:if test="${tipibando.id.codice==null}">
		<fmt:message key="form.tipibando.title.create" />
	</c:if> 
	<c:if test="${tipibando.id.codice!=null}">
		<fmt:message key="form.tipibando.title.view" />
	</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
        <jsp:param name="commandName" value="tipibando" />
    </jsp:include>
	<spring-form:form commandName="tipibando" name="inviodati">
	<table>
		<tr>
			<td><fmt:message key="form.tipibando.descrizione" /></td>
			<td>
			<spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/>
			</td>
		</tr>
        <tr>
			<td><fmt:message key="form.tipibando.modelli" /></td>
			<td>
				<spring-form:input id="dyn2Modellit_id" path="dyn2Modellit.descrizione" size="55" cssClass="searchbox" onchange="checkValue(this,'dyn2Modellit_hidden')" onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findAllDyn2Modelli.htm" idHidden="dyn2Modellit_hidden" idInput="dyn2Modellit_id" inputTitleKey="label.ricerca_modello"/>
				<spring-form:hidden id="dyn2Modellit_hidden" path="dyn2Modellit.id.codice"/>
				<spring-form:errors path="dyn2Modellit" cssClass="error"/>
			</td>
		</tr>
        <tr>
			<td><fmt:message key="form.tipibando.attivo" /></td>
			<td><spring-form:checkbox path="attivo" value="1" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipibando.flagMultiintervento" /></td>
			<td><spring-form:checkbox path="flagMultiintervento" value="1" />
			 <init:help idHelp="help_flagMultiintervento_id" textKey="label.flagMultiintervento.help" />
			 </td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipibando.flagBolkestein" /></td>
			<td><spring-form:checkbox path="flagBolkestein" />
			 <init:help idHelp="help_flagBolkestein_id" textKey="label.flagBolkestein.help" />
			 </td>
		</tr>		
	</table>
	<script type='text/javascript'>
		$('descrizione_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipibando.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipibando.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
        <li><a href="javascript:doSubmit('../tipigraduatoriet/list.htm?tipibando.id.codice=${tipibando.id.codice}','',document.inviodati)"><fmt:message key="button.tipibando.tipigraduatoria" /></a></li>
        <li><a href="javascript:doSubmit('../tipibandoinput/list.htm?tipibando.id.codice=${tipibando.id.codice}','',document.inviodati)"><fmt:message key="button.tipibando.campiinput" /></a></li>        
	</c:if>
       <li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
    </ul>
</div>
</body>
</html>
