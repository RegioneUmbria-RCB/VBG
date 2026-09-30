<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>	
			<fmt:message key="label.configurazioni_dati_tecnici" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.configurazioni_dati_tecnici" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="configurazione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="configurazione" />
		    </jsp:include>
			<table  width="100%" >
			
			
<%if( ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT) ){ %>



				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.configurazioni_dati_tecnici"/></td>
				</tr>
				<tr>
					<td>
						<label class="required">*</label> <fmt:message key="label.codice_amministrazione_sportello_unico" />
					</td>
					<td>
						<spring-form:input cssStyle="text-align: right;" id="codammsportellounico_id" path="configurazione.codammsportellounico" size="4"/>
						<spring-form:errors path="configurazione.codammsportellounico" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="required">*</label> <fmt:message key="label.codice_tutte_amministrazioni" />
					</td>
					<td>
						<spring-form:input  cssStyle="text-align: right;" id="codicetutteamministrazioni_id" path="configurazione.codicetutteamministrazioni" size="4"/>
						<spring-form:errors path="configurazione.codicetutteamministrazioni" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="required">*</label> <fmt:message key="label.codice_stessa_amministrazione" />
					</td>
					<td>
						<spring-form:input  cssStyle="text-align: right;" id="codicelastessaamministrazione_id" path="configurazione.codicelastessaamministrazione" size="4"/>
						<spring-form:errors path="configurazione.codicelastessaamministrazione" cssClass="error"/>
					</td>
				</tr>

<%}else{ %>
			
			
			<!-- GESTIONE ISTANZA ( NUMERAZIONE) -->
				<%
					String displayGestione_istanza_numerazione= "display:none;";
					String styleGestione_istanza_numerazione = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_NUMERAZIONE)).equals("1")) {
					    displayGestione_istanza_numerazione = "";
					    styleGestione_istanza_numerazione="sezioneDatiMeno";
					} else {
					    displayGestione_istanza_numerazione = "display:none;";
					    styleGestione_istanza_numerazione="sezioneDatiPiu";
					}
				%>
			    <tr class="titoloSezione">
					<td colspan="3">
						<a class="<%=styleGestione_istanza_numerazione%>" id="id_link_gestione_istanza_numerazione" href="javascript:showHidePanel('id_id_link_gestione_istanza_numerazione_table', 'id_link_gestione_istanza_numerazione', '<%= WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_NUMERAZIONE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.gestione_istanze"/>&nbsp;
						<fmt:message key="label.numerazione"/>">
							<label for="id_link_gestione_istanza_numerazione"><fmt:message key="label.gestione_istanze"/>&nbsp;<fmt:message key="label.numerazione"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_numerazione_table" style="<%=displayGestione_istanza_numerazione%>;">
					<td width="25%">
						<fmt:message key="label.numerazione_automatica_istanza" />
					</td>
					<td width="10%">
						<spring-form:checkbox id="numerazione_id" path="configurazione.numerazione" value="1" onclick="hideAndClearField(this,'progressivo_istanze_id','flagAttivacontatorealberoproc_id','flagNoNumeroistanza_id','flagIgnoraalfanum_id')"/>
						<spring-form:errors path="configurazione.numerazione" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.numerazione_automatica_istanza" />
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_numerazione_table" style="<%=displayGestione_istanza_numerazione%>;">
					<td colspan="1"></td>
					<td colspan="2" title="<fmt:message key="label.title_flag_tipologia_richiedente"/>">
						<spring-form:input  id="progressivo_istanze_id" path="configurazione.progressivoistanze" cssStyle="text-align:right;"  size="8"/>
						<spring-form:errors path="configurazione.progressivoistanze" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_numerazione_table" style="<%=displayGestione_istanza_numerazione%>;">
					<td>
						<fmt:message key="label.flag_attiva_contatore_alberoproc" />
					</td>
					<td>
						<spring-form:checkbox id="flagAttivacontatorealberoproc_id" path="configurazione.flagAttivacontatorealberoproc" value="1" />
						<spring-form:errors path="configurazione.flagAttivacontatorealberoproc" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.flag_attiva_contatore_alberoproc" />
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_numerazione_table" style="<%=displayGestione_istanza_numerazione%>;">
					<td>
						<fmt:message key="label.flag_no_numeroistanza" />
					</td>
					<td>
						<spring-form:checkbox id="flagNoNumeroistanza_id" path="configurazione.flagNoNumeroistanza" value="1" onclick="disabilitaCheckBox('flagIgnoraalfanum_id',this)" />
						<spring-form:errors path="configurazione.flagNoNumeroistanza" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.flag_no_numeroistanza" />
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_numerazione_table" style="<%=displayGestione_istanza_numerazione%>;">
					<td>
						<fmt:message key="label.flag_ignoraalfanum" />
					</td>
					<td>
						<spring-form:checkbox id="flagIgnoraalfanum_id" path="configurazione.flagIgnoraalfanum" value="1" />
						<spring-form:errors path="configurazione.flagIgnoraalfanum" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.flag_ignoraalfanum" />
					</td>
				</tr>
				
				<!-- GESTIONE ISTANZA ( GENERALE) -->
				<%
					String displayGestione_istanza_generale= "display:none;";
					String styleGestione_istanza_generale = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_GENERALE)).equals("1")) {
					    displayGestione_istanza_generale = "";
					    styleGestione_istanza_generale="sezioneDatiMeno";
					} else {
					    displayGestione_istanza_generale = "display:none;";
					    styleGestione_istanza_generale="sezioneDatiPiu";
					}
				%>
			    <tr class="titoloSezione">
					<td colspan="3">
						<a class="<%=styleGestione_istanza_generale%>" id="id_link_gestione_istanza_generale" href="javascript:showHidePanel('id_id_link_gestione_istanza_generale_table', 'id_link_gestione_istanza_generale', '<%= WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_GENERALE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.gestione_istanze"/>&nbsp;
						<fmt:message key="label.generale"/>">
							<label for="id_link_gestione_istanza_generale"><fmt:message key="label.gestione_istanze"/>&nbsp;<fmt:message key="label.generale"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_generale_table" style="<%=displayGestione_istanza_generale%>;">
					<td>
						<fmt:message key="label.flag_tipologia_richiedente" />
					</td>
					<td title="<fmt:message key="label.title_flag_tipologia_richiedente"/>">
						<spring-form:checkbox id="flagRichiedentepf_id" path="configurazione.flagRichiedentepf" value="1" />
						<spring-form:errors path="configurazione.flagRichiedentepf" cssClass="error"/>
					</td>
					<td width="65%" title="<fmt:message key="label.title_flag_tipologia_richiedente"/>">
						<fmt:message key="label.help.flag_tipologia_richiedente" />
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_generale_table" style="<%=displayGestione_istanza_generale%>;">
					<td>
						<fmt:message key="label.flag_visibilita_azienda" />
					</td>
					<td title="<fmt:message key="label.title_flag_visibilita_azienda"/>">
						<spring-form:checkbox id="flagAziendarappresentata_id" path="configurazione.flagAziendarappresentata" value="1" />
						<spring-form:errors path="configurazione.flagAziendarappresentata" cssClass="error"/>
					</td>
					<td title="<fmt:message key="label.title_flag_visibilita_azienda"/>" width="65%">
						<fmt:message key="label.help.flag_visibilita_azienda" />
					</td>
				</tr>
				<tr id="id_id_link_gestione_istanza_generale_table" style="<%=displayGestione_istanza_generale%>;">
					<td>
						<fmt:message key="label.disabilita_inserimento_stradario" />
					</td>
					<td  title="<fmt:message key="label.title_disabilita_inserimento_stradario"/>">
						<spring-form:checkbox id="flagVietainsstrdaistanze_id" path="configurazione.flagVietainsstrdaistanze" value="1" />
						<spring-form:errors path="configurazione.flagVietainsstrdaistanze" cssClass="error"/>
					</td>
					<td title="<fmt:message key="label.title_disabilita_inserimento_stradario"/>" width="65%">
						<fmt:message key="label.help.disabilita_inserimento_stradario" />
					</td>
				</tr>
				<!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////// -->
				
				
				<!-- GESTIONE ISTANZA (ELABORAZIONI/MOVIMENTI) -->
				<!-- DA GESTIRE -->

				<!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////// -->

				<!-- GESTIONE ISTANZA (PROTOCOLLO) -->
				<%
					String displayGestione_istanza_protocollo= "display:none;";
					String styleGestione_istanza_protocollo = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_PROTOCOLLO)).equals("1")) {
					    displayGestione_istanza_protocollo = "";
					    styleGestione_istanza_protocollo="sezioneDatiMeno";
					} else {
					    displayGestione_istanza_protocollo = "display:none;";
					    styleGestione_istanza_protocollo="sezioneDatiPiu";
					}
				%>
			    <tr class="titoloSezione">
					<td colspan="3">
						<a class="<%=styleGestione_istanza_protocollo%>" id="id_link_gestione_istanza_protocollo" href="javascript:showHidePanel('id_link_gestione_istanza_protocollo_table', 'id_link_gestione_istanza_protocollo', '<%= WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_PROTOCOLLO %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.gestione_istanze"/>&nbsp;
						<fmt:message key="label.protocollo"/>">
							<label for="id_link_gestione_istanza_protocollo"><fmt:message key="label.gestione_istanze"/>&nbsp;<fmt:message key="label.protocollo"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_link_gestione_istanza_protocollo_table" style="<%=styleGestione_istanza_protocollo%>;">
					<td>
						<fmt:message key="label.flag_num_protocollo_generale_oblig" />
					</td>
					<td>
						<spring-form:checkbox id="protgenobblig_id" path="configurazione.protgenobblig" value="1" />
						<spring-form:errors path="configurazione.protgenobblig" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.flag_num_protocollo_generale_obli" />
					</td>
				</tr>
				<tr id="id_link_gestione_istanza_protocollo_table" style="<%=styleGestione_istanza_protocollo%>;">
					<td>
						<fmt:message key="label.flag_num_protocollo_generale_inserim" />
					</td>
					<td>
						<spring-form:checkbox id="flagProtgeninser_id" path="configurazione.flagProtgeninser" value="1" />
						<spring-form:errors path="configurazione.flagProtgeninser" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.flag_num_protocollo_generale_inserim" />
					</td>
				</tr>
				<tr id="id_link_gestione_istanza_protocollo_table" style="<%=styleGestione_istanza_protocollo%>;">
					<td>
						<fmt:message key="label.num_protocollo_generale_modifica" />
					</td>
					<td>
						<spring-form:checkbox  id="flagProtgenmodif_id" path="configurazione.flagProtgenmodif" value="1" />
						<spring-form:errors path="configurazione.flagProtgenmodif" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.num_protocollo_generale_modific" />
					</td>
				</tr>
				<!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////// -->
				
				<!-- GESTIONE VARIE -->
				
				<%
					String displayGestione_varie= "display:none;";
					String styleGestione_varie = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VARIE)).equals("1")) {
					    displayGestione_varie = "";
					    styleGestione_varie="sezioneDatiMeno";
					} else {
					    displayGestione_varie = "display:none;";
					    styleGestione_varie="sezioneDatiPiu";
					}
				%>
			    <tr class="titoloSezione">
					<td colspan="3">
						<a class="<%=styleGestione_varie%>" id="id_link_varie" href="javascript:showHidePanel('id_link_varie_table', 'id_link_varie', '<%= WebConstants.CONF_UTENTE_VARIE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.varie"/>">
							<label for="id_link_varie"><fmt:message key="label.varie"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_link_varie_table" style="<%=displayGestione_varie%>;">
					<td>
						<fmt:message key="label.validita_news" />
					</td>
					<td>
						<spring-form:input id="validnews_id" path="configurazione.validnews" cssStyle="text-align:right;"  size="7" />
						<spring-form:errors path="configurazione.validnews" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.validita_news" />
					</td>
				</tr>
				<tr id="id_link_varie_table" style="<%=displayGestione_varie%>;">
					<td>
						<fmt:message key="label.flag_aggiorna_endo_automaticamente" />
					</td>
					<td>
						<spring-form:checkbox id="aggautoendo_id" path="configurazione.aggautoendo" value="1" />
						<spring-form:errors path="configurazione.aggautoendo" cssClass="error"/>
					</td>
					<td width="65%">
						<fmt:message key="label.help.flag_aggiorna_endo_automaticamente" />
					</td>
				</tr>
				<tr id="id_link_varie_table" style="<%=displayGestione_varie%>;">
					<td><fmt:message key="label.data_nuovo_movimento" /></td>
					<td colspan="2"><spring-form:select id="flagDatanuovomov_id" path="configurazione.flagDatanuovomov">
					    <spring-form:option value="0"><fmt:message key="label.data_scadenza" /></spring-form:option>
					    <spring-form:option value="1"><fmt:message key="label.data_odierna" /></spring-form:option>
					</spring-form:select> 
	                </td>
				</tr>
				<script type='text/javascript'>
				$('id_id_link_gestione_istanza_numerazione_table').focus();
				//Disabilita il checkbox passato (tramite l'id)
				//Se un ulteriore checkbox passato (tramite l'id) è selezionato 
				function disabilitaCheckBox(id,id1)
				{
					if(id1.checked)
					{
						document.getElementById(id).disabled = true;
						document.getElementById(id).checked = false;
					}else
					{
						document.getElementById(id).disabled = false;
					}
				}
				//Nasconde e disabilita i campi passati (tramite l'id)
				//Se un ulteriore checkbox passato (tramite l'id) è selezionato 
				//Il campo di testo non verrà pulito,ma verrà mantenuto il valore
				//idControll: rappresenta il checkbox su cui si fa il controllo
				function hideAndClearField(idControll,fieldText,fieldChecbox1,fieldChecbox2,fieldChecbox3)
				{
					if(idControll.checked==false)
					{
						document.getElementById(fieldChecbox1).disabled = true;
						document.getElementById(fieldChecbox2).disabled = true;
						document.getElementById(fieldChecbox3).disabled = true;
						document.getElementById(fieldText).disabled = true;
						document.getElementById(fieldChecbox1).checked = false;
						document.getElementById(fieldChecbox2).checked = false;
						document.getElementById(fieldChecbox3).checked = false;
					}else
					{
						document.getElementById(fieldChecbox1).disabled = false;
						document.getElementById(fieldChecbox2).disabled = false;
						document.getElementById(fieldChecbox3).disabled = false;
						document.getElementById(fieldText).disabled = false;
					}
				}
				
			</script>	
				<!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<%} %>								
			</table>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertOrUpdateComuniassociatidatitecnici.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
		
	</div>
</body>
</html>