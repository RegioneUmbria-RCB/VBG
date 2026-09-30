<%@ include file="../includes/taglibs.jsp" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


			<fieldset><legend><fmt:message key="label.dettaglio_posteggio"/></legend>
				<table>
					<tr>
						<td><fmt:message key="label.codiceposteggio" /></td>
						<td><b>${posteggio.codiceposteggio}</b></td>
					</tr>
					<tr>
						<td><fmt:message key="label.tipo_spazio" /></td>
						<td><b>${posteggio.tipoSpazio.tipospazio}</b></td>
					</tr>
					<tr>
						<td><fmt:message key="label.larghezza" /></td>
						<td>
						<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggio.larghezza}"/>
						<c:if test="${posteggio.larghezza!=null}">
						<fmt:message key="label.metri" />
						</c:if>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.lunghezza" /></td>
						<td>
						<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggio.lunghezza}"/>
						<c:if test="${posteggio.lunghezza!=null}">
						<fmt:message key="label.metri" />
						</c:if>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.superficie" /></td>
						<td>
						<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggio.superficie}"/>
						<c:if test="${posteggio.superficie!=null}">
						<fmt:message key="label.metri_quadri" />
						</c:if>
						</td>
					</tr>
					
					<c:if test="${not empty importoSpuntista}">
					
						<tr>
							<td><fmt:message key="label.importo" /></td>
							<td>
								${importoSpuntista}							
							</td>
						</tr>					
					
					</c:if>
				</table>	
			</fieldset>
			<c:if test="${not empty attivitas }">
			<fieldset><legend><fmt:message key="label.dettaglio_informazioni"/></legend>
				<table>
					<tr>
						<th>Merceologia</th>
						<th>Consentita<th>
					</tr>
				<c:forEach items="${attivitas}" var="merceologia" varStatus="varIndex">
					<tr>
						<td>${merceologia.attivita.istat}</td>
						<td>
							<c:if test="${ merceologia.flagConsentito eq true }"><fmt:message key="label.si"/></c:if>
							<c:if test="${ merceologia.flagConsentito eq false }"><fmt:message key="label.no"/></c:if>
						</td>
					</tr>
				</c:forEach>
				</table>
			</fieldset>
			</c:if>
			
			<fieldset><legend><fmt:message key="label.autorizzazioni"/></legend>
			
<c:forEach items="${_mercatidList}" var="posteggi" varStatus="varIndex">
			<c:forEach items="${posteggi.istanzeConcessioniMercatoHelpers}" var="concessioni" varStatus="index_concessioni">				    
					    <c:if test="${concessioni.codiceuso eq param.codiceuso or param.codiceuso eq null}">
<div>
							        <b>${concessioni.usoConcessione}:</b><br />
							        <b><fmt:message key="label.concessione" /></b>:&nbsp;
							       	${concessioni.numeroConcessione}
							        <c:if test="${concessioni.comuneConcessione ne ''}">, ${concessioni.comuneConcessione}</c:if>
							        <c:if test="${concessioni.registroConcessione ne ''}">, ${concessioni.registroConcessione}</c:if><br />
							        <b><fmt:message key="label.concessione_titolare"/></b>: ${concessioni.titolare}						       		
									<c:set scope="page" value="${concessioni.idOccupante}" var="occupante"></c:set>
									<c:set scope="page" value="imgAvvisi_occ_1" var="id_immage"></c:set>
									<br />
							        <b><fmt:message key="mercatid.label.occupante"/></b>: ${concessioni.occupante}
												        
</div>
							 <%-- Div che viene popolato alla chiamata ajax quando si ricercano i subentri sulla concesione attiva --%>
							 
						</c:if>
					</c:forEach>
					
	</c:forEach>
	</fieldset>