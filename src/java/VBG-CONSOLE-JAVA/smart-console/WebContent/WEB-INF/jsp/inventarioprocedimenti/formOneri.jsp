<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.tipicausalioneri" />
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
						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipicausalioneri" />		
							<jsp:param name="propertyPath" value="inventarioprocedimentioneri.tipicausalioneri" />				
							<jsp:param name="pathPropertyDescription" value="inventarioprocedimentioneri.tipicausalioneri.coDescrizione" />
							<jsp:param name="pathPropertyCode" value="inventarioprocedimentioneri.tipicausalioneri.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipicausalioneriForInventarioprocedimentioneri.htm" />	
							<jsp:param name="titleKey" value="label.ricerca_tipicausalioneri" />
							<jsp:param name="id_help" value="help_tipicausalioneri" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.importo_causale" />
					</td>
					<td>
						<spring-form:input id="importo_causale_id" path="inventarioprocedimentioneri.importo" cssStyle="text-align:right;" size="8" onblur="checkNumberValue(this);" />
						<spring-form:errors path="inventarioprocedimentioneri.importo" cssClass="error"/>
					</td>
				</tr>	
				<%-- 			
				<c:set var="displayImportoistruttoria" value="display: none;" />
				<c:if test="${isImportoIstruttoriaImpostabile eq true}">
					<c:set var="displayImportoistruttoria" value="" />
				</c:if>				
				<tr id="tr_aoImportoistruttoria" style="${displayImportoistruttoria}">
					<td>
						<fmt:message key="inventarioprocedimenti.label.importo_istruttoria" />
					</td>
					<td>
						<spring-form:input id="importo_istruttoria_id" path="inventarioprocedimentioneri.importoistruttoria" cssStyle="text-align:right;" size="8" onblur="checkNumberValue(this);" />
						<spring-form:errors path="inventarioprocedimentioneri.importoistruttoria" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.flag_pagato" />
					</td>
					<td>
						<spring-form:checkbox path="inventarioprocedimentioneri.flagPagato"/>
						<spring-form:errors path="inventarioprocedimentioneri.flagPagato" cssClass="error"/> 
					</td>
				</tr>
				--%>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="inventarioprocedimentioneri.note" cols="60" rows="4"/>
						<init:help idHelp="inventarioprocedimenti_help" textKey="inventarioprocedimenti.help.inventarioprocedimentioneri_note"/>
						<spring-form:errors path="inventarioprocedimentioneri.note" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice==null}">
				<li><a href="javascript:doSubmit('insertOneri.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.inventarioprocedimentioneri.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateOneri.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteOneri.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listoneri.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>