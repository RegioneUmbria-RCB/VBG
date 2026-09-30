<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.richiesta_protocollo" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message
	key="label.richiesta_protocollo" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
	<jsp:include page="../includes/history.jsp">
	   	<jsp:param name="path" value="../protocollazione/protocollazioneResult" />
	</jsp:include>
	<c:if test="${not empty protocolloCommand.entity.id.codice}">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${protocolloCommand.entity.id.codice}</c:param>
		</c:import>
	</c:if>
	<c:if test="${protocolloCommand.provenienza eq 'P'}">
		<fieldset>
			<legend><fmt:message key="pecinbox.label.dati_pec"/></legend> 								
			<table width="100%" border="0">
			<jsp:include page="../pecinbox/datiPEC.jsp" >
		        <jsp:param name="commandName" value="protocolloCommand" />
		        <jsp:param name="labelWidthPercentage" value="15" />
		    </jsp:include>
		    </table>
		</fieldset>
		
		
				<ul id="functions">
					<li><a href="javascript:void(0)" onclick="cercaPraticaSTC()"><fmt:message key="label.ricerca_istanza" /> >></a></li>	
				</ul>
				<script type="text/javascript">
					function cercaPraticaSTC(){
						var ww = window.open("${pageContext.request.contextPath}/stc/popupPannelloRicerca.htm",699,'status=1,menubar=0,scrollbars=1,width=800, height=600');
					}
				</script>		
		
	</c:if>
	<br class="clear" />
