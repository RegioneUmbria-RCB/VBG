<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">	
				<td><fmt:message key="attivita.label.istat"/></td>
				<td><fmt:message key="label.elimina" /></td>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach items="${gds}" var="entry" varStatus="idx">
				 <c:choose>
		        	<c:when test="${codiceAttivitaInserito == entry.attivita.id.codiceistat}">
		        		<c:set value="#aaf981" var="color" scope="page"></c:set>
		        	</c:when>
		            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
		        </c:choose>
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
    	 	   	<td style="background-color:${color}">${entry.attivita.descrizioneEstesa}</td>
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