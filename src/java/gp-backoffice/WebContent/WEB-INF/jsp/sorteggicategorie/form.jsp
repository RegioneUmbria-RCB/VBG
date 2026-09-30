<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${empty sorteggiCategorie.id.codice}">
	<fmt:message key="form.sorteggicategorie.title.create" />
</c:if> <c:if test="${not empty sorteggiCategorie.id.codice}">
	<fmt:message key="form.sorteggicategorie.title.view" />
</c:if></title>
</head>
<body>

<span class="titoloPagina">
<c:if test="${empty sorteggiCategorie.id.codice}">
	<fmt:message key="form.sorteggicategorie.title.create" />
</c:if> 
<c:if test="${not empty sorteggiCategorie.id.codice}">
	<fmt:message key="form.sorteggicategorie.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>	
<div id="subcontent">
<spring-form:form commandName="sorteggiCategorie" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="sorteggiCategorie" />
	</jsp:include>
	<table>
		<c:if test="${not empty sorteggiCategorie.id.codice}">
		<tr>
			<td><fmt:message key="form.sorteggicategorie.codice" /></td>      
			<td>${sorteggiCategorie.id.codice}</td>	
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="form.sorteggicategorie.descrizione" /></td>       
			<td><spring-form:input id="descrizione_id" path="descrizione" size="100" tabindex="0" />&nbsp;<spring-form:errors path="descrizione" cssClass="error" /></td>		 
		</tr>
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${empty sorteggiCategorie.id.codice}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${not empty sorteggiCategorie.id.codice}">
		<li><a href="javascript:doSubmit('update.htm','')"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<c:if test="${not empty sorteggiCategorie.id.codice}">
<br/>
<span class="titoloTabella">
	<fmt:message key="form.sorteggicategorie.title.listsorteggi" />
</span>
<form name="sorteggiForm" action="view.htm">
	<jmesa:springTableFacade
		id="sorteggi_id" 
		items="${sorteggiCategorie.sorteggiList}" 
		var="sorteggi_var"
		exportTypes="pdfp,excel,csv" 
		stateAttr="restore" >
		<jmesa:htmlTable>
			<jmesa:htmlRow>							
				<jmesa:htmlColumn property="stDescrizione" titleKey="form.sorteggi.descrizione" />
				<jmesa:htmlColumn property="stDatasorteggio" titleKey="form.sorteggi.data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
			</jmesa:htmlRow>
		</jmesa:htmlTable>
	</jmesa:springTableFacade>
	<input type="hidden" value="${sorteggiCategorie.id.codice}" name="codice"/>
</form>
<script type="text/javascript">
	<c:if test="${empty sorteggiCategorie.id.codice}">
	$('descrizione_id').focus();
	</c:if>
	var _jmesaUrl='../sorteggicategorie/view.htm?codice=${sorteggiCategorie.id.codice}&';
	var _captionTab='<fmt:message key="form.sorteggicategorie.title.listsorteggi" />';
</script>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('viewSorteggi.htm','')"><fmt:message key="button.viewSorteggi" /></a></li>
</ul>
</div>
</c:if>	
</body>
</html>