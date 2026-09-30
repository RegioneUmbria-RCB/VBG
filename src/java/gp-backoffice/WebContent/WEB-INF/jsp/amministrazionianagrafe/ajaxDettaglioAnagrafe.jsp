<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

			
		<c:forEach items="${gds}" var="entry" varStatus="idx">
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
		        <c:choose>
		        	<c:when test="${codiceAnagrafeInserito == entry.anagrafe.id.codice}">
		        		<c:set value="#aaf981" var="color" scope="page"></c:set>
		        	</c:when>
		            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
		        </c:choose>
    	 	    <td style="background-color:${color}">${entry.anagrafe.descrizioneRichiedente}</td>
    	 		<td style="background-color:${color}">
    	 			<input id="flagInoltraComunicazioneId${a.index}" type="checkbox" onclick="changeCheckboxValue('flagInoltraComunicazioneId${a.index}','${pageContext.request.contextPath}/amministrazionianagrafe/ajaxChangeFlagInoltraComunicazione.htm?codice=${entry.id.codice}')" ${entry.inoltraComunicazione?'checked':''} />
				</td>
    	 		<td style="background-color:${color}">
    	 			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaRiga(${entry.id.codice})" title="<fmt:message key="label.elimina" /> ${entry.id.codice}">
				         	 <label><fmt:message key="label.elimina.image" /></label>
					</a>			
				</td>
		</tr>    	 
		</c:forEach>				
		