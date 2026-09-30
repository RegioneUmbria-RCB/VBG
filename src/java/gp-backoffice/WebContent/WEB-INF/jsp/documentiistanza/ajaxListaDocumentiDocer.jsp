<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:choose>
<c:when test="${not empty errore}">
	<div class="error_header">${errore}</div>
</c:when>
<c:otherwise>
<div class="jmesa">
	<table class="table" style="width: 100%;">
		<thead>
			<tr class="header">
				<td><fmt:message key="label.documento" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${docs}" var="doc_var" varStatus="a">
				<tr class="${((a.index%2)==0)?'odd':'even'}">
					<td>
						${doc_var.valore}
				    </td>				    
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:otherwise>
</c:choose>