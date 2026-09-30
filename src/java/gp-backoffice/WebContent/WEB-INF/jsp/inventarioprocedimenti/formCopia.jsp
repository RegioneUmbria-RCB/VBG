<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="inventarioprocedimenti.label.nuova_copia_endo.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
	   <fmt:message key="inventarioprocedimenti.label.nuova_copia_endo.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.endo_incompatibile" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="endo_id" path="entity.procedimento" cssClass="searchbox" size="60" onchange="checkValue(this,'endo_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
						<init:autocompleter methodAjax='findInventarioprocedimentoAndSoftware.htm?codicesoftware=${inventarioprocedimenti.entity.software.codice}'  idHidden="endo_hidden"  idInput="endo_id" inputTitleKey="label.ricerca_endoprocedimento"></init:autocompleter>
						<spring-form:errors path="entity.procedimento" cssClass="error"/> 
						<spring-form:hidden id="endo_hidden" path="entity.id.codice"  />
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertCopia.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('create.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>