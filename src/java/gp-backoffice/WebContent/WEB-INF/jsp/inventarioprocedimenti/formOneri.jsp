<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice==null}">
				<fmt:message key="inventarioprocedimenti.label.nuovo_inventarioprocedimentioneri.title" />
			</c:if> 
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice!=null}">
				<fmt:message key="inventarioprocedimenti.label.dettaglio_inventarioprocedimentioneri.title" />
			</c:if>
		</title>
	</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice==null}">
				<fmt:message key="inventarioprocedimenti.label.nuovo_inventarioprocedimentioneri.title" />
			</c:if> 
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice!=null}">
				<fmt:message key="inventarioprocedimenti.label.dettaglio_inventarioprocedimentioneri.title" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
				<div class="parametriDiv">
			   		<div class="etichetta">
						<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
					</div>
					<div class="parametro">
						<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
				 	</div>
				</div>
				<br class="clear"/>
				<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
					<jsp:include page="../includes/displayGlobalMessages.jsp" >
				        <jsp:param name="commandName" value="inventarioprocedimenti" />
				    </jsp:include>
				    <fieldset>
				    	<legend><fmt:message key="label.parametri" /></legend>
				    	<div class="form-group">
							<label><fmt:message key="inventarioprocedimenti.label.tipicausalioneri" /></label>
							<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="tipicausalioneri" />		
								<jsp:param name="propertyPath" value="inventarioprocedimentioneri.tipicausalioneri" />				
								<jsp:param name="pathPropertyDescription" value="inventarioprocedimentioneri.tipicausalioneri.coDescrizione" />
								<jsp:param name="pathPropertyCode" value="inventarioprocedimentioneri.tipicausalioneri.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipicausalioneriForInventarioprocedimentioneri.htm?codice=" />	
								<jsp:param name="titleKey" value="label.ricerca_tipicausalioneri" />
								<jsp:param name="id_help" value="help_tipicausalioneri" />
								<jsp:param name="afterUpdateElement" value="isImportoIstruttoriaImpostabile" />						
							</jsp:include>
						</div>	
												
					    <div class="form-group" id="importo_causale" style="${isImporto}">
							<label><fmt:message key="inventarioprocedimenti.label.importo_causale" /></label>
							<spring-form:input id="importo_causale_id" path="inventarioprocedimentioneri.importo" cssStyle="text-align:right;" size="8" onblur="checkNumberValue(this);" />
							<spring-form:errors path="inventarioprocedimentioneri.importo" cssClass="error"/>							
						</div>
						<c:set var="displayImportoistruttoria" value="display: none;" />
						<c:if test="${isImportoIstruttoriaImpostabile eq true}">
							<c:set var="displayImportoistruttoria" value="" />
						</c:if>		
						<div class="form-group">
							<label><fmt:message key="inventarioprocedimenti.label.flag_importo_libero"/></label>
							<spring-form:checkbox path="inventarioprocedimentioneri.flagImportoLibero" id="flagImportoLibero"/>
							<init:help idHelp="inventarioprocedimenti_help" textKey="inventarioprocedimenti.help.inventarioprocedimentioneri_flag_importo_libero"/>
							<spring-form:errors path="inventarioprocedimentioneri.flagImportoLibero" cssClass="error"/> 
						</div>
						<div class="form-group" id="tr_aoImportoistruttoria" style="${displayImportoistruttoria}">
							<label><fmt:message key="inventarioprocedimenti.label.importo_istruttoria" /></label>
							<spring-form:input id="importo_istruttoria_id" path="inventarioprocedimentioneri.importoistruttoria" cssStyle="text-align:right;" size="8" onblur="checkNumberValue(this);" />
							<spring-form:errors path="inventarioprocedimentioneri.importoistruttoria" cssClass="error"/>
						</div>
						<div class="form-group">
							<label><fmt:message key="inventarioprocedimenti.label.flag_pagato" /></label>
							<spring-form:checkbox path="inventarioprocedimentioneri.flagPagato"/>
							<spring-form:errors path="inventarioprocedimentioneri.flagPagato" cssClass="error"/> 
						</div>
						<div class="form-group">
							<label><fmt:message key="label.note" /></label>
							<spring-form:textarea id="note_id" path="inventarioprocedimentioneri.note" cols="60" rows="4"/>
							<init:help idHelp="inventarioprocedimenti_help" textKey="inventarioprocedimenti.help.inventarioprocedimentioneri_note"/>
							<spring-form:errors path="inventarioprocedimentioneri.note" cssClass="error"/>
						</div>
				    </fieldset>
										<%-- sezione per front  --%>
				    <fieldset>
				    	<legend><fmt:message key="label.parametri_frontoffice" /></legend>
				    	<div class="form-group">
							<label><fmt:message key="label.modello" /></label>
							<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="dyn2Modellit" />		
								<jsp:param name="propertyPath" value="inventarioprocedimentioneri.dyn2Modellit" />				
								<jsp:param name="pathPropertyDescription" value="inventarioprocedimentioneri.dyn2Modellit.descrizione" />
								<jsp:param name="pathPropertyCode" value="inventarioprocedimentioneri.dyn2Modellit.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
								<jsp:param name="id_help" value="help_modello" />	
								<jsp:param name="help" value="help.modelli_archivi_base" />
							</jsp:include>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.campo" /></label>
							<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="dyn2Campi" />		
								<jsp:param name="propertyPath" value="inventarioprocedimentioneri.dyn2Campi" />				
								<jsp:param name="pathPropertyDescription" value="inventarioprocedimentioneri.dyn2Campi.nomecampo" />
								<jsp:param name="pathPropertyCode" value="inventarioprocedimentioneri.dyn2Campi.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
								<jsp:param name="id_help" value="help_campo" />	
							</jsp:include>
							<div class="input-help"><fmt:message key="label.campo_dinamico_oneri.help" /></div>
							
						</div>
				    </fieldset>
					<script type='text/javascript'>
						function isImportoIstruttoriaImpostabile(inputField,listItem) {
							var a = listItem.id;
							var arrayValori=a.split('#');	
							document.getElementById('tipicausalioneri_id1').value = inputField.value;
							document.getElementById('tipicausalioneri_hidden').value = arrayValori[0];
							if(arrayValori[1]=='true'){
								document.getElementById('tr_aoImportoistruttoria').show();
							}else{
								document.getElementById('tr_aoImportoistruttoria').hide();
							}							
						}
					
						function filterModello(element, entry) {
							return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
						}
					
						function setHiddenField(inputField,listItem){
							var a = listItem.id;
							//document.getElementById('dyn2Campi_id').value = inputField.value;
							document.getElementById('dyn2Campi_hidden').value = a;
						}
						
						let flagImportoLibero = document.querySelector('#flagImportoLibero');
						
						
						flagImportoLibero.addEventListener('click', (ev) =>{
							console.log(flagImportoLibero.checked);
							let isImporto = false; 
							if(flagImportoLibero.checked){
								document.querySelector('#importo_causale_id').value = '';
								document.querySelector('#importo_causale').hide();
								isImporto = true;								
							}else{
								document.querySelector('#importo_causale').show();
								isImporto = false;
							}
						});
						vbg.ready(() => {
							if(document.querySelector('#flagImportoLibero').checked){
								document.querySelector('#importo_causale').hide();
							}else{
								document.querySelector('#importo_causale').show();
							}
							
						});
					</script>	
				</spring-form:form>
		</div>
	</div>
		<div class="form-button">		
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insertOneri.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice!=null}">
				<a class="btn btn-primary" href="javascript:doSubmit('updateOneri.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('deleteOneri.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('listoneri.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a>		
		</div>
	</body>
</html>