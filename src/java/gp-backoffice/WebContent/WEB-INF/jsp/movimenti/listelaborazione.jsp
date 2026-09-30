<%@page import="java.util.Date"%>
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="java.net.URLEncoder" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.movimenti_istanza" /></title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-appio/vbg-io-movimenti.js?<%=vJS %>" defer></script>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-appio/vbg-io-comunicazione.js?<%=vJS %>" defer></script>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.movimenti_istanza"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../movimenti/listElaborazione" />
	    	<jsp:param name="qs" value="codiceIstanza%3D${istanza.id.codice}" />
		</jsp:include>
		
					<c:import url="/ajax/dettaglioIstanza.htm">
						<c:param name="codIstanza">${istanza.id.codice}</c:param>
					</c:import>
				
		
		<br class="clear" />
			<c:set var="VERTICALIZZAZIONE_STC_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_STC)%></c:set>
			<c:set var="VERTICALIZZAZIONE_INFOCAMERA_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_INFOCAMERA)%></c:set>
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="movimentiCommand" />
		    </jsp:include>
		<div id="subcontent">
		<%--
		
			<div id="functions">
				<%
					String infoChecked = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ATTMSGELABORAZIONE)).equals("1")) {
					    infoChecked = "checked='checked'";	
					} 
					pageContext.setAttribute("infoChecked", infoChecked);
				%>		
				<c:set var="_titleVisInformazioni"><fmt:message key="label.elaborazione_mostra_info_help" /></c:set>
		    	<c:set var="_CONF_UTENTE_ATTMSGELABORAZIONE"><%= WebConstants.CONF_UTENTE_ATTMSGELABORAZIONE%></c:set>
		    	<input title="${_titleVisInformazioni}" ${infoChecked} type="checkbox" id="CONF_UTENTE_ATTMSGELABORAZIONE" onclick="visualizzaInfo(this,'${_CONF_UTENTE_ATTMSGELABORAZIONE}');"/>
		    	<label for="CONF_UTENTE_ATTMSGELABORAZIONE" title="${_titleVisInformazioni}"><fmt:message key="label.informazioni_aggiuntive" /></label>			
			</div>		
		--%>	
		<c:set var="_colspan">7</c:set>
		
	    <div id="infoAggiuntive" class="vbg-form">
	    
		    <fieldset><legend><a title="<fmt:message key="button.gestione_allegati" />" 
		    				href="javascript:historySet('${_urlback}','../documentiistanza/list.htm?codiceIstanza=${istanza.id.codice}','')"><fmt:message key="label.situazione_allegati_endo_and_istanza"/></a></legend>
			
				<table class="vbg-table">
					<thead>
						<tr>			
							<th><fmt:message key="label.endoprocedimenti_numero_allegati_richiesti"/>: <font class="red">${allegatiRichiesti}</font></th>
							<th><fmt:message key="label.endoprocedimenti_numero_allegati_presentati"/>: <font class="red">${allegatiPresentati}</font></th>
							<th><fmt:message key="label.endoprocedimenti_numero_allegati_non_validi"/>: <font class="red">${allegatiNonValidi}</font></th>
							<th><fmt:message key="label.endoprocedimenti_numero_allegati_validi"/>: <font class="red">${allegatiValidi}</font></th>
						</tr>
					</thead>	
				</table>
			</div>
			
		
		<div class="vbg-form">
				<fieldset>
					<legend><fmt:message key="label.movimenti_effettuati"/></legend>
					<table border="0" class="vbg-table">
						<thead>
							
							<tr >
								<th width="5%"><fmt:message key="label.presentazione"/></th>
								<th width="5%"><fmt:message key="label.scadenza"/></th>
								<th width="15%"><fmt:message key="label.amministrazione"/></th>
								<th width="15%"><fmt:message key="label.endoprocedimento"/></th>
								<th colspan="3"><fmt:message key="label.movimento"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${movimentiEseguitiList}" var="movimentoEseguito" varStatus="movimentiStatus">							
								<tr>
									<td><fmt:formatDate value="${movimentoEseguito.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
									<td><fmt:formatDate value="${movimentoEseguito.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
									<td>${movimentoEseguito.amministrazioni.amministrazione}</td>
									<td>${movimentoEseguito.endoprocedimento.procedimento}</td>
									<td>
										<c:set var="classMovimento" value="movimentoEffettuato"/>
										<c:set var="linkMovimento">javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${movimentoEseguito.id.codice}','')</c:set>								
										<c:if test="${movimentoEseguito.transientAccessoNegato eq true}">
											<c:set var="classMovimento" value="movimentoReadOnly"/>
											<c:set var="linkMovimento">javascript:showDettaglioMovimentoDisabilitato('dettaglioMovDisabilitato${movimentoEseguito.id.codice}',${movimentoEseguito.id.codice})</c:set>
										</c:if>
										<a class="${classMovimento}" href="${linkMovimento}" title="<fmt:message key="label.edit.record" />&#13;&#10;[<c:out value="${movimentoEseguito.tipomovimento.id.tipomovimento}" />] - <c:out value="${movimentoEseguito.movimento}" />&#13;&#10;${movimentoEseguito.responsabile.responsabile} (<fmt:formatDate value="${movimentoEseguito.datainserimento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)">
											<c:if test="${not empty movimentoEseguito.movimento}">
												${movimentoEseguito.movimento}
											</c:if>
											<c:if test="${empty movimentoEseguito.movimento}">
												${movimentoEseguito.tipomovimento.movimento}
											</c:if>
										</a>
										<div dojoType="dijit.Dialog" id="dettaglioMovDisabilitato${movimentoEseguito.id.codice}" 
											title="<fmt:message key="label.dettaglio_movimento" />: [<c:out value="${movimentoEseguito.tipomovimento.id.tipomovimento}" />] - <c:out value="${movimentoEseguito.movimento}" />">
											<div id="dettaglioMovDisabilitato${movimentoEseguito.id.codice}_inner"></div>
										</div>																
									</td>
									<%
										String displayInfo="";
										if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ATTMSGELABORAZIONE)).equals("1")) {										
										    displayInfo="";
										}									
										pageContext.setAttribute("displayInfo", displayInfo);
									%>	
									<td>	
										<div id="infoAggiuntive" style="${displayInfo}">
											<jsp:include page="../includes/funzioni_movimenti.jsp">
												<jsp:param name="codiceMovimentoId" value="${movimentoEseguito.id.codice}" />
												<jsp:param name="funzioneRichiesta" value="visualizzamovimentiPadre" />
												<jsp:param name="mostraAbilitato" value="${fn:length(movimentoEseguito.movimentiContromovimentisForFkFiglio)}" />
											</jsp:include>&nbsp;
											<jsp:include page="../includes/funzioni_movimenti.jsp">
												<jsp:param name="codiceMovimentoId" value="${movimentoEseguito.id.codice}" />
												<jsp:param name="funzioneRichiesta" value="visualizzamovimentiFiglio" />
												<jsp:param name="mostraAbilitato" value="${fn:length(movimentoEseguito.movimentiContromovimentisForFkPadre)}" />
											</jsp:include>
										</div>
									</td>
									<td width="15%">
										<div id="infoAggiuntive" style="${displayInfo}">
											<span class="movimenti_allegati_div" data-codicemovimento="${movimentoEseguito.id.codice}"></span>
											<span class="movimenti_info_attivita_div" data-codicemovimento="${movimentoEseguito.id.codice}"></span>
										<c:if test="${movimentoEseguito.tipomovimento.tipologiaesito ne 0}">
												<c:if test="${movimentoEseguito.esito eq false}">
													<span class="vbg-btn btn-error" title="<fmt:message key="label.esito_negativo"/>"></span>
												</c:if>
											</c:if>
											<c:if test="${not empty movimentoEseguito.note}">
												<label title="<fmt:message key="label.note"/>" class="elab_image elab_image_text" onclick="dijit.byId('noteMovimento_${movimentoEseguito.id.codice}').show();"><fmt:message key="label.N" /></label>
												<div id="noteMovimento_${movimentoEseguito.id.codice}" dojoType="dijit.Dialog" title="<fmt:message key="label.note"/> - ${movimentoEseguito.tipomovimento.descrizioneEstesa}" style="display: none;">
											    	<div>
											    		<pre>${movimentoEseguito.note}</pre>
											    	</div>
											    </div>
											</c:if>									
											<c:if test="${not empty movimentoEseguito.parere}">
												<label title="<fmt:message key="label.parere"/>" class="elab_image elab_image_text" onclick="dijit.byId('parereMovimento_${movimentoEseguito.id.codice}').show();"><fmt:message key="label.P" /></label>
												<div id="parereMovimento_${movimentoEseguito.id.codice}" dojoType="dijit.Dialog" title="<fmt:message key="label.parere"/> - ${movimentoEseguito.tipomovimento.descrizioneEstesa}" style="display: none;">
											    	<div>
											    		<pre>${movimentoEseguito.parere}</pre>
											    	</div>
											    </div>
											</c:if>
											<c:if test="${not empty movimentoEseguito.autorizzazioni}">
												<c:set var="titleAutorizzazioni"><fmt:message key="label.autorizzazioni"/>:</c:set>
												<c:forEach items="${movimentoEseguito.autorizzazioni}" var="autorizzazione">
													<c:set var="titleAutorizzazioni">${titleAutorizzazioni}&#13;&#10;${autorizzazione.transientEstremiAut};</c:set>
												</c:forEach>
												<label class="elab_image elab_image_text" title="${titleAutorizzazioni}"><fmt:message key="label.A" /></label>
											</c:if>											
											<c:if test="${not empty movimentoEseguito.numeroprotocollo}">
												<label class="elab_image elab_image_text" title="<fmt:message key="label.numero_protocollo" />:&#13;&#10;${movimentoEseguito.numeroprotocollo}&nbsp;<fmt:message key="label.del" />&nbsp;<fmt:formatDate value="${movimentoEseguito.dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"><fmt:message key="label.pr" /></label>
											</c:if>
											<c:if test="${VERTICALIZZAZIONE_STC_IN_REQUEST eq true}">
												<jsp:include page="../includes/funzioni_stc.jsp">
													<jsp:param name="codiceIstanza" value="${movimentoEseguito.istanza.id.codice}" />
													<jsp:param name="codiceMovimento" value="${movimentoEseguito.id.codice}" />
													<jsp:param name="funzioneRichiesta" value="richiestaPraticaMovimento" />
													<jsp:param name="returnTo" value="${_urlback}" />
													<jsp:param name="flagStc" value="${movimentoEseguito.tipomovimento.flagStc}" />
													<jsp:param name="inviatoConStc" value="${movimentoEseguito.inviatoConStc}" />
													<jsp:param name="creatoDaStc" value="${movimentoEseguito.creatoDaStc}" />
													<jsp:param name="idAttDest" value="${movimentoEseguito.idAttDest}" />
													<jsp:param name="statoAttDest" value="${movimentoEseguito.statoAttDest}" />										
												</jsp:include>
											</c:if>
											<c:if test="${VERTICALIZZAZIONE_INFOCAMERA_IN_REQUEST eq true}">
												<jsp:include page="../includes/funzioni_infocamere.jsp">
													<jsp:param name="codiceMovimento" value="${movimentoEseguito.id.codice}" />
													<jsp:param name="flagCamcom" value="${movimentoEseguito.tipomovimento.flagCamcom}" />
													<jsp:param name="inviatoACamcom" value="${movimentoEseguito.inviatoACamcom}" />
												</jsp:include>
											</c:if>
											<c:if test="${not empty movimentoEseguito.movimentiAttis}">
												<c:set scope="page" value="false" var="isRicevuto"></c:set>
											    <c:forEach items="${movimentoEseguito.movimentiAttis}" var="movAtti">										    
											        <c:if test="${movAtti.dataRicezioneAtto ne null}">
											         	<c:set scope="page" value="true" var="isRicevuto"></c:set>
											        </c:if>
					    					    </c:forEach>
												<c:if test="${isRicevuto eq 'false'}">							
													<a class="vbg-btn btn-a" href="javascript:void(0);" title="<fmt:message key="movimentimail.label.ricerca_atto.title" />"
															onclick="ricercaAtto('${movimentoEseguito.id.codice}')">
													</a>
													<div dojoType="dijit.Dialog" id="ricercaAttoDiv${movimentoEseguito.id.codice}" title="<fmt:message key="movimentimail.label.ricerca_atto.title" />" style="display: none;"></div>
												</c:if>
												<c:if test="${isRicevuto eq 'true'}">
													<a class="vbg-btn btn-a-verde" href="javascript:void(0);" title="<fmt:message key="movimentimail.label.ricerca_atto.title" />"
															onclick="ricercaAtto('${movimentoEseguito.id.codice}')">
													</a>
													<div dojoType="dijit.Dialog" id="ricercaAttoDiv${movimentoEseguito.id.codice}" title="<fmt:message key="movimentimail.label.ricerca_atto.title" />" style="display: none;"></div>
												</c:if>
											</c:if>
											<c:if test="${not empty movimentoEseguito.movimentimails}">
												<a class="vbg-btn btn-email" href="javascript:historySet('${_urlback }','../movimentimail/list.htm?codicemovimento=${movimentoEseguito.id.codice}&codiceistanza=${movimentoEseguito.istanza.id.codice}','')" title="<fmt:message key="movimentimail.label.lista_movimentimail.title"/> - ${movimentoEseguito.tipomovimento.descrizioneEstesa}" >
												</a>
											</c:if>
											<c:if test="${movimentoEseguito.numeroEventiNonLetti>0}">
												<a class="vbg-btn btn-eventi-nonletti" href="javascript:tabVisualizzaEventiNonLetti('eventi_moviment_allegati_id_${movimentoEseguito.id.codice}','${movimentoEseguito.id.codice}')" title="${movimentoEseguito.numeroEventiNonLetti} <fmt:message key="label.eventi_movimento_non_letti"/>">
												</a>									
						 						<div dojoType="dijit.Dialog" id="eventi_moviment_allegati_id_${movimentoEseguito.id.codice}" title="<fmt:message key="label.eventi_movimento_non_letti" />: ${movimentoEseguito.movimento} [${movimentoEseguito.tipomovimento.id.tipomovimento}]">
													<div dojoType="dijit.layout.ContentPane" class="generic_dialog">
														<div id="lista_eventi_non_letti${movimentoEseguito.id.codice}"></div> 
													</div>			
											   </div> 
											</c:if>
											<c:if test="${movimentoEseguito.tipomovimento.flagAccediSchede eq 'true'}">
												<%							
													pageContext.setAttribute("URL_ISTANZEDYN2_MODELLI", BackofficeNETConstants.getURL_ISTANZE_DYN2_MODELLI());
												%>
												<c:set var="_URL_BACK" value="../movimenti/listElaborazione.htm?codiceIstanza=${movimentoEseguito.istanza.id.codice}"/>
												<c:set var="_URL_ISTANZEDYN2_MODELLI" value="${URL_ISTANZEDYN2_MODELLI}?CodiceIstanza=${movimentoEseguito.istanza.id.codice}&CodiceMovimento=${movimentoEseguito.id.codice}"/>			
												<c:set var="_URL_ISTANZEDYN2_MODELLI" value="${inite:linkschedemovimento(pageContext.request, _URL_BACK, null, false, movimentoEseguito.istanza.id.codice,movimentoEseguito.id.codice)}" />
												<a class="elab_image elab_image_text" href="${_URL_ISTANZEDYN2_MODELLI}" title="<fmt:message key="label.schede_del_movimento" />"><fmt:message key="label.s" /></a>
											</c:if>	
											<c:if test="${movimentoEseguito.isCodaIo }">
												<vbg-io-movimenti id-movimento="${ movimentoEseguito.id.codice}" descrizione="${ movimentoEseguito.movimento}" data="${ movimentoEseguito.datainserimento}" ref-protocolo="${ movimentoEseguito.descProtocollo}">
												</vbg-io-movimenti>
											</c:if>									
										</div>
										
									</td>
									
									
															
								</tr>
							</c:forEach>
						</tbody>		
						
						</table>
					</fieldset>
					
					
					
					<fieldset>
						<legend><fmt:message key="label.movimenti_non_effettuati"/></legend>
					
							<table class="vbg-table">											
							
							<thead>						
								<tr>
									<th><fmt:message key="label.presentazione"/></th>
									<th><fmt:message key="label.scadenza"/></th>
									<th><fmt:message key="label.amministrazione"/></th>
									<th><fmt:message key="label.endoprocedimento"/></th>
									<th colspan="3"><fmt:message key="label.movimento"/></th>
								</tr>
							</thead>
							<tbody >
								<c:forEach items="${movimentiDaEseguireList}" var="movimento" varStatus="movimentiStatus">												
									<tr>
										<td><fmt:formatDate value="${movimento.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
										<td>
											<fmt:formatDate value="${movimento.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
										</td>
										<td>${movimento.amministrazioni.amministrazione}</td>
										<td>${movimento.endoprocedimento.procedimento}</td>
										<td colspan="2">
												<div dojoType="dijit.Dialog" id="dettaglioMovDisabilitato${movimento.id.codice}" 
													title="<fmt:message key="label.dettaglio_movimento" />: [<c:out value="${movimentoEseguito.tipomovimento.id.tipomovimento}" />] - <c:out value="${movimentoEseguito.movimento}" />">">
													<div id="dettaglioMovDisabilitato${movimento.id.codice}_inner"></div>
												</div>
												<c:set var="classMovimento" value="movimentoDaEffettuare"/>
												<c:set var="linkMovimento">javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${movimento.id.codice}','')</c:set>								
												<div dojoType="dijit.Dialog" id="dettaglioEsito${movimento.id.codice}" 
													title="<fmt:message key="label.seleziona_esito" />">
													<div id="dettaglioEsito${movimento.id.codice}_inner">
														<div id="seleziona_esito_movimento_${movimento.id.codice}">			
															<fmt:message key="movimenti.label.seleziona_esito_movimento">
																<fmt:param>${movimento.tipomovimento.id.tipomovimento}</fmt:param>
																<fmt:param>${movimento.tipomovimento.movimento}</fmt:param>
															</fmt:message><br></br>																								
														</div>
														<div id="functions">
															<ul>
																<li>
																	<a href="../movimenti/view.htm?codice=${movimento.id.codice}&esito=1">Esito positivo</a>
																</li>
																<li>
																	<a href="../movimenti/view.htm?codice=${movimento.id.codice}&esito=0">Esito negativo</a>
																</li>
															</ul>
														</div>
													</div>
												</div>
												<c:if test="${movimento.transientAccessoNegato eq true}">
													<c:set var="classMovimento" value="movimentoReadOnly"/>
													<c:set var="linkMovimento">javascript:showDettaglioMovimentoDisabilitato('dettaglioMovDisabilitato${movimento.id.codice}',${movimento.id.codice})</c:set>
												</c:if>
												<c:set var="descrizioneMovimento" value="${movimento.movimento}"/>
												<c:if test="${empty movimento.movimento}">
													<c:set var="descrizioneMovimento" value="${movimento.tipomovimento.movimento}"/>
												</c:if>										
												<c:choose>
												<c:when test="${movimento.tipomovimento.flagAccediSchede eq 'true'}">
													<c:choose>
													<c:when test="${not empty movimento.tipomovimento.tipimovimentidyn2modellits}">
														<c:choose>
														<c:when test="${movimento.tipomovimento.tipologiaesito gt 0}">
															<a class="${classMovimento}" href="javascript:showDettaglioEsito('dettaglioEsito${movimento.id.codice}',${movimento.id.codice})" title="<fmt:message key="label.edit.record" />&#13;&#10;[${movimento.tipomovimento.id.tipomovimento}] - ${descrizioneMovimento}">
																 ${descrizioneMovimento}
															</a>
														</c:when>		
														<c:otherwise>												
															<a class="${classMovimento}" href="../movimenti/view.htm?codice=${movimento.id.codice}" title="<fmt:message key="label.edit.record" />&#13;&#10;[${movimento.tipomovimento.id.tipomovimento}] - ${descrizioneMovimento}">
															 	${descrizioneMovimento}
															</a>	
														</c:otherwise>
														</c:choose>								
													</c:when>
													<c:otherwise>
														<a class="${classMovimento}" href="javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${movimento.id.codice}','')" title="<fmt:message key="label.edit.record" />&#13;&#10;[${movimento.tipomovimento.id.tipomovimento}] - ${descrizioneMovimento}">
														 	${descrizioneMovimento}
														</a>
													</c:otherwise>	
													</c:choose>																															
												</c:when>
												<c:otherwise>
													<a class="${classMovimento}" href="javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${movimento.id.codice}','')" title="<fmt:message key="label.edit.record" />&#13;&#10;[${movimento.tipomovimento.id.tipomovimento}] - ${descrizioneMovimento}">
														 	${descrizioneMovimento}
													</a>
												</c:otherwise>
												</c:choose>
										</td>
										<td>
											<c:if test="${movimento.flagCmovObblig eq false or empty movimento.flagCmovObblig}">
												<c:if test="${movimento.transientAccessoNegato eq false}">
													<a class="vbg-btn btn-elimina" href="javascript:disabilitaMovimento(${movimento.id.codice})" title="<fmt:message key="label.elimina" /> ${descrizioneMovimento}" >
										            </a>
										        </c:if>    
									        </c:if>
									        <span class="movimenti_info_attivita_div" data-codicemovimento="${movimento.id.codice}"></span>
									        <c:if test="${movimento.tipomovimento.flagSostDocumentale gt 0}">
									        	<span class="vbg-btn vbg-btn-disabled btn-info" title="Il movimento permette le sostituzioni documentali" ></span>
											</c:if>   
										</td>																			
									</tr>
								</c:forEach>
							</tbody>													
						</table>
				</fieldset>
			</div>				
		</div>
		<script type="text/javascript">
		
			function disabilitaMovimento(codiceMovimento){				
				doHref('disabilitaMovimento.htm?codice='+codiceMovimento,'<fmt:message key="javascript.confirm.disabilita_movimento" />');				
			}
		
			function visualizzaInfo(obj ,nomeParametro){
				elsId = "infoAggiuntive";
				var visible = showHideElements(elsId, 'span');
				var valore = obj.checked==true?'1':'0';
				if(obj.checked){
					if(!visible){
						visible = showHideElements(elsId);
					}
				}
				saveUserPreference(nomeParametro, valore);
			}
		
			function riattivaMovimenti(){
				var movimentiDisabilitatiDlg = new dijit.Dialog({
		            title: "<fmt:message key="button.riattiva" />" ,
		            style: "overflow:auto; width: 600px;height: 300px;"
 		        });
				
				new Ajax.Request('<%=request.getContextPath()%>/movimenti/ajaxListaTipimovimentoDisabilitati.htm', {
					  method: 'post',
					  parameters: {codiceIstanza: ${istanza.id.codice}},
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  result = parseAjaxResponse(response, true, false);
						  movimentiDisabilitatiDlg.attr("content", result);
						  movimentiDisabilitatiDlg.show();							  
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
						movimentiDisabilitatiDlg.attr("content", response);
						movimentiDisabilitatiDlg.show();	
					  }
				});		
				
			}			
			function riattiva(objForm){
				doSubmit('riattiva.htm','',objForm);
			}
			
			function showDettaglioMovimentoDisabilitato(divId,codiceMov){
				var jqxhr = jQuery.ajax({
					  url: "ajaxDettaglioMovimentoDisabilitato.htm",
					  context: document.body,
					  cache: false,				
					  dataType: "html",
					  data: "codice="+codiceMov,
					  success: function(dataResult) { 
						   $(divId+"_inner").innerHTML = dataResult;
						   dijit.byId(divId).show();
						   applyStyle();
						},
					  error: function(dataError){						  
						  $(divId+"_inner").innerHTML = dataResult;
						   dijit.byId(divId).show();
						   applyStyle();
					  }	
					});		
				
			}
			function mostraEmail(divId,codiceMov){
				var jqxhr = jQuery.ajax({
					  url: "../movimentimail/ajaxMostraEmail.htm",
					  context: document.body,
					  cache: false,				
					  dataType: "html",
					  data: "codicemovimento="+codiceMov+"&codiceistanza=",
					  success: function(dataResult) { 
						   $(divId + "_inner").innerHTML = dataResult;
						   dijit.byId(divId).show();
						   applyStyle();
						},
					  error: function(dataError){						  
						  $(divId + "_inner").innerHTML = dataResult;
						   dijit.byId(divId).show();
						   applyStyle();
					  }	
					});		
				
			}
			
			
			
		function tabVisualizzaEventiNonLetti(divId,codiceMov){
				
				
				visualizzaEventiNonLetti(divId,codiceMov);		
		}
		
		
		function visualizzaEventiNonLetti(divId,codiceMov) {
			
			
			var jqxhr = jQuery.ajax({
				  url: '${pageContext.request.contextPath}/ajax/listaEventiNonLettiMovimento.htm',
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  data: "codiceMov="+ codiceMov,
				  success: function(dataResult) { 
					  	$("lista_eventi_non_letti"+codiceMov).innerHTML = dataResult;		
						dijit.byId(divId).show();
						applyStyle();
					},
				  error: function(dataError){						  
					  	$("lista_eventi_non_letti"+codiceMov).innerHTML = dataResult;		
						dijit.byId(divId).show();
						applyStyle();
				  }	
				});		
		
			}
		
		

		function ricercaAtto(codMovimento){
			disableFunctions();
			var jqxhr = jQuery.ajax({
				  url: "${pageContext.request.contextPath}/movimentiatti/ajaxRicercaAtto.htm",
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  data: "codicemovimento="+codMovimento,
				  success: function(dataResult) {
					  enableFunctions();
					  	dijit.byId('ricercaAttoDiv'+codMovimento).attr("style", "overflow:auto; width:600px");
			            dijit.byId('ricercaAttoDiv'+codMovimento).attr("content", dataResult);
				  		dijit.byId('ricercaAttoDiv'+codMovimento).show();
					  },
				  error: function(dataError){		
					  enableFunctions();
					  alert(dataError)
					  dijit.byId('ricercaAttoDiv'+codMovimento).attr("content", dataError);	
					  dijit.byId('ricercaAttoDiv'+codMovimento).show();
				  }	
				});		
		}
		
		function showDettaglioEsito(divId,codiceMov){
					   dijit.byId(divId).show();			
		}

		
		
		
		
			
		</script> 
		<jsp:include page="../includes/funzioni_movimenti.jsp">
			<jsp:param name="funzioneRichiesta" value="javascriptBlock" />
		</jsp:include>
		<div id="functions">
			<ul>
				<li><a href="javascript:historySet('${_urlback}','../movimenti/create.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:historySet('${_urlback}','../movimenti/createScadenza.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.nuova_scadenza" /></a></li>
				<li><a href="javascript:doHref('../movimenti/elabora.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.elabora" /></a></li>
				<li><a href="javascript:historySet('${_urlback}','../istanzeprocedimenti/list.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.gestione_endoprocedimenti"/></a></li>
				<c:if test="${isMovimentiDisabilitati eq true}">
					<li><a href="javascript:riattivaMovimenti();" title="<fmt:message key="button.riattiva.help"/>"><fmt:message key="button.riattiva"/></a></li>
				</c:if>
				<c:if test="${isCds eq true}">
					<li><a href="javascript:historySet('${_urlback}','../commissioniediliziet/listCommissioniIstanza.htm?codiceIstanza=${istanza.id.codice}','')"><fmt:message key="button.cds"/></a></li>
				</c:if>
				<c:if test="${istanza.alberoproc != null}">
					<c:set var="accessoAtti" value="${istanza.alberoproc.flagAccessoAtti}"/>
					<c:if test="${accessoAtti != null && accessoAtti}">
						<li><a href="javascript:historySet('${_urlback}','../istanzeaccessoattit/list.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.accesso_atti" /></a></li> 
					</c:if>
				</c:if>
				
				<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>