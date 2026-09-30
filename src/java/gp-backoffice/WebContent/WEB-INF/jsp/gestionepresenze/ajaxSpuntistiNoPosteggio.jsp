<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
		<div class="rTable" id="panelSpuntistiManifestazione">
				
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
				<div class="rTableHead">Alert</div>
			</div>			
			<c:forEach items="${spuntisti}" var="spuntista">
			
					<%-- IMPOSTA VARIABILI PER GESTIRE LA SEZIONE DI AZIONI E ALERT IN BASE ALLE CONDIZIONI
					     1. RINUNCIA PER ABBANDONO 
					     2. RINUNCIA POSTEGGIO 
					     
					--%>
					<c:choose>
				    	<c:when test="${spuntista.mercatipresenzeD.flagRinunciaPresenza}">
				    	    <%-- Variabile backgroud colonna alert --%>
				    		<c:set var="styleBackground" value="#FFF164;"></c:set>
				    		<c:choose>
						    	<c:when test="${spuntista.mercatipresenzeD.posteggioRinunciato!=null && spuntista.mercatipresenzeD.posteggioRinunciato.id.codice!=null}">
						    		<%-- caso rinuncia posteggio --%>
						    		<c:set var="label_alert" value="RP"></c:set>
						    		<c:set var="title_alert_rinuncia" value="Rinuncia posteggio ${spuntista.mercatipresenzeD.posteggioRinunciato.codiceposteggio}"></c:set>
						    		<c:set var="title_funzionalita_rinuncia_posteggio" value="Annulla rinuncia al posteggio"></c:set>
						    	</c:when>
						        <c:otherwise>
						        	<%-- caso rinuncia PER ABBANDONO --%>
						        	<c:set var="label_alert" value="A"></c:set>
						        	<c:set var="title_alert_rinuncia" value="Rinuncia per abbandono"></c:set>
						        </c:otherwise>
						    </c:choose>
				    	</c:when>
				    	
				    	<c:when test="${!spuntista.mercatipresenzeD.flagRinunciaPresenza}">
				    	<%-- Caso no abbandono e no rinuncia posteggio --%>	
				    		<c:set var="styleBackground" value=""></c:set>
				    		<c:set var="title_funzionalita_rinuncia_posteggio" value="Segna rinuncia al posteggio"></c:set>
				    	</c:when>
				        <c:otherwise>
				        	<c:set var="styleBackground" value=""></c:set>
				        	<c:set var="title_funzionalita_rinuncia_posteggio" value="Segna rinuncia al posteggio"></c:set>
				        </c:otherwise>
					</c:choose>
					
					<%-- IMPOSTA VARIABILI PER GESTIRE LA SEZIONE DI AZIONI E ALERT IN BASE ALLE CONDIZIONI
					     	1. PRESENZA NON MATURATA
					
					--%>
					<c:choose>
						<c:when test="${spuntista.mercatipresenzeD.numeropresenze == 0}">
							<c:set var="styleBackground" value="#FFF164;"></c:set>
							<c:set var="label_presenza_non_maturata" value="PNM"></c:set>
							<c:set var="title_alert_presenza_non_maturata" value="Presenza non maturata"></c:set>
						</c:when>
						<c:otherwise><c:set var="styleBackground" value=""></c:set></c:otherwise>
					</c:choose>
					
	
			
				<div class="rTableRow spuntistiNoPosteggioCls"  data-idautorizzazione="${spuntista.aut.id.codice}">
					<div class="rTableCell" style="background-color:${styleBackground}" title="${title_alert_presenza_non_maturata}">${spuntista.numeropresenze}</div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataCciaa}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
				    <div class="rTableCell"><fmt:formatDate value="${spuntista.dataAnzianita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">${spuntista.aut.autoriznumero}</div>
					<div class="rTableCell"><fmt:formatDate value="${spuntista.dataAutorizzazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">${spuntista.aut.autorizcomune.comune}</div>
					<div class="rTableCell">${spuntista.aut.autorigNumero}</div>
					<div class="rTableCell">				
					<c:choose>
						<c:when test="${not empty spuntista.mercatipresenzeD.gerenteSpuntista.id.codice}">
								<a href="javascript:dettaglioAnagrafe(${spuntista.mercatipresenzeD.gerenteSpuntista.id.codice})" class="vbg-btn btn-dettaglio"></a>
								${spuntista.mercatipresenzeD.gerenteSpuntista.descrizioneRichiedente}
						</c:when>
						<c:otherwise>
								<a href="javascript:dettaglioAnagrafe(${spuntista.mercatipresenzeD.occupante.id.codice})" class="vbg-btn btn-dettaglio"></a>
								${spuntista.mercatipresenzeD.occupante.descrizioneRichiedente}
						</c:otherwise>						
					</c:choose>
						<c:if test="${spuntista.aut.anagrafe.id.codice ne spuntista.mercatipresenzeD.occupante.id.codice}">
							<br /><i><fmt:message key="label.concessione_titolare"/>: ${spuntista.aut.anagrafe.descrizioneRichiedente}</i>
						</c:if>
					</div>
					<c:if test="${gestisciFasiSpunta eq true}">
						<div class="rTableHead">${spuntista.faseSpunta}</div>
					</c:if>
					
					
					<div class="rTableCell">
						<c:choose>
							<c:when test="${giornoMercato.flagPresenze eq true}">
							</c:when>
							<c:otherwise>
								<a class="vbg-btn btn-elimina" href="javascript:eliminaPresenzaSpuntista(${spuntista.mercatipresenzeDid},${spuntista.aut.id.codice});" title="<fmt:message key="label.elimina" /> ${spuntisti_var.id.codice}">
								</a>
								<c:choose>
									<c:when test="${empty spuntista.mercatipresenzeD.posteggioRinunciato}">
										<a class="vbg-btn btn-aggiungi" href="javascript:assegnaPosteggiospuntistaNelGiorno(${spuntista.mercatipresenzeDid});" title="assegna">
										</a>
									</c:when>
									<%-- 
									<c:otherwise>
										
									</c:otherwise>
									--%>
								</c:choose>
								<a class="vbg-btn btn-rinuncia-posteggio" href="javascript:segnaRinunciaAlPosteggio(${spuntista.mercatipresenzeDid});" title="${title_funzionalita_rinuncia_posteggio}">
								</a>
								<%-- 
								<c:if test="${spuntista.mercatipresenzeD.flagRinunciaPresenza}">
									<a class="vbg-btn btn-rinuncia-presenza" href="javascript:segnaRinunciaAlPosteggio(${spuntista.mercatipresenzeDid});" title="abbandono">
								</a>
								</c:if>
								--%>
							</c:otherwise>
						</c:choose>
						
					</div>
					
					
					
					
					<div class="rTableCell" style="background-color:${styleBackground}">
						<c:if test="${spuntista.mercatipresenzeD.flagRinunciaPresenza}">
						    <label title="${title_alert_rinuncia}"><b>${label_alert}</b></label>
						    
						</c:if>	
						<%-- 
						<c:if test="${spuntista.mercatipresenzeD.numeropresenze == 0}">
							<label title="${title_alert_presenza_non_maturata}"><b>${label_presenza_non_maturata}</b></label>
						</c:if>
						--%>
						</a>
					</div>
					
				</div>			
			</c:forEach>
		</div>
