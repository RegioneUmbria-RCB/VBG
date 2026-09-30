<%@page import="it.gruppoinit.pal.gp.core.domain.web.IstanzeCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>	
		<fmt:message key="label.archivio_istanze" />
	</title>
	
</head>
<body>
	<span class="titoloPagina">
		<init:editLabel key="label.archivio_istanze" role="ROLE_EDITLABEL" />		
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include> 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/searchIstanze" />
	</jsp:include>
	<div id="subcontent">	

		<spring-form:form commandName="istanzeCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeCommand" />
		    </jsp:include>

		    <input type="hidden" id="chiave_ricerca" value="istanze_search" />
			<input type="hidden" id="nome_form_ricerca" value="istanzeCommand" />
		    <jsp:include page="../includes/ricerche.jsp" >
		       <jsp:param value="${istanzeCommand.codiceRicerca}" name="ricerca_selezionata"/>
		        <jsp:param value="${istanzeCommand.tipoRicerca}" name="tipo_ricerca_selezionata"/>
		        <jsp:param value="${istanzeCommand.codiceIstanzaAccessoAtti}" name="codiceAccessoAtti"/>	
		        <jsp:param value="${istanzeCommand.codiceGruppoAnagrafetributaria}" name="codiceGruppoAnagrafetributaria" />	    
		     </jsp:include>
	    	<table width="100%" border="0">
				<tr id="elementIdBeforeCombo">
					<td width="250px;"></td>
					<td colspan="7"></td>
				</tr>
				<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="mostraTutti" value="true" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="istanzeFilter.comune" />
					<jsp:param name="colspan" value="4" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>
				<%
					pageContext.setAttribute("SEARCH_ISTANZE_COLLEGATE", WebConstants.SEARCH_ISTANZE_COLLEGATE);
					pageContext.setAttribute("SEARCH_ISTANZE", WebConstants.SEARCH_ISTANZE);
					pageContext.setAttribute("SEARCH_ISTANZE_ACCESSO_ATTI", WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI);
					pageContext.setAttribute("SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA", WebConstants.SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA);
				%>
				<c:if test="${istanzeCommand.tipoRicerca == SEARCH_ISTANZE_COLLEGATE  || istanzeCommand.tipoRicerca == SEARCH_ISTANZE_ACCESSO_ATTI }">  <%-- istanzeCommand.isIstanzecollegate = WebConstants.SEARCH_ISTANZE_COLLEGATE --%>
				<tr>
					<td><fmt:message key="label.software" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="modulo_id" />		
							<jsp:param name="propertyPath" value="istanzeFilter.modulo" />				
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.modulo.descrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.modulo.codice" />
							<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=false" />							
							<jsp:param name="titleKey" value="label.ricerca_software" />
						</jsp:include>
					</td>
				</tr>
				</c:if> 	  	
				<tr class="titoloSezione">
					<td colspan="8"><init:editLabel key="label.dati_istanza" role="ROLE_EDITLABEL" /></td>
				</tr>
				<tr>
					<td><init:editLabel key="label.numero_istanza" role="ROLE_EDITLABEL" /></td>
					<td colspan="7">
						<spring-form:input id="numeroistanza_id" path="istanzeFilter.numeroistanza" size="10"/>
					</td>
				</tr>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldCodiceDomandaStc')}">
				<tr>
				<%-- Commentato perchè non è possibile creare il collegamento tra Istanze e domandestc (Ved. IstanzeServiceImpl.istanzeFilterToFilterTable()) --%>
				
					<td><init:editLabel key="label.codice_domanda_online" role="ROLE_EDITLABEL" /></td>
					<td colspan="7" class="inline-ui-cell">
					 	<spring-form:input id="codicedomandastc_id" path="istanzeFilter.codicedomandastc" size="40"/>&nbsp;
					 	<spring-form:checkbox id="cercasolodomandestc_id" path="istanzeFilter.cercasolodomandestc"/>
						<label for="cercasolodomandestc_id"><fmt:message key="label.checkbox_mostra_solo_domande_stc"/></label>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldCodicePraticaTelematica')}">					
				<tr>
					<td><init:editLabel key="label.codice_pratica_telematica" role="ROLE_EDITLABEL" /></td>
					<td colspan="7">
							<spring-form:input id="entity_codice_pratica_tel_id" path="istanzeFilter.codicepraticatel" size="40"/>
							<spring-form:errors path="istanzeFilter.codicepraticatel" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<tr>
				
					<td><init:editLabel key="label.data_presentazione" role="ROLE_EDITLABEL" /></td>
					<td class="inline-ui-cell" style="min-width: 280px;" colspan="2">
						<label><fmt:message key="label.dalla_data" /></label>
						<spring-form:input id="dallaData_id" path="istanzeFilter.dallaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataInizioIst" idInput="dallaData_id" textKey="label.calendar" />	  	
						<spring-form:errors path="istanzeFilter.dallaData" cssClass="error" delimiter="," />
					</td>
					<td colspan="5" class="inline-ui-cell">
						<label><fmt:message key="label.alla_data" /></label>
						<spring-form:input id="allaData_id" path="istanzeFilter.allaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataFineIst" idInput="allaData_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaData" cssClass="error" delimiter="," />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.numero_protocollo" />
					</td>
					<td colspan="7" class="inline-ui-cell" style="min-width: 400px;">
						<spring-form:input id="numeroprotocollo_id" path="istanzeFilter.numeroprotocollo" size="10"/>
						<spring-form:checkbox id="cercaprotocolloinmovimenti_id" path="istanzeFilter.cercaprotocolloinmovimenti" />
						<label for="cercaprotocolloinmovimenti_id"><fmt:message key="label.cerca_protocollo_in_movimenti" /></label>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.data_protocollo" /></td>
					<td class="inline-ui-cell" style="min-width: 280px;" colspan="2">
						<label><fmt:message key="label.dalla_data" /></label>
						<spring-form:input id="dallaDataProt_id" path="istanzeFilter.dallaDataProtocollo" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataProtInizio" idInput="dallaDataProt_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaDataProtocollo" cssClass="error" delimiter="," />
					</td>
					<td colspan="5" class="inline-ui-cell">
						<label><fmt:message key="label.alla_data" /></label>
						<spring-form:input id="allaDataProt_id" path="istanzeFilter.allaDataProtocollo" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataProtFine" idInput="allaDataProt_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaDataProtocollo" cssClass="error" delimiter="," />
					</td>
				</tr>
				
				
				<!-- Richiedente -->
				<tr>
					<td style="min-width: 210px;">
						<div style="width: 180px; display: inline-block;">
							<init:editLabel key="label.nominativo_richiedente_soggetti_collegati" role="ROLE_EDITLABEL" />
						</div>
						<init:help idHelp="helpRicercaTestuale" textKey="help.cerca_testuale_in_anagrafe"/>
					</td>
					<td colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<spring-form:input id="soggettiistanza_id" path="istanzeFilter.soggettiistanza" size="70"/>
						<%--&nbsp;<fmt:message key="label.ricerca_nominativo_come_richiedente_azienda_professionista" /> --%>
						<spring-form:checkbox id="cercaInAnagrafestorico_id" path="istanzeFilter.cercaInAnagrafestorico" />
						
						<label for="cercaInAnagrafestorico_id"><fmt:message key="label.cerca_in_anagrafestorico" /></label>
						<init:help idHelp="helpCercaInAnagrafestorico" textKey="label.cerca_in_anagrafestorico.help"/>					
					</td>
				</tr>
				<tr>
					<td><init:editLabel key="label.richiedente_soggetti_collegati" role="ROLE_EDITLABEL" /></td>
					<td colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<jsp:include page="../includes/anagraficasearch.jsp" >
							<jsp:param name="idElemento" value="richiedenteIdCodice" />						
							<jsp:param name="pathAnagrafica" value="istanzeFilter.richiedente" />
							<jsp:param value="true" name="anagrafeHideFunctions"/>
							<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?statoAnagrafe=ALL" />
						</jsp:include>
						<label>
							<init:help idHelp="helpCercaRichAzProf" textKey="label.ricerca_anagrafica_come_richiedente_azienda_professionista"/>
						</label>
					</td>
				</tr>
				<!-- PIVA/CF -->
				<tr>
					<td><fmt:message key="label.partita_iva_cf" /></td>
					<td colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<spring-form:input id="soggettiistanzaPivaCF_id" path="istanzeFilter.soggettiistanzaPivaCF" size="16"/>	
						<label>
							<init:help idHelp="helpSoggettiistanzaPICF" textKey="label.ricerca_piva_cf_come_richiedente_azienda_professionista"/>
						</label>				
					</td>
				</tr>			
				<tr>
					<td><init:editLabel key="label.domicilio_elettronico" role="ROLE_EDITLABEL" /></td>
					<td colspan="7">
						<spring-form:input id="domicilioElettronico_id" path="istanzeFilter.domicilioElettronico" size="70" />
						<spring-form:errors path="istanzeFilter.domicilioElettronico" cssClass="error"/>								
					</td>
				</tr>
				<c:if test="${isArchiviopraticheVisible eq true }">
				<tr>
					<td><init:editLabel key="label.archivio_pratiche" role="ROLE_EDITLABEL" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="archiviopratiche_id" />		
							<jsp:param name="propertyPath" value="istanzeFilter.tipiarchivioistanza" />				
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.tipiarchivioistanza.archivio" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.tipiarchivioistanza.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiarchivioistanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
						</jsp:include>
					</td>
				</tr>
				</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldPosizioneArchivio')}">
				<tr>
					<td><init:editLabel key="label.posizione_in_archivio" role="ROLE_EDITLABEL" /></td>
					<td colspan="7">						
						<spring-form:input id="posizionearchivio_id" path="istanzeFilter.posizionearchivio" size="10"/>						
					</td>
				</tr>
				</c:if>
				<c:if test="${isTipologiaistanzaVisible eq true }">
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldTipologiaIstanza')}">					
					<tr>
						<td><init:editLabel key="label.tipologia_istanza" role="ROLE_EDITLABEL" /></td>
						<td colspan="7">						
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="tipologia_istanze_id" />
								<jsp:param name="propertyPath" value="istanzeFilter.tipologiaistanza" />							
								<jsp:param name="pathPropertyDescription" value="istanzeFilter.tipologiaistanza.tiDescrizione" />
								<jsp:param name="pathPropertyCode" value="istanzeFilter.tipologiaistanza.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipologiaistanze.htm" />							
								<jsp:param name="titleKey" value="label.ricerca_tipologiaistanze" />
							</jsp:include>
						</td>
					</tr>
					</c:if>
				</c:if>				
				<tr>
					<td>
						<init:editLabel key="label.operatore_responsabile_istruttore" role="ROLE_EDITLABEL" />
					</td>
					<td colspan="7">
					
						<spring-form:input id="responsabile_id" path="istanzeFilter.tuttiResponsabili.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabile_hidden');" onkeydown="return searchAll(this,event)"  size="67" />
						<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabile_hidden" idInput="responsabile_id" inputTitleKey="label.ricerca_responsabile" />						
						<spring-form:hidden id="responsabile_hidden" path="istanzeFilter.tuttiResponsabili.id.codice" />
					</td>
				</tr>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeTecnico')}">
				<tr>
					<td><init:editLabel key="label.tecnico" role="ROLE_EDITLABEL" /></td>
					<td colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<jsp:include page="../includes/anagraficasearchtecnico.jsp" >
							<jsp:param name="idElemento" value="professionistaIdCodice" />						
							<jsp:param name="pathAnagrafica" value="istanzeFilter.professionista" />
							<jsp:param name="anagrafeHideFunctions" value="true" />
							<jsp:param name="statoAnagrafe" value="ALL"/>
						</jsp:include>
					</td>
				</tr>
				</c:if>	
				
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeAmministrazioni')}">
				<tr>
					<td><init:editLabel key="alberoproc.label.amministrazioni" role="ROLE_EDITLABEL" /></td>
					<td colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioniIdCodice" />		
							<jsp:param name="propertyPath" value="istanzeFilter.amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.amministrazioni.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false&includiDisabilitate=true" />			
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
						</jsp:include>						
					</td>
				</tr>
				</c:if>		
               <!--  Sezione  RICERCA DA CAMPI SCHEDE-->
				<%--   SEZIONE 1- RICECA CAMPI DA CAMPI DINAMICI PER ULTERIORI TEST. LA QUERY CHE SI CREA NON FUNZIONA SU ORACLE
				       (GIANPAOLO E STEFANO MENDICHI) --%>
				<tr class="titoloSezione">
					<td  colspan="4"><fmt:message key="label.tab_schede"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.scheda" /></td>
					<td colspan="3">
						
							<script type="text/javascript">
							
							function changeSchedaDinamica(inputField,listItem){
								
								var a = listItem.id;
								document.getElementById('schedaDinamica_id').value = inputField.value;
								document.getElementById('schedaDinamica_hidden').value = a;
								setTimeout('doSubmit("changeScheda.htm?ts_='+Date()+'#listaCampiAncor","",document.inviodati)',150);	
							}					

							
							function resetSchedaValue(inputField, hiddenFieldId) {
								if (inputField.value == ''){
									$(hiddenFieldId).value = '';
								}
								setTimeout('doSubmit("changeScheda.htm#listaCampiAncor","",document.inviodati)',150);								
							}
							
							function filterSchedeBySoftware(element, entry) {
								var software = $('id_flag_schededinamiche').checked?$('id_flag_schededinamiche').value:'';
								if(software=='' || software!='TT'){
									software = getValoreDellaSelect($('modulosoftware_id'));
								}								
								return entry + "&codicesoftware="+software+"&ts_=" + Date();								
							}
							
							
							
							function renderCondizioni(obj, idx, nomeElementoValore, nomeElementoValore_hidden_fld_id){
								var elDestinatario = jQuery("#valore_"+idx);
								var codiceCampo = getValoreDellaSelect(obj);
								var valore = jQuery('#'+nomeElementoValore_hidden_fld_id).val();
								if(codiceCampo!=''){
									
									var jqxhr = jQuery.ajax({
										  url: "..ajax/ajaxRenderCampo.htm",
										  context: document.body,
										  cache: false,				
										  dataType: "html",
										  data: "codiceCampo="+codiceCampo+"&idx="+idx+"&nomeElementoValore="+escape(nomeElementoValore)+"&valore="+escape(valore),
										  success: function(dataResult) {
										  	elDestinatario.html(dataResult);
 											},
										  error: function(dataError){						  
											  elDestinatario.html(dataResult);											   
										  }	
										});		
								}else{									
									elDestinatario.html('');
								}
							}
							function addCampoDinamico(){								
								doSubmit('addCampoAScheda.htm?ts_='+Date()+'#listaCampiAncor','',document.inviodati);							
							}
							
							function removeCampoDinamico(idx){
								doSubmit('removeCampoScheda.htm?idx='+idx+'&ts_='+Date()+'#listaCampiAncor','<fmt:message key="javascript.confirm.delete" />',document.inviodati);
							}
							
							</script>
							
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="schedaDinamica" />
								<jsp:param name="propertyPath" value="istanzeFilter.schedaDinamicaFilter" />							
								<jsp:param name="pathPropertyDescription" value="istanzeFilter.schedaDinamicaFilter.scheda.descrizione" />
								<jsp:param name="pathPropertyCode" value="istanzeFilter.schedaDinamicaFilter.scheda.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm" />							
								<jsp:param name="titleKey" value="label.ricerca_schedadinamica" />
								<jsp:param name="ajaxCallBack" value="filterSchedeBySoftware"/>
								<jsp:param name="afterUpdateElement" value="changeSchedaDinamica"/>
								<jsp:param name="inputIdOnChange" value="resetSchedaValue(this,'schedaDinamica_hidden')" />
							</jsp:include>
						
							<input type="checkbox" id="id_flag_schededinamiche" value="TT"/>						
							<init:help idHelp="help_schede_tt" textKey="help.ricerca_per_software_TT"/>
							<fmt:message key="label.ricerca_schedadinamica.non_ha_effetto_su_filtro" />	
						</td>
					</tr>
					<tr>
						<td colspan="4">	
						<a name="listaCampiAncor"> </a>
						<c:if test="${not empty istanzeCommand.istanzeFilter.schedaDinamicaFilter.righe}">
						<fieldset>
							<table style="width:1000px">
								<tr class="titoloSezione">
									<td style="width: 50px">&nbsp;</td>
									<td style="width: 50px"></td>
									<td style="width: 400px"><fmt:message key="label.campo" /></td>
									<td style="width: 150px"><fmt:message key="label.criterio" /></td>
									<td style="width: 400px"><fmt:message key="label.valore" /></td>
									<td style="width: 50px"></td>
									<td style="width: 25px"></td>							
								</tr>
								<c:forEach items="${istanzeCommand.istanzeFilter.schedaDinamicaFilter.righe }" var="riga_var" varStatus="rigaStatus">
								<tr>								
									<td>
										<spring:bind path="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].andOr">
										<c:choose>
											<c:when test="${rigaStatus.index eq 0}">
												<input type="hidden" name="${status.expression}" value="" />
											</c:when>
											<c:otherwise>
												<select name="${status.expression}">
													<option value=""></option>
													<option value="<%=AndOrRestriction.AND %>" <c:if test="${ status.value eq 'AND'}"> selected </c:if> >E</option>
													<option value="<%=AndOrRestriction.OR %>" <c:if test="${ status.value eq 'OR'}"> selected </c:if> >O</option>
												</select>
											</c:otherwise>
										</c:choose>												
										</spring:bind>
									</td>
									<td>
										<spring:bind path="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].parentesiSx">
											<select name="${status.expression}">
												<option value=""></option>
												<option value="(" <c:if test="${ status.value eq '('}"> selected </c:if> >(</option>
											</select>											
										</spring:bind>
									</td>
									<td>
									<c:set var="nomeElementoValore" value="" />
									
									
									<spring:bind path="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].valore">
										<c:set var="nomeElementoValore" value="${status.expression}" />
										<input type="hidden" id="hidden_fld_id_${rigaStatus.index}" name="${ nomeElementoValore }_hidden_fld" value="${ status.value }"/>
									</spring:bind>
									
									<script type="text/javascript">
										function renderCondizioni_2_${rigaStatus.index}(){
											
											var elDestinatario = jQuery("#valore_${rigaStatus.index}");
											var codiceCampo = $('auto_select_campi_${rigaStatus.index}_hidden').value;
											var valore = jQuery('#hidden_fld_id_${rigaStatus.index}').val();
											console.info(codiceCampo);
											if(codiceCampo!=''){
											
												var jqxhr = jQuery.ajax({
													  url: "../ajax/ajaxRenderCampo.htm",
													  context: document.body,
													  cache: false,				
													  dataType: "html",
													  data: "codiceCampo="+codiceCampo+"&idx=${rigaStatus.index}&nomeElementoValore="+escape('${nomeElementoValore}') + "&valore="+escape(valore),
													  success: function(dataResult) {
													  	elDestinatario.html(dataResult);
			 											},
													  error: function(dataError){						  
														  elDestinatario.html(dataResult);											   
													  }	
													});		
											
											}else{									
												elDestinatario.html('');
											}
										}
										
										function renderCondizioni${rigaStatus.index}(inputField,listItem){										
											var a = listItem.id;
											document.getElementById('auto_select_campi_${rigaStatus.index}_id').value = inputField.value;
											document.getElementById('auto_select_campi_${rigaStatus.index}_hidden').value = a;
											renderCondizioni_2_${rigaStatus.index}();
										}					
	
										function filterCampoByScheda${rigaStatus.index}(element, entry) {
			
											var idScheda = $('schedaDinamica_hidden').value;
											return entry + "&codiceModello="+idScheda+"&ts_=" + Date();								
										}
									</script>
									
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="auto_select_campi_${rigaStatus.index}" />
										<jsp:param name="propertyPath" value="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}]" />							
										<jsp:param name="pathPropertyDescription" value="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].campo.nomecampo" />
										<jsp:param name="pathPropertyCode" value="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].campo.id.codice" />
										<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm" />							
										<jsp:param name="titleKey" value="label.ricerca_campodinamico" />
										<jsp:param name="ajaxCallBack" value="filterCampoByScheda${rigaStatus.index}"/>
										<jsp:param name="afterUpdateElement" value="renderCondizioni${rigaStatus.index}"/>										
									</jsp:include>									
									</td>
									
									<td>
										<spring:bind path="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].tipoConfronto">
											<select name="${status.expression}">												
												<option value="<%=FieldOperationsEnum.EQ %>" <c:if test="${ status.value eq 'EQ'}"> selected </c:if> >è uguale a</option>
												<option value="<%=FieldOperationsEnum.NE %>" <c:if test="${ status.value eq 'NE'}"> selected </c:if> >non è uguale a</option>
												<option value="<%=FieldOperationsEnum.STARTSWITH %>" <c:if test="${ status.value eq 'STARTSWITH'}"> selected </c:if> >inizia per</option>
												<option value="<%=FieldOperationsEnum.ENDSWITH %>" <c:if test="${ status.value eq 'ENDSWITH'}"> selected </c:if> >finisce per</option>
												<option value="<%=FieldOperationsEnum.CONTAINS %>" <c:if test="${ status.value eq 'CONTAINS'}"> selected </c:if> >è simile</option>
												<option value="<%=FieldOperationsEnum.GE %>" <c:if test="${ status.value eq 'GE'}"> selected </c:if> >è maggiore uguale a</option>
												<option value="<%=FieldOperationsEnum.GT %>" <c:if test="${ status.value eq 'GT'}"> selected </c:if> >è maggiore di</option>
												<option value="<%=FieldOperationsEnum.LE %>" <c:if test="${ status.value eq 'LE'}"> selected </c:if> >è minore uguale a</option>
												<option value="<%=FieldOperationsEnum.LT %>" <c:if test="${ status.value eq 'LT'}"> selected </c:if> >è minore di</option>
												<option value="<%=FieldOperationsEnum.ISEMPTY %>" <c:if test="${ status.value eq 'ISEMPTY'}"> selected </c:if> >è vuoto</option>
												<option value="<%=FieldOperationsEnum.ISNOTEMPTY %>" <c:if test="${ status.value eq 'ISNOTEMPTY'}"> selected </c:if> >non è vuoto</option>
											</select>											
										</spring:bind>
									</td>
									<td>
										<script type="text/javascript">
											jQuery(document).ready(function(){
												renderCondizioni_2_${rigaStatus.index}();
								    		});
										</script>
										<div id="valore_${rigaStatus.index}"></div>										
									</td>
									<td>
										<spring:bind path="istanzeFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].parentesiDx">
											<select name="${status.expression}">
												<option value=""></option>
												<option value=")" <c:if test="${ status.value eq ')'}"> selected </c:if> >)</option>
											</select>											
										</spring:bind>
									</td>
									<td>
										<a style="float: left;" class="eliminaRiga" href="javascript:removeCampoDinamico(${rigaStatus.index});" title="<fmt:message key="label.rimuovi_filtro" />">
											<label><fmt:message key="label.del.record.image" /></label>
										</a>
									</td>									
								</tr>				
								</c:forEach>
							</table>
							
						</fieldset>
						</c:if>						
							<a style="float: left;" class="addColumn" href="javascript:addCampoDinamico();" title="<fmt:message key="label.aggiungi_filtro" />">
								<label><fmt:message key="label.add.record.image" /></label>
							</a>				
						</td>
					</tr>					
					
                <!--  END SEZIONE RICERCA DA CAMPO DINAMICO -->

				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'sezioneAutConcessioni')}">				
				<%
					String displayAutConc = "display:none;";
					String styleAutConc = "";
					//gestisce la visualizzazione della tabella altri dati
					if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_SEARCH_DATI_CONC_AUT))) {
					    displayAutConc = "";
					    styleAutConc="sezioneDatiMeno";
					} else {
					    displayAutConc = "display:none;";
					    styleAutConc="sezioneDatiPiu";
					}
				%>
				<tr class="titoloSezione">
					<td colspan="4">
						<a class="<%=styleAutConc %>" 
							id="id_link_autconc" 
							href="javascript:showHidePanel('id_autconc_table', 'id_link_autconc', '<%= WebConstants.CONF_UTENTE_SEARCH_DATI_CONC_AUT %>', '${pageContext.request.contextPath}/images/');"	
							title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dati_concessioni_autorizzazioni"/>">
							<label for="id_link_autconc">&nbsp;<fmt:message key="label.dati_concessioni_autorizzazioni"/></label>
						</a>
					</td>
				</tr>				

				<tr id="id_autconc_table" style="<%=displayAutConc%>">
					<td>
						<fmt:message key="label.numero" />
					</td>
					<td colspan="7">
						<spring-form:input id="autoriznumero_id" path="istanzeFilter.datiAutorizzazione.autoriznumero" size="10" />						
					</td>
				</tr>
				<tr id="id_autconc_table" style="<%=displayAutConc%>">
					<td>
						<fmt:message key="label.data" />
					</td>
					<td colspan="7">
						<spring-form:input id="data_autorizzazione_id" path="istanzeFilter.datiAutorizzazione.autorizdata" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataAutorizzazione" idInput="data_autorizzazione_id" textKey="label.calendar" /> 			  	
					</td>
				</tr>
				<tr id="id_autconc_table" style="<%=displayAutConc%>">
		    		<td>
			    		<fmt:message key="label.comune" />
			    	</td>
				    <td colspan="7">
				    	<spring-form:input path="istanzeFilter.datiAutorizzazione.autorizcomune.descrizioneEstesa" id="autorizcomune_id" cssClass="searchbox"  onchange="checkValue(this,'autorizcomune_hidden')" onkeydown="return searchAll(this,event,3)" size="67"/>
				    	<init:autocompleter methodAjax="findComuni.htm" idHidden="autorizcomune_hidden" idInput="autorizcomune_id" inputTitleKey="label.ricerca_comune" minChars="3"/>
						<spring-form:hidden id="autorizcomune_hidden" path="istanzeFilter.datiAutorizzazione.autorizcomune.codicecomune" />
					</td>
				</tr> 
				<tr id="id_autconc_table" style="<%=displayAutConc%>">
					<td>
			    		<fmt:message key="label.registro" />
			    	</td>
			    	<td colspan="7">
			    		<spring-form:input path="istanzeFilter.datiAutorizzazione.tipologiaregistro.trDescrizione" id="autorizregistro_id" cssClass="searchbox"  onchange="checkValue(this,'autorizregistro_hidden')" onkeydown="return searchAll(this,event)" size="67"/>
			    		<init:autocompleter methodAjax="findTipologiaRegistri.htm" idHidden="autorizregistro_hidden" idInput="autorizregistro_id" inputTitleKey="label.ricerca_tipo_registro" />
						<spring-form:hidden id="autorizregistro_hidden" path="istanzeFilter.datiAutorizzazione.tipologiaregistro.id.codice" />
					</td>
				</tr>				
				</c:if>
				<%
					String displayLocalizzazione = "display:none;";
					String styleLocalizzazione = "";
					//gestisce la visualizzazione della tabella altri dati
					if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ))) {
					    displayLocalizzazione = "";
					    styleLocalizzazione="sezioneDatiMeno";
					} else {
					    displayLocalizzazione = "display:none;";
					    styleLocalizzazione="sezioneDatiPiu";
					}
				%>
                <c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'sezIstanzeLocalizzazione')}">
				<tr class="titoloSezione">
					<td colspan="8">
						<a class="<%=styleLocalizzazione %>" 
							id="id_link_localizzazione" 
							href="javascript:showHidePanel('id_localizzazione_table', 'id_link_localizzazione', '<%= WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ %>', '${pageContext.request.contextPath}/images/');"	
							title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dati_localizzazione"/>">
							<label for="id_link_localizzazione">&nbsp;<fmt:message key="label.dati_localizzazione"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><init:editLabel key="label.area" role="ROLE_EDITLABEL" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="area_id" />				
							<jsp:param name="propertyPath" value="istanzeFilter.istanzearee" />		
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzearee.area.denominazione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzearee.id.codicearea" />
							<jsp:param name="autocompleterAjax" value="findAree.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_aree" />
						</jsp:include>
					</td>
				</tr>
				<%-- INCLUDE SNIPPET FUNZIONI JAVASCRIPT --%>
				<jsp:include page="../includes/javascriptSIT.jsp" >
					<jsp:param name="PAGINA_PROVENIENZA" value="ISTANZE" />
					
				</jsp:include>		
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.indirizzo" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="stradario_id" />
							<jsp:param name="propertyPath" value="istanzeFilter.istanzestradario.stradario" />								
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzestradario.stradario.descrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzestradario.stradario.id.codice" />
							<jsp:param name="autocompleterAjax" value="findStradario.htm" />
							<jsp:param name="ajaxCallBack" value="filterCodiceComune" />
							<jsp:param name="titleKey" value="label.ricerca_stradario" />
						</jsp:include>
					</td>
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td id="id_ricerca_civici_label">
						<fmt:message key="label.civico" />
					</td>
					<td id="id_ricerca_civici_field" colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<spring-form:input id="civico_id" path="istanzeFilter.istanzestradario.civico" size="5" />
						<%--  <input type="checkbox" id="cercarange_civici_id" value="1" onchange="tipoRicercaCivicoRange()" /> --%>
						<input type="checkbox" id="cercasingolo_civico_id"  value="1" name="istanzeFilter.cercarangecivici" onchange="tipoRicercaCivicoRange()" />
						<label>&nbsp;<fmt:message key="label.ricerca_range_civico" /></label>
						<init:help idHelp="helpRicercaRangeCivici" textKey="label.ricerca_range_civici.help"/>
					</td>											
				</tr>
				
				<tr id="id_ricerca_civici_range" style="display: none;">
					<td><fmt:message key="label.civico" /></td>
					<td colspan="2" class="inline-ui-cell" style="min-width: 280px;">
						<spring-form:input id="civicoDa_id" path="istanzeFilter.civicoDa" size="5" onchange="checkNumberInt(this)"/>
						<label><fmt:message key="label.da" /></label>
					</td>
					<td colspan="5" class="inline-ui-cell" style="min-width: 400px;">
						<spring-form:input id="civicoA_id" path="istanzeFilter.civicoA" size="5" onchange="checkNumberInt(this)"  />
						<label><fmt:message key="label.a" /></label>
						<input type="checkbox" id="cercarange_civico_id"  value="1" name="istanzeFilter.cercarangecivici" onchange="tipoRicercaCivicoSingoloCampo()" />
						<label><fmt:message key="label.ricerca_singolo_civico" /></label>&nbsp;
						<init:help idHelp="helpRicercaSingoloCivico" textKey="label.ricerca_singolo_civico.help"/>
				</tr>
				<script type="text/javascript">
				
				   jQuery( document ).ready(function() {
					  if('true'==='${istanzeCommand.istanzeFilter.cercarangecivici}')
					  {
						  tipoRicercaCivicoRange();
					  }else{
						  tipoRicercaCivicoSingoloCampo();
					  }
					});
				
				function tipoRicercaCivicoRange()
				{
					jQuery('#id_ricerca_civici_label').hide();
					jQuery('#id_ricerca_civici_field').hide();
					jQuery('#id_ricerca_civici_range').show();
					jQuery('#civico_id').val("");
					jQuery('#cercasingolo_civico_id').prop('checked', false);
					jQuery('#cercarange_civico_id').prop('checked', true);
					
				}
				
				function tipoRicercaCivicoSingoloCampo()
				{
					jQuery('#id_ricerca_civici_label').show();
					jQuery('#id_ricerca_civici_field').show();
					jQuery('#id_ricerca_civici_range').hide();
					jQuery('#civicoDa_id').val("");
					jQuery('#civicoA_id').val("");
					jQuery('#cercasingolo_civico_id').prop('checked', false);
					jQuery('#cercarange_civico_id').prop('checked', false);
					
					
				}
				
				</script>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td>
						<fmt:message key="label.km" />
					</td>
					<td  colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<spring-form:input id="km_id" path="istanzeFilter.istanzestradario.km" size="5" />
					</td>											
				</tr>					

				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td>
						&nbsp;
					</td>
					<td  colspan="7" class="inline-ui-cell" style="min-width: 500px;">
						<fmt:message key="label.dal_km" /> <spring-form:input id="dal_km_id" path="istanzeFilter.istanzestradario.transientDalKM" size="5" />
						&nbsp;
						<fmt:message key="label.al_km" /> <spring-form:input id="al_km_id" path="istanzeFilter.istanzestradario.transientAlKM" size="5" />
						<init:help idHelp="helpRicercaTestualeRangeKm" textKey="help.ricerca_range_km_istanze"/>
					</td>											
				</tr>
									
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.esponente" /></td>
					<td colspan="7">						
						<spring-form:input id="esponente_id" path="istanzeFilter.istanzestradario.esponente" 
							size="5" />
					</td>	
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.scala" /></td>
					<td class="inline-ui-cell" style="min-width: 300px;" colspan="2">						
						<spring-form:input id="scala_id" path="istanzeFilter.istanzestradario.scala" 
							size="5" />
					</td>	
					<td class="inline-ui-cell" style="min-width: 150px;"><fmt:message key="label.piano" /></td>
					<td class="inline-ui-cell" colspan="5">						
						<spring-form:input id="piano_id" path="istanzeFilter.istanzestradario.piano" 
							size="5" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.interno" /></td>
					<td class="inline-ui-cell" style="min-width: 300px;" colspan="2">						
						<spring-form:input id="interno_id" path="istanzeFilter.istanzestradario.interno" 
							size="5" />						
					</td>	
					<td class="inline-ui-cell" style="min-width: 150px;"><fmt:message key="label.esponente_interno" /></td>
					<td class="inline-ui-cell" colspan="5">						
						<spring-form:input id="esponente_interno_id" path="istanzeFilter.istanzestradario.esponenteinterno" 
							size="5" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.fabbricato" /></td>
					<td class="inline-ui-cell" style="min-width: 300px;" colspan="2">						
						<spring-form:input id="fabbricato_id" path="istanzeFilter.istanzestradario.fabbricato" 
							size="5" />
					</td>	
					<td class="inline-ui-cell" style="min-width: 150px;"><fmt:message key="label.frazione" /></td>
					<td class="inline-ui-cell" colspan="5">	
						<spring-form:input id="frazione_id" path="istanzeFilter.istanzestradario.frazione" 
							size="5" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.cap" /></td>
					<td class="inline-ui-cell" style="min-width: 300px;" colspan="2">						
						<spring-form:input id="caps_id" path="istanzeFilter.istanzestradario.cap" 
							size="5" />
					</td>	
					<td class="inline-ui-cell" style="min-width: 150px;"><fmt:message key="label.quartiere" /></td>
					<td class="inline-ui-cell" colspan="5">						
						<spring-form:input id="quartiere_id" path="istanzeFilter.istanzestradario.quartiere" 
							size="5" />
					</td>
				</tr>				
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.note" /></td>
					<td colspan="7">		
						<spring-form:input id="notestradario_id" path="istanzeFilter.istanzestradario.note" size="70" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.circoscrizione" /></td>
					<td colspan="7">						
						<spring-form:input id="circoscrizione_id" path="istanzeFilter.istanzestradario.circoscrizione" size="70" />
					</td>
				</tr>				
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td>
						<c:if test="${isStradariocoloreVisible eq true }">
						<fmt:message key="label.colore" />
						</c:if>
					</td>
					<td colspan="7" class="inline-ui-cell">
						<c:if test="${isStradariocoloreVisible eq true }">
							<spring-form:select id="colore_id" path="istanzeFilter.istanzestradario.stradariocolore.id.codicecolore">
								<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
							    <spring-form:options items="${stradariocoloreList }" itemValue="id.codicecolore" itemLabel="colore"/>
							</spring-form:select>
							&nbsp;
						</c:if>
						<!--Gestisce la visualizzazione della ricerca per altri indirizzi  -->
					    <%
							String isChecked = "";
							//gestisce la visualizzazione della ricerca per altri indirizzi
							if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI))) {
							    isChecked = "checked";
							} else {
							    isChecked = "";
							}
						%>
						<input type="checkbox" value="1" name="istanzeFilter.cercalocalizzazioneinaltri" <%=isChecked%> onclick="javascript:saveUserPreference('<%=WebConstants.CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI%>',(this.checked==true) ? 1 : 0)"/>
						<label for="cercalocalizzazioneinaltri_id"><fmt:message key="label.cerca_localizzazione_in_altri_indirizzi" /></label>
						</td>
				</tr>		
				<tr id="id_localizzazione_table" style="<%=displayLocalizzazione%>">
					<td><fmt:message key="label.dati_catastali" /></td>
					<td class="inline-ui-cell">						
						<label><fmt:message key="label.catasto" /></label>
					</td>	
					<td class="inline-ui-cell">
						<spring-form:select id="catasto_id" path="istanzeFilter.istanzemappali.catasto.codice">
							<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
						    <spring-form:options items="${catastoList}" itemValue="codice" itemLabel="descrizione"/>
						</spring-form:select>
					</td>	
					<td class="inline-ui-cell">
						<fmt:message key="label.foglio" />
					</td>	
					<td class="inline-ui-cell">
						<spring-form:input id="sub_id" path="istanzeFilter.istanzemappali.foglio" size="5" />
					</td>	
					<td class="inline-ui-cell">
						<fmt:message key="label.particella" />
					</td>	
					<td class="inline-ui-cell">
						<spring-form:input id="sub_id" path="istanzeFilter.istanzemappali.particella" size="5" />
					</td>	
					<td class="inline-ui-cell">
						<fmt:message key="label.sub" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="sub_id" path="istanzeFilter.istanzemappali.sub" size="5" />
					</td>												
				</tr>
