<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%
int k = 1;
%>
<tr class="header hide_doc_procure">
	<td colspan="4"><fmt:message key="label.documenti_procure" /></td>
</tr>
<c:forEach
	items="${protocolloCommand.documentiHelper.istanzeprocureList}"
	var="current" varStatus="a">
	<tr class="hide_doc_procure">
		<td width="25%" style="vertical-align: top;"><b>${current.chiave}</b></td>
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
					<tr class="<%=(k % 2) == 0 ? "odd" : "even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
						<td width="50%">Documento della procura di
							${var.anagrafeProcuratore.descrizioneRichiedente}</td>
						<td width="35%">${var.nomeFile}<c:if
								test="${not empty var.codiceOggettoDocId }">
								<p />
											${var.nomeFileDocId}
										</c:if>
						</td>
						<td width="10%">&nbsp;</td>
						<td width="13%"><jsp:include
								page="../includes/visualizzaOggetto.jsp">
								<jsp:param name="idElemento" value="docProc${var.id.codice }" />
								<jsp:param name="fileId" value="${var.codiceOggetto}" />
							</jsp:include> <c:if test="${not empty var.codiceOggettoDocId }">
								<p />
								<jsp:include page="../includes/visualizzaOggetto.jsp">
									<jsp:param name="idElemento"
										value="docProcDocId${var.id.codice }" />
									<jsp:param name="fileId" value="${var.codiceOggettoDocId}" />
								</jsp:include>
							</c:if></td>
						<td><jsp:include page="../includes/dettaglioCheckOggetto.jsp">
								<jsp:param name="controllook" value="${var.controllook}" />
							</jsp:include></td>
						<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
								path="protocolloCommand.documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
								<input type="hidden"
									name="_<c:out value="${status.expression}"/>" value="visible" />
								<!-- Controllo se è zione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
								<input id="dp_${b.index}" type="checkbox"
									name="<c:out value="${status.expression}"/>" value="true"
									<c:if test="${status.value}">checked="checked"</c:if> />
							</spring:bind></td>

						<c:if
							test="${param.selezionaAllegatiPEC eq '1' and protocolloCommand.flusso eq param.flussoPartenza }">
							<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
									path="protocolloCommand.documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvioPec">
									<input type="hidden"
										name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<input id="dppec_${b.index}" type="checkbox"
										name="<c:out value="${status.expression}"/>" value="true"
										<c:if test="${status.value}">checked="true"</c:if> />
								</spring:bind></td>
						</c:if>

						<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><input id="radio_button_id${var.id.codice}" type="radio"
							value="${var.codiceOggetto}" name="documentoPrincipale" /></td>
					</tr>
					<%
					k++;
					%>
				</c:forEach>
			</table>
		</td>
	</tr>
</c:forEach>