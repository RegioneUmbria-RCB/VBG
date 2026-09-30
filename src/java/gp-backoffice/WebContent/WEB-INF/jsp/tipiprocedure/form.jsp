<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Tipiprocedure"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipiprocedure.id.codice==null}">
			<fmt:message key="tipiprocedure.label.nuovo_tipiprocedure.title" />
		</c:if> 
		<c:if test="${tipiprocedure.id.codice!=null}">
			<fmt:message key="tipiprocedure.label.dettaglio_tipiprocedure.title" />
		</c:if>
	</title>
</head>
<body>
 
 
    <%
      String  swSettato1="display:none;";
      String  swTT1="display:inline;";
      String hideField="display:none;";
      Tipiprocedure tipiprocedure=(Tipiprocedure)request.getAttribute("tipiprocedure");
      String detEfficacia=tipiprocedure.getDeterminazioneefficacia();
      if (detEfficacia!=null && detEfficacia.equals(WebConstants.MS)) {
        hideField="";
      }
      
    %>
    
    
	<span class="titoloPagina">
		<c:if test="${tipiprocedure.id.codice==null}">
			<fmt:message key="tipiprocedure.label.nuovo_tipiprocedure.title" />
		</c:if> 
		<c:if test="${tipiprocedure.id.codice!=null}">
			<fmt:message key="tipiprocedure.label.dettaglio_tipiprocedure.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	
	<script type="text/javascript">

	
	 
	jQuery(document).ready(function(){	
	 if(${tipiprocedure.id.codice!=null && isMovimentoAvvioDefaultPresent==false})
	    {
    		alert('<fmt:message key="tipiprocedure.label.movimento_default_non_presente"/>');
	    }
		if(${tipiprocedure.flagChiusuraAut eq false or  empty tipiprocedure.flagChiusuraAut}){
			jQuery('#statochiusuraistanza_id_tr').hide();	
		}else{
			jQuery('#statochiusuraistanza_id_tr').show();
		}	
	}
	);
    
	function tuttiSw(){
		if( $('id_flag1') )
		{
			if($('id_flag1').checked){
				
			    $('movimetoDeterminazione_id1').style.display="inline";
			    $('movimetoDeterminazione_id2').style.display="none";
			}else
			{
				$('movimetoDeterminazione_id1').style.display="none";
				$('movimetoDeterminazione_id2').style.display="inline";
			}
		}
	
		if($('id_flag2').checked){
		    $('tipimovimentoTrasmissioneNegativa_id1').style.display="inline";
		    $('tipimovimentoTrasmissioneNegativa_id2').style.display="none";
		}else
		{
			$('tipimovimentoTrasmissioneNegativa_id1').style.display="none";
			$('tipimovimentoTrasmissioneNegativa_id2').style.display="inline";
		}
	
	
        if($('id_flag3').checked){
			
		    $('tipimovimentoChiusura_id1').style.display="inline";
		    $('tipimovimentoChiusura_id2').style.display="none";
		}else
		{
			$('tipimovimentoChiusura_id1').style.display="none";
			$('tipimovimentoChiusura_id2').style.display="inline";
		}
	
	
        if($('id_flag4').checked){
			
		    $('tipimovimentoEsitoAut_id1').style.display="inline";
		    $('tipimovimentoEsitoAut_id2').style.display="none";
		}else
		{
			$('tipimovimentoEsitoAut_id1').style.display="none";
			$('tipimovimentoEsitoAut_id2').style.display="inline";
		}
	
	
        if($('id_flag5').checked){
			
		    $('letteretipo_id1').style.display="inline";
		    $('letteretipo_id2').style.display="none";
		}else
		{
			$('letteretipo_id1').style.display="none";
			$('letteretipo_id2').style.display="inline";
		}
        if($('id_flag6').checked){
			
		    $('tipimovimentoCds_id1').style.display="inline";
		    $('tipimovimentoCds_id2').style.display="none";
		}else
		{
			$('tipimovimentoCds_id1').style.display="none";
			$('tipimovimentoCds_id2').style.display="inline";
		}
        if($('id_flag7').checked){
			
		    $('tipimovimentoChiusuraCds_id1').style.display="inline";
		    $('tipimovimentoChiusuraCds_id2').style.display="none";
		}else
		{
			$('tipimovimentoChiusuraCds_id1').style.display="none";
			$('tipimovimentoChiusuraCds_id2').style.display="inline";
		}
        if($('id_flag8').checked){
			
		    $('tipimovimentoIntegrazioneDocumentale_id1').style.display="inline";
		    $('tipimovimentoIntegrazioneDocumentale_id2').style.display="none";
		}else
		{
			$('tipimovimentoIntegrazioneDocumentale_id1').style.display="none";
			$('tipimovimentoIntegrazioneDocumentale_id2').style.display="inline";
		}
  		if($('id_flag9').checked){
			
		    $('tipimovimentoChiusuraContraddittorio_id1').style.display="inline";
		    $('tipimovimentoChiusuraContraddittorio_id2').style.display="none";
		}else
		{
			$('tipimovimentoChiusuraContraddittorio_id1').style.display="none";
			$('tipimovimentoChiusuraContraddittorio_id2').style.display="inline";
		}
        if($('id_flag10').checked){
			
		    $('tipimovimentoSospensione_id1').style.display="inline";
		    $('tipimovimentoSospensione_id2').style.display="none";
		}else
		{
			$('tipimovimentoSospensione_id1').style.display="none";
			$('tipimovimentoSospensione_id2').style.display="inline";
		}
        if($('id_flag11').checked){
			
		    $('tipimovimentoPubblicita_id1').style.display="inline";
		    $('tipimovimentoPubblicita_id2').style.display="none";
		}else
		{
			$('tipimovimentoPubblicita_id1').style.display="none";
			$('tipimovimentoPubblicita_id2').style.display="inline";
		} 
        if($('id_flag12').checked){
			
		    $('tipimovimentoAudizioneContraddittorio_id1').style.display="inline";
		    $('tipimovimentoAudizioneContraddittorio_id2').style.display="none";
		}else
		{
			$('tipimovimentoAudizioneContraddittorio_id1').style.display="none";
			$('tipimovimentoAudizioneContraddittorio_id2').style.display="inline";
		}
		if($('id_flag13').checked){
			
		    $('tipimovimentoConsiglioministri_id1').style.display="inline";
		    $('tipimovimentoConsiglioministri_id2').style.display="none";
		}else
		{
			$('tipimovimentoConsiglioministri_id1').style.display="none";
			$('tipimovimentoConsiglioministri_id2').style.display="inline";
		}   
              
	}	
	function showHideField(id)
	{
		
		if(id.value=='<%=WebConstants.MS%>'){
			$('campo_movimeto_determinazione_id').appear();
			$('descrizione_mov_determinazione').appear();
		}else{
			$('campo_movimeto_determinazione_id').fade();
			$('descrizione_mov_determinazione').fade();
			
		}
	}

	
    </script>
  
	
	
	<div id="subcontent">
		<spring-form:form commandName="tipiprocedure" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipiprocedure" />
		    </jsp:include>
		    
		    <c:choose>
				<c:when test="${tipiprocedure.flagDisabilitato eq true}">
					<br class="clear" />					
					<div id="disabilitato_status_msg" class="alertLine">
			    		<b><fmt:message key="label.record_disabilitato"/></b>
			    	</div>
			    	<br class="clear" />
				</c:when>
			</c:choose>
		    <div class="titoloSezione"><fmt:message key="tipiprocedure.label.dati_generali"/></div>
			<table  width="100%">
				<tr>
					<td width="25%">
						<fmt:message key="label.procedura"/>
						<init:help idHelp="help_descr_procedura" textKey="label.descrizione_procedura"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:hidden path="id.codice"  id="codiceprocedura_id" />
						<spring-form:input id="procedura_id" path="procedura" size="75"/>
						<spring-form:errors path="procedura" cssClass="error"/>
						<c:if test="${tipiprocedure.id.codice!=null}">
							&nbsp;&nbsp;
							<c:if test="${isMovimentoAvvioDefaultPresent eq true}">
								<a href="javascript:visualizzaGraficoPopup()" title="<fmt:message key="label.grafico"/> <fmt:message key="label.procedura"/>"><fmt:message key="label.grafico"/></a>
							</c:if>
							<c:if test="${isMovimentoAvvioDefaultPresent eq false}">
								<a href="javascript:alertMovAvvioDefault()" title="<fmt:message key="label.grafico"/> <fmt:message key="label.procedura"/>"><fmt:message key="label.grafico"/></a>
							</c:if>
							<script type="text/javascript">
								function visualizzaGraficoPopup(){
									var url = "${pageContext.request.contextPath}/tipiprocedure/ajaxGrafico.htm?codice=${tipiprocedure.id.codice}";
									window.open(url,66,"top=0,left=0,width=800,height=600,menubar=no,toolbar=yes,scrollbars=yes,status=yes,resizable=yes");
								}
								function alertMovAvvioDefault(){
									alert("Specificare un movimento di avvio di default per poter visualizzare il grafico.");
								}
							</script>
						</c:if>
					</td>
				</tr>
				<!-- 
				<tr>
				  <td></td>
				  <td></td>
				</tr>
				 -->
				<tr>
					<td >
						<fmt:message key="tipiprocedure.label.info_web"/>
					</td>
					<td class="inline-ui-cell">
						<spring-form:textarea id="info_web_id" path="note" rows="4" cols="75"/>
						<spring-form:errors path="note" cssClass="error"/>
						<%-- <init:help idHelp="help_info_web" textKey="tipiprocedure.help.info_web"/> --%>
					</td>
				</tr>
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.natura_endo" />
						<init:help idHelp="help_desc_natura" textKey="tipiprocedure.label.descrizione_natura_endo"/>
					</td>
					<td >
						<spring-form:input id="naturaendo_id" path="naturaendo.natura" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'naturaendo_hidden')" size="75"/>
						<init:autocompleter methodAjax="findNaturaEndo.htm" idHidden="naturaendo_hidden" idInput="naturaendo_id" inputTitleKey="label.ricerca_natura_endo"></init:autocompleter>
						<spring-form:errors path="naturaendo" cssClass="error"/> 
						<spring-form:hidden id="naturaendo_hidden" path="naturaendo.id.codice"  />
					</td>
				</tr>
				<!-- 
				<tr>
				     <td></td>
				     <td></td>
				</tr>
				 -->
				<tr class="titoloSezione">				   
				     <td colspan="2"><fmt:message key="label.parametri_frontoffice_area_informativa"/></td>
				</tr>
				<tr>
					<td width="25%"><fmt:message key="tipiprocedure.label.nome_modello" /></td>
					<td><jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice_modello" />
						<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoModello.id.codice}" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
						<jsp:param name="nomefileId" value="oggetto_nomefile" />
					</jsp:include> 
					<spring-form:hidden path="oggettoModello.id.codice" id="oggetto_id_codice" />
					<spring-form:hidden path="oggettoModello.nomefile" id="oggetto_nomefile" />
					</td>
				</tr>
				<tr>
					<td width="25%" ><fmt:message key="tipiprocedure.label.nome_modello_on_line" /></td>
					<td><jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice_modello_online" />
						<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoModelloDomandaOnline.id.codice}" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice1" />
						<jsp:param name="nomefileId" value="oggetto_nomefile1" />
					</jsp:include> 
					<spring-form:hidden path="oggettoModelloDomandaOnline.id.codice" id="oggetto_id_codice1" />
					<spring-form:hidden path="oggettoModelloDomandaOnline.nomefile" id="oggetto_nomefile1" />
					</td>
				</tr>
				<tr>
					<td width="25%"><fmt:message key="tipiprocedure.label.nome_modello_diagramma" /></td>
					<td><jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice_modello_diagramma" />
						<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoModelloDiagramma.id.codice}" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice2" />
						<jsp:param name="nomefileId" value="oggetto_nomefile2" />
						<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg" />
					</jsp:include> 
					<spring-form:hidden path="oggettoModelloDiagramma.id.codice" id="oggetto_id_codice2" />
					<spring-form:hidden path="oggettoModelloDiagramma.nomefile" id="oggetto_nomefile2" />
					</td>
				</tr>
		        <tr>
					<td width="25%"><fmt:message key="tipiprocedure.label.nome_modello_precompilato" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp">
							<jsp:param name="idElemento" value="oggettoIdCodice_modello_precompilato" />
							<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoModelloPrecompilato.id.codice}" />
							<jsp:param name="codiceOggettoId" value="oggetto_id_codice3" />
							<jsp:param name="nomefileId" value="oggetto_nomefile3" />
						</jsp:include> 
						<spring-form:hidden path="oggettoModelloPrecompilato.id.codice" id="oggetto_id_codice3" />
						<spring-form:hidden path="oggettoModelloPrecompilato.nomefile" id="oggetto_nomefile3" />
					</td>
				</tr>
				
				<tr class="titoloSezione">				   
				     <td colspan="2"><fmt:message key="label.parametri_frontoffice_area_riservata"/></td>
				</tr>
			   <tr>
					<td width="25%">
						<fmt:message key="label.certificato_di_invio" /> 
						<init:help idHelp="helpCertificatoInvio" textKey="help.tipiprocedure.certificato_di_invio"/> 
					</td>
					<td>
						<jsp:include page="../includes/oggetti.jsp">
							<jsp:param name="idElemento" value="oggettoIdCodice_certificato_invio" />
							<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoCertificatoInvio.id.codice}" />
							<jsp:param name="codiceOggettoId" value="oggetto_id_certificato_invio" />
							<jsp:param name="nomefileId" value="nomefile_certificato_invio" />
						</jsp:include> 
						
						<spring-form:hidden path="oggettoCertificatoInvio.id.codice" id="oggetto_id_certificato_invio" />
						<spring-form:hidden path="oggettoCertificatoInvio.nomefile" id="nomefile_certificato_invio" />
					</td>
				</tr>
				<c:if test="${CART_ATTIVO eq true }">
					<tr>
						<td width="25%">
							<fmt:message key="label.ricevuta_invio_cart" /> 
							<init:help idHelp="helpRicevutaCart" textKey="help.tipiprocedure.ricevuta_invio_cart"/> 
						</td>
						<td>						
							<jsp:include page="../includes/oggetti.jsp">
								<jsp:param name="idElemento" value="oggettoIdCodice_ricevuta_cart" />
								<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoRicevutaCart.id.codice}" />
								<jsp:param name="codiceOggettoId" value="oggetto_id_ricevuta_cart" />
								<jsp:param name="nomefileId" value="nomefile_ricevuta_cart" />
							</jsp:include>						
							<spring-form:hidden path="oggettoRicevutaCart.id.codice" id="oggetto_id_ricevuta_cart" />
							<spring-form:hidden path="oggettoRicevutaCart.nomefile" id="nomefile_ricevuta_cart" />
						</td>
					</tr>
				</c:if>				
			</table>
			<div class="titoloSezione"><fmt:message key="tipiprocedure.label.parametri_elaborazione"/></div>
			<table  width="100%">
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.giorni_previsti"/>
						<init:help idHelp="help_giorni_previsti" textKey="tipiprocedure.label.descrizione_giorni_previsti"/>
					</td>
					<td class="inline-ui-cell" colspan="2">
						<spring-form:input cssStyle="text-align:right;" id="giorniPrevisti_id" path="giorni" size="4" />
						<spring-form:errors path="giorni" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.flag_elabch_termini"/>
					</td>
					<td class="inline-ui-cell" colspan="2">
						<spring-form:checkbox id="flagElabchTermini_id" path="flagElabchTermini" />
						<label for="flagElabchTermini_id"><fmt:message key="tipiprocedure.label.descrizione_flag_elabch_termini" /></label>
						<spring-form:errors path="flagElabchTermini" cssClass="error"/>
					</td>
				</tr>
				
				<tr >
					<td width="25%">
						<fmt:message key="tipiprocedure.label.flag_elabch_endo"/>
					</td>
					<td class="inline-ui-cell" colspan="2">
						<spring-form:checkbox id="flagElabchEndo_id" path="flagElabchEndo" />
						<label for="flagElabchEndo_id"><fmt:message key="tipiprocedure.label.descrizione_flag_elabch_endo" /></label>
						<spring-form:errors path="flagElabchEndo" cssClass="error"/>
					</td>
				</tr>
				<tr>
		           <td width="25%"><fmt:message key="tipiprocedure.label.determinazione_inizio_istanza" /></td>
		           <td>
		           		<spring-form:select  id="determinazioneinizioistanza_id"  path="determinazioneinizioistanza" >
				               <spring-form:option value="<%=WebConstants.PD%>" ><fmt:message key="tipiprocedure.label.data_presenta_domanda" /></spring-form:option>
				               <spring-form:option value="<%=WebConstants.UT%>" ><fmt:message key="tipiprocedure.label.data_ultima_trasmissione" /></spring-form:option>
				               <spring-form:option value="<%=WebConstants.PG%>"><fmt:message key="tipiprocedure.label.data_prot_generale" /></spring-form:option>
			            </spring-form:select>				
						<spring-form:errors path="determinazioneinizioistanza" cssClass="error"/>
					</td>
					<td>
						<div id="functions">
							<ul>
								<li><a href="javascript:confermaAggiornamento('message_dialog_confirm');"
									title="<fmt:message key="tipiprocedure.help.button_aggiorna" />"><fmt:message key="button.aggiorna" /></a>
								</li>
							</ul>
						</div>
					</td>
        		</tr>
        		
        		
