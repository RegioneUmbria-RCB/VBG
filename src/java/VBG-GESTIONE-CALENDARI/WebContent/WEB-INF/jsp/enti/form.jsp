<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.aggiungi-ente" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.aggiungi-ente" />
	</div>
	<div class="descrizione"></div>
	<spring-form:form commandName="entiCommand" action="aggiungi.htm" method="post" name="enteForm">
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.ente' /></td>
			<td>
				<spring-form:input path="comuniassociati.comune.comune" id="ricerca_comune" cssErrorClass="validation_error_input" />
				<spring-form:hidden path="comuniassociati.id.codicecomune" id="ricerca_comune_codice" />
				<spring-form:errors path="comuniassociati.id.codicecomune" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.import-automatico' /></td>
			<td>
				<spring-form:checkbox path="comuniassociati.importAutomatico" cssErrorClass="validation_error_input" />
				<spring-form:errors path="comuniassociati.importAutomatico" cssClass="validation_error" />
			</td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.onsite' /></td>
			<td>
				<spring-form:checkbox path="comuniassociati.onSite" cssErrorClass="validation_error_input" />
				<spring-form:errors path="comuniassociati.onSite" cssClass="validation_error" />
			</td>
		</tr>
	</table>
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">	
			<input type="button" value="<fmt:message key='button.conferma' />" id="conferma" />
			<input type="button" value="<fmt:message key='button.chiudi' />" id="annulla" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">
		$("#ricerca_comune").autocomplete({
			source: "${pageContext.request.contextPath}/ajax/getComune.htm",
			minLength: 2,
			select: function( event, ui ) {
				if(ui.item){
					$("#ricerca_comune_codice").val(ui.item.id);
				}else{
					$("#ricerca_comune_codice").val('');
					$("#ricerca_comune").val('');
				};
			},
			change: function(event, ui) {
				if(ui.item){
					$("#ricerca_comune_codice").val(ui.item.id);
				}else{
					$("#ricerca_comune_codice").val('');
					$("#ricerca_comune").val('');
				};
			}
		});
		$("#annulla").click(function(){
			window.location.replace("list.htm");
		});
		$("#conferma").click(function(){
			document.enteForm.submit();
		});
		$("#ricerca_comune").focus();
	</script>
</body>
</html>