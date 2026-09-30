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
		<fmt:message key="label.gestione_subentri.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
		<script type="text/javascript">

				function nuovaRicerca(){
					
					doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }&resetAttrs=true','');
				}
				
				vbg.ready(() => {
					
					
				});
			
		</script>
	 	<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}</c:param>
	 	</c:import>
	 	<br class="clear"/>
	 	
	 	<div>

			<spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati" cssClass="vbg-form">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
		        	<jsp:param name="commandName" value="autorizzazioniSubentriCommand" />
		    	</jsp:include>
		    	
<c:if test="${autorizzazioniSubentriCommand.presenteEsito}">
	
	<c:forEach var="entry" items="${autorizzazioniSubentriCommand.esito.mappaEsitiAutorizzazione}">
	
		<fieldset class="collassabile" data-collassato="false">
		<legend>${entry.value.estremiAutorizzazione}</legend>
		<c:forEach items="${entry.value.esiti}" var="esito">
			<div class="form-group">
				<c:if test="${not empty esito.errors}">
					<div>
					<div class="titolo_errore"><fmt:message key="label.subentri.errori_bloccanti" /></div>
					<c:forEach items="${esito.errors}" var="errore">
						<div class="error_operazione">
							${errore.messaggio}
						</div>
					</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty esito.warnings}">
					<div>
					<div class="titolo_warning"><fmt:message key="label.subentri.modifiche_dati" /></div>
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
								  
	</c:forEach>
	
</c:if>
			</spring-form:form>
		
		</div>
	</div>
	<br />
	
	<div id="functions">
		<ul>
<c:if test="${autorizzazioniSubentriCommand.possoSubentrare}">
	<script type="text/javascript">
			
				function insertSubentri(){
					
						doSubmit('insertSubentri.htm','Attenzione! Stai per eseguire il subentro delle Autorizzazioni/Concessioni selezionate. Continuare?');
				}
	</script>
			<li>
				<a href="javascript:insertSubentri()"><fmt:message key="button.subentro" /></a>
			</li>
</c:if>			
				
			<li><a href="javascript:nuovaRicerca();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>