<c:choose>
<%-- 
	SE LA PROCEDURA NON E' USATA NELLE ISTANZE PERMETTO DI MODIFICARE IL CALCOLO DELLA DATA DI VALIDITA' 
	
--%>
<c:when test="${isProceduraUsataInIstanze eq false }">

		<tr>
		           <td width="25%"><fmt:message key="tipiprocedure.label.determinazione_efficacia" /></td>
		           <td>
		           		<spring-form:select  id="determinazioneefficacia_id"  path="determinazioneefficacia" onchange="javascript:showHideField(this);" >
			               <spring-form:option value="<%=WebConstants.MA%>" ><fmt:message key="tipiprocedure.label.data_movimento_avvio" /></spring-form:option>
			               <spring-form:option value="<%=WebConstants.DP%>" ><fmt:message key="tipiprocedure.label.data_durata_procedimento" /></spring-form:option>
			               <spring-form:option value="<%=WebConstants.MS%>"><fmt:message key="tipiprocedure.label.data_movimento_specificato" /></spring-form:option>
			               <spring-form:option value="<%=WebConstants.NC%>"><fmt:message key="tipiprocedure.label.non_calcolare_data_validita" /></spring-form:option>
		            	</spring-form:select>				
						<spring-form:errors path="determinazioneefficacia" cssClass="error"/>
					</td>
					<td>
						<div id="functions">
							<ul>
								<li><a href="javascript:confermaAggiornamentoDataValidita('message_dialog_confirm_data_validita');"
									title="<fmt:message key="tipiprocedure.help.button_aggiorna_data_validita" />"><fmt:message key="button.aggiorna" /></a>
								</li>
							</ul>	
						</div>
					</td>
        		</tr>
        		<tr id="campo_movimeto_determinazione_id" style="<%=hideField%>">
					<td width="25%"></td>
					<td class="inline-ui-cell">
					    <div id="movimetoDeterminazione_id1" style="<%=swSettato1%>"><spring-form:input id="tipo_movimeto_determinazione_id1" path="tipimovimentoDeterminazione.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_determinazione_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_determinazione_hidden"  idInput="tipo_movimeto_determinazione_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="movimetoDeterminazione_id2" style="<%=swTT1%>"><spring-form:input id="tipo_movimeto_determinazione_id2" path="tipimovimentoDeterminazione.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_determinazione_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_determinazione_hidden"  idInput="tipo_movimeto_determinazione_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoDeterminazione" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_determinazione_hidden" path="tipimovimentoDeterminazione.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag1" onclick="tuttiSw();"/>
		                <init:help idHelp="help_archivi_base" textKey="help.tipimovimenti_archivi_base"/>
					</td>
					<td class="inline-ui-cell"><fmt:message key="tipiprocedure.label.ddeterminazione_esito" /></td>
			           <td ><spring-form:select id="determinazioneesito_id"  path="determinazioneesito" >
			               <spring-form:option value="0" ><fmt:message key="label.qualsiasi" /></spring-form:option>
			               <spring-form:option value="2" ><fmt:message key="label.positivo" /></spring-form:option>
			               <spring-form:option value="1"><fmt:message key="label.negativo" /></spring-form:option>
			            </spring-form:select>				
						<spring-form:errors path="determinazioneesito" cssClass="error"/>
					</td>
				</tr>

