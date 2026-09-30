<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<fmt:message key="label.modifica_intervento_istanza" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.modifica_intervento_istanza" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/view" />
    	<jsp:param name="qs" value="codice%3D${cambioInterventoCommand.entity.id.codice}%26software%3D${ cambioInterventoCommand.entity.software.codice }" />
	</jsp:include>	
<style>

.attuale{
	background-color: #e0e0e0;
}
.nuovo{
	background-color: #B3FFB4;
}

</style>


	<div id="subcontent">	
		<spring-form:form commandName="cambioInterventoCommand" name="inviodati" id="cambioInterventoCommandForm">
		<input type="hidden" name="codiceIstanza" value="${cambioInterventoCommand.entity.id.codice}"/>
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="cambioInterventoCommand" />
		    </jsp:include>
  
		   <fieldset style="width: 90%;min-height: 50px; border: thin dotted; font-weight: bolder;">
				<fmt:message key="label.help_modifica_intervento_istanza" />
			</fieldset>
			
			<fieldset style="width: 90%;min-height: 50px; border: thin dotted; font-weight: bolder;">
			<legend><fmt:message key="label.help_modifica_intervento_istanza.primo_passaggio" /></legend>
				<table width="89%" border="0">
									
						
							<c:choose>
								<c:when test="${empty cambioInterventoCommand.intervento.codice}">
								
			<tr id="id_progetto_table">
							<td valign="top"><label class="required">*</label> <fmt:message key="label.alberoproc" /></td>
							<td class="inline-ui-cell" colspan="5">					
									<spring-form:input id="alberoproc_hidden" path="intervento.codice" onchange="cercaProcedimento()" size="9" cssStyle="text-align: right;" />
									&nbsp;
									<%-- ALBEROPROC DOJO TREE --%>
									<a class="vbg-btn btn-cerca" href="javascript:cercaProcedimento();" id="alberoimg_id" style="vertical-align: bottom;min-width:20px;" title="Cerca procedimento">
									</a>&nbsp;
									<spring-form:input id="alberoproc_descrestesa_hidden" path="intervento.descrizione" size="100" readonly="true" />
									<spring-form:errors path="entity.alberoproc" cssClass="error" />
									
									<div id="functions">
										<ul><li><a href="javascript:void(0);" onclick="doSubmit('modificaInterventoScegliIntervento.htm','')">
										<fmt:message key="button.ok" /></a></li></ul>
									</div>
							<tr id="id_progetto_table">
							<td valign="top"></td>
							<td  colspan="5">							
									<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" 
										url="${pageContext.request.contextPath}/json/getAlberoproc.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>"> 
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
												alert("Ricerca per codice. Inserire un valore numerico");
												return;
											}
											new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=false', {
												  method: 'post',
												  parameters: {id: codiceAlberoproc},
												  onSuccess: function(transport){ 
													var response = transport.responseText;
													//alert(response);
													var json = response.evalJSON();
													if(json.id){
														if(json.padre == 'true'){
															alert("Procedimento non selezionabile.");
														}else{
															assegnaValori2(json);
															$('treeOne').style.display="none";
														}						
													}else{
														alert("Procedimento non trovato o disattivato.");
												    }
												  },
												  onFailure: function(transport){ 
													var response = transport.responseText; 
												    alert("Errore nella ricerca del procedimento!");
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
										
										var forzaNumeroPratica = false; 
										
										
										var endoSplashDiv=null;
										
										jQuery(document).ready(function(){
											 endoSplashDiv = new dijit.Dialog({
									            title: "<fmt:message key='label.endoprocedimenti' />" ,
									            style: "width: 500px"
									        });
										});
										
										function mostraDivEndo(codiceAlberoproc){
										
										}
										
										var progressivoIstanza='';
										
										function trovaProgressivoIstanza(codiceAlberoproc){
											
										}
										
										function chiudiEndoDiv(){
											endoSplashDiv.hide();
										}
										
										function assegnaValori2(map){
											$('alberoproc_hidden').value = map.id;
											$('alberoproc_descrestesa_hidden').value = map.desc;
											
										}
										
										function rimuoviValori2(){
											$('alberoproc_hidden').value = '';
											$('alberoproc_descrestesa_hidden').value = '';
											
										}
										
										function endoInSession(chkobjid, codiceinventario) {	
											
										}
										
										</script>
										
									</td>						
						</tr>	
										
								</c:when>
								<c:otherwise>
<tr id="id_progetto_table">
							<td valign="top"><label class="required">*</label> <fmt:message key="label.alberoproc" /></td>
							<td class="inline-ui-cell" colspan="5">													
								<spring-form:hidden id="alberoproc_hidden" path="intervento.codice" />
								<b>${cambioInterventoCommand.intervento.descrizione }</b>								
								<div id="functions">
									<ul><li><a href="javascript:void(0);" onclick="doSubmit('modificaIntervento.htm','')"><fmt:message key="button.reset" /></a></li></ul>
								</div>
								</td>						
						</tr>
								</c:otherwise>
								</c:choose>
								
								
							
				</table>
				
				</fieldset>
				
				<br class="clear" />
				
			<c:choose>
				<c:when test="${not empty cambioInterventoCommand.intervento.codice}">	
				
				<fieldset style="width: 90%;min-height: 50px; border: thin dotted; font-weight: bolder;">
				<legend><fmt:message key="label.help_modifica_intervento_istanza.secondo_passaggio" /></legend>
				
					<div class="jmesa">
					<table width="98%" border="0">
							<tr id="id_progetto_table">
								<td style="width: 50%" valign="top"></td>
								<td class="attuale">
								<b style="text-transform: uppercase;"><fmt:message key="label.situazione_attuale" /></b>
									<div id="functions">
										<ul><li><a href="javascript: void(0)" onclick="selezionaTuttiOld();"><fmt:message key="label.seleziona_tutto" /></a></li>
											<li><a href="javascript: void(0)" onclick="deselezionaTuttiOld();"><fmt:message key="label.deseleziona_tutto" /></a></li>
										</ul>
									</div>
								</td>
								<td  class="nuovo"><b  style="text-transform: uppercase;"><fmt:message key="label.nuovo_intervento" /></b>									
									<div id="functions">
										<ul>
											<li><a href="javascript: void(0)" onclick="selezionaTuttiNew();"><fmt:message key="label.seleziona_tutto" /></a></li>
											<li><a href="javascript: void(0)" onclick="deselezionaTuttiNew();"><fmt:message key="label.deseleziona_tutto" /></a></li>
										</ul>
									</div>
								</td>
							</tr>
							
							<%-- INIZIO DOCUMENTI --%>
							<tr id="id_progetto_table" class="titoloSezione">
								<td colspan="3"><fmt:message key="documentiistanza.label.lista_documentiistanza.title" /></td>
							</tr>
							<c:forEach items="${cambioInterventoCommand.docs}" var="current" varStatus="a">
								<tr id="id_progetto_table" class="<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>" onmouseover="this.className='highlight'"  onmouseout="this.className='<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>'">
									<td>${current.descrizione}</td>
									<td class="attuale">
									<c:if test="${current.attuale.presente eq true}">
										<c:if test="${current.attuale.readonly eq false}">
											<spring:bind path="docs[${a.index}].attuale.checked">							
												<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
												<input id="doc_attuale_${a.index}" type="checkbox" class="attualesel"
													name="<c:out value="${status.expression}"/>" 
													value="true" 
													<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> 
													/>
											</spring:bind>
										</c:if>
										<c:if test="${current.attuale.readonly eq true}">
											<input id="doc_attuale_${a.index}" type="checkbox" name="doc_null_${a.index}" 
												disabled="disabled"
												<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> />
										</c:if>																
									</c:if>									
									</td>
									<td class="nuovo">
										<c:if test="${current.nuovo.presente eq true}">
											<c:if test="${current.nuovo.readonly eq false}">
												<spring:bind path="docs[${a.index}].nuovo.checked">							
													<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
													<input id="doc_nuovo_${a.index}" type="checkbox"  class="nuovosel"
														name="<c:out value="${status.expression}"/>" 
														value="true" 
														<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> 
														/>
												</spring:bind>
											</c:if>
											<c:if test="${current.nuovo.readonly eq true}">
												<input id="doc_nuovo_${a.index}" type="checkbox" name="doc_null_${a.index}" 
													disabled="disabled"
													<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> />
											</c:if>																
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<%-- FINE DOCUMENTI --%>
							
							
							<%-- INIZIO ENDOPROCEDIMENTI --%>
							<tr id="id_progetto_table" class="titoloSezione">
								<td colspan="3"><fmt:message key="label.cambio_intervento.endoprocedimenti"/></td>
							</tr>
							<c:forEach items="${cambioInterventoCommand.endos}" var="current" varStatus="a">
								<tr id="id_progetto_table" class="<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>" onmouseover="this.className='highlight'"  onmouseout="this.className='<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>'">
									<td>${current.descrizione}</td>
									<td class="attuale">
									<c:if test="${current.attuale.presente eq true}">
										<c:if test="${current.attuale.readonly eq false}">
											<spring:bind path="endos[${a.index}].attuale.checked">							
												<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
												<input id="endo_attuale_${a.index}" type="checkbox"  class="attualesel"
													name="<c:out value="${status.expression}"/>" 
													value="true" 
													<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> 
													/>
											</spring:bind>
										</c:if>
										<c:if test="${current.attuale.readonly eq true}">
											<input id="endo_attuale_${a.index}" type="checkbox" name="endo_null_${a.index}" 
												disabled="disabled"
												<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> />
										</c:if>																
									</c:if>									
									</td>
									<td class="nuovo">
										<c:if test="${current.nuovo.presente eq true}">
											<c:if test="${current.nuovo.readonly eq false}">
												<spring:bind path="endos[${a.index}].nuovo.checked">							
													<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
													<input id="endo_nuovo_${a.index}" type="checkbox"  class="nuovosel"
														name="<c:out value="${status.expression}"/>" 
														value="true" 
														<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> 
														/>
												</spring:bind>
											</c:if>
											<c:if test="${current.nuovo.readonly eq true}">
												<input id="endo_nuovo_${a.index}" type="checkbox" name="endo_null_${a.index}" 
													disabled="disabled"
													<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> />
											</c:if>																
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<%-- FINE ENDOPROCEDIMENTI --%>

							<%-- INIZIO SCHEDE --%>
							<tr id="id_progetto_table" class="titoloSezione">
								<td colspan="3"><fmt:message key="label.schede_dell_istanza" /></td>
							</tr>
							<c:forEach items="${cambioInterventoCommand.schedes}" var="current" varStatus="a">
								<tr id="id_progetto_table" class="<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>" onmouseover="this.className='highlight'"  onmouseout="this.className='<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>'">
									<td>${current.descrizione}</td>
									<td class="attuale">
									<c:if test="${current.attuale.presente eq true}">
										<c:if test="${current.attuale.readonly eq false}">
											<spring:bind path="schedes[${a.index}].attuale.checked">							
												<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
												<input id="scheda_attuale_${a.index}" type="checkbox"  class="attualesel"
													name="<c:out value="${status.expression}"/>" 
													value="true" 
													<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> 
													/>
											</spring:bind>
										</c:if>
										<c:if test="${current.attuale.readonly eq true}">
											<input id="scheda_attuale_${a.index}" type="checkbox" name="scheda_null_${a.index}" 
												disabled="disabled"
												<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> />
										</c:if>																
									</c:if>									
									</td>
									<td class="nuovo">
										<c:if test="${current.nuovo.presente eq true}">
											<c:if test="${current.nuovo.readonly eq false}">
												<spring:bind path="schedes[${a.index}].nuovo.checked">							
													<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
													<input id="scheda_nuovo_${a.index}" type="checkbox"  class="nuovosel"
														name="<c:out value="${status.expression}"/>" 
														value="true" 
														<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> 
														/>
												</spring:bind>
											</c:if>
											<c:if test="${current.nuovo.readonly eq true}">
												<input id="scheda_nuovo_${a.index}" type="checkbox" name="scheda_null_${a.index}" 
													disabled="disabled"
													<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> />
											</c:if>																
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<%-- FINE SCHEDE --%>
							
							<%-- INIZIO RUOLI --%>
							<tr id="id_progetto_table" class="titoloSezione">
								<td colspan="3"><fmt:message key="label.ruoli_con_accesso_istanza"/></td>
							</tr>
							<c:forEach items="${cambioInterventoCommand.ruolis}" var="current" varStatus="a">
								<tr id="id_progetto_table" class="<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>" onmouseover="this.className='highlight'"  onmouseout="this.className='<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>'">
									<td>${current.descrizione}</td>
									<td class="attuale">
									<c:if test="${current.attuale.presente eq true}">
										<c:if test="${current.attuale.readonly eq false}">
											<spring:bind path="ruolis[${a.index}].attuale.checked">							
												<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
												<input id="ruolo_attuale_${a.index}" type="checkbox"  class="attualesel"
													name="<c:out value="${status.expression}"/>" 
													value="true" 
													<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> 
													/>
											</spring:bind>
										</c:if>
										<c:if test="${current.attuale.readonly eq true}">
											<input id="ruolo_attuale_${a.index}" type="checkbox" name="ruolo_null_${a.index}" 
												disabled="disabled"
												<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> />
										</c:if>																
									</c:if>									
									</td>
									<td class="nuovo">
										<c:if test="${current.nuovo.presente eq true}">
											<c:if test="${current.nuovo.readonly eq false}">
												<spring:bind path="ruolis[${a.index}].nuovo.checked">							
													<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
													<input id="ruolo_nuovo_${a.index}" type="checkbox"  class="nuovosel"
														name="<c:out value="${status.expression}"/>" 
														value="true" 
														<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> 
														/>
												</spring:bind>
											</c:if>
											<c:if test="${current.nuovo.readonly eq true}">
												<input id="ruolo_nuovo_${a.index}" type="checkbox" name="ruolo_null_${a.index}" 
													disabled="disabled"
													<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> />
											</c:if>																
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<%-- FINE RUOLI --%>
							
							<%-- INIZIO PERMESSI --%>
							<tr id="id_progetto_table" class="titoloSezione">
								<td colspan="3"><fmt:message key="label.operatori_accesso_istanza"/></td>
							</tr>
							<c:forEach items="${cambioInterventoCommand.permessis}" var="current" varStatus="a">
								<tr id="id_progetto_table" class="<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>" onmouseover="this.className='highlight'"  onmouseout="this.className='<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>'">
									<td>${current.descrizione}</td>
									<td class="attuale">
									<c:if test="${current.attuale.presente eq true}">
										<c:if test="${current.attuale.readonly eq false}">
											<spring:bind path="permessis[${a.index}].attuale.checked">							
												<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
												<input id="permesso_attuale_${a.index}" type="checkbox"  class="attualesel"
													name="<c:out value="${status.expression}"/>" 
													value="true" 
													<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> 
													/>
											</spring:bind>
										</c:if>
										<c:if test="${current.attuale.readonly eq true}">
											<input id="permesso_attuale_${a.index}" type="checkbox" name="permesso_null_${a.index}" 
												disabled="disabled"
												<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> />
										</c:if>																
									</c:if>									
									</td>
									<td class="nuovo">
										<c:if test="${current.nuovo.presente eq true}">
											<c:if test="${current.nuovo.readonly eq false}">
												<spring:bind path="permessis[${a.index}].nuovo.checked">							
													<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
													<input id="permesso_nuovo_${a.index}" type="checkbox"  class="nuovosel"
														name="<c:out value="${status.expression}"/>" 
														value="true" 
														<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> 
														/>
												</spring:bind>
											</c:if>
											<c:if test="${current.nuovo.readonly eq true}">
												<input id="permesso_nuovo_${a.index}" type="checkbox" name="permesso_null_${a.index}" 
													disabled="disabled"
													<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> />
											</c:if>																
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<%-- FINE PERMESSI --%>
							<%-- INIZIO AZIONE --%>
							<tr id="id_progetto_table" class="titoloSezione">
								<td colspan="3"><fmt:message key="label.azione" /></td>
							</tr>
							<tr id="id_progetto_table"  class="odd" onmouseover="this.className='highlight'"  onmouseout="this.className='odd'">
								<td>&nbsp;</td>
								<td class="attuale">
										<input id="azioneOld" type="checkbox" disabled="disabled" /> <label for="azioneOld">${cambioInterventoCommand.azioneOld.descrizione}</label>
								</td>
								<td class="nuovo">
										<b><fmt:message key="label.cambio_intervento.nuova_azione" /></b>
								<%--
								
									
									<input id="azioneNew" type="checkbox" disabled="disabled" checked="checked"/> <label for="azioneNew">${cambioInterventoCommand.azioneNew.descrizione}</label>
									 --%>
								</td>
							</tr>
							<%-- FINE AZIONE --%>
							<%-- PROCEDURA --%>
							<tr id="id_progetto_table"  class="titoloSezione">
								<td colspan="3"><fmt:message key="label.procedura_e_mov_avvio" /></td>
							</tr>
							<tr id="id_progetto_table"  class="odd" onmouseover="this.className='highlight'"  onmouseout="this.className='odd'">
								<td><fmt:message key="label.procedura" /></td>
								<td class="attuale">
										<input id="proceduraOld" type="checkbox" disabled="disabled" /> 
											<label for="proceduraOld">${cambioInterventoCommand.proceduraOld.descrizione}</label>
								</td>
								<td class="nuovo">
									<input id="proceduraNew" type="checkbox" disabled="disabled" checked="checked"/> 
										<label for="proceduraNew">${cambioInterventoCommand.proceduraNew.descrizione}</label>
								</td>
							</tr>
							<tr id="id_progetto_table"  class="even" onmouseover="this.className='highlight'"  onmouseout="this.className='even'">
								<td><fmt:message key="label.movimento_avvio" /></td>
								<td class="attuale">
										<input id="movAvvioOld" type="checkbox" disabled="disabled" /> 
											<label for="movAvvioOld">${cambioInterventoCommand.movAvvioOld.descrizione}</label>
								</td>
								<td class="nuovo">
									<input id="movAvvioNew" type="checkbox" disabled="disabled" checked="checked"/> 
										<label for="movAvvioNew">${cambioInterventoCommand.movAvvioNew.descrizione}</label>
								</td>
							</tr>
							<%-- FINE PROCEDURA --%>
							
							
							<tr id="id_progetto_table">
								<td style="text-align: right; text-transform: uppercase;"><fmt:message key="label.usa_configurazioni" /></td>
								<td>								
									<div id="functions">
										<ul><li><a href="javascript: void(0)" onclick="usaConfigurazioniOld();"><fmt:message key="label.attuali" /></a></li>
										</ul>
									</div>
								</td>
								<td>					
									<div id="functions">
										<ul><li><a href="javascript: void(0)" onclick="usaConfigurazioniNew();"><fmt:message key="label.nuove" /></a></li>
										</ul>
									</div>
								</td>
							</tr>
					</table>							
					</div>	
					</fieldset>
					</c:when>
				</c:choose>
				
			</spring-form:form>	
			
	</div>
	
	<div id="functions">
		<c:if test="${not empty cambioInterventoCommand.intervento.codice }">
			<ul><li><a href="javascript:void(0);" onclick="doSubmit('updateModificaInterventoIstanza.htm','<fmt:message key="javascript.confirm.procedere_con_l_operazione" />')"><fmt:message key="button.save" /></a></li></ul>
		</c:if>
		<ul><li><a href="javascript:historyBack();"><fmt:message key="label.chiudi" /></a></li></ul>
	</div>
	

	<script type="text/javascript">
	function ajaxHistorySet(url){
		new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
			  method: 'get',
			  parameters: {ReturnTo: url, limit: 12},
			  onSuccess: function(transport){},
			  onFailure: function(){}			  
		});
	}
	
	function selezionaTuttiOld(){
		selezionaTutti('attuale',true);
	}
	function selezionaTuttiNew(){
		selezionaTutti('nuovo',true);
	}
	function deselezionaTuttiOld(){
		selezionaTutti('attuale',false);
	}
	function deselezionaTuttiNew(){
		selezionaTutti('nuovo',false);
	}
	function selezionaTutti(quale, isChecked){
		jQuery( "."+quale+"sel" ).attr('checked', isChecked);
	}
	
	
	function usaConfigurazioniOld(){
		selezionaTuttiOld();
		deselezionaTuttiNew();
		
	}
	function usaConfigurazioniNew(){		
		selezionaTuttiNew();
		deselezionaTuttiOld();
	}
	
	</script>	
</body>
</html>