<div id="subcontent">
<spring-form:form commandName="protocolloCommand" name="inviodati">
     
    <!-- VARIABILI UTILIZZATE NELLA JSP PER FARE DEI CONTROLLI -->    
    <c:set value="${protocolloCommand.movimento.id.codice}" var="codiceMov" scope="page"></c:set>
    
    <!-- --------------------------------------------------------- --> 

	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="protocolloCommand" />
	</jsp:include>
	<c:set var="_NUMDATAPROTMITT"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NUMDATAPROTMITT)%></c:set>
	<c:set var="_NOALLEGATI"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NOALLEGATI)%></c:set>
	<c:set var="_GESTISCI_FASCICOLAZIONE"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE)%></c:set>	
	<table width="100%">
		<c:if test="${_NUMDATAPROTMITT eq '1'}">
			<tr>
				<td width="15%"><fmt:message key="label.numero_protocollo_mittente" /></td>
				<td>
					<spring-form:input id="numero_protocollo_mittente_id" path="numeroProtocolloMittente" size="10" />
					<spring-form:errors path="numeroProtocolloMittente" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.data_protocollo_mittente" /></td>
				<td>
					<spring-form:input id="data_protocollo_mittente_id" path="dataProtocolloMittente" size="10" maxlength="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataProtocolloMittente" idInput="data_protocollo_mittente_id" textKey="label.calendar"/>			
					<spring-form:errors path="dataProtocolloMittente" cssClass="error"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty regitriList}">
			<tr>
				<td><fmt:message key="label.registro" />
				</td>
				<td>
					<spring-form:select id="registroDocEr_id" path="registroDocEr" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${regitriList}" itemLabel="valore" itemValue="chiave" />
					</spring-form:select>
					<spring-form:errors path="registroDocEr" cssClass="error"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<td width="15%"><fmt:message key="label.classifica" /></td>
			<c:if test="${isModificaClassifica eq false}">
				<td>					
					<spring-form:input path="classifica" disabled="true"  size="70"/>					
					<spring-form:errors path="classifica" cssClass="error"/>
				</td>
			</c:if>
			<c:if test="${isModificaClassifica eq true}">
				<td>
					<c:choose>	
						<c:when test="${not empty listaClassifiches}">
							<spring-form:select path="classifica" >
								<spring-form:options items="${listaClassifiches}" itemLabel="descrizione" itemValue="codice" />
							</spring-form:select>
						</c:when>
						<c:otherwise>
							<spring-form:input path="classifica"   size="70"/>
						</c:otherwise>					
					</c:choose>
					<spring-form:errors path="classifica" cssClass="error"/>
				</td>
			</c:if>
		</tr>
		<c:if test="${not empty documentiList}">
			<tr>
				<td><fmt:message key="label.tipo_documento" />
				</td>
				<td>
					<spring-form:select id="tipoDocumento_id" path="tipoDocumento" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${documentiList}" itemLabel="descrizione" itemValue="codice"/>
					</spring-form:select>
					<spring-form:errors path="tipoDocumento" cssClass="error"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty protocolloSmistamentis}">
			<tr>
				<td><fmt:message key="label.tipo_smistamento" />
				</td>
				<td>
					<spring-form:select id="smistamento_id" path="smistamento" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${protocolloSmistamentis}" itemLabel="descrizione" itemValue="codice"/>
					</spring-form:select>
					<spring-form:errors path="smistamento" cssClass="error"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="label.protocollo_flusso" /></td>
			<td>
				<spring-form:select id="flusso_id" path="flusso" onchange="changeFlusso(this)">
					<spring-form:option value=""></spring-form:option>					
					<spring-form:options items="${flussiInRequest}" itemLabel="descrizione" itemValue="codice"/>
				</spring-form:select>
				<spring-form:errors path="flusso" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.oggetto" /></td>
			<td>
				<spring-form:textarea id="oggetto_id" path="oggetto" cols="100" rows="5" />
				<spring-form:errors path="oggetto" cssClass="error"/>
			</td>
		</tr>
				
		<c:set var="_FLUSSO_INTERNO"><%=ProtocollazioneCommand.FLUSSO_INTERNO %></c:set>
		<c:set var="_FLUSSO_ARRIVO"><%=ProtocollazioneCommand.FLUSSO_ARRIVO%></c:set>
		<c:set var="_FLUSSO_PARTENZA"><%=ProtocollazioneCommand.FLUSSO_PARTENZA%></c:set>
		<c:choose>
			<c:when test="${protocolloCommand.flusso eq _FLUSSO_INTERNO}">			
				<tr>
					<td><fmt:message key="label.mittente" /></td>
					<td>
						<spring-form:select id="mittente_id" path="mittente.amministrazioni.id.codice">
							<spring-form:option value=""></spring-form:option>					
							<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
						</spring-form:select>
						<spring-form:errors path="mittente" cssClass="error"/>
						&nbsp;<init:help idHelp="amministrazioni_id_help_mittente" textKey="label.help_codice_amministrazioni_interne"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.destinatario" /></td>
					<td>
						<spring-form:select id="mittente_id" path="destinatario.amministrazioni.id.codice">
							<spring-form:option value=""></spring-form:option>					
							<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
						</spring-form:select>
						<spring-form:errors path="destinatario" cssClass="error"/>
						&nbsp;<init:help idHelp="amministrazioni_id_help_destinatario" textKey="label.help_codice_amministrazioni_interne"/>
					</td>
				</tr>				
			</c:when>
			<c:when test="${protocolloCommand.flusso eq _FLUSSO_ARRIVO}">	
				<tr>
					<td valign="top"><fmt:message key="label.mittenti" /></td>
					<td valign="top">
						<table width="100%">
							<tr class="titoloSezione">
								<td>
									<fmt:message key="label.azioni" />&nbsp;
				 					<c:if test="${protocolloCommand.provenienza ne 'P'}">
										<a href="javascript:assegnaSoggetto('R')" title="<fmt:message key="label.riporta_richiedente_tra_mittenti" />">R&#x00BB;</a>&nbsp;&nbsp;
										<a href="javascript:assegnaSoggetto('A')" title="<fmt:message key="label.riporta_azienda_tra_mittenti" />">A&#x00BB;</a>&nbsp;&nbsp;
										<a href="javascript:assegnaSoggetto('T')" title="<fmt:message key="label.riporta_professionista_tra_mittenti" />">T&#x00BB;</a>&nbsp;&nbsp;
										<a href="javascript:assegnaSoggetto('S')" title="<fmt:message key="label.riporta_soggetti_collegati_tra_mittenti" />">S&#x00BB;</a>&nbsp;&nbsp;
										<a href="javascript:assegnaSoggetto('AMM')" title="<fmt:message key="label.riporta_amministrazioni_tra_mittenti" />">AMM&#x00BB;</a>
 									</c:if>
				 					<c:if test="${protocolloCommand.provenienza eq 'P'}">
										<a href="javascript:doSubmit('eliminaMittenti.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="pecinbox.label.eliminatuttimittenti" />">
											<img style="vertical-align: middle;" src="../images/cross.gif" alt="<fmt:message key="pecinbox.label.eliminatuttimittenti" />" />
										</a>
 									</c:if>
								</td>
							</tr>
						</table>
					<fieldset><legend><fmt:message key="label.amministrazione" />&nbsp;<init:help idHelp="amministrazioni_id_help_mittenti_anagrafe" textKey="label.help_codice_amministrazioni_interne"/></legend>
					<table style="width:100%;">
						<c:forEach items="${protocolloCommand.mittentis}" var="current" varStatus="a">
							<c:if test="${not empty current.amministrazioni.id.codice}">
								<tr>
									<td>									
										<jsp:include page="../includes/autocompletergenerico.jsp" >
											<jsp:param name="idElemento" value="amministrazione${a.index}_id" />		
											<jsp:param name="propertyPath" value="mittentis[${a.index}].amministrazioni" />				
											<jsp:param name="pathPropertyDescription" value="mittentis[${a.index}].amministrazioni.descrizioneEstesa" />
											<jsp:param name="pathPropertyCode" value="mittentis[${a.index}].amministrazioni.id.codice" />
											<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false&visualizzaPerProtocollo=true" />							
											<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
										</jsp:include>																		
									</td>
									<td>
										<a class="eliminaRiga" href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" />">
											<label><fmt:message key="label.elimina.image" /></label>
										</a>
									</td>
									<td>
										<c:if test="${not empty protocolloMezzis}">
											<label><fmt:message key="label.protocollazione.mezzo" />
											<spring-form:select id="selectMezzo${a.index}" path="mittentis[${a.index}].mezzo.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloMezzis}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
											
									</td>
									<td>
										<c:if test="${not empty protocolloModalitainvios}">
											<label><fmt:message key="label.protocollazione.modalita_invio" />
											<spring-form:select id="selectModInvio${a.index}" path="mittentis[${a.index}].modInvio.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloModalitainvios}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>											
											</label>
										</c:if>
											
									</td>
									<%--
									mezzi e modalità invio
									<c:if test="${DESTINATARICC eq '1' }">
										<td>
											<spring-form:checkbox path="mittentis[${a.index}].perConoscenzaAmm" value="1" title="<fmt:message key='label.help_amministrazione_mittente_per_conoscenza' />"/>
										</td>
									</c:if>
									 --%>
								</tr>	
							</c:if>																				
						</c:forEach>
						</table>
						<br class="clear"/>
						<a href="javascript:nuovoSoggetto('div_amministrazioni_id');" title="<fmt:message key="label.nuovo" />">
							<img src="${pageContext.request.contextPath}/images/add.gif" />
						</a>
						<c:forEach items="${protocolloCommand.mittentis}" var="current" varStatus="a">
							<c:if test="${empty current.amministrazioni.id.codice and empty current.anagrafe.id.codice}">
								<div id="div_amministrazioni_id" style="display: none;">
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="amministrazione${a.index}_id" />		
										<jsp:param name="propertyPath" value="mittentis[${a.index}].amministrazioni" />				
										<jsp:param name="pathPropertyDescription" value="mittentis[${a.index}].amministrazioni.descrizioneEstesa" />
										<jsp:param name="pathPropertyCode" value="mittentis[${a.index}].amministrazioni.id.codice" />
										<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false&visualizzaPerProtocollo=true" />							
										<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
									</jsp:include>	
									<a href="javascript:aggiorna('A','amministrazione${a.index}_id');" title="<fmt:message key="label.aggiungi" />">
										<img src="${pageContext.request.contextPath}/images/save.gif" />
									</a>									
								</div>	
							</c:if>
						</c:forEach>
					</fieldset>		
					<fieldset><legend><fmt:message key="label.anagrafe" /></legend>
						<table>
						<c:forEach items="${protocolloCommand.mittentis}" var="current" varStatus="a">
							<c:if test="${not empty current.anagrafe.id.codice}">
								<tr>
									<td>																			
										<jsp:include page="../includes/anagraficasearch.jsp" >
											<jsp:param name="idElemento" value="anagrafe_${a.index}_id" />						
											<jsp:param name="pathAnagrafica" value="mittentis[${a.index}].anagrafe" />
										</jsp:include>
									</td>
									<td>
										<a class="eliminaRiga" href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" />">
											<label><fmt:message key="label.elimina.image" /></label>
										</a>
									</td>
									

									<td>
										<c:if test="${not empty protocolloMezzis}">
											<label><fmt:message key="label.protocollazione.mezzo" />
											<spring-form:select id="selectMezzo${a.index}" path="mittentis[${a.index}].mezzo.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloMezzis}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
											
									</td>
									<td>
										<c:if test="${not empty protocolloModalitainvios}">
											<label><fmt:message key="label.protocollazione.modalita_invio" />
											<spring-form:select id="selectModInvio${a.index}" path="mittentis[${a.index}].modInvio.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloModalitainvios}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
											
									</td>															
									
									
									<%--
									
									mezzi e modalità invio
									<c:if test="${DESTINATARICC eq '1' }">
										<td>
											<spring-form:checkbox path="mittentis[${a.index}].perConoscenza" value="1" title="<fmt:message key='label.help_anagrafe_mittente_per_conoscenza' />"/>
										</td>
									</c:if>
									--%>
								</tr>																								
							</c:if>
						</c:forEach>
						</table>
						<br class="clear"/>
						<a href="javascript:nuovoSoggetto('div_anagrafe_id');" title="<fmt:message key="label.nuovo" />">
							<img src="${pageContext.request.contextPath}/images/add.gif" />
						</a>
						<c:forEach items="${protocolloCommand.mittentis}" var="current" varStatus="a">
							<c:if test="${empty current.amministrazioni.id.codice and empty current.anagrafe.id.codice}">
								<div id="div_anagrafe_id" style="display: none;">
									<jsp:include page="../includes/anagraficasearch.jsp" >
										<jsp:param name="idElemento" value="anagrafe_${a.index}_id" />						
										<jsp:param name="pathAnagrafica" value="mittentis[${a.index}].anagrafe" />		
									</jsp:include>
									<a href="javascript:aggiorna('R','anagrafe_${a.index}_id');" title="<fmt:message key="label.aggiungi" />">
										<img src="${pageContext.request.contextPath}/images/save.gif" />
									</a>
								</div>
							</c:if>							
						</c:forEach>
					</fieldset>
					
					<spring-form:errors path="mittentis" cssClass="error"/>		
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.destinatario" /></td>
					<td>
						<spring-form:select id="mittente_id" path="destinatario.amministrazioni.id.codice">
							<spring-form:option value=""></spring-form:option>					
							<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
						</spring-form:select>
						<spring-form:errors path="destinatario" cssClass="error"/>
						&nbsp;<init:help idHelp="amministrazioni_id_help_destinatario_anagrafe" textKey="label.help_codice_amministrazioni_interne"/>
					</td>
				</tr>
			</c:when>
			<c:when test="${protocolloCommand.flusso eq _FLUSSO_PARTENZA}">
				<tr>
					<td><fmt:message key="label.mittente" /></td>
					<td>
						<spring-form:select id="mittente_id" path="mittente.amministrazioni.id.codice">
							<spring-form:option value=""></spring-form:option>					
							<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
						</spring-form:select>
						<spring-form:errors path="mittente" cssClass="error"/>
						&nbsp;<init:help idHelp="amministrazioni_id_help_mittente_amministrazioni" textKey="label.help_codice_amministrazioni_interne"/>
					</td>
				</tr>
				<tr>
					<td valign="top"><fmt:message key="label.destinatari" /></td>
					<td valign="top">
					<c:if test="${protocolloCommand.provenienza ne 'P'}">
						<table width="100%">
							<tr class="titoloSezione">
								<td><fmt:message key="label.azioni" />&nbsp;
									<a href="javascript:assegnaSoggetto('R')" title="<fmt:message key="label.riporta_richiedente_tra_mittenti" />">R&#x00BB;</a>&nbsp;&nbsp;
									<a href="javascript:assegnaSoggetto('A')" title="<fmt:message key="label.riporta_azienda_tra_mittenti" />">A&#x00BB;</a>&nbsp;&nbsp;
									<a href="javascript:assegnaSoggetto('T')" title="<fmt:message key="label.riporta_professionista_tra_mittenti" />">T&#x00BB;</a>&nbsp;&nbsp;
									<a href="javascript:assegnaSoggetto('S')" title="<fmt:message key="label.riporta_soggetti_collegati_tra_mittenti" />">S&#x00BB;</a>&nbsp;&nbsp;
									<a href="javascript:assegnaSoggetto('AMM')" title="<fmt:message key="label.riporta_amministrazioni_tra_mittenti" />">AMM&#x00BB;</a>
								</td>
							</tr>
						</table>
					</c:if>
					<fieldset><legend><fmt:message key="label.amministrazione" />&nbsp;<init:help idHelp="amministrazioni_id_help_destinatario_amministrazioni" textKey="label.help_codice_amministrazioni_interne"/></legend>
					<table>
						<c:forEach items="${protocolloCommand.destinataris}" var="current" varStatus="a">
							<c:if test="${not empty current.amministrazioni.id.codice}">
								<tr>
									<td>									
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="amministrazione${a.index}_id" />		
										<jsp:param name="propertyPath" value="destinataris[${a.index}].amministrazioni" />				
										<jsp:param name="pathPropertyDescription" value="destinataris[${a.index}].amministrazioni.descrizioneEstesa" />
										<jsp:param name="pathPropertyCode" value="destinataris[${a.index}].amministrazioni.id.codice" />
										<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false&visualizzaPerProtocollo=true" />							
										<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
									</jsp:include>																		
								</td>
									<td>
										<a class="eliminaRiga" href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" />">
											<label><fmt:message key="label.elimina.image" /></label>
										</a>
									</td>
									
									<td>
										<c:if test="${not empty protocolloMezzis}">
											<label><fmt:message key="label.protocollazione.mezzo" />
											<spring-form:select id="selectMezzo${a.index}" path="destinataris[${a.index}].mezzo.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloMezzis}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
											
									</td>
									<td>
										<c:if test="${not empty protocolloModalitainvios}">
											<label><fmt:message key="label.protocollazione.modalita_invio" />
											<spring-form:select id="selectModInvio${a.index}" path="destinataris[${a.index}].modInvio.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloModalitainvios}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
											
									</td>
									
									<%--
									
									mezzi e modalità invio
									<c:if test="${DESTINATARICC eq '1' }">
										<td>
											<spring-form:checkbox path="destinataris[${a.index}].perConoscenzaAmm" value="1" title="<fmt:message key='label.help_amministrazione_destinatario_per_conoscenza'/>"/>
										</td>
									</c:if>	
									--%>								
								</tr>	
							</c:if>																				
						</c:forEach>
						</table>
						<br class="clear"/>
						<a href="javascript:nuovoSoggetto('div_amministrazioni_id');" title="<fmt:message key="label.nuovo" />">
							<img src="${pageContext.request.contextPath}/images/add.gif" />
						</a>
						<c:forEach items="${protocolloCommand.destinataris}" var="current" varStatus="a">
							<c:if test="${empty current.amministrazioni.id.codice and empty current.anagrafe.id.codice}">
								<div id="div_amministrazioni_id" style="display: none;">
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="amministrazione${a.index}_id" />		
										<jsp:param name="propertyPath" value="destinataris[${a.index}].amministrazioni" />				
										<jsp:param name="pathPropertyDescription" value="destinataris[${a.index}].amministrazioni.descrizioneEstesa" />
										<jsp:param name="pathPropertyCode" value="destinataris[${a.index}].amministrazioni.id.codice" />
										<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false&visualizzaPerProtocollo=true" />							
										<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
									</jsp:include>	
									<a href="javascript:aggiorna('A','amministrazione${a.index}_id');" title="<fmt:message key="label.aggiungi" />">
										<img src="${pageContext.request.contextPath}/images/save.gif" />
									</a>
								</div>	
							</c:if>
						</c:forEach>
					</fieldset>		
					<fieldset><legend><fmt:message key="label.anagrafe" /></legend>
						<table>
						<c:forEach items="${protocolloCommand.destinataris}" var="current" varStatus="a">
							<c:if test="${not empty current.anagrafe.id.codice}">
								<tr>
									<td>																			
										<jsp:include page="../includes/anagraficasearch.jsp" >
											<jsp:param name="idElemento" value="anagrafe_${a.index}_id" />						
											<jsp:param name="pathAnagrafica" value="destinataris[${a.index}].anagrafe" />
										</jsp:include>
									</td>
									<td>
										<a class="eliminaRiga" href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" />">
											<label><fmt:message key="label.elimina.image" /></label>
										</a>
									</td>
									
									
									<td>
										<c:if test="${not empty protocolloMezzis}">
											<label><fmt:message key="label.protocollazione.mezzo" />
											<spring-form:select id="selectMezzo${a.index}" path="destinataris[${a.index}].mezzo.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloMezzis}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
											
									</td>
									<td>
										<c:if test="${not empty protocolloModalitainvios}">
											<label><fmt:message key="label.protocollazione.modalita_invio" />
											<spring-form:select id="selectModInvio${a.index}" path="destinataris[${a.index}].modInvio.codice">
												<spring-form:option value=""></spring-form:option>
												<spring-form:options items="${protocolloModalitainvios}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
											</spring-form:select>
											</label>
										</c:if>
										
											
									</td>
									
									<%--									
									mezzi e modalità invio
									<c:if test="${DESTINATARICC eq '1' }">
										<td>
											<spring-form:checkbox path="destinataris[${a.index}].perConoscenza" value="1" title="<fmt:message key='label.help_anagrafe_destinatario_per_conoscenza'/>"/>
										</td>
									</c:if>
									--%>
								</tr>																								
							</c:if>
						</c:forEach>
						</table>
						<br class="clear"/>
						<a href="javascript:nuovoSoggetto('div_anagrafe_id');" title="<fmt:message key="label.nuovo" />">
							<img src="${pageContext.request.contextPath}/images/add.gif" />
						</a>
						<c:forEach items="${protocolloCommand.destinataris}" var="current" varStatus="a">
							<c:if test="${empty current.amministrazioni.id.codice and empty current.anagrafe.id.codice}">
								<div id="div_anagrafe_id" style="display: none;">
									<jsp:include page="../includes/anagraficasearch.jsp" >
										<jsp:param name="idElemento" value="anagrafe_${a.index}_id" />						
										<jsp:param name="pathAnagrafica" value="destinataris[${a.index}].anagrafe" />		
									</jsp:include>
									<a href="javascript:aggiorna('R','anagrafe_${a.index}_id');" title="<fmt:message key="label.aggiungi" />">
										<img src="${pageContext.request.contextPath}/images/save.gif" />
									</a>
								</div>
							</c:if>							
						</c:forEach>
					</fieldset>	
					<spring-form:errors path="destinataris" cssClass="error"/>	
					</td>
				</tr>							
			</c:when>		
		</c:choose>	
			<%-- Il primo gestisce la visualizzazione solo se il protocollo è in uscita, il secondo se è impostato 
			in verticalizzaizone --%>
			
		<c:if test="${protocolloCommand.isMostraSezioneConfigurazione && isVerticSezionePerInvioAllegatiComeLink}">
			<tr>
				<td>
						<fmt:message key="tipimovimento.label.flag_allegati_link"/>
					</td>
					<td>
						<spring-form:checkbox id="flgProtocollalinkall_id" path="flgProtocollalinkall" />
						<fmt:message key="help.flag_allegati_link"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.letteratipo_linkdoc" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="letteraTipoAllegati" />		
						<jsp:param name="propertyPath" value="letteraTipoAllegati" />				
						<jsp:param name="pathPropertyDescription" value="letteraTipoAllegati.descrizione" />
						<jsp:param name="pathPropertyCode" value="letteraTipoAllegati.id.codice" />
						<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />	
						<jsp:param name="titleKey" value="label.ricerca_tipo_lettera" />
						<jsp:param name="id_help" value="help_letteraTipoAllegati" />
						<jsp:param name="help" value="help.search_archivi_base" />
					</jsp:include>
					<fmt:message key="help.letteratipo_linkdoc"/>
					</td>
				</tr>				
		</c:if>	
	</table>

    
    <br class="clear" />
       
            <%--Inizializzo le variabili --%>
    		<c:set scope="page" value="0" var="num_doc_ist"></c:set>
			<c:set scope="page" value="0" var="num_doc_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_altri_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_endo"></c:set>
    	<c:choose>
			<c:when test="${_NOALLEGATI eq '1' }">
				<c:set scope="page" value="0" var="num_doc_ist"></c:set>
			<c:set scope="page" value="0" var="num_doc_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_altri_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_endo"></c:set>
			</c:when>
			<c:otherwise>
		
	
			<script type="text/javascript">
			
			
			function selezionaAndDeselezionaTutti(){
				if(jQuery('#a_check_allegati').attr('checked')!='checked')
				{	
					jQuery("input[id^='di_']").attr('checked', false);
					jQuery("input[id^='ma_']").attr('checked', false);
					jQuery("input[id^='altrima_']").attr('checked', false);
					jQuery("input[id^='ia_']").attr('checked', false);
					jQuery("input[id^='docanagr_']").attr('checked', false);
				}else{	
					jQuery("input[id^='di_']").attr('checked', true);
					jQuery("input[id^='ma_']").attr('checked', true);
					jQuery("input[id^='altrima_']").attr('checked', true);
					jQuery("input[id^='ia_']").attr('checked', true);
					jQuery("input[id^='docanagr_']").attr('checked', true);
				}
			}
			</script>
        <fieldset><legend><fmt:message	key="label.sezione_documenti" /></legend>
         
	            <div> 
	                <div style="float: right;">
					<input id="a_check_allegati" type="checkbox" onclick="selezionaAndDeselezionaTutti()"></input>
					<label id="message_label"><fmt:message key="label.seleziona_deseleziona_tutti" /></label>
				    </div>
				</div>
				
        <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEL MOVIMENTO  -->
            
			<div class="jmesa">
			<table border="0" cellpadding="2" cellspacing="0" class="table">
			<c:if test="${not empty protocolloCommand.documentiHelper.documentiMovimentoList}">
       			  <%int i=1;%>
				<%-- <thead> --%>
					<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.documenti_movimento" />
						</td>
					</tr>
					
				<%-- </thead> --%>
				<%--<tbody class="tbody">--%>
				
				<c:forEach items="${protocolloCommand.documentiHelper.documentiMovimentoList}" var="current" varStatus="a">
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								<td width="15%"><fmt:message key="label.principale" /></td>
			            	</tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
							    <tr class= "<%=(i%2)==0?"odd":"even"%>">
								<td width="50%">${var.descrizione}</td>
								<td width="35%">${var.oggetto.nomefile}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.oggetto.id.codice}" />
			   						</jsp:include>
   							    </td>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>		
								<td>
									<spring:bind path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	
							    <td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.oggetto.id.codice}" name="documentoPrincipale"/></td>
							    
							</tr>
							<%i++;%>								
							</c:forEach>
							
						</table>
						</td>
					</tr>
				</c:forEach>	
		   </c:if>		
		   
		   <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEGLI ALTRI MOVIMENTI  -->
		   <c:if test="${not empty protocolloCommand.documentiHelper.documentiAltriMovimentiList}">
		   <%int j=1;%>
				<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.documenti_altri_movimenti" />
						</td>
				</tr>
				<c:forEach items="${protocolloCommand.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4" ><fmt:message key="movimentimail.label.documento" /> </td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								<td width="15%"><fmt:message key="label.principale" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b"> 							
								<tr class= "<%=(j%2)==0?"odd":"even"%>">
								<td width="50%">${var.descrizione}</td>
								<td width="35%">${var.oggetto.nomefile}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.oggetto.id.codice}" />
			   						</jsp:include>
   							    </td>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>		
								<td>
									<spring:bind path="documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	
								<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.oggetto.id.codice}" name="documentoPrincipale"/></td>						
							<%j++; %>	
							</c:forEach>
							
						</table>
						</td>
					</tr>
				</c:forEach>	
        </c:if>
        <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
        <c:if test="${not empty protocolloCommand.documentiHelper.documentiIstanzaList}">
        <%int k=1;%>
				<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.allegati_istanza" />
						</td>
				</tr>
			<c:forEach items="${protocolloCommand.documentiHelper.documentiIstanzaList}" var="current" varStatus="a">
				<tr>
					<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
					<td width="75%" colspan="4">
					<table width="100%">
						<tr class="header" >
						    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
							<td width="2%" ><fmt:message key="label.seleziona" /></td>
							<td width="15%"><fmt:message key="label.principale" /></td>
			            </tr>
						<c:forEach items="${current.valore}" var="var" varStatus="b">	
							<tr class= "<%=(k%2)==0?"odd":"even"%>">
								<td width="50%">${var.documento}</td>
								<td width="35%">${var.oggetto.nomefile}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.oggetto.id.codice}" />
			   						</jsp:include>
   							    </td>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td>
									<spring:bind path="documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	
								<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.oggetto.id.codice}" name="documentoPrincipale" /></td>
							</tr>
							<%k++; %>								
						</c:forEach>
					</table>
					</td>
				</tr>
			    </c:forEach>
			 </c:if>	
     
		     <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEGLI ENDO  DELL' ISTANZA  -->
		     <c:if test="${not empty protocolloCommand.documentiHelper.documentiEndoprocedimentiList}">
		     <%int b=1;%>
					<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.allegati_endoprocedimenti" />
						</td>
					</tr>
					<c:forEach items="${protocolloCommand.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
						<tr>
							<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
							<td width="75%" colspan="4">
							<table width="100%">
								<tr class="header" >
								    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
									<td width="2%" ><fmt:message key="label.seleziona" /></td>
									<td width="15%"><fmt:message key="label.principale" /></td>
					            </tr>
								<c:forEach items="${current.valore}" var="var" varStatus="b">	
									<tr class= "<%=(b%2)==0?"odd":"even"%>">
										<td width="50%">${var.allegatoextra}</td>
										<td width="35%">${var.oggetto.nomefile}</td>
										<td width="13%">
											<jsp:include page="../includes/visualizzaOggetto.jsp" >
					       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
					       						<jsp:param name="fileId" value="${var.oggetto.id.codice}" />
					   						</jsp:include>
		   							    </td>
		   							    <td>
		   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
					       						<jsp:param name="controllook" value="${var.controllook}" />
					   						</jsp:include>
   							    		</td>		
										<td>
											<spring:bind path="documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
											<c:if test="${codiceMov == null }">
												<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										    </c:if>
										    <c:if test="${codiceMov != null }">
												<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										    </c:if>
											</spring:bind>
										</td>	
										<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.oggetto.id.codice}" name="documentoPrincipale" /></td>
									 </tr>
									<%b++; %>								
								</c:forEach>
							</table>
							</td>
						</tr>
					</c:forEach>	
					</c:if> 
				    <!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE -->
					<c:if test="${not empty protocolloCommand.documentiHelper.documentiAnagrafeList}">
					<%int p=1;%>
					<%-- <div class="jmesa">
					<table border="0" cellpadding="2" cellspacing="0" class="table">
						<thead>
					--%>	
					<tr class="header" >
							<td colspan="5">
								<fmt:message key="label.allegati_anagrafiche" />
							</td>
					</tr>
					<c:forEach items="${protocolloCommand.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								<td width="15%"><fmt:message key="label.principale" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
								<tr class= "<%=(p%2)==0?"odd":"even"%>">
									<td width="50%">${var.tipidocumento.documento}</td>
									<td width="35%">${var.oggetto.nomefile}</td>
									<td width="13%">
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.oggetto.id.codice}" />
				   						</jsp:include>
		  							 </td>
		  							 <td>
		  							    &nbsp;
		  							    <%-- 
	   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
				       						<jsp:param name="controllook" value="${var.controllook}" />
				   						</jsp:include>
				   						--%>
				   						
   							    	</td>		
									<td>
										<spring:bind path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<input id="docanagr_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										</spring:bind>
									</td>	
									<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.oggetto.id.codice}" name="documentoPrincipale" /></td>
								</tr>
								<%p++; %>								
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:forEach>
				</c:if>	
		    
		    
		    <%-- DOCUMENTO PER PROTOCOLLAZIONE PEC --%>
			<c:if test="${protocolloCommand.provenienza eq 'P'}">
					<thead>
						<tr class="header">
							<td width="90%"><fmt:message key="label.allegati" /></td>
							<td width="5%"><fmt:message key="label.seleziona" /></td>

							<td width="5%"><fmt:message key="label.principale" /></td>

						</tr>
					</thead>
					<tbody>
						<tr class="even">
							<td width="90%">Messaggio PEC: ${protocolloCommand.pec.pecSubject} </td>
							<td width="5%"> 
								<input id="pec_doc" type="checkbox" name="pec_doc" value="true" checked="true" 	disabled="disabled"/>
							</td>
							<td width="5%">
								<input id="radio_button_doc_pec" type="radio" value="1" name="documentoPrincipale" checked="true"/> 
							</td>
						</tr>
					</tbody>
			</c:if>
		    </table>
		    </div>
		   </fieldset>
		  </c:otherwise>
		 </c:choose>
         <br class="clear" />
    
    <!-- CODICE TOLTO START -->
    <!-- CODICE TOLTO END -->	

