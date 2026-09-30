<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<table>
			<tr>
				<td valign="top" style="width: 50%;">		
					<div class="header_dati">
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.istanza" />:</span>
								<span class="header_dato_valore"><a href="../istanze/view.htm?codice=${tempisticaHelper.istanza.id.codice}">${tempisticaHelper.istanza.numeroistanza}</a></span>
							</div>		
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.richiedente" />:</span>		
								<span class="header_dato_valore">
									${tempisticaHelper.istanza.transientRichiedenteQualitaAzienda}		
									<span id="fx_interdizioni" style="display: none;"></span>
									
										<script type="text/javascript">			
										jQuery(document).ready(function(){
											var _ts  = new Date().getTime();
												new Ajax.Request('<%=request.getContextPath()%>/ajax/isAnagrafeInterdetta.htm?ts_='+_ts, {
														method: 'post',	
														parameters: {codiceAnagrafe: ${tempisticaHelper.istanza.richiedente.id.codice}},
														onSuccess: function(transport){
															if(transport.responseText!=''){
																$('fx_interdizioni').innerHTML=" ("+transport.responseText+")";
																$('fx_interdizioni').style.display='';
																applyStyle();
															}else{
																$('fx_interdizioni').style.display='none';
															}
														},
														onFailure: function(transport){ 
															  
															}			
													});
										});
										</script>
								</span>   		
								</div>
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.protocollo" />:</span>
								<span class="header_dato_valore">
								${tempisticaHelper.istanza.numeroprotocollo} - <fmt:formatDate value="${tempisticaHelper.istanza.dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/>
								</span>
							</div>	
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.codice_pratica_telematica" />:</span>
								<span class="header_dato_valore">
									&nbsp;${tempisticaHelper.istanza.codicepraticatel}
								</span>
							</div>
							<div class="header_dato">
								<span class="header_dato_etichetta">
									<fmt:message key="label.alberoproc" />:</span>
								<span class="header_dato_valore">&nbsp;${tempisticaHelper.istanza.alberoproc.vwAlberoproc.scDescrizione}</span>
								
							</div>
							<c:if test="${not empty tempisticaHelper.istanza.operatoreInCarico.responsabile }">
							<div class="header_dato" style="font-size:1.4em; border: 1px dotted maroon; padding: 2px; margin: 2px;">
								<span class="header_dato_etichetta"><fmt:message key="label.pratica_in_carico_a" />:</span>
								<span class="header_dato_valore">${tempisticaHelper.istanza.operatoreInCarico.responsabile}</span>
							</div>	
							</c:if>	
							<c:if test="${isComuniAssociati eq true }">
								<div class="header_dato">
									<span class="header_dato_etichetta">
										<fmt:message key="label.comune" />:</span>
									<span class="header_dato_valore">${tempisticaHelper.istanza.comune.comune}</span>
								</div>	
							</c:if>
							<c:if test="${isVerticalizzazioneAUTORIZACCESSIAttiva}">
								<c:if test="${isExistAutAccessoCollegate}">
									<div class="header_dato">
										<div id="functions">
											<ul>
												<li><a href="javascript:historySet('${_urlback}','../autorizzazioni/viewOperazioni.htm?codiceIstanza=${tempisticaHelper.istanza.id.codice}','');"><fmt:message key="label.tab_operazioni" /></a></li>
											</ul>
										</div>
									</div>
								</c:if>
							</c:if>
			
					</div>				
				</td>
				<td valign="top" style="padding-left: 15px; style="width: 50%;"">
					<div class="header_dati">
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.data_presentazione_domanda" />:</span>
								<span class="header_dato_valore"><fmt:formatDate value="${tempisticaHelper.istanza.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></span>
							</div>	
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.data_inizio_istanza" />:</span>
								<span class="header_dato_valore"><fmt:formatDate value="${tempisticaHelper.datainizio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></span>
							</div>	
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.termine_stimato_del_procedimento" />:</span>
								<span class="header_dato_valore"><c:if test="${empty tempisticaHelper.stato}"><fmt:formatDate value="${tempisticaHelper.datafine}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />&nbsp;
							
										<br/>- ${tempisticaHelper.transientDurataStimataProcedimento}<fmt:message key="label.gg" />&nbsp;<fmt:message key="label.tempo_totale" />,${tempisticaHelper.transientDurataStimataProcedimento-tempisticaHelper.ggaggiuntivi}<fmt:message key="label.gg" />&nbsp;<fmt:message key="label.tempo_previsto" />,
										<br/>- ${tempisticaHelper.ggaggiuntivi}<fmt:message key="label.gg" />&nbsp;<fmt:message key="label.tempo_sospensioni_e_interruzioni" />,
										<br/>- ${tempisticaHelper.ggProroghe}<fmt:message key="label.tempo_proroghe" />
										
										</c:if>
										<c:if test="${tempisticaHelper.stato eq 'I'}">(INTERROTTA)</c:if>
										<c:if test="${tempisticaHelper.stato eq 'S'}">
											<label style="text-decoration: line-through;"><fmt:formatDate value="${tempisticaHelper.datafine}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />&nbsp;${tempisticaHelper.transientDurataStimataProcedimento}<fmt:message key="label.gg" /></label>&nbsp;(SOSPESA)
										</c:if>
								</span>
							</div>				
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.termine_del_procedimento" />:</span>
								<span class="header_dato_valore">
										<c:if test="${empty tempisticaHelper.stato}">
										<c:if test="${not empty tempisticaHelper.datafineeffettiva}"><fmt:formatDate value="${tempisticaHelper.datafineeffettiva}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
										<br/>-&nbsp;${tempisticaHelper.transientDurataProcedimento}<fmt:message key="label.gg" />&nbsp;<fmt:message key="label.tempo_totale" />,&nbsp;${tempisticaHelper.transientDurataProcedimento - tempisticaHelper.ggaggiuntivi }<fmt:message key="label.gg" />&nbsp;<fmt:message key="label.tempo_previsto" />,
										<br/>-&nbsp;${tempisticaHelper.ggaggiuntivi}<fmt:message key="label.gg" />&nbsp;<fmt:message key="label.tempo_sospensioni_e_interruzioni" />,
										<br/>-&nbsp;${tempisticaHelper.ggProroghe}<fmt:message key="label.tempo_proroghe" />
										</c:if>								
										</c:if>
								</span>
							</div>	
							<div class="header_dato">
								<span class="header_dato_etichetta"><fmt:message key="label.stato" />:</span>
								<span class="header_dato_valore">${tempisticaHelper.istanza.chiusura.stato}</span>
							</div>	
					</div>								
				</td>
			</tr>
		</table>	