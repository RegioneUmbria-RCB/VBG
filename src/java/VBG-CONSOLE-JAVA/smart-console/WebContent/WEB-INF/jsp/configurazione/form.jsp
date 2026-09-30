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
			<fmt:message key="label.configurazioni_dati_generali" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.configurazioni_dati_generali" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
		<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../configurazione/create" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="configurazione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="configurazione" />
		    </jsp:include>
			<table  width="100%">
				<tr>
					<td>
						<c:choose>
						<c:when test="${configurazione.configurazione.id.software eq 'TT'}">
							<fmt:message key="label.denominazione_comune" />
						</c:when>
						<c:otherwise>
							<fmt:message key="label.denominazione_sportello" />
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<spring-form:input id="denominazione_id" path="configurazione.denominazione" size="70" />
						<spring-form:errors path="configurazione.denominazione" cssClass="error"/>
					</td>
				</tr> 
				<c:if test="${!isComuniAssociati}">
				<tr>
					<td>
						<fmt:message key="label.codice_accreditamento" />
					</td>
					<td>
						<spring-form:input id="codiceaccreditamento_id" path="comuniassociatisoftware.codiceAccreditamento" size="30" />
						<spring-form:errors path="comuniassociatisoftware.codiceAccreditamento" cssClass="error"/>
					</td>
				</tr>
				<c:choose>
					<c:when test="${configurazione.configurazione.id.software eq 'TT'}">
						
						<tr>
							<td>
								<fmt:message key="label.codiceamministrazione_ipa" />
							</td>
							<td>
								<spring-form:input id="codiceamministrazioneIpa_id" path="comuniassociati.codiceamministrazioneIpa" size="20" />
								<spring-form:errors path="comuniassociati.codiceamministrazioneIpa" cssClass="error"/>
								<init:help idHelp="help_codiceamministrazioneIpa_id" textKey="help.configurazione.codiceamministrazione_ipa"/>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="label.cf_pi_ente" />
							</td>
							<td>
								<spring-form:input id="codicefiscale_id" path="comuniassociati.codicefiscale" size="20" />
								<spring-form:errors path="comuniassociati.codicefiscale" cssClass="error"/>
							</td>
						</tr>
						<c:if test="${isPayerAdapter eq true}">
						<tr>
							<td>
								<fmt:message key="label.codice_ente_payer" />
							</td>
							<td>
								<spring-form:input id="codiceEntePayer_id" path="comuniassociati.codiceEntePayer" size="5" maxlength="5" />
								<spring-form:errors path="comuniassociati.codiceEntePayer" cssClass="error"/>
								<init:help idHelp="help_codiceEntePayer_id" textKey="help.configurazione.codice_ente_payer"/>
							</td>
						</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
								<fmt:message key="label.codice_aoo" />
							</td>
							<td>
								<spring-form:input id="codiceaoo_id" path="comuniassociatisoftware.codiceAoo" size="20" />
								<spring-form:errors path="comuniassociatisoftware.codiceAoo" cssClass="error"/>
								<init:help idHelp="help_codiceaoo_id" textKey="help.configurazione.codiceaoo"/>
							</td>
						</tr>		
					</c:otherwise>
				</c:choose>
                </c:if>
				<tr>
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td>
						<spring-form:input id="indirizzo_id" path="configurazione.indirizzo" size="70" />
						<spring-form:errors path="configurazione.indirizzo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.cap" />
					</td>
					<td>
						<spring-form:input id="cap_id" path="configurazione.cap" size="8" />
						<spring-form:errors path="configurazione.cap" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.provincia" />
					</td>
					<td>
						<spring-form:input id="provincia_id" path="configurazione.provincia" size="2" maxlength="2" />
						<spring-form:errors path="configurazione.provincia" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.telefono" />
					</td>
					<td>
						<spring-form:input id="denominazione_id" path="configurazione.telefono" size="10" />
						<spring-form:errors path="configurazione.telefono" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.fax" />
					</td>
					<td>
						<spring-form:input id="denominazione_id" path="configurazione.fax" size="10" />
						<spring-form:errors path="configurazione.fax" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${configurazione.configurazione.id.software eq 'TT'}">
				<tr>
					<td>
						<fmt:message key="label.firma_digitale" />
					</td>
					<td>
					<spring-form:select path="configurazione.seFirmadigitale">
						<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
						<spring-form:option value="<%=WebConstants.S%>"><fmt:message key="label.attiva" /></spring-form:option>
						<spring-form:option value="<%=WebConstants.N%>"><fmt:message key="label.non_attiva" /></spring-form:option>
					</spring-form:select>
					<spring-form:errors path="configurazione.seFirmadigitale" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.firma_genera_password" />
					</td>
					<td>
					<spring-form:select path="configurazione.passwordautomatica">
						<spring-form:option value="0" ><fmt:message key="label.nessuno" /></spring-form:option>
						<spring-form:option value="1"><fmt:message key="label.tutti" /></spring-form:option>
						<spring-form:option value="2"><fmt:message key="label.solo_tecnici" /></spring-form:option>
					</spring-form:select>
					<spring-form:errors path="configurazione.passwordautomatica" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.pi_cf_obligatori" />
					</td>
					<td>
						<spring-form:checkbox id="pi_cf_obligatori_id" path="configurazione.codfisobblig" value="1" />
						<spring-form:errors path="configurazione.codfisobblig" cssClass="error"/>
						<fmt:message key="label.help.pi_cf_obligatori" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.url_logout" />
					</td>
					<td>
						<spring-form:input id="url_logout_id" path="configurazione.urllogout" size="70" />
						<spring-form:errors path="configurazione.urllogout" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.tipo_login" />
					</td>
					<td>
						<spring-form:checkbox id="tipo_login_id" path="configurazione.tipoLogin" value="true" />
						<spring-form:errors path="configurazione.tipoLogin" cssClass="error"/>
						<fmt:message key="label.help.tipo_login" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.doc_modello_tipo_comune" /></td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
		       			<jsp:param name="idElemento" value="oggettoIdCodice1" />
		   				<jsp:param name="codiceOggetto" value="${configurazione.configurazione.oggettomoddoctipo.id.codice}" />
		   				<jsp:param name="idComuneOggetto" value="${configurazione.configurazione.oggettomoddoctipo.id.idcomune}" />
		   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice1" />
		   				<jsp:param name="nomefileId" value="oggetto_nomefile1" />
	    			    <jsp:param name="overrideExtensionsAllowed" value="doc|rtf"/>	    			    
	    			</jsp:include>
    				<spring-form:hidden path="configurazione.oggettomoddoctipo.id.codice" id="oggetto_id_codice1"/>
    				<spring-form:hidden path="configurazione.oggettomoddoctipo.nomefile" id="oggetto_nomefile1"/>
    				<spring-form:errors path="configurazione.oggettomoddoctipo.id" cssClass="error"/>
				</td>
				</tr>
				 <tr>
			    	<td>	
						<fmt:message key="label.modello_sistema" />				
					</td>		   
					<td>
						<fmt:message key="label.scarica_ultima_versione_doc" />:	
					   <a href="<%=BackofficeNETConstants.getUrlTo(request, BackofficeNETConstants.getURL_RTF_BASE_DOC(), null, null, true) %>" target="blank" title='<fmt:message key="letteretipo.label.modello.sistema.download"/>'>base.doc</a>
					</td>
				</tr>
				</c:if>
				
				<tr>
					<td>
						<fmt:message key="label.responsabile_sportello_servizio"/>
					</td>
					<td>
					<spring-form:input id="responsabile_id" path="configurazione.responsabili.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabile_hidden')" onkeydown="javascript:return searchAll(this,event)" size="60"/>
						<init:autocompleter methodAjax="findResponsabiliBySoftware.htm" idHidden="responsabile_hidden" idInput="responsabile_id" inputTitleKey="label.ricerca_responsabile"></init:autocompleter>
						<spring-form:errors path="configurazione.responsabili" cssClass="error"/> 
						<spring-form:hidden id="responsabile_hidden" path="configurazione.responsabili.id.codice"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email_entrata" />
					</td>
					<td>
						<spring-form:input id="emailresponsabile_id" path="configurazione.emailresponsabile" size="70" />
						<spring-form:errors path="configurazione.emailresponsabile" cssClass="error"/>
						<fmt:message key="label.utilizzata_per_ricevere_posta"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email_pec"/>
					</td>
					<td>
						<spring-form:input id="emailresponsabilepec_id" path="configurazione.emailresponsabilepec" size="70" />
						<spring-form:errors path="configurazione.emailresponsabilepec" cssClass="error"/>
						<fmt:message key="label.utilizzata_per_ricevere_posta_certif"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.orari_apertura" />
					</td>
					<td>
						<spring-form:textarea id="orario_id" path="configurazione.orario" cols="40" rows="3" />
						<spring-form:errors path="configurazione.orario" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione_aggiuntiva"/>
					</td>
					<td>
						<spring-form:textarea id="descrizione_id" path="configurazione.descrizione" cols="40" rows="3" />
						<spring-form:errors path="configurazione.descrizione" cssClass="error"/>
					</td>
				</tr>
				
				<!-- DATI BOLLO VIRTUALE -->
				<!-- START -->
				<!-- ###################################################################################################### -->
				<!-- ###################################################################################################### -->
				<c:if test="${configurazione.configurazione.id.software eq 'TT'}">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.parametri_bollo_virtuale"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.estremi_bollo_virtuale" />
					</td>
					<td>
						<spring-form:textarea id="bollo_virtuale_id" path="configurazione.estremibollovirtuale" cols="40" rows="3" />
						<spring-form:errors path="configurazione.estremibollovirtuale" cssClass="error"/>
					</td>
				</tr>				
				</c:if>
				<!-- DATI COMUNI ASSOACIATI -->
				<!-- START -->
				<!-- ###################################################################################################### -->
				<!-- ###################################################################################################### -->
				
				<%-- 
				<c:if test="${isComuniAssociati eq true}">
				--%>
				<%
					String displayInstazione_comuniassociati= "display:none;";
					String styleComuniIntestazioniAssociati = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_INTESTAZIONI_COMUNIASSOCIATI)).equals("1")) {
					    displayInstazione_comuniassociati = "";
					    styleComuniIntestazioniAssociati="sezioneDatiMeno";
					} else {
					    displayInstazione_comuniassociati = "display:none;";
					    styleComuniIntestazioniAssociati="sezioneDatiPiu";
					}
				%>
				<c:choose>
				<c:when test="${configurazione.configurazione.id.software eq 'TT'}" >
				<tr class="titoloSezione">
					<td colspan="2">
						<a class="<%=styleComuniIntestazioniAssociati%>" id="id_link_intestazioni_comuni_associati" href="javascript:showHidePanel('id_intestazioni_comuni_associati_table', 'id_link_intestazioni_comuni_associati', '<%= WebConstants.CONF_UTENTE_INTESTAZIONI_COMUNIASSOCIATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.intestazione_comuni_associati"/>">
							<label for="id_link_intestazioni_comuni_associati"><fmt:message key="label.instazione_stampe"/></label>
						</a>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<tr class="titoloSezione">
					<td colspan="2">
						<a class="<%=styleComuniIntestazioniAssociati%>" id="id_link_intestazioni_comuni_associati" href="javascript:showHidePanel('id_intestazioni_comuni_associati_table', 'id_link_intestazioni_comuni_associati', '<%= WebConstants.CONF_UTENTE_INTESTAZIONI_COMUNIASSOCIATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.intestazione_comuni_associati_altri_Software"/>">
							<label for="id_link_intestazioni_comuni_associati">${software.descrizione}&nbsp;-&nbsp;<fmt:message key="label.instazione_stampe"/></label>
						</a>
					</td>
				</tr>
				</c:otherwise>
				</c:choose>
				<tr id="id_intestazioni_comuni_associati_table" style="<%=displayInstazione_comuniassociati%>;">
					<td><fmt:message key="label.stemma" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp" >
			       			<jsp:param name="idElemento" value="oggettoIdCodice2" />
			   				<jsp:param name="codiceOggetto" value="${configurazione.comuniassociatisoftware.oggetti.id.codice}" />
			   				<jsp:param name="idComuneOggetto" value="${configurazione.comuniassociatisoftware.oggetti.id.idcomune}" />
			   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice2" />
			   				<jsp:param name="nomefileId" value="oggetto_id_nomefile2" />
		    			    <jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|png"/>
	    				</jsp:include>
    				<spring-form:hidden path="comuniassociatisoftware.oggetti.id.codice" id="oggetto_id_codice2"/>
    				<spring-form:hidden path="comuniassociatisoftware.oggetti.nomefile" id="oggetto_id_nomefile2"/>
    				<spring-form:errors path="comuniassociatisoftware.oggetti.id.codice" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_intestazioni_comuni_associati_table" style="<%=displayInstazione_comuniassociati%>;">
					<td>
						<fmt:message key="label.intestazione_1" />
					</td>
					<td>
						<spring-form:input id="intestazione_1_id" path="comuniassociatisoftware.siIntestazione1" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siIntestazione1" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_intestazioni_comuni_associati_table" style="<%=displayInstazione_comuniassociati%>;">
					<td>
						<fmt:message key="label.intestazione_2" />
					</td>
					<td>
						<spring-form:input id="intestazione_ì2_id" path="comuniassociatisoftware.siIntestazione2" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siIntestazione2" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_intestazioni_comuni_associati_table" style="<%=displayInstazione_comuniassociati%>;">
					<td>
						<fmt:message key="label.intestazione_3" />
					</td>
					<td>
						<spring-form:input id="intestazione_3_id" path="comuniassociatisoftware.siIntestazione3" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siIntestazione3" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_intestazioni_comuni_associati_table" style="<%=displayInstazione_comuniassociati%>;">
					<td>
						<fmt:message key="label.pie_pagina_1" />
					</td>
					<td>
						<spring-form:input id="pie_pagina_1_id" path="comuniassociatisoftware.siPdp1" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siPdp1" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_intestazioni_comuni_associati_table" style="<%=displayInstazione_comuniassociati%>;">
					<td>
						<fmt:message key="label.pie_pagina_2" />
					</td>
					<td>
						<spring-form:input id="pie_pagina_2_id" path="comuniassociatisoftware.siPdp2" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siPdp2" cssClass="error"/>
					</td>
				</tr>
				<%-- 
				</c:if>
				--%>
			</table>
			<script type='text/javascript'>
				$('denominazione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertOrUpdate.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<c:if test="${configurazione.displayMode == configurazione.displayConstants.VIEW && isComuniAssociati}">	
				<li><a href="javascript:doHref('createComuniassociatisoftware.htm?software=${configurazione.configurazione.id.software}','')"><fmt:message key="button.parametri" /></a></li>
			</c:if>
			<c:if test="${configurazione.configurazione.id.software ne 'TT' && configurazione.displayMode == configurazione.displayConstants.VIEW}">
			    <li><a href="javascript:historySet('${_urlback}','../mailconfig/create.htm','');"><fmt:message key="button.parametri_mail" /></a></li>				
			</c:if>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>