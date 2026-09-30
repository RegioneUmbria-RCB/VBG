<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
			$("#submit_cerca_altro_soggetto_pf").click(function(){
				$.blockUI();
			});
			$("#annulla_cerca_altro_soggetto_pf").click(function(){
				$.blockUI();
				window.location.replace("modificaRuolo.htm?tipo=PF");
			});
			$("#submit_cerca_altro_soggetto_pg").click(function(){
				$.blockUI();
			});
			$("#annulla_cerca_altro_soggetto_pg").click(function(){
				$.blockUI();
				window.location.replace("modificaRuolo.htm?tipo=PG");
			});
			$("#submit_utente_connesso").click(function(){
				$.blockUI();
				$("#cf_pf").val($("#cf_utente_connesso").val());
			});
		});
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }"></c:out></div>
		
	<%@ include file="../includes/alert.jsp" %>
		
	<spring-form:form action="confermaRicercaSoggetto.htm" method="post" commandName="nuovaIstanzaCommand" id="confermaRicercaSoggetto">
	<input type="hidden" name="tipo" value="${tipo }" />
	<input type="hidden" id="cf_utente_connesso" value='<spring-security:authentication property="principal.cf" />' />
	<div class="titolo_sezione"><fmt:message key="label.nuovo-soggetto" />&nbsp;
	<c:choose>
			<c:when test="${tipo eq 'PF' }">
				(<fmt:message key="label.persona-fisica" />)
			</c:when>
			<c:otherwise>
				(<fmt:message key="label.persona-giuridica" />)
			</c:otherwise>
	</c:choose>
	</div>
	<div id="scelta_tipo_soggetto" class="sezione">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><fmt:message key="label.ruolo" /></td>
				<td>				
				<c:choose>
				<c:when test="${tipo eq 'PF' }">
				${nuovaIstanzaCommand.altroSoggettoPFH.soggetto.tipoRapporto.ruolo}
				</c:when>
				<c:otherwise>
				${nuovaIstanzaCommand.altroSoggettoPGH.soggetto.tipoRapporto.ruolo}
				</c:otherwise>
				</c:choose>			
				</td>
			</tr>
		</table>	
	</div>
	<c:choose>
	<c:when test="${tipo eq 'PF' }">
		<table class="sezione_table">
		<tr>
		<td class="sezione_table_label"><fmt:message key="label.codice-fiscale" /></td>
		<td>
		<spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.codiceFiscale" id="cf_pf" size="20" maxlength="16" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_buttons">
		<input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_altro_soggetto_pf" />
		<spring-security:authorize ifAllGranted="ROLE_USER">
		<input type="submit" value="<fmt:message key='button.utente-connesso' />" id="submit_utente_connesso" />
		</spring-security:authorize>
		<input type="button" value="<fmt:message key='button.annulla' />" id="annulla_cerca_altro_soggetto_pf" />
		</td>
		</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table class="sezione_table"><tr>
		<td class="sezione_table_label"><fmt:message key="label.partita-iva" /></td>
		<td>
		<spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.partitaIva" size="20" maxlength="11" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_label"><fmt:message key="label.codice-fiscale" /></td>
		<td>
		<spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.codiceFiscale" size="20" maxlength="16" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_buttons">
		<input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_altro_soggetto_pg" />
		<input type="button" value="<fmt:message key='button.annulla' />" id="annulla_cerca_altro_soggetto_pg" />
		</td>
		</tr></table>
	</c:otherwise>
	</c:choose>	
	</spring-form:form>
	
</body>
</html>