</c:when>
<c:otherwise>

<%-- 
	SE LA PROCEDURA E' USATA NELLE ISTANZE NON PERMETTO DI MODIFICARE IL CALCOLO DELLA DATA DI VALIDITA' 
	ALTRIMENTI AVREI PROBLEMI NELLA GESTIONE DEGLI SNAPSHOT E DEI DATI LEGATI ALLEATTIVITA'
--%>

		<tr>
		           <td width="25%"><fmt:message key="tipiprocedure.label.determinazione_efficacia" /></td>
		           <td>
						
						
		           		<spring-form:select  id="determinazioneefficacia_id"  path="determinazioneefficacia" 
		           			onchange="javascript:showHideField(this);" disabled="true">


			               <spring-form:option value="<%=WebConstants.MA%>" ><fmt:message key="tipiprocedure.label.data_movimento_avvio" /></spring-form:option>
			               <spring-form:option value="<%=WebConstants.DP%>" ><fmt:message key="tipiprocedure.label.data_durata_procedimento" /></spring-form:option>
			               <spring-form:option value="<%=WebConstants.MS%>"><fmt:message key="tipiprocedure.label.data_movimento_specificato" /></spring-form:option>
			               <spring-form:option value="<%=WebConstants.NC%>"><fmt:message key="tipiprocedure.label.non_calcolare_data_validita" /></spring-form:option>
		            	</spring-form:select>				
						
					</td>
					<td>
						<div id="functions">
							<ul>
								<li><a href="javascript:confermaAggiornamentoDataValidita('message_dialog_confirm_data_validita');"
									title="<fmt:message key="tipiprocedure.help.button_aggiorna_data_validita" />"><fmt:message key="button.aggiorna" /></a>
								</li>
							</ul>	
						</div>
					</td>
        		</tr>
        		<tr id="campo_movimeto_determinazione_id" style="<%=hideField%>">
					<td width="25%"></td>
					<td class="inline-ui-cell">
					
					
						<b>${tipiprocedure.tipimovimentoDeterminazione.descrizioneEstesa}</b>

					</td>
					<td class="inline-ui-cell"></td>
			           <td>
			           <fmt:message key="tipiprocedure.label.ddeterminazione_esito" />&nbsp;
						<spring-form:select id="determinazioneesito_id"  path="determinazioneesito" disabled="true">
			               <spring-form:option value="0" ><fmt:message key="label.qualsiasi" /></spring-form:option>
			               <spring-form:option value="2" ><fmt:message key="label.positivo" /></spring-form:option>
			               <spring-form:option value="1"><fmt:message key="label.negativo" /></spring-form:option>
			            </spring-form:select>				
						<spring-form:errors path="determinazioneesito" cssClass="error"/>
					</td>
				</tr>


