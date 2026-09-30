<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="25%"><fmt:message key="label.descrizione" /> </td>
                <td width="5%" ><fmt:message key="label.tariffa" /></td>
                <td width="5%" ><fmt:message key="label.data_inizio_validita" /></td>
                <td width="5%" ><fmt:message key="label.data_fine_validita" /></td>
            </tr>
		</thead>
		<tbody class="tbody">
		<%int l=1;%>
		<c:if test="${not empty gds}">
		<c:forEach items="${gds}" var="livello_servizio_var">
		<tr class="<%=(l%2)==0?"odd":"even"%>">
	       	  <td>${livello_servizio_var.descrizione}</td>
              <td><fmt:formatNumber value="${livello_servizio_var.tariffa}" minFractionDigits="2"></fmt:formatNumber></td>
              <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${livello_servizio_var.dataInizioValidita}"/></td>
              <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${livello_servizio_var.dataFineValidita}"/></td>
        </tr>
		<%l++; %>
		</c:forEach>
		</c:if>
		<c:if test="${empty gds}">
			<tr class="even">
				<td colspan="4"><fmt:message key="html.statusbar.noResultsFound" /></td>
			</tr>
		</c:if>
		</tbody>
	</table>
</div>
