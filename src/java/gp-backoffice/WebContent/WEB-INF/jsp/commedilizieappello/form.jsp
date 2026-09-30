<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.CommedilizieAppello"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizieappello.id.codice==null}">
			<fmt:message key="label.nuovo_convocato" />
		</c:if> 
		<c:if test="${commedilizieappello.id.codice!=null}">
			<fmt:message key="label.modifica_convocato" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizieappello.id.codice==null}">
			<fmt:message key="label.nuovo_convocato" />
		</c:if> 
		<c:if test="${commedilizieappello.id.codice!=null}">
			<fmt:message key="label.modifica_convocato" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.dettaglio_commissione_edilizia"/></legend>
			<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.numero_commissione" />:</div>
				<div><fmt:message key="label.descrizione" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>${commedilizieappello.commissioniedilizieT.numprotocollo}</div>
				<div>${commedilizieappello.commissioniedilizieT.descrizione}</div>
			</div>
	</div>
		</fieldset>
	</div>
	
	<br class="clear" />
		<spring-form:form commandName="commedilizieappello" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizieappello" />
		    </jsp:include>
		    <%
		    	String displayResponsabile="";
		    	String displayAmministrazione="";
		    	String displaySoggettoPratica = "";
		    	String displayAmmSoggPratica = "";
		    	if((CommedilizieAppello)request.getAttribute("commissione")!=null)
		    	{
			   		CommedilizieAppello commedilizieAppello= (CommedilizieAppello)request.getAttribute("commissione");
			   	    if(commedilizieAppello.getId()==null){
			    		displayResponsabile="";
			    		displayAmministrazione="display:none;";
			    		displaySoggettoPratica="display:none;";
			    		 displayAmmSoggPratica = "display:none;";
			   	   	}
				   	if(commedilizieAppello.getId()!=null){
				     	if(commedilizieAppello.getResponsabile().getId().getCodice()!=null){
				    		 displayResponsabile="";
				    		 displayAmministrazione="display:none;";
				    		 displaySoggettoPratica="display:none;";
				    		 displayAmmSoggPratica = "display:none;";
				   		 }
				     	else if(commedilizieAppello.getAmministrazioni().getId().getCodice()!=null){
				    		displayResponsabile="display:none;";
				    	    displayAmministrazione="";
				    		displaySoggettoPratica="display:none;";
				    		displayAmmSoggPratica = "";
				   	 	 }
				   	 	 else if (commedilizieAppello.getAnagrafe().getId() != null && commedilizieAppello.getAnagrafe().getId().getCodice() != null) 
				   	 	 {
				   	 	 	displayResponsabile="display:none;";
					    	displayAmministrazione="display:none;";
					    	displaySoggettoPratica="";
					    	displayAmmSoggPratica="";
				   	 	 }
				   	}
		    	}
		    
		    %>
		    <div class="vbg-form">
		    	<fieldset>
		    		<legend><fmt:message key="label.soggetto"/></legend>
		    	
		    	<c:if test="${commedilizieappello.id.codice==null}">
		    	
				<%
				String checkedResponsabile = "";
				String checkedAmministrazione = "";
				String checkedSoggetto = "";
				String valSelected = (String)request.getParameter("tipoConvocato");
				
				if("radio_soggetto_pratica".equalsIgnoreCase(valSelected)){
				    
				    checkedSoggetto = "selected";
				}else if("radio_amministratore".equalsIgnoreCase(valSelected)){
				    checkedAmministrazione = "selected";
				}else{
				    checkedResponsabile = "selected";
				}
				%>
		    	
		    	
			    	<div class="form-group">
			    		<label><fmt:message key="label.soggetto_convocato"/></label>
			    		<select name="tipoConvocato" id="convocato_id">
			    			<option value="radio_responsabile" <%=checkedResponsabile %>><fmt:message key="label.responsabile" /></option>
			    			<option value="radio_amministratore" <%= checkedAmministrazione %>><fmt:message key="label.amministrazione" /></option>
			    			<option value="radio_soggetto_pratica" <%= checkedSoggetto %>><fmt:message key="label.soggetto_pratica" /></option>
			    		</select>						
			    	</div>			    	
		    	</c:if>		    	

		    	<div class="form-group" id="responsabili_id" style="<%=displayResponsabile%>">
		    		<label><fmt:message key="label.responsabile" /></label>
		    		<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="responsabile" />
						<jsp:param name="propertyPath" value="responsabile" />										
						<jsp:param name="pathPropertyDescription" value="responsabile.responsabile" />
						<jsp:param name="pathPropertyCode" value="responsabile.id.codice" />
						<jsp:param name="autocompleterAjax" value="findResponsabili.htm" />							
						<jsp:param name="titleKey" value="label.ricerca_responsabile" />
						<jsp:param name="autocompleterInputSize" value="65" />
					</jsp:include>
		    	
		    	</div>
		    	
		    	<div class="form-group" id="amministratori_id" style="<%=displayAmministrazione%>">
		    		<label><fmt:message key="label.amministrazione" /></label>
		    		<c:if test="${commedilizieappello.id.codice==null}">
			    		<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />
							<jsp:param name="propertyPath" value="amministrazioni" />										
							<jsp:param name="pathPropertyDescription" value="amministrazioni.amministrazione" />
							<jsp:param name="pathPropertyCode" value="amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />							
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
							<jsp:param name="autocompleterInputSize" value="66" />
						</jsp:include>
					</c:if>
					<c:if test="${commedilizieappello.id.codice != null}">
						<input type="text" value="${commedilizieappello.amministrazioni.amministrazione }" disabled="disabled" size="68" />					
					</c:if>					
		    	</div>
		    	
		    	<div class="form-group" id="referente_id" style="<%=displayAmministrazione%>">
		    		<label><fmt:message key="label.referente" /></label>
		    		<spring-form:input id="referentefield_id" path="referente" size="68"  />
					<spring-form:errors path="referente" cssClass="error"/>	
		    	</div>
		    	
		    	<div class="form-group" id="soggetto_pratica_id" style="<%=displayAmmSoggPratica%>">
		    		<label id="label_sogg_pratica" style="<%=displaySoggettoPratica%>"><fmt:message key="label.soggetto_pratica" /></label>
		    		<label id="label_sogg" style="<%=displayAmministrazione%>" title="<fmt:message key="label.soggetto_amministrazione"/>" ><fmt:message key="label.soggetto_amministrazione"/> </label>
		    		<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="anagrafe" />
						<jsp:param name="propertyPath" value="anagrafe" />										
						<jsp:param name="pathPropertyDescription" value="anagrafe.richiedente" />
						<jsp:param name="pathPropertyCode" value="anagrafe.id.codice" />
						<jsp:param name="autocompleterAjax" value="findAnagrafe.htm?statoAnagrafe=ALL" />							
						<jsp:param name="titleKey" value="label.ricerca_soggetto_pratica" />
						<jsp:param name="autocompleterInputSize" value="66" />
					</jsp:include>
		    	</div>	    
		    	
		    	<div class="form-group">
		    		<label><fmt:message key="label.carica" /></label>
		    		<spring-form:select path="commedilizieCarica.id.codice">
						<spring-form:options items="${listCariche}" itemLabel="descrizione" itemValue="id.codice"/>
					</spring-form:select>		    	
		    	</div>
		    	
		    	<div class="form-group">
		    		<label><fmt:message key="label.presente" /></label>
		    		<spring-form:select path="presente">
					    <c:if test="${commedilizieappello.presente == true}">
							<spring-form:option value="1"><fmt:message key="label.si"/> </spring-form:option>
							<spring-form:option value="0"><fmt:message key="label.no"/> </spring-form:option>
						</c:if>
						<c:if test="${commedilizieappello.presente == false}">
							<spring-form:option value="0"><fmt:message key="label.no"/> </spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.si"/> </spring-form:option>
						</c:if>
					</spring-form:select>
		    	</div>		    	
		    	<div class="form-group" id="pin_div" style="<%=displayAmministrazione%>">
		    		<label><fmt:message key="commedilizieappello.label.pin"/></label>
		    		<input id="pin_id" type="text" value="${commedilizieappello.pin}" readonly size="10"/>		    		
		    		<div class="input-help"><fmt:message key="commedilizieappello.label.pin.help"/></div>	    		
		    	</div>					
		    	</fieldset>
		    </div>							
			<div class="vbg-form">
				<fieldset>
					<legend><fmt:message key="label.commisioni_edilize_elenco_pratiche" /></legend>
					<jsp:include page="./commedilizieappellopratiche.jsp">
						<jsp:param name="codicecommissione" value="${commedilizieappello.commissioniedilizieT.id.codice}" />
						<jsp:param name="codiceappello" value="${commedilizieappello.id.codice}" />
					</jsp:include>
				</fieldset>
			</div>
		</spring-form:form>
	</div>
	<div class="form-button">		
		<c:if test="${commedilizieappello.id.codice==null}">
			<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${commedilizieappello.id.codice!=null}">			
			<c:if test="${commedilizieappello.commissioniedilizieT.flagaperta eq true }">
				<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>		
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('list.htm?codiceCommissione=${commedilizieappello.commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a>
		
	</div>
	
	<script type='text/javascript'>

		let selectConvocato = document.querySelector('#convocato_id');		
		let pin = document.querySelector('#pin_id').value;				
		
		if(selectConvocato){
			selectConvocato.addEventListener('click', (e) =>{
			
				selectTipo(selectConvocato.value);
			});			
		}
		const fadeIn = (element) => {
			element.style.display = '';
		};
	
		const fadeOut = (element) => {
			element.style.display = 'none';
		};
	
		function selectTipo(id) {
			if(id=='radio_responsabile')
			{						
				fadeOut(document.getElementById('amministratori_id'));
				document.getElementById('amministrazioni_id').value = '';
				document.getElementById('referentefield_id').value = '';
				fadeOut(document.getElementById('referente_id'));
				fadeOut(document.getElementById('soggetto_pratica_id'));
				document.getElementById('anagrafe_id').value = '';
				fadeIn(document.getElementById('responsabili_id'),);
				document.getElementById('responsabili_id').style.display = '';
				document.querySelector('#pin_div').style.display = 'none';
				return true;
			}
			if(id=='radio_amministratore')
			{
				fadeOut(document.getElementById('responsabili_id'));
				document.getElementById('responsabile_id').value = '';
				document.getElementById('label_sogg_pratica').style.display = 'none';
				document.getElementById('label_sogg').style.display = 'inline-block'; 
				fadeIn(document.getElementById('soggetto_pratica_id'));
				fadeIn(document.getElementById('amministratori_id'));
				fadeIn(document.getElementById('referente_id'));
				document.getElementById('amministratori_id').style.display = "";
				document.getElementById('referente_id').style.display = "";						
				document.getElementById('anagrafe_id').value = '';
				document.querySelector('#pin_div').style.display = 'inline-block';				
				return true;
			}
			if (id == 'radio_soggetto_pratica') 
			{
				fadeOut(document.getElementById('amministratori_id'));
				document.getElementById('amministrazioni_id').value = '';
				document.getElementById('referentefield_id').value = '';
				fadeOut(document.getElementById('referente_id'));
				fadeOut(document.getElementById('responsabili_id'));
				document.getElementById('responsabile_id').value = '';
				document.getElementById('label_sogg_pratica').style.display = 'inline-block';
				document.getElementById('label_sogg').style.display = 'none'; 
				fadeIn(document.getElementById('soggetto_pratica_id'));
				document.getElementById('soggetto_pratica_id').style.display = "";
				document.getElementById('anagrafe_id').value = '';
				document.querySelector('#pin_div').style.display = 'none';					
				return true;
			}
			
		}
		
		if(selectConvocato){
			selectTipo(selectConvocato.value); 
		}				
	</script>
</body>
</html>