<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
    </div>
    <div class="clear"></div>
		<spring-form:form commandName="alberoprocOneri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocOneri" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocOneri_causali" />
					</td>
					<td>
					<script type="text/javascript">
						function isImportoIstruttoriaImpostabile(inputField,listItem) {
							var a = listItem.id;
							var arrayValori=a.split('#');	
							document.getElementById('tipicausalioneri_id1').value = inputField.value;
							document.getElementById('tipicausalioneri_hidden').value = arrayValori[0];
							if(arrayValori[1]=='true'){
								$('tr_aoImportoistruttoria').appear();
							}else{
								$('tr_aoImportoistruttoria').fade();
							}							
						}
					</script>
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
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocOneri_aoImportocausale" />
					</td>
					<td>
						<spring-form:input id="aoImportocausale_id" cssStyle="text-align: right;" path="aoImportocausale" size="11" maxlength="11" onchange="changeValue(this);" />
						<spring-form:errors path="aoImportocausale" cssClass="error"/>
					</td>
				</tr>
				<c:set var="displayImportoistruttoria" value="display: none;" />
				<c:if test="${isImportoIstruttoriaImpostabile eq true}">
					<c:set var="displayImportoistruttoria" value="" />
				</c:if>
				<tr id="tr_aoImportoistruttoria" style="${displayImportoistruttoria}">
					<td>
						<fmt:message key="alberoproc.label.alberoprocOneri_aoImportoistruttoria" />
					</td>
					<td>
						<spring-form:input id="aoImportoistruttoria_id" cssStyle="text-align: right;"  path="aoImportoistruttoria" size="11" maxlength="11" onchange="changeValue(this);" />
						<spring-form:errors path="aoImportoistruttoria" cssClass="error"/>
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocOneri_aoSerichiesto" />
					</td>
					<td>
						<spring-form:checkbox id="aoSerichiesto_id" path="aoSerichiesto"/>
						<init:help idHelp="aoSerichiesto_help" textKey="alberoproc.help.alberoprocOneri_aoSerichiesto"/>
						<spring-form:errors path="aoSerichiesto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="note" cols="60" rows="4"/>
						<init:help idHelp="alberoproconeri_help" textKey="alberoproc.help.alberoprocOneri_note"/>
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
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
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${alberoprocOneri.id.codice==null}">
				<li><a href="javascript:doSubmit('insertOneri.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${alberoprocOneri.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateOneri.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteOneri.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listOneri.htm?alberoproc.id.codice='+${alberoproc.id.codice},'')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>