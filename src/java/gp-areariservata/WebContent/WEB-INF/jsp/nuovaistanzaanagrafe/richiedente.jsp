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
			$("#show_hide_dettaglio_richiedente").hide();
			$("#show_hide_ruolo_richiedente").hide();
			$("#show_hide_azienda_richiedente").hide();
			$("#show_hide_dettaglio_azienda_richiedente").hide();
			$("#show_hide_altri_soggetti").hide();
			$("#show_hide_cerca_altri_soggetti_pf").hide();
			$("#show_hide_cerca_altri_soggetti_pg").hide();
			$("#show_hide_dettaglio_altro_soggetto_pf").hide();
			$("#show_hide_dettaglio_altro_soggetto_pg").hide();
			$("#show_hide_intermediario").hide();
			$("#show_hide_dettaglio_intermediario").hide();
			$('#richiedente_dataNascita').datepicker();
			$('#intermediario_dataNascita').datepicker();
			$('#az_iscrizioneCCIAA_data').datepicker();
			$('#az_iscrizioneREA_data').datepicker();
			$('#altro_soggetto_pf_dataNascita').datepicker();
			
			$("#submit_utente_connesso").click(function(){
				$.blockUI();
				$("#cf_richiedente").val($("#cf_utente_connesso").val());
				$("#azioneId").val("FIND");
			});
			$("#submit_cerca_anagrafica").click(function(){
				$.blockUI();
				$("#azioneId").val("FIND");
			});
			$("#submit_cerca_azienda").click(function(){
				$.blockUI();
				$("#azioneAzId").val("FIND");
			});
			$("#submit_conferma_anagrafica").click(function(){
				$.blockUI();
				$("#azioneId").val("CONFIRM");
			});
			$("#submit_modifica_anagrafica").click(function(){
				$.blockUI();
				$("#azioneId").val("MODIFY");
			});
			$("#submit_elimina_anagrafica").click(function(){
				$.blockUI();
				$("#azioneId").val("DELETE");
			});
			$("#submit_modifica_ruolo_richiedente").click(function(){
				$.blockUI();
				$("#azioneId").val("MODIFY_TIPO_SOGGETTO");
			});
			$("#submit_conferma_ruolo_richiedente").click(function(){
				$.blockUI();
				$("#azioneId").val("CONFIRM_TIPO_SOGGETTO");
			});
			$("#submit_conferma_azienda_richiedente").click(function(){
				$.blockUI();
				$("#azioneAzId").val("CONFIRM");
			});
			$("#submit_modifica_azienda_richiedente").click(function(){
				$.blockUI();
				$("#azioneAzId").val("MODIFY");
			});
			$("#submit_elimina_azienda_richiedente").click(function(){
				$.blockUI();
				$("#azioneAzId").val("DELETE");
			});
			$("#button_aggiungi_soggetto_pf").click(function(){
				$("#show_hide_cerca_altri_soggetti_pf").show();
				$("#show_hide_cerca_altri_soggetti_pg").hide();
			});
			$("#button_aggiungi_soggetto_pg").click(function(){				
				$("#show_hide_cerca_altri_soggetti_pf").hide();
				$("#show_hide_cerca_altri_soggetti_pg").show();
			});
			$("#submit_cerca_altro_soggetto_pf").click(function(){
				$.blockUI();
				$("#azioneAsId").val("FIND_AS_PF");
			});
			$("#submit_conferma_altro_soggetto_pf").click(function(){
				$.blockUI();
				$("#azioneAsId").val("CONFIRM_AS_PF");
			});
			$("#submit_elimina_altro_soggetto_pf").click(function(){
				$.blockUI();
				$("#azioneAsId").val("DELETE_AS_PF");
			});
			
			$("#submit_cerca_altro_soggetto_pg").click(function(){
				$.blockUI();
				$("#azioneAsId").val("FIND_AS_PG");
			});
			$("#submit_conferma_altro_soggetto_pg").click(function(){
				$.blockUI();
				$("#azioneAsId").val("CONFIRM_AS_PG");
			});
			$("#submit_elimina_altro_soggetto_pg").click(function(){
				$.blockUI();
				$("#azioneAsId").val("DELETE_AS_PG");
			});
			
			$("#submit_intermediario_utente_connesso").click(function(){
				$.blockUI();
				$("#cf_intermediario").val($("#cf_intermediario_utente_connesso").val());
				$("#azioneIntId").val("FIND_INT");
			});
			$("#submit_cerca_intermediario").click(function(){
				$.blockUI();
				$("#azioneIntId").val("FIND_INT");
			});
			$("#submit_conferma_intermediario").click(function(){
				$.blockUI();
				$("#azioneIntId").val("CONFIRM_INT");
			});
			$("#submit_modifica_intermediario").click(function(){
				$.blockUI();
				$("#azioneIntId").val("MODIFY_INT");
			});
			$("#submit_elimina_intermediario").click(function(){
				$.blockUI();
				$("#azioneIntId").val("DELETE_INT");
			});
			
			<c:if test="${nuovaIstanzaCommand.showDettaglioRichiedente eq true }">
			$("#dettaglio_richiedente").addClass("sezione_highlight"); 
			$("#show_hide_dettaglio_richiedente").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showRuoloRichiedente eq true }">
			$("#show_hide_ruolo_richiedente").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showAziendaRichiedente eq true }">
			$("#show_hide_azienda_richiedente").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showDettaglioAziendaRichiedente eq true }">
			$("#dettaglio_azienda_richiedente").addClass("sezione_highlight");
			$("#show_hide_dettaglio_azienda_richiedente").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showAltriSoggetti eq true }">
			$("#show_hide_altri_soggetti").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showDettaglioAltroSoggettoPF eq true }">
			$("#dettaglio_altro_soggetto_pf").addClass("sezione_highlight");
			$("#show_hide_dettaglio_altro_soggetto_pf").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showCercaAltroSoggettoPF eq true }">
			$("#show_hide_cerca_altri_soggetti_pf").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showDettaglioAltroSoggettoPG eq true }">
			$("#dettaglio_altro_soggetto_pg").addClass("sezione_highlight");
			$("#show_hide_dettaglio_altro_soggetto_pg").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showCercaAltroSoggettoPG eq true }">
			$("#show_hide_cerca_altri_soggetti_pg").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showCercaIntermediario eq true }">
			$("#show_hide_intermediario").show();
			</c:if>
			<c:if test="${nuovaIstanzaCommand.showDettaglioIntermediario eq true }">
			$("#dettaglio_intermediario").addClass("sezione_highlight");
			$("#show_hide_dettaglio_intermediario").show();
			</c:if>
			
			$( "#rich_nascita_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#rich_nascita_comune_codice").val(ui.item.id);
					}else{
						$("#rich_nascita_comune_codice").val('');
						$("#rich_nascita_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#rich_nascita_comune_codice").val(ui.item.id);
					}else{
						$("#rich_nascita_comune_codice").val('');
						$("#rich_nascita_comune").val('');
					};
				}
			});			
			$( "#rich_residenza_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#rich_residenza_comune_codice").val(ui.item.id);
					}else{
						$("#rich_residenza_comune_codice").val('');
						$("#rich_residenza_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#rich_residenza_comune_codice").val(ui.item.id);
					}else{
						$("#rich_residenza_comune_codice").val('');
						$("#rich_residenza_comune").val('');
					};
				}
			});			
			$( "#az_sedelegale_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#az_sedelegale_comune_codice").val(ui.item.id);
					}else{
						$("#az_sedelegale_comune_codice").val('');
						$("#az_sedelegale_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#az_sedelegale_comune_codice").val(ui.item.id);
					}else{
						$("#az_sedelegale_comune_codice").val('');
						$("#az_sedelegale_comune").val('');
					};
				}
			});
			$( "#az_indcorr_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#az_indcorr_comune_codice").val(ui.item.id);
					}else{
						$("#az_indcorr_comune_codice").val('');
						$("#az_indcorr_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#az_indcorr_comune_codice").val(ui.item.id);
					}else{
						$("#az_indcorr_comune_codice").val('');
						$("#az_indcorr_comune").val('');
					};
				}
			});
			$( "#az_cciaa_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#az_cciaa_comune_codice").val(ui.item.id);
					}else{
						$("#az_cciaa_comune_codice").val('');
						$("#az_cciaa_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#az_cciaa_comune_codice").val(ui.item.id);
					}else{
						$("#az_cciaa_comune_codice").val('');
						$("#az_cciaa_comune").val('');
					};
				}
			});
			$( "#altro_sogg_pf_comune_nascita" ).autocomplete({
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
			$( "#altro_sogg_pf_residenza_comune" ).autocomplete({
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
			$( "#rich_cittadinanza" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getCittadinanza.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#rich_cittadinanza_codice").val(ui.item.id);
					}else{
						$("#rich_cittadinanza_codice").val('');
						$("#rich_cittadinanza").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#rich_cittadinanza_codice").val(ui.item.id);
					}else{
						$("#rich_cittadinanza_codice").val('');
						$("#rich_cittadinanza").val('');
					};
				}
			});
			$( "#altro_sogg_cittadinanza" ).autocomplete({
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
			$( "#intermediario_nascita_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#intermediario_nascita_comune_codice").val(ui.item.id);
					}else{
						$("#intermediario_nascita_comune_codice").val('');
						$("#intermediario_nascita_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#intermediario_nascita_comune_codice").val(ui.item.id);
					}else{
						$("#intermediario_nascita_comune_codice").val('');
						$("#intermediario_nascita_comune").val('');
					};
				}
			});
			$( "#intermediario_cittadinanza" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getCittadinanza.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#intermediario_cittadinanza_codice").val(ui.item.id);
					}else{
						$("#intermediario_cittadinanza_codice").val('');
						$("#intermediario_cittadinanza").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#intermediario_cittadinanza_codice").val(ui.item.id);
					}else{
						$("#intermediario_cittadinanza_codice").val('');
						$("#intermediario_cittadinanza").val('');
					};
				}
			});
			$( "#intermediario_residenza_comune" ).autocomplete({
				source: "${pageContext.request.contextPath}/ajax/getComune.htm",
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						$("#intermediario_residenza_comune_codice").val(ui.item.id);
					}else{
						$("#intermediario_residenza_comune_codice").val('');
						$("#intermediario_residenza_comune").val('');
					};
				},
				change: function(event, ui) {
					if(ui.item){
						$("#intermediario_residenza_comune_codice").val(ui.item.id);
					}else{
						$("#intermediario_residenza_comune_codice").val('');
						$("#intermediario_residenza_comune").val('');
					};
				}
			});
			$( "#altro_sogg_sedelegale_comune" ).autocomplete({
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
			$( "#altro_sogg_indcorr_comune" ).autocomplete({
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
			$( "#altro_sogg_cciaa_comune" ).autocomplete({
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
		});
		function setHiddenAltroSoggettoDaEliminare(id){
			$("#azioneAsId").val("DELETE_AS");
			$("#hidden_altro_soggetto_da_eliminare_id").val(id);
		}
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="richiedentePOST.htm" method="post" commandName="nuovaIstanzaCommand" id="richiedentePOST">
		<input type="hidden" name="azione" id="azioneId" value="" />
		<input type="hidden" name="pager_step" />
		<input type="hidden" name="pager_azione" />
		<div class="titolo_sezione"><fmt:message key="label.richiedente" /></div>
		<c:if test="${nuovaIstanzaCommand.richiedenteConfirmed ne true }">
			<div id="cerca_richiedente" class="sezione">
			<table class="sezione_table">
				<tr>
				<td class="sezione_table_label"><label><fmt:message key="label.codice-fiscale" /></label></td>
				<td>
					<spring-form:input path="richiedente.anagrafica.codiceFiscale" size="20" maxlength="16" id="cf_richiedente" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_buttons">
					<input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_anagrafica" /><input type="submit" value="<fmt:message key='button.utente-connesso' />" id="submit_utente_connesso" />
				</td>
				</tr>
			</table>
			<input type="hidden" id="cf_utente_connesso" value='<spring-security:authentication property="principal.cf" />' />
			</div>
			<div id="show_hide_dettaglio_richiedente">
			<div class="titolo_sezione"><fmt:message key='label.dettaglio-anagrafe' /></div>
			<div id="dettaglio_richiedente" class="sezione">
			<table class="sezione_table">
				<tr><td class="sezione_table_label"><fmt:message key='label.titolo' /></td><td><spring-form:input path="richiedente.anagrafica.titolo" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.cognome' /></td><td><spring-form:input path="richiedente.anagrafica.cognome" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.nome' /></td><td><spring-form:input path="richiedente.anagrafica.nome" size="40" cssErrorClass="validation_error_input" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.sesso' /></td><td>
				<spring-form:select path="richiedente.anagrafica.sesso">
					<spring-form:option value="F"><fmt:message key='label.sesso-femmina' /></spring-form:option>
					<spring-form:option value="M"><fmt:message key='label.sesso-maschio' /></spring-form:option>
				</spring-form:select>
				</td><td class="sezione_table_label"><fmt:message key='label.data-di-nascita' /></td><td><spring-form:input path="richiedente.anagrafica.dataNascita" size="10" maxlength="10" id="richiedente_dataNascita" /><spring-form:errors path="richiedente.anagrafica.dataNascita" cssClass="validation_error" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.comune-di-nascita' /></td><td><spring-form:input path="richiedente.anagrafica.comuneNascita.comune" size="40" id="rich_nascita_comune" /><spring-form:hidden path="richiedente.anagrafica.comuneNascita.codiceCatastale" id="rich_nascita_comune_codice" /></td><td class="sezione_table_label"><fmt:message key='label.cittadinanza' /></td><td><spring-form:input path="richiedente.anagrafica.cittadinanza.descrizione" size="40" id="rich_cittadinanza" /><spring-form:hidden path="richiedente.anagrafica.cittadinanza.codiceCatastale" id="rich_cittadinanza_codice" /></td></tr>
			</table>
			<div class="titolo_sottosezione"><fmt:message key='label.residenza' /></div>
			<table class="sezione_table">	
				<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="richiedente.anagrafica.residenza.indirizzo" size="40" cssErrorClass="validation_error_input" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="richiedente.anagrafica.residenza.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="richiedente.anagrafica.residenza.comune.comune" size="40" id="rich_residenza_comune" cssErrorClass="validation_error_input" /><spring-form:hidden path="richiedente.anagrafica.residenza.comune.codiceCatastale" id="rich_residenza_comune_codice" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="richiedente.anagrafica.residenza.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="richiedente.anagrafica.residenza.cap" /></td></tr>
			</table>
			<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
			<table class="sezione_table">	
				<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td colspan="3"><spring-form:input path="richiedente.anagrafica.telefono" size="40" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="richiedente.anagrafica.email" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="richiedente.anagrafica.pec" size="40" /></td></tr>
			</table>
			<table class="sezione_table">
			<tr>
				<td class="sezione_table_buttons">
				<input type="submit" value="<fmt:message key='button.conferma' />" id="submit_conferma_anagrafica" />
				<input type="submit" value="<fmt:message key='button.elimina' />" id="submit_elimina_anagrafica" />
				</td>
			</tr>
			</table>
			</div>
			</div>
		</c:if>
		<c:if test="${nuovaIstanzaCommand.richiedenteConfirmed eq true }">
		<div id="richiedente_confirmed" class="sezione">
		<table class="sezione_table">	
			<tr>
				<td class="sezione_table_label2">${nuovaIstanzaCommand.richiedente.anagrafica.cognome} ${nuovaIstanzaCommand.richiedente.anagrafica.nome}<br /><fmt:message key='label.cf' /> (${nuovaIstanzaCommand.richiedente.anagrafica.codiceFiscale})</td>
				<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.modifica' />" id="submit_modifica_anagrafica" /></td>
			</tr>
		</table>
		</div>	
		</c:if>
		<c:if test="${nuovaIstanzaCommand.tipoSoggettoConfirmed ne true }">
			<div id="show_hide_ruolo_richiedente">
			<div class="titolo_sezione"><fmt:message key='label.in-qualita-di' /></div>
			<div id="cerca_ruolo_richiedente" class="sezione">	
			<table class="sezione_table">
				<tr>
					<td class="sezione_table_label"><fmt:message key='label.ruolo' /></td>
					<td>
					<spring-form:select path="ruoloRichiedente.ruolo" id="scelta_ruolo_richiedente" cssErrorClass="validation_error_input">
						<spring-form:option value=""></spring-form:option>
						<c:forEach items="${tipiSoggettoPF }" var="tp"><spring-form:option value="${tp.tiposoggetto}">${tp.tiposoggetto}</spring-form:option></c:forEach>
					</spring-form:select>
					</td>
					<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.conferma' />" id="submit_conferma_ruolo_richiedente" /></td>
				</tr>
			</table>
			</div>
			</div>
		</c:if>
		<c:if test="${nuovaIstanzaCommand.tipoSoggettoConfirmed eq true }">
			<div class="titolo_sezione"><fmt:message key='label.in-qualita-di' /></div>
			<div id="ruolo_richiedente_confirmed" class="sezione">
			<table class="sezione_table">
				<tr>
					<td class="sezione_table_label2">${nuovaIstanzaCommand.ruoloRichiedente.ruolo }</td>
					<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.modifica' />" id="submit_modifica_ruolo_richiedente" /></td>
				</tr>
			</table>
			</div>
		</c:if>
	</spring-form:form>
	
	<spring-form:form action="aziendarichiedentePOST.htm" method="post" commandName="nuovaIstanzaCommand">
		<input type="hidden" name="azioneAz" id="azioneAzId" value="" />
		
		<c:if test="${nuovaIstanzaCommand.aziendaRichiedenteConfirmed ne true }">	
		<div id="show_hide_azienda_richiedente">
		<div class="titolo_sezione"><fmt:message key='label.azienda-richiedente' /></div>
		<div id="cerca_azienda_richiedente" class="sezione">
		<table class="sezione_table"><tr>
		<td class="sezione_table_label"><fmt:message key='label.partita-iva' /></td>
		<td>
		<spring-form:input path="aziendaRichiedente.partitaIva" size="20" maxlength="11" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_label"><fmt:message key='label.codice-fiscale' /></td>
		<td>
		<spring-form:input path="aziendaRichiedente.codiceFiscale" size="20" maxlength="16" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_azienda" /></td>
		</tr></table>
		</div>		
		<div id="show_hide_dettaglio_azienda_richiedente">
		<div class="titolo_sezione"><fmt:message key='label.dettaglio-anagrafe' /></div>
		<div id="dettaglio_azienda_richiedente" class="sezione">
		<table class="sezione_table">
			<tr><td class="sezione_table_label"><fmt:message key='label.ragione-sociale' /></td><td><spring-form:input path="aziendaRichiedente.ragioneSociale" size="80" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.natura-giuridica' /></td><td><spring-form:input path="aziendaRichiedente.naturaGiuridica" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.sede-legale' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td><spring-form:input path="aziendaRichiedente.sedeLegale.indirizzo" size="40" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="aziendaRichiedente.sedeLegale.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="aziendaRichiedente.sedeLegale.comune.comune" size="40" id="az_sedelegale_comune" /><spring-form:hidden path="aziendaRichiedente.sedeLegale.comune.codiceCatastale" id="az_sedelegale_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="aziendaRichiedente.sedeLegale.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="aziendaRichiedente.sedeLegale.cap" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.corrispondenza' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="aziendaRichiedente.indirizzoCorrispondenza.indirizzo" size="40" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="aziendaRichiedente.indirizzoCorrispondenza.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="aziendaRichiedente.indirizzoCorrispondenza.comune.comune" size="40" id="az_indcorr_comune" /><spring-form:hidden path="aziendaRichiedente.indirizzoCorrispondenza.comune.codiceCatastale" id="az_indcorr_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="aziendaRichiedente.indirizzoCorrispondenza.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="aziendaRichiedente.indirizzoCorrispondenza.cap" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.cciaa' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.numero' /></td><td><spring-form:input path="aziendaRichiedente.iscrizioneCCIAA.numero" size="40" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.data' /></td><td><spring-form:input path="aziendaRichiedente.iscrizioneCCIAA.data" size="10" maxlength="10" id="az_iscrizioneCCIAA_data" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="aziendaRichiedente.iscrizioneCCIAA.comune.comune" size="40" id="az_cciaa_comune" /><spring-form:hidden path="aziendaRichiedente.iscrizioneCCIAA.comune.codiceCatastale" id="az_cciaa_comune_codice" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.rea' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.numero' /></td><td><spring-form:input path="aziendaRichiedente.iscrizioneREA.numero" size="40" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.data' /></td><td><spring-form:input path="aziendaRichiedente.iscrizioneREA.data" size="10" maxlength="10" id="az_iscrizioneREA_data" /></td><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="aziendaRichiedente.iscrizioneREA.siglaProvincia" size="40" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td><spring-form:input path="aziendaRichiedente.telefono" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.fax' /></td><td><spring-form:input path="aziendaRichiedente.fax" size="40" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="aziendaRichiedente.email" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="aziendaRichiedente.pec" size="40" /></td></tr>
		</table>
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">	
				<input type="submit" value="<fmt:message key='button.conferma' />" id="submit_conferma_azienda_richiedente" />
				<input type="submit" value="<fmt:message key='button.elimina' />" id="submit_elimina_azienda_richiedente" />
			</td>
		</tr>
		</table>
		</div>
		</div>
		</div>
		</c:if>
		<c:if test="${nuovaIstanzaCommand.aziendaRichiedenteConfirmed eq true }">
		<div class="titolo_sezione"><fmt:message key='label.azienda-richiedente' /></div>
		<div id="cerca_azienda_richiedente" class="sezione">
		<table class="sezione_table">
			<tr><td class="sezione_table_label2">${nuovaIstanzaCommand.aziendaRichiedente.ragioneSociale} (${nuovaIstanzaCommand.aziendaRichiedente.naturaGiuridica})<br />
			<fmt:message key='label.piva' /> (${nuovaIstanzaCommand.aziendaRichiedente.partitaIva})  <fmt:message key='label.cf' /> (${nuovaIstanzaCommand.aziendaRichiedente.codiceFiscale})</td>
			<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.modifica' />" id="submit_modifica_azienda_richiedente" /></td></tr>
		</table>
		</div>
		</c:if>
	</spring-form:form>
	
	<div id="show_hide_intermediario">
	
	<spring-form:form action="intermediarioPOST.htm" method="post" commandName="nuovaIstanzaCommand" id="intermediarioPOST">
		<input type="hidden" name="azioneInt" id="azioneIntId" value="" />
		<div class="titolo_sezione"><fmt:message key='label.intermediario' /></div>
		<c:if test="${nuovaIstanzaCommand.intermediarioConfirmed ne true }">
			<div id="cerca_intermediario" class="sezione">
			<table class="sezione_table">
				<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.codice-fiscale' /></label></td>
				<td>
					<spring-form:input path="intermediario.codiceFiscale" size="20" maxlength="16" id="cf_intermediario" cssErrorClass="validation_error_input" />
				</td>
				<td class="sezione_table_buttons">
					<input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_intermediario" /><input type="submit" value="<fmt:message key='button.utente-connesso' />" id="submit_intermediario_utente_connesso" />
				</td>
				</tr>
			</table>
			<input type="hidden" id="cf_intermediario_utente_connesso" value='<spring-security:authentication property="principal.cf" />' />
			</div>
			<div id="show_hide_dettaglio_intermediario">
			<div class="titolo_sezione"><fmt:message key='label.dettaglio-anagrafe' /></div>
			<div id="dettaglio_intermediario" class="sezione">
			<table class="sezione_table">
				<tr><td class="sezione_table_label"><fmt:message key='label.titolo' /></td><td><spring-form:input path="intermediario.titolo" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.cognome' /></td><td><spring-form:input path="intermediario.cognome" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.nome' /></td><td><spring-form:input path="intermediario.nome" size="40" cssErrorClass="validation_error_input" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.sesso' /></td><td>
				<spring-form:select path="intermediario.sesso">
					<spring-form:option value="F"><fmt:message key='label.sesso-femmina' /></spring-form:option>
					<spring-form:option value="M"><fmt:message key='label.sesso-maschio' /></spring-form:option>
				</spring-form:select>
				</td><td class="sezione_table_label"><fmt:message key='label.data-di-nascita' /></td><td><spring-form:input path="intermediario.dataNascita" size="10" maxlength="10" id="intermediario_dataNascita" /><spring-form:errors path="intermediario.dataNascita" cssClass="validation_error" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.comune-di-nascita' /></td><td><spring-form:input path="intermediario.comuneNascita.comune" size="40" id="intermediario_nascita_comune" /><spring-form:hidden path="intermediario.comuneNascita.codiceCatastale" id="intermediario_nascita_comune_codice" /></td><td class="sezione_table_label"><fmt:message key='label.cittadinanza' /></td><td><spring-form:input path="intermediario.cittadinanza.descrizione" size="40" id="intermediario_cittadinanza" /><spring-form:hidden path="intermediario.cittadinanza.codiceCatastale" id="intermediario_cittadinanza_codice" /></td></tr>
			</table>
			<div class="titolo_sottosezione"><fmt:message key='label.residenza' /></div>
			<table class="sezione_table">	
				<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="intermediario.residenza.indirizzo" size="40" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="intermediario.residenza.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="intermediario.residenza.comune.comune" size="40" id="intermediario_residenza_comune" /><spring-form:hidden path="intermediario.residenza.comune.codiceCatastale" id="intermediario_residenza_comune_codice" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="intermediario.residenza.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="intermediario.residenza.cap" /></td></tr>
			</table>
			<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
			<table class="sezione_table">	
				<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td colspan="3"><spring-form:input path="intermediario.telefono" size="40" /></td></tr>
				<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="intermediario.email" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="intermediario.pec" size="40" /></td></tr>
			</table>
			<table class="sezione_table">
			<tr>
				<td class="sezione_table_buttons">				
					<input type="submit" value="<fmt:message key='button.conferma' />" id="submit_conferma_intermediario" />
					<input type="submit" value="<fmt:message key='button.elimina' />" id="submit_elimina_intermediario" />
				</td>
			</tr>
			</table>
			</div>
			</div>
		</c:if>
		<c:if test="${nuovaIstanzaCommand.intermediarioConfirmed eq true }">
		<div id="intermediario_confirmed" class="sezione">
		<table class="sezione_table">	
			<tr>
				<td class="sezione_table_label2">${nuovaIstanzaCommand.intermediario.cognome} ${nuovaIstanzaCommand.intermediario.nome}<br /><fmt:message key='label.cf' /> (${nuovaIstanzaCommand.intermediario.codiceFiscale})</td>
				<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.modifica' />" id="submit_modifica_intermediario" /></td>
			</tr>
		</table>
		</div>	
		</c:if>
	</spring-form:form>	
	</div>
	
	<div id="show_hide_altri_soggetti">
	<spring-form:form action="altriSoggettiPOST.htm" method="post" commandName="nuovaIstanzaCommand">
	<div class="titolo_sezione"><fmt:message key='label.altri-soggetti' /></div>
	<div class="sezione">
	<c:if test="${not empty nuovaIstanzaCommand.altriSoggetti }">
	<input type="hidden" value="" name="hidden_altro_soggetto_da_eliminare" id="hidden_altro_soggetto_da_eliminare_id" />
	<table class="sezione_table">
	<c:forEach items="${nuovaIstanzaCommand.altriSoggetti }" var="currentAS">	
		<tr>
		<c:if test="${currentAS.value.soggetto.personaFisica.codiceFiscale != null }">
		<td class="sezione_table_label2">${currentAS.value.soggetto.personaFisica.cognome} ${currentAS.value.soggetto.personaFisica.nome}<br /><fmt:message key='label.cf' /> (${currentAS.value.soggetto.personaFisica.codiceFiscale})</td><td>${currentAS.value.tipoRapporto.ruolo}</td>
		<td class="sezione_table_buttons"><input type="submit" value="Elimina" onclick="setHiddenAltroSoggettoDaEliminare('${currentAS.key}')" /></td>
		</c:if>
		<c:if test="${currentAS.value.soggetto.personaGiuridica != null }">
		<td class="sezione_table_label2">${currentAS.value.soggetto.personaGiuridica.ragioneSociale}<br /><fmt:message key='label.piva' /> (${currentAS.value.soggetto.personaGiuridica.partitaIva}) <fmt:message key='label.cf' /> (${currentAS.value.soggetto.personaGiuridica.codiceFiscale})</td><td>${currentAS.value.tipoRapporto.ruolo}</td>
		<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.elimina' />" onclick="setHiddenAltroSoggettoDaEliminare('${currentAS.key}')" /></td>
		</c:if>
		</tr>
	</c:forEach>
	</table>
	</c:if>
	<c:if test="${empty nuovaIstanzaCommand.altriSoggetti }">
		<div><fmt:message key='label.nessun-soggetto' /></div>
		<div class="titolo_sottosezione"></div>
	</c:if>
	</div>
	<div class="sezione">
		<table class="sezione_table">
		<tr>
		<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.aggiungi-persona-fisica' />" id="button_aggiungi_soggetto_pf" />
			<input type="button" value="<fmt:message key='button.aggiungi-persona-giuridica' />" id="button_aggiungi_soggetto_pg" />
		</td>
		</tr>
		</table>	
	</div>
	<div id="cerca_altri_soggetti" class="sezione">
		
		<input type="hidden" name="azioneAs" id="azioneAsId" value="" />
		<div id="show_hide_cerca_altri_soggetti_pf">
		<table class="sezione_table"><tr><td class="sezione_table_label"><fmt:message key='label.codice-fiscale' /></td>
		<td>
		<spring-form:input path="altroSoggettoPF.soggetto.personaFisica.codiceFiscale" size="20" maxlength="16" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_altro_soggetto_pf" /></td>
		</tr></table>
		</div>
		<div id="show_hide_cerca_altri_soggetti_pg">
		<table class="sezione_table"><tr>
		<td class="sezione_table_label"><fmt:message key='label.partita-iva' /></td>
		<td>
		<spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.partitaIva" size="20" maxlength="11" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_label"><fmt:message key='label.codice-fiscale' /></td>
		<td>
		<spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.codiceFiscale" size="20" maxlength="16" cssErrorClass="validation_error_input" />
		</td>
		<td class="sezione_table_buttons"><input type="submit" value="<fmt:message key='button.cerca' />" id="submit_cerca_altro_soggetto_pg" /></td>
		</tr></table>
		</div>
		
		<div id="show_hide_dettaglio_altro_soggetto_pf">
		<div class="titolo_sezione"><fmt:message key='label.dettaglio-anagrafe' /></div>
		<div id="dettaglio_altro_soggetto_pf" class="sezione">
		<table class="sezione_table">
			<tr><td class="sezione_table_label"><fmt:message key='label.titolo' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.titolo" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.cognome' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.cognome" size="40" cssErrorClass="validation_error_input" /></td><td class="sezione_table_label"><fmt:message key='label.nome' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.nome" size="40" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.sesso' /></td><td>
			<spring-form:select path="altroSoggettoPF.soggetto.personaFisica.sesso">
				<spring-form:option value="F"><fmt:message key='label.sesso-femmina' /></spring-form:option>
				<spring-form:option value="M"><fmt:message key='label.sesso-maschio' /></spring-form:option>
			</spring-form:select>
			</td><td class="sezione_table_label"><fmt:message key='label.data-di-nascita' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.dataNascita" size="10" maxlength="10" id="altro_soggetto_pf_dataNascita" /><spring-form:errors path="altroSoggettoPF.soggetto.personaFisica.dataNascita" cssClass="validation_error" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.comune-di-nascita' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.comuneNascita.comune" size="40" id="altro_sogg_pf_comune_nascita" /><spring-form:hidden path="altroSoggettoPF.soggetto.personaFisica.comuneNascita.codiceCatastale" id="altro_sogg_pf_comune_nascita_codice" /></td><td class="sezione_table_label">Cittadinanza</td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.cittadinanza.descrizione" size="40" id="altro_sogg_cittadinanza" /><spring-form:hidden path="altroSoggettoPF.soggetto.personaFisica.cittadinanza.codiceCatastale" id="altro_sogg_cittadinanza_codice" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.residenza' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.residenza.indirizzo" size="40" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.residenza.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.residenza.comune.comune" size="40" id="altro_sogg_pf_residenza_comune" /><spring-form:hidden path="altroSoggettoPF.soggetto.personaFisica.residenza.comune.codiceCatastale" id="altro_sogg_pf_residenza_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.residenza.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.residenza.cap" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td colspan="3"><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.telefono" size="40" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.email" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="altroSoggettoPF.soggetto.personaFisica.pec" size="40" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.in-qualita-di' /></div>
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.ruolo' /></td>
				<td>
				<spring-form:select path="altroSoggettoPF.tipoRapporto.ruolo" cssErrorClass="validation_error_input">
					<spring-form:option value=""></spring-form:option>
					<c:forEach items="${tipiSoggettoPF }" var="tp1"><spring-form:option value="${tp1.tiposoggetto}">${tp1.tiposoggetto}</spring-form:option></c:forEach>
				</spring-form:select>
				</td>
			</tr>
		</table>
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
				<input type="submit" value="<fmt:message key='button.conferma' />" id="submit_conferma_altro_soggetto_pf" />
				<input type="submit" value="<fmt:message key='button.elimina' />" id="submit_elimina_altro_soggetto_pf" />
			</td>
		</tr>
		</table>
		</div>
		</div>
		
		<div id="show_hide_dettaglio_altro_soggetto_pg">
		<div class="titolo_sezione"><fmt:message key='label.dettaglio-anagrafe' /></div>
		<div id="dettaglio_altro_soggetto_pg" class="sezione">
		<table class="sezione_table">
			<tr><td class="sezione_table_label"><fmt:message key='label.ragione-sociale' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.ragioneSociale" size="80" cssErrorClass="validation_error_input" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.natura-giuridica' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.naturaGiuridica" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.sede-legale' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.sedeLegale.indirizzo" size="40" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.sedeLegale.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.sedeLegale.comune.comune" size="40" id="altro_sogg_sedelegale_comune" /><spring-form:hidden path="altroSoggettoPG.soggetto.personaGiuridica.sedeLegale.comune.codiceCatastale" id="altro_sogg_sedelegale_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.sedeLegale.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.sedeLegale.cap" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.corrispondenza' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.indirizzo' /></td><td colspan="3"><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.indirizzoCorrispondenza.indirizzo" size="40" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.localita' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.indirizzoCorrispondenza.localita" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.indirizzoCorrispondenza.comune.comune" size="40" id="altro_sogg_indcorr_comune" /><spring-form:hidden path="altroSoggettoPG.soggetto.personaGiuridica.indirizzoCorrispondenza.comune.codiceCatastale" id="altro_sogg_indcorr_comune_codice" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.indirizzoCorrispondenza.provincia" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.cap' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.indirizzoCorrispondenza.cap" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.cciaa' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.numero' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneCCIAA.numero" size="40" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.data' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneCCIAA.data" size="10" maxlength="10" id="altro_sogg_iscrizioneCCIAA_data" /></td><td class="sezione_table_label"><fmt:message key='label.comune' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneCCIAA.comune.comune" size="40" id="altro_sogg_cciaa_comune" /><spring-form:hidden path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneCCIAA.comune.codiceCatastale" id="altro_sogg_cciaa_comune_codice" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.rea' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.numero' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneREA.numero" size="40" /></td><td class="sezione_table_label"></td><td></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.data' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneREA.data" size="10" maxlength="10" id="altro_sogg_iscrizioneREA_data" /></td><td class="sezione_table_label"><fmt:message key='label.provincia' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.iscrizioneREA.siglaProvincia" size="40" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.altri-dati' /></div>
		<table class="sezione_table">	
			<tr><td class="sezione_table_label"><fmt:message key='label.telefono' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.telefono" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.fax' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.fax" size="40" /></td></tr>
			<tr><td class="sezione_table_label"><fmt:message key='label.email' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.email" size="40" /></td><td class="sezione_table_label"><fmt:message key='label.pec' /></td><td><spring-form:input path="altroSoggettoPG.soggetto.personaGiuridica.pec" size="40" /></td></tr>
		</table>
		<div class="titolo_sottosezione"><fmt:message key='label.in-qualita-di' /></div>
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.ruolo' /></td>
				<td>
				<spring-form:select path="altroSoggettoPG.tipoRapporto.ruolo" cssErrorClass="validation_error_input">
					<spring-form:option value=""></spring-form:option>
					<c:forEach items="${tipiSoggettoPG }" var="tp2"><spring-form:option value="${tp2.tiposoggetto}">${tp2.tiposoggetto}</spring-form:option></c:forEach>
				</spring-form:select>
				</td>
			</tr>
		</table>
		<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
				<input type="submit" value="<fmt:message key='button.conferma' />" id="submit_conferma_altro_soggetto_pg" />
				<input type="submit" value="<fmt:message key='button.elimina' />" id="submit_elimina_altro_soggetto_pg" />
			</td>
		</tr>
		</table>
		</div>
		</div>	
	</div>
	</spring-form:form>
	</div>
	
	<%@ include file="../includes/pager.jsp" %>
</body>
</html>