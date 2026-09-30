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
		<fmt:message key="label.autorizzazioni_modifica_data_cessazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
		
	 	<br class="clear"/>
	 	
	 	<div>

			<spring-form:form commandName="validazioneDataCessazioneSubentroCommand" name="inviodati" cssClass="vbg-form">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
		        	<jsp:param name="commandName" value="validazioneDataCessazioneSubentroCommand" />
		    	</jsp:include>
		    	
		    	<div style="font-weight: bolder; font-size: 1.2em; padding: 10px;"><fmt:message key="label.autorizzazioni_modifica_data_cessazione.help" /></div>
				
				<c:if test="${validazioneDataCessazioneSubentroCommand.presenteEsito}">
					
						<fieldset class="collassabile" data-collassato="false">
						<legend>Esito validazione</legend>
						<c:forEach items="${validazioneDataCessazioneSubentroCommand.esito.esiti}" var="esito">
							<div class="form-group">
								<c:if test="${not empty esito.errors}">
									<div>
									<div class="titolo_errore"><fmt:message key="label.autorizzazioni_modifica_data_cessazione.errori_bloccanti" /></div>
									<c:forEach items="${esito.errors}" var="errore">
										<div class="error_operazione">
											${errore.messaggio}
										</div>
									</c:forEach>
									</div>
								</c:if>
								<c:if test="${not empty esito.warnings}">
									<div>
									<div class="titolo_warning"><fmt:message key="label.autorizzazioni_modifica_data_cessazione.modifiche_dati" /></div>
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
						<c:when test="${validazioneDataCessazioneSubentroCommand.concessione eq true}">
							<fmt:message key="label.concessione_dati_della_concessione" />
						</c:when>
						<c:otherwise>
							<fmt:message key="label.autorizzazione_numero_autorizzazione" />
						</c:otherwise>
					</c:choose>
					${validazioneDataCessazioneSubentroCommand.estremiAtto}
				</legend>
				
					<div class="form-group">
						<label><fmt:message key="label.concessione_titolare" /></label>							
						<input type="text" value="${validazioneDataCessazioneSubentroCommand.titolare.descrizione}" size="100" disabled="disabled"/>
					</div>				
					<div class="form-group">
						<label><fmt:message key="mercatid.label.precedente_occupante" /></label>							
						<input type="text" value="${validazioneDataCessazioneSubentroCommand.occupante.descrizione}" size="100" disabled="disabled"/>
					</div>	
					
				</fieldset>
				
				<fieldset>
				<legend>
					<fmt:message key="label.passaggi_della_concessione" /><br />
					<c:choose>		
						<c:when test="${validazioneDataCessazioneSubentroCommand.concessione eq true}">
							<fmt:message key="label.concessione_dati_della_concessione" />
						</c:when>
						<c:otherwise>
							<fmt:message key="label.autorizzazione_numero_autorizzazione" />
						</c:otherwise>
					</c:choose>
					${validazioneDataCessazioneSubentroCommand.estremiSubentro}
				</legend>
				
					<div class="form-group">
						<label><fmt:message key="label.concessione_titolare" /></label>							
						<input type="text" value="${validazioneDataCessazioneSubentroCommand.titolareSubentro.descrizione}" size="100" disabled="disabled"/>
					</div>				
					<div class="form-group">
						<label><fmt:message key="mercatid.label.precedente_occupante" /></label>							
						<input type="text" value="${validazioneDataCessazioneSubentroCommand.occupanteSubentro.descrizione}" size="100" disabled="disabled"/>
					</div>	
					<div class="form-group">
						<label><fmt:message key="label.data_cessazione" /></label>							
						<input type="text" value="<fmt:formatDate value="${validazioneDataCessazioneSubentroCommand.dataCessazionePrecedente}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" size="100" disabled="disabled"/>
					</div>	
				</fieldset>
				<div class="form-group">
						<label><fmt:message key="label.data_cessazione" /></label>
						<spring-form:input id="cessazione_id" path="nuovaDataCessazione" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
				</div>
				
			</spring-form:form>
		
		</div>
	</div>
	<br />
	
	<div id="functions">
		<ul>
			<c:choose>		
			<c:when test="${validazioneDataCessazioneSubentroCommand.possoModificare}">
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
		
		
<script type="text/javascript">

			function chiudi(){
				
				doHref(decodeURIComponent('${validazioneDataCessazioneSubentroCommand.returnTo}'),'');
			}

			function validaForm(){
				let campoDataCessazione = document.getElementById('cessazione_id').value;
				if(campoDataCessazione==''){
					alert('Il campo <fmt:message key="label.data_cessazione" /> è obbligatorio');
					return false;
				}
				return true;
			}
					
		<c:if test="${validazioneDataCessazioneSubentroCommand.possoModificare}">
			function modifica(){
					if(validaForm()){
						doSubmit('updateModificaDataCessazioneSubentro.htm','Confermate l\'operazione? l\'attività sarà registrata nei logs.');
					}
			}
		</c:if>
		
			function verifica(){
				if(validaForm()){
					doSubmit('verificaDataCessazioneSubentro.htm','');
				}
			}
</script>
	</div>
</body>
</html>