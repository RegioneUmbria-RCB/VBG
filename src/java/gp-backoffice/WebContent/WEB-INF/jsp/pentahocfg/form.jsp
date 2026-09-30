<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.nuova_configurazione_pentho.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.nuova_configurazione_pentho.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<br />
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="pentahocfg" />
	</jsp:include>
	<spring-form:form commandName="pentahocfg" name="inviodati">
	<table>
		<tr>
			<td>
				<fmt:message key="label.modello" />
			</td>
			<td>
			<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="mailtipo" />		
					<jsp:param name="propertyPath" value="entity.mailtipo" />				
					<jsp:param name="pathPropertyDescription" value="entity.mailtipo.descrizione" />
					<jsp:param name="pathPropertyCode" value="entity.mailtipo.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />	
					<jsp:param name="titleKey" value="label.ricerca_mail_tipo" />
					<jsp:param name="id_help" value="help_mailtipo" /> 
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.id_ente_base" /></td>
			<td><spring-form:input path="entity.identebase" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.userid" /></td>
			<td><spring-form:input path="entity.userid" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.password" /></td>
			<td><spring-form:password path="entity.password" /></td>
		</tr>
	</table>
</spring-form:form>
</div>

<div id="functions">
<ul>
	<c:if test="${pentahocfg.displayMode==pentahocfg.displayConstants.NEW}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${pentahocfg.displayMode==pentahocfg.displayConstants.VIEW}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>