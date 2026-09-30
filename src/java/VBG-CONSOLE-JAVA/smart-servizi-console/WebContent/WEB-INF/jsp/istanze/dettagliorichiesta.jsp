<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.service.helper.FoDomRichiesteHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomRichieste"%>
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

<c:set scope="page" var="tipo_comunicazione" value="${ richiesta.tipoRichiesta }"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %></title>
</head>
<body>
<style>

	.panelNotifiche{
	
		width: 80%;
		border: dotted;
		border-color: red;
		padding: 10px;
		margin: 5px;
		display: none;
	}
</style>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp">
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>
	<div class="titolo"><%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %></div>
	<div class="descrizione"></div>
	
	
	<fieldset style="padding: 2px;">
	
	
	<br />
	<div id="richieste_id" >
	
		
		<div id="sez_indirizzo" class="sezione">
		
		
		<c:choose>
				<c:when test="${richiesta.confermaRicezioneSuap eq true }">
					<c:choose>
						<c:when test="${empty richiesta.erroreRicezioneSuap}">
							<div class="successbox">
							L'ente di competenza ha ricevuto correttamente il messaggio di 								
								<%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %> in data 
										<fmt:formatDate value="${richiesta.dataRicezioneSuap  }"  pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" />															 
							</div>
						</c:when>
						<c:otherwise>
							<div class="errorbox">
								Il sistema informatico dell'ente ha tornato un errore nel recupero della comunicazione e questa non è stata acquisita correttamente.
							</div>
						</c:otherwise>
					</c:choose>
					
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${richiesta.versoInOut eq 'O'}">
							<div class="warningbox">L'ente di competenza non ha ancora ricevuto la comunicazione</div>
						</c:when>
					</c:choose>				
				
				
					
				</c:otherwise>
		</c:choose>
		
		
		<table class="sezione_table">
		
			
		
			<c:if test="${not empty richiesta.messaggio }">
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.messaggio-richiesta' /></label></td>
				<td>
					${richiesta.messaggio }
				</td>
			</tr>	
			</c:if>
			
			<tr>
				<td class="sezione_table_label"><label>E' possibile rispondere fino a</label></td>
				<td 				
					<c:if test="${scadenzaRichiesta.chiave eq true }">
						style="color: white; background-color: red"	
					</c:if>
					>
					<fmt:formatDate value="${scadenzaRichiesta.valore }"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /> 
					
				</td>
			</tr>				
			
			<tr>
				<td class="sezione_table_label"><label><fmt:message key='label.data' /></label></td>
				<td>
					<fmt:formatDate value="${richiesta.dataRichiesta }"  pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /> 
				</td>
			</tr>	
		

			<c:if test="${not empty helperAllegati.comunicazioniFormali }">
			
			
			<tr>
				<td class="sezione_table_label"><label>Lista delle comunicazioni</label></td>
				<td>
					
					<c:forEach items="${helperAllegati.comunicazioniFormali}" var="allegato" varStatus="status">
						<div style="padding-left: 10px;padding-top: 5px;">
						
							<c:if test="${not empty allegato.oggetti.id.codice }">
								<%
									FoDomrichAllegati all = (FoDomrichAllegati)pageContext.getAttribute("allegato");
									Oggetti o = all.getOggetti();
									String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneOggetto=" + o.getId().getIdcomune() + "&ts_" + System.currentTimeMillis());								
								%>
							
							<a href="${pageContext.request.contextPath}/ajax/download.htm?<%=link %>"
								 title="Scarica il file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"/></a>${allegato.oggetti.nomefile}  (  ${allegato.oggetti.dimensioneFileLeggibile } )								

							</c:if>
							<c:if test="${not empty allegato.link }">
								<a href="${allegato.link}" target="_blank">${allegato.link}</a>
							</c:if>
						</div>				
					</c:forEach>	
					
				</td>
			</tr>	
			
			</c:if>

<c:if test="${not empty helperAllegati.documentiRichiesti }">
			
				<tr>
					<td class="sezione_table_label" style="vertical-align: top"><label>Lista degli allegati richiesti per i diversi moduli</label></td>
				<td>				
				
					<c:forEach items="${ helperAllegati.documentiRichiesti}" var="allegatoMap">
						<div style="padding: 5px;">
							<label style="font-weight: bolder; font-size: 1.2em;">Modulo ${allegatoMap.key}</label>							
							<c:forEach items="${allegatoMap.value}" var="allegato" varStatus="status">
								<div style="padding-left: 10px; padding-top: 5px;">
									<c:if test="${not empty allegato.oggetti.id.codice }">
									<%
									FoDomrichAllegati all = (FoDomrichAllegati)pageContext.getAttribute("allegato");
									Oggetti o = all.getOggetti();
									String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneOggetto=" + o.getId().getIdcomune() + "&ts_" + System.currentTimeMillis());								
									%>
									<a href="${pageContext.request.contextPath}/ajax/download.htm?<%=link %>" title="Scarica il file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"/></a>&nbsp;${allegato.oggetti.nomefile}  (  ${allegato.oggetti.dimensioneFileLeggibile } )</a>								
									</c:if>
									<c:if test="${not empty allegato.link }">
										<a href="${allegato.link}" target="_blank">${allegato.link}</a>
									</c:if>
								</div>
							</c:forEach>
							</ul>
						</div>					
					</c:forEach>

				</td>
				</tr>			
