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
			$("#altro_soggetto_pf_dataNascita" ).datepicker();			
			$("#altro_sogg_iscrizioneCCIAA_data" ).datepicker();
			$("#altro_sogg_iscrizioneREA_data" ).datepicker();
			
			$("#altro_sogg_pf_comune_nascita" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_pf_comune_nascita_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_pf_comune_nascita_codice").val('');
						$("#altro_sogg_pf_comune_nascita").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_pf_comune_nascita_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_pf_comune_nascita_codice").val('');
						$("#altro_sogg_pf_comune_nascita").val('');
					};
				}
			});
			$("#altro_sogg_pf_residenza_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_pf_residenza_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_pf_residenza_comune_codice").val('');
						$("#altro_sogg_pf_residenza_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_pf_residenza_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_pf_residenza_comune_codice").val('');
						$("#altro_sogg_pf_residenza_comune").val('');
					};
				}
			});
			$("#altro_sogg_cittadinanza" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getCittadinanza.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_cittadinanza_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_cittadinanza_codice").val('');
						$("#altro_sogg_cittadinanza").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_cittadinanza_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_cittadinanza_codice").val('');
						$("#altro_sogg_cittadinanza").val('');
					};
				}
			});
			
			$("#altro_sogg_titolo" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getTitolo.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_titolo").val(ui.item.value);
					}else{
						$("#altro_sogg_titolo").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_titolo").val(ui.item.value);
					}else{
						$("#altro_sogg_titolo").val('');
					};
				}
			});
			
			
			$("#altro_sogg_sedelegale_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_sedelegale_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_sedelegale_comune_codice").val('');
						$("#altro_sogg_sedelegale_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_sedelegale_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_sedelegale_comune_codice").val('');
						$("#altro_sogg_sedelegale_comune").val('');
					};
				}
			});
			$("#altro_sogg_indcorr_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_indcorr_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_indcorr_comune_codice").val('');
						$("#altro_sogg_indcorr_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_indcorr_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_indcorr_comune_codice").val('');
						$("#altro_sogg_indcorr_comune").val('');
					};
				}
			});
			$("#altro_sogg_cciaa_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_cciaa_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_cciaa_comune_codice").val('');
						$("#altro_sogg_cciaa_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_cciaa_comune_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_cciaa_comune_codice").val('');
						$("#altro_sogg_cciaa_comune").val('');
					};
				}
			});
			$("#altro_sogg_albo" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getOrdineProfessionisti.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#altro_sogg_albo_codice").val(ui.item.id);
					}else{
						$("#altro_sogg_albo_codice").val('');
						$("#altro_sogg_albo").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#altro_sogg_albo_codice").val(ui.item.id);					
					}else{
						$("#altro_sogg_albo_codice").val('');
						$("#altro_sogg_albo").val('');
					};
				}
			});
			<c:choose>
			<c:when test="${not empty idSoggModifica}">
			$("#annulla_conferma_altro_soggetto_pf").click(function(){
				$.blockUI();
				window.location.replace("view.htm");
			});
			$("#annulla_conferma_altro_soggetto_pg").click(function(){
				$.blockUI();
				window.location.replace("view.htm");
			});
			</c:when>
			<c:otherwise>
			$("#annulla_conferma_altro_soggetto_pf").click(function(){
				$.blockUI();
				window.location.replace("ricercaSoggetto.htm?tipo=PF");
			});
			$("#annulla_conferma_altro_soggetto_pg").click(function(){
				$.blockUI();
				window.location.replace("ricercaSoggetto.htm?tipo=PG");
			});
			</c:otherwise>
			</c:choose>
			
			function setAnagrafeDiBaseReadonly(){
				var cf_utente = $("#altro_sogg_pf_cf").val();
				var cf_utente_connesso = $("#cf_utente_connesso").val();
				if(cf_utente){
					if(cf_utente.toLowerCase() === cf_utente_connesso.toLowerCase()){
						$("#pf_cognome").attr('readonly', true);
						$("#pf_nome").attr('readonly', true);
						$('#pf_sesso option:not(:selected)').attr('disabled', true);
						$("#altro_soggetto_pf_dataNascita").attr('readonly', true);
						$("#altro_soggetto_pf_dataNascita").datepicker("disable");			
						$("#altro_sogg_pf_comune_nascita").attr('readonly', true);	
					}
				}
			}
			setAnagrafeDiBaseReadonly();
			
			$("#submit_conferma_altro_soggetto_pf").click(function(){
				controllaCF('altro_sogg_pf_cf','pf_cognome','pf_nome','pf_sesso','altro_soggetto_pf_dataNascita','altro_sogg_pf_comune_nascita_codice','altro_sogg_pf_comune_nascita',
						function(success){
							if(!success){
								if(!confirm( "<fmt:message key='alert.controllo-codice-fiscale-ko' />" )){
									return false;
								};
							}
							$.blockUI();
							//submit
							document.aggiungiSoggetto.submit();
						}
				);
				return false;
			});
			$("#submit_conferma_altro_soggetto_pg").click(function(){
				$.blockUI();
				//submit
				document.aggiungiSoggetto.submit();
			});
		});
		function controllaCF(id_cf, id_cognome, id_nome, id_sesso, id_data, id_comune, id_comune_desc, callbackFunc){
			var cf         = $("#"+id_cf);
			var cognome    = $("#"+id_cognome);
			var nome       = $("#"+id_nome);
			var sesso      = $("#"+id_sesso);
			var dataNascita= $("#"+id_data);
			var comune     = $("#"+id_comune);
			var comuneDesc = $("#"+id_comune_desc);
			var url = "../ajax/calcolaCF.htm";
			$.get( url, { cognome: cognome.val(), nome: nome.val(), sesso: sesso.val(), data: dataNascita.val(), comune: comune.val() })
			.done(function( resp ) {
				if( resp == '' || resp.toLowerCase() != cf.val().toLowerCase()){			
					cognome.addClass('validation_error_input');
					nome.addClass('validation_error_input');
					sesso.addClass('validation_error_input');
					dataNascita.addClass('validation_error_input');
					comuneDesc.addClass('validation_error_input');
					callbackFunc(false);
				}else{				
					cognome.removeClass('validation_error_input');
					nome.removeClass('validation_error_input');
					sesso.removeClass('validation_error_input');
					dataNascita.removeClass('validation_error_input');
					comuneDesc.removeClass('validation_error_input');
					callbackFunc(true);
				}
			});
		}	
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }"></c:out></div>
	
	<%@ include file="../includes/alert.jsp" %>
	
	<spring-form:form action="aggiungiSoggetto.htm" method="post" commandName="nuovaIstanzaCommand" name="aggiungiSoggetto">
	<input type="hidden" name="tipo" value="${tipo }" />
	<input type="hidden" name="idSoggModifica" value="${idSoggModifica }" />
	<input type="hidden" id="cf_utente_connesso" value='<spring-security:authentication property="principal.cf" />' />
	<div class="titolo_sezione"><fmt:message key='label.nuovo-soggetto' />&nbsp;
	<c:choose>
			<c:when test="${tipo eq 'PF' }">
				(<fmt:message key='label.persona-fisica' />)
			</c:when>
			<c:otherwise>
				(<fmt:message key='label.persona-giuridica' />)
			</c:otherwise>
	</c:choose>
	</div>
	<c:choose>
	<c:when test="${tipo eq 'PF' }">
		<div id="dettaglio_altro_soggetto_pf" class="sezione">	
		<table class="sezione_table">
			<tr>
			<td class="sezione_table_label"><fmt:message key='label.ruolo' /></td>
			<td>
			${nuovaIstanzaCommand.altroSoggettoPFH.soggetto.tipoRapporto.ruolo}
			</td>
			</tr>
		</table>
		<table class="sezione_table">
			<tr>
			<td class="sezione_table_label"><fmt:message key='label.codice-fiscale' /></td>
			<td>
			${nuovaIstanzaCommand.altroSoggettoPFH.soggetto.soggetto.personaFisica.codiceFiscale}
			<spring-form:hidden path="altroSoggettoPFH.soggetto.soggetto.personaFisica.codiceFiscale" id="altro_sogg_pf_cf" />
			</td>
			</tr>
		</table>	
		<table class="sezione_table">
			<tr><td class="sezione_table_label"><fmt:message key='label.titolo' /></td>
				<td>
					<spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.titolo" id="altro_sogg_titolo" cssErrorClass="validation_error_input" />
				</td>
			</tr>		
			<tr><td class="sezione_table_label"><fmt:message key='label.cognome' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.cognome" id="pf_cognome" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.nome' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.nome" id="pf_nome" size="40" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.sesso' /></td><td>
			<spring-form:select path="altroSoggettoPFH.soggetto.soggetto.personaFisica.sesso" id="pf_sesso">
				<spring-form:option value="F"><fmt:message key='label.sesso-femmina' /></spring-form:option>
				<spring-form:option value="M"><fmt:message key='label.sesso-maschio' /></spring-form:option>
			</spring-form:select>
			</td><td class="sezione_table_label"><fmt:message key='label.data-di-nascita' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.dataNascita" size="10" maxlength="10" id="altro_soggetto_pf_dataNascita" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.comune-di-nascita' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.comuneNascita.comune" size="40" id="altro_sogg_pf_comune_nascita" cssErrorClass="validation_error_input" /><spring-form:hidden path="altroSoggettoPFH.soggetto.soggetto.personaFisica.comuneNascita.codiceCatastale" id="altro_sogg_pf_comune_nascita_codice" /></td><td class="sezione_table_label"><fmt:message key='label.cittadinanza' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.cittadinanza.descrizione" size="40" id="altro_sogg_cittadinanza" cssErrorClass="validation_error_input" /><spring-form:hidden path="altroSoggettoPFH.soggetto.soggetto.personaFisica.cittadinanza.id" id="altro_sogg_cittadinanza_codice" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.residenza' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.indirizzo" size="40" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.localita" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.comune.comune" size="40" id="altro_sogg_pf_residenza_comune" cssErrorClass="validation_error_input" /><spring-form:hidden path="altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.comune.codiceCatastale" id="altro_sogg_pf_residenza_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.provincia" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.cap" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td colspan="3"><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.telefono" size="40" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.email" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.pec" size="40" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<c:if test="${nuovaIstanzaCommand.altroSoggettoPFH.tipoSoggetto.flgDatialbo eq true}">
		<div class="titolo_sottosezione"><fmt:message key='label.dati-albo' /></div>
		<table class="sezione_table">	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.albo-ordine-professionisti' /></td>
				<td colspan="3">
					<spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.tipoOrdineProfessionisti.descrizione" id="altro_sogg_albo" size="40" cssErrorClass="validation_error_input" />
					<spring-form:hidden path="altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.tipoOrdineProfessionisti.codice" id="altro_sogg_albo_codice" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.numero-albo' /></td>
				<td colspan="3">
				<spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.numeroIscrizione" size="40" cssErrorClass="validation_error_input" />
				<fmt:message key='label.sigla-provincia-albo' />
				<spring-form:input path="altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.siglaProvincia" size="2" maxlength="2" cssErrorClass="validation_error_input" /></td>
			</tr>
		</table>
		</c:if>
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
				<input type="button" value="<fmt:message key='button.conferma' />" id="submit_conferma_altro_soggetto_pf" />
				<input type="button" value="<fmt:message key='button.annulla' />" id="annulla_conferma_altro_soggetto_pf" />
			</td>
		</tr>
		</table>
		</div>
	</c:when>
	<c:otherwise>
		<div id="dettaglio_altro_soggetto_pg" class="sezione">
		<table class="sezione_table">
			<tr>
			<td class="sezione_table_label"><fmt:message key='label.ruolo' /></td>
			<td>
			${nuovaIstanzaCommand.altroSoggettoPGH.soggetto.tipoRapporto.ruolo}
			</td>
			</tr>
		</table>
		<table class="sezione_table">
			<tr>
			<td class="sezione_table_label"><fmt:message key='label.partita-iva' /></td>
			<td>
			<c:choose>
			<c:when test="${empty nuovaIstanzaCommand.altroSoggettoPGH.soggetto.soggetto.personaGiuridica.partitaIva}">
			<spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.partitaIva" size="11" cssErrorClass="validation_error_input" />
			</c:when>
			<c:otherwise>${nuovaIstanzaCommand.altroSoggettoPGH.soggetto.soggetto.personaGiuridica.partitaIva}</c:otherwise>
			</c:choose>
			</td>
			</tr>
			<tr>
			<td class="sezione_table_label"><fmt:message key='label.codice-fiscale' /></td>
			<td>
			<c:choose>
			<c:when test="${empty nuovaIstanzaCommand.altroSoggettoPGH.soggetto.soggetto.personaGiuridica.codiceFiscale}">
			<spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.codiceFiscale" size="16" cssErrorClass="validation_error_input" />
			</c:when>
			<c:otherwise>${nuovaIstanzaCommand.altroSoggettoPGH.soggetto.soggetto.personaGiuridica.codiceFiscale}</c:otherwise>
			</c:choose>
			</td>
			</tr>
		</table>
		<table class="sezione_table">
			<tr><td class="sezione_table_label"><fmt:message key='label.ragione-sociale' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.ragioneSociale" size="80" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.natura-giuridica' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.naturaGiuridica" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.sede-legale' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.indirizzo" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.localita" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.comune.comune" size="40" id="altro_sogg_sedelegale_comune" cssErrorClass="validation_error_input" /><spring-form:hidden path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.comune.codiceCatastale" id="altro_sogg_sedelegale_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.provincia" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.cap" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.corrispondenza' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.indirizzo" size="40" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.localita" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.comune.comune" size="40" id="altro_sogg_indcorr_comune" cssErrorClass="validation_error_input" /><spring-form:hidden path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.comune.codiceCatastale" id="altro_sogg_indcorr_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.provincia" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.cap" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.cciaa' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.numero' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.numero" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.data' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.data" size="10" maxlength="10" id="altro_sogg_iscrizioneCCIAA_data" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.comune.comune" size="40" id="altro_sogg_cciaa_comune" cssErrorClass="validation_error_input" /><spring-form:hidden path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.comune.codiceCatastale" id="altro_sogg_cciaa_comune_codice" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.rea' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.numero' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneREA.numero" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.data' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneREA.data" size="10" maxlength="10" id="altro_sogg_iscrizioneREA_data" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label">Provincia</td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneREA.siglaProvincia" size="40" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.telefono" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.fax' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.fax" size="40" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.email" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="altroSoggettoPGH.soggetto.soggetto.personaGiuridica.pec" size="40" cssErrorClass="validation_error_input" /></td></tr>
		</table>
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
				<input type="button" value="<fmt:message key='button.conferma' />" id="submit_conferma_altro_soggetto_pg" />
				<input type="button" value="<fmt:message key='button.annulla' />" id="annulla_conferma_altro_soggetto_pg" />
			</td>
		</tr>
		</table>
		</div>
	</c:otherwise>
	</c:choose>	
	</spring-form:form>
	
</body>
</html>