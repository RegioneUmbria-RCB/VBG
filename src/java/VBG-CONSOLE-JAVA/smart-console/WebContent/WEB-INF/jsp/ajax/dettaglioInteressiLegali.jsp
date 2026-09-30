<%@ include file="../includes/taglibs.jsp" %>
   <fieldset><legend><fmt:message key="form.interessilegali.title"></fmt:message></legend>
    <div class="jmesa">
	<table border="0" cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td ><fmt:message key="form.interessi.dataInizio" /> </td>
				<td  ><fmt:message key="form.interessi.dataFine" /></td>
                <td ><fmt:message key="form.interessi.tassoPercentuale" /></td>
                <td ><fmt:message key="form.interessi.disposizioneNormativa" /></td>
            </tr>
		</thead>
		<tbody class="tbody">
		<%int j=1;%>
		<c:forEach items="${interessiLegaliList}" var="interessi_var">
			<tr class="<%=(j%2)==0?"odd":"even"%>">
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${interessi_var.dataInizio}"/>  </td>
				<td ><fmt:formatDate pattern="dd/MM/yyyy" value="${interessi_var.dataFine}"/>  </td>
                <td align="right">${interessi_var.tassoPercentuale}% </td>
                <td >${interessi_var.disposizioneNormativa} </td>
			</tr>
			<%j++; %>
		</c:forEach>
		
		</tbody>
	</table>
	</div>	
    </fieldset>