<c:if test="${protocolloCommand.registrazioneParticolareDocEr eq false}"> 
	<%-- la fascicolazione non viene gestita in Registrazioneparticolare DOCER --%>   
	<c:if test="${_GESTISCI_FASCICOLAZIONE eq '1' }">
		<c:set var="readOnlyFascicolo" value="true"></c:set>
		<c:set var="isMovimento" value="true"></c:set>
		<c:if test="${empty protocolloCommand.movimento.id.codice}">
			<c:set var="readOnlyFascicolo" value=""></c:set>
			<c:set var="isMovimento" value="false"></c:set>
		</c:if>
	
		<fieldset><legend><fmt:message	key="label.parametri_fascicolazione" /></legend> 
		<table width="100%">
		<tr>
			<td width="15%"><fmt:message key="label.numero_fascicolo" /></td>
			<td>
				<%-- listaFascicoli non  più gestito
				<c:if test="${not empty listaFascicoli}">
					<spring-form:select id="numeroFascicolo_id" path="numeroFascicolo" onchange="changeNumeroFascicolo();">
						<spring-form:options items="${listaFascicoli}" itemLabel="descrizione" itemValue="descrizione"/>
					</spring-form:select>
				</c:if>
				<c:if test="${empty listaFascicoli}">
				 --%>
					<spring-form:input id="numeroFascicolo_id" path="numeroFascicolo" size="100" readonly="${readOnlyFascicolo}" />
				<%-- listaFascicoli non  più gestito 
				</c:if>			
				--%>	
				<spring-form:errors path="numeroFascicolo" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.data" /></td>
			<td>
				<c:if test="${isMovimento eq 'true' }">
					<spring-form:input id="dataFascicolo_id" path="dataFascicolo" size="10" maxlength="10"  readonly="${readOnlyFascicolo}" />
				</c:if> 
				<c:if test="${isMovimento eq 'false' }">
				<%-- listaFascicoli non  più gestito
					<c:if test="${not empty listaFascicoli}">
						<spring-form:select id="dataFascicolo_id" path="dataFascicolo" onchange="changeDataFascicolo();">
							<spring-form:options items="${listaFascicoli}" itemLabel="codice" itemValue="codice"/>
						</spring-form:select>
					</c:if>
					<c:if test="${empty listaFascicoli}">
					--%>
						<spring-form:input id="dataFascicolo_id" path="dataFascicolo" size="10" maxlength="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataFascicolo" idInput="dataFascicolo_id" textKey="label.calendar"/>																
					<%-- listaFascicoli non  più gestito 
					</c:if>			
					--%>
				</c:if>
				<spring-form:errors path="dataFascicolo" cssClass="error"/>
			</td>
		</tr>
		<tr id="classificaOggetto">
			<td><fmt:message key="label.classifica" /></td>
			<c:if test="${isModificaClassificaParametriProt eq false}">
				<td>
					<spring-form:input path="classificaFascicolo" id="classificaFascicolo_id" disabled="true"  size="70" readonly="${readOnlyFascicolo}" />
					<spring-form:errors path="classificaFascicolo" cssClass="error"/>
				</td>
			</c:if>
			<c:if test="${isModificaClassificaParametriProt eq true}">
				<td>
					<c:choose>	
						<c:when test="${not empty listaClassificheFascicolis}">
							<spring-form:select path="classificaFascicolo" id="classificaFascicolo_id" >
								<spring-form:options items="${listaClassificheFascicolis}" itemLabel="descrizione" itemValue="codice" />
							</spring-form:select>
						</c:when>
						<c:otherwise>
							<spring-form:input path="classificaFascicolo" id="classificaFascicolo_id"  size="70"/>
						</c:otherwise>					
					</c:choose>				
					<spring-form:errors path="classificaFascicolo" cssClass="error"/>
				</td>
			</c:if>
		</tr>
		<tr id="classificaOggetto">
			<td><fmt:message key="label.oggetto" />
			</td>
			<td>
				<spring-form:input id="oggettoFascicolo_id" path="oggettoFascicolo" size="70" readonly="${readOnlyFascicolo}" />
				<spring-form:errors path="oggettoFascicolo" cssClass="error"/>
			</td>
		</tr>
		
		<tr id="classificaOggetto">
			<td><fmt:message key="label.anno" />
			</td>
			<td>
				<spring-form:input id="annoFascicolo_id" path="annoFascicolo" size="6" readonly="${readOnlyFascicolo}" />
				<spring-form:errors path="annoFascicolo" cssClass="error"/>
			</td>
		</tr>
		</table>

		<c:if test="${isDocEr eq true}">
			<fmt:message key="label.ricerca_fascicoli" />
			<a href="javascript:void(0);" onclick="dijit.byId('pannello_ricerca_fascicoli_id').show();"><img src="${pageContext.request.contextPath}/images/find.gif" border="0" /></a>
			
		</c:if>

		</fieldset>
		</c:if>		
