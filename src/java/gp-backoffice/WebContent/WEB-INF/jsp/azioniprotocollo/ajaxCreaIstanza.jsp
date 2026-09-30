<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Anagrafe"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Istanze"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PECCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento"%>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.crea_istanza" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.crea_istanza" />
	</span>
	<br  class="clear" />
		<spring-form:form commandName="azioniProtocollazioneCommand" name="istanzaDaPecForm" id="istanzaDaPecForm" action="${pageContext.request.contextPath}/azioniprotocollo/creaIstanza.htm">
	
			<br />
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzaPecCommand" />
		    </jsp:include>
		    
			<table width="100%" border="0">
				<tr>
					<td><fmt:message key="azioniprotocollo.label.invia_allegati" /></td>
					<td colspan="5">
						<spring-form:checkbox path="inviaAllegati" value="true" onclick="toggleScompattaAllegati()"/>&nbsp;						
					</td>
				</tr>
				<tr id="scompattaAllegati_TRID">
					<td><fmt:message key="pecinbox.label.scompattaallegaticompressi" /></td>
					<td colspan="5">
						<spring-form:checkbox path="scompattaAllegati" value="true" />&nbsp;
						<fmt:message key="pecinbox.label.scompattaallegaticompressi.helpistanza"/>
					</td>
				</tr>
				
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="label.dati_istanza"/>		
						</td>
					</tr>
					<c:if test="${empty error }">
						<c:if test="${not empty azioniProtocollazioneCommand.entity.id.codice }">
							<tr>
								<td style="vertical-align: top;"><fmt:message key="label.numeroistanza" /></td>
								<td colspan="5">
									
										<span id="label_numeroistanza" class="error">
											<fmt:message key="pecinbox.message.errorestc" >
												<fmt:param>${azioniProtocollazioneCommand.entity.numeroistanza }</fmt:param>
											</fmt:message>
										</span>
									
									<c:if test="${not istanzaPecCommand.stcError eq true}">
										<span id="label_numeroistanza"><input type="text" class="inputRed" id="_entity_numeroistanza_id" readonly="readonly" name="entity.numeroistanza" size="40" value="${azioniProtocollazioneCommand.entity.numeroistanza}"/></span>
									</c:if>
								</td>
							</tr>
						</c:if>
							<jsp:include page="../includes/comboComuni.jsp">
								<jsp:param name="mostraTutti" value="false" />
								<jsp:param name="emptyLabelTutti" value="false" />						
								<jsp:param name="readOnly" value="false" />
								<jsp:param name="commandPropertyPath" value="entity.comune" />
								<jsp:param name="colspan" value="5" />
								<jsp:param name="comune" value="${azioniProtocollazioneCommand.entity.comune.codicecomune}" />
								<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
							</jsp:include>							
						<!-- richiedente -->
						<tr>
							<td style="vertical-align: top;">
								<label class="required">*</label>
								<fmt:message key="label.richiedente" />
							</td>
							<td colspan="5">
								<jsp:include page="../includes/anagraficasearch.jsp" >
									<jsp:param name="idElemento" value="richiedenteIdCodice" />						
									<jsp:param name="pathAnagrafica" value="entity.richiedente" />
									<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=F" />
									<jsp:param name="tiposoggetto"  value="F"/>
								</jsp:include>
							</td>
						</tr>		
						<c:choose>
							<c:when test="${configurazione.flagAziendarappresentata eq true }">
								<tr>
									<td><fmt:message key="label.in_qualita_di" /></td>
									<td colspan="5">
									<script type="text/javascript">
											function setFieldTipisoggetto(inputField, listItem){
													var a = listItem.id;
													//array che contiene l'id dei campi separati da '#'
													//il primo valore è l'id della tabella tipisogegtto e il secondo se deve mostrare o meno la descrizione del soggetto
													var arrayValori=a.split('#');
													document.getElementById('tipisoggetto_id_id').value = inputField.value;
													document.getElementById('tipisoggetto_id_hidden').value = arrayValori[0];
													if(arrayValori[1]=='true'){
														$('TR_DESCRSOGGETTO').appear();
													}else{
														$('TR_DESCRSOGGETTO').fade();
													}
											}
										</script>
										
											<jsp:include page="../includes/autocompletergenerico.jsp" >
												<jsp:param name="idElemento" value="tipisoggetto_id" />
												<jsp:param name="propertyPath" value="entity.tipisoggetto" />
												<jsp:param name="pathPropertyDescription" value="entity.tipisoggetto.tiposoggetto" />
												<jsp:param name="pathPropertyCode" value="entity.tipisoggetto.id.codice" />
												<jsp:param name="autocompleterAjax" value="findTipisoggettoAndSpecificadescrizione.htm?flagQualita=true" />							
												<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
												<jsp:param name="afterUpdateElement" value="setFieldTipisoggetto" />
											</jsp:include>
										
										
									</td>
								</tr>
								<c:set var="displayDescrSoggetto" value="display: none;" />
							
								<c:if test="${azioniProtocollazioneCommand.entity.tipisoggetto.flgSpecificadescrizione eq true}">
									<c:set var="displayDescrSoggetto" value="" />
								</c:if>
							
								<tr id="TR_DESCRSOGGETTO" style="${displayDescrSoggetto}">
									<td>&nbsp;</td>
									<td colspan="5">
										<spring-form:input path="entity.descrsoggetto" size="70" />
										<spring-form:errors path="entity.descrsoggetto" cssClass="error"/>
										&nbsp;<fmt:message key="label.specificare_la_tipologia_di_soggetto" />
									</td>
								</tr>				
								<tr>
									<td style="vertical-align: top;"><fmt:message key="label.ragione_sociale" /></td>
									<td colspan="5">
										<jsp:include page="../includes/anagraficasearch.jsp" >
											<jsp:param name="idElemento" value="titolareLegaleIdCodice" />						
											<jsp:param name="pathAnagrafica" value="entity.titolarelegale" />
											<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=G" />
											<jsp:param name="tiposoggetto"  value="G"/>
										</jsp:include>
									</td>
								</tr>
								
							</c:when>
							<c:otherwise>
								<spring-form:hidden path="entity.tipisoggetto.id.codice" />
								<spring-form:hidden path="entity.descrsoggetto" />
								<spring-form:hidden path="entity.titolarelegale.id.codice" />
							</c:otherwise>
						</c:choose>					 
						<tr>
							<td style="vertical-align: text-top; "><fmt:message key="label.codice_pratica_telematica" /></td>
							<td colspan="5">
								<fieldset>
								<spring-form:input id="codice_pratica_telematica_id" path="entity.codicepraticatel" size="70" cssStyle="font-weight: bolder;"/>
								<spring-form:errors path="entity.codicepraticatel" cssClass="error" />
								
									<a href="javascript: void(0);" id="btn_calcola">
										<img src="../images/calcolatrice.gif" title="<fmt:message key="pecinbox.button.calcolacodicepratica"/>" />
									</a>									
									<div style="width: 450px;">
										<fmt:message key="label.codice_pratica_telematica.help" />	
									</div>
								</fieldset>		
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.domicilio_elettronico" /></td>
							<td colspan="5">
								<spring-form:input id="domicilioElettronico_id" path="entity.domicilioElettronico"	size="70" cssStyle="font-weight: bolder;"/>
								<spring-form:errors path="entity.domicilioElettronico" cssClass="error" />
							</td>
						</tr>
						<tr id="id_progetto_table">
							<td valign="top">
								
								<label class="required">*</label>
								
								<fmt:message key="label.alberoproc" />
							</td>
							<td colspan="5">
							<spring-form:input id="alberoproc_hidden" path="entity.alberoproc.id.codice" onchange="cercaProcedimento()" size="9" cssStyle="text-align: right; font-weight: bolder;" />
							<%-- ALBEROPROC DOJO TREE --%>
							
							<a class="vbg-btn btn-dettaglio" id="alberoimg_id" href="javascript:cercaProcedimento();" style="vertical-align: bottom;" title="Cerca procedimento">
							</a>
							
							<spring-form:input id="alberoproc_descrestesa_hidden" path="entity.alberoproc.vwAlberoproc.scDescrizione" size="100" readonly="true" cssStyle="font-weight: bolder;"/>
							<spring-form:errors path="entity.alberoproc" cssClass="error" />
							<div id="treeOne"></div>
		   	  				<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div>
								</td>						
							</tr>
							<tr>
								<td style="vertical-align: top;">
									<fmt:message key="label.lavori" />
								</td>
								<td colspan="5">
									<spring-form:textarea id="lavori_id" path="entity.lavori" cols="105" rows="4" cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
							<tr>
								<td  style="vertical-align: top;">
									<fmt:message key="label.lavoriestesa" />
								</td>
								<td colspan="5">
									<spring-form:textarea id="lavoriestesa_id" path="entity.lavoriestesa" 
									cols="105" rows="4" cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty error}">
						<tr>
							<td colspan="6">
								<span class="error">${error}</span>		
							</td>
						</tr>
						</c:if>
					
					<c:if test="${not empty error}">
						<tr>
							<td colspan="6">
								<span class="error">${error}</span>		
							</td>
						</tr>
					</c:if>
				</table>
				
			</spring-form:form>	
			
		<div id="functions">
			<ul>
				<li><a href="javascript:void();" id="btn_salva"><fmt:message key="label.salva" /></a></li>
			</ul>
		</div>
			 		
	</div>

	<script type="text/javascript">
	
	
	function toggleScompattaAllegati(){
		
		jQuery('#scompattaAllegati_TRID').toggle();
		
	}
	
	
	
	function ajaxHistorySet(url){
		new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
			  method: 'get',
			  parameters: {ReturnTo: url, limit: 12},
			  onSuccess: function(transport){},
			  onFailure: function(){}			  
		});
	}
	
	  jQuery(document).ready(function(){
		  
		  jQuery('#btn_salva').click(creaIstanza);
		  jQuery('#btn_calcola').click(calcolaCodicePraticaTelematica);
		  jQuery('#richiedenteIdCodice').css('fontWeight','bolder');
		  jQuery('#titolareLegaleIdCodice').css('fontWeight','bolder');
		  jQuery('#tipisoggetto_id_id').css('fontWeight','bolder');
		  //titolareLegaleIdCodice
		  
		  if($('alberoproc_descrestesa_hidden')){
			if('${fn:replace(istanzaPecCommand.intervento.vwAlberoproc.scDescrizione,'\'','%27')}' != ''){									
				$('alberoproc_descrestesa_hidden').innerText='${fn:replace(istanzaPecCommand.intervento.vwAlberoproc.scDescrizione,'\'','\\\'')}';
			}
		  }
		});
	  
	  function creaIstanza(){
		  disableFunctions();
		  jQuery.ajax({
				url: '${pageContext.request.contextPath}/azioniprotocollo/ajaxCreaIstanzaSTC.htm?', 
				dataType: 'html',
				type: 'post',
				data: jQuery('#istanzaDaPecForm').serialize(),
				cache: false,	
				success: function(data){
					enableFunctions();
					if (data.match("^OK")) {
						jQuery('#outputmessaggicreazione_div').html(data.replace("OK",""));
						avvisoIstanzaCreata();
					}else{
						jQuery('#outputmessaggicreazione_div').html(data.replace("KO",""));
						avvisoErroreIstanza();
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
					enableFunctions();
					var errorMessage = "Errore in inserimento pratica.";					
					if(errorThrown){
						errorMessage += errorThrown;
					}
					displayErrorMessage(errorMessage);
				}
			});
	  }
	 		
	  
	  function calcolaCodicePraticaTelematica(){
		  
			jQuery.ajax({
				url: '${pageContext.request.contextPath}/azioniprotocollo/ajaxCalcolaCodicePraticaTel.htm?', 
				dataType: 'json',
				data: jQuery('#istanzaDaPecForm').serialize(),
				cache: false,	
				success: calcolaCodicePraticaTelematicaCallback,
				error: function(jqXHR, textStatus, errorThrown){
					var errorMessage = "Errore nel calcolo automatico del codice pratica telematica.";
					/*
					if(textStatus){
						errorMessage += textStatus + " ";
					}
					*/
					if(errorThrown){
						errorMessage += errorThrown;
					}
					displayErrorMessage(errorMessage);
				}
			});
	  }
	  
	  function calcolaCodicePraticaTelematicaCallback(data, textStatus, jqXHR){
		  if(data.codicePraticaTel){
			  jQuery('#codice_pratica_telematica_id').val(data.codicePraticaTel);
		  }
		  else if(data.error){
			  displayErrorMessage(data.error);
		  }
	  }
	  
	  function displayErrorMessage(msg){
		  alert(msg);
	  }
	</script>	

</body>
</html>