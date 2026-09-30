<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<% int i = 1;%>
<tr class="header">
	<td colspan="5"><fmt:message key="label.documenti_movimento" /></td>
</tr>

					<c:if test="${ protocolloCommand.flusso eq param.flussoPartenza }">
				
					
						<jsp:include page="../includes/ziplogicosection.jsp">
							<jsp:param name="movimento" value="${protocolloCommand.movimento.movimento}" />
							<jsp:param name="codicemovimento" value="${protocolloCommand.movimento.id.codice }"/>
							<jsp:param name="isZipLogico" value="${isZipLogico }"/>
							<jsp:param name="displayNone" value="<%=false %>"/>
							<jsp:param name="labelForFlgZipLogicoChbx" value="label.movimenti_zip_logico.protocolla"/>
							<jsp:param name="commandPathProperty" value="flgProtocollaZipLogico"/>
							<jsp:param name="help" value="label.movimenti_zip_logico.help_protocollo"/>
							<jsp:param name="hideDocAltrimov" value=".hide_doc_altri_movimenti"/>
							<jsp:param name="inputMaChbx" value="input[id^='altrima_']" />
							<jsp:param name="hideDocist" value=".hide_doc_istanza"/>
							<jsp:param name="inputIstChbx" value="input[id^='di_']" />
							<jsp:param name="hideDocproc" value=".hide_doc_procure"/>
							<jsp:param name="inputProcChbx" value="input[id^='dp_']" />
							<jsp:param name="hideDocendo" value=".hide_doc_endo"/>
							<jsp:param name="inputEndoChbx" value="input[id^='ia_']" />
							<jsp:param name="hideDocanag" value=".hide_doc_anagrafe"/>
							<jsp:param name="inputAnagChbx" value="input[id^='docanagr_']" />
							<jsp:param name="hideDoccds" value=".hide_doc_cds"/>
							<jsp:param name="inputCdsChbx" value="input[id^='cds_']" />
							<jsp:param name="isRadioBtn" value="<%=true %>" />
							<jsp:param name="colspan" value="4" />
						</jsp:include>
					
				</c:if>		

