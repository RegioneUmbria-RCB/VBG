<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<table class="vbg-table">
	<thead>
		<tr>
			<th><fmt:message key="label.numeroistanza" /></th>
			<th><fmt:message key="label.data_presentazione" /></th>
			<th><fmt:message key="label.richiedente" /></th>
			<th><fmt:message key="label.alberoproc" /></th>
			<th><fmt:message key="label.stato_istanza" /></th>
			<th><fmt:message key="label.comune" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${listaIstanze}" var="istanza_var">
			<tr>
				<td>		
					<a href="../istanze/view.htm?codice=${istanza_var.codiceistanza}&software=${istanza_var.software}">${istanza_var.numeroistanza}</a>				
				</td>
				<td><fmt:formatDate value="${istanza_var.data}"
					pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></td>
				<td>${istanza_var.transientDescrizioneRichiedenteQualitaAzienda}</td>
				<td>${istanza_var.interventoproc}</td>
				<td>
					<a href="../istanze/infoView.htm?codice=${istanza_var.codiceistanza}&software=${istanza_var.software}">${istanza_var.statoistanza}</a>
				</td>
				<td>${istanza_var.comune}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>