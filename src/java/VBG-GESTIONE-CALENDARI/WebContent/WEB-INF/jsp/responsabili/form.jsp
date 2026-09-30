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
		<fmt:message key="label.responsabile" />
	</div>
	<div class="descrizione"></div>
	<spring-form:form action="aggiungi.htm" method="post" commandName="responsabiliCommand" name="respForm">
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
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.gestione-feste-sagre' /></td>
			<td><spring-form:checkbox path="responsabili.gestioneFesteSagre" cssErrorClass="validation_error_input" /></td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.gestione-fiere-mostre' /></td>
			<td><spring-form:checkbox path="responsabili.gestioneFiereMostre" cssErrorClass="validation_error_input" /></td>
		</tr>
		<%-- COMMENTATO PERCHè NON FACEVA PARTE ANCORA DELL'ORDINE --%>				         
		<%--
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.gestione-manifestazioni-areepubbliche' /></td>
			<td><spring-form:checkbox path="responsabili.gestioneAreepubbliche" cssErrorClass="validation_error_input" /></td>
		</tr>
		<tr>
			<td class="sezione_table_label"><fmt:message key='label.gestione-inserimento-anagrafiche' /></td>
			<td><spring-form:checkbox path="responsabili.gestioneInserimentoAnagrafiche" cssErrorClass="validation_error_input" /></td>
		</tr>
		--%>
	</table>
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">	
			<input type="button" value="<fmt:message key='button.conferma' />" id="conferma" />
			<input type="button" value="<fmt:message key='button.chiudi' />" id="chiudi" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<c:if test="${responsabiliCommand.responsabili.id.codice != null }">
	<div class="titolo_sottosezione">
		<fmt:message key="label.aggiungi-ente" />
	</div>
	<form action="aggiungiComunePerResponsabile.htm" method="post" id="aggiungiComunePerResponsabileFormId">
		<input type="text" name="comune" size="40" id="ricerca_comune" />
		<input type="hidden" name="codiceresp" value="${responsabiliCommand.responsabili.id.codice }" />
		<input type="hidden" name="codicecomune" id="ricerca_comune_codice" />
		<input type="submit" value="Aggiungi" />
	</form>
	<br />
	<div class="titolo_sottosezione">
		<fmt:message key="label.enti" />
	</div>
	<form name="entiPerResponsabileForm" action="view.htm">
		<jmesa:springTableModel id="tag" items="${responsabiliCommand.responsabili.responsabilicomunis}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.nome-ente" property="comune.comune" filterable="false" sortable="false" />
					<jmesa:htmlColumn filterable="false" sortable="false" width="20%">
    					<a class="table_button" href="javascript:eliminaComunePerResponsabile('${bean.id.codicecomune }')" title="<fmt:message key='label.elimina' />"><fmt:message key='button.elimina' /></a>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
		<input type="hidden" name="codiceresp" value="${responsabiliCommand.responsabili.id.codice }"/>
	</form>
	<br />
	<button id="aggiungi_tutti_i_comuni"><fmt:message key='label.aggiungi-tutti' /></button>
	</c:if>
	<script type="text/javascript">
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function eliminaComunePerResponsabile(codicecomune){		
			document.location.href="${pageContext.request.contextPath}/responsabili/eliminaComunePerResponsabile.htm?codiceresp=${responsabiliCommand.responsabili.id.codice }&codicecomune="+codicecomune;
		}
		$("#chiudi").click(function(){
			window.location.replace("list.htm");
		});
		$("#conferma").click(function(){
			document.respForm.submit();
		});
		$("#ricerca_comune").autocomplete({
			source: "${pageContext.request.contextPath}/ajax/getComuneassociato.htm",
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
		$("#aggiungiComunePerResponsabileFormId").submit(function () {
		    var codicecomune = $('#ricerca_comune_codice').val();
		    if (codicecomune  === '') {
		        alert('Attenzione! Specificare il comune.');
		        return false;
		    }
		});
		$("#aggiungi_tutti_i_comuni").click(function(){
			window.location.replace("aggiungiTuttiIComuniPerResponsabile.htm?codiceresp=${responsabiliCommand.responsabili.id.codice }");
		});
	</script>
</body>
</html>