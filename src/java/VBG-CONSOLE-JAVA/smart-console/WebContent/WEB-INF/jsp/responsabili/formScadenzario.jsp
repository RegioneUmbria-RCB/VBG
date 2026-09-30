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
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.parametri_scadenzario_avvio" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
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
			<table>				
				<tr>
					<td><fmt:message key="responsabili.label.scadenzario" /></td>
					<td><spring-form:checkbox tabindex="21" id="scadenzario_id" path="entity.scadenzario" onclick="showHideElements('scadenzarioDiv', 'table');showHideElements('scadenzarioDiv', 'fieldset')" />
					<fmt:message key="responsabili.help.scadenzario" />
					<spring-form:errors path="entity.scadenzario" cssClass="error" /></td>
				</tr>
			</table>
			
			<c:if test="${ empty isBatchScadenzarioPage }">	
	
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

			<table id="scadenzarioDiv" cellpadding="2" cellspacing="0" border="1px" style="width: 80%;${showDiv}">				
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
						<td>Documenti da firmare</td>
						<td>
							<jsp:include page="../includes/checkboxConfUtente.jsp">
								<jsp:param name="idConfUtente" value="<%= WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE %>" />
								<jsp:param name="idElement" value="solo_eventi_sistema_id" />
							</jsp:include>
						</td>
					</tr>		

			</table>
		</fieldset>	
		</c:if>
		
		<script type="text/javascript">
		
		function modificaPaginaCentrale(obj){
			saveUserPreference('<%=WebConstants.CONF_UTENTE_PAGINA_CENTRALE%>',obj.value);	
		}
		
		function mostraDettagliotm(tipo){
			
			new Ajax.Request('${pageContext.request.contextPath}/ajax/dettaglioRespTipimov.htm?tipo='+tipo+'&codiceResponsabile=${responsabile.entity.id.codice}', {
				method: 'post',	
				onSuccess: function(transport){						
				  $("responsabilitm"+tipo+"_id").innerHTML = transport.responseText;
				  $("responsabilitm"+tipo+"_id").style.display='';     				  
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
			if(tipomov){
				if(tipomov!=''){
					new Ajax.Request('${pageContext.request.contextPath}/ajax/insertRespTipimov.htm?ts_='+ts_+'&tipomovimento='+tipomov+'&tipo='+tipo+'&codiceResponsabile=${responsabile.entity.id.codice}', {
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
		
		</script>
		
		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('saveParametriscadenzario.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>