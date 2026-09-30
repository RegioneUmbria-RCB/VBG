<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%
int kkkk = 1;
%>
<tr class="header hide_doc_cds">
	<td colspan="4"><fmt:message key="label.verbale_cds" /></td>
</tr>
<c:forEach items="${protocolloCommand.documentiHelper.cdsattiList}"
	var="current" varStatus="a">
	<tr class="hide_doc_cds">
		<td width="25%" style="vertical-align: top;"><b>Cds</b></td>
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
					<tr class="<%=(kkkk % 2) == 0 ? "odd" : "even"%>">

						<td width="80%" colspan="4">${var.nomefile}</td>

						<td width="13%"><jsp:include
								page="../includes/visualizzaOggetto.jsp">
								<jsp:param name="idElemento" value="docProc${var.id.codice }" />
								<jsp:param name="fileId" value="${var.codiceoggetto}" />
							</jsp:include></td>
						<td><spring:bind
								path="protocolloCommand.documentiHelper.cdsattiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
								<input type="hidden"
									name="_<c:out value="${status.expression}"/>" value="visible" />
								<input id="cds_${b.index}" type="checkbox"
									name="<c:out value="${status.expression}"/>" value="true"
									<c:if test="${status.value}">checked="checked"</c:if> />
							</spring:bind></td>
						<c:if
							test="${param.selezionaAllegatiPEC eq '1' and protocolloCommand.flusso eq param.flussoPartenza }">
							<td><spring:bind
									path="protocolloCommand.documentiHelper.cdsattiList[${a.index}].valore[${b.index}].transientSegnaPerInvioPec">
									<input type="hidden"
										name="_<c:out value="${status.expression}"/>" value="visible" />
									<input id="cdspec_${b.index}" type="checkbox"
										name="<c:out value="${status.expression}"/>" value="true"
										<c:if test="${status.value}">checked="true"</c:if> />
								</spring:bind></td>
						</c:if>
						<td><input id="radio_button_id${var.id.codice}" type="radio"
							value="${var.codiceoggetto}" name="documentoPrincipale" /></td>
					</tr>
					<%
					kkkk++;
					%>
				</c:forEach>
			</table>
		</td>
	</tr>
</c:forEach>