</c:if>				
				<%
					String displayProgetto = "display:none;";
					String styleProgetto = "";
					//gestisce la visualizzazione della tabella altri dati
					if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_SEARCH_DATI_PROGETTO))) {
					    displayProgetto = "";
					    styleProgetto="sezioneDatiMeno";
					} else {
					    displayProgetto = "display:none;";
					    styleProgetto="sezioneDatiPiu";
					}
				%>			
				
				<tr class="titoloSezione">
					<td colspan="8">
						<a class="<%=styleProgetto %>" 
							id="id_link_progetto" 
							href="javascript:showHidePanel('id_progetto_table', 'id_link_progetto', '<%= WebConstants.CONF_UTENTE_SEARCH_DATI_PROGETTO %>', '${pageContext.request.contextPath}/images/');"	
							title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dati_progetto"/>">
							<label for="id_link_progetto"><fmt:message key="label.dati_progetto"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_progetto_table" style="<%=displayProgetto%>">
					<td valign="top"><fmt:message key="label.alberoproc" /></td>
					<td colspan="7" >
						<jsp:include page="../includes/searchAlberoProc.jsp">
							<jsp:param name="propertyPath" value="istanzeFilter.alberoproc" />								
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.alberoproc.vwAlberoproc.scDescrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.alberoproc.id.codice" />
							<jsp:param name="isSelectLeafDisable" value="true" />
							<jsp:param name="isSelectNodoPadre" value="true" />
						</jsp:include>
					</td>						
				</tr>

