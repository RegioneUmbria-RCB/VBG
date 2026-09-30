<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

    <br />
	<%-- SEZIONE MERCATI PER CUI IL RICHIDENTE È GIÀ  SPUNTISTA --%>
	
	<fieldset>
		<legend><fmt:message key="label.lista_mercati_spunta_attivi"/></legend>
	
		<table class="vbg-table">
			<thead>				
				<th><fmt:message key="label.mercato"/></th>
				<th><fmt:message key="label.giorno" /></th>
				<th><fmt:message key="label.autorizzazione" /></th>
				<th><fmt:message key="label.numero_istanza" />(<fmt:message key="label.autorizzazione" />)</th>
				<th><fmt:message key="label.numero_istanza" />(<fmt:message key="label.registrazione_spuntista_istanza" />)</th>
				<th><fmt:message key="label.data_registrazione" /></th>
				<th><fmt:message key="label.attivo" /></th>
				<th><fmt:message key="label.data_disattivazione" /></th>
				<c:if test="${param.isCreate eq true }">
					<th><fmt:message key="label.azione" /></th>
				</c:if>				
			</thead>
			<tbody>	
				<c:if test="${not empty  mercatiInCuiESpuntista}">
					<c:forEach items="${mercatiInCuiESpuntista}" var="_mercatiInCuiSpunstita" varStatus="idx">
						<c:if test="${_mercatiInCuiSpunstita.flgAttivo }">
							<tr>
					   	 		<td>${_mercatiInCuiSpunstita.mercati.descrizione}</td>
					   	 		<td>${_mercatiInCuiSpunstita.mercatiUso.descrizione}</td>
					   	 		<td>${_mercatiInCuiSpunstita.autorizzazioni.autoriznumero}</td>
					   	 		<td>${_mercatiInCuiSpunstita.autorizzazioni.istanza.numeroistanza}</td>
					   	 		<td>${_mercatiInCuiSpunstita.istanze.numeroistanza}</td>
					   	 		<td><fmt:formatDate value="${_mercatiInCuiSpunstita.dataRegistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					   	 		<c:if test="${_mercatiInCuiSpunstita.flgAttivo}">
					   	 			<td><fmt:message key="label.si" /></td>
					   	 		</c:if>
					   	 		<c:if test="${!_mercatiInCuiSpunstita.flgAttivo}">
					   	 			<td><fmt:message key="label.no" /></td>
					   	 		</c:if>
					   	 		<td> - </td>
					   	 		<c:if test="${param.isCreate eq true }">
						   	 		<c:choose>
							   	 		<c:when test="${_mercatiInCuiSpunstita.istanze!=null && _mercatiInCuiSpunstita.istanze.id.codice!=null}">
						    	 			<td> &nbsp; </td>	
										</c:when>
										<c:otherwise>
											<td>
											    <a class="eliminaRiga" style="float: none;" href="javascript:eliminaRiga(${_mercatiInCuiSpunstita.id.codice})" title="<fmt:message key="label.elimina" /> ${_mercatiInCuiSpunstita.id.codice}">
											         <label><fmt:message key="label.elimina.image" /></label>
												</a>	
											</td>	
										</c:otherwise>
									</c:choose>
								</c:if>
							</tr> 
						</c:if>   	 
					</c:forEach>				
				</c:if>
			<c:if test="${empty  mercatiInCuiESpuntista}">
				<tr align="center">
	   	 	   		<td colspan="8"><b><fmt:message key="label.nessun_mercato_per_cui_e_spuntista"/></b></td>
				</tr>   
			</c:if>
			</tbody>
		</table>
	</fieldset>
		
		<br />
		<%-- SEZIONE MERCATI PER CUI IL RICHIEDNETE ERA SPUNTISTA (DISATTIVATI) --%>
		
	<fieldset>
		<legend><fmt:message key="label.lista_mercati_spunta_cessati"/></legend>	
		<table class="vbg-table">
			<thead>				
				<th><fmt:message key="label.mercato"/></th>
				<th><fmt:message key="label.giorno" /></th>
				<th><fmt:message key="label.autorizzazione" /></th>
				<th><fmt:message key="label.numero_istanza" />(<fmt:message key="label.autorizzazione" />)</th>
				<th><fmt:message key="label.numero_istanza" />(<fmt:message key="label.registrazione_spuntista_istanza" />)</th>
				<th><fmt:message key="label.data_registrazione" /></th>
				<th><fmt:message key="label.attivo" /></th>
				<th><fmt:message key="label.data_disattivazione" /></th>				
			</thead>
			<tbody>	
				<c:if test="${not empty  mercatiInCuiESpuntista}">
					<c:forEach items="${mercatiInCuiESpuntista}" var="_mercatiInCuiSpunstita" varStatus="idx">
						<c:if test="${!_mercatiInCuiSpunstita.flgAttivo }">
							<tr style="background-color: red; color:#FFFFFF; font: bold;" class="${(idx.index % 2 == 0)? 'odd':'even' }">
					   	 		<td>${_mercatiInCuiSpunstita.mercati.descrizione}</td>
					   	 		<td>${_mercatiInCuiSpunstita.mercatiUso.descrizione}</td>
					   	 		<td>${_mercatiInCuiSpunstita.autorizzazioni.autoriznumero}</td>
					   	 		<td>${_mercatiInCuiSpunstita.autorizzazioni.istanza.numeroistanza}</td>
					   	 		<td>${_mercatiInCuiSpunstita.istanze.numeroistanza}</td>
					   	 		<td><fmt:formatDate value="${_mercatiInCuiSpunstita.dataRegistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					   	 		<c:if test="${_mercatiInCuiSpunstita.flgAttivo}">
					   	 			<td><fmt:message key="label.si" /></td>
					   	 		</c:if>
					   	 		<c:if test="${!_mercatiInCuiSpunstita.flgAttivo}">
					   	 			<td><fmt:message key="label.no" /></td>
					   	 		</c:if>
					   	 		<c:if test="${_mercatiInCuiSpunstita.dataDisattivazione != null}">
					   	 			<td colspan="2"> <fmt:formatDate value="${_mercatiInCuiSpunstita.dataDisattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					   	 		</c:if>
					   	 		<c:if test="${_mercatiInCuiSpunstita.dataDisattivazione == null}">
					   	 			<td colspan="2"> - </td>   	
					   	 		</c:if>
					   	 		
							</tr>
						</c:if>    	 
					</c:forEach>				
				</c:if>
				<c:if test="${empty  mercatiInCuiESpuntista}">
					<tr align="center">
		   	 	   		<td colspan="8"><b><fmt:message key="label.nessun_mercato_per_cui_e_spuntista"/></b></td>
					</tr>   
				</c:if>
			</tbody>
		</table>
	</fieldset>