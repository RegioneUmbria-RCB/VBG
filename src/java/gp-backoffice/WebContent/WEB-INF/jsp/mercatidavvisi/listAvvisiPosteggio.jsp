<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	
	<fieldset>
	     <legend><b>${tipicausalioneri.coDescrizione}  - ${mercatiD.codiceposteggio}</b></legend>
			<table  	width ="100%" >
				<c:forEach items="${avvisiMercatid}" var="avvisi">
				
				<tr>
				    <c:set scope="page" value="red" var="color"></c:set>
				    <c:set scope="page" value="error.png" var="immage"></c:set>
				    <c:if test="${avvisi.flagVerificato eq true}">
			    	 <c:set scope="page" value="green" var="color"></c:set>
			         <c:set scope="page" value="success.png" var="immage"></c:set>
				    </c:if>
					<td valign="top"">
					<img alt="" src="<%=request.getContextPath()%>/images/${immage}">
						${avvisi.anagrafe.descrizioneRichiedente}
				    </td>
				    <tr>
						<td colspan="3" class="titoloSezione" height="3px"></td>
					</tr>
				</tr>
				</c:forEach>
			</table>
	     </fieldset>