<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldProcedura')}">				
				<tr id="id_progetto_table" style="<%=displayProgetto%>">
					<td><fmt:message key="label.tipiprocedure.procedura" /></td>
					<td colspan="7" class="inline-ui-cell">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipiprocedure_id" />				
							<jsp:param name="propertyPath" value="istanzeFilter.procedura" />			
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.procedura.procedura" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.procedura.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=true" />
							<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
						</jsp:include>
					</td>
				</tr>				
</c:if>				
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldTipoMovimento')}">
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
						<td><fmt:message key="label.tipomovimento" /></td>
						<td colspan="7" class="inline-ui-cell">
							<jsp:include page="../includes/tipimovimentosearch.jsp" >
								<jsp:param name="idElemento" value="tipoMovimentoInputId" />
								<jsp:param name="pathTipomovimento" value="istanzeFilter.tipoMovimento" />
								<jsp:param name="includiDisabilitate" value="true" />
							</jsp:include>					
						</td>
					</tr>
					
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
					
						<td></td>
						<td class="inline-ui-cell" style="min-width: 280px;" colspan="2">
							<label><fmt:message key="label.dalla_data" /></label>&nbsp;
							<spring-form:input id="dallaDataTipoMov_id" path="istanzeFilter.dallaDataTipoMov" size="10" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaDataTipoMov_id" textKey="label.calendar" />	  	
							<spring-form:errors path="istanzeFilter.dallaDataTipoMov" cssClass="error" delimiter="," />
						</td>
						<td colspan="5" class="inline-ui-cell">
							<label><fmt:message key="label.alla_data" /></label>&nbsp;
							<spring-form:input id="allaDataTipoMov_id" path="istanzeFilter.allaDataTipoMov" size="10" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaDataTipoMov_id" textKey="label.calendar" /> 
							<spring-form:errors path="istanzeFilter.allaDataTipoMov" cssClass="error" delimiter="," />
						</td>
					</tr>
					
				</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldEndoProcedimento')}">
					<c:if test="${isTipifamiglieendoVisible eq true}">
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
						</td>
						<td colspan="7" class="inline-ui-cell">
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="famiglia_id" />		
								<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo" />					
								<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo" />
								<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipifamiglieendoSWeTT.htm" />
								<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
							</jsp:include>
						</td>
					</tr>
					</c:if>
					
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
						</td>
						<td colspan="7" class="inline-ui-cell">
							<script type="text/javascript">
								function filtertipiendo(element, entry) {
									if(document.getElementById("famiglia_id_hidden")){
										return entry + "&codiceFamiglia=" + document.getElementById("famiglia_id_hidden").value;
									}else{
										return entry ;
									}
								}
							</script>
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="tipiendo_id" />						
								<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti.tipoendo" />
								<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipo" />
								<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.tipoendo.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipiendoSWeTT.htm" />
								<jsp:param name="titleKey" value="label.ricerca_tipiendo" />
								<jsp:param name="ajaxCallBack" value="filtertipiendo" />
							</jsp:include>						
						</td>
					</tr>
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
						</td>
						<td colspan="7" class="inline-ui-cell">
							<script type="text/javascript">
								function inventarioCallBack(inputField,listItem){
									var a = listItem.id;
									document.getElementById('inventarioprocedimento_id').value = inputField.value;
									document.getElementById('inventarioprocedimento_hidden').value = a;
									$('inventarioprocedimento_id_choices').fade();	
								}
								function filterinventario(element, entry) { 
									if(document.getElementById("famiglia_id_hidden")){
										return entry + "&escludiDisabilitati=false&codiceFamiglia=" + document.getElementById("famiglia_id_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_id_hidden").value;
									}else{
										return entry + "&escludiDisabilitati=false&codiceTipologia=" + document.getElementById("tipiendo_id_hidden").value;
									}
								}
							</script>
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="inventarioprocedimento" />					
								<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti" />	
								<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.procedimento" />
								<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.id.codice" />
								<jsp:param name="autocompleterAjax" value="findInventarioprocedimento.htm" />
								<jsp:param name="titleKey" value="label.ricerca_inventarioprocedimento" />
								<jsp:param name="ajaxCallBack" value="filterinventario" />
								<jsp:param name="afterUpdateElement" value="inventarioCallBack" />
							</jsp:include>
						</td>
					</tr>	
				</c:if>				
				<c:if test="${isSettoriVisible eq true }">

