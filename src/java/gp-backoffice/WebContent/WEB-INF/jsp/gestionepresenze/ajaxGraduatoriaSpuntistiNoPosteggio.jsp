<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
		<div class="rTable" id="panelSpuntistiManifestazione" style="max-height: 400px; overflow: auto;">
			<div class="rTableHeading">
				<div class="rTableHead">Presenze</div>
				<div class="rTableHead">Data iscrizione cciaa</div>
				<div class="rTableHead">Data anzianità</div>
				<div class="rTableHead">Numero autorizzazione</div>
				<div class="rTableHead">Data autorizzazione</div>				
				<div class="rTableHead">Comune autorizzazione</div>
				<div class="rTableHead">Autorizzazione originaria</div>
				<div class="rTableHead">Occupante</div>
				<c:if test="${giornataMercatoChiusa eq false }">
					<div class="rTableHead">Azioni</div>
				</c:if>	
			</div>			
			<c:forEach items="${spuntisti}" var="spuntista" varStatus="a">
				<div class="rTableRow graduatoriaSpuntistiCls" data-idautorizzazione="${spuntista.idautorizzazione}">	 	
					<div class="rTableCell">${spuntista.numpresenze}</div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataanzianita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">					
						${spuntista.autoriznumero}
					</div>	
					<div class="rTableCell"><fmt:formatDate value="${spuntista.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">
						 ${spuntista.autorizcomune}
					</div>	
					<div class="rTableCell">
						${spuntista.autoriginnumero}	
					</div>
					<div id="anagrafe_id_${a.index}" class="rTableCell">
					
						<c:choose>
							<c:when test="${not empty spuntista.codiceGerente}">
								<a href="javascript:dettaglioAnagrafe(${spuntista.codiceGerente})" class="vbg-btn btn-dettaglio"></a>${spuntista.descrizioneGerente}
							</c:when>
							<c:otherwise>
								<a href="javascript:dettaglioAnagrafe(${spuntista.codiceanagrafe})" class="vbg-btn btn-dettaglio"></a>${spuntista.descrizioneRichiedente}
							</c:otherwise>
						</c:choose>
						<c:if test="${spuntista.codiceanagrafe ne spuntista.codicetitolare}">						
							<br /><i><fmt:message key="label.concessione_titolare"/>: ${spuntista.descrizioneTitolare}</i>
						</c:if>	
						
						</div>	
					
					<c:if test="${giornataMercatoChiusa eq false }">
						<div class="rTableCell">
							<a class="vbg-btn btn-aggiungi" href="javascript:aggiungiSpuntista(${spuntista.idautorizzazione},${spuntista.codiceanagrafe},'anagrafe_id_${a.index}');" title="Aggiungi spuntista"></a>
						</div>
					</c:if>
					
				</div>
			</c:forEach>
		</div>			