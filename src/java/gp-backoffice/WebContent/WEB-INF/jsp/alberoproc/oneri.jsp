<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${alberoprocOneri.id.codice==null}">
			<fmt:message key="alberoproc.label.nuovo_alberoprocOneri.title" />
		</c:if> 
		<c:if test="${alberoprocOneri.id.codice!=null}">
			<fmt:message key="alberoproc.label.dettaglio_alberoprocOneri.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${alberoprocOneri.id.codice==null}">
			<fmt:message key="alberoproc.label.nuovo_alberoprocOneri.title" />
		</c:if> 
		<c:if test="${alberoprocOneri.id.codice!=null}">
			<fmt:message key="alberoproc.label.dettaglio_alberoprocOneri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
		<jsp:param name="commandName" value="alberoprocOneri" />
	</jsp:include>
	<div id="subcontent">
    <div class="clear"></div>
    	
    	<spring-form:form commandName="alberoprocOneri" name="inviodati">
	    	<div class="vbg-form">
	    		<fieldset>
	    			<legend>${alberoproc.vwAlberoproc.scDescrizionepadre} - ${alberoproc.vwAlberoproc.scDescrizionebreve}</legend>
	    		
		    		<div class="form-group">
		    			<label><fmt:message key="alberoproc.label.alberoprocOneri_causali" /></label>
		    			<jsp:include page="../includes/autocompletergenericoTT.jsp" >
							<jsp:param name="idElemento" value="tipicausalioneri" />		
							<jsp:param name="propertyPath" value="tipicausalioneri" />				
							<jsp:param name="pathPropertyDescription" value="tipicausalioneri.coDescrizione" />
							<jsp:param name="pathPropertyCode" value="tipicausalioneri.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipicausalioneriFilterByFlagEndo.htm?flagEndo=false&codicesoftware=" />	
							<jsp:param name="titleKey" value="label.ricerca_tipicausalioneri" />
							<jsp:param name="id_help" value="help_tipicausalioneri" />
							<jsp:param name="afterUpdateElement" value="isImportoIstruttoriaImpostabile" />						
						</jsp:include>
		    		</div>
		    		<div class="form-group" id="aoImportocausale_field">
		    			<label>
							<fmt:message key="alberoproc.label.alberoprocOneri_aoImportocausale" />
						</label>
						<spring-form:input id="aoImportocausale_id" cssStyle="text-align: right;" path="aoImportocausale" size="11" maxlength="11" onchange="changeValue(this);" />
						<spring-form:errors path="aoImportocausale" cssClass="error"/>
		    		</div>
		    		<div class="form-group" id="tr_aoImportoistruttoria">
		    			<c:set var="displayImportoistruttoria" value="display: none;" />
						<c:if test="${isImportoIstruttoriaImpostabile eq true}">
							<c:set var="displayImportoistruttoria" value="" />
						</c:if>
						<label style="${displayImportoistruttoria}">
							<fmt:message key="alberoproc.label.alberoprocOneri_aoImportoistruttoria" />
						</label>
						<spring-form:input id="aoImportoistruttoria_id" cssStyle="text-align: right;${displayImportoistruttoria}"  path="aoImportoistruttoria" size="11" maxlength="11" onchange="changeValue(this);" />
						<spring-form:errors path="aoImportoistruttoria" cssClass="error"/>
		
		    		</div>
		    		<div class="form-group">
		    			<label>
						<fmt:message key="inventarioprocedimenti.label.flag_importo_libero" />
						</label>
						<spring-form:checkbox id="flagImportoLibero_id" path="flagImportoLibero"/>
						<init:help idHelp="flagImportoLibero_help" textKey="inventarioprocedimenti.help.inventarioprocedimentioneri_flag_importo_libero"/>
						<spring-form:errors path="flagImportoLibero" cssClass="error"/>

		    		
		    		</div>
		    		<div class="form-group">
		    			<label>
						<fmt:message key="alberoproc.label.alberoprocOneri_aoSerichiesto" />
						</label>
						<spring-form:checkbox id="aoSerichiesto_id" path="aoSerichiesto"/>
						<init:help idHelp="aoSerichiesto_help" textKey="alberoproc.help.alberoprocOneri_aoSerichiesto"/>
						<spring-form:errors path="aoSerichiesto" cssClass="error"/>

		    		
		    		</div>
		    		<div class="form-group">
		    			<label>
						<fmt:message key="label.note" />
						</label>
						<spring-form:textarea id="note_id" path="note" cols="60" rows="4"/>
						<init:help idHelp="alberoproconeri_help" textKey="alberoproc.help.alberoprocOneri_note"/>
						<spring-form:errors path="note" cssClass="error"/>
		    		</div>
		    		</fieldset>
		    		<fieldset>
		    			<legend><fmt:message key="label.parametri_frontoffice" /></legend>
			    		<div class="form-group">
			    			
			    			<label>
								<fmt:message key="label.modello" />
							</label>
						
							<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="dyn2Modellit" />		
								<jsp:param name="propertyPath" value="dyn2Modellit" />				
								<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
								<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
								<jsp:param name="id_help" value="help_modello" />	
								<jsp:param name="help" value="help.modelli_archivi_base" />
							</jsp:include>
			    			
		    		
		    		</div>
		    		
		    		<div class="form-group">
		    			<label>
							<fmt:message key="label.campo" />
						</label>
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="dyn2Campi" />		
						<jsp:param name="propertyPath" value="dyn2Campi" />				
						<jsp:param name="pathPropertyDescription" value="dyn2Campi.nomecampo" />
						<jsp:param name="pathPropertyCode" value="dyn2Campi.id.codice" />
						<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
						<jsp:param name="id_help" value="help_campo" />	
					</jsp:include>
					<div class="input-help"><fmt:message key="label.campo_dinamico_oneri.help" /></div>
		    		</div>
		    		</fieldset>
	    		
	    	</div>
    	</spring-form:form>
    	<script type="text/javascript">
    	
    	vbg.ready(() => {
    		if (document.getElementById('flagImportoLibero_id').checked) {
    			document.getElementById('aoImportocausale_id').value = ''; 
				document.getElementById('aoImportocausale_field').style.display='none';
			} else {
				document.getElementById('aoImportocausale_field').style.display='';

			}
    		
    		
    		document.getElementById('flagImportoLibero_id').addEventListener('click',(e)=>{
    			console.log(e.target.checked)
    			if (e.target.checked) {
					document.getElementById('aoImportocausale_id').value = ''; 
					document.getElementById('aoImportocausale_field').style.display='none';
				} else {
					document.getElementById('aoImportocausale_field').style.display='';
				}
    		});
    		
    	});
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
	    	function changeValue(obj){
				var importo=obj.value;
				if(isNaN(importo.replace(",","."))){
					alert('<fmt:message key="alert.field.numeric" />');
					obj.value = '';
					return;
				}
				obj.value = importo.replace(".",",");
			}
    	
    	</script>
	</div>
	<div class="form-button">
		
			<c:if test="${alberoprocOneri.id.codice==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insertOneri.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${alberoprocOneri.id.codice!=null}">
				<a class="btn btn-primary" href="javascript:doSubmit('updateOneri.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('deleteOneri.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('listOneri.htm?alberoproc.id.codice='+${alberoproc.id.codice},'')"><fmt:message key="button.back" /></a>
		</ul>
	</div>
</body>
</html>