<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeSettori')}">				
				<tr id="id_progetto_table" style="<%=displayProgetto%>">
					<td>
						<fmt:message key="label.tipo_informazione" />
					</td>
					<td colspan="7" class="inline-ui-cell">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="settori" />					
							<jsp:param name="propertyPath" value="istanzeFilter.istanzeattivita.attivita.settori" />	
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzeattivita.attivita.settori.settore" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzeattivita.attivita.settori.id.codicesettore" />
							<jsp:param name="autocompleterAjax" value="findSettori.htm" />
							<jsp:param name="titleKey" value="label.ricerca_settori" />
						</jsp:include>
					</td>
				</tr>
</c:if>				
				</c:if>
				<c:if test="${isAttivitaVisible eq true }">

<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeAttivita')}">				
				<tr id="id_progetto_table" style="<%=displayProgetto%>">
					<td>
						<fmt:message key="label.dettaglio_informazione" />
					</td>
					<td colspan="7" class="inline-ui-cell">
						<script type="text/javascript">
							function filtertiposettore(element, entry) {
								if(document.getElementById("settori_hidden")){	
									return entry + "&codicesettore=" + document.getElementById("settori_hidden").value;
								}else{
									return entry;
								}
							}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="attivita" />			
							<jsp:param name="propertyPath" value="istanzeFilter.istanzeattivita.attivita" />			
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzeattivita.attivita.istat" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzeattivita.attivita.id.codiceistat" />
							<jsp:param name="autocompleterAjax" value="findAttivita.htm" />
							<jsp:param name="titleKey" value="label.ricerca_attivita" />
							<jsp:param name="ajaxCallBack" value="filtertiposettore" />
						</jsp:include>						
					</td>
				</tr>