</c:otherwise>
</c:choose>        		
        		
				<tr id="descrizione_mov_determinazione" style="<%=hideField%>">
				  <td></td>
				  <td><fmt:message key="tipiprocedure.label.descrizione_movimento_determinazione"/></td>
				</tr>
        		<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.num_gg_invio"/>
						<init:help idHelp="help_gg_invio" textKey="tipiprocedure.label.descrizione_num_gg_invio"/>
					</td>
					<td colspan="2"  class="inline-ui-cell">
						<spring-form:input cssStyle="text-align:right;" id="numgginvio_id" path="numgginvio" size="4" onchange="javascript:checkNumberInt(this)"/>
						<spring-form:errors path="numgginvio" cssClass="error"/>
					</td>
				</tr>
				<tr>
				<td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_trasmissione_negativa" />
						<init:help idHelp="help_mov_tramis_negativa" textKey="tipiprocedure.label.descrizione_tipimovimento_trasmissione_negativa"/>
				</td>
				<td>
					    <div id="tipimovimentoTrasmissioneNegativa_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_trasmissione_negativa_id1" path="tipimovimentoTrasmissioneNegativa.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_trasmissione_negativa_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_trasmissione_negativa_hidden"  idInput="tipimovimento_trasmissione_negativa_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoTrasmissioneNegativa_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_trasmissione_negativa_id2" path="tipimovimentoTrasmissioneNegativa.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_trasmissione_negativa_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_trasmissione_negativa_hidden"  idInput="tipimovimento_trasmissione_negativa_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoTrasmissioneNegativa" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_trasmissione_negativa_hidden" path="tipimovimentoTrasmissioneNegativa.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag2" onclick="tuttiSw();"/>
					</td>
			    </tr>
			    <!-- 
			   <tr>
				  <td></td>
				  <td><fmt:message key="tipiprocedure.label.descrizione_tipimovimento_trasmissione_negativa"/></td>
				</tr>
				 -->
			</table>
			
			<div class="titoloSezione"><fmt:message key="tipiprocedure.label.registro_provvedimento_autorizzativo"/></div>
			<table  width="100%">
			<tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_chiusura_negativa" />
						<init:help idHelp="help_mov_chiusura_neg" textKey="tipiprocedure.label.descrizione_tipimovimento_chiusura_negativa"/>
				</td>
				<td>
					    <div id="tipimovimentoChiusura_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_chiusura_negativa_id1" path="tipimovimentoChiusura.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_chiusura_negativa_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_chiusura_negativa_hidden"  idInput="tipimovimento_chiusura_negativa_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoChiusura_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_chiusura_negativa_id2" path="tipimovimentoChiusura.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_chiusura_negativa_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_chiusura_negativa_hidden"  idInput="tipimovimento_chiusura_negativa_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoChiusura" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_chiusura_negativa_hidden" path="tipimovimentoChiusura.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag3" onclick="tuttiSw();"/>
		                <init:help idHelp="help3" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>			
				<td width="25%">
					<fmt:message key="tipiprocedure.label.flag_chiusura_automatica"/>
				</td>
				<td class="inline-ui-cell">
					<spring-form:checkbox id="flagChiusuraAut_id" path="flagChiusuraAut" onclick="jQuery('#statochiusuraistanza_id_tr').toggle();"/>
					<label for="flagChiusuraAut_id"><fmt:message key="tipiprocedure.label.flag_chiusura_automatica.help" /></label>
					<spring-form:errors path="flagChiusuraAut" cssClass="error"/>
				</td>
		    </tr>		    
			<tr id="statochiusuraistanza_id_tr">
				<td>
					<fmt:message key="label.statoistanza_chiusura_automatica"/>
				</td>
				<td>
					<spring-form:select id="statochiusuraistanza_id"  path="statochiusuraistanza.id.codicestato">
                       <spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
                    <spring-form:options items="${statiistanzaList}" itemLabel="stato" itemValue="id.codicestato" />
	                </spring-form:select>
	                <init:help idHelp="help_statochiusuraistanza_id" textKey="label.statoistanza_chiusura_automatica.help" />
					<spring-form:errors path="statochiusuraistanza" cssClass="error"/>
				</td>
			</tr>
		
			
			
			<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.flag_atto_chiusura"/>
					</td>
					<td >
						<spring-form:checkbox id="flagattochiusura_id" path="flagattochiusura" />
						<spring-form:errors path="flagattochiusura" cssClass="error"/>
						<label for="flagattochiusura_id"><fmt:message key="tipiprocedure.label.descrizione_flag_atto_chiusura" /></label>
					</td>
		    </tr>
		    <tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_esito_aut" />
						<init:help idHelp="help_mov_esito_aut" textKey="tipiprocedure.label.descrizione_tipimovimento_esito_aut"/>
				</td>
				<td class="inline-ui-cell">
					    <div id="tipimovimentoEsitoAut_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_esito_aut_id1" path="tipimovimentoEsitoAut.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_esito_aut_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_esito_aut_hidden"  idInput="tipimovimento_esito_aut_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoEsitoAut_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_esito_aut_id2" path="tipimovimentoEsitoAut.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_esito_aut_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_esito_aut_hidden"  idInput="tipimovimento_esito_aut_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoEsitoAut" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_esito_aut_hidden" path="tipimovimentoEsitoAut.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag4" onclick="tuttiSw();"/>
		                <init:help idHelp="help4" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>
				<td width="25%">
					<fmt:message key="tipiprocedure.label.num_gg_validita_provv"/>
					<init:help idHelp="help_gg_val_provv" textKey="tipiprocedure.label.descrizione_tnum_gg_validita_provv"/>
				</td>
				<td>
					<spring-form:input cssStyle="text-align:right;" id="numggvaliditaprovv_id" path="numggvaliditaprovv" size="4" onchange="javascript:checkNumberInt(this)"/>
					<spring-form:errors path="numggvaliditaprovv" cssClass="error"/>
				</td>
			</tr>
			    
		    <tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.lettere_tipo" />
						<init:help idHelp="help_lett_tipo" textKey="tipiprocedure.label.descrizione_lettere_tipo"/>
				</td>
				<td>
					    <div id="letteretipo_id1" style="<%=swSettato1%>"><spring-form:input id="lettere_tipo_id1" path="letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm?codicesoftware=TT'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id1" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<div id="letteretipo_id2" style="<%=swTT1%>"><spring-form:input id="lettere_tipo_id2" path="letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id2" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<spring-form:errors path="letteretipo" cssClass="error"/> 
						<spring-form:hidden id="lettere_tipo_hidden" path="letteretipo.id.codice"  />
					    <input type="checkbox" id="id_flag5" onclick="tuttiSw();"/>
		                <init:help idHelp="help5" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			</table>
			<div class="titoloSezione"><fmt:message key="tipiprocedure.label.parametri_cds"/></div>
			<table  width="100%">
			<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.flag_prevede_cds"/>
					</td>
					<td >
						<spring-form:checkbox id="flagprevedecds_id" path="flagprevedecds" onclick="javascript:disabilitaAndClearField(this)" />
						<label for="flagprevedecds_id"><fmt:message key="tipimovimento.label.descrizione_flag_prevede_cds" /></label>
						<spring-form:errors path="flagprevedecds" cssClass="error"/>
					</td>
		   	 </tr>
		     <tr>
			    <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_cds" />
						<init:help idHelp="help_mov_cds" textKey="tipiprocedure.label.descrizione_tipimovimento_cds"/>
				</td>
				<td>
					    <div id="tipimovimentoCds_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_cds_id1" path="tipimovimentoCds.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_cds_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_cds_hidden"  idInput="tipimovimento_cds_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoCds_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_cds_id2" path="tipimovimentoCds.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_cds_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_cds_hidden"  idInput="tipimovimento_cds_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoCds" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_cds_hidden" path="tipimovimentoCds.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag6" onclick="tuttiSw();"/>
		                <init:help idHelp="help7" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
		     <tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_chiusura_cds" />
						<init:help idHelp="help6" textKey="tipiprocedure.label.descrizione_tipimovimento_chiusura_cds"/>
				</td>
				<td>
					    <div id="tipimovimentoChiusuraCds_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_chiusura_cds_id1" path="tipimovimentoChiusuraCds.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_chiusura_cds_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_chiusura_cds_hidden"  idInput="tipimovimento_chiusura_cds_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoChiusuraCds_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_chiusura_cds_id2" path="tipimovimentoChiusuraCds.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_chiusura_cds_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_chiusura_cds_hidden"  idInput="tipimovimento_chiusura_cds_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoChiusuraCds" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_chiusura_cds_hidden" path="tipimovimentoChiusuraCds.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag7" onclick="tuttiSw();"/>
		                <init:help idHelp="help8" textKey="help.tipimovimenti_archivi_base"/>
				</td>
				</tr>
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.num_gg_cds_piudata"/>
						<init:help idHelp="help9" textKey="tipiprocedure.label.descrizione_num_gg_cds_piudata"/>
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;"  id="numggcdspiudata_id" path="numggcdspiudata" size="4" onchange="javascript:checkNumberInt(this)"/>
						<spring-form:errors path="numggcdspiudata" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.num_gg_cds"/>
						<init:help idHelp="help10" textKey="tipiprocedure.label.descrizione_num_gg_cds"/>
					</td>
					<td colspan="2">
						<spring-form:input cssStyle="text-align:right;" id="numggcds_id" path="numggcds" size="4" />
						<spring-form:errors path="numggcds" cssClass="error"/>
					</td>
				</tr>
				<tr>
			   		<td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_consiglio_dei_ministri" />
					</td>
					<td>
					    <div id="tipimovimentoConsiglioministri_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_consiglio_ministri_id1" path="tipimovimentoConsiglioDeiMinistri.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_consiglio_ministri_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_consiglio_ministri_hidden"  idInput="tipimovimento_consiglio_ministri_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoConsiglioministri_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_consiglio_ministri_id2" path="tipimovimentoConsiglioDeiMinistri.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_consiglio_ministri_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_consiglio_ministri_hidden"  idInput="tipimovimento_consiglio_ministri_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoCds" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_consiglio_ministri_hidden" path="tipimovimentoConsiglioDeiMinistri.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag13" onclick="tuttiSw();"/>
					    <init:help idHelp="help13" textKey="help.tipimovimenti_archivi_base"/>
		               
					</td>
				</tr>
			</table>
			
			<div class="titoloSezione"><fmt:message key="tipiprocedure.label.altri_dati"/></div>
			<table  width="100%">
			<tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_integrazione_documentale" />
						<init:help idHelp="help_mov_doc" textKey="tipiprocedure.label.descrizione_tipimovimento_integrazione_documentale"/>
				</td>
				<td>
					    <div id="tipimovimentoIntegrazioneDocumentale_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_integrazione_documentale_id1" path="tipimovimentoIntegrazioneDocumentale.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_integrazione_documentale_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_integrazione_documentale_hidden"  idInput="tipimovimento_integrazione_documentale_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoIntegrazioneDocumentale_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_integrazione_documentale_id2" path="tipimovimentoIntegrazioneDocumentale.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_integrazione_documentale_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_integrazione_documentale_hidden"  idInput="tipimovimento_integrazione_documentale_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoIntegrazioneDocumentale" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_integrazione_documentale_hidden" path="tipimovimentoIntegrazioneDocumentale.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag8" onclick="tuttiSw();"/>
		                <init:help idHelp="help11" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.gg_richiesta_documentazione"/>
						<init:help idHelp="help_gg_doc" textKey="tipiprocedure.label.descrizione_gg_richiesta_documentazione"/>
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;" id="nautoggrichiestadoc_id" path="nautoggrichiestadoc" size="4" onchange="checkNumberInt(this)"/>
						<spring-form:errors path="nautoggrichiestadoc" cssClass="error"/>
					</td>
			</tr>
			<tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_chiusura_contraddittorio" />
						<init:help idHelp="help_mov_contr" textKey="tipiprocedure.label.descrizione_tipimovimento_chiusura_contraddittorio"/>
				</td>
				<td>
					    <div id="tipimovimentoChiusuraContraddittorio_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_chiusura_contraddittorio_id1" path="tipimovimentoChiusuraContraddittorio.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_chiusura_contraddittorio_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_chiusura_contraddittorio_hidden"  idInput="tipimovimento_chiusura_contraddittorio_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoChiusuraContraddittorio_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_chiusura_contraddittorio_id2" path="tipimovimentoChiusuraContraddittorio.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_chiusura_contraddittorio_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_chiusura_contraddittorio_hidden"  idInput="tipimovimento_chiusura_contraddittorio_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoChiusuraContraddittorio" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_chiusura_contraddittorio_hidden" path="tipimovimentoChiusuraContraddittorio.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag9" onclick="tuttiSw();"/>
		                <init:help idHelp="help12" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_sospensione" />
						<init:help idHelp="help_mov_sosp" textKey="tipiprocedure.label.descrizione_tipimovimento_sospensione"/>
				</td>
				<td>
					    <div id="tipimovimentoSospensione_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_sospensione_id1" path="tipimovimentoSospensione.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_sospensione_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_sospensione_hidden"  idInput="tipimovimento_sospensione_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoSospensione_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_sospensione_id2" path="tipimovimentoSospensione.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_sospensione_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_sospensione_hidden"  idInput="tipimovimento_sospensione_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoSospensione" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_sospensione_hidden" path="tipimovimentoSospensione.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag10" onclick="tuttiSw();"/>
		                <init:help idHelp="help14" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_pubblicita" />
						<init:help idHelp="help_mov_pubbl" textKey="tipiprocedure.label.descrizione_tipimovimento_pubblicita"/>
				</td>
				<td >
					    <div id="tipimovimentoPubblicita_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_pubblicita_id1" path="tipimovimentoPubblicita.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'ttipimovimento_pubblicita_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="ttipimovimento_pubblicita_hidden"  idInput="tipimovimento_pubblicita_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoPubblicita_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_pubblicita_id2" path="tipimovimentoPubblicita.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'ttipimovimento_pubblicita_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="ttipimovimento_pubblicita_hidden"  idInput="tipimovimento_pubblicita_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoPubblicita" cssClass="error"/> 
						<spring-form:hidden id="ttipimovimento_pubblicita_hidden" path="tipimovimentoPubblicita.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag11" onclick="tuttiSw();"/>
		                <init:help idHelp="help15" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>
			   <td width="25%">
						<fmt:message key="tipiprocedure.label.tipimovimento_audizione_contraddittorio" />
						<init:help idHelp="help_mov_audiz" textKey="tipiprocedure.label.descrizione_tipimovimento_audizione_contraddittorio"/>
				</td>
				<td>
					    <div id="tipimovimentoAudizioneContraddittorio_id1" style="<%=swSettato1%>"><spring-form:input id="tipimovimento_audizione_contraddittorio_id1" path="tipimovimentoAudizioneContraddittorio.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_audizione_contraddittorio_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice=TT'  idHidden="tipimovimento_audizione_contraddittorio_hidden"  idInput="tipimovimento_audizione_contraddittorio_id1" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<div id="tipimovimentoAudizioneContraddittorio_id2" style="<%=swTT1%>"><spring-form:input id="tipimovimento_audizione_contraddittorio_id2" path="tipimovimentoAudizioneContraddittorio.movimento" cssClass="searchbox" size="75" onchange="checkValue(this,'tipimovimento_audizione_contraddittorio_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm?codice='  idHidden="tipimovimento_audizione_contraddittorio_hidden"  idInput="tipimovimento_audizione_contraddittorio_id2" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter></div>
						<spring-form:errors path="tipimovimentoAudizioneContraddittorio" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_audizione_contraddittorio_hidden" path="tipimovimentoAudizioneContraddittorio.id.tipomovimento"  />
					    <input type="checkbox" id="id_flag12" onclick="tuttiSw();"/>
		                <init:help idHelp="help16" textKey="help.tipimovimenti_archivi_base"/>
				</td>
			</tr>
			<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.audizione_terzi_gg"/>
						<init:help idHelp="help_gg_audiz" textKey="tipiprocedure.label.descrizione_audizione_terzi_gg"/>
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;"  id="idautoaudizterzigg_id" path="idautoaudizterzigg" size="4" onchange="javascript:checkNumberInt(this)"/>
						<spring-form:errors path="idautoaudizterzigg" cssClass="error"/>
					</td>
			</tr>
			<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.codice_export"/>
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;"  id="codiceexport_id" path="codiceexport" size="4" onchange="javascript:checkNumberInt(this)"/>
						<spring-form:errors path="codiceexport" cssClass="error"/>
					</td>
			</tr>
			
			<tr class="titoloSezione">
					<td colspan="3"><fmt:message key="label.anagrafe_tributaria"/></td>
					<init:help idHelp="atribTiporichiesta_id_help" textKey="help.tiposoggetto.attributo_tipo_richiesta"/>
				</tr>
				<tr>
					<td><fmt:message key="label.tipologia_richiesta"/></td>
					<td colspan="2">
						<spring-form:input id="atribTiporichiesta_id" path="atribTiporichiesta" size="4" />						
						<spring-form:errors path="atribTiporichiesta" cssClass="error"/>
					</td>
				</tr>
			
			<tr class="titoloSezione">
				<td colspan="3"><fmt:message key="label.dati_registro_imprese.legend" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.dati_registro_imprese.tipiprocedimento" />
				</td>
				<td><jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="riTipiprocedimento_id" />
						<jsp:param name="propertyPath" value="riTipiprocedimento" />
						<jsp:param name="pathPropertyDescription"
							value="riTipiprocedimento.descrizioneEstesa" />
						<jsp:param name="pathPropertyCode" value="riTipiprocedimento.codice" />
						<jsp:param name="autocompleterAjax" value="findRiTipiprocedimento.htm" />
						<jsp:param name="titleKey"
							value="label.ricerca_dati_registro_imprese.tipiprocedimento" />
						<jsp:param  name="autocompleterInputSize" value="90"/>
					</jsp:include>
				</td>
			</tr>			
			</table>
			<script type='text/javascript'>
				$('procedura_id').focus();	
				
				//Nasconde e disabilita i campi passati alla selezione del checkbox passato
				function disabilitaAndClearField(idControll)
				{
					
					
					if(idControll.checked==true)
					{
						// controllo del campo tipo movimento cds
						document.getElementById('tipimovimento_cds_id1').disabled = false;
						document.getElementById('tipimovimento_cds_id2').disabled = false;
						document.getElementById('id_flag6').disabled = false;
						// controllo campo movimento chiusura cds
						document.getElementById('tipimovimento_chiusura_cds_id1').disabled = false;
						document.getElementById('tipimovimento_chiusura_cds_id2').disabled = false;
						document.getElementById('id_flag7').disabled = false;
						// controllo dei campi numero giorni
						document.getElementById('numggcdspiudata_id').disabled = false;
						document.getElementById('numggcds_id').disabled = false;
					}else
					{
						// controllo del campo tipo movimento cds
						document.getElementById('tipimovimento_cds_id1').disabled = true;
						document.getElementById('tipimovimento_cds_id2').disabled = true;
						document.getElementById('id_flag6').disabled = true;
						document.getElementById('tipimovimento_cds_id1').value = '';
						document.getElementById('tipimovimento_cds_id2').value = '';
						document.getElementById('id_flag6').checked = false;
						document.getElementById('tipimovimento_cds_hidden').value = '';
						// controllo campo movimento chiusura cds
						document.getElementById('tipimovimento_chiusura_cds_id1').disabled = true;
						document.getElementById('tipimovimento_chiusura_cds_id2').disabled = true;
						document.getElementById('id_flag7').disabled = true;
						document.getElementById('tipimovimento_chiusura_cds_id1').value = '';
						document.getElementById('tipimovimento_chiusura_cds_id2').value = '';
						document.getElementById('tipimovimento_chiusura_cds_hidden').value = '';
						document.getElementById('id_flag7').checked = false;
						// controllo dei campi numero giorni
						document.getElementById('numggcdspiudata_id').disabled = true;
						document.getElementById('numggcds_id').disabled = true;
						document.getElementById('numggcdspiudata_id').value = '';
						document.getElementById('numggcds_id').value = '';
						
					}
				}
			</script>	
		</spring-form:form>
		
		
		
		
	</div>
	<div id="functions">
		<ul>
			<c:if test="${tipiprocedure.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipiprocedure.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			    <li><a href="javascript:doHref('listsubprocedure.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.fasi_attuative" /></a></li>
			    <li><a href="javascript:doHref('listdocumenti.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.documenti" /></a></li>
			    <li><a href="javascript:doHref('listmodelli.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.modelli" /></a></li>
			    <li>
					<c:choose>
						<c:when test="${tipiprocedure.flagDisabilitato eq true}">
							<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.abilita"><fmt:param value="Procedure"/></fmt:message>',document.inviodati)"><fmt:message key="button.abilita" /></a>
						</c:when>
						<c:otherwise>
							<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.disabilita"><fmt:param value="Procedure" /></fmt:message>',document.inviodati)"><fmt:message key="button.disabilita" /></a>
						</c:otherwise>
					</c:choose>
				</li>
			</c:if>
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
			
		</ul>
	</div>
	<c:if test="${tipiprocedure.id.codice!=null}">
	<div class="jmesa" >
        <table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td width="20%" colspan="2"><fmt:message key="label.movimenti_avvio"/></td>
					
				</tr>
				</thead>
                <%
			    int i=0;
			    %>
				<tbody class="tbody">
				<c:forEach items="${tipiprocedure.tipiProcedureavvios}" var="var_procedure_avvio" varStatus="index">
				
					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>
							<a href="javascript:doHref('viewMovimentoAvvio.htm?codiceprocedura=${var_procedure_avvio.id.codiceprocedura}&codicemovimento=${var_procedure_avvio.id.tipomovimento}','')">${var_procedure_avvio.tipoMovimento.descrizioneEstesa}<c:if test="${var_procedure_avvio.defaultsn eq true}">&nbsp;<fmt:message key="label.default" /></c:if></a>							
						</td>
                        <td align="right" width="5%">
							<a class="eliminaRiga" href="javascript:doHref('eliminaMovimentoAvvio.htm?codiceprocedura=${var_procedure_avvio.id.codiceprocedura}&codicemovimento=${var_procedure_avvio.id.tipomovimento}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />&nbsp;${var_procedure_avvio.tipoMovimento.movimento}">
							<label><fmt:message key="label.azioni" /></label>
						</a> 
						</td>
					</tr>
				<%i++;%>
				
				</c:forEach>
				</tbody>
			</table>
	    </div>
	    <br class="clear"/>
	    <div id="functions">
		<ul>
			<li><a href="javascript:doHref('createMovimentoAvvio.htm?codiceprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.insert" /></a></li>
		</ul>
	</div>
	</c:if>
	
	
	
	<!-- Crea la finstra di dialogo che avevrte l'operatore che l'operazione che sta facendo potrebe durare alcuni minuti  
		     In quanto è un operazione di backup	
		-->
		<script type="text/javascript">
		
		function confermaAggiornamento(divId){
				dijit.byId(divId).show();
		}
 
		function confermaAggiornamentoDataValidita(divId){
			dijit.byId(divId).show();
		}
		</script>
		<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm" title="<fmt:message key="label.messaggio_conferma"/>">
	 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:400px;height: 100px">
			<div align="center">
				<div align="center"><fmt:message key="javascript.confirm.aggiornamento_validita_istanza" /><div>
				<br class="clear" />
				<div style="float: none;" id="functions">
					<ul>
				    	<li><a href="javascript:doHref('aggiornaDataInizioProcedura.htm?codice=${tipiprocedure.id.codice}','');" ><fmt:message key="button.ok" /></a></li>
						<li><a href="javascript:void(0)" onClick="dijit.byId('message_dialog_confirm').hide()"><fmt:message key="button.annulla" /></a></li>
			   		</ul>
			   	</div>
			</div>		
		</div>
	
	
	   <div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm_data_validita" title="<fmt:message key="label.messaggio_conferma"/>">
	 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:400px;height: 100px">
			<div align="center">
				<div align="center"><fmt:message key="javascript.confirm.aggiornamento_validita_istanza" /><div>
				<br class="clear" />
				<div style="float: none;" id="functions">
					<ul>
				    	<li><a href="javascript:doHref('aggiornaDataValiditaProcedura.htm?codice=${tipiprocedure.id.codice}','');" ><fmt:message key="button.ok" /></a></li>
						<li><a href="javascript:void(0)" onClick="dijit.byId('message_dialog_confirm_data_validita').hide()"><fmt:message key="button.annulla" /></a></li>
			   		</ul>
			   	</div>
			</div>		
		</div>
	
	
</body>
</html>