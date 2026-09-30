<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<h1>
  <fmt:message key="label.firme_allegati_commissione" /> 
</h1>
	
<table class="vbg-table">
	<thead>
		<tr>
			<th><fmt:message key="label.data_firma"/></th>
			<th><fmt:message key="label.convocato"/></th>
			<th><fmt:message key="label.impronta_file"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${firmePerAllegato}" var="firmePerAllegato_var" varStatus="idx">
			<tr id="zip_row_id">
				<td><fmt:formatDate value="${firmePerAllegato_var.dataFirma}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>"/> </td>
				<td>${firmePerAllegato_var.convocato}</td>
				<td>${firmePerAllegato_var.hashSha256}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

	