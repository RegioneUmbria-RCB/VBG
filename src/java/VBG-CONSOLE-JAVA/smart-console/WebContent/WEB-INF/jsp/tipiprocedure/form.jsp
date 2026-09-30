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
	 
	});
    
	function tuttiSw(){
		
		<%--
		
		if($('id_flag1').checked){
			
		    $('movimetoDeterminazione_id1').style.display="inline";
		    $('movimetoDeterminazione_id2').style.display="none";
		}else
		{
			$('movimetoDeterminazione_id1').style.display="none";
			$('movimetoDeterminazione_id2').style.display="inline";
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
        --%>      
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
					</td>
					<td>
						<spring-form:hidden path="id.codice"  id="codiceprocedura_id" />
						<spring-form:input id="procedura_id" path="procedura" size="75"/>
						<spring-form:errors path="procedura" cssClass="error"/>
						<c:if test="${tipiprocedure.id.codice!=null}">
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
				<tr>
				  <td></td>
				  <td><fmt:message key="label.descrizione_procedura"/></td>
				</tr>
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.info_web"/>
					</td>
					<td>
						<spring-form:textarea id="info_web_id" path="note" rows="4" cols="75"/>
						<spring-form:errors path="note" cssClass="error"/>
						<%-- <init:help idHelp="help_info_web" textKey="tipiprocedure.help.info_web"/> --%>
					</td>
				</tr>
				<tr>
					<td width="25%">
						<fmt:message key="tipiprocedure.label.natura_endo" />
					</td>
					<td>
						<spring-form:input id="naturaendo_id" path="naturaendo.natura" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'naturaendo_hidden')" size="75"/>
						<init:autocompleter methodAjax="findNaturaEndo.htm" idHidden="naturaendo_hidden" idInput="naturaendo_id" inputTitleKey="label.ricerca_natura_endo"></init:autocompleter>
						<spring-form:errors path="naturaendo" cssClass="error"/> 
						<spring-form:hidden id="naturaendo_hidden" path="naturaendo.id"  />
					</td>
				</tr>
				<tr>
				     <td></td>
				     <td><fmt:message key="tipiprocedure.label.descrizione_natura_endo"/></td>
				</tr>
				<tr class="titoloSezione">				   
				     <td colspan="2"><fmt:message key="label.parametri_frontoffice_area_informativa"/></td>
				</tr>
				<tr>
					<td width="25%"><fmt:message key="tipiprocedure.label.nome_modello" /></td>
					<td><jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice_modello" />
						<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoModello.id.codice}" />
						<jsp:param name="idComuneOggetto" value="${tipiprocedure.oggettoModello.id.idcomune}" />
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
						<jsp:param name="idComuneOggetto" value="${tipiprocedure.oggettoModelloDomandaOnline.id.idcomune}" />
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
						<jsp:param name="idComuneOggetto" value="${tipiprocedure.oggettoModelloDiagramma.id.idcomune}" />
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
							<jsp:param name="idComuneOggetto" value="${tipiprocedure.oggettoModelloPrecompilato.id.idcomune}" />
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
					<td width="25%"><fmt:message key="label.certificato_di_invio" /> <init:help idHelp="helpCertificatoInvio" textKey="help.tipiprocedure.certificato_di_invio"/> </td>
					<td>
						
						<jsp:include page="../includes/oggetti.jsp">
							<jsp:param name="idElemento" value="oggettoIdCodice_certificato_invio" />
							<jsp:param name="codiceOggetto" value="${tipiprocedure.oggettoCertificatoInvio.id.codice}" />
							<jsp:param name="idComuneOggetto" value="${tipiprocedure.oggettoCertificatoInvio.id.idcomune}" />
							<jsp:param name="codiceOggettoId" value="oggetto_id_certificato_invio" />
							<jsp:param name="nomefileId" value="nomefile_certificato_invio" />
						</jsp:include> 
						
						<spring-form:hidden path="oggettoCertificatoInvio.id.codice" id="oggetto_id_certificato_invio" />
						<spring-form:hidden path="oggettoCertificatoInvio.nomefile" id="nomefile_certificato_invio" />
					</td>
				</tr>				
			</table>
			
			
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
				<%--
			    <li><a href="javascript:doHref('listsubprocedure.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.fasi_attuative" /></a></li>
			     
			    <li><a href="javascript:doHref('listdocumenti.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.documenti" /></a></li>			    
			    <li><a href="javascript:doHref('listmodelli.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.modelli" /></a></li>
			    --%>
			    
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