<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<tr>
	<td><fmt:message key="label.mittente" /></td>
	<td><spring-form:select id="mittente_id"
			path="mittente.amministrazioni.id.codice">
			<spring-form:option value=""></spring-form:option>
			<spring-form:options items="${amministrazioniList}"
				itemLabel="amministrazione" itemValue="id.codice" />
		</spring-form:select> <spring-form:errors path="mittente" cssClass="error" /> &nbsp;<init:help
			idHelp="amministrazioni_id_help_mittente"
			textKey="label.help_codice_amministrazioni_interne" /></td>
</tr>
<tr>
	<td><fmt:message key="label.destinatario" /></td>
	<c:if test="${isSmistamentoMultiploAttivoInterno eq true}">
		<td>
			<fieldset>
				<legend>
					<fmt:message key="label.amministrazione" />
					&nbsp;
					<init:help
						idHelp="amministrazioni_id_help_destinatario_amministrazioni1"
						textKey="label.help_codice_amministrazioni_interne" />
				</legend>
				<table>
					<colgroup>
						<col style="width: 75%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 5%;">
					</colgroup>
					<tr>
						<th>
							<label> <fmt:message key="label.amministrazione" />
								&nbsp; <init:help
									idHelp="amministrazioni_id_help_destinatario_amministrazioni2"
									textKey="label.help_codice_amministrazioni_interne" />
							</label>
						</th>
						<th><label><fmt:message key="label.protocollazione.mezzo" /></label></th>
						<th><label><fmt:message key="label.protocollazione.modalita_invio" /></label></th>
						<th><label>Canc.</label></th>
					</tr>
					<c:forEach items="${protocolloCommand.destinataris}" var="current"
						varStatus="a">
						<c:if test="${not empty current.amministrazioni.id.codice}">
							<tr>
								<td class="inline-ui-cell" valign="bottom">
									<jsp:include
										page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento"
											value="amministrazione_int_dest_set${a.index}_id" />
										<jsp:param name="propertyPath"
											value="destinataris[${a.index}].amministrazioni" />
										<jsp:param name="pathPropertyDescription"
											value="destinataris[${a.index}].amministrazioni.descrizioneEstesa" />
										<jsp:param name="pathPropertyCode"
											value="destinataris[${a.index}].amministrazioni.id.codice" />
										<jsp:param name="autocompleterAjax"
											value="findAmministrazioniForProtocolloRegistri.htm?codiceComune=${command.entity.comune.codicecomune }" />
										<jsp:param name="titleKey"
											value="label.ricerca_tipiarchivioistanze" />
									</jsp:include>
								</td>
								<td>
									<c:if test="${not empty protocolloMezzis}">
										<spring-form:select id="selectMezzo${a.index}"
											path="destinataris[${a.index}].mezzo.codice">
											<spring-form:option value=""></spring-form:option>
											<spring-form:options items="${protocolloMezzis}"
												itemLabel="descrizione" itemValue="codice"></spring-form:options>
										</spring-form:select>
									</c:if>
								</td>
								<td>
									<c:if test="${not empty protocolloModalitainvios}">
										<spring-form:select id="selectModInvio${a.index}"
											path="destinataris[${a.index}].modInvio.codice">
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
					href="javascript:nuovoSoggetto('div_amministrazioni_dest_id');"
					title="<fmt:message key="label.nuovo" />"> </a>
				<c:forEach items="${protocolloCommand.destinataris}" var="current"
					varStatus="a">
					<c:if test="${empty current.amministrazioni.id.codice}">
						<div id="div_amministrazioni_dest_id" style="display: none;">
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento"
									value="amministrazione_int_dest${a.index}_id" />
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
								href="javascript:aggiorna('A','amministrazione_int_dest${a.index}_id');"
								title="<fmt:message key="label.aggiungi" />"> </a>
						</div>
					</c:if>
				</c:forEach>
			</fieldset>
		</td>
	</c:if>
	<c:if test="${isSmistamentoMultiploAttivoInterno eq false}">
		<td><spring-form:select id="mittente_id"
				path="destinatario.amministrazioni.id.codice">
				<spring-form:option value=""></spring-form:option>
				<spring-form:options items="${amministrazioniList}"
					itemLabel="amministrazione" itemValue="id.codice" />
			</spring-form:select> <spring-form:errors path="destinatario" cssClass="error" /> &nbsp;
			<init:help idHelp="amministrazioni_id_help_destinatario"
				textKey="label.help_codice_amministrazioni_interne" /></td>
	</c:if>
</tr>