<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

	<c:choose>
			<c:when test="${not empty votazione.codiceoggetto}">
				<span id="file-content-${votazione.id.codice}" style="display: inline-block;"> 
					<jsp:include page="../includes/visualizzaOggetto.jsp" >
	  						<jsp:param name="idElemento" value="allegato${votazione.id.codice}" />
	  						<jsp:param name="fileId" value="${votazione.codiceoggetto}" />
	  						<jsp:param name="readonly" value="true" />
					</jsp:include>
				</span>	
				<i class="fas fa-times vbg-link fa-lg elimina-allegato azione" 
						data-id="${votazione.id.codice}"
						style="cursor: pointer; display: inline-block" 
						title="Elimina"></i>									
			</c:when>
			<c:otherwise>
				<c:if test="${tipologiaUploadParere eq 'true' }">
					<jsp:include page="../includes/oggetti.jsp">	
						<jsp:param name="idElemento" value="oggettoIdCodice_${votazione.id.codice}" />
						<jsp:param name="codiceOggetto" value="" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice_${votazione.id.codice}" />
						<jsp:param name="nomefileId" value="oggetto_nomefile_${votazione.id.codice}" />					
						</jsp:include>	
						<input type="hidden" name="oggetto_nomefile_${votazione.id.codice}" 
							id="oggetto_nomefile_${votazione.id.codice}" />
				</c:if>					
			</c:otherwise>
	</c:choose>
