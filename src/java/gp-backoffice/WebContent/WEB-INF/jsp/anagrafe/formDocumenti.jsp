<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${anagrafe.anagrafedocumenti.id.codice==null}">
			<fmt:message key="label.nuovo_documento_anagrafe" />
		</c:if> 
		<c:if test="${anagrafe.anagrafedocumenti.id.codice!=null}">
			<fmt:message key="label.dettaglio_documento_anagrafe" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${anagrafe.anagrafedocumenti.id.codice==null}">
			<fmt:message key="label.nuovo_documento_anagrafe" />
		</c:if> 
		<c:if test="${anagrafe.anagrafedocumenti.id.codice!=null}">
			<fmt:message key="label.dettaglio_documento_anagrafe" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="anagrafe" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="anagrafe" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.tipo_documento" />
					</td>
					<td>
						<script type="text/javascript">
						function tipodocumentoCallback(inputField,listItem){
							var a = listItem.id;
							document.getElementById('tipodocumento_id').value = inputField.value;
							document.getElementById('tipodocumento_hidden').value = a;
							$('tipodocumento_id_choices').fade();	
							calcolaFineValidita(document.getElementById('datainiziovalidita_id'), document.getElementById('tipodocumento_hidden'));
						}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipodocumento" />		
							<jsp:param name="propertyPath" value="anagrafedocumenti.tipidocumento" />				
							<jsp:param name="pathPropertyDescription" value="anagrafedocumenti.tipidocumento.documento" />
							<jsp:param name="pathPropertyCode" value="anagrafedocumenti.tipidocumento.id.codice" />
							<jsp:param name="autocompleterAjax" value="findtipodocumento.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipodocumenti" />
							<jsp:param name="afterUpdateElement" value="tipodocumentoCallback" />
						</jsp:include>						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.riferimento_documento" />
					</td>
					<td>
						<spring-form:input id="rifdocumento_id" path="anagrafedocumenti.rifdocumento" size="70" />
						<spring-form:errors path="anagrafedocumenti.rifdocumento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.data_registrazione_richiesta" />
	       			</td>
	       			<td>
						<spring-form:input id="dataregistrazione_id" path="anagrafedocumenti.dataregistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>
					    <spring-form:errors path="anagrafedocumenti.dataregistrazione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.data_inizio_validita" />
	       			</td>
	       			<td>
						<spring-form:input id="datainiziovalidita_id" path="anagrafedocumenti.datainiziovalidita" size="10" maxlength="10" onblur="isValidDate(this,true);" onchange="isValidDate(this,true);calcolaFineValidita(this, document.getElementById('tipodocumento_hidden'))"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatainiziovalidita" idInput="datainiziovalidita_id" textKey="label.calendar"/>
					    <spring-form:errors path="anagrafedocumenti.datainiziovalidita" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.data_fine_validita" />
	       			</td>
	       			<td>
						<spring-form:input id="datafinevalidita_id" path="anagrafedocumenti.datafinevalidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafinevalidita" idInput="datafinevalidita_id" textKey="label.calendar"/>
					    <spring-form:errors path="anagrafedocumenti.datafinevalidita" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.documento" /></td>
					<td><jsp:include page="../includes/oggetti.jsp">
					<jsp:param name="idElemento" value="oggettoIdCodice" />
					<jsp:param name="codiceOggetto" value="${anagrafe.anagrafedocumenti.oggetto.id.codice}" />
					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
					<jsp:param name="nomefileId" value="oggetto_nomefile" />					
					</jsp:include> 
					<spring-form:hidden path="anagrafedocumenti.oggetto.id.codice" id="oggetto_id_codice" />
					<spring-form:hidden path="anagrafedocumenti.oggetto.nomefile" id="oggetto_nomefile" />
					<spring-form:errors path="anagrafedocumenti.oggetto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
	       			</td>
	       			<td>
						<spring-form:textarea id="dataregistrazione_id" path="anagrafedocumenti.annotazioni" cols="40" rows="5"/>
					    <spring-form:errors path="anagrafedocumenti.annotazioni" cssClass="error"/>
					</td>
				</tr>
				
			</table>
			<script type='text/javascript'>
				$('rifdocumento_id').focus();
				function calcolaFineValidita(obj, elTipoDocumento){
					if(obj){
						if(obj.value!=''){
							if(elTipoDocumento){
								if(elTipoDocumento.value!=''){																
								var jqxhr = jQuery.ajax({
									  url: "ajaxCalcolaFineValidita.htm",
									  context: document.body,
									  cache: false,				
									  dataType: "html",
									  data: "tipoDocumento="+elTipoDocumento.value+'&dataInizio='+obj.value,
									  success: function(dataResult) { 
										   $('datafinevalidita_id').value = dataResult;
										},
									  error: function(dataError){
										  alert(dataError.innerText);
									  }	
									});								
								}	
							}
						}
					}					
					
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${anagrafe.anagrafedocumenti.id.codice==null}">
				<li><a href="javascript:doSubmit('insertDocumenti.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${anagrafe.anagrafedocumenti.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateDocumenti.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteDocumenti.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>