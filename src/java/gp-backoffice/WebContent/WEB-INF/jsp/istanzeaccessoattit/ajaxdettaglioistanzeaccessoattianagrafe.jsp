<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

	<table  class="vbg-table">
		<thead>
			<tr>	
				<th><fmt:message key="label.anagrafica" /></th>
				<th width="5%"><fmt:message key="label.elimina" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${afs}" var="entry" varStatus="idx">
			<tr>
			    <c:choose>
		        	<c:when test="${codiceanagrafeinserito == entry.anagrafe.id.codice}">
		        		<c:set value="#aaf981" var="color" scope="page"></c:set>
		        	</c:when>
		            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
		        </c:choose>
    	 	    <td style="background-color:${color};">${entry.anagrafe.descrizioneRichiedente}</td>
    	 		<td style="background-color:${color}">
    	 			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaRiga(${entry.istanzeAccessoAttiT.id.codice}, ${entry.anagrafe.id.codice})" title="<fmt:message key="label.elimina" /> ${entry.anagrafe.id.codice}">
				         	 <label><fmt:message key="label.elimina.image" /></label>
					</a>			
				</td>
			</tr>    	 
		</c:forEach>				
		</tbody>
	</table>
		
