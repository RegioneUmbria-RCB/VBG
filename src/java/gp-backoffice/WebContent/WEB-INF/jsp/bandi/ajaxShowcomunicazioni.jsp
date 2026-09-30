<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:choose>
<c:when test="${empty gds}">
	
	<h3>Non sono state effettuate comunicazioni</h3>

</c:when>
<c:when test="${existsAllegati eq false}">
	
	<h3>Non sono presenti allegati da stampare</h3>

</c:when>
<c:otherwise>

<div class="jmesa">
	<table class="table">
		<thead class="header">
			<td><fmt:message key="label.comunicazione" /></td>
			<td width=""><fmt:message key="label.allegato" /></td>
		</thead>
		<tbody class="tbody">
		
			<%int i = 1; %>
			<c:forEach items="${ gds }" var="helper">
				<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
					<td>${helper.graduatoriedCom.graduatorietCom.descrizione}</td>					
					<td >
						<table class="table" border="1" >
							<tbody class="tbody">
								<c:choose>
								<c:when test="${empty helper.movimentiAllegatis and empty helper.tipiMovdoctipos}">
									<tr>
										<td>
											Non sono presenti allegati da stampare
										</td>
									</tr>
								</c:when>
								<c:otherwise>					
								<%int a = 1; %>
								<c:forEach items="${helper.movimentiAllegatis}" var="mall">					
									<c:if test="${not empty mall.oggetto.id.codice }">						
									<tr class="<%=(a % 2) == 0 ? "odd" : "even"%>">
										<td>
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
					     					<jsp:param name="idElemento" value="mall${mall.id.codice }" />
					     					<jsp:param name="fileId" value="${mall.oggetto.id.codice}" />
					     					<jsp:param name="mostralabel" value="true"/>
					     					<jsp:param name="mostraNomeFile" value="true"/>
											<jsp:param name="readonly" value="true"/>
											<jsp:param name="styleHref" value="float: right;"/>
					  					</jsp:include>
					  					</td>
				  					</tr>
				  					<%a++; %>
				  					</c:if>
			  					</c:forEach>
								<c:if test="${not empty  helper.tipiMovdoctipos}">
									<c:forEach items="${helper.tipiMovdoctipos}" var="mall">			
										<c:if test="${not empty mall.letteretipo.file.id.codice }">
											<tr class="<%=(a % 2) == 0 ? "odd" : "even"%>">
												<td>
													<a style="float: right;" class="generaallegato vbg-btn btn-aggiungi" title="Crea allegato" href="#" data-codice="${helper.graduatoriedCom.id.codice}" onclick="creaAllegatoPerGraduatoria(this,'${mall.letteretipo.id.codice}')">
														<!-- img src="${pageContext.request.contextPath}/images/add.png"/>  -->
													</a>		
													${mall.letteretipo.descrizione}
												</td>	
											</tr>
											<%a++; %>									
										</c:if>
									</c:forEach>
								</c:if>
								
								</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</td>				   						
				</tr>
				<%i++; %>
			</c:forEach>	
		</tbody>
	</table>
</div>
</c:otherwise>
</c:choose>