</c:if>
		<br class="clear" />
	
</spring-form:form>

<c:if test="${protocolloCommand.registrazioneParticolareDocEr eq false}">

		<c:if test="${isDocEr eq true}">
			<div dojoType="dijit.Dialog" id="pannello_ricerca_fascicoli_id" 
				 title="<fmt:message key="label.ricerca_fascicoli" />" style="display: none;">
				 <span style="width: 100%; border: thin;">
				 	<fmt:message key="label.ricerca_fascicoli.docer_help" />
				 </span>
				<table>
				<tr>
					<td><fmt:message key="label.anno" /></td>
					<td><input type="text" id="s_anno_id" size="10"/></td>
				</tr>
				<tr>	
					<td><fmt:message key="label.numero" /></td>
					<td><input type="text" id="s_numero_id" size="10"/></td>
				</tr>
				<tr>	
					<td><fmt:message key="label.classifica" /></td>
					<td><input type="text" id="s_classifica_id" size="10"/></td>
				</tr>
				<tr>	
					<td><fmt:message key="label.descrizione" /></td>
					<td><input type="text" id="s_descrizione_id" size="20"/></td>
				</tr>
				</table>	
				<div id="functions">
					<ul>
						<li><a href="javascript:cercaFascicoli();"><fmt:message key="button.search" /></a></li>
					</ul>
				</div>
				<br class="clear" />
				<div id="cercaFascicoliRisposta_id" style="overflow-y: scroll; width: 600px; height: 250px;" ></div>
				<br class="clear" />
			</div>
			<script type="text/javascript">
			
				function scegliFascicolo(idx){
					
					jQuery('#annoFascicolo_id').val(jQuery('#anno_fasc_'+idx).html());
					jQuery('#numeroFascicolo_id').val(jQuery('#numero_fasc_'+idx).html());
					jQuery('#oggettoFascicolo_id').val(jQuery('#oggetto_fasc_'+idx).html());
					jQuery('#classificaFascicolo_id').val(jQuery('#classifica_fasc_'+idx).html());					
					// jQuery('#dataFascicolo_id').val(jQuery('#data_fasc_'+idx).html());
					dijit.byId('pannello_ricerca_fascicoli_id').hide();
					
					
				}
				function cercaFascicoli(){
					
					var anno = jQuery('#s_anno_id').val();
					var nf = jQuery('#s_numero_id').val();
					var oggetto = jQuery('#s_descrizione_id').val();
					var classifica = jQuery('#s_classifica_id').val();
					var htmlInCorso = "ricerca in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
					jQuery('#cercaFascicoliRisposta_id').html(htmlInCorso);
					var jhqrPr = jQuery.ajax({
						  url: '../protocollazione/ajaxCercaFascicoli.htm',
						  context: document.body,
						  cache: false,
						  data: "anno="+anno+"&numeroFascicolo="+nf+"&oggetto="+oggetto+"&classifica="+classifica,
						  dataType: "html",
						  success: function(data) {
								jQuery('#cercaFascicoliRisposta_id').html(data);
						  },
						  error: function(jqXHR, textStatus, errorThrown){
							  jQuery('#cercaFascicoliRisposta_id').html(jqXHR.responseText);
						  }
					});
					
				}
			</script>
			
			
			
		</c:if>
		
					