<c:forEach
	items="${protocolloCommand.documentiHelper.documentiMovimentoList}"
	var="current" varStatus="a">
	<tr>
		<td width="25%" style="vertical-align: top;">${current.chiave}</td>
		<td width="75%" colspan="4">
			<table width="100%">
				<tr class="header">
					<td width="98%" colspan="5"><fmt:message
							key="movimentimail.label.documento" /></td>
					<td width="2%"><fmt:message key="label.seleziona" /></td>
					<c:if
						test="${param.selezionaAllegatiPEC eq '1' and protocolloCommand.flusso eq param.flussoPartenza }">
						<td width="2%"><fmt:message key="label.invia_pec" /></td>
					</c:if>
					<td width="15%"><fmt:message key="label.principale" /></td>
				</tr>
				<c:forEach items="${current.valore}" var="var" varStatus="b">
					<tr class="<%=(i % 2) == 0 ? "odd" : "even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
						<td width="50%">${var.descrizione}</td>
						<td width="35%">${var.nomeFile}</td>
						<td width="10%">${var.note}</td>
						<c:if test="${var.codiceOggetto!=null}">
							<td width="13%"><jsp:include
									page="../includes/visualizzaOggetto.jsp">
									<jsp:param name="idElemento" value="docist${var.id.codice }" />
									<jsp:param name="fileId" value="${var.codiceOggetto}" />
								</jsp:include></td>
							<td><jsp:include
									page="../includes/dettaglioCheckOggetto.jsp">
									<jsp:param name="controllook" value="${var.controllook}" />
								</jsp:include></td>
							<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
									path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden"
										name="_<c:out value="${status.expression}"/>" value="visible" />
									<%-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati --%>
									<c:if test="${param.codiceMovimento == null }">
										<input id="ma_${a.index}_${b.index}" type="checkbox"
											name="<c:out value="${status.expression}"/>" value="true"
											checked="true" onchange="selezionaInvioPecAutomatico(this)" />
									</c:if>
									<c:if test="${param.codiceMovimento != null }">
										<input id="ma_${a.index}_${b.index}" type="checkbox"
											name="<c:out value="${status.expression}"/>" value="true"
											<c:if test="${status.value}">checked="true"</c:if>
											onchange="selezionaInvioPecAutomatico(this)" />
									</c:if>
								</spring:bind></td>

							<c:if
								test="${param.selezionaAllegatiPEC eq '1' and protocolloCommand.flusso eq param.flussoPartenza }">
								<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
										path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvioPec">
										<input type="hidden"
											name="_<c:out value="${status.expression}"/>" value="visible" />
										<c:if test="${param.codiceMovimento == null }">
											<input id="mapec_${a.index}_${b.index}" type="checkbox"
												name="<c:out value="${status.expression}"/>" value="true"
												checked="true" onchange="" />
										</c:if>
										<c:if test="${param.codiceMovimento != null }">
											<input id="ma_${a.index}_${b.index}" type="checkbox"
												name="<c:out value="${status.expression}"/>" value="true"
												<c:if test="${status.value}">checked="true"</c:if>
												onchange="" />
										</c:if>
									</spring:bind></td>
							</c:if>

							<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
								<input id="radio_button_id${var.id.codice}" type="radio"
								value="${var.codiceOggetto}" name="documentoPrincipale" /></td>
						</c:if>
						<c:if test="${var.codiceOggetto==null && not empty var.messageId}">
							<%-- se esiste un record con oggetto null, ma messageid popolato carica automaticamente il codice per scaricare 
							   	l'allegato eml dal server di posta configurato --%>
							<td colspan="2"><jsp:include page="../ajax/downloadEml.jsp">
									<jsp:param name="codiceallegato" value="${var.id.codice}" />
									<jsp:param name="idElemento" value="docist${var.id.codice }" />
									<jsp:param name="fileId" value="${var.codiceOggetto}" />
									<jsp:param name="mostralabel" value="true" />
									<jsp:param name="id_ckh" value="ma_${a.index}_${b.index}" />
									<jsp:param name="id_radio_button"
										value="radio_button_id${var.id.codice}" />
								</jsp:include></td>
							<td><spring:bind
									path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden"
										name="_<c:out value="${status.expression}"/>" value="visible" />
									<c:if test="${param.codiceMovimento == null }">
										<input id="ma_${a.index}_${b.index}" type="checkbox"
											name="<c:out value="${status.expression}"/>"
											disabled="disabled"
											title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />"
											value="true" checked="true"
											onchange="selezionaInvioPecAutomatico(this)" />
									</c:if>
									<c:if test="${param.codiceMovimento != null }">
										<input id="ma_${a.index}_${b.index}" type="checkbox"
											name="<c:out value="${status.expression}"/>"
											title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />"
											disabled="disabled" value="true"
											<c:if test="${status.value}">checked="true"</c:if>
											onchange="selezionaInvioPecAutomatico(this)" />
									</c:if>
								</spring:bind></td>
							<c:if
								test="${param.selezionaAllegatiPEC eq '1' and protocolloCommand.flusso eq param.flussoPartenza }">
								<td><spring:bind
										path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvioPec">
										<input type="hidden"
											name="_<c:out value="${status.expression}"/>" value="visible" />
										<c:if test="${param.codiceMovimento == null }">
											<input id="mapec_${a.index}_${b.index}" type="checkbox"
												name="<c:out value="${status.expression}"/>"
												disabled="disabled"
												title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />"
												value="true" checked="true" onchange="" />
										</c:if>
										<c:if test="${param.codiceMovimento != null }">
											<input id="ma_${a.index}_${b.index}" type="checkbox"
												name="<c:out value="${status.expression}"/>"
												disabled="disabled"
												title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />"
												value="true"
												<c:if test="${status.value}">checked="true"</c:if>
												onchange="" />
										</c:if>
									</spring:bind></td>
							</c:if>
							<td><input id="radio_button_id${var.id.codice}" type="radio"
								value="${var.codiceOggetto}" disabled="disabled"
								name="documentoPrincipale" /></td>
						</c:if>
					</tr>
					<%
					i++;
					%>
				</c:forEach>

			</table>
		</td>
	</tr>
</c:forEach>