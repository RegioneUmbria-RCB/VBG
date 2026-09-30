<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
				<table style="margin-top: 0px;">
					<c:forEach items="${regInOutList}" var="rif">
					<tr>
						<td valign="top">${rif.tipimodalitapagamento.mpDescrestesa}&nbsp;<b>${rif.riferimentiPagamento}</b>&nbsp;<fmt:message key="form.registrazioni.riferimenti.delgiorno"/><fmt:formatDate value="${rif.dataIncasso}" pattern="dd/MM/yyyy"/>
						(<fmt:formatNumber minFractionDigits="2" value="${rif.importo}" /> &euro;)						
						</td>
					</tr>
					</c:forEach>
				</table>