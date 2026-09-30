<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.net.URLEncoder"%>
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
		<fmt:message key="pecinbox.creaistanza.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="pecinbox.creaistanza.title" />
	</span>
	<br  class="clear" />
		<spring-form:form commandName="istanzaPecCommand" name="istanzaDaPecForm" id="istanzaDaPecForm" action="${pageContext.request.contextPath}/pecinbox/creaIstanza.htm">
		
		<spring-form:hidden path="pec.id.id" />
		
			<br />
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzaPecCommand" />
		    </jsp:include>

<%

String pecEncoded = request.getParameter("codicePec");

if(pecEncoded == null){
       // out.println("<br/>...");
        pecEncoded =  ((PECCommand)request.getAttribute("istanzaPecCommand")).getPec().getId().getId();
}

//out.print("<br />==================================");
// out.print("<br />"+pecEncoded);
pecEncoded = pecEncoded.replaceAll("[\\+]", "%2b");
// out.print("<br />"+pecEncoded);
pecEncoded = URLEncoder.encode(pecEncoded );
//out.print("<br />"+pecEncoded);
// out.print("<br />==================================");

// String pecEncoded = URLEncoder.encode(request.getParameter(codicePec).replaceAll("+","%2B"));
pageContext.setAttribute("pecCodificata", pecEncoded);



