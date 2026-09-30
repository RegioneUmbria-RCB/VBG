<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
		<fmt:message key="label.dehorscfg.title" />
</title>
</head>
	<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.dehorscfg.title" />
	</span>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../dehorscfg/view" />
	</jsp:include>
	
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent"><spring-form:form commandName="dehorscfg" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="dehorscfg" />
		</jsp:include>
		<table>
				<tr>			
					<td>
						<fmt:message key="label.tipologia_registri" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipologiaregistri" />					
							<jsp:param name="propertyPath" value="tipologiaregistri" />	
							<jsp:param name="pathPropertyDescription" value="tipologiaregistri.trDescrizione" />
							<jsp:param name="pathPropertyCode" value="tipologiaregistri.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipologiaRegistri.htm" />
							<jsp:param name="titleKey" value="label.ricerca_tipologiaregistro" />
						</jsp:include>
						<init:help idHelp="help_tipologia_registri" textKey="dyn2modellid.help.tipologia.registri"/>
					</td>
				</tr>
				<tr>			
					<td>
						<fmt:message key="label.concessione_causale_cessazione" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="concessionicausali" />					
							<jsp:param name="propertyPath" value="concessionicausali" />	
							<jsp:param name="pathPropertyDescription" value="concessionicausali.descrizione" />
							<jsp:param name="pathPropertyCode" value="concessionicausali.id.codice" />
							<jsp:param name="autocompleterAjax" value="findConcessioniCausali.htm?flagStorico=true" />
							<jsp:param name="titleKey" value="label.ricerca_concessionecausale" />
						</jsp:include>
						<init:help idHelp="help_concessione_causale_cessazione" textKey="dyn2modellid.help.concessione.causale.cessazione"/>
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
		<c:if test="${dehorscfg.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${dehorscfg.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>