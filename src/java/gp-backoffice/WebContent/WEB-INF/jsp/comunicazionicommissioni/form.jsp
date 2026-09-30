<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <html xmlns="http://www.w3.org/1999/xhtml" lang="it">
 	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <title>
	        <fmt:message key="label.comunicazione.commissione.title" />
		</title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-multi-upload.js?<%=vJS %>" defer></script>
	</head>
	<body>
		<style>
			.fa {
				cursor: pointer;
			}
		</style>
		<spring-form:form commandName="comunicazioneCommissioneDetail" name="inviodati">
			<span class="titoloPagina">		
				<fmt:message key="label.comunicazione.commissione.title" />		
			</span>
	        <jsp:include page="../includes/innerNavigation.jsp">
	            <jsp:param name="navmode" value="form" />
	        </jsp:include>
	        <jsp:include page="../includes/history.jsp">
	            <jsp:param name="path" value="../comunicazionicommissioni/view" />
	        </jsp:include>
			<jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName" value="comunicazioneCommissioneDetail" />
            </jsp:include>
	        <div id="subcontent">
		        <div class="parametriDiv">
		            <div class="etichetta">
		                <div>
		                    <fmt:message key="label.descrizione" />:</div>
		            </div>
		            <div class="parametro">
		                <div>${comunicazioneCommissioneDetail.commissione}</div>
		            </div>
		        </div>
				</br>
				</br>
				<div id="form" class="vbg-form">
					<fieldset class="collassabile" data-collassato='false'>
						<legend><fmt:message key="label.dati_principali" /></legend>				
						<div class="form-group">
							<label><fmt:message key="label.data" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.dataFormattata}</span>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.descrizione" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.comunicazione}</span>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.escludinomail" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.escludiDestinatariSenzaMail}</span>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.scelta_mail_anagrafe" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.sceltaMailAnagrafe}</span>
						</div>		
					</fieldset>
					<fieldset class="collassabile" >
						<legend><fmt:message key="label.allegati" /></legend>	
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.allegatifissi" /></label>

							<vbg-multi-upload id="allegatifissiId" path-name="allegatiFissi" context-path='${pageContext.request.contextPath}' readonly='true'>
		                		<c:forEach items="${comunicazioneCommissioneDetail.allegatiFissi}" var="allegato">
		                			<input type="hidden" value="${allegato.codiceOggetto}"></input>
		                		</c:forEach>
		                	</vbg-multi-upload>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.documentitipo" /></label>
							<div class='readonly-form-control' >
								<ul>
									<c:forEach items="${comunicazioneCommissioneDetail.lettereTipo}" var="letteraTipo">
										<li>
											${letteraTipo.descrizioneLettera}
											</br>
											<div style="font-style: italic; margin-bottom: var(--default-padding);">
												<jsp:include page="../includes/visualizzaOggetto.jsp" >
						       						<jsp:param name="idElemento" value="letteraTipo${letteraTipo.codiceOggetto}" />
						       						<jsp:param name="fileId" value="${letteraTipo.codiceOggetto}" />
						       						<jsp:param name="mostralabel" value="true"/>
						       						<jsp:param name="mostraNomeFile" value="true"/>
													<jsp:param name="readonly" value="true"/>
						   						</jsp:include>
					   						</div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.convertiPDF" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.convertiInPDF}</span>
						</div>
						
					</fieldset>
					<fieldset class="collassabile">
						<legend><fmt:message key="label.firma_digitale" /></legend>
							<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.firmatari" /></label>
							<div class='readonly-form-control' >
								<ul>
									<c:forEach items="${comunicazioneCommissioneDetail.firmatari}" var="firmatario">
										<li>${firmatario}</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</fieldset>
					<fieldset class="collassabile">
						<legend><fmt:message key="label.protocollazione" /></legend>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.mailSoggProtocollo" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.oggettoProtocollo}</span>
						</div>
					</fieldset>	
					<fieldset class="collassabile">
						<legend><fmt:message key="label.email" /></legend>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.accountMail" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.accountInvioMail}</span>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.comunicazione.commissione.templateMailTipo" /></label>
							<span class='readonly-form-control'>${comunicazioneCommissioneDetail.templateMailTipo}</span>
						</div>
					</fieldset>
				</div>
				<div style="margin-bottom: var(--default-padding);">
					<div class='btn btn-primary' id='cmdElaboraComunicazione' data-id="${comunicazioneCommissioneDetail.idTestataComunicazione}">
						<fmt:message key="button.elabora" />
					</div>				
					<div class='btn btn-primary' id='cmdEliminaComunicazione' data-idtestata='' data-url='eliminaMassiva.htm?idTestata=${comunicazioneCommissioneDetail.idTestataComunicazione}&idCommissione=${comunicazioneCommissioneDetail.idCommissione}'>
						<fmt:message key="button.delete" />
					</div>					
					<div class='btn btn-secondary' id='cmdChiudiDettaglio' data-url='list.htm?idCommissione=${comunicazioneCommissioneDetail.idCommissione}'>
						<fmt:message key="button.back" />
					</div>
				</div>
	        </div>
	        <jsp:include page="./funzioniJS.jsp" />
			<jsp:include page="./dettaglioComunicazione.jsp" />
			<jsp:include page="./dettaglioRigaComunicazione.jsp" />
			<jsp:include page="./modificaMail.jsp" />
		</spring-form:form>
	</body>
</html>