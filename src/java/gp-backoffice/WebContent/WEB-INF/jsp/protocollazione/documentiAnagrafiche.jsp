<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%
int p = 1;
%>

<tr class="header hide_doc_anagrafe">
	<td colspan="5"><fmt:message key="label.allegati_anagrafiche" />
	</td>
</tr>
<c:forEach
	items="${protocolloCommand.documentiHelper.documentiAnagrafeList}"
	var="current" varStatus="a">
	<tr class="hide_doc_anagrafe">
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
					<tr class="<%=(p % 2) == 0 ? "odd" : "even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
						<td width="50%">${var.documento}</td>
						<td width="35%">${var.nomeFile}</td>
						<td width="10%">&nbsp;</td>
						<td width="13%"><jsp:include
								page="../includes/visualizzaOggetto.jsp">
								<jsp:param name="idElemento" value="docist${var.id.codice }" />
								<jsp:param name="fileId" value="${var.codiceOggetto}" />
							</jsp:include></td>
						<td>&nbsp;</td>
						<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
								path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
								<input type="hidden"
									name="_<c:out value="${status.expression}"/>" value="visible" />
								<input id="docanagr_${b.index}" type="checkbox"
									name="<c:out value="${status.expression}"/>" value="true"
									<c:if test="${status.value}">checked="true"</c:if>
									onchange="selezionaInvioPecAutomatico(this)" />
							</spring:bind></td>

						<c:if
							test="${param.selezionaAllegatiPEC eq '1' and protocolloCommand.flusso eq param.flussoPartenza }">
							<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
									path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvioPec">
									<input type="hidden"
										name="_<c:out value="${status.expression}"/>" value="visible" />
									<input id="docanagrpec_${b.index}" type="checkbox"
										name="<c:out value="${status.expression}"/>" value="true"
										<c:if test="${status.value}">checked="true"</c:if>
										onchange="selezionaProtocolloDaPec(this)" />
								</spring:bind></td>
						</c:if>

						<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"><input id="radio_button_id${var.id.codice}" type="radio"
							value="${var.codiceOggetto}" name="documentoPrincipale" /></td>
					</tr>
					<%
					p++;
					%>
				</c:forEach>
			</table>
		</td>
	</tr>
</c:forEach>