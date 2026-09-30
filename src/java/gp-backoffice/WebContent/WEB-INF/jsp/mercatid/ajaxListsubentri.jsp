<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


	<div class="vbg-form" style="width: 95%">
		<table class="vbg-table">
		<thead>
			<tr>
			    <th><fmt:message key="mercatid.label.autorizzazione" /></th>
				<th><fmt:message key="mercatid.label.istanza" /></th>
				<th><fmt:message key="mercatid.label.occupante" /></th>
				<th><fmt:message key="mercatid.label.causale" /></th>
				<th><fmt:message key="mercatid.label.causale_storico" /></th>
				<th><fmt:message key="mercatid.label.data_cessazione" /></th>
			</tr>
		</thead>	
		<tbody>
			<c:if test="${fn:length(listSubentri)>0}">
			<c:forEach items="${listSubentri}" var="subentri">
				<tr>
					<td>${subentri.autoriznumero} - <fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${subentri.autorizdata}"/> </td>
					<td>${subentri.istanze.numeroistanza}</td>
					<td>${subentri.occupante.descrizioneRichiedente}
					<c:if test="${  subentri.anagrafe.id.codice ne subentri.occupante.id.codice }">
						<br/><b><fmt:message key="label.concessione_titolare"/></b>: ${subentri.anagrafe.descrizioneRichiedente}
					</c:if>
					</td>
					<td>${subentri.concessionicausaliByFkAutsubConccausAcq.descrizione}</td>
					<td>${subentri.concessionicausaliByFkAutsubConccausCess.descrizione}</td>
					<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${subentri.dataCessazione}"/></td>
				</tr>

			 </c:forEach>
			 </c:if>
		     <c:if test="${fn:length(listSubentri)==0}">
		     	<tr valign="top" align="center">
		     		<td colspan="6"><fmt:message key="mercatid.label.no_subentri" /></td>
		     	</tr>
		     </c:if>
		 </tbody> 
		</table>
	</div>	

