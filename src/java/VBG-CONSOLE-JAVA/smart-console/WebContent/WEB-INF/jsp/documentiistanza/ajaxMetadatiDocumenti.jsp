<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:choose>
<c:when test="${not empty errore}">
	<div class="error_header">${errore}</div>
</c:when>
<c:otherwise>
<c:if test="${not empty mds}">
		<table style="width: 600px;">
			<colgroup>
			    <col style="width: 150px">
			    <col style="">
			  </colgroup>
		<c:forEach items="${mds }" var="md">
			<tr>
				<td>${md.descrizione}</td>
				<td>
					<c:choose>					
						<c:when test="${md.codice eq 'INITMD_DATAPRATICA'}">
							<input type="text" name="${md.codice}" id="${md.codice}_id" onchange="isValidDate(this);"/>
						</c:when>
						<c:otherwise>
							<input type="text" name="${md.codice}" id="${md.codice}_id" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>	  
		</table>	
</c:if>
</c:otherwise>
</c:choose>