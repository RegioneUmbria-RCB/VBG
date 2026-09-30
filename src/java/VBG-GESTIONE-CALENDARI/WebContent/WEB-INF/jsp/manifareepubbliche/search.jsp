<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.cerca-manifestazione-aree-pubbliche" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.cerca-manifestazione-aree-pubbliche" />
	</div>
	<div class="descrizione"></div>
	<spring-form:form commandName="filter" action="search.htm" method="post" name="cercaForm">
		
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.denominazione' /></td>
			<td>
				<spring-form:input path="denominazione" cssErrorClass="validation_error_input" />
				<spring-form:errors path="denominazione" cssClass="validation_error" />
			</td>
		</tr>

		
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.tipologia' /></td>
			<td>
					<spring-form:select path="tipologia" cssErrorClass="validation_error_input">
						<spring-form:option value=""></spring-form:option>				
						<spring-form:option value="MERCATO"></spring-form:option>
						<spring-form:option value="FIERA"></spring-form:option>
					</spring-form:select>
			<%-- 	<spring-form:errors path="tipologia" cssClass="validation_error" /> --%>
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.cadenza' /></td>
			<td>
				<spring-form:select path="cadenza" cssErrorClass="validation_error_input">
						<spring-form:option value=""></spring-form:option>				
						<spring-form:option value="GIORNALIERA"></spring-form:option>
						<spring-form:option value="SETTIMANALE"></spring-form:option>
						<spring-form:option value="QUINDICINALE"></spring-form:option>
						<spring-form:option value="MENSILE"></spring-form:option>
						<spring-form:option value="TRIMESTRALE"></spring-form:option>
						<spring-form:option value="SEMESTRALE"></spring-form:option>
						<spring-form:option value="ANNUALE"></spring-form:option>
						<spring-form:option value="ESTIVO"></spring-form:option>
					</spring-form:select>
				<%-- <spring-form:errors path="cadenza" cssClass="validation_error" /> --%>
			</td>
		</tr>
		<%-- 
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.giornate' /></td>
			<td>
				<spring-form:checkbox path="gioni" value="mon" /><fmt:message key='label.mon' />&nbsp;
				<spring-form:checkbox path="gioni" value="tue" /><fmt:message key='label.tue' />&nbsp;
				<spring-form:checkbox path="gioni" value="wend" /><fmt:message key='label.wend' />&nbsp;
				<spring-form:checkbox path="gioni" value="thu" /><fmt:message key='label.thu' /><br/>
				<spring-form:checkbox path="gioni" value="frid" /><fmt:message key='label.frid' />&nbsp;
				<spring-form:checkbox path="gioni" value="sat" /><fmt:message key='label.sat' />&nbsp;
				<spring-form:checkbox path="gioni" value="sun" /><fmt:message key='label.sun' />&nbsp;
		</tr>
		--%>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.comune-svolgimento' /></td>
			<td>
				<spring-form:select path="codicecomune" cssErrorClass="validation_error_input">
					<c:if test="${fn:length(comuni) gt 1 }">
					<spring-form:option value=""></spring-form:option>
					</c:if>
					<spring-form:options items="${comuni}" itemLabel="comune.comune" itemValue="id.codicecomune" />
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