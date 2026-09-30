<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Allegati"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.StpCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	<div id="ctl00_ContentPlaceHolder1_pnlDatiEndo">

		<div id="accordion" class="popupDettagliEndo inputForm dettagliIntervento">
			<div>
					<h3><a href="#">Informazioni</a></h3>
				<div>
					<fieldset>
						<div>
							<div class='etichetta'>
								Nome procedimento</div>
							<span id="ctl00_ContentPlaceHolder1_Literal1">${endo.procedimento}</span>
						</div>						
						<div>
							<div class='etichetta'>
								Tipologia</div>
							<span id="ctl00_ContentPlaceHolder1_ltrTipologia">
								<c:if test="${not empty endo.tipoendo.tipifamiglieendo.tipo}">
									${endo.tipoendo.tipifamiglieendo.tipo} - 
								</c:if>	
								<c:if test="${not empty endo.tipoendo.tipo}">
									${endo.tipoendo.tipo}
								</c:if>	
								</span>
						</div>
						<c:if test="${not empty endo.naturaendo}">
							<div>
								<div class='etichetta'>
									Natura del procedimento</div>
								<span id="ctl00_ContentPlaceHolder1_ltrNatura">${endo.naturaendo.natura}</span>
							</div>
						</c:if>
						<c:if test="${not empty endo.amministrazioni}">
							<div>
								<div class='etichetta'>
									Amministrazione competente</div>
								<span id="ctl00_ContentPlaceHolder1_ltrAmministrazione">${endo.amministrazioni.amministrazione}</span>
							</div>
						</c:if>
					</fieldset>
				</div>
			</div>
			<c:if test="${not empty endo.datigenerali}">
				<div>
					<h3>
						<a href="#">Descrizione</a></h3>
					<div>
						${endo.datigenerali}
					</div>
				</div>
			</c:if>
			<c:if test="${not empty endo.campoapplicazione}">
				<div>
					<h3>								
					<a href="#"><fmt:message key="label.requisiti" /></a></h3>
					<div>
						${endo.campoapplicazione}
					</div>
				</div>
			</c:if>
			
			
			<c:if test="${not empty endo.adempimenti}">
				<div>
					<h3>
						<a href="#">Adempimenti</a></h3>
					<div>
						${endo.adempimenti}					
					</div>
				</div>
			</c:if>

			
			<c:if test="${not empty endo.allegatis}">						
				<div>
					<h3>
						<a href="#">Modulistica</a></h3>
					<div>
					<table>
						<tr>
							<td>Descrizione</td>
							<td>Visualizza/download</td>
						</tr>
						
						<c:forEach items="${endo.allegatis}" var="doc">
							<c:if test="${doc.pubblica eq 1 or doc.pubblica eq 2}">
								
								<c:if test="${codiceComune eq doc.comune.codicecomune or empty doc.comune.codicecomune}">									
								<tr>
									<td>${doc.allegato}</td>
									<td>
										<c:if test="${not empty doc.oggetti.id.codice}">
											<%
												Allegati fado = ((Allegati)pageContext.getAttribute("doc"));
												String queryString = "id=" + fado.getOggetti().getId().getCodice() + "&ts_=" + System.currentTimeMillis();
												String qsDownloadFile = Utilities.getLinkForFile(queryString);
											%>											
											<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_blank"
												 title="scarica file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"></a>
										&nbsp;
										</c:if>
										
										<c:if test="${not empty doc.indirizzoweb }">
											<a class="linkEsterno" href="${ doc.indirizzoweb}" target="_blank" 
												title="Visualizza il riferimento esterno"></a>	
										</c:if>
									</td>
								</tr>	
								</c:if>					
							</c:if>
						</c:forEach>
						</table>						
					</div>
				</div>
			</c:if>
			<c:if test="${not empty endo.inventarioprocLeggis}">
				<div>
					<h3>
						<a href="#">Normativa</a></h3>
					<div>
					<table>
						<tr>
							<td>Descrizione</td>
							<td>Tipologia</td>
							<td>&nbsp;</td>
						</tr>
						<c:forEach items="${endo.inventarioprocLeggis}" var="doc">
									
								<tr>
									<td>${doc.leggi.leDescrizione}</td>
									<td>${doc.leggi.leggitipi.ltDescrizione}</td>
									<td>
										<c:if test="${not empty doc.leggi.oggetto.id.codice}">
											<%
												InventarioprocLeggi fado = ((InventarioprocLeggi)pageContext.getAttribute("doc"));
												String queryString = "id=" + fado.getLeggi().getOggetto().getId().getCodice() + "&ts_=" + System.currentTimeMillis();
												String qsDownloadFile = Utilities.getLinkForFile(queryString);
											%>											
											<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_blank"
												 title="scarica file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"></a>
										&nbsp;
										</c:if>
										
										<c:if test="${not empty doc.leggi.leLink }">
											<a class="linkEsterno" href="${ doc.leggi.leLink}" target="_blank" 
												title="Visualizza il riferimento esterno"></a>	
										</c:if>
									</td>
								</tr>						
							
						</c:forEach>
						</table>						
					</div>
				</div>
			</c:if>			
		</div>
</div>