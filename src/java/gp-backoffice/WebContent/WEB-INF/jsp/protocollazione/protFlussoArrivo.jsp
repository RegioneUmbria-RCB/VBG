<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<tr>
	<td valign="top"><fmt:message key="label.mittenti" /></td>
	<td valign="top">
		<table width="100%">
			<tr class="titoloSezione">
				<td><fmt:message key="label.azioni" /> &nbsp; 
					<c:if
						test="${protocolloCommand.provenienza ne 'P'}">
						<a href="javascript:assegnaSoggetto('R')" title="<fmt:message key="label.riporta_richiedente_tra_mittenti" />">R&#x00BB;</a>&nbsp;&nbsp;						
						<a href="javascript:assegnaSoggetto('A')" title="<fmt:message key="label.riporta_azienda_tra_mittenti" />">A&#x00BB;</a>&nbsp;&nbsp;						
						<a href="javascript:assegnaSoggetto('T')" title="<fmt:message key="label.riporta_professionista_tra_mittenti" />">T&#x00BB;</a>&nbsp;&nbsp;				
						<a href="javascript:assegnaSoggetto('S')" title="<fmt:message key="label.riporta_soggetti_collegati_tra_mittenti" />">S&#x00BB;</a>&nbsp;&nbsp;						
						<a href="javascript:assegnaSoggetto('AMM')" title="<fmt:message key="label.riporta_amministrazioni_tra_mittenti" />">AMM&#x00BB;</a>
					</c:if> 
					<c:if test="${protocolloCommand.provenienza eq 'P'}">
						<a class="vbg-btn btn-elimina" href="javascript:doSubmit('eliminaMittenti.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="pecinbox.label.eliminatuttimittenti" />">
						</a>
					</c:if>
				</td>
			</tr>
		</table>
		<fieldset>
			<legend>
				<fmt:message key="label.amministrazione" />	&nbsp;
				<init:help idHelp="amministrazioni_id_help_mittenti_anagrafe" textKey="label.help_codice_amministrazioni_interne" />
			</legend>
			<table style="width: 100%;">
				<colgroup>
					<col style="width: 75%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
					<col style="width: 5%;">
				</colgroup>
				<tr>
					<th><label>&nbsp;</label></th>
					<th><label><fmt:message key="label.protocollazione.mezzo" /></label></th>
					<th><label><fmt:message key="label.protocollazione.modalita_invio" /></label></th>
					<th><label>Canc.</label></th>
				</tr>
				<c:forEach items="${protocolloCommand.mittentis}" var="current"
					varStatus="a">
					<c:if test="${not empty current.amministrazioni.id.codice}">
						<tr>
							<td class="inline-ui-cell" valign="bottom">
								<jsp:include
									page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento"
										value="amministrazione${a.index}_id" />
									<jsp:param name="propertyPath"
										value="mittentis[${a.index}].amministrazioni" />
									<jsp:param name="pathPropertyDescription"
										value="mittentis[${a.index}].amministrazioni.descrizioneEstesa" />
									<jsp:param name="pathPropertyCode"
										value="mittentis[${a.index}].amministrazioni.id.codice" />
									<jsp:param name="autocompleterAjax"
										value="findAmministrazioni.htm?tutteLeAmministrazioni=false&visualizzaPerProtocollo=true" />
									<jsp:param name="titleKey"
										value="label.ricerca_tipiarchivioistanze" />
								</jsp:include>
							</td>
							<td>
								<c:if test="${not empty protocolloMezzis}">
									<spring-form:select id="selectMezzo${a.index}"
										path="mittentis[${a.index}].mezzo.codice">
										<spring-form:option value=""></spring-form:option>
										<spring-form:options items="${protocolloMezzis}"
											itemLabel="descrizione" itemValue="codice"></spring-form:options>
									</spring-form:select>
								</c:if>
							</td>
							<td>
								<c:if test="${not empty protocolloModalitainvios}">
									<spring-form:select id="selectModInvio${a.index}"
										path="mittentis[${a.index}].modInvio.codice">
										<spring-form:option value=""></spring-form:option>
										<spring-form:options items="${protocolloModalitainvios}"
											itemLabel="descrizione" itemValue="codice"></spring-form:options>
									</spring-form:select>
								</c:if>
							</td>
							<td style="text-align: center;">
								<a class="vbg-btn btn-elimina"
								href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);"
								title="<fmt:message key="label.elimina" />"> </a>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			<br class="clear" /> <a class="vbg-btn btn-aggiungi"
				href="javascript:nuovoSoggetto('div_amministrazioni_id');"
				title="<fmt:message key="label.nuovo" />"> </a>
			<c:forEach items="${protocolloCommand.mittentis}" var="current"
				varStatus="a">
				<c:if
					test="${empty current.amministrazioni.id.codice and empty current.anagrafe.id.codice}">
					<div id="div_amministrazioni_id" style="display: none;">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="amministrazione${a.index}_id" />
							<jsp:param name="propertyPath"
								value="mittentis[${a.index}].amministrazioni" />
							<jsp:param name="pathPropertyDescription"
								value="mittentis[${a.index}].amministrazioni.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode"
								value="mittentis[${a.index}].amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax"
								value="findAmministrazioni.htm?tutteLeAmministrazioni=false&visualizzaPerProtocollo=true" />
							<jsp:param name="titleKey"
								value="label.ricerca_tipiarchivioistanze" />
						</jsp:include>
						<a class="vbg-btn btn-salva"
							href="javascript:aggiorna('A','amministrazione${a.index}_id');"
							title="<fmt:message key="label.aggiungi" />"> </a>
					</div>
				</c:if>
			</c:forEach>
		</fieldset>
		<fieldset>
			<legend>
				<fmt:message key="label.anagrafe" />
			</legend>
			<table>
				<tr>
					<th><label> &nbsp; </label></th>
					<th><label><fmt:message
								key="label.protocollazione.mezzo" /></label></th>
					<th><label><fmt:message
								key="label.protocollazione.modalita_invio" /></label></th>
					<th><label>Canc.</label></th>
				</tr>
				<c:forEach items="${protocolloCommand.mittentis}" var="current"
					varStatus="a">
					<c:if test="${not empty current.anagrafe.id.codice}">
						<tr>
							<td class="inline-ui-cell" valign="bottom"><jsp:include
									page="../includes/anagraficasearch.jsp">
									<jsp:param name="idElemento" value="anagrafe_${a.index}_id" />
									<jsp:param name="pathAnagrafica"
										value="mittentis[${a.index}].anagrafe" />
								</jsp:include></td>
							<td><c:if test="${not empty protocolloMezzis}">
									<spring-form:select id="selectMezzo${a.index}"
										path="mittentis[${a.index}].mezzo.codice">
										<spring-form:option value=""></spring-form:option>
										<spring-form:options items="${protocolloMezzis}"
											itemLabel="descrizione" itemValue="codice"></spring-form:options>
									</spring-form:select>
								</c:if></td>
							<td><c:if test="${not empty protocolloModalitainvios}">
									<spring-form:select id="selectModInvio${a.index}"
										path="mittentis[${a.index}].modInvio.codice">
										<spring-form:option value=""></spring-form:option>
										<spring-form:options items="${protocolloModalitainvios}"
											itemLabel="descrizione" itemValue="codice"></spring-form:options>
									</spring-form:select>
								</c:if></td>
							<td><a class="vbg-btn btn-elimina"
								href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);"
								title="<fmt:message key="label.elimina" />"> </a></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			<br class="clear" /> <a class="vbg-btn btn-aggiungi"
				href="javascript:nuovoSoggetto('div_anagrafe_id');"
				title="<fmt:message key="label.nuovo" />"> </a>
			<c:forEach items="${protocolloCommand.mittentis}" var="current"
				varStatus="a">
				<c:if
					test="${empty current.amministrazioni.id.codice and empty current.anagrafe.id.codice}">
					<div id="div_anagrafe_id" style="display: none;">
						<jsp:include page="../includes/anagraficasearch.jsp">
							<jsp:param name="idElemento" value="anagrafe_${a.index}_id" />
							<jsp:param name="pathAnagrafica"
								value="mittentis[${a.index}].anagrafe" />
						</jsp:include>
						<a class="vbg-btn btn-salva"
							href="javascript:aggiorna('R','anagrafe_${a.index}_id');"
							title="<fmt:message key="label.aggiungi" />"> </a>
					</div>
				</c:if>
			</c:forEach>
		</fieldset> <spring-form:errors path="mittentis" cssClass="error" />
	</td>
