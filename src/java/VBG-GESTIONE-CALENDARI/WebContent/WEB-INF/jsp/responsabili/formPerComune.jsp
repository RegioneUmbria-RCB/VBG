<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.responsabile" /></title>
</head>
<body>
	<div class="titolo">
		${responsabiliCommand.comuni.comune } 
	</div>
	<div class="descrizione"><fmt:message key="label.responsabile" /></div>
	<spring-form:form action="aggiungiPerComune.htm" method="post" commandName="responsabiliCommand" name="respForm">
	<input type="hidden" name="codicecomune" value="${responsabiliCommand.comuni.codicecomune }"/>
	<spring-form:hidden path="comuni.codicecomune" />
	<spring-form:hidden path="responsabili.id.codice" />
	<spring-form:hidden path="responsabili.amministratore" />
	<table class="sezione_table">
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.nome' /></td>
			<td>
				<spring-form:input path="responsabili.responsabile" cssErrorClass="validation_error_input" />
				<spring-form:errors path="responsabili.responsabile" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.userid' /></td>
			<td>
				<spring-form:input path="responsabili.userid" cssErrorClass="validation_error_input" />
				<spring-form:errors path="responsabili.userid" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.readonly' /></td>
			<td><spring-form:checkbox path="responsabili.readonly" cssErrorClass="validation_error_input" /></td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.disabilitato' /></td>
			<td><spring-form:checkbox path="responsabili.disabilitato" cssErrorClass="validation_error_input" /></td>
		</tr>
	</table>
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.conferma' />" id="conferma" />
			<input type="button" value="<fmt:message key='button.annulla' />" id="annulla" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">
		$("#annulla").click(function(){
			window.location.replace("listPerComune.htm?codicecomune=${responsabiliCommand.comuni.codicecomune}");
		});
		$("#conferma").click(function(){
			document.respForm.submit();
		});
	</script>
</body>
</html>