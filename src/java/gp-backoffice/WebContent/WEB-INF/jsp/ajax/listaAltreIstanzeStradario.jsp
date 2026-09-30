<%@page import="it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Istanze"%>
<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${fn:length(istanzes) eq maxResult }">
	<b><fmt:message key="label.visualizzati_i_primi_n_record_della_tabella">
		 <fmt:param value="${fn:length(istanzes)}"/>
		 <fmt:param value="Istanze"/>
	</fmt:message></b>
</c:if>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">
				<td><fmt:message key="label.modulo_software" /></td>
				<td><fmt:message key="label.numeroistanza" /></td>
				<td><fmt:message key="label.data_presentazione" /></td>
				<td><fmt:message key="label.richiedente" /></td>
				<td><fmt:message key="label.alberoproc" /></td>
				<td><fmt:message key="label.stato_istanza" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${istanzes}" var="istanza_var">
				<tr>
					<td>${istanza_var.software}</td>
					<td>
						<c:choose>
						<c:when test="${showLinkIstanze}">
						<%
						String uriTo = "../istanze/view.htm?codice=" + ((IstanzeListHelper)pageContext.getAttribute("istanza_var")).getCodiceistanza() + "&software=" + ((IstanzeListHelper)pageContext.getAttribute("istanza_var")).getCodicesoftware();
						uriTo = URLEncoder.encode(uriTo);
						%>					
						<a href="../history/set.htm?<%= WebConstants.GOTO %>=<%=uriTo %>&<%= WebConstants.RETURNTO %>=${urlBack}">${istanza_var.numeroistanza}</a>
						</c:when>
						<c:otherwise>
						${istanza_var.numeroistanza}
						</c:otherwise>
						</c:choose>		
					</td>
					<td><fmt:formatDate value="${istanza_var.data}"
						pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></td>
					<td>${istanza_var.richiedentenominativo} ${istanza_var.richiedentenome}</td>
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