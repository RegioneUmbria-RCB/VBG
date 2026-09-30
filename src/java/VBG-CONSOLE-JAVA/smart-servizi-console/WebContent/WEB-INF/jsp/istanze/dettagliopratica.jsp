<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.service.helper.FoDomRichiesteHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomRichieste"%>
<%@page import="it.gruppoinit.pal.gp.core.service.FoStatiDomandaService"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoStatiDomanda"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Oggetti"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.pal.gp.areariservata.web.filter.ServiziResolverFilter.ServiziEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomande"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.dati-istanza' /></title>
</head>
<body>
<style>


</style>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp">
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>
	<div class="titolo"><fmt:message key='label.dati-istanza' /></div>
	<div class="descrizione"></div>
	
	
	<fieldset style="padding: 2px;">
	
	
	<br />
	<div id="richieste_id" >
	
		
		<div id="sez_indirizzo" class="sezione">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.codice-domanda' /></label></td>
				<td>
					${domanda.identificativodomanda}
				</td>
			</tr>	
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.comune' /></label></td>
				<td>
					${domanda.comuni.comune}
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.data-invio' /></label></td>
				<td>
					<fmt:formatDate value="${domanda.datainvio}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /> 
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.presentatore' /></label></td>
				<td>
					${domanda.anagrafe.nome} ${domanda.anagrafe.nominativo} 
					<c:if test="${not empty domanda.anagrafe.codicefiscale}">
					
					<br />(${domanda.anagrafe.codicefiscale})
					</c:if>
				</td>
			</tr>			
			
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.intestatario' /></label></td>
				<td>
					${domanda.richNome} ${domanda.richCognome} 
					<c:if test="${not empty domanda.richCodicefiscale }">
					
					<br />(${domanda.richCodicefiscale})
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.stato' /></label></td>
				<td>
					
					<div class="stato_istanza" data-identificativo="${domanda.id.codice}" data-idcomunedomanda="${domanda.id.idcomune}"  onclick="verificaStato(this,'${domanda.id.codice}','${domanda.id.idcomune}')" title="clicca qui per verificare lo stato" />
				</td>
			</tr>

		

			<c:if test="${not empty allegatiPratica}">			
				<tr>
					<td class="sezione_table_label"><label>Allegati della pratica</label></td>
					<td><div class="toggle_allegati" >Mostra/nascondi</div></td>
				</tr>
				<tr id="allegati_pratica_id" style="display: none;">				
					<td colspan="2" >
						
						<c:forEach items="${allegatiPratica}" var="allegato" varStatus="status">
							<div style="padding-left: 10px;padding-top: 5px;">
							
								<c:if test="${not empty allegato.oggetti.id.codice }">
									<%
										FoDomandeOggetti all = (FoDomandeOggetti)pageContext.getAttribute("allegato");
										Oggetti o = all.getOggetti();
										String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneOggetto=" + o.getId().getIdcomune() + "&ts_" + System.currentTimeMillis());								
									%>							
								<a href="${pageContext.request.contextPath}/ajax/download.htm?<%=link %>"
									 title="Scarica il file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"/></a>${allegato.oggetti.nomefile}  (  ${allegato.oggetti.dimensioneFileLeggibile } )								
	
								</c:if>
							</div>				
						</c:forEach>	
					</td>
				</tr>	
			</c:if>
			
			
			<c:if test="${not empty stati}">			
				<tr>
					<td><label>Stati della pratica</label></td>
					<td><div class="toggle_stato" >Mostra/nascondi</div></td>
				</tr>
				<tr id="stati_pratica_id" style="display: none;">				
					<td colspan="2" >
						<table>
						<tr>
							<th>Data</th>
							<th>Stato</th>
						</tr>
							<c:forEach items="${stati}" var="stato" varStatus="status">							
							<tr>
								<td>
									<fmt:formatDate value="${stato.data}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /> 
								</td>
								<td>
									<c:if test="${not empty stato.stato}">
									<%
									FoStatiDomanda all = (FoStatiDomanda)pageContext.getAttribute("stato");									
									out.print(FoStatiDomandaService.StatoDomandaFacctEnum.valueOf(all.getStato()).value());								
									%>		
									</c:if>
								</td>	
							</tr>
							</c:forEach>
						</table>	
					</td>
				</tr>	
			</c:if>
			
			
			<c:if test="${not empty comunicazioni}">
				<tr>
					<td class="sezione_table_label"><label>Messaggi</label></td>
					<td><div class="toggle_comunicazioni" >Mostra/nascondi</div></td>
				</tr>
				<tr id="comunicazioni_pratica_id" style="display: none;">				
					<td colspan="2" >
						<table>
						<tr>
							<th>Data</th>
							<th>Tipologia</th>
							<th>Messaggio</th>
							<th>Verso</th>
							<th>Azioni</th>
							<th>Stato</th>
						</tr>
						<c:forEach items="${comunicazioni}" var="comunicazione" varStatus="status">
								
							<c:set scope="page" var="tipo_comunicazione" value="${ comunicazione.tipoRichiesta }"></c:set>
															
							<tr>
									<td><fmt:formatDate value="${comunicazione.dataRichiesta}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /> </td>
									<td>										
										
										<%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %>
									</td>
									<td>${comunicazione.messaggio}</td>
									<td>
										<c:choose>
											<c:when test="${comunicazione.versoInOut eq 'I'}">
												Entrata
											</c:when>
											<c:when test="${comunicazione.versoInOut eq 'O'}">
												Uscita
											</c:when>
											<c:otherwise>
												${comunicazione.versoInOut}
											</c:otherwise>
										</c:choose>
									</td>
									<td>
									<%	
										FoDomRichieste o = (FoDomRichieste)pageContext.getAttribute("comunicazione");			
										String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneRecord=" + o.getId().getIdcomune() + "&ts_" + System.currentTimeMillis());
									 %>	
										<div class="dettagliorichiesta-link" data-qsmac="<%= link %>" >Dettaglio</div>
									</td>
									<td>
										<c:choose>
											<c:when test="${comunicazione.versoInOut eq 'O'}">
																						
											<c:choose>
												<c:when test="${comunicazione.confermaRicezioneSuap eq true }">
													<c:choose>
														<c:when test="${empty comunicazione.erroreRicezioneSuap}">
															<div class="successbox">
																L'ente di competenza ha ricevuto correttamente il messaggio di 	<%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %> in data 
																<fmt:formatDate value="${comunicazione.dataRicezioneSuap }"  pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" /> 
															</div>
														</c:when>
														<c:otherwise>
															<div class="errorbox">
																Il sistema informatico dell'ente ha tornato un errore nel recupero del messaggio di <%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %> e questa non è stata acquisita correttamente. 
															</div>
														</c:otherwise>
													</c:choose>													
												</c:when>
												<c:otherwise>
													<div class="warningbox">L'ente di competenza non ha ancora ricevuto il messaggio di <%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %></div>
												</c:otherwise>
										</c:choose>												
												
												
											</c:when>
											<c:otherwise>
												
											</c:otherwise>
										</c:choose>
									</td>
							</tr>
						</c:forEach>
						</table>	
					</td>
				</tr>	
			</c:if>
			
			
		</table>			
			
		</div>
		

	
	</div>
	

	<br />
	
	
	<script type="text/javascript">
	
		<jsp:include page="../includes/fodomande_js.jsp"></jsp:include>
		
		$(function(){
			
			$( ".toggle_allegati" ).button({
	  			  label: "Mostra/nascondi"
	  		});    		
	  		$( ".toggle_allegati" ).click(function() {
	  			$('#allegati_pratica_id').toggle();
	  		});
	  		$( ".toggle_stato" ).button({
  			  label: "Mostra/nascondi"
	  		});    		
	  		$( ".toggle_stato" ).click(function() {
	  			$('#stati_pratica_id').toggle();
	  		});
		
	  		
	  		$( ".dettagliorichiesta-link" ).button({
				  label: "Vai al dettaglio"
			});	
			
			$( ".dettagliorichiesta-link" ).click(function() {
				
				var url = '${pageContext.request.contextPath}/istanze/dettagliorichiesta.htm?' + $(this).data("qsmac");
				document.location.href=url;			

			});
	  		
	  		
	  		$( ".toggle_comunicazioni" ).button({
	  			  label: "Mostra/nascondi"
		  		});    		
		  		$( ".toggle_comunicazioni" ).click(function() {
		  			$('#comunicazioni_pratica_id').toggle();
		  		});
		  		
	  		$( ".stato_istanza" ).each(function() {
    			var idDomanda = $( this ).data("identificativo");
    			var idComuneDomanda = $( this ).data("idcomunedomanda");
				if (idDomanda){
					verificaStato($(this),idDomanda,idComuneDomanda);
				}
	        });
	  		
		});
		
	
		
	</script>
	<input type="button" id="chiudi" data-custatt="session" class="bottone-cart" onclick="document.location.href='../istanze/listFoDomande.htm'" value="Chiudi"/>
</body>
</html>