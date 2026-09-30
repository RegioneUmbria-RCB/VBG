<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

	<div id="subcontent">
	<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
			    <td><fmt:message key="mercatid.label.autorizzazione" /></td>
				<td><fmt:message key="mercatid.label.istanza" /></td>
				<td><fmt:message key="mercatid.label.occupante" /></td>
				<td><fmt:message key="mercatid.label.causale" /></td>
				<td><fmt:message key="mercatid.label.causale_storico" /></td>
				<td><fmt:message key="mercatid.label.data_cessazione" /></td>
			</tr>
		</thead>	
		<tbody class="tbody">
			<%int i=1;%>
			<c:if test="${fn:length(listSubentri)>0}">
			<c:forEach items="${listSubentri}" var="subentri">
				<tr class="<%=(i%2)==0?"odd":"even"%>" valign="top">
					<td>${subentri.autoriznumero} - <fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${subentri.autorizzazioni.autorizdata}"/> </td>
					<td>${subentri.istanze.numeroistanza}</td>
					<td>${subentri.anagrafe.descrizioneRichiedente}</td>
					<td>${subentri.concessionicausaliByFkAutsubConccausAcq.descrizione}</td>
					<td>${subentri.concessionicausaliByFkAutsubConccausCess.descrizione}</td>
					<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${subentri.dataCessazione}"/></td>
				</tr>
			 <%i++; %>
			 </c:forEach>
			 </c:if>
		     <c:if test="${fn:length(listSubentri)==0}">
		     	<tr class="even" valign="top" align="center">
		     		<td colspan="6"><fmt:message key="mercatid.label.no_subentri" /></td>
		     	</tr>
		     </c:if>
		</table>
</div>
