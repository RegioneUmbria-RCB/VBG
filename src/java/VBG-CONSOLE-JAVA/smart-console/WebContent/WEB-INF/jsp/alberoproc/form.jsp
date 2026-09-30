<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.AuthLevel"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocEndo"%>
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
	</style>
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
		String urlBack = request.getScheme() + "://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/alberoproc/view.htm?codice="+request.getParameter("codice");
		urlBack = URLEncoder.encode(urlBack,"UTF-8");
		%>
        <div id="subcontent">
		<script type="text/javascript">
			function visualizzaSubEndo(codiceendo, idcomune){
				var divId = 'subEndo_id';
				
				var jqxhr = jQuery.ajax({
					  url: '${pageContext.request.contextPath}/inventarioprocedimenti/ajaxVisualizzasubEndo.htm',
					  context: document.body,
					  cache: false,				
					  dataType: "html",
					  data: "codiceinventario="+ codiceendo+"&idcomuneinventario="+idcomune,
					  success: function(dataResult) { 
						  $("subEndo_content_id").innerHTML = dataResult;		
							dijit.byId(divId).show();
						},
					  error: function(dataError){						  
						  $("subEndo_content_id").innerHTML = dataResult;		
							dijit.byId(divId).show();
					  }	
					});					
				
				
			}
		</script>
		<div dojoType="dijit.Dialog" id="subEndo_id" title="<fmt:message key="label.inventarioprocendo.title" />">
			<div dojoType="dijit.layout.ContentPane" class="generic_dialog">
    			<div id="subEndo_content_id"></div> 
			</div>			
		</div>
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
                <td id="albero_box" style="vertical-align: top;">    
					<a title="<fmt:message key="label.visualizza_albero_procedimenti" />" href="javascript:showHideDiv('alberoContent')">
    					<img id="alberoimg_id" border="0" src="${pageContext.request.contextPath }/images/search.gif" />
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
                                    if(item){
                                        dis = alberoprocStore.getValue(item, "disabilitato");
                                    }
                                    if(dis == 'false'){
                                        return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
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
							<td colspan="3">
								<spring-form:input id="descrizione_id" path="scDescrizione" size="70" />
								<spring-form:errors path="scDescrizione" cssClass="error"/>
								<c:if test="${alberoproc.id.codice==null}">
									<span><spring-form:checkbox path="areaPrimaria" /><fmt:message key="alberoproc.label.areaprimaria" /></span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="label.note" />
							</td>
							<td colspan="3">
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
							<td colspan="3">
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
							<td colspan="3">
								<spring-form:select path="scPubblica" > 
									<spring-form:option value="" ><fmt:message key='alberoproc.label.eredita_dal_padre' /></spring-form:option>
									<spring-form:option value="0" ><fmt:message key='label.scPubblica_0' /></spring-form:option>
									<spring-form:option value="1"><fmt:message key='label.scPubblica_1' /></spring-form:option>
								</spring-form:select>
								<spring-form:errors path="scPubblica" cssClass="error"/> 
							</td>
						</tr>		
				<c:set scope="page" value="<%=ORMHelper.isConsoleRegionale() %>" var="_isRegionale"></c:set>	
				<%-- 
				<c:if test="${_isRegionale}">
				

				<tr>
					<td class="titoloSezione" colspan="4">
						<fmt:message key="label.parametri_cart" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.tipo" />
					</td>
					<td colspan="3">
						<input type="hidden" name="stp_endo_codice" value="${stpEndoTipo2_loc.id.codice}" />
						<select id="tipo_id" name="stp_endo_tipo" onchange="mostranascondiintervento();" >
								<option <c:if test="${stpEndoTipo2_loc.tipo eq 'ATTIVITA' }">selected="selected"</c:if> value="ATTIVITA" label="">ATTIVITA</option>
								<option <c:if test="${stpEndoTipo2_loc.tipo eq 'CATEGORIA' }">selected="selected"</c:if> value="CATEGORIA" label="">CATEGORIA</option>
								<option <c:if test="${stpEndoTipo2_loc.tipo eq 'ENDO' }">selected="selected"</c:if> value="ENDO" label="">ENDO</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.codice_endo_regionale" />
					</td>
					<td colspan="3">
						<input id="codiceEndoRegionale_id" style="text-align: right;" name="stp_endo_codiceEndoRegionale" size="10" maxlength="20" value="${stpEndoTipo2_loc.codiceEndoRegionale}"/>
						
					</td>
				</tr>
				
				<c:set var="mostraInterventoDisplay" value="display: none;"></c:set>				
				<c:if test="${stpEndoTipo2_loc.tipo eq 'ENDO' or empty stpEndoTipo2_loc.tipo }">		
					<c:set var="mostraInterventoDisplay" value=""></c:set>
				</c:if>
				<tr id="tipointerventoId" style="${mostraInterventoDisplay}">
					<td>
						<fmt:message key="label.stp_tipologie_endo2" />
					</td>
					<td colspan="3">					
						<select id="stp_endo_stpTipologieEndo2_id" name="stp_endo_stpTipologieEndo2_codice"  >
							<option value="" label="">seleziona...</option>
							<c:forEach items="${stpTipologieEndo2s}" var="stpTipologieEndo2_var">
									<option value="${stpTipologieEndo2_var.id.codice}"  <c:if test="${stpEndoTipo2_loc.stpTipologieEndo2.id.codice eq stpTipologieEndo2_var.id.codice}">selected="selected"</c:if>>${stpTipologieEndo2_var.descrizione}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				

				
				
				<tr>
					<td class="titoloSezione" colspan="4"></td>
				</tr>
				
				
					<!-- -->
				
					<c:if test="${vert_cart_attivo}">
						
							<tr>
								<td>
									<fmt:message key="alberoproc.label.pubblica_cart" />
								</td>
								<td colspan="3">
									
								<div id="functions" style="width: inherit;">
									<ul>
										<li><a href="javascript:popupStpendo2()" title="<fmt:message key="alberoproc.button.nuovoateco" />">Parametri CART</a></li>
									</ul>
								</div>
								<script type="text/javascript">
								

								function popupStpendo2(){
									
									var jqxhr = jQuery.ajax({
										  url: "${pageContext.request.contextPath}/alberoproc/ajaxViewParametriStp.htm",
										  context: document.body,
										  cache: false,				
										  dataType: "html",
										  data: "codiceAlberoproc=${alberoproc.id.codice}",
										  success: function(dataResult) {
											  enableFunctions();
											  
											  dijit.byId('parametriStpId').attr("style", "overflow:auto; width:600px");
									          dijit.byId('parametriStpId').attr("content", dataResult);
										  	  dijit.byId('parametriStpId').show();
											  
											  },
										  error: function(dataError){		
											  enableFunctions();
											  alert(dataError)
											  dijit.byId('parametriStpId').attr("style", "overflow:auto; width:600px");
									          dijit.byId('parametriStpId').attr("content", dataError);
										  	  dijit.byId('parametriStpId').show();
										  }	
										});										
								}								
								</script>
								
																										
								</td>
							</tr>
						
					</c:if>						 
				
				</c:if>						 
			--%>			 
					
						 
						<tr>
							<td><fmt:message key="alberoproc.label.azione" /></td>
							<td colspan="3">
								<spring-form:select path="azione.azId" >
									<option value="" />
									<spring-form:options items="${azioniList}" itemLabel="azDescrizione" itemValue="azId" />
								</spring-form:select>
								<spring-form:errors path="azione" cssClass="error"/> 
							</td>
						</tr>
				<%--
						<tr>
							<td><fmt:message key="alberoproc.label.tipologiaregistro" /></td>
							<td colspan="3">
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
							<td colspan="3">							
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
							<td>
								<fmt:message key="alberoproc.label.responsabileproc" />
							</td>
							<td colspan="3">
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
							<td colspan="3">
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
							<td colspan="3">
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
							<td colspan="3">
								<spring-form:input id="progressivoistanze_id" path="progressivoistanze" size="15"  maxlength="15"/>
								<init:help idHelp="help_progressivoistanze" textKey="alberoproc.help.progressivoistanze"/>
								<spring-form:errors path="progressivoistanze" cssClass="error"/>
							</td>
						</tr>
						</c:if>
						
						
						
						 --%>	 

						
						 
						
			    		<tr>
							<td>
								<fmt:message key="alberoproc.label.scOrdine" />
							</td>
							<td colspan="3">
								<spring-form:input id="scOrdine_id" path="scOrdine" size="2"  maxlength="2"/>
								<spring-form:errors path="scOrdine" cssClass="error"/>
							</td>
						</tr>
						
						<%--
						<c:if test="${vert_replicaistanze_attivo == true}">
						<tr>
							<td>
								<fmt:message key="alberoproc.label.flagReplicaistanze" />
							</td>
							<td colspan="3">
								<spring-form:checkbox id="flagReplicaistanze_id" path="flagReplicaistanze" />
								<init:help idHelp="help_flagReplicaistanze" textKey="alberoproc.help.flagReplicaistanze" />
								<spring-form:errors path="flagReplicaistanze" cssClass="error"/>
							</td>
						</tr>
						</c:if>
						
						--%>
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
									<jsp:param name="idComuneOggetto" value="${alberoproc.oggettoWorkflowAreaRis.id.idcomune}" />
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
<%--
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
 --%>						
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
						</c:if>	

						<tr>
							<td class="titoloSezione" colspan="4">
								<fmt:message key="label.dati_anagrafe_tributaria" />
							</td>
						</tr>
						<tr>
							<td><fmt:message key="alberoproc.label.atribTipologiaintervento" /></td>
							<td colspan="3">								
								 <spring-form:input path="atribTipologiaintervento" size="5" />
								 <init:help idHelp="atribTipologiaintervento_help" textKey="alberoproc.label.atribTipologiaintervento.help"/>
								 <spring-form:errors path="atribTipologiaintervento" cssClass="error"/> 
							</td>
						</tr>

						<%--						
						</c:when>
						<c:otherwise>
						
						</c:otherwise>
						</c:choose>
						<c:if test="${mercati_attivi eq true}">
						<tr>
							<td class="titoloSezione" colspan="4">
								<fmt:message key="alberoproc.label.parametrimercati" />
							</td>
						</tr>
						<tr id="mercati">
							<td><fmt:message key="alberoproc.label.mercati" /></td>
							<td colspan="3">
								<script type="text/javascript">
									function setHiddenFieldmercati(inputField,listItem){
										var a = listItem.id;
										document.getElementById('mercato_id').value = inputField.value;
										document.getElementById('mercato_hidden').value = a;
										$("mercatiUso").appear();
									}
								</script>
								
								
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="mercato" />		
									<jsp:param name="propertyPath" value="mercato" />				
									<jsp:param name="pathPropertyDescription" value="mercato.descrizione" />
									<jsp:param name="pathPropertyCode" value="mercato.id.codice" />
									<jsp:param name="autocompleterAjax" value="findMercati.htm" />
									<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati" />
									<jsp:param name="titleKey" value="label.ricerca_manifestazione" />
							  	</jsp:include>	
								
									
							
								
							</td>
						</tr>
						<tr id="mercatiUso" style="<%=mercatiUso%>">
							<td><fmt:message key="alberoproc.label.mercatiUso" /></td>
							<td colspan="3">	
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
							<td colspan="3"> 
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
								<td>
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
								<td><fmt:message key="alberoproc.label.scMaxmq" /></td>
								<td>
									<spring-form:input cssStyle="text-align: right;" path="scMaxmq" size="15" maxlength="12"  onchange="changeValue(this);"/>
									<fmt:message key="alberoproc.label.nolimite" />
								</td>
							</tr>
							
						<%} %>
						
							
							<tr class="titoloSezione">
								<td colspan="4"><fmt:message
										key="label.dati_registro_imprese.legend" />
								</td>
							</tr>
							<tr>
								<td><fmt:message key="label.dati_registro_imprese.tipo_intervento" />
								</td>
								<td><jsp:include page="../includes/autocompletergenerico.jsp">
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
						
						 --%>
						<c:if test="${alberoproc.id.codice!=null}">
						
						
						
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
							<fieldset><legend><b><fmt:message key="alberoproc.label.endoprocedimenti.ttr" /></b></legend>
							<div class="jmesa">
							<script type='text/javascript'>
								function changeDescrizione(obj, elid,codiceendo,codicealbero){
									var url = '${pageContext.request.contextPath}/alberoproc/ajaxmodificadescrizioneendo.htm?descrizione=' + obj.value + '&codiceendo=' + codiceendo + '&codicealbero=' + codicealbero;
									new Ajax.Request(url, {
										method: 'post',	
										onSuccess: function(transport){
											dijit.showTooltip(transport.responseText, dojo.byId(elid));
											setTimeout(function(){dijit.hideTooltip(dojo.byId(elid))},1000);
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
							</script>
							<table border="0"  cellpadding="2" cellspacing="0" class="table">
								<thead>
									<tr class="header">
										<td width="20%"><fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" /> </td>
										<%--
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_azione" /></td>
										 --%>
										<%-- 
										<td width="20%" ><fmt:message key="label.descrizione" /></td>
										 --%>
						                <td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" /></td>
						                
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPrincipale" /></td>
										
										 <%--
										 <td width="5%" ><fmt:message key="label.flag_intervento" /></td>
										  --%>
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" /></td>
										<%--
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagRichiedeEndo" /> <init:help idHelp="flagRichiedeEndo_help" textKey="alberoproc.help.alberoprocEndo_flagRichiedeEndo"/></td>
										 --%>
										<%--
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagUsaBO" /><init:help idHelp="help_flag_usa_BO" textKey="alberoproc.help.flag_usa_BO"/></td>
										 --%>
						                <td width="6%"><fmt:message key="label.azioni" /></td>						                
						                
						            </tr>
								</thead>
								<tbody class="tbody">
								
								<%								
								
								boolean isInterventoPrincipale = false;								
								
								int p=1;%>
								<c:forEach items="${alberoprocEndosList}" var="endo_var" varStatus="a">
								<%
								if(!isInterventoPrincipale){
									AlberoprocEndo ape = (AlberoprocEndo)pageContext.getAttribute("endo_var");
									if(ape != null){
									 	if(ape.getInventarioprocedimento() != null){
/* 									 	 	if(ape.getInventarioprocedimento().getStpEndoTipo2s().size()>0){
									 	    	isInterventoPrincipale = true;
									 		} */
										}
									}
								}
				
								if(ORMHelper.isConsoleRegionale()){
									    
								%>
								
								<tr class="<%=(p%2)==0?"odd":"even"%>">
								      <td >
								 			<a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${endo_var.id.codiceinventario}&codicecomune=${endo_var.id.idcomune}', '')" title="<fmt:message key="label.edit.record" /> ${endo_var.id.codiceinventario}">
											${endo_var.inventarioprocedimento.procedimento}
											</a>
									  </td>
									  <%--
									  <td>	
									 
									  		<input size="70" maxlength="300" type="text" id="descrizioneId${a.index}" value="${endo_var.descrizione}" onchange="changeDescrizione(this,'descrizioneId${a.index}','${endo_var.id.codiceinventario}','${endo_var.id.fkscid}')" />			
											
									  </td>
 									--%>
		    		                  <td>		    		                   
		    		                    <input id="flagRichiestoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagRichiestoId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_proposto',true);" ${endo_var.flagRichiesto?'checked':''} />
		    		                  </td>
									
					              	  <td>					               	    
					              	    <input id="flagPrincipaleId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagPrincipaleId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_principale',true)" ${endo_var.flagPrincipale?'checked':''} />
					              	  </td>
					              	   
					              	  <td>					              	    
					              	    <input id="flagPubblicatoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagPubblicatoId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_pubblica',false)" ${endo_var.flagPubblica?'checked':''} />
					              	  </td>
					              	  <%--
					              	  <td>					              	    
					              	    <input id="flagRichiedeEndoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndo${a.index}(flagRichiedeEndoId${a.index},'${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndo.htm?codiceendo=${endo_var.id.codiceinventario}&codicealberoproc=${endo_var.id.fkscid}&tipoFlag=flag_richiede_endo',false)" ${endo_var.flagRichiedeEndo?'checked':''} />
					              	  </td>
					              	   --%>
					                  <td>
					                  <%--
					                  		<c:if test="${fn:length(endo_var.inventarioprocedimento.inventarioprocEndots)>0}">
					                  			<a class="infoColumn" style="float: none;" href="javascript:visualizzaSubEndo('${endo_var.id.codiceinventario}','${endo_var.id.idcomune}')" title="Visualizza i sotto endo">
						               			 	<label><fmt:message key="label.edit.record.image" /></label>
						          				</a>					                  			
					                  		</c:if>
					                   --%>
					                  
					                  		<a class="dettaglioColumn" style="float: none;" href="javascript:historySet('${_urlback}', '../alberoproc/editEndo.htm?invProcId=${endo_var.id.codiceinventario}&aProcId=${endo_var.id.fkscid}&idComune=${endo_var.id.idcomune}', '')" title="<fmt:message key="label.edit.record.image" /> ${endo_var.id.codiceinventario}">
						               			 <label><fmt:message key="label.edit.record.image" /></label>
						          			</a>

						                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}&codiceinventario=${endo_var.id.codiceinventario}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${endo_varr.id.codiceinventario}">
						               			 <label><fmt:message key="label.elimina.image" /></label>
						          			</a>
					                  </td>
								</tr>
								
								 	<script type='text/javascript'>

									 	
									 	function changeCheckboxValueEndo${a.index}(id, url, isPrincipaleOrProposto){
											
											var lid = id;
											new Ajax.Request(url, {
													method: 'post',	
													onSuccess: function(transport){
														dijit.showTooltip(transport.responseText, dojo.byId(id));
														setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);
														/*
														if(isPrincipaleOrProposto)
														{
															jQuery('#flagRichiestoBoId${a.index}').attr('checked','checked');
													 	    setReadOnly${a.index}('#flagPrincipaleId${a.index}','#flagRichiestoId${a.index}');
														} */
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
									 	
									
								 	</script>
							<%}else /*CONSOLE LOCALE*/{%>
    
    						<tr class="<%=(p%2)==0?"odd":"even"%>">
								      <td >						
											<a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${endo_var.id.codiceinventario}&codicecomune=${endo_var.id.idcomune}', '')" title="<fmt:message key="label.edit.record" /> ${endo_var.id.codiceinventario}">
											${endo_var.inventarioprocedimento.procedimento}
											</a>
									  </td>
									  <%--
									  <td>										 
									  		${endo_var.descrizione}														
									  </td>
 										--%>
		    		                  <td>		    		                   
		    		                    <input id="flagRichiestoId${a.index}" type="checkbox" disabled="disabled" ${endo_var.flagRichiesto?'checked':''} />
		    		                  </td>
<%--
					              	  <td>					               	    
					              	    <input id="flagInterventoId${a.index}" type="checkbox" disabled="disabled" ${endo_var.flagIntervento?'checked':''} />
					              	    
					              	  </td>
 --%> 					              	  
					              	  <td>					              	    
					              	    <input id="flagPubblicatoId${a.index}" type="checkbox" disabled="disabled" ${endo_var.flagPubblica?'checked':''} />
					              	  </td>
					              	  <td>					              	    
					              	    <input id="flagRichiedeEndoId${a.index}" type="checkbox" disabled="disabled" ${endo_var.flagRichiedeEndo?'checked':''} />
					              	  </td>
									  <td>
											<c:if test="${fn:length(endo_var.inventarioprocedimento.inventarioprocEndots)>0}">
					                  			<a class="infoColumn" style="float: none;" href="javascript:visualizzaSubEndo('${endo_var.id.codiceinventario}','${endo_var.id.idcomune}')" title="Visualizza i sotto endo">
						               			 	<label><fmt:message key="label.edit.record.image" /></label>
						          				</a>					                  			
					                  		</c:if>									  
									  </td>
								</tr>
<%}%>
								
								
								<%p++; %>
								</c:forEach>
								</tbody>
							</table>
							</div>
							</fieldset>
							</td>
						</tr>
						
						
						
<%

if(ORMHelper.isConsoleRegionale()){
								    
%>						
						<tr>
							<td colspan="4">
								<div id="functions" style="float:right;width: inherit;">
								<%-- SOLO SE SONO SU FOGLIA --%>
										<ul>
											<li><a href="javascript:doHref('createEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}#endo_anchor','') " title="Aggiungi endo di tipo 1"><fmt:message key="alberoproc.button.nuovaendo" /></a></li>
										</ul>									
										<c:if test="${not empty stpEndoTipo2_loc.id.codice && stpEndoTipo2_loc.tipo eq 'ENDO'}">
											<%if(isInterventoPrincipale == false){ %>
										<ul>
											<li><a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/createEndoPrincipale.htm?alberoproc.id.codice=${alberoproc.id.codice}#endo_anchor','') " title="<fmt:message key="label.crea_endo_amministrativo.help" />"><fmt:message key="label.crea_endo_amministrativo" /></a></li>
										</ul>
											<%} %>
									</c:if>	
								</div>
							</td>
						</tr>
<%} 
				
if(ORMHelper.isConsoleLocale()){
								    
%>
	<p />
					
						<%-- listendolocs --%>
						
						<tr id="endoloc_anchor">
							<td colspan="4">
							<fieldset><legend><b><fmt:message key="label.endoprocedimenti.loc.endo" /></b></legend>
							<div class="jmesa">
							
							<script type='text/javascript'>
								function changeDescrizioneIntervento(obj, elid,codice){
									var url = '${pageContext.request.contextPath}/alberoproc/ajaxmodificadescrizioneinterventoloc.htm?descrizione=' + obj.value + '&codice=' + codice;
									new Ajax.Request(url, {
										method: 'post',	
										onSuccess: function(transport){
											dijit.showTooltip(transport.responseText, dojo.byId(elid));
											setTimeout(function(){dijit.hideTooltip(dojo.byId(elid))},1000);
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
								
								function changeCheckboxValueEndoLoc(id, url, isPrincipaleOrProposto){
									
									var lid = id;
									new Ajax.Request(url, {
											method: 'post',	
											onSuccess: function(transport){
												dijit.showTooltip(transport.responseText, dojo.byId(id));
												setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);												
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
								
							</script>							
							<table border="0"  cellpadding="2" cellspacing="0" class="table">
								<thead>
									<tr class="header">
										<td width="20%"><fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" /> </td>
										<%-- <td width="20%" ><fmt:message key="label.descrizione" /></td> --%>
						                <td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" /></td>
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" /></td>
										
										<c:if test="${_COMUNIASSOCIATI_ eq true }">
											<td width="5%" ><fmt:message key="label.comune" /></td>						
										</c:if>
										
						                <td width="6%"><fmt:message key="label.azioni" /></td>						                
						            </tr>
								</thead>
								<tbody class="tbody">
								<c:forEach items="${listendolocs}" var="endo_var" varStatus="a">								
									<tr class="<%=(p%2)==0?"odd":"even"%>">
									      <td >						
												${endo_var.inventarioprocedimenti.procedimento}
										  </td>
										  <%--
										  <td>	
									  		<input size="70" maxlength="300" type="text" id="descrizioneEndoId${a.index}" value="${endo_var.descrizione}" onchange="changeDescrizioneIntervento(this,'descrizioneEndoId${a.index}','${endo_var.id.codice}')" />			
										  </td>
										  --%>
			    		                  <td>		    		                   
			    		                    <input id="flagNecessarioEndoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndoLoc('flagNecessarioEndoId${a.index}','${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndoLoc.htm?codice=${endo_var.id.codice}&tipoFlag=flag_necessario',true);" ${endo_var.flagNecessario?'checked':''} />
			    		                  </td>
						              	  <td>					              	    
						              	    <input id="flagPubblicatoEndoId${a.index}" type="checkbox" onclick="changeCheckboxValueEndoLoc('flagPubblicatoEndoId${a.index}','${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndoLoc.htm?codice=${endo_var.id.codice}&tipoFlag=flag_pubblica',false)" ${endo_var.flagPubblica?'checked':''} />
						              	  </td>
						              	  <c:if test="${_COMUNIASSOCIATI_ eq true }">
											<td>
												<c:choose>
													<c:when test="${empty endo_var.comune.comune}">
														<fmt:message key="label.tutti" />
													</c:when>
													<c:otherwise>${endo_var.comune.comune}</c:otherwise>
												
												</c:choose>
											</td>						
										  </c:if>
						                  <td>
						                        <c:if test="${fn:length(endo_var.inventarioprocedimenti.inventarioprocEndots)>0}">
						                  			<a class="infoColumn" style="float: none;" href="javascript:visualizzaSubEndo('${endo_var.inventarioprocedimenti.id.codice}','${endo_var.id.idcomune}')" title="Visualizza i sotto endo">
							               			 	<label><fmt:message key="label.edit.record.image" /></label>
							          				</a>					                  			
					                  			</c:if>
						                  		<a class="dettaglioColumn" style="float: none;" href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${endo_var.inventarioprocedimenti.id.codice}&codicecomune=${endo_var.inventarioprocedimenti.id.idcomune}', '')" title="<fmt:message key="label.edit.record.image" /> ${endo_var.id.codice}">
							               			 <label><fmt:message key="label.edit.record.image" /></label>
							          			</a>
							                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteEndoLoc.htm?alberoproc.id.codice=${alberoproc.id.codice}&codice=${endo_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${endo_var.id.codice}">
							               			 <label><fmt:message key="label.elimina.image" /></label>
							          			</a>
						                  </td>
									</tr>								
								</c:forEach>
								</tbody>
							</table>
							</div>	
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div id="functions" style="float:right;width: inherit;">
									<ul>
										<li><a href="javascript:doHref('createEndoLoc.htm?alberoproc.id.codice=${alberoproc.id.codice}#endoloc_anchor','') " title="<fmt:message key="alberoproc.button.nuovaendo" />"><fmt:message key="alberoproc.button.nuovaendo" /></a></li>
									</ul>
								</div>
							</td>
						</tr>
						
						
						
<%--


	listendolocs
	
 --%>


<%} %>

               <c:if test="<%=ORMHelper.isConsoleLocale() %>">
               	<c:if test="<%= isInterventoPrincipale == false %>">
					<tr id="endolocint_anchor">
							<td colspan="4">
							<fieldset><legend><b><fmt:message key="label.endoprocedimenti.loc.interventi" /></b></legend>
							<div class="jmesa">
							
							<script type='text/javascript'>
								function changeDescrizioneIntervento(obj, elid,codice){
									var url = '${pageContext.request.contextPath}/alberoproc/ajaxmodificadescrizioneinterventoloc.htm?descrizione=' + obj.value + '&codice=' + codice;
									new Ajax.Request(url, {
										method: 'post',	
										onSuccess: function(transport){
											dijit.showTooltip(transport.responseText, dojo.byId(elid));
											setTimeout(function(){dijit.hideTooltip(dojo.byId(elid))},1000);
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
								
								function changeCheckboxValueEndointervento(id, url, isPrincipaleOrProposto){
									
									var lid = id;
									new Ajax.Request(url, {
											method: 'post',	
											onSuccess: function(transport){
												dijit.showTooltip(transport.responseText, dojo.byId(id));
												setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);												
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
								
							</script>							
							<table border="0"  cellpadding="2" cellspacing="0" class="table">
								<thead>
									<tr class="header">
										<td width="20%"><fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" /> </td>
										<td width="20%" ><fmt:message key="label.descrizione" /></td>
<%--										
						                <td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" /></td>
 --%>						                
						                <%--
 									 	<td width="5%" ><fmt:message key="label.flag_intervento" /></td>
 									 	 --%>
										<td width="5%" ><fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" /></td>
						                <td width="6%"><fmt:message key="label.azioni" /></td>						                
						            </tr>
								</thead>
								<tbody class="tbody">
								<c:forEach items="${listinterventilocs}" var="endo_var" varStatus="a">								
									<tr class="<%=(p%2)==0?"odd":"even"%>">
									      <td >						
												${endo_var.inventarioprocedimenti.procedimento}
										  </td>
										  <td>	
									  		<input size="70" maxlength="300" type="text" id="descrizioneInterventoId${a.index}" value="${endo_var.descrizione}" onchange="changeDescrizioneIntervento(this,'descrizioneInterventoId${a.index}','${endo_var.id.codice}')" />			
										  </td>
<%--	
			    		                  <td>		    		                   
			    		                    <input id="flagNecessarioIntId${a.index}" type="checkbox" onclick="changeCheckboxValueEndointervento('flagNecessarioIntId${a.index}','${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndoLoc.htm?codice=${endo_var.id.codice}&tipoFlag=flag_necessario',true);" ${endo_var.flagNecessario?'checked':''} />
			    		                  </td>
 --%>			    		                  
						              	  <td>					              	    
						              	    <input id="flagPubblicatoIntId${a.index}" type="checkbox" onclick="changeCheckboxValueEndointervento('flagPubblicatoIntId${a.index}','${pageContext.request.contextPath}/alberoproc/ajaxChangeFlagAlberoprocEndoLoc.htm?codice=${endo_var.id.codice}&tipoFlag=flag_pubblica',false)" ${endo_var.flagPubblica?'checked':''} />
						              	  </td>
						                  <td>
						                        <c:if test="${fn:length(endo_var.inventarioprocedimenti.inventarioprocEndots)>0}">
						                  			<a class="infoColumn" style="float: none;" href="javascript:visualizzaSubEndo('${endo_var.inventarioprocedimenti.id.codice}','${endo_var.id.idcomune}')" title="Visualizza i sotto endo">
							               			 	<label><fmt:message key="label.edit.record.image" /></label>
							          				</a>					                  			
					                  			</c:if>
						                  		<a class="dettaglioColumn" style="float: none;" href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${endo_var.inventarioprocedimenti.id.codice}&codicecomune=${endo_var.inventarioprocedimenti.id.idcomune}', '')" title="<fmt:message key="label.edit.record.image" /> ${endo_var.id.codice}">
							               			 <label><fmt:message key="label.edit.record.image" /></label>
							          			</a>
							                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteInterventoEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}&codice=${endo_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${endo_var.id.codice}">
							               			 <label><fmt:message key="label.elimina.image" /></label>
							          			</a>
						                  </td>
									</tr>								
								</c:forEach>
								</tbody>
							</table>
							</div>	
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div id="functions" style="float:right;width: inherit;">
									<ul>
										<li><a href="javascript:doHref('createIntervento.htm?alberoproc.id.codice=${alberoproc.id.codice}#endolocint_anchor','') " title="<fmt:message key="button.aggiungi_intervento" />"><fmt:message key="button.aggiungi_intervento" /></a></li>
									</ul>
								</div>
							</td>
						</tr>
						</c:if>
						
						
						
                       </c:if>


















	
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
					       						<jsp:param name="idComuneOggetto" value="${leggier_var.legge.oggetto.id.idcomune}" />
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
						       						<jsp:param name="idComuneOggetto" value="${leggi_var.legge.oggetto.id.idcomune}" />						   							
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
						   						<jsp:param name="idComuneOggetto" value="${docer_var.oggetto.id.idcomune}" />
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
						   						<jsp:param name="idComuneOggetto" value="${doc_var.oggetto.id.idcomune}" />
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
	                  
							<c:if test="${alberoprocArendoEreditate!=null and not empty alberoprocArendoEreditate}" > <%-- (alberoproc.alberoprocArendos==null || empty alberoproc.alberoprocArendos)}"> --%>	
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
							<fieldset><legend><b><fmt:message key="alberoproc.label.arendo" /> BASE</b></legend>
							<div class="jmesa">
							<table border="0"  cellpadding="2" cellspacing="0" class="table">
								<thead>
									<tr class="header">
										<td width="70%"><fmt:message key="label.famiglia" /> </td>
						                <td width="60%"><fmt:message key="label.categoria" /> </td>
						                <c:if test="<%=ORMHelper.isConsoleRegionale() %>">
						                <td width="5%" align="center"><fmt:message key="label.elimina" /></td>
						                </c:if>
						            </tr>
								</thead>
								<tbody class="tbody">
								<%int k1=1;%>
								<c:forEach items="${alberoprocArendosTTR}" var="arendo_var">
								<tr class="<%=(k1%2)==0?"odd":"even"%>">
								      <td >	
								      	${arendo_var.tipifamiglieendo.tipo}
									  </td>
								      <td >	
								      	${arendo_var.tipiendo.tipo}
									  </td>
					                  <c:if test="<%=ORMHelper.isConsoleRegionale() %>">
					                  <td>
					                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaArendo.htm?alberoproc.id.codice=${alberoproc.id.codice}&codicearendo=${arendo_var.id.codice}#arendo_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ">
							               	 <label><fmt:message key="label.elimina.image" /></label>
							            </a>
					                  </td>
					                  
					                  </c:if>
								</tr>
								<%k1++; %>
								</c:forEach>
								</tbody>
							</table>
							</div>
							</fieldset>
							</td>
						</tr>
						<c:if test="<%=ORMHelper.isConsoleRegionale()%>">
							<tr>
								<td colspan="4">
									<div id="functions" style="float:right;width: inherit;">
										<ul>
											<li><a href="javascript:doHref('createArendo.htm?alberoproc.id.codice=${alberoproc.id.codice}#arendo_anchor','') " title="<fmt:message key="label.aggiungi" />"><fmt:message key="label.aggiungi" /></a></li>
										</ul>
									</div>
								</td>
							</tr>		
						</c:if>
							
						
						<c:if test="<%=ORMHelper.isConsoleLocale()%>">
						<tr id="arendo_anchor">
							<td colspan="4">
							<fieldset><legend><b><fmt:message key="alberoproc.label.arendo" /> Locale</b></legend>
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
								<c:forEach items="${alberoprocArendosLoc}" var="arendo_var">
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
						</c:if>
						
						
						
						
						<%-- ALBEROPROC_TIPISOGGETTO --%>
						
						
						<c:if test="${alberoprocTipisoggettoEreditati!=null and not empty alberoprocTipisoggettoEreditati}">	
							<tr >
								<td colspan="4">
								<fieldset><legend><b><fmt:message key="alberoproc.label.alberoproctipisoggetto_ereditate" /></b></legend>
								<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
				     						<tr class="header">											
							                <td width="70%"><fmt:message key="label.descrizione" /> </td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int zz=1;%>
									<c:forEach items="${alberoprocTipisoggettoEreditati}" var="arts_var">
									<tr class="<%=(zz%2)==0?"odd":"even"%>">
									      <td >	
									      	${arts_var.tipisoggetto.tiposoggetto}
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
						                <td width="60%"><fmt:message key="label.descrizione" /> </td>
						                <td width="5%" align="center"><fmt:message key="label.elimina" /></td>
						            </tr>
								</thead>
								<tbody class="tbody">
								<%int kk=1;%>
								<c:forEach items="${alberoprocTipisoggettos}" var="arts_var">
								<tr class="<%=(kk%2)==0?"odd":"even"%>">
								      <td >	
								      	${arts_var.tipisoggetto.tiposoggetto}
									  </td>
								      
					                  <td>
					                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteTiposoggetto.htm?alberoproc.id.codice=${alberoproc.id.codice}&codice=${arts_var.id.codice}#artipisoggetto_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ">
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
										<li><a href="javascript:doHref('createTipisoggetto.htm?alberoproc.id.codice=${alberoproc.id.codice}#artipisoggetto_anchor','') " title="<fmt:message key="label.aggiungi" />"><fmt:message key="label.aggiungi" /></a></li>
									</ul>
								</div>
							</td>
						</tr>	
						
						
						<%-- END ALBEROPROC_TIPISOGGETTO --%>									
						<tr id="ateco_anchor">
							<td colspan="4">
							<fieldset><legend><b><fmt:message key="alberoproc.label.ateco" /></b></legend>
							<div class="jmesa">
							<table border="0"  cellpadding="2" cellspacing="0" class="table">
								<thead>
									<tr class="header">
										<td width="5%"><fmt:message key="label.codice" /> </td>
										<td width="20%" ><fmt:message key="label.titolo" /></td>
<% 
if(ORMHelper.isConsoleRegionale()){
%>			    		                      		                  										
										<td width="2%" align="center"><fmt:message key="label.elimina" /></td>
<%} %>					                										
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
<% 
if(ORMHelper.isConsoleRegionale()){
%>			    		                      		                  
					                  <td>		
					                  	<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaAteco.htm?alberoproc.id.codice=${alberoproc.id.codice}&codiceateco=${ateco_var.ateco.id}#ateco_anchor','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${ateco_var.ateco.id}">
							               	 <label><fmt:message key="label.elimina.image" /></label>
							            </a>
					                  </td>
<%} %>					                  
								</tr>
								<%i++; %>
								</c:forEach>
								</tbody>
							</table>
							</div>
							</fieldset>
							</td>
						</tr>					
<% 
				
if(ORMHelper.isConsoleRegionale()){
								    
%>	
						<tr>
							<td colspan="4">
								<div id="functions" style="float:right;width: inherit;">
									<ul>
										<li><a href="javascript:historySet('${_urlback }','../alberoprocateco/assegnaAteco.htm?alberoproc.id.codice=${alberoproc.id.codice}','') " title="<fmt:message key="alberoproc.button.nuovoateco" />"><fmt:message key="alberoproc.button.nuovoateco" /></a></li>
									</ul>
								</div>
							</td>
						</tr>
<%} %>						
						</c:if>
					
					</table>
				
					<script type='text/javascript'>
					
					
					
					
					
						function mostranascondiintervento() {
							var jqrObj = jQuery('#tipo_id');
							var valore_tipo = jqrObj.val();				
							if(valore_tipo==='ENDO'){
								jQuery('#tipointerventoId').show();		
							}else{
								jQuery('#tipointerventoId').hide();
							}				
						}
					
						$('descrizione_id').focus();
											
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
					</script>
				</spring-form:form>
				<div id="">
				</div>
				
				<div dojoType="dijit.Dialog" id="parametriStpId" title="configurazioni CART" ></div>
				
				<div id="functions">
		<ul>
            <%
            if(ORMHelper.isConsoleRegionale()){
                %>				
				<c:if test="${alberoproc.id.codice==null}">
					<li><a href="javascript:doSubmit('insert.htm?codicepadre=${alberoprocPadre.id.codice}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
					<li><a href="javascript:doHref('view.htm?codice=${alberoprocPadre.id.codice}','');"><fmt:message key="button.annulla" /></a></li>
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
					<c:if test="${vert_cart_attivo}">
						<c:if test="${cart_schedaspiegazione eq true}">
							<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlCartScheda,'')"><fmt:message key="alberoproc.button.schedaspiegazione" /></a></li>
						</c:if>
					</c:if>
					<li><a href="javascript:historySet('${_urlback}','..%2Falberoproc/comuniesclusi.htm?codiceprocedimento=${alberoproc.id.codice}','')"><fmt:message key="alberoproc.button.escludicomuni" /></a></li>
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				</c:if>
            <%} %>			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
				</td>
			</tr>
		</table>
	</div>
	
</body>
</html>