</c:if>				
				</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldDescrizioneLavori')}">
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
						<td><fmt:message key="label.lavori" /></td>
						<td colspan="7">
							<spring-form:textarea id="lavori_id"  path="istanzeFilter.lavori" cols="73" rows="2" />
						</td>
					</tr>
				</c:if>
				
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeNomeAttivita')}">				
				<tr id="id_progetto_table" style="<%=displayProgetto%>">
					<td><fmt:message key="label.denominazione_attivita" /></td>
					<td colspan="7">
						<spring-form:input id="nomeattivita_id"  path="istanzeFilter.nomeattivita" size="70" />
					</td>
				</tr>
				</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldNote')}">
					<tr id="id_progetto_table" style="<%=displayProgetto%>">
						<td><fmt:message key="label.lavoriestesa" /></td>
						<td colspan="7">
							<spring-form:textarea id="lavoriestesa_id" path="istanzeFilter.lavoriestesa" cols="73" rows="2" />
						</td>
					</tr>
				</c:if>

				<tr id="id_progetto_table" style="<%=displayProgetto%>">
					<td>
						<fmt:message key="label.stato_istanza" />
					</td>
					<td colspan="7">
						<spring-form:select id="statoistanza_id" path="istanzeFilter.chiusura.id.codicestato" onchange="savePreferenceStatoistanza('statoistanza_id')">
						    <spring-form:option value="stato_tutte"><fmt:message key="label.tutte"/></spring-form:option>
						    <spring-form:option value="stato_aperte"><fmt:message key="label.tutte_le_istanze_non_chiuse"/></spring-form:option>
						    <spring-form:option value="stato_chiuse"><fmt:message key="label.tutte_le_istanze_chiuse"/></spring-form:option>
							<spring-form:options items="${statiistanzaList}" itemValue="id.codicestato" itemLabel="stato"/>
						</spring-form:select>
					</td>
				</tr>													
				<tr>
					<td colspan="8" style="font-size: 2px;"><hr/></td>
					
				</tr>				
				<tr>
					<td>
						<fmt:message key="label.ordinare_la_lista_per" />
					</td>
					<td colspan="7" class="inline-ui-cell">
						<spring-form:select id="ordinamento_id" path="istanzeFilter.orderBy" onchange="savePreferenceFieldOrder('ordinamento_id')">
						    <spring-form:option value="data"><fmt:message key="label.data_presentazione"/></spring-form:option>
						    <spring-form:option value="richiedente.nominativo"><fmt:message key="label.richiedente"/></spring-form:option>
							<spring-form:option value="numeroistanza"><fmt:message key="label.numeroistanza"/></spring-form:option>
							<spring-form:option value="istanzestradarios.stradario.descrizione"><fmt:message key="label.localizzazione"/></spring-form:option>
							<spring-form:option value="dataprotocollo"><fmt:message key="label.data_protocollo"/></spring-form:option>
							<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldPosizioneArchivio')}">
								<spring-form:option value="posizionearchivio"><fmt:message key="label.posizione_in_archivio"/></spring-form:option>
							</c:if>
						</spring-form:select>
						<!--Gestisce il tipo ordinamento nella ricerca delle istaze (ASC,DESC)  -->
					   
					   
							<% 

								String ordinamentoAsc = "";
						        String ordinamentoDesc = "";
								//gestisce la visualizzazione della ricerca per altri indirizzi
								if ("ASC".equals(((String) request.getAttribute(WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE)))) {
								    ordinamentoAsc = "selected";
								    ordinamentoDesc = "";
								} else {
								    ordinamentoAsc = "";
								    ordinamentoDesc = "selected";
								}
						
							%>
						
						<spring-form:select id="ordinamentoascdesc_id" path="istanzeFilter.orderAscDesc" onchange="savePreferenceTypeOrder('ordinamentoascdesc_id')">
						    <option value="<%=DAOOrderTypeEnum.ASC%>" <%=ordinamentoAsc%>><fmt:message key="label.ordinamento_asc"/></option>
						    <option value="<%=DAOOrderTypeEnum.DESC%>" <%=ordinamentoDesc%>><fmt:message key="label.ordinamento_desc"/></option>
						</spring-form:select>
					 </td>
				</tr>					
				<%-- Gestione Configurazione Utente --%>
				<tr>
					<td>
						<fmt:message key="label.visualizza_dettaglio_istanza" />					
					</td>
					<td colspan="7">
						<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISISTANZA_id" 
						    onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISISTANZA_CHECKED}/>
					</td>
				</tr>
				<tr>
					<td><label for="ricercaVeloce_id"><fmt:message key="label.ricerca_veloce_istanze" /></label></td>
					<td colspan="7">
					<input type="checkbox" id="ricercaVeloce_id" name="istanzeFilter.ricercaVeloce" onclick="salvaPreferenza('CONF_UTENTE_LISTISTANZA_NUMRECORDLISTE',this)" ${CONF_UTENTE_NUMRECORDLISTE_CHECKED}/>
					&nbsp;<init:help idHelp="helpRicercaVeloce" textKey="label.ricerca_veloce_istanze.help"/>
					</td>
				</tr>
	    	</table>
	    	<script type="text/javascript">			
				function salvaPreferenza(nomeparametro, objchk){
					var valore = "0";	
					if(objchk.checked==true){
						valore="1";	
					}
					saveUserPreference(nomeparametro, valore);
				}
			</script>	    	
	    </spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:searchIstanze(${istanzeCommand.tipoRicerca});"><fmt:message key="button.search" /></a></li>
			 
			<!-- Il bottone "nuovo" deve comparire solo se veniamo dalla ricerca base delle istanze  -->
			<c:if test="${istanzeCommand.tipoRicerca == null || istanzeCommand.tipoRicerca == SEARCH_ISTANZE}">
				<li><a href="javascript:historySet('${_urlback}', '../istanze/create.htm', '')"><fmt:message key="button.new" /></a></li>
			</c:if>
			<li><a href="javascript:historySet('${_urlback}', '../istanze/listComunicazioniM.htm', '')"><fmt:message key="button.comunicazioni.massive" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
		</ul>
	</div>

	<div class="notifichePopup" id="notificaDomandeOnline" style="display:none">
		<div class="notifichePopupTitle">pannello notifiche</div>
		<c:if test="${not empty stcConAnomalie}">
			<c:forEach items="${stcConAnomalie}" var="cvb">
				<div class="notifichePopupInner2">
					<c:choose>
						<c:when test="${cvb.chiave eq 'IN_ATTESA' }">
							<img src="${pageContext.request.contextPath}/images/retestc_out_wait.gif" class="elab_image elab_image_38" title=""/>
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/images/retestc_out_ko.gif" class="elab_image elab_image_38" title=""/>
						</c:otherwise>
					</c:choose>					
					<a href="javascript:ricercaAnomalieSTC('${cvb.chiave}')">
						${cvb.valore}					
					Movimenti
					<c:choose>
						<c:when test="${cvb.chiave eq 'IN_ATTESA' }">
							in attesa di conferma consegna
						</c:when>
						<c:otherwise>
							con errore di consegna
						</c:otherwise>
					</c:choose>
					</a>
				</div>
			</c:forEach>
		</c:if>	
	
				<c:if test="${countIstanzeInWarning>0}">
					<div class="notifichePopupInner">
						<a href="javascript:searchIstanzeInWarning()">
							${countIstanzeInWarning}&nbsp;<fmt:message key="label.istanze_stato_flag_warning" />
						</a>
					</div>
				</c:if>
	
		<c:if test="${istanzeOnLineHelper.notificaVisibile eq true}">
				<c:if test="${istanzeOnLineHelper.numDomandeConErrori>0}">
					<div class="notifichePopupInner">
						<a href="javascript:ricercaDomandeFrontOffice('online_errori')">
							${istanzeOnLineHelper.numDomandeConErrori}&nbsp;<fmt:message key="label.nuove_domande_stc_con_errori" />
						</a>
					</div>				   
				</c:if>
				<c:if test="${not empty istanzeOnLineHelper.domandePervenute}">
					<c:forEach var="istanzeOnline" items="${istanzeOnLineHelper.domandePervenute}">
						<div class="notifichePopupInfoInner">
							<c:set var="codiceDescrizione" value="${fn:split(istanzeOnline.key,'|')}"/>
							<a href="javascript:ricercaDomandeFrontOffice('online_ok','${codiceDescrizione[0]}','${codiceDescrizione[2]}','${codiceDescrizione[3]}')">
								${istanzeOnline.value}&nbsp;<fmt:message key="label.nuove_domande_stc" /> ${codiceDescrizione[1]}
							</a>
						</div>
					</c:forEach>	
				</c:if>
				<c:if test="${countIstanzeDaAssegnaAdIstruttore>0}">
					<div class="notifichePopupInfoInner">
						<a href="javascript:searchIstanzeDaAssegnare()">
							${countIstanzeDaAssegnaAdIstruttore}&nbsp;<fmt:message key="label.nuove_istanze_da_assegnare" />
						</a>
					</div>
				</c:if>
				<c:if test="${countIstanzeAssegnaAdIstruttore>0}">
					<div class="notifichePopupInfoInner">
						<a href="javascript:searchIstanzeDaAccettare()">
							${countIstanzeAssegnaAdIstruttore}&nbsp;<fmt:message key="label.nuove_istanze_da_accettare" />
						</a>
					</div>
				</c:if>
				
								
		</c:if>
	</div>
	
	<script type="text/javascript">
		var START_OPACITY = 0.8;
		var mostraNotifica = ${istanzeCommand.tipoRicerca == WebConstants.SEARCH_ISTANZE || istanzeCommand.tipoRicerca==null};
		console.log("mostra pannello: "+mostraNotifica);
		if(mostraNotifica){
			jQuery(function(){
				if(jQuery('#notificaDomandeOnline').html()=="")
				return;
				jQuery('#notificaDomandeOnline').fadeTo('slow',START_OPACITY,function(){ jQuery(this).css('display','block'); })
				   .hover(
						function() {
							jQuery(this).stop().animate({"opacity": "1"}, "slow");
						},
						function() {
							jQuery(this).stop().animate({"opacity": START_OPACITY}, "slow");
				   });
			});
		}
	</script>
	
	<script type="text/javascript">
	$('numeroistanza_id').focus();
	var executed = false;
	jQuery("*").keypress(function(e) {
	  	  	var code = e.keyCode ? e.keyCode : e.which;
			if(code.toString() == 13) {
			if(!executed){
				executed=true;
					  searchIstanze(${istanzeCommand.tipoRicerca});	   
				  }
			}
	});
	
	function ajaxHistorySet(url){
		
		var jhqr = jQuery.ajax({
			  url: '../history/ajaxSet.htm?ReturnTo='+url.replace('cercasubito','_###_'),
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  success: function(data) { 				   
				} 
			});
		
	}
	
	function searchIstanze(isIstanzecollegate){
		if(isIstanzecollegate==undefined || isIstanzecollegate==0){	
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
		}
		setTimeout("doSubmit('listIstanze.htm','',document.inviodati)",10);;
	}
	/* function searchIstanzeDaAssegnare(isIstanzecollegate){
		if(isIstanzecollegate==undefined || isIstanzecollegate==false){	
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
		}
		setTimeout("doHref('listIstanzeDaAssegnare.htm','')",10);;
	} */
	function searchIstanzeDaAssegnare(isIstanzecollegate){
		if(isIstanzecollegate==undefined || isIstanzecollegate==0){	
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
		}
		setTimeout("doHref('listIstanzeDaAssegnare.htm','')",10);;
	}
	
	/* function searchIstanzeDaAccettare(isIstanzecollegate){
		if(isIstanzecollegate==undefined || isIstanzecollegate==false){	
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
		}
		setTimeout("doHref('listIstanzeDaAccettare.htm','')",10);;
	} */
	function searchIstanzeDaAccettare(isIstanzecollegate){
		if(isIstanzecollegate==undefined || isIstanzecollegate==0){	
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
		}
		setTimeout("doHref('listIstanzeDaAccettare.htm','')",10);;
	}
	
	function searchIstanzeInWarning(){
		setTimeout("doHref('listIstanzeInWarning.htm','')",10);
	}
	
	function savePreferenceTypeOrder(obj){
		var a=document.getElementById(obj).value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE%>',a);
	}
	
	function savePreferenceFieldOrder(obj){
		var a=document.getElementById(obj).value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE%>',a);
	}
	
	function savePreferenceStatoistanza(obj){
		var a=document.getElementById(obj).value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA%>',a);
	}
	
	
	function ricercaDomandeFrontOffice(tipo, idnodo,identemitt, idsportellomitt){
		ajaxHistorySet(URLDecode('${_urlback}'));
	    var _idsportellomitt=	URLEncode(idsportellomitt)
		doSubmit('istanzeOnline.htm?tipo='+tipo+"&idnodo="+idnodo+"&identemitt="+identemitt+"&idsportellomitt="+_idsportellomitt,'',document.inviodati);
	}
	
	function ricercaAnomalieSTC(stato){
		ajaxHistorySet(URLDecode('${_urlback}'));	    
		doSubmit('anomalieSTC.htm?stato='+stato,'',document.inviodati);		
	}
	
	
	vbg.ready(() => {
		if('${param.cercasubito}'==='true'){
			window.vbg.mostraModalCaricamento();
			setTimeout("searchIstanze("+${istanzeCommand.tipoRicerca}+")", 2000);
		}
	});
	
	
	</script>
	
</body>
</html>