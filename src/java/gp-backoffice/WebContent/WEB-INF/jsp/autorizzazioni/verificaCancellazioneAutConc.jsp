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
		<fmt:message key="label.autorizzazioni_concessioni_cancellazione" />
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
		<fmt:message key="label.autorizzazioni_concessioni_cancellazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
		
	 	<br class="clear"/>
	 	
	 	<div>

			<spring-form:form commandName="validaEliminazioneAutConcCommand" name="inviodati" cssClass="vbg-form">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
		        	<jsp:param name="commandName" value="validaEliminazioneAutConcCommand" />
		    	</jsp:include>
		    	
		    	<div style="font-weight: bolder; font-size: 1.2em; padding: 10px;"><fmt:message key="label.autorizzazioni_concessioni_cancellazione.help" /></div>
				
				<c:if test="${validaEliminazioneAutConcCommand.presenteEsito}">
					
						<fieldset class="collassabile" data-collassato="false">
						<legend>Esito validazione</legend>
						<c:forEach items="${validaEliminazioneAutConcCommand.esito.esiti}" var="esito">
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
						<c:when test="${validaEliminazioneAutConcCommand.concessione eq true}">
							<fmt:message key="label.concessione_dati_della_concessione" />
						</c:when>
						<c:otherwise>
							<fmt:message key="label.autorizzazione_numero_autorizzazione" />
						</c:otherwise>
					</c:choose>
					${validaEliminazioneAutConcCommand.estremiAtto}
				</legend>
				
					<div class="form-group">
						<label><fmt:message key="label.concessione_titolare" /></label>							
						<input type="text" value="${validaEliminazioneAutConcCommand.titolare.descrizione}" size="100" disabled="disabled"/>
					</div>				
					<div class="form-group">
						<label><fmt:message key="mercatid.label.precedente_occupante" /></label>							
						<input type="text" value="${validaEliminazioneAutConcCommand.occupante.descrizione}" size="100" disabled="disabled"/>
					</div>	
					
				</fieldset>
	
				
			</spring-form:form>
		
		</div>
	</div>
	<br />
	
	<div id="functions">
		<ul>
			<c:choose>		
			<c:when test="${validaEliminazioneAutConcCommand.possoModificare}">
						<li>
							<a href="javascript:modifica()"><fmt:message key="label.elimina" /></a>
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
				
				doHref(decodeURIComponent('${validaEliminazioneAutConcCommand.returnTo}'),'');
			}

			function validaForm(){
				
				return true;
			}
					
		<c:if test="${validaEliminazioneAutConcCommand.possoModificare}">
			function modifica(){
					if(validaForm()){
						doSubmit('deleteConcessione.htm','Confermate l\'operazione? l\'attività sarà registrata nei logs.');
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