</c:if>			
		</table>			
			
		</div>
		

	
	</div>
	

	<br />
	
	<%
		FoDomRichieste rich = (FoDomRichieste)request.getAttribute("richiesta");
		String linkRichiesta = Utilities.getLinkForFile("idRichiesta=" + rich.getId().getCodice() +"&idDomanda=" + rich.getFoDomande().getId().getCodice() + "&ts_" + System.currentTimeMillis());								
	 %>
	
	
	<input type="button" id="chiudi" data-custatt="session" class="bottone-cart" onclick="chiudiPagina()" value="Chiudi"/>
	
	
	<c:choose>
		<c:when test="${empty risposte}">
		<c:if test="${richiesta.versoInOut eq 'I' }">
			<c:if test="${richiesta.tipoRichiesta eq 'I' or richiesta.tipoRichiesta eq 'C'  or richiesta.tipoRichiesta eq 'M'  }">
				<input type="button" id="rispondi_btn" class="bottone-cart" value="Rispondi" />
			</c:if>
		</c:if>		
		</c:when>
		<c:otherwise>
		
		<p />&nbsp;
		<table class="sezione_table">
				<tr>
					<td>Data</td>
					<td width="60%">Messaggio</td>
					<td>Stato</td>
					<td width="10%">Azioni</td>					
				</tr>
			<c:forEach items="${ risposte }" var="risposta">
				<tr>
					<td><fmt:formatDate value="${risposta.dataRichiesta }"  pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /></td>
					<td>${risposta.messaggio}</td>
					<td>						
						<c:choose>
						<c:when test="${empty risposta.idRichiestaSistema}">
							Incompleta o non inviata
						</c:when>
						<c:otherwise>
							<span title="Riferimento invio: ${risposta.idRichiestaSistema}">Inviata</span>
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<%
							FoDomRichieste ris = (FoDomRichieste)pageContext.getAttribute("risposta");
							String linkRisposta = Utilities.getLinkForFile("id=" + ris.getId().getCodice() + "&ts_" + System.currentTimeMillis());								
						 %>
						<input type="button" class="dettaglio_risposta" data-qsmac="<%= linkRisposta %>" class="bottone-cart" value="Visualizza" />
					</td>
				</tr>			
			</c:forEach>
		</table>

		</c:otherwise>	
	</c:choose>
	
	<script type="text/javascript">
	
	function chiudiPagina(){
		<c:if test="${empty ReturnToList}">
			history.back();
		</c:if>
		<c:if test="${ReturnToList eq true }">
			location.href="${pageContext.request.contextPath}/istanze/listFoDomande.htm";
		</c:if>
	}
		
		$(function(){
			
		$( ".segna-letto-link" ).button({
  			  label: "Elimina dalla lista"
  		});	
		
		$( ".segna-letto-link" ).click(function() {
			
			var url = '${pageContext.request.contextPath}/istanze/ajaxsegnacomeletto.htm?' + $(this).data("qsmac");
			
			var ajaxOpts = {
					 
					context: this,
					type: 'POST',
					success: function(data){
						$("#myDialogText").html(data);
						$("#myDialog").dialog({
							 resizable: false,
							 modal: true,
							 width:'auto',
							 title: 'Lista degli allegati'
							}
						);
					}
				};
			    			
			$.ajax(url,ajaxOpts);  
		});
		
		$( ".dettaglio-link" ).button({
			  label: "Vai al dettaglio"
		});	
		
		$( ".dettaglio-link" ).click(function() {
			
			var url = '${pageContext.request.contextPath}/istanze/dettagliorichiesta.htm?' + $(this).data("qsmac");
			document.location.href=url;			

		});
		
		$( "#rispondi_btn" ).button();	
		$( "#rispondi_btn" ).click(function() {
			
				var url = '${pageContext.request.contextPath}/istanze/risposta.htm?<%= linkRichiesta%>';
				document.location.href=url;			
	
			});
		
		});
		
		$( ".dettaglio_risposta" ).button();
		$( ".dettaglio_risposta" ).click(function() {
			
			var url = '${pageContext.request.contextPath}/istanze/viewComunicazione.htm?' + $(this).data("qsmac");
			document.location.href=url;			

		});
		
		
	</script>
</body>
</html>