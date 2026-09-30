<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.AuthLevel"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Ateco" %>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>	
			<c:if test="${alberoproc.id.codice==null}">
				<fmt:message key="alberoproc.label.nuovo_alberoproc.title" />
			</c:if> 
			<c:if test="${alberoproc.id.codice!=null}">
				<fmt:message key="alberoproc.label.dettaglio_alberoproc.title" />
			</c:if>
		</title>
        <style>
            .presenza-enti-esclusi {
                border-style: dashed;
                border-width: 1px;
                border-color: black;
                text-align: center;
                padding-top: 12px;
                padding-bottom: 12px;
            }
            .presenza-enti-esclusi > div:first-child {
                font-weight: bold;
                font-size: large;
                color: #B61218;
            }
            .presenza-enti-esclusi > div:last-child {
                padding-top: 5px;
                color: #333333;
            }
            .tooltip-text {
                position: absolute;
                top: -40px;
                left: -50%; */
                z-index: 2;
                width: 100px;
                color: white;
                font-size: 12px;
                background-color: #192733;
                border-radius: 10px;
                padding: 10px 15px 10px 15px;
            }
            .hover-text:hover #fade { opacity: 1; }
            .hover-text:hover #delay { opacity: 1; }
            .hover-text {
                position: relative;
                display: inline-block;
                margin: 40px;
                font-family: Arial;
                text-align: center;
            }
        </style>
		<script type="text/javascript">
		vbg.ready(() => {
			let selectPubblica = document.querySelector('#scPubblica');
			let fieldSetAlberoProcTempi = document.querySelector('fieldset[name="alberoproctempi_testata"]');
			
			selectPubblica.addEventListener('change',async function(e){
				e.preventDefault();
				mostraNascondiAlberoProcTempi();
			});
			
			mostraNascondiAlberoProcTempi();
			
			function mostraNascondiAlberoProcTempi(){
				switch(selectPubblica.value) {
					case '1':{
						fieldSetAlberoProcTempi.style.display = '';
						break;
					}
					case '4':{
						fieldSetAlberoProcTempi.style.display = '';
						break;
					}
					default: {
						fieldSetAlberoProcTempi.style.display = 'none';
						break;
					}
				}
			}
		})
		</script>
		
	</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${alberoproc.id.codice==null}">
				<fmt:message key="alberoproc.label.nuovo_alberoproc.title" />
			</c:if> 
			<c:if test="${alberoproc.id.codice!=null}">
				<fmt:message key="alberoproc.label.dettaglio_alberoproc.title" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../alberoproc/view" />
		</jsp:include>
		<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore"
									url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"/>
		<%
		String  mercatiUso="display:none;";
		%>
		<%
		String urlBack = request.getScheme() + "://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/alberoproc/view.htm?codice="+request.getParameter("codice");
		urlBack = URLEncoder.encode(urlBack,"UTF-8");
		%>					
		<div id="subcontent">
			<div class="parametriDiv">
	       		<div class="parametro"> 
		        	<div>
		        		${alberoproc.vwAlberoproc.scDescrizionepadre}
		        	</div>
		        </div>
		    </div>
		    <div class="clear"></div>
		    
		    <table id="container_box">
		    <tr>
		    <td id="albero_box" style="vertical-align: top;" >    
		    	<a class="vbg-btn btn-dettaglio" id="alberoimg_id" title="<fmt:message key="label.visualizza_albero_procedimenti" />" href="javascript:showHideDiv('alberoContent')">
		    	</a>
				<span id="alberoContent" style="display: none;">
				<fieldset>
				<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>	
				<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>		
				<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />	
					<script type="dojo/method" event="onClick" args="item">
				 if(item.id != '0'){
					var itemId = alberoprocStore.getValue(item, "id");
					if(itemId>0){
						doHref('view.htm?codice='+itemId,'');
					}
				 }
   				</script>
				
				    <script type="dojo/method" event="getIconClass" args="item, opened">


		if(item.id != '0'){
			var dis = 'false';
			var scPubblica = '';
			if(item){
				dis = alberoprocStore.getValue(item, "disabilitato");
				scPubblica = alberoprocStore.getValue(item, "scPubblica");
			}
			var icona = "";
			if(dis == 'false'){
				if(!item || this.model.mayHaveChildren(item)){
					if(opened){
						icona = "dijitFolderOpened";
					}else{
						icona =	"dijitFolderClosed";
					}	
			}else{
				icona = "dijitLeaf";
			}

			if(scPubblica){
				if(scPubblica=='0'){
					icona = icona.replace('dijit','nonPubblicare');
				}else if(scPubblica=='1'){
					icona = icona.replace('dijit','areaRiservataFrontoffice');
				}else if(scPubblica=='2'){
					icona = icona.replace('dijit','areaRiservata');
				}else if(scPubblica=='3'){
					icona = icona.replace('dijit','frontoffice');
				}	
			}

			return icona;

			}else{
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
			}
		}else{
			return "dijitFolderOpened"
		}

   				</script>
				</fieldset>
				</span>			
		    </td>
		    <td id="form_box" style="width: 100%">
					<spring-form:form commandName="alberoproc" name="inviodati">
						<jsp:include page="../includes/displayGlobalMessages.jsp" >
					        <jsp:param name="commandName" value="alberoproc" />
					    </jsp:include>
						<table width="100%">
                            <c:if test="${entiEsclusiPresenti}">
                                <tr>
                                    <td class="presenza-enti-esclusi" colspan=4>
                                        <div><fmt:message key="alberoproc.label.presenza_enti_esclusi" /></div>
                                        <div>
                                            <a href="javascript:historySet('${_urlback}','..%2Falberoproc/comuniesclusi.htm?codiceprocedimento=${alberoproc.id.codice}','')">
                                                <fmt:message key="alberoproc.label.visualizza_enti_esclusi" />
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
						<c:if test="${alberoproc.id.codice!=null}">
							<tr>
								<td>
									<fmt:message key="label.codice" />
								</td>
								<td colspan="3">
								
									<b>${alberoproc.id.codice} - ${alberoproc.scCodice}</b>			
								
								</td>
							</tr>		
						</c:if>							
							<tr>
								<td>
									<fmt:message key="label.descrizione" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:input id="descrizione_id" path="scDescrizione" size="70" />
									<spring-form:errors path="scDescrizione" cssClass="error"/>
									<c:if test="${alberoproc.id.codice==null}">
										<span><spring-form:checkbox path="areaPrimaria" /><fmt:message key="alberoproc.label.areaprimaria" /></span>
									</c:if>
								</td>
							</tr>
							<c:if test="${collegamentoPM!=null}">
								<tr>
									<td>
										<fmt:message key="procedimarche.label.collegamento" />
									</td>
									<td colspan="3">
										<a href="javascript:historySet('../alberoproc/view.htm?codice=${alberoproc.id.codice}','../procedimarche/view.htm?idProc=${collegamentoPM.codiceStp}');">${collegamentoPM.codiceStp}</a>			
									</td>
								</tr>		
							</c:if>							
							<tr>
								<td>
									<fmt:message key="label.note" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:textarea id="scNote_id" path="scNote" rows="5" cols="60" />
									<spring-form:errors path="scNote" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="label.parole_chiave" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:textarea id="paroleChiave_id" path="paroleChiave" rows="5" cols="60" />
									<spring-form:errors path="paroleChiave" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="alberoproc.label.scAttivo" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:checkbox id="scAttivo_id" path="scAttivo" />
									<spring-form:errors path="scAttivo" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="alberoproc.label.livello_autenticazione" />
								</td>
								<td colspan="3" class="inline-ui-cell">
								    <spring-form:select id="ivelloAutenticazione_id" path="livelloAutenticazione">
								    <spring-form:option value="">Seleziona</spring-form:option>
								    <spring-form:option value="<%=AuthLevel.ANONIMO.getValore()%>"><%=AuthLevel.ANONIMO.getDescrizione() %></spring-form:option>
								    <spring-form:option value="<%=AuthLevel.UTENTE_NON_IDENTIFICATO.getValore()%>"><%=AuthLevel.UTENTE_NON_IDENTIFICATO.getDescrizione() %></spring-form:option>
								    <spring-form:option value="<%=AuthLevel.UTENTE_IDENTIFICATO.getValore()%>"><%=AuthLevel.UTENTE_IDENTIFICATO.getDescrizione() %></spring-form:option>
								    </spring-form:select >
									
									<spring-form:errors path="livelloAutenticazione" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td><fmt:message key="alberoproc.label.scPubblica" /></td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:select path="scPubblica" > 
										<spring-form:option value="" ><fmt:message key='alberoproc.label.eredita_dal_padre' /></spring-form:option>
										<spring-form:option value="0" ><fmt:message key='label.scPubblica_0' /></spring-form:option>
										<spring-form:option value="1"><fmt:message key='label.scPubblica_1' /></spring-form:option>
										<spring-form:option value="2" ><fmt:message key='label.scPubblica_2' /></spring-form:option>
										<spring-form:option value="3" ><fmt:message key='label.scPubblica_3' /></spring-form:option>
										<spring-form:option value="4" ><fmt:message key='label.scPubblica_4' /></spring-form:option>
									</spring-form:select>
									<spring-form:errors path="scPubblica" cssClass="error"/> 
								</td>
							</tr>
							<tr>
								<td><fmt:message key="alberoproc.label.escludiRisultatiDaRicercaPubblico" /></td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:checkbox id="escludiFlagEscludiRisultatiDaRicerca_id" path="flagEscludiRisultatiRicerca" />
									<spring-form:errors path="flagEscludiRisultatiRicerca" cssClass="error"/>
									<label for="escludiFlagEscludiRisultatiDaRicerca_id"><fmt:message key="alberoproc.label.escludiRisultatiDaRicercaPubblico.help" /></label>
									
								</td>
							</tr>
							<%-- DATI CONCENTRAMENTO NON PIU' GESTITI
							<tr>
								<td>
									<fmt:message key="alberoproc.label.controllamq" />
								</td>
								<td colspan="3">
									<spring-form:checkbox id="controllamq_id" path="controllamq" />
									<init:help idHelp="help_controllamq" textKey="alberoproc.help.controllamq"/>
									<spring-form:errors path="controllamq" cssClass="error"/>
								</td>
							</tr>
							 --%>
					<c:if test="${alberoproc.id.codice!=null}">
						<c:if test="${vert_cart_attivo}">
							<c:if test="${pubblicabile_manualmente_cart eq true}">
								<tr>
									<td>
										<fmt:message key="alberoproc.label.pubblica_cart" />
									</td>
									<td colspan="3" class="inline-ui-cell">
										<c:set var="checked_pubblica_cart"></c:set>
										<c:if test="${pubblicato_cart eq true}">
											<c:set var="checked_pubblica_cart"> checked="checked" </c:set>
										</c:if>
										<input type="checkbox" id="pubblica_cart_id" name="pubblica_cart" ${checked_pubblica_cart} onclick="pubblicaSuCART(this);"/>									
										<init:help idHelp="help_pubblica_cart" textKey="alberoproc.label.pubblica_cart.help"/>
										<script type="text/javascript">
											function pubblicaSuCART(obj){
												var ts = new Date().getTime();
												var pubblica = jQuery('#pubblica_cart_id').is(':checked');											
												doHref('pubblicaSuCart.htm?codiceAlberoproc=${alberoproc.id.codice}&pubblica='+pubblica+"&ts_="+ts,'<fmt:message key="alberoproc.label.pubblica_cart.javascript_confirm" />');
											}
										</script>
									</td>
								</tr>
							</c:if>
						</c:if>						 
					</c:if>						 
							 
							 
							 
							<tr>
								<td><fmt:message key="alberoproc.label.azione" /></td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:select path="azione.azId" >
										<option value="" />
										<spring-form:options items="${azioniList}" itemLabel="azDescrizione" itemValue="azId" />
									</spring-form:select>
									<spring-form:errors path="azione" cssClass="error"/> 
								</td>
							</tr>
							<tr>
								<td><fmt:message key="alberoproc.label.tipologiaregistro" /></td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:select path="tipologiaregistro.id.codice" >
										<spring-form:option value=""></spring-form:option>
										<spring-form:options items="${tipologiaregistriList}" itemLabel="trDescrizione" itemValue="id.codice"/>
									</spring-form:select>
									<init:help idHelp="help_tipologiaregistro" textKey="alberoproc.help.tipologiaregistro"/>
									<spring-form:errors path="tipologiaregistro" cssClass="error"/> 
								</td>
							</tr>
							<tr>
								<td><fmt:message key="alberoproc.label.tipoProcedura" /></td>
								<td colspan="3" class="inline-ui-cell">							
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="tipiprocedure_id" />				
										<jsp:param name="propertyPath" value="tipoProcedura" />			
										<jsp:param name="pathPropertyDescription" value="tipoProcedura.procedura" />
										<jsp:param name="pathPropertyCode" value="tipoProcedura.id.codice" />
										<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?soloConMovimentoAvvio=true" />
										<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
									</jsp:include>						
									<init:help idHelp="help_tipoProcedura" textKey="alberoproc.help.tipoProcedura"/>
									<spring-form:errors path="tipoProcedura" cssClass="error"/> 
								</td>
							</tr>
							<tr>
								<td><fmt:message key="alberoproc.label.amministrazioni" /></td>
								<td colspan="3" class="inline-ui-cell">
								
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="amministrazioni" />		
										<jsp:param name="propertyPath" value="amministrazioni" />				
										<jsp:param name="pathPropertyDescription" value="amministrazioni.descrizioneEstesa" />
										<jsp:param name="pathPropertyCode" value="amministrazioni.id.codice" />
										<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />			
										<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
									</jsp:include>	
															
									<spring-form:errors path="amministrazioni" cssClass="error"/> 
								</td>
							</tr>						
							<tr>
								<td>
									<fmt:message key="alberoproc.label.responsabileproc" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:input id="responsabili_id" path="responsabile.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabili_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
									<init:autocompleter  methodAjax="findResponsabiliProcedimento.htm" idHidden="responsabili_hidden" idInput="responsabili_id" inputTitleKey="label.ricerca_responsabile"/>
									<init:help idHelp="help_responsabileproc" textKey="alberoproc.help.responsabileproc"/>
									<spring-form:errors path="responsabile.responsabile" cssClass="error"/> 
									<spring-form:hidden id="responsabili_hidden" path="responsabile.id.codice"  />
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="alberoproc.label.respistruttoria" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:input id="responsabileistruttoria_id" path="respistruttoria.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabileistruttoria_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
									<init:autocompleter methodAjax="findResponsabiliIstruttoria.htm" idHidden="responsabileistruttoria_hidden" idInput="responsabileistruttoria_id" inputTitleKey="label.ricerca_responsabile"/>
									<init:help idHelp="help_respistruttoria" textKey="alberoproc.help.respistruttoria"/>
									<spring-form:errors path="respistruttoria.responsabile" cssClass="error"/> 
									<spring-form:hidden id="responsabileistruttoria_hidden" path="respistruttoria.id.codice"  />
								</td>
							</tr>
							
							<tr>
								<td>
									<fmt:message key="alberoproc.label.operatoreStc" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:input id="operatoreStc_id" path="operatoreStc.responsabile" cssClass="searchbox" onchange="checkValue(this,'operatoreStc_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
									<init:autocompleter methodAjax="findResponsabili.htm" idHidden="operatoreStc_hidden" idInput="operatoreStc_id" inputTitleKey="label.ricerca_responsabile"/>
									<init:help idHelp="help_operatoreStc" textKey="alberoproc.help.operatoreStc"/>
									<spring-form:errors path="operatoreStc.responsabile" cssClass="error"/> 
									<spring-form:hidden id="operatoreStc_hidden" path="operatoreStc.id.codice"  />
								</td>
							</tr>
							
							<c:if test="${conf_contatore == true}">
							<tr>
								<td>
									<fmt:message key="alberoproc.label.progressivoistanze" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:input id="progressivoistanze_id" path="progressivoistanze" size="15"  maxlength="35"/>
									<init:help idHelp="help_progressivoistanze" textKey="alberoproc.help.progressivoistanze"/>
									<spring-form:errors path="progressivoistanze" cssClass="error"/>
								</td>
							</tr>
							</c:if>
				    		<tr>
								<td>
									<fmt:message key="alberoproc.label.scOrdine" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:input id="scOrdine_id" path="scOrdine" size="5" />
									<spring-form:errors path="scOrdine" cssClass="error"/>
								</td>
							</tr>
							<c:if test="${vert_replicaistanze_attivo == true}">
							<tr>
								<td>
									<fmt:message key="alberoproc.label.flagReplicaistanze" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:checkbox id="flagReplicaistanze_id" path="flagReplicaistanze" />
									<init:help idHelp="help_flagReplicaistanze" textKey="alberoproc.help.flagReplicaistanze" />
									<spring-form:errors path="flagReplicaistanze" cssClass="error"/>
								</td>
							</tr>
							</c:if>
							<c:choose>
							<c:when test="${ARJ_ATTIVA eq true}">
							<tr>
								<td><fmt:message key="label.workflow_area_riservata" /></td>
								<td class="inline-ui-cell">
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="foarjstepstestata_id" />				
										<jsp:param name="propertyPath" value="foArjStepsTestata" />			
										<jsp:param name="pathPropertyDescription" value="foArjStepsTestata.descrizione" />
										<jsp:param name="pathPropertyCode" value="foArjStepsTestata.id.codice" />
										<jsp:param name="autocompleterAjax" value="findFoArjStepsTestata.htm" />
										<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
									</jsp:include>
								</td>
							</tr>						
							</c:when>
							<c:otherwise>
							<tr>
								<td><fmt:message key="label.workflow_area_riservata" /></td>
								<td class="inline-ui-cell">
									<jsp:include page="../includes/oggetti.jsp">
										<jsp:param name="idElemento" value="oggettoIdCodice" />
										<jsp:param name="codiceOggetto" value="${alberoproc.oggettoWorkflowAreaRis.id.codice}" />
										<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
										<jsp:param name="nomefileId" value="oggetto_nomefile" />
									</jsp:include> 
									<spring-form:hidden path="oggettoWorkflowAreaRis.id.codice"	id="oggetto_id_codice" />
									<spring-form:hidden path="oggettoWorkflowAreaRis.nomefile" id="oggetto_nomefile" />
								</td>
							</tr>
							</c:otherwise>
							</c:choose>
							<tr>
							    <td><fmt:message key="label.inizio_validita" /></td>
							    <td class="inline-ui-cell">
									<spring-form:input id="data_inizio_id" path="inizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
									<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="data_inizio_id" textKey="label.calendar"/>
							        <input type="text" name="oraInizioValidita" value="${alberoproc.oraInizioValidita}" size="6" placeholder="HH:MM" onblur="checkTime(this,true)" />
							        <init:help idHelp="help_data_inizio_id" textKey="alberoproc.help.data_inizio_validita"/>
							        <b>${dataInizioValiditaEreditata}</b> 
							        <c:if test="${codiceInterEriditaDate ne null}">
								        <a class="dettaglioColumn" style="float: none;" href="javascript:historySet('${_urlback}', '../alberoproc/view.htm?codice=${codiceInterEriditaDate}', '')" title="<fmt:message key="label.edit.record.image" /> ${endo_var.id.codiceinventario}">
								              <label><fmt:message key="label.edit.record.image" /></label>
								        </a>
								     </c:if>
							        <spring-form:errors path="inizioValidita" cssClass="error"/>
							   </td>
							</tr>
							<tr>
								<td><fmt:message key="label.fine_validita" /></td>
							    <td class="inline-ui-cell">
							   		<spring-form:input id="data_fine_id" path="fineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
									<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="data_fine_id" textKey="label.calendar"/>
									<input type="text" name="oraFineValidita" value="${alberoproc.oraFineValidita}" size="6" placeholder="HH:MM" onblur="checkTime(this,true)" />
								    <init:help idHelp="help_data_fine_id" textKey="alberoproc.help.data_fine_validita"/>
								    <b>${dataFineValiditaEreditata}</b>
								    <spring-form:errors path="fineValidita" cssClass="error"/>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="alberoproc.label.flagOggettoPraticaDefault" />
								</td>
								<td colspan="3" class="inline-ui-cell">
									<spring-form:select path="flagOggettoPraticaDefault" id="flagReplicaistanze_id" > 
										<spring-form:option value="0" ><fmt:message key='alberoproc.label.eredita_dal_padre' /></spring-form:option>
										<spring-form:option value="1" ><fmt:message key='label.no' /></spring-form:option>
										<spring-form:option value="2" ><fmt:message key='label.si' /></spring-form:option>
									</spring-form:select>									
									<init:help idHelp="help_flagOggettoPraticaDefault" textKey="alberoproc.help.flagOggettoPraticaDefault" />
									<spring-form:errors path="flagOggettoPraticaDefault" cssClass="error"/>
								</td>
							</tr>
							
							<tr>
								<td>
									<fmt:message key="alberoproc.label.flag_gestione_spunta" />
								</td>
								<td colspan="3" class="inline-ui-cell"> 
									<spring-form:checkbox id="flagGestioneSpuntisti_id" path="flagGestioneSpuntisti" />
									<init:help idHelp="help_flag_gestione_spunta_id" textKey="alberoproc.help.flag_gestione_spunta"/>
								</td>
							</tr>
							
							<c:if test="${mercati_attivi eq true}">
							<tr>
								<td class="titoloSezione" colspan="4">
									<fmt:message key="alberoproc.label.parametrimercati" />
								</td>
							</tr>
							<tr id="mercati">
								<td><fmt:message key="alberoproc.label.mercati" /></td>
								<td colspan="3" class="inline-ui-cell">
									<script type="text/javascript">
										function setHiddenFieldmercati(inputField,listItem){
											var a = listItem.id;
											document.getElementById('mercato_id').value = inputField.value;
											document.getElementById('mercato_hidden').value = a;
											$("mercatiUso").appear();
										}
									</script>
									
									<%--RICERCA MANIFESTAZIONI --%>
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="mercato" />		
										<jsp:param name="propertyPath" value="mercato" />				
										<jsp:param name="pathPropertyDescription" value="mercato.descrizione" />
										<jsp:param name="pathPropertyCode" value="mercato.id.codice" />
										<jsp:param name="autocompleterAjax" value="findMercati.htm" />
										<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati" />
										<jsp:param name="titleKey" value="label.ricerca_manifestazione" />
								  	</jsp:include>	
									
										
									<%-- 		--%>
									
								</td>
							</tr>
							<tr id="mercatiUso" style="<%=mercatiUso%>">
								<td><fmt:message key="alberoproc.label.mercatiUso" /></td>
								<td colspan="3" class="inline-ui-cell">	
									<script type="text/javascript">
											function filter(element, entry) {
												return entry + "&codiceMercato=" + document.getElementById("mercato_hidden").value;								
											}
									</script>
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="mercatoUso" />		
										<jsp:param name="propertyPath" value="mercatoUso" />				
										<jsp:param name="pathPropertyDescription" value="mercatoUso.descrizione" />
										<jsp:param name="pathPropertyCode" value="mercatoUso.id.codice" />
										<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />							
										<jsp:param name="titleKey" value="label.ricerca_mercati_uso" />
										<jsp:param name="ajaxCallBack" value="filter" />
									</jsp:include>		
								</td>
							</tr>
							</c:if>
							
							
							
							
							
							<c:if test="${alberoproc.scPadre eq false || alberoproc.flagescludisorteggio eq true}">
							<tr>
								<td class="titoloSezione" colspan="4">
									<fmt:message key="alberoproc.label.parametrisorteggi" />
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="alberoproc.label.flagescludisorteggio" />
								</td>
								<td colspan="3" class="inline-ui-cell"> 
									<c:if test="${alberoproc.scPadre eq true && alberoproc.flagescludisorteggio eq true}">
										<spring-form:checkbox id="flagescludisorteggio_id" path="flagescludisorteggio"  disabled="true"/>
									</c:if>
									<c:if test="${alberoproc.scPadre eq false }">
										<spring-form:checkbox id="flagescludisorteggio_id" path="flagescludisorteggio" />
									</c:if>
									<spring-form:errors path="flagescludisorteggio" cssClass="error"/>
								</td>
							</tr>
							</c:if>
							<%if(!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_CE)){ %>
								<tr>
									<td class="titoloSezione" colspan="4">
										<fmt:message key="alberoproc.label.intervallomqconsentiti" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key="alberoproc.label.scMinmq" /></td>
									<td class="inline-ui-cell" style="max-width: 250px;">
									<script type="text/javascript">
									function changeValue(obj){
										var importo=obj.value;
										if(isNaN(importo.replace(",","."))){
											alert('<fmt:message key="alert.field.numeric" />');
											obj.value = '';
											return;
										}
										obj.value = importo.replace(".",",");
									}
									</script>
									<spring-form:input cssStyle="text-align: right;" path="scMinmq" size="15" maxlength="12"  onchange="changeValue(this);"/></td>
									<td class="inline-ui-cell"><fmt:message key="alberoproc.label.scMaxmq" /></td>
									<td class="inline-ui-cell">
										<spring-form:input cssStyle="text-align: right;" path="scMaxmq" size="15" maxlength="12"  onchange="changeValue(this);"/>
										<label><fmt:message key="alberoproc.label.nolimite" /></label>
									</td>
								</tr>
								
							<%} %>
							
								<tr>
									<td class="titoloSezione" colspan="4">
										<fmt:message key="label.dati_anagrafe_tributaria" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key="alberoproc.label.atribTipologiaintervento" /></td>
									<td colspan="3" class="inline-ui-cell">								
										 <spring-form:input path="atribTipologiaintervento" size="5" />
										 <init:help idHelp="atribTipologiaintervento_help" textKey="alberoproc.label.atribTipologiaintervento.help"/>
										 <spring-form:errors path="atribTipologiaintervento" cssClass="error"/> 
									</td>
								</tr>
								<tr class="titoloSezione">
									<td colspan="4"><fmt:message
											key="label.dati_registro_imprese.legend" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key="label.dati_registro_imprese.tipo_intervento" />
									</td>
									<td class="inline-ui-cell" colspan="3">
										<jsp:include page="../includes/autocompletergenerico.jsp">
											<jsp:param name="idElemento" value="riTipiintervento_id" />
											<jsp:param name="propertyPath" value="riTipiintervento" />
											<jsp:param name="pathPropertyDescription"
												value="riTipiintervento.descrizioneEstesa" />
											<jsp:param name="pathPropertyCode" value="riTipiintervento.codice" />
											<jsp:param name="autocompleterAjax" value="findRiTipiintervento.htm" />
											<jsp:param  name="autocompleterInputSize" value="90"/>
											<jsp:param name="titleKey"
												value="label.ricerca_dati_registro_imprese.tipo_intervento" />
										</jsp:include>
									</td>
								</tr>			
							<%-- GESTIONE NORME ANTICORRUZIONE --%>
							<tr class="titoloSezione">
								<td colspan="4"><fmt:message
										key="label.dati_gestione_norme_anticorruzione.legend" />
										<init:help idHelp="help_anti_corruzione_id" textKey="label.anticorruzione.help" />
								</td>
						    </tr>
						    
						    <tr>
								<td><fmt:message key="label.gruppo_istruttori" /></td>
								<td colspan="3" class="inline-ui-cell">							
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="gruppiIstruttori_id" />				
										<jsp:param name="propertyPath" value="gruppiIstruttori" />			
										<jsp:param name="pathPropertyDescription" value="gruppiIstruttori.descrizione" />
										<jsp:param name="pathPropertyCode" value="gruppiIstruttori.id.codice" />
										<jsp:param name="autocompleterAjax" value="findGruppiIstruttori.htm" />
										<jsp:param name="titleKey" value="label.ricerca_gruppiistruttori" />
									</jsp:include>						
									<spring-form:errors path="tipoProcedura" cssClass="error"/> 
								</td>
							</tr>
						    
							<tr>
								<td>
									<fmt:message key="label.flag_assegnazione_automatica" />
								</td>
								<td colspan="3" class="inline-ui-cell"> 
									<spring-form:checkbox id="grpFlagAssegnazioneAut_id" path="grpFlagAssegnazioneAut" />
									<spring-form:errors path="grpFlagAssegnazioneAut" cssClass="error"/>
									<fmt:message key="label.flag_assegnazione_automatica.help" />
								</td>
								
							</tr>
							<tr>
								<td>
									<fmt:message key="label.flag_accettazione_richiesta" />
								</td>
								<td colspan="3" class="inline-ui-cell"> 
									<spring-form:checkbox id="grpFlagAccettazione_id" path="grpFlagAccettazione" />
									<spring-form:errors path="grpFlagAccettazione" cssClass="error"/>
									<fmt:message key="label.flag_accettazione_istrutttore.help" />
								</td>
								
							</tr>
							<c:if test="${vert_osservatorio_attivo eq true }">
								<c:if test="${vert_osservatorio_attivo eq true }">
									<tr class="titoloSezione">
										<td colspan="4"><fmt:message
												key="label.sezione_osservatorio_regionale" />
										</td>
								    </tr>
									<tr>
										<td>
											<fmt:message key="label.flag_progressivo_osservatorio_regionale" />
										</td>
										<td colspan="3" class="inline-ui-cell"> 
											<spring-form:checkbox id="flagProgAttOsserv_id" path="flagProgAttOsserv" />
											<spring-form:errors path="flagProgAttOsserv" cssClass="error"/>
											<fmt:message key="label.flag_progressivo_osservatorio_regionale.help" />
										</td>
										
									</tr>							    					    
						    	</c:if>
							</c:if>
							<c:if test="${vert_livorno_servizi_cittadini_attivo}">		 
							 <tr class="titoloSezione">
								<td colspan="4">Riferimenti Portale Servizi DRUPAL</td>
						    </tr>
							<tr>
								<td>
									Identificativo Drupal
								</td>
								<td colspan="3" > 							
									<spring-form:input path="drupalNid" size="15" />
									<spring-form:errors path="drupalNid" cssClass="error"/>								
								</td>
								
							</tr>
							 
							</c:if>	
							
							<c:if test="${AREARISERVATA_REDIRECT}">		 
								 <tr class="titoloSezione">
									<td colspan="4">Parametri area riservata</td>
							    </tr>
								<tr>
									<td>
										<fmt:message key="label.flag_arreariservata_redirect" />
									</td>
									<td colspan="3" class="inline-ui-cell"> 
										<spring-form:checkbox id="flagArRedirect_id" path="flagArRedirect" />
										<spring-form:errors path="flagArRedirect" cssClass="error"/>
										<fmt:message key="label.flag_arreariservata_redirect.help" />
									</td>
								</tr>
							</c:if>							
								<tr>
									<td>
										<fmt:message key="label.flag_unica_domanda" />
									</td>
									<td colspan="3" class="inline-ui-cell"> 
										<spring-form:checkbox id="flagUnicaDomanda_id" path="flagUnicaDomanda" />
										<spring-form:errors path="flagUnicaDomanda" cssClass="error"/>
										<fmt:message key="label.flag_unica_domanda.help" />
									</td>
								</tr>
							
							
							<c:if test="${attivoAccessoAtti}">
									<tr>
										<td>
											<fmt:message key="label.flag_accesso_atti" />
										</td>
										<td colspan="3" class="inline-ui-cell">
											<spring-form:checkbox id="flagAccessoAtti_id" path="flagAccessoAtti" />
											<spring-form:errors path="flagAccessoAtti" cssClass="error"/>
											<fmt:message key="label.flag_accesso_atti.help" />
										</td>
									</tr>
							</c:if>
								
						
							
								
							<c:if test="${isLdpContesti eq true}">
							
								<tr>
									<td class="titoloSezione" colspan="4">
									 	Parametri occupazione suolo pubblico livorno
									</td>
								</tr>
								<tr>
									<td>
										Occupazione
									</td>
									<td colspan="3" > 							
										<spring-form:select path="ldpOccupazionis.id.codice" >
											<spring-form:option value="" />
											<spring-form:options items="${ldpOccupazionis}" itemLabel="descrizione" itemValue="id.codice" />
										</spring-form:select>
										<spring-form:errors path="ldpOccupazionis" cssClass="error"/>								
									</td>
									
								</tr>
								<tr>
									<td>
										Periodo
									</td>
									<td colspan="3" > 							
										<spring-form:select path="ldpPeriodis.id.codice" >
											<spring-form:option value="" />
											<spring-form:options items="${ldpPeriodis}" itemLabel="descrizione" itemValue="id.codice" />
										</spring-form:select>
										<spring-form:errors path="ldpPeriodis" cssClass="error"/>								
									</td>
								</tr>						
								
								<tr>
									<td>
										Geometria
									</td>
									<td colspan="3" > 							
										<spring-form:select path="ldpGeometries.id.codice" >
											<spring-form:option value="" />
											<spring-form:options items="${ldpGeometries}" itemLabel="descrizione" itemValue="id.codice" />
										</spring-form:select>
										<spring-form:errors path="ldpGeometries" cssClass="error"/>								
									</td>
									
								</tr>
	
								<tr>
									<td>
										Stato iniziale domanda frontoffice
									</td>
									<td colspan="3" > 		
										<spring-form:input path="ldpDolQstring" size="40" />
										&nbsp; serve per assegnare uno stato alla pratica in presentazione nel front (Esempio manomissioni stradali che imposta uno stato presentato)	
										<spring-form:errors path="ldpDolQstring" cssClass="error"/>								
									</td>
									
								</tr>						
							</c:if>	
							
							
							<c:if test="${alberoproc.id.codice!=null}">
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('listOneri.htm?alberoproc.id.codice=${alberoproc.id.codice}','') " title="<fmt:message key="alberoproc.button.gestioneoneri" />"><fmt:message key="alberoproc.button.gestioneoneri" /></a></li>
										</ul>
									</div>
								</td>
							</tr>
							
							
							<!-- Tabelle che riportano gli endo procedimenti e gli endo procedimenti ereditati collegati alla voce dell'albero -->
							<tr>
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.endoprocedimenti_ereditati" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="40%"><fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" /> </td>
											<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_azione" /></td>
											<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" /></td>
							                <td width="5%" align="center" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPrincipale" /></td>
							                <td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" /></td>
							                <td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagUsaBO" /><init:help idHelp="help_flag_usa_BO_er" textKey="alberoproc.help.flag_usa_BO"/></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int l=1;%>
									<c:forEach items="${alberoprocEndoEreditate}" var="endoer_var">
									<tr class="<%=(l%2)==0?"odd":"even"%>">
									      <td >						
												${endoer_var.inventarioprocedimento.procedimento}
										  </td>
										  <td align="center">
										  		${endoer_var.azione.azAzione}
			    		                  </td>
			    		                   <td >
			    		                   	<c:if test="${endoer_var.flagRichiesto eq false || endoer_var.flagRichiesto == null}"><fmt:message key="label.no" /></c:if>
											<c:if test="${endoer_var.flagRichiesto eq true}"><fmt:message key="label.si" /></c:if>
						              	  </td>
						               	  <td >
					               	  	 	<c:if test="${endoer_var.flagPrincipale eq false || empty endoer_var.flagPrincipale}"><fmt:message key="label.no" /></c:if>
											<c:if test="${endoer_var.flagPrincipale eq true}"><fmt:message key="label.si" /></c:if>
						              	  </td>
						              	  <td >
					               	  	 	<c:if test="${endoer_var.flagPubblica eq false || empty endoer_var.flagPubblica}"><fmt:message key="label.no" /></c:if>
											<c:if test="${endoer_var.flagPubblica eq true}"><fmt:message key="label.si" /></c:if>
						              	  </td>
						              	   <td >
					               	  	 	<c:if test="${endoer_var.flagRichiestoBo eq false || empty endoer_var.flagRichiestoBo}"><fmt:message key="label.no" /></c:if>
											<c:if test="${endoer_var.flagRichiestoBo eq true}"><fmt:message key="label.si" /></c:if>
						              	  </td>
						             </tr>
									<%l++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr id="endo_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.endoprocedimenti" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="35%"><fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" /> </td>
											<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_azione" /></td>
							                <td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" /></td>
											<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPrincipale" /></td>
											<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" /></td>
											<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagUsaBO" /><init:help idHelp="help_flag_usa_BO" textKey="alberoproc.help.flag_usa_BO"/></td>
							                <td width="6%"><fmt:message key="label.azioni" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int p=1;%>
									<c:forEach items="${alberoprocEndosList}" var="endo_var" varStatus="a">
									<tr class="<%=(p%2)==0?"odd":"even"%>">
									      <td >						
												${endo_var.inventarioprocedimento.procedimento}
										  </td>
										  <td>
												<select id="selectsEndo_id${a.index}" onchange="changeSelectboxAzione('selectsEndo_id${a.index}','${pageContext.request.contextPath}/alberoproc/ajaxChangeSelectBoxAzioneEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&codiceAzione=')">
												     <c:if test="${endo_var.azione.azId != null}">
													     <option id="selectAzioneId${a.index}" value=""  selected="selected">Seleziona</option>
													</c:if>
													<c:if test="${endo_var.azione.azId == null}">
													     <option id="selectAzioneId${a.index}" value=""  selected="selected">Seleziona</option>
													</c:if>
												    <c:forEach items="${azionis}" var="azioni_var" varStatus="azioni_indice">
													         <c:if test="${azioni_var.azId == endo_var.azione.azId}">
													        	 <option id="selectAzioneId${a.index}" value="${azioni_var.azId}"  selected="selected">${azioni_var.descrizioneTransient}</option>
													         </c:if>
													         <c:if test="${azioni_var.azId != endo_var.azione.azId}">
													         	<option id="selectAzioneId${a.index}" value="${azioni_var.azId}">${azioni_var.descrizioneTransient}</option>
													         </c:if>
												    </c:forEach>       
												</select>
			    		                  </td>
			    		                  <td>
			    		                    <input id="flagRichiestoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagRichiestoId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_proposto',true);" ${endo_var.flagRichiesto?'checked':''} />
			    		                  </td>
						               	  <td>
						              	    <input id="flagPrincipaleId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagPrincipaleId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_principale',true)" ${endo_var.flagPrincipale?'checked':''} />
						              	  </td>
						              	  <td >
						              	    <input id="flagPubblicatoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagPubblicatoId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_pubblica',false)" ${endo_var.flagPubblica?'checked':''} />
						              	  </td>
						              	  <td>
						              	  	<input id="flagRichiestoBoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagRichiestoBoId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_richiesto_bo',false)" ${endo_var.flagRichiestoBo?'checked':''} />
						              	  </td>
						                  <td>
						                  		<a class="dettaglioColumn" style="float: none;" href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${endo_var.id.codiceinventario}', '')" title="<fmt:message key="label.edit.record.image" /> ${endo_var.id.codiceinventario}">
							               			 <label><fmt:message key="label.edit.record.image" /></label>
							          			</a>
							          			<a class="blockColumn" style="float: none;" href="javascript:historySet('${_urlback}','../alberoproc/listincompatibili.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${alberoproc.vwAlberoproc.id.codice}','')" title="<fmt:message key="alberoproc.label.endo_incompatibili" />">
							               			 <label><fmt:message key="label.edit.record.image" /></label>
							          			</a>
							                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}&codiceinventario=${endo_var.id.codiceinventario}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${endo_varr.id.codiceinventario}">
							               			 <label><fmt:message key="label.elimina.image" /></label>
							          			</a>
						                  </td>
									</tr>
									 	<script type='text/javascript'>
										 	jQuery(document).ready(function(){
										 		setReadOnly${a.index}('#flagPrincipaleId${a.index}','#flagRichiestoId${a.index}'); 
										 	});
										 	
										 	
										 	function changeCheckboxValueEndo${a.index}(id,url,isPrincipaleOrProposto){
												
												var lid = id;
												new Ajax.Request(url, {
														method: 'post',	
														onSuccess: function(transport){
															dijit.showTooltip(transport.responseText, dojo.byId(id));
															setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);
															if(isPrincipaleOrProposto)
															{
																jQuery('#flagRichiestoBoId${a.index}').attr('checked','checked');
														 	    setReadOnly${a.index}('#flagPrincipaleId${a.index}','#flagRichiestoId${a.index}');
															}
														},
														onFailure: function(transport){
														  console.error(transport);
														  alert(transport.responseText);
														  if($(id).checked){
															  $(id).checked = false;
														  }else{
															  $(id).checked = true;
														  }
														}
												});
												
											}	
										 	
									 	   function setReadOnly${a.index}(principaleId,richiestoId)
									 	   {
									 		  var principale=jQuery(principaleId).is(':checked');
									 		  var richiesto=jQuery(richiestoId).is(':checked');
									 		 
									 		  if(richiesto || principale)
									 		  {
									 			jQuery("#flagRichiestoBoId${a.index}").prop("disabled", true);
									 		  }
									 		  if(!richiesto && !principale)
									 		  {
									 			 jQuery("#flagRichiestoBoId${a.index}").prop("disabled", false);
									 		  }
									 		 
									 	   }
									 	
									 	</script>
									
									<%p++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}#endo_anchor','') " title="<fmt:message key="alberoproc.button.nuovaendo" />"><fmt:message key="alberoproc.button.nuovaendo" /></a></li>
										</ul>
									</div>
								</td>
							</tr>
							<!-- Tabelle che riportano le leggi e le leggi ereditate collegate alla voce dell'albero -->
							<tr>
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.normativa_ereditate" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="40%"><fmt:message key="alberoproc.label.normativa_descrizione" /> </td>
											<td width="40%" ><fmt:message key="alberoproc.label.normativa_tipologia" /></td>
							                <td width="5%" align="center" ><fmt:message key="alberoproc.label.normativa_scarica" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int z=1;%>
									<c:forEach items="${alberoprocLeggiEreditate}" var="leggier_var">
									<tr class="<%=(z%2)==0?"odd":"even"%>">
									      <td >						
												${leggier_var.legge.leDescrizione}
										  </td>
										  <td>
										  		${leggier_var.legge.leggitipi.ltDescrizione}
			    		                  </td>
						               	  <td  align="center">
						               	  	  <c:if test="${leggier_var.legge.oggetto.id.codice!=null}">
						               	  	  	<jsp:include page="../includes/visualizzaOggetto.jsp" >
						       						<jsp:param name="idElemento" value="legge${leggier_var.legge.oggetto.id.codice}" />
						       						<jsp:param name="fileId" value="${leggier_var.legge.oggetto.id.codice}" />
						   						</jsp:include>										  
											  </c:if>
						              	  </td>
						             </tr>
									<%z++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr id="legge_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.normativa" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="40%"><fmt:message key="alberoproc.label.normativa_descrizione" /> </td>
											<td width="40%" ><fmt:message key="alberoproc.label.normativa_tipologia" /></td>
							                <td width="5%" align="center" ><fmt:message key="alberoproc.label.normativa_scarica" /></td>
							                <td width="5%" align="center"><fmt:message key="alberoproc.label.normativa_elimina" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int i=1;%>
									<c:forEach items="${alberoprocLeggiList}" var="leggi_var">
									<tr class="<%=(i%2)==0?"odd":"even"%>">
									      <td >						
												${leggi_var.legge.leDescrizione}
										  </td>
										  <td>
										  		${leggi_var.legge.leggitipi.ltDescrizione}
			    		                  </td>
						               	  <td  align="center">
						               	  	  <c:if test="${leggi_var.legge.oggetto.id.codice!=null}">
							               	  	  <jsp:include page="../includes/visualizzaOggetto.jsp" >
							       						<jsp:param name="idElemento" value="leggeB${leggi_var.legge.oggetto.id.codice}" />
							       						<jsp:param name="fileId" value="${leggi_var.legge.oggetto.id.codice}" />						   							
							   					  </jsp:include>										 
											  </c:if>
						              	  </td>
						                  <td>
						                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaLegge.htm?alberoproc.id.codice=${alberoproc.id.codice}&codicelegge=${leggi_var.id.codice}#legge_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${leggi_var.legge.leDescrizione}">
								               	 <label><fmt:message key="label.elimina.image" /></label>
								            </a>
						                  </td>
									</tr>
									<%i++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createLeggi.htm?alberoproc.id.codice=${alberoproc.id.codice}#legge_anchor','') " title="<fmt:message key="alberoproc.button.nuovalegge" />"><fmt:message key="alberoproc.button.nuovalegge" /></a></li>
										</ul>
									</div>
								</td>
							</tr>
							</c:if>
							<c:if test="${alberoproc.id.codice!=null}">
							<tr>
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.documentazione_ereditate" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
				     						<tr class="header">
											<td width="30%"><fmt:message key="alberoproc.label.documentazione_descrizione" /> </td>
											<td width="40%"><fmt:message key="label.alberoproc" /> </td>
											<td width="2%" ><fmt:message key="alberoproc.label.alberoprocDocumenti_pubblica" /><init:help idHelp="help_p_er" textKey="alberoproc.help.documentazione_p"/></td>
											<td width="2%"><fmt:message key="alberoproc.label.alberoprocDocumenti_richiesto" /><init:help idHelp="help_r_er" textKey="alberoproc.help.documentazione_r"/></td>
											<td width="2%"><fmt:message key="alberoproc.label.alberoprocDocumenti_flgDomandafo" /></td>
							                <td width="2%"><fmt:message key="alberoproc.label.alberoprocDocumenti_foRichiedefirma" /></td>
							                <td width="5%" align="center"><fmt:message key="alberoproc.label.documentazione_scarica" /></td>
							               
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int y=1;%>
									<c:forEach items="${alberoprocDocumentiEreditati}" var="docer_var" varStatus="idx_docer">
									<tr class="<%=(y%2)==0?"odd":"even"%>">
									      <td>									       	 
									       	 <c:out value="${docer_var.descrizione}" escapeXml="true"/>
										  </td>
										  <td>	
									       	 <a href="view.htm?codice=${docer_var.alberoproc.id.codice}">${docer_var.alberoproc.vwAlberoproc.scDescrizione}</a>
										  </td>
										  <td>
										  	  <c:if test="${docer_var.pubblica == 1}">AR/FE</c:if>
										  	  <c:if test="${docer_var.pubblica == 2}">AR</c:if>
										  	  <c:if test="${docer_var.pubblica == 3}">FO</c:if>
			    		                  </td>
			    		                  <td>
							             	  <c:if test="${docer_var.richiesto eq true}">SI</c:if>
						              	  </td>
										  <td>
							             	  <c:if test="${docer_var.flgDomandafo eq true}">SI</c:if>	  	  
						              	  </td>
						              	  <td>
							             	  <c:if test="${docer_var.foRichiedefirma eq true}">SI</c:if>								  	
						              	  </td>
						              	  <td align="center">
							               	 <c:if test="${docer_var.oggetto.id.codice!=null}">
							               	 	<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       					<jsp:param name="idElemento" value="DocEred${docer_var.oggetto.id.codice}" />
							   						<jsp:param name="fileId" value="${docer_var.oggetto.id.codice}" />
							   					</jsp:include>						               	  
											  </c:if>
						              	  </td>
						            </tr>
									<%y++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr id="doc_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.documentazione" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="70%"><fmt:message key="alberoproc.label.documentazione_descrizione" /> </td>
											<td width="2%" ><fmt:message key="alberoproc.label.alberoprocDocumenti_pubblica" /><init:help idHelp="help_p" textKey="alberoproc.help.documentazione_p"/></td>
											<td width="2%" ><fmt:message key="alberoproc.label.alberoprocDocumenti_richiesto" /><init:help idHelp="help_r" textKey="alberoproc.help.documentazione_r"/></td>						                
											<td width="2%" ><fmt:message key="alberoproc.label.alberoprocDocumenti_flgDomandafo" /></td>
											<td width="2%" ><fmt:message key="alberoproc.label.alberoprocDocumenti_foRichiedefirma" /></td>
							                <td width="5%" align="center"><fmt:message key="alberoproc.label.documentazione_scarica" /></td>
							                <td width="5%" align="center"><fmt:message key="alberoproc.label.documentazione_elimina" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int j=1;%>
									<c:forEach items="${alberoprocDocumentiList}" var="doc_var">
									<tr class="<%=(j%2)==0?"odd":"even"%>">
									      <td >	
									      	<a href="javascript:doHref('viewDocumenti.htm?alberoproc.id.codice=${alberoproc.id.codice}&alberoprocDocumenti.id.codice=${doc_var.id.codice}#doc_anchor')" 
									      	title="<fmt:message key="label.visualizza" />  <c:out value="${doc_var.descrizione}" escapeXml="true"/>">
											 <c:out value="${doc_var.descrizione}" escapeXml="true"/>
											</a>
										  </td>
										  <td>								  	 
										      <c:if test="${doc_var.pubblica == 1}">AR/FE</c:if>
										  	  <c:if test="${doc_var.pubblica == 2}">AR</c:if>
										  	  <c:if test="${doc_var.pubblica == 3}">FO</c:if>
			    		                  </td>
							              <td>
							             	  <c:if test="${doc_var.richiesto eq true}">SI</c:if>								  	  
						              	  </td>
										  <td>
							             	  <c:if test="${doc_var.flgDomandafo eq true}">SI</c:if>
						              	  </td>								  
						              	  <td>
							             	  <c:if test="${doc_var.foRichiedefirma eq true}">SI</c:if> 
						              	  </td>
						              	  <td align="center">
							               	 <c:if test="${doc_var.oggetto.id.codice!=null}">
							               	 	<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       					<jsp:param name="idElemento" value="Doc${doc_var.oggetto.id.codice}" />
							   						<jsp:param name="fileId" value="${doc_var.oggetto.id.codice}" />
							   					</jsp:include>							               	  
											  </c:if>
						              	  </td>
						                  <td>
						                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaDocumento.htm?alberoproc.id.codice=${alberoproc.id.codice}&codicedocumento=${doc_var.id.codice}#doc_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />  <c:out value="${doc_var.descrizione}" escapeXml="true"/>">
								               	 <label><fmt:message key="label.elimina.image" /></label>
								            </a>
						                  </td>
									</tr>
									<%j++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createDocumenti.htm?alberoproc.id.codice=${alberoproc.id.codice}#docanchor','') " title="<fmt:message key="alberoproc.button.nuovodocumento" />"><fmt:message key="alberoproc.button.nuovodocumento" /></a></li>
										</ul>
									</div>
								</td>
							</tr>									
		
								<c:if test="${alberoprocArendoEreditate!=null and not empty alberoprocArendoEreditate && (alberoproc.alberoprocArendos==null || empty alberoproc.alberoprocArendos)}">	
								<tr >
									<td colspan="4">
									<fieldset><legend><b><fmt:message key="alberoproc.label.alberoprocarendo_ereditate" /></b></legend>
									<div class="jmesa">
									<table border="0"  cellpadding="2" cellspacing="0" class="table">
										<thead>
					     						<tr class="header">
												<td width="70%"><fmt:message key="label.famiglia" /> </td>
								                <td width="70%"><fmt:message key="label.categoria" /> </td>
								            </tr>
										</thead>
										<tbody class="tbody">
										<%int z=1;%>
										<c:forEach items="${alberoprocArendoEreditate}" var="arendoer_var">
										<tr class="<%=(z%2)==0?"odd":"even"%>">
										      <td >	
										      	${arendoer_var.tipifamiglieendo.tipo}
											  </td>
										      <td >	
										      	${arendoer_var.tipiendo.tipo}
											  </td>
							            </tr>
										<%z++; %>
										</c:forEach>
										</tbody>
									</table>
									</div>
									</fieldset>
									</td>
								</tr>
								</c:if>
										
							<tr id="arendo_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.arendo" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="70%"><fmt:message key="label.famiglia" /> </td>
							                <td width="60%"><fmt:message key="label.categoria" /> </td>
							                <td width="5%" align="center"><fmt:message key="label.elimina" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int k=1;%>
									<c:forEach items="${alberoprocArendos}" var="arendo_var">
									<tr class="<%=(k%2)==0?"odd":"even"%>">
									      <td >	
									      	${arendo_var.tipifamiglieendo.tipo}
										  </td>
									      <td >	
									      	${arendo_var.tipiendo.tipo}
										  </td>
						                  <td>
						                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaArendo.htm?alberoproc.id.codice=${alberoproc.id.codice}&codicearendo=${arendo_var.id.codice}#arendo_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ">
								               	 <label><fmt:message key="label.elimina.image" /></label>
								            </a>
						                  </td>
									</tr>
									<%k++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createArendo.htm?alberoproc.id.codice=${alberoproc.id.codice}#arendo_anchor','') " title="<fmt:message key="label.aggiungi" />"><fmt:message key="label.aggiungi" /></a></li>
										</ul>
									</div>
								</td>
							</tr>		
							
							
							<%-- ALBEROPROC_TIPISOGGETTO --%>
							
							<c:if test="${alberoprocTipisoggettoEreditati!=null and not empty alberoprocTipisoggettoEreditati}">	
								<tr >
									<td colspan="4">
									<fieldset><legend><b><fmt:message key="alberoproc.label.alberoproctipisoggetto_ereditate" /></b></legend>
									<div class="jmesa">
									<table border="0"  cellpadding="2" cellspacing="0" class="table">
										<thead>
					     						<tr class="header">											
													<td width="40%"><fmt:message key="label.descrizione" /> </td>
									                <td width="45%"><fmt:message key="label.descrizione_estesa" /> </td>
										            <td width="5%"><fmt:message key="label.obbligatorio" /> </td>
										            <td width="5%"><fmt:message key="label.occorrenze_max" /> </td>								              
										         </tr>
										</thead>
										<tbody class="tbody">
										<%int zz=1;%>
										<c:forEach items="${alberoprocTipisoggettoEreditati}" var="arts_var"  varStatus="a">
										<tr class="<%=(zz%2)==0?"odd":"even"%>">
										      <td >	
										      	${arts_var.tipisoggetto.tiposoggetto}
											  </td>
											  <td width="70%">
											  <input id="flagObbDescId${arts_var.id.codice}" data-codice="${arts_var.id.codice }"
								              			type="text" maxlength="400"
								              			onchange="changeTipoSoggettoDescrizione(this);" value="${arts_var.overrideDescrizione}" size="50" /></td>
								              <td width="70%">
								              		<input id="flagObbTsId${arts_var.id.codice}" data-codice="${arts_var.id.codice }"
								              			type="checkbox" 
								              			onclick="changeTipoSoggettoObb(this);" ${arts_var.obbligatorio?'checked':''} /></td>
								              <td width="70%">
								              
								              	<input id="flagObbOccId${arts_var.id.codice}" 
								              			data-codice="${arts_var.id.codice }"
								              			inputmode="numeric"
								              			oninput="this.value = this.value.replace(/\D+/g, '')"  
								              			maxlength="2"
								              			onchange="changeTipoSoggettoOccurrence(this);" value="${arts_var.occorrenzeMax}" size="2"/>
								              </td>
							            </tr>
										<%zz++; %>
										</c:forEach>
										</tbody>
									</table>
									</div>
									</fieldset>
									</td>
								</tr>
								</c:if>
										
							<tr id="artipisoggetto_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.alberoproctipisoggetto" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
							                <td width="40%"><fmt:message key="label.descrizione" /> </td>
							                <td width="45%"><fmt:message key="label.descrizione_estesa" /> </td>
								            <td width="5%"><fmt:message key="label.obbligatorio" /> </td>
								            <td width="5%"><fmt:message key="label.occorrenze_max" /> </td>
							                <td width="5%" align="center"><fmt:message key="label.elimina" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int kk=1;%>
									<c:forEach items="${alberoprocTipisoggettos}" var="arts_var" varStatus="a">
									<tr class="<%=(kk%2)==0?"odd":"even"%>">
									      <td >	
									      	${arts_var.tipisoggetto.tiposoggetto}
										  </td>
									      <td>
											  <input id="flagObbDescId${arts_var.id.codice}" data-codice="${arts_var.id.codice }"
								              			type="text"  maxlength="400"
								              			onchange="changeTipoSoggettoDescrizione(this);" value="${arts_var.overrideDescrizione}" size="50"/></td>
								           <td>
								              		<input id="flagObbTsId${arts_var.id.codice}" data-codice="${arts_var.id.codice }"
								              			type="checkbox" 
								              			onclick="changeTipoSoggettoObb(this);" ${arts_var.obbligatorio?'checked':''} /></td>
								           <td>
								              
								              	<input id="flagObbOccId${arts_var.id.codice}" 
								              			data-codice="${arts_var.id.codice }"
								              			inputmode="numeric"
								              			oninput="this.value = this.value.replace(/\D+/g, '')"  
								              			maxlength="2"
								              			onchange="changeTipoSoggettoOccurrence(this);" value="${arts_var.occorrenzeMax}" size="2" />
						                  <td>
						                  	<a class="eliminaRiga" style="float: none;" href="javascript:eliminaTSoggetto(${alberoproc.id.codice},${arts_var.id.codice})" title="<fmt:message key="label.elimina" /> ">
								               	 <label><fmt:message key="label.elimina.image" /></label>
								            </a>
								            <script type="text/javascript">
								            
								                 function eliminaTipoSoggetto1(codicealberoproc, codice, messaggio){
								                	 console.log(messaggio);
								                	 doHref('deleteTiposoggetto.htm?alberoproc.id.codice='+codicealberoproc+'&codice='+codice+'#artipisoggetto_anchor',messaggio);
								                 }
								                 
								                 async function checkUsingTipiSoggetto(codicealberoproc, codice){
								                	 const response = await fetch('ajaxCheckUsingTipiSoggetto.htm?idAlberoproc='+codicealberoproc+'&idTipiSoggetto='+codice ,{
								                	        method: "POST",
								                	        cache: "no-cache"
								                	 });
								                	 
								                	 let json = await response.json();								                	 
								                	 return json.usingTipiSoggetto;								                		 
								                 }
								                 
								                 async function eliminaTSoggetto(codicealberoproc, codice){
								                	 let messaggio = '<fmt:message key="javascript.confirm.delete" />';
								                	 if(await checkUsingTipiSoggetto(codicealberoproc, codice)){
								                		 messaggio = '<fmt:message key="javascript.confirm.delete.warning.tipisoggetto" />';
								                	 }
								                	 eliminaTipoSoggetto1(codicealberoproc, codice, messaggio);
								                 }
								               
								            
								            </script>
						                  </td>
									</tr>
									<%kk++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createTipisoggetto.htm?alberoproc.id.codice=${alberoproc.id.codice}#artipisoggetto_anchor','') " title="<fmt:message key="label.aggiungi" />"><fmt:message key="label.aggiungi" /></a></li>
										</ul>
									</div>
								</td>
							</tr>	
							<%--END ALBEROPROC_TIPISOGGETTO --%>									
							<tr id="ateco_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.ateco" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="5%"><fmt:message key="label.codice" /> </td>
											<td width="20%" ><fmt:message key="label.titolo" /></td>
											<td width="2%" align="center"><fmt:message key="label.elimina" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int i=1;%>
									<c:forEach items="${alberoprocAtecos}" var="ateco_var">
									<tr class="<%=(i%2)==0?"odd":"even"%>">
									      <td >						
												${ateco_var.ateco.codice}
										  </td>
										  <td>
										  		${ateco_var.ateco.titolo}
			    		                  </td>		    		                  
						                  <td>					                  	 
						                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaAteco.htm?alberoproc.id.codice=${alberoproc.id.codice}&codiceateco=${ateco_var.ateco.id}#ateco_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${ateco_var.ateco.id}">
								               	 <label><fmt:message key="label.elimina.image" /></label>
								            </a>							            
						                  </td>
									</tr>
									<%i++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>						
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:historySet('${_urlback }','../alberoprocateco/assegnaAteco.htm?alberoproc.id.codice=${alberoproc.id.codice}','') " title="<fmt:message key="alberoproc.button.nuovoateco" />"><fmt:message key="alberoproc.button.nuovoateco" /></a></li>
										</ul>
									</div>
								</td>
							</tr>
							</c:if>
						
						
							<%-- ALBEROPROC_METADATI --%>
							<c:if test="${alberoprocMetadatiEreditati!=null and not empty alberoprocMetadatiEreditati}">	
								<tr >
									<td colspan="4">
									<fieldset><legend><b><fmt:message key="alberoproc.label.alberoprocmetadati_ereditati" /></b></legend>
									<div class="jmesa">
									<table border="0"  cellpadding="2" cellspacing="0" class="table">
										<thead>
					     					<tr class="header">											
								                <td width="15%"><fmt:message key="label.metadato" /> </td>
								                <td width="15%"><fmt:message key="label.valore" /> </td>
								                <td width="70%"><fmt:message key="label.alberoproc" /> </td>
								            </tr>
										</thead>
										<tbody class="tbody">
										<%int zz=1;%>
										<c:forEach items="${alberoprocMetadatiEreditati}" var="metadato_ereditato">
										<tr class="<%=(zz%2)==0?"odd":"even"%>">
										      <td >${metadato_ereditato.chiave}</td>
										      <td >${metadato_ereditato.valore}</td>
										      <td>
										      	<a href="view.htm?codice=${metadato_ereditato.codiceIntervento}#albmetadati_anchor">${metadato_ereditato.descrizioneIntervento}</a></td>
							            </tr>
										<%zz++; %>
										</c:forEach>
										</tbody>
									</table>
									</div>
									</fieldset>
									</td>
								</tr>
								</c:if>
										
							<tr id="albmetadati_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.alberoprocmetadati" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="10%"><fmt:message key="label.metadato" /> </td>
							                <td width="85%"><fmt:message key="label.valore" /> </td>
							                <td width="5%" align="center"><fmt:message key="label.elimina" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int kk=1;%>
									<c:forEach items="${metadatis}" var="metadato">
									<tr class="<%=(kk%2)==0?"odd":"even"%>">
									      <td >${metadato.chiave}</td>
									      <td >${metadato.valore}</td>
						                  <td>
						                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteMetadato.htm?alberoproc.id.codice=${alberoproc.id.codice}&chiave=${metadato.chiave}#albmetadati_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ">
								               	 <label><fmt:message key="label.elimina.image" /></label>
								            </a>
						                  </td>
									</tr>
									<%kk++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createMetadato.htm?alberoproc.id.codice=${alberoproc.id.codice}#albmetadati_anchor','') " title="<fmt:message key="label.aggiungi" />"><fmt:message key="label.aggiungi" /></a></li>
										</ul>
									</div>
								</td>
							</tr>	
							<%--END ALBEROPROC_METADATI --%>	
							<%-- ALBEROPROC_MOVIMENTI --%>
							
							<c:if test="${alberoprocMovimentiEreditati!=null and not empty alberoprocMovimentiEreditati}">	
								<tr >
									<td colspan="4">
									<fieldset><legend><b><fmt:message key="alberoproc.label.alberoproctipimovimento_ereditati" /></b></legend>
									<div class="jmesa">
									<table border="0"  cellpadding="2" cellspacing="0" class="table">
										<thead>
					     					<tr class="header">											
								                <td width="15%"><fmt:message key="label.tipomovimento" /> </td>
								                <td width="15%"><fmt:message key="label.amministrazione" /> </td>
								                <td width="70%"><fmt:message key="label.alberoproc" /> </td>
								            </tr>
										</thead>
										<tbody class="tbody">
										<%int zza=1;%>
										<c:forEach items="${alberoprocMovimentiEreditati}" var="alberoprocMov">
										<tr class="<%=(zza%2)==0?"odd":"even"%>">
										      <td >${alberoprocMov.tipimovimento.descrizioneEstesa}</td>
										      <td >${alberoprocMov.amministrazioni.descrizioneEstesa}</td>
										      <td>
										      	<a href="view.htm?codice=${alberoprocMov.alberoproc.id.codice}#tipimovimento_anchor">${alberoprocMov.alberoproc.scDescrizione}</a></td>
							            </tr>
										<%zza++; %>
										</c:forEach>
										</tbody>
									</table>
									</div>
									</fieldset>
									</td>
								</tr>
								</c:if>
							<tr id="tipimovimento_anchor">
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.alberoproctipimovimento" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
					     					<tr class="header">											
								                <td width="15%"><fmt:message key="label.tipomovimento" /> </td>
								                <td width="15%"><fmt:message key="label.amministrazione" /> </td>
								                <td width="70%"><fmt:message key="label.elimina" /> </td>
								            </tr>
									</thead>
									<tbody class="tbody">
									<%int kka=1;%>
									<c:forEach items="${alberoprocMovimentis}" var="alberoprocMov">
									<tr class="<%=(kka%2)==0?"odd":"even"%>">
									       <td >${alberoprocMov.tipimovimento.descrizioneEstesa}</td>
										      <td >${alberoprocMov.amministrazioni.descrizioneEstesa}</td>
										      <td>
										      		<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteTipimovimento.htm?alberoproc.id.codice=${alberoproc.id.codice}&codice=${alberoprocMov.id.codice}#tipimovimento_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${alberoprocMov.id.codice}">
										               	 <label><fmt:message key="label.elimina.image" /></label>
										            </a>	
											 </td>
							            
									</tr>
									<%kka++; %>
									</c:forEach>
									</tbody>
								</table>
								</div>
								</fieldset>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createTipimovimento.htm?alberoproc.id.codice=${alberoproc.id.codice}#tipimovimento_anchor','') " title="<fmt:message key="label.aggiungi" />"><fmt:message key="label.aggiungi" /></a></li>
										</ul>
									</div>
								</td>
							</tr>	
							<%--END ALBEROPROC_MOVIMENTI --%>
						</table>
						<jsp:include page="formAlberoProcTempi.jsp" >
							<jsp:param name="codiceIntervento" value="${alberoproc.id.codice}"/>
						</jsp:include>
						<%-- Nel caso di mercati attivi gestisce le opzioni di visualizzazioni
						     del mercato uso in base al mercato scelto --%>
						<c:if test="${mercati_attivi eq true}">
							<script type='text/javascript'>
								mercatiUsoDisplay();
								function mercatiUsoDisplay(){
									var idMercato=document.getElementById("mercato_hidden").value;
									if(idMercato!=''){
										 $("mercatiUso").appear();
									}
								 }
							</script>
						</c:if>
						
						
						
						
						
						
						
						
						
	   <vbg-modal id="vbgmodal-validazione">
			<div slot='body'>
				<h1>
	               <fmt:message key="label.result_validazione"/>
	             </h1>
	             <div id="vbgmodal-validazione-testo" class="">
	             	
	             </div>
			</div>
			<div slot='footer'>
				<div class="btn btn-primary" id="closeModalValidazione"><fmt:message key="button.close"/></div>            
			</div>
		</vbg-modal>
						
						
						
						<script type='text/javascript'>
						
							
							function changeSelectboxAzione(idPosition,url){
								
								var codiceAzione=document.getElementById(idPosition).value;
								new Ajax.Request(url+codiceAzione, {
										method: 'post',	
										onSuccess: function(transport){
											dijit.showTooltip(transport.responseText, dojo.byId(idPosition))
											setTimeout(function(){dijit.hideTooltip(dojo.byId(idPosition))},2000);
										},
										onFailure: function(transport){ 
										  alert("Errore durante il salvataggio del dato");
										  console.error(transport);
										}						    		 
								});
							}
						
							
							
							
							const modalValidazione = document.getElementById('vbgmodal-validazione');
							
							document.getElementById('closeModalValidazione').addEventListener('click', (e)=>{
								modalValidazione.close();		
							});
							
							
							function datoAggiornato(obj, status, text){
	
								document.getElementById('vbgmodal-validazione-testo').classList.remove('success_header');
								document.getElementById('vbgmodal-validazione-testo').classList.remove('error_header');
								
								let cssClass = (status===200)?'success_header':'error_header';
								
								document.getElementById('vbgmodal-validazione-testo').classList.add(cssClass);
								
								if(status===200){
									document.getElementById('vbgmodal-validazione-testo').innerText=text;								
								}else{
									document.getElementById('vbgmodal-validazione-testo').innerText='Si sono verificati errori nel salvataggio del dato';
									console.log(text);
								}
								
								
								
								modalValidazione.open();
								
							}
							
							
							
						async function changeTipoSoggettoDescrizione(obj){
								
								var codice = obj.dataset.codice;
																				
									
									vbg.mostraModalCaricamento();
						    		const data = new URLSearchParams();
									data.append('id', codice);
									data.append('descrizione', obj.value);
																	
									const response = await fetch("${pageContext.request.contextPath}/alberoproc/ajaxChangeTipiSoggettoDescrizione.htm", {
						                method: "POST",
						                cache: "no-cache",
						                body: data
									});
								
									
								 	
								 	window.vbg.nascondiModalCaricamento();
								 	datoAggiornato(obj, response.status,  await response.text());  
								
							}
							
					async function changeTipoSoggettoObb(obj){
						
							var codice = obj.dataset.codice;
							
							
							vbg.mostraModalCaricamento();
				    		const data = new URLSearchParams();
							data.append('id', codice);
							data.append('obbligatorio', obj.checked);
															
							const response = await fetch("${pageContext.request.contextPath}/alberoproc/ajaxChangeTipiSoggettoObb.htm", {
				                method: "POST",
				                cache: "no-cache",
				                body: data
							});
													
						 	
						 	window.vbg.nascondiModalCaricamento();
						 	datoAggiornato(obj, response.status,  await response.text()); 
								
							}
							
					async function changeTipoSoggettoOccurrence(obj){
						
							var codice = obj.dataset.codice;
							
							
							vbg.mostraModalCaricamento();
				    		const data = new URLSearchParams();
							data.append('id', codice);
							data.append('occorrenze', obj.value);
															
							const response = await fetch("${pageContext.request.contextPath}/alberoproc/ajaxChangeTipiSoggettoOccurrence.htm", {
				                method: "POST",
				                cache: "no-cache",
				                body: data
							});
						
						
					 	
					 		window.vbg.nascondiModalCaricamento();
					 		
					 		datoAggiornato(obj, response.status,  await response.text()); 
								
							}
							
							
						</script>
					</spring-form:form>
						<div id="functions">
							<ul>
								<c:if test="${alberoproc.id.codice==null}">
									<li><a href="javascript:doSubmit('insert.htm?codicepadre=${alberoprocPadre.id.codice}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
								</c:if>
								<c:if test="${alberoproc.id.codice!=null}">
									<script type="text/javascript">
										var goToUrlRuoli = "../alberoproc/createRuoli.htm?alberoproc.id.codice=${alberoproc.id.codice}";
										goToUrlRuoli = escape(goToUrlRuoli);
										var goToUrlAlberocausali= "../alberocausali/list.htm?alberoproc.id.codice=${alberoproc.id.codice}";
										goToUrlAlberocausali = escape(goToUrlAlberocausali);
										var goToUrlPeopleOp= "../alberoprocpeopleoper/list.htm?alberoproc.id.codice=${alberoproc.id.codice}";
										goToUrlPeopleOp = escape(goToUrlPeopleOp);
										var goToUrlPeopleDic= "../alberoprocpeoplehref/list.htm?alberoproc.id.codice=${alberoproc.id.codice}";
										goToUrlPeopleDic = escape(goToUrlPeopleDic);
										var goToUrlCartScheda= "../stp/schedaSpiegazioneEndo2.htm?codice=${stpendo2_codice}";
										goToUrlCartScheda = escape(goToUrlCartScheda);
										var goToUrlCartPannello= "../cart/viewInvioEndo.htm?tipo=2&codice=${alberoproc.id.codice}";
										goToUrlCartPannello = escape(goToUrlCartPannello);
									</script>
									<li><a href="javascript:doHref('create.htm?codicepadre=${alberoproc.id.codice}','');"><fmt:message key="button.new" /></a></li>
									<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
									<li><a href="javascript:historySet('${_urlback}','..%2Falberoproc/listmodelli.htm?codiceprocedimento=${alberoproc.id.codice}','')"><fmt:message key="alberoproc.button.modelli" /></a></li>
									<li><a href="javascript:historySet('${_urlback}','..%2Falberoproc/listmodelliAttivita.htm?codiceprocedimento=${alberoproc.id.codice}','')"><fmt:message key="alberoproc.button.modelli_attivita" /></a></li>
									<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlAlberocausali,'')"><fmt:message key="alberoproc.button.alberocausali" /></a></li>
									<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlRuoli,'')"><fmt:message key="alberoproc.button.ruoli" /></a></li>
									<c:if test="${vert_prot_attivo == true}">
										<li><a href="javascript:historySet('${_urlback}','..%2Falberoproc/listparametriProtAndFasc.htm?codiceAlberoproc=${alberoproc.id.codice}','')" title="<fmt:message key="alberoproc.button.parametri_protocollazione_fascicolazione" />"><fmt:message key="alberoproc.button.parametri_protocollazione_fascicolazione" /></a></li>
									</c:if>
									<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlPeopleOp,'')"><fmt:message key="alberoproc.button.peopleoperazioni" /></a></li>
									<c:if test="${vert_people_attivo eq true }">
										<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlPeopleDic,'')"><fmt:message key="alberoproc.button.peopledichiarazioni" /></a></li>
									</c:if>
									<c:if test="${vert_cart_attivo}">
										<c:if test="${cart_schedaspiegazione eq true}">
											<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlCartScheda,'')"><fmt:message key="alberoproc.button.schedaspiegazione" /></a></li>
										</c:if>
										<c:if test="${cart_pannellocontrollo eq true}">
											<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlCartPannello,'')"><fmt:message key="alberoproc.button.pannellocontrollo" /></a></li>
										</c:if>
									</c:if>
								 	<c:if test="${CENTRO_SERVIZI eq true}">
								 		<li><a href="javascript:historySet('${_urlback}','../foarjservizi/list.htm?codiceprocedimento=${alberoproc.id.codice}','')"><fmt:message key="alberoproc.button.foarjservizi" /></a></li>
								 	</c:if>
								 	<li><a href="javascript:historySet('${_urlback}','../alberoproctipisogback/list.htm?codiceAlberoproc=${alberoproc.id.codice}','')"><fmt:message key="alberoproc.button.tipi_sogg_richiesti" /></a></li>
									<c:if test="${flag_bolkestein eq true}">
										<li><a href="javascript:historySet('${_urlback}','..%2Falberoprocbolkestein/view.htm?codiceAlberoproc=${alberoproc.id.codice}','')" title="<fmt:message key="alberoproc.button.configurazione_bolkestein" />"><fmt:message key="alberoproc.button.configurazione_bolkestein" /></a></li>
									</c:if>
									<li><a href="javascript:doHref('../alberoproc/viewModificaAlbero.htm?codice=${alberoproc.id.codice}','')"><fmt:message key="button.sposta" /></a></li>
                                    <li><a href="javascript:historySet('${_urlback}','..%2Falberoproc/comuniesclusi.htm?codiceprocedimento=${alberoproc.id.codice}','')"><fmt:message key="alberoproc.button.escludicomuni" /></a></li>
                                    <li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li> 
								</c:if>
								<c:if test="${isSpostaPratica }">
									<li><a href="javascript:doHref('../spostamentopratiche/start.htm?codiceIntervento=${alberoproc.id.codice}','')" ><fmt:message key="button.sposta_pratica"/></a></li>
								</c:if>
								<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>