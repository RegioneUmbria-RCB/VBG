<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.areariservata.web.util.FileUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp"%>
	
		
		<%-- BEGIN CONTENT --%>
		
		<c:set var="totaleOneri" value="0.0"/>
			
		<table style="width: 100%">
			<colgroup>
		       <col style="width: 30%;">
		       <col style="width: 20%;">
		       <col style="width: 2%;">
		       <col style="width: 10%;">
		       <col style="width: 15%;">
		       <col style="width: 25%;">
		    </colgroup>
			<c:if test="${not empty nuovaIstanzaCommand.domandaOneriHelper.oneriIntervento}">	
			<tr><td colspan="6"><div class="titolo_sezione" style="font-size: 1.3em;"><fmt:message key='label.intervento-selezionato' /></div></td></tr>
			<c:set var="interventoScritto" value="false" />
			<c:forEach items="${nuovaIstanzaCommand.domandaOneriHelper.oneriIntervento}" var="oi_var" varStatus="oi_status">				
				<tr>
					<td >
						<c:if test="${interventoScritto eq 'false' }">
							${intervento.vwAlberoproc.scDescrizione}
						</c:if>
					</td>
					<td >
						${oi_var.tipicausalioneri.coDescrizione}							
					</td>
					<td >
						<c:if test="${not empty oi_var.note}">
							<span class="help_image" id="FLD_${oi_var.id.codice}_id_HELP" title="${oi_var.note}"><label>(?)</label></span>								
						</c:if>
					</td>
					<td style="text-align: right;"><fmt:formatNumber value="${oi_var.importo}" minFractionDigits="2" /> &euro;</td>
					
					<td>
						<c:if test="${PAGAMENTI_ONLINE eq true}">
							<c:choose>
								<c:when test="${oi_var.flagOnline eq true && oi_var.flagStato eq true}">
									<c:choose>									
										<c:when test="${empty oi_var.oggettoPdf or empty oi_var.oggettoXml}">
											<%-- IN CASO DI PAGAMENTO EFFETTUATO MA RICEVUTE NON PRODOTTE --%>											
											<form action="oneriVERIFICASTATOPAGAMENTO.htm" method="post" 
												id="form_oneri_verificapagonline_${oi_var.id.codice}_id" 
												name="form_oneri_verificapagonline_${oi_var.id.codice}">
												<input type="hidden" name="identificativo" id="identificativo_${oi_var.id.codice}" value="${oi_var.id.codice}" />
												<span class="help_image" id="FLD_VPOL_${oi_var.id.codice}_id_HELP" title="<fmt:message key='help.verifica-stato-pagamento-online' />"><label>(?)</label></span>	
												<input type="button" value="<fmt:message key='button.verifica-stato-pagamento-online' />" onclick="$('#form_oneri_verificapagonline_${oi_var.id.codice}_id').submit();"/> 
											</form>
											<%-- END IN CASO DI PAGAMENTO EFFETTUATO MA RICEVUTE NON PRODOTTE --%>
										</c:when>
										<c:otherwise>
											<ul>									
											<c:if test="${not empty oi_var.oggettoPdf}">
												<%
													FoArjDomandeOneri fado = ((FoArjDomandeOneri)pageContext.getAttribute("oi_var"));
													String queryString = "id=" + fado.getOggettoPdf().getId().getCodice()+"&ts_="+System.currentTimeMillis();
													String qsDownloadFile = FileUtils.getLinkForFile(queryString);
												%>
												<li>${oi_var.oggettoPdf.nomefile}&nbsp;<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_new"><fmt:message key="label.scarica" /></a>
												</li>
											</c:if>
											<c:if test="${not empty oi_var.oggettoXml}">
												<%
													FoArjDomandeOneri fado = ((FoArjDomandeOneri)pageContext.getAttribute("oi_var"));
													String queryString = "id=" + fado.getOggettoXml().getId().getCodice()+"&ts_="+System.currentTimeMillis();
													String qsDownloadFile = FileUtils.getLinkForFile(queryString);
												%>
												<li>${oi_var.oggettoXml.nomefile}&nbsp;<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_new"><fmt:message key="label.scarica" /></a>
												</li>
											</c:if>
											</ul>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:if test="${empty oi_var.oggettoPdf}">
										<c:set var="checked" value=""/>
										<c:if test="${oi_var.flagOnline eq true}">
											<c:set var="checked" > checked="checked" </c:set>
										</c:if>
										<form name="form_pagamenti_ol_${oi_var.id.codice}" name="form_pagamenti_ol_id_${oi_var.id.codice}"
												action="gestisciOneriONLINE.htm" method="post" 
												id="form_pagamenti_ol_${oi_var.id.codice}_id">																						
												<input type="hidden" name="identificativo" value="${oi_var.id.codice}" />	
												<input type="hidden" id="attivo_${oi_var.id.codice}_id"	name="attivo"/>
												<input id="online_chk_${oi_var.id.codice}" ${ checked } name="attivo_chk"
												 type="checkbox" onclick="$('#attivo_${oi_var.id.codice}_id').val($('#online_chk_${oi_var.id.codice}').prop('checked'));$('#form_pagamenti_ol_${oi_var.id.codice}_id').submit();"/>
												 <label for="online_chk_${oi_var.id.codice}">Pagamento online</label>									
										</form>
									</c:if>											
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
									
					<td>
							<jsp:include page="../includes/gestioneAllegatiOneri.jsp">
								<jsp:param name="pagamentoOnline" value="${oi_var.flagOnline}" />
								<jsp:param name="codiceOggetto" value="${oi_var.oggettoPdf.id.codice}" />
								<jsp:param name="identificativo" value="${oi_var.id.codice}" />
								<jsp:param name="nomeFile" value="${oi_var.oggettoPdf.nomefile}" />
							</jsp:include>
					</td>
				</tr>
				<c:set var="interventoScritto" value="true" />
				<c:set var="totaleOneri" value="${totaleOneri+(oi_var.importo)}"/>
				<tr>
					<td colspan="6" class="titoloSezione" style="font-size: 1px;">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${not empty nuovaIstanzaCommand.domandaOneriHelper.oneriProcedimenti}">		
			<tr><td colspan="6"><div class="titolo_sezione" style="font-size: 1.3em;"><fmt:message key='label.procedimenti-selezionati' /></div></td></tr>			
			<c:forEach items="${nuovaIstanzaCommand.domandaOneriHelper.oneriProcedimenti}" var="oi_var" varStatus="oi_status">				
				<tr>
					<td>
						${oi_var.inventarioprocedimenti.procedimento}						
					</td>
					<td>${oi_var.tipicausalioneri.coDescrizione}</td>
					<td width="2%">
						<c:if test="${not empty oi_var.note}">
							<span class="help_image" id="FLD_${oi_var.id.codice}_id_HELP" title="${oi_var.note}"><label>(?)</label></span>								
						</c:if>
					</td>					
					<td style="text-align: right;"><fmt:formatNumber value="${oi_var.importo}" minFractionDigits="2" /> &euro;</td>
					<td>
						<c:if test="${PAGAMENTI_ONLINE eq true}">
							<c:choose>
								<c:when test="${oi_var.flagOnline eq true && oi_var.flagStato eq true}">
									<c:choose>
										<c:when test="${empty oi_var.oggettoPdf or empty oi_var.oggettoXml}">
											<%-- IN CASO DI PAGAMENTO EFFETTUATO MA RICEVUTE NON PRODOTTE --%>											
											<form action="oneriVERIFICASTATOPAGAMENTO.htm" method="post" 
												id="form_oneri_verificapagonline_${oi_var.id.codice}_id" 
												name="form_oneri_verificapagonline_${oi_var.id.codice}">
												<input type="hidden" name="identificativo" id="identificativo_${oi_var.id.codice}" value="${oi_var.id.codice}" />
												<span class="help_image" id="FLD_VPOL_${oi_var.id.codice}_id_HELP" title="<fmt:message key='help.verifica-stato-pagamento-online' />"><label>(?)</label></span>	
												<input type="button" value="<fmt:message key='button.verifica-stato-pagamento-online' />" onclick="$('#form_oneri_verificapagonline_${oi_var.id.codice}_id').submit();"/> 
											</form>
											<%-- END IN CASO DI PAGAMENTO EFFETTUATO MA RICEVUTE NON PRODOTTE --%>
										</c:when>
										<c:otherwise>										
											<ul>									
											<c:if test="${not empty oi_var.oggettoPdf}">
												<%
													FoArjDomandeOneri fado = ((FoArjDomandeOneri)pageContext.getAttribute("oi_var"));
													String queryString = "id=" + fado.getOggettoPdf().getId().getCodice()+"&ts_="+System.currentTimeMillis();
													String qsDownloadFile = FileUtils.getLinkForFile(queryString);
												%>
												<li>${oi_var.oggettoPdf.nomefile}&nbsp;<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_new"><fmt:message key="label.scarica" /></a>
												</li>
											</c:if>
											<c:if test="${not empty oi_var.oggettoXml}">
												<%
													FoArjDomandeOneri fado = ((FoArjDomandeOneri)pageContext.getAttribute("oi_var"));
													String queryString = "id=" + fado.getOggettoXml().getId().getCodice()+"&ts_="+System.currentTimeMillis();
													String qsDownloadFile = FileUtils.getLinkForFile(queryString);
												%>
												<li>${oi_var.oggettoXml.nomefile}&nbsp;<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_new"><fmt:message key="label.scarica" /></a>
												</li>
											</c:if>
											</ul>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:if test="${empty oi_var.oggettoPdf}">
										<c:set var="checked" value=""/>
										<c:if test="${oi_var.flagOnline eq true}">
											<c:set var="checked" > checked="checked" </c:set>
										</c:if>
										<form name="form_pagamenti_ol_${oi_var.id.codice}" name="form_pagamenti_ol_id_${oi_var.id.codice}"
												action="gestisciOneriONLINE.htm" method="post" 
												id="form_pagamenti_ol_${oi_var.id.codice}_id">
												
												<input type="hidden" name="identificativo" value="${oi_var.id.codice}" />	
												<input type="hidden" id="attivo_${oi_var.id.codice}_id"	name="attivo"/>
												<input id="online_chk_${oi_var.id.codice}" ${ checked } name="attivo_chk"
												 type="checkbox" onclick="$('#attivo_${oi_var.id.codice}_id').val($('#online_chk_${oi_var.id.codice}').prop('checked'));$('#form_pagamenti_ol_${oi_var.id.codice}_id').submit();"/>
												 <label for="online_chk_${oi_var.id.codice}">Pagamento online</label>
										</form>		 
								</c:if>		
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
					<td>					
						
							<jsp:include page="../includes/gestioneAllegatiOneri.jsp">
								<jsp:param name="pagamentoOnline" value="${oi_var.flagOnline}" />
								<jsp:param name="codiceOggetto" value="${oi_var.oggettoPdf.id.codice}" />
								<jsp:param name="identificativo" value="${oi_var.id.codice}" />
								<jsp:param name="nomeFile" value="${oi_var.oggettoPdf.nomefile}" />
							</jsp:include>
						 
								
					</td>
				</tr>
				<c:set var="totaleOneri" value="${totaleOneri+(oi_var.importo)}"/>
				<tr>
					<td colspan="6" class="titoloSezione" style="font-size: 1px;">&nbsp;</td>
				</tr>
			</c:forEach>
		</c:if>
		
		
		<tfoot>
			<tr>				
				<td colspan="2" style="text-align: right;"><b><fmt:message key='label.totale-oneri' /></b></td>
				<td style="text-align: right;" colspan="2"><b><fmt:formatNumber value="${totaleOneri}" minFractionDigits="2" /> &euro;</b></td>
				<td style="text-align: right;" colspan="2">&nbsp;</td>
			</tr>
		</tfoot>		
		</table>
		<c:if test="${importoDaPagareOnline gt 0}">		
			<br />
			<div id="frame_pagamenti" style="width: 100%; text-align: center; background-color: #dedede">
	
					
					<fieldset>
						<fmt:message key='label.totale-oneri-selezionati-online' >
							<fmt:param><fmt:formatNumber value="${importoDaPagareOnline}" minFractionDigits="2" /></fmt:param>
						</fmt:message>
						<spring-form:form action="pagamentiOnline.htm" 
													method="post" commandName="nuovaIstanzaCommand" 
													name="form_oneri_pagamenti_online" 
													id="form_oneri_pagamenti_online_id">	
							<br />
							<fmt:message key='label.email-pagamenti-online' />* <input type="text" size="60" name="emailPagamentoOnline" name="emailPagamentoOnline_id" value="${nuovaIstanzaCommand.domandaOneriHelper.emailPagamentiOnline}" />
							<span class="help_image" id="FLD_emailPagamentoOnline_id_HELP" title="<fmt:message key='help.email-pagamenti-online' />"><label>(?)</label></span>	
							<br />
							<br />				
							<input type="button" value="<fmt:message key='button.pagamento-online' />" 
								onclick="$('#form_oneri_pagamenti_online_id').submit();"/>
						</spring-form:form>
					</fieldset>
						
			</div>
		</c:if>	
		<%-- END CONTENT --%>
		<script type="text/javascript">
		
			
			$( '.help_image' ).tooltip();
		</script>
		<%-- PAGER --%>		
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand" name="form_oneri_post">	
		<jsp:include page="../includes/pager.jsp">
			<jsp:param name="formName" value="form_oneri_post" />
		</jsp:include>
	</spring-form:form>
</body>
</html>