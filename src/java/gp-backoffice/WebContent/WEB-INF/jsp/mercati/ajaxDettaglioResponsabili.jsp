<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">	
				<td><fmt:message key="label.responsabile" /></td>
				<td><fmt:message key="label.azioni" /></td>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach items="${gds}" var="entry" varStatus="idx">
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
		        <c:choose>
		        	<c:when test="${codiceRespInserito == entry.responsabili.id.codice}">
		        		<c:set value="#aaf981" var="color" scope="page"></c:set>
		        	</c:when>
		            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
		        </c:choose>
    	 	    <td style="background-color:${color};">${entry.responsabili.responsabile}</td>
    	 		<td style="background-color:${color}">
    	 			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaRiga(${entry.id.codice})" title="<fmt:message key="label.elimina" /> ${entry.id.codice}">
				         	 <label><fmt:message key="label.elimina.image" /></label>
					</a>			
				</td>
		</tr>    	 
		</c:forEach>				
		</tbody>
	</table>
		
</div>