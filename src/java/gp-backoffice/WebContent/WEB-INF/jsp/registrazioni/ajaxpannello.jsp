<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<fieldset>
			<legend>
				<b><fmt:message key="label.scheda"/> ${current} <fmt:message key="label.di"/> ${numRisultati}</b></legend>
			<br />	
			<%--					
				<table >									
					<tr>
						<td><fmt:message key="label.data" /></td>
						<td><b><fmt:formatDate pattern="<%= WebConstants.DATE_FORMAT_PATTERN %>" value="${messaggio.dataMessaggio}"/></b></td>
					</tr>
					<tr>
						<td><fmt:message key="messaggi.label.autore" /></td>
						<td><b>${messaggio.autore}</b></td>
					</tr>
					<tr>
						<td><fmt:message key="messaggi.label.oggetto" /></td>
						<td><b>${messaggio.oggetto}</b></td>
					</tr>
					<tr>
						<td></td>
						<td><b>${messaggio.corpo}</b></td>
					</tr>	
				</table>
				 --%>
				 <c:choose>
					 <c:when test="${prev gt -1}">
					 	<a href="${pageContext.request.contextPath}/registrazioni/view.htm?codice=${prev}">&lt;&lt;<fmt:message key="label.precedente"/></a>
					 </c:when>
					 <c:otherwise>&lt;&lt;<fmt:message key="label.precedente"/></c:otherwise>
				 </c:choose>
				 .....
				 <c:choose>
					 <c:when test="${next gt -1}">
				 		<a href="${pageContext.request.contextPath}/registrazioni/view.htm?codice=${next}"><fmt:message key="label.successivo"/>&gt;&gt;</a>	
				 	</c:when>
				  	<c:otherwise><fmt:message key="label.successivo"/>&gt;&gt;</c:otherwise>
				 </c:choose>				
</fieldset>