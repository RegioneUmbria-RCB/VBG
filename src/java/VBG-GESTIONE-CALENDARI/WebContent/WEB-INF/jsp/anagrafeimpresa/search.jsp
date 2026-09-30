<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.cerca-imprese-aree-pubbliche" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.cerca-imprese-aree-pubbliche" />
	</div>
	<div class="descrizione"></div>
	<spring-form:form commandName="filter" action="search.htm" method="post" name="cercaForm">
		
		<table class="sezione_table">
		
		
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.codicefiscale' /></td>
			<td>
				<spring-form:input path="codicefiscale" cssErrorClass="validation_error_input" />
				<spring-form:errors path="codicefiscale" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.cognome' /></td>
			<td>
				<spring-form:input path="cognome" cssErrorClass="validation_error_input" />
				<spring-form:errors path="cognome" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.denominazione' /></td>
			<td>
				<spring-form:input path="denominazione" cssErrorClass="validation_error_input" />
				<spring-form:errors path="denominazione" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.cf_pi' /></td>
			<td>
				<spring-form:input path="cfPi" cssErrorClass="validation_error_input" />
				<spring-form:errors path="cfPi" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.numero-registro-imprese' /></td>
			<td>
				<spring-form:input path="numeroRegImprese" cssErrorClass="validation_error_input" />
				<spring-form:errors path="numeroRegImprese" cssClass="validation_error" />
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
		//$("#dal").datepicker();	
		//$("#al").datepicker();
		$("#cerca").click(function(){
			document.cercaForm.submit();
		});
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/home/start.htm");
		});
	</script>
</body>
</html>