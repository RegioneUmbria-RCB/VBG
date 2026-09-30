<%@ include file="../includes/taglibs.jsp" %>


			
			<table >							
			<c:forEach var="all" items="${listaFiles}">					
		
					<tr>
						<td>
						<a 
						href="${pageContext.request.contextPath}/ajax/download.htm?${all.chiave}"
							 title=""><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"/>
							${all.valore.nomefile}  (  ${all.valore.dimensioneFileLeggibile } )</a>						
						</td>
					</tr>

			</c:forEach>
			</table>
