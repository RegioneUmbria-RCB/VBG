<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${stradariocolore.displayMode==stradariocolore.displayConstants.NEW }">
			<fmt:message key="stradariocolore.label.nuovo_stradariocolore.title" />
		</c:if> 
		<c:if test="${stradariocolore.displayMode==stradariocolore.displayConstants.VIEW}">
			<fmt:message key="stradariocolore.label.dettaglio_stradariocolore.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${stradariocolore.displayMode==stradariocolore.displayConstants.NEW }">
			<fmt:message key="stradariocolore.label.nuovo_stradariocolore.title" />
		</c:if> 
		<c:if test="${stradariocolore.displayMode==stradariocolore.displayConstants.VIEW}">
			<fmt:message key="stradariocolore.label.dettaglio_stradariocolore.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="stradariocolore" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="stradariocolore" />
		    </jsp:include>
			<table>
			<tr>
					<td>
						<fmt:message key="label.codice" />
					</td>
					<c:if test="${stradariocolore.displayMode==stradariocolore.displayConstants.NEW}">
					<td class="inline-ui-cell">
						<spring-form:input id="codice_id" path="entity.id.codicecolore" size="5" />
						<spring-form:errors path="entity.id.codicecolore" cssClass="error"/>
					</td>
					</c:if>
					<c:if test="${stradariocolore.displayMode==stradariocolore.displayConstants.VIEW}">
					<td class="inline-ui-cell">
						<spring-form:input id="codice_id" path="entity.id.codicecolore" size="5" readonly="true"/>
						<init:help idHelp="help1" textKey="help.codice"/>
					</td>
					</c:if>
				</tr>
				<tr>
					<td>
						<fmt:message key="stradariocolore.label.colore" />
					</td>
					<td>
						<spring-form:input id="colore_id" path="entity.colore" size="40" />
						<spring-form:errors path="entity.colore" cssClass="error"/>
					</td>
				</tr>
				
			</table>
			<script type='text/javascript'>
				$('codice_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${ stradariocolore.displayMode==stradariocolore.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${ stradariocolore.displayMode==stradariocolore.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>