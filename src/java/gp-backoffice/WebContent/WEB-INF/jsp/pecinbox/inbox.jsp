<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.SessioneElaborazionePEC.TipoSessioneElaborazionePEC"%>
<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.SessioneElaborazionePEC"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.ReportProcessamentoPecDTO"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.PECInboxController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PECInboxFilter"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<%
SessioneElaborazionePEC processingSession = (SessioneElaborazionePEC)session.getAttribute(PECInboxController.ESITO_PROCESSAMENTO_PEC);
if(processingSession == null){
    processingSession = new SessioneElaborazionePEC();
}
ReportProcessamentoPecDTO esitoProcessamento = processingSession.getEsitoProcessamento();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><spring:message code="pecinbox.list.title" arguments="${pecInboxFilter.mailAccount}"></spring:message></title>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
		<style>
			<!--
			.jmesa .active-pec{
				background-color: #b6e7bd;
				/* F3A600 b6e7bd d0ffce */
			}
			.jmesa .active-pec td {
				font-family: tahoma, arial, helvetica;
				font-size: 11px;
				border: 1px solid #b6e7bd;
				padding: 2px 3px 2px 3px;
				/*border: 1px solid #F5F5F0;*/
				
			}
			.jmesa .active-pec a {
				color: black;
				text-decoration: none;
			}
			-->
		</style>
		<span class="titoloPagina"><spring:message code="pecinbox.list.title" arguments="${pecInboxFilter.descMailConfigDefault};${pecInboxFilter.mailAccount}"
		htmlEscape="false" argumentSeparator=";"></spring:message></span>
		
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../pecinbox/inbox" />
	    	 
		</jsp:include>	
		<input type="hidden" name="jMesaParamString" id="jMesaParamString" value="${ pecInboxFilter.jMesaParamString}"/>
		<jsp:include page="../includes/displayGlobalMessages.jsp">
	        <jsp:param name="commandName" value="pecInboxFilter" />
	    </jsp:include>
		<spring-form:form commandName="pecInboxFilter" name="filter" id="filter_form" action="${pageContext.request.contextPath}/pecinbox/ajaxPECList.htm">
			<table>
				<tr>
					<td width="48%">
						<fieldset style="height: 230px;">
							<legend ><fmt:message key="label.gestione_ricerca" /></legend>
															   
							    <table width="100%">
							        <tr>
										<td><b><fmt:message key="label.modifica" /> <fmt:message key="label.account_mail_cfg" /></b></td>
								        <td colspan="2">
											<spring-form:select id="select_account_email_id" path="idMailConfig" onchange="javascript:changeAccount()" >
											<spring-form:option value=""><fmt:message key="label.seleziona" /> </spring-form:option>
											<c:forEach items="${listMailConfig}" var="current" >
												<spring-form:option value="${current.id.codice}">${current.descrizioneLunga}</spring-form:option>
											</c:forEach>
											</spring-form:select>
											<spring-form:hidden path="descMailConfigDefault"  />
										</td>
									</tr>
									<tr>
										<%-- 
										<td width="30%">
											<fmt:message key="pecinbox.label.dataricezione" />
										</td>
										--%>
										<td colspan="3">
											<fmt:message key="label.dalla_data" />
											<spring-form:input id="dallaData" path="dataRicezioneDa" size="10" onblur="isValidDate(this,true);" />
											<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData" textKey="label.calendar" /> 			  	
											<spring-form:errors path="dataRicezioneDa" cssClass="error" delimiter="," />
											&nbsp;&nbsp;
											<fmt:message key="label.alla_data" />
											<spring-form:input id="allaData" path="dataRicezioneA" size="10" onblur="isValidDate(this,true);" />
											<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData" textKey="label.calendar" /> 
											<spring-form:errors path="dataRicezioneA" cssClass="error" delimiter="," />
										</td>
									</tr>
									<tr id="rigaFlagLetti">
										<td width="16%">
											<spring-form:radiobutton path="lettiNonLettiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.SI.value()%>" id="flagLetti"/>
											<spring-form:label for="flagLetti" path="lettiNonLettiString"><fmt:message key="pecinbox.label.letti" /></spring-form:label>
										</td>
										<td width="20%">
											<spring-form:radiobutton path="lettiNonLettiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.NO.value()%>" id="flagNonLetti"/>
											<spring-form:label for="flagNonLetti" path="lettiNonLettiString"><fmt:message key="pecinbox.label.nonletti" /></spring-form:label>
										</td>
										<td>
											<spring-form:radiobutton path="lettiNonLettiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.TUTTI.value()%>" id="flagLettiTutti"/>
											<spring-form:label for="flagLettiTutti" path="lettiNonLettiString"><fmt:message key="pecinbox.label.tutti" /></spring-form:label>
										</td>
									</tr>
									<tr id="rigaFlagElaborate">
										<td width="16%">
											<spring-form:radiobutton path="elaboratiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.SI.value()%>" id="flagElaborati"/>
											<spring-form:label for="flagElaborati" path="elaboratiString"><fmt:message key="pecinbox.label.elaborati" /></spring-form:label>
										</td>
										<td width="20%">
											<spring-form:radiobutton path="elaboratiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.NO.value()%>" id="flagNonElaborati"/>
											<spring-form:label for="flagNonElaborati" path="elaboratiString"><fmt:message key="pecinbox.label.nonelaborati" /></spring-form:label>
										</td>
										<td >
											<spring-form:radiobutton path="elaboratiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.TUTTI.value()%>" id="flagElaboratiTutti"/>
											<spring-form:label for="flagElaboratiTutti" path="elaboratiString"><fmt:message key="pecinbox.label.tutti" /></spring-form:label>
										</td>
									</tr>
									<tr id="rigaFlagLavorate">
										<td width="16%">
											<spring-form:radiobutton path="lavoratiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.SI.value()%>" id="flagLavorati"/>
											<spring-form:label for="flagLavorati" path="lavoratiString"><fmt:message key="pecinbox.label.lavorati" /></spring-form:label>
										</td>
										<td width="20%">
											<spring-form:radiobutton path="lavoratiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.NO.value()%>" id="flagNonLavorati"/>
											<spring-form:label for="flagNonLavorati" path="lavoratiString"><fmt:message key="pecinbox.label.nonlavorati" /></spring-form:label>
										</td>
										<td>
											<spring-form:radiobutton path="lavoratiString" value="<%=PECInboxFilter.FiltroPECSiNoTuttiEnum.TUTTI.value()%>" id="flagLavoratiTutti"/>
											<spring-form:label for="flagLavoratiTutti" path="lavoratiString"><fmt:message key="pecinbox.label.tutti" /></spring-form:label>
										</td>
									</tr>
									<tr id="rigaFlagRicevute">
										<td colspan="3">
											<spring-form:checkbox path="flagRicevute" value="true" id="flagRicevute"/>
											<spring-form:label for="flagRicevute" path="flagRicevute"><fmt:message key="pecinbox.label.ricevute" /></spring-form:label>
											<spring-form:hidden path="mailAccount" />
											<input type="hidden" name="clearSelection" id="clearSelection" value="<%= BooleanUtils.toBoolean(request.getParameter("resetFilter"))%>"/>
										</td>
									</tr>
								</table>
								<%-- 
								<div id="functions">
									<ul>
										<li>
											<a href="javascript:void(0);" id="btn_cerca"><fmt:message key="label.cerca" /></a>&nbsp;
										</li>
									</ul>
								</div>
								--%>
						</fieldset>
					</td>
					<td width="48%" valign="top" >
						<fieldset style="height: 172px;">
							<legend >Elaborazioni</legend>
							<div id="functions" style="height: 20%;">
								<ul>
									<li>
										<a title="<fmt:message key="pecinbox.button.scaricanuovimessaggi.help" />" href="javascript:sincronizzaPEC(false);">
											<fmt:message key="pecinbox.button.scaricanuovimessaggi" />
										</a>
									</li>
									<li>
										<a title="<fmt:message key="pecinbox.button.processamessaggi.help" />" href="javascript:processaMessaggi();">
											<fmt:message key="pecinbox.button.processamessaggi" />
										</a>
									</li>
									<li>
										<a title="<fmt:message key="pecinbox.button.sincronizzamessaggi.help" />" href="javascript:sincronizzaPEC(true);">
											<fmt:message key="pecinbox.button.sincronizzamessaggi" />
										</a>
									</li>
								</ul>
							</div>
							<div id="esito" style="height: 80%;">
								<span id="span_processamento_in_corso" style="display: none;">
									<img style="vertical-align: middle; height: 18px;" src="${pageContext.request.contextPath}/images/spinner.gif" alt="" />
									<fmt:message key="pecinbox.label.processamentoincorso" />
								</span>
								<span id="span_sincronizzazione_in_corso" style="display: none;">
									<img style="vertical-align: middle; height: 18px;" src="${pageContext.request.contextPath}/images/spinner.gif" alt="" />
									<fmt:message key="pecinbox.label.sincronizzazioneincorso" />
								</span>
								<div id="functions">
									<ul>
										<li id="li_esito_elaborazione" style="display: none;">
											<span id="span_esito_elaborazione">
											&nbsp;
											</span>
											<span class="error" id="span_errore_elaborazione">
											&nbsp;
											</span>
											<a id="link_report_processamento" title="<fmt:message key="pecinbox.button.esitoprocessamento.help" />" href="javascript:dettaglioUltimoProcessamento();">
												<fmt:message key="pecinbox.button.esitoprocessamento" />
											</a>
											<br />
											<fmt:message key="pecinbox.message.aggiornaelenco" />
										</li>
									</ul>
								</div>
							</div>
						</fieldset>
					</td>
				</tr>
			</table>
				<div id="functions">
					<ul>
						<li><a href="javascript:void(0);" id="btn_cerca"><fmt:message key="label.cerca" /></a>&nbsp;</li>
						<li>
							<a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>&nbsp;
						</li>
					</ul>
				</div>
			<div id="subcontent">
				
				
			</div>
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				//var _captionTab='<fmt:message key="pecinbox.list.title" />';
				var parameterString ="";
				function onInvokeAction(id) {
					
				    setExportToLimit(id, '');
				    parameterString = createParameterStringForLimit(id);
				    readPEC(parameterString);
				}	
				
				var processing = <%= processingSession.isRunning()%>;
				var processingType = "<%= processingSession.getTipoProcessamento() != null ? processingSession.getTipoProcessamento().value() : ""%>";
				var hasEsito = <%= esitoProcessamento != null%>;
				
				var idResponsabile = "${operatore.id.codice}";
				var responsabile = "${operatore.responsabile}";
				var passwordSbloccoAttiva = ${sbloccoAttivo};
				
				jQuery(document).ready(function(){
					jQuery("#btn_cerca").click(startSearch);
					var esistePecReportDialogDiv = dijit.byId('pecReportDialogDiv');
					if(esistePecReportDialogDiv){
						dijit.byId('pecReportDialogDiv').onHide = hideEsitoProcessamento;
					}
					//jQuery('input[name="readFromServer"]').click(abilitaCampiFiltro);
					//abilitaCampiFiltro();
					//la queryString con le impostazioni jMesa salvate si trova nel campo hidden jMesaParamString
					gestisciStatoElaborazione();
					var jMesaParams = jQuery('#jMesaParamString').val();
					readPEC(jMesaParams);
				});
				
				
				function changeAccount()
				{
					var idAccount=jQuery("#select_account_email_id").val();
					if(idAccount!='')
				    {javascript:doHref('inbox.htm?idAccount='+idAccount,'');}
					else{javascript:doHref('inbox.htm?resetFilter=true','');}
				}
				
				
				var refreshing = false;
				function startSearch(event){
					//quando si clicca sul bottone cerca viene resettata la riga selezionata
					if(event){
						jQuery("#clearSelection").val("true");
						event.preventDefault();
					}
					var queryString = "";
					var table = jQuery.jmesa.getTableFacade('inbox_list');
					if(table){
						queryString = createParameterStringForLimit('inbox_list');
					}
					readPEC(queryString);
					jQuery("#clearSelection").val("false");
				}
				
				function readPEC(parameterString){
					if(!refreshing){
						refreshing = true;
						disableFunctions();
						parameterString = parameterString ? parameterString : '';
						if(parameterString){
							parameterString += "<%= PECInboxController.JMESA_QUERYSTRING_SPLIT%>&";
						}
						parameterString += jQuery("#filter_form").formSerialize();
					    jQuery.post(
					    		'${pageContext.request.contextPath}/pecinbox/ajaxPECList.htm?' + parameterString+"&idAccount=${pecInboxFilter.idMailConfig}", 
					    		'html',
					    		function(data, textStatus, jqXHR) {
					    			enableFunctions();
					    			if(data){
					    				jQuery('#subcontent').html(data);
					    			}
					    			else{
					    				alert(textStatus);
					    			}
					    			refreshing = false;
					    });
					}
				}
				
				function abilitaCampiFiltro(){
					var valoreSelezionato = jQuery('input[name="readFromServer"]:checked').val();
					var disabledValue = false;
					if(valoreSelezionato == "false"){
						disabledValue = true;
					}
					jQuery('#flagNonLetti').prop('disabled', disabledValue);
					jQuery('#flagLetti').prop('disabled', disabledValue);
					if(disabledValue){
						jQuery('#rigaFlagLetti').hide();
					}
					else{
						jQuery('#rigaFlagLetti').show();
					}
				}
				
				function dettaglioPec(idPec){		
					var ancora = escape("#ancora_pec_id_" + idPec);
					var url = "../pecinbox/dettaglioPEC.htm?codicePec=" + encodeURIComponent(idPec);
					historySet('${_urlback}'+ancora, url, '');					
					disableFunctions();
				}
				
				
				function protocollaPec(idPec,idAccount){					
					var url = "../pecinbox/protocolloDaPEC.htm?codicePec=" + encodeURIComponent(idPec)+'&idAccount='+idAccount;
					// historySet('${_urlback}', url, '');
					// disableFunctions();
					//if(isSoftwareTT){
					// 	pannelloSceltaSoftware(url,'historySet',true,'',idPec);						
					//}else{
						vaiAFunzione(url,'historySet',true,'',idPec);
					//}										
				}
				
				function creaIstanzaDaPec(idPec,idAccount){
					
					var url = "../pecinbox/istanzaDaPEC.htm?codicePec=" + encodeURIComponent(idPec)+'&idAccount='+idAccount;
					// historySet('${_urlback}', url, '');
					// disableFunctions();
					if(isSoftwareTT){
						pannelloSceltaSoftware(url,'historySet',true,'',idPec,idAccount);						
					}else{
						vaiAFunzione(url,'historySet',true,'',idPec);
					}	
				}
				
				function creaMovimentoDaPec(idPec,idAccount){
					var url = "../pecinbox/movimentoDaPEC.htm?codicePec=" + encodeURIComponent(idPec)+'&idAccount='+idAccount;;
					if(isSoftwareTT){
						pannelloSceltaSoftware(url,'historySet',false,'',idPec,idAccount);						
					}else{
						vaiAFunzione(url,'historySet',false,'',idPec);
					}					
					// historySet('${_urlback}', url, '');
				}
				
				
				
				function segnaPecComeNonCancellata(idPec){
                    var message='Attenzione si sta segnando una PEC come non cancellata. Si vuole procedere?';
                    
                   if (checkConfirmMessage(message)) {
						var url ='../pecinbox/ajaxSegnaPecNonCancellata.htm?identificativo='+encodeURIComponent(idPec);
	                    new Ajax.Request(url, {
							method: 'post',	
							onSuccess: function(transport){
								readPEC(parameterString);
							},
							onFailure: function(transport){ 
							  alert("Errore durante il salvataggio del dato");
							  readPEC(parameterString);
							  //console.error(transport);
							}						    		 
						});
                   }
					
				}
				
				
				function rilasciaPEC(idPec, richiestaPassword){
					
					var pwd = "";
					if(richiestaPassword){
						pwd = prompt('<fmt:message key="pecinbox.message.javascript.richiestapassword" />');
					}
					if(pwd != null){
						var url = "../pecinbox/rilasciaPEC.htm?codicePec="  + encodeURIComponent(idPec) + "&pwd=" + pwd + "#ancora_pec_id_" + idPec;
						doHref(url, !richiestaPassword ? '<fmt:message key="pecinbox.message.javascript.rilasciapec" />' : undefined);
					}
				}
				
				function visualizzaMovimentoDaPec(idMovimento, software, idPec){
					var ancora = escape("#ancora_pec_id_" + idPec);
					var url = "../movimenti/view.htm?codice=" + idMovimento + "&software=" + software + "#ancora_pec_id_" + idPec;
					historySet('${_urlback}'+ancora, url, '');
				}
				function visualizzaIstanzaDaPec(idIstanza, software, idPec){
					var ancora = escape("#ancora_pec_id_" + idPec);
					var url = "../istanze/view.htm?codice=" + idIstanza+"&software=" + software + "#ancora_pec_id_" + idPec;
					historySet('${_urlback}'+ancora, url, '');
				}
				
				function visualizzaProtocolloPec(idPec){
					var ancora = escape("#ancora_pec_id_" + idPec);
					var url ="../pecinbox/leggiProtocollo.htm?codicePec=" + encodeURIComponent(idPec) + "#ancora_pec_id_" + idPec;
					historySet('${_urlback}'+ancora, url, '');
				}
				
				function switchFlagLetta(idPec){
					//disableFunctions();
					var img = jQuery('#imgLetta_' + escapeStringForCssSelector(idPec));
					img.hide();
					img = jQuery('#imgWait_' + escapeStringForCssSelector(idPec));
					img.show();
				    jQuery.ajax({
			    		url: "../pecinbox/ajaxSwitchFlagLetta.htm?codicePec=" + encodeURIComponent(idPec), 
			    		dataType: 'json',
			    		cache: false,	
			    		success: switchFlagLettaCallback,
			    		error: switchFlagLettaError
			    	});
				}
				
	    		function switchFlagLettaCallback(data, textStatus, jqXHR) {
	    			//enableFunctions();
	    			if(!data.success){
	    				displayErrorMessage(data.message);
	    			}
	    			else {
	    				if(data.codicePec){
	    					var img = jQuery('#imgLetta_' + escapeStringForCssSelector(data.codicePec));
	    					if(img && img.length == 1){
	    						var imgSrc = img.attr('src');
	    						var srcSplitted = imgSrc.split('/');
	    						if(srcSplitted.length > 0){
	    							if(srcSplitted[srcSplitted.length-1] == 'email_letta.gif'){
	    								srcSplitted[srcSplitted.length-1] = 'email_unread.png';
	    							}
	    							else{
	    								srcSplitted[srcSplitted.length-1] = 'email_letta.gif';
	    							}
	    							imgSrc = srcSplitted.join('/');
	    							img.attr('src', imgSrc);
	    						}
	    					}
	    					var img = jQuery('#imgWait_' + escapeStringForCssSelector(data.codicePec));
	    					img.hide();
	    					img = jQuery('#imgLetta_' + escapeStringForCssSelector(data.codicePec));
	    					img.show();
	    				}
	    				//displayMessage(data.message);
	    			}
	    		}	
	    		
	    		function switchFlagLettaError(jqXHR, textStatus, errorThrown) {
	    			 mostraErroriCallback(jqXHR, textStatus, errorThrown);
	    		}

	    		function mettiInEvidenza(idPec,flagEvidenza){
	    			var idRespEvidenza = jQuery('#fkIdRespEvidenza_' + escapeStringForCssSelector(idPec));
	    			var respEvidenza = jQuery('#fkRespEvidenza_' + escapeStringForCssSelector(idPec));
	    			if(idRespEvidenza.length == 1 && respEvidenza.length == 1){
	    				idRespEvidenza = idRespEvidenza.html();
	    				execute = true;
	    				if(idRespEvidenza.length > 0){
	    					if(idRespEvidenza != idResponsabile){
	    						execute = confirm("La PEC é stata messa in evidenza dall'operatore " + respEvidenza.val() + "\r\nProcedere comunque?");
	    					}
	    				}
	    				if(execute){
							var img = jQuery('#imgEvidenza_' + escapeStringForCssSelector(idPec));
							img.hide();
							img = jQuery('#imgEvidenzaWait_' + escapeStringForCssSelector(idPec));
							img.show();
						    jQuery.ajax({
					    		url: "../pecinbox/ajaxEvidenziaPEC.htm?codicePec=" + idPec + "&evidenzia=" + flagEvidenza, 
					    		dataType: 'json',
					    		cache: false,	
					    		success: mettiInEvidenzaCallback,
					    		error: mettiInEvidenzaError
					    	});
	    				}
	    			}
	    		}
	    		
	    		function mettiInEvidenzaCallback(data, textStatus, jqXHR) {
	    			if(!data.success){
	    				displayErrorMessage(data.message);
	    			}
	    			else {
	    				if(data.codicePec){
	    					var img = jQuery('#imgEvidenza_' + escapeStringForCssSelector(data.codicePec));
	    					if(img && img.length == 1){
	    						var imgSrc = img.attr('src');
	    						var srcSplitted = imgSrc.split('/');
	    						if(srcSplitted.length > 0){
	    							if(srcSplitted[srcSplitted.length-1] == 'favorites-icon-disabled.gif'){
	    								srcSplitted[srcSplitted.length-1] = 'favorites-icon.png';
	    							}
	    							else{
	    								srcSplitted[srcSplitted.length-1] = 'favorites-icon-disabled.gif';
	    							}
	    							imgSrc = srcSplitted.join('/');
	    							img.attr('src', imgSrc);
	    						}
	    					}
	    					var img = jQuery('#imgEvidenzaWait_' + escapeStringForCssSelector(data.codicePec));
	    					img.hide();
	    					img = jQuery('#imgEvidenza_' + escapeStringForCssSelector(data.codicePec));
	    					img.show();
	    					
	    					var codResponsabile = '';
	    					if(data.codiceResponsabile){
	    						codResponsabile = data.codiceResponsabile;
	    					}
	    					jQuery('#fkIdRespEvidenza_' + escapeStringForCssSelector(data.codicePec)).html(codResponsabile);
	    	    			jQuery('#fkRespEvidenza_' + escapeStringForCssSelector(data.codicePec)).html(codResponsabile);
	    					
	    				}
	    				//displayMessage(data.message);
	    			}
	    		}
	    		
	    		function mettiInEvidenzaError(jqXHR, textStatus, errorThrown) {
	    			 mostraErroriCallback(jqXHR, textStatus, errorThrown);
	    		}
				
				function gestisciStatoElaborazione(){
					if(processing){
						aggiornaStatoElaborazione();
					}
					else if(hasEsito){
		    			var data = {
		    				error: "<%= processingSession.getErrore()%>",
		    				<%if(esitoProcessamento == null){%>
		    				data: null
	    					<%}else{%>
	    					data:{
	    						errore: "<%= esitoProcessamento.getErrore()%>",
	    						avviso: "<%= esitoProcessamento.getAvviso()%>",
	    						numeroPecProcessate: "<%= esitoProcessamento.getNumeroPecProcessate()%>",
	    						numeroPecConErrore: "<%= esitoProcessamento.getNumeroPecConErrore()%>",
	    						messaggio: "<%= esitoProcessamento.getMessaggio()%>"
	    					}
	    					<%}%>
		    			};
		    			//if(processingType == "<%= TipoSessioneElaborazionePEC.PROCESSAMENTO%>"){
							elaborazioneCallback(data,"",null);
						/*
		    			}
		    			else{
		    				elaborazioneCallback(data,"",null);
		    			}
		    			*/
					}
				}
				
				function aggiornaStatoElaborazione(){
					displayAttesa();
				    jQuery.ajax({
				    		url: '${pageContext.request.contextPath}/pecinbox/ajaxStatoProcessamentoPEC.htm?', 
				    		dataType: 'json',
				    		cache: false,	
				    		success: aggiornaStatoElaborazioneCallback,
				    		error: elaborazioneError
				    });					
				}
				
				function aggiornaStatoElaborazioneCallback(data, textStatus, jqXHR){
					if(data.running && data.running === "true"){
						setTimeout(aggiornaStatoElaborazione,30000);
					}
					else{
		    			//if(processingType == "<%= TipoSessioneElaborazionePEC.PROCESSAMENTO%>"){
							elaborazioneCallback(data, textStatus, jqXHR);
						/*
		    			}
		    			else{
		    				elaborazioneCallback(data, textStatus, jqXHR);
		    			}
		    			*/
					}
				}
				
				function displayAttesa(){
					if(processingType == "<%= TipoSessioneElaborazionePEC.SINCRONIZZAZIONE.value()%>"){
	    				jQuery('#span_sincronizzazione_in_corso').show();
					}
					else{
						jQuery('#span_processamento_in_corso').show();
					}
	    			jQuery('#li_esito_elaborazione').hide();
				}
				
				function hideEsitoProcessamento(){
	    			jQuery('#span_processamento_in_corso').hide();
	    			jQuery('#span_sincronizzazione_in_corso').hide();
	    			jQuery('#li_esito_elaborazione').hide();
				}
				
	    		function processaMessaggi(){
	    			if(!processing){
	    				processing = true;
	    				processingType = "<%= TipoSessioneElaborazionePEC.PROCESSAMENTO.value()%>";
	    				displayAttesa();
					    jQuery.ajax({
					    		url: '${pageContext.request.contextPath}/pecinbox/processaMessaggiPEC.htm?idAccount=${pecInboxFilter.idMailConfig}', 
					    		dataType: 'json',
					    		cache: false,	
					    		success: elaborazioneCallback,
					    		error: elaborazioneError
					    });
	    			}else{
	    				displayMessage("<fmt:message key="pecinbox.message.processamentoincorso" />");
	    			}
				}
				
	    		function elaborazioneCallback(data, textStatus, jqXHR) {
	    			processing = false;
	    			jQuery('#span_processamento_in_corso').hide();
	    			jQuery('#span_sincronizzazione_in_corso').hide();
	    			if(data.error && data.error != "null"){
	    				displayErrore(data.error);
	    			}
	    			else if(data.data){
	    				displayEsito(data.data);
	    			}
	    			else{
	    				displayErrore(textStatus);
	    			}
	    			processingType = "";
	    		}	
	    		
	    		function elaborazioneError(jqXHR, textStatus, errorThrown) {
	    			processing = false;
	    			/*
	    			se siamo in attesa della risposta alla chiamata ajax di processaMessaggi.htm o sincronizzaMessaggi.htm e si invoca un'altra pagina
	    			la chiamata ajax in attesa si comporta come se avesse ricevuto un'errore, 
	    			in realtà quando si verifica un vero errore il valore di readyState è 4 in caso i abort vale zero.
	    			*/
	    			if(jqXHR.readyState == 4){
		    			var errorMessage = '<fmt:message key="pecinbox.message.erroresincronizzazione" />';
		    			if(errorThrown){
		    				errorMessage += errorThrown;
		    			}
		    			displayErrore(errorMessage);
	    			}
	    		}
	    		
				function sincronizzaPEC(syncAll){
	    			if(!processing){
	    				processing = true;
	    				processingType = "<%= TipoSessioneElaborazionePEC.SINCRONIZZAZIONE.value()%>";
	    				displayAttesa();
						var mailAccount = jQuery('#mailAccount').val();
						if(syncAll === true || syncAll === "true" || syncAll === 1){
							syncAll = "true";
						}
						else{
							syncAll = "false";
						}
					    jQuery.ajax({
				    		url: "../pecinbox/ajaxSincronizzaPECInbox.htm?syncAll=" + syncAll + "&account=" + mailAccount+"&idAccount=${pecInboxFilter.idMailConfig}", 
				    		dataType: 'json',
				    		cache: false,	
				    		success: elaborazioneCallback,
				    		error: elaborazioneError
				    	});
	    			}else{
	    				displayMessage("<fmt:message key="pecinbox.message.processamentoincorso" />");
	    			}
				}
				
	    		function displayErrore(errorMessage){
	    			jQuery('#li_esito_elaborazione').show();
	    			jQuery('#span_esito_elaborazione').hide();
	    			jQuery('#link_report_processamento').hide();
	    			var spanErrore = jQuery('#span_errore_elaborazione');
	    			spanErrore.text(errorMessage);
	    			spanErrore.show();
	    		}
	    		
	    		function displayEsito(reportData){
	    			var isProcessamento = processingType == "<%= TipoSessioneElaborazionePEC.PROCESSAMENTO%>";
	    			jQuery('#li_esito_elaborazione').show();
	    			if(isProcessamento){
	    				jQuery('#link_report_processamento').show();
	    			}
	    			else{
	    				jQuery('#link_report_processamento').hide();
	    			}
	    			jQuery('#span_errore_elaborazione').hide();
	    			var spanEsito = jQuery('#span_esito_elaborazione');
	    			spanEsito.show();
	    			if(reportData.messaggio){
		    			spanEsito.text(reportData.messaggio);
	    				jQuery('#pecReportContentDiv').html("<span>" + reportData.messaggio + "</span>");
	    				jQuery('#pecReportContentDiv').show();
	    			}
	    			else{
	    				jQuery('#pecReportContentDiv').hide();
	    			}
	    			if(reportData.errore){
	    				jQuery('#pecReportErrorDiv').html("<span>" + reportData.errore + "</span>");
	    				jQuery('#pecReportErrorDiv').show();
	    			}
	    			else{
	    				jQuery('#pecReportErrorDiv').hide();
	    			}
	    			if(reportData.avviso){
	    				jQuery('#pecReportWarningDiv').html("<span>" + reportData.avviso + "</span>");
	    				jQuery('#pecReportWarningDiv').show();
	    			}
	    			else{
	    				jQuery('#pecReportWarningDiv').hide();
	    			}
	    			//TODO visualizzare l'elenco dettagliato di tutti gli errori restituiti nell'oggetto json magari associandoli alla riga nella tabella JMESA
	    			//dijit.byId('pecReportDialogDiv').show();	    			
	    		}
	    		
	    		function dettaglioUltimoProcessamento(){
	    			dijit.byId('pecReportDialogDiv').show();
	    		}
	    		
			</script>
		</spring-form:form>

		<!-- div per la visualizzazione della dialog con il rapporto del processamento delle PEC sul server di posta -->
		<div dojoType="dijit.Dialog" id="pecReportDialogDiv" onHide="javascript: startSerch(); return true;" title="<fmt:message key="pecinbox.message.esitoprocessamento.title" />: ">
			<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 400px;height: 200px; overflow: auto; " >
				<div id="pecReportContentDiv">
				</div>
				<br/>
				<div id="pecReportErrorDiv" class="error_header">
				</div>
				<br/>
				<div id="pecReportWarningDiv" class="warning_header">
				</div>
			</div>
		</div>
		
		<jsp:include page="../includes/dialogs.jsp"/>
		<jsp:include page="../includes/pannelloSceltaSoftwarePec.jsp"/>
		
		
	</body>
</html>