</c:if>
</div>

<script type="text/javascript">

	function protocolla(messPassato)
	{
		
		var mess='\nAttenzione,allegato principale non selezionato. Se necessario, selezionarne uno prima di protocollare';
		if('${_NOALLEGATI}' == '0' && !rb_controllo())
		{   
			doSubmit('protocolla.htm',messPassato+mess,document.inviodati);
			
		}else
		{
			
			doSubmit('protocolla.htm',messPassato,document.inviodati);
		}
		
	}
	
	
	
    function rb_controllo(){    	
    	<c:choose>
	    	<c:when test="${protocolloCommand.provenienza ne 'P'}">
	    	var rb_scelto = false;	    	
	        if(document.inviodati.documentoPrincipale){
	                 for (counter = 0; counter < document.inviodati.documentoPrincipale.length; counter++) {
	                 	if (document.inviodati.documentoPrincipale[counter].checked){
	                         rb_scelto = true;
	                 	}
	                 }
	                 if (!rb_scelto) {
	                     //alert("Selezionare almeno un'opzione");
	                     return (false);
	                 }
	         }
	         return (true);

	        </c:when>
	        <c:otherwise>
	        return true;
	        </c:otherwise>
        </c:choose>
    }
   
	
	function changeFlusso(obj){
		doSubmit('changeFlusso.htm','',document.inviodati);
	}
	
	function nuovoSoggetto(divId){
		showHideDiv(divId);
	}
	
	function aggiorna(tipoSoggetto, idElemento){
		var soggetto = $(idElemento+'_hidden');
		if(soggetto.value!=''){
			doSubmit('aggiorna.htm?tipoSoggetto='+tipoSoggetto,'',document.inviodati);
		}
		
	}
	
	function assegnaSoggetto(tipoAssegnazione){
		doSubmit('assegnaSoggetto.htm?tipoSoggetto='+tipoAssegnazione,'',document.inviodati);
	}
	
	
	function changeDataFascicolo(){
		var pos = $('dataFascicolo_id').selectedIndex;
		$('numeroFascicolo_id').selectedIndex=pos;			
		gestClassificaOggetto();
	}
	function changeNumeroFascicolo(){
		var pos = $('numeroFascicolo_id').selectedIndex;
		$('dataFascicolo_id').selectedIndex=pos;			
		gestClassificaOggetto();
	}
	
	function gestClassificaOggetto(){
		
		var numeroFascicolo = getSelectTextAndValue($('numeroFascicolo_id'));
		
		if(numeroFascicolo[0]==''){
			mostraNascondiElems(false);					
		}else{
			mostraNascondiElems(true);
		}
		
	}
	function mostraNascondiElems(mostra){
		var nodeList = document.getElementsByTagName('tr');
		for(var i=0;i<nodeList.length;i++){
			var currEl=nodeList[i];
			if (currEl.id == 'classificaOggetto'){
				if(mostra){
					currEl.style.display='';
				}else{
					currEl.style.display='none';
				}
			}
		}
	}
	
	
	function checkIsPrincipale(id)
    {
    	
    	//$(id).checked=false;
    }
    

	function backToPecInbox(){
		doHref("${pageContext.request.contextPath}/pecinbox/indietro.htm?idPec=${protocolloCommand.pec.id.id}", "");
	}
	
</script>
<div id="functions">
<ul>
	<c:if test="${protocolloCommand.mettiAllaFirma == null || protocolloCommand.mettiAllaFirma eq false}">
		<c:if test="${protocolloCommand.registrazioneParticolareDocEr eq true}">
			<li><a href="javascript:protocolla('');"><fmt:message key="label.registrazione_docer" /></a></li>
		</c:if>
		<c:if test="${protocolloCommand.registrazioneParticolareDocEr eq false}">
			<li><a href="javascript:protocolla('');"><fmt:message key="button.protocolla" /></a></li>
		</c:if>		
	</c:if>
	<c:if test="${protocolloCommand.mettiAllaFirma eq true}">
	<li><a href="javascript:protocolla('<fmt:message key="javascript.confirm.mettiallafirma" />');"><fmt:message key="button.metti_alla_firma" /></a></li>
	</c:if>
	<c:if test="${protocolloCommand.provenienza ne 'P'}">
		<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
	</c:if>
	<c:if test="${protocolloCommand.provenienza eq 'P'}">
		<li><a href="javascript:backToPecInbox();"><fmt:message	key="button.back" /></a></li>
	</c:if>
</ul>
</div>
</body>
</html>