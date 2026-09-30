<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService.ENUM_COPIA_ONERI"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_subentri.title" />
	</title>
</head>
<body>
<style>
.form-group > span{
	padding-left: 20px;
}
.form-group > span > em{
	font-weight: bold;
	text-decoration: underline; 	
}
.warning_operazione{
	
	padding-bottom:10px;
}
.error_operazione{

	padding-bottom:10px;
}
.titolo_errore{
	color: red;
	font-weight: bolder;
	padding-bottom:10px; 
}
.titolo_warning{
	font-weight: bolder;
	padding-bottom:10px;
}
</style>
	<span class="titoloPagina">
		<fmt:message key="label.autorizzazioni_modifica_occupante" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
	 	<br class="clear"/>
	 	<c:choose>
				<c:when test="${validazioneOccupanteCommand.attoCollegato}">
						<h3><fmt:message key="label.autorizzazioni_modifica_occupante.errore_atto_collegato" /></h3>
						<div id="functions">
							<ul>
								<li><a href="javascript: chiudi()"><fmt:message key="button.back" /></a></li>
							</ul>
							
						</div>					
				
				</c:when>
				<c:otherwise>
					<div>
								<spring-form:form commandName="validazioneOccupanteCommand" name="inviodati" cssClass="vbg-form">
									<jsp:include page="../includes/displayGlobalMessages.jsp">
							        	<jsp:param name="commandName" value="validazioneOccupanteCommand" />
							    	</jsp:include>
							    	
									<c:if test="${validazioneOccupanteCommand.presenteEsito}">
										
											<fieldset class="collassabile" data-collassato="false">
											<legend>Esito validazione</legend>
											<c:forEach items="${validazioneOccupanteCommand.esito.esiti}" var="esito">
												<div class="form-group">
													<c:if test="${not empty esito.errors}">
														<div>
														<div class="titolo_errore"><fmt:message key="label.autorizzazioni_modifica_occupante.errori_bloccanti" /></div>
														<c:forEach items="${esito.errors}" var="errore">
															<div class="error_operazione">
																${errore.messaggio}
															</div>
														</c:forEach>
														</div>
													</c:if>
													<c:if test="${not empty esito.warnings}">
														<div>
														<div class="titolo_warning"><fmt:message key="label.autorizzazioni_modifica_occupante.modifiche_dati" /></div>
														<c:forEach items="${esito.warnings}" var="avviso" >
															<div class="warning_operazione">
																${avviso.messaggio}
															</div>
														</c:forEach>
														</div>
													</c:if>	
												</div>	
											</c:forEach>		
											</fieldset>		
																	  
										
										
									</c:if>
									<fieldset>
									<legend>
										<c:choose>		
											<c:when test="${validazioneOccupanteCommand.concessione eq true}">
												<fmt:message key="label.concessione_dati_della_concessione" />
											</c:when>
											<c:otherwise>
												<fmt:message key="label.autorizzazione_numero_autorizzazione" />
											</c:otherwise>
										</c:choose>
										${validazioneOccupanteCommand.estremiAtto}
									</legend>
									<div style="font-weight: bolder; font-size: 1.2em; padding: 10px;"><fmt:message key="label.autorizzazioni_modifica_occupante.help" /></div>
										<div class="form-group">
											<label><fmt:message key="label.concessione_titolare" /></label>							
											<input type="text" value="${validazioneOccupanteCommand.vecchioTitolare.descrizione}" size="100" disabled="disabled"/>
										</div>				
										<div class="form-group">
											<label><fmt:message key="mercatid.label.precedente_occupante" /></label>							
											<input type="text" value="${validazioneOccupanteCommand.vecchioOccupante.descrizione}" size="100" disabled="disabled"/>
										</div>	
										<div class="form-group">
												<label><fmt:message key="mercatid.label.occupante" /></label>
												<input type="text" size="98" id="occupante_id" 
																class="searchbox" 
																onchange="checkValue(this,'occupante_hidden')" 			
																onkeydown="javascript:return searchAll(this,event)" 
																name="descrizioneNuovoOccupante" 
																value="${validazioneOccupanteCommand.descrizioneNuovoOccupante}"/>		
					
												<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="occupante_hidden" 
													idInput="occupante_id" inputTitleKey="label.ricerca"/>
												<spring-form:hidden id="occupante_hidden" path="codiceAnagrafeNuovoOccupante" />
										</div>
									</fieldset>
									
								</spring-form:form>
							
							</div>
						
						<br />
						
						<div id="functions">
							<ul>
								<c:choose>		
								<c:when test="${validazioneOccupanteCommand.possoModificare}">
											<li>
												<a href="javascript:modifica()"><fmt:message key="label.modifica" /></a>
											</li>
								</c:when>
								<c:otherwise>
											<li>
												<a href="javascript:verifica()"><fmt:message key="label.verifica" /></a>
											</li>
								</c:otherwise>
								</c:choose>
								<li><a href="javascript: chiudi()"><fmt:message key="button.back" /></a></li>
							</ul>
							
						</div>					
				
				</c:otherwise>
			</c:choose>
	 	
	 	
	 	
	 	
	 	
	 	
<script type="text/javascript">

			function chiudi(){
				
				doHref(decodeURIComponent('${validazioneOccupanteCommand.returnTo}'),'');
			}

			function validaForm(){
				let campoOccupante = document.getElementById('occupante_hidden').value;
				if(campoOccupante==''){
					alert('Il campo occupante è obbligatorio');
					return false;
				}
				return true;
			}
					
		<c:if test="${validazioneOccupanteCommand.possoModificare}">
			function modifica(){
					if(validaForm()){
						doSubmit('updateModificaOccupante.htm','Confermate l\'operazione? l\'attività sarà registrata nei logs.');
					}
			}
		</c:if>
		
			function verifica(){
				if(validaForm()){
					doSubmit('verificaModificaOccupante.htm','');
				}
			}
		</script>
	</div>
</body>
</html>