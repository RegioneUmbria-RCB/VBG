<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">
				<td><fmt:message key="label.numeroistanza" /></td>
				<td><fmt:message key="label.data_presentazione" /></td>
				<td><fmt:message key="label.richiedente" /></td>
				<td><fmt:message key="label.alberoproc" /></td>
				<td><fmt:message key="label.stato_istanza" /></td>
				<td><fmt:message key="label.comune" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listaIstanze}" var="istanza_var">
				<tr>
					<td>						
						${istanza_var.numeroistanza}
					</td>
					<td><fmt:formatDate value="${istanza_var.data}"
						pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></td>
					<td>${istanza_var.transientDescrizioneRichiedenteQualitaAzienda}</td>
					<td>${istanza_var.interventoproc}</td>
					<td>${istanza_var.statoistanza}</td>
				</tr>
				<tr>
					<td style="border-bottom: #c0c0c0 1px dashed;" colspan="6"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>				