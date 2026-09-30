<%@ include file="../includes/taglibs.jsp"%><%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><c:choose><c:when test="${empty attivitas}"></c:when><c:otherwise>

	<table style="width:100%">
		<tr>
			<th style="border: 1px solid;">Merceologia</th>
			<th style="border: 1px solid;">Consentita</th>
		</tr>
	<c:forEach items="${attivitas}" var="merceologia" varStatus="varIndex">
		<tr>
			<td style="border: 1px solid;">${merceologia.attivita.istat}</td>
			<td style="border: 1px solid;">
				<c:if test="${ merceologia.flagConsentito eq true }"><fmt:message key="label.si"/></c:if>
				<c:if test="${ merceologia.flagConsentito eq false }"><fmt:message key="label.no"/></c:if>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:otherwise></c:choose>