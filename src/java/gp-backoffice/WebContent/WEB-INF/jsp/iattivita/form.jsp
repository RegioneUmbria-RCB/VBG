<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Ateco"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<fmt:message key="label.gestione_attivita" />
		</title>
		<style>
			.inline-element{
				display: inline-block;
				padding-right: var(--default-padding);
			}

			.storico{
				padding-bottom: var(--default-padding);
			}

			.input_posArch{
				width: 100px;
			}

			.tooltip-text {
				visibility: hidden;									
				font-size: 12px;					
				border-radius: 10px;		
				border: 1px;			
			}

			#hover-text:hover ~ .tooltip-text {
				visibility: visible;
			}				
		</style>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-istanza/vbg-dettaglio-istanza.js?<%=vJS %>" defer></script>
	</head>
	<body>
		<span class="titoloPagina">
				<fmt:message key="label.gestione_attivita" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../iattivita/view" />
			<jsp:param name="qs" value="codice%3D${iattivitaCommand.entity.id.codice}" />
		</jsp:include>

		<div id="subcontent">
			<spring-form:form commandName="iattivitaCommand" name="inviodati">
				<div class="vbg-form">
					<jsp:include page="../includes/displayGlobalMessages.jsp" >
						<jsp:param name="commandName" value="iattivitaCommand" />
					</jsp:include>
					<!-- Sezione inizale -->
					<!-- START -->			    
					<div class="storico">						
						<c:set var="_CONF_UTENTE_VIS_VISSTORICOATTIVITA"><%= WebConstants.CONF_UTENTE_VIS_VISSTORICOATTIVITA%></c:set>
						<spring-form:checkbox path="visstorico" id="CONF_UTENTE_VIS_VISSTORICOATTIVITA" onclick="visualizzaStorico(this,'${_CONF_UTENTE_VIS_VISSTORICOATTIVITA}');"/>
						<label for="CONF_UTENTE_VIS_VISSTORICOATTIVITA"><fmt:message key="label.attivita_help_visualizza_storico" /></label>
					</div>				    
					<fieldset>
						<legend><fmt:message key="label.dati_dell_attivita" /></legend>
						<c:if test="${visualizzaDataInizioFine eq true }">	
							<div class="form-group">
								<div class="inline-element">
									<label><fmt:message key="label.data_inizio" />:</label>
									<c:if test="${iattivitaCommand.entity.dataInizio!=null}">
										&nbsp;<b><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" value="${iattivitaCommand.entity.dataInizio}"/></b>
									</c:if>
									<c:if test="${iattivitaCommand.entity.dataInizio==null}">
										&emsp;<b>-</b>
									</c:if>
								</div>
								<div class="inline-element">
									<label><fmt:message key="label.data_fine" />:</label>
									<c:if test="${iattivitaCommand.entity.dataFine!=null}">
										&nbsp;<b><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" value="${iattivitaCommand.entity.dataFine}"/> </b>
									</c:if>
									<c:if test="${iattivitaCommand.entity.dataFine==null}">
										&emsp;<b>-</b>
									</c:if>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label><fmt:message key="label.stato" /></label>
							<b>
								<c:if test="${iattivitaCommand.entity.attiva eq true}">
									<fmt:message key="label.attiva" />
								</c:if>
								<c:if test="${iattivitaCommand.entity.attiva ne true}">
									<fmt:message key="label.non_attiva" />
								</c:if>
							</b>	
						</div>
						<div class="form-group">
							<label><fmt:message key="label.denominazione_dell_attivita" /></label>
							<spring-form:input id="denominazione_id" path="entity.denominazione" size="70" />
							<spring-form:errors path="entity.denominazione" cssClass="error"/>					
							<c:if test="${cartograficoAttivo eq true}">
                                <a style="float: none;" href="" title="<fmt:message key="label.cartografico.aprimappa" />">
                                    <i class="fa fa-map-marked-alt fa-lg mostra-mappa"></i>
                                </a>
                            </c:if>
						</div>
						<c:if test="${vert_osservatorio_attivo eq true }">
							<div class="form-group">
								<label><fmt:message key="label.codice_osservatorio" /></label>
								<spring-form:input id="codiceOsservatorio_id" path="entity.codiceOsservatorio" size="10" maxlength="10" />
								<spring-form:errors path="entity.codiceOsservatorio" cssClass="error"/>
							</div>					
						</c:if>				
						<div class="form-group">
							<label><fmt:message key="label.tipologia_attivita" /></label>
							<script type="text/javascript">								
								function filterTipologieBySoftware(element, entry) {
									
									var software = '<%=ORMHelper.getSoftware()%>';
									return entry + "&paramSoftware=" + software+"&ts_=" + Date();								
								}
						</script>
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="foarjstepstestata_id" />				
								<jsp:param name="propertyPath" value="entity.tipologiaAttivita" />			
								<jsp:param name="pathPropertyDescription" value="entity.tipologiaAttivita.descrizione" />
								<jsp:param name="pathPropertyCode" value="entity.tipologiaAttivita.id.codice" />
								<jsp:param name="autocompleterAjax" value="../iattivitatipologie/ajaxFindIAttivitaTipologie.htm" />
								<jsp:param name="titleKey" value="label.ricerca_tipologieattivita" />
								<jsp:param name="ajaxCallBack" value="filterTipologieBySoftware" />
							</jsp:include>		
						</td>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.istanza_rappresentativa" /></label>
							<b>${iattivitaCommand.entity.istanza.numeroistanza}</b>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.richiedente" /></label>
							<c:choose>
								<c:when test="${INVERTI_RICHIEDENTE eq true }">
									<b>${iattivitaCommand.entity.istanza.transientRichiedenteQualitaAzienda}</b>
									<init:help idHelp="helpAnagrafestorico_1" text="${iattivitaCommand.entity.istanza.transientRichiedenteQualitaAziendaStorico}"/>				
								</c:when>
								<c:otherwise>
									<b>${iattivitaCommand.entity.istanza.transientRichiedenteQualitaAziendaStorico}</b>
									<init:help idHelp="helpAnagrafestorico_1" text="${iattivitaCommand.entity.istanza.transientRichiedenteQualitaAzienda}"/>									
								</c:otherwise>
							</c:choose>
						</div>			
					</fieldset>
					<!-- Sezione inizale -->
					<!-- END -->			
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI SULLE LOCALIZZAZIONI---------------------------------  -->
					<!-- ----------------------------------------------START-----------------------------------------------------  -->
					<%
						String displayTr = "display:none;";
						String styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_LOCALIZZAZIONI)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<fieldset>
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
						<legend>
								<a class="<%= styleTr%>"
									id="id_link_localizzazioni"
									href="javascript:showHidePanel('localizzazioni_table', 'id_link_localizzazioni','<%= WebConstants.CONF_UTENTE_VIS_LOCALIZZAZIONI %>','${pageContext.request.contextPath}/images/','div');"
									title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.localizzazioni"/>">
									<label for="id_link_localizzazioni"><fmt:message key="label.localizzazioni" /></label> 
								</a> 					
						</legend>
						<div id="localizzazioni_table" style="<%=displayTr%>">
							<!-- Tabella che riporta le localizzazioni -->			
							<table class="vbg-table" >
								<caption></caption>
								<thead>
									<tr>
										<th><fmt:message key="label.numero_istanza"/></th>
										<th colspan="2"><fmt:message key="label.indirizzo"/></th>
										<th><fmt:message key="label.cap"/></th>
										<th><fmt:message key="label.frazione"/></th>
										<th><fmt:message key="label.circoscrizione"/></th>
										<th colspan="2"><fmt:message key="label.dati_catastali"/></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${iattivitaCommand.visstorico}">
										<tr>
											<fmt:message key="label.storico_attivita"/>
											<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI%></c:set>
											<spring-form:checkbox path="flagStoricoLocalizzazioni" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI}');" />
											<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
										</tr>
									</c:if>
									<!-- Crea la sola prima riga utilizzando l'istanza contenuta nell'oggetto iattivita con il numero istanza e 
									setta un rowspan per quante sono gli stradati dell'istanza -->
									<c:forEach items="${iattivitaCommand.entity.istanza.istanzestradarios}" var="istStradario" varStatus="istStradStatus">
									<c:set var="trStyle" value="odd"/>							
										<tr class="${trStyle}" title="${primarioTitle}">
										<c:if test="${istStradStatus.index==0}">
												<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzestradarios)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
											</c:if>
											<c:if test="${istStradStatus.index!=0}">												
											</c:if>
										<td>
											<c:if test="${not empty istStradario.primario && istStradario.primario eq true}">
												<fmt:message key="label.p"/>
											</c:if>	
										</td>
										
										<td>${istStradario.descrizioneEstesaTransient}</td>
										<%-- 
										<td>${istStradario.stradario.descrizioneAndCodiceviario}</td>
										--%>
										<td>${istStradario.cap}</td>
										<td>${istStradario.frazione}</td>
										<td>${istStradario.circoscrizione}</td>
										<td>
											<c:forEach items="${istStradario.istanzemappalis}" var="istMappale" varStatus="istMappStatus">
												${istMappale.descrizioneEstesa}<br />															
											</c:forEach>	
										</td>
										</tr>
										</c:forEach>
										<%--
										<c:if test="${fn:length(iattivitaCommand.entity.istanza.istanzestradarios)!=0}">
										<tr class="header">
											<td colspan="8"></td>
										</tr>
										</c:if>
										--%>
										<c:if test="${iattivitaCommand.flagStoricoLocalizzazioni eq true}">
										<!-- Crea le righe con le istanze che appartengono alla stessa attivita -->
										<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper"  varStatus="indexIst">
											<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
											<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
											<c:forEach items="${istanzaHelper.istanzestradarios}" var="istStradario" varStatus="istStradStatus">								
												<c:if test="${istStradStatus.index==0}">
													<tr class="header">
														<td colspan="8"></td>
													</tr>
												</c:if>							
											<c:set var="primarioTitle" value=""/>
												<c:if test="${not empty istStradario.primario && istStradario.primario eq true}">
													<c:set var="primarioTitle"><fmt:message key="label.primario"/></c:set>
												</c:if>				
											<c:set var="trStyle" value="odd"/>							
												<tr class="${trStyle}" title="${primarioTitle}">	
													<!-- Crea la sola prima colonna con il numero istanza e setta un rowspan per quante sono le istanze -->
													<c:if test="${istStradStatus.index==0}">
														<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzestradarios)}">${istStradario.istanza.numeroistanza}</td>
													</c:if>
													<c:if test="${istStradStatus.index!=0}">											
													</c:if>
													<td width="2%" >
														<c:if test="${not empty istStradario.primario && istStradario.primario eq true}">
															<fmt:message key="label.p"/>
														</c:if>	
													</td>
													<td>${istStradario.descrizioneEstesaTransient}</td>
													<td width="5%">${istStradario.cap}</td>
													<td>${istStradario.frazione}</td>
													<td>${istStradario.circoscrizione}</td>
													<td>
														<c:forEach items="${istStradario.istanzemappalis}" var="istMappale" varStatus="istMappStatus">
															${istMappale.descrizioneEstesa}<br />															
														</c:forEach>	
													</td>
												</tr>									
											</c:forEach>
											</c:if>	
										</c:forEach>
										</c:if>
								</tbody>
							</table>       
						</div>
					</fieldset>	
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI SULLE LOCALIZZAZIONI---------------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_SOGGCOLL)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI SULLE SOGGETTI COLLEGATI-----------------------------  -->
					<!-- ----------------------------------------------START-----------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_sogg_coll"
								href="javascript:showHidePanel('soggcoll_table', 'id_link_sogg_coll','<%= WebConstants.CONF_UTENTE_VIS_SOGGCOLL%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.soggetti_collegati_all_istanza" />">
								<label for="id_link_sogg_coll"><fmt:message key="label.soggetti_collegati_all_istanza" /></label> 
							</a> 			
						</legend>
						<div id="soggcoll_table" style="<%=displayTr%>">
						<!-- Tabella che riporta soggetti collegati -->		
							<table class="vbg-table">
								<caption></caption>
								<thead>
									<tr>
										<th><fmt:message key="label.numero_istanza"/></th>
										<th><fmt:message key="label.nominativo"/></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${iattivitaCommand.visstorico}">
										<tr>
											<fmt:message key="label.storico_attivita"/>
											<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI%></c:set>
											<spring-form:checkbox path="flagStoricoSoggettiCollegati" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI}');" />
											<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
										</tr>
									</c:if>
									<!-- Crea la sola prima riga utilizzando l'istanza contenuta nell'oggetto iattivita con il numero istanza e setta un rowspan per quante sono i soggetti dell'istanza -->
									<c:forEach items="${iattivitaCommand.entity.istanza.istanzerichiedentis}" var="istanzerichiedenti" varStatus="statusRic">
										<c:set var="trStyle" value="odd"/>							
										<tr class="${trStyle}" >
											<c:if test="${statusRic.index==0}">
												<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzerichiedentis)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
											</c:if>
											<c:if test="${statusRic.index!=0}">							
											</c:if>						
											<td>
												${istanzerichiedenti.richiedente.descrizioneRichiedente} - ${istanzerichiedenti.tiposoggetto.tiposoggetto}
												<c:if test="${not empty istanzerichiedenti.descrsoggetto}">
													&nbsp;${istanzerichiedenti.descrsoggetto}
												</c:if>
												&nbsp;${istanzerichiedenti.anagrafeCollegata.descrizioneRichiedente}
											</td>
										</tr>	
									</c:forEach>	   
									<!-- Mostro tutti i soggetti collegati della altre istanze legate all'attività -->
									<c:if test="${iattivitaCommand.flagStoricoSoggettiCollegati eq true}">			
										<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper"  varStatus="indexIst">
											<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
											<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
												<c:forEach items="${istanzaHelper.istanzerichiedentis}" var="istanzerichiedenti" varStatus="statusRic">							
													<c:if test="${statusRic.index==0}">
														<tr class="header">
															<td colspan="8"></td>
														</tr>
													</c:if>							
													<c:set var="trStyle" value="odd"/>
													<tr class="${trStyle}" >
														<c:if test="${statusRic.index==0}">
															<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzerichiedentis)}">${istanzerichiedenti.istanza.numeroistanza}</td>
														</c:if>
														<c:if test="${statusRic.index!=0}">								
														</c:if>						
														<td>
															${istanzerichiedenti.richiedente.descrizioneRichiedente} - ${istanzerichiedenti.tiposoggetto.tiposoggetto}
																<c:if test="${not empty istanzerichiedenti.descrsoggetto}">
																	&nbsp;${istanzerichiedenti.descrsoggetto}
																</c:if>
																&nbsp;${istanzerichiedenti.anagrafeCollegata.descrizioneRichiedente}
														</td>
													</tr>	
												</c:forEach>
											</c:if>	
										</c:forEach>
									</c:if>
								</tbody>								
							</table>		
						</div>
					</fieldset>
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI SUI SOGGETTI COLLEGATI-----------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_DETTINFO)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI DETTAGLIO INFORMAZIONI-----------------------------  -->
					<!-- ------------------------------------------------START-----------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_det_info"
								href="javascript:showHidePanel('det_info_table', 'id_link_det_info','<%= WebConstants.CONF_UTENTE_VIS_DETTINFO%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dettaglio_informazioni" />">
								<label for="id_link_det_info"><fmt:message key="label.dettaglio_informazioni" /></label> 
							</a> 			
						</legend>
						<!-- Inizio ciclo della lista delle istanze   -->
						<!-- Gestione del fieldset secondario associato ad ogni istanza -->
						<div id="det_info_table" style="<%=displayTr%>">
						<!-- Tabella che riporta le localizzazioni per ogni istanza cliclata -->		
							<table class="vbg-table">
								<caption></caption>				
								<thead>
									<tr>
										<th><fmt:message key="label.numero_istanza"/></th>
										<th><fmt:message key="label.tipo_informazione"/></th>
										<th><fmt:message key="label.dettaglio_informazione"/></th>
										<th><fmt:message key="label.quantita"/></th>
										<th><fmt:message key="label.note"/></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${iattivitaCommand.visstorico}">
										<tr>
											<fmt:message key="label.storico_attivita"/>
											<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO%></c:set>
											<spring-form:checkbox path="flagStoricoDettaglioInfo" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO}');" />
											<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
										</tr>
									</c:if>					
									<!-- Crea la sola prima riga utilizzando l'istanza contenuta nell'oggetto iattivita con il numero istanza e setta un rowspan per quante sono i soggetti dell'istanza -->
									<c:forEach items="${iattivitaCommand.entity.istanza.istanzeattivitas}" var="istanzeattivita"  varStatus="statusDett">
										<c:set var="trStyle" value="odd"/>							
										<tr class="${trStyle}" >
											<c:if test="${statusDett.index==0}">
												<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzeattivitas)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
											</c:if>
											<c:if test="${statusDett.index!=0}">								
											</c:if>						
											<td>${istanzeattivita.attivita.settori.settore}</td>
											<td>${istanzeattivita.attivita.istat}</td>
											<td>
												${istanzeattivita.attivita.settori.tipiunitamisura.umDescrbreve}&nbsp;
												${istanzeattivita.metriq}
											</td>
											<td>${istanzeattivita.note}</td>
										</tr>
									</c:forEach>					   
									<c:if test="${iattivitaCommand.flagStoricoDettaglioInfo}">
										<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="indexIst">
											<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
											<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
												<c:forEach items="${istanzaHelper.istanzeattivitas}" var="istanzeattivita" varStatus="statusDett">								
													<c:if test="${statusDett.index==0}">
														<tr class="header">
															<td colspan="8"></td>
														</tr>
													</c:if>								
													<c:set var="trStyle" value="odd"/>
													<tr class="${trStyle}">
														<c:if test="${statusDett.index==0}">
															<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzeattivitas)}">${istanzeattivita.istanza.numeroistanza}</td>
														</c:if>
														<c:if test="${statusDett.index!=0}">
															
														</c:if>	
														<td>${istanzeattivita.attivita.settori.settore}</td>
														<td>${istanzeattivita.attivita.istat}</td>
														<td>
															${istanzeattivita.attivita.settori.tipiunitamisura.umDescrbreve}&nbsp;
															${istanzeattivita.metriq}
														</td>
														<td>${istanzeattivita.note}</td>
													</tr>	
												</c:forEach>
											</c:if>	
										</c:forEach>
									</c:if>
								</tbody>									
							</table>
						</div>
					</fieldset>
					<!-- Fine Gestione del fieldset pricipale che descrive la sezione -->
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI DETTAGLIO INFORMAZIONI-----------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ORARI)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI DETTAGLIO ORARI---------------------------------------  -->
					<!-- ------------------------------------------------START-----------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_orari"
								href="javascript:showHidePanel('det_orari_table', 'id_link_orari','<%= WebConstants.CONF_UTENTE_VIS_ORARI%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.orari" />">
								<label for="id_link_orari"><fmt:message key="label.orari" /></label> 
							</a> 			
						</legend>
						<div id="det_orari_table" style="<%=displayTr%>">				
						<!-- Tabella che gestisce il la testata e dettaglio di un tipo orario  -->
							<table class="vbg-table">
								<caption></caption>
								<c:if test="${iattivitaCommand.visstorico}">
									<tr>
										<fmt:message key="label.storico_attivita"/>
										<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_ORARI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_ORARI%></c:set>
										<spring-form:checkbox path="flagStoricoOrari" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_ORARI}');" />
										<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
									</tr>
								</c:if>	
								<c:forEach items="${iattivitaCommand.entity.istanza.orariaperturatestatas}" var="orariaperturatestata" varStatus="statusOrari">
									<!-- Riga che visualizza la testata  di un tipo orario  -->
									<th>														
										<fmt:message key="label.descrizione"/>:
										&nbsp;${orariaperturatestata.tipiorario.toDescrizione}
										&nbsp;(<fmt:message key="label.periodo_dal"/>&nbsp;${orariaperturatestata.periododaTransient}
										&nbsp;<fmt:message key="label.al"/>&nbsp;${orariaperturatestata.periodoaTransient})								
									</th>
									<!-- Righe che visualizzano la testata di un tipo orario  -->
									<tr>
										<td>
											<table class="vbg-table">
												<caption></caption>
												<thead>
													<tr>
														<th><fmt:message key="label.numero_istanza"/></th>
														<th><fmt:message key="label.giorno_settimana"/></th>
														<th><fmt:message key="label.inizio_mattina"/></th>
														<th><fmt:message key="label.fine_mattina"/></th>
														<th><fmt:message key="label.inizio_pomeriggio"/></th>
														<th><fmt:message key="label.fine_pomeriggio"/></th>
														<th><fmt:message key="label.tipologia_orario"/></th>  
													</tr>
												</thead>
												<tbody class="tbody">
													<c:forEach items="${orariaperturatestata.orariaperturas}" var="orariaperturadettaglio" varStatus="statusDettOrari">
														<c:if test="${statusOrari.index ne 0}">
															<c:if test="${statusDettOrari.index==0}">
																<tr class="header">
																</tr>
															</c:if>
														</c:if>
														<c:set var="trStyle" value="odd"/>
															<tr class="${trStyle}">
															<c:if test="${statusDettOrari.index==0}">
																	<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(orariaperturatestata.orariaperturas)}">${orariaperturatestata.istanze.numeroistanza}</td>
															</c:if>
															<c:if test="${statusDettOrari.index!=0}">										
															</c:if>	
															<td>${orariaperturadettaglio.giornisettimana.gsDescrizione}</td>
															<td>${orariaperturadettaglio.oaDalleore}</td>
															<td>${orariaperturadettaglio.oaAlleore}</td>
															<td>${orariaperturadettaglio.oaDalleorepom}</td>
															<td>${orariaperturadettaglio.oaAlleorepom}</td>
															<td>${orariaperturadettaglio.tipiapertura.taDescrizione}</td>
														</tr>	
													</c:forEach>	
												</tbody>
											</table>
										</td>
									</tr>
								</c:forEach>	
								<c:if test="${iattivitaCommand.flagStoricoOrari}">
									<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="orariIndex">
									<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattivita -->
										<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
											<c:if test="${not empty istanzaHelper.orariaperturatestatas}" >
												<c:forEach items="${istanzaHelper.orariaperturatestatas}" var="orariaperturatestata" varStatus="statusOrari">
												<!-- Riga che visualizzano la testata  di un tipo orario  -->
													<tr>
														<td>
															<table class="vbg-table">
																<tr>
																	<td class="header"><fmt:message key="label.descrizione"/>:
																		&nbsp;${orariaperturatestata.tipiorario.toDescrizione}
																		&nbsp;(<fmt:message key="label.periodo_dal"/>&nbsp;${orariaperturatestata.periododaTransient}
																		&nbsp;<fmt:message key="label.al"/>&nbsp;${orariaperturatestata.periodoaTransient})
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- Righe che visualizzano la testata  di un tipo orario  -->
													<tr>
														<td>
															<table class="vbg-table">
																<caption></caption>
																<thead>
																	<tr class="header">
																		<th><fmt:message key="label.numero_istanza"/></th>
																		<th><fmt:message key="label.giorno_settimana"/></th>
																		<th><fmt:message key="label.inizio_mattina"/></th>
																		<th><fmt:message key="label.fine_mattina"/></th>
																		<th><fmt:message key="label.inizio_pomeriggio"/></th>
																		<th><fmt:message key="label.fine_pomeriggio"/></th>
																		<th><fmt:message key="label.tipologia_orario"/></th>  
																	</tr>
																</thead>
																<tbody class="tbody">
																	<c:forEach items="${orariaperturatestata.orariaperturas}" var="orariaperturadettaglio" varStatus="statusDettOrari">
																		<c:if test="${statusOrari.index ne 0}">
																			<c:if test="${statusDettOrari.index==0}">
																				<tr class="header">
																					<td colspan="8"></td>
																				</tr>
																			</c:if>
																		</c:if>
																		<c:set var="trStyle" value="odd"/>
																		<tr class="${trStyle}">
																			<c:if test="${statusDettOrari.index==0}">
																					<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(orariaperturatestata.orariaperturas)}">${orariaperturatestata.istanze.numeroistanza}</td>
																			</c:if>
																			<c:if test="${statusDettOrari.index!=0}">								
																			</c:if>	
																			<td>${orariaperturadettaglio.giornisettimana.gsDescrizione}</td>
																			<td>${orariaperturadettaglio.oaDalleore}</td>
																			<td>${orariaperturadettaglio.oaAlleore}</td>
																			<td>${orariaperturadettaglio.oaDalleorepom}</td>
																			<td>${orariaperturadettaglio.oaAlleorepom}</td>
																			<td>${orariaperturadettaglio.tipiapertura.taDescrizione}</td>
																		</tr>	
																	</c:forEach>	
																</tbody>
															</table>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</c:if>
									</c:forEach>
								</c:if>
							</table>
							<!-- Fine Tabella che gestisce il la testata di un tipo orario  -->			
						</div>															
					</fieldset>
					<!--Fine Gestione del fieldset pricipale che descrive la sezione -->
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI DETTAGLIO ORARI---------------------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_AUTORIZZAZIONI)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI AUTORIZZAZIONI---------------------------------------  -->
					<!-- ------------------------------------------------START-----------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_autoriz"
								href="javascript:showHidePanel('autorizz_table', 'id_link_autoriz','<%= WebConstants.CONF_UTENTE_VIS_AUTORIZZAZIONI%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.autorizzazioni" />">
								<label for="id_link_autoriz"><fmt:message key="label.autorizzazioni" /></label> 
							</a>						
						</legend>
						<!-- Inizio ciclo della lista delle istanze   -->		
						<!-- Gestione del fieldset secondario associato ad ogni istanza -->
						<div id="autorizz_table" style="<%=displayTr%>">		
							<table class="vbg-table">
								<caption></caption>
								<thead>
									<tr>
										<th><fmt:message key="label.numero_istanza"/></th>
										<th><fmt:message key="label.numero"/></th>
										<th><fmt:message key="label.data"/></th>
										<th><fmt:message key="label.comune"/></th>
										<th><fmt:message key="label.registro"/></th>
										<th ><fmt:message key="label.stato"/></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${iattivitaCommand.visstorico}">
										<tr>
											<fmt:message key="label.storico_attivita"/>
											<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI%></c:set>
											<spring-form:checkbox path="flagStoricoAutorizzazioni" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI}');" />
																	
											<fmt:message key="label.raggruppa_per_istanza"/>
											<c:set var="_CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA"><%= WebConstants.CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA%></c:set>
											<spring-form:checkbox path="flagRaggruppaAutPerIstanza" id="id_CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA" onclick="visualizzaRaggruppatoIstanza(this,'${_CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA}');"/>
											<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
											<label for="id_CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA"></label>
										</tr>
									</c:if>				  
									<%--  <c:if test="${iattivitaCommand.flagStoricoAutorizzazioni}"> --%>
									<c:forEach items="${istanzeAttivitaAutorizzazioniHelpers}" var="istanzeAttivita" varStatus="autIndex">
										<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattivita -->
										<%-- 
										<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
										--%>
											<c:forEach items="${istanzeAttivita.autorizzazioniAttivitaHelpers}" var="autorizzazioni" varStatus="statusAut">
											<%-- 	<c:if test="${empty autorizzazioni.autorizzazioniConcessionisForFkAutconcAutatt}"> --%>								
													<c:if test="${statusAut.index==0}">
														<tr class="header">
															<td colspan="8"></td>
														</tr>
													</c:if>							    
												<c:set var="trStyle" value="odd"/>
												<tr class="${trStyle}">								
													<c:if test="${statusAut.index==0 && iattivitaCommand.flagRaggruppaAutPerIstanza}">
														<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzeAttivita.autorizzazioniAttivitaHelpers)}">${autorizzazioni.numeroIstanza}</td> 
													</c:if>								
													<c:if test="${statusAut.index!=0}"></c:if>	
													<c:if test="${!iattivitaCommand.flagRaggruppaAutPerIstanza}">
														<td><b>${autorizzazioni.numeroIstanza}</b></td>
													</c:if>
													<%-- <td>${autorizzazioni.numeroAutorizzazione}</td> --%>
													<td>
														<c:choose>
															<c:when test="${autorizzazioni.subentrata eq true}">
																&nbsp;${autorizzazioni.numeroAutorizzazione}
															</c:when>
															<c:otherwise> 
																<a href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?codiceIstanza=${autorizzazioni.codiceIstanza}&codice=${autorizzazioni.codiceAutorizzazione}', '')">${autorizzazioni.numeroAutorizzazione}</a>
															</c:otherwise>
														</c:choose> 
													</td>
													<td><fmt:formatDate value="${autorizzazioni.dataAutorizzazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
													<td>${autorizzazioni.comune}</td>
													<td>${autorizzazioni.registroAutorizzazione}</td>
													<td>
														<c:if test="${autorizzazioni.stato eq true}"><fmt:message key="label.attiva" /></c:if>
														<c:if test="${autorizzazioni.stato ne true}"><fmt:message key="label.cessata" /> 
															<fmt:formatDate value="${autorizzazioni.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
															<c:if test="${autorizzazioni.subentrata eq true}">
																<b><label title="<fmt:message key="label.subentrata" />">[<fmt:message key="label.s" />]</label></b>	
															</c:if> 
														</c:if>
													</td>
												</tr>
											<%-- </c:if>	--%>
											</c:forEach>
										<%-- </c:if> --%>							
									</c:forEach>
									<%-- </c:if> --%>
								</tbody>				
							</table>		
						</div>
					</fieldset>				
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI AUTORIZZAZIONI---------------------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_CONCESSIONI)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI CONCESSIONI---------------------------------------  -->
					<!-- ------------------------------------------------START-----------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_conc"
								href="javascript:showHidePanel('conc_table', 'id_link_conc','<%= WebConstants.CONF_UTENTE_VIS_CONCESSIONI%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.concessioni" />">
								<label for="id_link_conc"><fmt:message key="label.concessioni" /></label> 
							</a>						
						</legend>
						<div id="conc_table" style="<%=displayTr%>">		
							<table class="vbg-table">
								<caption></caption>
								<thead>
									<tr>
										<th><fmt:message key="label.numero_istanza"/></th>
										<th><fmt:message key="label.numero"/></th>
										<th><fmt:message key="label.data"/></th>
										<th><fmt:message key="label.comune"/></th>
										<th><fmt:message key="label.registro"/></th>
										<th><fmt:message key="label.stato"/></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${iattivitaCommand.visstorico}">
										<tr>
											<fmt:message key="label.storico_attivita"/>
											<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI%></c:set>
											<spring-form:checkbox path="flagStoricoConcessioni" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI}');" />
											<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
											
											<fmt:message key="label.raggruppa_per_istanza"/>
											<c:set var="_CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA"><%= WebConstants.CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA %></c:set>
											<spring-form:checkbox path="flagRaggruppaConcPerIstanza" id="id_CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA" onclick="visualizzaRaggruppatoIstanza(this,'${_CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA}');"/>
											<label for="id_CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA"></label>
										</tr>
									</c:if>
									<c:forEach items="${istanzeAttivitaConcHelpers}" var="istanzeAttivita" varStatus="concIndex">
										<c:forEach items="${istanzeAttivita.autorizzazioniAttivitaHelpers}" var="autorizzazioni" varStatus="statusConc">
											<c:if test="${statusConc.index == 0}">
												<tr class="header">
													<td colspan="8"></td>
												</tr>
											</c:if>
											<c:set var="trStyle" value="odd"/>
											<tr class="${trStyle}">
												<c:if test="${statusConc.index ==0 && iattivitaCommand.flagRaggruppaConcPerIstanza}">
													<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzeAttivita.autorizzazioniAttivitaHelpers)}">${autorizzazioni.numeroIstanza}</td> 
													</c:if>
												<c:if test="${statusConc.index !=0}"></c:if>	 
												<c:if test="${!iattivitaCommand.flagRaggruppaConcPerIstanza}">
													<td><b>${autorizzazioni.numeroIstanza}</b></td>
												</c:if>
												<td><a href="javascript:historySet('${_urlback}', '../autorizzazioni/viewConcessione.htm?codiceAutorizzazione=${autorizzazioni.codiceAutorizzazione}&codiceIstanza=${autorizzazioni.codiceIstanza}', '')">${autorizzazioni.numeroAutorizzazione}</a></td>					
												<td><fmt:formatDate value="${autorizzazioni.dataAutorizzazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
												<td>${autorizzazioni.comune}</td>
												<td>${autorizzazioni.registroAutorizzazione}</td>
												<td>
												<c:if test="${autorizzazioni.stato eq true}"><fmt:message key="label.attiva" /></c:if>
												<c:if test="${autorizzazioni.stato ne true}"><fmt:message key="label.cessata" /> 
													<fmt:formatDate value="${autorizzazioni.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
												</c:if>
												</td>
											</tr>											
										</c:forEach>
									</c:forEach>						
								</tbody>
							</table>			
						</div>
					</fieldset>					
					<!-- Fine del fieldset pricipale che descrive la sezione -->							
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI CONCESSIONI---------------------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ENDOPROCEDIMENTI)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI ENDO PROCEDIMENTI------------------------------------  -->
					<!-- ------------------------------------------------START---------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_endo"
								href="javascript:showHidePanel('endo_table', 'id_link_endo','<%= WebConstants.CONF_UTENTE_VIS_ENDOPROCEDIMENTI%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.endoprocedimenti" />">
								<label for="id_link_endo"><fmt:message key="label.endoprocedimenti" /></label> 
							</a>						
						</legend>
						<!-- Inizio ciclo della lista delle istanze   -->			
						<!-- Gestione del fieldset secondario associato ad ogni istanza -->
						<div id="endo_table" style="<%= displayTr%>">			
							<table class="vbg-table">
								<caption></caption>
								<thead>
									<tr>
										<th><fmt:message key="label.numero_istanza"/></th>
										<th><fmt:message key="label.endoprocedimento"/></th>						
										<th><fmt:message key="label.famiglia_endo"/></th>
										<th><fmt:message key="label.categoria_endo"/></th>
										<th><fmt:message key="label.data"/></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${iattivitaCommand.visstorico}">
										<tr>
											<fmt:message key="label.storico_attivita"/>
											<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI%></c:set>
											<spring-form:checkbox path="flagStoricoEndoprocedimenti" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI}');" />
											<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
										</tr>
									</c:if>						 
									<c:forEach items="${iattivitaCommand.entity.istanza.istanzeprocedimentis}" var="endos"  varStatus="statusEndo">
										<c:set var="trStyle" value="odd"/>							
											<c:set var="trStyle" value="odd"/>
												<tr class="${trStyle}">
												<c:if test="${statusEndo.index==0}">
													<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzeprocedimentis)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
												</c:if>
												<c:if test="${statusEndo.index!=0}">
												</c:if>	
													<td>${endos.inventarioprocedimenti.procedimento}</td>
													<td>${endos.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo}</td>
													<td>${endos.inventarioprocedimenti.tipoendo.tipo}</td>
													<td><fmt:formatDate value="${endos.dataattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
												</tr>
									</c:forEach>						
									<c:if test="${iattivitaCommand.flagStoricoEndoprocedimenti}">
										<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="endoIndex">
											<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
											<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
												<c:forEach items="${istanzaHelper.istanzeprocedimentis}" var="endos" varStatus="statusEndo">											
													<c:if test="${statusEndo.index==0}">
														<tr class="header">
															<td colspan="8"></td>
														</tr>
													</c:if>											
													<c:set var="trStyle" value="odd"/>
													<tr class="${trStyle}">
														<c:if test="${statusEndo.index==0}">
															<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzeprocedimentis)}">${endos.istanza.numeroistanza}</td>
														</c:if>
														<c:if test="${statusEndo.index!=0}">
														</c:if>	
															<td>${endos.inventarioprocedimenti.procedimento}</td>
															<td>${endos.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo}</td>
															<td>${endos.inventarioprocedimenti.tipoendo.tipo}</td>
															<td><fmt:formatDate value="${endos.dataattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
													</tr>											
												</c:forEach>
											</c:if>	
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>			
					</fieldset>							
					<br class="clear"/>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI ENDO PROCEDIMENTI------------------------------------  -->
					<!-- ------------------------------------------------END-----------------------------------------------------  -->
					<%
						displayTr = "display:none;";
						styleTr = "";
						//gestisce la visualizzazione della tabella altri dati
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ONERI)).equals("1")) {
							displayTr = "";
							styleTr="sezioneDatiMeno";
						} else {
							displayTr = "display:none;";
							styleTr="sezioneDatiPiu";
						}
					%>
					<!-- --------------------------------------------------------------------------------------------------------  -->
					<!-- --------------------------------------INFORMAZIONI ENDO ONERI------------------------------------  -->
					<!-- ------------------------------------------------START---------------------------------------------------  -->
					<!-- Gestione del fieldset pricipale che descrive la sezione -->
					<fieldset>
						<legend>
							<a class="<%= styleTr%>"
								id="id_link_oneri"
								href="javascript:showHidePanel('oneri_table', 'id_link_oneri','<%= WebConstants.CONF_UTENTE_VIS_ONERI%>','${pageContext.request.contextPath}/images/','div');"
								title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.oneri" />">
								<label for="id_link_oneri"><fmt:message key="label.oneri" /></label> 
							</a>						
						</legend>
						<div  id="oneri_table" style="<%= displayTr%>">			
							<table class="vbg-table">
								<caption></caption>
									<thead>
										<tr class="header">
											<th><fmt:message key="label.numero_istanza"/></th>
											<th><fmt:message key="label.causale"/></th>
											<th><fmt:message key="label.importo"/></th>
											<th><fmt:message key="label.data_registrazione"/></th>
											<th><fmt:message key="label.data_scadenza"/></th>
											<th><fmt:message key="label.data_pagamento"/></th>
										</tr>
									</thead>
									<tbody class="tbody">
										<c:if test="${iattivitaCommand.visstorico}">
											<tr>
												<fmt:message key="label.storico_attivita"/>
												<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_ONERI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_ONERI%></c:set>
												<spring-form:checkbox path="flagStoricoOneri" id="hover-text" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_ONERI}');" />
												<label class="tooltip-text" for="hover-text"><fmt:message key="label.attivita_help_visualizza_storico_sezione" /></label>
											</tr>
										</c:if>						
										<c:forEach items="${iattivitaCommand.entity.istanza.istanzeoneris}" var="oneri"  varStatus="statusOneri">
											<c:set var="trStyle" value="odd"/>
											<tr class="${trStyle}">
												<c:if test="${statusOneri.index==0}">
													<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzeoneris)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
												</c:if>
												<c:if test="${statusOneri.index!=0}">
												</c:if>	
												<td>${oneri.tipicausalioneri.coDescrizione}</td>
												<td>${oneri.prezzo}</td>
												<td><fmt:formatDate value="${oneri.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
												<td><fmt:formatDate value="${oneri.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
												<td><fmt:formatDate value="${oneri.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
											</tr>
										</c:forEach>						 
										<c:if test="${iattivitaCommand.flagStoricoOneri}">
											<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="oneriIndex">
											<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
												<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
													<c:forEach items="${istanzaHelper.istanzeoneris}"  var="oneri" varStatus="statusOneri">
														<c:if test="${statusOneri.index==0}">
															<tr class="header">
																<td colspan="8"></td>
															</tr>
														</c:if>							   
														<c:set var="trStyle" value="odd"/>
														<tr class="${trStyle}">
															<c:if test="${statusOneri.index==0}">
																<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzeoneris)}">${oneri.istanza.numeroistanza}</td>
															</c:if>
															<c:if test="${statusOneri.index!=0}">
															</c:if>	
																<td>${oneri.tipicausalioneri.coDescrizione}</td>
																<td>${oneri.prezzo}</td>
																<td><fmt:formatDate value="${oneri.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
																<td><fmt:formatDate value="${oneri.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
																<td><fmt:formatDate value="${oneri.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
														</tr>
													</c:forEach>
												</c:if>	
											</c:forEach>
										</c:if>
									</tbody>
								</table>		 
							</div>
						</fieldset>					
						<br class="clear"/>
						<!-- --------------------------------------------------------------------------------------------------------  -->
						<!-- --------------------------------------INFORMAZIONI ENDO PROCEDIMENTI------------------------------------  -->
						<!-- ------------------------------------------------END---------------------------------------------------  -->
						<%
							displayTr = "display:none;";
							styleTr = "";
							//gestisce la visualizzazione della tabella altri dati
							if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ISTANZE)).equals("1")) {
								displayTr = "";
								styleTr="sezioneDatiMeno";
							} else {
								displayTr = "display:none;";
								styleTr="sezioneDatiPiu";
							}
						%>
						<!-- --------------------------------------------------------------------------------------------------------  -->
						<!-- --------------------------------------INFORMAZIONI ISTANZE------------------------------------  -->
						<!-- ----------------------------------------------START-------------------------------------------  -->
						<fieldset>
							<legend>
									<a class="<%= styleTr%>"
									id="id_link_istanze"
									href="javascript:showHidePanel('istanze_table', 'id_link_istanze','<%= WebConstants.CONF_UTENTE_VIS_ISTANZE%>','${pageContext.request.contextPath}/images/','div');"
									title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.istanze" />"> 
									<label for="id_link_istanze"><fmt:message key="label.istanze" /></label> 
								</a>							
							</legend>
							<!-- La variabile "page" istanzestoricos recupera le istanze che sono legate all'attività sia che vogliamo visualizzare
							lo storico delle istanze che la compongono ,sia che vogliamo visualizzare le informazioni solo dell'ultima istanza legata
							all'attività  
							-->
							<c:choose>
								<c:when test="${iattivitaCommand.visstorico}">
									<c:set value="${iattivitaCommand.istanzeHelper.istanzes}" var="istanzestoricos" scope="page"></c:set>
								</c:when>
								<c:otherwise>
									<c:set value="${iattivitaCommand.istanzeHelper.storicoistanzes}" var="istanzestoricos" scope="page"></c:set>
								</c:otherwise>
							</c:choose>
							<div id="istanze_table" style="<%=displayTr%>">
								<table class="vbg-table">
									<caption></caption>
									<thead>
										<tr class="header">
											<th><fmt:message key="label.numero"/></th>
											<th><fmt:message key="label.data"/></th>
											<th><fmt:message key="label.data_validita"/></th>
											<th><fmt:message key="label.richiedente"/></th>
											<th><fmt:message key="label.richiedente_storico"/></th>
											<th><fmt:message key="label.indirizzo"/></th>
											<th style="width: 180px;"><fmt:message key="label.posizione_in_archivio" /></th>
											<th><fmt:message key="label.intervento"/></th>
											<th><fmt:message key="label.procedura"/></th>
											<th><fmt:message key="label.stato"/></th>
											<th style="width: 1%;"><fmt:message key="label.azione"/></th>
											<th style="width: 180px;"><fmt:message key="label.operatore"/></th>
											<th><fmt:message key="label.ordine"/></th>
											<th><fmt:message key="label.info"/> </th>
										</tr>
									</thead>
									<tbody class="tbody">			
										<c:forEach items="${istanzestoricos}" var="istanzaHelper" varStatus="status">										
											<tr class="righe" data-codice="${istanzaHelper.id.codice}">
												<td>
													<a href="javascript:historySet('${_urlback}', '../istanze/view.htm?codice=${istanzaHelper.id.codice}&software=${istanzaHelper.software.codice}', '')">${istanzaHelper.numeroistanza}</a>
												</td>													
												<td><fmt:formatDate value="${istanzaHelper.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
												<td><fmt:formatDate value="${istanzaHelper.datavalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
												<td>${istanzaHelper.transientRichiedenteQualitaAzienda}</td>
												<td>${istanzaHelper.transientRichiedenteQualitaAziendaStorico}</td>												
												<td>${istanzaHelper.transientLocalizzazionePrimario}</td>												
												<td>
													<input class="input_posArch" id="pos_archivio_txt_${istanzaHelper.id.codice}" type="text" value="${istanzaHelper.posizionearchivio}" readonly>
													<span class="modificaPosArchivio_${istanzaHelper.id.codice}" style="display:none;">
														<a class="aggiornaPosArchivio" id="aggiornaPosArchivioId_${istanzaHelper.id.codice}" href="#">
															<i class="fa fa-save"></i><fmt:message key="label.salva" />
														</a>
													</span>
													<span id="result_${istanzaHelper.id.codice}" style="display: none"></span>
												</td>
												<td>${istanzaHelper.alberoproc.vwAlberoproc.scDescrizione}</td>
												<td>${istanzaHelper.procedura.procedura}</td>
												<td>${istanzaHelper.chiusura.stato}</td>
												<td>${istanzaHelper.azione}</td>
												<td>
													${istanzaHelper.responsabile.responsabile} <input type="hidden" value="${istanzaHelper.responsabile.id.codice}" id="responsabileId_${istanzaHelper.id.codice}" />
													<input type="hidden" id="responsabile_${istanzaHelper.id.codice}" value="${istanzaHelper.responsabile.responsabile}"/>
													<span class="modificaOperatore_${istanzaHelper.id.codice}" style="display: none;">
														<a class="cambiaOperatore" id="cambiaOperatoreId_${istanzaHelper.id.codice}" href="#" title="Modifica">
															<i class="fa fa-pencil"></i><!-- <fmt:message key="label.modifica" /> -->
														</a>
													</span>
												</td>
												<c:if test="${status.index!=fn:length(istanzestoricos)-1}"> 
													<td>
														<a href="javascript:copia('TUTTI', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.copia_tutto"/>"><fmt:message key="label.T"/></a>
														<a href="javascript:copia('SOGGETTI_COLLEGATI', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.soggetti_collegati"/>"><fmt:message key="label.S"/></a>
														<a href="javascript:copia('MAPPALI', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.mappali"/>"><fmt:message key="label.M"/></a>
														<a href="javascript:copia('DETTAGLIO_INFORMAZIONI', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.dettaglio_info"/>"><fmt:message key="label.D"/></a>
														<a href="javascript:copia('ORARI_APERTURA', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.orari"/>"><fmt:message key="label.O"/></a>
														<a href="javascript:copia('LOCALIZZAZIONI', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.localizzazioni"/>"><fmt:message key="label.L"/></a>
														<a href="javascript:copia('SCHEDE_DINAMICHE', ${istanzaHelper.id.codice}, ${status.index});" title="<fmt:message key="label.schede_dell_istanza"/>"><fmt:message key="label.S"/></a>
													</td>
												</c:if>
												<c:if test="${status.index==fn:length(istanzestoricos)-1}">
													<td>&nbsp;</td>
												</c:if>
												<td>
													<c:if test="${fn:length(istanzestoricos)>1}">
														<fmt:parseDate value="${istanzestoricos[status.index].datavalidita}" type="DATE" pattern="yyyy-MM-dd"    var="formatedDate1" scope="page"/>
														<fmt:parseDate value="${istanzestoricos[status.index+1].datavalidita}" type="DATE" pattern="yyyy-MM-dd"  var="formatedDate2" scope="page"/>
														<fmt:parseDate value="${istanzestoricos[status.index-1].datavalidita}" type="DATE" pattern="yyyy-MM-dd"  var="formatedDate3" scope="page"/>
													<c:choose>
														<c:when test="${(istanzestoricos[status.index].datavalidita!=null ) && (formatedDate1 eq formatedDate2 or formatedDate1 eq formatedDate3)}">									
															<input type="text"  value="${istanzaHelper.attivitaOrdine}" name="ordineIstanze" size="4"></input>
															<input type="hidden" value="${istanzaHelper.id.codice}" name="codiceIstanze"></input>
															<c:set scope="page" value="true" var="viewSalvaOrdine"></c:set>
															<c:if test="${formatedDate3 == formatedDate1}">
																<a class="upColumn" href="scambiaOrdine.htm?idAttivita=${iattivitaCommand.entity.id.codice}&codiceIstanzaPrec=${istanzestoricos[status.index-1].id.codice}&codiceIstanzaSuc=${istanzestoricos[status.index].id.codice}" title="<fmt:message key="label.up" /> ">
																	<label><fmt:message key="label.azioni" /></label>
																</a>
															</c:if>
															<c:if test="${formatedDate1 == formatedDate2 }">
																<a class="downColumn" href="scambiaOrdine.htm?idAttivita=${iattivitaCommand.entity.id.codice}&codiceIstanzaPrec=${istanzestoricos[status.index].id.codice}&codiceIstanzaSuc=${istanzestoricos[status.index+1].id.codice}" title="<fmt:message key="label.down" /> ">
																	<label><fmt:message key="label.azioni" /></label>
																</a>
															</c:if>
														</c:when>
														<c:when test="${istanzestoricos[status.index].datavalidita==null && istanzaDataValiditaNull>1}">
															<input type="text"  value="${istanzaHelper.attivitaOrdine}" name="ordineIstanze" size="4"></input>
															<input type="hidden" value="${istanzaHelper.id.codice}" name="codiceIstanze"></input>
															<c:set scope="page" value="true" var="viewSalvaOrdine"></c:set>
															<c:if test="${istanzestoricos[status.index-1].datavalidita==istanzestoricos[status.index].datavalidita}">
																<a class="upColumn" href="upColumn.htm?codiceIstanzaSup=${istanzestoricos[status.index-1].id.codice}&codiceIstanza=${istanzestoricos[status.index].id.codice}" title="<fmt:message key="label.up" /> ">
																	<label><fmt:message key="label.azioni" /></label>
																</a>
															</c:if>
															<c:if test="${status.index!=fn:length(istanzestoricos)-1}">
																<a class="downColumn" href="downColumn.htm?codiceIstanza=${istanzestoricos[status.index].id.codice}&codiceIstanzaInf=${istanzestoricos[status.index+1].id.codice}" title="<fmt:message key="label.down" /> ">
																	<label><fmt:message key="label.azioni" /></label>
																</a>
															</c:if>
														</c:when> 
														<c:otherwise>
															<input type="hidden"  value="0" name="ordineIstanze" size="4"></input>
															<input type="hidden" value="${istanzaHelper.id.codice}" name="codiceIstanze"></input>
														</c:otherwise>
													</c:choose>
													</c:if>		
												</td>
												<td>
													<a class="info_istanza" id="info_istanza_${istanzaHelper.id.codice}" href="#">
														<i class="fa fa-info"></i>
													</a>
												</td>
											</tr>										
										</c:forEach> 
										<tr>
											<td colspan="13">&nbsp;</td>
											<td valign="top">							
												<%-- <init:help idHelp="helpSalvataggio" textKey="label.aggiorna_ordine_istanze" />--%>
												<a class="aggiornaColumn" o id="mioRefId" href="javascript:doSubmit('updateOrdineIstanza.htm','',document.inviodati)" >
														<label><fmt:message key="label.aggiorna" /></label>
												</a>
												<script type="text/javascript">
													new dijit.Tooltip({
														connectId: ["mioRefId"],									   
														label: "<fmt:message key="label.aggiorna_ordine_istanze" />"
													});
												</script>							
											</td>
										</tr>																			
									</tbody>
								</table>
							</div>
							<vbg-modal id="modal-operatore">
								<div slot='body' id='popup_operatore'>
									<h1>Modifica operatore</h1>										
									<div class="form-group">
										<label>Operatore</label>
										<input type="text" id="modalOperatoreId" value="" oninput="cercaOperatore(value)"/>
										<input type="hidden" id="modalOperatoreIdHidden" />
										<input type="hidden" id="modalCodiceIstanzaId" value="" />
										<div id="operatoreList" class="autocomplete" style="display: none;"></div>
									</div>
								</div>
								<div slot='footer'>
									<div class="btn btn-primary" id="saveButtonModalOperatore"><fmt:message key="button.save"/></div>
									<div class="btn btn-secondary" id="closeButtonModalOperatore"><fmt:message key="button.close"/></div>            
								</div>		
							</vbg-modal>					
						</fieldset>	
						<!-- --------------------------------------------------------------------------------------------------------  -->
						<!-- --------------------------------------INFORMAZIONI ISTANZE------------------------------------  -->
						<!-- -----------------------------------------------END--------------------------------------------  -->
				</div>																
			</spring-form:form>
		</div>
		<div class="form-button">	
			<c:if test="${iattivitaCommand.entity.id.codice!=null}">
				<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>		
				<%-- SCHEDE --%>
				<c:set var="_URL_BACK" value="../iattivita/view.htm?codice=${iattivitaCommand.entity.id.codice}" scope="page"/>			
				<%							
					pageContext.setAttribute("URL_ATTIVITADYN2_MODELLI", BackofficeNETConstants.getURL_ATTIVITA_DYN2_MODELLI());
				%>
				<c:set var="_URL_ATTIVITADYN2_MODELLI" value="${URL_ATTIVITADYN2_MODELLI}?CodiceAttivita=${iattivitaCommand.entity.id.codice}"/>			
				<c:set var="_URL_ATTIVITADYN2_MODELLI" value="${inite:linkschedeattivita(pageContext.request, _URL_BACK, null, false, iattivitaCommand.entity.id.codice)}" />
				
				<a class="btn btn-primary" href="${_URL_ATTIVITADYN2_MODELLI}"><fmt:message key="button.schede" /></a>
				<c:if test="${isSchedePresenti eq false}">
					<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
				</c:if>
				<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../iattivita/viewStoricoSnapshot.htm?codiceattivita=${iattivitaCommand.entity.id.codice}','')"><fmt:message key="button.storico" /></a>
				
				<c:if test="${isSchedePresenti eq true}">
					<a class="btn btn-primary" href="javascript:cancellaIattivitaConfirm();"><fmt:message key="button.delete" /></a>
						<%-- DIV che contiene un dialog che viene aperto alla cancellazione di un attivita quando ad
							essa sono collegate delle schede  --%>
						<div dojoType="dijit.Dialog" id="cancellaIattivitaDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />" style="display: none;">
							<input type="checkbox" id="cancellazionedocistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
							<label for="cancellazioneiattivitachk_id">
								<fmt:message key="label.messaggio_cancellazione_iattivita_con_schede_dinamiche"/><br /><br />
								<fmt:message key="label.messaggio_cancellazione_attivita_per_operatore">
									<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
								</fmt:message>
							</label>
							<div id="functions">
								<ul>
									<li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
									<li><a href="javascript:void 0" onclick="dijit.byId('cancellaIattivitaDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
								</ul>
							</div>
							<br class="clear" />										
						</div>					
						<script type="text/javascript">
							function cancellaIattivitaConfirm(){
								dijit.byId('cancellaIattivitaDialogDiv').show();
							}	
						</script>
				</c:if>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		</div>
		<vbg-dettaglio-istanza id="vbg-dettaglio-istanza"></vbg-dettaglio-istanza>
		<script type="text/javascript">	
		
		const catenaIstanze = new Map([
			<c:forEach items="${istanzestoricos}" var="istanzaHelper" varStatus="status">	
				[${status.index}, ${istanzaHelper.id.codice}],
			</c:forEach>
				  ]);
		
			function copia(tabella, codiceIstanza, indice){
				
				let url = ''
				switch(tabella){
				case 'TUTTI':
					url = '../iattivita/copiaTutti.htm';
					break;
				case 'SOGGETTI_COLLEGATI':
					url = '../iattivita/copiaSoggettiCollegati.htm';
					break;
				case 'MAPPALI':
					url = '../iattivita/copiaMappali.htm';
					break;
				case 'DETTAGLIO_INFORMAZIONI':
					url = '../iattivita/copiaDettaglioInformazioni.htm';
					break;
				case 'ORARI_APERTURA':					
					url = '../iattivita/copiaOrariApertura.htm';
					break;
				case 'LOCALIZZAZIONI':
					url = '../iattivita/copiaLocalizzazioni.htm';
					break;
				case 'SCHEDE_DINAMICHE':
					url = '../iattivita/copiaSchedeDinamiche.htm';
					break;
				}
				console.log("catenaIstanze: "+catenaIstanze);
				let codiceIstanzaSorgente = catenaIstanze.get(++indice);
				console.log("indice: "+ indice +", codiceIstanza: " + codiceIstanza + ", codiceIstanzaSorgente: "+codiceIstanzaSorgente)
				url += '?codiceIstanza=' + codiceIstanza + '&codiceIstanzaSorgente='+codiceIstanzaSorgente+'&codice=${iattivitaCommand.entity.id.codice}'; 
				doSubmit(url,'', document.inviodati);
				
				
			}
		
			function value()
			{
				var a=document.getElementById('id_data_validita0').value;
				alert(a);
			}
			
			var clickedOnce = false;
			function visualizzaStorico(chkbox,nomepreferenza){
				if(!clickedOnce){
					clickedOnce = true;
					salvaPreferenza( nomepreferenza , chkbox );
					setTimeout("doSubmit('view.htm?codice=${iattivitaCommand.entity.id.codice}','')", 300);
				}
			}
			
			function visualizzaRaggruppatoIstanza(chkbox,nomepreferenza){
				if(!clickedOnce){
					clickedOnce = true;
					salvaPreferenza( nomepreferenza , chkbox );
					setTimeout("doSubmit('view.htm?codice=${iattivitaCommand.entity.id.codice}','')", 300);
				}
			}			
			
			function salvaPreferenza(nomeparametro, objchk){
				var valore = "0";	
				if(objchk.checked == true){
					valore="1";	
				}
				saveUserPreference(nomeparametro, valore);
			}			

			vbg.ready(() => {	

				document.querySelectorAll('.righe').forEach(async (el) =>  {
					let codice = el.dataset.codice;					
					let response = await fetch('../iattivita/ajaxAbilitaModificaPosArchivio.htm?codiceIstanza='+codice);
					let messaggio = await response.text();						
					
					if(messaggio=='OK'){
						document.querySelector('.modificaPosArchivio_'+codice).style.display = 'inline-block';
						document.querySelector('#pos_archivio_txt_'+codice).readOnly = false;
						document.querySelector('.modificaOperatore_'+codice).style.display = 'inline-block';
					}
				});
				
	            if(${cartograficoAttivo} === true){
	                
	                let buttonMostraInMappa = document.querySelector('.mostra-mappa');
	                buttonMostraInMappa.addEventListener('click',async (e) => {
	                    e.preventDefault();
	                    window.vbg.mostraModalCaricamento();
	                    try
	                    {
	                        await visualizzaMappa();
	                    }
	                    catch(error) {
	                        console.log(error);
	                        alert('Si sono verificati errori durante l\'apertura della mappa: ' + error);
	                    }
	                    window.vbg.nascondiModalCaricamento();
	                });
	                
	            }
			});
			
		    async function visualizzaMappa(){
                
		    	
		    	
                const response = await fetch('../istanzestradariocartografico/jsonMostraSingolaAttivitaInMappa.htm?idAttivita=${iattivitaCommand.entity.id.codice}&codiceIstanza=', {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                    }
                });
                
                const jsResponse = await response.json();
                
                if(jsResponse.esito.esito == "KO"){
                    throw new Error(jsResponse.esito.exceptions.join(' - '));
                }
                
                
                if( jsResponse.method === "GET" ){
                    location.replace(jsResponse.url);
                    return;
                }
                if( jsResponse.method === "POST" ){
                    const formMappa = document.createElement("form");
                    formMappa.method = "POST";
                    formMappa.action = jsResponse.url;
                    console.log(jsResponse.body);
                    jsResponse.body.forEach(item => {
                        const input = document.createElement("input");
                        input.type = "hidden";
                        input.name = item.chiave;
                        input.value = item.valore;
                        formMappa.appendChild(input);
                    });
                    
                    document.body.appendChild(formMappa);
                    formMappa.submit();
                    return;
                }
            }

			document.querySelectorAll('.aggiornaPosArchivio').forEach( el => {
				el.addEventListener('click', async function(event) {
					event.preventDefault();
					let codice = Number( el.getAttribute('id').substring(22,28));
					let pos_archivio = document.querySelector('#pos_archivio_txt_'+codice).value;
					console.log(codice,pos_archivio);					
					let url = 'ajaxUpdateIstanza.htm?codiceIstanza='+codice+'&posArchivio='+pos_archivio;
					let response = await fetch('../iattivita/ajaxUpdateIstanza.htm?codiceIstanza='+codice+'&posArchivio='+pos_archivio);
					let messaggio = await response.text();
					console.log(messaggio);
					if(messaggio=="Ok"){						
						let messagioDatoAggiornato = document.querySelector('#result_'+codice);
						messagioDatoAggiornato.innerText = "Dato aggiornato";
						messagioDatoAggiornato.addClassName('success_header');
						messagioDatoAggiornato.style.display= '';						
						setTimeout(()=>{ messagioDatoAggiornato.style.display="none"; }, 2000);
						
					}else{
						alert(messaggio);
					}
				
				});

			});

			document.querySelectorAll('.cambiaOperatore').forEach(el =>{
				el.addEventListener('click', async (ev) =>{

					let codiceIstanza = ev.currentTarget.id.substring(18,28);
					let codiceOperatore = document.querySelector('#responsabileId_'+codiceIstanza).value;
					let operatore = document.querySelector('#responsabile_'+codiceIstanza).value;
					modalOperatore.querySelector('#modalOperatoreId').value = operatore;
					modalOperatore.querySelector('#modalCodiceIstanzaId').value = codiceIstanza;
					modalOperatore.open();
					console.log(codiceIstanza, ' - ', codiceOperatore);
				});				

			});

			let modalOperatore = document.querySelector('#modal-operatore');

			document.getElementById('closeButtonModalOperatore').addEventListener('click', (e)=>{
				modalOperatore.close();	
				modalOperatore.querySelector('#operatoreList').innerHTML = messaggio;
			});
			
			document.getElementById('saveButtonModalOperatore').addEventListener('click', (e)=>{

				e.preventDefault();
				let codiceIstanza = modalOperatore.querySelector('#modalCodiceIstanzaId').value;
				let codiceOperatore = modalOperatore.querySelector('#modalOperatoreIdHidden').value;
				salvaOperatore(codiceIstanza, codiceOperatore);

			});			

			async function cercaOperatore(text){

				console.log('Cerca operatore...',text);
				let response = await fetch('../ajax/findResponsabili.htm?textToSearch='+text);
				let messaggio = await response.text();				
				modalOperatore.querySelector('#operatoreList').innerHTML = messaggio;
				document.querySelector('#operatoreList').style.display = 'block';
				modalOperatore.querySelector('#operatoreList').querySelectorAll('li').forEach(el => { 
					
					el.addEventListener('click', (ev) => {
						console.log(ev.target.innerText);
						modalOperatore.querySelector('#modalOperatoreIdHidden').value = ev.target.id;
						modalOperatore.querySelector('#modalOperatoreId').value = ev.target.innerText;
						document.querySelector('#operatoreList').style.display = 'none';

						
					});
				});
				
			}

			async function salvaOperatore(istanza, operatore){
				console.log('Salva operatore...',istanza, operatore);
				let response = await fetch('../iattivita/ajaxSaveResponsabile.htm?codiceIstanza='+istanza+'&codiceOperatore='+operatore);
				let messaggio = await response.text();
				if(messaggio=='Ok'){
					modalOperatore.close();
					window.location.reload();
				}
				
			}

			let dettaglioIstanza = document.querySelector('#vbg-dettaglio-istanza');
			let json = '{"istanza": {"numero_istanza": "1/test","data_istanza": "12/01/2024","numero_protocollo": "125","data_protocollo": "12/01/2024","intervento": "Permesso a costruire","oggetto": "oggetto della pratica","note": "..","operatore": "Responsabile ufficio","richiedenti": [{"titolo": "Richiedente","nominativo": "Mario Rossi"},{"titolo": "Intermediario","nominativo": "Giuseppe Bianchi"}],"stradario": [{"via": "via Roma,12"},{"via": "piazza Italia"}]}}';

			document.querySelectorAll('.info_istanza').forEach(el => {

				el.addEventListener('click', async (ev) => {
				
					ev.preventDefault();
					let istanza = ev.currentTarget.id.substring(13,28);				

					let response = await fetch('../iattivita/ajaxViewDettagliIStanza.htm?codiceIstanza='+istanza);
					let messaggio = await response.json();					
					dettaglioIstanza.show(messaggio);
				
				});
			});

		</script>
	</body>
</html>