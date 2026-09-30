<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<div  class="popup-form">
	<table>
		<tr>
			<td style="text-align: center;"><fmt:message key="bollgestione.rata.numero" /></td>
			<td style="text-align: center;"><fmt:message key="bollgestione.rata.scadenza" /></td>
			<td style="text-align: center;"><fmt:message key="bollgestione.rata.importo" /></td>
		</tr>
		<c:forEach
                items="${rate}"
                var="rata">
	   		<tr>
				<td><div class="read-only" style="border: 0px;">	${rata.numeroRata}</div></td>
				<td><div class="read-only" style="text-align: right; border: 0px;">
				<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${rata.scadenza}"/>
				</div>
				</td>
				<td>
					<div class="read-only" style="text-align: right; border: 0px;">
						<fmt:formatNumber type = "number" minFractionDigits = "2" value = "${rata.importoTotale}" />
					</div> 
				</td>
			</tr>     
        </c:forEach>
	</table>
</div>