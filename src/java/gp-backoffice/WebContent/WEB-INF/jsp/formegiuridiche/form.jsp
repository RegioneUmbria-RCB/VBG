<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${formegiuridiche.id.codice==null}">
		<fmt:message key="formegiuridiche.label.nuovo_formegiuridiche.title" />
	</c:if> <c:if test="${formegiuridiche.id.codice!=null}">
		<fmt:message
			key="formegiuridiche.label.dettaglio_formegiuridiche.title" />
	</c:if></title>
</head>
<body>
	<span class="titoloPagina"> <c:if
			test="${formegiuridiche.id.codice==null}">
			<fmt:message key="formegiuridiche.label.nuovo_formegiuridiche.title" />
		</c:if> <c:if test="${formegiuridiche.id.codice!=null}">
			<fmt:message
				key="formegiuridiche.label.dettaglio_formegiuridiche.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="formegiuridiche" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="formegiuridiche" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="formegiuridiche.label.formagiuridica" /></td>
					<td><spring-form:input id="formagiuridica_id"
							path="formagiuridica" size="30" /> <spring-form:errors
							path="formagiuridica" cssClass="error" /></td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message
							key="label.dati_registro_imprese.legend" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.dati_registro_imprese.formagiuridica" />
					</td>
					<td><jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="riFormegiuridiche_id" />
							<jsp:param name="propertyPath" value="riFormegiuridiche" />
							<jsp:param name="pathPropertyDescription"
								value="riFormegiuridiche.descrizione" />
							<jsp:param name="pathPropertyCode" value="riFormegiuridiche.codice" />
							<jsp:param name="autocompleterAjax" value="findRiFormegiuridiche.htm" />
							<jsp:param name="titleKey"
								value="label.ricerca_dati_registro_imprese.formagiuridica" />
						</jsp:include></td>
				</tr>				
			</table>

			<script type='text/javascript'>
				$('formagiuridica_id').focus();
			</script>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${formegiuridiche.id.codice==null}">
				<li><a
					href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
							key="button.insert" /></a></li>
			</c:if>
			<c:if test="${formegiuridiche.id.codice!=null}">
				<li><a
					href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message
							key="button.update" /></a></li>
				<li><a
					href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
							key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>
