<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.parametri_scadenzario_avvio" />
	</title>
	<style>
	#scadenzarioDiv{
		    border-collapse: separate;
	}
  	#catalog ul { margin: 0; padding: 1em 0 3em 3em; }
  	
  	#cart ul { margin: 0; padding: 1em 0 3em 3em; }
  	</style>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.parametri_scadenzario_avvio" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.responsabile" /></legend>
			<div class="parametriDiv">			
				<div class="etichetta">
		            <div>
		             	<fmt:message key="label.responsabile" />:
		        	</div>
	           	</div>
	           	<div class="parametro">
	       	   		<div>
			        	 <c:out value="${responsabile.entity.responsabile}"/>
			   		</div>
			 	</div>
        	</div>   
		</fieldset>		
        <br />
		<spring-form:form commandName="responsabile" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="responsabile" />
		    </jsp:include>
		    		   
		    <spring-form:hidden path="entity.id.codice" />
		    <c:set var="showDiv" scope="page"></c:set>
		    <c:if test="${responsabile.entity.scadenzario eq false }">
		    	<c:set var="showDiv" scope="page">display: none;</c:set>
		    </c:if>
		    <div class="form-group">
		    	<label><fmt:message key="responsabili.label.scadenzario" /></label>
		    	<spring-form:checkbox tabindex="21" id="scadenzario_id" path="entity.scadenzario" onclick="showHideElements('scadenzarioDiv', 'table');showHideElements('scadenzarioDiv', 'fieldset')" />
				<fmt:message key="responsabili.help.scadenzario" />
				<spring-form:errors path="entity.scadenzario" cssClass="error" />
		    </div>			
			
			<c:if test="${ isBatchScadenzarioPage eq false}">	
	
					<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
						<fieldset id="scadenzarioDiv_" style="width: 80%;">	
						<legend><fmt:message key="label.impostazione_utente_pagina_centrale" /> </legend>		
								<fmt:message key="label.impostazione_utente_pagina_centrale.help" />
							<table>				
								<tr>		
									<td><fmt:message key="label.impostazione_utente_pagina_centrale" /></td>
									<td>
										<input type="text" name="center_page" size="50" 
										value="<%= StringUtils.defaultString((String)request.getAttribute(WebConstants.CONF_UTENTE_PAGINA_CENTRALE))%>"	/>						
									</td>
								</tr>
							</table>
						</fieldset>
					</spring-security:authorize>				
			</c:if>
			
			<br />

			<table class="vbg-table" id="scadenzarioDiv" border="1px" style="width: 81.5%;${showDiv}">				
				<tr style="vertical-align: top;">
					<td rowspan="3"><fmt:message key="label.considera_le_scadenze" /></td>
					<td rowspan="2"><fmt:message key="label.da" /></td>
					<td>
						<fmt:message key="label.data" />
					</td>
					<td>
						<spring-form:input id="scadDatainizio_id" path="entity.scadDatainizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
						<init:calendar imagePath="/images/cal.gif" idImage="calscadDatainizio" idInput="scadDatainizio_id" textKey="label.calendar"/>
				   		<spring-form:errors path="entity.scadDatainizio" cssClass="error" />
				   		<init:help idHelp="help_scadDatainizio" textKey="help.responsabili.scadDatainizio"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.n_gg_da_oggi" />
					</td>
					<td>
						<spring-form:input id="scadenzarioprec_id" path="entity.scadenzarioprec" size="6" maxlength="5"/>
						<spring-form:errors path="entity.scadenzarioprec" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.a" /></td>
					<td>
						<fmt:message key="label.n_gg_da_oggi" />
					</td>
					<td>
						<spring-form:input tabindex="24" id="numggscadenz_id" path="entity.numggscadenz" size="6" maxlength="5"/>
						<spring-form:errors path="entity.numggscadenz" cssClass="error" />
						<fmt:message key="responsabili.help.numggscadenz" />					
					</td>
				</tr>
				<tr>
					<td colspan="3"><fmt:message key="label.considera_le_scadenze_per_software" /></td>
					<td>
					
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="modulo_id" />		
							<jsp:param name="propertyPath" value="entity.scadSoftware" />				
							<jsp:param name="pathPropertyDescription" value="entity.scadSoftware.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.scadSoftware.codice" />
							<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=true&codiceResponsabile=${responsabile.entity.id.codice }" />							
							<jsp:param name="titleKey" value="label.ricerca_software" />
						</jsp:include>					
						<spring-form:errors path="entity.scadSoftware" cssClass="error" />
						<init:help idHelp="help_scadSoftware" textKey="help.responsabili.scadSoftware"/>
					</td>
				</tr>
				<tr>
					<td colspan="3"><fmt:message key="label.considera_le_scadenze_per_operatore" /></td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="responsabile_scadOperatore" />		
							<jsp:param name="propertyPath" value="scadOperatore" />				
							<jsp:param name="pathPropertyDescription" value="scadOperatore.valore" />
							<jsp:param name="pathPropertyCode" value="scadOperatore.chiave" />
							<jsp:param name="autocompleterAjax" value="findResponsabili.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_responsabile" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td colspan="3"><fmt:message key="label.considera_le_scadenze_per_stato_pratica" /></td>
					<td>
						<spring-form:select path="entity.scadComportamento" >
						    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
							<spring-form:option value="0"><fmt:message key="label.stato_pratiche_attive"/></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.stato_pratiche_chiuse"/></spring-form:option>
						</spring-form:select> 
						<spring-form:errors path="entity.scadComportamento" cssClass="error" />
					</td>
				</tr>
			</table>
						
					<spring:bind path="entity.responsabilicomunis">
						<c:forEach items="${responsabile.entity.responsabilicomunis}" var="currentRespCom">
							<input type="hidden" name="${status.expression}" value="${currentRespCom.comune.codicecomune}"></input>
						</c:forEach>
					</spring:bind>
					<spring:bind path="entity.softwareAbilitati">
							<c:forEach var="d" items="${responsabile.entity.softwareAbilitati}">
								
									<input type="hidden" id="swabilitati_id${counter.index}" name="${status.expression}" value="${d.id.software}" />
															
							</c:forEach>
					</spring:bind>
		<div><br class="clear" /></div>
			        	<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">
       	
		<legend><b><fmt:message key="label.responsabili_scadenzario.ordinamento" /></b></legend>
		<br class="clear" />	
				<div>
					<span><fmt:message key="label.data_scadenza" /></span>
					<span>
						<spring-form:select path="valoreOrdinamentoData">
							<spring-form:option value="DESC" label="Decrescente"><fmt:message key="label.ordinamento_desc"/></spring-form:option>
							<spring-form:option value="ASC" label="Crescente"><fmt:message key="label.ordinamento_asc"/></spring-form:option>
						</spring-form:select> 
					</span>
				</div>
		</fieldset>  
		
		<!--Parametri Scadenzario oneri -->		
		<br class="clear" />	
		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">				
		<legend><b><fmt:message key="label.visualizza_parametri_scadenzario_oneri" /></b></legend>
			<div class="form-group">
				<label><fmt:message key="label.n_gg_da_oggi"/></label>
				<input type="text" id="giorni_da_oggi_oneri" name="ngiorniDaOggi" value="${responsabile.ngiorniDaOggi}"/>
				<div class="input-help"><fmt:message key="help.scadenzario.giorniDaOggiOneri" /></div>
			<%-- 	<init:help idHelp="help_giorniDaOggiOneri" textKey="help.scadenzario.giorniDaOggiOneri"/> --%>
			</div>
		</fieldset>			
		
		</spring-form:form>
		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">
		<legend><b><fmt:message key="label.responsabili_tipimovimento_scadenzario" /></b></legend>
		<br class="clear" />	
		<div>				
				<div>
					<span><fmt:message key="label.tipomovimento" /></span>
					<span>
					
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="responsabile_tm_sca_id" />		
							<jsp:param name="propertyPath" value="responsabile.tipimovimentoSca" />				
							<jsp:param name="pathPropertyDescription" value="responsabile.tipimovimentoSca.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="responsabile.tipimovimentoSca.id.tipomovimento" />
							<jsp:param name="autocompleterAjax" value="findTipiMovPerResponsabile.htm?tipo=sca&codiceResponsabile=${responsabile.entity.id.codice}" />							
							<jsp:param name="titleKey" value="label.ricerca_tipimovimento" />
						</jsp:include>
						
					</span>
					<!-- La sezione seguente viene visualizzata solo se il tipomovimento inserito nella barra di ricerca è già presente nella tabella RESPONSABILI_TM_SCA.
					     Il messaggio di errore viene visualizzato tramite il metodo mostraErroreSePresenteTipoMov() -->
					<div id="messaggioErrore_sca" class="error_header" style="display: none;">
					</div>
					<div>
					<a class="addColumn" style="float: none;" href="javascript:aggiungiResptm('${responsabile.entity.id.codice}','responsabile_tm_sca_id','sca')" title="<fmt:message key="label.aggiungi" />">
					    <label><fmt:message key="label.add.record.image" /></label>
					</a>
					</div>
				</div>
				<br class="clear"/>
				<div style="border: #c0c0c0 1px dashed; padding: 4px;">
					<div id="responsabilitmsca_id">
				
					</div>
				</div>
		</div>
		</fieldset>


			
		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">	
		<br class="clear" />			
		<legend><b><fmt:message key="label.responsabili_tipimovimento_avvisi" /></b></legend>
		<br class="clear" />	
			<div>				
					<div>
						<span><fmt:message key="label.tipomovimento" /></span>
						<span>
						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="responsabile_tm_avv_id" />		
							<jsp:param name="propertyPath" value="responsabile.tipimovimentoAvv" />				
							<jsp:param name="pathPropertyDescription" value="responsabile.tipimovimentoAvv.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="responsabile.tipimovimentoAvv.id.tipomovimento" />
							<jsp:param name="autocompleterAjax" value="findTipiMovPerResponsabile.htm?tipo=avv&codiceResponsabile=${responsabile.entity.id.codice}" />							
							<jsp:param name="titleKey" value="label.ricerca_tipimovimento" />
						</jsp:include>
						
						</span>
					</div>
					<!-- La sezione seguente viene visualizzata solo se il tipomovimento inserito nella barra di ricerca è già presente nella tabella RESPONSABILI_TM_AVV.
					     Il messaggio di errore viene visualizzato tramite il metodo mostraErroreSePresenteTipoMov() -->
					<div id="messaggioErrore_avv" class="error_header" style="display: none;">
					</div>
					<div>
						<a class="addColumn" style="float: none;" href="javascript:aggiungiResptm('${responsabile.entity.id.codice}','responsabile_tm_avv_id','avv')" title="<fmt:message key="label.aggiungi" />">
						    <label><fmt:message key="label.add.record.image" /></label>
						</a>
					</div>
					<br class="clear"/>
					<div style="border: #c0c0c0 1px dashed; padding: 4px;">
						<div id="responsabilitmavv_id">
							
						</div>
					</div>
					
			</div>
		</fieldset>

		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">
		<legend><b><fmt:message key="label.responsabili_tipimovimento_scadenzario_esclude" /></b></legend>
		<br class="clear" />	
		<div>				
				<div>
					<span><fmt:message key="label.tipomovimento" /></span>
					<span>
					
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="responsabile_tm_sca_esclude_id" />		
							<jsp:param name="propertyPath" value="responsabile.tipimovimentoSca" />				
							<jsp:param name="pathPropertyDescription" value="responsabile.tipimovimentoSca.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="responsabile.tipimovimentoSca.id.tipomovimento" />
							<jsp:param name="autocompleterAjax" value="findTipiMovPerResponsabile.htm?tipo=sca&codiceResponsabile=${responsabile.entity.id.codice}" />							
							<jsp:param name="titleKey" value="label.ricerca_tipimovimento" />
						</jsp:include>
						
					</span>
					<!-- La sezione seguente viene visualizzata solo se il tipomovimento inserito nella barra di ricerca è già presente nella tabella RESPONSABILI_TM_SCA.
						 Il messaggio di errore viene visualizzato tramite il metodo mostraErroreSePresenteTipoMov() -->
					<div id="messaggioErrore_sca_esclude" class="error_header" style="display: none;">
					</div>
					<div>
					<a class="addColumn" style="float: none;" href="javascript:aggiungiResptm('${responsabile.entity.id.codice}','responsabile_tm_sca_esclude_id','sca_esclude')" title="<fmt:message key="label.aggiungi" />">
					    <label><fmt:message key="label.add.record.image" /></label>
					</a>
					</div>
				</div>
				<br class="clear"/>
				<div style="border: #c0c0c0 1px dashed; padding: 4px;">
					<div id="responsabilitmsca_esclude_id">
				
					</div>
				</div>
		</div>
		</fieldset>
		
		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">	
		<br class="clear" />			
		<legend><b><fmt:message key="label.responsabili_tipimovimento_avvisi_esclude" /></b></legend>
		<br class="clear" />	
			<div>				
					<div>
						<span><fmt:message key="label.tipomovimento" /></span>
						<span>
						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="responsabile_tm_avv_esclude_id" />		
							<jsp:param name="propertyPath" value="responsabile.tipimovimentoAvv" />				
							<jsp:param name="pathPropertyDescription" value="responsabile.tipimovimentoAvv.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="responsabile.tipimovimentoAvv.id.tipomovimento" />
							<jsp:param name="autocompleterAjax" value="findTipiMovPerResponsabile.htm?tipo=avv&codiceResponsabile=${responsabile.entity.id.codice}" />							
							<jsp:param name="titleKey" value="label.ricerca_tipimovimento" />
						</jsp:include>
						
						</span>
					</div>
					<!-- La sezione seguente viene visualizzata solo se il tipomovimento inserito nella barra di ricerca è già presente nella tabella RESPONSABILI_TM_AVV.
					     Il messaggio di errore viene visualizzato tramite il metodo mostraErroreSePresenteTipoMov() -->
					<div id="messaggioErrore_avv_esclude" class="error_header" style="display: none;">
					</div>
					<div>
						<a class="addColumn" style="float: none;" href="javascript:aggiungiResptm('${responsabile.entity.id.codice}','responsabile_tm_avv_esclude_id','avv_esclude')" title="<fmt:message key="label.aggiungi" />">
						    <label><fmt:message key="label.add.record.image" /></label>
						</a>
					</div>
					<br class="clear"/>
					<div style="border: #c0c0c0 1px dashed; padding: 4px;">
						<div id="responsabilitmavv_esclude_id">
							
						</div>
					</div>
					
			</div>
		</fieldset>
		
		<c:if test="${ isBatchScadenzarioPage eq true }">	
		<br class="clear" />		
		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">	
					
		<legend><b><fmt:message key="label.visualizza_sezioni_scadenzario" /></b></legend>		
				<fmt:message key="label.visualizza_sezioni_scadenzario.help" />
			<table>				
			<tr>
						<td><fmt:message key="label.movimenti_da_effettuare" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE %>" />
								<jsp:param name="idElement" value="solo_movimenti_da_effettuare_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>						
						<td><fmt:message key="label.movimenti_da_visionare" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE %>" />
								<jsp:param name="idElement" value="solo_movimenti_da_visionare_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>						
						<td><fmt:message key="label.movimenti_non_notificati" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI %>" />
								<jsp:param name="idElement" value="solo_movimenti_non_notificati_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>						
						<td><fmt:message key="label.richieste_frontoffice" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO %>" />
								<jsp:param name="idElement" value="solo_richieste_fe_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>		
						<td><fmt:message key="label.eventi_non_letti" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI %>" />
								<jsp:param name="idElement" value="solo_eventi_non_letti_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>		
						<td><fmt:message key="label.nuove_istanze_stc" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC %>" />
								<jsp:param name="idElement" value="solo_istanze_da_stc_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>		
						<td><fmt:message key="label.istanze_stc_non_importate" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NON_IMPORTATE %>" />
								<jsp:param name="idElement" value="solo_istanze_da_stc_non_importate_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>		
						<td><fmt:message key="label.eventi_sistema" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_SISTEMA %>" />
								<jsp:param name="idElement" value="solo_eventi_sistema_id" />
							</jsp:include>
						</td>
					</tr>
					<tr>		
						<td><fmt:message key="label.documenti_da_firmare" /></td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE %>" />
								<jsp:param name="idElement" value="solo_eventi_sistema_id" />
							</jsp:include>
						</td>
					</tr>		
					<tr>		
						<td><fmt:message key="label.scadenzario_oneri"/></td>
						<td>						
							<jsp:include page="../includes/checkboxConfUtente.jsp">  
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_SCADENZARIO_ONERI %>" />
								<jsp:param name="idElement" value="solo_scadenzario_oneri" />
							</jsp:include>
						</td>
					</tr>

			</table>
		</fieldset>	
		</c:if>		
		
		
		<br class="clear" />		
		<fieldset id="scadenzarioDiv" style="width: 80%;${showDiv}">					
		<legend><b><fmt:message key="label.visualizza_colonne_scadenzario" /></b></legend>	
		<fmt:message key="label.visualizza_colonne_scadenzario.help" />		 	 
	 	 	<br class="clear" />
			<!--  TABELLA PER LA GESTIONE DELLE COLONNE DA VISUALIZZARE SULLO SCADENZARIO -->
		    <div class="jmesa">
				<table border="1"  cellpadding="0" cellspacing="0" class="vbg-table">
					<thead>
						<tr class="header">
							<th><fmt:message key="label.colonne_visualizzabili"/></th>
							<th><fmt:message key="label.colonne_visualizzare"/>	</th>
			            </tr>
					</thead>
					<tbody class="tbody">
					<tr>
					    
				 		<td>
							 <div id="catalog">
							    <div class="ui-widget-content">
							      <ul>							         
								      <c:forEach items="${configurazioneUtenteHelpers}" var="configurazioneUtente" varStatus="a">
								      <c:if test="${configurazioneUtente.valore eq 0 }">
								       	<li  data-valore='${configurazioneUtente.valore}'>${configurazioneUtente.nomeparametro}</li>
								      </c:if>
								      </c:forEach>
							      </ul>
							    </div>
							  </div>
				 		</td>	 		
				 		 
			 		     <td >		
							<div id="cart">
				  				<div class="ui-widget-content">
					   				 <c:if test="${!isVisualizzaAlemnoUnaColonna}">
					   				 <ul>
					      				<li class="placeholder"><fmt:message key="label.aggiungi_colonna"/>	</li>
					    			 </ul>
					    			 </c:if>
					    			 <ul>
					    			 <c:forEach items="${configurazioneUtenteHelpers}" var="configurazioneUtente">
								      <c:if test="${configurazioneUtente.valore eq 1 }">
								     	<li>${configurazioneUtente.nomeparametro}</li> 
								      </c:if>
								     </c:forEach>
								     </ul>
				  				</div>
							</div>
						</td>
						
					</tr>
					</tbody>
				</table> 
		</div>
		</fieldset>
		
		<script type="text/javascript">
		
		
			function checkScadenzario(nomeparametro, valore) {
				
				javascript:saveUserPreference(nomeparametro,valore);
			}		

			jQuery(function() {
				jQuery("#catalog").accordion();
				jQuery("#catalog li").draggable({
					appendTo : "body",
					helper : "clone"
				});

				jQuery("#cart ul").droppable(
								{
									activeClass : "ui-state-default",
									hoverClass : "ui-state-hover",
									accept : ":not(.ui-sortable-helper)",
									drop : function(event, ui) {

										console.log({
											testo : ui.draggable.text(),
											valore : ui.draggable
													.data('valore')
										});
										var value = ui.draggable.data('valore') == '0' ? '1' : '0';
										var jhqrPr = jQuery.ajax({
											url : 'salvaPreferenza.htm',
											context : document.body,
											cache : false,
											dataType : "html",
											method : 'GET',
											data : {
												nomeparametro : ui.draggable
														.text(),
												valore : value
											}
										});
										jQuery(this).find(".placeholder").remove();
										jQuery(ui.draggable).remove();

										jQuery("<li></li>").text(ui.draggable.text()).appendTo(this);
										jQuery("#cart li").draggable();
										jQuery('data-valore').val(0);

										//jQuery( "#cart li" ).val('draggable.data').appendTo( 1 );

									}
								}).sortable({
							items : "li:not(.placeholder)",
							sort : function() {
								// gets added unintentionally by droppable interacting with sortable
								// using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
								jQuery(this).removeClass("ui-state-default");
							}
						});
			});

			jQuery(function() {
				jQuery("#cart").accordion();
				jQuery("#cart li").draggable({
					appendTo : "body",
					helper : "clone"
				});

				jQuery("#catalog ul ")
						.droppable(
								{

									activeClass : "ui-state-default",
									hoverClass : "ui-state-hover",
									accept : ":not(.ui-sortable-helper)",
									drop : function(event, ui) {

										console.log({
											testo : ui.draggable.text(),
											valore : ui.draggable
													.data('valore')
										});

										var value = ui.draggable.data('valore') == '0' ? '1'
												: '0';
										var jhqrPr = jQuery.ajax({
											url : 'salvaPreferenza.htm',
											context : document.body,
											cache : false,
											dataType : "html",
											method : 'GET',
											data : {
												nomeparametro : ui.draggable
														.text(),
												valore : value
											}
										});
										//jQuery( this ).find( ".placeholder" ).appendTo( '<fmt:message key="label.aggiungi_colonna"/>' );
										jQuery(ui.draggable).remove();
										jQuery("<li></li>").text(
												ui.draggable.text()).appendTo(
												this);
										jQuery("#catalog li").draggable();
										jQuery('#catalog li').attr(
												"data-valore", "0");

									}
								}).sortable({
							items : "li:not(.placeholder)",
							sort : function() {
								// gets added unintentionally by droppable interacting with sortable
								// using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
								jQuery(this).removeClass("ui-state-default");
							}
						});
			});
			
		

			function modificaPaginaCentrale(obj) {
				saveUserPreference('<%=WebConstants.CONF_UTENTE_PAGINA_CENTRALE%>',obj.value);	
			}
		
		/*
			Mostra il messaggio di testo contenuto nel parametro transport.
			Tale messaggio viene restituito dal server tramite chiamata AJAX al metodo aggiungiResptm.
		*/
		function mostraErroreSePresenteTipoMov(tipo, transport){
			console.log('mostra errore se presente tipo: ' + tipo);
			console.log('mostra errore se presente transport text: ' + transport);
			jQuery('#messaggioErrore_'+tipo).html(transport);
			jQuery('#messaggioErrore_'+tipo).show();
		}
		
		function nascondiMessaggioErrore(tipo){
			jQuery("#messaggioErrore_" + tipo).hide();
		}
		
		/*
			Verifica il risultato, contenuto nel parametro data, ottenuto dalla chiamata AJAX aggiungiResptm.
			Il parametro tipo può avere uno di questi valori: sca, avv, sca_esclude o avv_esclude; ed è a sua volta
			parametro del parametro callback: la funzione da chiamare nel caso in cui è avvenuto l'inserimento.
		*/
		function verificaErroreoSuccesso(data, tipo, callback){
			
			// recupero il messaggio di testo (la stringa) restituito dal server
			// recupero l'array dal messaggio di testo
			if (data.responseText != '') {
				var parts = data.responseText.split('|');
				if (parts[0] == 'IS_PRESENT') { // IS_PRESENT: indica che il tipomovimento inserito nell'input è già stato inserito nel db.
					console.log("Transport after insert " + parts[1]);
					mostraErroreSePresenteTipoMov(tipo, parts[1]);
				}
			} else {
				callback(tipo);
			}
		}
		
		function mostraDettagliotm(tipo){
			
			new Ajax.Request('${pageContext.request.contextPath}/ajax/dettaglioRespTipimov.htm?tipo='+tipo+'&codiceResponsabile=${responsabile.entity.id.codice}', {
				method: 'post',	
				onSuccess: function(transport){						
				  $("responsabilitm"+tipo+"_id").innerHTML = transport.responseText;
				  $("responsabilitm"+tipo+"_id").style.display='';
				  applyStyle();			  
 				},
 				onFailure: function(transport){ 
 				  $("responsabilitm"+tipo+"_id").innerHTML = transport.responseText;
 				  $("responsabilitm"+tipo+"_id").style.display='';     				      				  
 				 }						    		 
 			});
		} 
		function aggiungiResptm(codiceresponsabile, idelem, tipo){
			var ts_ = new Date().getTime();
			var tipomov = $(idelem+'_hidden').value;
			// nasconde l'eventuale messaggio d'errore se presente 
			nascondiMessaggioErrore(tipo);
			if(tipomov){
				if(tipomov!=''){
					new Ajax.Request('${pageContext.request.contextPath}/ajax/insertRespTipimov.htm?ts_='+ts_+'&tipomovimento='+tipomov+'&tipo='+tipo+'&codiceResponsabile=${responsabile.entity.id.codice}', {
						method: 'post',	
						onSuccess: function(transport){	
							verificaErroreoSuccesso(transport, tipo, mostraDettagliotm);
							//mostraDettagliotm(tipo) ;   				  
		 				},
		 				onFailure: function(transport){
		 				  $("responsabilitm"+tipo+"_id").innerHTML = transport.responseText;
		 				  $("responsabilitm"+tipo+"_id").style.display='';     				      				  
		 				}						    		 
		 			});
				}
			}
		}
		
		
		function eliminaResptm(codiceresponsabile, idtipomov, tipo){
			var ts_ = new Date().getTime();
			new Ajax.Request('${pageContext.request.contextPath}/ajax/deleteRespTipimov.htm?ts_='+ts_+'&tipomovimento='+idtipomov+'&tipo='+tipo+'&codiceResponsabile=${responsabile.entity.id.codice}', {
				method: 'post',	
				onSuccess: function(transport){						
					 mostraDettagliotm(tipo) ;   				  
 				},
 				onFailure: function(transport){ 
 				  $("responsabilitm"+tipo+"_id").innerHTML = transport.responseText;
 				  $("responsabilitm"+tipo+"_id").style.display='';     				      				  
 				 }						    		 
 			});
		}
		
		mostraDettagliotm('avv');
		mostraDettagliotm('sca');
		mostraDettagliotm('avv_esclude');
		mostraDettagliotm('sca_esclude');
		
		</script>
		
		</div>
	</div>
	<div class="form-button">
		<a class="btn btn-primary" href="javascript:doSubmit('saveParametriscadenzario.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
		<a class="btn btn-secondary" href="javascript:historyBack('');"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>