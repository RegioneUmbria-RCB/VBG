<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function(){
			$( "#loc_indirizzo" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getLocalizzazione.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#loc_indirizzo_id").val(ui.item.id);
						$("#loc_indirizzo_viario").val(ui.item.viario);
					}else{
						$("#loc_indirizzo").val('');
						$("#loc_indirizzo_id").val('');
						$("#loc_indirizzo_viario").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#loc_indirizzo_id").val(ui.item.id);
						$("#loc_indirizzo_viario").val(ui.item.viario);
					}else{
						$("#loc_indirizzo").val('');
						$("#loc_indirizzo_id").val('');
						$("#loc_indirizzo_viario").val('');
					};
				}
			});	
			$("#aggiungi_catasto").click(function(){
				$("#azioneId").val("CONFIRM_CA");
			});
		});
		function setHiddenCatastoDaEliminare(id){
			$("#azioneId").val("DELETE_CA");
			$("#hidden_catasto_da_eliminare_id").val(id);
		}
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
		<input type="hidden" name="azione" id="azioneId" value="" />
		<div class="titolo_sezione"><fmt:message key='label.indirizzo' /></div>
		<div id="sez_indirizzo" class="sezione">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.denominazione' /></label></td>
				<td>
					<spring-form:input path="localizzazione.denominazione" size="40" id="loc_indirizzo" cssErrorClass="validation_error_input" />
					<spring-form:hidden path="localizzazione.id" id="loc_indirizzo_id" />
					<spring-form:hidden path="localizzazione.codiceViario" id="loc_indirizzo_viario" />
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.civico' /></label></td>
				<td>
					<spring-form:input path="localizzazione.civico" size="10" cssErrorClass="validation_error_input" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.esponente' /></label></td>
				<td>
					<spring-form:input path="localizzazione.esponente" size="10" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.scala' /></label></td>
				<td>
					<spring-form:input path="localizzazione.scala" size="10" cssErrorClass="validation_error_input" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.interno' /></label></td>
				<td>
					<spring-form:input path="localizzazione.interno" size="10" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.esponente-interno' /></label></td>
				<td>
					<spring-form:input path="localizzazione.esponenteInterno" size="10" cssErrorClass="validation_error_input" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.piano' /></label></td>
				<td>
					<spring-form:input path="localizzazione.piano" size="10" cssErrorClass="validation_error_input" />
				</td>	
				<c:if test="${isColore eq true }">
				<td class="sezione_table_label"><label><fmt:message key='label.colore' /></label></td>
				<td>	
					<spring-form:select path="localizzazione.colore" cssErrorClass="validation_error_input">
						<spring-form:option value=""></spring-form:option>
					    <spring-form:options items="${colori }" itemValue="id.codicecolore" itemLabel="colore" />
					</spring-form:select>
				</td>
				</c:if>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.frazione' /></label></td>
				<td>
					<spring-form:input path="localizzazione.frazione.descrizione" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.quartiere' /></label></td>
				<td>
					<spring-form:input path="localizzazione.quartiere.descrizione" cssErrorClass="validation_error_input" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.circoscrizione' /></label></td>
				<td colspan="3">
					<spring-form:input path="localizzazione.circoscrizione.descrizione" cssErrorClass="validation_error_input" />
				</td>
			</tr>
		</table>
		</div>
		<div class="titolo_sezione"><fmt:message key='label.riferimenti-catastali' /></div>
		
		<c:if test="${not empty nuovaIstanzaCommand.riferimentiCatastali }">
		<input type="hidden" value="" name="hidden_catasto_da_eliminare" id="hidden_catasto_da_eliminare_id" />
		<div id="sez_catasto_list" class="sezione">
		<table class="sezione_table">
		<c:forEach items="${nuovaIstanzaCommand.riferimentiCatastali }" var="currentCA">	
			<tr>
			<td class="sezione_table_label"><fmt:message key='label.tipo-catasto' /></td><td>${currentCA.value.tipoCatasto}</td>
			<td class="sezione_table_label"><fmt:message key='label.foglio' /></td><td>${currentCA.value.foglio}</td>
			<td class="sezione_table_label"><fmt:message key='label.particella' /></td><td>${currentCA.value.particella}</td>
			<td class="sezione_table_label"><fmt:message key='label.sub' /></td><td>${currentCA.value.sub}</td>
			<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.elimina' />" onclick="setHiddenCatastoDaEliminare('${currentCA.key}')" /></td>
			</tr>
		</c:forEach>
		</table>
		</div>
		</c:if>
		<div id="sez_catasto" class="sezione">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.tipo-catasto' /></label></td>
				<td>
					<spring-form:select path="catasto.tipoCatasto" cssErrorClass="validation_error_input">
						<option value="" selected="selected"></option>
						<spring-form:options items="${catastoList}" itemValue="descrizione" itemLabel="descrizione" />
					</spring-form:select>
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.foglio' /></label></td>
				<td>
					<spring-form:input path="catasto.foglio" size="10" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.particella' /></label></td>
				<td>
					<spring-form:input path="catasto.particella" size="10" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_label"><label><fmt:message key='label.sub' /></label></td>
				<td>
					<spring-form:input path="catasto.sub" size="10" />
				</td>
				<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.aggiungi' />" id="aggiungi_catasto" /></td>
			</tr>
		</table>
		</div>
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>