</tr>
<tr>
	<td><fmt:message key="label.destinatario" /></td>
	<c:if test="${isSmistamentoMultiploAttivoArrivo eq 'false'}">
		<td class="inline-ui-cell"><spring-form:select id="mittente_id"
				path="destinatario.amministrazioni.id.codice">
				<spring-form:option value=""></spring-form:option>
				<spring-form:options items="${amministrazioniList}"
					itemLabel="amministrazione" itemValue="id.codice" />
			</spring-form:select> <spring-form:errors path="destinatario" cssClass="error" /> &nbsp;
			<init:help idHelp="amministrazioni_id_help_destinatario_anagrafe"
				textKey="label.help_codice_amministrazioni_interne" /></td>
	</c:if>
	<c:if test="${isSmistamentoMultiploAttivoArrivo eq true}">
		<td>
			<fieldset>
				<legend>
					<fmt:message key="label.amministrazione" />
					&nbsp;
					<init:help
						idHelp="amministrazioni_id_help_destinatario_amministrazioni3"
						textKey="label.help_codice_amministrazioni_interne" />
				</legend>
				<table>
					<tr>
						<th><label> <fmt:message key="label.amministrazione" />
								&nbsp; <init:help
									idHelp="amministrazioni_id_help_destinatario_amministrazioni4"
									textKey="label.help_codice_amministrazioni_interne" />
						</label></th>
						<th><label><fmt:message
									key="label.protocollazione.mezzo" /></label></th>
						<th><label><fmt:message
									key="label.protocollazione.modalita_invio" /></label></th>
						<th><label>Canc.</label></th>
					</tr>
					<c:forEach items="${protocolloCommand.destinataris}" var="current"
						varStatus="a">
						<c:if test="${not empty current.amministrazioni.id.codice}">
							<tr>
								<td class="inline-ui-cell" valign="bottom"><jsp:include
										page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento"
											value="amministrazione_dest_set${a.index}_id" />
										<jsp:param name="propertyPath"
											value="destinataris[${a.index}].amministrazioni" />
										<jsp:param name="pathPropertyDescription"
											value="destinataris[${a.index}].amministrazioni.descrizioneEstesa" />
										<jsp:param name="pathPropertyCode"
											value="destinataris[${a.index}].amministrazioni.id.codice" />
										<jsp:param name="autocompleterAjax"
											value="findAmministrazioniForProtocolloRegistri.htm?codiceComune=${protocolloCommand.entity.comune.codicecomune}" />
										<jsp:param name="titleKey"
											value="label.ricerca_tipiarchivioistanze" />
									</jsp:include></td>
								<td><c:if test="${not empty protocolloMezzis}">
										<spring-form:select id="selectMezzo${a.index}"
											path="destinataris[${a.index}].mezzo.codice">
											<spring-form:option value=""></spring-form:option>
											<spring-form:options items="${protocolloMezzis}"
												itemLabel="descrizione" itemValue="codice"></spring-form:options>
										</spring-form:select>
									</c:if></td>
								<td><c:if test="${not empty protocolloModalitainvios}">
										<spring-form:select id="selectModInvio${a.index}"
											path="destinataris[${a.index}].modInvio.codice">
											<spring-form:option value=""></spring-form:option>
											<spring-form:options items="${protocolloModalitainvios}"
												itemLabel="descrizione" itemValue="codice"></spring-form:options>
										</spring-form:select>
									</c:if></td>
								<td><a class="vbg-btn btn-elimina"
									href="javascript:doSubmit('removeSoggetto.htm?idx=${a.index}&mittOrDest=D','<fmt:message key="javascript.confirm.delete" />',document.inviodati);"
									title="<fmt:message key="label.elimina" />"> </a></td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
				<br class="clear" /> <a class="vbg-btn btn-aggiungi"
					href="javascript:nuovoSoggetto('div_amministrazioni_dest_id');"
					title="<fmt:message key="label.nuovo" />"> </a>
				<c:forEach items="${protocolloCommand.destinataris}" var="current"
					varStatus="a">
					<c:if test="${empty current.amministrazioni.id.codice}">
						<div id="div_amministrazioni_dest_id" style="display: none;">
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento"
									value="amministrazione_dest${a.index}_id" />
								<jsp:param name="propertyPath"
									value="destinataris[${a.index}].amministrazioni" />
								<jsp:param name="pathPropertyDescription"
									value="destinataris[${a.index}].amministrazioni.descrizioneEstesa" />
								<jsp:param name="pathPropertyCode"
									value="destinataris[${a.index}].amministrazioni.id.codice" />
								<jsp:param name="autocompleterAjax"
									value="findAmministrazioniForProtocolloRegistri.htm?codiceComune=${protocolloCommand.entity.comune.codicecomune}" />
								<jsp:param name="titleKey"
									value="label.ricerca_tipiarchivioistanze" />
							</jsp:include>
							<a class="vbg-btn btn-salva"
								href="javascript:aggiorna('A','amministrazione_dest${a.index}_id');"
								title="<fmt:message key="label.aggiungi" />"> </a>
						</div>
					</c:if>
				</c:forEach>
			</fieldset>
		</td>
	</c:if>
</tr>