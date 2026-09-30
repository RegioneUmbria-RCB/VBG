<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

	<div id="subcontent">
	<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
			    <td><fmt:message key="label.codiceProcSTP" /></td>
			</tr>
		</thead>	
		<tbody class="tbody">
			<%int i=1;%>
			<c:if test="${fn:length(inventarioprocedimenti.entity.inventarioprocedimentipeoples)>0}">
			<c:forEach items="${inventarioprocedimenti.entity.inventarioprocedimentipeoples}" var="codicimapping">
				<tr class="<%=(i%2)==0?"odd":"even"%>" valign="top">
					<td>${codicimapping.codProcPeople}</td>
				</tr>
			 <%i++; %>
			 </c:forEach>
			 </c:if>
		     <c:if test="${fn:length(inventarioprocedimenti.entity.inventarioprocedimentipeoples)==0}">
		     	<tr class="even" valign="top" align="center">
		     		<td colspan="6"><fmt:message key="label.record_non_presenti" /></td>
		     	</tr>
		     </c:if>
		     </tbody>
		</table>
</div>
</div>