%>
			<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../pecinbox/istanzaDaPEC" />
				<jsp:param name="qs"
					value="codicePec%3D${pecCodificata}%26idAccount%3D${param.idAccount}%26software%3D${ istanzeCommand.entity.software.codice }" />
			</jsp:include>			    
		    
		    <c:if test="${ not empty istanzaPecCommand.istanza.id.codice }">
		    	<c:choose>
			    	<c:when test="${ not istanzaPecCommand.stcError eq true  }">
					    <div id="status_msg" class="success_header" >
				          <fmt:message key="pecinbox.message.istanzacreata">
				          	<fmt:param>${istanzaPecCommand.istanza.numeroistanza}</fmt:param>
				          </fmt:message>
					    </div><br/>
				    </c:when>
				    <c:otherwise>
					    <div id="status_msg" class="error_header" >
				          <fmt:message key="pecinbox.message.erroreistanzastc">
				          	<fmt:param>${istanzaPecCommand.istanza.numeroistanza}</fmt:param>
				          </fmt:message>
					    </div><br/>
				    </c:otherwise>
			    </c:choose>
		    </c:if>
			<table width="100%" border="0">
				<tr class="titoloSezione">
					<td colspan="6">
						<fmt:message key="pecinbox.label.dati_pec"/>		
					</td>
				</tr>
				<jsp:include page="datiPEC.jsp" >
			        <jsp:param name="commandName" value="istanzaPecCommand" />
			    </jsp:include>
				<tr>
					<td><fmt:message key="pecinbox.label.scompattaallegaticompressi" /></td>
					<td colspan="5">
						<spring-form:checkbox path="scompattaAllegati" value="true" />
						<init:help idHelp="helpScompattaAllegati" textKey="pecinbox.label.scompattaallegaticompressi.helpistanza" />
					</td>
				</tr>
				<c:if test="${not empty istanzaPecCommand.pec}">
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="label.dati_istanza"/>		
						</td>
					</tr>
					<c:if test="${empty error }">
						<c:if test="${not empty istanzaPecCommand.istanza.id.codice }">
							<tr>
								<td style="vertical-align: top;"><fmt:message key="label.numeroistanza" /></td>
								<td colspan="5">
									<c:if test="${istanzaPecCommand.stcError eq true}">
										<span id="label_numeroistanza" class="error">
											<fmt:message key="pecinbox.label.istanzaerrorestc" >
												<fmt:param>${istanzaPecCommand.istanza.numeroistanza }</fmt:param>
											</fmt:message>
										</span>
									</c:if>
									<c:if test="${not istanzaPecCommand.stcError eq true}">
										<span id="label_numeroistanza"><input type="text" class="inputRed" id="_entity_numeroistanza_id" readonly="readonly" name="istanza.numeroistanza" size="40" value="${istanzaPecCommand.istanza.numeroistanza}"/></span>
									</c:if>
								</td>
							</tr>
						</c:if>
						<tr>
							<td><fmt:message key="label.data" /></td>
							<td colspan="5">
								<spring-form:input readonly="true" id="data_id" path="istanza.data" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>
								<spring-form:input readonly="true" id="oraInserimento_id" path="istanza.oraInserimento" size="10"  cssStyle="font-weight: bolder;"/>
							</td>
						</tr>
						<%-- 
						<c:if test="${true}">
						--%>
							<jsp:include page="../includes/comboComuni.jsp">
								<jsp:param name="mostraTutti" value="false" />
								<jsp:param name="emptyLabelTutti" value="false" />						
								<jsp:param name="readOnly" value="${istanzaPecCommand.readOnly}" />
								<jsp:param name="commandPropertyPath" value="istanza.comune" />
								<jsp:param name="colspan" value="5" />
								<jsp:param name="comune" value="${istanzaPecCommand.istanza.comune.codicecomune}" />
								<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
							</jsp:include>	
						<%-- 
						</c:if>
						<c:if test="${istanzaPecCommand.readOnly and not empty istanzaPecCommand.istanza.comune.comune}">
							<tr>
								<td style="vertical-align: top;"><fmt:message key="label.combocomuni" /></td>
								<td colspan="5">
									<spring-form:input id="descComune_id" 
										path="istanza.comune.comune" 
										size="70" 
										readonly="true"
										cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
						</c:if>
						--%>
						<!-- richiedente -->
						<tr>
							<td style="vertical-align: top;">
								<c:if test="${not istanzaPecCommand.readOnly}">
									<label class="required">*</label>
								</c:if>
								<fmt:message key="label.richiedente" />
							</td>
							<td colspan="5">
								<c:if test="${not istanzaPecCommand.readOnly}">
									<jsp:include page="../includes/anagraficasearch.jsp" >
										<jsp:param name="idElemento" value="richiedenteIdCodice" />						
										<jsp:param name="pathAnagrafica" value="istanza.richiedente" />
										<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=F" />
										<jsp:param name="tiposoggetto"  value="F"/>
									</jsp:include>
								</c:if>
								<c:if test="${istanzaPecCommand.readOnly}">
									<spring-form:input id="richiedente_id"
										path="istanza.richiedente.descrizioneRichiedente"
										cssStyle="font-weight: bolder;" 
										readonly="true" 
										size="70" />
								</c:if>
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
										<c:if test="${not istanzaPecCommand.readOnly}">
											<jsp:include page="../includes/autocompletergenerico.jsp" >
												<jsp:param name="idElemento" value="tipisoggetto_id" />
												<jsp:param name="propertyPath" value="istanza.tipisoggetto" />
												<jsp:param name="pathPropertyDescription" value="istanza.tipisoggetto.tiposoggetto" />
												<jsp:param name="pathPropertyCode" value="istanza.tipisoggetto.id.codice" />
												<jsp:param name="autocompleterAjax" value="findTipisoggettoAndSpecificadescrizione.htm?flagQualita=true" />							
												<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
												<jsp:param name="afterUpdateElement" value="setFieldTipisoggetto" />
											</jsp:include>
										</c:if>
										<c:if test="${istanzaPecCommand.readOnly}">
											<spring-form:input id="tipisoggetto_id"
												path="istanza.tipisoggetto.tiposoggetto"
												cssStyle="font-weight: bolder;" 
												readonly="true" 
												size="70" />
										</c:if>
									</td>
								</tr>
								<c:set var="displayDescrSoggetto" value="display: none;" />
							
								<c:if test="${istanzaPecCommand.istanza.tipisoggetto.flgSpecificadescrizione eq true}">
									<c:set var="displayDescrSoggetto" value="" />
								</c:if>
							
								<tr id="TR_DESCRSOGGETTO" style="${displayDescrSoggetto}">
									<td>&nbsp;</td>
									<td colspan="5">
										<spring-form:input path="istanza.descrsoggetto" size="70" />
										<spring-form:errors path="istanza.descrsoggetto" cssClass="error"/>
										&nbsp;<fmt:message key="label.specificare_la_tipologia_di_soggetto" />
									</td>
								</tr>				
								<tr>
									<td style="vertical-align: top;"><fmt:message key="label.ragione_sociale" /></td>
									<td colspan="5">
										<c:if test="${not istanzaPecCommand.readOnly}">
											<jsp:include page="../includes/anagraficasearch.jsp" >
												<jsp:param name="idElemento" value="titolareLegaleIdCodice" />						
												<jsp:param name="pathAnagrafica" value="istanza.titolarelegale" />
												<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=G" />
												<jsp:param name="tiposoggetto"  value="G"/>
											</jsp:include>
										</c:if>
										<c:if test="${istanzaPecCommand.readOnly}">
											<%-- 
											<c:set var="descrRichiedente" value="" />										
											<c:if test="${istanzaPecCommand.istanza.titolarelegale not empty}">
												<c:set var="descrRichiedente" value="${istanzaPecCommand.istanza.titolarelegale.descrizioneRichiedente}" />
											</c:if>
											--%>
											<spring-form:input id="titolarelegale_id"
												path="titolarelegale.descrizioneRichiedente"
												cssStyle="font-weight: bolder;" 
												readonly="true" 
												size="70" />
										</c:if>
									</td>
								</tr>
								
							</c:when>
							<c:otherwise>
								<spring-form:hidden path="istanza.tipisoggetto.id.codice" />
								<spring-form:hidden path="istanza.descrsoggetto" />
								<spring-form:hidden path="istanza.titolarelegale.id.codice" />
							</c:otherwise>
						</c:choose>					 
						<%--  
						<tr>
							<td style="vertical-align: top;"><fmt:message key="pecinbox.label.aziendarichiedente" /></td>
							<td colspan="5">
								<c:if test="${not istanzaPecCommand.readOnly}">
									<jsp:include page="../includes/anagraficasearch.jsp" >
										<jsp:param name="idElemento" value="titolareLegaleIdCodice" />						
										<jsp:param name="pathAnagrafica" value="titolarelegale" />
										<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=G" />
										<jsp:param name="tiposoggetto"  value="G"/>
									</jsp:include>
								</c:if>
								<c:if test="${istanzaPecCommand.readOnly}">
									<spring-form:input id="titolarelegale_id"
										path="titolarelegale.descrizioneRichiedente"
										cssStyle="font-weight: bolder;" 
										readonly="true" 
										size="70" />
								</c:if>
							</td>
						</tr>
						 --%>
						<tr>
							<td><fmt:message key="label.codice_pratica_telematica" /></td>
							<td colspan="5">
								<spring-form:input id="codice_pratica_telematica_id" 
								path="istanza.codicepraticatel" 
								size="70" 
								readonly="${istanzaPecCommand.readOnly}"
								cssStyle="font-weight: bolder;"/>
								<spring-form:errors path="istanza.codicepraticatel" cssClass="error" />
								<c:if test="${istanzaPecCommand.readOnly eq false}">
									<a class="vbg-btn btn-calcola" href="javascript: void(0);" id="btn_calcola" title="<fmt:message key="pecinbox.button.calcolacodicepratica"/>">
									</a>
									<c:set var="helpCodiceTelText"><fmt:message key="label.codice_pratica_telematica.help" /></c:set>
									<init:help idHelp="helpCodiceTelematico" text="${helpCodiceTelText}" />	
								</c:if>		
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.domicilio_elettronico" /></td>
							<td colspan="5">
								<spring-form:input id="domicilioElettronico_id" 
								path="istanza.domicilioElettronico" 
								size="70" 
								readonly="${istanzaPecCommand.readOnly}"
								cssStyle="font-weight: bolder;"/>
								<spring-form:errors path="istanza.domicilioElettronico" cssClass="error" />
							</td>
						</tr>
						<tr id="id_progetto_table">
							<td valign="top">
								<c:if test="${not istanzaPecCommand.readOnly}">
									<label class="required">*</label>
								</c:if>
								<fmt:message key="label.alberoproc" />
							</td>
							<td colspan="5">
							<spring-form:input id="alberoproc_hidden" path="intervento.id.codice" onchange="cercaProcedimento()" size="9" cssStyle="text-align: right; font-weight: bolder;" readonly="${istanzaPecCommand.readOnly}"/>
							<%-- ALBEROPROC DOJO TREE --%>
							<c:if test="${not istanzaPecCommand.readOnly}">
								<a class="vbg-btn btn-cerca" href="javascript:cercaProcedimento();" style="vertical-align: bottom;" title="Cerca procedimento" >
								</a>
							</c:if>
							<spring-form:input id="alberoproc_descrestesa_hidden" path="intervento.vwAlberoproc.scDescrizione" size="100" readonly="true" cssStyle="font-weight: bolder;"/>
							<spring-form:errors path="intervento" cssClass="error" />
							<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" 
								url="${pageContext.request.contextPath}/json/getAlberoprocPec.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>"> 
							</div>
							<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" 
								rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" 
								childrenAttrs="children">
							</div>
							<br />
							<div id="treeOne"></div>
		   	  				<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div>
							<script type="text/javascript">
										var treeControl = null;
										var treeInitialized = false;
										
										function cercaProcedimento(){									
											var codProc = $('alberoproc_hidden').value;
											rimuoviValori2();
											if(codProc){
												cercaProcedimentoAjax(codProc);	
											}else{
												apriAlbero();
											}
											$('alberoproc_hidden').focus();
										}
										
										function cercaProcedimentoAjax(codiceAlberoproc){
											if(isNaN(codiceAlberoproc)){
												displayErrorMessage("Ricerca per codice. Inserire un valore numerico");
												return;
											}
											new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=true', {
												  method: 'post',
												  parameters: {id: codiceAlberoproc},
												  onSuccess: function(transport){ 
													var response = transport.responseText;
													//displayErrorMessage(response);
													var json = response.evalJSON();
													if(json.id){
														if(json.padre == 'true'){
															displayErrorMessage("Procedimento non selezionabile.");
														}else{
															assegnaValori2(json);
															$('treeOne').style.display="none";
														}						
													}else{
														displayErrorMessage("Procedimento non trovato o disattivato.");
												    }
												  },
												  onFailure: function(transport){ 
													var response = transport.responseText; 
												    displayErrorMessage("Errore nella ricerca del procedimento!");
												  }						    		 
											} );
										}
										
										jQuery($('alberoproc_hidden')).keypress(function(e) {
									  	  	var code = e.keyCode ? e.keyCode : e.which;
											if(code.toString() == 13) {
												cercaProcedimento(); 
											}
									    });
										
										function apriAlbero() {
									        if(!treeControl){
										        treeControl = new dijit.Tree({
										            model: alberoprocModel,
										            showRoot: true,							            
										            onClick: function(item, node){
										            	if(item.id !='0' ){	
											        		var itemId = alberoprocStore.getValue(item, "id");
											        		if(itemId>0){
																cercaProcedimentoAjax(itemId);
																$('alberoproc_hidden').focus();
											        		}
										            	}
										            },
										            getIconClass: function(item,opened){							            	
										            	treeInitialized = true;
										            	if(item.id!='0'){
											        		var dis = 'false';
											        		if(item){
											        			dis = alberoprocStore.getValue(item, "disabilitato");
											        		}
											        		if(dis == 'false'){
											        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf";
											        		}else{
											        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled";
											        		}
										            	}else{
										            		return "dijitFolderOpened"
										            	}	
										            }
										        },
										        "treeOne");
									        }else{
									        	document.getElementById('treeOne').style.display="";
									        }
									    }
										
										var forzaNumeroPratica = true; 
										
										var endoSplashDiv=null;
										
										jQuery(document).ready(function(){
											endoSplashDiv = new dijit.Dialog({
									            title: "<fmt:message key='label.endoprocedimenti' />" ,
									            style: "width: 500px"
									        });
										});
										
										function mostraDivEndo(codiceAlberoproc){
											
											new Ajax.Request('../pecinbox/ajaxListaEndo.htm', {
												  method: 'post',
												  parameters: {codiceAlberoProc: codiceAlberoproc},
												  onSuccess: function(transport){ 
													  endoSplashDiv.attr("content", transport.responseText);
													  endoSplashDiv.show();
												  },
												  onFailure: function(transport){ 
													var response = transport.responseText;
													console.error("Errore nella ricerca degli endo procedimenti:" + response);										    
												  }						    		 
											} );			
											
										}
										
										var progressivoIstanza='';
										
										function chiudiEndoDiv(){
											endoSplashDiv.hide();
										}
										
										function assegnaValori2(map){
											$('alberoproc_hidden').value = map.id;
											$('alberoproc_descrestesa_hidden').value = map.desc;
											// GESTIRE GLI ENDOPROCEDIMENTI ?
											if(map.endo_presenti == "true"){
												mostraDivEndo(map.id);
											}else{
												resetEndoInSession();
											}											
										}
										
										
										function resetEndoInSession(){
											jQuery.ajax({
												url: '${pageContext.request.contextPath}/pecinbox/ajaxResetEndo.htm', 
												dataType: 'text',													
												cache: false													
											});
										}
										
										function rimuoviValori2(){
											$('alberoproc_hidden').value = '';
											$('alberoproc_descrestesa_hidden').value = '';											
										}
										
										function endoInSession(chkobjid, codiceinventario) {			
											var _ts = new Date().getTime();
											var url = '${pageContext.request.contextPath}/pecinbox/ajaxEndoInSession.htm?_ts='+_ts;
													url+='&codiceinventario='+codiceinventario+'&selezionato='+$(chkobjid).checked;																			
											changeCheckboxValue(chkobjid,url);									
										}
									
									</script>
								</td>						
							</tr>
							<%-- SEZIONE LOCALIZZAZIONE START --%>
							<tr class="titoloSezione">
								<td colspan="6"><fmt:message key="label.dati_localizzazione"/></td>
							</tr>
							
							
							<tr  id="id_localizzazione_table">
							<%--
							<td>
								<label for="codcivico_id" style="display: none;"><fmt:message key="label.codice_civico" /></label>
								<spring-form:hidden id="codcivico_id" path="istanzestradario.entity.codicecivico" />
					        </td>
					         --%>
                            </tr>
                            <tr id="id_localizzazione_table">
						    <c:if test="${not empty tipiLocalizzazionis}"> <td><label for="tipo_localizzazione_id"><fmt:message key="label.tipo_localizzazione" /></label></td>
								<td colspan="4"  >
									<spring-form:select id="tipo_localizzazione_id" path="istanzestradario.entity.tipiLocalizzazioni.id.codice">
										<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
										<spring-form:options items="${tipiLocalizzazionis}" itemValue="id.codice" itemLabel="descrizione"/>
									</spring-form:select> 
								</td>
							</c:if>	
						 	</tr>
							
							<tr id="id_localizzazione_table">
							<td><label for="stradario_id"><fmt:message key="label.indirizzo" /></label></td>
								<td>
								<jsp:include page="../includes/searchstradario.jsp" >
									<jsp:param name="idElemento" value="stradario_id" />						
									<jsp:param name="pathStradario" value="istanzestradario.entity.stradario" />
									<jsp:param name="stradarioHideFunctions" value="${isAddStradario}" />
									<jsp:param name="stradarioAutocompleterAjax" value="findStradario.htm?searchDisabilitati=false" />
									 <jsp:param name="ajaxCallBack" value="filterCodiceComune" />					
								</jsp:include>
									<%-- <jsp:param name="ajaxCallBack" value="filterCodiceComune" />
									<jsp:param name="afterUpdateElement" value="ricercaStradarioAfterUpdate" />	
									--%>	
									
								</td>
		
							</tr>
							<tr id="id_localizzazione_table">
								<td width="20%"><label for="civico_id"><fmt:message key="label.civico" /></label></td>
								<td><spring-form:input path="istanzestradario.entity.civico" size="10"/></td>
								<td><label for="esponente_id"><fmt:message key="label.esponente" /></label></td>
								<td><spring-form:input path="istanzestradario.entity.esponente" size="10"/></td>					
								 <c:if test="${isStradariocoloreVisible eq true }">
								<td><label for="colore_id"><fmt:message key="label.colore" /></label></td>
								<td>
									<spring-form:select id="colore_id" path="istanzestradario.entity.stradariocolore.id.codicecolore" onchange="${fnColoreSit}">
										<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
									    <spring-form:options items="${stradariocoloreList }" itemValue="id.codicecolore" itemLabel="colore"/>
									</spring-form:select>
								</td>
								</c:if>
							</tr>
							<tr>
								<td><label for="scala_id"><fmt:message key="label.scala" /></label></td>
							    <td><spring-form:input path="istanzestradario.entity.scala" size="10"/></td>
							    <td><fmt:message key="label.piano" /></td>
							    <td><spring-form:input path="istanzestradario.entity.piano" size="10"/></td>
							     <td><label for="interno_id"><fmt:message key="label.interno" /></label></td>
							    <td><spring-form:input path="istanzestradario.entity.interno" size="10"/></td>
							    
							</tr>
							<tr>
							   
							    <td><label for="esponenteinterno_id"><fmt:message key="label.esponente_interno" /></label></td>
							    <td colspan="2"><spring-form:input path="istanzestradario.entity.esponenteinterno" size="10" /></td>
								
							</tr>
							<tr>
							    <td><label for="fabbricato_id"><fmt:message key="label.fabbricato" /></label></td>
								<td><spring-form:input path="istanzestradario.entity.fabbricato" size="10" /></td>
								<td><label for="km_id"><fmt:message key="label.km" /></label></td>
								<td colspan="2"><spring-form:input path="istanzestradario.entity.km" size="10"/></td>	
							</tr>
							<tr>
								<td><label for="tipocatasto_id"><fmt:message key="label.catasto" /></label></td>
								<td colspan="4">
								<spring-form:select id="tipocatasto_id" path="istanzestradario.istanzemappali.catasto.codice">
									<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
								    <spring-form:options items="${catastoList}" itemValue="codice" itemLabel="descrizione"/>
								</spring-form:select>
								</td>
							</tr>
							<tr>
								<td><label for="foglio_id"><fmt:message key="label.foglio" /></label></td>
							    <td><spring-form:input path="istanzestradario.istanzemappali.foglio" size="10"/></td>
							    <td><label for="particella_id"><fmt:message key="label.particella" /></label></td>
							    <td colspan="2"><spring-form:input path="istanzestradario.istanzemappali.particella" size="10"/></td>
							</tr>
							<tr>
							    <td><label for="sub_id"><fmt:message key="label.sub" /></label></td>
							    <td><spring-form:input path="istanzestradario.istanzemappali.sub" size="10" /></td>
								<td><label for="unitaimmob_id"><fmt:message key="label.unita_immobiliare" /></label></td>
							    <td colspan="2"><spring-form:input path="istanzestradario.istanzemappali.unitaimmob" size="10"/></td>
							</tr>
							<tr>
							    <%-- 
								<td><fmt:message key="label.note" /></td>
								<td><spring-form:input path="istanzestradario.entity.note" size="70"/></td>
								--%>
								<td><label for="cap_id"><fmt:message key="label.cap" /></label></td>
								<td colspan="2"><spring-form:input path="istanzestradario.entity.cap" size="8"/></td>
							</tr>
							<tr>
								<td><label for="frazione_id"><fmt:message key="label.frazione" /></label></td>
								<td><spring-form:input path="istanzestradario.entity.frazione" size="25"/></td>
								<td><label for="quartiere_id"><fmt:message key="label.quartiere" /></label></td>
								<td colspan="2"><spring-form:input path="istanzestradario.entity.quartiere" size="25"/></td>
							</tr>
							<tr>
								<td><label for="circoscrizione_id"><fmt:message key="label.circoscrizione" /></label></td>
								<td><spring-form:input path="istanzestradario.entity.circoscrizione" size="25"/></td>	
							</tr>
							<tr>
								<td><label for="longitudine_id"><fmt:message key="label.longitudine" /></label></td>
								<td><spring-form:input path="istanzestradario.entity.longitudine" size="15"/></td>
								<td><fmt:message key="label.latitudine" /></td>
								<td colspan="2"><spring-form:input id="longitudine_id" path="istanzestradario.entity.latitudine" size="15" /></td>
							</tr>
							
					        <%-- SEZIONE LOCALIZZAZIONE END --%>
							<tr class="titoloSezione">
								<td colspan="6"><fmt:message key="label.altri_dati"/></td>
							</tr>
							<tr>
								<td style="vertical-align: top;">
									<fmt:message key="label.lavori" />
								</td>
								<td colspan="5">
									<spring-form:textarea readonly="${istanzaPecCommand.readOnly}" id="lavori_id" path="istanza.lavori" 
									cols="105" rows="4" cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
							<tr>
								<td  style="vertical-align: top;">
									<fmt:message key="label.lavoriestesa" />
								</td>
								<td colspan="5">
									<spring-form:textarea readonly="${istanzaPecCommand.readOnly}" id="lavoriestesa_id" path="istanza.lavoriestesa" 
									cols="105" rows="4" cssStyle="font-weight: bolder;"/>
								</td>
							</tr>
							<c:choose>
							<c:when test="${not empty istanzaPecCommand.pec.dataprotocollo and not empty istanzaPecCommand.pec.numeroprotocollo}">
								<tr>
									<td>
										<fmt:message key="label.numero_protocollo" />
									</td>
									<td colspan="2">
										<spring-form:input readonly="true" id="numeroprotocollo_id" path="numeroProtocollo" size="10" cssStyle="font-weight: bolder;"/>
									</td>
									<td>
										<fmt:message key="label.data_protocollo" />
									</td>
									<td colspan="3">
										<spring-form:input readonly="true" id="dataprotocollo_id" path="dataProtocollo" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>
										<%-- 
										<init:calendar imagePath="/images/cal.gif" idImage="calDataprotocollo" idInput="dataprotocollo_id" textKey="label.calendar" />
										--%> 			  	
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td>
										<fmt:message key="label.numero_protocollo" />
									</td>
									<td colspan="2">
										<spring-form:input id="numeroprotocollo_id" path="numeroProtocollo" size="10" cssStyle="font-weight: bolder;"/>
									</td>
									<td>
										<fmt:message key="label.data_protocollo" />
									</td>
									<td colspan="3">
										<spring-form:input id="dataprotocollo_id" path="dataProtocollo" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>										
										<init:calendar imagePath="/images/cal.gif" idImage="calDataprotocollo" idInput="dataprotocollo_id" textKey="label.calendar" />										 			  	
									</td>
								</tr>
							</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${not empty error}">
						<tr>
							<td colspan="6">
								<span class="error">${error}</span>		
							</td>
						</tr>
						</c:if>
					</c:if>
					<c:if test="${empty istanzaPecCommand.pec and not empty error}">
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
				<c:if test="${empty error and not istanzaPecCommand.readOnly}">
					<li><a href="javascript:void();" id="btn_salva"><fmt:message key="label.salva" /></a></li>
				</c:if>
				<li><a href="javascript:void();" id="btn_indietro"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
			 		
	</div>

	<script type="text/javascript">
	
	function filterCodiceComune(element, entry) {
		
		if(document.getElementById("istanza_comune")){
				return entry + "&codiceComune=" + document.getElementById("istanza_comune").value; // Filtra nel caso di inserimento istanza
			}
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
		  jQuery('#btn_indietro').click(indietro);
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
		  doSubmit('${pageContext.request.contextPath}/pecinbox/creaIstanza.htm','', document.istanzaDaPecForm);
		  disableFunctions();
	  }
	  
	  function indietro(){
		  doHref("${pageContext.request.contextPath}/pecinbox/indietro.htm?idPec=${istanzaPecCommand.pec.id.id}", "");
	  }
	  
	  function calcolaCodicePraticaTelematica(){
		  
			jQuery.ajax({
				url: '${pageContext.request.contextPath}/pecinbox/ajaxCalcolaCodicePraticaTel.htm?', 
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