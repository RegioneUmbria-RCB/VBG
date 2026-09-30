<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="it.gruppoinit.pal.gp.core.features.oneri.IstanzeOneriListTotali"%>
<%@page import="it.gruppoinit.pal.gp.core.features.oneri.IstanzeOneriListModel"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.math.BigDecimal"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_istanze_oneri.title" /></title>
    <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>
    <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
    
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_istanze_oneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="istanzeoneri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeoneri" />
		    </jsp:include>
			<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../istanzeoneri/list" />
			</jsp:include>
			<c:import url="/ajax/dettaglioIstanza.htm">
				<c:param name="codIstanza">${istanza.id.codice}</c:param>
			</c:import>
			<!-- Variabili che gestiscono la configurazione utente per la tipologia di visualizzazione
			      RAGGRUPPATA O DETTAGLIO -->
			<%
			   if(StringUtils.isNotBlank((String)request.getAttribute(WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI)))
			   {
			   	pageContext.setAttribute("conf",(String)request.getAttribute(WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI));
			   }else
			   {
				pageContext.setAttribute("conf",'0');
			   }
			%>
			<br class="clear" />
			<!-- Visibile solo in visulazlizzaione -->
			<c:if test="${isModifica eq false }">
				<table width="100%">
					<tr>
						<td>
							<c:if test="${conf eq '0'}">
						<a class="raggruppamentoOneri" href="javascript:changeView();" title="<fmt:message key="label.oneri_raggruppati"/>">
							<label><fmt:message key="label.oneri_raggruppati"/></label>
					</a>
					</c:if>
					<c:if test="${conf eq '1'}">
						<a class="dettagliOneri" href="javascript:changeView();" title="<fmt:message key="label.oneri_dettaglio"/>">
							<label><fmt:message key="label.oneri_dettaglio"/></label>
					</a>
					</c:if>
						</td>
				 	</tr>
				</table>
			</c:if>
		    <vbg-fetch-ref id='data-by-id' method='get' response-format='json' request-format='form'
		                    url='../dettposizionedebitoria/ajaxDettaglioPosizione.htm'>
		    </vbg-fetch-ref>
			<!-- 
			    LEGGENDA VARIABILI :
			    1. "conf" getsisce la modalità di rappresentazione e visualizzazione degli oneri; può essere 
			    	a. 0: visualizzazione oneri dettaglio
			    	b. 1  visualizzazione oneri raggruppati
			    2. "isModifica", indica se la lista degli oneri è in semplice visualizzazione o può permettere di modificare i dati; può essere:
			   		a. true : significa che siamo nella modalità di modifica in line direttamente dalla lista
			        b. false: significa che siamo nella modalità di visualizzazione 
			    3. "viewAmministrazioneAndEndo" indica se l'onere richiede un endo collegato e ci dice se visulaizzare la colonna che mostra 
			        amministrazione + endo; può essere :
			        a. true : richiede endo
			        b. false: non richiede endo	
			        
			-->
			<div class="jmesa" >
				<!---------------------------------------- TABELLA  DETTAGLIO DEGLI ONERI ---------------------------------------------------->
     			<!---------------------------------------------------------------------------------------------------------------------------->
				<c:if test="${conf eq '0'}">
					<table width="100%" border="0"  cellpadding="0"  cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="8%"><fmt:message key="label.codice"/></td>
								<c:if test="${isModifica eq false }">
									<td width="2%"></td>
								</c:if>
								<td><fmt:message key="label.raggruppamento"/></td>
								<c:if test="${isModifica eq false }">
									<td width="2%"></td>
								</c:if>
								<td><fmt:message key="label.causale"/></td>
								<c:if test="${viewAmministrazioneAndEndo==true}">
									<td width="15%"><fmt:message key="label.amministrazione"/></td>
								</c:if>
								<td width="5%"><fmt:message key="label.entrate_causale"/></td>
								<td width="5%"><fmt:message key="label.entrate_istruttoria"/></td>
								<c:if test="${viewAmministrazioneAndEndo==true}">
									<td width="5%"><fmt:message key="label.uscita_oneri"/></td>
									<td width="5%"><fmt:message key="label.ribasso"/></td>
								</c:if>
								<td width="5%"><fmt:message key="label.importo_incassato_o_riversato_oneri"/></td>
								<td width="12%"><fmt:message key="label.data_pagamento"/></td>
								<td width="8%"><fmt:message key="label.data_scadenza"/></td>
								<c:if test="${isModifica eq false }">
									<td width="15%"><fmt:message key="label.edit.record"/></td>
								</c:if>
							</tr>
						</thead>
						<tbody class="tbody">
							<c:forEach items="${istanzeOneriListModel.elenco}" var="raggruppamento" varStatus="a">
								<c:set value="${fn:length(istanzeOneriListModel.elenco)}" var="indice" scope="page"></c:set>
								
								<c:set value="${raggruppamento.key.raggruppamento}" var="descRaggruppamento" scope="page"></c:set>
								<%
			    					int i=0;
									IstanzeOneriListModel model = (IstanzeOneriListModel)request.getAttribute("istanzeOneriListModel");
									String descRaggruppamento = (String)pageContext.getAttribute("descRaggruppamento");
									IstanzeOneriListTotali totali = model.getTotaleRaggruppamento(descRaggruppamento);
			    				%>
			    				
			    				<c:forEach items="${raggruppamento.value}" var="istanzeoneriDettaglio" varStatus="b">
									<c:set value="${istanzeoneriDettaglio.tipicausalioneri.raggruppamentocausalioneri.rcoDescr}" var="descrizioneRaggruppamento" scope="page"></c:set>
									<!-- Setta lo stile della riga nel caso sia popolato il campo flagNonDovuto -->
									<c:set value="" var="idNonDovuto"></c:set>
									<c:if test="${istanzeoneriDettaglio.flagNondovuto eq true }">
										<c:set value="onere-non-dovuto" var="idNonDovuto" scope="page"></c:set>
							    	</c:if>
							    	<c:forEach items="${istanzeoneriHelperDettaglio.istanzeoneris}" var="IstOneri" >
							    		<c:if test="${IstOneri.flagOnereRateizzato}">
											<c:set value="true" var="isRateizzato" ></c:set>
										</c:if>				    	
									</c:forEach>													
									<tr id="${idNonDovuto}"  class="<%=(i%2)==0?"odd":"even"%>">
										<td>
											<a href="javascript:historySet('${_urlback }','../istanzeoneri/view.htm?codice=${istanzeoneriDettaglio.id.codice}','');" >${istanzeoneriDettaglio.id.codice}</a>
										</td>
										<%-- In modifica non mostriamo la funzionalità di rateizzazione, NON IMPLEMENTATA --%>
										<c:if test="${isModifica eq false }">
											<%-- La funzionalità di rateizzazione per raggruppamento deve essere visualizzata solo per gli oneri che sono associati a un raggruppamento --%>
											<td>								
												<c:if test="${istanzeoneriDettaglio.tipicausalioneri.raggruppamentocausalioneri.id.codice!=null}">
													<%-- La funzionalità di rateizzazione si deve vedere solo per il primo record del raggruppamento --%>
													<c:if test="<%=i==0%>">
														<%-- FUNZIONALITA' ENTERPRISE DA SVILUPPARE --%>
														<!-- §§§BEGIN§§§ -->
														<c:if test="${isRateizzato }">
															<a href="javascript:doHref('listRate.htm?codiceIstanza=${istanza.id.codice}&codiceRaggruppamento=${istanzeoneriDettaglio.tipicausalioneri.raggruppamentocausalioneri.id.codice}','')"><fmt:message key="label.d"/></a></li>
														</c:if>												  
														<c:if test="${!isRateizzato}">
															<a href="javascript:doHref('../oneritipirateizzazione/view.htm?codiceIstanza=${istanza.id.codice}&codiceRaggruppamento=${istanzeoneriDettaglio.tipicausalioneri.raggruppamentocausalioneri.id.codice}','');" title="<fmt:message key="label.rateizzazioni_raggruppamento"/>"><fmt:message key="label.r"/></a>
														</c:if>
														<!-- §§§END§§§ -->
														<%-- --%>
													</c:if>
												</c:if>
											</td>	
										</c:if>
										<!-- DEscrizione raggruppamento -->
										<td>${istanzeoneriDettaglio.tipicausalioneri.raggruppamentocausalioneri.rcoDescr}</td>
										<%-- In modifica non mostriamo la funzionalità di rateizzazione di un singolo onere--%>
										<c:if test="${isModifica eq false }">
										<%-- La rateizzazione dle singolo onere si deve vedere solo se l'onere non è stato pagato ()datapagamento 
										     diversa da null --%>
										     <td>
												<c:if test="${istanzeoneriDettaglio.datapagamento==null}">
													<%-- FUNZIONALITA' ENTERPRISE DA SVILUPPARE --%>
													<!-- §§§BEGIN§§§ --> 
													<c:if test="${istanzeoneriDettaglio.flagOnereRateizzato}">
														<a href="javascript:doHref('listRate.htm?codiceIstanza=${istanza.id.codice}&idCO=${istanzeoneriDettaglio.tipicausalioneri.id.codice}','')"><fmt:message key="label.d"/></a></li>
													</c:if>												  
													<c:if test="${!istanzeoneriDettaglio.flagOnereRateizzato}">
														<a href="javascript:doHref('../oneritipirateizzazione/view.htm?codiceIstanza=${istanza.id.codice}&idOnere=${istanzeoneriDettaglio.id.codice}','');" title="<fmt:message key="label.rateizzazioni_causale"/>"><fmt:message key="label.r"/></a>
													</c:if>
													<!-- §§§END§§§ -->
													<%-- --%>
												</c:if>
											</td>
										</c:if>
										<!-- Descrizione tipo causale onere -->
										<td>${istanzeoneriDettaglio.tipicausalioneri.coDescrizione }
											<c:if test="${istanzeoneriDettaglio.nrDocumento ne '' && istanzeoneriDettaglio.nrDocumento ne null}">
										    	<br class="clear" />
										    	(<fmt:message key="label.numero_documento"/>:&nbsp;<b>${istanzeoneriDettaglio.nrDocumento}</b>)
										    </c:if>
										    <c:if test="${istanzeoneriDettaglio.flagNondovuto eq true }">
										    	<br class="clear" />
										     	<fmt:message key="label.onere_non_dovuto.help"/>
										    </c:if>
										</td>
										<!-- Se l'onere può essere associato a un endo mostro la stringa che riporta endo + amministrazione -->
										<c:if test="${viewAmministrazioneAndEndo==true }">
											 <c:if test="${istanzeoneriDettaglio.transientAmministrazioneEndo ne '' || istanzeoneriDettaglio.transientAmministrazioneEndo ne null }">
												<td>${istanzeoneriDettaglio.transientAmministrazioneEndo}</td>
											</c:if>
										</c:if>
										<!-- Gesione della visualizzazione di un onere in entrata -->
										<!----------------------------INIZIO------------------------>
										<c:if test="${istanzeoneriDettaglio.flentratauscita eq true  }">
											<!-- Visualizzaizone degli oneri in entrata quando sono stati pagati -->
											<c:if test="${istanzeoneriDettaglio.datapagamento!=null }">
												<!-- In visualizzazione -->
												<c:if test="${isModifica eq false }">
													<td style="text-align: right;"><fmt:formatNumber  value="${istanzeoneriDettaglio.prezzo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													<!--Controllo se mostrare o no il prezzo di istruttoria  -->
													<td style="text-align: right;"><fmt:formatNumber  value="${istanzeoneriDettaglio.prezzoistruttoria}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
											    </c:if>
											    <!-- In modifica -->
											    <c:if test="${isModifica eq true }">
											    	<td style="text-align: right;"> 
											    		<input id="id_prezzo${a.index}${b.index}" align="right" type="text" name="prezzo" value="${istanzeoneriDettaglio.prezzo}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_prezzo${a.index}${b.index}','prezzo');" />
											    	</td>
													<td style="text-align: right;">
														<input id="id_prezzoistruttoria${a.index}${b.index}" align="right" type="text" name="prezzo" value="${istanzeoneriDettaglio.prezzoistruttoria}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_prezzoistruttoria${a.index}${b.index}','prezzoistruttoria');" />
													</td>
											    </c:if>
											    <%-- rappresenta le due colonne che esistono quando sono visualizzate uscite e ribasso , ma sono null perchè
											    l'onere è in entrata --%>
											    <c:if test="${viewAmministrazioneAndEndo==true }">
											    	<td colspan="2"></td>
											    </c:if>
											</c:if>
											<!-- Visualizzaizone degli oneri in entrata quando non  sono stati ancora  pagati -->
											<c:if test="${istanzeoneriDettaglio.datapagamento==null }">
												<!-- In visualizzazione -->
												<c:if test="${isModifica eq false }">
													<td style="text-align: right;color: red;"><fmt:formatNumber  value="${istanzeoneriDettaglio.prezzo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													<td style="text-align: right;color: red;"><fmt:formatNumber  value="${istanzeoneriDettaglio.prezzoistruttoria}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
											    </c:if>
											    <!-- In modifica -->
											    <c:if test="${isModifica eq true }">
											    	<td style="text-align: right;"> 
											    	    <input id="id_prezzo${a.index}${b.index}" align="right" type="text" name="prezzo" value="${istanzeoneriDettaglio.prezzo}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_prezzo${a.index}${b.index}','prezzo');" />  		
											    	</td>
											    	<td style="text-align: right;"><input id="id_prezzoistruttoria${a.index}${b.index}" align="right" type="text" name="prezzo" value="${istanzeoneriDettaglio.prezzoistruttoria}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_prezzoistruttoria${a.index}${b.index}','prezzoistruttoria');" /></td>
											    </c:if>
											    <%-- rappresenta le due colonne che esistono quando sono visualizzate uscite e ribasso , ma sono null perchè
											    l'onere è in entrata --%>
											    <c:if test="${viewAmministrazioneAndEndo==true }">
											    	<td colspan="2"></td>
											   </c:if>
											</c:if>
										</c:if>
									    <!----------------------------FINE-------------------------->
									    
									    <!-- Gesione della visualizzazione di un onere in uscita --->
									    <!----------------------------INIZIO------------------------>
										<c:if test="${istanzeoneriDettaglio.flentratauscita eq false || istanzeoneriDettaglio.flentratauscita eq null}">
										    <%-- rappresenta le due colonne entrata causale e entrata istruttoria che non devono visualizzare nessun valore
										    quando l'onere è in uscita --%>
											<!-- Gestione onere  pagato -------------  --> 
											<c:if test="${istanzeoneriDettaglio.datapagamento!=null }">
												<!-- In visualizzazione -->
												<c:if test="${isModifica eq false }">
													<td  style="text-align: right;"><fmt:formatNumber  value="${istanzeoneriDettaglio.prezzo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													<td  style="text-align: right;">${istanzeoneriDettaglio.transientPercetualeAndVAloreRibasso}</td>
											    </c:if>
											    <!-- In modifica -->
											    <c:if test="${isModifica eq true }">
											    	<td style="text-align: right;"> 
											    		<input id="id_prezzo${a.index}${b.index}" align="right" type="text" name="prezzo" value="${istanzeoneriDettaglio.prezzo}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_prezzo${a.index}${b.index}','prezzo');" />
											        </td>
											        <td style="text-align: right;">${istanzeoneriDettaglio.transientPercetualeAndVAloreRibasso}</td>
											    </c:if>
											</c:if>
											<!-- Gestione onere non pagato -------------  --> 
											<c:if test="${istanzeoneriDettaglio.datapagamento==null }">
											    <!-- In visualizzazione -->
											    <c:if test="${isModifica eq false }">
													<td  style="text-align: right;color: red;"><fmt:formatNumber  value="${istanzeoneriDettaglio.transietUscitaRibassata}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													<td  style="text-align: right;color: red;">${istanzeoneriDettaglio.transientPercetualeAndVAloreRibasso}</td>
											    </c:if>
											     <!-- In modifica -->
											     <c:if test="${isModifica eq true }">
											     	<td  style="text-align: right;"> <input id="id_prezzo${a.index}${b.index}" align="right" type="text" name="prezzo" value="${istanzeoneriDettaglio.transietUscitaRibassata}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_prezzo${a.index}${b.index}','prezzo');" /></td>
											     	<td  style="text-align: right;color: red;">${istanzeoneriDettaglio.transientPercetualeAndVAloreRibasso}</td>
											     </c:if>
											</c:if>
											<c:if test="${viewAmministrazioneAndEndo==true }">
											    	<td colspan="2"></td>
											</c:if>
										</c:if>
									    <!----------------------------FINE-------------------------->
									    
									    <!-- Gestione del campo importo pagato (Importo Inc / Riv) -->
									    <!----------------------------INIZIO------------------------->
									   	<c:if test="${isModifica eq false }"> 
					    					<td style="text-align: right;"><fmt:formatNumber  value="${istanzeoneriDettaglio.importopagato}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
					    				</c:if>
					    				<!-- In modifica -->
									    <c:if test="${isModifica eq true }">
									    	<td  style="text-align: right;"> <input id="id_importopagato${a.index}${b.index}" align="right" type="text" name="importopagato" value="${istanzeoneriDettaglio.importopagato}"  size="8" onchange="checkNumberValue(this);changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'id_importopagato${a.index}${b.index}','importopagato');" /></td>
									    </c:if>					    				
									    <!----------------------------FINE--------------------------->
									    
										<!--------  Gestione campi date pagameto e scadenza --------->
									    <!----------------------------INIZIO------------------------->
										<!-- In visualizzazione -->
										<c:if test="${isModifica eq false }">
										    <c:if test="${viewAmministrazioneAndEndo eq false}">
								      	 		<td> <fmt:formatDate value="${istanzeoneriDettaglio.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" />  </td>
								      	 	</c:if>
							                <c:if test="${viewAmministrazioneAndEndo  eq true }">
								      	 		<td><fmt:formatDate value="${istanzeoneriDettaglio.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" />  </td>
								      	 	</c:if>
											<td><fmt:formatDate value="${istanzeoneriDettaglio.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" />  </td>
									    </c:if>
									    <!-- In modifica -->
									    <c:if test="${isModifica eq true }">
									    	<td>
												<input id="datapagamento1_id${a.index}${b.index}" type="text" name="datapagamento" value="<fmt:formatDate value="${istanzeoneriDettaglio.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" size="8" maxlength="10"  onblur="isValidDate(this,true);"
												onchange="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datapagamento1_id${a.index}${b.index}','datapagamento');"/> 
												<init:calendar imagePath="/images/cal.gif" idImage="caldatapagamento${a.index}${b.index}" idInput="datapagamento1_id${a.index}${b.index}" textKey="label.calendar"
												javascriptAction="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datapagamento1_id${a.index}${b.index}','datapagamento');"/>
											    <a href="javascript:tabRiferimetiPagamento${a.index}${b.index}('riferimentiDialogDiv${a.index}${b.index}',${istanzeoneriDettaglio.id.codice })" title="<fmt:message key="label.riferimenti_pagamento"/>"><fmt:message key="label.r"/></a>
											    <a href="javascript:associaDataScadenza${a.index}${b.index}();" title="<fmt:message key="label.associa_data_scadenza"/>"><fmt:message key="label.s"/></a>
											    <!-- Pannello contente le informazioni sui riferimenti del pagamento -->  
											    <div dojoType="dijit.Dialog" id="riferimentiDialogDiv${a.index}${b.index}" title="<fmt:message key="label.riferimenti_pagamento" />: ">
													<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 700px">
														<div id="riferimenti${a.index}${b.index}"></div>
													</div>
												</div>
											</td>
											<td>
												<input id="datascadenza2_id${a.index}${b.index}" type="text" name="datascadenza" value="<fmt:formatDate value="${istanzeoneriDettaglio.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" size="8" maxlength="10"  onblur="isValidDate(this,true);" onchange="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datascadenza2_id${a.index}${b.index}','datascadenza');"/>
												<init:calendar imagePath="/images/cal.gif" idImage="caldatascadenza${a.index}${b.index}" idInput="datascadenza2_id${a.index}${b.index}" textKey="label.calendar"
												javascriptAction="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datascadenza2_id${a.index}${b.index}','datascadenza');"/>
											</td>	
										</c:if>
									    <!----------------------------FINE--------------------------->
									    <script type='text/javascript'>
										    /**
										    tabRiferimetiPagamento() e riferimetiPagamentoTab(): gestisco la modifica dei campi che riguardano i dettagli di pagamento
										                                                         vengono utilizzati solo quando siamo in modalità di modifica.
										
										    changeValue()									   : richiama un metodo ajax per la modifica inline dei campi , utilizzato solo
										     													 in modalità di modifica
										
										    associaDataScadenza${a.index}${b.index}()          : associa alla data di pagamento la data di scadenza se esiste.Funzionalità usata in modalità modifica    													 
											**/
									
											function associaDataScadenza${a.index}${b.index}()
											{
												var dataScadenza=document.getElementById('datascadenza2_id${a.index}${b.index}').value;
												if(dataScadenza!='')
												{
													$('datapagamento1_id${a.index}${b.index}').value=dataScadenza;
													changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datapagamento1_id${a.index}${b.index}','datapagamento');
												}else
												{
													alert('Attenzione: Impossibile impostare la data di pagamento con la data di scadenza. Data di scadenza non presente.');
												}
											}
									
											function tabRiferimetiPagamento${a.index}${b.index}(divId,codice){
												dijit.byId(divId).show();
												riferimetiPagamentoTab${a.index}${b.index}(codice);		
											}
											
											function riferimetiPagamentoTab${a.index}${b.index}(codice) {
												
												new Ajax.Request(
														'${pageContext.request.contextPath}/istanzeoneri/ajaxRiferimentiPagamento.htm?codice='+ codice,
														{
															method : 'post',
															onSuccess : function(transport) {							
																var response = transport.responseText;							
																$("riferimenti${a.index}${b.index}").innerHTML = response;
																applyStyle();
																//$("dettaglio" + inventario.value).appear();							
															},
															onFailure : function(transport) {
																var response = transport.responseText;
																alert(response);
															}
														});
											}
											
											function changeValue${a.index}${b.index}(id,obj,campo)
											{
												window.vbg.mostraModalCaricamento();
												var data = document.getElementById(obj);
												let popup = document.querySelector('#popup_msg_id');
												popup.querySelector('#msg_id').innerHTML = '';
												popup.querySelector('#msgIntMora_id').innerHTML = '';
												popup.querySelector('#msgErrore_id').innerHTML = '';

												new Ajax.Request(
														'${pageContext.request.contextPath}/istanzeoneri/ajaxChangeOnereValue.htm?codice='+id+'&valore='+data.value+'&campo='+campo,
														{
															method : 'post',
															onSuccess : function(transport) {
																
																/* dijit.showTooltip(transport.responseText, dojo.byId(obj));
																setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000); */
																let json = transport.responseJSON;
																let popup = document.querySelector('#popup_msg_id');
																
																if (json.msg !== '') {
																	popup.querySelector('#msg_id').innerHTML = json.msg ;
																}if(json.msgIntMora !== ''){
																	popup.querySelector('#msgIntMora_id').innerHTML = json.msgIntMora ;
																}if(json.errore !== ''){
																	popup.querySelector('#msgErrore_id').innerHTML = json.errore;
																}
																
																window.vbg.nascondiModalCaricamento();
																popup.open();
																
															},
															onFailure : function(transport) {
																let json = transport.responseJSON;
																let popup = document.querySelector('#popup_msg_id');
																popup.querySelector('#msgErrore_id').innerHTML = json.errore;
																window.vbg.nascondiModalCaricamento();
																popup.open();
																
																
															}
														});			
											}
											
											
											vbg.ready(function(){
												function aggiungiEventoReload(){
													let det = document.querySelectorAll('vbg-dettaglio-posizione-debitoria');
													det.forEach((vbgDettaglioPosizione)=>{
														vbgDettaglioPosizione.addEventListener('stato-modificato',()=>{
															window.vbg.mostraModalCaricamento();
															document.location.reload();
														});
														
													});
													
												}
												
												aggiungiEventoReload();
												
											});
									    </script>
									    
									    <!-- STATO POSIZIONE DEBITORIA -->							
										<!-- Colonna che mostra l'icona che porta al dettaglio di un onere, visibile solo in modalità di visualizzazione -->		
									    <c:if test="${isModifica eq false }">
										    <td style='white-space: nowrap;'>
									    		<a style='display: inline-block;margin-right: var(--half-padding);' 
							                        href="javascript:historySet('${_urlback }','../istanzeoneri/view.htm?codice=${istanzeoneriDettaglio.id.codice}','');" 
							                        title="<fmt:message key="label.edit.record"/>"
							                        aria-label='<fmt:message key="label.edit.record"/>'>
							                        <i class='fa fa-pencil fa-2x' aria-hidden='true'></i>
												</a>
												<c:forEach items="${ istanzeoneriDettaglio.istoneriDettPosizioni}" var="istanzaonere">
													<vbg-dettaglio-posizione-debitoria
														id-posizione="${istanzaonere.dettPosizioneDebitoria.id.codice}"
														fetch-ref='data-by-id'>
													</vbg-dettaglio-posizione-debitoria>
							
							
												</c:forEach>
												<c:if test="${not empty istanzeoneriDettaglio.bollGestIstanzeoneris }">
													<c:forEach items="${istanzeoneriDettaglio.bollGestIstanzeoneris}" var="posBollettazione">
														<c:if test="${not empty posBollettazione.bollGestDettaglio.dettPosizioneDebitoria}">
							                            	<vbg-dettaglio-posizione-debitoria id-posizione="${posBollettazione.bollGestDettaglio.dettPosizioneDebitoria.id.codice}" fetch-ref='data-by-id'></vbg-dettaglio-posizione-debitoria>
														</c:if>
														<c:forEach items="${posBollettazione.bollGestDettaglio.bollGestDettRate}" var="rata">
															<c:if test="${not empty rata.dettPosizioneDebitoria}">
																<vbg-dettaglio-posizione-debitoria mostra-testo="false" id-posizione="${rata.dettPosizioneDebitoria.id.codice}" fetch-ref='data-by-id'></vbg-dettaglio-posizione-debitoria>
															</c:if>
														</c:forEach>
													</c:forEach>
											    </c:if>	
											</td>
										</c:if>
									</tr>
									<%i++;%>
								</c:forEach>
			    				<!-- ------------------------------- Sezione per la gestione delle righe di sub totale------------------------ -->
								<!-- ----------------------------------------------------INIZIO ---------------------------------------------- -->
			    				<c:if test="${fn:length(raggruppamento.value)>0}">
									<!-- In visuallizzazione -->
							      	<c:if test="${isModifica eq false }">
							      		<tr>
							      			<c:if test="${viewAmministrazioneAndEndo==true }">
							      	 			<td colspan="5"></td>
							      	 		</c:if>
							      	 		<c:if test="${viewAmministrazioneAndEndo==false }">
							      	 			<td colspan="3"></td>
							      	 		</c:if>
							      	 		<td colspan="6">
												<table border="0" cellspacing="5" width="100%">
													<tr class="rigaSubTotaleOneri">
														<c:if test="${descrizioneRaggruppamento != null}">
															<b><td colspan="6" style="text-align: left;border: medium;">${descrizioneRaggruppamento}</td></b>
														</c:if>
														<c:if test="${descrizioneRaggruppamento == null}">
															<b><td colspan="6" style="text-align: left;border: medium;">&nbsp;</td></b>
														</c:if>
													</tr>
													<tr>
														<td style="text-align: right;" width="18%"><b><fmt:message key="label.entrate"/>:</b></td>
														<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getEntrate().getImportoComplessivo()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
														<td style="text-align: right;" width="18%"><b><fmt:message key="label.incassato"/>:</b></td>
														<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getIncassato().getImportoComplessivo()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
														<td style="text-align: right;" width="18%"><b><fmt:message key="label.saldo"/>:</b></td>
														<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getSaldoEntrateIncassato()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													</tr>
													<tr>
														<td style="text-align: right;" ><b><fmt:message key="label.uscite"/>:</b></td>
														<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getUscite().getImportoComplessivo()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
														<td style="text-align: right;" ><b><fmt:message key="label.riversato"/>:</b></td>
														<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getRiversato().getImportoComplessivo()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
														<td style="text-align: right;"><b><fmt:message key="label.saldo"/>:</b></td>
														<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getSaldoUsciteRiversato()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													 </tr>
												</table>
							      	 		</td>
											<c:if test="${viewAmministrazioneAndEndo==true }">
												<td colspan="3"></td>
											</c:if>
							      			<c:if test="${viewAmministrazioneAndEndo==false }">
												<td colspan="2"></td>
							      			</c:if>
								     	</tr>
									    <tr class="rigaSubTotaleOneri">
											<td  width="100%" height="2" colspan="14"></td>
									    </tr>
								    	<tr>
								    		<td colspan="14">&nbsp;</td>
								    	</tr>
									</c:if>
								</c:if>
								<!-- ----------------------------------------------------------FINE ------------------------------------------------- -->
			    				<c:if test="${a.index+1==indice}">
									<tr>
									    <c:if test="${viewAmministrazioneAndEndo==true}">
									    	<td	width="100%" height="10" colspan="14"></td>
									    </c:if>
									    <c:if test="${viewAmministrazioneAndEndo==false}">
									    	<td	width="100%" height="10" colspan="13"></td>
									    </c:if>
									</tr>
									<!-- In visualizzazione -->
			    					<c:if test="${isModifica eq false }">
										<!-- -----------------------------------Sezione per la gestione delle righe di  totale------------------------------- -->
										<!-- ------------------------------------------------------INIZIO --------------------------------------------------- -->
										<tr class="rigaTotaleOneri">
									 		<c:if test="${viewAmministrazioneAndEndo==true }">
								      	 		<td colspan="5"></td>
								      	 	</c:if>
								      	 	<c:if test="${viewAmministrazioneAndEndo==false }">
								      	 		<td colspan="3"></td>
								      		</c:if>
											<td colspan="6">
												<table border= "0" cellspacing="5" width="100%">
							      	 	    		<tr class="rigaSubTotaleOneri">
							      				 		<td colspan="6" style="text-align: left;border: medium;"><fmt:message key="label.totale"/></td>
							        			 	</tr>
							      	 	 		 	<tr>
								      	 	 			<td width="18%"><b><fmt:message key="label.entrate"/></b></td>
								      	 	 			<td style="text-align: right;"><fmt:formatNumber  value="${istanzeOneriListModel.totali.entrate.importoComplessivo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
								      	 	 			<td width="18%"><b><fmt:message key="label.incassato"/></b></td>
								      	 	 			<td style="text-align: right;"><fmt:formatNumber  value="${istanzeOneriListModel.totali.incassato.importoComplessivo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
								      	 	 			<td width="18%"><b><fmt:message key="label.saldo"/></b></td>
								      	 	 			<td style="text-align: right;"><fmt:formatNumber  value="${istanzeOneriListModel.totali.saldoEntrateIncassato}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
													</tr>
							      	 	 			<tr>
								      	 	 			<td><b><fmt:message key="label.uscite"/></b></td>
								      	 	 			<td style="text-align: right;"><fmt:formatNumber  value="${istanzeOneriListModel.totali.uscite.importoComplessivo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
								      	 	 			<td><b><fmt:message key="label.riversato"/></b></td>
								      	 	 			<td style="text-align: right;"><fmt:formatNumber  value="${istanzeOneriListModel.totali.riversato.importoComplessivo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
								      	 	 			<td><b><fmt:message key="label.saldo"/></b></td>
								      	 	 			<td style="text-align: right;"><fmt:formatNumber  value="${istanzeOneriListModel.totali.saldoUsciteRiversato}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
							      	 	 			</tr>
												</table>
											</td>
											<c:if test="${viewAmministrazioneAndEndo==true }">
							      	 			<td colspan="3"></td>
							      	    	</c:if>
							      	    	<c:if test="${viewAmministrazioneAndEndo==false }">
							      	 			<td colspan="2"></td>
							      	   		</c:if>
							      	   	</tr>
										<!-- ----------------------------------------------------------FINE ------------------------------------------------- -->
			    					
										<!-- -----------------------------------Sezione per la gestione della legenda fine pagina---------------------------- -->
										<!-- ------------------------------------------------------INIZIO --------------------------------------------------- -->
										<tr>
											<td colspan="14"><fmt:message key="label.istanza_oneri_legenda_ribasso"/></td>
										</tr>
										<tr>
									        <td colspan="14"><fmt:message key="label.istanza_oneri_legenda"/></td>
										</tr>
			    							<!-- Compare solo se ci sono due o più oneri per la stessa amministrazione e la somma delle uscite supera la somma
											delle entrate e nella modalità di dettaglio oneri -->
											<c:if test="${fn:length(oneriEntrateUsciteAmministraziones)>0 && conf eq '0'}">
												<tr  class="titoloSezione">
													<td colspan="14" align="center"><fmt:message key="label.avvertenze"/></td>
												</tr>
												<c:forEach items="${oneriEntrateUsciteAmministraziones}" var="oneriEntrataUscita">
													<tr>
													 	<td colspan="14"><fmt:message key="label.importo_uscita"/>&nbsp;${oneriEntrataUscita.totaleUscitePerAmministrazione }
													 	    <fmt:message key="label.superato_importo_entrata"/>&nbsp;${oneriEntrataUscita.totaleEntratePerAmministrazione}&nbsp;
													 	    <fmt:message key="label.per_endoprocedimento"/>:&nbsp;${oneriEntrataUscita.amministrazione.amministrazione}
													 	</td>
													</tr>
												</c:forEach>
											</c:if>
			    						<!-- ----------------------------------------------------------FINE ------------------------------------------------- -->
			    					</c:if>
			    				</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<!---------------------------------------- FINE TABELLA  DETTAGLIO DEGLI ONERI ----------------------------------------------->
				<!---------------------------------------------------------------------------------------------------------------------------->
		
				<!------------------------------------------ TABELLA  RAGGRUPPAMENTO ONERI --------------------------------------------------->
				<!---------------------------------------------------------------------------------------------------------------------------->
		  		<c:if test="${conf eq '1'}">
		  			<table width="100%" border="0"  cellpadding="0"  cellspacing="0"  class="table">
		  				<thead>
							<tr class="header">
								<td width="2%"></td> 
								<td width="25%"><fmt:message key="label.raggruppamento"/></td>
								<td width="8%"><fmt:message key="label.entrate_causale"/></td>
								<td width="8%"><fmt:message key="label.entrate_istruttoria"/></td>	
								<td width="8%"><fmt:message key="label.data_pagamento"/></td>
								<td width="8%"><fmt:message key="label.data_scadenza"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<%
							IstanzeOneriListModel model = (IstanzeOneriListModel)request.getAttribute("istanzeOneriListModel");
							String precRaggruppamento = "";
							BigDecimal totaleEntrateCausale = BigDecimal.ZERO;
							BigDecimal totaleEntrateIstruttoria = BigDecimal.ZERO;
							int i=0;
						%>
							<c:forEach items="${istanzeOneriListModel.elenco}" var="raggruppamento" varStatus="a">
								<c:set value="${fn:length(istanzeOneriListModel.elenco)}" var="indice" scope="page"></c:set>
								<c:set value="${raggruppamento.key.raggruppamento}" var="descRaggruppamento" scope="page"></c:set>
								<c:set value="${raggruppamento.key.dataPagamento}" var="dataPagamento" scope="page"></c:set>
								<c:set value="${raggruppamento.key.dataScadenza}" var="dataScadenza" scope="page"></c:set>
								
								<%
								String descRaggruppamento = (String)pageContext.getAttribute("descRaggruppamento");
								Date dataPagamento = (Date)pageContext.getAttribute("dataPagamento");
								Date dataScadenza = (Date)pageContext.getAttribute("dataScadenza");
								
								IstanzeOneriListTotali totali = model.getTotaleRaggruppamentoDataPagamentoEScadenza(descRaggruppamento,dataPagamento,dataScadenza);
		    					
								if( precRaggruppamento == "" || precRaggruppamento == descRaggruppamento ){
								    totaleEntrateCausale = totaleEntrateCausale.add(totali.getEntrate().getImportoCausale());
								    totaleEntrateIstruttoria = totaleEntrateIstruttoria.add(totali.getEntrate().getImportoIstruttoria());
								} 
								
		    					if( ( i > 0 && precRaggruppamento != descRaggruppamento) ) {
			    					%>
									<tr class="rigaSubTotaleOneri">
										<td colspan="2" style="text-align: right;border: medium;"><b><fmt:message key="label.totale"/>&nbsp;${descRaggruppamento}</b></td>
							            <td style="text-align: right;"><b><fmt:formatNumber  value="<%= totaleEntrateCausale%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></b></td>
							            <td style="text-align: right;"><b><fmt:formatNumber  value="<%= totaleEntrateIstruttoria%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></b></td>
									    <td colspan="3"></td>
									</tr>
									<%
									totaleEntrateCausale = totali.getEntrate().getImportoCausale();
									totaleEntrateIstruttoria = totali.getEntrate().getImportoIstruttoria();
		    					}
								%>

		    					<tr class="<%=(i%2)==0?"odd":"even"%>">
									<td></td>
									<td>${raggruppamento.key.raggruppamento}</td>
									<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getEntrate().getImportoCausale()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
	        						<td style="text-align: right;"><fmt:formatNumber  value="<%= totali.getEntrate().getImportoIstruttoria()%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
									<!-- In Visualizzazione -->
									<c:if test="${isModifica eq false }">
										<td><fmt:formatDate value="${raggruppamento.key.dataPagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" />  </td>
										<td><fmt:formatDate value="${raggruppamento.key.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" />  </td>
								    </c:if>
									<!-- In modifica -->
									<c:if test="${isModifica eq true }">
									 	<td>
											<input id="datapagamento0_id${a.index}${b.index}" type="text" name="datapagamento" value="<fmt:formatDate value="${raggruppamento.key.dataPagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" size="8" maxlength="10"  onblur="isValidDate(this,true);" onchange="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datapagamento0_id${a.index}${b.index}','datapagamento');"/> 
											<init:calendar imagePath="/images/cal.gif" idImage="caldatapagamento${a.index}${b.index}" idInput="datapagamento0_id${a.index}${b.index}" textKey="label.calendar"
												javascriptAction="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice},'datapagamento0_id${a.index}${b.index}','datapagamento');"/>
											<a href="javascript:tabRiferimetiPagamento${a.index}${b.index}('riferimentiDialogDiv${a.index}${b.index}',${istanzeoneriDettaglio.id.codice })" title="<fmt:message key="label.riferimenti_pagamento"/>"><fmt:message key="label.r"/></a>
											<a href="javascript:associaDataScadenza${a.index}${b.index}();" title="<fmt:message key="label.associa_data_scadenza"/>"><fmt:message key="label.s"/></a>
											<!-- Pannello contente le informazioni sui riferimenti del pagamento -->
											<div dojoType="dijit.Dialog" id="riferimentiDialogDiv${a.index}${b.index}" title="<fmt:message key="label.riferimenti_pagamento" />: ">
												<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 700px; height: 100px;">
													<div id="riferimenti${a.index}${b.index}"></div>
												</div>
											</div>
										</td>
										<td>
											<input id="datascadenza00_id${a.index}${b.index}" type="text" name="datascadenza" value="<fmt:formatDate value="${raggruppamento.key.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" size="8" maxlength="10"  onblur="isValidDate(this,true);" onchange="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datascadenza00_id${a.index}${b.index}','datascadenza');"/>
											<init:calendar imagePath="/images/cal.gif" idImage="caldatascadenza${a.index}${b.index}" idInput="datascadenza00_id${a.index}${b.index}" textKey="label.calendar"
												javascriptAction="changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datascadenza00_id${a.index}${b.index}','datascadenza');"/>
										</td>	
									</c:if>
		    					</tr>
		    					
		    					<script type='text/javascript'>
			    					/**
			    				    tabRiferimetiPagamento() e riferimetiPagamentoTab(): gestisco la modifica dei campi che riguardano i dettagli di pagamento
			    				                                                         vengono utilizzati solo quando siamo in modalità di modifica.
			    				
			    				    changeValue()									   : richiama un metodo ajax per la modifica inline dei campi , utilizzato solo
			    				     													 in modalità di modifica
			    				
			    				    associaDataScadenza${a.index}${b.index}()          : associa alla data di pagamento la data di scadenza se esiste.Funzionalità usata in modalità modifica     													 
			    					**/
			    					
			    					function associaDataScadenza${a.index}${b.index}()
			    					{
			    						var dataScadenza=document.getElementById('datascadenza00_id${a.index}${b.index}').value;
			    						if(dataScadenza!='')
			    						{
			    							$('datapagamento0_id${a.index}${b.index}').value=dataScadenza;
			    							changeValue${a.index}${b.index}(${istanzeoneriDettaglio.id.codice },'datapagamento0_id${a.index}${b.index}','datapagamento');
			    						}else
			    						{
			    							alert('Attenzione: Impossibile impostare la data di pagamento con la data di scadenza. Data di scadenza non presente.');
			    						}
			    					}
			    					
			    					function tabRiferimetiPagamento${a.index}${b.index}(divId,codice){
			    						dijit.byId(divId).show();
			    						riferimetiPagamentoTab${a.index}${b.index}(codice);		
			    					}
			    					
			    					function riferimetiPagamentoTab${a.index}${b.index}(codice) {
			    						
										new Ajax.Request(
											'${pageContext.request.contextPath}/istanzeoneri/ajaxRiferimentiPagamento.htm?codice='+ codice,
											{
												method : 'post',
												onSuccess : function(transport) {							
													var response = transport.responseText;							
													$("riferimenti${a.index}${b.index}").innerHTML = response;
													applyStyle();							
												},
												onFailure : function(transport) {
													var response = transport.responseText;
													alert(response);
												}
										});
									}
			    					
			    					function changeValue${a.index}${b.index}(id,obj,campo)
			    					{
			    						window.vbg.mostraModalCaricamento();
			    						var data = document.getElementById(obj);
			    						let popup = document.querySelector('#popup_msg_id');
										popup.querySelector('#msg_id').innerHTML = '';
										popup.querySelector('#msgIntMora_id').innerHTML = '';
										popup.querySelector('#msgErrore_id').innerHTML = '';
			    						new Ajax.Request(
			    							'${pageContext.request.contextPath}/istanzeoneri/ajaxChangeOnereValue.htm?codice='+id+'&valore='+data.value+'&campo='+campo,
			    							{
			    								method : 'post',
			    								onSuccess : function(transport) {
			    									/* dijit.showTooltip(transport.responseText, dojo.byId(obj));
			    									setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000); */
			    									let json = transport.responseJSON;
													let popup = document.querySelector('#popup_msg_id');
													if (json.msg !== '') {
														popup.querySelector('#msg_id').innerHTML = json.msg ;
													}if(json.msgIntMora !== ''){
														popup.querySelector('#msgIntMora_id').innerHTML = json.msgIntMora ;
													}if(json.errore !== ''){
														popup.querySelector('#msgErrore_id').innerHTML = json.errore;
													}
													
													window.vbg.nascondiModalCaricamento();
													popup.open();
			    								},
			    								onFailure : function(transport) {
			    									let json = transport.responseJSON;
													let popup = document.querySelector('#popup_msg_id');
													popup.querySelector('#msgErrore_id').innerHTML = json.errore;
													window.vbg.nascondiModalCaricamento();
													popup.open();
			    								}
			    						});			
			    					}
		    					</script>

		    					<%
		    					if( i == model.getElenco().size()-1 ) {
			    					%>
									<tr class="rigaSubTotaleOneri">
										<td colspan="2" style="text-align: right;border: medium;"><b><fmt:message key="label.totale"/>&nbsp;${descRaggruppamento}</b></td>
							            <td style="text-align: right;"><b><fmt:formatNumber  value="<%= totaleEntrateCausale%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></b></td>
							            <td style="text-align: right;"><b><fmt:formatNumber  value="<%= totaleEntrateIstruttoria%>" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></b></td>
									    <td colspan="3"></td>
									</tr>
									<%

		    					}
								precRaggruppamento = descRaggruppamento;
								i++;
								%>
							</c:forEach>
						</tbody>
		  			</table>
		  		</c:if>
				<!--------------------------------------- FINE TABELLA RAGGRUPPAMENTO ONERI -------------------------------------------------->
				<!---------------------------------------------------------------------------------------------------------------------------->
			</div>
	 	</spring-form:form>
	 	
	 	<vbg-modal id="popup_msg_id" >		
		
		<div slot="body" >
			<h1>Modifica dati oneri</h1>
			<div class="vbg-form">
				<div class="form-group">
				<fieldset>
					<legend>
						Risultati
					</legend>
				
					<p id="msg_id"></p>
	                <p id="msgIntMora_id" style="color: #669900;"></p>
	                <p id="msgErrore_id" style="color: #B61218;;"></p>
				
				</fieldset>
		
						  
	              
				</div>
			</div>
			
			<div slot="footer">
				<a id="bottone_chiudi" href="#" data-role='toggle-popup' class="btn btn-secondary"><fmt:message key="button.back" /></a>
			</div>
		</div>	
	</vbg-modal>	
	</div>

	
	<script type='text/javascript'>
		document.querySelector('#bottone_chiudi').addEventListener('click',()=>{
			
			document.querySelector('#popup_msg_id').close();
		});
		function changeView()
		{
			if(${conf}=='0')
			{
				saveUserPreference('<%=WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI%>', '1');
			}
			else{
				saveUserPreference('<%=WebConstants.CONF_UTENTE_ONERI_RAGGRUPPATI%>', '0');
			}				
			setTimeout('ricaricaPagina();', 1000);								
		}
		function ricaricaPagina(){				
			doHref('list.htm?codiceIstanza=${istanza.id.codice}&ts_='+new Date().getTime(),'');
		}
	</script>
	<div id="functions">
		<ul>
		    <c:if test="${conf=='0' && isModifica eq false}">
				<li><a href="javascript:doHref('create.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
			</c:if>
			<c:if test="${isModifica eq false }">	
				<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanza.id.codice}&isModifica=true','');"><fmt:message key="button.modify" /></a></li>
				<c:set var="software" value="<%=ORMHelper.getSoftware() %>" scope="page"/>
				<c:set var="codiceIstanza" value="${istanza.id.codice}" />
				<c:set var="_URL_BACK" scope="page">/</c:set>
				<c:if test="${not empty _urlback}">
					<c:set var="_URL_BACK"><%= URLDecoder.decode((String)request.getAttribute("_urlback"),"UTF-8" )%></c:set>
					<c:set var="_URL_BACK"><%= URLDecoder.decode((String)pageContext.getAttribute("_URL_BACK"),"UTF-8" )%></c:set>		
				</c:if>
				<c:set var="_URL_ISTANZEONERI_CANONI" value="${inite:linkIstanzeoneriCanoni(pageContext.request, _URL_BACK, software, false, codiceIstanza)}" />	
				<c:set var="_URL_ISTANZEONERI_COSTRUZIONE" value="${inite:linkIstanzeoneriCostoCostruzione(pageContext.request, _URL_BACK, software, false, codiceIstanza)}" />
				<c:set var="_URL_ISTANZEONERI_URBANIZZAZIONE" value="${inite:linkIstanzeoneriCostoUrbanizzazione(pageContext.request, _URL_BACK, software, false, codiceIstanza)}" />
				<c:set var="_URL_ISTANZEONERI_FIDEJUSSIONE" value="${inite:linkIstanzeoneriFidejussioni(pageContext.request, _URL_BACK, software, false, codiceIstanza)}" />
				<c:if test="${isExistRecord eq true }">
					<li><a href="${_URL_ISTANZEONERI_COSTRUZIONE}"><fmt:message key="button.oneri.costo_costruzione"/></a></li>
				</c:if>
				<c:if test="${isExistRecordOvalidita eq true }">
					<li><a href="${_URL_ISTANZEONERI_URBANIZZAZIONE}"><fmt:message key="button.oneri.costo_urbanizzazione"/></a></li>
				</c:if>
				<c:if test="${isExistRecordIstanzeFidejussioni eq true }">
					<li><a href="${_URL_ISTANZEONERI_FIDEJUSSIONE}"><fmt:message key="button.oneri.fidejussioni"/></a></li>
				</c:if>				
				<c:if test="${isExistRecordCanoniConfigurazione eq true }">
					<li><a href="${_URL_ISTANZEONERI_CANONI}"><fmt:message key="button.oneri.calcolo_canoni"/></a></li>
				</c:if>	
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${isModifica eq true }">
				<li><a href="javascript:window.location.reload();"><fmt:message key="button.aggiorna" /></a></li>
				<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanza.id.codice}&isModifica=false','');"><fmt:message key="button.back" /></a></li>
			</c:if>		
		</ul>
	</div>

	<style media="all">
	    #onere-non-dovuto {
			background-color:#ddf;
		}
		
		#onere-non-dovuto > td:first-of-type {
			border-left: 4px solid BLUE;
			border-radius: 4px 0 0 4px;
		}
	</style>
	
</body>
</html>