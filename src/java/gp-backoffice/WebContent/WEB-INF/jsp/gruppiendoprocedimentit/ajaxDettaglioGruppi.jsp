<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">	
				<td><fmt:message key="label.codice" /></td>
				<td><fmt:message key="label.inventarioprocedimenti" /></td>
				<td><fmt:message key="label.azioni" /></td>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach items="${gds}" var="entry" varStatus="idx">
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">   	 
    	 		<td>${entry.inventarioprocedimento.id.codice}</td>
    	 		<td>${entry.inventarioprocedimento.procedimento}</td>
    	 		<td>
    	 			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaEndo(${entry.id.codice})" title="<fmt:message key="label.elimina" /> ${entry.id.codice}">
				         	 <label><fmt:message key="label.elimina.image" /></label>
					</a>			
				</td>
		</tr>    	 
		</c:forEach>				
		</tbody>
	</table>
		
</div>