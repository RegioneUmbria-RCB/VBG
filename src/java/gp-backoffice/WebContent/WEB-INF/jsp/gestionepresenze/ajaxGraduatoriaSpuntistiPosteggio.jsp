<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


		<div class="rTable" id="panelSpuntistiManifestazionePosteggio">
				
			<div class="rTableHeading">
				<div class="rTableHead">Presenze</div>
				<div class="rTableHead">Data iscrizione cciaa</div>
				<div class="rTableHead">Data anzianità</div>
				<div class="rTableHead">Numero autorizzazione</div>
				<div class="rTableHead">Data autorizzazione</div>
				<div class="rTableHead">Comune autorizzazione</div>
				<div class="rTableHead">Autorizzazione originaria</div>
				<div class="rTableHead">Titolare</div>
				<c:if test="${gestisciFasiSpunta eq true}">
					<div class="rTableHead">Fase</div>
				</c:if>
				<div class="rTableHead">Azioni</div>
			</div>			
			<c:forEach items="${spuntisti}" var="spuntista">
				<div class="rTableRow graduatoriaSpuntistiPosteggioCls" data-idautorizzazione="${spuntista.aut.id.codice}">
					<div class="rTableCell">${spuntista.numeropresenze}</div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataCciaa}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataAnzianita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">${spuntista.aut.autoriznumero}</div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataAutorizzazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">${spuntista.aut.autorizcomune.comune}</div>
					<div class="rTableCell">${spuntista.aut.autorigNumero}</div>
					<div class="rTableCell">
					<c:choose>
						<c:when test="${empty spuntista.autCsi.anagrafe.id.codice}">
								<a href="javascript:dettaglioAnagrafe(${spuntista.aut.anagrafe.id.codice})" class="vbg-btn btn-dettaglio"></a>
								${spuntista.aut.anagrafe.descrizioneRichiedente}</div>	
						</c:when>
						<c:otherwise>
								<a href="javascript:dettaglioAnagrafe(${spuntista.autCsi.anagrafe.id.codice})" class="vbg-btn btn-dettaglio"></a>
								${spuntista.autCsi.anagrafe.descrizioneRichiedente}</div>
						</c:otherwise>
					</c:choose>
					<c:if test="${gestisciFasiSpunta eq true}">
						<div class="rTableHead">${spuntista.faseSpunta}</div>
					</c:if>
					<div class="rTableCell">
						<a class="vbg-btn btn-aggiungi" href="javascript:assegnaPresenzaSpuntistaNelGiorno(${presenzaCodice},${spuntista.mercatipresenzeDid});" title="assegna ${spuntisti_var.id.codice}">
						</a>
					</div>			
				</div>			
			</c:forEach>
		</div>