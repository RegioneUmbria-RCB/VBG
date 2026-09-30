<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipifamiglieendo.id.codice==null}">
			<fmt:message key="tipifamiglieendo.label.nuova_tipifamigliaendo" />
		</c:if> 
		<c:if test="${tipifamiglieendo.id.codice!=null}">
			<fmt:message key="tipifamiglieendo.label.modifica_tipifamigliaendo" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipifamiglieendo.id.codice==null}">
	<fmt:message key="tipifamiglieendo.label.nuova_tipifamigliaendo" />
</c:if> 
<c:if test="${tipifamiglieendo.id.codice!=null}">
	<fmt:message key="tipifamiglieendo.label.modifica_tipifamigliaendo" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../tipifamiglieendo/view" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipifamiglieendo" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipifamiglieendo" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="tipifamiglieendo.label.tipo" /></td>
			<td><spring-form:input id="tipo_ID" path="tipo" size="70" tabindex="0" />
			<spring-form:errors path="tipo" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="label.ordine" /></td>
			<td><spring-form:input  id="ordine_ID" path="ordine" size="5" tabindex="0" cssStyle="text-align:right;" onchange="checkNumberValue(this)" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td><spring-form:textarea id="note_ID" path="note" cols="53" rows="5"  tabindex="0" />
			<spring-form:errors path="note" cssClass="error"/></td>
		</tr>
		
	</table>
	
	<script type='text/javascript'>
		$('tipo_ID').focus();
	
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
<%if(ORMHelper.isConsoleRegionale()) {%>
	<c:if test="${tipifamiglieendo.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
<%} %>	
	<c:if test="${tipifamiglieendo.id.codice!=null}">
<%if(ORMHelper.isConsoleRegionale()) {%>	
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
<%} %>		
		<li><a href="javascript:historySet('${_urlback}','../tipiendoreg/list.htm?codiceTipofamigliaEndo=${tipifamiglieendo.id.codice}','')"><fmt:message key="button.lista_categorie_endo" /></a></li>
<%if(ORMHelper.isConsoleRegionale()) {%>		
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
<%} %>		
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<%--//fabrizioc: selenium input type="hidden" id="id-codice" value="${tipifamiglieendo.id.codice}" / --%>
</body>
</html>