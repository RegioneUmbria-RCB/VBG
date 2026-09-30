<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
		<fmt:message key="label.inventarioprocsoggfirmatari.title" />
</title>
</head>
	<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.inventarioprocsoggfirmatari.title" />
	</span>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../inventarioprocsoggfirmatari/view" />
	</jsp:include>
	
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent"><spring-form:form commandName="inventarioprocsoggfirmatari" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="inventarioprocsoggfirmatari" />
		</jsp:include>
		<table>
				<tr>			
					<td>
						<fmt:message key="label.tipo_soggetto" />
					</td>
					<td class="inline-ui-cell">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipisoggetto" />					
							<jsp:param name="propertyPath" value="tipisoggetto" />	
							<jsp:param name="pathPropertyDescription" value="tipisoggetto.tiposoggetto" />
							<jsp:param name="pathPropertyCode" value="tipisoggetto.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipisoggetto.htm" />
							<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
						</jsp:include>
					</td>
				</tr>
		</table>
	<script type='text/javascript'>
	//$('denominazione_id').focus();
</script>
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<c:if test="${inventarioprocsoggfirmatari.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${inventarioprocsoggfirmatari.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('list.htm?codicedocumento=${inventarioprocsoggfirmatari.documenti.id.codice}','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>