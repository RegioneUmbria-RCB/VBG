<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.cerca-manifestazione" /></title>
</head>
<body>
	<div class="titolo">
		<c:if test="${filter.tipoManifestazione eq 'FM' }">
		<fmt:message key="label.cerca-fiere-mostre" />
		</c:if>
		<c:if test="${filter.tipoManifestazione eq 'FS' }">
		<fmt:message key="label.cerca-feste-sagre" />
		</c:if>
	</div>
	<div class="descrizione"></div>
	<spring-form:form commandName="filter" action="search.htm" method="post" name="cercaForm">
		<spring-form:hidden path="tipoManifestazione" />
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.denominazione' /></td>
			<td>
				<spring-form:input path="denominazione" cssErrorClass="validation_error_input" />
				<spring-form:errors path="denominazione" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.dal' /></td>
			<td>
				<spring-form:input path="dal" cssErrorClass="validation_error_input" maxlength="10" size="10" id="dal" />
				<spring-form:errors path="dal" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.al' /></td>
			<td>
				<spring-form:input path="al" cssErrorClass="validation_error_input" maxlength="10" size="10" id="al" />
				<spring-form:errors path="al" cssClass="validation_error" />
			</td>
		</tr>
		<c:if test="${filter.tipoManifestazione eq 'FS' }">
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.tipologia' /></td>
			<td>
				<spring-form:select path="tipologia" cssErrorClass="validation_error_input">
						<spring-form:option value=""></spring-form:option>				
						<spring-form:option value="SAGRA DELL'UMBRIA"></spring-form:option>
						<spring-form:option value="FESTA POPOLARE"></spring-form:option>
					</spring-form:select>
				<spring-form:errors path="tipologia" cssClass="validation_error" />
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.luogo-svolgimento' /></td>
			<td>
				<spring-form:input path="luogoSvolgimento" cssErrorClass="validation_error_input" />
				<spring-form:errors path="luogoSvolgimento" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.organizzatore' /></td>
			<td>
				<spring-form:input path="organizzatore" cssErrorClass="validation_error_input" />
				<spring-form:errors path="organizzatore" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.comune-svolgimento' /></td>
			<td>
				<spring-form:select path="codicecomune" cssErrorClass="validation_error_input">
					<c:if test="${fn:length(comuni) gt 1 }">
					<spring-form:option value=""></spring-form:option>
					</c:if>
					<spring-form:options items="${comuni }" itemLabel="comune.comune" itemValue="id.codicecomune" />
				</spring-form:select>
				<spring-form:errors path="codicecomune" cssClass="validation_error" />
			</td>
		</tr>
	</table>
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.cerca' />" id="cerca" />
			<input type="button" value="<fmt:message key='button.chiudi' />" id="chiudi" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">
		$("#dal").datepicker();	
		$("#al").datepicker();
		$("#cerca").click(function(){
			document.cercaForm.submit();
		});
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/home/start.htm");
		});
	</script>
</body>
</html>