<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.altre_aree" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.altre_aree" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanze.id.codice}</c:param>
	</c:import>
 	<br class="clear" />
		<spring-form:form commandName="istanzearee" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzearee" />
		    </jsp:include>
			<table>
				
				<tr>
					<td><fmt:message key="label.aree"/></td>
					<td colspan="5">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="aree_id" />
							<jsp:param name="propertyPath" value="area" />										
							<jsp:param name="pathPropertyDescription" value="area.denominazione" />
							<jsp:param name="pathPropertyCode" value="area.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAreeNoninserite.htm?codiceIstanza=${istanze.id.codice}" />							
							<jsp:param name="titleKey" value="label.ricerca_aree" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.primario" />
					</td>
					<td>
						<spring-form:checkbox id="primario_id" path="primario"/>
						<spring-form:errors path="primario" cssClass="error"/>
					</td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>